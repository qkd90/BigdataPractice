package com.hmlyinfo.app.soutu.weixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.weixin.domain.WxReply;
import com.hmlyinfo.app.soutu.weixin.mapper.WxReplyMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class WxReplyService extends BaseService<WxReply, Long>{

	@Autowired
	private WxReplyMapper<WxReply> mapper;

	@Override
	public BaseMapper<WxReply> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
