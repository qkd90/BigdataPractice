package com.data.data.hmly.action.yhypc.request;

import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-21,0021.
 */
public class OrderUpdateRequest {
    private Long id;
    private String name;
    private Integer day;
    private String playDate;
    private List<Map<String, Object>> details;
    private OrderContact contact = new OrderContact();
    private String orderType;
    private Map<String, Object> creditCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public List<Map<String, Object>> getDetails() {
        return details;
    }

    public void setDetails(List<Map<String, Object>> details) {
        this.details = details;
    }

    public OrderContact getContact() {
        return contact;
    }

    public void setContact(OrderContact contact) {
        this.contact = contact;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Map<String, Object> getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Map<String, Object> creditCard) {
        this.creditCard = creditCard;
    }
}
