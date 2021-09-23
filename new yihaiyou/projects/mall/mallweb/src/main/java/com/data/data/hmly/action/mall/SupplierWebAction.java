package com.data.data.hmly.action.mall;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.entity.*;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.util.Encryption;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.Constants;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import javax.annotation.Resource;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by guoshijie on 2015/10/21.
 */
public class SupplierWebAction extends MallAction {
	private static final long serialVersionUID = 8179390978335932440L;
	@Resource
	private TbAreaService tbAreaService;
	@Resource
	private UserService userService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private LineService lineService;
	@Resource
	private SysUnitService sysUnitService;
	private SysUser user = new SysUser();
	private SysUnit unit = new SysUnit();
	private SysUnitDetail unitDetail = new SysUnitDetail();
	private Long areaId;
	private String filePath;

	// 返回数据
	Map<String, Object> map = new HashMap<String, Object>();
	private String uid;
	private String siteid;
	private List<TbArea> areas;
	private String unitId; // 成功标志
	private String supplierName, supplierType;
	private Long city;

	private String cityName;
	private String conditionStr;
	private int pageIndex = 0;
	private String lineName;
	private String productAttr;
	private String lineDay;
	private String cost;
	private String mincost;
	private String maxcost;

	/**
	 * 获取省、市列表
	 * 
	 * @author caiys
	 * @date 2015年10月27日 下午1:59:15
	 * @return
	 */
	public Result listArea() {
		String fatherId = (String) getParameter("fatherId");
		if (StringUtils.isNotBlank(fatherId)) {
			areas = tbAreaService.getCityByPro(fatherId, 2);
		} else {
			areas = tbAreaService.getCityByPro("100000", 1);
		}
		JsonConfig config = JsonFilter.getIncludeConfig();
		JSONArray json = JSONArray.fromObject(areas, config);
		return json(json);
	}

	/**
	 * 转向入住页面
	 * 
	 * @author caiys
	 * @date 2015年11月3日 上午11:44:58
	 * @return
	 */
	public Result inivited() {
		uid = (String) getParameter("uid");
		areas = tbAreaService.getCityByPro("100000", 1);
		unitId = (String) getParameter("unitId");
		return dispatch();
	}

	/**
	 * 提交入住信息
	 * 
	 * @author caiys
	 * @date 2015年10月21日 下午3:36:41
	 * @return
	 */
	@AjaxCheck
	public Result doInivited() {
		filePath = (String) getParameter("filePath");
		uid = (String) getParameter("uid");
		SysUser parentUser = null;
		if (StringUtils.isBlank(uid)) {	// 如果是空，默认为一级入驻，同站点管理员添加流程
			SysSite sysSite = (SysSite) getSession().getAttribute(UserConstans.SYSTEM_SITE_INFORMATION);
			parentUser = sysUserService.findCompanyManager(sysSite.getId(), UserType.SiteManage);
		} else {
			parentUser = sysUserService.load(Long.valueOf(uid));
		}
		user.setCreatedTime(new Date());
		user.setPassword(Encryption.encry(Constants.password));
		user.setMobile(user.getAccount());
		user.setUserType(UserType.CompanyManage);
		user.setStatus(UserStatus.lock);
		List<UserRelation> userRelations = new ArrayList<UserRelation>();
		if (parentUser.getUserType() == UserType.SiteManage) {
			user.setSuperior(user);
		} else {
			user.setParent(parentUser);
			user.setGrand(parentUser.getParent());
			user.setSuperior(parentUser.getSuperior());
			// 设置用户关联关系
			if (parentUser != null) {
				UserRelation userRelation = new UserRelation();
				userRelation.setCreatedTime(new Date());
				userRelation.setParentUser(parentUser);
				userRelation.setChildUser(user);
				userRelation.setLevel(1);
				userRelations.add(userRelation);
			}
			if (parentUser.getParent() != null) {
				UserRelation userRelation = new UserRelation();
				userRelation.setCreatedTime(new Date());
				userRelation.setParentUser(parentUser.getParent());
				userRelation.setChildUser(user);
				userRelation.setLevel(2);
				userRelations.add(userRelation);
			}
			if (parentUser.getGrand() != null) {
				UserRelation userRelation = new UserRelation();
				userRelation.setCreatedTime(new Date());
				userRelation.setParentUser(parentUser.getGrand());
				userRelation.setChildUser(user);
				userRelation.setLevel(3);
				userRelations.add(userRelation);
			}
		}
		user.setIsUse(false);
		user.setDelFlag(false);
		user.setSysSite(parentUser.getSysSite());

		TbArea area = new TbArea();
		area.setId(areaId);
		unit.setArea(area);
		unit.setSysSite(parentUser.getSysSite());
		unit.setDelFlag(false);
		unit.setStatus(-1); // 状态：-1待审核；0通过；1不通过；
		unit.setUnitType(UnitType.company);
		unit.setCreateTime(new Date());

		unitDetail.setLogoImgPath(filePath);
		unitDetail.setInivitorId(parentUser.getId());
		unitDetail.setInivitorName(parentUser.getUserName());
		unitDetail.setContactName(user.getUserName());
		unitDetail.setMobile(user.getAccount());

		sysUserService.doInivited(user, unit, unitDetail, userRelations);
		// getSession().setAttribute("result", result);
		// getSession().setAttribute("uid", uid);
		// getSession().setAttribute("filePath", filePath);
		return redirect("/mall/supplier/inivited.jhtml?unitId="+unit.getId()+"&uid=" + StringUtils.defaultString(uid));
	}
	

