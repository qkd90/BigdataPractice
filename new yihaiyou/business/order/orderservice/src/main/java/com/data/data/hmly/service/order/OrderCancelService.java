package com.data.data.hmly.service.order;

import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.pojo.CancelOrderResult;
import com.data.data.hmly.service.elong.service.result.HotelOrderCancelResult;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.nctripticket.CtripTicketApiService;
import com.data.data.hmly.service.nctripticket.CtripTicketService;
import com.data.data.hmly.service.nctripticket.pojo.CtripCancelOrderItemVO;
import com.data.data.hmly.service.nctripticket.pojo.CtripOrderCancelCheckVO;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.hmly.service.translation.flight.juhe.JuheFlightService;
import com.data.hmly.service.translation.flight.juhe.entity.RefundCheckResult;
import com.data.hmly.service.translation.flight.juhe.entity.RefundResult;
import com.data.hmly.service.translation.flight.juhe.entity.TicketResult;
import com.data.hmly.service.translation.train.juhe.JuheTrainService;
import com.data.hmly.service.translation.train.juhe.entity.Ticket;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zzl on 2016/5/23.
 */
@Service
public class OrderCancelService {

    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private UserService userService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderMsgService orderMsgService;
    @Resource
    private OrderRefundService orderRefundService;
    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private ElongHotelService elongHotelService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private CtripTicketApiService ctripTicketApiService;
    @Resource
    private CtripTicketService ctripTicketService;

//    @Resource
//    private RefundLogService refundLogService;

    public Map<String, Object> doStartCancel(Order order, OrderDetail orderDetail, SysUser loginUser) {
        Map<String, Object> result;
        result = orderService.isAbleToCancel(order, orderDetail);
        if (!(Boolean) result.get("isAbleToCancel")) {
            return result;
        } else {
            if (OrderStatus.WAIT.equals(order.getStatus()) || OrderDetailStatus.FAILED.equals(orderDetail.getStatus())) {
                if (OrderDetailStatus.FAILED.equals(orderDetail.getStatus())) {
                    // 用户账户余额更新 (已变更退款方式 2016-05-26)
//                    User user = userService.get(orderDetail.getOrder().getUser().getId());
//                    user.setBalance(user.getBalance() == null ? 0 : user.getBalance() + orderDetail.getFinalPrice());
//                    userService.update(user);
                    // @ auto refund (根据付款方式, 退回支付账户)
                    result = orderRefundService.doStartRefund(order, orderDetail, orderDetail.getFinalPrice());
                    // 确认退款结果
                    if (!(Boolean) result.get("isAbleToCancel")) {
                        OrderLog orderLog = orderLogService.createOrderLog(
                                loginUser, "订单详情#"
                                        + orderDetail.getId() + "取消失败!退款失败",
                                orderDetail.getId(),
                                orderDetail.getOrder().getId());
                        orderLogService.loggingOrderLog(orderLog);
                        return result;
                    }
                    orderDetail.setApiResult(orderDetail.getApiResult() + "(已取消,可退:"
                            + orderDetail.getFinalPrice() + ")");
                    // 设置订单详情的退款金额
                    orderDetail.setRefund(orderDetail.getFinalPrice());
                    OrderLog orderLog = orderLogService.createOrderLog(
                            loginUser, "订单详情#"
                                    + orderDetail.getId() + "取消成功!可退:"
                                    + orderDetail.getFinalPrice(),
                            orderDetail.getId(),
                            orderDetail.getOrder().getId());
                    orderLogService.loggingOrderLog(orderLog);
                } else if (OrderStatus.WAIT.equals(order.getStatus())) {
                    orderDetail.setApiResult(orderDetail.getApiResult() + "(已取消,无需退款)");
                    OrderLog orderLog = orderLogService.createOrderLog(
                            loginUser,
                            "订单详情#" + orderDetail.getId() + "取消成功!无可退款项",
                            orderDetail.getId(), orderDetail.getOrder().getId());
                    orderLogService.loggingOrderLog(orderLog);
                }
//                    // 只有一个子订单且被退, 整个订单关闭
//                    if (order.getOrderDetails().size() == 1) {
//                        order.setStatus(OrderStatus.CLOSED);
//                        orderService.update(order);
//                    }
                orderDetail.setStatus(OrderDetailStatus.CANCELED);
                orderDetailService.update(orderDetail);
                result = orderService.handleTicketCancelResult(result, "提交退订成功!", orderDetail, true);
                switch (orderDetail.getProductType()) {
                    case scenic:
                        TicketPrice ticketPrice = ticketPriceService.findFullById(orderDetail.getCostId());
                        // @SMS 发送门票退订成功短信
                        orderMsgService.doSendTicketCancelSuccessMsg(orderDetail, ticketPrice);
                        break;
                    case hotel:
                        // @SMS 发送酒店退订成功短信
                        HotelPrice hotelPrice = hotelPriceService.findFullById(orderDetail.getCostId());
                        orderMsgService.doSendHotelCancelSuuccessMsg(orderDetail, hotelPrice);
                        break;
                    case train:
                        // @SMS 发送火车票退订成功短信
                        TrafficPrice trainTrafficPrice = trafficPriceService.findFullById(orderDetail.getCostId());
                        orderMsgService.doSendTrainCancelSuccessMsg(orderDetail, trainTrafficPrice);
                        break;
                    case flight:
                        // @SMS 发送飞机票退订成功短信
                        TrafficPrice flightTrafficPrice = trafficPriceService.findFullById(orderDetail.getCostId());
                        orderMsgService.doSendFlightCancelSuccessMsg(orderDetail, flightTrafficPrice);
                        break;
                    default:
                        break;
                }
                return result;
            } else {
                switch (orderDetail.getProductType()) {
                    case scenic:
                        result = this.doCancelScenicTicket(order, orderDetail, loginUser);
                        break;
                    case hotel:
                        result = this.doCancelHotel(order, orderDetail, loginUser);
                        break;
                    case train:
                        result = this.doCancelTrain(order, orderDetail, loginUser);
                        break;
                    case flight:
                        result = this.doCancelFlight(order, orderDetail, loginUser);
                        break;
                    default:
                        break;
                }
            }
        }
        return result;
    }


