package com.data.data.hmly.service.dao;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.UserRelation;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRelationDao extends DataAccess<UserRelation> {

	public void deleteByUid(Long id) {
		Criteria<UserRelation> criteria = new Criteria<UserRelation>(UserRelation.class);
		criteria.eq("childUser.id", id);
		List<UserRelation> userRelations = findByCriteria(criteria);
		if (userRelations.size() > 0) {
			deleteAll(userRelations);
		}
	}

    /**
     * 查询层级人员
     * @param parentId
     * @param level
     * @return
     */
    public List<Member> listChildren(Long parentId, Integer level, Page page) {
        StringBuilder hql = new StringBuilder();
        hql.append("select m ")
            .append("from UserRelation ur, Member m ")
            .append("where ur.childUser.id = m.id and ur.parentUser.id = ? and ur.level = ? ");
        List<Member> list = findByHQL(hql.toString(), page, parentId, level);
        return list;
    }

    /**
     * 统计层级人员数
     * @param parentId
     * @param level
     * @return
     */
	public Long countChildren(Long parentId, Integer level) {
		Criteria<UserRelation> criteria = new Criteria<UserRelation>(UserRelation.class);
		criteria.eq("parentUser.id", parentId);
        if (level != null) {
            criteria.eq("level", level);
        }
		criteria.setProjection(Projections.rowCount());
		return (Long) findUniqueCriteria(criteria);
	}

    public Object findUniqueCriteria(final com.framework.hibernate.util.Criteria<UserRelation> criteria) {
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
