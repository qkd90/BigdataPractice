package com.hmlyinfo.app.soutu.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.common.domain.Dictionary;
import com.hmlyinfo.app.soutu.common.mapper.DictionaryMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class DictionaryService extends BaseService<Dictionary, Long>{

	@Autowired
	private DictionaryMapper<Dictionary> mapper;

	@Override
	public BaseMapper<Dictionary> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	/**
	 * 系统按条件列出美食列表
	 * <ul>
	 * 	<li>必选:城市id{cityId}</li>
	 *  <li>url:/api/auth/dictionary/list</li>
	 * </ul>
	 *  
	 * @return 
	 */
	public List<Dictionary> getDictionary(Map<String, Object> paramMap) {
		return mapper.list(paramMap);
	}

}
