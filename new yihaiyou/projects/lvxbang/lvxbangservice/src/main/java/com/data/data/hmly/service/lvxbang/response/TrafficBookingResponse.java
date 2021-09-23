package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.zuipin.util.StringUtils;

/**
 * @author Jonathan.Guo
 */
public class TrafficBookingResponse extends BookingResponse {

    private String companyName;
    private String startTime;
    private String startStation;
    private String duration;
    private String endTime;
    private String endStation;
    private String seat;
    private String priceHash;
    private String trafficHash;
    private Float airportBuildFee;
    private Float additionalFuelTax;
    private String companyCode;

    public TrafficBookingResponse() {
    }

    public TrafficBookingResponse(TrafficPrice trafficPrice) {
        super(trafficPrice);
        this.companyName = trafficPrice.getTraffic().getCompany();
        this.companyCode = trafficPrice.getTraffic().getCompanyCode();
        this.startTime = trafficPrice.getTraffic().getLeaveTime();
        this.startStation = trafficPrice.getTraffic().getLeaveTransportation().getName();
        if (StringUtils.isBlank(this.startStation)) {
            this.startStation = trafficPrice.getTraffic().getStartPort();
        }
        this.duration = DateUtils.parsePrettyTimeFromMinute(trafficPrice.getTraffic().getFlightTime().intValue());
        this.endTime = trafficPrice.getTraffic().getArriveTime();
        this.endStation = trafficPrice.getTraffic().getArriveTransportation().getName();
        if (StringUtils.isBlank(this.endStation)) {
            this.endStation = trafficPrice.getTraffic().getEndPort();
        }
        this.seat = trafficPrice.getSeatName();
        this.priceHash = trafficPrice.getHashCode();
        this.trafficHash = trafficPrice.getTraffic().getHashCode();
        this.additionalFuelTax = trafficPrice.getAdditionalFuelTax();
        this.airportBuildFee = trafficPrice.getAirportBuildFee();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getPriceHash() {
        return priceHash;
    }

    public void setPriceHash(String priceHash) {
        this.priceHash = priceHash;
    }

    public String getTrafficHash() {
        return trafficHash;
    }

    public void setTrafficHash(String trafficHash) {
        this.trafficHash = trafficHash;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}
