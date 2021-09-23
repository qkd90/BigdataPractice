package com.data.data.hmly.action.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysActionLogService;
import com.data.data.hmly.service.SysRoleService;
import com.data.data.hmly.service.SysSiteService;
import com.data.data.hmly.service.SysUnitDetailService;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.entity.SiteStatus;
import com.data.data.hmly.service.entity.SysRole;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitDetail;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.UnitType;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.util.Encryption;
import com.data.data.hmly.util.WfConstants;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.Constants;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;

/**
 * @author cjj
 * @date 2015年4月8日
 * @TODO 站点
 */
public class SysSiteAction extends FrameBaseAction implements ModelDriven<SysRole> {
	private static final long serialVersionUID = 1L;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysSiteService sysSiteService;
	@Resource
	private SysActionLogService sysActionLogService;
	@Resource
	private TbAreaService tbAreaService;
	@Resource
	private SysUnitService sysUnitService;
	@Resource
	private SysUnitDetailService sysUnitDetailService;
	@Resource
	private SysUserService				sysUserService;
	private String json;
	private Integer page = 1;
	private Integer rows = 10;
	private SysRole role = new SysRole();
	
	private Map<String, Object> map = new HashMap<String, Object>();
	private final SysUser sysUser = getLoginUser();
	private String content = "";
	String account = ""; // 用于记录当前登录用户账号
	String name = ""; // 用于记录当前登录用户姓名
	
	// 页面字段
	private SysSite					 	site				= new SysSite();
	private SysUser						user				= new SysUser();
	private SysUnit 					unit 				= new SysUnit();
	private SysUnitDetail				unitDetail			= new SysUnitDetail();
	private Long						areaId;
	private String filePath;
	private String siteId;

	
	public Result getEditSite(){
		
		String sidStr = (String) getParameter("sid");
		SysUnit unit = null;
		SysUnitDetail unitDetail = null;
		SysSite sysSite = null;
		if (!StringUtils.isEmpty(sidStr)) {
			sysSite = sysSiteService.findSysSiteById(Long.parseLong(sidStr));
			unit = sysUnitService.finUnitBySid(Long.parseLong(sidStr));
			if (unit != null) {
				if (unit.getSysUnitDetail() != null) {
					unitDetail = unit.getSysUnitDetail();
				}
			}
			
			
		}
		
		if (sysSite != null){
			map.put("sysSite", sysSite);
			if (unit != null) {
				map.put("unit", unit);
				if (sysSite.getArea() != null) {
					map.put("cityCode", unit.getArea().getId());
				} else {
					map.put("cityCode", "");
				}
				
				if (unitDetail != null) {
					map.put("unitDetail", unitDetail);
				} else {
					map.put("unitDetail", null);
				}
			} else {
				map.put("unit", null);
			}
			simpleResult(map, true, "");
		} else {
			simpleResult(map, false, "获取数据失败!");
		}

		Result rel = json(JSONObject.fromObject(map));
//		Result rel = json(JSONObject.fromObject(map, JsonFilter.getFilterConfig("parentUnit","companyUnit","area","sysUnit","scenic","status")));
		return rel;
	}
	
	/**
	 * 功能描述：系统角色管理
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:24:14
	 * @return
	 */
	public Result sitemanage() {
		List<TbArea> provinces = tbAreaService.findProvince();
		setAttribute("province", provinces);
		long provinceId = 110000;
		List<TbArea> citys = tbAreaService.findCity(provinceId);
		setAttribute("city", citys);
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
		return jsonResult(roles, pager.getTotalCount());
	}

