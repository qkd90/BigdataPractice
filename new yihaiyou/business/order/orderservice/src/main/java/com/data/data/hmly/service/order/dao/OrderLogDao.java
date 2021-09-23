package com.data.data.hmly.service.order.dao;

import com.data.data.hmly.service.order.entity.OrderLog;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zzl on 2016/3/28.
 */
@Repository
public class OrderLogDao extends DataAccess<OrderLog> {

    public OrderLog get(Long id) {
        Criteria<OrderLog> criteria = new Criteria<OrderLog>(OrderLog.class);
        criteria.eq("id", id);
        return findUniqueByCriteria(criteria);
    }

}
