package com.hmlyinfo.app.soutu.weixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.weixin.domain.WxGraphic;
import com.hmlyinfo.app.soutu.weixin.mapper.WxGraphicMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class WxGraphicService extends BaseService<WxGraphic, Long>{

	@Autowired
	private WxGraphicMapper<WxGraphic> mapper;

	@Override
	public BaseMapper<WxGraphic> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
