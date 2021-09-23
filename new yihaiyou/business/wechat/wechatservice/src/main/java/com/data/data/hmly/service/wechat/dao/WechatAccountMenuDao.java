package com.data.data.hmly.service.wechat.dao;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.wechat.entity.WechatAccountMenu;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

@Repository
public class WechatAccountMenuDao extends DataAccess<WechatAccountMenu> {

	/**
	 * 
	 * @author caiys
	 * @date 2015年11月20日 下午1:57:54
	 * @param accountId
	 * @param parentId
	 * @return
	 */
	public List<WechatAccountMenu> findChildren(Long accountId, Long parentId) {
		Criteria<WechatAccountMenu> criteria = new Criteria<WechatAccountMenu>(WechatAccountMenu.class);
		criteria.eq("wechatAccount.id", accountId);
		if (parentId == null) {
			criteria.isNull("parentMenu");
			// 关联查询
			criteria.createCriteria("wechatResource", "wr", JoinType.LEFT_OUTER_JOIN);
		} else {
			criteria.eq("parentMenu.id", parentId);
			// 关联查询
			criteria.createCriteria("wechatResource", "wr", JoinType.LEFT_OUTER_JOIN);
//			criteria.eq("wr.validFlag", true);
			criteria.add(Restrictions.or(Restrictions.isNull("wr.id"), Restrictions.eq("wr.validFlag", true)));
		}
		criteria.orderBy("orderNo", "asc");
		return findByCriteria(criteria);
	}


	public List<WechatAccountMenu> getMenuList(Long accountId) {
		Criteria<WechatAccountMenu> criteria = new Criteria<WechatAccountMenu>(WechatAccountMenu.class);
		criteria.eq("wechatAccount.id", accountId);
		criteria.orderBy("level", "asc");
		criteria.orderBy("orderNo", "asc");
		return findByCriteria(criteria);
	}

	public WechatAccountMenu get(Long id) {
		Criteria<WechatAccountMenu> criteria = new Criteria<WechatAccountMenu>(WechatAccountMenu.class);
		criteria.eq("id", id);
		return findUniqueByCriteria(criteria);
	}

	public Integer getCount(Long accountId, Integer level) {
		Criteria<WechatAccountMenu> criteria = new Criteria<WechatAccountMenu>(WechatAccountMenu.class);
		criteria.eq("accountId", accountId);
		criteria.eq("level", level);
		criteria.setProjection(Projections.rowCount());
		Long count = findLongCriteria(criteria);
		return count.intValue();
	}
}
