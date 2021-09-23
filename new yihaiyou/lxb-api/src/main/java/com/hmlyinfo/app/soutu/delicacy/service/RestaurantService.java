package com.hmlyinfo.app.soutu.delicacy.service;

import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.mapper.RestaurantMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RestaurantService extends BaseService<Restaurant, Long>{

    @Autowired
	private RestaurantMapper<Restaurant> restaurantMapper;
	@Override
	public BaseMapper<Restaurant> getMapper() {
		return restaurantMapper;
	}

	@Override
	public String getKey() {
		return null;
	}

	public List<Restaurant> listColumns(Map<String, Object> paramMap, List<String> columns) {
		paramMap.put("needColumns", columns);
		return restaurantMapper.listColumns(paramMap);
	}
}
