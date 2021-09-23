package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.action.mobile.request.OrderContact;
import com.data.data.hmly.action.mobile.request.SimpleTourist;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-08-08,0008.
 */
public class HotelOrderResponse extends OrderSimpleResponse {
    private String endDate;
    private String address;
    private String roomName;
    private Long hotelId;
    private Integer num;
    private PriceStatus priceStatus;
    private OrderContact contact;
    private OrderDetailStatus orderDetailStatus;
    private List<SimpleTourist> tourists = Lists.newArrayList();
    private List<HotelPriceCalendar> calendarList = Lists.newArrayList();

    public HotelOrderResponse() {
    }

    public HotelOrderResponse(Order order) {
        super(order);
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public OrderContact getContact() {
        return contact;
    }

    public void setContact(OrderContact contact) {
        this.contact = contact;
    }

    public List<SimpleTourist> getTourists() {
        return tourists;
    }

    public void setTourists(List<SimpleTourist> tourists) {
        this.tourists = tourists;
    }

    public List<HotelPriceCalendar> getCalendarList() {
        return calendarList;
    }

    public void setCalendarList(List<HotelPriceCalendar> calendarList) {
        this.calendarList = calendarList;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public OrderDetailStatus getOrderDetailStatus() {
        return orderDetailStatus;
    }

    public void setOrderDetailStatus(OrderDetailStatus orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public PriceStatus getPriceStatus() {
        return priceStatus;
    }

    public void setPriceStatus(PriceStatus priceStatus) {
        this.priceStatus = priceStatus;
    }
}
