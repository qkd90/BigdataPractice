package com.hmlyinfo.app.soutu.weixin.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.weixin.domain.ReplyItemGroup;
import com.hmlyinfo.app.soutu.weixin.mapper.ReplyItemGroupMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class ReplyItemGroupService extends BaseService<ReplyItemGroup, Long>{

	@Autowired
	private ReplyItemGroupMapper<ReplyItemGroup> mapper;

	/**
	 * 条件搜索
	 * @param paramMap
	 * @return
	 */
	public int searchCount(Map<String, Object> paramMap)
	{
		paramMap.put("operCount", true);
		return mapper.searchCount(paramMap);
	}
	
	@Override
	public BaseMapper<ReplyItemGroup> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
