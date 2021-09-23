package com.hmlyinfo.app.soutu.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.order.domain.OrderContact;
import com.hmlyinfo.app.soutu.order.mapper.OrderContactMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class OrderContactService extends BaseService<OrderContact, Long>{

	@Autowired
	private OrderContactMapper<OrderContact> mapper;

	@Override
	public BaseMapper<OrderContact> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
