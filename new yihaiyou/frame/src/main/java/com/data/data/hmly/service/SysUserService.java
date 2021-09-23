package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.SysSiteDao;
import com.data.data.hmly.service.dao.SysUnitDao;
import com.data.data.hmly.service.dao.SysUnitDetailDao;
import com.data.data.hmly.service.dao.SysUserDao;
import com.data.data.hmly.service.dao.SysUserRoleDao;
import com.data.data.hmly.service.dao.UserDao;
import com.data.data.hmly.service.dao.UserRelationDao;
import com.data.data.hmly.service.entity.*;
import com.data.data.hmly.util.Encryption;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.framework.hibernate.util.RegUtil;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserService {
    @Resource
    private UserDao userDao;
	@Resource
	private SysUserDao dao;
	@Resource
	private SysUnitDao sysUnitDao;
	@Resource
	private SysUnitDetailDao sysUnitDetailDao;
	@Resource
	private SysUserRoleDao sysUserRoleDao;
	@Resource
	private SysSiteDao sysSiteDao;
	@Resource
	private UserRelationDao userRelationDao;
	@Resource
	private SysUnitImageService unitImageService;
	@Resource
	private SysUnitQualificationService unitQualificationService;


	/**
	 * 通过帐号名称获取用户数量
	 * @param account
	 * @return
	 */
	public Integer getCountUserByAccount(String account) {
		Criteria<SysUser> c = new Criteria<SysUser>(SysUser.class);
		c.eq("account", account);
		return dao.findByCriteria(c).size();
	}
	
	
	/**
	 * 功能描述：用户保存
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:27:18
	 * @param user
	 */
	public Integer saveOrUpdateUser(SysUser user) {
		if (user.getIsUse() == null) {
			user.setIsUse(true);
		}
		if (user.getDelFlag() == null) {
			user.setDelFlag(false);
		}
		if (user.getCreatedTime() == null) {
			user.setCreatedTime(new Date());
		}
		if (user.getUpdateTime() == null) {
			user.setUpdateTime(new Date());
		}
		if (user != null) {
			if (user.getId() == null) {
				SysUser sysUser = findUserByAccount(user.getAccount());
				if (sysUser != null) {
					return 1; // 帐户名重复
				}
				dao.save(user);
			} else {
				dao.update(user);
			}
		}
		return 0;
	}

	/**
	 * 功能描述：获取登录用户是否存在
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2014年12月12日下午3:35:22
	 * @param user
	 * @return
	 */
	public SysUser findLoginUser(SysUser user) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysUser where");
		sb.append(" account = '" + user.getAccount() + "'");
		sb.append(" and password ='" + user.getPassword() + "'");
		if (user.getUserType() != null) {
			sb.append(" and userType ='" + user.getUserType() + "'");
		}
		// Criteria<SysUser> criteria = new Criteria<SysUser>(SysUser.class);
		// criteria.eq("account", user.getAccount());
		// criteria.eq("password", pwd);
		// List<SysUser> loginusers = dao.findByCriteria(criteria);
		SysUser loginuser = dao.findOneByHQL(sb.toString());
		return loginuser;
	}

	/**
	 * 功能描述：根据用户帐号获取用户
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月9日下午5:08:50
	 * @param account
	 * @return
	 */
	public SysUser findUserByAccount(String account) {
		Criteria<SysUser> c = new Criteria<SysUser>(SysUser.class);
		c.eq("account", account);
		List<SysUser> sulist = dao.findByCriteria(c);
		if (sulist != null && sulist.size() > 0) {
			return sulist.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 功能描述：根据用户帐号获取用户
	 * 
	 * @author caiys
	 * @date 2015年11月8日 上午8:07:19
	 * @param account
	 * @param neUserId	要排除的用户id
	 * @return
	 */
	public boolean isExistsAccount(String account, String neUserId) {
        return userDao.isExistsAccount(account, neUserId);
	}

	/**
	 * 功能描述：根据用户帐号获取用户
	 *
	 * @author caiys
	 * @date 2015年11月8日 上午8:07:19
	 * @param user
	 * @param neUserId	要排除的用户id
	 * @return
	 */
	public boolean isExistsAccount(User user, String neUserId) {
		return userDao.isExistsAccount(user, neUserId);
	}

	/**
	 * 功能描述：用户删除
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:53:19
	 * @param user
	 */
	public void deleteUser(SysUser user) {
		if (user != null) {
			if (user.getId() != null) {
				String hql = "update SysUser set delFlag = ? where id= ? ";
				dao.updateByHQL(hql, true, user.getId());
			}
		}
	}

	/**
	 * 功能描述：根据ID删除
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:53:57
	 * @param userId
	 */
	public void deleteUserById(Long userId) {
		if (userId != null) {
			String hql = "update SysUser set delFlag = ? where id= ? ";
			dao.updateByHQL(hql, true, userId);
		}
	}

	/**
	 * 功能描述：激活
	 * 
	 * @param userId
	 */
	public void doOkUser(Long userId) {
		if (userId != null) {
			String hql = "update SysUser set isUse = ? where id= ? ";
			dao.updateByHQL(hql, true, userId);
		}

	}

	/**
	 * 功能描述：冻结
	 * 
	 * @param userId
	 */
	public void doNoUser(Long userId) {
		if (userId != null) {
			String hql = "update SysUser set isUse = ? where id= ? ";
			dao.updateByHQL(hql, false, userId);
		}
	}

	/**
	 * 功能描述：根据ID获取用户
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:42:56
	 * @param userId
	 * @return
	 */
	public SysUser findSysUserById(Long userId) {
		if (userId != null) {
			return dao.get(SysUser.class, userId);
		}
		return null;
	}

	/**
	 * 功能描述：查询用户信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:39:17
	 * @param user
	 * @param page
	 * @param sysSite
	 * @param isSuperAdmin
	 * @return
	 */
	public List<SysUser> findUserList(SysUser user, Page page, SysSite sysSite, Boolean isSuperAdmin) {
		Criteria<SysUser> c = new Criteria<SysUser>(SysUser.class);
		foramtCond(user, c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false)));
		if (!isSuperAdmin) {
			c.eq("sysSite.id", sysSite.getId());
		}
		List<SysUser> users = dao.findByCriteria(c, page);
		if (users != null) {
			for (SysUser u : users) {
				String hql = " select s.role from SysUserRole s where s.role.delFlag='0' and s.role.status='0' and s.user.id = ?";
				List<SysRole> roles = dao.findByHQL(hql, u.getId());
				if (roles != null) {
					String rolenames = "";
					for (int i = 0; i < roles.size(); i++) {
						if (i != 0) {
							rolenames += ",";
						}
						rolenames += roles.get(i).getName();
					}
					u.setRoles(rolenames);
				}
			}
		}
		return users;
	}
	public List<SysUser> findUserListBySysUser(SysUser user, SysUnit sysUnit, Page page, SysSite sysSite, Boolean isSuperAdmin) {
		Criteria<SysUser> c = new Criteria<SysUser>(SysUser.class);
		c.createCriteria("sysUnit", "sysUnit", JoinType.INNER_JOIN);
		foramtPara(user, c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false)));
		
		SysUnit unit = sysUnitDao.load(sysUnit.getId());
		
		if (unit.getParentUnit() != null) {
			String unitHql = "from SysUnit syu where syu.id = " + sysUnit.getId();
			
			List<SysUnit> sysUnits = sysUnitDao.findByHQL(unitHql);
			
			c.in("sysUnit", sysUnits);
		} else {
			String unitHql = "from SysUnit syu where syu.companyUnit.id = " + sysUnit.getId();
			
			List<SysUnit> sysUnits = sysUnitDao.findByHQL(unitHql);
			
			c.in("sysUnit", sysUnits);
//			if (!isSuperAdmin) {
//				c.eq("sysSite.id", sysSite.getId());
//			}
		}


		

		List<SysUser> users = dao.findByCriteria(c, page);
		if (users != null) {
			for (SysUser u : users) {
				String hql = " select s.role from SysUserRole s where s.role.delFlag='0' and s.role.status='0' and s.user.id = ?";
				List<SysRole> roles = dao.findByHQL(hql, u.getId());
				if (roles != null) {
					String rolenames = "";
					for (int i = 0; i < roles.size(); i++) {
						if (i != 0) {
							rolenames += ",";
						}
						rolenames += roles.get(i).getName();
					}
					u.setRoles(rolenames);
				}
			}
		}
		return users;
	}

	public SysUser findByMobile(String mobile) {
		Criteria<SysUser> criteria = new Criteria<>(SysUser.class);
		criteria.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		criteria.eq("mobile", mobile);
		List<SysUser> userList = dao.findByCriteria(criteria);
		if (userList.isEmpty()) {
			return null;
		}
		return userList.get(0);
	}

	/*public List<SysUser> getUserList(SysUser user){
		List<SysUser> users = new ArrayList<SysUser>();
		
		if(user.get){
			
		}
		
		return users;
		
	}*/
	
	

	/**
	 * 功能描述：查询条件拼接
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:38:50
	 * @param user
	 * @param c
	 */
	public void foramtCond(SysUser user, Criteria<SysUser> c) {
		if (user != null) {
			if (StringUtils.isNotBlank(user.getUserName())) {
				c.like("userName", user.getUserName());
			}
			if (StringUtils.isNotBlank(user.getAccount())) {
				c.like("account", user.getAccount());
			}
			if (StringUtils.isNotBlank(user.getUserName())) {
				c.like("mobile", user.getMobile());
			}
			if (StringUtils.isNotBlank(user.getAddress())) {
				c.like("address", user.getAddress());
			}
			if (StringUtils.isNotBlank(user.getEmail())) {
				c.like("email", user.getEmail());
			}
			// if (StringUtils.isNotBlank(user.getPhone())) {
			// c.like("phone", user.getPhone());
			// }
			if (user.getSysUnit() != null && user.getSysUnit().getId() != null) {
				c.eq("sysUnit.id", user.getSysUnit().getId());
			}
			// if (user.getIsUse() != null) {
			// c.eq("isUse", user.getIsUse());
			// }
			// if (user.getRole() != null) {
			// c.createAlias("role", "r");
			// if (user.getRole().getId() != null) {
			// c.eq("r.id", user.getRole().getId());
			// }
			// if (StringUtils.isNotBlank(user.getRole().getName())) {
			// c.like("r.name", user.getRole().getName());
			// }
			// }
			if (StringUtils.isNotBlank(user.getGender())) {
				c.like("gender", user.getGender());
			}

		}
	}
	
	
	/**
	 * 功能描述：查询条件拼接
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:38:50
	 * @param user
	 * @param c
	 */
	public void foramtPara(SysUser user, Criteria<SysUser> c) {
		if (user != null) {
			if (StringUtils.isNotBlank(user.getUserName())) {
				c.like("userName", user.getUserName());
			}
			if (StringUtils.isNotBlank(user.getAccount())) {
				c.like("account", user.getAccount());
			}
			if (StringUtils.isNotBlank(user.getMobile())) {
				c.like("mobile", user.getMobile());
			}
			if (StringUtils.isNotBlank(user.getAddress())) {
				c.like("address", user.getAddress());
			}
			if (StringUtils.isNotBlank(user.getEmail())) {
				c.like("email", user.getEmail());
			}
			// if (StringUtils.isNotBlank(user.getPhone())) {
			// c.like("phone", user.getPhone());
			// }
//			if (user.getSysUnit() != null && user.getSysUnit().getId() != null) {
//				c.eq("sysUnit.id", user.getSysUnit().getId());
//			}
			// if (user.getIsUse() != null) {
			// c.eq("isUse", user.getIsUse());
			// }
			// if (user.getRole() != null) {
			// c.createAlias("role", "r");
			// if (user.getRole().getId() != null) {
			// c.eq("r.id", user.getRole().getId());
			// }
			// if (StringUtils.isNotBlank(user.getRole().getName())) {
			// c.like("r.name", user.getRole().getName());
			// }
			// }
			if (StringUtils.isNotBlank(user.getGender())) {
				c.like("gender", user.getGender());
			}

		}
	}

	/**
	 * 功能描述：更新用户角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月9日下午3:03:41
	 * @param roleIds
	 * @param user
	 */
	public void updateUserRole(String roleIds, SysUser user) {
		String delhal = " delete SysUserRole where user.id = ? ";
		dao.updateByHQL(delhal, user.getId());
		if (StringUtils.isNotBlank(roleIds)) {
			String[] idarr = roleIds.split(",");
			for (String id : idarr) {
				String insert = "INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES ( ?, ?)";
				dao.updateBySQL(insert, user.getId(), id);
			}
		}
	}

	/**
	 * 功能描述：更新登陆用户的登陆次数
	 * 
	 * @author : Teny_lu 刘鹰
	 * @E_Mail : liuying5590@163.com
	 * @CreatedTime : 2014年12月16日-上午9:00:12
	 * @return
	 */
	public void updateUser(SysUser loginuser) {
		if (loginuser != null && loginuser.getId() != null) {
			dao.update(loginuser);
		}
	}

	/**
	 * 功能描述：根据用户名密码查询用户
	 * 
	 * @author : Teny_lu 刘鹰
	 * @E_Mail : liuying5590@163.com
	 * @CreatedTime : 2015年4月21日-下午12:00:08
	 * @return
	 */
	public SysUser findUserByLogin(SysUser user) {
		StringBuffer sb = new StringBuffer("FROM SysUser wf WHERE 1=1");
		sb.append(" AND wf.account = '").append(user.getAccount().trim()).append("'");
		String pwd = Encryption.encry(user.getPassword());
		sb.append(" AND wf.password = '").append(pwd).append("'");
		sb.append(" AND wf.isUse = '0' "); // 状态为已激活
		sb.append(" AND wf.delFlag = '0'");

		List<SysUser> list = dao.findByHQL(sb.toString(), new Object[] {});
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 功能描述：
	 * 
	 * @author : Teny_lu 刘鹰
	 * @E_Mail : liuying5590@163.com
	 * @CreatedTime : 2015年4月21日-下午12:28:06
	 * @return
	 */
	public SysUser findWfStaffByUser(SysUser user) {
		StringBuffer sb = new StringBuffer("FROM SysUser wf WHERE 1=1");
		sb.append(" AND wf.account = '").append(user.getAccount().trim()).append("'");
		sb.append(" AND wf.password = '").append(user.getPassword().trim()).append("'");
		sb.append(" AND wf.isUse = '0' "); // 状态为已激活
		sb.append(" AND wf.delFlag = '0'");

		List<SysUser> list = dao.findByHQL(sb.toString(), new Object[] {});
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据标识获取用户
	 * @author caiys
	 * @date 2015年11月5日 上午9:42:22
	 * @param id
	 * @return
	 */
	public SysUser load(long id) {
		return dao.load(id);
	}
	
	/**
	 * 查询供应商管理员信息
	 * @author caiys
	 * @date 2015年11月7日 下午7:55:29
	 * @param unitId
	 * @return
	 */
	public SysUser findCompanyManager(Long unitId, UserType userType) {
		Criteria<SysUser> c = new Criteria<SysUser>(SysUser.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		c.eq("sysUnit.id", unitId);
		c.eq("userType", userType);
		List<SysUser> users = dao.findByCriteria(c);
		if (users.size() > 0) {
			return users.get(0);  
		} else {
			return null;
		}
	}
	
	/**
	 * 邀请入住信息保存：公司信息、公司详情、默认人员信息（角色）
	 * @author caiys
	 * @date 2015年11月4日 下午5:13:38
	 * @param user
	 * @param unit
	 * @param unitDetail
	 * @param unitImages
	 */
	public void doInivited(SysUser user, SysUnit unit, SysUnitDetail unitDetail, List<SysUnitImage> unitImages, List<UserRelation> userRelations) {
		// 新增时默认用户角色，通过才授予默认角色
		if (unit.getId() == null) {
			SysUserRole userRole = new SysUserRole();
			userRole.setUser(user);
			SysRole role = new SysRole();
			role.setId(UserType.CompanyManage.getId()); // 默认角色
			userRole.setRole(role);
			sysUserRoleDao.save(userRole);
		}
		unit.setCompanyUnit(unit);
		unit.setSysUnitDetail(unitDetail);
		unitDetail.setSysUnit(unit);

		RegUtil.regFilter(unit);
		sysUnitDao.save(unit);
		RegUtil.regFilter(unitDetail);
		sysUnitDetailDao.save(unitDetail);
		user.setSysUnit(unit);
		RegUtil.regFilter(user);
		dao.save(user);

		if (userRelations != null && userRelations.size() > 0) {
			userRelationDao.save(userRelations);
		}
		unitImageService.deleteByUnit(unit);
		if (unitImages != null) {
			unitImageService.save(unitImages, unit);
		}
		if (unit.getQualificationList() != null) {
			List<UnitQualification> qualificationList = unit.getQualificationList();
			unitQualificationService.saveQuanlificationAll(qualificationList, unit);
		}
	}
	
	/**
	 * 新增、编辑站点信息保存：站点信息、公司信息、公司详情、默认人员信息（角色）
	 * @author caiys
	 * @date 2015年11月4日 下午5:13:38
	 * @param user
	 * @param unit
	 * @param unitDetail
	 */
	public void saveSiteInfo(SysSite site, SysUser user, SysUnit unit, SysUnitDetail unitDetail) {
		sysSiteDao.save(site);
		// 新增时默认用户角色
		if (unit.getId() == null) {
			SysUserRole userRole = new SysUserRole();
			userRole.setUser(user);
			SysRole role = new SysRole();
			role.setId(user.getUserType().getId()); // 默认角色
			userRole.setRole(role);
			sysUserRoleDao.save(userRole);
		}
		unit.setCompanyUnit(unit);
		unit.setSysUnitDetail(unitDetail);
		unitDetail.setSysUnit(unit);
		sysUnitDao.save(unit);
		sysUnitDetailDao.save(unitDetail);
		user.setSysUnit(unit);
		dao.save(user);
	}

	public List<SysUser> findAll(SysUser sysUser) {
		Criteria<SysUser> criteria = new Criteria<SysUser>(SysUser.class);
		if (sysUser.getUserType() != null) {
			criteria.eq("userType", sysUser.getUserType());
		}
		if (sysUser.getSysUnit() != null) {
			if (sysUser.getSysUnit().getUnitType() != null) {
				criteria.createCriteria("sysUnit", "sysUnit", JoinType.INNER_JOIN).add(Restrictions.eq("unitType", sysUser.getSysUnit().getUnitType()));
			} else if (sysUser.getId() != null) {
				criteria.eq("sysUnit.id", sysUser.getSysUnit().getId());
			}
		}

		return dao.findByCriteria(criteria);
	}

	public List<SysUser> findAll() {
		return dao.findAll();
	}

	public void editUserRelation(SysUser sysUser) {
		userRelationDao.deleteByUid(sysUser.getId());
		sysUser = dao.load(sysUser.getId());
		if (sysUser != null && sysUser.getParent() != null) {
			UserRelation userRelation1 = new UserRelation();
			userRelation1.setChildUser(sysUser);
			userRelation1.setParentUser(sysUser.getParent());
			userRelation1.setLevel(1);
			userRelation1.setCreatedTime(new Date());
			userRelationDao.save(userRelation1);
		
			User parUser2 = null;
			if (sysUser.getParent() != null) {
				parUser2 =  dao.load(sysUser.getParent().getId());
			}
			if (parUser2 != null && parUser2.getParent() != null) {
				UserRelation userRelation2 = new UserRelation();
				userRelation2.setChildUser(sysUser);
				userRelation2.setParentUser(parUser2.getParent());
				userRelation2.setLevel(2);
				userRelation2.setCreatedTime(new Date());
				userRelationDao.save(userRelation2);
				User parUser3 = null;
				if (parUser2.getParent() != null) {
					parUser3 =  dao.load(parUser2.getParent().getId());
				}
				if (parUser3 != null && parUser3.getParent() != null) {
					UserRelation userRelation3 = new UserRelation();
					userRelation3.setChildUser(sysUser);
					userRelation3.setParentUser(parUser3.getParent());
					userRelation3.setLevel(3);
					userRelation3.setCreatedTime(new Date());
					userRelationDao.save(userRelation3);
					
				}
			}
		}
		
		
		
	}
	
	public static SysUser getUserParent(SysUser sysUser){
		
		if (sysUser.getParent() != null) {
			return getUserParent((SysUser) sysUser.getParent());
		}
		return null;
		
	}

	public void doResetPassword(SysUser user) {
		if (user != null) {
			user.setPassword(Encryption.encry("123456"));
			userDao.update(user);
		}
	}

	public void update(SysUser user) {
		userDao.update(user);
	}
}
