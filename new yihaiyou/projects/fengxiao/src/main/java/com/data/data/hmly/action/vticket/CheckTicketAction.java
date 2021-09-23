package com.data.data.hmly.action.vticket;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.action.vticket.enums.ErrMsg;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.common.MsgService;
import com.data.data.hmly.service.common.ProValidCodeService;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.entity.ProValidCode;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.common.entity.ProductValidateRecord;
import com.data.data.hmly.service.entity.SupplierType;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitDetail;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.util.Encryption;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caiys on 2016/1/27.
 */
@NotNeedLogin
public class CheckTicketAction extends FrameBaseAction {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private ProductValidateCodeService validateCodeService;
    @Resource
    private ProValidCodeService proValidCodeService;
    @Resource
    private TicketService ticketService;
    @Resource
    private MsgService msgService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private TicketPriceService ticketPriceService;


//    @Resource
//    private JszxOrderService jszxOrderService;
//
//    @Resource
//    private JszxOrderDetailService jszxOrderDetailService;

    @Resource
    private ProductService productService;

    private String account;
    private String password;
    private String errMsg;
    private String validateCode;
    private Integer validateCount;
    private String codeId;
    private Integer			page				= 1;
    private Integer			rows				= 10;
    private String          validateDate;
    private String          validateTime;   // 记录时点，以保证异步加载更多（当天存在新增数据）时列表连续性

    // 返回数据
    Map<String, Object> map					= new HashMap<String, Object>();
    private List<ProductValidateCode> validateCodes;
    private List<ProductValidateRecord> validateRecords;

    /**
     * 验票登录页面
     */
    public Result clogin() {
        if (StringUtils.isNotBlank(errMsg)) {
            ErrMsg em = ErrMsg.valueOf(errMsg);
            errMsg = em.getDesc();
        }
        return dispatch();
    }

