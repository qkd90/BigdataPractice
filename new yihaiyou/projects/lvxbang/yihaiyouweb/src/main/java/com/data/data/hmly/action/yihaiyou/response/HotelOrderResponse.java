package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.action.yihaiyou.request.OrderContact;
import com.data.data.hmly.action.yihaiyou.request.SimpleTourist;
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
    private Boolean hasBreakfast;
    private PriceStatus priceStatus;
    private OrderContact contact;
    private OrderDetailStatus orderDetailStatus;
    private List<SimpleTourist> tourists = Lists.newArrayList();
    private String msg;
    private List<ProValidCodeResponse> codes = Lists.newArrayList();
    private String hotelTelephone;
//    private List<HotelPriceCalendar> calendarList = Lists.newArrayList();

    public HotelOrderResponse() {
    }

    public HotelOrderResponse(OrderSimpleResponse orderSimpleResponse) {
        super(orderSimpleResponse);
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

    public Boolean getHasBreakfast() {
        return hasBreakfast;
    }

    public void setHasBreakfast(Boolean hasBreakfast) {
        this.hasBreakfast = hasBreakfast;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ProValidCodeResponse> getCodes() {
        return codes;
    }

    public void setCodes(List<ProValidCodeResponse> codes) {
        this.codes = codes;
    }

    public String getHotelTelephone() {
        return hotelTelephone;
    }

    public void setHotelTelephone(String hotelTelephone) {
        this.hotelTelephone = hotelTelephone;
    }
}
