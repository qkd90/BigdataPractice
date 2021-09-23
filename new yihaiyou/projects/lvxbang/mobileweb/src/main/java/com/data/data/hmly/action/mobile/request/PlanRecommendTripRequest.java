package com.data.data.hmly.action.mobile.request;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
public class PlanRecommendTripRequest {
    private Long id;
    private Integer ranking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
