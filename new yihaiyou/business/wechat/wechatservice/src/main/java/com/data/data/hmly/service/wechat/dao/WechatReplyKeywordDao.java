package com.data.data.hmly.service.wechat.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatReplyKeyword;
import com.data.data.hmly.service.wechat.entity.WechatReplyRule;
import com.data.data.hmly.service.wechat.entity.enums.MatchType;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

@Repository
public class WechatReplyKeywordDao extends DataAccess<WechatReplyKeyword> {

	/**
	 * 查询关键字信息
	 * @author caiys
	 * @date 2015年11月25日 下午3:03:05
	 * @param wechatReplyKeyword
	 * @return
	 */
	public List<WechatReplyKeyword> findBy(WechatReplyKeyword wechatReplyKeyword) {
		Criteria<WechatReplyKeyword> criteria = new Criteria<WechatReplyKeyword>(WechatReplyKeyword.class);
		criteria.createCriteria("replyRule", "replyRule", JoinType.INNER_JOIN);
		if (StringUtils.isNotBlank(wechatReplyKeyword.getKeyword())) {	// 根据匹配模式设置查询条件
			criteria.add(Restrictions.or(Restrictions.and(Restrictions.eq("matchType", MatchType.explicit), Restrictions.eq("keyword", wechatReplyKeyword.getKeyword())), 
					Restrictions.and(Restrictions.eq("matchType", MatchType.implicit), Restrictions.like("keyword", wechatReplyKeyword.getKeyword(), MatchMode.ANYWHERE))));
		}
		if (wechatReplyKeyword.getWechatAccount() != null) {
			criteria.eq("wechatAccount.id", wechatReplyKeyword.getWechatAccount().getId());
		}
		List<WechatReplyKeyword> wechatReplyKeywords = findByCriteria(criteria);
		return wechatReplyKeywords;
	}

	public List<WechatReplyKeyword> findByRuleId(Long ruleId,  WechatAccount wechatAccount) {
		
		Criteria<WechatReplyKeyword> criteria = new Criteria<WechatReplyKeyword>(WechatReplyKeyword.class);
		
		criteria.eq("replyRule.id", ruleId);
//		criteria.eq("userId", userid);
		criteria.eq("wechatAccount", wechatAccount);
		
		return findByCriteria(criteria);
	}

	public void delKeywordByRule(WechatReplyRule replyRule) {
		
		Criteria<WechatReplyKeyword> criteria = new Criteria<WechatReplyKeyword>(WechatReplyKeyword.class);
		criteria.eq("replyRule", replyRule);
		deleteAll(findByCriteria(criteria));
	}
}
