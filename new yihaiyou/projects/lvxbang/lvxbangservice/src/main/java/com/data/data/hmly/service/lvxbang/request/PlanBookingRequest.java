package com.data.data.hmly.service.lvxbang.request;

import com.data.data.hmly.service.traffic.entity.enums.TrafficType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jonathan.Guo
 */
public class PlanBookingRequest {

    private Long planId;
    private Integer days;
    private Long toCityId;
    private Long fromCityId;
    private Long prevFromCityId;
    private String fromDateStr;
    private Long coreScenic;
    private Integer hotelStar;
    private TrafficType trafficType;

    public Date getFromDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(fromDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Long getToCityId() {
        return toCityId;
    }

    public void setToCityId(Long toCityId) {
        this.toCityId = toCityId;
    }

    public Long getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(Long fromCityId) {
        this.fromCityId = fromCityId;
    }

    public Long getPrevFromCityId() {
        return prevFromCityId;
    }

    public void setPrevFromCityId(Long prevFromCityId) {
        this.prevFromCityId = prevFromCityId;
    }

    public String getFromDateStr() {
        return fromDateStr;
    }

    public void setFromDateStr(String fromDateStr) {
        this.fromDateStr = fromDateStr;
    }

    public Long getCoreScenic() {
        return coreScenic;
    }

    public void setCoreScenic(Long coreScenic) {
        this.coreScenic = coreScenic;
    }

    public Integer getHotelStar() {
        return hotelStar;
    }

    public void setHotelStar(Integer hotelStar) {
        this.hotelStar = hotelStar;
    }

    public TrafficType getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(TrafficType trafficType) {
        this.trafficType = trafficType;
    }
}
