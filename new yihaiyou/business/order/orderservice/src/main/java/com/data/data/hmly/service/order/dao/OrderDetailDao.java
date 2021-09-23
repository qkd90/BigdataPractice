package com.data.data.hmly.service.order.dao;

import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by guoshijie on 2015/10/14.
 */
@Repository
public class OrderDetailDao extends DataAccess<OrderDetail> {

	public List<OrderDetail> getByOrderId(Long orderId) {
		Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
//        criteria.createCriteria("order", "order");
		criteria.eq("order.id", orderId);
		return findByCriteria(criteria);
	}

	/**
	 * 退款订单查询
	 * @param billSummaryId 如果是重新生成，需带此参数，以做合并
	 * @return
	 */
	public List<OrderDetail> getRefundOrderDetail(Date billDateStart, Date billDateEnd, Long companyUnitId, Long billSummaryId) {
		Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
		criteria.createCriteria("product", "p", JoinType.INNER_JOIN);
		criteria.eq("p.companyUnit.id", companyUnitId);
		criteria.ge("refundDate", billDateStart);
		criteria.lt("refundDate", billDateEnd);
		criteria.eq("status", OrderDetailStatus.REFUNDED);
		if (billSummaryId != null) {
			criteria.or(Restrictions.eq("refundBillSummaryId", billSummaryId), Restrictions.isNull("refundBillSummaryId"));
		} else {
			criteria.isNull("refundBillSummaryId");
		}
		return findByCriteria(criteria);
	}

	public OrderDetail get(Long id) {
		Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
        criteria.createCriteria("order");
		criteria.eq("id", id);
		return findUniqueByCriteria(criteria);
	}

	public void delete(Long id) {
		OrderDetail order = get(id);
		delete(order);
	}

	public OrderDetail findUniqueBy(String realOrderId) {
		Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
		criteria.eq("realOrderId", realOrderId);
		return findUniqueByCriteria(criteria);
	}

	public void save(Order order) {
		saveOrUpdate(order, order.getId());
	}
}
