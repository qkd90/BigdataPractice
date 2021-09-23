package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.traffic.entity.TrafficPrice;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class TrafficPriceResponse {
    private String priceHash;
    private String seatName;
    private String seatNum;
    private Float price;

    public TrafficPriceResponse() {
    }

    public TrafficPriceResponse(TrafficPrice trafficPrice) {
        this.priceHash = trafficPrice.getHashCode();
        this.seatName = trafficPrice.getSeatName();
        this.seatNum = trafficPrice.getSeatNum();
        this.price = trafficPrice.getPrice();
    }

    public String getPriceHash() {
        return priceHash;
    }

    public void setPriceHash(String priceHash) {
        this.priceHash = priceHash;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
