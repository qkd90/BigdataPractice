package com.data.data.hmly.action.mobile.response;

/**
 * Created by huangpeijie on 2016-07-22,0022.
 */
public class LineOrderDetailResponse {
    private String startDate;
    private Integer adultNum;
    private Integer childNum;
    private Float adultPrice;
    private Float childPrice;
    private Integer day;
    private String startCity;
    private Long lineId;


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getAdultNum() {
        return adultNum;
    }

    public void setAdultNum(Integer adultNum) {
        this.adultNum = adultNum;
    }

    public Integer getChildNum() {
        return childNum;
    }

    public void setChildNum(Integer childNum) {
        this.childNum = childNum;
    }

    public Float getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(Float adultPrice) {
        this.adultPrice = adultPrice;
    }

    public Float getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(Float childPrice) {
        this.childPrice = childPrice;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }
}
