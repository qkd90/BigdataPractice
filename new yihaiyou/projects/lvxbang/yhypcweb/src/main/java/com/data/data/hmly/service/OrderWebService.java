package com.data.data.hmly.service;

import com.data.data.hmly.action.yhypc.request.OrderUpdateRequest;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.YhyMsgService;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.OrderValidateCodeService;
import com.data.data.hmly.service.order.YhyOrderService;
import com.data.data.hmly.service.order.dao.OrderDao;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.entity.enums.OrderWay;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.order.util.MsgTemplateKey;
import com.data.data.hmly.service.order.vo.OrderResult;
import com.data.data.hmly.util.GenOrderNo;
import com.google.common.collect.Maps;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2017-02-06,0006.
 */
@Service
public class OrderWebService {
    @Resource
    private OrderService orderService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private MemberService memberService;
    @Resource
    private BalanceService balanceService;
    @Resource
    private YhyOrderService yhyOrderService;
    @Resource
    private OrderValidateCodeService orderValidateCodeService;
    @Resource
    private YhyMsgService yhyMsgService;
    @Resource
    private SysUnitService sysUnitService;

    public Order saveOrder(OrderUpdateRequest orderUpdateRequest, Member user, FerryOrder ferryOrder) throws Exception {
        Order order = null;
        if (orderUpdateRequest.getId() != null) {
            order = orderService.get(orderUpdateRequest.getId());
        }
        if (order == null) {
            order = new Order();
            order.setOrderNo(GenOrderNo.generate(propertiesManager.getString("MACHINE_NO", "")));
            order.setCreateTime(new Date());
            order.setOrderWay(OrderWay.web);
        }
        order.setUser(user);
        order.setRecName(orderUpdateRequest.getContact().getName());
        order.setMobile(orderUpdateRequest.getContact().getTelephone());
        order.setModifyTime(new Date());
        order.setHasComment(false);
        order.setPlayDate(DateUtils.parseShortTime(orderUpdateRequest.getPlayDate()));
        order.setName(orderUpdateRequest.getName());
        order.setStatus(OrderStatus.PROCESSING);
        order.setOrderType(OrderType.valueOf(orderUpdateRequest.getOrderType()));
        if (order.getOrderType() == null) {
            order.setDeleteFlag(true);
            return order;
        }
        if (OrderType.plan.equals(order.getOrderType())) {
            order.setDay(orderUpdateRequest.getDay());
        }
        orderDao.save(order);
        List<OrderDetail> orderDetailList = orderDetailService.createLxbOrderDetails(orderUpdateRequest.getDetails(), order, true);
        order.setOrderDetails(orderDetailList);
        for (OrderDetail orderDetail : orderDetailList) {
            if (orderDetail.getStatus().equals(OrderDetailStatus.UNCONFIRMED) && order.getStatus().equals(OrderStatus.PROCESSING)) {
                order.setStatus(OrderStatus.UNCONFIRMED);
                break;
            }
        }
        if (OrderType.hotel.equals(order.getOrderType()) && ProductSource.ELONG.equals(orderDetailList.get(0).getProduct().getSource())) {
            order.setStatus(OrderStatus.PAYED);
        }
        if (order.getStatus().equals(OrderStatus.PROCESSING)) {
            orderService.orderWaitTime(order);
        }
        orderInsurance(order);
        orderDao.save(order);
        if (ferryOrder != null) {
            order.setPrice(order.getPrice() + ferryOrder.getAmount());
            orderService.update(order);
            ferryOrder.setOrderId(order.getId());
            ferryOrderService.updateOrder(ferryOrder);
        }
        if (OrderType.plan.equals(order.getOrderType()) && order.getPrice() == 0f) {
            order.setStatus(OrderStatus.INVALID);
            order.setDeleteFlag(true);
        }
        orderService.update(order);
        if (!order.getDeleteFlag()) {
            orderService.updateOrderDetailInventory(order);
        }
        for (OrderDetail orderDetail : orderDetailList) {
            // 短信发送
            if (OrderType.yacht.equals(order.getOrderType()) || OrderType.sailboat.equals(order.getOrderType()) || OrderType.huanguyou.equals(order.getOrderType())) {
                Map<String, Object> msgData = new HashMap<String, Object>();
                // 待确认的
                if (OrderStatus.UNCONFIRMED.equals(order.getStatus())) {
                    // @WEB_SMS
                    // 用户提示待确认短信
                    yhyMsgService.doSendSMS(msgData, order.getMobile(), MsgTemplateKey.WEB_TICKET_WAIT_CFM_TLE);
                    // @WEB_SMS
                    // 商户提示待确认短信
                    SysUnit sysUnit = sysUnitService.findUnitById(orderDetail.getProduct().getCompanyUnit().getId());
                    yhyMsgService.doSendSMS(msgData, sysUnit.getSysUnitDetail().getMobile(), MsgTemplateKey.MERCHANT_TICKET_WAIT_CFM_TLE);
                } else if (OrderStatus.WAIT.equals(order.getStatus())) {
                    // 等待支付
                    Integer timeout = 0;
                    switch (order.getOrderType()) {
                        case yacht:
                            timeout = propertiesManager.getInteger("ORDER_TICKET_PAY_WAIT_TIME");
                            break;
                        case sailboat:
                            timeout = propertiesManager.getInteger("ORDER_SAILBOAT_PAY_WAIT_TIME");
                            break;
                        case huanguyou:
                            timeout = propertiesManager.getInteger("ORDER_TICKET_PAY_WAIT_TIME");
                            break;
                        default:
                            timeout = 30;
                            break;
                    }
                    // 游艇/帆船/鹭岛游 短信发送
                    msgData.put("timeout", timeout);
                    msgData.put("orderNo", order.getOrderNo());
                    // @WEB_SMS
                    yhyMsgService.doSendSMS(msgData, order.getMobile(), MsgTemplateKey.WEB_TICKET_WAIT_PAY_TLE);
                }
            }
        }
        return order;
    }

