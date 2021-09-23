package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.plan.entity.Plan;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-21,0021.
 */
public class PlanResponse {
    private Long id;
    private Long userId;
    private Integer planDay;
    private Integer viewNum;
    private Integer quoteNum;
    private Long startCityId;
    private String startCityName;
    private String name;
    private String startDate;
    private String cover;
    private List<PlanDayResponse> days = Lists.newArrayList();

    public PlanResponse() {
    }

    public PlanResponse(Plan plan) {
        this.id = plan.getId();
        this.userId = plan.getUser().getId();
        this.planDay = plan.getPlanDays();
        this.viewNum = plan.getPlanStatistic().getViewNum();
        this.quoteNum = plan.getPlanStatistic().getQuoteNum();
        this.startCityId = plan.getStartCity().getId();
        this.startCityName = plan.getStartCity().getName();
        this.name = plan.getName();
        this.startDate = DateUtils.formatShortDate(plan.getStartTime());
        this.cover = cover(plan.getCoverPath());
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        } else {
            if (cover.startsWith("http")) {
                return cover;
            } else {
                return "http://7u2inn.com2.z0.glb.qiniucdn.com/" + cover;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(Long startCityId) {
        this.startCityId = startCityId;
    }

    public String getStartCityName() {
        return startCityName;
    }

    public void setStartCityName(String startCityName) {
        this.startCityName = startCityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<PlanDayResponse> getDays() {
        return days;
    }

    public void setDays(List<PlanDayResponse> days) {
        this.days = days;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getPlanDay() {
        return planDay;
    }

    public void setPlanDay(Integer planDay) {
        this.planDay = planDay;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getQuoteNum() {
        return quoteNum;
    }

    public void setQuoteNum(Integer quoteNum) {
        this.quoteNum = quoteNum;
    }
}
