package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.action.mobile.request.GuideTripUpdateRequest;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class GuideDayResponse {
    private Long dayId;
    private Integer day;
    private Integer scenics;
    private List<GuideTripUpdateRequest> planTrips = Lists.newArrayList();

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
}
