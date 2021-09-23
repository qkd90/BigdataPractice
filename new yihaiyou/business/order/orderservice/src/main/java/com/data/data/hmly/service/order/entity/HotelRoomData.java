package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;

import java.util.List;

/**
 * Created by zzl on 2016/9/7.
 */
public class HotelRoomData {

    private Hotel hotel;

    private HotelPrice hotelPrice;

    private List<OrderDetail> orderDetailList;

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public HotelPrice getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(HotelPrice hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
