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
import com.data.hmly.service.translation.train.juhe.JuheTrainService;
import com.data.hmly.service.translation.train.juhe.entity.CheckOrderResult;
import com.framework.hibernate.util.Page;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/3/29.
 */
@Component
public class TrainTicketRefundQuzrtz {

    @Resource
    private PropertiesManager propertiesManager;

    @Resource
    private OrderRefundService orderRefundService;
    @Resource
    private OrderDispatchService orderDispatchService;
    @Resource
    private UserService userService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderMsgService orderMsgService;
    @Resource
    private TrafficPriceService trafficPriceService;

    public void doRefundTrainTicket() {
        String trainKey = propertiesManager.getString("JUHE_TRAIN_KEY");
        User user = userService.get(9L);
        List<OrderDetail> orderDetailList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        OrderDetail condition = new OrderDetail();
        Map<String, Object> result = new HashMap<String, Object>();
        // 取出取消中的火车票订单
        condition.setStatus(OrderDetailStatus.CANCELING);
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
                TrafficPrice trafficPrice = trafficPriceService.findFullById(orderDetail.getCostId());
                OrderLog orderTrainLog1 = orderLogService.createOrderLog(user, "订单详情#"
                        + orderDetail.getId() + "检查退票状态,现在状态:"
                        + orderDetail.getStatus().getDescription()
                        + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId(),
                        OrderLogLevel.debug);
                orderLogService.loggingOrderLog(orderTrainLog1);
                result.put("orderId", orderDetail.getRealOrderId());
                result.put("type", ProductType.train);
                if (OrderDetailStatus.CANCELING.equals(orderDetail.getStatus())) {
                    String realOrderId = orderDetail.getRealOrderId();
                    CheckOrderResult checkOrderResult = JuheTrainService.checkOrder(trainKey, realOrderId);
                    if (checkOrderResult.getError_code() == 0 && checkOrderResult.getResult() != null) {
                        if ("7".equals(checkOrderResult.getResult().getStatus())) {
                            OrderLog orderTrainLog2 = orderLogService.createOrderLog(user, "订单详情#"
                                    + orderDetail.getId() + "," + checkOrderResult.getResult().getMsg()
                                    + ",退款金额: " + checkOrderResult.getResult().getRefund_money()
                                    + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(orderTrainLog2);
                            result.put("status", OrderDetailStatus.CANCELED);
                            result.put("apiResult", checkOrderResult.getResult().getMsg());
                            // 用户账户余额更新 (已变更退款方式 2016-05-26)
                            User refundUser = userService.get(orderDetail.getOrder().getUser().getId());
//                            refundUser.setBalance(refundUser.getBalance() + Double.parseDouble(checkOrderResult.getResult().getRefund_money()));
//                            userService.update(refundUser);
                            // @ auto refund (根据付款方式, 退回原支付账户)
                            orderRefundService.doStartRefund(orderDetail.getOrder(), orderDetail, Float.parseFloat(checkOrderResult.getResult().getRefund_money()));
                            orderDetail.setRefund(Float.parseFloat(checkOrderResult.getResult().getRefund_money()));
                            OrderLog orderTrainLog3 = orderLogService.createOrderLog(user, "订单详情#" + orderDetail.getId()
                                    + ",已向用户# " + refundUser.getId() + "退款, 金额 :" + checkOrderResult.getResult().getRefund_money()
                                    + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId());
                            orderLogService.loggingOrderLog(orderTrainLog3);
                            // @SMS 发送火车票取消成功短信
                            orderMsgService.doSendTrainCancelSuccessMsg(orderDetail, trafficPrice);
                        } else {
                            OrderLog orderTrainLog4 = orderLogService.createOrderLog(user, "订单详情#"
                                    + orderDetail.getId() + "检查车票状态:"
                                    + checkOrderResult.getResult().getMsg()
                                    + "(#" + orderDetail.getRealOrderId() + ")", orderDetail.getOrder().getId(), orderDetail.getId(), OrderLogLevel.debug);
                            orderLogService.loggingOrderLog(orderTrainLog4);
                            result.put("status", orderDetail.getStatus());
                            result.put("apiResult", checkOrderResult.getResult().getMsg());
                        }
                    } else {
                        result.put("status", orderDetail.getStatus());
                        result.put("apiResult", checkOrderResult.getReason());
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
