package com.data.data.hmly.action.sys;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysActionLogService;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.entity.SupplierType;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitDetail;
import com.data.data.hmly.service.entity.SysUnitImage;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.UnitQualification;
import com.data.data.hmly.service.entity.UnitType;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.service.vo.UnitVo;
import com.data.data.hmly.util.Encryption;
import com.data.data.hmly.util.WfConstants;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.Constants;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.NumberUtil;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjj
 * @date 2015年3月30日
 * @TODO 组织管理
 */
public class SysUnitAction extends FrameBaseAction implements ModelDriven<SysUnit> {
	private static final long serialVersionUID = 1L;
	@Resource
	private SysUnitService sysUnitService;
	@Resource
	private SysActionLogService sysActionLogService;
	@Resource
	private SysUserService				sysUserService;
	@Resource
	private PropertiesManager propertiesManager;
	@Resource
	private TbAreaService tbAreaService;

	private String json;
	private Integer page = 1;
	private Integer rows = 10;
	private SysUnit unit = new SysUnit();
	private Map<String, Object> map = new HashMap<String, Object>();
	private final SysUser sysUser = getLoginUser();
	private String content = "";
	String account = ""; // 用于记录当前登录用户账号
	String name = ""; // 用于记录当前登录用户姓名
	private String fgDomain;
	private SysUser						user				= new SysUser();
	private SysUnitDetail				unitDetail			= new SysUnitDetail();
	private Long						areaId;
	private String filePath;
	private String unitId;
	String identityCode = "";
	private List<SysUnitImage> unitImages = Lists.newArrayList();
	private List<UnitQualification> unitQualifications = Lists.newArrayList();


	public Result downloadFile() throws Exception {
		String urlStr = (String) getParameter("imgPathURL");
		String filePath = (String) getParameter("filePath");
		String fileName = (String) getParameter("fileName");
		if (com.zuipin.util.StringUtils.isBlank(urlStr)) {
			simpleResult(map, false, "下载失败");
			return jsonResult(map);
		}
		if (com.zuipin.util.StringUtils.isBlank(filePath)) {
			filePath = "C:\\yihaiyou";
		}
		String fix = urlStr.substring(urlStr.lastIndexOf("."), urlStr.length());
		if (com.zuipin.util.StringUtils.isBlank(fileName)) {
			fileName = urlStr.substring(urlStr.lastIndexOf("/"), urlStr.length());
		} else {
			fileName = fileName + fix;
		}
		byte[] btImg = FileUtil.getImageFromNetByUrl(urlStr);
		if (null != btImg && btImg.length > 0) {
//			System.out.println("读取到：" + btImg.length + " 字节");
			FileUtil.writeImageToDisk(btImg, fileName, filePath);
		} else {
			System.out.println("没有从该连接获得内容");
		}
		simpleResult(map, true, "");
		return jsonResult(map);

	}

	/**
	 * 功能描述：系统组织管理
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午3:24:14
	 * @return
	 */
	public Result manage() {
		getRequest().setAttribute("userType", sysUser.getUserType());
		return dispatch();
	}

	/**
	 * 功能描述：组织架构
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月8日下午5:24:18
	 * @return
	 */
	public Result loadUnitTree() {
		List<UnitVo> roots = null;
		if (isSupperAdmin()) {
			roots = sysUnitService.findAllUnit();
		} else if (isSiteAdmin()) {
			roots = sysUnitService.findRootUnit(getLoginUser().getSysSite().getId());
		} else {
			roots = sysUnitService.findCompanyUnit(getCompanyUnit().getId());
		}
		for (UnitVo vo : roots) {
			vo.setChildren(sysUnitService.findUnitTree(vo.getId()));
		}
		map.put("rows", roots);
		return json(JSONObject.fromObject(map, JsonFilter.getFilterConfig("area", "childs", "parentUnit")));
	}

