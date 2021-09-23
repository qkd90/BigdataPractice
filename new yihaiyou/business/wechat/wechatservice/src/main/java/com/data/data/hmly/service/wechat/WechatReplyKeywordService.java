package com.data.data.hmly.service.wechat;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.data.hmly.service.wechat.dao.WechatAccountDao;
import com.data.data.hmly.service.wechat.dao.WechatReplyKeywordDao;
import com.data.data.hmly.service.wechat.dao.WechatReplyRuleDao;
import com.data.data.hmly.service.wechat.entity.WechatReplyKeyword;
import com.data.data.hmly.service.wechat.entity.WechatReplyRule;

@Service
public class WechatReplyKeywordService {
    @Resource
    private WechatReplyKeywordDao wechatReplyKeywordDao;
    @Resource
    private WechatAccountDao accountDao;
    @Resource
    private WechatReplyRuleDao ruleDao;
	
	/**
	 * 查询关键字信息
	 * @author caiys
	 * @date 2015年11月25日 下午3:03:05
	 * @param wechatReplyKeyword
	 * @return
	 */
	public List<WechatReplyKeyword> findBy(WechatReplyKeyword wechatReplyKeyword) {
		return wechatReplyKeywordDao.findBy(wechatReplyKeyword);
	}

	public void save(WechatReplyKeyword keyword) {
		keyword.setCreateTime(new Date());
		keyword.setModifyTime(new Date());
		wechatReplyKeywordDao.save(keyword);
	}

	public List<WechatReplyKeyword> findByRuleId(long ruleId) {
		
		WechatReplyRule rule = ruleDao.load(ruleId);
		
		return wechatReplyKeywordDao.findByRuleId( ruleId,  rule.getWechatAccount());
	}

	public void delKeywordByRule(WechatReplyRule replyRule) {
		
		
		wechatReplyKeywordDao.delKeywordByRule(replyRule);
	}
	
}