    public void orderInsurance(Order order) {
        Float price = 0F;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            price += orderService.calPrice(orderDetail);
        }
        order.setPrice(price);
    }

    public void saveBalancePay(Order order, Member user) {
        payedOrderWaitTime(order);
        orderService.update(order);
        orderService.updateOrderBill(order);
        user.setBalance(user.getBalance() - order.getPrice());
        memberService.update(user);
        balanceService.savePayResult(order, AccountType.consume);
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
                    // 用户提示成功短信
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
        orderService.update(order);
    }

    public void payedOrderWaitTime(Order order) {
        order.setStatus(OrderStatus.PAYED);
        order.setPayType(OrderPayType.ONLINE);
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
    }

    public Order saveBalanceOrder(OrderUpdateRequest orderUpdateRequest, Member user) {
        Order order = null;
        if (orderUpdateRequest.getId() != null) {
            order = orderService.get(orderUpdateRequest.getId());
        }
        if (order == null) {
            order = new Order();
            order.setOrderNo(GenOrderNo.generate(propertiesManager.getString("MACHINE_NO", "")));
            order.setCreateTime(new Date());
        }
        order.setUser(user);
        AccountType accountType = null;
        if (OrderType.recharge.name().equals(orderUpdateRequest.getOrderType())) {
            order.setOrderType(OrderType.recharge);
            order.setName("充值");
            accountType = AccountType.recharge;
        } else if (OrderType.withdraw.name().equals(orderUpdateRequest.getOrderType())) {
            order.setOrderType(OrderType.withdraw);
            order.setName("提现");
            accountType = AccountType.withdraw;
        }
        order.setStatus(OrderStatus.WAIT);
        Map<String, Object> detail = orderUpdateRequest.getDetails().get(0);
        order.setPrice(Float.valueOf(detail.get("price").toString()));
        Integer minute = Integer.valueOf(propertiesManager.getString("ORDER_BALANCE_PAY_WAIT_TIME"));
        order.setWaitTime(DateUtils.add(new Date(), Calendar.MINUTE, minute));
        order.setOrderWay(OrderWay.web);
        orderDao.save(order);
        balanceService.savePayResult(order, accountType);
        return order;
    }
}