    /**
     * 景点门票退订
     * DO! NOT! call this method directly
     * @param orderDetail
     * @return
     */
    private Map<String, Object> doCancelScenicTicket(Order order, OrderDetail orderDetail, SysUser loginUser) {
        Map<String, Object> result = new HashMap<String, Object>();
        String realOrderId = orderDetail.getRealOrderId();
        TicketPrice ticketPrice = ticketPriceService.findFullById(orderDetail.getCostId());
        if (realOrderId != null && !"".equals(realOrderId)) {
            Long ctripOrderId = Long.parseLong(realOrderId);
            String uuid = UUID.randomUUID().toString();
            try {
                OrderLog orderTicketLog0 = orderLogService.createOrderLog(loginUser, "订单详情#"
                        + orderDetail.getId() + "检查是否可退, 现在状态"
                        + orderDetail.getStatus().getDescription()
                        + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTicketLog0);
                // 检查可退
                CtripOrderCancelCheckVO ctripOrderCancelCheckVO = ctripTicketApiService.doOrderCancelCheck(ctripOrderId, uuid);
                OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情#"
                        + orderDetail.getId() + "已检查接口订单可退标记:"
                        + ctripOrderCancelCheckVO.getCancelStatus()
                        + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTicketLog1);
                if (Integer.valueOf(1).equals(ctripOrderCancelCheckVO.getCancelStatus())) {
                    String reason = "其他";
                    // 查询订单详情（获取订单状态和订单项信息）
                    List<CtripCancelOrderItemVO> cancelItems = new ArrayList<CtripCancelOrderItemVO>(); // 资源退款信息集合
                    com.data.data.hmly.service.ctripcommon.enums.OrderStatus ctripOrderStatus = ctripTicketApiService.doGetOrderStatus(ctripOrderId, uuid, cancelItems);
                    OrderLog orderTicketLog2 = orderLogService.createOrderLog(loginUser, "订单详情#"
                            + orderDetail.getId() + "已获取接口订单状态:"
                            + ctripOrderStatus.getDescription()
                            + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderTicketLog2);
                    switch (ctripOrderStatus) {
                        case CREATE:
                            break;
                        case SUCCESS:
                            boolean success = ctripTicketApiService.doOrderCancel(ctripOrderId, reason, cancelItems, uuid);
                            if (success) {
                                // 更新订单状态为退订中
                                ctripTicketService.updateOrderStatus(ctripOrderId, ctripOrderStatus);
                                // 更新本地订单状态
                                orderDetail.setStatus(OrderDetailStatus.CANCELING);
                                orderDetail.setRefund(ctripOrderCancelCheckVO.getTotalAmount().floatValue());
                                orderDetail.setApiResult(orderDetail.getApiResult() + "(已提交退订,可退" + ctripOrderCancelCheckVO.getTotalAmount() + ")");
                                orderDetailService.update(orderDetail);
                                // 用户账户余额更新 (已变更退款方式 2016-05-26)
//                                User refundUser = userService.get(orderDetail.getOrder().getUser().getId());
//                                refundUser.setBalance(refundUser.getBalance() == null ? 0 : refundUser.getBalance() + ctripOrderCancelCheckVO.getTotalAmount());
//                                userService.update(refundUser);
                                // @ auto refund (根据付款方式, 退回支付账户)
                                orderRefundService.doStartRefund(order, orderDetail, ctripOrderCancelCheckVO.getTotalAmount().floatValue());
                                result = orderService.handleTicketCancelResult(result, "已成功提交退订!", orderDetail, true);
                                OrderLog orderTicketLog3 = orderLogService.createOrderLog(loginUser, "订单详情#"
                                        + orderDetail.getId() + "已成功提交退订!"
                                        + ctripOrderStatus.getDescription()
                                        + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                                orderLogService.loggingOrderLog(orderTicketLog3);
                                // @SMS 发送门票退订成功短信
                                orderMsgService.doSendTicketCancelSuccessMsg(orderDetail, ticketPrice);
                                return result;
                            } else {
                                result = orderService.handleTicketCancelResult(result, "退订失败,稍候重试", orderDetail, false);
                                return result;
                            }
                        case CANCELING:
                            // 如果是退订中或已退订，更新订单状态
                            ctripTicketService.updateOrderStatus(ctripOrderId, ctripOrderStatus);
                            // 更新本地订单状态
                            orderDetail.setStatus(OrderDetailStatus.CANCELING);
                            orderDetail.setApiResult(orderDetail.getApiResult() + "(已提交退订,可退" + ctripOrderCancelCheckVO.getTotalAmount() + ")");
                            orderDetailService.update(orderDetail);
                            result = orderService.handleTicketCancelResult(result, "您已退订过,请稍后查询退订结果", orderDetail, false);
                            break;
                        case CANCELED:
                            // 如果是退订中或已退订，更新订单状态
                            ctripTicketService.updateOrderStatus(ctripOrderId, ctripOrderStatus);
                            // 更新本地订单状态
                            orderDetail.setStatus(OrderDetailStatus.CANCELED);
                            orderDetail.setApiResult(orderDetail.getApiResult() + "(已成功退订,可退" + ctripOrderCancelCheckVO.getTotalAmount() + ")");
                            orderDetailService.update(orderDetail);
                            result = orderService.handleTicketCancelResult(result, "您已退订过,请稍后查询退订结果", orderDetail, false);
                            break;
                        case FAIL:
                            break;
                        default:
                            result = orderService.handleTicketCancelResult(result, "退订失败,稍候重试", orderDetail, false);
                            break;
                    }
                } else {
                    result = orderService.handleTicketCancelResult(result, "该门票不能退订!", orderDetail, false);
                    // @SMS 发送门票退订失败短信
                    orderMsgService.doSendTicketCancelFailMsg(orderDetail, ticketPrice);
                }
            } catch (Exception e) {
                result = orderService.handleTicketCancelResult(result, "无法退订,稍候重试", orderDetail, false);
            }
        } else {
            result = orderService.handleTicketCancelResult(result, "退订失败!订单号为空!", orderDetail, false);
        }
        return result;
    }


