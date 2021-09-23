package com.data.data.hmly.action.yihaiyou.response;


import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.LvjiOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderAll;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;

import java.util.Date;

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
    private String endDate;
    private Float price;
    private OrderType type;
    private String cover;
    private OrderStatus orderStatus;
    private String orderDate;
    private Integer num;
    private Long proId;
    private ProductType proType;
    private String flightNumber;
    private Integer waitTime;
    private String seatType;
    private String startName;
    private String endName;
    private String mobile;
    private String recName;
    private String url;

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
        this.mobile = order.getMobile();
        this.recName = order.getRecName();
    }

    public OrderSimpleResponse(OrderAll order) {
        this.id = order.getId();
        this.orderNo = order.getOrderNo();
        this.status = order.getStatus().getDescription();
        this.name = order.getName();
        this.day = order.getDay();
        if (OrderType.ferry.equals(order.getOrderType())) {
            this.startDate = order.getDepartTime();
        } else if (OrderType.shenzhou.equals(order.getOrderType())) {
            if (order.getStartDate() != null) {
                this.startDate = DateUtils.formatDate(order.getStartDate());
            }
            if (order.getEndDate() != null) {
                this.endDate = DateUtils.format(order.getEndDate(), "HH:mm:ss");
            }
        } else {
            if (order.getPlayDate() != null) {
                this.startDate = DateUtils.formatShortDate(order.getPlayDate());
            }
            if (order.getLeaveDate() != null) {
                this.endDate = DateUtils.formatShortDate(order.getLeaveDate());
            }
        }
        this.price = order.getPrice();
        this.type = order.getOrderType();
        this.orderStatus = order.getStatus();
        this.orderDate = DateUtils.formatDate(order.getCreateTime());
        this.num = order.getNum();
        this.flightNumber = order.getFlightNumber();
        this.seatType = order.getSeatType();
        this.startName = order.getStartName();
        this.endName = order.getEndName();
        this.mobile = order.getMobile();
    }

    public OrderSimpleResponse(OrderSimpleResponse orderSimpleResponse) {
        this.id = orderSimpleResponse.getId();
        this.orderNo = orderSimpleResponse.getOrderNo();
        this.status = orderSimpleResponse.getStatus();
        this.name = orderSimpleResponse.getName();
        this.day = orderSimpleResponse.getDay();
        this.startDate = orderSimpleResponse.getStartDate();
        this.price = orderSimpleResponse.getPrice();
        this.type = orderSimpleResponse.getType();
        this.orderStatus = orderSimpleResponse.getOrderStatus();
        this.orderDate = orderSimpleResponse.getOrderDate();
    }

    public OrderSimpleResponse(FerryOrder ferryOrder) {
        this.id = ferryOrder.getId();
        this.orderNo = ferryOrder.getOrderNumber();
        this.status = ferryOrder.getStatus().getDescription();
        this.name = ferryOrder.getFlightLineName();
        this.startDate = ferryOrder.getDepartTime();
        this.price = ferryOrder.getAmount();
        this.type = OrderType.ferry;
        this.orderStatus = ferryOrder.getStatus();
        this.orderDate = DateUtils.formatDate(ferryOrder.getCreateTime());
        this.flightNumber = ferryOrder.getFlightNumber();
        if (ferryOrder.getWaitTime() != null) {
            this.waitTime = Long.valueOf(DateUtils.getDateDiffLong(ferryOrder.getWaitTime(), new Date()) / 1000).intValue();
        }
    }

    public OrderSimpleResponse(LvjiOrder order){

        this.id = order.getId();
        this.orderNo = order.getOrderNo();
        this.status = order.getStatus().getDescription();
        this.name = order.getScenicName();
        this.price = order.getPrice();
        this.orderStatus = order.getStatus();
        this.orderDate = DateUtils.formatDate(order.getCreateTime());
        this.num = order.getNum();
        this.mobile = order.getMobile();
        this.url = order.getUrl();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public ProductType getProType() {
        return proType;
    }

    public void setProType(ProductType proType) {
        this.proType = proType;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }
}
