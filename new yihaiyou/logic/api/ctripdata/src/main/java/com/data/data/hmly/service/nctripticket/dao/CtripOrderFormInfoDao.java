package com.data.data.hmly.service.nctripticket.dao;

import com.data.data.hmly.service.ctripcommon.enums.OrderStatus;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderFormInfo;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caiys on 2016/2/17.
 */
@Repository
public class CtripOrderFormInfoDao extends DataAccess<CtripOrderFormInfo> {

    public CtripOrderFormInfo findUniqueBy(Long ctripOrderId) {
        Criteria<CtripOrderFormInfo> criteria = new Criteria<CtripOrderFormInfo>(CtripOrderFormInfo.class);
        criteria.eq("ctripOrderId", ctripOrderId);
        return findUniqueByCriteria(criteria);
    }

    /**
     * 更新原始订单取消状态
     */
    public void updateOriginalOrderCanceled(Long ctripOrderId) {
        String hql = " update torderdetail t set t.status = ? where t.realOrderId = ? ";
        updateBySQL(hql, OrderStatus.CANCELED.toString(), ctripOrderId);
    }

    /**
     * 分页查询
     */
    public List<CtripOrderFormInfo> listCtripOrderFormInfo(CtripOrderFormInfo ctripOrderFormInfo, Page page) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder hql = new StringBuilder("from CtripOrderFormInfo t where 1=1");
        if (ctripOrderFormInfo.getOrderStatus() != null) {
            hql.append(" and t.orderStatus = ?");
            params.add(ctripOrderFormInfo.getOrderStatus());
        }
        if (ctripOrderFormInfo.getCancelHandleTime() != null) {
            hql.append(" and (t.cancelHandleTime is null or t.cancelHandleTime < ?)");
            params.add(ctripOrderFormInfo.getCancelHandleTime());
        }
        hql.append(" order by t.id asc ");
        List<CtripOrderFormInfo> list = findByHQL(hql.toString(), page, params.toArray());
        return list;
    }

    /*
     * 不返回总数的分页
     * (non-Javadoc)
     * @see com.framework.hibernate.BaseDataAccess#findByHQL(java.lang.String, com.framework.hibernate.util.Page, java.lang.Object[])
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> List<T> findByHQL(final String hqlstr, final Page page, final Object... parameters) {
        getHibernateTemplate().setCacheQueries(getCacheable());
        return (List<T>) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hqlstr);
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i, parameters[i]);
                }
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getFirstResult());
//                query.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                return query.list();
            }
        });
    }
}
