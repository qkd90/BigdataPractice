package com.hmlyinfo.app.soutu.account.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface UserMapper<T extends User> extends BaseMapper<T> {
	
	public User summaryinfo(long id);
    public void updatePassword(Map<String, Object> params);
    public List<User> listColumns(Map<String, Object> params);
    
}
