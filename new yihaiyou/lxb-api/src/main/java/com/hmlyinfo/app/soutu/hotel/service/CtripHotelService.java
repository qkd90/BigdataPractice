package com.hmlyinfo.app.soutu.hotel.service;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.mapper.CtripHotelMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class CtripHotelService extends BaseService<CtripHotel, Long>{

	@Autowired
	private CtripHotelMapper<CtripHotel> mapper;
	
	@Override
	public BaseMapper<CtripHotel> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

	public List<CtripHotel> listColumns(Map<String, Object> paramMap, List<String> columns) {
		paramMap.put("needColumns", columns);
		return mapper.listColumns(paramMap);
	}
	
	public List<CtripHotel> listAndOrder(Map<String, Object> paramMap){
		return mapper.listAndOrder(paramMap);
	}
	
}
