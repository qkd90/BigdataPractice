package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.lvxbang.response.PlanBookingResponse;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class TrafficRecommendResponse {
    private Long fromCityId;
    private String fromCityName;
    private Long toCityId;
    private String toCityName;
    private String startDate;
    private List<TrafficResponse> traffics = Lists.newArrayList();
    private List<TrafficResponse> returnTraffics = Lists.newArrayList();

    public TrafficRecommendResponse() {
    }

    public TrafficRecommendResponse(PlanBookingResponse planBookingResponse) {
        this.fromCityId = planBookingResponse.getFromCityId();
        this.fromCityName = planBookingResponse.getFromCityName();
        this.toCityId = planBookingResponse.getCityId();
        this.toCityName = planBookingResponse.getCityName();
        this.startDate = DateUtils.formatShortDate(planBookingResponse.getFromDate());
    }

    public Long getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(Long fromCityId) {
        this.fromCityId = fromCityId;
    }

    public String getFromCityName() {
        return fromCityName;
    }

    public void setFromCityName(String fromCityName) {
        this.fromCityName = fromCityName;
    }

    public Long getToCityId() {
        return toCityId;
    }

    public void setToCityId(Long toCityId) {
        this.toCityId = toCityId;
    }

    public String getToCityName() {
        return toCityName;
    }

    public void setToCityName(String toCityName) {
        this.toCityName = toCityName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<TrafficResponse> getTraffics() {
        return traffics;
    }

    public void setTraffics(List<TrafficResponse> traffics) {
        this.traffics = traffics;
    }

    public List<TrafficResponse> getReturnTraffics() {
        return returnTraffics;
    }

    public void setReturnTraffics(List<TrafficResponse> returnTraffics) {
        this.returnTraffics = returnTraffics;
    }
}
