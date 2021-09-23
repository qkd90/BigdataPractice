package com.data.data.hmly.service.nctripticket.dao;

import com.data.data.hmly.service.ctripcommon.enums.RowStatus;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotResource;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Page;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caiys on 2016/2/1.
 */
@Repository
public class CtripScenicSpotResourceDao extends DataAccess<CtripScenicSpotResource> {

    /**
     * 统计门票资源数量
     */
    public Long countCtripScenicSpotResource() {
        List<Object> params = new ArrayList<Object>();
        StringBuilder hql = new StringBuilder("select count(*) from CtripScenicSpotResource ctr where 1=1");
        hql.append(" and ctr.rowStatus <> ?");
        Long count = findLongByHQL(hql.toString(), RowStatus.DELETE);
        return count;
    }

    /**
     * 分页查询
     */
    public List<CtripScenicSpotResource> listCtripScenicSpotResourceId(Integer pageIndex, Integer pageSize) {
        StringBuilder hql = new StringBuilder("select new CtripScenicSpotResource(ctr.id, ctr.ctripResourceId, ctr.productId) from CtripScenicSpotResource ctr where 1=1");
        hql.append(" and ctr.rowStatus <> ?");
        hql.append(" order by ctr.id asc ");
        Page page = new Page(pageIndex, pageSize);
        List<CtripScenicSpotResource> list = findByHQL(hql.toString(), page, RowStatus.DELETE);
        return list;
    }

    /**
     * 根据携程资源标识查询资源信息
     */
    public List<CtripScenicSpotResource> listResourceBy(List<Long> ctripResourceIdList) {
        StringBuilder hql = new StringBuilder("select new CtripScenicSpotResource(ctr.id, ctr.ctripResourceId, ctr.productId) from CtripScenicSpotResource ctr where ctr.rowStatus <> ?");
        hql.append(" and ctr.ctripResourceId in (");
        for (Long resourceId : ctripResourceIdList) {
            hql.append("?,");
        }
        hql.deleteCharAt(hql.length() - 1);
        hql.append(") ");
        List<Object> params = new ArrayList<Object>();
        params.add(RowStatus.DELETE);
        params.addAll(ctripResourceIdList);
        List<CtripScenicSpotResource> list = super.findByHQL(hql.toString(), params.toArray());
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
