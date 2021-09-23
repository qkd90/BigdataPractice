package com.data.data.hmly.action.mobile.response;


import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
public class OrderSimpleResponse {
    private Long id;
    private String orderNo;
    private String status;
    private String name;
    private Integer day;
    private String startDate;
    private Float price;
    private OrderType type;
    private String cover;
    private OrderStatus orderStatus;
    private String orderDate;

    public OrderSimpleResponse() {
    }

    public OrderSimpleResponse(Order order) {
        this.id = order.getId();
        this.orderNo = order.getOrderNo();
        this.status = order.getStatus().getDescription();
        this.name = order.getName();
        this.day = order.getDay();
        if (order.getPlayDate() != null) {
            this.startDate = DateUtils.formatShortDate(order.getPlayDate());
        }
        this.price = order.getPrice();
        this.type = order.getOrderType();
        this.orderStatus = order.getStatus();
        this.orderDate = DateUtils.formatDate(order.getCreateTime());
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
