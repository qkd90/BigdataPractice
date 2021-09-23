package com.data.data.hmly.service.lvxbang.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class PlanUpdateRequest {

	public Long id;
    public String name;
    public String tips;
    public Date startTime;
    public Long cityId;

	public List<PlanDayUpdateRequest> days = new ArrayList<PlanDayUpdateRequest>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<PlanDayUpdateRequest> getDays() {
        return days;
    }

    public void setDays(List<PlanDayUpdateRequest> days) {
        this.days = days;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
