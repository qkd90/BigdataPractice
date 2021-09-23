package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.SysSiteDao;
import com.data.data.hmly.service.dao.SysUnitDao;
import com.data.data.hmly.service.dao.SysUserDao;
import com.data.data.hmly.service.dao.SysUserRoleDao;
import com.data.data.hmly.service.entity.SysRole;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitDetail;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.SysUserRole;
import com.data.data.hmly.service.entity.UnitType;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.service.vo.UnitVo;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.ConvertUtils;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Sys unit service.
 */
@Service
public class SysUnitService {
	@Resource
	private SysUnitDao dao;
	@Resource
	private SysSiteDao sysSiteDao;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysUserRoleDao sysUserRoleDao;


	/**
	 * 查询单位列表
	 * @param unit
	 * @param page
	 * @return
	 */
	public List<SysUnit> listCompanys(SysUnit unit, Page page) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		if (unit.getStatus() != null) {
			c.eq("status", unit.getStatus());
		}
		if (unit.getUnitType() != null) {
			c.eq("unitType", unit.getUnitType());
		}
		if (unit.getId() != null) {
			c.eq("id", unit.getId());
		}
        if (unit.getTestFlag() != null) {
            c.eq("testFlag", unit.getTestFlag());
        }
		c.orderBy(Order.asc("id"));
		return dao.findByCriteria(c, page);
	}


	/**
	 * 功能描述：通过identityCode获取公司
	 * @param identityCode
	 * @return
	 */
	public SysUnit findUnitByIdentityCode(String identityCode) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.eq("identityCode", identityCode);
		return dao.findUniqueByCriteria(c);
	}


	/**
	 * 功能描述：判断串码的唯一性
	 * @param identityCode
	 * @return
	 */
	public boolean doCheckIdentityCode(String identityCode) {

		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.eq("identityCode", identityCode);

		Integer resultSize = dao.findByCriteria(c).size();

		if (resultSize != null && resultSize != 0) {
			return false;
		}
		return true;
	}

	

	/**
	 * 功能描述：组织保存
	 *
	 * @param unit the unit
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:27:18
	 */
	public void saveOrUpdateUnit(SysUnit unit) {
		if (unit != null) {
			if (unit.getStatus() == null) {
				unit.setStatus(0);
			}
			if (unit.getDelFlag() == null) {
				unit.setDelFlag(false);
			}
			if (unit.getSeq() == null) {
				unit.setSeq(0);
			}
			if (unit.getId() == null) {
				unit.setUnitType(UnitType.department);
				unit.setCreateTime(new Date());
				unit.setSysUnitDetail(null);
				dao.save(unit);
			} else {
				dao.updateTransisent(unit);
			}
		}
	}

	/**
	 * 通过公司名称获取公司数量
	 *
	 * @param name the name
	 * @return count unit by name
	 */
	public Integer getCountUnitByName(String name) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.eq("name", name);
		return dao.findByCriteria(c).size();
	}


	/**
	 * Find site company list.
	 *
	 * @param siteId the site id
	 * @param page   the page
	 * @param c      the c
	 * @return the list
	 */
	public List<SysUnit> findSiteCompany(Long siteId, Page page, Criteria<SysUnit> c) {
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		// c.ne("delFlag", true);// 过滤未删除的数据是否删除 0(false)未删 1(true)已删除
		c.eq("unitType", UnitType.company);
		c.eq("sysSite.id", siteId);
		c.addOrder(Order.desc("id"));
		return dao.findByCriteria(c, page);
	}

	/**
	 * 查询站点公司信息
	 *
	 * @param siteId   the site id
	 * @param uintType the uint type
	 * @return sys unit
	 * @author caiys
	 * @date 2015年11月7日 下午7:55:29
	 */
	public SysUnit findUnit(Long siteId, UnitType uintType) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		c.eq("sysSite.id", siteId);
		c.eq("unitType", uintType);
		List<SysUnit> units = dao.findByCriteria(c);
		if (units.size() > 0) {
			return units.get(0);  
		} else {
			return null;
		}
	}

	/**
	 * 功能描述：组织删除
	 *
	 * @param unit the unit
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:53:19
	 */
	public void deleteUnit(SysUnit unit) {
		if (unit != null) {
			if (unit.getId() != null) {
				String hql = "update SysUnit set delFlag = ? where id= ? ";
				dao.updateByHQL(hql, false, unit.getId());
			}
		}
	}

	/**
	 * 功能描述：根据ID删除角色
	 *
	 * @param roleId the role id
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:53:57
	 */
	public void deleteUserById(Long roleId) {
		if (roleId != null) {
			String hql = "update SysUnit set delFlag = ? where id= ?";
			dao.updateByHQL(hql, false, roleId);
		}
	}

	/**
	 * 功能描述：根据ID获取角色
	 *
	 * @param roleId the role id
	 * @return sys unit
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:42:56
	 */
	public SysUnit findSysUnitById(Long roleId) {
		if (roleId != null) {
			return dao.get(SysUnit.class, roleId);
		}
		return null;
	}

	/**
	 * 功能描述：获取最大编号
	 *
	 * @param unitId the unit id
	 * @param site   the site
	 * @return string
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月9日上午9:01:23
	 */
	public String findMaxUnitNoById(Long unitId, SysSite site) {
		SysUnit parentUnit = findSysUnitById(unitId);
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		// c.ne("delFlag", true);// 过滤未删除的数据是否删除 0(false)未删 1(true)已删除
		c.eq("parentUnit.id", unitId);
		c.eq("sysSite.id", site.getId());
		c.orderBy(Order.desc("unitNo"));
		String maxUnitNo = parentUnit.getUnitNo();
		List<SysUnit> units = dao.findByCriteria(c, new Page(1, 1));
		if (units != null && units.size() > 0) {
			SysUnit unit = units.get(0);
			String last2 = unit.getUnitNo().substring(unit.getUnitNo().length() - 2);
			Integer max = ConvertUtils.objectToInteger(last2) + 1;
			if (max < 10) {
				maxUnitNo = maxUnitNo + "0" + max;
			} else {
				maxUnitNo += max;
			}
		} else {
			maxUnitNo += "01";
		}
		return maxUnitNo;
	}

	/**
	 * 功能描述：查询角色信息
	 *
	 * @param unit         the unit
	 * @param page         the page
	 * @param sysSite      the sys site
	 * @param isSuperAdmin the is super admin
	 * @return list
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:39:17
	 */
	public List<SysUnit> findUnitList(SysUnit unit, Page page, SysSite sysSite, Boolean isSuperAdmin) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		if (unit.getSysUnitDetail() != null) {
			DetachedCriteria dc = c.createCriteria("sysUnitDetail", "sysUnitDetail", JoinType.LEFT_OUTER_JOIN);
			if (unit.getSysUnitDetail().getSupplierType() != null) {
				dc.add(Restrictions.eq("supplierType", unit.getSysUnitDetail().getSupplierType()));
			}
			dc.add(Restrictions.isNull("scenicid"));
		}

		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		if (!isSuperAdmin) {
			c.eq("sysSite.id", sysSite.getId());
		}
		// c.ne("delFlag", true);// 过滤未删除的数据是否删除 0(false)未删 1(true)已删除
		foramtCond(unit, c);
		c.orderBy(Order.asc("seq"));
		return dao.findByCriteria(c, page);
	}

	/**
	 * 根据关键字查询公司和站点
	 *
	 * @param unit         the unit
	 * @param page         the page
	 * @param sysUser      the sys user
	 * @param isSuperAdmin the is super admin
	 * @param isSiteAdmin  the is site admin
	 * @return list
	 */
	public List<SysUnit> listCompanys(SysUnit unit, Page page, SysUser sysUser, Boolean isSuperAdmin, Boolean isSiteAdmin) {
        Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
        // 权限过滤
        if (!isSuperAdmin) {
            c.eq("sysSite.id", sysUser.getSysSite().getId());
            if (!isSiteAdmin) {
                c.eq("companyUnit.id", sysUser.getSysUnit().getCompanyUnit().getId());
            }
        }
        c.createCriteria("sysUnitDetail", "ud", JoinType.LEFT_OUTER_JOIN);
        c.isNull("ud.scenicid");
		c.eq("status", 0);
        c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
        foramtCond(unit, c);
        c.orderBy(Order.asc("seq"));
        return dao.findByCriteria(c, page);
    }

	/**
	 * 根据关键字查询公司和站点
	 *
	 * @param unit         the unit
	 * @param page         the page
	 * @param sysUser      the sys user
	 * @param isSuperAdmin the is super admin
	 * @return list
	 */
	public List<SysUnit> listContractCompanys(SysUnit unit, Page page, SysUser sysUser, Boolean isSuperAdmin) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		// 权限过滤
		if (!isSuperAdmin) {
			c.eq("sysSite.id", sysUser.getSysSite().getId());
//			if (!isSiteAdmin) {
//				c.eq("companyUnit.id", sysUser.getSysUnit().getCompanyUnit().getId());
//			}
		}
		c.eq("unitType", UnitType.company);
		c.createCriteria("sysUnitDetail", "ud", JoinType.LEFT_OUTER_JOIN);
		c.isNull("ud.scenicid");
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		foramtCond(unit, c);
		c.orderBy(Order.asc("seq"));
		return dao.findByCriteria(c, page);
	}


	/**
	 * 增加数据权限过滤
	 *
	 * @param unit         the unit
	 * @param page         the page
	 * @param sysUser      the sys user
	 * @param isSuperAdmin the is super admin
	 * @param isSiteAdmin  the is site admin
	 * @return list
	 * @author caiys
	 * @date 2015年11月18日 上午10:48:55
	 */
	public List<SysUnit> findUnitList(SysUnit unit, Page page, SysUser sysUser, Boolean isSuperAdmin, Boolean isSiteAdmin) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		if (unit.getSysUnitDetail() != null) {
			DetachedCriteria dc = c.createCriteria("sysUnitDetail", "sysUnitDetail", JoinType.LEFT_OUTER_JOIN);
			if (unit.getSysUnitDetail().getSupplierType() != null) {
				dc.add(Restrictions.eq("supplierType", unit.getSysUnitDetail().getSupplierType()));
			}
		}

		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		if (!isSuperAdmin) {
			c.eq("sysSite.id", sysUser.getSysSite().getId());
			if (!isSiteAdmin) {
				c.eq("companyUnit.id", sysUser.getSysUnit().getCompanyUnit().getId());
			}
		}
		// c.ne("delFlag", true);// 过滤未删除的数据是否删除 0(false)未删 1(true)已删除
		foramtCond(unit, c);
		c.orderBy(Order.asc("seq"));
		return dao.findByCriteria(c, page);
	}

	/**
	 * Find unit list list.
	 *
	 * @param page         the page
	 * @param sysSite      the sys site
	 * @param isSuperAdmin the is super admin
	 * @return the list
	 */
	public List<SysUnit> findUnitList(Page page, SysSite sysSite, Boolean isSuperAdmin) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		if (!isSuperAdmin) {
			c.eq("sysSite.id", sysSite.getId());
		}
		// c.ne("delFlag", true);// 过滤未删除的数据是否删除 0(false)未删 1(true)已删除
		c.orderBy(Order.asc("seq"));
		return dao.findByCriteria(c, page);
	}

	/**
	 * List unit by parent list.
	 *
	 * @param parentId the parent id
	 * @return the list
	 */
	public List<SysUnit> listUnitByParent(Long parentId) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		// c.ne("delFlag", true);// 过滤未删除的数据是否删除 0(false)未删 1(true)已删除
		c.eq("parentUnit.id", parentId);
		c.orderBy(Order.asc("seq"));
		return dao.findByCriteria(c);
	}

	/**
	 * 功能描述：查询条件拼接
	 *
	 * @param unit the unit
	 * @param c    the c
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:38:50
	 */
	public void foramtCond(SysUnit unit, Criteria<SysUnit> c) {
		if (unit != null) {
			if (StringUtils.isNotBlank(unit.getName())) {
				c.like("name", unit.getName());
			}
			if (StringUtils.isNotBlank(unit.getUnitNo())) {
				c.like("unitNo", unit.getUnitNo());
			}
			if (unit.getParentUnit() != null && unit.getParentUnit().getId() != null) {
				c.eq("parentUnit.id", unit.getParentUnit().getId());
			}
			if (unit.getStatus() != null) {
				c.eq("status", unit.getStatus());
			}
			if (StringUtils.isNotBlank(unit.getRemark())) {
				c.like("remark", unit.getRemark());
			}
			if (unit.getUnitType() != null) {
				c.eq("unitType", unit.getUnitType());
			}
		}
	}

	/**
	 * 功能描述：查询组织架构树
	 *
	 * @param parentId the parent id
	 * @return list
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月8日下午5:13:08
	 */
	public List<UnitVo> findUnitTree(Long parentId) {
		List<UnitVo> vos = new ArrayList<UnitVo>();
		List<SysUnit> units = listUnitByParent(parentId);
		for (SysUnit unit : units) {
			UnitVo vo = new UnitVo();
			vo.setId(unit.getId());
			vo.setSysSite(unit.getSysSite());
			vo.setName(unit.getName());
			vo.setText(unit.getName());
			if (unit.getCompanyUnit() != null) {
				vo.setCompanyUnit(new SysUnit(unit.getCompanyUnit().getId()));
			}
			vo.setChildren(findUnitTree(unit.getId()));
			vos.add(vo);
		}
		return vos;
	}

	/**
	 * 功能描述：获取公司根节点
	 *
	 * @param companyId the company id
	 * @return list
	 * @author caiys
	 * @date 2015年11月18日 上午9:15:20
	 */
	public List<UnitVo> findCompanyUnit(Long companyId) {
		List<UnitVo> vos = new ArrayList<UnitVo>();
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		c.isNull("parentUnit");
		c.eq("companyUnit.id", companyId);
		List<SysUnit> units = dao.findByCriteria(c);
		for (SysUnit unit : units) {
			UnitVo vo = new UnitVo();
			vo.setId(unit.getId());
			vo.setSysSite(unit.getSysSite());
			if (unit.getCompanyUnit() != null) {
				vo.setCompanyUnit(new SysUnit(unit.getCompanyUnit().getId()));
			}
			vo.setName(unit.getName());
			vo.setState("open");
			vo.setText(unit.getName());
			vo.setChildren(findUnitTree(unit.getId()));
			vos.add(vo);
		}
		return vos;
	}

	/**
	 * 功能描述：获取组织根节点
	 *
	 * @param siteId the site id
	 * @return list
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月8日下午5:28:59
	 */
	public List<UnitVo> findRootUnit(Long siteId) {
		List<UnitVo> vos = new ArrayList<UnitVo>();
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		c.isNull("parentUnit");
		c.eq("sysSite.id", siteId);
		List<SysUnit> units = dao.findByCriteria(c);
		for (SysUnit unit : units) {
			UnitVo vo = new UnitVo();
			vo.setId(unit.getId());
			vo.setSysSite(unit.getSysSite());
			if (unit.getCompanyUnit() != null) {
				vo.setCompanyUnit(new SysUnit(unit.getCompanyUnit().getId()));
			}
			vo.setName(unit.getName());
			vo.setState("open");
			vo.setText(unit.getName());
			vo.setChildren(findUnitTree(unit.getId()));
			vos.add(vo);
		}
		return vos;
	}

	/**
	 * Find all unit list.
	 *
	 * @return the list
	 */
	public List<UnitVo> findAllUnit() {
		List<UnitVo> vos = new ArrayList<UnitVo>();
		List<SysUnit> units = listAllUnit();
		for (SysUnit unit : units) {
			UnitVo vo = new UnitVo();
			vo.setId(unit.getId());
			vo.setSysSite(unit.getSysSite());
			if (unit.getCompanyUnit() != null) {
				vo.setCompanyUnit(new SysUnit(unit.getCompanyUnit().getId()));
			}
			vo.setName(unit.getName());
			vo.setState("open");
			vo.setText(unit.getName());
			vo.setChildren(findUnitTree(unit.getId()));
			vos.add(vo);
		}
		return vos;
	}

	/**
	 * List all unit list.
	 *
	 * @return the list
	 */
	public List<SysUnit> listAllUnit() {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		c.isNull("parentUnit");
		// c.eq("sysSite.id", siteId);
		return dao.findByCriteria(c);
	}

	/**
	 * Insert.
	 *
	 * @param sysUnitDetail the sys unit detail
	 */
	public void insert(SysUnitDetail sysUnitDetail) {
		dao.save(sysUnitDetail);
	}

	/**
	 * Gets by site.
	 *
	 * @param sysSite the sys site
	 * @return the by site
	 */
	public List<SysUnit> getBySite(SysSite sysSite) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.eq("sysSite.id", sysSite.getId());
		return dao.findByCriteria(c);
	}

	/**
	 * Gets by parent ids.
	 *
	 * @param unitIds the unit ids
	 * @return the by parent ids
	 */
	public List<SysUnit> getByParentIds(List<Long> unitIds) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.in("parentUnit.id", unitIds);
		return dao.findByCriteria(c);
	}

	/**
	 * Gets by parent id.
	 *
	 * @param parentId the parent id
	 * @return the by parent id
	 */
	public List<SysUnit> getByParentId(long parentId) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.eq("parentUnit.id", parentId);
		return dao.findByCriteria(c);
	}

	/**
	 * 根据标识批量审核通过，多个逗号分隔
	 *
	 * @param ids the ids
	 * @author caiys
	 * @date 2015年10月26日 下午8:25:59
	 */
	public void doBatchAudit(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idsArray = ids.split(",");
			for (String id : idsArray) {
				SysUnit unit = dao.load(Long.valueOf(id));
				unit.setStatus(0); // 状态：-1待审核；0通过；1不通过；

				// 设置用户状态和默认角色
				Criteria<SysUser> c = new Criteria<SysUser>(SysUser.class);
				c.createCriteria("sysUnit", "u");
				c.eq("u.companyUnit.id", Long.valueOf(id));
				c.eq("userType", UserType.CompanyManage);
				List<SysUser> users = sysUserDao.findByCriteria(c);
				for (SysUser user : users) {
					user.setStatus(UserStatus.activity);
					user.setIsUse(true);
					user.setUpdateTime(new Date());

					// 是否第一次授权
					Criteria<SysUserRole> cr = new Criteria<SysUserRole>(SysUserRole.class);
					cr.eq("user.id", user.getId());
					List<SysUserRole> urs = sysUserRoleDao.findByCriteria(cr);
					if (urs == null || urs.size() <= 0) {
						SysUserRole userRole = new SysUserRole();
						userRole.setUser(user);
						SysRole role = new SysRole();
						role.setId(UserType.CompanyManage.getId());// 默认角色
						userRole.setRole(role);
						sysUserRoleDao.save(userRole);
					}
					sysUserDao.save(user);
				}
			}
		}
	}

	/**
	 * 根据标识批量审核不通过，多个逗号分隔
	 *
	 * @param ids    the ids
	 * @param reason the reason
	 * @author caiys
	 * @date 2015年10月26日 下午8:25:59
	 */
	public void doBatchFreeze(String ids, String reason) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idsArray = ids.split(",");
			for (String id : idsArray) {
				SysUnit unit = dao.load(Long.valueOf(id));
				unit.setStatus(1); // 状态：-1待审核；0通过；1不通过；
				unit.setReason(reason);

				// 设置用户冻结状态
				Criteria<SysUser> c = new Criteria<SysUser>(SysUser.class);
				c.createCriteria("sysUnit", "u");
				c.eq("u.companyUnit.id", Long.valueOf(id));
				List<SysUser> users = sysUserDao.findByCriteria(c);
				for (SysUser user : users) {
					user.setStatus(UserStatus.lock);
					user.setIsUse(false);
					user.setUpdateTime(new Date());
					sysUserDao.save(user);
				}
			}
		}
	}

	/**
	 * 根据标识批量删除，多个逗号分隔
	 *
	 * @param ids the ids
	 * @author caiys
	 * @date 2015年10月26日 下午8:25:59
	 */
	public void doBatchDel(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idsArray = ids.split(",");
			for (String id : idsArray) {
				SysUnit unit = dao.load(Long.valueOf(id));
				unit.setDelFlag(true);
				dao.update(unit);
				// 设置用户删除状态
				Criteria<SysUser> c = new Criteria<SysUser>(SysUser.class);
				c.createCriteria("sysUnit", "u");
				c.eq("u.companyUnit.id", Long.valueOf(id));
				List<SysUser> users = sysUserDao.findByCriteria(c);
				for (SysUser user : users) {
					user.setDelFlag(true);
					user.setUpdateTime(new Date());
					sysUserDao.save(user);
				}
			}
		}
	}

	/**
	 * Fin unit by sid sys unit.
	 *
	 * @param sid the sid
	 * @return the sys unit
	 */
	public SysUnit finUnitBySid(long sid) {
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.eq("sysSite.id", sid);
		c.createCriteria("sysUnitDetail", "sysUnitDetail", JoinType.LEFT_OUTER_JOIN);

		return dao.findUniqueByCriteria(c);
	}

	/**
	 * Find unit by id sys unit.
	 *
	 * @param id the id
	 * @return the sys unit
	 */
	public SysUnit findUnitById(long id) {

		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.eq("id", id);
		c.createCriteria("sysUnitDetail", "sysUnitDetail", JoinType.LEFT_OUTER_JOIN);

		return dao.findUniqueByCriteria(c);
	}

	/**
	 * Update.
	 *
	 * @param site       the site
	 * @param unit       the unit
	 * @param unitDetail the unit detail
	 */
	public void update(SysSite site, SysUnit unit, SysUnitDetail unitDetail) {

		unit.setSysSite(site);
		unitDetail.setSysUnit(unit);

		unit.setUnitType(UnitType.site);
		dao.update(unit);

	}

	/**
	 * Gets unit by site.
	 *
	 * @param site the site
	 * @return the unit by site
	 */
	public List<SysUnit> getUnitBySite(SysSite site) {
		
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		c.eq("sysSite.id", site.getId());
//		c.ne("unitType", UnitType.company);
		c.not("unitType", UnitType.department);
		
		return dao.findByCriteria(c);
	}

}
