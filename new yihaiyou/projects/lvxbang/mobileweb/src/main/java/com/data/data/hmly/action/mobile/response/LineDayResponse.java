package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.line.entity.Linedays;

import java.util.List;

/**
 * Created by huangpeijie on 2016-07-26,0026.
 */
public class LineDayResponse {
    private String dayDesc;
    private Integer lineDay;
    private String arrange;
    private String meals;
    private String hotelName;
    private List<LineDayPlanResponse> linedaysplan;

    public LineDayResponse() {
    }

    public LineDayResponse(Linedays linedays) {
        this.dayDesc = linedays.getDayDesc();
        this.lineDay = linedays.getLineDay();
        this.arrange = linedays.getArrange();
        this.meals = linedays.getMeals();
        this.hotelName = linedays.getHotelName();
    }

    public String getDayDesc() {
        return dayDesc;
    }

    public void setDayDesc(String dayDesc) {
        this.dayDesc = dayDesc;
    }

    public Integer getLineDay() {
        return lineDay;
    }

    public void setLineDay(Integer lineDay) {
        this.lineDay = lineDay;
    }

    public String getArrange() {
        return arrange;
    }

    public void setArrange(String arrange) {
        this.arrange = arrange;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<LineDayPlanResponse> getLinedaysplan() {
        return linedaysplan;
    }

    public void setLinedaysplan(List<LineDayPlanResponse> linedaysplan) {
        this.linedaysplan = linedaysplan;
    }
}
