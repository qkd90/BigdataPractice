package com.hmlyinfo.app.soutu.weixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.weixin.domain.Graphic;
import com.hmlyinfo.app.soutu.weixin.mapper.GraphicMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class ReplyGraphicService extends BaseService<Graphic, Long>{

	@Autowired
	private GraphicMapper<Graphic> mapper;

	@Override
	public BaseMapper<Graphic> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
