package com.data.data.hmly.action.mobile.request;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class PlanRecommendSimpleRequest {
    private Long cityId;
    private Integer day;
    private List<PlanRecommendTripRequest> tripList;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public List<PlanRecommendTripRequest> getTripList() {
        return tripList;
    }

    public void setTripList(List<PlanRecommendTripRequest> tripList) {
        this.tripList = tripList;
    }
}
