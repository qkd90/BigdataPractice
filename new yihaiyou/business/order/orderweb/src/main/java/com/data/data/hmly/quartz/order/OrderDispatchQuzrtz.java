package com.data.data.hmly.quartz.order;

import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.common.entity.Product;
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
import com.framework.hibernate.util.Page;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zzl on 2016/3/10.
 */
@Component
public class OrderDispatchQuzrtz {

    @Resource
    private OrderDispatchService orderDispatchService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private UserService userService;
    @Resource
    private OrderMsgService orderMsgService;


    public void doDispatchOrder() {
        List<Order> orders;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        Order condition = new Order();
        // 取出已经支付的订单
        condition.setStatus(OrderStatus.PAYED);
        // 要过滤的订单类型 (请在在config中配置 "FILTER_ORDER_TYPE" 字段)
        List<OrderType> filterOrderTypes = orderService.getFilterOrderTypes();
        // 需要向第三方接口下单的产品来源 (请在在config中配置 "THIRD_ORDER_SOURCE" 字段)
        List<ProductSource> thirdOrderSources = orderService.getThirdOrderSource();
        User user = orderLogService.getSysOrderLogUser();
        if (!filterOrderTypes.isEmpty()) {
            condition.setFilterOrderTypes(filterOrderTypes);
        }
        if (!thirdOrderSources.isEmpty()) {
            condition.setThirdOrderSources(thirdOrderSources);
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
                OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单#" + order.getId() + "准备处理,现在状态:" + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog1);
                // @SMS 发送订单支付成功消息
                orderMsgService.doSendOrderPaySuccess(order);
                // 更新订单状态为处理中...
                order.setStatus(OrderStatus.PROCESSING);
                orderService.update(order);
                // 写入订单 日志
                OrderLog orderLog2 = orderLogService.createOrderLog(user, "订单#" + order.getId() + "开始处理,现在状态:" + order.getStatus().getDescription(), order.getId(), null);
                orderLogService.loggingOrderLog(orderLog2);
//                List<OrderDetail> orderDetails = orderDetailService.getByOrderId(order.getId());
                // 取出经过筛选的orderDetail
                List<OrderDetail> orderDetails = orderDetailService.getByOrderIdAndSource(order.getId(), thirdOrderSources);
                Integer successNum = 0;
                Integer orderDetailNum = orderDetails.size();
                // 在这里处理分发各种订单......
                for (OrderDetail orderDetail : orderDetails) {
                    // 在这里处理各种类型的订单详情......
                    handleOrderDetail(order, orderDetail);
                    //检查订单详情状态
                    if (orderDetail.getStatus() == OrderDetailStatus.SUCCESS) {
                        successNum += 1;
                    }
                }
//                // 检查整个订单状态
//                if (successNum == orderDetailNum) {
//                    order.setStatus(OrderStatus.SUCCESS);
//                } else if (successNum > 0) { // 部分失败
//                    order.setStatus(OrderStatus.PARTIAL_FAILED);
//                } else { // 全部失败
//                    order.setStatus(OrderStatus.FAILED);
//                }
                // 更新整个订单状态(改为系统确认此订单, 代表已经扫描过)
                order.setStatus(OrderStatus.PROCESSED);
                orderService.update(order);
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
     * 处理订单详情
     *
     * @param orderDetail
     */
    private void handleOrderDetail(Order order, OrderDetail orderDetail) {
        User user = orderLogService.getSysOrderLogUser();
        // 在这里处理各种类型的订单详情......
        Map<String, Object> result;
        switch (orderDetail.getProductType()) {
            case scenic:
                // 写入订单 日志
                OrderLog orderLogScenic1 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "准备处理", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderLogScenic1);
                orderDetail.setStatus(OrderDetailStatus.BOOKING);
                orderDetailService.update(orderDetail);
                OrderLog orderLogScenic2 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId() + "开始处理,现在状态:" + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderLogScenic2);
                result = orderDispatchService.doDispatchToTicket(order, orderDetail);
                result.put("type", ProductType.scenic);
                orderDispatchService.updateOrderStatus(orderDetail, result);
                break;
            case restaurant:
                break;
            case hotel:
                // 写入订单 日志
                OrderLog orderHotelLog1 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "准备处理", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderHotelLog1);
                orderDetail.setStatus(OrderDetailStatus.BOOKING);
                orderDetailService.update(orderDetail);
                OrderLog orderLogHotel2 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId() + "开始处理,现在状态:" + orderDetail.getStatus().getDescription(), order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderLogHotel2);
                result = orderDispatchService.doDispatchToHotel(order, orderDetail);
                result.put("type", ProductType.hotel);
                orderDispatchService.updateOrderStatus(orderDetail, result);
                break;
            case line:
                break;
            case train:
                // 先保存接口订单ID
                String trainOrderId = orderDetail.getId() + System.currentTimeMillis() + "";
                // 写入订单 日志
                OrderLog orderTrainLog1 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "准备处理"
                        + "(#" + trainOrderId + ")", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTrainLog1);
                orderDetail.setRealOrderId(trainOrderId);
                orderDetail.setStatus(OrderDetailStatus.BOOKING);
                orderDetailService.update(orderDetail);
                OrderLog orderTrainLog2 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "开始处理,现在状态:"
                        + orderDetail.getStatus().getDescription()
                        + "(#" + trainOrderId + ")", order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTrainLog2);
                result = orderDispatchService.doDispatchToTrain(order, orderDetail);
                result.put("type", ProductType.train);
                orderDispatchService.updateOrderStatus(orderDetail, result);
                break;
            case flight:
                // 先保存接口订单ID
                String flightOrderId = orderDetail.getId() + System.currentTimeMillis() + "";
                orderDetail.setRealOrderId(flightOrderId);
                orderDetail.setStatus(OrderDetailStatus.BOOKING);
                orderDetailService.update(orderDetail);
                result = orderDispatchService.doDispatchToFlight(order, orderDetail);
                result.put("type", ProductType.flight);
                orderDispatchService.updateOrderStatus(orderDetail, result);
                break;
            case delicacy:
                break;
            case recplan:
                break;
            case plan:
                break;
            default:
                break;
        }
    }
}
