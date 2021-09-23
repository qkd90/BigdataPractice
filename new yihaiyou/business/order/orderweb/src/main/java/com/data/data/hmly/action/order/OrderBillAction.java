package com.data.data.hmly.action.order;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.contract.ContractService;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.UnitType;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.OrderBillSummaryService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.ShenzhouOrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.ShenzhouOrder;
import com.data.data.hmly.service.order.entity.enums.OrderBillTarget;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/10/31.
 */
public class OrderBillAction extends FrameBaseAction {

    private int page;
    private int rows;
    private Integer draw;
    private Integer start = 1;
    private Integer length = 10;
    private String orderProperty;
    private String orderType;
    private OrderBillSummary orderBillSummary = new OrderBillSummary();
    private String billSummaryDateStr;
    private String billDateStr;
    private Long companyUnitId;

    @Resource
    private OrderBillSummaryService orderBillSummaryService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private ShenzhouOrderService shenzhouOrderService;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private SysUnitService sysUnitService;
    @Resource
    private ContractService contractService;

    private Map<String, Object> map = new HashMap<String, Object>();


    /**
     * 神州专车结算页面
     * @return
     */
    public Result shenzhouIndex() {
        return dispatch();
    }

    /**
     * 轮渡船票结算页面
     * @return
     */
    public Result ferryIndex() {
        return dispatch();
    }

    /**
     * 商户结算页面
     * @return
     */
    public Result index() {
        return dispatch();
    }


