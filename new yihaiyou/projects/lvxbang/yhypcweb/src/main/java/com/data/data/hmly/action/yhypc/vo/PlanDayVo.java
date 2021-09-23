package com.data.data.hmly.action.yhypc.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by zzl on 2017/2/13.
 */
public class PlanDayVo {

    private String startDate;
    private Integer day;
    private FerryInfoVo ferry;
    private HotelInfoVo hotel;
    private Float price;
    private Integer playTime;
    private List<ScenicInfoVo> scenics;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public FerryInfoVo getFerry() {
        return ferry;
    }

    public void setFerry(FerryInfoVo ferry) {
        this.ferry = ferry;
    }

    public HotelInfoVo getHotel() {
        return hotel;
    }

    public void setHotel(HotelInfoVo hotel) {
        this.hotel = hotel;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public List<ScenicInfoVo> getScenics() {
        return scenics;
    }

    public void setScenics(List<ScenicInfoVo> scenics) {
        this.scenics = scenics;
    }
}
