package com.hmlyinfo.app.soutu.scenic.service;

import com.hmlyinfo.app.soutu.scenic.domain.City;
import com.hmlyinfo.app.soutu.scenic.mapper.CityMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityService extends BaseService<City, Long>{

	@Autowired
	private CityMapper<City> mapper;

	@Override
	public BaseMapper<City> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}

    public City getByCityCode(String cityCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cityCode", cityCode);
        List<City> cityList = mapper.list(params);
        if (cityList != null && !cityList.isEmpty()) {
            return cityList.get(0);
        }
        return null;
    }

}
