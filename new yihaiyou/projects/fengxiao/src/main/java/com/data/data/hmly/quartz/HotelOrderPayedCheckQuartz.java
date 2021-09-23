package com.data.data.hmly.quartz;

import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.common.entity.enums.ProductType;
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
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by huangpeijie on 2016-12-23,0023.
 */
@Component
public class HotelOrderPayedCheckQuartz {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private BalanceService balanceService;

    public void doCheckOrderPayed() {
        SysUser admin = sysUserService.findUserByAccount("admin");
        User user = orderLogService.getSysOrderLogUser();
        List<Order> orderList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        Order condition = new Order();
        List<OrderStatus> neededStatus = Lists.newArrayList(OrderStatus.PAYED);
        // 取出等待支付(WAIT)的订单
        condition.setNeededStatuses(neededStatus);
        final List<OrderType> includeOrderTypes = Lists.newArrayList(OrderType.hotel, OrderType.plan);
        condition.setIncludeOrderTypes(includeOrderTypes);
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
                Date waitTime = order.getWaitTime();
                Date nowTime = new Date();
                boolean isTimeOut = true;
                if (waitTime != null && waitTime.after(nowTime)) {
                    isTimeOut = false;
                }
                if (isTimeOut) {
                    // 商家超时未确认
                    OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                            + "订单超时,商家未确认, 订单取消! 现在状态:"
                            + order.getStatus().getDescription(), order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog2);
                    List<Long> orderDetailIds = Lists.transform(orderDetailList, new Function<OrderDetail, Long>() {
                        @Override
                        public Long apply(OrderDetail input) {
                            if (input.getProductType().equals(ProductType.hotel)) {
                                return input.getId();
                            } else {
                                return null;
                            }
                        }
                    });
                    orderDetailIds.removeAll(Collections.singleton(null));
                    for (OrderDetail detail : orderDetailList) {
                        try {
                            balanceService.doFailOrderRefund(order, detail, null, admin);
                        } catch (Exception e) {
                            e.printStackTrace();
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
