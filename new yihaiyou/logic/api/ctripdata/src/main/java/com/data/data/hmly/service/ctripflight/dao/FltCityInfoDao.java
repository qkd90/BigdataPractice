package com.data.data.hmly.service.ctripflight.dao;

import com.data.data.hmly.service.ctripflight.entity.FltCityInfo;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FltCityInfoDao extends DataAccess<FltCityInfo> {
	
	
	

	public List<FltCityInfo> getCityCodeList() {
		Criteria<FltCityInfo> criteria = new Criteria<FltCityInfo>(FltCityInfo.class);
		criteria.eq("countryName", "中国");
		criteria.not("cityCode", "");
		return findByCriteria(criteria);
	}
	
	
	
}
