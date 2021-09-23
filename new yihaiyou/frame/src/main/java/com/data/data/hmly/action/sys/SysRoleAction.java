package com.data.data.hmly.action.sys;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysActionLogService;
import com.data.data.hmly.service.SysRoleService;
import com.data.data.hmly.service.entity.SysRole;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.SysUserRole;
import com.data.data.hmly.util.WfConstants;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.Constants;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjj
 * @date 2015年3月30日
 * @TODO 角色管理
 */
public class SysRoleAction extends FrameBaseAction implements ModelDriven<SysRole> {
	private static final long serialVersionUID = 1L;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysActionLogService sysActionLogService;
	private String json;
	private Integer page = 1;
	private Integer rows = 10;
	private SysRole role = new SysRole();
	private Map<String, Object> map = new HashMap<String, Object>();
	private Long userId;
	private final SysUser sysUser = getLoginUser();
	private String content = "";
	String account = ""; // 用于记录当前登录用户账号
	String name = ""; // 用于记录当前登录用户姓名

	/**
	 * 功能描述：系统角色管理
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:24:14
	 * @return
	 */
	public Result manage() {
		getRequest().setAttribute("userType", sysUser.getUserType());
		return dispatch();
	}

	/**
	 * 功能描述：查询角色信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:52:04
	 * @return
	 */
	public Result searchRole() {
		Page pager = new Page(page, rows);
		List<SysRole> roles = sysRoleService.findRoleList(isSupperAdmin(), getSite(), role, pager, getLoginUser());
		return datagrid(roles, pager.getTotalCount());
//		return jsonResult(roles, pager.getTotalCount());
	}

	/**
	 * 功能描述：查询所有活动角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:52:04
	 * @return
	 */
	public Result searchAllRole() {

		List<SysRole> allRoles = sysRoleService.findAllUserRoles(isSupperAdmin(), getSite(), getLoginUser());
//		List<SysRole> allRoles = sysRoleService.findAllRoles();
		map.put("rows", allRoles);
		map.put("total", allRoles.size());
		if (userId != null) {
			List<SysUserRole> userRoles = sysRoleService.findUserRoles(userId);
			String roleValue = "";
			if (userRoles != null && userRoles.size() > 0) {
				for (int i = 0; i < userRoles.size(); i++) {
					if (i != 0) {
						roleValue += ",";
					}
					roleValue += userRoles.get(i).getRole().getId();
				}
			}
			map.put("roles", roleValue);
			
		}
		simpleResult(map, true, "");
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig("father","childs");
//		JsonConfig jsonConfig = JsonFilter.getFilterConfig("father","childs");
		JSONObject json = JSONObject.fromObject(map,jsonConfig);
		return json(json);
	}

	/**
	 * 功能描述：保存角色信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:11:43
	 * @return
	 */
	public Result saveRole() {
		try {
			role.setSysSite(getLoginUser().getSysSite());
			sysRoleService.saveOrUpdateRole(role);
			simpleResult(map, true, "");
			if (sysUser != null) {
				account = sysUser.getAccount();
				name = sysUser.getUserName();
			}
			content = "账号：" + account + "保存一个角色，ID为：" + role.getId();
			sysActionLogService.addSysLog(account, name, "sysRole", "保存", content);
		} catch (Exception e) {
			simpleResult(map, false, "保存角色出错!");
			slog(Constants.log_error, "保存角色出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：冻结角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result forzenRole() {
		try {
			role = sysRoleService.findSysRoleById(role.getId());
			if (role.getStatus() != null && role.getStatus().equals(1)) {
				simpleResult(map, false, "冻结失败,角色已冻结!");
			} else {
				role.setStatus(1);
				sysRoleService.saveOrUpdateRole(role);
				simpleResult(map, true, "");
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "冻结一个角色，ID为：" + role.getId();
				sysActionLogService.addSysLog(account, name, "sysRole", WfConstants.frozen, content);
			}
		} catch (Exception e) {
			simpleResult(map, false, "冻结角色出错!");
			slog(Constants.log_error, "冻结角色出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：解结角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result unForzenRole() {
		try {
			role = sysRoleService.findSysRoleById(role.getId());
			if (role.getStatus() != null && role.getStatus().equals(0)) {
				simpleResult(map, false, "解冻失败,角色已解冻!");
			} else {
				role.setStatus(0);
				sysRoleService.saveOrUpdateRole(role);
				simpleResult(map, true, "");
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "解冻一个角色，ID为：" + role.getId();
				sysActionLogService.addSysLog(account, name, "sysRole", WfConstants.remove_Frozen, content);
			}
		} catch (Exception e) {
			simpleResult(map, false, "解冻角色出错!");
			slog(Constants.log_error, "解冻角色出错!", e);
		}
		return jsonResult(map);
	}

	public Result deleteRole() {
		try {
			role = sysRoleService.findSysRoleById(role.getId());
			if (role.getDelFlag() != null && role.getDelFlag()) {
				simpleResult(map, false, "删除失败,角色已删除!");
			} else {
				role.setDelFlag(true);
				sysRoleService.saveOrUpdateRole(role);
				simpleResult(map, true, "");
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "删除一个角色，ID为：" + role.getId();
				sysActionLogService.addSysLog(account, name, "sysRole", WfConstants.deleteString, content);
			}
		} catch (Exception e) {
			simpleResult(map, false, "删除角色出错!");
			slog(Constants.log_error, "删除角色出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：根据ID获取角色对象
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:53:29
	 * @return
	 */
	public Result getRoleById() {
		try {
			role = sysRoleService.findSysRoleById(role.getId());
			simpleResult(map, true, "");
			map.put("role", role);
		} catch (Exception e) {
			simpleResult(map, false, "读取角色出错!");
			slog(Constants.log_error, "读取角色出错!", e);
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
	public SysRole getModel() {
		return role;
	}

	public SysRole getRole() {
		return role;
	}

	public void setRole(SysRole role) {
		this.role = role;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
