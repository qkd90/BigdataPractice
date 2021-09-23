package com.data.data.hmly.quartz.order;

import com.data.data.hmly.service.SendingMsgService;
import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SendStatus;
import com.data.data.hmly.service.entity.SendingMsg;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderDispatchService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderMsgService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.OrderTouristService;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.TrafficService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.hmly.service.translation.flight.juhe.JuheFlightService;
import com.data.hmly.service.translation.flight.juhe.entity.PaymentResult;
import com.data.hmly.service.translation.flight.juhe.entity.TicketResult;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/3/28.
 */
@Component
public class FlightTicketPayQuzrtz {

    @Resource
    private PropertiesManager propertiesManager;

    @Resource
    private OrderService orderService;
    @Resource
    private OrderDispatchService orderDispatchService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderTouristService orderTouristService;
    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private TrafficService trafficService;
    @Resource
    private UserService userService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private SendingMsgService sendingMsgService;
    @Resource
    private OrderMsgService orderMsgService;

    public void doPayFlightTicket() {
        User user = userService.get(9L);
        String flightKey = propertiesManager.getString("JUHE_FLIGHT_KEY");
        List<OrderDetail> orderDetailList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        OrderDetail condition = new OrderDetail();
        Map<String, Object> result = new HashMap<String, Object>();
        // 取出预定中或已支付的飞机票订单
        List<OrderDetailStatus> neededStatus = new ArrayList<OrderDetailStatus>();
        neededStatus.add(OrderDetailStatus.BOOKING);
        neededStatus.add(OrderDetailStatus.PAYED);
        condition.setNeededStatuses(neededStatus);
        condition.setProductType(ProductType.flight);
        while (true) {
            page = new Page(pageIndex, pageSize);
            orderDetailList = orderDetailService.list(condition);
            if (orderDetailList.isEmpty()) {
                break;
            }
            // 总待处理订单数目
            total = page.getTotalCount();
            Iterator<OrderDetail> iterator = orderDetailList.iterator();
            while (iterator.hasNext()) {
                result.clear();
                OrderDetail orderDetail = iterator.next();
                OrderLog orderFlightLog1 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "检查是否可付款,现在状态:"
                        + orderDetail.getStatus().getDescription()
                        + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderFlightLog1);
                result.put("orderId", orderDetail.getRealOrderId());
                result.put("type", ProductType.flight);
                if (OrderDetailStatus.BOOKING.equals(orderDetail.getStatus())
                        || OrderDetailStatus.PAYED.equals(orderDetail.getStatus())) {
                    String realOrderId = orderDetail.getRealOrderId();
                    TicketResult ticketResult = JuheFlightService.getTicket(flightKey, realOrderId);
                    if (ticketResult.getResult() != null) {
                        String msg = "";
                        if ("SUCC_CREATE".equals(ticketResult.getResult().getBillStatus())
                                && !OrderDetailStatus.PAYED.equals(orderDetail.getStatus())) {
                            msg = "预定成功";
                            OrderLog orderFlightLog2 = orderLogService.createOrderLog(user, "订单详情#"
                                    + orderDetail.getId() + "已获取接口订单状态:" + msg
                                    + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                    + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(orderFlightLog2);
                            //接口支付
                            PaymentResult payResult = JuheFlightService.pay(flightKey, realOrderId);
                            // 再次获取订单状态
                            ticketResult = JuheFlightService.getTicket(flightKey, realOrderId);
                            // 更改初始订单状态, 更新实际订单id
                            if ("200".equals(payResult.getError_code())) {
                                OrderLog orderFlightLog3 = orderLogService.createOrderLog(user, "订单详情#"
                                        + orderDetail.getId() + "接口支付成功!已获取接口订单状态:" + msg
                                        + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                        + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                                orderLogService.loggingOrderLog(orderFlightLog3);
                                msg = "接口支付成功!";
                                result.put("status", OrderDetailStatus.PAYED);
                                // 发送收到订单短信
                                prepareSendMsg(orderDetail, ticketResult);
                            } else {
                                msg = payResult.getReason();
                                OrderLog orderFlightLog3 = orderLogService.createOrderLog(user, "订单详情#"
                                        + orderDetail.getId() + "接口支付失败!已获取接口订单状态:" + msg
                                        + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                        + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                                orderLogService.loggingOrderLog(orderFlightLog3);
                                result.put("status", OrderDetailStatus.FAILED);
                            }
                        } else if ("FAIL_CREATE".equals(ticketResult.getResult().getBillStatus())) {
                            msg = "预定失败!";
                            result.put("status", OrderDetailStatus.FAILED);
                        } else if ("SUCC_CANCEL".equals(ticketResult.getResult().getBillStatus())) {
                            msg = "取消成功";
                            result.put("status", OrderDetailStatus.CANCELED);
                        } else if ("FAIL_CANCEL".equals(ticketResult.getResult().getBillStatus())) {
                            msg = "取消订单失败";
                            result.put("status", orderDetail.getStatus());
                        } else if ("ISSUE_ING".equals(ticketResult.getResult().getBillStatus())) {
                            msg = "正在出票";
                            result.put("status", orderDetail.getStatus());
                        } else if ("SUCC_ISSUE".equals(ticketResult.getResult().getBillStatus())) {
                            // 当整个订单状态为出票成功的时候, 检查每张票的出票状态
                            TicketResult.ResultEntity resultEntity = ticketResult.getResult();
                            List<TicketResult.ResultEntity.TicketListEntity> ticketList = resultEntity.getTicketList();
                            Integer totalNum = ticketList.size();
                            Integer successNum = 0;
                            for (TicketResult.ResultEntity.TicketListEntity ticketListEntity : ticketList) {
                                String ticketStatus = ticketListEntity.getTicketStatus();
                                if ("FAIL_ISSUE".equals(ticketStatus)) {
                                    OrderLog orderFlightLog3 = orderLogService.createOrderLog(user, "订单详情#"
                                            + orderDetail.getId() + "乘客:" + ticketListEntity.getPassengerName() + "出票失败!"
                                            + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                            + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                                    orderLogService.loggingOrderLog(orderFlightLog3);
                                } else if ("SUCC_ISSUE".equals(ticketStatus)) {
                                    successNum++;
                                }
                            }
                            // 有乘客出票失败的时候
                            if (successNum < totalNum) {
                                msg = "有乘客出票失败!";
                                result.put("status", OrderDetailStatus.PARTIAL_FAILED);
                                result.put("apiResult", msg);
                                // 更新订单状态(非预定中或已支付的订单, 不在这里更新状态)
                                orderDispatchService.updateOrderStatus(orderDetail, result);
                                // 发送出票成功短信(仅发送成功出票的用户)
                                prepareSendMsg(orderDetail, ticketResult);
                                return;
                            }
                            // 全部成功出票
                            msg = "出票成功";
                            result.put("status", OrderDetailStatus.SUCCESS);
                        } else if ("FAIL_ISSUE".equals(ticketResult.getResult().getBillStatus())) {
                            msg = "出票失败";
                            // 发送出票失败短信
                            prepareSendMsg(orderDetail, ticketResult);
                            result.put("status", OrderDetailStatus.FAILED);
                        } else if ("REFUND_ING".equals(ticketResult.getResult().getBillStatus())) {
                            msg = "正在退款";
                            result.put("status", orderDetail.getStatus());
                        } else if ("SUCC_REFUND".equals(ticketResult.getResult().getBillStatus())) {
                            msg = "退票成功";
                            result.put("status", OrderDetailStatus.CANCELED);
                        } else if ("FAIL_REFUND".equals(ticketResult.getResult().getBillStatus())) {
                            msg = "退票失败";
                            result.put("status", orderDetail.getStatus());
                        } else if ("CHANGE_ING".equals(ticketResult.getResult().getBillStatus())) {
                            msg = "正在改期";
                            result.put("status", orderDetail.getStatus());
                        } else if ("SUCC_CHANGE".equals(ticketResult.getResult().getBillStatus())) {
                            msg = "改期成功";
                            result.put("status", orderDetail.getStatus());
                        } else if ("FAIL_CHANGE".equals(ticketResult.getResult().getBillStatus())) {
                            msg = "改期失败";
                            result.put("status", orderDetail.getStatus());
                        }
                        result.put("apiResult", msg);
                        OrderLog orderFlightLog3 = orderLogService.createOrderLog(user, "订单详情#"
                                + orderDetail.getId() + "检查接口订单状态完成:" + msg
                                + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                        orderLogService.loggingOrderLog(orderFlightLog3);
                    } else {
                        OrderLog orderFlightLog3 = orderLogService.createOrderLog(user, "订单详情#"
                                + orderDetail.getId() + "暂时没有查到订单信息,稍后查看"
                                + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                        orderLogService.loggingOrderLog(orderFlightLog3);
                        result.put("status", orderDetail.getStatus());
                        result.put("apiResult", "接口未查询到订单信息, 请稍候查看订单状态");
                    }
                    // 更新订单状态(非预定中或已支付的订单, 不在这里更新状态)
                    orderDispatchService.updateOrderStatus(orderDetail, result);
                }
            }
            // 本次已处理总订单详情数目
            processed += orderDetailList.size();
            if (processed >= total) {
                break;
            }
            pageIndex += 1;
            orderDetailList.clear();
        }
    }

    /**
     * 机票预订成功/失败/其他...短信通知(准备数据)
     * @param orderDetail
     * @param ticketResult
     */
    private void prepareSendMsg(OrderDetail orderDetail, TicketResult ticketResult) {
        User user = userService.get(9L);
        Order order = orderDetail.getOrder();
        TrafficPrice trafficPrice = trafficPriceService.findFullById(orderDetail.getCostId());
        Traffic traffic = trafficPrice.getTraffic();
        StringBuilder msg = new StringBuilder();
        TicketResult.ResultEntity resultEntity = ticketResult.getResult();
        String billStatus = resultEntity.getBillStatus();
        if ("SUCC_CREATE".equals(billStatus)) {
            // 取消该状态短信发送
            // ......
        } else if ("FAIL_ISSUE".equals(billStatus)) {
            // @SMS 发送整个订单出票失败短信
            orderMsgService.doSendFlightBookingFailMsg(orderDetail, trafficPrice);
        } else if ("SUCC_ISSUE".equals(billStatus)) {
            List<TicketResult.ResultEntity.TicketListEntity> ticketList = resultEntity.getTicketList();
            // @SMS 发送机票出票成功短信
            orderMsgService.doSendFlightBookingSuccessMsg(orderDetail, trafficPrice, ticketResult);
        }
    }
}
