package com.data.data.hmly.service.lvxbang.request;

import java.util.List;
import java.util.Map;

/**
 * @author Jonathan.Guo
 */
public class PlanOptimizeRequest {
    public int type;
    public int day;
    public int hour;
    public List<TripNode> scenicList;
    public Map<String, Integer> cityDays;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public List<TripNode> getScenicList() {
        return scenicList;
    }

    public void setScenicList(List<TripNode> scenicList) {
        this.scenicList = scenicList;
    }

    public Map<String, Integer> getCityDays() {
        return cityDays;
    }

    public void setCityDays(Map<String, Integer> cityDays) {
        this.cityDays = cityDays;
    }
}
