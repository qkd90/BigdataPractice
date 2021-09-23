package com.hmlyinfo.app.soutu.weixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.weixin.domain.ReplyItem;
import com.hmlyinfo.app.soutu.weixin.mapper.ReplyItemMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class ReplyItemService extends BaseService<ReplyItem, Long>{

	@Autowired
	private ReplyItemMapper<ReplyItem> mapper;

	@Override
	public BaseMapper<ReplyItem> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
