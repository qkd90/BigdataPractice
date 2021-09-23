package com.data.data.hmly.action.yihaiyou.request;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class PlanRecommendRequest {
    private String startDate;
    private Long startCityId;
    private List<PlanRecommendSimpleRequest> plan;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(Long startCityId) {
        this.startCityId = startCityId;
    }

    public List<PlanRecommendSimpleRequest> getPlan() {
        return plan;
    }

    public void setPlan(List<PlanRecommendSimpleRequest> plan) {
        this.plan = plan;
    }
}