    /**
     * 验票登录
     */
    public Result doClogin() {
        if (StringUtils.isBlank(account)) {
            return redirect("/vticket/checkTicket/clogin.jhtml?errMsg=" + ErrMsg.inputAccount);
        }
        if (StringUtils.isBlank(password)) {
            return redirect("/vticket/checkTicket/clogin.jhtml?errMsg=" + ErrMsg.inputPassword);
        }
        SysUser user = new SysUser();
        user.setAccount(account);
        user.setPassword(Encryption.encry(password));
        user.setUserType(UserType.CompanyManage);
        SysUser loginuser = sysUserService.findLoginUser(user);
        if (loginuser == null) {
            return redirect("/vticket/checkTicket/clogin.jhtml?errMsg=" + ErrMsg.accountInfoErr);
        }
        if (loginuser.getStatus() != UserStatus.activity) {
            return redirect("/vticket/checkTicket/clogin.jhtml?errMsg=" + ErrMsg.accountLocked);
        }
        SysUnit loginUnit = loginuser.getSysUnit();
        if (loginUnit == null || loginUnit.getCompanyUnit() == null) {
            return redirect("/vticket/checkTicket/clogin.jhtml?errMsg=" + ErrMsg.unitNotExisted);
        }
        if (loginUnit.getSysUnitDetail() == null || (loginUnit.getSysUnitDetail().getSupplierType() != SupplierType.sailboat
                && loginUnit.getSysUnitDetail().getSupplierType() != SupplierType.ticket)) {
            return redirect("/vticket/checkTicket/clogin.jhtml?errMsg=" + ErrMsg.unitTypeError);
        }
        loginuser.setLoginNum(loginuser.getLoginNum() + 1);
        sysUserService.updateUser(loginuser);
        SysUnit unit = new SysUnit();
        unit.setId(loginuser.getSysUnit().getId());
        unit.setName(loginuser.getSysUnit().getName());
        unit.setUnitType(loginuser.getSysUnit().getUnitType());
        unit.setUnitNo(loginuser.getSysUnit().getUnitNo());
        SysUnit companyUnit = new SysUnit();
        if (loginuser.getSysUnit().getCompanyUnit() != null) {
            companyUnit.setId(loginuser.getSysUnit().getCompanyUnit().getId());
            companyUnit.setName(loginuser.getSysUnit().getCompanyUnit().getName());
            companyUnit.setUnitType(loginuser.getSysUnit().getCompanyUnit().getUnitType());
            companyUnit.setUnitNo(loginuser.getSysUnit().getCompanyUnit().getUnitNo());
            SysUnitDetail detail = new SysUnitDetail();
            detail.setScenicid(loginuser.getSysUnit().getCompanyUnit().getSysUnitDetail().getScenicid());
            companyUnit.setSysUnitDetail(detail);
            SysSite site = new SysSite();
            site.setId(loginuser.getSysSite().getId());
            site.setSitename(loginuser.getSysSite().getSitename());
            companyUnit.setSysSite(site);
        }
        unit.setCompanyUnit(companyUnit);
        loginuser.setSysUnit(unit);
        getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, loginuser);
//        getSession().setAttribute("account", user.getAccount());
//        getSession().setAttribute("staffName", user.getUserName());
        return redirect("/vticket/checkTicket/check.jhtml");
    }



    /**
     * 验票页面
     */
    public Result check() {
        SysUser loginuser = getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
        if (loginuser == null) {    // 用户未登录
            return redirect("/vticket/checkTicket/clogin.jhtml");
        }
        return dispatch("/WEB-INF/jsp/vticket/checkTicket/checkTicket.jsp");
    }

    /**
     * 验票记录
     */
    public Result checkRecord() {
        SysUser loginuser = getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
        if (loginuser == null) {    // 用户未登录
            return redirect("/vticket/checkTicket/clogin.jhtml");
        }
        // 参数
        Date dateEnd = new Date();
        if (StringUtils.isNotBlank(validateDate)) {
            String today = DateUtils.format(dateEnd, "yyyy-MM-dd");
            if (today.compareTo(validateDate) <= 0) {   // 如果是当天，设定到具体时点
                validateTime = DateUtils.format(dateEnd, "yyyy-MM-ddHHmmssSSS");
            } else {
                validateTime = validateDate + "235959999";
            }
        } else {    // 默认日期
            validateTime = DateUtils.format(dateEnd, "yyyy-MM-ddHHmmssSSS");
            validateDate = DateUtils.format(dateEnd, "yyyy-MM-dd");
        }
        dateEnd = DateUtils.getDate(validateTime, "yyyy-MM-ddHHmmssSSS");
        Date dateStart = DateUtils.getDate(validateDate, "yyyy-MM-dd");

        Page pageInfo = new Page(page, rows);
        ProductValidateRecord pvr = new ProductValidateRecord();
        pvr.setScenicId(loginuser.getSysUnit().getCompanyUnit().getSysUnitDetail().getScenicid());
        pvr.setValidateTimeStart(dateStart);
        pvr.setValidateTimeEnd(dateEnd);
        validateRecords = validateCodeService.findValidateRecords(pvr, pageInfo);
//        ProductValidateCode pvc = new ProductValidateCode();
//        pvc.setUsed(1); // 1-已使用
//        pvc.setUpdateTimeStart(dateStart);
//        pvc.setUpdateTimeEnd(dateEnd);
//        validateCodes = validateCodeService.findCheckRecord(pageInfo, pvc, loginuser, isSupperAdmin(loginuser), isSiteAdmin(loginuser));
        return dispatch();
    }

    /**
     * 验票记录加载更多
     */
    @AjaxCheck
    public Result loadMoreRecord() {
        SysUser loginuser = getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
        if (loginuser == null) {
            simpleResult(map, false, "用户未登录!");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        // 参数
        Date dateEnd = DateUtils.getDate(validateTime, "yyyy-MM-ddHHmmssSSS");
        Date dateStart = DateUtils.getDate(validateTime.substring(0, 10), "yyyy-MM-dd");

        Page pageInfo = new Page(page, rows);
        ProductValidateRecord pvr = new ProductValidateRecord();
        pvr.setScenicId(loginuser.getSysUnit().getCompanyUnit().getSysUnitDetail().getScenicid());
        pvr.setValidateTimeStart(dateStart);
        pvr.setValidateTimeEnd(dateEnd);
        validateRecords = validateCodeService.findValidateRecords(pvr, pageInfo);
//        ProductValidateCode pvc = new ProductValidateCode();
//        pvc.setUsed(1); // 1-已使用
//        pvc.setUpdateTimeStart(dateStart);
//        pvc.setUpdateTimeEnd(dateEnd);
//        validateCodes = validateCodeService.findCheckRecord(pageInfo, pvc, loginuser, isSupperAdmin(loginuser), isSiteAdmin(loginuser));
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (ProductValidateRecord record : validateRecords) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", record.getId());
            map.put("nameAndCount", record.getNameAndCount());
            map.put("buyerMobile", record.getBuyerMobile());
            map.put("validateTimeStr", record.getValidateTimeStr());
            data.add(map);
        }
        simpleResult(map, true, "");
//        map.put("page", page);
        map.put("data", data);
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }

    /**
     * 获取验票产品
     */
