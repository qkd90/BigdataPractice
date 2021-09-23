package com.hmlyinfo.app.soutu.weixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.weixin.domain.ReplyText;
import com.hmlyinfo.app.soutu.weixin.mapper.ReplyTextMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class ReplyTextService extends BaseService<ReplyText, Long>{

	@Autowired
	private ReplyTextMapper<ReplyText> mapper;

	@Override
	public BaseMapper<ReplyText> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
