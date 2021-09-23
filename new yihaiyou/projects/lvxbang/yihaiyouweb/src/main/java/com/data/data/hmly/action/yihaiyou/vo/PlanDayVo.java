package com.data.data.hmly.action.yihaiyou.vo;

import java.util.List;

/**
 * Created by zzl on 2017/2/21.
 */
public class PlanDayVo {

    private String startDate;
    private Integer day;
    private FerryInfoVo ferry;
    private HotelInfoVo hotel;
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
