package com.hmlyinfo.app.soutu.signet.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.signet.domain.Merchant;
import com.hmlyinfo.app.soutu.signet.domain.SignetRoad;
import com.hmlyinfo.app.soutu.signet.mapper.MerchantMapper;
import com.hmlyinfo.app.soutu.signet.mapper.SignetRoadMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;

@Service
public class SignetRoadService extends BaseService<SignetRoad, Long>{

	@Autowired
	private SignetRoadMapper<SignetRoad> mapper;
	@Autowired
	private MerchantMapper<Merchant> merchantMapper;

	@Override
	public BaseMapper<SignetRoad> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

	//
	//查询SIgnetRoad的商家列表和子节点列表
	//pw
	//
	public SignetRoad getChildList(String id)
	{
		SignetRoad signetRoad = mapper.selById(id);
		List<SignetRoad> childList = mapper.list(Collections.<String, Object>singletonMap("parentId", id));
		List childIds = ListUtil.getIdList(childList, "id");
		List<Merchant> merchantList = merchantMapper.list(Collections.<String, Object>singletonMap("roadId", id));
		List merchantIds = ListUtil.getIdList(merchantList, "id");
		signetRoad.setChildIds(childIds);
		signetRoad.setMerchantIds(merchantIds);
		return signetRoad;
	}
}
