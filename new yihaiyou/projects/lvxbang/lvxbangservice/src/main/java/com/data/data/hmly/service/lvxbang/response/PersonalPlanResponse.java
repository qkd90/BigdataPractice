package com.data.data.hmly.service.lvxbang.response;

import java.util.List;

/**
 * Created by vacuity on 16/1/18.
 */
public class PersonalPlanResponse {

    private Long id;
    private String name;
    private String cover;
    private Integer days;
    private Float cost;
    private String date;
    private List<String> citys;
    private Long recplanId;
    private Boolean started;

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

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getCitys() {
        return citys;
    }

    public void setCitys(List<String> citys) {
        this.citys = citys;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Long getRecplanId() {
        return recplanId;
    }

    public void setRecplanId(Long recplanId) {
        this.recplanId = recplanId;
    }
}
