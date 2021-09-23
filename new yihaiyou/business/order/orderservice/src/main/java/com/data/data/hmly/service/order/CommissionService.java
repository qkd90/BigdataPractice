package com.data.data.hmly.service.order;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.dao.CommissionDao;
import com.data.data.hmly.service.order.entity.Commission;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vacuity on 15/11/4.
 */

@Service
public class CommissionService {

	@Resource
	private CommissionDao dao;

	public List<Commission> listByUser(User user, Page page) {
		Commission commission = new Commission();
		commission.setUser(user);
		return list(commission, page, "createdTime");
	}

	public List<Commission> list(Commission commission, Page page, String... orderProperties) {
		Criteria<Commission> criteria = createCriteria(commission, orderProperties);
		if (page == null) {
			return dao.findByCriteria(criteria);
		}
		return dao.findByCriteria(criteria, page);
	}

	public Criteria<Commission> createCriteria(Commission commission, String... orderProperties) {
		Criteria<Commission> criteria = new Criteria<Commission>(Commission.class);
		if (orderProperties.length == 2) {
			criteria.orderBy(orderProperties[0], orderProperties[1]);
		} else if (orderProperties.length == 1) {
			criteria.orderBy(Order.desc(orderProperties[0]));
		}
		if (commission == null) {
			return criteria;
		}
		if (commission.getOrderDetail() != null) {
			OrderDetail orderDetail = commission.getOrderDetail();
			DetachedCriteria orderDetailCriteria = criteria.createCriteria("orderDetail", "orderDetail", JoinType.INNER_JOIN);
			if (orderDetail.getId() != null) {
				orderDetailCriteria.add(Restrictions.eq("id", orderDetail.getId()));
			}
		}
		if (commission.getUser() != null) {
			User user = commission.getUser();
			DetachedCriteria userCriteria = criteria.createCriteria("user", "user", JoinType.INNER_JOIN);
			if (user.getId() != null) {
				userCriteria.add(Restrictions.eq("id", user.getId()));
			}
		}
		return criteria;
	}


	public void save(Commission commission) {
		dao.save(commission);
	}
}
