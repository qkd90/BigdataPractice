package com.hmlyinfo.app.soutu.plan.mapper;

import java.util.List;

import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface PlanDaysMapper <T extends PlanDay> extends BaseMapper<T>
{
	public void insertmore(List<PlanDay> planDays);
	public void delByPlan(Long planId);
	/**
     * 根据行程id和天更新每天的酒店
     * @Title: updateHotel
     * @email shiqingju@hmlyinfo.com
     * @date 2015年11月9日 下午2:44:12
     *
     * @param plan
     *
     * @return void
     * @throws
     */
    public void updateHotel(PlanDay planday);
    
    public long getMaxId();
}
