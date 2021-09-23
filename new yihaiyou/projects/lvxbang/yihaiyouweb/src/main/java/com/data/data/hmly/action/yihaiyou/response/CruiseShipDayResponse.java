package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.cruiseship.entity.CruiseShipPlan;

/**
 * Created by huangpeijie on 2016-09-19,0019.
 */
public class CruiseShipDayResponse {
    private Integer day;
    private String dayDesc;
    private String arrange;
    private String arriveTime;
    private String leaveTime;
    private String breakfast;
    private String lunch;
    private String supper;
    private String hotelName;

    public CruiseShipDayResponse() {
    }

    public CruiseShipDayResponse(CruiseShipPlan cruiseShipPlan) {
        this.day = cruiseShipPlan.getDay();
        this.dayDesc = cruiseShipPlan.getDayDesc();
        this.arrange = cruiseShipPlan.getArrange();
        this.arriveTime = cruiseShipPlan.getArriveTime();
        this.leaveTime = cruiseShipPlan.getLeaveTime();
        this.breakfast = cruiseShipPlan.getBreakfast();
        this.lunch = cruiseShipPlan.getLunch();
        this.supper = cruiseShipPlan.getSupper();
        this.hotelName = cruiseShipPlan.getHotelName();
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getDayDesc() {
        return dayDesc;
    }

    public void setDayDesc(String dayDesc) {
        this.dayDesc = dayDesc;
    }

    public String getArrange() {
        return arrange;
    }

    public void setArrange(String arrange) {
        this.arrange = arrange;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getSupper() {
        return supper;
    }

    public void setSupper(String supper) {
        this.supper = supper;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
}
