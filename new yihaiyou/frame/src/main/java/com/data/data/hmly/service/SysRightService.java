package com.data.data.hmly.service;

import com.data.data.hmly.service.dao.SysMenuDao;
import com.data.data.hmly.service.dao.SysResourceDao;
import com.data.data.hmly.service.dao.SysRoleMenuDao;
import com.data.data.hmly.service.dao.SysRoleResourceDao;
import com.data.data.hmly.service.entity.SysMenu;
import com.data.data.hmly.service.entity.SysResource;
import com.data.data.hmly.service.entity.SysRole;
import com.data.data.hmly.service.entity.SysRoleMenu;
import com.data.data.hmly.service.entity.SysRoleResource;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.SysUserRole;
import com.data.data.hmly.service.vo.TreeGridRightVo;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.ConvertUtils;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRightService {
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysMenuService sysMenuService;
	@Resource
	private SysResourceService sysResourceService;
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;
	@Resource
	private SysRoleResourceDao sysRoleResourceDao;
	@Resource
	private SysMenuDao sysMenuDao;
	@Resource
	private SysResourceDao sysResourceDao;

	/**
	 * 功能描述：查询所有系统权限
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日上午11:38:00
	 * @return
	 */
	public List<TreeGridRightVo> findRight(Map<String, Object> roleRights, List<SysMenu> userRights) {
		// 查询一级菜单
		List<TreeGridRightVo> rights = new ArrayList<TreeGridRightVo>();
		if (userRights != null && userRights.size() > 0) {
			for (SysMenu menu : userRights) {
				TreeGridRightVo right = menuToRight(menu);
				// 判断模块 权限
				setMenuRight(roleRights, menu, right);
				// 递归查找子模块
				List<TreeGridRightVo> childs = findRight(roleRights, menu.getChildren());
				right.setChildren(childs);
				// 查找子资源
				List<SysResource> resources = sysResourceService.findChildResources(menu.getMenuid());
				if (resources != null && resources.size() > 0) {
					for (SysResource resource : resources) {
						// resource.setSysMenu(null);
						// 判断资源权限
						setResourceRight(roleRights, resource);
					}
				}
				right.setResources(resources);
				rights.add(right);
			}
		}
		return rights;
	}

	/**
	 * 功能描述：将菜单转化为vo
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午5:27:59
	 * @param menu
	 * @return
	 */
	public TreeGridRightVo menuToRight(SysMenu menu) {
		TreeGridRightVo right = new TreeGridRightVo();
		right.setParentId(String.valueOf(menu.getParentId()));
		right.setId(menu.getMenuid());
		right.setText(menu.getMenuname());
		return right;
	}

	/**
	 * 功能描述：判断资源权限
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午2:49:32
	 * @param map
	 * @param resource
	 */
	public void setResourceRight(Map<String, Object> map, SysResource resource) {
		if (resource.getIsPublic() != null && resource.getIsPublic().equals(1)) {
			// 公共资源
			resource.setHasRight(2);
		} else {
			// 私有资源 判断是否拥有
			String key = "r_" + resource.getId();
			if (map.get(key) != null) {
				resource.setHasRight(1);
			}
		}
	}

	/**
	 * 功能描述：判断模块权限
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午2:21:14
	 * @param map
	 * @param menu
	 */
	public void setMenuRight(Map<String, Object> map, SysMenu menu, TreeGridRightVo right) {
		if (menu.getIsPublic() != null && menu.getIsPublic().equals(1)) {
			// 公共模块
			right.setHasRight(2);
		} else {
			// 私有模块 判断角色是否拥有权限
			String key = "m_" + menu.getMenuid();
			if (map.get(key) != null) {
				right.setHasRight(1);
			}
		}
	}

	/**
	 * 功能描述：获取用户权限,封装成键值对
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午3:25:48
	 * @param user
	 * @return
	 */
	public Map<String, Object> findMenuRight(SysUser user) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysRole> roles = new ArrayList<SysRole>();
		for (SysUserRole sysUserRole : user.getSysRoles()) {
			roles.add(sysUserRole.getRole());
		}
		// 将角色ID,拼成字符串
		String roleStr = getRoleIds(roles);
		if (StringUtils.isNotBlank(roleStr)) {
			List<SysRoleMenu> menus = findRoleMenuByRoleIds(roleStr);
			for (SysRoleMenu rm : menus) {
				String key = "m_" + rm.getMenu().getMenuid();
				if (map.get(key) == null) {
					map.put(key, rm.getMenu());
				}
			}
			List<SysRoleResource> resources = findRoleResourceByRoleIds(roleStr);
			for (SysRoleResource rr : resources) {
				String key = "r_" + rr.getResource().getId();
				if (map.get(key) == null) {
					map.put(key, rr.getResource());
				}
			}
		}
		return map;
	}

	/**
	 * 功能描述：获取角色权限,封装成键值对
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午3:25:48
	 * @return
	 */
	public Map<String, Object> findMenuRight(Long roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (roleId != null) {
			List<SysRoleMenu> menus = findRoleMenuByRoleId(roleId);
			for (SysRoleMenu rm : menus) {
				String key = "m_" + rm.getMenu().getMenuid();
				if (map.get(key) == null) {
					map.put(key, rm.getMenu());
				}
			}
			List<SysRoleResource> resources = findRoleResourceByRoleId(roleId);
			for (SysRoleResource rr : resources) {
				String key = "r_" + rr.getResource().getId();
				if (map.get(key) == null) {
					map.put(key, rr.getResource());
				}
			}
		}
		return map;
	}

	public Map<String, Object> findAllMenuRight() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysMenu> menus = findAllMenu(0L, null);
		for (SysMenu menu : menus) {
			String key = "m_" + menu.getMenuid();
			if (map.get(key) == null) {
				map.put(key, menu);
			}
		}
		List<SysResource> resources = findAllResource();
		for (SysResource sr : resources) {
			String key = "r_" + sr.getId();
			if (map.get(key) == null) {
				map.put(key, sr);
			}
		}
		return map;
	}

	/**
	 * 功能描述：查询角色模块(菜单)权限
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午3:11:50
	 * @param roleIds
	 * @return
	 */
	public List<SysRoleMenu> findRoleMenuByRoleIds(String roleIds) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysRoleMenu s where s.role.delFlag='0' and s.menu.delFlag ='0' and s.menu.status='0' ");
		sb.append(" and s.role.id in (" + roleIds + ")");
		List<SysRoleMenu> roleMenus = sysRoleMenuDao.findByHQL(sb.toString());
		return roleMenus;
	}

	/**
	 * 功能描述：查询角色模块(菜单)树
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午3:11:50
	 * @param roleIds
	 * @return
	 */
	public List<SysMenu> findRoleMenuTreeByRoleIds(String roleIds, Long parentId) {
		return findRoleMenuTreeByRoleIds(roleIds, parentId, null);
	}

	/**
	 * 功能描述：查询角色模块(菜单)树
	 * @param roleIds
	 * @param parentId
	 * @param subSystemFlag 子系统菜单标识
	 * @return
	 */
	public List<SysMenu> findRoleMenuTreeByRoleIds(String roleIds, Long parentId, Boolean subSystemFlag) {
		StringBuffer sb = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("select s.menu from SysRoleMenu s where s.role.delFlag='0' and s.menu.delFlag ='0' and s.menu.status='0' ");
		sb.append(" and s.role.id in (" + roleIds + ")");
		sb.append(" and s.menu.parentId='").append(parentId).append("'");
		if (subSystemFlag != null) {
			sb.append(" and s.menu.subSystemFlag = :subSystemFlag");
			params.put("subSystemFlag", subSystemFlag);
		}
		sb.append(" order by s.menu.seq asc");
		List<SysMenu> menus = sysRoleMenuDao.findByHQL2(sb.toString(), params);
		for (SysMenu sysMenu : menus) {
			sysMenu.setChildren(findRoleMenuTreeByRoleIds(roleIds, sysMenu.getMenuid(), subSystemFlag));
		}
		return menus;
	}

	public List<SysMenu> findAllMenu(Long parentId, Boolean subSystemFlag) {
		Criteria<SysMenu> criteria = new Criteria<SysMenu>(SysMenu.class);
		criteria.eq("delFlag", false);
		criteria.eq("status", 0);
		criteria.eq("parentId", parentId);
        if (subSystemFlag != null) {
            criteria.eq("subSystemFlag", subSystemFlag);
        }
		criteria.addOrder(Order.asc("seq"));
		List<SysMenu> menus = sysMenuDao.findByCriteria(criteria);
		for (SysMenu sysMenu : menus) {
			sysMenu.setChildren(findAllMenu(sysMenu.getMenuid(), subSystemFlag));
		}
		return menus;
	}

	/**
	 * 功能描述：查询角色资源权限
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午3:24:20
	 * @param roleIds
	 * @return
	 */
	public List<SysRoleResource> findRoleResourceByRoleIds(String roleIds) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysRoleResource where role.delFlag='0' and resource.delFlag ='0' and resource.status='0' ");
		sb.append(" and role.id in (" + roleIds + ")");
		List<SysRoleResource> roleMenus = sysRoleResourceDao.findByHQL(sb.toString());
		return roleMenus;
	}

	public List<SysResource> findAllResource() {
		Criteria<SysResource> criteria = new Criteria<SysResource>(SysResource.class);
		criteria.eq("delFlag", false);
		criteria.eq("status", 0);
		List<SysResource> resources = sysResourceDao.findByCriteria(criteria);
		return resources;
	}

	/**
	 * 功能描述：查询角色模块(菜单)权限
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午3:11:50
	 * @return
	 */
	public List<SysRoleMenu> findRoleMenuByRoleId(Long roleId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysRoleMenu s where s.role.delFlag='0' and s.menu.delFlag ='0' ");
		sb.append(" and s.role.id = ?");
		List<SysRoleMenu> roleMenus = sysRoleMenuDao.findByHQL(sb.toString(), roleId);
		return roleMenus;
	}

	/**
	 * 功能描述：查询角色资源权限
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午3:24:20
	 * @return
	 */
	public List<SysRoleResource> findRoleResourceByRoleId(Long roleId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysRoleResource s where s.role.delFlag='0' and s.resource.delFlag ='0' and s.role.status='0' ");
		sb.append(" and s.role.id = ?");
		List<SysRoleResource> roleMenus = sysRoleResourceDao.findByHQL(sb.toString(), roleId);
		return roleMenus;
	}

	/**
	 * 功能描述：将角色列表,拼接成角色ID字符串
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午3:06:45
	 * @param roles
	 * @return
	 */
	public String getRoleIds(List<SysRole> roles) {
		String roleStr = "";
		if (roles != null && roles.size() > 0) {
			for (int i = 0; i < roles.size(); i++) {
				SysRole sysRole = roles.get(i);
				if (i != 0) {
					roleStr += ",";
				}
				roleStr += sysRole.getId();
			}
		}
		return roleStr;
	}

	/**
	 * 功能描述：保存角色权限
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月8日上午9:52:53
	 * @param roleId
	 * @param mRights
	 * @param rRights
	 */
	public void updateRight(Long roleId, String mRights, String rRights) {
		// 删除原来的权限
		deleteRightByRole(roleId);
		// 保存新权限
		if (StringUtils.isNotBlank(mRights)) {
			new ArrayList<SysRoleMenu>();
			String[] rights = mRights.split(",");
			for (String m : rights) {
				if (StringUtils.isNotBlank(m)) {
					Long mid = ConvertUtils.objectToLong(m);
					SysRoleMenu srm = new SysRoleMenu(roleId, mid);
					// srmList.add(srm);
					sysRoleMenuDao.save(srm);
				}
			}
			// sysRoleMenuDao.save(srmList);
		}
		if (StringUtils.isNotBlank(rRights)) {
			new ArrayList<SysRoleResource>();
			String[] rights = rRights.split(",");
			for (String m : rights) {
				if (StringUtils.isNotBlank(m)) {
					Long mid = ConvertUtils.objectToLong(m);
					SysRoleResource srr = new SysRoleResource(roleId, mid);
					sysRoleResourceDao.save(srr);
					// srrList.add(srm);
				}
			}
			// sysRoleResourceDao.save(srrList);
		}
	}

	/**
	 * 功能描述：删除角色所拥有的权限
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月8日上午9:55:43
	 * @param roleId
	 */
	public void deleteRightByRole(Long roleId) {
		String delMenu = " delete SysRoleMenu where role.id =?";
		String delResource = " delete SysRoleResource where role.id =?";
		sysRoleMenuDao.updateByHQL(delMenu, roleId);
		sysRoleResourceDao.updateByHQL(delResource, roleId);
	}
}
