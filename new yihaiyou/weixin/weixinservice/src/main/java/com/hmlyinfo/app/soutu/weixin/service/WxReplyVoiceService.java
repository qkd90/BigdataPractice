package com.hmlyinfo.app.soutu.weixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.weixin.domain.WxReplyVoice;
import com.hmlyinfo.app.soutu.weixin.mapper.WxReplyVoiceMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class WxReplyVoiceService extends BaseService<WxReplyVoice, Long>{

	@Autowired
	private WxReplyVoiceMapper<WxReplyVoice> mapper;

	@Override
	public BaseMapper<WxReplyVoice> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
