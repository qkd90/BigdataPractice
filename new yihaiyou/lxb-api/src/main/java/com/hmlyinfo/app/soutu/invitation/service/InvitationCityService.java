package com.hmlyinfo.app.soutu.invitation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.invitation.domain.InvitationCity;
import com.hmlyinfo.app.soutu.invitation.mapper.InvitationCityMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class InvitationCityService extends BaseService<InvitationCity, Long>{

	@Autowired
	private InvitationCityMapper<InvitationCity> mapper;

	@Override
	public BaseMapper<InvitationCity> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
