package com.data.data.hmly.service;

import com.data.data.hmly.service.balance.AccountLogService;
import com.data.data.hmly.service.balance.entity.AccountLog;
import com.data.data.hmly.service.balance.entity.enums.AccountStatus;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.ShenzhouOrderService;
import com.data.data.hmly.service.order.entity.ShenzhouOrder;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.entity.enums.ShenzhouOrderPaymentStatus;
import com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.zuipin.util.HttpUtils;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-09-08,0008.
 */
@Service
public class OrderShenzhouService {
    @Resource
    private ShenzhouOrderService shenzhouOrderService;
    @Resource
    private OrderShenzhouService orderShenzhouService;
    @Resource
    private MemberService memberService;
    @Resource
    private AccountLogService accountLogService;

    public ShenzhouOrder saveOrder(Map<String, Object> map, Member user, Long orderId, String cancelReason, Double cancelCost) {
        if (orderId == null || orderId < 0) {
            return null;
        }
        ShenzhouOrder shenzhouOrder = shenzhouOrderService.get(orderId);
        ShenzhouOrderStatus status1 = shenzhouOrder.getStatus();
        if (ShenzhouOrderStatus.completed.equals(status1)) {
            return shenzhouOrder;
        }

        Map<String, Object> order = (Map<String, Object>) map.get("order");
        ShenzhouOrderStatus status2 = ShenzhouOrderStatus.valueOf(order.get("status").toString());
        if (status1 != null && status1.getLevel() >= status2.getLevel()) {
            return shenzhouOrder;
        }
        shenzhouOrder.setShenzhouOrderId(order.get("id").toString());
        shenzhouOrder.setOrderNo(order.get("number").toString());
        shenzhouOrder.setServiceId(Integer.valueOf(order.get("serviceId").toString()));
        shenzhouOrder.setCarGroupId(Integer.valueOf(order.get("carGroupId").toString()));
        shenzhouOrder.setStatus(status2);
        if (order.get("paymentStatus") == null || StringUtils.isBlank(order.get("paymentStatus").toString())) {
            shenzhouOrder.setPaymentStatus((ShenzhouOrderPaymentStatus.unpaid));
        } else {
            shenzhouOrder.setPaymentStatus(ShenzhouOrderPaymentStatus.valueOf(order.get("paymentStatus").toString()));
        }
        shenzhouOrder.setStartLat(Double.valueOf(order.get("slat").toString()));
        shenzhouOrder.setStartLng(Double.valueOf(order.get("slng").toString()));
        shenzhouOrder.setStartName(order.get("startName").toString());
        shenzhouOrder.setEndLat(Double.valueOf(order.get("elat").toString()));
        shenzhouOrder.setEndLng(Double.valueOf(order.get("elng").toString()));
        shenzhouOrder.setEndName(order.get("endName").toString());
        shenzhouOrder.setPassengerMobile(order.get("passengerMobile").toString());
        shenzhouOrder.setPassengerName(order.get("passengerName").toString());
        shenzhouOrder.setCreateTime(new Date((Long.valueOf(order.get("createTime").toString())) * 1000));
        shenzhouOrder.setDepartureTime(new Date((Long.valueOf(order.get("departureTime").toString())) * 1000));
        if (order.get("dispatchedTime") != null && Long.valueOf(order.get("dispatchedTime").toString()) > 0) {
            shenzhouOrder.setDispatchedTime(new Date(Long.valueOf(order.get("dispatchedTime").toString()) * 1000));
        }
        if (order.get("finishedTime") != null && Long.valueOf(order.get("finishedTime").toString()) > 0) {
            shenzhouOrder.setFinishedTime(new Date(Long.valueOf(order.get("finishedTime").toString()) * 1000));
        }

        Map<String, Object> price = (Map<String, Object>) map.get("price");
        shenzhouOrder.setTotalPrice(Float.valueOf(price.get("totalPrice").toString()));
        shenzhouOrder.setArrearsPrice(Float.valueOf(price.get("arrearsPrice").toString()));
        shenzhouOrder.setDistance(Float.valueOf(price.get("distance").toString()));
        shenzhouOrder.setDuration(Float.valueOf(price.get("duration").toString()));
        List<Map<String, Object>> detail = (List<Map<String, Object>>) price.get("detail");
        for (Map<String, Object> detailMap : detail) {
            String name = detailMap.get("name").toString();
            Float amount = Float.valueOf(detailMap.get("amount").toString());
            if (name.contains("起租价")) {
                shenzhouOrder.setStartPrice(amount);
            } else if (name.contains("里程费")) {
                shenzhouOrder.setKilometrePrice(amount);
            } else if (name.contains("时长费")) {
                shenzhouOrder.setTimePrice(amount);
            } else if (name.contains("抹零")) {
                shenzhouOrder.setCountChange(amount);
            } else if (name.contains("远途费")) {
                shenzhouOrder.setLongDistancePrice(amount);
            } else if (name.contains("套餐价")) {
                shenzhouOrder.setStartPrice(amount);
            }
        }

        Map<String, Object> driver = (Map<String, Object>) map.get("driver");
        shenzhouOrder.setDriverName(driver.get("driverName").toString());
        shenzhouOrder.setVehicleNo(driver.get("vehicleNo").toString());
        shenzhouOrder.setVirtualPhone4Purchaser(driver.get("virtualPhone4Purchaser").toString());
        shenzhouOrder.setVirtualPhone4Passenger(driver.get("virtualPhone4Passenger").toString());
        shenzhouOrder.setVehicleModel(driver.get("vehicleModel").toString());
        shenzhouOrder.setDriverScore(driver.get("driverScore").toString());

        if (StringUtils.isNotBlank(cancelReason)) {
            shenzhouOrder.setCancelReson(cancelReason);
        }
        shenzhouOrder.setUser(user);
        if (status2.equals(ShenzhouOrderStatus.completed) && (shenzhouOrder.getScore() == null || shenzhouOrder.getScore() < 1)) {
            shenzhouOrder.setScore(5);
        }

        Float frozenPrice = shenzhouOrder.getFrozenPrice();
        if (frozenPrice == null) {
            frozenPrice = shenzhouOrder.getTotalPrice() * 2;
            shenzhouOrder.setFrozenPrice(frozenPrice);
        }
        if (cancelCost != null) {
            shenzhouOrder.setCancelCost(cancelCost.floatValue());
        }
        shenzhouOrderService.saveOrUpdate(shenzhouOrder);

        if (status1 == null) {
            orderShenzhouService.updateBalance(user.getId(), frozenPrice, 0d, true, shenzhouOrder);
        } else if (!status1.equals(ShenzhouOrderStatus.canceled) && status2.equals(ShenzhouOrderStatus.canceled)) {
            orderShenzhouService.updateBalance(user.getId(), frozenPrice, cancelCost, false, shenzhouOrder);
        } else if (!status1.equals(ShenzhouOrderStatus.paid) && (status2.equals(ShenzhouOrderStatus.paid) || status2.equals(ShenzhouOrderStatus.completed))) {
            orderShenzhouService.updateBalance(user.getId(), frozenPrice, shenzhouOrder.getTotalPrice().doubleValue(), false, shenzhouOrder);
        }

        return shenzhouOrder;
    }

