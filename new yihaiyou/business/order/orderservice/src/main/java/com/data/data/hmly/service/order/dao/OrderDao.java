package com.data.data.hmly.service.order.dao;

import com.data.data.hmly.service.order.entity.Order;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

/**
 * Created by guoshijie on 2015/10/13.
 */
@Repository
public class OrderDao extends DataAccess<Order> {

	public Order get(Long id) {
		Criteria<Order> criteria = new Criteria<Order>(Order.class);
		criteria.eq("id", id);
		return findUniqueByCriteria(criteria);
	}

	public void delete(Long id) {
		Order order = get(id);
		delete(order);
	}

	public void save(Order order) {
		order.setWechatCode(null);
		saveOrUpdate(order, order.getId());
	}

	public void sendMessage(Order order) {
		// 此方法仅为了下单后发送提醒消息
	}
}
