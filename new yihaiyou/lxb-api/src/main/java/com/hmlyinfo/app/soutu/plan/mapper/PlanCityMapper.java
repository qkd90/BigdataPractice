package com.hmlyinfo.app.soutu.plan.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.plan.domain.PlanCity;

public interface PlanCityMapper <T extends PlanCity> extends BaseMapper<T>{
	public List<Long> listDistinct(Map<String, Object> paramMap);
	public void insertmore(List<PlanCity> planCity);
	
	public void deleteByPlan(Long planId);
	
	public long getMaxId();
}
