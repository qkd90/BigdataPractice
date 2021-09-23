package com.data.data.service;

import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.data.service.dao.ScenicInfoDao;
import com.data.data.service.pojo.ScenicInfo;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;

@Service
public class ScenicService {
	@Autowired
	private ScenicInfoDao	scenicInfoDao;

	public ScenicInfo findCityTopScenic(String cityCode, List<Long> noContainsIds) {
		Criteria<ScenicInfo> c = new Criteria<ScenicInfo>(ScenicInfo.class);
		c.like("city_code", cityCode);
		c.notin("id", noContainsIds);
		c.addOrder(Order.asc("ranking"));
		Page page = new Page(1, 1);
		List<ScenicInfo> scenics = scenicInfoDao.findByCriteria(c, page);
		return scenics.isEmpty() ? null : scenics.get(0);
	}
}
