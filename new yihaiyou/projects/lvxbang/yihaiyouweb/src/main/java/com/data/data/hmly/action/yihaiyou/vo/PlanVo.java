package com.data.data.hmly.action.yihaiyou.vo;

import java.util.List;

/**
 * Created by zzl on 2017/2/21.
 */
public class PlanVo {
    private Long id;
    private String name;
    private String startDate;
    private String startTime;
    private String trips;
    private Integer planDays;
    private Float price;
    private String cover;
    private Integer scenicNum;
    private List<String> scenicInfoNames;
    private List<PlanDayVo> days;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTrips() {
        return trips;
    }

    public void setTrips(String trips) {
        this.trips = trips;
    }

    public Integer getPlanDays() {
        return planDays;
    }

    public void setPlanDays(Integer planDays) {
        this.planDays = planDays;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getScenicNum() {
        return scenicNum;
    }

    public void setScenicNum(Integer scenicNum) {
        this.scenicNum = scenicNum;
    }

    public List<String> getScenicInfoNames() {
        return scenicInfoNames;
    }

    public void setScenicInfoNames(List<String> scenicInfoNames) {
        this.scenicInfoNames = scenicInfoNames;
    }

    public List<PlanDayVo> getDays() {
        return days;
    }

    public void setDays(List<PlanDayVo> days) {
        this.days = days;
    }
}
