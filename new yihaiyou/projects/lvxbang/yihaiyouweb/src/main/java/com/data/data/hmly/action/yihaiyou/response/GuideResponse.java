package com.data.data.hmly.action.yihaiyou.response;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class GuideResponse {
    private Long id;
    private String title;
    private String cover;
    private Integer days;
    private Long userId;
    private String createTime;
    private Integer scenics;
    private List<GuideDayResponse> planDays = Lists.newArrayList();

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getScenics() {
        return scenics;
    }

    public void setScenics(Integer scenics) {
        this.scenics = scenics;
    }

    public List<GuideDayResponse> getPlanDays() {
        return planDays;
    }

    public void setPlanDays(List<GuideDayResponse> planDays) {
        this.planDays = planDays;
    }
}
