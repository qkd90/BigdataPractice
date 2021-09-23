package com.hmlyinfo.app.soutu.invitation.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationComment;

public interface InvitationCommentMapper <T extends InvitationComment> extends BaseMapper<T>{
	
	public List<InvitationComment> listcomment(Map<String, Object> paramMap);
	
}
