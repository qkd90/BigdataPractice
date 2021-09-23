package com.hmlyinfo.app.soutu.scenicTicket.qunar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarContant;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.mapper.QunarContantMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class QunarContantService extends BaseService<QunarContant, Long>{

	@Autowired
	private QunarContantMapper<QunarContant> mapper;

	@Override
	public BaseMapper<QunarContant> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
