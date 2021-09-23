package com.data.data.hmly.service.outOrder;

import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderMsgService;
import com.data.data.hmly.service.order.OrderRefundService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.data.data.hmly.service.outOrder.entity.JszxOrderDetail;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderStatus;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2016/6/8.
 */
@Service
public class OutOrderCancelService {

    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderRefundService orderRefundService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderMsgService orderMsgService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private LinetypepriceService linetypepriceService;


    @Resource
    private JszxOrderService jszxOrderService;
    @Resource
    private JszxOrderDetailService jszxOrderDetailService;
    @Resource
    private BalanceService balanceService;


    public Map<String, Object> doStartCancel(Order order, OrderDetail orderDetail, SysUser loginUser) {
        Map<String, Object> result = new HashMap<>();
        if (OrderStatus.WAIT.equals(order.getStatus()) || OrderDetailStatus.FAILED.equals(orderDetail.getStatus())) {
            // 失败或者为付款订单退订 (直接退)
            if (OrderDetailStatus.FAILED.equals(orderDetail.getStatus())) {
                // @ auto refund (根据付款方式, 退回支付账户)
                result = orderRefundService.doStartRefund(order, orderDetail, orderDetail.getFinalPrice());
                // 确认退款结果
                if (!(Boolean) result.get("isAbleToCancel")) {
                    OrderLog orderLog = orderLogService.createOrderLog(
                            loginUser, "订单详情(G)#"
                                    + orderDetail.getId() + "退订失败!退款失败",
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
                        loginUser, "订单详情(G)#"
                                + orderDetail.getId() + "取消成功!可退:"
                                + orderDetail.getFinalPrice(),
                        orderDetail.getId(),
                        orderDetail.getOrder().getId());
                orderLogService.loggingOrderLog(orderLog);

            } else if (OrderStatus.WAIT.equals(order.getStatus())) {
                orderDetail.setApiResult(orderDetail.getApiResult() + "(已取消,无需退款)");
                OrderLog orderLog = orderLogService.createOrderLog(
                        loginUser,
                        "订单详情(G)#" + orderDetail.getId() + "取消成功!无可退款项",
                        orderDetail.getId(), orderDetail.getOrder().getId());
                orderLogService.loggingOrderLog(orderLog);
            }
            orderDetail.setStatus(OrderDetailStatus.CANCELED);
            orderDetailService.update(orderDetail);
            result = orderService.handleTicketCancelResult(result, "提交退订成功!", orderDetail, true);
            // 退订成功短信发送入口
            this.doPreSendCancelSuccessMsg(orderDetail);
        } else {
            // 其他状态(指成功状态的)订单退订
            switch (orderDetail.getProductType()) {
                case scenic:
                    result = this.doCancelScenicTicket(order, orderDetail, loginUser);
                    break;
                case hotel:
                    result = this.doCancelHotel(order, orderDetail, loginUser);
                    break;
                case line:
                    result = this.doCancelLine(order, orderDetail, loginUser);
                    break;
                case ship:
                    result = this.doCancelShip(order, orderDetail, loginUser);
                    break;
                case insurance:
                    result = this.doCancelInsurance(order, orderDetail, loginUser);
                default:
                    result = orderService.handleTicketCancelResult(result, "没有找到可用的退订渠道!", orderDetail, false);
                    break;
            }
        }
        return result;
    }

    /**
     * 供应商景点门票退订
     * DO! NOT! call this method directly
     * @param order
     * @param orderDetail
     * @param loginUser
     * @return
     */
    private Map<String, Object> doCancelScenicTicket(Order order, OrderDetail orderDetail, SysUser loginUser) {
        Map<String, Object> result = new HashMap<String, Object>();
        String realOrderIdStr = orderDetail.getRealOrderId();
        Long costId = orderDetail.getCostId();
        TicketPrice ticketPrice = ticketPriceService.findFullById(costId);
        if (StringUtils.hasText(realOrderIdStr)) {
            Long realOrderId = Long.parseLong(realOrderIdStr);
            JszxOrderDetail jszxOrderDetail = jszxOrderDetailService.load(realOrderId);
            Date playDate = orderDetail.getPlayDate();
            Calendar playCalendar = Calendar.getInstance();
            Calendar nowCalendar = Calendar.getInstance();
            playCalendar.setTime(playDate);
            Integer allowDay = playCalendar.get(Calendar.DAY_OF_YEAR);
            Integer nowDay = nowCalendar.get(Calendar.DAY_OF_YEAR);
            OrderLog orderTicketLog0 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                    + orderDetail.getId() + "检查是否可退, 现在状态"
                    + orderDetail.getStatus().getDescription()
                    + ",(#" + orderDetail.getRealOrderId() + ")",
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTicketLog0);
            // 简单退票验证, 无验票记录, 不能过期(含当天)
            if (jszxOrderDetail.getRestCount() != jszxOrderDetail.getCount()) {
                result.put("isAbleToCancel", false);
                result.put("msg", "门票有验票记录! 不能退订!");
                OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                                + orderDetail.getId() + "门票有验票记录! 不能退订!现在状态:"
                                + orderDetail.getStatus().getDescription()
                                + ",(#" + orderDetail.getRealOrderId() + ")",
                        orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTicketLog1);
                return result;
            }
            //
//            if (nowDay >= allowDay) {
//                result.put("isAbleToCancel", false);
//                result.put("msg", "门票已经过期! 不能退订!");
//                OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
//                                + orderDetail.getId() + "门票已经过期(含当天)! 不能退订!现在状态:"
//                                + orderDetail.getStatus().getDescription()
//                                + ",(#" + orderDetail.getRealOrderId() + ")",
//                        orderDetail.getOrder().getId(), orderDetail.getId());
//                orderLogService.loggingOrderLog(orderTicketLog1);
//                return result;
//            }
            // 可退订情况
            result = orderRefundService.doStartRefund(order, orderDetail, orderDetail.getFinalPrice());
            // 退款失败情况
            if (!(Boolean) result.get("isAbleToCancel")) {
                OrderLog orderLog = orderLogService.createOrderLog(
                        loginUser, "订单详情(G)#"
                                + orderDetail.getId() + "退订失败!退款失败" + "(" + result.get("msg") + ")",
                        orderDetail.getId(),
                        orderDetail.getOrder().getId());
                orderLogService.loggingOrderLog(orderLog);
                return result;
            }
            // 退款成功
            orderDetail.setApiResult(orderDetail.getApiResult() + "(已取消,可退:"
                    + orderDetail.getFinalPrice() + ")");
            // 设置订单详情的退款金额
            orderDetail.setRefund(orderDetail.getFinalPrice());
            orderDetail.setStatus(OrderDetailStatus.CANCELED);
            orderDetailService.update(orderDetail);
            result = orderService.handleTicketCancelResult(result, "提交退订成功!", orderDetail, true);
            OrderLog orderLog = orderLogService.createOrderLog(
                    loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "取消成功!可退:"
                            + orderDetail.getFinalPrice(),
                    orderDetail.getId(),
                    orderDetail.getOrder().getId());
            orderLogService.loggingOrderLog(orderLog);
            // @SMS 发送门票退订成功短信
            orderMsgService.doSendTicketCancelSuccessMsg(orderDetail, ticketPrice);
        } else {
            result = orderService.handleTicketCancelResult(result, "退订失败!供应商订单号为空!", orderDetail, false);
            OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "退订失败!供应商订单号为空!现在状态:"
                            + orderDetail.getStatus().getDescription()
                            + ",(#" + orderDetail.getRealOrderId() + ")",
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTicketLog1);
        }
        return result;
    }

    /**
     * 供应商酒店订单退订
     * @param order
     * @param orderDetail
     * @param loginUser
     * @return
     */
    private Map<String, Object> doCancelHotel(Order order, OrderDetail orderDetail, SysUser loginUser) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = orderLogService.getSysOrderLogUser();
        Long costId = orderDetail.getCostId();
        Long jszxOrderId = order.getJszxOrderId();
        JszxOrder jszxOrder = jszxOrderService.load(jszxOrderId);
        HotelPrice hotelPrice = hotelPriceService.findFullById(costId);
        OrderLog orderTicketLog0 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                        + orderDetail.getId() + "检查是否可退", order.getId(), orderDetail.getId());
        orderLogService.loggingOrderLog(orderTicketLog0);
        // 当前时间
        Date nowDate = new Date();
        // 最晚退订时间
        Calendar maxCancelCalendar = Calendar.getInstance();
        maxCancelCalendar.setTime(orderDetail.getLeaveDate());
        maxCancelCalendar.set(Calendar.HOUR_OF_DAY, 12);
        maxCancelCalendar.set(Calendar.MINUTE, 0);
        maxCancelCalendar.set(Calendar.SECOND, 0);
        Date maxDate = maxCancelCalendar.getTime();
        // 当前时间段不可以退, 超过入住当天12点
        if (nowDate.getTime() > maxDate.getTime()) {
            result.put("isAbleToCancel", false);
            result.put("msg", "当前时间段不可退! 超过入住当天12点! 不能退订!");
            OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "当前时间段不可退! 超过入住当天12点! 不能退订!"
                            + ",(#" + orderDetail.getRealOrderId() + ")",
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTicketLog1);
            return result;
        } else if (OrderDetailStatus.CHECKIN.equals(orderDetail.getStatus())) {
            result.put("isAbleToCancel", false);
            result.put("msg", "已办理入住! 不能退订!");
            OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "已办理入住! 不能退订!"
                            + ",(#" + orderDetail.getRealOrderId() + ")",
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTicketLog1);
            return result;
        } else if (OrderDetailStatus.CHECKOUT.equals(orderDetail.getStatus())) {
            result.put("isAbleToCancel", false);
            result.put("msg", "已办理退房! 不能退订!");
            OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "已办理退房! 不能退订!"
                            + ",(#" + orderDetail.getRealOrderId() + ")",
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTicketLog1);
            return result;
        }
        // 可退订情况
        result = orderRefundService.doStartRefund(order, orderDetail, orderDetail.getFinalPrice());
        // 退款失败情况
        if (!(Boolean) result.get("isAbleToCancel")) {
            OrderLog orderLog = orderLogService.createOrderLog(
                    loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "退订失败!退款失败" + "(" + result.get("msg") + ")",
                    orderDetail.getId(),
                    orderDetail.getOrder().getId());
            orderLogService.loggingOrderLog(orderLog);
            return result;
        }
        // 退款成功
        // 供应商余额流水记录
        balanceService.updateBalance(orderDetail.getFinalPrice().doubleValue(), AccountType.refund, jszxOrder.getUser().getId(), jszxOrder.getSupplierId(), user.getId(), jszxOrder.getOrderNo(), jszxOrder.getId());
        // 更新供应商订单状态
        jszxOrder.setStatus(JszxOrderStatus.CANCELED);
        jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
        OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                + "供应商订单(G)#" + jszxOrder.getId() + "已经退回相应的余额账户!"
                + "旅行帮订单现在状态: " + order.getStatus().getDescription()
                + "供应商订单现在状态: " + jszxOrder.getStatus().getDescription(), order.getId(), null);
        orderLogService.loggingOrderLog(orderLog3);
        orderDetail.setApiResult(orderDetail.getApiResult() + "(已取消,可退:"
                + orderDetail.getFinalPrice() + ")");
        // 设置订单详情的退款金额
        orderDetail.setRefund(orderDetail.getFinalPrice());
        orderDetail.setStatus(OrderDetailStatus.CANCELED);
        orderDetailService.update(orderDetail);
        result = orderService.handleTicketCancelResult(result, "提交退订成功!", orderDetail, true);
        OrderLog orderLog = orderLogService.createOrderLog(
                loginUser, "订单详情(G)#"
                        + orderDetail.getId() + "取消成功!可退:"
                        + orderDetail.getFinalPrice(),
                orderDetail.getId(),
                orderDetail.getOrder().getId());
        orderLogService.loggingOrderLog(orderLog);
        // @SMS 发送酒店退订成功短信
