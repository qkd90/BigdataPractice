package com.data.data.hmly.action.yhy;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.order.OrderForm;
import com.data.data.hmly.action.order.vo.OrderVo;
import com.data.data.hmly.enums.SysUnitImageType;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.contract.ContractService;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.contract.entity.nums.SettlementType;
import com.data.data.hmly.service.contract.entity.nums.ValuationModels;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitImage;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.OrderBillSummaryService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.vo.OrderDataForm;
import com.data.data.hmly.action.yhy.vo.CompanyInfoData;
import com.data.data.hmly.service.yhy.YhyCompanyInfoService;
import com.data.data.hmly.util.PageData;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dy on 2016/11/16.
 */
public class YhyHotelAction extends FrameBaseAction {

    @Resource
    private SysUnitService unitService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private ContractService contractService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderBillSummaryService orderBillSummaryService;
    @Resource
    private YhyCompanyInfoService companyInfoService;


    private SysUnit company = new SysUnit();
    private SysUser user = new SysUser();

    private Map<String, Object> map = new HashMap<String, Object>();
    private OrderForm orderFormVo = new OrderForm();
    private OrderDataForm orderDataFormVo = new OrderDataForm();
    private PageData<OrderVo> orderData = new PageData<OrderVo>();
    private PageData<OrderDetail> orderDetailData = new PageData<OrderDetail>();
    private OrderBillSummary orderBillSummary = new OrderBillSummary();
    private CompanyInfoData companyInfoData = new CompanyInfoData();
    private Long orderId;
    private Long detailId;
    private String orderProperty;
    private String orderType;
    private Integer sEcho;
    private Integer iDisplayStart;
    private Integer iDisplayLength;

    private Integer draw;
    private Integer start = 1;
    private Integer length = 10;