	public Result searchSite() {
		Page pager = new Page(page, rows);



		List<SysSite> sites = sysSiteService.findAllSite(pager, UnitType.site);
		// return jsonResult(sites, pager.getTotalCount());
//		return datagrid(sites, pager.getTotalCount(), "father", "childs");
//		return datagrid(sites, pager.getTotalCount(),"area");
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, new String[]{"area"});
		return datagrid(sites, pager.getTotalCount(), jsonConfig);
	}

	/**
	 * 查询站点
	 * @author caiys
	 * @date 2015年11月7日 下午8:32:55
	 * @return
	 */
	public Result listSite() {
		List<SysSite> sites = sysSiteService.findAllSite();
//		JsonConfig config = JsonFilter.getFilterConfig("area");
		JSONArray json = JSONArray.fromObject(sites);
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
			sysRoleService.saveOrUpdateRole(role);
			simpleResult(map, true, "");
			if (sysUser != null) {
				account = sysUser.getAccount();
				name = sysUser.getUserName();
			}
			content = "账号：" + account + "保存一个角色，ID为：" + role.getId();
			sysActionLogService.addSysLog(account, name, "sysSite", "保存", content);
		} catch (Exception e) {
			simpleResult(map, false, "保存角色出错!");
			slog(Constants.log_error, "保存角色出错!", e);
		}
		return jsonResult(map);
	}

	/*public Result editSite() {
		return text("ddd");
	}*/

	/**
	 * 功能描述：冻结角色
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result forzenRole() {
		try {
			role = sysRoleService.findSysRoleById(role.getId());
			if (role.getStatus() != null && role.getStatus().equals( 1)) {
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
				sysActionLogService.addSysLog(account, name, "sysSite", WfConstants.frozen, content);
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
			if (role.getStatus() != null && role.getStatus().equals( 0)) {
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
				sysActionLogService.addSysLog(account, name, "sysSite", WfConstants.remove_Frozen, content);
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
				sysActionLogService.addSysLog(account, name, "sysSite", WfConstants.deleteString, content);
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

	/**
	 * 新增、编辑站点信息
	 * @author caiys
	 * @date 2015年11月7日 上午9:01:10
	 * @return
	 */
	public Result editSite() {
		siteId = (String) getParameter("siteId");
		if (StringUtils.isNotBlank(siteId)) {
			site = sysSiteService.findSysSiteById(Long.valueOf(siteId));
			unit = sysUnitService.findUnit(Long.valueOf(siteId), UnitType.site);
			unitDetail = unit.getSysUnitDetail();
			user = sysUserService.findCompanyManager(unit.getId(), UserType.SiteManage);
			areaId = unit.getArea().getId();
			if (unitDetail != null) {
				filePath = unitDetail.getLogoImgPath();
			}
		}
		return dispatch();
	}
	
	/**
	 * 新增、编辑站点
	 * @author caiys
	 * @date 2015年10月21日 下午3:36:41
	 * @return
	 */
	@AjaxCheck
	public Result saveSite() {
		if (unit.getId() == null) {		// 新增
			TbArea area = new TbArea();
			area.setId(areaId);
			site.setArea(area);
			site.setStatus(SiteStatus.OK);
			
			user.setCreatedTime(new Date());
			user.setPassword(Encryption.encry(Constants.password));
			user.setMobile(user.getAccount());
			user.setUserType(UserType.SiteManage);
			user.setStatus(UserStatus.activity);
//			user.setParent(user);
//			user.setParent(parentUser);
//			user.setGrand(parentUser.getParent());
//			user.setSuperior(user);
			user.setIsUse(true);
			user.setDelFlag(false);
			/*SysSite site = new SysSite();
			site.setId(siteId);*/
			user.setSysSite(site);

			unit.setArea(area);
			unit.setSysSite(site);
			unit.setDelFlag(false);
			unit.setStatus(0);	// 状态：-1待审核；0通过；1不通过；
			unit.setUnitType(UnitType.site);
			unit.setCreateTime(new Date());

			unitDetail.setLogoImgPath(filePath);
			unitDetail.setInivitorId(getLoginUser().getId());
			unitDetail.setInivitorName(getLoginUser().getUserName());
		} else {	// 修改
			SysSite oldSite = sysSiteService.finSiteById(site.getId());
			oldSite.setSitename(site.getSitename());
			oldSite.setSiteurl(site.getSiteurl());
			TbArea area = new TbArea();
			area.setId(areaId);
			oldSite.setArea(area);
			
			SysUser oldUser = sysUserService.load(user.getId());
			oldUser.setUserName(user.getUserName());
			oldUser.setAccount(user.getAccount());
			oldUser.setMobile(user.getAccount());
			oldUser.setEmail(user.getEmail());
			oldUser.setQqNo(user.getQqNo());
			/*SysSite site = new SysSite();
			site.setId(siteId);
			oldUser.setSysSite(site);*/
			oldUser.setUpdateTime(new Date());
			
			SysUnit oldUnit = sysUnitService.findUnitById(unit.getId());
			oldUnit.setName(unit.getName());
			oldUnit.setArea(area);
			oldUnit.setAddress(unit.getAddress());
			/*oldUnit.setSysSite(site);*/
			
			SysUnitDetail oldUnitDetail = oldUnit.getSysUnitDetail();
			oldUnitDetail.setBrandName(unitDetail.getBrandName());
			oldUnitDetail.setSupplierType(unitDetail.getSupplierType());
			oldUnitDetail.setBusinessScope(unitDetail.getBusinessScope());
			oldUnitDetail.setBusinessType(unitDetail.getBusinessType());
			oldUnitDetail.setTelphone(unitDetail.getTelphone());
			oldUnitDetail.setFax(unitDetail.getFax());
			oldUnitDetail.setMainBody(unitDetail.getMainBody());
			oldUnitDetail.setBusinessModel(unitDetail.getBusinessModel());
			oldUnitDetail.setPartnerChannel(unitDetail.getPartnerChannel());
			oldUnitDetail.setPartnerUrl(unitDetail.getPartnerUrl());
			oldUnitDetail.setPartnerAdvantage(unitDetail.getPartnerAdvantage());
			oldUnitDetail.setLogoImgPath(filePath);
			oldUnitDetail.setContactName(unitDetail.getContactName());
			oldUnitDetail.setMobile(unitDetail.getMobile());
			oldUnitDetail.setMainBusiness(unitDetail.getMainBusiness());
			oldUnitDetail.setIntroduction(unitDetail.getIntroduction());
			
			site = oldSite;
			user = oldUser;
			unit = oldUnit;
			unitDetail = oldUnitDetail;
		}
		
		sysUserService.saveSiteInfo(site, user, unit, unitDetail);
//		map.put("id", productId);
		simpleResult(map, true, "");
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

	public SysSite getSite() {
		return site;
	}

	public void setSite(SysSite site) {
		this.site = site;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public SysUnit getUnit() {
		return unit;
	}

	public void setUnit(SysUnit unit) {
		this.unit = unit;
	}

	public SysUnitDetail getUnitDetail() {
		return unitDetail;
	}

	public void setUnitDetail(SysUnitDetail unitDetail) {
		this.unitDetail = unitDetail;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}


}
