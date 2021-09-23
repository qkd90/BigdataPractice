package com.hmlyinfo.app.soutu.invitation.mapper;

import java.util.Map;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.invitation.domain.Invitation;

public interface InvitationMapper <T extends Invitation> extends BaseMapper<T>{
	
	public void changeNum(Map<String, Object> paramMap);
	
}
