package com.hmlyinfo.app.soutu.scenic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenic.domain.ScenicOther;
import com.hmlyinfo.app.soutu.scenic.mapper.ScenicOtherMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class ScenicOtherService extends BaseService<ScenicOther, Long>{

	@Autowired
	private ScenicOtherMapper<ScenicOther> mapper;

	@Override
	public BaseMapper<ScenicOther> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
