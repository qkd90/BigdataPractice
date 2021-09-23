package com.data.data.hmly.service.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.entity.TbArea;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

@Repository
public class TbAreaDao extends DataAccess<TbArea> {

	public TbArea getById(Long id) {
		Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
		criteria.eq("id", id);
		return findUniqueByCriteria(criteria);
		// String hql = "from TbArea where city_code = '"+cityCode+"'";
		// return findOneByHQL(hql);
	}

	public void doRecommendCities(List<Long> ids) {
		updateByHQL("update TbArea set recommended=false");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		updateByHQL2("update TbArea set recommended=true where id in :ids", params);
	}

	public List<TbArea> findCityByPro(Long id) {
		Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
		criteria.eq("level", 2);
		criteria.eq("father.id", id);
		List<TbArea> cityList = findByCriteria(criteria);
		List<TbArea> areas = new ArrayList<TbArea>();
		for (TbArea area : cityList){
			areas = findCityByCity(area.getId());
			areas.add(area);
		}
		return cityList;
	}
	public List<TbArea> findCityByCity(Long id) {
		Criteria<TbArea> criteria = new Criteria<TbArea>(TbArea.class);
//		criteria.eq("level", 3);
		criteria.eq("father.id", id);
		return findByCriteria(criteria);
	}


}
