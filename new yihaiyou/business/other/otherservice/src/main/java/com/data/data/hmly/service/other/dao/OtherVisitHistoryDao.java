package com.data.data.hmly.service.other.dao;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.other.entity.OtherVisitHistory;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OtherVisitHistoryDao extends DataAccess<OtherVisitHistory> {
	
	/**
	 * 批量删除个人浏览历史
	 * @author caiys
	 * @date 2015年12月22日 下午1:40:23
	 * @param cookieId
	 * @param userId
	 */
	public void clearHistoryBy(String cookieId, Long userId, ProductType resType, Long resObjectId) {
		StringBuilder hql = new StringBuilder();
		hql.append("update OtherVisitHistory vh set vh.deleteFlag = 1 where vh.deleteFlag = 0 and (vh.cookieId = ? or vh.userId = ?) ");
		List<Object> params = new ArrayList<Object>();
		params.add(cookieId);
		params.add(userId);
		if (resType != null) {
			hql.append("and vh.resType = ? ");
			params.add(resType);
		}
		if (resObjectId != null) {
			hql.append("and vh.resObjectId = ? ");
			params.add(resObjectId);
		}
		updateByHQL(hql.toString(), params.toArray());
	}
	
	/**
	 * 查询列表
	 * @author caiys
	 * @date 2015年12月22日 下午2:04:31
	 * @param otherVisitHistory
	 * @param page
	 * @return
	 */
	public List<OtherVisitHistory> findOtherVisitHistoryList(OtherVisitHistory otherVisitHistory, Page page) {
		Criteria<OtherVisitHistory> criteria = new Criteria<OtherVisitHistory>(OtherVisitHistory.class);
		// 标题
		if (StringUtils.isNotBlank(otherVisitHistory.getTitle())) {
			criteria.like("title", otherVisitHistory.getTitle(), MatchMode.ANYWHERE);
		}
		// 资源类型
		if (otherVisitHistory.getResType() != null) {
			criteria.eq("resType", otherVisitHistory.getResType());
		}
		// cookieId 和 用户标识 组合
		if (StringUtils.isNotBlank(otherVisitHistory.getCookieId()) || otherVisitHistory.getUserId() != null) {
			criteria.add(Restrictions.or(Restrictions.eq("cookieId", otherVisitHistory.getCookieId()), Restrictions.eq("userId", otherVisitHistory.getUserId())));
		}
		// 删除标识
		if (otherVisitHistory.getDeleteFlag() != null) {
			criteria.eq("deleteFlag", otherVisitHistory.getDeleteFlag());
		}
		
		criteria.orderBy("createTime", "desc");
		return findByCriteria(criteria, page);
	}
	
	/**
	 * 根据资源类型查询资源相关的信息列表
	 * @author caiys
	 * @date 2015年12月22日 下午2:04:31
	 * @param otherVisitHistory
	 * @param page
	 * @return
	 */
	public List<OtherVisitHistory> findVisitHistoryResObject(OtherVisitHistory otherVisitHistory, ProductType resType, Page page) {
		StringBuilder hql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		
		if (resType == ProductType.delicacy) {
			hql.append("select new OtherVisitHistory(ovh.id, t.name as title, t.taste as introduction, t.cover as imgPath, ovh.resObjectId, ovh.createTime) ");
			hql.append("from OtherVisitHistory ovh, Delicacy t where ovh.resObjectId = t.id ");
			if (StringUtils.isNotBlank(otherVisitHistory.getTitle())) { // 标题
				hql.append("and t.name like '%'||?||'%' ");
				params.add(otherVisitHistory.getTitle());
			}
		} else if (resType == ProductType.hotel) {	// 如果结构有调整，则需修改
			hql.append("select new OtherVisitHistory(ovh.id, t.name as title, t.shortDesc as introduction, t.cover as imgPath, ovh.resObjectId, ovh.createTime) ");
			hql.append("from OtherVisitHistory ovh, Hotel t where ovh.resObjectId = t.id ");
			if (StringUtils.isNotBlank(otherVisitHistory.getTitle())) { // 标题
				hql.append("and t.name like '%'||?||'%' ");
				params.add(otherVisitHistory.getTitle());
			}
		} else if (resType == ProductType.plan) {
			hql.append("select new OtherVisitHistory(ovh.id, t.name as title, t.tips as introduction, t.coverPath as imgPath, ovh.resObjectId, ovh.createTime) ");
			hql.append("from OtherVisitHistory ovh, Plan t where ovh.resObjectId = t.id ");
			if (StringUtils.isNotBlank(otherVisitHistory.getTitle())) { // 标题
				hql.append("and t.name like '%'||?||'%' ");
				params.add(otherVisitHistory.getTitle());
			}
		} else if (resType == ProductType.recplan) {
			hql.append("select new OtherVisitHistory(ovh.id, t.planName as title, t.description as introduction, t.coverPath as imgPath, ovh.resObjectId, ovh.createTime) ");
			hql.append("from OtherVisitHistory ovh, RecommendPlan t where ovh.resObjectId = t.id ");
			if (StringUtils.isNotBlank(otherVisitHistory.getTitle())) { // 标题
				hql.append("and t.planName like '%'||?||'%' ");
				params.add(otherVisitHistory.getTitle());
			}
		} else if (resType == ProductType.scenic) {
			hql.append("select new OtherVisitHistory(ovh.id, t.name as title, t.level as introduction, t.cover as imgPath, ovh.resObjectId, ovh.createTime, t.price) ");
			hql.append("from OtherVisitHistory ovh, ScenicInfo t where ovh.resObjectId = t.id ");
			if (StringUtils.isNotBlank(otherVisitHistory.getTitle())) { // 标题
				hql.append("and t.name like '%'||?||'%' ");
				params.add(otherVisitHistory.getTitle());
			}
		} else if (resType == ProductType.line) {
			hql.append("select new OtherVisitHistory(ovh.id, t.name as title, t.appendTitle as introduction, t.lineImageUrl as imgPath, ovh.resObjectId, ovh.createTime, t.price) ");
			hql.append("from OtherVisitHistory ovh, Line t where ovh.resObjectId = t.id ");
			if (StringUtils.isNotBlank(otherVisitHistory.getTitle())) { // 标题
				hql.append("and t.name like '%'||?||'%' ");
				params.add(otherVisitHistory.getTitle());
			}
		} else {
			return findOtherVisitHistoryList(otherVisitHistory, page);
		}

		// 资源类型
		hql.append("and ovh.resType = ? ");
		params.add(resType);
		// cookieId 和 用户标识 组合
		if (StringUtils.isNotBlank(otherVisitHistory.getCookieId()) || otherVisitHistory.getUserId() != null) {
			hql.append("and (ovh.cookieId = ? or ovh.userId = ?) ");
			params.add(otherVisitHistory.getCookieId());
			params.add(otherVisitHistory.getUserId());
		}
		// 删除标识
		if (otherVisitHistory.getDeleteFlag() != null) {
			hql.append("and ovh.deleteFlag = ? ");
			params.add(otherVisitHistory.getDeleteFlag());
		}
		hql.append("order by ovh.createTime desc ");
		return findVisitHistoryByHQL(hql.toString(), page, params.toArray());
	}

	/**
	 * 统计访问次数最多的前N条，目前只考虑景点，如果是自己浏览的记录
	 * @param otherVisitHistory
	 * @param limit
	 * @return
	 */
	public List<OtherVisitHistory> findVisitScenicCountTop(OtherVisitHistory otherVisitHistory, Integer limit) {
        StringBuilder hql = new StringBuilder();
        hql.append("select t.id, t.name as title, t.level as introduction, t.cover as imgPath, t.price ");
        hql.append("from (select h.resObjectId, count(*) as total, sum(case when (h.cookieId = ? or h.userId = ?) then 1 else 0 end) as factor from other_visit_history h ");
        hql.append("where h.resType = ? group by h.resObjectId order by factor desc, total desc) ovh, scenic t where t.id = ovh.resObjectId and t.status = 1 limit ? ");
        List<Object> list = findBySQL(hql.toString(), otherVisitHistory.getCookieId(), otherVisitHistory.getUserId(), ProductType.scenic.toString(), limit);
        List<OtherVisitHistory> result = new ArrayList<OtherVisitHistory>();
        for (Object obj : list) {
            Object[] objArr = (Object[]) obj;
            OtherVisitHistory ovh = new OtherVisitHistory();
            ovh.setResObjectId(objArr[0] != null ? ((Integer) objArr[0]).longValue() : null);
            ovh.setTitle((String) objArr[1]);
            ovh.setIntroduction((String) objArr[2]);
            ovh.setImgPath((String) objArr[3]);
            ovh.setPrice(objArr[4] != null ? ((BigDecimal) objArr[4]).floatValue() : null);
            result.add(ovh);
        }
        return result;
	}

	public List<OtherVisitHistory> findVisitLineCountTop(OtherVisitHistory otherVisitHistory, Integer limit) {
		StringBuilder hql = new StringBuilder();
		hql.append("select p.id, p.name as title ");
		hql.append("from (select h.resObjectId, count(*) as total, sum(case when (h.cookieId = ? or h.userId = ?) then 1 else 0 end) as factor from other_visit_history h ");
		hql.append("where h.resType = ? group by h.resObjectId order by factor desc, total desc) ovh, line t join product p on t.productId = p.id where p.id = ovh.resObjectId and t.lineStatus = 'show' limit ? ");
		List<Object> list = findBySQL(hql.toString(), otherVisitHistory.getCookieId(), otherVisitHistory.getUserId(), ProductType.line.toString(), limit);
		List<OtherVisitHistory> result = new ArrayList<OtherVisitHistory>();
		for (Object obj : list) {
			Object[] objArr = (Object[]) obj;
			OtherVisitHistory ovh = new OtherVisitHistory();
			ovh.setResObjectId(objArr[0] != null ? ((BigInteger) objArr[0]).longValue() : null);
			ovh.setTitle((String) objArr[1]);
//			ovh.setIntroduction((String) objArr[2]);
//			ovh.setImgPath((String) objArr[3]);
//			ovh.setPrice(objArr[4] != null ? ((BigDecimal) objArr[4]).floatValue() : null);
			result.add(ovh);
		}
		return result;
	}
	
	/**
	 * 重写方法，否则不支持select new()
	 * @author caiys
	 * @date 2015年12月24日 下午2:46:41
	 * @param hqlstr
	 * @param page
	 * @param parameters
	 * @return
	 */
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
