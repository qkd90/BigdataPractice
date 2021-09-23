package com.hmlyinfo.app.soutu.activity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.activity.domain.Reply;
import com.hmlyinfo.app.soutu.activity.mapper.ReplyMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class ReplyService extends BaseService<Reply, Long>{

	@Autowired
	private ReplyMapper<Reply> mapper;

	@Override
	public BaseMapper<Reply> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
