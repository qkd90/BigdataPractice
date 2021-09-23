package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.action.yihaiyou.request.OrderContact;
import com.data.data.hmly.action.yihaiyou.request.OrderInvoice;
import com.data.data.hmly.action.yihaiyou.request.SimpleTourist;
import com.data.data.hmly.service.order.entity.Order;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
public class OrderResponse extends OrderSimpleResponse {
    private List<PlanOrderHotelResponse> hotels = Lists.newArrayList();
    private List<TicketScenicResponse> scenics = Lists.newArrayList();
    private List<SimpleTourist> tourists = Lists.newArrayList();
    private List<SimpleTourist> childTourists = Lists.newArrayList();
    private FerryOrderResponse ferry;
    private OrderContact contact;
    private OrderInvoice invoice;
    private Float couponValue;
    private Float insurancePrice;
    private Integer adultNum;
    private Integer childNum;

    public OrderResponse() {
    }

    public OrderResponse(Order order) {
        super(order);
        this.insurancePrice = order.getInsurancePrice();
    }

    public List<PlanOrderHotelResponse> getHotels() {
        return hotels;
    }

    public void setHotels(List<PlanOrderHotelResponse> hotels) {
        this.hotels = hotels;
    }

    public List<SimpleTourist> getTourists() {
        return tourists;
    }

    public void setTourists(List<SimpleTourist> tourists) {
        this.tourists = tourists;
    }

    public OrderContact getContact() {
        return contact;
    }

    public void setContact(OrderContact contact) {
        this.contact = contact;
    }

    public OrderInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(OrderInvoice invoice) {
        this.invoice = invoice;
    }

    public Float getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(Float couponValue) {
        this.couponValue = couponValue;
    }

    public List<TicketScenicResponse> getScenics() {
        return scenics;
    }

    public void setScenics(List<TicketScenicResponse> scenics) {
        this.scenics = scenics;
    }

    public OrderResponse withHotels(List<PlanOrderHotelResponse> hotels) {
        this.hotels = hotels;
        return this;
    }

    public OrderResponse withScenics(List<TicketScenicResponse> scenics) {
        this.scenics = scenics;
        return this;
    }

    public List<SimpleTourist> getChildTourists() {
        return childTourists;
    }

    public void setChildTourists(List<SimpleTourist> childTourists) {
        this.childTourists = childTourists;
    }

    public Float getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(Float insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public Integer getAdultNum() {
        return adultNum;
    }

    public void setAdultNum(Integer adultNum) {
        this.adultNum = adultNum;
    }

    public Integer getChildNum() {
        return childNum;
    }

    public void setChildNum(Integer childNum) {
        this.childNum = childNum;
    }

    public FerryOrderResponse getFerry() {
        return ferry;
    }

    public void setFerry(FerryOrderResponse ferry) {
        this.ferry = ferry;
    }
}
