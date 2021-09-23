package com.data.data.hmly.action.yhyorder;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.order.OrderForm;
import com.data.data.hmly.action.order.vo.OrderVo;
import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.common.YhyMsgService;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderAll;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderTouristService;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.util.MsgTemplateKey;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/10/17.
 */
public class YhyOrderAction extends FrameBaseAction {

    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderTouristService orderTouristService;
    @Resource
    private UserService userService;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private YhyMsgService yhyMsgService;

    private OrderForm orderFormVo = new OrderForm();
    private OrderVo yhyOrderVo;
    private  Order order = new Order();
    private Integer page = 1;
    private Integer rows = 10;
    private Long orderId;
    private Long orderDetailId;
    private List<Long> orderDetailIds;
    private List<FerryOrder> ferryOrders;


    /**
     * 订单总览
     * @return
     */
    public Result pandectOrder() {
        return dispatch("/WEB-INF/jsp/yhyorder/yhyOrder/pandectOrder.jsp");
    }

    /**
     * 订单总览 - 统计数据
     * @return
     */
    public Result statisticsData() {
        Date startTime = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, -1);
        order.setCreateTime(startTime);
        order.setStartTime(startTime);
        // 帆船
        order.setOrderType(OrderType.sailboat);
        Long sailboatCount = orderService.getNewestOrderCount(order);
        List<OrderAll> sailboatOrders = orderService.listLastestOrderAll(order, 5);
        result.put("sailboatCount", sailboatCount);
        result.put("sailboatOrders", sailboatOrders);

        // 游艇
        order.setOrderType(OrderType.yacht);
        Long yachtCount = orderService.getNewestOrderCount(order);
        List<OrderAll> yachtOrders = orderService.listLastestOrderAll(order, 5);
        result.put("yachtCount", yachtCount);
        result.put("yachtOrders", yachtOrders);

        // 鹭岛游
        order.setOrderType(OrderType.huanguyou);
        Long huanguyouCount = orderService.getNewestOrderCount(order);
        List<OrderAll> huanguyouOrders = orderService.listLastestOrderAll(order, 5);
        result.put("huanguyouCount", huanguyouCount);
        result.put("huanguyouOrders", huanguyouOrders);

        // 酒店民宿
        order.setOrderType(OrderType.hotel);
        Long hotelCount = orderService.getNewestOrderCount(order);
        List<OrderAll> hotelOrders = orderService.listLastestOrderAll(order, 5);
        result.put("hotelCount", hotelCount);
        result.put("hotelOrders", hotelOrders);

        // 行程规划
        order.setOrderType(OrderType.plan);
        Long planCount = orderService.getNewestOrderCount(order);
        List<OrderAll> planOrders = orderService.listLastestOrderAll(order, 5);
        result.put("planCount", planCount);
        result.put("planOrders", planOrders);

