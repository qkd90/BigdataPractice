package com.data.data.hmly.service.ctripticket.dao;

import com.data.data.hmly.service.ctripticket.entity.CtripTicketResource;
import com.data.data.hmly.service.ctripticket.entity.RowStatus;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Page;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;

import java.util.ArrayList;
import java.util.List;

//@Repository
public class CtripTicketResourceDao extends DataAccess<CtripTicketResource> {
	/**
	 * 批量更新行状态
	 * @author caiys
	 * @date 2015年12月3日 上午11:40:53
	 * @param rowStatus
	 */
	public void updateRowStatus(RowStatus rowStatus, Integer districtID) {
		List<Object> params = new ArrayList<Object>();
		String hql = "update CtripTicketResource ctr set ctr.rowStatus = ?, updateTime = now()";
		params.add(rowStatus);
		if (districtID != null && districtID > 0) {
			hql = hql + " where exists(select 1 from CtripScenicSpot css, CtripTicket ct where ctr.ticketId = ct.id and ct.scenicSpotId = css.id and css.districtID = ?)";
			params.add(districtID);
		}
		updateByHQL(hql, params.toArray());
	}
	
	/**
	 * 查询门票资源列表
	 * @author caiys
	 * @date 2015年12月9日 下午8:58:19
	 * @param neRowStatus
	 * @param districtID
	 * @return
	 */
	public List<CtripTicketResource> listCtripTicketResourceBy(RowStatus neRowStatus, Integer districtID) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder("select ctr from CtripTicketResource ctr where 1=1");
		if (neRowStatus != null) {
			hql.append(" and ctr.rowStatus <> ?");
			params.add(neRowStatus);
		}
		if (districtID != null && districtID > 0) {
			hql.append(" and exists(select 1 from CtripScenicSpot css, CtripTicket ct where ctr.ticketId = ct.id and ct.scenicSpotId = css.id and css.districtID = ?)");
			params.add(districtID);
		}
		return findByHQL(hql.toString(), params.toArray());
	}
	
	/**
	 * 统计门票资源数量
	 * @author caiys
	 * @date 2015年12月29日 上午10:44:35
	 * @param neRowStatus
	 * @param districtID
	 * @return
	 */
	public Integer countCtripTicketResourceBy(RowStatus neRowStatus, Integer districtID) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder("select count(*) from CtripTicketResource ctr where 1=1");
		if (neRowStatus != null) {
			hql.append(" and ctr.rowStatus <> ?");
			params.add(neRowStatus);
		}
		if (districtID != null && districtID > 0) {
			hql.append(" and exists(select 1 from CtripScenicSpot css, CtripTicket ct where ctr.ticketId = ct.id and ct.scenicSpotId = css.id and css.districtID = ?)");
			params.add(districtID);
		}
		Long count = findLongByHQL(hql.toString(), params.toArray());
		return count.intValue();
	}
	
	/**
	 * 分页查询
	 * @author caiys
	 * @date 2015年12月29日 上午10:53:21
	 * @param pageIndex
	 * @param pageSize
	 * @param neRowStatus
	 * @param districtID
	 * @return
	 */
	public List<CtripTicketResource> listCtripTicketResourceBy(Integer pageIndex, Integer pageSize, RowStatus neRowStatus, Integer districtID) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder hql = new StringBuilder("select ctr from CtripTicketResource ctr where 1=1");
		if (neRowStatus != null) {
			hql.append(" and ctr.rowStatus <> ?");
			params.add(neRowStatus);
		}
		if (districtID != null && districtID > 0) {
			hql.append(" and exists(select 1 from CtripScenicSpot css, CtripTicket ct where ctr.ticketId = ct.id and ct.scenicSpotId = css.id and css.districtID = ?)");
			params.add(districtID);
		}
		hql.append(" order by ctr.id asc ");
		Page page = new Page(pageIndex, pageSize);
		return findByHQL(hql.toString(), page, params.toArray());
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
//				ScrollableResults scrollableResults = query.scroll();
//				scrollableResults.last();
//				int rownum = scrollableResults.getRowNumber();
//				page.setTotalCount(rownum + 1);
				query.setMaxResults(page.getPageSize());
				query.setFirstResult(page.getFirstResult());
				query.setResultTransformer(RootEntityResultTransformer.INSTANCE);
				return query.list();
			}
		});
	}
}
