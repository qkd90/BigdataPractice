package com.data.data.hmly.quartz.order;

import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderDispatchService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderMsgService;
import com.data.data.hmly.service.order.OrderRefundService;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.hmly.service.translation.flight.juhe.JuheFlightService;
import com.data.hmly.service.translation.flight.juhe.entity.TicketResult;
import com.framework.hibernate.util.Page;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/3/31.
 */
@Component
public class FlightTicketRefundQuzrtz {

    @Resource
    private PropertiesManager propertiesManager;

    @Resource
    private OrderRefundService orderRefundService;
    @Resource
    private OrderDispatchService orderDispatchService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private UserService userService;
    @Resource
    private OrderMsgService orderMsgService;
    @Resource
    private TrafficPriceService trafficPriceService;



    public void doRefunFlightTicket() {
        String flightKey = propertiesManager.getString("JUHE_FLIGHT_KEY");
        User user = userService.get(9L);
        List<OrderDetail> orderDetailList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        OrderDetail condition = new OrderDetail();
        Map<String, Object> result = new HashMap<String, Object>();
        // 取出取消中的机票订单
        condition.setStatus(OrderDetailStatus.CANCELING);
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
                TrafficPrice trafficPrice = trafficPriceService.findFullById(orderDetail.getCostId());
                OrderLog orderflightLog1 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "检查退票状态,现在状态:"
                        + orderDetail.getStatus().getDescription()
                        + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId(),
                        OrderLogLevel.debug);
                orderLogService.loggingOrderLog(orderflightLog1);
                result.put("orderId", orderDetail.getRealOrderId());
                result.put("type", ProductType.train);
                if (OrderDetailStatus.CANCELING.equals(orderDetail.getStatus())) {
                    String realOrderId = orderDetail.getRealOrderId();
                    TicketResult ticketResult = JuheFlightService.getTicket(flightKey, realOrderId);
                    if (ticketResult.getResult() != null) {
                        TicketResult.ResultEntity resultEntity = ticketResult.getResult();
                        List<TicketResult.ResultEntity.TicketListEntity> ticketList = resultEntity.getTicketList();
                        String billStatus = ticketResult.getResult().getBillStatus();
                        OrderLog orderflightLog2 = orderLogService.createOrderLog(user, "订单详情#"
                                + orderDetail.getId() + "已获取接口订单状态:" + billStatus
                                + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId(),
                                OrderLogLevel.debug);
                        orderLogService.loggingOrderLog(orderflightLog2);
                        // 总退款金额 (初始, 未扣减手续费)
                        Float totalRefundFee = orderDetail.getFinalPrice();
                        // 总退票手续费
                        Float totalFee = 0F;
                        for (TicketResult.ResultEntity.TicketListEntity ticketListEntity : ticketList) {
                            String ticketStatus = ticketListEntity.getTicketStatus();
                            String msg = "";
                            if ("SUCC_CANCEL".equals(billStatus)) {
                                msg = "取消订单成功!";
                                result.put("status", OrderDetailStatus.CANCELING);
                            } else if ("FAIL_CANCEL".equals(billStatus)) {
                                msg = "取消订单失败";
                                result.put("status", orderDetail.getStatus());
                            } else if ("REFUND_ING".equals(billStatus)) {
                                msg = "正在退款";
                                result.put("status", OrderDetailStatus.CANCELING);
                            } else if ("SUCC_REFUND".equals(billStatus)) {
                                msg = "退票成功";
                                result.put("status", OrderDetailStatus.CANCELED);
                                // 单张机票退票手续费
                                Float singleRefundFee = Float.parseFloat(ticketListEntity.getRefundFee() == null ? "0" : ticketListEntity.getRefundFee());
                                OrderLog orderFlightLog3 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId()
                                        + "旅客: " + ticketListEntity.getPassengerName() + "(#" + ticketListEntity.getIdNumber() + ")退票成功!"
                                        + "退票手续费: " + singleRefundFee + "机票原总价: " + ticketListEntity.getSum()
                                        + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                                orderLogService.loggingOrderLog(orderFlightLog3);
                                // 累加总退票手续费
                                totalFee += singleRefundFee;
//                                Double userBalance = refundUser.getBalance() == null ? 0D : refundUser.getBalance();
//                                refundUser.setBalance(userBalance + finallyRefundFee);
//                                userService.update(refundUser);
                            } else if ("FAIL_REFUND".equals(billStatus)) {
                                msg = "退票失败";
                                result.put("status", orderDetail.getStatus());
                                OrderLog orderFlightLog4 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId()
                                        + ",旅客: " + ticketListEntity.getPassengerName() + "(#" + ticketListEntity.getIdNumber() + ")退票失败!"
                                        + ",该旅客机票状态:" + ticketStatus
                                        + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                                orderLogService.loggingOrderLog(orderFlightLog4);
                                // @SMS 发送发送机票退订失败短信 (单张机票)
                                orderMsgService.doSendFlightBookingFailMsg(orderDetail, trafficPrice);
                            } else if ("CHANGE_ING".equals(billStatus)) {
                                msg = "正在改期";
                                result.put("status", orderDetail.getStatus());
                            } else if ("SUCC_CHANGE".equals(billStatus)) {
                                msg = "改期成功";
                                result.put("status", orderDetail.getStatus());
                            } else if ("FAIL_CHANGE".equals(billStatus)) {
                                msg = "改期失败";
                                result.put("status", orderDetail.getStatus());
                            } else {
                                msg = "未获取到信息, 稍后查询";
                                result.put("status", orderDetail.getStatus());
                            }
                            result.put("apiResult", msg);
                            OrderLog orderFlightLog9 = orderLogService.createOrderLog(user, "订单详情#"
                                    + orderDetail.getId() + "检查乘客:" + ticketListEntity.getPassengerName() + "机票状态完成:" + msg
                                    + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                    + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(orderFlightLog9);
                        }
                        // 进行退款操作
                        // 用户账户余额更新 (已变更退款方式 2016-05-26)
                        // 计算最终退款金额 (扣减总退票手续费后)
                        totalRefundFee = totalRefundFee - totalFee;
                        User refundUser = userService.get(orderDetail.getOrder().getUser().getId());
                        orderDetail.setRefund(totalRefundFee);
                        OrderLog orderFlightLog5 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId()
                                + "退票完成,已向用户# " + refundUser.getId() + "退款, 金额 :" + totalRefundFee
                                + "总手续费: " + totalFee + "(#" + orderDetail.getRealOrderId() + ")",
                                orderDetail.getOrder().getId(), orderDetail.getId());
                        orderLogService.loggingOrderLog(orderFlightLog5);
                        // @ auto refund (根据付款方式, 退回原支付账户)
                        orderRefundService.doStartRefund(orderDetail.getOrder(), orderDetail, totalRefundFee);
                    } else {
                        OrderLog orderflightLog10 = orderLogService.createOrderLog(user, "订单详情#"
                                + orderDetail.getId() + "暂时没有获取到接口订单信息!"
                                + "原因:" + ticketResult.getReason() + "(" + ticketResult.getResult() + ")"
                                + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                        orderLogService.loggingOrderLog(orderflightLog10);
                    }
                    // 更新订单状态(非取消中的订单, 不在这里更新状态)
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
}