    /**
     * 艺龙酒店退订
     * DO! NOT! call this method directly
     * @param orderDetail
     * @return
     */
    private Map<String, Object> doCancelHotel(Order order, OrderDetail orderDetail, SysUser loginUser) {
        Map<String, Object> result = new HashMap<String, Object>();
        String realOrderId = orderDetail.getRealOrderId();
        HotelPrice hotelPrice = hotelPriceService.findFullById(orderDetail.getCostId());
        if (realOrderId != null && !"".equals(realOrderId)) {
            Long elongOrderId = Long.parseLong(realOrderId);
            HotelOrderCancelResult hotelCancelOrderResult = elongHotelService.cancelOrder(elongOrderId, "其他", "其他");
            CancelOrderResult cancelOrderResult = hotelCancelOrderResult.getResult();
            if ("0".equals(hotelCancelOrderResult.getCode()) && cancelOrderResult != null) {
                if (cancelOrderResult.isSuccesss()) {
                    orderDetail.setStatus(OrderDetailStatus.CANCELED);
                    orderDetail.setApiResult(orderDetail.getApiResult() + "(已退订,没有退款)");
                    orderDetailService.update(orderDetail);
                    // @SMS 发送酒店退订成功短信
                    orderMsgService.doSendHotelCancelSuuccessMsg(orderDetail, hotelPrice);
                    result = orderService.handleTicketCancelResult(result, "退订成功!", orderDetail, true);
                } else {
                    // @SMS 发送酒店退订失败短信
                    orderMsgService.doSendHotelCancelFailMsg(orderDetail, hotelPrice);
                    result = orderService.handleTicketCancelResult(result, "退订失败", orderDetail, false);
                }
            } else if (hotelCancelOrderResult.getCode().contains("H001056")) {
                // H001056 : 订单已经处于取消状态
                orderDetail.setStatus(OrderDetailStatus.CANCELED);
                orderDetail.setApiResult(orderDetail.getApiResult() + "(已退订,没有退款)");
                orderDetailService.update(orderDetail);
                result = orderService.handleTicketCancelResult(result, "退订成功", orderDetail, true);
            } else {
                // 其他退订失败情况
                result = orderService.handleTicketCancelResult(result, hotelCancelOrderResult.getCode(), orderDetail, false);
            }
        } else {
            result = orderService.handleTicketCancelResult(result, "退订失败!订单号为空!", orderDetail, false);
        }
        return result;
    }


