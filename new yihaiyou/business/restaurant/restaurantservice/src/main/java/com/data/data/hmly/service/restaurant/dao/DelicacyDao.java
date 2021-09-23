package com.data.data.hmly.service.restaurant.dao;

import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;

/**
 * Created by guoshijie on 2015/12/8.
 */
@Repository
public class DelicacyDao extends DataAccess<Delicacy> {

	/**
	 * 目的地详情必游景点排行榜
	 * @param sceCodition
	 * @param order
	 * @param tbAreas
	 * @param page
	 * @return
	 */
	public List<Delicacy> getTopSceByDestination(Delicacy sceCodition,
			Order order, List<TbArea> tbAreas, Page page) {
		Criteria<Delicacy> criteria = createCriteria(sceCodition, order, tbAreas);
		return findByCriteria(criteria, page);
	}

	private Criteria<Delicacy> createCriteria(Delicacy scenicInfo, Order order, List<TbArea> tbAreas) {
		
		Criteria<Delicacy> criteria = new Criteria<Delicacy>(Delicacy.class);
		criteria.orderBy(order.asc("ranking"));
		if (scenicInfo == null) {
			return criteria;
		}
		if (!tbAreas.isEmpty()) {
			criteria.in("city", tbAreas);
		}
		criteria.eq("status", 1);
//		criteria.orderBy("", sortOrder);
//		if (scenicInfo.getLabelItems() != null && !scenicInfo.getLabelItems().isEmpty()) {
//			DetachedCriteria dclabelItem = criteria.createCriteria("labelItems", "labelItem");
//			for (LabelItem labelItem : scenicInfo.getLabelItems()) {
//				dclabelItem.add(Restrictions.eq("label.id", labelItem.getLabel().getId()));
//			}
//		}
//		
		return criteria;
	}

	
}
