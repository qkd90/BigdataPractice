package com.data.data.hmly.service.pay;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.pay.dao.PayLogDao;
import com.data.data.hmly.service.pay.entity.PayLog;
import com.data.data.hmly.service.pay.entity.enums.PayAction;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 15/10/20.
 */

@Service
public class PayLogService {
    @Resource
    private PayLogDao payLogDao;

    public PayLog get(long id){
        return payLogDao.get(id);
    }

    public void delete(long id){
        payLogDao.delete(id);
    }

    public void save(PayLog payLog){
        payLogDao.save(payLog);
    }


    // 根据订单，用户名，电话，开始和结束日期查询交易日志
    public List<PayLog> getPayLogs(User user, Page page, long orderId, String name, String mobile, Date sTime, Date eTime) {

        Criteria<PayLog> criteria = new Criteria<PayLog>(PayLog.class);
        DetachedCriteria orderCriteria = criteria.createCriteria("order", "order", JoinType.INNER_JOIN);
//        DetachedCriteria orderDetailsCriteria = orderCriteria.createCriteria("orderDetails", "orderDetails", JoinType.INNER_JOIN);
//        DetachedCriteria commissionCriteria = orderDetailsCriteria.createCriteria("commissions", "commissions", JoinType.LEFT_OUTER_JOIN);
//        DetachedCriteria topProductCriteria = commissionCriteria.createCriteria("topProduct", "topProduct", JoinType.LEFT_OUTER_JOIN);
//        criteria.add(Restrictions.or(Restrictions.eq("commissions.user.id", user.getId()), Restrictions.eq("topProduct.user.id", user.getId())));


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
        criteria.orderBy("requestTime", "desc");
        return payLogDao.findByCriteria(criteria, page);
    }

    public PayLog findPayLogByOrder(Long orderId, Long userId, PayAction payAction) {

        Criteria<PayLog> criteria = new Criteria<PayLog>(PayLog.class);
        criteria.createAlias("order", "o", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("user", "u", JoinType.LEFT_OUTER_JOIN);
        criteria.eq("o.id", orderId);
        criteria.eq("u.id", userId);
        criteria.eq("action", payAction);

        List<PayLog> payLogs = payLogDao.findByCriteria(criteria);

        if (!payLogs.isEmpty()) {
            if (payLogs.size() <= 1) {
                return payLogs.get(0);
            }
        }
        return null;
    }

}