        simpleResult(result, true, "");
        return jsonResult(result);
    }

    public Result newOrderCount() {
        order.setCreateTime(DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, -1));
        Long totalCount = orderService.getNewestOrderCount(order);
        result.put("totalCount", totalCount);
        simpleResult(result, true, "");
        return jsonResult(result);
    }

    /**
     * 待确认、待支付订单取消
     * @return
     */
    public Result doCancelOrder() {
        Map<String, Object> resutlMap = new HashMap<String, Object>();
        if (orderId == null || orderDetailId == null) {
            simpleResult(resutlMap, false, "请求数据出错！");
            return jsonResult(resutlMap);
        }
        Order order = orderService.get(orderId);
        OrderDetail optDetail = orderDetailService.get(Long.valueOf(orderDetailId));    // 本次操作的订单详情

        if (optDetail.getStatus() != OrderDetailStatus.WAITING && optDetail.getStatus() != OrderDetailStatus.UNCONFIRMED) {
            simpleResult(resutlMap, false, "该订单无法做取消操作，请尝试获取最新数据进行操作！");
            return jsonResult(resutlMap);
        }

        String remark = (String) getParameter("remark");
        orderService.doCancelOrder(order, optDetail, remark, getLoginUser());
        // 短信发送
        if (OrderType.yacht.equals(order.getOrderType()) || OrderType.sailboat.equals(order.getOrderType()) || OrderType.huanguyou.equals(order.getOrderType())) {
            Map<String, Object> msgData = new HashMap<String, Object>();
            msgData.put("orderNo", order.getOrderNo());
            // @WEB_SMS
            yhyMsgService.doSendSMS(msgData, order.getMobile(), MsgTemplateKey.MERCHANT_TICKET_CANCEL_TLE);
        }
        simpleResult(resutlMap, true, "");
        return jsonResult(resutlMap);
    }

    /**
     * 支付前确认订单
     * @return
     */
    public Result doConfirmOrder() {
        Map<String, Object> resutlMap = new HashMap<String, Object>();
        if (orderId == null || orderDetailId == null) {
            simpleResult(resutlMap, false, "请求数据出错！");
            return jsonResult(resutlMap);
        }
        Order order = orderService.get(orderId);
        OrderDetail optDetail = orderDetailService.get(Long.valueOf(orderDetailId));    // 本次操作的订单详情

        if (optDetail.getStatus() != OrderDetailStatus.UNCONFIRMED) {
            simpleResult(resutlMap, false, "该订单无法做确认操作，请尝试获取最新数据进行操作！");
            return jsonResult(resutlMap);
        }

        orderService.doConfirmOrder(order, optDetail, getLoginUser());
        simpleResult(resutlMap, true, "");
        return jsonResult(resutlMap);
    }

    /**
     * 支付后确认订单
     * @return
     */
    public Result doPayedConfirm() {
        Map<String, Object> resutlMap = new HashMap<String, Object>();
        if (orderDetailIds != null && !orderDetailIds.isEmpty() && orderId != null) {
            resutlMap = orderService.doPayedConfirm(orderId, orderDetailIds, getLoginUser());
        }
        simpleResult(resutlMap, true, "");
        return jsonResult(resutlMap);
    }

    public Result getConfirmOrderList() {
        List<OrderVo> orderVos = new ArrayList<OrderVo>();
        if (orderId != null) {
            order = orderService.get(orderId);
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail detail: orderDetails) {
                yhyOrderVo = new OrderVo();
                if (detail.getStatus() == OrderDetailStatus.UNCONFIRMED) {
                    yhyOrderVo.setId(order.getId());
                    yhyOrderVo.setOrderDetailId(detail.getId());
                    yhyOrderVo.setOrderName(order.getName() + detail.getSeatType());
                    yhyOrderVo.setOrderType(detail.getProductType().name());
                    yhyOrderVo.setOrderNo(order.getOrderNo());
                    yhyOrderVo.setDetailStatus(detail.getStatus());
                    orderVos.add(yhyOrderVo);
                }
            }
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("");
        return datagrid(orderVos, orderVos.size(), jsonConfig);
    }

    public Result getOrderList() {
        Page page = new Page(this.page, rows);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Boolean isSuperAdmin = isSupperAdmin();
        Boolean isSiteAdmin = isSiteAdmin();
        orderFormVo.setOrderProperty("createTime");
        if (StringUtils.isNotBlank(orderFormVo.getStartTime())) {
            order.setStartTime(DateUtils.getStartDay(DateUtils.toDate(orderFormVo.getStartTime()), 0));
        }
        if (StringUtils.isNotBlank(orderFormVo.getEndTime())) {
            order.setEndTime(DateUtils.getEndDay(DateUtils.toDate(orderFormVo.getEndTime()), 0));
        }
        List<OrderAll> orders = orderService.listOrderAll(order, page, isSuperAdmin, isSiteAdmin, loginUser, orderFormVo.getOrderType());
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("orderDetails", "product", "rootProduct", "user", "operator");
        return datagrid(orders, page.getTotalCount(), jsonConfig);
    }

    public Result getNewOrderList() {
        Page page = new Page(this.page, rows);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Boolean isSuperAdmin = isSupperAdmin();
        Boolean isSiteAdmin = isSiteAdmin();
        orderFormVo.setOrderProperty("createTime");
        order.setStartTime(DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, -1));
        List<OrderAll> orders = orderService.listOrderAll(order, page, isSuperAdmin, isSiteAdmin, loginUser, orderFormVo.getOrderType());
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(orders, page.getTotalCount(), jsonConfig);
    }

    /**
     * 订单详情列表，如果是行程规划，还需查询是否有轮渡船票
     * @return
     */
    public Result orderDetail() {
        if (orderId != null) {
            order = orderService.get(orderId);
            if (order.getOrderType() == OrderType.plan) {   // 行程规划查询是否有船票信息
                ferryOrders = ferryOrderService.getByOrderId(orderId);
            }
        }
        if (order.getOrderType() == OrderType.hotel) {
            return dispatch("/WEB-INF/jsp/yhyorder/yhyOrder/hotelOrderDetail.jsp");
        }
        if (order.getOrderType() == OrderType.plan) {
            return dispatch("/WEB-INF/jsp/yhyorder/yhyOrder/planOrderDetail.jsp");
        }
        if (order.getOrderType() == OrderType.sailboat
                || order.getOrderType() == OrderType.yacht
                || order.getOrderType() == OrderType.huanguyou) {
            return dispatch("/WEB-INF/jsp/yhyorder/yhyOrder/sailboatOrderDetail.jsp");
        }
        return dispatch();
    }

    public void getOrderDetailByAuthorize(Order order, boolean isSuperAdmin, boolean isSiteAdmin, SysUser sysUser) {
        if (!isSuperAdmin) {
            List<OrderDetail> detailList = order.getOrderDetails();
            List<OrderDetail> newDetailList = Lists.newArrayList();
            for (OrderDetail detail : detailList) {
                if (!isSiteAdmin) {
                    if (sysUser.getSysUnit().getId() == detail.getProduct().getCompanyUnit().getId()) {
                        newDetailList.add(detail);
                    }
                } else {
                    if (sysUser.getSysSite().getId() == detail.getProduct().getCompanyUnit().getSysSite().getId()) {
                        newDetailList.add(detail);
                    }
                }
            }
            order.setOrderDetails(newDetailList);
        }
    }

