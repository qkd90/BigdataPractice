package com.hmlyinfo.app.soutu.plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanImage;
import com.hmlyinfo.app.soutu.plan.mapper.RecommendPlanImageMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class RecommendPlanImageService extends BaseService<RecommendPlanImage, Long>{

	@Autowired
	private RecommendPlanImageMapper<RecommendPlanImage> mapper;

	@Override
	public BaseMapper<RecommendPlanImage> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
