package com.data.data.hmly.service.dao;

import java.util.List;

import com.framework.hibernate.util.RegUtil;
import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.UnitType;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;

@Repository
public class SysUnitDao extends DataAccess<SysUnit> {

	public List<SysUnit> findAllSite(Page page, UnitType site) {
		
		Criteria<SysUnit> criteria = new Criteria<SysUnit>(SysUnit.class);
		
//		criteria.in("sysSite", sysSites);
		criteria.eq("unitType", site);
		
		return findByCriteria(criteria,page);
		
	}

	public List<SysUnit> findUnitBySite(SysSite sysSite) {
		Criteria<SysUnit> criteria = new Criteria<SysUnit>(SysUnit.class);
		
		criteria.eq("sysSite", sysSite);
		criteria.ne("unitType", UnitType.department);
		
		return findByCriteria(criteria);
	}


}
