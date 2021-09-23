package com.data.data.hmly.action.mobile.request;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class GuideUpdateRequest {
    private Long id;
    private String title;
    private String cover;
    private Integer days;
    private Long userId;
    private Integer scenics;
    private List<Long> removeDays;
    private List<GuideDayUpdateRequest> planDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getScenics() {
        return scenics;
    }

    public void setScenics(Integer scenics) {
        this.scenics = scenics;
    }

    public List<Long> getRemoveDays() {
        return removeDays;
    }

    public void setRemoveDays(List<Long> removeDays) {
        this.removeDays = removeDays;
    }

    public List<GuideDayUpdateRequest> getPlanDays() {
        return planDays;
    }

    public void setPlanDays(List<GuideDayUpdateRequest> planDays) {
        this.planDays = planDays;
    }
}