    /**
     * 火车票退订
     * DO! NOT! call this method directly
     * @param orderDetail
     * @return
     */
    private Map<String, Object> doCancelTrain(Order order, OrderDetail orderDetail, SysUser loginUser) {
        Map<String, Object> result = new HashMap<String, Object>();
        String realOrderId = orderDetail.getRealOrderId();
        TrafficPrice trafficPrice = trafficPriceService.findFullById(orderDetail.getCostId());
        String key = propertiesManager.getString("JUHE_TRAIN_KEY");
        if (realOrderId != null && !"".equals(realOrderId)) {
            OrderDetailStatus status = orderDetail.getStatus();
            if (OrderDetailStatus.SUCCESS.equals(status)) {
                OrderLog orderTrainLog0 = orderLogService.createOrderLog(loginUser, "订单详情#"
                        + orderDetail.getId() + "检查是否可退, 现在状态"
                        + orderDetail.getStatus().getDescription()
                        + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTrainLog0);
                // 接口已经付款火车票退订
                com.data.hmly.service.translation.train.juhe.entity.TicketResult ticketResult = JuheTrainService.getTicket(key, realOrderId);
                OrderLog orderTrainLog1 = orderLogService.createOrderLog(loginUser, "订单详情#"
                        + orderDetail.getId() + "已获取接口订单信息:"
                        + ticketResult.getResult().getMsg()
                        + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTrainLog1);
                List<com.data.hmly.service.translation.train.juhe.entity.TicketResult.ResultEntity.PassengersEntity> passengers = ticketResult.getResult().getPassengers();
                List<Ticket> tickets = new ArrayList<Ticket>();
                for (com.data.hmly.service.translation.train.juhe.entity.TicketResult.ResultEntity.PassengersEntity passenger : passengers) {
                    Ticket ticket = new Ticket();
                    ticket.setTicket_no(passenger.getTicket_no());
                    ticket.setPassengername(passenger.getPassengersename());
                    ticket.setPassporttypeseid(passenger.getPassporttypeseid());
                    ticket.setPassportseno(passenger.getPassportseno());
                    tickets.add(ticket);
                }
                com.data.hmly.service.translation.train.juhe.entity.TicketResult refundResult = JuheTrainService.refundTicket(key, realOrderId, tickets);
                if (Integer.valueOf(0).equals(refundResult.getError_code())) {
                    orderDetail.setStatus(OrderDetailStatus.CANCELING);
                    orderDetail.setApiResult(orderDetail.getApiResult() + "(已提交退订,稍后查看退款情况!");
                    orderDetailService.update(orderDetail);
                    result = orderService.handleTicketCancelResult(result, "已提交退订, 请稍候查询退订结果!", orderDetail, true);
                    OrderLog orderTrainLog2 = orderLogService.createOrderLog(loginUser, "订单详情#"
                            + orderDetail.getId() + "已提交退订, 已获取接口订单信息:"
                            + refundResult.getResult().getMsg()
                            + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderTrainLog2);
                } else {
                    result = orderService.handleTicketCancelResult(result, "退订失败!请稍候重试", orderDetail, true);
                    OrderLog orderTrainLog3 = orderLogService.createOrderLog(loginUser, "订单详情#"
                            + orderDetail.getId() + "提交退订失败"
                            + ",接口返回:" + refundResult.getReason()
                            + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderTrainLog3);
                    // @SMS 发送火车票退订失败短信
                    orderMsgService.doSendTrainCancelFailMsg(orderDetail, trafficPrice);
                }
            } else {
                // 接口未付款火车票退订, 按订单详情价格退回原价
                OrderLog orderflightLog4 = orderLogService.createOrderLog(loginUser, "订单详情#"
                        + orderDetail.getId() + "接口未付款订单退订, 现在状态"
                        + orderDetail.getStatus().getDescription()
                        + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderflightLog4);
                com.data.hmly.service.translation.train.juhe.entity.CancelOrderResult cancelOrderResult = JuheTrainService.cancelOrder(key, realOrderId);
                if (Integer.valueOf(0).equals(cancelOrderResult.getError_code())) {
                    orderDetail.setStatus(OrderDetailStatus.CANCELED);
                    orderDetail.setRefund(orderDetail.getFinalPrice());
                    orderDetail.setApiResult(orderDetail.getApiResult() + "(已退订,可退: " + orderDetail.getFinalPrice() + ")");
                    orderDetailService.update(orderDetail);
                    // 用户账户余额更新 (已变更退款方式 2016-05-26)
                    User refundUser = userService.get(orderDetail.getOrder().getUser().getId());
//                    refundUser.setBalance(refundUser.getBalance() == null ? 0 : refundUser.getBalance() + orderDetail.getFinalPrice());
//                    userService.update(refundUser);
                    // @ auto refund (根据付款方式, 退回支付账户)
                    orderRefundService.doStartRefund(order, orderDetail, orderDetail.getFinalPrice());
                    result = orderService.handleTicketCancelResult(result, "已提交退订, 请稍候查询退订结果", orderDetail, true);
                    OrderLog orderflightLog5 = orderLogService.createOrderLog(loginUser, "订单详情#"
                            + orderDetail.getId() + "接口未付款订单退订, 已经退订!"
                            + "已向用户: " + refundUser.getUserName() + "(#" + refundUser.getId() + ")"
                            + "退款, 金额: " + orderDetail.getFinalPrice()
                            + orderDetail.getStatus().getDescription()
                            + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderflightLog5);
                    // @SMS 发送火车票退订成功短信
                    orderMsgService.doSendTrainCancelSuccessMsg(orderDetail, trafficPrice);
                } else {
                    result = orderService.handleTicketCancelResult(result, "退订失败,稍后重试", orderDetail, false);
                    OrderLog orderflightLog6 = orderLogService.createOrderLog(loginUser, "订单详情#"
                            + orderDetail.getId() + "接口未付款订单退订, 退订失败!"
                            + "接口返回: " + cancelOrderResult.getReason()
                            + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderflightLog6);
                    // @SMS 发送火车票退订失败短信
                    orderMsgService.doSendTrainCancelFailMsg(orderDetail, trafficPrice);
                }
            }
        } else {
            result = orderService.handleTicketCancelResult(result, "退订失败!订单号为空!", orderDetail, false);
        }
        return result;
    }


