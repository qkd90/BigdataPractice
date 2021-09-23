package com.data.data.hmly.service.hotel.request;

import java.util.Date;

/**
 * Created by HMLY on 2016/4/21.
 */
public class HotelPriceRequest {
    private String elongId;
    private Long hotelId;
    private Date arrive;
    private Date departure;
    private String roomId;
    private String roomTypeId;
    private Integer ratePla;

    public String getElongId() {
        return elongId;
    }

    public void setElongId(String elongId) {
        this.elongId = elongId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Date getArrive() {
        return arrive;
    }

    public void setArrive(Date arrive) {
        this.arrive = arrive;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getRatePla() {
        return ratePla;
    }

    public void setRatePla(Integer ratePla) {
        this.ratePla = ratePla;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
}
