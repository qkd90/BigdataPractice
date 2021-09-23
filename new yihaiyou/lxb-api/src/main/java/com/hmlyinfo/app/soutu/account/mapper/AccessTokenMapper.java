package com.hmlyinfo.app.soutu.account.mapper;

import java.util.Map;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.account.domain.AccessToken;

public interface AccessTokenMapper <T extends AccessToken> extends BaseMapper<T>{
	
	void delClientTokenByUser(Map<String, Object> paramMap);
}
