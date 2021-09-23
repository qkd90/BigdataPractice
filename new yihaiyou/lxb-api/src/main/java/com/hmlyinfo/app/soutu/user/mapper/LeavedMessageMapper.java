package com.hmlyinfo.app.soutu.user.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.user.domain.LeavedMessage;

public interface LeavedMessageMapper <T extends LeavedMessage> extends BaseMapper<T>{
	
	public List<LeavedMessage> listrecord(Map<String, Object> paramMap);
	
}
