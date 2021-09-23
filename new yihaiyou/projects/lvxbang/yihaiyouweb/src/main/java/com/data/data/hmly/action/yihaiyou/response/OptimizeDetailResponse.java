package com.data.data.hmly.action.yihaiyou.response;

import java.util.List;

/**
 * Created by huangpeijie on 2016-09-30,0030.
 */
public class OptimizeDetailResponse {
    private String name;
    private Float price;
    private Integer scenicNum;
    private String startDate;
    private List<OptimizeDetailDayResponse> days;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getScenicNum() {
        return scenicNum;
    }

    public void setScenicNum(Integer scenicNum) {
        this.scenicNum = scenicNum;
    }

    public List<OptimizeDetailDayResponse> getDays() {
        return days;
    }

    public void setDays(List<OptimizeDetailDayResponse> days) {
        this.days = days;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
