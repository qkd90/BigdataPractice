package com.hmlyinfo.app.soutu.plan.service;

import com.hmlyinfo.app.soutu.plan.domain.HdScenicInfo;
import com.hmlyinfo.app.soutu.plan.mapper.HdScenicInfoMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HdScenicInfoService extends BaseService<HdScenicInfo, Long>{

	@Autowired
	private HdScenicInfoMapper<HdScenicInfo> mapper;

	@Override
	public BaseMapper<HdScenicInfo> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

	/**
	 *传入一个str 如果是拼音按拼音查询，如果是中文按中文查询，返回列表
	 * @param params
	 * @return
	 */
	public List listName(Map<String, Object> params) {
		String name =  params.get("str")+"";
		if(name.matches("^[0-9a-zA-Z]*")){//如果是拼音的，按拼音查询
			params.put("spellName", name);
		}else{//非拼音用中文查询
			params.put("scenicName", name);
		}
		List<HdScenicInfo> list = list(params);
		return list;
	}
}
