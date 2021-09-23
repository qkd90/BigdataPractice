package com.hmlyinfo.app.soutu.bargain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanTrip;
import com.hmlyinfo.app.soutu.bargain.mapper.BargainPlanTripMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class BargainPlanTripService extends BaseService<BargainPlanTrip, Long>{

	@Autowired
	private BargainPlanTripMapper<BargainPlanTrip> mapper;

	@Override
	public BaseMapper<BargainPlanTrip> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
