package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.order.entity.enums.OrderType;

import java.util.List;

/**
 * Created by vacuity on 16/1/12.
 */
public class PersonalOrderResponse {

    private Long id;
    private String orderNo;
    private String orderDate;
    private String detailUrl;
    private String status;
    private OrderType orderType;
    private String orderTypeDesc;
    private String name;
    private List<PersonalOrderDetailResponse> orderDetailList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderTypeDesc() {
        return orderTypeDesc;
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
    }

    public List<PersonalOrderDetailResponse> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<PersonalOrderDetailResponse> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
