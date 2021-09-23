package com.data.spider.service.dao;

import com.data.spider.service.pojo.tb.TbDestination;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;


@Repository
public class TbDestinationDao extends DataAccess<TbDestination> {

	public TbDestination getById(Long id) {
		Criteria<TbDestination> criteria = new Criteria<TbDestination>(TbDestination.class);
		criteria.eq("id", id);
		return findUniqueByCriteria(criteria);
	}
}
