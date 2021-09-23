package com.hmlyinfo.app.soutu.scenic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenic.domain.HdMapLevel;
import com.hmlyinfo.app.soutu.scenic.mapper.HdMapLevelMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class HdMapLevelService extends BaseService<HdMapLevel, Long>{

	@Autowired
	private HdMapLevelMapper<HdMapLevel> mapper;

	@Override
	public BaseMapper<HdMapLevel> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
