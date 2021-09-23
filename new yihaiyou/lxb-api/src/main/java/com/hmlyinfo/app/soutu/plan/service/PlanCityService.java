package com.hmlyinfo.app.soutu.plan.service;

import com.hmlyinfo.app.soutu.plan.domain.PlanCity;
import com.hmlyinfo.app.soutu.plan.mapper.PlanCityMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanCityService extends BaseService<PlanCity, Long> {

	@Autowired
	private PlanCityMapper<PlanCity> mapper;

	@Override
	public BaseMapper<PlanCity> getMapper() {
		return mapper;
	}

	@Override
	public String getKey() {
		return "id";
	}

	@Transactional
	public void insertList(List<PlanCity> cityList) {
		
		long maxId = mapper.getMaxId();
		for (PlanCity city : cityList)
		{
			city.setId(++maxId);
		}
		mapper.insertmore(cityList);
	}
	
	public void deleteByPlan(Long planId)
	{
		mapper.deleteByPlan(planId);
	}
}
