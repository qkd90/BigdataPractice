package com.hmlyinfo.app.soutu.scenicTicket.service;

import com.hmlyinfo.app.soutu.scenicTicket.domain.RefundOrder;
import com.hmlyinfo.app.soutu.scenicTicket.mapper.RefundOrderMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RefundOrderService extends BaseService<RefundOrder, Long> {

	@Autowired
	private RefundOrderMapper<RefundOrder> mapper;

	@Override
	public BaseMapper<RefundOrder> getMapper()
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
