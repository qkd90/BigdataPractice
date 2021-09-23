package com.data.data.hmly.service.wechat.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.data.data.hmly.service.wechat.entity.WechatReplyRule;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;

/**
 * Created by vacuity on 15/11/20.
 */

@Repository
public class WechatReplyRuleDao extends DataAccess<WechatReplyRule> {

	public List<WechatReplyRule> findRuleList(SysUnit companyUnit, Long userId, Page pageInfo, WechatReplyRule replyRule) {
		
		Criteria<WechatReplyRule> criteria = new Criteria<WechatReplyRule>(WechatReplyRule.class);
		checkKeyword(criteria, replyRule);
		criteria.eq("sysUnit", companyUnit);
		criteria.eq("userId", userId);
		criteria.orderBy("modifyTime", "ASC");
		
		return findByCriteria(criteria, pageInfo);
	}

	public List<WechatReplyRule> findRuleList(Page pageInfo, WechatReplyRule replyRule) {
		
		Criteria<WechatReplyRule> criteria = new Criteria<WechatReplyRule>(WechatReplyRule.class);
		checkKeyword(criteria, replyRule);
		criteria.orderBy("modifyTime", "ASC");
		
		return findByCriteria(criteria, pageInfo);
	}

	public List<WechatReplyRule> findRuleList(List<SysUnit> sysUnList, Page pageInfo, WechatReplyRule replyRule) {
		
		Criteria<WechatReplyRule> criteria = new Criteria<WechatReplyRule>(WechatReplyRule.class);
		checkKeyword(criteria, replyRule);
		criteria.in("sysUnit", sysUnList);
		criteria.orderBy("modifyTime", "ASC");
		
		return findByCriteria(criteria, pageInfo);
	}

	public WechatReplyRule checkRuleHasItem(WechatDataItem dataItem) {
		Criteria<WechatReplyRule> criteria = new Criteria<WechatReplyRule>(WechatReplyRule.class);
		criteria.eq("dataItem", dataItem);
		return findUniqueByCriteria(criteria);
	}

	public Criteria<WechatReplyRule> checkKeyword(Criteria<WechatReplyRule> criteria, WechatReplyRule replyRule) {
		
		if (replyRule.getWechatAccount() != null) {
			criteria.eq("wechatAccount", replyRule.getWechatAccount());
		}
		if (StringUtils.isNotBlank(replyRule.getName())) {
			criteria.like("name", replyRule.getName(), MatchMode.ANYWHERE);
		}
		
		return criteria;
	}
	
}
