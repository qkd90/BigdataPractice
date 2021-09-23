package com.hmlyinfo.app.soutu.plan.mapper;

import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.base.persistent.BaseMapper;

import java.util.List;

public interface PlanTripMapper<T extends PlanTrip> extends BaseMapper<T> {
    public void insertmore(List<PlanTrip> planTrip);

    public void delByDay(Long planDaysId);
    public void delByPlan(Long planId);
    
    public long getMaxId();

}
