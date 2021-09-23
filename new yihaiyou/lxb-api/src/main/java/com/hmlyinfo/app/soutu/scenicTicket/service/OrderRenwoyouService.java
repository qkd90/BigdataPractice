package com.hmlyinfo.app.soutu.scenicTicket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenicTicket.domain.OrderRenwoyou;
import com.hmlyinfo.app.soutu.scenicTicket.mapper.OrderRenwoyouMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class OrderRenwoyouService extends BaseService<OrderRenwoyou, Long>{

	@Autowired
	private OrderRenwoyouMapper<OrderRenwoyou> mapper;

	@Override
	public BaseMapper<OrderRenwoyou> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
