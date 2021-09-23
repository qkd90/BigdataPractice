package com.hmlyinfo.app.soutu.recplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.recplan.domain.RecplanTrip;
import com.hmlyinfo.app.soutu.recplan.mapper.RecplanTripMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class RecplanTripService extends BaseService<RecplanTrip, Long>{

	@Autowired
	private RecplanTripMapper<RecplanTrip> mapper;

	@Override
	public BaseMapper<RecplanTrip> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