	/**
	 * 验证手机唯一性
	 * @author caiys
	 * @date 2015年11月13日 下午2:14:59
	 * @return
	 */
	public Result validateMobile() {
		String mobile = (String) getParameter("mobile");
//		String neUserId = (String) getParameter("neUserId");
//		SysUser user = sysUserService.findUserByAccount(mobile, null);
//		if (user != null) {
//			return text("该手机号已注册，请重新输入");
//		}
		return text("valid");
	}

	public Result list() throws UnsupportedEncodingException {
		getSupplierSearcher();
		Page page = new Page(pageIndex + 1, 10);
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		makeSearchCondition(c);
		List<SysUnit> units = sysUnitService.findSiteCompany(getMallConfig().getSiteId(), page, c);
		setAttribute("units", units);
		setAttribute("pageIndex", pageIndex);
		setAttribute("totalCount", page.getTotalCount());

		return dispatch();
	}

	public Result lines() throws UnsupportedEncodingException {
		String uid = (String) getParameter("id");
		if (uid == null) {
			return redirect("/");
		}
		SysUnit unit = sysUnitService.findUnitById(Long.parseLong(uid));
		setAttribute("unit", unit);
		getLineSearcher();
		Page page = new Page(pageIndex + 1, 10);
		makeSearchCondition();
		// if (StringUtils.isBlank(conditionStr)) {
		// }
		conditionStr = String.format("%s AND supplierId:%s", conditionStr, uid);
		QueryResponse results = lineService.findLine(page, conditionStr, getMallConfig().getSiteId());
		SolrDocumentList docs = results.getResults();
		setAttribute("lines", docs);
		setAttribute("pageIndex", pageIndex);
		setAttribute("totalCount", docs.getNumFound());
		return dispatch();
	}

	public void getLineSearcher() {
		setAttribute(MallConstants.LINE_SEARCHER_KEY,
				FileUtil.loadHTML(String.format(MallConstants.LINE_SEARCHER, getMallConfig().getSiteId())));
	}