    /**
     * 订单详情
     * @return
     */
    public Result getHotelOrderDetail() {
        String detailIdStr = (String) getParameter("detailId");
        if (detailId != null) {
            OrderDetail orderDetail = orderDetailService.get(detailId);
            List<OrderDetail> orderDetails = Lists.newArrayList();
            orderDetails.add(orderDetail);
            map.put("orderDetails", orderDetails);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }
        return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig("operator")));
    }

    /**
     * 订单列表
     * @return
     */
    public Result getHotelOrderList() {
        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Boolean isSuperAdmin = isSupperAdmin();
        Boolean isSiteAdmin = isSiteAdmin();
        orderDataFormVo.setOrderProperty("createTime");
        List<OrderDetail> orderDetails = orderDetailService.listByAuthorize(orderDataFormVo, page, isSuperAdmin, isSiteAdmin, loginUser);
        result.put("data", orderDetails);
        result.put("recordsTotal", page.getTotalCount());
        result.put("recordsFiltered", page.getTotalCount());
        result.put("draw", draw);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("order", "product", "user",
                "operator");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    /**
     * 拼装商户信息数据
     * @param user
     * @param unit
     * @param contract
     * @return
     */
    public Map<String, Object> getTenantData(SysUser user, SysUnit unit, Contract contract) {
        Map<String, Object> tempMap = new HashMap<String, Object>();
        tempMap.put("userId", user.getId());
        tempMap.put("account", user.getAccount());
        tempMap.put("password", user.getPassword());
        tempMap.put("mobile", user.getMobile());
        tempMap.put("userName", user.getUserName());
        if (contract != null) {
            tempMap.put("effectiveTime", DateUtils.format(contract.getEffectiveTime(), "yyyy-MM-dd"));
            tempMap.put("expirationTime", DateUtils.format(contract.getExpirationTime(), "yyyy-MM-dd"));
            if (contract.getValuationModels() == ValuationModels.commissionModel) {
                tempMap.put("valuationModels", "按" + contract.getValuationValue() + "%佣金模式");
            } else if (contract.getValuationModels() == ValuationModels.fixedModel) {
                tempMap.put("valuationModels", "按" + contract.getValuationValue() + "元模式");
            } else {
                tempMap.put("valuationModels", "按底价模式");
            }

            if (contract.getSettlementType() == SettlementType.month) {
                tempMap.put("settlementType", "每月" + contract.getSettlementValue() + "日");
            } else if (contract.getSettlementType() ==  SettlementType.week) {
                if (contract.getSettlementValue() == 1) {
                    tempMap.put("settlementType", "每周日");
                } else if (contract.getSettlementValue() == 2) {
                    tempMap.put("settlementType", "每周一");
                } else if (contract.getSettlementValue() == 3) {
                    tempMap.put("settlementType", "每周二");
                } else if (contract.getSettlementValue() == 4) {
                    tempMap.put("settlementType", "每周三");
                } else if (contract.getSettlementValue() == 5) {
                    tempMap.put("settlementType", "每周四");
                } else if (contract.getSettlementValue() == 6) {
                    tempMap.put("settlementType", "每周五");
                } else if (contract.getSettlementValue() == 7) {
                    tempMap.put("settlementType", "每周六");
                }
            } else {
                tempMap.put("settlementType", "T+" + contract.getSettlementValue() + "日");
            }
        } else {
            tempMap.put("effectiveTime", "请签约合同");
            tempMap.put("expirationTime", "请签约合同");
            tempMap.put("valuationModels", "请签约合同");
            tempMap.put("settlementType", "请签约合同");
        }
        if (unit.getSysUnitDetail() != null) {
            tempMap.put("legalPerson", unit.getSysUnitDetail().getLegalPerson());
            tempMap.put("legalIdCardNo", unit.getSysUnitDetail().getLegalIdCardNo());
            tempMap.put("mainBusiness", unit.getSysUnitDetail().getMainBusiness());
        }
        if (unit.getSysUnitImages() != null && !unit.getSysUnitImages().isEmpty()) {
            List<String> idcardImgs = Lists.newArrayList();
            for (SysUnitImage image : unit.getSysUnitImages()) {
                if (image.getType() == SysUnitImageType.POSITIVE_IDCARD) {
                    tempMap.put("positiveIdcard", image.getPath());
                } else if (image.getType() == SysUnitImageType.OPPOSITIVE_IDCARD) {
                    tempMap.put("oppositiveIdcard", image.getPath());
                } else if (image.getType() == SysUnitImageType.BUSINESS_LICENSE) {
                    tempMap.put("businessLicenseImg", image.getPath());
                }
            }
            tempMap.put("idCardImg", idcardImgs);
        }

        return tempMap;

    }

    public Result hotelFinance() {
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_finance.jsp");
    }

    public Result hotelOrder() {
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_order.jsp");
    }

    public Result hotelTenant() {
        SysUnit unit = getCompanyUnit();
        company = unitService.findSysUnitById(unit.getId());
        user = sysUserService.findSysUserById(getLoginUser().getId());


        Contract contract = contractService.getContractByCompanyB(company);

        companyInfoData = companyInfoService.getCompanyInfo(company, user, contract);

        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_tenant.jsp");
    }

    /*public Result hotelStatement() {
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_statement.jsp");
    }*/

    public SysUnit getCompany() {
        return company;
    }

    public void setCompany(SysUnit company) {
        this.company = company;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public OrderForm getOrderFormVo() {
        return orderFormVo;
    }

    public void setOrderFormVo(OrderForm orderFormVo) {
        this.orderFormVo = orderFormVo;
    }

    public PageData<OrderVo> getOrderData() {
        return orderData;
    }

    public void setOrderData(PageData<OrderVo> orderData) {
        this.orderData = orderData;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(Integer iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public Integer getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(Integer iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public Integer getsEcho() {
        return sEcho;
    }

    public void setsEcho(Integer sEcho) {
        this.sEcho = sEcho;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public OrderBillSummary getOrderBillSummary() {
        return orderBillSummary;
    }

    public void setOrderBillSummary(OrderBillSummary orderBillSummary) {
        this.orderBillSummary = orderBillSummary;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public OrderDataForm getOrderDataFormVo() {
        return orderDataFormVo;
    }

    public void setOrderDataFormVo(OrderDataForm orderDataFormVo) {
        this.orderDataFormVo = orderDataFormVo;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
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

    public CompanyInfoData getCompanyInfoData() {
        return companyInfoData;
    }

    public void setCompanyInfoData(CompanyInfoData companyInfoData) {
        this.companyInfoData = companyInfoData;
    }
}
