package com.hmlyinfo.app.soutu.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenic.domain.City;
import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.base.cache.CacheProvider;
import com.hmlyinfo.base.cache.XMemcachedImpl;
import com.hmlyinfo.base.util.StringUtil;

@Service
public class DictService 
{
	private static final String CITY_KEY = "DICT_CITYS";
	private static CacheProvider cacheProvider = XMemcachedImpl.getInstance();
	
	@Autowired
	private CityService cityService;
	
	/**
	 * 获取城市字典
	 * @return
	 */
	public Map<String, Object> getCityDicts()
	{
		Map<String, Object> cityInfoMap = cacheProvider.get(CITY_KEY);
		if (cityInfoMap == null)
		{
			cityInfoMap = new HashMap<String, Object>();
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pageSize", 4000);
			List<City> cities = cityService.list(paramMap);
			for (int i = 0; i < cities.size(); i++) {
				String cityCode = cities.get(i).getCityCode() + "";
				String cityName = cities.get(i).getName();
				cityInfoMap.put(cityCode, cityName);
			}
			cacheProvider.set(CITY_KEY, cityInfoMap);
		}
		
		return cityInfoMap;
	}
	
	/**
	 * 获取单个城市字典
	 * @return
	 */
	public String getCityById(String cityCode)
	{
		Map<String, Object> cityMap = getCityDicts();
		String cityName = (String) cityMap.get(cityCode);
		if(!StringUtil.isEmpty(cityName))
		{
			cityName = cityName.replace("县","");
			cityName = cityName.replace("市","");
		}
		
		return cityName;
	}
}
