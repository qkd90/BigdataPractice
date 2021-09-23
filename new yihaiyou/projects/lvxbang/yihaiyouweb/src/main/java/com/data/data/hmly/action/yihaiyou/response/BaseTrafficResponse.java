package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;

/**
 * Created by huangpeijie on 2016-04-21,0021.
 */
public class BaseTrafficResponse {
    protected String company;
    protected String companyCode;
    protected String trafficCode;
    protected TrafficType trafficType;
    protected String startStation;
    protected String startTime;
    protected String endStation;
    protected String endTime;

    public BaseTrafficResponse() {
    }

    public BaseTrafficResponse(Traffic traffic) {
        this.company = traffic.getCompany();
        this.companyCode = traffic.getCompanyCode();
        this.trafficCode = traffic.getTrafficCode();
        this.trafficType = traffic.getTrafficType();
        this.startStation = traffic.getLeaveTransportation().getName();
        this.startTime = traffic.getLeaveTime();
        this.endStation = traffic.getArriveTransportation().getName();
        this.endTime = traffic.getArriveTime();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getTrafficCode() {
        return trafficCode;
    }

    public void setTrafficCode(String trafficCode) {
        this.trafficCode = trafficCode;
    }

    public TrafficType getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(TrafficType trafficType) {
        this.trafficType = trafficType;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
