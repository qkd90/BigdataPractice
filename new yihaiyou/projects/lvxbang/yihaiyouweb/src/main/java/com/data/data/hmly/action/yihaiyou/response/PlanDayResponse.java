package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-21,0021.
 */
public class PlanDayResponse {
    private Long dayId;
    private Long cityId;
    private String cityName;
    private Integer day;
    private String playDate;
    private PlanTrafficResponse traffic;
    private PlanTrafficResponse returnTraffic;
    private PlanHotelResponse hotel;
    private List<PlanTripResponse> trips = Lists.newArrayList();

    public PlanDayResponse() {
    }

    public PlanDayResponse(PlanDay planDay) {
        this.dayId = planDay.getId();
        this.cityId = planDay.getCity().getId();
        this.cityName = planDay.getCity().getName();
        this.day = planDay.getDays();
        if (planDay.getDate() != null) {
            this.playDate = DateUtils.formatShortDate(planDay.getDate());
        }
    }

    public Long getDayId() {
        return dayId;
    }

    public void setDayId(Long dayId) {
        this.dayId = dayId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public PlanTrafficResponse getTraffic() {
        return traffic;
    }

    public void setTraffic(PlanTrafficResponse traffic) {
        this.traffic = traffic;
    }

    public PlanTrafficResponse getReturnTraffic() {
        return returnTraffic;
    }

    public void setReturnTraffic(PlanTrafficResponse returnTraffic) {
        this.returnTraffic = returnTraffic;
    }

    public PlanHotelResponse getHotel() {
        return hotel;
    }

    public void setHotel(PlanHotelResponse hotel) {
        this.hotel = hotel;
    }

    public List<PlanTripResponse> getTrips() {
        return trips;
    }

    public void setTrips(List<PlanTripResponse> trips) {
        this.trips = trips;
    }
}
