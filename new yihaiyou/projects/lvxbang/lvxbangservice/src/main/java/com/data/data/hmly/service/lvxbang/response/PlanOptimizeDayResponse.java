package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.lvxbang.request.TripNode;

import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class PlanOptimizeDayResponse {
    public MiniCityResponse city;
    public int day;
    public List<TripNode> tripList;

    public MiniCityResponse getCity() {
        return city;
    }

    public void setCity(MiniCityResponse city) {
        this.city = city;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<TripNode> getTripList() {
        return tripList;
    }

    public void setTripList(List<TripNode> tripList) {
        this.tripList = tripList;
    }
}
