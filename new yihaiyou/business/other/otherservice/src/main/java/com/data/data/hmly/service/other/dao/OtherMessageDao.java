package com.data.data.hmly.service.other.dao;

import com.data.data.hmly.service.other.entity.OtherMessage;
import com.data.data.hmly.service.other.enums.MsgType;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OtherMessageDao extends DataAccess<OtherMessage> {

	/**
	 * 批量清除消息
	 * @author caiys
	 * @date 2015年12月24日 上午9:37:22
	 * @param msgType
	 * @param userId
	 */
	public void clearMessageBy(MsgType msgType, Long userId) {
		StringBuilder hql = new StringBuilder();
		hql.append("update OtherMessage m set m.deleteFlag = 1 where m.deleteFlag = 0 ");
		List<Object> params = new ArrayList<Object>();
		if (msgType != null) {
			hql.append("and m.msgType = ? ");
			params.add(msgType);
		}
		if (userId != null) {
			hql.append("and m.toUser.id = ? ");
			params.add(userId);
		}
		updateByHQL(hql.toString(), params.toArray());
	}
	
	/**
	 * 查询列表
	 * @author caiys
	 * @date 2015年12月22日 下午2:04:31
	 * @param otherMessage
	 * @param page
	 * @return
	 */
	public List<OtherMessage> findOtherMessageList(OtherMessage otherMessage, Page page) {
		Criteria<OtherMessage> criteria = new Criteria<OtherMessage>(OtherMessage.class);
		// 标题
		if (StringUtils.isNotBlank(otherMessage.getTitle())) {
			criteria.like("title", otherMessage.getTitle(), MatchMode.ANYWHERE);
		}
		// 消息类型
		if (otherMessage.getMsgType() != null) {
			criteria.eq("msgType", otherMessage.getMsgType());
		}
		// 发送人 
		if (otherMessage.getFromUser() != null) {
			criteria.eq("fromUser.id", otherMessage.getFromUser().getId());
		}
		// 接收人 
		if (otherMessage.getToUser() != null) {
			criteria.eq("toUser.id", otherMessage.getToUser().getId());
		}
		// 删除标识
		if (otherMessage.getDeleteFlag() != null) {
			criteria.eq("deleteFlag", otherMessage.getDeleteFlag());
		}
		
		criteria.orderBy("createTime", "desc");
		return findByCriteria(criteria, page);
	}
	
	/**
	 * 统计数量
	 * @author caiys
	 * @date 2016年1月7日 下午5:50:27
	 * @param otherMessage
	 * @return
	 */
	public Long countOtherMessage(OtherMessage otherMessage) {
		Criteria<OtherMessage> criteria = new Criteria<OtherMessage>(OtherMessage.class);
		// 标题
		if (StringUtils.isNotBlank(otherMessage.getTitle())) {
			criteria.like("title", otherMessage.getTitle(), MatchMode.ANYWHERE);
		}
		// 消息类型
		if (otherMessage.getMsgType() != null) {
			criteria.eq("msgType", otherMessage.getMsgType());
		}
		// 发送人 
		if (otherMessage.getFromUser() != null) {
			criteria.eq("fromUser.id", otherMessage.getFromUser().getId());
		}
		// 接收人 
		if (otherMessage.getToUser() != null) {
			criteria.eq("toUser.id", otherMessage.getToUser().getId());
		}
		// 删除标识
		if (otherMessage.getDeleteFlag() != null) {
			criteria.eq("deleteFlag", otherMessage.getDeleteFlag());
		}
		// 读取标识
		if (otherMessage.getReadFlag() != null) {
			criteria.eq("readFlag", otherMessage.getReadFlag());
		}

		criteria.setProjection(Projections.rowCount());
		return (Long) findUniqueCriteria(criteria);
	}
	
	/**
	 * 根据查询消息相关的信息列表
	 * @author caiys
	 * @date 2015年12月22日 下午2:04:31
	 * @param page
	 * @return
	 */
	public List<OtherMessage> findMessageAndObject(OtherMessage otherMessage, Page page) {
		StringBuilder hql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		hql.append("select new OtherMessage(om.id, om.msgType, om.title, om.content, om.createTime, om.readFlag, om.commentTargetId, ")
			.append("om.messageId, om.commentTargetType, fu.nickName as fromUserName, fu.head as fromUserHead) ")
			.append("from OtherMessage om left join om.fromUser fu where 1=1 ");
		// 标题
		if (StringUtils.isNotBlank(otherMessage.getTitle())) {
			hql.append("and om.title like '%'||?||'%' ");
			params.add(otherMessage.getTitle());
		}
		// 消息类型
		if (otherMessage.getMsgType() != null) {
			hql.append("and om.msgType = ? ");
			params.add(otherMessage.getMsgType());
		}
		// 发送人 
		if (otherMessage.getFromUser() != null) {
			hql.append("and om.fromUser.id = ? ");
			params.add(otherMessage.getFromUser().getId());
		}
		// 接收人 
		if (otherMessage.getToUser() != null) {
			hql.append("and om.toUser.id = ? ");
			params.add(otherMessage.getToUser().getId());
		}
		// 删除标识
		if (otherMessage.getDeleteFlag() != null) {
			hql.append("and om.deleteFlag = ? ");
			params.add(otherMessage.getDeleteFlag());
		}

		hql.append("order by om.createTime desc ");
		return findVisitHistoryByHQL(hql.toString(), page, params.toArray());
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object findUniqueCriteria(final com.framework.hibernate.util.Criteria<OtherMessage> criteria) {
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
