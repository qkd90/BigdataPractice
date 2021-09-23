package com.data.data.hmly.action.yihaiyou.request;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class GuideDayUpdateRequest {
    private Long dayId;
    private Integer day;
    private Integer scenics;
    private List<GuideTripUpdateRequest> planTrips;
    private List<GuideAddTripUpdateRequest> addTrips;
    private List<Long> removeTrips;

    public Long getDayId() {
        return dayId;
    }

    public void setDayId(Long dayId) {
        this.dayId = dayId;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getScenics() {
        return scenics;
    }

    public void setScenics(Integer scenics) {
        this.scenics = scenics;
    }

    public List<GuideTripUpdateRequest> getPlanTrips() {
        return planTrips;
    }

    public void setPlanTrips(List<GuideTripUpdateRequest> planTrips) {
        this.planTrips = planTrips;
    }

    public List<GuideAddTripUpdateRequest> getAddTrips() {
        return addTrips;
    }

    public void setAddTrips(List<GuideAddTripUpdateRequest> addTrips) {
        this.addTrips = addTrips;
    }

    public List<Long> getRemoveTrips() {
        return removeTrips;
    }

    public void setRemoveTrips(List<Long> removeTrips) {
        this.removeTrips = removeTrips;
    }
}
