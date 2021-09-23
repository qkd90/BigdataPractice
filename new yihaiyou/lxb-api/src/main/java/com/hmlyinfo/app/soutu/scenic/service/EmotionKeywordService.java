package com.hmlyinfo.app.soutu.scenic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenic.domain.EmotionKeyword;
import com.hmlyinfo.app.soutu.scenic.mapper.EmotionKeywordMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class EmotionKeywordService extends BaseService<EmotionKeyword, Long>{

	@Autowired
	private EmotionKeywordMapper<EmotionKeyword> mapper;
	@Autowired
	private ScenicInfoService scenicInfoService;

	@Override
	public BaseMapper<EmotionKeyword> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	

}
