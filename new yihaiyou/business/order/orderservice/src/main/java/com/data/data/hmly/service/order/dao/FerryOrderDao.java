package com.data.data.hmly.service.order.dao;

import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-11-21,0021.
 */
@Repository
public class FerryOrderDao  extends DataAccess<FerryOrder> {

    /**
     * 账单详情查询
     * @param orderBillSummary
     * @param page
     * @return
     */
    public List<FerryOrder> getOrderBillDetail(OrderBillSummary orderBillSummary, Page page) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("select new FerryOrder(o.ferryNumber, o.orderNumber, o.flightNumber, o.flightLineName, o.departTime, o.amount, o.orderBillPrice, o.returnAmount, o.refundDate) ");
        hql.append("from FerryOrder o where 1 = 1 ");
        if (orderBillSummary.getId() != null) {
            hql.append("and o.billSummaryId = :billSummaryId ");
            params.put("billSummaryId", orderBillSummary.getId());
        }
        if (orderBillSummary.getRefundBillSummaryId() != null) {
            hql.append("and o.refundBillSummaryId = :refundBillSummaryId ");
            params.put("refundBillSummaryId", orderBillSummary.getRefundBillSummaryId());
        }
        hql.append("order by o.createTime desc ");
        if (page != null) {
            return findByHQL2ForNew(hql.toString(), page, params);
        }
        return findByHQL2ForNew(hql.toString(), params);
    }

    /**
     * 退款订单查询
     * @param billSummaryId 如果是重新生成，需带此参数，以做合并
     * @return
     */
    public List<FerryOrder> getRefundOrderDetail(Date billDateStart, Date billDateEnd, Long billSummaryId) {
        Criteria<FerryOrder> criteria = new Criteria<FerryOrder>(FerryOrder.class);
        criteria.ge("refundDate", billDateStart);
        criteria.lt("refundDate", billDateEnd);
        criteria.eq("status", OrderStatus.REFUND);
        if (billSummaryId != null) {
            criteria.or(Restrictions.eq("refundBillSummaryId", billSummaryId), Restrictions.isNull("refundBillSummaryId"));
        } else {
            criteria.isNull("refundBillSummaryId");
        }
        return findByCriteria(criteria);
    }
}
