package com.data.data.hmly.action.yhypc.response;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-21,0021.
 */
public class PlanOrderResponse {
    private String name;
    private Integer day;
    private String playDate;
    private List<PlanOrderHotelResponse> hotels = Lists.newArrayList();
    private List<TicketScenicResponse> scenics = Lists.newArrayList();

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

    public List<PlanOrderHotelResponse> getHotels() {
        return hotels;
    }

    public void setHotels(List<PlanOrderHotelResponse> hotels) {
        this.hotels = hotels;
    }

    public List<TicketScenicResponse> getScenics() {
        return scenics;
    }

    public void setScenics(List<TicketScenicResponse> scenics) {
        this.scenics = scenics;
    }
}
