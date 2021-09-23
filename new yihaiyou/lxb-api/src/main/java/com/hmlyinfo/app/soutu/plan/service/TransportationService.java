package com.hmlyinfo.app.soutu.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.plan.domain.Transportation;
import com.hmlyinfo.app.soutu.plan.mapper.TransportationMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class TransportationService extends BaseService<Transportation, Long>{

	@Autowired
	private TransportationMapper<Transportation> mapper;

	@Override
	public BaseMapper<Transportation> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
