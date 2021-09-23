package com.data.data.hmly.service.outOrder.dao;

import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Page;
import org.hibernate.*;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dy on 2016/2/23.
 */
@Repository
public class JszxOrderDao extends DataAccess<JszxOrder> {


    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> List<T> findVisitHistoryByHQL(final String hqlstr, final Page page, final Object... parameters) {
        getHibernateTemplate().setCacheQueries(getCacheable());
        return (List<T>) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hqlstr);
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i, parameters[i]);
                }
                ScrollableResults scrollableResults = query.scroll();
                scrollableResults.last();
                int rownum = scrollableResults.getRowNumber();
                page.setTotalCount(rownum + 1);
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getFirstResult());
                return query.list();
            }
        });
    }

}
