package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.action.mobile.request.OrderContact;
import com.data.data.hmly.action.mobile.request.OrderInvoice;
import com.data.data.hmly.action.mobile.request.SimpleTourist;
import com.data.data.hmly.service.order.entity.Order;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
public class OrderResponse extends OrderSimpleResponse {
    private List<PlanOrderTrafficResponse> planes = Lists.newArrayList();
    private List<PlanOrderTrafficResponse> trains = Lists.newArrayList();
    private List<HotelSimpleResponse> hotels = Lists.newArrayList();
    private List<PlanOrderDayResponse> days = Lists.newArrayList();
    private List<SimpleTourist> tourists = Lists.newArrayList();
    private List<SimpleTourist> childTourists = Lists.newArrayList();
    private LineOrderDetailResponse lineOrderDetail;
    private OrderContact contact;
    private OrderInvoice invoice;
    private Float couponValue;
    private Float insurancePrice;

    public OrderResponse() {
    }

    public OrderResponse(Order order) {
        super(order);
        this.insurancePrice = order.getInsurancePrice();
    }

    public List<PlanOrderTrafficResponse> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlanOrderTrafficResponse> planes) {
        this.planes = planes;
    }

    public List<PlanOrderTrafficResponse> getTrains() {
        return trains;
    }

    public void setTrains(List<PlanOrderTrafficResponse> trains) {
        this.trains = trains;
    }

    public List<HotelSimpleResponse> getHotels() {
        return hotels;
    }

    public void setHotels(List<HotelSimpleResponse> hotels) {
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

    public List<PlanOrderDayResponse> getDays() {
        return days;
    }

    public void setDays(List<PlanOrderDayResponse> days) {
        this.days = days;
    }

    public OrderResponse withPlanes(List<PlanOrderTrafficResponse> planes) {
        this.planes = planes;
        return this;
    }

    public OrderResponse withTrains(List<PlanOrderTrafficResponse> trains) {
        this.trains = trains;
        return this;
    }

    public OrderResponse withHotels(List<HotelSimpleResponse> hotels) {
        this.hotels = hotels;
        return this;
    }

    public OrderResponse withDays(List<PlanOrderDayResponse> days) {
        this.days = days;
        return this;
    }

    public List<SimpleTourist> getChildTourists() {
        return childTourists;
    }

    public void setChildTourists(List<SimpleTourist> childTourists) {
        this.childTourists = childTourists;
    }

    public LineOrderDetailResponse getLineOrderDetail() {
        return lineOrderDetail;
    }

    public void setLineOrderDetail(LineOrderDetailResponse lineOrderDetail) {
        this.lineOrderDetail = lineOrderDetail;
    }

    public Float getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(Float insurancePrice) {
        this.insurancePrice = insurancePrice;
    }
}
