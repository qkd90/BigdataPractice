package com.data.data.hmly.action.main;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysMenuService;
import com.data.data.hmly.service.SysRoleService;
import com.data.data.hmly.service.SysSiteService;
import com.data.data.hmly.service.entity.SysMenu;
import com.data.data.hmly.service.entity.SysRole;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.SysUserRole;
import com.data.data.hmly.service.entity.UserType;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class
IndexAction extends FrameBaseAction implements ModelDriven<SysMenu> {
	@Resource
	private SysMenuService menuService;
	@Resource
	private SysSiteService sysSiteService;
	@Resource
	private PropertiesManager propertiesManager;
    @Resource
    private SysRoleService sysRoleService;

	private SysMenu menu = new SysMenu();
	private String json;
	private Integer page = 1;
	private Integer rows = 10;

	private String logo = null;

	private List<SysMenu> firstMenus;
	private List<SysMenu> menuList;
	private String fgDomain;

	@Override
	public SysMenu getModel() {
		return menu;
	}

	/**
	 * 功能描述：
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月9日下午6:10:46
	 * @return
	 */
	public Result index() {
		Map<String, Object> result = new HashMap<String, Object>();
		SysUser user = getLoginUser();
		SysSite sysSite = sysSiteService.finSiteById(user.getSysSite().getId());

		if (user == null) {
			return dispatchlogin();
		}
		// 查询第一级菜单
		menu.setMenulevel(1);
		menu.setParentId(0L);
		firstMenus = (List<SysMenu>) getSession().getAttribute("menuTree");
		menuList = firstMenus != null && firstMenus.size() > 0 ? firstMenus.get(0).getChildren() : Collections.EMPTY_LIST;
		// 未授权页面时，转到403
        if (menuList == null || menuList.isEmpty()) {
			return dispatch("/WEB-INF/jsp/common/403.jsp");
		}
		result.put("firstMenus", firstMenus);
		result.put("menuList", menuList);
//		result.put("logo", sysSite.getLogoImg());
        // 首页显示的logo
		if (StringUtils.isNotBlank(sysSite.getLogoImg())) {
			logoImg = sysSite.getLogoImg();
		}
        fgDomain = propertiesManager.getString("FG_DOMAIN", "javascript:void(0);");
        // 首页显示的文字：拥有单个角色时按角色的“页面显示名称”显示，多个显示默认文字“后台管理系统”，站点和全局管理员显示默认
        if (user.getUserType() != UserType.AllSiteManage && user.getUserType() != UserType.SiteManage
                && user.getSysRoles() != null) {
            Set<String> displayNameSet = new HashSet<String>();
            for (SysUserRole userRole : user.getSysRoles()) {
                SysRole role = sysRoleService.findSysRoleById(userRole.getRole().getId());
                if (StringUtils.isNotBlank(role.getDisplayName())) {
                    displayNameSet.add(role.getDisplayName());
                }
            }
            if (displayNameSet.size() == 1) {
                systemTitle = displayNameSet.iterator().next();
            }
        }

		json = readJson(result);
		return dispatch();
	}

	/**
	 * 功能描述：切换顶层菜单
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月10日上午10:40:46
	 * @return
	 */
	public Result changeMenu() {
		Map<String, Object> result = new HashMap<String, Object>();
		SysUser user = getLoginUser();
		if (user == null) {
			result.put("success", true);
		} else {
			Map<Long, List<SysMenu>> menuMap = (Map<Long, List<SysMenu>>) getSession().getAttribute("menuMap");
			menuList = menuMap.get(Long.valueOf(menu.getParentId()));
			for (SysMenu menu : menuList) {
				if (menu.getChildren() == null || menu.getChildren().size() == 0) {
					menu.setChildren(new ArrayList<SysMenu>());
				}
			}
			result.put("menuList", menuList);
			result.put("success", true);
		}
		json = readJson(result);
		return json(JSONObject.fromObject(json));
	}

    /**
     * 商家首页
     * @return
     */
	public Result merchant() {
		fgDomain = propertiesManager.getString("FG_DOMAIN", "javascript:void(0);");
		Map<String, Object> result = new HashMap<String, Object>();
		SysUser user = getLoginUser();
		if (user == null) {
			return dispatchlogin();
		}
        SysSite sysSite = sysSiteService.finSiteById(user.getSysSite().getId());
		// 查询主菜单（系统二级菜单）
        List<SysMenu> menuTreeList  = (List<SysMenu>) getSession().getAttribute("menuTree");
        menuList = new ArrayList<SysMenu>();
        if (menuTreeList != null && !menuTreeList.isEmpty()) {
            for (SysMenu menu : menuTreeList) {
                if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                    menuList.addAll(menu.getChildren());
                }
            }
        }
		// 首页显示的logo
		if (StringUtils.isNotBlank(sysSite.getLogoImg())) {
			logoImg = sysSite.getLogoImg();
		}
		// 首页显示的文字：拥有单个角色时按角色的“页面显示名称”显示，多个显示默认文字“后台管理系统”，站点和全局管理员显示默认
        if (user.getUserType() != UserType.AllSiteManage && user.getUserType() != UserType.SiteManage
                && user.getSysRoles() != null) {
            Set<String> displayNameSet = new HashSet<String>();
            for (SysUserRole userRole : user.getSysRoles()) {
                SysRole role = sysRoleService.findSysRoleById(userRole.getRole().getId());
                if (StringUtils.isNotBlank(role.getDisplayName())) {
                    displayNameSet.add(role.getDisplayName());
                }
            }
            if (displayNameSet.size() == 1) {
                systemTitle = displayNameSet.iterator().next();
            }
        }

		json = readJson(result);
		return dispatch();
	}

	public SysMenu getMenu() {
		return menu;
	}

	public void setMenu(SysMenu menu) {
		this.menu = menu;
	}

	@Override
	public String getJson() {
		return json;
	}

	@Override
	public void setJson(String json) {
		this.json = json;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public List<SysMenu> getFirstMenus() {
		return firstMenus;
	}

	public void setFirstMenus(List<SysMenu> firstMenus) {
		this.firstMenus = firstMenus;
	}

	public List<SysMenu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<SysMenu> menuList) {
		this.menuList = menuList;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getFgDomain() {
		return fgDomain;
	}

	public void setFgDomain(String fgDomain) {
		this.fgDomain = fgDomain;
	}
}
