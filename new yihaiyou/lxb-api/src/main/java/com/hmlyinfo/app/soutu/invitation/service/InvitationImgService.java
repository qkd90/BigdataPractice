package com.hmlyinfo.app.soutu.invitation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.invitation.domain.InvitationImg;
import com.hmlyinfo.app.soutu.invitation.mapper.InvitationImgMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class InvitationImgService extends BaseService<InvitationImg, Long>{

	@Autowired
	private InvitationImgMapper<InvitationImg> mapper;

	@Override
	public BaseMapper<InvitationImg> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
