package com.hmlyinfo.app.soutu.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.hotel.domain.CtripPrice;
import com.hmlyinfo.app.soutu.hotel.mapper.CtripPriceMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class CtripPriceService extends BaseService<CtripPrice, Long>{

	@Autowired
	private CtripPriceMapper<CtripPrice> mapper;

	@Override
	public BaseMapper<CtripPrice> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
