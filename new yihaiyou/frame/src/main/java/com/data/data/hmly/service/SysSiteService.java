package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.SysRoleDao;
import com.data.data.hmly.service.dao.SysSiteDao;
import com.data.data.hmly.service.dao.SysUnitDao;
import com.data.data.hmly.service.dao.SysUnitDetailDao;
import com.data.data.hmly.service.dao.SysUserDao;
import com.data.data.hmly.service.dao.SysUserRoleDao;
import com.data.data.hmly.service.entity.SiteStatus;
import com.data.data.hmly.service.entity.SysRole;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitDetail;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.SysUserRole;
import com.data.data.hmly.service.entity.UnitType;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.util.Encryption;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.PinyinUtil;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysSiteService {

	private static final long DEFAULT_SYS_SITE_ID = 1;

	@Resource
	private SysSiteDao dao;
	@Resource
	private SysUnitDao sysUnitDao;
	@Resource
	private SysSiteDao sysSiteDao;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SysUserRoleDao sysUserRoleDao;
	@Resource
	private SysUnitDetailDao sysuDetailDao;

	/**
	 * 功能描述：站点保存
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:27:18
	 * @param site
	 */
	public void saveOrUpdateSite(SysSite site) {
		if (site != null) {
			// if (site.getStatus() == null) {
			// site.setStatus(0);
			// }
			// if (site.getDelFlag() == null) {
			// site.setDelFlag(false);
			// }
			// if (site.getSeq() == null) {
			// site.setSeq(0);
			// }
			if (site.getId() == null) {
				dao.save(site);
			} else {
				dao.updateTransisent(site);
			}
		}
	}

	/**
	 * 功能描述：站点删除
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:53:19
	 * @param site
	 */
	public void deleteSite(SysSite site) {
		if (site != null) {
			if (site.getId() != null) {
				String hql = "update SysSite set delFlag = ? where id= ? ";
				dao.updateByHQL(hql, false, site.getId());
			}
		}
	}

	/**
	 * 功能描述：根据ID删除角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:53:57
	 * @param roleId
	 */
	public void deleteUserById(Long roleId) {
		if (roleId != null) {
			String hql = "update SysSite set delFlag = ? where id= ?";
			dao.updateByHQL(hql, false, roleId);
		}
	}

	/**
	 * 功能描述：根据ID获取角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:42:56
	 * @param roleId
	 * @return
	 */
	public SysSite findSysSiteById(Long roleId) {
		if (roleId != null) {
			return dao.get(SysSite.class, roleId);
		}
		return null;
	}

	/**
	 * 功能描述：查询角色信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:39:17
	 * @param site
	 * @param page
	 * @return
	 */
	public List<SysSite> findSiteList(SysSite site, Page page) {
		Criteria<SysSite> c = new Criteria<SysSite>(SysSite.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		// c.ne("delFlag", true);// 过滤未删除的数据是否删除 0(false)未删 1(true)已删除
		foramtCond(site, c);
		c.orderBy(Order.asc("seq"));
		return dao.findByCriteria(c, page);
	}

	/**
	 * 查询所有站点
	 * @author caiys
	 * @date 2015年11月7日 下午8:35:03
	 * @return
	 */
	public List<SysSite> findAllSite() {
		Criteria<SysSite> c = new Criteria<SysSite>(SysSite.class);
		c.eq("status", SiteStatus.OK);
		c.orderBy(Order.asc("id"));
		return dao.findByCriteria(c);
	}

	/**
	 * 功能描述：查询条件拼接
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:38:50
	 * @param site
	 * @param c
	 */
	public void foramtCond(SysSite site, Criteria<SysSite> c) {
		if (site != null) {
			// if (StringUtils.isNotBlank(site.getName())) {
			// c.like("name", site.getName());
			// }
			// if (site.getStatus() != null) {
			// c.eq("status", site.getStatus());
			// }
			// if (StringUtils.isNotBlank(site.getRemark())) {
			// c.like("remark", site.getRemark());
			// }
		}
	}

	public List<SysSite> findAllSite(Page page, UnitType site) {
		
		
//		Criteria<SysSite> c = new Criteria<SysSite>(SysSite.class);
//		c.createCriteria("area", "a", JoinType.LEFT_OUTER_JOIN);
//		c.orderBy(Order.asc("id"));
//		
//		List<SysSite> sysSites = dao.findByCriteria(c, page);
		
		List<SysSite> sysSitesList = new ArrayList<SysSite>();
		
		List<SysUnit> sysUnits = sysUnitDao.findAllSite(page, site);
//		for(SysSite ss:sysSites){
			for (SysUnit su : sysUnits) {
//				if(su.getSysSite().getId() == ss.getId()){
					sysSitesList.add(su.getSysSite());
//				}
//			}
		}
		
		return sysSitesList;
	}
	
	
	public List<SysSite> findAllSite(Page page) {
		
		Criteria<SysSite> c = new Criteria<SysSite>(SysSite.class);
		c.createCriteria("area", "a", JoinType.LEFT_OUTER_JOIN);
		c.orderBy(Order.asc("id"));
		return dao.findByCriteria(c, page);
	}
	public void addSceUnit(SysUnit sysUnit) {
//		sysUnit.getSysSite().setArea(sysUnit.getArea());
		sysUnit.setUnitType(UnitType.company);
		sysUnit.setDelFlag(false);
		sysUnit.setStatus(0);
		sysUnit.setUnitNo(PinyinUtil.hanziToPinyin(sysUnit.getSysSite().getSitename()));
		sysUnit.setCompanyUnit(sysUnit);
		sysUnit.setParentUnit(sysUnit);
		sysUnitDao.save(sysUnit);
		SysUser user = new SysUser();
		user.setAccount(sysUnit.getName());
		user.setAddress(sysUnit.getAddress());
		user.setCreatedTime(new Date());
		user.setDelFlag(false);
		user.setIsUse(false);
		user.setLoginNum(0);
		user.setPassword(Encryption.encry("123456"));
		user.setSysSite(sysUnit.getSysSite());
		user.setSysUnit(sysUnit);
		user.setStatus(UserStatus.lock);
		user.setUpdateTime(new Date());
		user.setUserType(UserType.ScenicManage);
		user.setUserName(sysUnit.getName());
		sysUserDao.save(user);
		
		SysUserRole sysUserRole = new SysUserRole();
		SysRole sysRole = sysRoleDao.load(UserType.ScenicManage.getId());
		sysUserRole.setUser(user);
		sysUserRole.setRole(sysRole);
		sysUserRoleDao.save(sysUserRole);
		
	}

	public void addNewSite(SysUnit sysUnit) {
//		sysUnit.getSysSite().setArea(sysUnit.getArea());
		sysUnit.setUnitType(UnitType.site);
		sysUnit.setDelFlag(false);
		sysUnit.setStatus(0);
		sysUnit.setUnitNo(PinyinUtil.hanziToPinyin(sysUnit.getSysSite().getSitename()));
		sysSiteDao.save(sysUnit.getSysSite());
		sysUnitDao.save(sysUnit);
		SysUnitDetail sysUnitDetail = sysUnit.getSysUnitDetail();
		sysUnitDetail.setSysUnit(sysUnit);
		sysUnitDao.save(sysUnitDetail);
		SysUser user = new SysUser();
		user.setAccount(sysUnit.getName());
		user.setAddress(sysUnit.getAddress());
		user.setCreatedTime(new Date());
		user.setDelFlag(false);
		user.setIsUse(true);
		user.setLoginNum(0);
		user.setPassword(Encryption.encry("123456"));
		user.setSysSite(sysUnit.getSysSite());
		user.setSysUnit(sysUnit);
		user.setUpdateTime(new Date());
		user.setUserType(UserType.SiteManage);
		user.setUserName(sysUnit.getName());
		sysUserDao.save(user);
	}

	public List<SysSite> findByUrl(String url) {
		Criteria<SysSite> criteria = new Criteria<SysSite>(SysSite.class);
		criteria.eq("status", SiteStatus.OK);
		criteria.eq("siteurl", url);
		return dao.findByCriteria(criteria);
	}

    /**
     * 只返回第一个符合条件的结果
     * @param url
     * @return
     */
    public SysSite findUniqueByUrl(String url) {
        Criteria<SysSite> criteria = new Criteria<SysSite>(SysSite.class);
        criteria.eq("status", SiteStatus.OK);
        criteria.eq("siteurl", url);
        Page page = new Page(1, 1);
        List<SysSite> list =  dao.findByCriteria(criteria, page);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

	public SysSite getDefaultSite() {
		return dao.load(DEFAULT_SYS_SITE_ID);
	}

	public void insert(SysSite site) {
		// TODO Auto-generated method stub

	}

	public void addSecAcc(SysUnit sysUnit, SysUnitDetail sysUnitDetail) {

		
		sysUnitDetail.setSysUnit(sysUnit);
		sysUnit.setSysUnitDetail(sysUnitDetail);
		addSceUnit(sysUnit);
		sysuDetailDao.save(sysUnitDetail);

	}

	public SysSite finSiteById(long sid) {
		
		Criteria<SysSite> criteria = new Criteria<SysSite>(SysSite.class);
		
		criteria.eq("id", sid);
		
		return sysSiteDao.findUniqueByCriteria(criteria);
	}
}
