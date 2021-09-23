package com.data.data.hmly.action.balance;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.BankCardService;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.balance.AccountLogService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.AccountLog;
import com.data.data.hmly.service.balance.entity.enums.AccountStatus;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitDetail;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderCostType;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.pay.CmbPayService;
import com.data.data.hmly.service.pay.WeChatPayService;
import com.data.data.hmly.service.pay.entity.BankInfo;
import com.data.data.hmly.service.pay.util.httpclient.HttpUtil;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.util.PageData;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NotNeedLogin;
import com.framework.struts.XmlResult;
import com.gson.WeChatPay;
import com.gson.bean.PayResult;
import com.opensymphony.xwork2.Result;
import com.zuipin.exception.BizLogicException;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BalanceAction extends FrameBaseAction {
    private Map<String, Object> map = new HashMap<String, Object>();

    private AccountLog accountLog = new AccountLog();
    @Resource
    private AccountLogService accountLogService;
    @Resource
    private ProductService productService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;


    @Resource
    private SysUserService sysUserService;
    @Resource
    private BalanceService balanceService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private WeChatPayService weChatPayService;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private SysUnitService sysUnitService;
    @Resource
    private CmbPayService cmbPayService;
    @Resource
    private BankCardService bankCardService;


    private SysUser user;
    private Order order = new Order();
    private Long accountId;
    private Product product;
    private Long productId;
    private Long orderId;
    private Long orderDetailId;
    private String payForm;
    private Map<String, Object> orderMap = new HashMap<String, Object>();
    private String payWay;
    private String errMsg;
    private String detailUrl;
    private Float money;
    private String remark;
    private List<AccountLog> accountLogList = new ArrayList<AccountLog>();
    private List<Long> orderDetailIds = new ArrayList<Long>();
    private PageData<AccountLog> alogPageData = new PageData<AccountLog>();

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private Integer			page				= 1;
    private Integer			rows				= 10;

    public Result balanceMgr() {
        return dispatch();
    }


    /**
     * 商户对账单确认
     * @return
     */
    public Result cfmOrderBillSummary() {
        String billSummaryId = (String) getParameter("billSummaryId");
        map = balanceService.doCfmOrderBillSummary(Long.valueOf(billSummaryId), getLoginUser());
        return jsonResult(map);
    }


    /**
     * 已支付确认订单
     * @return
     */
    public Result doPayedConfirm() {
        Map<String, Object> resutlMap = new HashMap<String, Object>();
        if (orderId == null || orderDetailId == null) {
            simpleResult(resutlMap, false, "请求数据出错！");
            return jsonResult(resutlMap);
        }
        // 根据订单详情做退款
        Order order = orderService.get(orderId);
        OrderDetail optDetail = orderDetailService.get(Long.valueOf(orderDetailId));    // 本次操作的订单详情

        String remark = (String) getParameter("remark");
        // 处理订单详情退款
        if (optDetail.getStatus() != OrderDetailStatus.PAYED) {
            simpleResult(resutlMap, false, "该订单无法做确认操作，请尝试获取最新数据进行操作！");
            return jsonResult(resutlMap);
        }

        balanceService.doPayedConfirm(order, optDetail, getLoginUser());
        simpleResult(resutlMap, true, "");
        return jsonResult(resutlMap);
    }

    public Result getFundsFlowInof() {
        SysUser loginUser = sysUserService.load(getLoginUser().getId());
        SysUnit unit = loginUser.getSysUnit();
        map = balanceService.findFundsFlowInfo(unit);
        if (loginUser.getBalance() == null) {
            map.put("totalBalance", 0D);
        } else {
            map.put("totalBalance", loginUser.getBalance());
        }

        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 订单退款（用户已支付，订单不是成功状态）
     * @return
     */
    public Result doFailOrderRefund() {
        if (orderId == null || orderDetailId == null) {
            orderMap.put("flag", false);
            orderMap.put("reMsg", "请求数据出错！");
            return jsonResult(orderMap);
        }
        // 根据订单详情做退款
        Order order = orderService.get(orderId);
        OrderDetail optDetail = orderDetailService.get(Long.valueOf(orderDetailId));    // 本次操作的订单详情

        String remark = (String) getParameter("remark");
        // 处理订单详情退款
        if (optDetail.getStatus() != OrderDetailStatus.FAILED && optDetail.getStatus() != OrderDetailStatus.PAYED) {
            orderMap.put("flag", false);
            orderMap.put("reMsg", "该订单无法做退款操作，请尝试获取最新数据进行操作！");
            return jsonResult(orderMap);
        }
        try {
            balanceService.doFailOrderRefund(order, optDetail, remark, getLoginUser());
        } catch (BizLogicException be) {
            be.printStackTrace();
            orderMap.put("flag", false);
            orderMap.put("reMsg", be.getMessage());
            return jsonResult(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            orderMap.put("flag", false);
            orderMap.put("reMsg", "退款失败！");
            return jsonResult(orderMap);
        }
        orderMap.put("flag", true);
        return jsonResult(orderMap);
    }

    /**
     * 订单退款（用户已支付，订单是成功状态）
     * @return
     */
    public Result doRefund() {
        if (orderId == null || orderDetailId == null) {
            orderMap.put("flag", false);
            orderMap.put("reMsg", "请求数据出错！");
            return jsonResult(orderMap);
        }
        // 根据订单详情做退款
        Order order = orderService.get(orderId);
        OrderDetail optDetail = orderDetailService.get(Long.valueOf(orderDetailId));    // 本次操作的订单详情

        // 处理订单详情退款
        if (optDetail.getStatus() != OrderDetailStatus.SUCCESS) {  // 状态不为预订成功
            orderMap.put("flag", false);
            orderMap.put("reMsg", "该订单无法做退款操作，请尝试获取最新数据进行操作！");
            return jsonResult(orderMap);
        }
        try {
            balanceService.doRefundOrder(order, optDetail, getLoginUser());
        } catch (BizLogicException be) {
            be.printStackTrace();
            orderMap.put("flag", false);
            orderMap.put("reMsg", be.getMessage());
            return jsonResult(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            orderMap.put("flag", false);
            orderMap.put("reMsg", "退款失败！");
            return jsonResult(orderMap);
        }
        orderMap.put("flag", true);
        return jsonResult(orderMap);
    }

    /**
     * 订单退款（用户已支付，订单不是成功状态） - 轮渡船票
     * @return
     */
    public Result doFailOrderRefundFerry() {
        if (orderId == null) {
            orderMap.put("flag", false);
            orderMap.put("reMsg", "请求数据出错！");
            return jsonResult(orderMap);
        }
        String remark = (String) getParameter("remark");
        // 根据订单详情做退款
        FerryOrder ferryOrder = ferryOrderService.getById(Long.valueOf(orderId));
        if (ferryOrder.getStatus() != OrderStatus.FAILED || ferryOrder.getStatus() != OrderStatus.PAYED) {
            orderMap.put("flag", false);
            orderMap.put("reMsg", "该订单无法做退款操作，请尝试获取最新数据进行操作！");
            return jsonResult(orderMap);
        }
        try {
            balanceService.doFailOrderRefundFerry(ferryOrder, remark, getLoginUser());
        } catch (BizLogicException be) {
            be.printStackTrace();
            orderMap.put("flag", false);
            orderMap.put("reMsg", be.getMessage());
            return jsonResult(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            orderMap.put("flag", false);
            orderMap.put("reMsg", "退款失败！");
            return jsonResult(orderMap);
        }
        orderMap.put("flag", true);
        return jsonResult(orderMap);
    }

    /**
     * 余额管理列表
     * @return
     */
    @AjaxCheck
    public Result findBalanceMgrList() {
        // 参数
        String dateStartStr = (String) getParameter("dateStart");
        String dateEndStr = (String) getParameter("dateEnd");
        String companyIdStr = (String) getParameter("companyId");
        String statusStr = (String) getParameter("status");
        String inTypeStr = (String) getParameter("inType"); // 多个类型逗号拼接
        Long companyId = null;
        if (StringUtils.isNotBlank(companyIdStr)) {
            companyId = Long.parseLong(companyIdStr);
        }
        if (StringUtils.isNotBlank(statusStr)) {
            AccountStatus status = AccountStatus.valueOf(statusStr);
            accountLog.setStatus(status);
        }
        if (StringUtils.isNotBlank(inTypeStr)) {
            List<AccountType> types = new ArrayList<AccountType>();
            String[] inType = inTypeStr.split(",");
            for (String typeStr : inType) {
                types.add(AccountType.valueOf(typeStr));
            }
            accountLog.setInType(types);
        }

        Page pageInfo = new Page(page, rows);
        if (StringUtils.isNotBlank(dateStartStr)) {
            accountLog.setCreateTimeStart(DateUtils.getDate(dateStartStr, "yyyy-MM-dd"));
        }
        if (StringUtils.isNotBlank(dateEndStr)) {
            accountLog.setCreateTimeEnd(DateUtils.getDate(dateEndStr + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        accountLogList = accountLogService.findBalanceMgrList(accountLog, pageInfo, companyId, getLoginUser(), isSiteAdmin(), isSupperAdmin());
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("user");
        return datagrid(accountLogList, pageInfo.getTotalCount(), jsonConfig);
    }

    /**
     * 一海游余额管理列表
     * @return
     */
    @AjaxCheck
    public Result yhyBalanceMgrList() {
        // 参数
        String dateStartStr = (String) getParameter("dateStart");
        String dateEndStr = (String) getParameter("dateEnd");
        Integer pageIndex = start / length + 1;
        Page pageInfo = new Page(pageIndex, length);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Long companyId = getLoginUser().getSysUnit().getCompanyUnit().getId();
        if (StringUtils.isNotBlank(dateStartStr)) {
            accountLog.setCreateTimeStart(DateUtils.getDate(dateStartStr, "yyyy-MM-dd"));
        }
        if (StringUtils.isNotBlank(dateEndStr)) {
            accountLog.setCreateTimeEnd(DateUtils.getDate(dateEndStr + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        accountLogList = accountLogService.findYhyBalanceMgrList(accountLog, pageInfo, getLoginUser());

        result.put("data", accountLogList);
        result.put("recordsTotal", pageInfo.getTotalCount());
        result.put("recordsFiltered", pageInfo.getTotalCount());
        result.put("draw", draw);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return json(JSONObject.fromObject(result, jsonConfig));
    }


    /**
     * 查询余额
     * @return
     */
    @AjaxCheck
    public Result findBalance() {
        String companyUnitIdStr = (String) getParameter("companyUnitId");
        Long companyUnitId = getCompanyUnit().getId();  // 如果没指定，取当前用户余额
        if (StringUtils.isNotBlank(companyUnitIdStr)) {
            companyUnitId = Long.valueOf(companyUnitIdStr);
        }
        SysUser manageUser = balanceService.findCompanyManager(companyUnitId);
        map.put("balance", manageUser.getBalance());
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 设置余额
     * TODO 如果是提现，可提现资金应该扣除冻结部分资金（比如下单需确认的订单金额）
     * @return
     */
    @AjaxCheck
    public Result optBalance() {
        AccountType type = AccountType.valueOf((String) getParameter("type"));
        Long companyUnitId = Long.valueOf((String) getParameter("companyUnitId"));
        Double amount = Double.valueOf((String) getParameter("amount"));
        SysUser manageUser = balanceService.findCompanyManager(companyUnitId);

        if (amount <= 0) {
            simpleResult(map, false, "操作失败，提现金额必须大于0");
            return jsonResult(map);
        }

        if (type == AccountType.outlinewd) { // 如果是提现，判断余额是否足够
            if (manageUser.getBalance() < amount) {
                simpleResult(map, false, "操作失败，金额超出余额");
                return jsonResult(map);
            }
        }
        balanceService.updateBalance(amount, type, manageUser.getId(), null,
                getLoginUser().getId(), null, null);
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 申请提现
     * @return
     */
    @AjaxCheck
    public Result applyWithdraw() {
        Double amount = Double.valueOf((String) getParameter("amount"));

        if (amount <= 0) {
            simpleResult(map, false, "操作失败，提现金额必须大于0");
            return jsonResult(map);
        }

        SysUser manageUser = balanceService.findBalanceAccountBy(getLoginUser().getId());
        if (manageUser.getBalance() < amount) {
            simpleResult(map, false, "操作失败，金额超出余额");
            return jsonResult(map);
        }
        Double frozenBalance = manageUser.getFrozenBalance();

        if (frozenBalance == null) {
            frozenBalance = 0D;
        }

        manageUser.setFrozenBalance(frozenBalance + amount);
        manageUser.setBalance(manageUser.getBalance() - amount);
        sysUserService.updateUser(manageUser);

        // 提交申请记录
        AccountLog accountLog = new AccountLog();
        accountLog.setType(AccountType.outlinewd);
//        accountLog.setOrderNo(runningNo);
        accountLog.setMoney(amount);
        accountLog.setBalance(manageUser.getBalance());
        accountLog.setDescription(AccountType.outlinewd.getDescription());
        accountLog.setStatus(AccountStatus.submit);
        accountLog.setUser(getLoginUser());
        accountLog.setCreateTime(new Date());
        accountLog.setCompanyUnit(getCompanyUnit());
//        accountLog.setSourceId(sourceId);
        balanceService.saveAccountLog(accountLog);
        simpleResult(map, true, "");
        return jsonResult(map);
    }


    /**
     * 申请提现
     * @return
     */
    @AjaxCheck
    public Result applyBankWithdraw() {
        Double amount = Double.valueOf((String) getParameter("amount"));

        SysUnit sysUnit = sysUnitService.findUnitById(getLoginUser().getSysUnit().getId());

        if (sysUnit == null && sysUnit.getSysUnitDetail() == null) {
            simpleResult(map, false, "商户基本信息不完善，无法提现，请维护商户基本信息!");
            return jsonResult(map);
        }

        SysUnitDetail sysUnitDetail = sysUnit.getSysUnitDetail();
        if (StringUtils.isBlank(sysUnitDetail.getCrtacc())
                || StringUtils.isBlank(sysUnitDetail.getCrtbnk())
                || StringUtils.isBlank(sysUnitDetail.getCrtnam())) {
            simpleResult(map, false, "暂无银行账户信息，无法提现，请维护商户银行账户信息!");
            return jsonResult(map);
        }

        if (amount <= 0) {
            simpleResult(map, false, "操作失败，提现金额必须大于0");
            return jsonResult(map);
        }
        SysUser manageUser = balanceService.findBalanceAccountBy(getLoginUser().getId());
        if (manageUser.getBalance() < amount) {
            simpleResult(map, false, "操作失败，金额超出余额");
            return jsonResult(map);
        }

        user = getLoginUser();
        if (StringUtils.isBlank(sysUnitDetail.getCrtacc())) {
            simpleResult(map, false, "操作失败，请维护银行卡信息！");
            return jsonResult(map);
        }
        order  = balanceService.saveUnitBalanceOrder(user, amount);

        BankInfo bankInfo = new BankInfo();
        bankInfo.setBankName(sysUnitDetail.getCrtbnk());
        bankInfo.setBankNo(sysUnitDetail.getCrtacc());
        bankInfo.setHolderName(sysUnitDetail.getCrtnam());
        bankInfo.setCity(sysUnitDetail.getCrtCity().getName());
        bankInfo.setProvince(sysUnitDetail.getCrtCity().getFather().getName());
        Map<String, Object> cmbResultMap = cmbPayService.doWithdrawRequest(bankInfo, order);
        if (!(Boolean) cmbResultMap.get("success")) {
            simpleResult(map, false, "提现操作失败，银行返回数据失败！");
            return jsonResult(map);
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 申请提现-通过
     * @return
     */
    @AjaxCheck
    public Result pass() {
        Long id = Long.valueOf((String) getParameter("id"));
        AccountLog al = balanceService.getAccountLog(id);
        SysUser manageUser = balanceService.findCompanyManager(al.getCompanyUnit().getId());
        if (manageUser.getFrozenBalance() < al.getMoney()) {
            simpleResult(map, false, "操作失败，金额超出余额");
            return jsonResult(map);
        }
        balanceService.doPass(al, manageUser);
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 申请提现-拒绝
     * @return
     */
    @AjaxCheck
    public Result reject() {
        Long id = Long.valueOf((String) getParameter("id"));
        String rejectReason = (String) getParameter("reason");
        AccountLog al = balanceService.getAccountLog(id);

        SysUser manageUser = balanceService.findCompanyManager(al.getCompanyUnit().getId());

        // 修改账户记录
        al.setUpdateTime(new Date());
        al.setAuditTime(new Date());
        al.setStatus(AccountStatus.reject);
        al.setRejectReason(rejectReason);
        balanceService.saveAccountLog(al);

        balanceService.doReject(al, manageUser);

        simpleResult(map, true, "");
        return jsonResult(map);
    }

    public Result getBalanceDataList() {
        Page pageInfo = new Page(page, rows);
//        accountLog.setType(AccountType.consume);
        accountLog.setStatus(AccountStatus.normal);
        accountLogList = accountLogService.getAccountList(accountLog, pageInfo, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin());
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("user");
        return datagrid(accountLogList, pageInfo.getTotalCount(), jsonConfig);
    }

    public Result getConsumeList() {
        Page pageInfo = new Page(page, rows);
        accountLog.setType(AccountType.consume);
        accountLog.setStatus(AccountStatus.normal);
        accountLogList = accountLogService.getAccountList(accountLog, pageInfo, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin());
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("user");
        return datagrid(accountLogList, pageInfo.getTotalCount(), jsonConfig);
    }


    public Result getRechargeList() {

        Page pageInfo = new Page(page, rows);

        accountLog.setType(AccountType.recharge);
        accountLog.setStatus(AccountStatus.normal);

        accountLogList = accountLogService.getAccountList(accountLog, pageInfo, getCompanyUnit(), getLoginUser(), isSiteAdmin(), isSupperAdmin());

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("user");

        return datagrid(accountLogList, pageInfo.getTotalCount(), jsonConfig);
    }


    /**
     * 提交充值订单
     * @return
     */
    public Result subPreRecharge() {

//        String productIdStr = (String) getParameter("productId");
//
//        if (StringUtils.isNotBlank(productIdStr)) {

//            product = productService.get(Long.parseLong(productIdStr));

            if (money <= 0l) {
                simpleResult(map, false, "充值金额不能小于0元");
                return jsonResult(map);
            }

            order.setName("充值");
            order.setCreateTime(new Date());
            order.setOrderType(OrderType.recharge); //充值
            if (money != null) {
                order.setPrice(money);
            }
            if (StringUtils.isNotBlank(remark)) {
                order.setRemark(StringUtils.htmlEncode(remark));
            }
            order.setPayType(OrderPayType.ONLINE);
            order.setStatus(OrderStatus.WAIT);
            order.setUser(getLoginUser());
            order.setCompanyUnit(getCompanyUnit());
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setFinalPrice(money);
            detail.setNum(money.intValue());
            detail.setProductType(ProductType.recharge);
            detail.setCostType(OrderCostType.recharge);
            detail.setStatus(OrderDetailStatus.WAITING);
            detail.setFinalPrice(money);
            detail.setTotalPrice(money);
//            detail.setProduct(product);
            List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
            orderDetailList.add(detail);
            order.setOrderDetails(orderDetailList);
            orderService.insert(order);
            orderDetailService.save(detail);
            if (order.getId() != null) {
                map.put("orderId", order.getId());
//                map.put("productId", product.getId());
                simpleResult(map, true, "提交成功");
            } else {
                simpleResult(map, false, "请新增一条充值产品");
            }
//        }


        return jsonResult(map);
    }

    public Result manage() {
        user = balanceService.findBalanceAccountBy(getLoginUser().getId());
//        user = sysUserService.load(getLoginUser().getId());
        return dispatch();
    }




    public Result createOrder() {
        if (money != null) {
            money = money;
        }
//        product = productService.getRchargeProduct(1f, ProductType.recharge);
        return dispatch();
    }

    /**
     * 申请确认jsp
     * @return
     */
    public Result request() {
        if (orderId != null) {
            order = orderService.get(orderId);
//            product = productService.get(productId);
        }
        return dispatch();
    }

    /**
     * 检测充值回调结果
     * @return
     */
    public Result checkPayBackResult() {
        order = orderService.get(orderId);
        map.put("orderId", order.getId());
        map.put("orderStatus", order.getStatus());
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 充值成功
     * @return
     */
    @NotNeedLogin
    public Result paySuccess() {
        if (orderId != null) {
            order = orderService.get(orderId);
            user = sysUserService.load(getLoginUser().getId());
        }
        return dispatch();
    }

    /**
     * 充值失败
     * @return
     */
    @NotNeedLogin
    public Result payFail() {
        if (orderId != null) {
            order = orderService.get(orderId);
            user = sysUserService.load(getLoginUser().getId());
        }
        return dispatch();
    }


    /**
     * 微信充值后回调
     * @return
     */
    @NotNeedLogin
    public Result weixinPayBack() throws IOException {
        HttpServletRequest request = getRequest();
        Map<String, String> map = HttpUtil.parseXml(request);
        String attach = map.get("attach");
        if (StringUtils.isBlank(attach)) {
            throw new RuntimeException("attach参数为空，无法处理");
        }
        String[] orderIdAndSiteId = attach.split(",");
        WechatAccount wechatAccount = weChatPayService.findSiteWechatAccount(Long.valueOf(orderIdAndSiteId[1]));
//        String paternerKey = propertiesManager.getString("WEBCHAT_KEY");
        PayResult payResult = WeChatPay.payBack(map, wechatAccount.getMchKey());

        Long orderId = Long.valueOf(orderIdAndSiteId[0]);
        Order order = orderService.get(orderId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (order.getStatus() == OrderStatus.SUCCESS || order.getStatus() == OrderStatus.FAILED) {
            // 通知微信已经处理成功（微信还是会多次回调，可能没写对）
            return new XmlResult(WeChatPay.notifyBack("SUCCESS", "OK"), "utf-8");
        }
        if (order.getStatus() != OrderStatus.WAIT) {
            return null;
        }

        // 处理结果
        if (payResult.isSuccess()) {
            order.setStatus(OrderStatus.SUCCESS);
        } else {
            order.setStatus(OrderStatus.FAILED);
            order.setWechatCode(payResult.getMsg());
        }
        balanceService.savePayResult(order);
        if (payResult.isSuccess()) {
            // 通知微信已经处理成功（微信还是会多次回调，可能没写对）
            return new XmlResult(WeChatPay.notifyBack("SUCCESS", "OK"), "utf-8");
        } else {
            return null;
        }
    }

    /**
     * 微信充值
     * @return
     */
    public Result wechatPay() {
        order = orderService.get(orderId);
        // 发起微信支付
        Integer amount = Float.valueOf(order.getPrice() * 100).intValue();
        PayResult payResult = weChatPayService.doPrePay(String.valueOf(order.getId()), order.getOrderNo(), amount, order.getName(), getSite().getId());

        // 更新支付类型，支付信息
        order.setPayType(OrderPayType.WECHAT);
        if (!payResult.isSuccess()) { // 如果失败，直接修改状态
            order.setStatus(OrderStatus.FAILED);
            order.setWechatCode(payResult.getMsg());
        } else {
            payForm = payResult.getCodeUrl();
            order.setWechatCode(payResult.getCodeUrl());
            order.setWechatTime(payResult.getExpireTime());
        }
        balanceService.savePayResult(order);

        if (!payResult.isSuccess()) { // 失败支付跳转失败页面
            return redirect("/balance/balance/payFail.jhtml?orderId=" + order.getId());
        }
        return dispatch();
    }

    /**
     * 招行充值
     * @return
     */
    public Result zhaohPay() {
        //  参数
        String payDate = (String) getParameter("payDate");

        order = orderService.get(orderId);
        order.setPayType(OrderPayType.ZHAOH);
        order.setWechatTime(DateUtils.getDate(payDate + "235959", "yyyyMMddHHmmss"));
        orderService.update(order);
        return dispatch();
    }

    /**
     * 查询招行配置
     * @return
     */
    @AjaxCheck
    public Result findZhaohCfg() {
        String billNo = StringUtils.paddingLeft((String) getParameter("orderId"), '0', 10);
        map.put("PayUrl", propertiesManager.getString("ZHAOH_PAY_URL"));
        map.put("BranchID", propertiesManager.getString("ZHAOH_BRANCH_ID"));
        map.put("CoNo", propertiesManager.getString("ZHAOH_CONO"));
        map.put("MerchantURL", propertiesManager.getString("ZHAOH_NOTIFY_URL"));
        map.put("BillNo", billNo);
        map.put("Date", DateUtils.format(new Date(), "yyyyMMdd"));
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 招行充值后回调
     * @return
     */
    @NotNeedLogin
    public Result zhaohPayBack() {
        // 参数
        String succeed = (String) getParameter("Succeed");    // 取值Y(成功)或N(失败)
        String coNo = (String) getParameter("CoNo");          // 商户号，6位长数字，由银行在商户开户时确定
        Long billNo = Long.valueOf((String) getParameter("BillNo")); // 定单号(由支付命令送来)，测试返回000000
        String amount = (String) getParameter("Amount");      // 实际支付金额(由支付命令送来)
        String date = (String) getParameter("Date");          // 交易日期(由支付命令送来)
        String msg = (String) getParameter("Msg");            // 银行通知用户的支付结果消息
        String signature = (String) getParameter("Signature"); // 银行用自己的Private Key对通知命令的签名

        // TODO 校验签名，待完善代码
        billNo = 0L;

                // 订单状态修改
        Order order = orderService.get(billNo);
        if (order.getStatus() == OrderStatus.SUCCESS) { // 已经是成功状态
            return redirect("/balance/balance/paySuccess.jhtml?orderId=" + order.getId());
        }
        if (order.getStatus() == OrderStatus.FAILED) {  // 已经是失败状态
            return redirect("/balance/balance/payFail.jhtml?orderId=" + order.getId());
        }
        if (order.getStatus() != OrderStatus.WAIT) {
            return null;
        }
        if ("Y".equals(succeed)) {
            order.setStatus(OrderStatus.SUCCESS);
        } else {
            order.setStatus(OrderStatus.FAILED);
        }
        order.setWechatCode(msg);
        balanceService.savePayResult(order);
        return null;
    }


    /**
     * 随机生成订单编号
     * @return
     */
    public String createOrderNo() {

        int code = 0;

        while (true) {
            int num = new Random().nextInt(1000000000);
            while (num < 100000000) {
                num = num * 10;
            }
            code = num;
            break;
        }

        return "A" + code;

    }


    public AccountLog getAccountLog() {
        return accountLog;
    }

    public void setAccountLog(AccountLog accountLog) {
        this.accountLog = accountLog;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPayForm() {
        return payForm;
    }

    public void setPayForm(String payForm) {
        this.payForm = payForm;
    }

    public Map<String, Object> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<String, Object> orderMap) {
        this.orderMap = orderMap;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public List<Long> getOrderDetailIds() {
        return orderDetailIds;
    }

    public void setOrderDetailIds(List<Long> orderDetailIds) {
        this.orderDetailIds = orderDetailIds;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
}
