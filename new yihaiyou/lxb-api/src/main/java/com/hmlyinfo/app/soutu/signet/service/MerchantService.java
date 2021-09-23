package com.hmlyinfo.app.soutu.signet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.signet.domain.Merchant;
import com.hmlyinfo.app.soutu.signet.mapper.MerchantMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class MerchantService extends BaseService<Merchant, Long>{

	@Autowired
	private MerchantMapper<Merchant> mapper;

	@Override
	public BaseMapper<Merchant> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

}
