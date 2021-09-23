package com.data.spider.service.tb;

import com.data.spider.service.dao.TbDestinationDao;
import com.data.spider.service.pojo.tb.TbDestination;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbDestinationService {

	@Resource
	private TbDestinationDao TbDestinationDao;
	public List<TbDestination> list(TbDestination destination, Order order) {
		Criteria<TbDestination> criteria = createCriteria(destination, order);
		return TbDestinationDao.findByCriteria(criteria);
	}
	private Criteria<TbDestination> createCriteria(TbDestination destination, Order order) {
		Criteria<TbDestination> criteria = new Criteria<TbDestination>(TbDestination.class);
		if (order != null) {
			criteria.orderBy(order);
		}
		if (destination == null) {
			return criteria;
		}
		if (!StringUtils.isBlank(destination.getName())) {
			criteria.eq(("name"), "%" + destination.getName() + "%");
		}
		return criteria;
	}
	public void save(TbDestination TbDestination) {
		TbDestinationDao.save(TbDestination);
	}
	public void update(TbDestination TbDestination) {
		TbDestinationDao.update(TbDestination);
	}
}
