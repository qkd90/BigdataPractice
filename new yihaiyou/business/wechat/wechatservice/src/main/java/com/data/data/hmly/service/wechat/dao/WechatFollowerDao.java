package com.data.data.hmly.service.wechat.dao;

import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatFollower;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WechatFollowerDao extends DataAccess<WechatFollower> {
	
	public WechatFollower get(Long id) {
		Criteria<WechatFollower> criteria = new Criteria<WechatFollower>(WechatFollower.class);
		criteria.eq("id", id);
		return findUniqueByCriteria(criteria);
	}
	
	public List<WechatFollower> getFollowerList(WechatAccount account, Page page) {
		Criteria<WechatFollower> criteria = new Criteria<WechatFollower>(WechatFollower.class);
		criteria.eq("followAccount.id", account.getId());
		return findByCriteria(criteria, page);
	}
	public List<WechatFollower> getFollowerListByAccount(WechatAccount account, Page page) {
		Criteria<WechatFollower> criteria = new Criteria<WechatFollower>(WechatFollower.class);
		criteria.eq("followAccount.id", account.getId());
		return findByCriteria(criteria, page);
	}

	public List<WechatFollower> getFollowerListAll(WechatAccount account) {
		Criteria<WechatFollower> criteria = new Criteria<WechatFollower>(WechatFollower.class);
		criteria.eq("followAccount.id", account.getId());
		return findByCriteria(criteria);
	}
	
	public void deleteFollowerList(WechatAccount account) {
		updateByHQL("delete WechatFollower where followAccount.id=?", account.getId());
	}

	/**
	 * 条件获取粉丝列表数据
	 * @param follower
	 * @param isSupport
	 * @param isHasSupport
	 * @param page  @return
	 */
	public List<WechatFollower> getFollowerList(WechatFollower follower, String isSupport, Boolean isHasSupport, Page page) {

		StringBuilder sb = new StringBuilder();
		List param = new ArrayList();
		sb.append("from WechatFollower wf where wf.followAccount.id=?");
		param.add(follower.getFollowAccount().getId());
		if (follower.getNickName() != null) {
			sb.append(" and wf.nickName=?");
			param.add(follower.getNickName());
		}

		if (StringUtils.isNotBlank(isSupport)) {
			if (Boolean.parseBoolean(isSupport)) {
				sb.append(" and exists(select 1 from WechatSupportAccount wsa where wsa.wechatAccount.id=? and wf.openId=wsa.openId)");
				param.add(follower.getFollowAccount().getId());
			} else {
				if (isHasSupport) {
					sb.append(" and not exists(select 1 from WechatSupportAccount wsa where wsa.wechatAccount.id=? and wf.openId = wsa.openId)");
					param.add(follower.getFollowAccount().getId());
				}
			}
		}
		sb.append(" order by wf.subscribeTime desc");
		return findByHQL(sb.toString(), page, param.toArray());
	}

	public WechatFollower getByOpenId(String openId, Long wechatAccountId) {
		Criteria<WechatFollower> criteria = new Criteria<WechatFollower>(WechatFollower.class);
		criteria.eq("openId", openId);
		criteria.eq("followAccount.id", wechatAccountId);
        return findUniqueByCriteria(criteria);
	}
}
