package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.traffic.entity.enums.TrafficType;

import java.util.Date;

/**
 * Created by vacuity on 16/1/8.
 */
public class TrafficResponse {

    private Long id;
    private Long priceId;
    private TrafficType trafficType;
    private String company;
    private String trafficCode;
    private String leaveTime;
    private String leavePort;
    private Date date;
    private String trafficTime;
    private String arriveTime;
    private String arrivePort;
    private String seatType;
    private Float price;
    private String leaveCity;
    private String arriveCity;
    private Float airportBuildFee;
    private Float additionalFuelTax;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public TrafficType getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(TrafficType trafficType) {
        this.trafficType = trafficType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTrafficCode() {
        return trafficCode;
    }

    public void setTrafficCode(String trafficCode) {
        this.trafficCode = trafficCode;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getLeavePort() {
        return leavePort;
    }

    public void setLeavePort(String leavePort) {
        this.leavePort = leavePort;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrafficTime() {
        return trafficTime;
    }

    public void setTrafficTime(String trafficTime) {
        this.trafficTime = trafficTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getArrivePort() {
        return arrivePort;
    }

    public void setArrivePort(String arrivePort) {
        this.arrivePort = arrivePort;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getLeaveCity() {
        return leaveCity;
    }

    public void setLeaveCity(String leaveCity) {
        this.leaveCity = leaveCity;
    }

    public String getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
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
}
