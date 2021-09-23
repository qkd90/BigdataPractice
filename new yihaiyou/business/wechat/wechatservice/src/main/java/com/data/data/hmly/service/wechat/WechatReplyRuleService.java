package com.data.data.hmly.service.wechat;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.wechat.dao.WechatReplyRuleDao;
import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.data.data.hmly.service.wechat.entity.WechatReplyRule;
import com.framework.hibernate.util.Page;

/**
 * Created by vacuity on 15/11/20.
 */

@Service
public class WechatReplyRuleService {

    @Resource
    private WechatReplyRuleDao dao;

	public void save(WechatReplyRule replyRule, SysUser sysUser) {
		replyRule.setUserId(sysUser.getId());
		replyRule.setCreateTime(new Date());
		replyRule.setModifyTime(new Date());
		dao.save(replyRule);
	}

	

	public WechatReplyRule load(long id) {
		return dao.load(id);
	}

	public void update(WechatReplyRule replyRule, SysUser loginUser) {
		replyRule.setModifyTime(new Date());
		dao.update(replyRule);
	}

	public void deletById(long id) {
		
		
		dao.delete(dao.load(id));
	}

	
	public List<WechatReplyRule> findRuleList(SysUnit companyUnit, Long id, Page pageInfo, WechatReplyRule replyRule) {
		return dao.findRuleList( companyUnit,  id, pageInfo, replyRule);
	}
	public List<WechatReplyRule> findRuleList(Page pageInfo, WechatReplyRule replyRule) {
		return dao.findRuleList(pageInfo, replyRule);
	}



	public List<WechatReplyRule> findRuleList(List<SysUnit> sysUnList, Page pageInfo, WechatReplyRule replyRule) {
		return dao.findRuleList( sysUnList, pageInfo, replyRule);
	}



	public boolean checkRuleHasItem(WechatDataItem dataItem) {
		
		WechatReplyRule replyRule = dao.checkRuleHasItem(dataItem);
		boolean flag = true;
		if (replyRule != null) {
			flag = false;
		}
		
		return flag;
	}

   

}
