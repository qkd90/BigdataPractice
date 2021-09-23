package com.data.data.hmly.action.yihaiyou.response;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-22,0022.
 */
public class PlanOrderDayResponse {
    private Integer day;
    private List<TicketScenicResponse> scenics = Lists.newArrayList();

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public List<TicketScenicResponse> getScenics() {
        return scenics;
    }

    public void setScenics(List<TicketScenicResponse> scenics) {
        this.scenics = scenics;
    }
}