	/**
	 * 功能描述：查询组织信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年3月30日下午4:52:04
	 * @return
	 */
	public Result searchUnit() {
		Page pager = new Page(page, rows);
		
//		List<SysUnit> units = sysUnitService.findUnitList(unit, pager, getSite(), isSupperAdmin());
		List<SysUnit> units = sysUnitService.findUnitList(unit, pager, getLoginUser(), isSupperAdmin(), isSiteAdmin());
		List<SysUnit> unitList = new ArrayList<SysUnit>();
		for (SysUnit s : units) {
			if (s.getParentUnit() != null) {
				s.setParentName(s.getParentUnit().getName());	
			}
			if (s.getSysSite() != null) {
				s.setSiteName(s.getSysSite().getSitename());
			}
			
			unitList.add(s);
		}
		
		
//		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, new String[]{"sysUnitDetail", "sysSite", "parentUnit"});
//		return datagrid(units, pager.getTotalCount(), jsonConfig);
		return datagrid(unitList, pager.getTotalCount() );
//		return datagrid(units, pager.getTotalCount(), "area", "parentUnit", "sysUnitDetail", "children", "companyUnit");
	}

	public Result resetPassword() {
		Long userid = 0l;
		String type = (String) getParameter("type");
		if ("unit".equals(type)) {
			String unitIdStr = (String) getParameter("unitId");
			if (com.zuipin.util.StringUtils.isNotBlank(unitIdStr)) {
				user = sysUserService.findCompanyManager(Long.parseLong(unitIdStr), UserType.CompanyManage);
				sysUserService.doResetPassword(user);
			}
		} else if ("user".equals(type)) {
			String userIdStr = (String) getParameter("userId");
			user = sysUserService.load(Long.parseLong(userIdStr));
			sysUserService.doResetPassword(user);
		}
		simpleResult(map, true, "");
		return jsonResult(map);
	}

