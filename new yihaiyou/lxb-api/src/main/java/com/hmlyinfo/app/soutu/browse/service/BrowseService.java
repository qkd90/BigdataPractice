package com.hmlyinfo.app.soutu.browse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.activity.domain.Activity;
import com.hmlyinfo.app.soutu.browse.domain.Browse;
import com.hmlyinfo.app.soutu.browse.mapper.BrowseMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class BrowseService extends BaseService<Browse, Long>{

	@Autowired
	private BrowseMapper<Browse> mapper;

	@Override
	public BaseMapper<Browse> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
