package com.data.data.hmly.service.order.vo;

import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;

/**
 * Created by zzl on 2016/10/19.
 */
public class OrderResult {

    private Boolean success;
    private String msg;
    private String apiResult;
    private String errorCode;
    private String realOrderId;
    private Long orderId;
    private Long orderDetailId;
    private OrderDetailStatus orderDetailStatus;
    private OrderStatus orderStatus;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getApiResult() {
        return apiResult;
    }

    public void setApiResult(String apiResult) {
        this.apiResult = apiResult;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getRealOrderId() {
        return realOrderId;
    }

    public void setRealOrderId(String realOrderId) {
        this.realOrderId = realOrderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public OrderDetailStatus getOrderDetailStatus() {
        return orderDetailStatus;
    }

    public void setOrderDetailStatus(OrderDetailStatus orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
