package com.data.data.hmly.action.sys;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysActionLogService;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.entity.*;
import com.data.data.hmly.util.Encryption;
import com.data.data.hmly.util.Jacksons;
import com.data.data.hmly.util.WfConstants;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.Constants;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.RegExUtil;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjj
 * @date 2015年3月30日
 * @TODO 用户管理
 */
public class SysUserAction extends FrameBaseAction implements ModelDriven<SysUser> {
	private static final long serialVersionUID = 1L;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysUnitService sysUnitService;
	@Resource
	private SysActionLogService sysActionLogService;

	private String json;
	private Integer page = 1;
	private Integer rows = 20;
	private SysUser user = new SysUser();
	private User accountUser = new User();
	Map<String, Object> map = new HashMap<String, Object>();
	private String roleIds;
	private String pwd; // 用户密码
	private final SysUser sysUser = getLoginUser();
	private String content = "";
	String account = ""; // 用于记录当前登录用户账号
	String name = ""; // 用于记录当前登录用户姓名

	private String 					iniviteUrl;


	public Result editSysUser() {
        if (sysUser == null) {
            return dispatchlogin();
        }
		if (!UserType.SiteManage.equals(sysUser.getUserType()) && !UserType.AllSiteManage.equals(sysUser.getUserType())) {
			simpleResult(map, false, "操作失败！非站点管理员，请勿操作！");
			return jsonResult(map);
		}
		String uId = (String) getParameter("Uid");

		if(!StringUtils.isEmpty(uId)){
			user = sysUserService.findSysUserById(Long.parseLong(uId));
		}
		return dispatch();
	}


	/**
	 * 功能描述：系统用户管理
	 *
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:24:14
	 * @return
	 */
	public Result manage() {
        getRequest().setAttribute("userType", sysUser.getUserType());
		return dispatch();
	}

	public Result manage2() {
		return dispatch();
	}

	/**
	 * 功能描述：查询用户信息
	 *
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:52:04
	 * @return
	 */
	public Result searchUser() {
		initPage();
		Page pager = new Page(page, rows);

//		List<SysUser> users = sysUserService.findUserList(getLoginUser(), pager, getSite(), isSupperAdmin());

		SysUnit sysUnit = getCompanyUnit();

		String unitId = (String) getParameter("sysUnit.id");

		if(!StringUtils.isEmpty(unitId)){
			sysUnit = sysUnitService.findSysUnitById(Long.parseLong(unitId));
		}

		List<SysUser> usersList = sysUserService.findUserListBySysUser(user,sysUnit, pager, getSite(), isSupperAdmin());

		JsonConfig jsonConfig = JsonFilter.getIncludeConfig("sysUnit");
//		List<SysUser> users = sysUserService.findUserList(user, pager, getSite(), isSupperAdmin());
//		return datagrid(users, pager.getTotalCount(), "area");
		return datagrid(usersList, pager.getTotalCount(),jsonConfig);
	}

	public Result edtiUserPassword() {
		SysUser sysUser = sysUserService.findSysUserById(user.getId());

		String code = (String) getParameter("validCode");
		String validateCode = (String) getSession().getAttribute("checkNum");
		if (!StringUtils.hasText(code) || !code.equals(validateCode)) {
			simpleResult(map, false, "验证码不正确");
			return jsonResult(map);
		}

		String oldPassword = (String) getParameter("oldPassword");
		if (!sysUser.getPassword().equals(Encryption.encry(oldPassword))) {
			simpleResult(map, false, "原密码错误");
			return jsonResult(map);
		}

		if (!RegExUtil.checkPassword(user.getPassword()) || !StringUtils.isNotBlank(user.getPassword())) {
			simpleResult(map, false, "操作失败！请重新操作");
			return jsonResult(map);
		}

		SysUnit unit = sysUnitService.findUnitById(getCompanyUnit().getId());
		if (unit.getUnitType() != UnitType.company
				|| getLoginUser().getUserType() != UserType.CompanyManage) {
			simpleResult(map, false, "操作失败！非公司管理员，请勿操作！");
			return jsonResult(map);
		}

		if (!getLoginUser().getId().equals(sysUser.getId())) {
			simpleResult(map, false, "操作失败！只能修改自己的密码！");
			return jsonResult(map);
		}

		sysUser.setPassword(Encryption.encry(user.getPassword()));

		sysUserService.saveOrUpdateUser(sysUser);
		simpleResult(map, true, "");
		return jsonResult(map);
	}

