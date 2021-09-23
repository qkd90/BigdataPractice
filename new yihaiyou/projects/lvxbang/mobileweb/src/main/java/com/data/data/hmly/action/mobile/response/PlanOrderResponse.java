package com.data.data.hmly.action.mobile.response;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-21,0021.
 */
public class PlanOrderResponse {
    private String name;
    private Integer day;
    private String playDate;
    private List<PlanOrderTrafficResponse> planes = Lists.newArrayList();
    private List<PlanOrderTrafficResponse> trains = Lists.newArrayList();
    private List<HotelSimpleResponse> hotels = Lists.newArrayList();
    private List<PlanOrderDayResponse> days = Lists.newArrayList();
    private List<CouponResponse> coupons = Lists.newArrayList();

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

    public List<PlanOrderDayResponse> getDays() {
        return days;
    }

    public void setDays(List<PlanOrderDayResponse> days) {
        this.days = days;
    }

    public List<CouponResponse> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponResponse> coupons) {
        this.coupons = coupons;
    }
}
