package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class TotalPlanBookingResponse {

    private TbArea startCity;
    private Date startDate;
    private Integer hotelType;
    private TrafficType trafficType;

    private List<PlanBookingResponse> planBookingResponses = new ArrayList<PlanBookingResponse>();

    public TotalPlanBookingResponse addPlanBooking(PlanBookingResponse planBookingResponse) {
        this.planBookingResponses.add(planBookingResponse);
        return this;
    }

    public TotalPlanBookingResponse addPlanBooking(Collection<PlanBookingResponse> planBookingResponse) {
        this.planBookingResponses.addAll(planBookingResponse);
        return this;
    }

    public TotalPlanBookingResponse withCity(TbArea startCity) {
        this.startCity = startCity;
        return this;
    }

    public TotalPlanBookingResponse withDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public TotalPlanBookingResponse withHotelType(Integer hotelType) {
        this.hotelType = hotelType;
        return this;
    }

    public TotalPlanBookingResponse withTrafficType(TrafficType trafficType) {
        this.trafficType = trafficType;
        return this;
    }

    public TbArea getStartCity() {
        return startCity;
    }

    public void setStartCity(TbArea startCity) {
        this.startCity = startCity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getHotelType() {
        return hotelType;
    }

    public void setHotelType(Integer hotelType) {
        this.hotelType = hotelType;
    }

    public TrafficType getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(TrafficType trafficType) {
        this.trafficType = trafficType;
    }

    public List<PlanBookingResponse> getPlanBookingResponses() {
        return planBookingResponses;
    }

    public void setPlanBookingResponses(List<PlanBookingResponse> planBookingResponses) {
        this.planBookingResponses = planBookingResponses;
    }
}
