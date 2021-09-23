package com.hmlyinfo.app.soutu.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.hotel.domain.HotelRelation;
import com.hmlyinfo.app.soutu.hotel.mapper.HotelRelationMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class HotelRelationService extends BaseService<HotelRelation, Long>{

	@Autowired
	private HotelRelationMapper<HotelRelation> mapper;

	@Override
	public BaseMapper<HotelRelation> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
