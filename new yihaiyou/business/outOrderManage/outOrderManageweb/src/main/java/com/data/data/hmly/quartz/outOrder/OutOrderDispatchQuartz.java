package com.data.data.hmly.quartz.outOrder;

import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderDispatchService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderMsgService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.outOrder.OutOrderDispatchService;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/5/31.
 */
@Component
public class OutOrderDispatchQuartz {

    @Resource
    private OutOrderDispatchService outOrderDispatchService;
    @Resource
    private OrderDispatchService orderDispatchService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderMsgService orderMsgService;

    public void doDispatchOutOrder() {
        List<Order> orders;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        Order condition = new Order();
        List<OrderStatus> neededStatuses = new ArrayList<OrderStatus>();
        // 取出已经支付和待确认(需要确认的线路专用)的订单
        neededStatuses.add(OrderStatus.PAYED);
        neededStatuses.add(OrderStatus.UNCONFIRMED);
        condition.setNeededStatuses(neededStatuses);
        // 要过滤的订单类型 (请在在config中配置 "FILTER_ORDER_TYPE" 字段)
        List<OrderType> filterOrderTypes = orderService.getFilterOrderTypes();
        // 增加过滤行程订单
        filterOrderTypes.add(OrderType.plan);
//        filterOrderTypes.add(OrderType.)
        // 向供应商(旅行帮)下单, source为空即可
        List<ProductSource> thirdOrderSources = new ArrayList<ProductSource>();
        condition.setThirdOrderSources(thirdOrderSources);
        User user = orderLogService.getSysOrderLogUser();
        if (!filterOrderTypes.isEmpty()) {
            condition.setFilterOrderTypes(filterOrderTypes);
        }
        while (true) {
            page = new Page(pageIndex, pageSize);
            orders = orderService.list(condition, page, null, null, null);
            if (orders.isEmpty()) {
                break;
            }
            // 总待处理订单数目
            total = page.getTotalCount();
            Iterator<Order> orderIterator = orders.iterator();
            while (orderIterator.hasNext()) {
                Order order = orderIterator.next();
                // 写入订单 日志
                OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                        + "准备处理,现在状态:" + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog1);
                // 重复订单判断
                if (order.getJszxOrderId() != null && order.getJszxOrderId() > 0) {
                    // 需要确认的线路订单, 供应商确认后,下单在前, 支付成功状态在后
                    if (OrderStatus.PAYED.equals(order.getStatus())) {
                        order.setStatus(OrderStatus.SUCCESS);
                        orderService.update(order);
                        // 写入订单 日志
                        OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                                + "供应商已经确认, 客户已经付款, 当前订单状态:"
                                + order.getStatus().getDescription(), order.getId(), null);
                        orderLogService.loggingOrderLog(orderLog2);
                        // @SMS 发送订单支付成功消息
                        orderMsgService.doSendOrderPaySuccess(order);
                        continue;
                    } else {
                        // 写入订单 日志
                        OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                                + "疑似重复订单(供应商订单号已经存在), 不再处理, 当前订单状态:"
                                + order.getStatus().getDescription(), order.getId(), null);
                        orderLogService.loggingOrderLog(orderLog2);
                        continue;
                    }
                }
                // 对于已经支付的订单
                if (OrderStatus.PAYED.equals(order.getStatus())) {
                    // @SMS 发送订单支付成功消息
                    orderMsgService.doSendOrderPaySuccess(order);
                }
                // 旅行帮订单状态变更为处理中(PROCESSING)...
                order.setStatus(OrderStatus.PROCESSING);
                orderService.update(order);
                // 写入订单 日志
                OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                        + "开始处理,现在状态:" + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog2);
                // 查出经过筛选的orderDetail
                List<OrderDetail> orderDetails = orderDetailService.getByOrderIdAndSource(order.getId(), thirdOrderSources);
                Integer successNum = 0;
                Integer orderDetailNum = orderDetails.size();
                if (OrderType.line.equals(order.getOrderType())) {
//                    OrderLog orderLogLine1 = orderLogService.createOrderLog(user, "订单#"
//                            + order.getId() + "准备处理", order.getId(), null);
//                    orderLogService.loggingOrderLog(orderLogLine1);
                    // 线路下单流程
                    if (order.getIsCombineLine()) {
                        // 线路组合订单预下单处理
                        this.preDispatchToLine(order, orderDetails);
                    } else if (!order.getIsCombineLine()) {
                        // 普通线路订单下单流程
                        outOrderDispatchService.doDispatchToLine(order, orderDetails);
                    }
                    // ......
                    OrderLog orderLogLine2 = orderLogService.createOrderLog(user, "订单(G)#"
                            + order.getId() + "处理完成, 现在状态: "
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLogLine2);
                } else if (OrderType.ticket.equals(order.getOrderType())
                        || OrderType.sailboat.equals(order.getOrderType())) {
                    for (OrderDetail orderDetail : orderDetails) {
                        // 向供应商的下单
                        handleOrderDetail(order, orderDetail);
                    }
                    // 更新整个订单状态
                    order.setStatus(OrderStatus.PROCESSED);
                    orderService.update(order);
                } else if (OrderType.hotel.equals(order.getOrderType())) {
                    // 酒店订单流程
                    outOrderDispatchService.doDispatchToHotel(order, orderDetails);
                    OrderLog orderLogLine2 = orderLogService.createOrderLog(user, "订单(G)#"
                            + order.getId() + "处理完成, 现在状态: "
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLogLine2);
                } else if (OrderType.ship.equals(order.getOrderType())) {
                    // 交通船票订单流程
                    outOrderDispatchService.doDispatchToShip(order, orderDetails);
                    OrderLog orderLogLine2 = orderLogService.createOrderLog(user, "订单(G)#"
                            + order.getId() + "处理完成, 现在状态: "
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLogLine2);
                } else if (OrderType.cruiseship.equals(order.getOrderType())) {
                    // 邮轮订单流程
                    outOrderDispatchService.doDispatchToCruiseShip(order, orderDetails);
                    OrderLog orderLogLine2 = orderLogService.createOrderLog(user, "订单(G)#"
                            + order.getId() + "处理完成, 现在状态: "
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLogLine2);
                }
            }
            // 本次已处理订单数目
            processed += orders.size();
            if (processed >= total) {
                break;
            }
            pageIndex += 1;
            orders.clear();
        }
    }

    /**
     * 处理供应商订单详情
     * @param order
     * @param orderDetail
     */
    public void handleOrderDetail(Order order, OrderDetail orderDetail) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> result;
        switch (orderDetail.getProductType()) {
            case scenic:
                // 门票
//                OrderLog orderLogScenic1 = orderLogService.createOrderLog(user, "供应商门票订单详情#"
//                        + orderDetail.getId() + "准备处理", order.getId(), orderDetail.getId());
//                orderLogService.loggingOrderLog(orderLogScenic1);
                orderDetail.setStatus(OrderDetailStatus.BOOKING);
                orderDetailService.update(orderDetail);
//                OrderLog orderLogScenic2 = orderLogService.createOrderLog(user, "供应商门票订单详情#"
//                        + orderDetail.getId() + "开始处理,现在状态:"
//                        + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
//                orderLogService.loggingOrderLog(orderLogScenic2);
                //
                result = outOrderDispatchService.doDispatchToTicket(order, orderDetail);
                //
                result.put("type", ProductType.scenic);
                orderDispatchService.updateOrderStatus(orderDetail, result);
                break;
            case hotel:
                // 酒店
                break;
            case line:
//                // 线路
//                OrderLog orderLogLine1 = orderLogService.createOrderLog(user, "供应商线路订单详情#"
//                        + orderDetail.getId() + "准备处理", order.getId(), orderDetail.getId());
//                orderLogService.loggingOrderLog(orderLogLine1);
//                orderDetail.setStatus(OrderDetailStatus.BOOKING);
//                orderDetailService.update(orderDetail);
//                OrderLog orderLogLine2 = orderLogService.createOrderLog(user, "供应商门票订单详情#"
//                        + orderDetail.getId() + "开始处理,现在状态:"
//                        + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
//                orderLogService.loggingOrderLog(orderLogLine2);
//                //
////                result = outOrderDispatchService.doDispatchToLine(order, orderDetail);
//                //
//                result.put("type", ProductType.line);
//                orderDispatchService.updateOrderStatus(orderDetail, result);
//                break;
            default:break;
        }
    }

    /**
     * 线路组合订单详情下单处理
     * @param order
     * @param orderDetails
     */
    public void preDispatchToLine(Order order, List<OrderDetail> orderDetails) {
        List<OrderDetail> ticketDetails = new ArrayList<OrderDetail>();
        List<OrderDetail> hotelDetails = new ArrayList<OrderDetail>();
        List<OrderDetail> lineDetails = new ArrayList<OrderDetail>();
        // 线路组合订单详情拆分
        for (OrderDetail orderDetail : orderDetails) {
            if (ProductType.scenic.equals(orderDetail.getProductType())) {
                ticketDetails.add(orderDetail);
            } else if (ProductType.hotel.equals(orderDetail.getProductType())) {
                hotelDetails.add(orderDetail);
            } else if (ProductType.line.equals(orderDetail.getProductType())) {
                lineDetails.add(orderDetail);
            }
        }
        // 门票订单详情下单
        for (OrderDetail orderDetail : ticketDetails) {
            // 向供应商的下单
            handleOrderDetail(order, orderDetail);
        }
        // 酒店订单详情下单
        for (OrderDetail orderDetail : hotelDetails) {
            List<OrderDetail> hotelOrderDetails = new ArrayList<>();
            hotelOrderDetails.add(orderDetail);
            outOrderDispatchService.doDispatchToHotel(order, hotelOrderDetails);
        }
        // 更新整体订单状态
        order.setStatus(OrderStatus.PROCESSED);
        orderService.update(order);
    }

}
