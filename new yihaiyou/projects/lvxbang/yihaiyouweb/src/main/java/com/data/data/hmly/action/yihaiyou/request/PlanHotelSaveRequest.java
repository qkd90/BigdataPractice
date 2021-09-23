package com.data.data.hmly.action.yihaiyou.request;

/**
 * Created by huangpeijie on 2016-04-18,0018.
 */
public class PlanHotelSaveRequest {
    private Long hotelId;
    private Long priceId;
    private String startDate;
    private String endDate;

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
