package com.data.data.hmly.service.other.dao;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.other.entity.OtherFavorite;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OtherFavoriteDao extends DataAccess<OtherFavorite> {

    /**
     * 批量清除收藏夹
     *
     * @param favoriteType
     * @param userId
     * @author caiys
     * @date 2015年12月23日 下午3:44:00
     */
    public void clearFavoriteBy(ProductType favoriteType, Long userId, Long favoriteId) {
        StringBuilder hql = new StringBuilder();
        hql.append("update OtherFavorite f set f.deleteFlag = 1 where f.deleteFlag = 0 ");
        List<Object> params = new ArrayList<Object>();
        if (favoriteType != null) {
            hql.append("and f.favoriteType = ? ");
            params.add(favoriteType);
        }
        if (userId != null) {
            hql.append("and f.userId = ? ");
            params.add(userId);
        }
        if (favoriteId != null) {
            hql.append("and f.favoriteId = ? ");
            params.add(favoriteId);
        }
        updateByHQL(hql.toString(), params.toArray());
    }

    public void clearFavoritesBy(Long userId, List<Long> ids) {
        StringBuilder sql = new StringBuilder();
        sql.append("update other_favorite f set f.deleteFlag = 1 where f.deleteFlag = 0 ");
        if (userId == null || ids == null) {
            return;
        }
        sql.append("and f.userId = ").append(userId).append(" and f.id in ").append(ids.toString().replace("[", "(").replace("]", ")"));
        updateBySQL(sql.toString());
    }

    public Long countOtherFavorite(OtherFavorite otherFavorite) {
        Criteria<OtherFavorite> criteria = new Criteria<OtherFavorite>(OtherFavorite.class);
        // 标题
        if (StringUtils.isNotBlank(otherFavorite.getTitle())) {
            criteria.like("title", otherFavorite.getTitle(), MatchMode.ANYWHERE);
        }
        // 资源类型
        if (otherFavorite.getFavoriteType() != null) {
            criteria.eq("favoriteType", otherFavorite.getFavoriteType());
        }
        // 用户标识
        if (otherFavorite.getUserId() != null) {
            criteria.eq("userId", otherFavorite.getUserId());
        }
        // 删除标识
        if (otherFavorite.getDeleteFlag() != null) {
            criteria.eq("deleteFlag", otherFavorite.getDeleteFlag());
        }
        criteria.setProjection(Projections.rowCount());
        return findLongCriteria(criteria);
    }

    /**
     * 查询列表
     *
     * @param otherFavorite
     * @param page
     * @return
     * @author caiys
     * @date 2015年12月22日 下午2:04:31
     */
    public List<OtherFavorite> findOtherFavoriteList(OtherFavorite otherFavorite, Page page) {
        Criteria<OtherFavorite> criteria = new Criteria<OtherFavorite>(OtherFavorite.class);
        // 标题
        if (StringUtils.isNotBlank(otherFavorite.getTitle())) {
            criteria.like("title", otherFavorite.getTitle(), MatchMode.ANYWHERE);
        }
        // 资源类型
        if (otherFavorite.getFavoriteType() != null) {
            criteria.eq("favoriteType", otherFavorite.getFavoriteType());
        }
        // 用户标识
        if (otherFavorite.getUserId() != null) {
            criteria.eq("userId", otherFavorite.getUserId());
        }
        // 删除标识
        if (otherFavorite.getDeleteFlag() != null) {
            criteria.eq("deleteFlag", otherFavorite.getDeleteFlag());
        }

        criteria.orderBy("createTime", "desc");
        return findByCriteria(criteria, page);
    }

    /**
     * 查询收藏夹列表
     *
     * @param favoriteType
     * @param favoriteId
     * @param userId
     * @return
     * @author caiys
     * @date 2015年12月23日 下午3:46:58
     */
    public List<OtherFavorite> findOtherFavoriteBy(ProductType favoriteType, Long favoriteId, Long userId) {
        Criteria<OtherFavorite> criteria = new Criteria<OtherFavorite>(OtherFavorite.class);
        criteria.eq("favoriteType", favoriteType);
        criteria.eq("favoriteId", favoriteId);
        criteria.eq("userId", userId);
        criteria.eq("deleteFlag", false);
        return findByCriteria(criteria);
    }

    /**
     * 根据类型查询列表
     *
     * @param favoriteType
     * @param userId
     * @return
     * @author huangpeijie
     * @date 2016-01-07
     */
    public List<OtherFavorite> findOtherFavoriteBy(ProductType favoriteType, Long userId) {
        Criteria<OtherFavorite> criteria = new Criteria<OtherFavorite>(OtherFavorite.class);
        criteria.eq("favoriteType", favoriteType);
        criteria.eq("userId", userId);
        criteria.eq("deleteFlag", false);
        return findByCriteria(criteria);
    }

    /**
     * 根据类型查询列表
     *
     * @param favoriteType
     * @param userId
     * @return
     * @author huangpeijie
     * @date 2016-01-07
     */
    public List<OtherFavorite> findOtherFavoriteBy(Long userId, Page page, ProductType... favoriteType) {
        Criteria<OtherFavorite> criteria = new Criteria<OtherFavorite>(OtherFavorite.class);
        criteria.in("favoriteType", favoriteType);
        criteria.eq("userId", userId);
        criteria.eq("deleteFlag", false);
        criteria.orderBy(Order.desc("createTime"));
        return findByCriteria(criteria, page);
    }

    /**
     * 分组重新收藏类型
     *
     * @param userId
     * @return
     * @author caiys
     * @date 2016年1月3日 下午5:10:11
     */
    public List<Object> groupByFavoriteType(Long userId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select o.favoriteType, count(*) as count ");
        sql.append("from other_favorite o where o.userId = ? and o.deleteFlag = ? group by o.favoriteType ");
        List<Object> list = findBySQL(sql.toString(), userId, false);
        return list;
    }

    /**
     * 重写方法，否则不支持select new()
     *
     * @param hqlstr
     * @param page
     * @param parameters
     * @return
     * @author caiys
     * @date 2015年12月24日 下午2:46:41
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List<T> findFavoriteByHQL(final String hqlstr, final Page page, final Object... parameters) {
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
