package com.hmlyinfo.app.soutu.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanDay;
import com.hmlyinfo.app.soutu.plan.mapper.RecommendPlanDayMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class RecommendPlanDayService extends BaseService<RecommendPlanDay, Long>{

	@Autowired
	private RecommendPlanDayMapper<RecommendPlanDay> mapper;

	@Override
	public BaseMapper<RecommendPlanDay> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
