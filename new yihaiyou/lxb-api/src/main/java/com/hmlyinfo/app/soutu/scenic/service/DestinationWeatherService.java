package com.hmlyinfo.app.soutu.scenic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenic.domain.DestinationWeather;
import com.hmlyinfo.app.soutu.scenic.mapper.DestinationWeatherMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class DestinationWeatherService extends BaseService<DestinationWeather, Long>{

	@Autowired
	private DestinationWeatherMapper<DestinationWeather> mapper;

	@Override
	public BaseMapper<DestinationWeather> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
