package com.hmlyinfo.app.soutu.signet.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.signet.domain.SignetImage;
import com.hmlyinfo.app.soutu.signet.domain.SignetRecord;
import com.hmlyinfo.app.soutu.signet.mapper.SignetImageMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class SignetImageService extends BaseService<SignetImage, Long>{

	@Autowired
	private SignetImageMapper<SignetImage> mapper;
	@Autowired
	private SignetRecordService recordService;

	@Override
	public BaseMapper<SignetImage> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	/**
	 * 根据bookid更新签章图片
	 * @param bookId
	 * @param memo
	 */
	public void updateBybookid(String merchantId, SignetImage si)
	{
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("bookId", si.getBookId());
		paramMap.put("merchantId", merchantId);
		
		// 查询签章记录
		SignetRecord sr = (SignetRecord) recordService.one(paramMap);
		if (sr == null)
		{
			sr = new SignetRecord();
			sr.setBookId(si.getBookId());
			sr.setMerchantId(Long.valueOf(merchantId));
			recordService.insert(sr);
		}
		si.setSignetRecordId(sr.getId());
		paramMap.put("signetRecordId", sr.getId());
		
		SignetImage lsi = (SignetImage) one(paramMap);
		
		if (lsi != null)
		{
			si.setId(lsi.getId());
			update(si);
		}
		else
		{
			insert(si);
		}
	}

}
