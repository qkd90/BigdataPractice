package com.data.data.hmly.service.order.dao;

import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.ShenzhouOrder;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-09-08,0008.
 */
@Repository
public class ShenzhouOrderDao extends DataAccess<ShenzhouOrder> {

    /**
     * 账单详情查询
     * @param orderBillSummary
     * @param page
     * @return
     */
    public List<ShenzhouOrder> getOrderBillDetail(OrderBillSummary orderBillSummary, Page page) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("select new ShenzhouOrder(o.shenzhouOrderId, o.orderNo, o.passengerMobile, o.passengerName, o.totalPrice, o.orderBillPrice) ");
        hql.append("from ShenzhouOrder o where 1 = 1 ");
        if (orderBillSummary.getId() != null) {
            hql.append("and o.billSummaryId = :billSummaryId ");
            params.put("billSummaryId", orderBillSummary.getId());
        }
        hql.append("order by o.createTime desc ");
        if (page != null) {
            return findByHQL2ForNew(hql.toString(), page, params);
        }
        return findByHQL2ForNew(hql.toString(), params);
    }
}
