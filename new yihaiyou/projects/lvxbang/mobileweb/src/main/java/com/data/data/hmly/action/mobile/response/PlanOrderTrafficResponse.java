package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.common.util.DateUtils;

/**
 * Created by huangpeijie on 2016-04-21,0021.
 */
public class PlanOrderTrafficResponse extends BaseTrafficResponse {
    private String startCity;
    private String endCity;
    private String startDate;
    private Long trafficId;
    private Long priceId;
    private Float price;
    private Float airportBuildFee;
    private Float additionalFuelTax;

    public PlanOrderTrafficResponse() {
    }

    public PlanOrderTrafficResponse(com.data.data.hmly.service.lvxbang.response.TrafficResponse trafficResponse) {
        this.startDate = DateUtils.formatShortDate(trafficResponse.getDate());
        this.trafficType = trafficResponse.getTrafficType();
        this.company = trafficResponse.getCompany();
        this.trafficCode = trafficResponse.getTrafficCode();
        this.startStation = trafficResponse.getLeavePort();
        this.endStation = trafficResponse.getArrivePort();
        this.startCity = trafficResponse.getLeaveCity();
        this.endCity = trafficResponse.getArriveCity();
        this.startTime = trafficResponse.getLeaveTime();
        this.endTime = trafficResponse.getArriveTime();
        this.trafficId = trafficResponse.getId();
        this.priceId = trafficResponse.getPriceId();
        this.price = trafficResponse.getPrice();
        this.additionalFuelTax = trafficResponse.getAdditionalFuelTax();
        this.airportBuildFee = trafficResponse.getAirportBuildFee();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getTrafficId() {
        return trafficId;
    }

    public void setTrafficId(Long trafficId) {
        this.trafficId = trafficId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getAirportBuildFee() {
        return airportBuildFee;
    }

    public void setAirportBuildFee(Float airportBuildFee) {
        this.airportBuildFee = airportBuildFee;
    }

    public Float getAdditionalFuelTax() {
        return additionalFuelTax;
    }

    public void setAdditionalFuelTax(Float additionalFuelTax) {
        this.additionalFuelTax = additionalFuelTax;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }
}
