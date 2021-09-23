package com.data.data.hmly.service.pay;

import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.YhyMsgService;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.OrderValidateCodeService;
import com.data.data.hmly.service.order.YhyOrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.LvjiOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.order.util.MsgTemplateKey;
import com.data.data.hmly.service.order.vo.OrderResult;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.google.common.collect.Maps;
import com.gson.bean.RefundResult;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PayService {

    private OrderService orderService = SpringContextHolder.getBean("orderService");
    private BalanceService balanceService = SpringContextHolder.getBean("balanceService");
    private YhyOrderService yhyOrderService = SpringContextHolder.getBean("yhyOrderService");
    private OrderDetailService orderDetailService = SpringContextHolder.getBean("orderDetailService");
    private OrderValidateCodeService orderValidateCodeService = SpringContextHolder.getBean("orderValidateCodeService");
    private FerryOrderService ferryOrderService = SpringContextHolder.getBean("ferryOrderService");
    private PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
    private YhyMsgService yhyMsgService = SpringContextHolder.getBean("yhyMsgService");

    private final Log log = LogFactory.getLog(this.getClass());

    public abstract PayRequest doMakePayRequest(Order order);

    public PayRequest doMakePayRequest(FerryOrder order) {
        return null;
    }

    public PayRequest doMakePayRequest(LvjiOrder order) {return null;}

    public Map<String, String> doMakePayRequestMap(Order order) {
        return null;
    }

    public abstract PayBackObject doMakeBackObject(Map<String, String> params, Order order);

    public abstract PayBackObject doMakeBackObject(Map<String, String> params, FerryOrder order);

    // 已经成功回调后再次回调
    public abstract PayBackObject doMakeBackObject();

    public Map doMakePayRequest(Order order, WechatAccount wechatAccount, String openId, String ip, Boolean noCredit) {
        return null;
    }

    public Map doMakePayRequest(FerryOrder order, WechatAccount wechatAccount, String openId, String ip) {
        return null;
    }
    public Map doMakePayRequest(LvjiOrder order, WechatAccount wechatAccount, String openId, String ip) {
        return null;
    }

    public Map doMakeAppPayRequest(Order order, WechatAccount wechatAccount, String openId, String ip) {
        return null;
    }

    public Map doMakeAppPayRequest(FerryOrder order, WechatAccount wechatAccount, String openId, String ip) {
        return null;
    }

    public Map doTransfersRequest(Order order, WechatAccount wechatAccount, String openId, String ip, String filePath) {
        return null;
    }

    public abstract void doPayRequest();

    public abstract PaySearchResult doPaySearch();

    public boolean hasBusinessDone(Order order) {
        return order.getStatus() != OrderStatus.WAIT;
    }

    public void doBusiness(Order order) {
        //TODO ORDER BUSINESS
        log.warn("order=" + order.getId() + "done!");
        if (OrderType.recharge.equals(order.getOrderType())) {
            order.setStatus(OrderStatus.SUCCESS);
            balanceService.savePayResult(order, AccountType.recharge);
        } else {
            order.setStatus(OrderStatus.PAYED);
            Integer minute = null;
            switch (order.getOrderType()) {
                case hotel:
                    minute = Integer.valueOf(propertiesManager.getString("ORDER_PAYED_HOTEL_PAY_WAIT_TIME"));
                    break;
                case plan:
                    minute = Integer.valueOf(propertiesManager.getString("ORDER_PAYED_PLAN_PAY_WAIT_TIME"));
                    break;
            }
            if (minute != null) {
                order.setWaitTime(DateUtils.add(new Date(), Calendar.MINUTE, minute));
            }
            orderService.update(order);
            orderService.updateOrderBill(order);
            OrderResult result = yhyOrderService.doTakeOrder(order.getId());
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                OrderDetail detail = orderDetailService.get(orderDetail.getId());
                if (detail.getStatus().equals(OrderDetailStatus.SUCCESS) && (detail.getProduct().getSource() == null || ProductSource.LXB.equals(detail.getProduct().getSource()))) {
                    List<Map<String, Object>> resultList = orderValidateCodeService.doCreateValidateCode(order, detail);
                    if (OrderType.yacht.equals(order.getOrderType()) || OrderType.sailboat.equals(order.getOrderType()) || OrderType.huanguyou.equals(order.getOrderType())) {
                        // 游艇/帆船/鹭岛游 短信发送
                        Map<String, Object> msgData = new HashMap<String, Object>();
                        String code = "";
                        for (Map<String, Object> retMap : resultList) {
                            code += "," + retMap.get("code");
                        }
                        msgData.put("code", code.substring(1));
                        msgData.put("proName", orderDetail.getProduct().getName());
                        // @WEB_SMS
                        yhyMsgService.doSendSMS(msgData, order.getMobile(), MsgTemplateKey.WEB_TICKET_SUCCESS_TLE);
                        // @WEB_SMS
                        // 商家提示成功短信
                        yhyMsgService.doSendSMS(msgData, orderDetail.getProduct().getCompanyUnit().getSysUnitDetail().getMobile(), MsgTemplateKey.MERCHANT_TICKET_SUCCESS_TLE);
                    }
                }
            }
            List<FerryOrder> ferryOrders = ferryOrderService.getByOrderId(order.getId());
            if (!ferryOrders.isEmpty()) {
                FerryOrder ferryOrder = ferryOrders.get(0);
                Map<String, Object> payResult = FerryUtil.payNotify(ferryOrder.getFerryNumber(), true);
                if (!(Boolean) payResult.get("success")) {
                    if (result.getSuccess()) {
                        order.setStatus(OrderStatus.PARTIAL_FAILED);
                    }
                    ferryOrder.setStatus(OrderStatus.FAILED);
                } else {
                    ferryOrder.setStatus(OrderStatus.SUCCESS);
                    Map<String, Object> data = Maps.newHashMap();
                    data.put("lineName", ferryOrder.getFlightLineName());
                    // @WEB_MSG
                    yhyMsgService.doSendSMS(data, ferryOrder.getUser().getFerryMember().getMobile(), MsgTemplateKey.WEB_FERRY_BOOKING_SUCCESS_TLE);
                }
                ferryOrderService.updateOrder(ferryOrder);
            }
        }
        orderService.processHasComment(order);
        orderService.update(order);
    }

    public RefundResult refundOrder(String orderNo, String refundNo, Float totalPrice, Float refundPrice){
        return null;
    }

    public void doBusiness(FerryOrder order) {
        //TODO ORDER BUSINESS
        log.warn("ferryOrder=" + order.getId() + "done!");
        Map<String, Object> result = FerryUtil.payNotify(order.getFerryNumber(), true);
        if (!(Boolean) result.get("success")) {
            log.warn(result);
            order.setStatus(OrderStatus.FAILED);
            ferryOrderService.updateOrder(order);
            return;
        }
        order.setStatus(OrderStatus.SUCCESS);
        ferryOrderService.updateOrderBill(order);
        Map<String, Object> data = Maps.newHashMap();
        data.put("lineName", order.getFlightLineName());
        // @WEB_MSG
        yhyMsgService.doSendSMS(data, order.getUser().getFerryMember().getMobile(), MsgTemplateKey.WEB_FERRY_BOOKING_SUCCESS_TLE);
    }


}
