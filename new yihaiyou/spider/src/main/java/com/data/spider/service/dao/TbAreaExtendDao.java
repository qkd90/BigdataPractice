package com.data.spider.service.dao;

import com.data.spider.service.pojo.tb.TbAreaExtend;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TbAreaExtendDao extends DataAccess<TbAreaExtend> {

	public TbAreaExtend getById(Long id) {
		Criteria<TbAreaExtend> criteria = new Criteria<TbAreaExtend>(TbAreaExtend.class);
		criteria.eq("id", id);
		return findUniqueByCriteria(criteria);
		// String hql = "from TbAreaExtend where city_code = '"+cityCode+"'";
		// return findOneByHQL(hql);
	}

	public void doRecommendCities(List<Long> ids) {
		updateByHQL("update TbAreaExtend set recommended=false");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		updateByHQL2("update TbAreaExtend set recommended=true where id in :ids", params);
	}

}
