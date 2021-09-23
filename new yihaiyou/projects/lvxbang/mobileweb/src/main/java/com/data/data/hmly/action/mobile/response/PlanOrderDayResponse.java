package com.data.data.hmly.action.mobile.response;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-22,0022.
 */
public class PlanOrderDayResponse {
    private Long cityId;
    private String cityName;
    private Integer day;
    private List<TicketResponse> tickets = Lists.newArrayList();

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

    public List<TicketResponse> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketResponse> tickets) {
        this.tickets = tickets;
    }
}
