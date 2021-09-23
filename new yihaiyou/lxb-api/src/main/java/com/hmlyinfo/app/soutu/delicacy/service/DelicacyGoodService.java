package com.hmlyinfo.app.soutu.delicacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.delicacy.domain.DelicacyGood;
import com.hmlyinfo.app.soutu.delicacy.mapper.DelicacyGoodMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class DelicacyGoodService extends BaseService<DelicacyGood, Long>{

	@Autowired
	private DelicacyGoodMapper<DelicacyGood> mapper;

	@Override
	public BaseMapper<DelicacyGood> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
