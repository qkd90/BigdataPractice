package com.hmlyinfo.app.soutu.weixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.weixin.domain.WxReplyLog;
import com.hmlyinfo.app.soutu.weixin.mapper.WxReplyLogMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class WxReplyLogService extends BaseService<WxReplyLog, Long>{

	@Autowired
	private WxReplyLogMapper<WxReplyLog> mapper;

	@Override
	public BaseMapper<WxReplyLog> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