//    @AjaxCheck
//    public Result doGetTicket() {
//        if (StringUtils.isBlank(validateCode)) {
//            simpleResult(map, false, "请输入验证码!");
//            JSONObject json = JSONObject.fromObject(map);
//            return json(json);
//        }
//        SysUser loginuser = getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
//        if (loginuser == null) {
//            simpleResult(map, false, "用户未登录!");
//            JSONObject json = JSONObject.fromObject(map);
//            return json(json);
//        }
//        Long scenicId = loginuser.getSysUnit().getCompanyUnit().getSysUnitDetail().getScenicid();
//        ProductValidateCode productValidateCode = validateCodeService.validateByCode(validateCode, scenicId);
//        if (productValidateCode == null) {
//            simpleResult(map, false, "无效验证码!");
//            JSONObject json = JSONObject.fromObject(map);
//            return json(json);
//        }
//        // 检查验票数量是否合法
//        if (validateCount != null) {
//            if (validateCount > productValidateCode.getOrderCount()) {
//                simpleResult(map, false, "超过可验票数量(" + productValidateCode.getOrderCount() + "张)！");
//                return jsonResult(map);
//            }
//        } else {
//            validateCount = productValidateCode.getOrderCount();
//        }
//        JszxOrder order = jszxOrderService.load(productValidateCode.getOrderId());
//        JszxOrderDetail jszxOrderDetail = jszxOrderDetailService.getOrderDetailByOrderNo(productValidateCode.getTicketNo(), order);
//        Long endLong = com.zuipin.util.DateUtils.getDateDiffLong(jszxOrderDetail.getEndTime(), new Date());
//        String content = "";
//        if (endLong < 0 ) {
//            content = "此验证码已经过期，请重新购买！";
//            simpleResult(map, false, content);
//            JSONObject json = JSONObject.fromObject(map);
//            return json(json);
//        }
//
//        map.put("codeId", productValidateCode.getId());
//        if (productValidateCode.getProduct() != null) {
//            map.put("orderNo", productValidateCode.getTicketNo());
//            map.put("productId", productValidateCode.getProduct().getId());
//            map.put("productName", productValidateCode.getProduct().getName());
//            map.put("orderCount", productValidateCode.getOrderCount());
//            map.put("validateCount", validateCount);
//        } else {
//            map.put("productName", "找不到对应产品!");
//        }
//        simpleResult(map, true, "");
//        JSONObject json = JSONObject.fromObject(map);
//        return json(json);
//    }

    /**
     * 验票
     */
    @AjaxCheck
    public Result doCheck() {
        if (StringUtils.isBlank(codeId)) {
            simpleResult(map, false, "很抱歉，无效参数!");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        SysUser loginuser = getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
        if (loginuser == null) {
            simpleResult(map, false, "很抱歉，用户未登录!");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        Long supplierId = loginuser.getSysUnit().getCompanyUnit().getId();
        ProValidCode pvCode = new ProValidCode();
//        ProductValidateCode pvCode = new ProductValidateCode();
        pvCode.setSupplierId(supplierId);
        ProValidCode productValidateCode = proValidCodeService.checkVliadateCode(codeId, pvCode);
        if (productValidateCode == null) {
            simpleResult(map, false, "很抱歉，当前验证码不存在！");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        if (productValidateCode.getUsed() == 1) {
            simpleResult(map, false, "很抱歉，验证码已验证!");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        } else if (productValidateCode.getUsed() == -1) {
            simpleResult(map, false, "很抱歉，验证码已经无效!");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        productValidateCode.syncUsed(productValidateCode.getInvalidTime()); // 有效期之前已经判断，此处暂时省略
        productValidateCode.setValidateUser(loginuser);
        proValidCodeService.update(productValidateCode);
        if (productValidateCode.getUsed() == -1) {
            simpleResult(map, false, "很抱歉，验证码已过期");
        } else {
            simpleResult(map, true, "恭喜您，验证成功！");
        }
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }

    /**
     * 提供下单时验证码生成和短信发送
     * 数据格式如下：
     * 参数名          类型        是否必填      描述
     * productId      Long        是           产品标识
     * buyerName      String      是           购买人
     * buyerMobile    String      是           购买人手机号
     * orderCount     Integer     是           购买数量
     * userId         Long        否           用户标识
     * orderNo        String      否           订单号（订单标识）
     */
    @AjaxCheck
    public Result genCode() {
        // 校验ip是否在允许访问列表中，参见config.properties键值VTICKET_ALLOW_IPS
        String vticketAllowIps = propertiesManager.getString("VTICKET_ALLOW_IPS");
        String ip = com.zuipin.util.StringUtils.getIpAddr(getRequest());
        if (StringUtils.isBlank(vticketAllowIps) || vticketAllowIps.indexOf(ip) < 0) {
            simpleResult(map, false, "IP:" + ip + "没有访问权限");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }
        // 参数校验
        String relateOrderId = (String) getParameter("orderId");
        String productId = (String) getParameter("productId");   // 产品标识
        String buyerName = (String) getParameter("buyerName");   // 购买人
        String buyerMobile = (String) getParameter("buyerMobile"); // 购买人手机号
        String orderCount = (String) getParameter("orderCount");   // 购买数量
        String userId = (String) getParameter("userId");            // 用户标识
        String orderNo = (String) getParameter("orderNo");          // 订单号（订单标识）
        String errorMsg = null;

        if (StringUtils.isBlank(productId)) {
            errorMsg = "产品标识不能为空";
        } else if (StringUtils.isBlank(buyerName)) {
            errorMsg = "购买人不能为空";
        } else if (StringUtils.isBlank(buyerMobile)) {
            errorMsg = "购买人手机号不能为空";
        } else if (StringUtils.isBlank(orderCount)) {
            errorMsg = "购买数量不能为空";
        }
        if (StringUtils.isNotBlank(errorMsg)) {
            simpleResult(map, false, errorMsg);
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        }

        Long supplierId = null;
        if (getParameter("supplierId") != null) {
            //
            supplierId = Long.parseLong(getParameter("supplierId").toString());
        }
        String supplierName = null;
        if (getParameter("supplierName") != null) {
            //
            supplierName = getParameter("supplierName").toString();
        }

        try {
            ProductValidateCode productValidateCode = new ProductValidateCode();
            Ticket ticket = ticketService.findTicketById(Long.valueOf(productId));
            productValidateCode.setProduct(ticket);
            productValidateCode.setBuyerName(buyerName);
            productValidateCode.setBuyerMobile(buyerMobile);
            productValidateCode.setOrderInitCount(Integer.valueOf(orderCount));
            productValidateCode.setOrderCount(Integer.valueOf(orderCount));
            productValidateCode.setRefundCount(0);
            if (StringUtils.isNotBlank(userId)) {
                User buyer = new User();
                buyer.setId(Long.valueOf(userId));
                productValidateCode.setBuyer(buyer);
            }
            productValidateCode.setOrderNo(orderNo);
            productValidateCode.setUsed(0);
            productValidateCode.setCreateTime(new Date());

            if (supplierId != null) {
                productValidateCode.setSupplierId(supplierId);
            }
            if (StringUtils.isNotBlank(supplierName)) {
                productValidateCode.setSupplierName(supplierName);
            }
            if (StringUtils.isNotBlank(relateOrderId)) {
                productValidateCode.setRelateOrderId(relateOrderId);
            }

            msgService.addAndSendMsgCode(productValidateCode);
            simpleResult(map, true, "处理成功");
            JSONObject json = JSONObject.fromObject(map);
            return json(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        simpleResult(map, false, "请求处理失败");
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }


    /**
     * 该验证码下的订单门票
     * @return
     */
//    public Result getVcheckProTicketList() {
//
//        String orderNo = (String) getParameter("orderNO");
//        String productId = (String) getParameter("productId");
//
//        if (StringUtil.isNotBlank(orderNo) && StringUtil.isNotBlank(productId)) {
//            JszxOrderDetail detail = jszxOrderDetailService.getOrderDetailByOrderNo(orderNo);
//
//            map.put("detail", detail);
//
//            simpleResult(map, true, "");
//
//            return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig("")));
//
//        }
//
//        simpleResult(map, false, "");
//        return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig("")));
//    }


//    public List<JszxOrderDetailEntity> formatterOticket(List<JszxOrderDetail> jszxOrderDetails) {
//
//        List<JszxOrderDetailEntity> jszxOrderDetailEntityList = new ArrayList<JszxOrderDetailEntity>();
//
//        Set<Long> ticketPriceIdSet = new HashSet<Long>();
//
//        for (JszxOrderDetail jszxOrderDetail : jszxOrderDetails) {
//            ticketPriceIdSet.add(jszxOrderDetail.getTypePriceId());
//        }
//
//        Map<String, List<JszxOrderDetail>> tempMap = new HashMap<String, List<JszxOrderDetail>>();
//
//        Iterator<Long> it = ticketPriceIdSet.iterator();
//
//        for (Long tpIid : ticketPriceIdSet) {
//            List<JszxOrderDetail> jszxOrderDetailTemp = new ArrayList<JszxOrderDetail>();
//            for (JszxOrderDetail temp : jszxOrderDetails) {
//                TicketPrice ticketPrice = ticketPriceService.getPrice(tpIid);
//                if (ticketPrice.getId() == temp.getTypePriceId()) {
//                    jszxOrderDetailTemp.add(temp);
//                }
//            }
//            tempMap.put(tpIid.toString(), jszxOrderDetailTemp);
//        }
//
//        for (String key : tempMap.keySet()) {
//
//            JszxOrderDetail jszxOrderDetail = tempMap.get(key).get(0);
//            JszxOrderDetailEntity jszxOrderDetailEntity = new JszxOrderDetailEntity();
//            jszxOrderDetailEntity.setName(jszxOrderDetail.getTicketName());
//            jszxOrderDetailEntity.setType(ticketPriceService.findById(jszxOrderDetail.getTypePriceId()).getType());
//            jszxOrderDetailEntity.setCount(tempMap.get(key).size());
//            jszxOrderDetailEntity.setPrice(jszxOrderDetail.getPrice());
//            jszxOrderDetailEntityList.add(jszxOrderDetailEntity);
//
//        }
//
//        return jszxOrderDetailEntityList;
//
//    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<ProductValidateCode> getValidateCodes() {
        return validateCodes;
    }

    public void setValidateCodes(List<ProductValidateCode> validateCodes) {
        this.validateCodes = validateCodes;
    }

    public Boolean isSupperAdmin(SysUser user) {
        return UserType.AllSiteManage == user.getUserType();
    }

    public Boolean isSiteAdmin(SysUser user) {
        return UserType.SiteManage == user.getUserType();
    }

    public String getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(String validateDate) {
        this.validateDate = validateDate;
    }

    public String getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(String validateTime) {
        this.validateTime = validateTime;
    }

    public Integer getValidateCount() {
        return validateCount;
    }

    public void setValidateCount(Integer validateCount) {
        this.validateCount = validateCount;
    }

    public List<ProductValidateRecord> getValidateRecords() {
        return validateRecords;
    }

    public void setValidateRecords(List<ProductValidateRecord> validateRecords) {
        this.validateRecords = validateRecords;
    }
}
