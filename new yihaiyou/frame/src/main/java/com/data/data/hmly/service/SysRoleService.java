package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.SysRoleDao;
import com.data.data.hmly.service.entity.SysRole;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.SysUserRole;
import com.data.data.hmly.service.entity.UserType;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleService {
	@Resource
	private SysRoleDao dao;

	/**
	 * 功能描述：用户保存
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:27:18
	 */
	public void saveOrUpdateRole(SysRole role) {
		if (role != null) {
			if (role.getStatus() == null) {
				role.setStatus(0);
			}
			if (role.getDelFlag() == null) {
				role.setDelFlag(false);
			}
			if (role.getSeq() == null) {
				role.setSeq(0);
			}
			if (role.getId() == null) {
				dao.save(role);
			} else {
				dao.update(role);
			}
		}
	}

	/**
	 * 功能描述：用户删除
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:53:19
	 */
	public void deleteRole(SysRole role) {
		if (role != null) {
			if (role.getId() != null) {
				String hql = "update SysRole set delFlag = ? where id= ? ";
				dao.updateByHQL(hql, false, role.getId());
			}
		}
	}

	/**
	 * 功能描述：根据ID删除角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:53:57
	 */
	public void deleteUserById(Long roleId) {
		if (roleId != null) {
			String hql = "update SysRole set delFlag = ? where id= ?";
			dao.updateByHQL(hql, false, roleId);
		}
	}

	/**
	 * 功能描述：根据ID获取角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:42:56
	 * @return
	 */
	public SysRole findSysRoleById(Long roleId) {
		if (roleId != null) {
			return dao.get(SysRole.class, roleId);
		}
		return null;
	}

	public List<SysRole> findAllRoles() {

		Criteria<SysRole> c = new Criteria<SysRole>(SysRole.class);

		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		c.eq("status", 0);
		c.orderBy(Order.asc("id"));
		List<SysRole> allRoles = dao.findByCriteria(c);
		return allRoles;

	}

	/**
	 * 功能描述：查询用户权限
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月9日下午2:26:38
	 * @param sysUser
	 * @param sysSite
	 * @return
	 */
	public List<SysRole> findAllUserRoles(Boolean isSupperAdmin, SysSite sysSite, SysUser sysUser) {
		Criteria<SysRole> c = new Criteria<SysRole>(SysRole.class);
		if (!isSupperAdmin) {
			List<SysUserRole> hasUserRoles = sysUser.getSysRoles();
			if (hasUserRoles.isEmpty()) {
				c.eq("sysSite.id", "-99");	// 不查询出数据，一个不可能的值
			} else if (hasUserRoles.get(0).getRole().getId() == UserType.SiteManage.getId()) {	// 站点角色，查询特殊角色、本身角色、自身站点下的角色
				c.or(Restrictions.eq("sysSite.id", sysSite.getId()),
						Restrictions.and(Restrictions.eq("sysSite.id", -1L), Restrictions.ne("id", UserType.AllSiteManage.getId())));
			} else if (hasUserRoles.get(0).getRole().getId() == UserType.CompanyManage.getId()) {	// 公司角色，查询本身角色、自身站点下的角色
				List<Long> hasUserRoleIds = new ArrayList<Long>();
				for (SysUserRole hasUserRole : hasUserRoles) {
					hasUserRoleIds.add(hasUserRole.getRole().getId());
				}
				c.or(Restrictions.eq("sysSite.id", sysSite.getId()), Restrictions.in("id", hasUserRoleIds));
			} else {	// 否则，查询本身角色
				List<Long> hasUserRoleIds = new ArrayList<Long>();
				for (SysUserRole hasUserRole : hasUserRoles) {
					hasUserRoleIds.add(hasUserRole.getRole().getId());
				}
				c.add(Restrictions.in("id", hasUserRoleIds));
			}
		}
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		c.eq("status", 0);
		c.orderBy(Order.asc("id"));
		List<SysRole> allRoles = dao.findByCriteria(c);
		return allRoles;
	}

	/**
	 * 功能描述：查询角色信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:39:17
	 * @param sysSite
	 * @param page
	 * @param sysUser
	 * @return
	 */
	public List<SysRole> findRoleList(Boolean isSupperAdmin, SysSite sysSite, SysRole role, Page page, SysUser sysUser) {
		Criteria<SysRole> c = new Criteria<SysRole>(SysRole.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		// c.ne("delFlag", true);// 过滤未删除的数据是否删除 0(false)未删 1(true)已删除
		if (!isSupperAdmin) {
			List<SysUserRole> hasUserRoles = sysUser.getSysRoles();
			if (hasUserRoles.isEmpty()) {
				c.eq("sysSite.id", "-99");	// 不查询出数据，一个不可能的值
			} else if (hasUserRoles.get(0).getRole().getId() == UserType.SiteManage.getId()) {	// 站点角色，查询特殊角色、本身角色、自身站点下的角色
				c.or(Restrictions.eq("sysSite.id", sysSite.getId()),
						Restrictions.and(Restrictions.eq("sysSite.id", -1L), Restrictions.ne("id", UserType.AllSiteManage.getId())));
			} else if (hasUserRoles.get(0).getRole().getId() == UserType.CompanyManage.getId()) {	// 公司角色，查询本身角色、自身站点下的角色
				List<Long> hasUserRoleIds = new ArrayList<Long>();
				for (SysUserRole hasUserRole : hasUserRoles) {
					hasUserRoleIds.add(hasUserRole.getRole().getId());
				}
				c.or(Restrictions.eq("sysSite.id", sysSite.getId()), Restrictions.in("id", hasUserRoleIds));
			} else {	// 否则，查询本身角色
				List<Long> hasUserRoleIds = new ArrayList<Long>();
				for (SysUserRole hasUserRole : hasUserRoles) {
					hasUserRoleIds.add(hasUserRole.getRole().getId());
				}
				c.add(Restrictions.in("id", hasUserRoleIds));
			}
		}
		foramtCond(role, c);
		c.orderBy(Order.asc("id"));
		return dao.findByCriteria(c, page);
	}

	/**
	 * 功能描述：获取用户角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午3:01:20
	 * @param sysUser
	 * @return
	 */
	public List<SysRole> findUserRoles(SysUser sysUser) {
		StringBuffer sb = new StringBuffer();
		sb.append("select role from SysRole role,SysUser user where (role.delFlag is null or role.delFlag='0') ");
		sb.append(" and role.id=user.role.id and user.id =? ");
		List<SysRole> list = dao.findByHQL(sb.toString(), sysUser.getId());
		return list;
	}

	/**
	 * 功能描述：获取用户角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月9日下午2:31:51
	 * @param userId
	 * @return
	 */
	public List<SysUserRole> findUserRoles(Long userId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysUserRole where role.delFlag='0' ");
		sb.append(" and user.id =? ");
		sb.append(" order by role.seq asc");

		Criteria<SysUserRole> criteria = new Criteria<SysUserRole>(SysUserRole.class);
		criteria.eq("role.delFlag", 0);
		criteria.eq("user.id", userId);
		criteria.orderBy(Order.asc("role.seq"));

		List<SysUserRole> list = dao.findByHQL(sb.toString(), userId);
		// c.ne("delFlag", true);// 过滤未删除的数据是否删除 0(false)未删 1(true)已删除
		return list;
	}

	/**
	 * 功能描述：获取用户 已激活的角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月9日下午2:31:51
	 * @param userId
	 * @return
	 */
	public List<SysUserRole> findUserRolesIsUse(Long userId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysUserRole where role.delFlag='0' and role.status='0' ");
		sb.append(" and user.id =? ");
		sb.append(" order by role.seq asc");
		List<SysUserRole> list = dao.findByHQL(sb.toString(), userId);
		// c.ne("delFlag", true);// 过滤未删除的数据是否删除 0(false)未删 1(true)已删除
		return list;
	}

	/**
	 * 功能描述：查询条件拼接
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:38:50
	 * @param c
	 */
	public void foramtCond(SysRole role, Criteria<SysRole> c) {
		if (role != null) {
			if (StringUtils.isNotBlank(role.getName())) {
				c.like("name", role.getName());
			}
			if (role.getStatus() != null) {
				c.eq("status", role.getStatus());
			}
			if (StringUtils.isNotBlank(role.getRemark())) {
				c.like("remark", role.getRemark());
			}
		}
	}
}