	/**
	 * 功能描述：保存用户信息
	 *
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:11:43
	 * @return
	 */
	public Result saveUser() {
		try {
			Integer flag = 0;
			// 新增时设置默认密码
			if (user.getId() == null) {
				user.setPassword(Encryption.encry(Constants.password));
				user.setCreatedTime(new Date());
				user.setUserType(UserType.ADMIN);
				user.setLoginNum(0);
				// SysUnit sysUnit = getLoginUser().getSysUnit();
				// user.setSysUnit(sysUnit);
				user.setSysSite(getSite());
				user.setParent(getLoginUser());
				user.setSuperior(getLoginUser().getSuperior());
				user.setGrand(getLoginUser().getParent());
				flag = sysUserService.saveOrUpdateUser(user);
			}else{
				SysUser sysUser = sysUserService.findSysUserById(user.getId());
				if (StringUtils.isNotBlank(user.getAccount())) {
					sysUser.setAccount(user.getAccount());
				}
				if (StringUtils.isNotBlank(user.getAddress())) {
					sysUser.setAddress(user.getAddress());
				}
				if (StringUtils.isNotBlank(user.getEmail())) {
					sysUser.setEmail(user.getEmail());
				}
				if (StringUtils.isNotBlank(user.getGender())) {
					sysUser.setGender(user.getGender());
				}
				if (StringUtils.isNotBlank(user.getMobile())) {
					sysUser.setMobile(user.getMobile());
				}
				if (StringUtils.isNotBlank(user.getUserName())) {
					sysUser.setUserName(user.getUserName());
				}
				if (StringUtils.isNotBlank(user.getRoles())) {
					sysUser.setRoles(user.getRoles());
				}

//				if (StringUtils.isNotBlank(user.getPassword())) {
//					sysUser.setPassword(Encryption.encry(user.getPassword()));
//				}

				if(user.getSysUnit() != null && user.getSysUnit().getId()!=null){
					sysUser.setSysUnit(sysUnitService.findSysUnitById(user.getSysUnit().getId()));
				}

				sysUser.setUpdateTime(new Date());
				flag = sysUserService.saveOrUpdateUser(sysUser);
			}
//			user.setUpdateTime(new Date());
//			Integer flag = sysUserService.saveOrUpdateUser(user);
			if (flag.equals(1)) {
				simpleResult(map, false, "新增失败,帐号重复!");
			} else {
				if (StringUtils.isNotBlank(roleIds)) {
					sysUserService.updateUserRole(roleIds, user);
				}
				simpleResult(map, true, "");
			}
			if (sysUser != null) {
				account = sysUser.getAccount();
				name = sysUser.getUserName();
			}
			content = "账号：" + account + "保存一个用户信息，ID为：" + user.getId();
			sysActionLogService.addSysLog(account, name, "sysUser", "保存", content);
		} catch (Exception e) {
			simpleResult(map, false, "保存用户出错!");
			slog(Constants.log_error, "保存用户出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：冻结用户
	 *
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result forzenUser() {
        if (sysUser == null) {
            return dispatchlogin();
        }
		if (!UserType.SiteManage.equals(sysUser.getUserType()) && !UserType.AllSiteManage.equals(sysUser.getUserType())) {
			simpleResult(map, false, "操作失败！非站点管理员，请勿操作！");
			return jsonResult(map);
		}
		try {
			user = sysUserService.findSysUserById(user.getId());
			user.setIsUse(false);
			sysUserService.saveOrUpdateUser(user);
            account = sysUser.getAccount();
            name = sysUser.getUserName();
            content = "账号：" + account + "冻结一个用户，ID为：" + user.getId();
            sysActionLogService.addSysLog(account, name, "sysUser", WfConstants.frozen, content);
			map.put("success", true);
		} catch (Exception e) {
			simpleResult(map, false, "冻结用户出错!");
			slog(Constants.log_error, "冻结用户出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：激活用户
	 *
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result unForzenUser() {
		if (sysUser == null) {
			return dispatchlogin();
		}
		if (!UserType.SiteManage.equals(sysUser.getUserType()) && !UserType.AllSiteManage.equals(sysUser.getUserType())) {
			simpleResult(map, false, "操作失败！非站点管理员，请勿操作！");
			return jsonResult(map);
		}
		try {
			user = sysUserService.findSysUserById(user.getId());
			user.setIsUse(true);
			sysUserService.saveOrUpdateUser(user);
            map.put("success", true);
            account = sysUser.getAccount();
            name = sysUser.getUserName();
            content = "账号：" + account + "解冻一个用户，ID为：" + user.getId();
			sysActionLogService.addSysLog(account, name, "sysUser", WfConstants.remove_Frozen, content);
		} catch (Exception e) {
			simpleResult(map, false, "解冻用户出错!");
			slog(Constants.log_error, "解冻用户出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：删除用户
	 *
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月9日下午4:05:37
	 * @return
	 */
	public Result deleteUser() {
        if (sysUser == null) {
            return dispatchlogin();
        }
        if (!UserType.SiteManage.equals(sysUser.getUserType()) && !UserType.AllSiteManage.equals(sysUser.getUserType())) {
            simpleResult(map, false, "操作失败！非站点管理员，请勿操作！");
            return jsonResult(map);
        }
		String content = "";
		try {
			user = sysUserService.findSysUserById(user.getId());
			user.setDelFlag(true);
            sysUserService.saveOrUpdateUser(user);
            account = sysUser.getAccount();
            name = sysUser.getUserName();
            content = "账号：" + account + "删除一条用户信息，ID为：" + user.getId();
            sysActionLogService.addSysLog(account, name, "sysUser", WfConstants.deleteString, content);
            map.put("success", true);
        } catch (Exception e) {
			simpleResult(map, false, "删除用户出错!");
			slog(Constants.log_error, "删除用户出错!", e);
		}
		return jsonResult(map);
	}

	/********* 分界线 ****************/
	// 新增，修改
	public Result addEditUser() {
        if (sysUser == null) {
            return dispatchlogin();
        }
        if (!UserType.SiteManage.equals(sysUser.getUserType()) && !UserType.AllSiteManage.equals(sysUser.getUserType())) {
            simpleResult(map, false, "操作失败！非站点管理员，请勿操作！");
            return jsonResult(map);
        }
		try {
			if (user.getId() == null) {
				user.setDelFlag(false);
				user.setPassword(Encryption.encry("123456"));
				user.setCreatedTime(new Date());
				user.setUserType(UserType.ADMIN);
				user.setLoginNum(0);
				SysUnit sysUnit = getLoginUser().getSysUnit();
				user.setSysUnit(sysUnit);
				user.setSysSite(sysUnit.getSysSite());
				user.setPassword("123456");
				user.setCreatedTime(new Date());
			} else {
				user.setUpdateTime(new Date());
			}
			sysUserService.saveOrUpdateUser(user);
            map.put("success", true);
            account = sysUser.getAccount();
            name = sysUser.getUserName();
            content = "账号：" + account + "保存一条用户信息,ID为：" + user.getId();
            sysActionLogService.addSysLog(account, name, "sysUser", WfConstants.updateString, content);
			// TODO 保存操作日记
			json = Jacksons.me().readAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			json = Jacksons.me().readAsString(map);
		}
		return json(JSONObject.fromObject(json));
	}

	// 详情
	public Result getUserInfo() {
        if (sysUser == null) {
            return dispatchlogin();
        }
        if (!UserType.SiteManage.equals(sysUser.getUserType()) && !UserType.AllSiteManage.equals(sysUser.getUserType())) {
            simpleResult(map, false, "操作失败！非站点管理员，请勿操作！");
            return jsonResult(map);
        }
		if (user.getId() != null) {
			user = sysUserService.findSysUserById(user.getId());
		}
		return dispatch();
	}

	public Result getUserDetail() {
		if (getLoginUser().getId() == null) {
			simpleResult(map, false, "当前未有用户登录，请登录系统！");
			return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig()));
		}
		user = sysUserService.findSysUserById(getLoginUser().getId());
		map.put("user", user);
		simpleResult(map, true, "");
		return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig()));
	}

	// 删除
	public Result delUser() {
        if (sysUser == null) {
            return dispatchlogin();
        }
        if (!UserType.SiteManage.equals(sysUser.getUserType()) && !UserType.AllSiteManage.equals(sysUser.getUserType())) {
            simpleResult(map, false, "操作失败！非站点管理员，请勿操作！");
            return jsonResult(map);
        }
		SysUser su = new SysUser();
		try {
			su = sysUserService.findSysUserById(user.getId());
			if (su != null) {
				if (su.getDelFlag() == true) {
					simpleResult(map, false, "删除失败,用户已删除!");
				} else {
					sysUserService.deleteUser(user);
					simpleResult(map, true, "用户已删除!");
                }
            }
            account = sysUser.getAccount();
            name = sysUser.getUserName();
            content = "账号：" + account + "删除一个用户，ID为：" + user.getId();
            sysActionLogService.addSysLog(account, name, "sysUser", WfConstants.deleteString, content);
			// TODO 保存操作日记
		} catch (Exception e) {
			simpleResult(map, false, "删除用户出错!");
		}
		return jsonResult(map);
	}

	// 激活
	public Result okUser() {
        if (sysUser == null) {
            return dispatchlogin();
        }
        if (!UserType.SiteManage.equals(sysUser.getUserType()) && !UserType.AllSiteManage.equals(sysUser.getUserType())) {
            simpleResult(map, false, "操作失败！非站点管理员，请勿操作！");
            return jsonResult(map);
        }
		SysUser su = new SysUser();
		try {
			su = sysUserService.findSysUserById(user.getId());
			if (su != null) {
				if (su.getIsUse() == true) {
					simpleResult(map, false, "激活失败,用户已激活!");
				} else {
					sysUserService.doOkUser(user.getId());
					simpleResult(map, true, "用户已激活!");
				}
			}
            account = sysUser.getAccount();
            name = sysUser.getUserName();
            content = "账号：" + account + "激活一个用户，ID为：" + user.getId();
            sysActionLogService.addSysLog(account, name, "sysUser", WfConstants.checkSum, content);
		} catch (Exception e) {
			e.printStackTrace();
			simpleResult(map, false, "激活用户出错!");
		}
		return jsonResult(map);
	}

	// 冻结
	public Result noUser() {
        if (sysUser == null) {
            return dispatchlogin();
        }
        if (!UserType.SiteManage.equals(sysUser.getUserType()) && !UserType.AllSiteManage.equals(sysUser.getUserType())) {
            simpleResult(map, false, "操作失败！非站点管理员，请勿操作！");
            return jsonResult(map);
        }
		SysUser su = new SysUser();
		try {
			su = sysUserService.findSysUserById(user.getId());
			if (su != null) {
				if (su.getIsUse() == false) {
					simpleResult(map, false, "冻结失败,用户已冻结!");
				} else {
					sysUserService.doNoUser(user.getId());
					simpleResult(map, true, "用户已冻结!");
				}
			}
            account = sysUser.getAccount();
            name = sysUser.getUserName();
            content = "账号：" + account + "冻结一个用户，ID为：" + user.getId();
            sysActionLogService.addSysLog(account, name, "sysUser", WfConstants.frozen, content);
		} catch (Exception e) {
			simpleResult(map, false, "冻结用户出错!");
		}
		return jsonResult(map);
	}

	// 回显修改
	public Result getUserInfoJson() {
		if (user.getId() != null) {
			user = sysUserService.findSysUserById(user.getId());
			map.put("user", user);
		}
		return jsonResult(map);
	}

	/**
	 * 邀请供应商
	 * @author caiys
	 * @date 2015年11月5日 下午4:01:31
	 * @return
	 */
	public Result inivite() {
		Long uid = getLoginUser().getId();
		String siteUrl = getSite().getSiteurl();
		iniviteUrl = siteUrl + "/mall/supplier/inivited.jhtml?uid=" +uid;
		return dispatch();
	}

	/**
	 * 校验手机是否存在
	 * @author caiys
	 * @date 2015年11月5日 下午4:01:31
	 * @return
	 */
	public Result validateMobile() {
		String mobile = (String) getParameter("mobile");
		String neUserId = (String) getParameter("neUserId");

		boolean isExists = sysUserService.isExistsAccount(mobile, neUserId);
		if (!isExists) {
			map.put("notExisted", true);
		} else {
			map.put("notExisted", false);
		}
		simpleResult(map, true, null);
		return jsonResult(map);
	}

	public Result validateUser() {

		String neUserId = (String) getParameter("neUserId");
		boolean isExists = sysUserService.isExistsAccount(accountUser, neUserId);
		if (!isExists) {
			map.put("notExisted", true);
		} else {
			map.put("notExisted", false);
		}
		simpleResult(map, true, null);
		return jsonResult(map);

	}
	public Result validateYhyUser() {

		String neUserId = (String) getParameter("neUserId");
		boolean isExists = sysUserService.isExistsAccount(accountUser, neUserId);
		if (!isExists) {
			map.put("valid", true);
		} else {
			map.put("valid", false);
		}
		return jsonResult(map);
	}
	public Result validateYhyUserPassword() {

		String neUserId = (String) getParameter("neUserId");
		accountUser.setId(Long.parseLong(neUserId));
		boolean isExists = sysUserService.isExistsAccount(accountUser, null);
		if (isExists) {
			map.put("valid", true);
		} else {
			map.put("valid", false);
		}
		return jsonResult(map);
	}

	// 分页初始化
	public void initPage() {
		pager = new Page();
		pager.setPageIndex(page);
		pager.setPageSize(rows);
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
	public SysUser getModel() {
		return user;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getIniviteUrl() {
		return iniviteUrl;
	}

	public void setIniviteUrl(String iniviteUrl) {
		this.iniviteUrl = iniviteUrl;
	}

	public User getAccountUser() {
		return accountUser;
	}

	public void setAccountUser(User accountUser) {
		this.accountUser = accountUser;
	}
}
