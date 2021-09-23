package com.data.data.hmly.service.ctripcommon.dao;

import com.data.data.hmly.service.ctripcommon.entity.CtripApiLog;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2016/1/26.
 */
@Repository
public class CtripApiLogDao extends DataAccess<CtripApiLog> {

    /**
     * 查询最大的日期值
     * @param icode
     * @return
     */
    public Date findMaxNextTime(String icode) {
        Criteria<CtripApiLog> criteria = new Criteria<CtripApiLog>(CtripApiLog.class);
        criteria.eq("icode", icode);
        criteria.eq("success", true);
        criteria.setProjection(Projections.max("nextTime"));
        Date date = (Date) findUniqueCriteria(criteria);
        return date;
    }

    /**
     * 根据uuid查询唯一值
     * @param uuid
     * @return
     */
    public CtripApiLog findUniqueBy(String uuid, String icode) {
        Criteria<CtripApiLog> criteria = new Criteria<CtripApiLog>(CtripApiLog.class);
        criteria.eq("uuid", uuid);
        criteria.eq("icode", icode);
        return findUniqueByCriteria(criteria);
    }

    /**
     * 根据uuid更新错误状态和错误信息
     * @param uuid
     * @param errorCode
     * @param errorMessage
     */
    public void updateErrorInfo(String uuid, String errorCode, String errorMessage) {
        String hql = " update CtripApiLog set success = ?, errorCode = ?, errorMessage = ? where uuid = ? ";
        updateByHQL(hql, false, errorCode, errorMessage, uuid);
    }

    /**
     * 统计数量
     */
    public Long countCtripApiLog(CtripApiLog ctripApiLog) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder hql = new StringBuilder("select count(*) from CtripApiLog t where 1=1");
        if (ctripApiLog.getSuccess() != null) {
            hql.append(" and t.success = ?");
            params.add(ctripApiLog.getSuccess());
        }
        if (StringUtils.isNotBlank(ctripApiLog.getIcode())) {
            hql.append(" and t.icode = ?");
            params.add(ctripApiLog.getIcode());
        }
        if (ctripApiLog.getExecTimeStart() != null) {
            hql.append(" and t.execTime >= ?");
            params.add(ctripApiLog.getExecTimeStart());
        }
        if (ctripApiLog.getExecTimeEnd() != null) {
            hql.append(" and t.execTime <= ?");
            params.add(ctripApiLog.getExecTimeEnd());
        }

        Long count = findLongByHQL(hql.toString(), params.toArray());
        return count;
    }

    /**
     * 分页查询
     */
    public List<CtripApiLog> listCtripApiLog(CtripApiLog ctripApiLog, Integer pageIndex, Integer pageSize) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder hql = new StringBuilder("from CtripApiLog t where 1=1");
        if (ctripApiLog.getSuccess() != null) {
            hql.append(" and t.success = ?");
            params.add(ctripApiLog.getSuccess());
        }
        if (StringUtils.isNotBlank(ctripApiLog.getIcode())) {
            hql.append(" and t.icode = ?");
            params.add(ctripApiLog.getIcode());
        }
        if (ctripApiLog.getExecTimeStart() != null) {
            hql.append(" and t.execTime >= ?");
            params.add(ctripApiLog.getExecTimeStart());
        }
        if (ctripApiLog.getExecTimeEnd() != null) {
            hql.append(" and t.execTime <= ?");
            params.add(ctripApiLog.getExecTimeEnd());
        }
        hql.append(" order by t.execTime asc ");
        Page page = new Page(pageIndex, pageSize);
        List<CtripApiLog> list = findByHQL(hql.toString(), page, params.toArray());
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
                return query.list();
            }
        });
    }

    public Object findUniqueCriteria(final com.framework.hibernate.util.Criteria<CtripApiLog> criteria) {
        return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                List<Object> list = hibernateCriteria.list();
                if (!list.isEmpty()) {
                    return list.get(0);
                } else {
                    return null;
                }
            }
        });
    }

}