    /**
     * 账单明细
     * @return
     */
    @Deprecated
    public Result orderBillList() {
        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        List<OrderDetail> orderDetails = orderDetailService.getOrderBillDetail(orderBillSummary, page);
        result.put("data", orderDetails);
        result.put("recordsTotal", page.getTotalCount());
        result.put("recordsFiltered", page.getTotalCount());
        result.put("draw", draw);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("order", "product");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    /**
     * 获取结算账单汇总数据
     * @return
     */
    @Deprecated
    public Result orderSummaryList() {
        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        SysUnit companyUnit = loginUser.getSysUnit();
        orderBillSummary.setCompanyUnit(companyUnit);
        orderProperty = "billSummaryDate";
        orderType = "desc";
        List<OrderBillSummary> orderBillSummaryList = orderBillSummaryService.list(orderBillSummary, page, orderProperty, orderType);
        List<OrderBillSummary> orderBillSummaries = Lists.newArrayList();
        for (OrderBillSummary billSummary : orderBillSummaryList) {
            List<OrderDetail> orderBills = orderDetailService.getOrderBillDetailBySummaryId(billSummary, null);
            if (!orderBills.isEmpty()) {
                billSummary.setOrderNum(orderBills.size());
            } else {
                billSummary.setOrderNum(0);
            }
            orderBillSummaries.add(billSummary);
        }

        result.put("data", orderBillSummaries);
        result.put("recordsTotal", page.getTotalCount());
        result.put("recordsFiltered", page.getTotalCount());
        result.put("draw", draw);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("order", "product", "user",
                "operator");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    /**
     * 结算分页数据
     * @return
     */
    public Result summaryList() {
        Page page = new Page(this.page, this.rows);
//        if (StringUtils.hasText(billSummaryDateStr)) {
//            orderBillSummary.setBillSummaryDate(DateUtils.getDate(billSummaryDateStr, "yyyy-MM-dd"));
//        }
        if (StringUtils.hasText(billSummaryDateStr)) {
            orderBillSummary.setBillSummaryDate(DateUtils.getDate(billSummaryDateStr, "yyyy-MM-dd"));
        }
        List<OrderBillSummary> orderBillSummaryList = orderBillSummaryService.list(orderBillSummary, page, "billSummaryDate", "desc");
        if (orderBillSummary.getBillTarget() == OrderBillTarget.SYSTEM) {
            for (OrderBillSummary orderBillSummary : orderBillSummaryList) {
                if (orderBillSummary.getCompanyUnit() != null) {
                    orderBillSummary.setCompanyUnitName(orderBillSummary.getCompanyUnit().getName());
                }
            }
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(orderBillSummaryList, page.getTotalCount(), jsonConfig);
    }

    /**
     * 人工生成对账单，已生成过的账单如有问题请用账单列表的“重新生成”
     * 可以指定商户进行生成，日期必须是今天之前（包括今天）
     * @return
     */
    public Result genBillSummary() {
        Date today = DateUtils.getDate(DateUtils.format(new Date(), "yyyyMMdd"), "yyyyMMdd");
        Date billSummaryDate = DateUtils.getDate(billSummaryDateStr, "yyyy-MM-dd");
        if (today.compareTo(billSummaryDate) < 0) {    // T0不能生成今天的账单
            map.put("success", false);
            map.put("errorMsg", "不能生成今天之后的账单");
            return jsonResult(map);
        }

        Integer pageIndex = 1;
        Integer pageSize = 20;
        Page page = null;
        OrderBillSummary orderBillSummary = new OrderBillSummary();
        orderBillSummary.setBillSummaryDate(billSummaryDate);
//        orderBillSummary.setOrderDetailStatus(OrderDetailStatus.SUCCESS);
        orderBillSummary.setNotBillSummary(true);
        SysUnit unit = new SysUnit();
        if (companyUnitId != null) {
            unit.setId(companyUnitId);
        }
        unit.setStatus(0);
        unit.setUnitType(UnitType.company);
        unit.setTestFlag(false);
        while (true) {
            page = new Page(pageIndex, pageSize);
            List<SysUnit> companys = sysUnitService.listCompanys(unit, page);
            pageIndex++;
            // 查询合同信息
            List<Contract> contracts = new ArrayList<Contract>();
            for (SysUnit u : companys) {
                Contract contract = contractService.getContractByCompanyB(u.getId(), today);
                if (contract != null) {
                    contracts.add(contract);
                }
            }
            if (contracts.isEmpty()) {
                if (page.getHasNext()) {
                    continue;
                } else {
                    break;
                }
            }
            orderBillSummaryService.doCreateBillSummary(contracts, orderBillSummary, null);
            if (!page.getHasNext()) {
                break;
            }
        }
        map.put("success", true);
        if (page.getTotalCount() <= 0) {
            map.put("success", false);
            map.put("errorMsg", "没有可生成的账单");
        }
        return jsonResult(map);
    }

    /**
     * 未结算对账单重新生成
     * @return
     */
    public Result regenBillSummary() {
        String billSummaryId = (String) getParameter("billSummaryId");
        map = orderBillSummaryService.doRegenBillSummary(Long.valueOf(billSummaryId), getLoginUser());
        return jsonResult(map);
    }

    /**
     * 账单明细
     * @return
     */
    public Result billDetail() {
        String billSummaryId = (String) getParameter("billSummaryId");
        orderBillSummary = orderBillSummaryService.get(Long.valueOf(billSummaryId));
        return  dispatch();
    }

    /*
     * 订单明细查询
     */
    public Result orderDetailList() {
        Page page = new Page(this.page, this.rows);
        OrderBillSummary orderBillSummary = new OrderBillSummary();
        orderBillSummary.setId(Long.valueOf((String) getParameter("billSummaryId")));
        List<OrderDetail> orderDetails = orderDetailService.getOrderBillDetail(orderBillSummary, page);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(orderDetails, page.getTotalCount(), jsonConfig);
    }

    /*
     * 退款订单明细查询
     */
    public Result refundDetailList() {
        Page page = new Page(this.page, this.rows);
        OrderBillSummary orderBillSummary = new OrderBillSummary();
        orderBillSummary.setRefundBillSummaryId(Long.valueOf((String) getParameter("billSummaryId")));
        List<OrderDetail> orderDetails = orderDetailService.getOrderBillDetail(orderBillSummary, page);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(orderDetails, page.getTotalCount(), jsonConfig);
    }

    /**
     * 人工生成神州专车对账单，已生成过的账单如有问题请用账单列表的“重新生成”
     * 可以指定商户进行生成，日期必须是今天之前
     * @return
     */
    public Result genBillSummaryShenzhou() {
        Date today = DateUtils.getDate(DateUtils.format(new Date(), "yyyyMMdd"), "yyyyMMdd");
        Date billSummaryDate = DateUtils.getDate(billSummaryDateStr, "yyyy-MM-dd");
        if (today.compareTo(billSummaryDate) < 0) {
            map.put("success", false);
            map.put("errorMsg", "不能生成今天之后的账单");
            return jsonResult(map);
        }

        OrderBillSummary orderBillSummary = new OrderBillSummary();
        orderBillSummary.setBillSummaryDate(billSummaryDate);
//        orderBillSummary.setOrderDetailStatus(OrderDetailStatus.SUCCESS);
        orderBillSummary.setNotBillSummary(true);
        boolean b = orderBillSummaryService.doCreateBillSummaryShenzhou(orderBillSummary, null);

        map.put("success", true);
        if (!b) {
            map.put("success", false);
            map.put("errorMsg", "没有可生成的账单");
        }
        return jsonResult(map);
    }

    /**
     * 未结算对账单重新生成-神州专车
     * @return
     */
    public Result regenBillSummaryShenzhou() {
        String billSummaryId = (String) getParameter("billSummaryId");
        map = orderBillSummaryService.doRegenBillSummaryShenzhou(Long.valueOf(billSummaryId), getLoginUser());
        return jsonResult(map);
    }

    /**
     * 对账单进行结算-神州专车
     * @return
     */
    public Result cfmBillSummaryShenzhou() {
        String billSummaryId = (String) getParameter("billSummaryId");
        map = orderBillSummaryService.doCfmBillSummaryShenzhou(Long.valueOf(billSummaryId), getLoginUser());
        return jsonResult(map);
    }

    /**
     * 账单明细 - 神州专车
     * @return
     */
    public Result billDetailShenzhou() {
        String billSummaryId = (String) getParameter("billSummaryId");
        orderBillSummary = orderBillSummaryService.get(Long.valueOf(billSummaryId));
        return  dispatch();
    }

    /*
     * 订单明细查询-神州专车
     */
    public Result orderBillDetailShenzhou() {
        Page page = new Page(this.page, this.rows);
        OrderBillSummary orderBillSummary = new OrderBillSummary();
        orderBillSummary.setId(Long.valueOf((String) getParameter("billSummaryId")));
        List<ShenzhouOrder> orderDetails = shenzhouOrderService.getOrderBillDetail(orderBillSummary, page);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(orderDetails, page.getTotalCount(), jsonConfig);
    }

    /**
     * 人工生成轮渡船票对账单，已生成过的账单如有问题请用账单列表的“重新生成”
     * 可以指定商户进行生成，日期必须是今天之前
     * @return
     */
    public Result genBillSummaryFerry() {
        Date today = DateUtils.getDate(DateUtils.format(new Date(), "yyyyMMdd"), "yyyyMMdd");
        Date billSummaryDate = DateUtils.getDate(billSummaryDateStr, "yyyy-MM-dd");
        if (today.compareTo(billSummaryDate) < 0) {
            map.put("success", false);
            map.put("errorMsg", "不能生成今天之后的账单");
            return jsonResult(map);
        }

        OrderBillSummary orderBillSummary = new OrderBillSummary();
        orderBillSummary.setBillSummaryDate(billSummaryDate);
//        orderBillSummary.setOrderDetailStatus(OrderDetailStatus.SUCCESS);
        orderBillSummary.setNotBillSummary(true);
        boolean b = orderBillSummaryService.doCreateBillSummaryFerry(orderBillSummary, null);

        map.put("success", true);
        if (!b) {
            map.put("success", false);
            map.put("errorMsg", "没有可生成的账单");
        }
        return jsonResult(map);
    }

    /**
     * 未结算对账单重新生成-轮渡船票
     * @return
     */
    public Result regenBillSummaryFerry() {
        String billSummaryId = (String) getParameter("billSummaryId");
        map = orderBillSummaryService.doRegenBillSummaryFerry(Long.valueOf(billSummaryId), getLoginUser());
        return jsonResult(map);
    }

    /**
     * 对账单进行结算-轮渡船票
     * @return
     */
    public Result cfmBillSummaryFerry() {
        String billSummaryId = (String) getParameter("billSummaryId");
        OrderBillSummary orderBillSummary = orderBillSummaryService.get(Long.valueOf(billSummaryId));
        // 其他轮渡统计参数
        String saleCount = (String) getParameter("saleCount");
        if (StringUtils.isNotBlank(saleCount)) {
            orderBillSummary.setSaleCount(Integer.valueOf(saleCount));
        }
        String saleAmount = (String) getParameter("saleAmount");
        if (StringUtils.isNotBlank(saleAmount)) {
            orderBillSummary.setSaleAmount(Float.valueOf(saleAmount));
        }
        String returnCount = (String) getParameter("returnCount");
        if (StringUtils.isNotBlank(returnCount)) {
            orderBillSummary.setRefundCount(Integer.valueOf(returnCount));
        }
        String returnAmount = (String) getParameter("returnAmount");
        if (StringUtils.isNotBlank(returnAmount)) {
            orderBillSummary.setReturnAmount(Float.valueOf(returnAmount));
        }
        String poundageAmount = (String) getParameter("poundageAmount");
        if (StringUtils.isNotBlank(poundageAmount)) {
            orderBillSummary.setPoundageAmount(Float.valueOf(poundageAmount));
        }

        map = orderBillSummaryService.doCfmBillSummaryFerry(orderBillSummary, getLoginUser());
        return jsonResult(map);
    }

    /**
     * 账单明细 - 轮渡
     * @return
     */
    public Result billDetailFerry() {
        String billSummaryId = (String) getParameter("billSummaryId");
        orderBillSummary = orderBillSummaryService.get(Long.valueOf(billSummaryId));
        return  dispatch();
    }

    /*
     * 订单明细查询-轮渡船票
     */
    public Result orderBillDetailFerry() {
        Page page = new Page(this.page, this.rows);
        OrderBillSummary orderBillSummary = new OrderBillSummary();
        orderBillSummary.setId(Long.valueOf((String) getParameter("billSummaryId")));
        List<FerryOrder> orderDetails = ferryOrderService.getOrderBillDetail(orderBillSummary, page);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(orderDetails, page.getTotalCount(), jsonConfig);
    }

    /*
     * 退款订单明细查询-轮渡船票
     */
    public Result refundDetailFerry() {
        Page page = new Page(this.page, this.rows);
        OrderBillSummary orderBillSummary = new OrderBillSummary();
        orderBillSummary.setRefundBillSummaryId(Long.valueOf((String) getParameter("billSummaryId")));
        List<FerryOrder> orderDetails = ferryOrderService.getOrderBillDetail(orderBillSummary, page);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(orderDetails, page.getTotalCount(), jsonConfig);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public OrderBillSummary getOrderBillSummary() {
        return orderBillSummary;
    }

    public void setOrderBillSummary(OrderBillSummary orderBillSummary) {
        this.orderBillSummary = orderBillSummary;
    }

    public String getBillSummaryDateStr() {
        return billSummaryDateStr;
    }

    public void setBillSummaryDateStr(String billSummaryDateStr) {
        this.billSummaryDateStr = billSummaryDateStr;
    }

    public OrderBillSummaryService getOrderBillSummaryService() {
        return orderBillSummaryService;
    }

    public void setOrderBillSummaryService(OrderBillSummaryService orderBillSummaryService) {
        this.orderBillSummaryService = orderBillSummaryService;
    }

    public String getBillDateStr() {
        return billDateStr;
    }

    public void setBillDateStr(String billDateStr) {
        this.billDateStr = billDateStr;
    }

    public Long getCompanyUnitId() {
        return companyUnitId;
    }

    public void setCompanyUnitId(Long companyUnitId) {
        this.companyUnitId = companyUnitId;
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
}