    public ShenzhouOrder updateOrder(ShenzhouOrder order, String url, String reason, Double cancelCost) throws IOException {
        Map<String, Object> params = Maps.newHashMap();
        params.put("access_token", order.getUser().getShenzhouAccessToken());
        params.put("orderId", order.getShenzhouOrderId());
        String detailStr = HttpUtils.post(url, params);
        Map<String, Object> detailResult = new ObjectMapper().readValue(detailStr, Map.class);
        return orderShenzhouService.saveOrder((Map<String, Object>) detailResult.get("content"), order.getUser(), order.getId(), reason, cancelCost);
    }

    public void updateBalance(Long userId, Float frozenPrice, Double cost, Boolean isFrozen, ShenzhouOrder order) {
        Member user = memberService.get(userId);
        if (isFrozen) {
            if (user.getFrozenBalance() == null) {
                user.setFrozenBalance(0d);
            }
            user.setBalance(user.getBalance() - frozenPrice);
            user.setFrozenBalance(user.getFrozenBalance() + frozenPrice);
        } else {
            user.setFrozenBalance(user.getFrozenBalance() - frozenPrice);
            user.setBalance(user.getBalance() + frozenPrice - cost);
            savePayResult(order, user);
            shenzhouOrderService.updateOrderBill(order);
        }
        memberService.update(user);
    }

    public void savePayResult(ShenzhouOrder order, Member user) {
        if (order.getTotalPrice() == 0) {
            return;
        }
        AccountLog search = new AccountLog();
        search.setOrderNo(order.getOrderNo());
        search.setOrderType(OrderType.shenzhou);
        List<AccountLog> list = accountLogService.findListByAccountLogAll(search);
        AccountLog accountLog = new AccountLog();
        if (!list.isEmpty()) {
            accountLog = list.get(0);
            accountLog.setUpdateTime(new Date());
        } else {
            accountLog.setCreateTime(new Date());
            accountLog.setType(AccountType.consume);
            accountLog.setOrderNo(order.getOrderNo());
            accountLog.setMoney(order.getTotalPrice().doubleValue());
            accountLog.setDescription(AccountType.consume.getDescription());
            accountLog.setUser(user);
            accountLog.setAccountUser(user);
//            accountLog.setCompanyUnit(order.getCompanyUnit());
            accountLog.setSourceId(order.getId());
        }
        accountLog.setStatus(AccountStatus.normal);
        accountLog.setOrderType(OrderType.shenzhou);
        accountLog.setBalance(user.getBalance());
        accountLogService.save(accountLog);
    }
}
