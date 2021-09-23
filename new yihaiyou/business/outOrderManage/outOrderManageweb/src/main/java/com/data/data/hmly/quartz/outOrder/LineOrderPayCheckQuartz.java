package com.data.data.hmly.quartz.outOrder;

import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.outOrder.JszxOrderService;
import com.data.data.hmly.service.outOrder.OutOrderDispatchService;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderStatus;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zzl on 2016/7/11.
 */
@Component
public class LineOrderPayCheckQuartz {

    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OutOrderDispatchService outOrderDispatchService;
    @Resource
    private JszxOrderService jszxOrderService;
    @Resource
    private BalanceService balanceService;



    public void doCheckLineOrderPay() {
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
        // 限制订单类型为线路
        condition.setOrderType(OrderType.line);
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
                    OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                            + "没有找到供应商订单ID, 无法确认! 现在状态:"
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog2);
                    continue;
                }
                // 取出供应商订单
                JszxOrder jszxOrder = jszxOrderService.load(jszxOrderId);
                SysUser jszxOrderUser = jszxOrder.getUser();
                Date confirmTime = order.getConfirmTime();
                Date waitTime = order.getWaitTime();
                Date nowTime = new Date();
                boolean isTimeOut;
                if (waitTime != null) {
                    isTimeOut = outOrderDispatchService.payCheckByTime(waitTime, nowTime);
                } else if (confirmTime != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(confirmTime);
                    calendar.add(Calendar.HOUR, 2);
                    waitTime = calendar.getTime();
                    isTimeOut = outOrderDispatchService.payCheckByTime(waitTime, nowTime);

                } else {
                    OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                            + "订单没有找到确认时间或支付超时时间!无法处理! 现在状态:"
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog2);
                    continue;
                }
                if (isTimeOut) {
                    // 客户超时未支付
                    OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                            + "订单超时,客户未支付, 订单取消! 现在状态:"
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog2);
                    // 更新旅行帮订单状态
                    order.setStatus(OrderStatus.CANCELED);
                    orderService.update(order);
                    // 供应商余额退回
                    Double payPrice = jszxOrder.getActualPayPrice().doubleValue();
                    balanceService.updateBalance(payPrice, AccountType.refund, jszxOrderUser.getId(), jszxOrder.getSupplierId(), user.getId(), jszxOrder.getOrderNo(), jszxOrder.getId());
                    // 更新供应商订单状态
                    jszxOrder.setStatus(JszxOrderStatus.CANCELED);
                    jszxOrderService.update(jszxOrder, jszxOrderUser, jszxOrder.getCompanyUnit());
                    OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                            + "供应商订单(G)#" + jszxOrder.getId() + "已经退回相应的余额账户!"
                            + "旅行帮订单现在状态: " + order.getStatus().getDescription()
                            + "供应商订单现在状态: " + jszxOrder.getStatus().getDescription()
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog3);
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
