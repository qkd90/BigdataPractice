package com.hmlyinfo.app.soutu.activity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.activity.domain.Apply;
import com.hmlyinfo.app.soutu.activity.mapper.ApplyMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplyService extends BaseService<Apply, Long>{

	@Autowired
	private ApplyMapper<Apply> mapper;

	@Override
	public BaseMapper<Apply> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

	/**
	 * 用户是否已经申请
	 * @return
	 */
	@Transactional
	public boolean isUserApply(long userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		List<Apply> applyList = list(paramMap);
		return !applyList.isEmpty();
	}

}