    /**
     * 机票退订
     * DO! NOT! call this method directly
     * @param orderDetail
     * @return
     */
    private Map<String, Object> doCancelFlight(Order order, OrderDetail orderDetail, SysUser loginUser) {
        Map<String, Object> result = new HashMap<String, Object>();
        String realOrderId = orderDetail.getRealOrderId();
        TrafficPrice trafficPrice = trafficPriceService.findFullById(orderDetail.getCostId());
        String key = propertiesManager.getString("JUHE_FLIGHT_KEY");
        if (realOrderId != null && !"".equals(realOrderId)) {
            OrderDetailStatus status = orderDetail.getStatus();
            if (OrderDetailStatus.SUCCESS.equals(status)) {
                OrderLog orderFlightLog0 = orderLogService.createOrderLog(loginUser, "订单详情#"
                        + orderDetail.getId() + "检查是否可退, 现在状态"
                        + orderDetail.getStatus().getDescription()
                        + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderFlightLog0);
                //接口已经付款, 机票退订, 先检查可退
                com.data.hmly.service.translation.flight.juhe.entity.TicketResult ticketResult = JuheFlightService.getTicket(key, realOrderId);
                List<TicketResult.ResultEntity.TicketListEntity> ticketList = ticketResult.getResult().getTicketList();
                Integer ticketNum = ticketList.size();
                Integer successRefNum = Integer.valueOf(0);
                for (com.data.hmly.service.translation.flight.juhe.entity.TicketResult.ResultEntity.TicketListEntity flightTicket : ticketList) {
                    // 取票号
                    String ticketNo = flightTicket.getTicketNum();
                    RefundCheckResult refundCheckResult = JuheFlightService.refundCheck(key, realOrderId, ticketNo);
                    OrderLog orderFlightLog1 = orderLogService.createOrderLog(loginUser, "订单详情#"
                            + orderDetail.getId() + "可退状态: " + refundCheckResult.getReason()
                            + "(" + refundCheckResult.getResult().getRefundCheckResult().getFlag() + ")"
                            + ",退票费用" + refundCheckResult.getResult().getRefundCheckResult().getFee()
                            + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderFlightLog1);
                    if ("200".equals(refundCheckResult.getError_code())
                            && "Y".equals(refundCheckResult.getResult().getRefundCheckResult().getFlag())) {
                        String ticketId = flightTicket.getTicketNum();
                        RefundResult refundResult = JuheFlightService.refund(true, key, realOrderId, ticketId);
                        if ("200".equals(refundResult.getError_code())) {
                            successRefNum++;
                            OrderLog orderflightLog2 = orderLogService.createOrderLog(loginUser, "订单详情#"
                                    + orderDetail.getId() + "有乘客提交退票:"
                                    + flightTicket.getPassengerName() + "(#" + flightTicket.getIdNumber() + ")"
                                    + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(orderflightLog2);
                        } else {
                            OrderLog orderflightLog3 = orderLogService.createOrderLog(loginUser, "订单详情#"
                                    + orderDetail.getId() + "有乘客提交退票失败:"
                                    + flightTicket.getPassengerName() + "(#" + flightTicket.getIdNumber() + ")"
                                    + ", 原因:" + refundResult.getReason()
                                    + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(orderflightLog3);
                        }
                    } else {
                        OrderLog orderflightLog6 = orderLogService.createOrderLog(loginUser, "订单详情#"
                                + orderDetail.getId() + "订单当前不可退!"
                                + "原因: " + refundCheckResult.getResult().getRefundCheckResult().getMessage()
                                + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                        orderLogService.loggingOrderLog(orderflightLog6);
                        // 不可退
                        result = orderService.handleTicketCancelResult(result, "退订失败! 请稍后重试", orderDetail, false);
                    }
                }
                // 检查整体退票情况, 是否全部或部分退票成功
                if (successRefNum == ticketNum) {
                    orderDetail.setStatus(OrderDetailStatus.CANCELING);
//                        Float refund = orderDetail.getFinalPrice() - Float.parseFloat(refundCheckResult.getResult().getRefundCheckResult().getFee());
                    orderDetail.setApiResult(orderDetail.getApiResult() + "已提交退订, 请稍候查询退订结果");
                    orderDetailService.update(orderDetail);
                    // 用户账户余额更新 (已变更退款方式 2016-05-26)
//                        User user = userService.get(orderDetail.getOrder().getUser().getId());
//                        user.setBalance(user.getBalance() + orderDetail.getFinalPrice());
//                        userService.update(user);
                    result = orderService.handleTicketCancelResult(result, "退订已经提交,请稍候查看退订结果", orderDetail, false);
                    OrderLog orderflightLog4 = orderLogService.createOrderLog(loginUser, "订单详情#"
                            + orderDetail.getId() + "已提交全部旅客退票,稍候查看退订结果"
                            + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderflightLog4);
                } else {
                    OrderLog orderflightLog5 = orderLogService.createOrderLog(loginUser, "订单详情#"
                            + orderDetail.getId() + "已提交部分旅客退票,有部分旅客退票失败. 稍候查看退票结果"
                            + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderflightLog5);
                    result = orderService.handleTicketCancelResult(result, "部分退订失败! 请知晓!", orderDetail, false);
                }
            } else {
                // 接口未付款时候, 机票退订
                OrderLog orderflightLog7 = orderLogService.createOrderLog(loginUser, "订单详情#"
                        + orderDetail.getId() + "接口未付款订单退订, 现在状态"
                        + orderDetail.getStatus().getDescription()
                        + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderflightLog7);
                com.data.hmly.service.translation.flight.juhe.entity.CancelOrderResult cancelOrderResult = JuheFlightService.cancelOrderResult("其他", key, realOrderId);
                if (Integer.valueOf(200).equals(cancelOrderResult.getError_code())) {
                    orderDetail.setStatus(OrderDetailStatus.CANCELED);
                    orderDetail.setApiResult(orderDetail.getApiResult() + "(已退订,可退: " + orderDetail.getFinalPrice() + ")");
                    orderDetail.setRefund(orderDetail.getFinalPrice());
                    orderDetailService.update(orderDetail);
                    // 用户账户余额更新 (已变更退款方式 2016-05-26)
                    User refundUser = userService.get(orderDetail.getOrder().getUser().getId());
//                    refundUser.setBalance(refundUser.getBalance() == null ? 0 : refundUser.getBalance() + orderDetail.getFinalPrice());
//                    userService.update(refundUser);
                    // @ auto refund (根据付款方式, 退回支付账户)
                    orderRefundService.doStartRefund(order, orderDetail, orderDetail.getFinalPrice());
                    result = orderService.handleTicketCancelResult(result, "退订已提交,请稍后查看退订结果", orderDetail, true);
                    OrderLog orderflightLog8 = orderLogService.createOrderLog(loginUser, "订单详情#"
                            + orderDetail.getId() + "接口未付款订单退订, 已经退订!"
                            + "已向用户: " + refundUser.getUserName() + "(#" + refundUser.getId() + ")"
                            + "退款, 金额: " + orderDetail.getFinalPrice()
                            + orderDetail.getStatus().getDescription()
                            + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderflightLog8);
                    // @SMS 发送机票退订成功短信
                    orderMsgService.doSendFlightCancelSuccessMsg(orderDetail, trafficPrice);
                } else {
                    result = orderService.handleTicketCancelResult(result, "退订失败,稍后重试", orderDetail, false);
                    OrderLog orderflightLog9 = orderLogService.createOrderLog(loginUser, "订单详情#"
                            + orderDetail.getId() + "接口未付款订单退订, 退订失败!"
                            + "原因: " + cancelOrderResult.getReason() + "(" + cancelOrderResult.getResult() + ")"
                            + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderflightLog9);
                }
            }
        } else {
            result = orderService.handleTicketCancelResult(result, "退订失败!订单号为空!", orderDetail, false);
        }
        return result;
    }
}
