package com.data.data.hmly.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.data.data.hmly.service.dao.SysResourceDao;
import com.data.data.hmly.service.entity.SysResource;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;

@Service
public class SysResourceService {
	@Resource
	private SysResourceDao	dao;
	
	/**
	 * 功能描述：用户保存
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:27:18
	 * @param user
	 */
	public void saveOrUpdateResource(SysResource resource) {
		if (resource != null) {
			if (resource.getStatus() == null) {
				resource.setStatus(0);
			}
			if (resource.getDelFlag() == null) {
				resource.setDelFlag(false);
			}
			if (resource.getIsPublic() == null) {
				resource.setIsPublic(0);
			}
			if (resource.getSeq() == null) {
				resource.setSeq(0);
			}
			if (resource.getId() == null) {
				dao.save(resource);
			} else {
				dao.update(resource);
			}
		}
	}
	
	/**
	 * 功能描述：用户删除
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:53:19
	 * @param user
	 */
	public void deleteResource(SysResource resource) {
		if (resource != null) {
			if (resource.getId() != null) {
				String hql = "update SysResource set delflag = ? where id= ? ";
				dao.updateByHQL(hql, false, resource.getId());
			}
		}
	}
	
	/**
	 * 功能描述：根据ID删除资源
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:53:57
	 * @param userId
	 */
	public void deleteUserById(Long roleId) {
		if (roleId != null) {
			String hql = "update SysResource set delflag = ? where id= ?";
			dao.updateByHQL(hql, false, roleId);
		}
	}
	
	/**
	 * 功能描述：根据ID获取资源
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:42:56
	 * @param userId
	 * @return
	 */
	public SysResource findSysResourceById(Long roleId) {
		if (roleId != null) {
			return dao.get(SysResource.class, roleId);
		}
		return null;
	}
	
	/**
	 * 功能描述：根据资源编号获取非自身的资源
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月14日下午5:29:15
	 * @param rId
	 * @param roleId
	 * @return
	 */
	public SysResource findSysResourceByNo(String rNo, Long rId) {
		String hql = "  from SysResource where delFlag='0' and resourceNo='" + rNo + "'";
		if (rId != null) {
			hql += " and id !='" + rId + "'";
		}
		List<SysResource> list = dao.findByHQL(hql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 功能描述：根据资源编号获取资源
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月14日下午5:29:15
	 * @param rId
	 * @param roleId
	 * @return
	 */
	public SysResource findSysResourceByNo(String rNo) {
		String hql = "  from SysResource where delFlag='0' and resourceNo='" + rNo + "'";
		List<SysResource> list = dao.findByHQL(hql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 功能描述：查询资源信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:39:17
	 * @param user
	 * @param page
	 * @return
	 */
	public List<SysResource> findResourceList(SysResource resource, Page page) {
		Criteria<SysResource> c = new Criteria<SysResource>(SysResource.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		// c.ne("delflag", true);// 过滤未删除的数据是否删除 0(false)未删 1(true)已删除
		foramtCond(resource, c);
		c.orderBy(Order.asc("seq"));
		if (page != null) {
			return dao.findByCriteria(c, page);
		} else {
			return dao.findByCriteria(c);
		}
	}
	
	/**
	 * 功能描述：根据模块ID模找子资源
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午2:35:59
	 * @param menuid
	 * @return
	 */
	public List<SysResource> findChildResources(Long menuid) {
		Criteria<SysResource> c = new Criteria<SysResource>(SysResource.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		c.eq("sysMenu.menuid", menuid);
		c.eq("status", 0);
		c.orderBy(Order.asc("seq"));
		return dao.findByCriteria(c);
	}
	
	/**
	 * 功能描述：查询条件拼接
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:38:50
	 * @param user
	 * @param c
	 */
	public void foramtCond(SysResource resource, Criteria<SysResource> c) {
		if (resource != null) {
			if (StringUtils.isNotBlank(resource.getName())) {
				c.like("name", resource.getName());
			}
			if (resource.getSysMenu() != null) {
				if (resource.getSysMenu().getMenuid() != null) {
					c.eq("sysMenu.menuid", resource.getSysMenu().getMenuid());
				}
				if (StringUtils.isNotBlank(resource.getSysMenu().getMenuname())) {
					c.like("sysMenu.menuname", resource.getSysMenu().getMenuname());
				}
			}
			if (resource.getStatus() != null) {
				c.eq("status", resource.getStatus());
			}
			if (StringUtils.isNotBlank(resource.getRemark())) {
				c.like("remark", resource.getRemark());
			}
			if (StringUtils.isNotBlank(resource.getResourceNo())) {
				c.like("resourceNo", resource.getResourceNo());
			}
			if (StringUtils.isNotBlank(resource.getResourceUrl())) {
				c.like("resourceUrl", resource.getResourceUrl());
			}
			if (resource.getIsPublic() != null) {
				c.eq("isPublic", resource.getIsPublic());
			}
		}
	}
}
