package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.UserDao;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;



/**
 * Created by vacuity on 15/11/3.
 */

@Service
public class UserService {

	private static final Logger logger = Logger.getLogger(UserService.class);

	@Resource
	private UserDao dao;

	public User get(long id) {
		Criteria<User> criteria = new Criteria<User>(User.class);
		criteria.eq("id", id);
		return dao.findUniqueByCriteria(criteria);
	}

	public User findByAccount(String account, SysSite sysSite) {
		Criteria<User> criteria = new Criteria<User>(User.class);
		criteria.eq("account", account);
		criteria.eq("sysSite.id", sysSite.getId());
		return dao.findUniqueByCriteria(criteria);
	}

	public boolean save(User member) {
		try {
			member.setCreatedTime(new Date());
			member.setUpdateTime(new Date());
			dao.save(member);
		} catch (Exception e) {
			logger.error("注册失败", e);
			return false;
		}
		return true;
	}


	public boolean update(User user) {
		try {
			dao.save(user);
		} catch (Exception e) {
			logger.error("更新member失败#" + user.getId(), e);
			return false;
		}
		return true;
	}


	public List<SysUser> getUserList(String sceId,String status, String cityIdStr, Page pageInfo, SysUnit companyUnit, Boolean isSupperAdmin, Boolean isSiteAdmin) {

		return dao.getUserList(sceId,status,cityIdStr,pageInfo, companyUnit, isSupperAdmin, isSiteAdmin);
	}

	public void updateUserInfo(User user) {
		dao.update(user);
	}


}
