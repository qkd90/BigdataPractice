package com.hmlyinfo.app.soutu.scenicTicket.qunar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarPassenger;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.mapper.QunarPassengerMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class QunarPassengerService extends BaseService<QunarPassenger, Long>{

	@Autowired
	private QunarPassengerMapper<QunarPassenger> mapper;

	@Override
	public BaseMapper<QunarPassenger> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
