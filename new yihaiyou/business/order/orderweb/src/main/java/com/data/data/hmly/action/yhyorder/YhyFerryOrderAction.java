package com.data.data.hmly.action.yhyorder;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.order.OrderForm;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.OrderBillSummaryService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderAll;
import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/12/5.
 */
public class YhyFerryOrderAction extends FrameBaseAction {


    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private OrderBillSummaryService orderBillSummaryService;
    @Resource
    private OrderService orderService;

    private FerryOrder ferryOrder = new FerryOrder();
    private Integer			page				= 1;
    private Integer			rows				= 10;
    private OrderForm orderFormVo = new OrderForm();
    private Order order = new Order();



    public Result list() {
        Page pageInfo = new Page(page, rows);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Boolean isSuperAdmin = isSupperAdmin();
        Boolean isSiteAdmin = isSiteAdmin();
        orderFormVo.setOrderProperty("createTime");
        if (StringUtils.isNotBlank(orderFormVo.getStartTime())) {
            order.setStartTime(com.zuipin.util.DateUtils.getStartDay(
                    com.zuipin.util.DateUtils.toDate(orderFormVo.getStartTime()), 0));
        }
        if (StringUtils.isNotBlank(orderFormVo.getEndTime())) {
            order.setEndTime(com.zuipin.util.DateUtils.getEndDay(
                    com.zuipin.util.DateUtils.toDate(orderFormVo.getEndTime()), 0));
        }
        List<OrderAll> orders = orderService.listOrderAll(order, pageInfo, isSuperAdmin, isSiteAdmin, loginUser, orderFormVo.getOrderType());
//        List<FerryOrder> ferryOrders = ferryOrderService.list(ferryOrder, pageInfo, "createTime", "desc");
        return datagrid(orders, pageInfo.getTotalCount(), JsonFilter.getIncludeConfig("user"));
    }

    public Result ferryOrderManage() {

        return dispatch("/WEB-INF/jsp/yhyorder/ferryOrder/ferryOrderIndex.jsp");
    }

    public Result ferryOrderDetail() {
        if (ferryOrder.getId() != null) {
            ferryOrder = ferryOrderService.getOrder(ferryOrder.getId());
        }
        return dispatch("/WEB-INF/jsp/yhyorder/ferryOrder/ferryOrderDetail.jsp");
    }

    /**
     * 轮渡船票退款申请
     * @return
     */
    public Result defundOrder() {
        String ferryOrderId = (String) getParameter("ferryOrderId");
        Map<String, Object> map = ferryOrderService.doDefundOrder(Long.valueOf(ferryOrderId), getLoginUser());
        return jsonResult(map);
    }

    /**
     * 轮渡船票对账统计信息
     * @return
     */
    public Result getOrderCollect() {
        try {
            String billSummaryId = (String) getParameter("billSummaryId");
            OrderBillSummary orderBillSummary = orderBillSummaryService.get(Long.valueOf(billSummaryId));
            Date billDate = orderBillSummary.getBillDate();
            Date startDate = null;
            Date endDate = null;
            if (orderBillSummary.getBillType() == OrderBillType.D1 || orderBillSummary.getBillType() == OrderBillType.T1
                    || orderBillSummary.getBillType() == OrderBillType.DN || orderBillSummary.getBillType() == OrderBillType.TN) {
                startDate = DateUtils.add(billDate, Calendar.DAY_OF_MONTH, -orderBillSummary.getBillDays());
                endDate = startDate;
            } else if (orderBillSummary.getBillType() == OrderBillType.week) {
                startDate = DateUtils.add(billDate, Calendar.DAY_OF_MONTH, -7);
                endDate = DateUtils.add(billDate, Calendar.DAY_OF_MONTH, -1);
            } else if (orderBillSummary.getBillType() == OrderBillType.month) {
                startDate = DateUtils.add(billDate, Calendar.MONTH, -1);
                endDate = DateUtils.add(billDate, Calendar.DAY_OF_MONTH, -1);
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("success", false);
                map.put("errorMsg", "无效结算方式");
                return jsonResult(map);
            }

            Map<String, Object> map = FerryUtil.getOrderCollect(startDate, endDate);
            return jsonResult(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("errorMsg", "轮渡系统获取对账统计信息失败");
        return jsonResult(map);
    }



    public FerryOrder getFerryOrder() {
        return ferryOrder;
    }

    public void setFerryOrder(FerryOrder ferryOrder) {
        this.ferryOrder = ferryOrder;
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

    public OrderForm getOrderFormVo() {
        return orderFormVo;
    }

    public void setOrderFormVo(OrderForm orderFormVo) {
        this.orderFormVo = orderFormVo;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
