package com.data.spider.service.dao;

import com.data.spider.service.pojo.tb.TbArea;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