	private void makeSearchCondition() throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(cityName)) {
			sb.append(String.format(" AND crossCitys : \"%s\"", new String(cityName.getBytes("iso-8859-1"), "UTF-8")));
		}
		if (StringUtils.isNotBlank(this.cost)) {
			sb.append(String.format(" AND disCountPrice : [%s]", this.cost));
		}
		if (StringUtils.isNotBlank(this.maxcost)) {
			sb.append(String.format(" AND disCountPrice : [%s TO %s]", this.mincost, this.maxcost));
		}
		if (StringUtils.isNotBlank(this.lineDay)) {
			lineDay = new String(lineDay.getBytes("iso-8859-1"), "UTF-8");
			if (lineDay.contains("天")) {
				sb.append(String.format(" AND lineDay : %s", this.lineDay.replace("天", "")));
			} else if (lineDay.contains("以上")) {
				sb.append(String.format(" AND lineDay : [%s TO 999]", this.lineDay.replace("以上", "")));

			}
		}
		if (StringUtils.isNotBlank(this.lineName)) {
			sb.append(String.format(" AND name : %s", new String(lineName.getBytes("iso-8859-1"), "UTF-8")));
		}
		if (StringUtils.isNotBlank(this.productAttr)) {
			sb.append(String.format(" AND productAttr : %s", new String(productAttr.getBytes("iso-8859-1"), "UTF-8")));
		}
		if (StringUtils.isNotBlank(this.supplierName)) {
			sb.append(String.format(" AND supplierName : %s", new String(supplierName.getBytes("iso-8859-1"), "UTF-8")));
		}
		this.conditionStr = sb.toString();
	}

	public Result searchSupplier() throws UnsupportedEncodingException {
		Page page = new Page(pageIndex + 1, 10);
		Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
		makeSearchCondition(c);
		List<SysUnit> units = sysUnitService.findSiteCompany(getMallConfig().getSiteId(), page, c);
		JSONObject o = new JSONObject();
		JSONArray unitsJson = new JSONArray();
		for (SysUnit sysUnit : units) {
			JSONObject unit = new JSONObject();
			unit.put("name", sysUnit.getName());
			unit.put("id", sysUnit.getId());
			JSONObject detail = new JSONObject();
			detail.put("logoImgPath", sysUnit.getSysUnitDetail().getLogoImgPath());
			detail.put("mainBusiness", sysUnit.getSysUnitDetail().getMainBusiness());
			unit.put("sysUnitDetail", detail);
			JSONObject area = new JSONObject();
			JSONObject areaFather = new JSONObject();
			areaFather.put("name", sysUnit.getArea().getFather().getName());
			area.put("name", sysUnit.getArea().getName());
			area.put("father", areaFather);
			unit.put("area", area);
			unitsJson.add(unit);
		}
		o.put("units", unitsJson);
		o.put("page", page);
		return json(o);
	}

	public void getSupplierSearcher() {
		setAttribute(MallConstants.SUPPLIER_SEARCH_KEY,
				FileUtil.loadHTML(String.format(MallConstants.SUPPLIER_SEARCH, getMallConfig().getSiteId())));
	}

	private void makeSearchCondition(Criteria<SysUnit> c) throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(supplierName)) {
			c.like("name", new String(supplierName.getBytes("iso-8859-1"), "UTF-8"));
		}
		DetachedCriteria detail = c.createCriteria("sysUnitDetail");
		detail.add(Restrictions.isNull("sysUnitDetail.scenicid"));
		if (StringUtils.isNotBlank(supplierType)) {
			detail.add(Restrictions.eq("sysUnitDetail.supplierType", SupplierType.findByName(supplierType)));
		}
		if (city != null) {
			c.eq("area.id", city);
		}
	}

	public Result home() {
		String id = (String) getParameter("id");
		if (id == null) {
			return redirect("/");
		}

		setAttribute(MallConstants.SUPPLIER_HOME_KEY, FileUtil.loadHTML(MallConstants.SUPPLIER_HOME + id));
		return dispatch();
	}

	public Result about() {
		String id = (String) getParameter("id");
		if (id == null) {
			return redirect("/");
		}

		setAttribute(MallConstants.SUPPLIER_ABOUT_KEY, FileUtil.loadHTML(MallConstants.SUPPLIER_ABOUT + id));
		return dispatch();
	}

	public Result contact() {
		String id = (String) getParameter("id");
		if (id == null) {
			return redirect("/");
		}

		setAttribute(MallConstants.SUPPLIER_CONTACT_KEY, FileUtil.loadHTML(MallConstants.SUPPLIER_CONTACT + id));
		return dispatch();
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public List<TbArea> getAreas() {
		return areas;
	}

	public void setAreas(List<TbArea> areas) {
		this.areas = areas;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

}
