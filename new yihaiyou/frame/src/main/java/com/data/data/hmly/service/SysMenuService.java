package com.data.data.hmly.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.data.data.hmly.service.dao.SysMenuDao;
import com.data.data.hmly.service.entity.SysMenu;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.vo.TreeVo;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;

@Service
public class SysMenuService {
	@Resource
	private SysMenuDao dao;

	/**
	 * 功能描述：菜单查询
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2014年10月20日下午5:06:59
	 * @param m
	 * @return
	 */
	public List<SysMenu> findMenus(SysMenu m, SysUser user) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysMenu m where 1=1 ");
		if (user == null) {
			return null;
		} else {
			// sb.append(" and exists (select 1 from SysRoleMenu r where r.role.id="
			// + user.getRole().getId() + " and r.menu.id=m.menuid )  ");
		}
		if (m != null) {
			if (m.getParentId() != null) {
				sb.append(" and m.parentId='").append(m.getParentId()).append("'");
			}
		}
		sb.append(" order by seq asc");
		return dao.findByHQL(sb.toString());
	}

	public List<SysMenu> findMenus(String parentid, SysUser user) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysMenu m where 1=1 ");
		if (user == null) {
			return null;
		} else {
			// sb.append(" and exists (select 1 from SysRoleMenu r where r.role.id="
			// + user.getRole().getId() + " and r.menu.id=m.menuid )  ");
		}
		if (parentid != null) {
			sb.append(" and m.parentId='").append(parentid).append("'");
		}
		sb.append(" order by seq asc");
		return dao.findByHQL(sb.toString());
	}

	/**
	 * 功能描述：菜单查询
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2014年10月20日下午5:06:59
	 * @param m
	 * @return
	 */
	public List<SysMenu> findMenus(SysMenu m) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from SysMenu m where 1=1 ");
		if (m != null) {
			if (m.getParentId() != null) {
				sb.append(" and m.parentId='").append(m.getParentId()).append("'");
			}
		}
		sb.append(" order by seq asc");
		return dao.findByHQL(sb.toString());
	}

	/**
	 * 功能描述：查询菜单树
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2014年10月20日下午5:06:59
	 * @param m
	 * @return
	 */
	public List<SysMenu> findMenuTree(String parentId, SysUser user) {
		List<SysMenu> list = findMenus(parentId, user);
		for (SysMenu m : list) {
			List<SysMenu> childs = findMenuTree(m.getMenuid() + "", user);
			m.setChildren(childs);
		}
		return list;
	}

	/************************ 菜单管理 ******************************************/

	/**
	 * 功能描述：加载菜单树
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月3日上午10:00:42
	 * @param parentId
	 * @param user
	 * @return
	 */
	public List<TreeVo> loadMenuTree(long parentId) {
		List<TreeVo> list = listTreeMenus(parentId);
		for (TreeVo t : list) {
			List<TreeVo> childs = listTreeMenus(t.getId());
			t.setChildren(childs);
		}
		return list;
	}

	/**
	 * 功能描述：查询菜单列表
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月3日上午9:27:48
	 * @param menu
	 * @param page
	 * @return
	 */
	public List<SysMenu> listMenus(SysMenu menu, Page page) {
		Criteria<SysMenu> c = new Criteria<SysMenu>(SysMenu.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		if (menu != null) {
			if (StringUtils.isNotBlank(menu.getMenuname())) {
				c.like("menuname", menu.getMenuname());
			}
			if (menu.getMenulevel() != null) {
				c.eq("menulevel", menu.getMenulevel());
			}
			if (StringUtils.isNotBlank(menu.getUrl())) {
				c.like("url", menu.getUrl());
			}
			if (menu.getStatus() != null) {
				c.eq("status", menu.getStatus());
			}
			if (menu.getParentId() != null) {
				c.eq("parentId", menu.getParentId());
			}
		}
		c.orderBy(Order.asc("parentId"));
		c.orderBy(Order.asc("seq"));
		if (page == null) {
			return dao.findByCriteria(c);
		} else {
			return dao.findByCriteria(c, page);
		}
	}

	/**
	 * 功能描述：查询子菜单
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日下午2:32:21
	 * @param parentId
	 * @return
	 */
	public List<SysMenu> listChildMenus(long parentId) {
		Criteria<SysMenu> c = new Criteria<SysMenu>(SysMenu.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		c.eq("status", 0);
		c.eq("parentId", parentId);
		c.orderBy(Order.asc("parentId"));
		c.orderBy(Order.asc("seq"));
		return dao.findByCriteria(c);
	}

	/**
	 * 功能描述：根据父ID 查询子模块
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月3日上午10:05:01
	 * @param parentId
	 * @return
	 */
	public List<TreeVo> listTreeMenus(Long parentId) {
		Criteria<SysMenu> c = new Criteria<SysMenu>(SysMenu.class);
		c.or(Restrictions.isNull("delFlag"), Restrictions.eq("delFlag", false));
		c.eq("parentId", parentId);
		c.orderBy(Order.asc("seq"));
		List<SysMenu> menus = dao.findByCriteria(c);
		List<TreeVo> treeVos = new ArrayList<TreeVo>();
		if (menus != null) {
			for (SysMenu m : menus) {
				TreeVo tree = new TreeVo();
				tree.setId(m.getMenuid());
				tree.setState("closed");
				tree.setText(m.getMenuname());
				if (StringUtils.isNotBlank(m.getUrl())) {
					tree.setIconCls("icon-redo");
				}
				tree.setChildren(listTreeMenus(m.getMenuid()));
				treeVos.add(tree);
			}
		}
		return treeVos;
	}

	/**
	 * 功能描述：新增或保存菜单
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月3日上午9:33:58
	 * @param menu
	 * @return
	 */
	public SysMenu saveOrUpdateMenu(SysMenu menu) {
		if (menu != null) {
			if (menu.getDelFlag() == null) {
				menu.setDelFlag(false);
			}
			if (menu.getStatus() == null) {
				menu.setStatus(0);
			}
			if (menu.getParentId() == null) {
				menu.setParentId(0L);
			}
			if (menu.getMenuid() != null) {
				dao.update(menu);
			} else {
				dao.save(menu);
			}
		}
		return menu;
	}

	/**
	 * 功能描述：新增或保存菜单
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月3日上午9:33:58
	 * @param menu
	 * @return
	 */
	public SysMenu getMenuById(Long menuid) {
		return dao.get(SysMenu.class, menuid);
	}

	public void findAllMenu() {
		dao.findAll();
	}

}
