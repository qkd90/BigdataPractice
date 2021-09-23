package com.data.data.hmly.quartz.outOrder;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.enums.LineConfirmAndPayType;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderDispatchService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderMsgService;
import com.data.data.hmly.service.order.OrderRefundService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.outOrder.JszxOrderDetailService;
import com.data.data.hmly.service.outOrder.JszxOrderService;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderStatus;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/6/7.
 */
@Component
public class LineOrderStatusCheckQuartz {


    @Resource
    private OrderService orderService;
    @Resource
    private OrderDispatchService orderDispatchService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderRefundService orderRefundService;
    @Resource
    private OrderMsgService orderMsgService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private JszxOrderService jszxOrderService;
    @Resource
    private JszxOrderDetailService jszxOrderDetailService;
    @Resource
    private LinetypepriceService linetypepriceService;

    public void doCheckLineOrderStatus() {
        User user = orderLogService.getSysOrderLogUser();
        List<Order> orderList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        Order condition = new Order();
        List<OrderStatus> neededStatus = new ArrayList<OrderStatus>();
        // 取出处理中(PROCESSING)的订单(即供应商确认中的订单)
        neededStatus.add(OrderStatus.PROCESSING);
        condition.setNeededStatuses(neededStatus);
        // 限制订单类型为线路
        condition.setOrderType(OrderType.line);
//        // 要过滤的订单类型 (请在在config中配置 "FILTER_ORDER_TYPE" 字段)
//        List<OrderType> filterOrderTypes = orderService.getFilterOrderTypes();
//        if (!filterOrderTypes.isEmpty()) {
//            condition.setFilterOrderTypes(filterOrderTypes);
//        }
        while (true) {
            page = new Page(pageIndex, pageSize);
            orderList = orderService.list(condition, page, null, null, null);
            if (orderList.isEmpty()) {
                break;
            }
            // 总待处理订单数目
            total = page.getTotalCount();
            Iterator<Order> orderIterator = orderList.iterator();
            while (orderIterator.hasNext()) {
                Order order = orderIterator.next();
                List<OrderDetail> orderDetailList = orderDetailService.getByOrderId(order.getId());
                // 写入订单 日志
                OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                        + "检查供应商确认状态,现在状态:"
                        + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog1);
                if (orderDetailList.isEmpty()) {
                    OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                            + "没有找到订单详情, 无法确认, 不再检查该订单, 订单失败! 现在状态:"
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog2);
                    order.setStatus(OrderStatus.FAILED);
                    orderService.update(order);
                    continue;
                }
                Long jszxOrderId = order.getJszxOrderId();
                if (jszxOrderId == null) {
                    OrderLog orderLineLog = orderLogService.createOrderLog(user, "订单#"
                                    + order.getId() + "##没有供应商订单ID(jszxOrderId), 本次不处理!",
                            order.getId(), null);
                    orderLogService.loggingOrderLog(orderLineLog);
                    continue;
                }
                Long costId = orderDetailList.get(0).getCostId();
                if (costId == null || costId <= 0) {
                    OrderLog orderLineLog = orderLogService.createOrderLog(user, "订单#"
                                    + order.getId() + "##没有消费产品价格信息ID(costId), 本次不处理!",
                            order.getId(), null);
                    orderLogService.loggingOrderLog(orderLineLog);
                    continue;
                }
                // 查找线路和线路价格信息
                Linetypeprice linetypeprice = linetypepriceService.findFullById(costId);
                if (linetypeprice == null) {
                    OrderLog orderLineLog = orderLogService.createOrderLog(user, "订单#"
                                    + order.getId() + "##没有消费产品价格信息(Linetypeprice), 订单变更为无效! 不再处理!",
                            order.getId(), null);
                    orderLogService.loggingOrderLog(orderLineLog);
                    order.setStatus(OrderStatus.INVALID);
                    orderService.update(order);
                    continue;
                }
                Line line = linetypeprice.getLine();
                // 取出供应商订单
                JszxOrder jszxOrder = jszxOrderService.load(jszxOrderId);
                // 旅行帮订单未确认状态(PROCESSING)
                if (OrderStatus.PROCESSING.equals(order.getStatus())) {
                    // 订单被供应商取消
                    if (JszxOrderStatus.CANCELED.equals(jszxOrder.getStatus())) {
                        // 更新旅行帮订单状态
                        order.setStatus(OrderStatus.CANCELED);
                        orderService.update(order);
                        OrderLog orderLineLog = orderLogService.createOrderLog(user, "订单(G)#"
                                        + order.getId() + "##供应商订单取消了该线路订单!",
                                order.getId(), null);
                        orderLogService.loggingOrderLog(orderLineLog);
                        // @SMS 发送线路预订失败短信
                        orderMsgService.doSendLineBookingFailMsg(order, line);
                    } else if (JszxOrderStatus.PAYED.equals(jszxOrder.getStatus())) {
                        // 供应商确认该线路订单
                        // 如果是无需确认的订单, 说明是由于供应商在之前订单处理过程由于余额不足,扣款失败,
                        // 导致需要供应商再次付款处理, 而进入该流程, 此时, 客户无需再付款
                        if (LineConfirmAndPayType.noconfirm.equals(line.getConfirmAndPay())) {
                            // 更新旅行帮订单状态(供应商再次付款成功, 状态变更为 "成功")
                            order.setStatus(OrderStatus.SUCCESS);
                            // 更新旅行帮订单状态
                            orderService.update(order);
                            OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                                    + "供应商线路下单成功!(供应商再次付款成功!)(#" + jszxOrder.getId()
                                    + "),订单(Order)现在状态:" + order.getStatus().getDescription(), order.getId(), null);
                            orderLogService.loggingOrderLog(orderLog2);
                            // @SMS 发送线路预订成功短信
                            orderMsgService.doSendLineBookingSuccessMsg(order, line);
                        } else {
                            // 如果是需要确认的订单, 则供应商确认后, 需要客户付款
                            // 更新旅行帮订单状态为等待支付
                            order.setStatus(OrderStatus.WAIT);
                            // 保存旅行帮订单的确认时间, 支付截止时间
                            order.setConfirmTime(new Date());
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(order.getConfirmTime());
                            calendar.add(Calendar.HOUR, 2);
                            order.setWaitTime(calendar.getTime());
                            // 更新旅行帮订单状态
                            orderService.update(order);
                            OrderLog orderLineLog = orderLogService.createOrderLog(user, "订单(G)#"
                                            + order.getId() + "##供应商订单确认了该线路订单!等待客户支付!",
                                    order.getId(), null);
                            orderLogService.loggingOrderLog(orderLineLog);
                            // @SMS 发送线路预订成功等待支付短信
                            orderMsgService.doSendLineBookingSuccessMsg(order, line);
                        }
                    }
                } else if (OrderStatus.CANCELING.equals(order.getStatus())) {
                    // 旅行帮订单取消中状态, 待确认退款的的订单
                    // 供应商确认退款
                    if (JszxOrderStatus.CANCELED.equals(jszxOrder.getStatus())) {
                        // 向用户退款操作
                        Map<String, Object> result = orderRefundService.doStartRefund(order, null, order.getPrice());
                        if ((Boolean) result.get("isAbleToCancel")) {
                            // 更新旅行帮订单状态
                            order.setStatus(OrderStatus.CANCELED);
                            orderService.update(order);
                            OrderLog orderLineLog = orderLogService.createOrderLog(user, "订单#"
                                    + order.getId() + "##供应商订单确认取消该线路订单!", order.getId(), null);
                            orderLogService.loggingOrderLog(orderLineLog);
                            // @SMS 发送线路取消成功短信短信
                            orderMsgService.doSendLineCancelSuccessMsg(order, line);
                        } else {
                            OrderLog orderLineLog = orderLogService.createOrderLog(user, "订单#"
                                    + order.getId() + "##旅行帮退款失败!(供应商已经确认取消该线路订单!)"
                                    + "退款失败原因: " + result.get("msg").toString(), order.getId(), null);
                            orderLogService.loggingOrderLog(orderLineLog);
                        }
                    } else {
                        // 更新旅行帮订单状态
                        order.setStatus(OrderStatus.CANCELING);
                        orderService.update(order);
                        OrderLog orderLineLog = orderLogService.createOrderLog(user, "订单#"
                                        + order.getId() + "##正在等待供应商订单确认取消该线路订单!",
                                order.getId(), null);
                        orderLogService.loggingOrderLog(orderLineLog);
                    }
                }
            }
            // 本次已处理总订单详情数目
            processed += orderList.size();
            if (processed >= total) {
                break;
            }
            pageIndex += 1;
            orderList.clear();
        }
    }


}
