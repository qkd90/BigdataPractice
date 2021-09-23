package com.hmlyinfo.app.soutu.recplan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.recplan.domain.RecplanPhoto;
import com.hmlyinfo.app.soutu.recplan.mapper.RecplanPhotoMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class RecplanPhotoService extends BaseService<RecplanPhoto, Long>{

	@Autowired
	private RecplanPhotoMapper<RecplanPhoto> mapper;

	@Override
	public BaseMapper<RecplanPhoto> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
