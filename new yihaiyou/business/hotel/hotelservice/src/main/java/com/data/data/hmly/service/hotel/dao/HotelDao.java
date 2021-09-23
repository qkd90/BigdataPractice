package com.data.data.hmly.service.hotel.dao;

import com.data.data.hmly.service.hotel.entity.Hotel;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Page;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by guoshijie on 2015/12/11.
 */
@Repository
public class HotelDao extends DataAccess<Hotel> {

    /*
     * 不返回总数的分页
     * (non-Javadoc)
     * @see com.framework.hibernate.BaseDataAccess#findByHQL(java.lang.String, com.framework.hibernate.util.Page, java.lang.Object[])
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> List<T> findByHQLNoTotal(final String hqlstr, final Page page, final Object... parameters) {
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


    @Override
    public void save(Object entity) {
        super.save(entity);
    }

    @Override
    public void save(List<?> objs) {
        super.save(objs);
    }

    @Override
    public void update(Object entity) {
        super.update(entity);
    }
}
