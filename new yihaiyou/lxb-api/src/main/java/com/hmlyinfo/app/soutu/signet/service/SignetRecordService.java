package com.hmlyinfo.app.soutu.signet.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.signet.domain.SignetRecord;
import com.hmlyinfo.app.soutu.signet.mapper.SignetRecordMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class SignetRecordService extends BaseService<SignetRecord, Long>{

	@Autowired
	private SignetRecordMapper<SignetRecord> mapper;

	@Override
	public BaseMapper<SignetRecord> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	public SignetRecord addRecord(SignetRecord sr)
	{
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("bookId", sr.getBookId());
		paramMap.put("merchantId", sr.getMerchantId());
		
		SignetRecord csr = (SignetRecord)one(paramMap);
		if (csr == null)
		{
			sr.setSignetFlag("T");
			insert(sr);
		}
		else
		{
			csr.setSignetFlag("T");
			update(csr);
		}
		
		return sr;
	}

}
