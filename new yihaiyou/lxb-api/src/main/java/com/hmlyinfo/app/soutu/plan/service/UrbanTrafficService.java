package com.hmlyinfo.app.soutu.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.plan.domain.UrbanTraffic;
import com.hmlyinfo.app.soutu.plan.mapper.UrbanTrafficMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class UrbanTrafficService extends BaseService<UrbanTraffic, Long>{

	@Autowired
	private UrbanTrafficMapper<UrbanTraffic> mapper;

	@Override
	public BaseMapper<UrbanTraffic> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
