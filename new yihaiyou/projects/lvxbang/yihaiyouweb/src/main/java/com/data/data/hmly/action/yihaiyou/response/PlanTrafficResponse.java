package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.traffic.entity.Traffic;

/**
 * Created by huangpeijie on 2016-04-21,0021.
 */
public class PlanTrafficResponse extends BaseTrafficResponse {
    private Long fromCityId;
    private String fromCityName;
    private Long toCityId;
    private String toCityName;
    private Long trafficId;
    private Long priceId;
    private Float price;

    public PlanTrafficResponse() {
    }

    public PlanTrafficResponse(Traffic traffic) {
        super(traffic);
        this.fromCityId = traffic.getLeaveCity().getId();
        this.fromCityName = traffic.getLeaveCity().getName();
        this.toCityId = traffic.getArriveCity().getId();
        this.toCityName = traffic.getArriveCity().getName();
        this.trafficId = traffic.getId();
    }

    public Long getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(Long fromCityId) {
        this.fromCityId = fromCityId;
    }

    public String getFromCityName() {
        return fromCityName;
    }

    public void setFromCityName(String fromCityName) {
        this.fromCityName = fromCityName;
    }

    public Long getToCityId() {
        return toCityId;
    }

    public void setToCityId(Long toCityId) {
        this.toCityId = toCityId;
    }

    public String getToCityName() {
        return toCityName;
    }

    public void setToCityName(String toCityName) {
        this.toCityName = toCityName;
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
}
