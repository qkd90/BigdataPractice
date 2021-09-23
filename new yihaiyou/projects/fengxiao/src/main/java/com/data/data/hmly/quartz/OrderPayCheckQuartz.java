package com.data.data.hmly.quartz;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.pay.UlineWeixinPayService;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-11-30,0030.
 */
@Component
public class OrderPayCheckQuartz {
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private FerryOrderService ferryOrderService;

    public void doCheckOrderPay() {
        orderCheck();
        ferryOrderCheck();
    }

    private void orderCheck() {
        User user = orderLogService.getSysOrderLogUser();
        List<Order> orderList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        Order condition = new Order();
        List<OrderStatus> neededStatus = new ArrayList<OrderStatus>();
        // 取出等待支付(WAIT)的订单
        neededStatus.add(OrderStatus.WAIT);
        condition.setNeededStatuses(neededStatus);
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
                        + "检查客户支付状态,现在状态:"
                        + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog1);
//                if (orderDetailList.isEmpty()) {
//                    OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
//                            + "没有找到订单详情, 无法确认, 不再检查该订单, 订单失败! 现在状态:"
//                            + order.getStatus().getDescription(), order.getId(), null);
//                    orderLogService.loggingOrderLog(orderLog2);
//                    order.setStatus(OrderStatus.FAILED);
//                    orderService.update(order);
//                    continue;
//                }
                Date waitTime = order.getWaitTime();
                Date nowTime = new Date();
                boolean isTimeOut = true;
                if (waitTime != null && waitTime.after(nowTime)) {
                    isTimeOut = false;
                }
                if (isTimeOut) {
                    // 客户超时未支付
                    OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                            + "订单超时,客户未支付, 订单取消! 现在状态:"
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog2);
                    // 更新旅行帮订单状态
                    for (OrderDetail orderDetail : orderDetailList) {
                        orderDetail.setStatus(OrderDetailStatus.CANCELED);
                    }
                    orderDetailService.update(orderDetailList);
                    order.setMsg("订单超时,客户未支付");
                    order.setModifyTime(new Date());
                    order.setStatus(OrderStatus.CANCELED);
                    orderService.update(order);
                    if (StringUtils.isNotBlank(order.getWechatCode())) {
                        UlineWeixinPayService ulineWeixinPayService = new UlineWeixinPayService();
                        Map<String, Object> closeResult = ulineWeixinPayService.closeOrder(order.getOrderNo());
                        if (!"ok".equals(closeResult.get("status"))) {
                            OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                                    + "优畅微信订单关闭失败, 失败原因:"
                                    + closeResult.get("err_code_des"), order.getId(), null);
                            orderLogService.loggingOrderLog(orderLog3);
                        }
                    }
                    List<FerryOrder> ferryOrders = ferryOrderService.getByOrderId(order.getId());
                    if (!ferryOrders.isEmpty()) {
                        FerryOrder ferryOrder = ferryOrders.get(0);
                        ferryOrder.setStatus(OrderStatus.CANCELED);
                        ferryOrderService.updateOrder(ferryOrder);
                        FerryUtil.payNotify(ferryOrder.getFerryNumber(), false);
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

    private void ferryOrderCheck() {
        User user = orderLogService.getSysOrderLogUser();
        List<FerryOrder> orderList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        FerryOrder condition = new FerryOrder();
        condition.setStatus(OrderStatus.WAIT);
        while (true) {
            page = new Page(pageIndex, pageSize);
            orderList = ferryOrderService.list(condition, null);
            if (orderList.isEmpty()) {
                break;
            }
            // 总待处理订单数目
            total = page.getTotalCount();
            Iterator<FerryOrder> orderIterator = orderList.iterator();
            while (orderIterator.hasNext()) {
                FerryOrder order = orderIterator.next();
                // 写入订单 日志
                OrderLog orderLog1 = orderLogService.createOrderLog(user, "轮渡订单(G)#" + order.getId()
                        + "检查客户支付状态,现在状态:"
                        + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog1);
                Date waitTime = order.getWaitTime();
                Date nowTime = new Date();
                boolean isTimeOut = true;
                if (waitTime != null && waitTime.after(nowTime)) {
                    isTimeOut = false;
                }
                if (isTimeOut) {
                    // 客户超时未支付
                    OrderLog orderLog2 = orderLogService.createOrderLog(user, "轮渡订单(G)#" + order.getId()
                            + "订单超时,客户未支付, 订单取消! 现在状态:"
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog2);
                    // 更新旅行帮订单状态
                    order.setModifyTime(new Date());
                    order.setStatus(OrderStatus.CANCELED);
                    FerryUtil.payNotify(order.getFerryNumber(), false);
                    ferryOrderService.updateOrder(order);
                    if (StringUtils.isNotBlank(order.getWechatCode())) {
                        UlineWeixinPayService ulineWeixinPayService = new UlineWeixinPayService();
                        Map<String, Object> closeResult = ulineWeixinPayService.closeOrder(order.getOrderNumber());
                        if (!"ok".equals(closeResult.get("status"))) {
                            OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                                    + "优畅微信订单关闭失败, 失败原因:"
                                    + closeResult.get("err_code_des"), order.getId(), null);
                            orderLogService.loggingOrderLog(orderLog3);
                        }
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
