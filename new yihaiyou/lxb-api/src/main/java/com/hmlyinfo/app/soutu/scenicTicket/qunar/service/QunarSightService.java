package com.hmlyinfo.app.soutu.scenicTicket.qunar.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarSight;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.mapper.QunarSightMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class QunarSightService extends BaseService<QunarSight, Long>{

	@Autowired
	private QunarSightMapper<QunarSight> mapper;
	@Autowired
	private QunarService qunarService;

	@Override
	public BaseMapper<QunarSight> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	public void getSightInfos()
	{
		boolean flag = true;
		int pageNo = 1;
		while(flag)
		{
			Map<String, Object> sightMap = qunarService.sightInfos(pageNo + "");
			List<Map<String, Object>> sightList = (List<Map<String, Object>>) sightMap.get("sightInfos");
			if(sightList.isEmpty()){
				flag = false;
				continue;
			}
			for(Map<String, Object> sight : sightList){
				String id = (String) sight.get("sightId");
				QunarSight newQunarSight = new QunarSight();
				newQunarSight.setId(Long.parseLong(id));
				newQunarSight.setName((String) sight.get("name"));
				newQunarSight.setNamePinyin((String) sight.get("namePinyin"));
				newQunarSight.setAddress((String) sight.get("address"));
				newQunarSight.setCity((String) sight.get("city"));
				newQunarSight.setCountry((String) sight.get("country"));
				newQunarSight.setProvince((String) sight.get("province"));
				newQunarSight.setAreaNamePath((String) sight.get("areaNamePath"));
				if(mapper.selById(id) == null){
					mapper.insert(newQunarSight);
				}else{
					mapper.update(newQunarSight);
				}
			}
			pageNo++;
		}
	}

}
