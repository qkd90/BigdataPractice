package com.hmlyinfo.app.soutu.scenicTicket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketSubOrder;
import com.hmlyinfo.app.soutu.scenicTicket.mapper.ScenicTicketSubOrderMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class ScenicTicketSubOrderService extends BaseService<ScenicTicketSubOrder, Long>{

	@Autowired
	private ScenicTicketSubOrderMapper<ScenicTicketSubOrder> mapper;

	@Override
	public BaseMapper<ScenicTicketSubOrder> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
