package com.data.data.hmly.action.hotel.vo;

/**
 * Created by zzl on 2016/11/30.
 */
public class HotelPriceCalendarVo {
    private String date;
    private Float member;
    private Float cost;
    private Integer inventory;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getMember() {
        return member;
    }

    public void setMember(Float member) {
        this.member = member;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
}