//    /yhyorder/yhyOrder/sailboatIndex.jhtml
    public Result sailboatIndex() {
        return dispatch();
    }

//    /yhyorder/yhyOrder/hotelIndex.jhtml
    public Result hotelIndex() {
        return dispatch();
    }

    //    /yhyorder/yhyOrder/cruiseshipIndex.jhtml
    public Result cruiseshipIndex() {
        return dispatch();
    }

//    /yhyorder/yhyOrder/ticketIndex.jhtml
    public Result ticketIndex() {
        return dispatch();
    }

    //    /yhyorder/yhyOrder/planIndex.jhtml
    public Result planIndex() {
        return dispatch();
    }


    public OrderForm getOrderFormVo() {
        return orderFormVo;
    }

    public void setOrderFormVo(OrderForm orderFormVo) {
        this.orderFormVo = orderFormVo;
    }

    public OrderVo getYhyOrderVo() {
        return yhyOrderVo;
    }

    public void setYhyOrderVo(OrderVo yhyOrderVo) {
        this.yhyOrderVo = yhyOrderVo;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Long> getOrderDetailIds() {
        return orderDetailIds;
    }

    public void setOrderDetailIds(List<Long> orderDetailIds) {
        this.orderDetailIds = orderDetailIds;
    }

    public List<FerryOrder> getFerryOrders() {
        return ferryOrders;
    }

    public void setFerryOrders(List<FerryOrder> ferryOrders) {
        this.ferryOrders = ferryOrders;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
}
