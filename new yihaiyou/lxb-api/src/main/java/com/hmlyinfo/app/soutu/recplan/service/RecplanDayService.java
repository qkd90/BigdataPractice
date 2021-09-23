package com.hmlyinfo.app.soutu.recplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.recplan.domain.RecplanDay;
import com.hmlyinfo.app.soutu.recplan.mapper.RecplanDayMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class RecplanDayService extends BaseService<RecplanDay, Long>{

	@Autowired
	private RecplanDayMapper<RecplanDay> mapper;

	@Override
	public BaseMapper<RecplanDay> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
