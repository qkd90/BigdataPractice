package com.hmlyinfo.app.soutu.scenic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenic.domain.ScenicKeyword;
import com.hmlyinfo.app.soutu.scenic.mapper.ScenicKeywordMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class ScenicKeywordService extends BaseService<ScenicKeyword, Long>{

	@Autowired
	private ScenicKeywordMapper<ScenicKeyword> mapper;
	@Autowired
	private ScenicInfoService scenicInfoService;

	@Override
	public BaseMapper<ScenicKeyword> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