	/**
	 * 功能描述：保存组织信息
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:11:43
	 * @return
	 */
	public Result saveUnit() {
		try {
			SysUnit companyUnit = sysUnitService.findSysUnitById(unit.getCompanyUnit().getId());	// 找公司城市
			unit.setArea(companyUnit.getArea());
			sysUnitService.saveOrUpdateUnit(unit);
			simpleResult(map, true, "");
			if (sysUser != null) {
				account = sysUser.getAccount();
				name = sysUser.getUserName();
			}
			content = "账号：" + account + "保存一个组织,ID为：" + unit.getId();
			sysActionLogService.addSysLog(account, name, "sysUnit", "保存", content);
		} catch (Exception e) {
			simpleResult(map, false, "保存组织出错!");
			slog(Constants.log_error, "保存组织出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：冻结组织
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result forzenUnit() {
		try {
			unit = sysUnitService.findSysUnitById(unit.getId());
			if (unit.getStatus() != null && unit.getStatus().equals( 1 )) {
				simpleResult(map, false, "冻结失败,组织已冻结!");
			} else {
				unit.setStatus(1);
				sysUnitService.saveOrUpdateUnit(unit);
				simpleResult(map, true, "");
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "冻结一个组织,ID为：" + unit.getId();
				sysActionLogService.addSysLog(account, name, "sysUnit", WfConstants.frozen, content);
			}
		} catch (Exception e) {
			simpleResult(map, false, "冻结组织出错!");
			slog(Constants.log_error, "冻结组织出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：解结组织
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午5:42:54
	 * @return
	 */
	public Result unForzenUnit() {
		try {
			unit = sysUnitService.findSysUnitById(unit.getId());
			if (unit.getStatus() != null && unit.getStatus().equals( 0 )) {
				simpleResult(map, false, "解冻失败,组织已解冻!");
			} else {
				unit.setStatus(0);
				sysUnitService.saveOrUpdateUnit(unit);
				simpleResult(map, true, "");
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "解冻一个组织,ID为：" + unit.getId();
				sysActionLogService.addSysLog(account, name, "sysUnit", WfConstants.remove_Frozen, content);
			}
		} catch (Exception e) {
			simpleResult(map, false, "解冻组织出错!");
			slog(Constants.log_error, "解冻组织出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：删除组织
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月8日下午4:18:20
	 * @return
	 */
	public Result deleteUnit() {
		try {
			unit = sysUnitService.findSysUnitById(unit.getId());
			if (unit.getDelFlag() != null && unit.getDelFlag()) {
				simpleResult(map, false, "删除失败,组织已删除!");
			} else {
				unit.setDelFlag(true);
				sysUnitService.saveOrUpdateUnit(unit);
				simpleResult(map, true, "");
				if (sysUser != null) {
					account = sysUser.getAccount();
					name = sysUser.getUserName();
				}
				content = "账号：" + account + "删除一个组织,ID为：" + unit.getId();
				sysActionLogService.addSysLog(account, name, "sysUnit", WfConstants.deleteString, content);
			}
		} catch (Exception e) {
			simpleResult(map, false, "删除组织出错!");
			slog(Constants.log_error, "删除组织出错!", e);
		}
		return jsonResult(map);
	}
	/**
	 * 功能描述：根据ID获取组织对象
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:53:29
	 * @return
	 */
	public Result getUnitByIdStr() {
		try {
			String idStr = (String) getParameter("idStr");
			
			if (!StringUtils.isEmpty(idStr)) {
				unit = sysUnitService.findSysUnitById(Long.parseLong(idStr));
			}
			simpleResult(map, true, "");
			map.put("unit", unit);
			map.put("comUnit", unit.getCompanyUnit());
			map.put("parUnit", unit.getParentUnit());
			map.put("site", unit.getSysSite());
		} catch (Exception e) {
			simpleResult(map, false, "读取组织出错!");
			slog(Constants.log_error, "读取组织出错!", e);
		}
		JsonConfig jsonConfig = JsonFilter.getFilterConfig("companyUnit", "parentUnit", "sysSite", "children", "sysUnitImages", "area", "sysUnitDetail");
				
		return json(JSONObject.fromObject(map, jsonConfig));
	}

	/**
	 * 功能描述：根据ID获取组织对象
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:53:29
	 * @return
	 */
	public Result getUnitById() {
		try {
			unit = sysUnitService.findSysUnitById(unit.getId());
			simpleResult(map, true, "");
			map.put("unit", unit);
		} catch (Exception e) {
			simpleResult(map, false, "读取组织出错!");
			slog(Constants.log_error, "读取组织出错!", e);
		}
		
//		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(String[]{"sysUnit"});
		
//		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, new String[]{"parentUnit", "companyUnit", "sysSite"});
		return jsonResult(map);
//		return json(JSONObject.fromObject(map,jsonConfig));
	}

	/**
	 * 功能描述：根据ID获取组织对象
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月2日下午4:53:29
	 * @return
	 */
	public Result toAddUnit() {
		try {
			String maxno = sysUnitService.findMaxUnitNoById(unit.getId(), getSite());
			map.put("maxno", maxno);
		} catch (Exception e) {
			simpleResult(map, false, "读取组织出错!");
			slog(Constants.log_error, "读取组织出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 功能描述：根据父组织,生成组织编号
	 * 
	 * @author : cjj 陈俊杰 @CreatedTime : 2015年4月9日上午8:50:56
	 * @return
	 */
	public Result getNewUnitNo() {
		try {

			unit = sysUnitService.findSysUnitById(unit.getId());
			simpleResult(map, true, "");
			map.put("unit", unit);
		} catch (Exception e) {
			simpleResult(map, false, "读取组织出错!");
			slog(Constants.log_error, "读取组织出错!", e);
		}
		return jsonResult(map);
	}

	/**
	 * 邀请供应商审核列表
	 * 
	 * @author caiys
	 * @date 2015年11月5日 下午5:55:00
	 * @return
	 */
	public Result auditList() {
		fgDomain = propertiesManager.getString("FG_DOMAIN");
		return dispatch();
	}

	/**
	 * 搜索审核列表
	 * 
	 * @author caiys
	 * @date 2015年11月5日 下午7:30:49
	 * @return
	 */
	public Result searchAuditList() {
		// 参数
		String supplierType = (String) getParameter("supplierType");
		String keyword = (String) getParameter("keyword");
		Page pager = new Page(page, rows);
		SysUnitDetail ud = new SysUnitDetail();
		if (StringUtils.isNotBlank(supplierType)) {
			ud.setSupplierType(SupplierType.valueOf(supplierType));
		}
		unit.setSysUnitDetail(ud);
		if (StringUtils.isNotBlank(keyword)) {
			unit.setName(keyword);
		}
		unit.setUnitType(UnitType.company); // 只查询公司
		List<SysUnit> units = sysUnitService.findUnitList(unit, pager, getSite(), isSupperAdmin());
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, "sysUnitDetail");
		return datagrid(units, pager.getTotalCount(), jsonConfig);
//		return datagrid(units, pager.getTotalCount(), "companyUnit", "sysSite", "area", "parentUnit", "sysUnit", "scenic");
	}

    /**
     * 根据关键字查询公司和站点
     */
	public Result listCompanys() throws SolrServerException {
        // 参数
		String keyword = (String) getParameter("q");

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (StringUtils.isNotBlank(keyword)) {
            Page pager = new Page(1, 10);
            unit.setName(keyword);
            List<SysUnit> units = sysUnitService.listCompanys(unit, pager, getLoginUser(), isSupperAdmin(), isSiteAdmin());
            for (SysUnit unit : units) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("unitId", unit.getId().toString());
                map.put("unitName", unit.getName());
                list.add(map);
            }
        }
		JSONArray json = JSONArray.fromObject(list);
		return json(json);
	}

	public Result getUnitACompany() {
		SysSite sysSite = getSite();
		unit = sysUnitService.findUnit(sysSite.getId(), UnitType.site);
		if (unit != null) {
			map.put("unit", unit);
			map.put("success", true);
		} else {
			map.put("success", false);
		}
		return json(JSONObject.fromObject(map, JsonFilter.getIncludeConfig()));
	}

	/**
	 * 根据关键字查询公司和站点
	 */
	public Result listContractCompanys() throws SolrServerException {
		// 参数
		String keyword = (String) getParameter("q");

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (StringUtils.isNotBlank(keyword)) {
			Page pager = new Page(1, 10);
			unit.setName(keyword);
			List<SysUnit> units = sysUnitService.listContractCompanys(unit, pager, getLoginUser(), isSupperAdmin());
			for (SysUnit unit : units) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("unitId", unit.getId().toString());
				map.put("unitName", unit.getName());
				list.add(map);
			}
		}
		JSONArray json = JSONArray.fromObject(list);
		return json(json);
	}

	/**
	 * 新增、编辑供应商
	 * @author caiys
	 * @date 2015年11月7日 上午9:01:10
	 * @return
	 */
	public Result editSupplier() {
		fgDomain = propertiesManager.getString("FG_DOMAIN");
		unitId = (String) getParameter("unitId");
		if (StringUtils.isNotBlank(unitId)) {
			unit = sysUnitService.findSysUnitById(Long.valueOf(unitId));
			unitDetail = unit.getSysUnitDetail();
			user = sysUserService.findCompanyManager(Long.valueOf(unitId), UserType.CompanyManage);
			areaId = unit.getArea().getId();
			filePath = unitDetail.getLogoImgPath();
//			siteId = unit.getSysSite().getId();
		}
		return dispatch();
	}

	/**
	 * 编辑供应商自身信息
	 * @author caiys
	 * @date 2015年11月7日 上午9:01:10
	 * @return
	 */
	public Result editSupplierSelf() {
		fgDomain = propertiesManager.getString("FG_DOMAIN");
		unitId = String.valueOf(getCompanyUnit().getId());
		unit = sysUnitService.findSysUnitById(getCompanyUnit().getId());
		unitDetail = unit.getSysUnitDetail();
		UserType userType = UserType.CompanyManage;
		if (getLoginUser().getUserType() == UserType.AllSiteManage || getLoginUser().getUserType() == UserType.SiteManage) {
			userType = getLoginUser().getUserType();
		}
		user = sysUserService.findCompanyManager(getCompanyUnit().getId(), userType);
		if (unit.getArea() != null) {
			areaId = unit.getArea().getId();
		}
		if (unitDetail != null) {
			filePath = unitDetail.getLogoImgPath();
		}
//		siteId = unit.getSysSite().getId();
		return dispatch();
	}

	/**
	 * 查看供应商
	 * @author caiys
	 * @date 2015年11月7日 上午9:01:10
	 * @return
	 */
	public Result viewSupplier() {
		unitId = (String) getParameter("unitId");
		unit = sysUnitService.findSysUnitById(Long.valueOf(unitId));
		unitDetail = unit.getSysUnitDetail();
		user = sysUserService.findCompanyManager(Long.valueOf(unitId), UserType.CompanyManage);
		areaId = unit.getArea().getId();
		filePath = unitDetail.getLogoImgPath();
//		siteId = unit.getSysSite().getId();
		return dispatch();
	}
	
	/**
	 * 新增、编辑供应商
	 * @author caiys
	 * @date 2015年10月21日 下午3:36:41
	 * @return
	 */
	@AjaxCheck
	public Result
	saveSupplier() {
		Long uid = getLoginUser().getId();
		UserType userType = getLoginUser().getUserType();

		if (com.zuipin.util.StringUtils.isBlank(user.getAccount())
				|| com.zuipin.util.StringUtils.isBlank(com.zuipin.util.StringUtils.filterString(user.getAccount()))) {
			simpleResult(map, false, "保存错误，用户名异常。");
			return jsonResult(map);
		}

		if (unit.getId() == null) {		// 新增
			SysUser sysUser = sysUserService.findByMobile(user.getMobile());
			if (sysUser != null) {
				simpleResult(map, false, "保存错误，手机号已存在。");
				return jsonResult(map);
			}
			user.setCreatedTime(new Date());
			user.setPassword(Encryption.encry(Constants.password));
//			user.setMobile(user.getAccount());
			if (userType == UserType.AllSiteManage) {
				user.setUserType(UserType.SiteManage);
			} else {
				user.setUserType(UserType.CompanyManage);
				user.setParent(getLoginUser());
			}
			user.setStatus(UserStatus.activity);
//			user.setGrand(parentUser.getParent());
			user.setSuperior(user);
			user.setIsUse(true);
			user.setDelFlag(false);
			/*SysSite site = new SysSite();
			site.setId(siteId);*/
			user.setSysSite(getLoginUser().getSysSite());

			SysUnitImage sysUnitImage = new SysUnitImage();
//			TbArea area = new TbArea();
//			area.setId(areaId);
//			unit.setArea(area);
			unit.setSysSite(getLoginUser().getSysSite());
			unit.setDelFlag(false);
			unit.setStatus(0);	// 状态：-1待审核；0通过；1不通过；
			unit.setUnitType(UnitType.company);
			unit.setCreateTime(new Date());

			if (!unitQualifications.isEmpty()) {
				unit.setQualificationList(unitQualifications);
			}

			identityCode = doIdentityCode();

			unit.setIdentityCode(identityCode);

			TbArea area = tbAreaService.getArea(unitDetail.getCrtCity().getId());
			unitDetail.setCrtCity(area);
//			unitDetail.setLogoImgPath(filePath);
			unitDetail.setInivitorId(uid);
			unitDetail.setInivitorName(getLoginUser().getUserName());
		} else {	// 修改
			SysUser oldUser = sysUserService.load(user.getId());
			oldUser.setUserName(user.getUserName());
			oldUser.setAccount(user.getAccount());
			oldUser.setMobile(user.getMobile());
			oldUser.setEmail(user.getEmail());
			oldUser.setQqNo(user.getQqNo());
			oldUser.setUpdateTime(new Date());
			
			SysUnit oldUnit = sysUnitService.findUnitById(unit.getId());
			oldUnit.setName(unit.getName());
			oldUnit.setArea(unit.getArea());
			oldUnit.setAddress(unit.getAddress());
			oldUnit.getSysUnitImages().clear();

			if (!unitQualifications.isEmpty()) {
				oldUnit.setQualificationList(unitQualifications);
			}

			SysUnitDetail oldUnitDetail = oldUnit.getSysUnitDetail();
			oldUnitDetail.setLegalPerson(unitDetail.getLegalPerson());
			oldUnitDetail.setLegalIdCardNo(unitDetail.getLegalIdCardNo());
			oldUnitDetail.setSupplierType(unitDetail.getSupplierType());
			oldUnitDetail.setTelphone(unitDetail.getTelphone());
			oldUnitDetail.setContactName(unitDetail.getContactName());
			oldUnitDetail.setMobile(unitDetail.getMobile());
			oldUnitDetail.setMainBusiness(unitDetail.getMainBusiness());
			oldUnitDetail.setIntroduction(unitDetail.getIntroduction());
			oldUnitDetail.setCertificateType(unitDetail.getCertificateType());

			oldUnitDetail.setCrtnam(unitDetail.getCrtnam());
			oldUnitDetail.setCrtbnk(unitDetail.getCrtbnk());
			oldUnitDetail.setCrtacc(unitDetail.getCrtacc());

			TbArea area = tbAreaService.getArea(unitDetail.getCrtCity().getId());
			oldUnitDetail.setCrtCity(area);

			user = oldUser;
			unit = oldUnit;
			unitDetail = oldUnitDetail;
		}
		sysUserService.doInivited(user, unit, unitDetail, unitImages, null);
		map.put("id", unit.getId());
		simpleResult(map, true, "");
		return jsonResult(map);
	}

	/**
	 * 功能描述：创建唯一串码
	 * @return
	 */
	public String doIdentityCode() {

		String identityCode = "";

		while (true) {
			identityCode = NumberUtil.getRandomNo();
			if (sysUnitService.doCheckIdentityCode(identityCode)) {
				break;
			}
		}
		return identityCode;
	}





	/**
	 * 根据标识批量审核通过，多个逗号分隔
	 * @author caiys
	 * @date 2015年10月26日 下午8:07:05
	 * @return
	 */
	@AjaxCheck
	public Result doBatchAudit() {
		fgDomain = propertiesManager.getString("FG_DOMAIN");
		String ids = (String) getParameter("ids");
		if (StringUtils.isNotBlank(ids)) {
			sysUnitService.doBatchAudit(ids);
		}
		simpleResult(map, true, "");
		return jsonResult(map);
	}
	
	/**
	 * 根据标识批量审核不通过，多个逗号分隔
	 * @author caiys
	 * @date 2015年10月26日 下午8:07:05
	 * @return
	 */
	@AjaxCheck
	public Result doBatchFreeze() {
		String ids = (String) getParameter("ids");
		String reason = (String) getParameter("reason");
		if (StringUtils.isNotBlank(ids)) {
			sysUnitService.doBatchFreeze(ids, reason);
		}
		simpleResult(map, true, "");
		return jsonResult(map);
	}


	/**
	 * 根据标识批量审核不通过，多个逗号分隔
	 * @author caiys
	 * @date 2015年10月26日 下午8:07:05
	 * @return
	 */
	@AjaxCheck
	public Result doBatchActivate() {
		String ids = (String) getParameter("ids");
		if (StringUtils.isNotBlank(ids)) {
			sysUnitService.doBatchAudit(ids);
		}
		simpleResult(map, true, "");
		return jsonResult(map);
	}
	
	/**
	 * 根据标识批量删除，多个逗号分隔
	 * @author caiys
	 * @date 2015年10月26日 下午8:07:05
	 * @return
	 */
	@AjaxCheck
	public Result doBatchDel() {
		String ids = (String) getParameter("ids");
		if (StringUtils.isNotBlank(ids)) {
			sysUnitService.doBatchDel(ids);
		}
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
	public SysUnit getModel() {
		return unit;
	}

	public SysUnit getUnit() {
		return unit;
	}

	public void setUnit(SysUnit unit) {
		this.unit = unit;
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

	public String getFgDomain() {
		return fgDomain;
	}

	public void setFgDomain(String fgDomain) {
		this.fgDomain = fgDomain;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
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

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public List<SysUnitImage> getUnitImages() {
		return unitImages;
	}

	public void setUnitImages(List<SysUnitImage> unitImages) {
		this.unitImages = unitImages;
	}

	public List<UnitQualification> getUnitQualifications() {
		return unitQualifications;
	}

	public void setUnitQualifications(List<UnitQualification> unitQualifications) {
		this.unitQualifications = unitQualifications;
	}
}
