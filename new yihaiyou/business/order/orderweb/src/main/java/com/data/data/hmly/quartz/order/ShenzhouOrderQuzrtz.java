package com.data.data.hmly.quartz.order;

import com.data.data.hmly.service.order.ShenzhouOrderService;
import com.data.data.hmly.service.order.entity.ShenzhouOrder;
import com.data.data.hmly.service.order.entity.enums.ShenzhouOrderPaymentStatus;
import com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.zuipin.util.HttpUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-09-18,0018.
 */
@Component
public class ShenzhouOrderQuzrtz {
    @Resource
    private ShenzhouOrderService shenzhouOrderService;
    @Resource
    private PropertiesManager propertiesManager;

    public static final String ORDER_DETAIL = "/v1/resource/order/getOrderDetail";


    public void doUpdateShenzhouOrder() {
        String shenzhouApi = propertiesManager.getString("shenzhouApi");
        List<ShenzhouOrder> list = shenzhouOrderService.getOrderList();
        try {
            for (ShenzhouOrder order : list) {
                Map<String, Object> params = Maps.newHashMap();
                params.put("access_token", order.getUser().getShenzhouAccessToken());
                params.put("orderId", order.getShenzhouOrderId());
                String detailStr = HttpUtils.post(shenzhouApi + ORDER_DETAIL, params);
                Map<String, Object> detailResult = new ObjectMapper().readValue(detailStr, Map.class);
                Map<String, Object> content = (Map<String, Object>) detailResult.get("content");
                if ((content.get("status") != null && !content.get("status").toString().equals(order.getStatus().name())) || (content.get("paymentStatus") != null && !content.get("paymentStatus").toString().equals(order.getPaymentStatus().name()))) {
                    updateShenzhouOrder(order, content);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateShenzhouOrder(ShenzhouOrder shenzhouOrder, Map<String, Object> map) {
        Map<String, Object> order = (Map<String, Object>) map.get("order");
        shenzhouOrder.setOrderNo(order.get("number").toString());
        shenzhouOrder.setServiceId(Integer.valueOf(order.get("serviceId").toString()));
        shenzhouOrder.setCarGroupId(Integer.valueOf(order.get("carGroupId").toString()));
        shenzhouOrder.setStatus(ShenzhouOrderStatus.valueOf(order.get("status").toString()));
        if (order.get("paymentStatus") == null || StringUtils.isBlank(order.get("paymentStatus").toString())) {
            shenzhouOrder.setPaymentStatus((ShenzhouOrderPaymentStatus.unpaid));
        } else {
            shenzhouOrder.setPaymentStatus(ShenzhouOrderPaymentStatus.valueOf(order.get("paymentStatus").toString()));
        }
        shenzhouOrder.setStartName(order.get("startName").toString());
        shenzhouOrder.setEndName(order.get("endName").toString());
        shenzhouOrder.setPassengerMobile(order.get("passengerMobile").toString());
        shenzhouOrder.setPassengerName(order.get("passengerName").toString());
        shenzhouOrder.setCreateTime(new Date((Long.valueOf(order.get("createTime").toString())) * 1000));
        shenzhouOrder.setDepartureTime(new Date((Long.valueOf(order.get("departureTime").toString())) * 1000));
        shenzhouOrder.setDispatchedTime(new Date((Long.valueOf(order.get("dispatchedTime").toString())) * 1000));
        shenzhouOrder.setFinishedTime(new Date((Long.valueOf(order.get("finishedTime").toString())) * 1000));

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
            }
        }

        Map<String, Object> driver = (Map<String, Object>) map.get("driver");
        shenzhouOrder.setDriverName(driver.get("driverName").toString());
        shenzhouOrder.setVehicleNo(driver.get("vehicleNo").toString());
        shenzhouOrder.setVirtualPhone4Purchaser(driver.get("virtualPhone4Purchaser").toString());
        shenzhouOrder.setVirtualPhone4Passenger(driver.get("virtualPhone4Passenger").toString());
        shenzhouOrder.setVehicleModel(driver.get("vehicleModel").toString());
        shenzhouOrder.setDriverScore(driver.get("driverScore").toString());

        shenzhouOrderService.update(shenzhouOrder);
    }
}