//        orderMsgService.doSendHotelCancelSuuccessMsg(orderDetail, hotelPrice);
        return result;
    }

    /**
     *
     * 供应商线路订单退订
     * @param order
     * @param orderDetail
     * @param loginUser
     * @return
     */
    private Map<String, Object> doCancelLine(Order order, OrderDetail orderDetail, SysUser loginUser) {
        Map<String, Object> result = new HashMap<String, Object>();
        String realOrderIdStr = orderDetail.getRealOrderId();
        Long costId = orderDetail.getCostId();
        Linetypeprice linetypeprice = linetypepriceService.findFullById(costId);
        Line line = linetypeprice.getLine();
        if (StringUtils.hasText(realOrderIdStr)) {
            Long readlOrderId = Long.parseLong(realOrderIdStr);
            JszxOrderDetail jszxOrderDetail = jszxOrderDetailService.load(readlOrderId);
            Date playDate = orderDetail.getPlayDate();
            Calendar playCalendar = Calendar.getInstance();
            Calendar nowCalendar = Calendar.getInstance();
            playCalendar.setTime(playDate);
            Integer allowDay = playCalendar.get(Calendar.DAY_OF_YEAR);
            Integer nowDay = nowCalendar.get(Calendar.DAY_OF_YEAR);
            OrderLog orderTicketLog0 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "检查是否可退, 现在状态"
                            + orderDetail.getStatus().getDescription()
                            + ",(#" + orderDetail.getRealOrderId() + ")",
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTicketLog0);
            // 简单线路退订样子, 无验票记录, 不能过期(含当天)
            if (jszxOrderDetail.getRestCount() > 0) {
                result.put("isAbleToCancel", false);
                result.put("msg", "线路有验票记录! 不能退订!");
                OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                                + orderDetail.getId() + "线路有验票记录! 不能退订!现在状态:"
                                + orderDetail.getStatus().getDescription()
                                + ",(#" + orderDetail.getRealOrderId() + ")",
                        orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTicketLog1);
                return result;
            }
            //
            if (nowDay >= allowDay) {
                result.put("isAbleToCancel", false);
                result.put("msg", "门票已经过期! 不能退订!");
                OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                                + orderDetail.getId() + "门票已经过期(含当天)! 不能退订!现在状态:"
                                + orderDetail.getStatus().getDescription()
                                + ",(#" + orderDetail.getRealOrderId() + ")",
                        orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTicketLog1);
                return result;
            }
            // 可退订情况 (线路退订需要供应商确认, 这里不马上退款)
            jszxOrderDetail.setUseStatus(JszxOrderDetailStatus.REFUNDING);
            jszxOrderDetailService.update(jszxOrderDetail);
            // 更新订单详情状态
            orderDetail.setStatus(OrderDetailStatus.CANCELING);
            orderDetailService.update(orderDetail);
            result = orderService.handleTicketCancelResult(result, "提交退订成功,等待确认!", orderDetail, true);
            OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "提交退订成功,等待供应商确认退订!现在状态:"
                            + orderDetail.getStatus().getDescription()
                            + ",(#" + orderDetail.getRealOrderId() + ")",
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTicketLog1);
        } else {
            result = orderService.handleTicketCancelResult(result, "退订失败!供应商订单号为空!", orderDetail, false);
            OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "退订失败!供应商订单号为空!现在状态:"
                            + orderDetail.getStatus().getDescription()
                            + ",(#" + orderDetail.getRealOrderId() + ")",
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTicketLog1);
        }
        return result;
    }

    /**
     * 供应商船票退订
     * @param order
     * @param orderDetail
     * @param loginUser
     * @return
     */
    public Map<String, Object> doCancelShip(Order order, OrderDetail orderDetail, SysUser loginUser) {
        Map<String, Object> result = new HashMap<String, Object>();
        String realOrderIdStr = orderDetail.getRealOrderId();
        Long jszxOrderId = order.getJszxOrderId();
        User user = orderLogService.getSysOrderLogUser();
        Long costId = orderDetail.getCostId();
        TrafficPrice trafficPrice = trafficPriceService.findFullById(costId);
        Traffic traffic = trafficPrice.getTraffic();
        if (StringUtils.hasText(realOrderIdStr) && jszxOrderId != null) {
            Long realOrderId = Long.parseLong(realOrderIdStr);
            JszxOrder jszxOrder = jszxOrderService.load(jszxOrderId);
            JszxOrderDetail jszxOrderDetail = jszxOrderDetailService.load(realOrderId);
            OrderLog orderTicketLog0 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "检查是否可退, 现在状态"
                            + orderDetail.getStatus().getDescription()
                            + ",(#" + orderDetail.getRealOrderId() + ")",
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTicketLog0);
            Integer resCount = jszxOrderDetail.getRestCount();
            // 退票验证, 要有剩余验票次数
            if (resCount <= 0) {
                result.put("isAbleToCancel", false);
                result.put("msg", "已全部验票, 无可用退票次数");
                OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                                + orderDetail.getId() + "船票全部使用! 不能退订!现在状态:"
                                + orderDetail.getStatus().getDescription()
                                + ",(#" + orderDetail.getRealOrderId() + ")",
                        orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTicketLog1);
                return result;
            }
            // 可退订情况
            result = orderRefundService.doStartRefund(order, orderDetail, resCount * orderDetail.getUnitPrice());
            // 退款失败情况
            if (!(Boolean) result.get("isAbleToCancel")) {
                OrderLog orderLog = orderLogService.createOrderLog(
                        loginUser, "订单详情(G)#" + orderDetail.getId()
                                + "退订失败!退款失败" + "(" + result.get("msg") + ")",
                        orderDetail.getId(),
                        orderDetail.getOrder().getId());
                orderLogService.loggingOrderLog(orderLog);
                return result;
            }
            // 退款成功
            // 供应商余额流水记录
            balanceService.updateBalance(orderDetail.getFinalPrice().doubleValue(), AccountType.refund, jszxOrder.getUser().getId(), jszxOrder.getSupplierId(), user.getId(), jszxOrder.getOrderNo(), jszxOrder.getId());
            // 更新供应商订单状态
            jszxOrder.setStatus(JszxOrderStatus.CANCELED);
            jszxOrderDetail.setUseStatus(JszxOrderDetailStatus.CANCEL);
            jszxOrderService.update(jszxOrder, jszxOrder.getUser(), jszxOrder.getCompanyUnit());
            jszxOrderDetailService.update(jszxOrderDetail);
            OrderLog orderLog3 = orderLogService.createOrderLog(user, "订单(G)#" + order.getId()
                    + "供应商订单(G)#" + jszxOrder.getId() + "已经退回相应的余额账户!"
                    + "旅行帮订单现在状态: " + order.getStatus().getDescription()
                    + "供应商订单现在状态: " + jszxOrder.getStatus().getDescription(), order.getId(), null);
            orderLogService.loggingOrderLog(orderLog3);
            orderDetail.setApiResult(orderDetail.getApiResult() + "(已取消,可退:"
                    + orderDetail.getFinalPrice() + ")");
            // 设置订单详情的退款金额
            orderDetail.setRefund(orderDetail.getFinalPrice());
            orderDetail.setStatus(OrderDetailStatus.CANCELED);
            orderDetailService.update(orderDetail);
            result = orderService.handleTicketCancelResult(result, "提交退订成功!", orderDetail, true);
            //@SMS 发送船票退订成功短信
            orderMsgService.doSendShipCancelSuccessMsg(order, traffic);
        } else {
            result = orderService.handleTicketCancelResult(result, "退订失败!供应商订单号为空!", orderDetail, false);
            OrderLog orderTicketLog1 = orderLogService.createOrderLog(loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "退订失败!供应商订单号为空!现在状态:"
                            + orderDetail.getStatus().getDescription()
                            + ",(#" + orderDetail.getRealOrderId() + ")",
                    orderDetail.getOrder().getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderTicketLog1);
        }
        return result;
    }

    /**
     * 保险退款
     * @param order
     * @param orderDetail
     * @param loginUser
     * @return
     */
    public Map<String, Object> doCancelInsurance(Order order, OrderDetail orderDetail, SysUser loginUser) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (OrderStatus.PAYED.equals(order.getStatus()) || OrderStatus.SUCCESS.equals(order.getStatus())
                || OrderStatus.PROCESSED.equals(order.getStatus())) {
            result = orderRefundService.doStartRefund(order, orderDetail, orderDetail.getFinalPrice());
            // 退款失败情况
            if (!(Boolean) result.get("isAbleToCancel")) {
                OrderLog orderLog = orderLogService.createOrderLog(
                        loginUser, "订单详情(G)#"
                                + orderDetail.getId() + "保险退订失败!退款失败" + "(" + result.get("msg") + ")",
                        orderDetail.getId(),
                        orderDetail.getOrder().getId());
                orderLogService.loggingOrderLog(orderLog);
                return result;
            }
            // 退款成功
            orderDetail.setApiResult(orderDetail.getApiResult() + "(已取消,可退:"
                    + orderDetail.getFinalPrice() + ")");
            // 设置订单详情的退款金额
            orderDetail.setRefund(orderDetail.getFinalPrice());
            orderDetail.setStatus(OrderDetailStatus.CANCELED);
            orderDetailService.update(orderDetail);
            result = orderService.handleTicketCancelResult(result, "提交退订成功!", orderDetail, true);
            OrderLog orderLog = orderLogService.createOrderLog(
                    loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "保险取消成功!可退:"
                            + orderDetail.getFinalPrice(),
                    orderDetail.getId(),
                    orderDetail.getOrder().getId());
            orderLogService.loggingOrderLog(orderLog);
        } else {
            // 未支付的订单, 保险也是未支付, 无需退款流程
            orderDetail.setApiResult(orderDetail.getApiResult() + "(已取消, 订单未支付, 无需退款)");
            // 设置订单详情的退款金额
            orderDetail.setRefund(orderDetail.getFinalPrice());
            orderDetail.setStatus(OrderDetailStatus.CANCELED);
            orderDetailService.update(orderDetail);
            result = orderService.handleTicketCancelResult(result, "提交退订成功!", orderDetail, true);
            OrderLog orderLog = orderLogService.createOrderLog(
                    loginUser, "订单详情(G)#"
                            + orderDetail.getId() + "保险取消成功! 订单未支付, 无需退款!",
                    orderDetail.getId(),
                    orderDetail.getOrder().getId());
            orderLogService.loggingOrderLog(orderLog);
        }
        return result;
    }

    /**
     * 退订成功短信预发送
     * @param orderDetail
     */
    private void doPreSendCancelSuccessMsg(OrderDetail orderDetail) {
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
    }
}
