package com.data.data.hmly.service.dao;

import com.data.data.hmly.service.entity.ShareType;
import com.data.data.hmly.service.entity.UserShareRecord;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caiys on 2016/7/21.
 */
@Repository
public class UserShareRecordDao extends DataAccess<UserShareRecord> {

    /**
     * 统计用户分享记录
     * @param userId
     * @param shareType
     * @return
     */
    public Long countBy(Long userId, ShareType shareType) {
        Criteria<UserShareRecord> criteria = new Criteria<UserShareRecord>(UserShareRecord.class);
        criteria.eq("userId", userId);
        if (shareType != null) {
            criteria.eq("shareType", shareType);
        }
        criteria.setProjection(Projections.rowCount());
        return (Long) findUniqueCriteria(criteria);
    }

    public Object findUniqueCriteria(final com.framework.hibernate.util.Criteria<UserShareRecord> criteria) {
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
