package com.hmlyinfo.app.soutu.scenicTicket.custom.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.app.soutu.scenicTicket.custom.domain.TicketCustom;
import com.hmlyinfo.app.soutu.scenicTicket.custom.mapper.TicketCustomMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class TicketCustomService extends BaseService<TicketCustom, Long>{

	@Autowired
	private TicketCustomMapper<TicketCustom> mapper;
	@Autowired
    private ScenicInfoService scenicInfoService;

	@Override
	public BaseMapper<TicketCustom> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	@Override
    public List<TicketCustom> list(Map<String, Object> paramMap) 
    {
		paramMap.put("status", "1");
        return super.list(paramMap);
    }
}
