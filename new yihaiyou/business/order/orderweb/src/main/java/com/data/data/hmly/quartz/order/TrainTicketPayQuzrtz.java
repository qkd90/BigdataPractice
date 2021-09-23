package com.data.data.hmly.quartz.order;

import com.data.data.hmly.service.SendingMsgService;
import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.SendStatus;
import com.data.data.hmly.service.entity.SendingMsg;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderDispatchService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderMsgService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.OrderTouristService;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.hmly.service.translation.train.juhe.JuheTrainService;
import com.data.hmly.service.translation.train.juhe.entity.CheckOrderResult;
import com.data.hmly.service.translation.train.juhe.entity.PaymentResult;
import com.framework.hibernate.util.Page;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/3/25.
 */
@Component
public class TrainTicketPayQuzrtz {

    @Resource
    private PropertiesManager propertiesManager;

    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderTouristService orderTouristService;
    @Resource
    private OrderDispatchService orderDispatchService;
    @Resource
    private SendingMsgService sendingMsgService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderMsgService orderMsgService;
    @Resource
    private UserService userService;
    @Resource
    private TrafficPriceService trafficPriceService;

    public void doPayTrainTicket() {
//        SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
//        boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
        User user = orderLogService.getSysOrderLogUser();
        String trainKey = propertiesManager.getString("JUHE_TRAIN_KEY");
        List<OrderDetail> orderDetailList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        OrderDetail condition = new OrderDetail();
        Map<String, Object> result = new HashMap<String, Object>();
        // 取出预定中或已支付的火车票订单
        List<OrderDetailStatus> neededStatus = new ArrayList<OrderDetailStatus>();
        neededStatus.add(OrderDetailStatus.BOOKING);
        neededStatus.add(OrderDetailStatus.PAYED);
        condition.setNeededStatuses(neededStatus);
        condition.setProductType(ProductType.train);
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
                OrderLog orderTrainLog1 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "检查是否可付款,现在状态:"
                        + orderDetail.getStatus().getDescription()
                        + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderTrainLog1);
                result.put("orderId", orderDetail.getRealOrderId());
                result.put("type", ProductType.train);
                if (OrderDetailStatus.BOOKING.equals(orderDetail.getStatus())
                        || OrderDetailStatus.PAYED.equals(orderDetail.getStatus())) {
                    String realOrderId = orderDetail.getRealOrderId();
                    CheckOrderResult checkOrderResult = JuheTrainService.checkOrder(trainKey, realOrderId);
                    if (checkOrderResult.getResult() != null) {
                        // 订单状态信息
                        result.put("apiResult", checkOrderResult.getResult().getMsg());
                        OrderLog orderTrainLog2 = orderLogService.createOrderLog(user, "订单详情#"
                                + orderDetail.getId() + "已获取接口订单状态:"
                                + checkOrderResult.getResult().getMsg()
                                + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                        orderLogService.loggingOrderLog(orderTrainLog2);
                        if ("2".equals(checkOrderResult.getResult().getStatus())
                                && !OrderDetailStatus.PAYED.equals(orderDetail.getStatus())) {
                            PaymentResult paymentResult = JuheTrainService.pay(trainKey, realOrderId);
                            checkOrderResult = JuheTrainService.checkOrder(trainKey, realOrderId);
                            if ("0".equals(paymentResult.getError_code())) {
                                OrderLog orderTrainLog3 = orderLogService.createOrderLog(user, "订单详情#"
                                        + orderDetail.getId() + "接口支付成功, 已获取接口订单状态:"
                                        + checkOrderResult.getResult().getMsg()
                                        + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                        + ",(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                                orderLogService.loggingOrderLog(orderTrainLog3);
                                result.put("status", OrderDetailStatus.PAYED);
                                // 发送确认收到订单短信
                                prepareSendMsg(orderDetail, checkOrderResult);
                            } else {
                                OrderLog orderTrainLog3 = orderLogService.createOrderLog(user, "订单详情#"
                                        + orderDetail.getId() + "接口支付失败, 已获取接口订单状态:"
                                        + checkOrderResult.getResult().getMsg()
                                        + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                        + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                                orderLogService.loggingOrderLog(orderTrainLog3);
                                result.put("status", OrderDetailStatus.FAILED);
                            }
                            result.put("apiResult", checkOrderResult.getResult().getMsg());
                        } else if ("0".equals(checkOrderResult.getResult().getStatus())) {
                            result.put("status", OrderDetailStatus.BOOKING);
                        } else if ("1".equals(checkOrderResult.getResult().getStatus())) {
                            result.put("status", OrderDetailStatus.FAILED);
                            //发送订单失败短信
                            prepareSendMsg(orderDetail, checkOrderResult);
                        } else if ("3".equals(checkOrderResult.getResult().getStatus())) {
                            result.put("status", OrderDetailStatus.PAYED);
                        } else if ("4".equals(checkOrderResult.getResult().getStatus())) {
                            result.put("apiResult", checkOrderResult.getResult().getMsg());
                            result.put("status", OrderDetailStatus.SUCCESS);
                            OrderLog orderTrainLog3 = orderLogService.createOrderLog(user, "订单详情#"
                                    + orderDetail.getId() + "出票成功, 已获取接口订单状态:"
                                    + checkOrderResult.getResult().getMsg()
                                    + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                    + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(orderTrainLog3);
                            // 短信发送前, 先更新订单状态,
                            orderDispatchService.updateOrderStatus(orderDetail, result);
                            // 发送成功消息
                            prepareSendMsg(orderDetail, checkOrderResult);
                        } else if ("5".equals(checkOrderResult.getResult().getStatus())) {
                            result.put("status", OrderDetailStatus.FAILED);
                            OrderLog orderTrainLog4 = orderLogService.createOrderLog(user, "订单详情#"
                                    + orderDetail.getId() + "出票失败, 已获取接口订单状态:"
                                    + checkOrderResult.getResult().getMsg()
                                    + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                    + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(orderTrainLog4);
                            //发送出票失败短信
                            prepareSendMsg(orderDetail, checkOrderResult);
                        } else if ("6".equals(checkOrderResult.getResult().getStatus())) {
                            result.put("status", OrderDetailStatus.CANCELING);
                        } else if ("7".equals(checkOrderResult.getResult().getStatus())) {
                            result.put("status", OrderDetailStatus.CANCELED);
                        } else if ("8".equals(checkOrderResult.getResult().getStatus())) {
                            result.put("status", OrderDetailStatus.CANCELED);
                        }
                    } else {
                        OrderLog orderTrainLog5 = orderLogService.createOrderLog(user, "订单详情#"
                                + orderDetail.getId() + "暂时没有查到订单信息,稍后查看"
                                + ",订单详情状态: " + orderDetail.getStatus().getDescription()
                                + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                        orderLogService.loggingOrderLog(orderTrainLog5);
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
//        ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
    }

    /**
     * 火车票预订成功/失败/其他...短信通知(准备数据)
     * @param orderDetail
     * @param checkOrderResult
     */
    private void prepareSendMsg(OrderDetail orderDetail, CheckOrderResult checkOrderResult) {
        TrafficPrice trafficPrice = trafficPriceService.findFullById(orderDetail.getCostId());
        String apiStatus = checkOrderResult.getResult().getStatus();
        if ("4".equals(apiStatus)) {
            List<CheckOrderResult.ResultEntity.PassengersEntity> passengers = checkOrderResult.getResult().getPassengers();
            List<OrderTourist> orderTouristList = orderTouristService.getByOrderDetailId(orderDetail.getId());
            Map<String, Object> touristMap = orderDetailService.orderTouristToMap(orderTouristList);
//            for (CheckOrderResult.ResultEntity.PassengersEntity passengersEntity : passengers) {
            // @SMS 发送火车票预订成功短信
            orderMsgService.doSendTrainBookingSuccessMsg(orderDetail, trafficPrice, checkOrderResult);
//            }
        } else if ("2".equals(apiStatus)) {
            // 取消该状态短信发送
            // ......
        } else if ("5".equals(apiStatus) || "1".equals(apiStatus)) {
            // @SMS 发送火车票预订失败短信
            orderMsgService.doSendTrainBookingFailMsg(orderDetail, trafficPrice);
        }
    }


    private String getTrainStartDate(String dateTime) {
        String time = "";
        try {
            Date date = DateUtils.parse(dateTime, "yyyy-MM-dd HH:mm:ss");
            return DateUtils.format(date, "MM月dd日");
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

    private String getTrainStartTime(String dateTime) {
        String time = "";
        try {
            Date date = DateUtils.parse(dateTime, "yyyy-MM-dd HH:mm:ss");
            return DateUtils.format(date, "HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
    }
}
