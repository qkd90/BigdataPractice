package com.hmlyinfo.app.soutu.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.plan.domain.PlanUrbanTrafficRel;
import com.hmlyinfo.app.soutu.plan.mapper.PlanUrbanTrafficRelMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class PlanUrbanTrafficRelService extends BaseService<PlanUrbanTrafficRel, Long>{

	@Autowired
	private PlanUrbanTrafficRelMapper<PlanUrbanTrafficRel> mapper;

	@Override
	public BaseMapper<PlanUrbanTrafficRel> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

	public void delScenicRel(Long planId)
	{
		mapper.delScenicRel(planId);
	}
	
	public void delHotelRel(Long planId)
	{
		mapper.delHotelRel(planId);
	}
}
