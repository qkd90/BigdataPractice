package com.hmlyinfo.app.soutu.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.common.domain.Survey;
import com.hmlyinfo.app.soutu.common.mapper.SurveyMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class SurveyService extends BaseService<Survey, Long>{

	@Autowired
	private SurveyMapper<Survey> mapper;

	@Override
	public BaseMapper<Survey> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
