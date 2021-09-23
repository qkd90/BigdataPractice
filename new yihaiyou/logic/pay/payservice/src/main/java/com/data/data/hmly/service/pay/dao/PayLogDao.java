package com.data.data.hmly.service.pay.dao;

import com.data.data.hmly.service.pay.entity.PayLog;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 15/10/20.
 */

@Repository
public class PayLogDao extends DataAccess<PayLog> {

    public PayLog get(Long id) {
        Criteria<PayLog> criteria = new Criteria<PayLog>(PayLog.class);
        criteria.eq("id", id);
        return findUniqueByCriteria(criteria);
    }

    public void save(PayLog payLog) {
        saveOrUpdate(payLog, payLog.getId());
    }

    // 根据订单，用户名，电话，开始和结束日期查询交易日志
    public List<PayLog> getPayLogs(Page page, long orderId, String name, String mobile, Date sTime, Date eTime) {

        Criteria<PayLog> criteria = new Criteria<PayLog>(PayLog.class);
        DetachedCriteria orderCriteria = criteria.createCriteria("order", "order", JoinType.INNER_JOIN);

        if (orderId > 0) {
            orderCriteria.add(Restrictions.eq("id", orderId));
        }
        if(name != null && !"".equals(name)){
            orderCriteria.add(Restrictions.eq("recName", name));
        }
        if(mobile != null && !"".equals(mobile)){
            orderCriteria.add(Restrictions.eq("mobile", mobile));
        }
        if(sTime != null){
            criteria.gt("requestTime", sTime);
        }
        if(eTime != null){
            criteria.lt("requestTime", eTime);
        }
        return findByCriteria(criteria, page);
    }

}
