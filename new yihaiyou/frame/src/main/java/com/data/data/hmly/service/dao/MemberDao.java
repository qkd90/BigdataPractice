package com.data.data.hmly.service.dao;

import java.util.List;

import com.data.data.hmly.service.entity.Member;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

import org.springframework.stereotype.Repository;

/**
 * Created by guoshijie on 2015/10/27.
 */
@Repository
public class MemberDao extends DataAccess<Member> {
	
	/**
	 * 功能描述：根据用户帐号获取用户
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月9日下午5:08:50
	 * @param account
	 * @return
	 */
	public Member findUserByAccount(String account) {
		Criteria<Member> c = new Criteria<Member>(Member.class);
		c.eq("account", account);
		List<Member> sulist = findByCriteria(c);
		if (sulist != null && !sulist.isEmpty()) {
			return sulist.get(0);
		} else {
			return null;
		}
	}
	
}
