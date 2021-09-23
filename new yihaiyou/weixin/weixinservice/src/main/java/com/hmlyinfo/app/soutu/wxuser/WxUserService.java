package com.hmlyinfo.app.soutu.wxuser;

import java.util.Map;

public interface WxUserService {
	
	public void updateFollower(Map<String, Object> paramMap);
	// TODO:通过openId查询用户信息，进行更新地理位置,参考api方法getUserByOpenId
	public void updateUserLocation(String openId, Map<String, Object> paramMap);
	
}
