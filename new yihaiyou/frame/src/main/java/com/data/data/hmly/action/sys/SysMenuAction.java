package com.data.data.hmly.action.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysActionLogService;
import com.data.data.hmly.service.SysMenuService;
import com.data.data.hmly.service.entity.SysMenu;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.vo.TreeVo;
import com.data.data.hmly.util.Jacksons;
import com.data.data.hmly.util.WfConstants;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.Constants;

/**
 * @author cjj
 * @date 2015年3月30日
 * @TODO 用户管理
 */
public class SysMenuAction extends FrameBaseAction implements ModelDriven<SysMenu> {
	private static final long serialVersionUID = 1L;
	@Resource
	private SysMenuService sysMenuService;
	@Resource
	private SysActionLogService sysActionLogService;
	private String json;
	private Integer page = 1;
	private Integer rows = 10;
	private SysMenu menu = new SysMenu();
	private Map<String, Object> map = new HashMap<String, Object>();
	private final SysUser sysUser = getLoginUser();
	private String content = "";
	String account = ""; // 用于记录当前登录用户账号
	String name = ""; // 用于记录当前登录用户姓名

	/**
	 * 功能描述：系统用户管理
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:24:14
	 * @return
	 */
	public Result manage() {
		return dispatch();
	}

	/**
	 * 功能描述：查询模块信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:52:04
	 * @return
	 */
	public Result searchMenu() {
		Page pager = new Page(page, rows);
		List<SysMenu> menus = sysMenuService.listMenus(menu, pager);
		return jsonResult(menus, pager.getTotalCount());
	}

	/**
	 * 功能描述：查询所有模块
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:52:04
	 * @return
	 */
	public Result searchAll() {
		Page pager = new Page(page, rows);
		List<SysMenu> menus = sysMenuService.listMenus(menu, pager);
		return jsonResult(menus, pager.getTotalCount());
	}

	/**
	 * 功能描述：加载模块树
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月3日上午9:57:37
	 * @return
	 */
	public Result loadMenuTree() {
		List<TreeVo> menus = new ArrayList<TreeVo>();
		TreeVo tree = new TreeVo();
		tree.setId(0l);
		tree.setText("后台模块管理");
		tree.setState("open");
		List<TreeVo> childs = sysMenuService.loadMenuTree(0L);
		tree.setChildren(childs);
		menus.add(tree);
		map.put("menus", menus);
		json = Jacksons.me().readAsString(map);
		return json(JSONObject.fromObject(json));
	}

	/**
	 * 功能描述：保存模块信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:11:43
	 * @return
	 */
	public Result saveMenu() {
		try {
			sysMenuService.saveOrUpdateMenu(menu);
			simpleResult(map, true, "");
			if (sysUser != null) {
				account = sysUser.getAccount();
				name = sysUser.getUserName();
			}
			content = "账号：" + account + "保存一个模块，模块ID为：" + menu.getMenuid();
			sysActionLogService.addSysLog(account, name, "ysyMenu", "保存", content);
		} catch (Exception e) {
			simpleResult(map, false, "保存模块出错!");
			slog(Constants.log_error, "保存模块出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：冻结模块
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result forzenMenu() {
		try {
			menu = sysMenuService.getMenuById(menu.getMenuid());
			if (menu.getStatus() != null && menu.getStatus().equals(1)) {
				simpleResult(map, false, "冻结失败,模块已冻结!");
			} else {
				menu.setStatus(1);
				sysMenuService.saveOrUpdateMenu(menu);
				simpleResult(map, true, "");
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "冻结一个模块，模块ID为：" + menu.getMenuid();
				sysActionLogService.addSysLog(account, name, "sysMenu", WfConstants.frozen, content);
			}
		} catch (Exception e) {
			simpleResult(map, false, "冻结模块出错!");
			slog(Constants.log_error, "冻结模块出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：解结模块
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result unForzenMenu() {
		try {
			menu = sysMenuService.getMenuById(menu.getMenuid());
			if (menu.getStatus() != null && menu.getStatus().equals(0)) {
				simpleResult(map, false, "解冻失败,模块已解冻!");
			} else {
				menu.setStatus(0);
				sysMenuService.saveOrUpdateMenu(menu);
				simpleResult(map, true, "");
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "解冻一个模块，模块ID为：" + menu.getMenuid();
				sysActionLogService.addSysLog(account, name, "sysMenu", WfConstants.remove_Frozen, content);
			}
		} catch (Exception e) {
			simpleResult(map, false, "解冻模块出错!");
			slog(Constants.log_error, "解冻模块出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：删除模块
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月3日上午9:56:56
	 * @return
	 */
	public Result deleteMenu() {
		try {
			menu = sysMenuService.getMenuById(menu.getMenuid());
			if (menu.getDelFlag() != null && menu.getDelFlag()) {
				simpleResult(map, false, "删除失败,模块已删除!");
			} else {
				menu.setDelFlag(true);
				sysMenuService.saveOrUpdateMenu(menu);
				simpleResult(map, true, "");
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "删除一个模块，模块ID为：" + menu.getMenuid();
				sysActionLogService.addSysLog(account, name, "sysMenu", WfConstants.deleteString, content);
			}
		} catch (Exception e) {
			simpleResult(map, false, "删除模块出错!");
			slog(Constants.log_error, "删除模块出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：根据ID获取模块对象
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:53:29
	 * @return
	 */
	public Result getMenuById() {
		try {
			menu = sysMenuService.getMenuById(menu.getMenuid());
			simpleResult(map, true, "");
			map.put("menu", menu);
		} catch (Exception e) {
			simpleResult(map, false, "读取模块出错!");
			slog(Constants.log_error, "读取模块出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：公开模块
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result publicMenu() {
		try {
			menu = sysMenuService.getMenuById(menu.getMenuid());
			if (menu == null) {
				simpleResult(map, false, "模块不存在!");
			} else {
				menu.setIsPublic(1);
				sysMenuService.saveOrUpdateMenu(menu);
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "公开模块，模块ID为：" + menu.getMenuid();
				sysActionLogService.addSysLog(account, name, "sysMenu", "公开", content);
			}
			simpleResult(map, true, "");
		} catch (Exception e) {
			simpleResult(map, false, "公开模块出错!");
			slog(Constants.log_error, "公开模块出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：私有模块
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result privateMenu() {
		try {
			menu = sysMenuService.getMenuById(menu.getMenuid());
			if (menu == null) {
				simpleResult(map, false, "模块不存在!");
			} else {
				menu.setIsPublic(0);
				sysMenuService.saveOrUpdateMenu(menu);
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "私有一个模块，模块ID为：" + menu.getMenuid();
				sysActionLogService.addSysLog(account, name, "sysMenu", "私有", content);
			}
			simpleResult(map, true, "");
		} catch (Exception e) {
			simpleResult(map, false, "私有模块出错!");
			slog(Constants.log_error, "私有模块出错!", e);
		}
		return jsonResult(map);
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

	@Override
	public SysMenu getModel() {
		return menu;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
