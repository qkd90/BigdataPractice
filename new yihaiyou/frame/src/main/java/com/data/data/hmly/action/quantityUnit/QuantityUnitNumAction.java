package com.data.data.hmly.action.quantityUnit;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.QuantityUnitNumService;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.entity.QuantityUnitNum;
import com.data.data.hmly.service.entity.SysUnit;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuantityUnitNumAction extends FrameBaseAction {





	@Resource
	private SysUnitService sysUnitService;

	@Resource
	private QuantityUnitNumService quantityUnitNumService;


	private Map<String, Object> map = new HashMap<String, Object>();

	private String identityCode;

	private QuantityUnitNum quantityUnitNum;

	private Integer page = 1;
	private Integer rows = 10;


	/**
	 * 功能描述：编辑拱量信息
	 * @return
	 */
	public Result editQuantityUnitNum() {

		if (quantityUnitNum == null) {
			simpleResult(map, false, "操作失败！");
			return jsonResult(map);
		}

		if (quantityUnitNum.getId() == null) {
			simpleResult(map, false, "操作失败！");
			return jsonResult(map);
		}

		quantityUnitNumService.update(quantityUnitNum);

		simpleResult(map, true, "操作成功！");
		return jsonResult(map);
	}

	/**
	 * 功能描述：删除记录
	 * @return
	 */
	public Result delQuantityUnitNum() {

		String ids = (String) getParameter("idStr");

		if (!StringUtils.isNotBlank(ids)) {
			simpleResult(map, false, "需要删除的记录，后台接收失败！");
			return jsonResult(map);
		}

		String[] idsArr = ids.split(",");
		for (String idStr : idsArr) {
			quantityUnitNumService.delQuantityUnitNumById(Long.parseLong(idStr));
		}
		simpleResult(map, true, "删除成功！");
		return jsonResult(map);
	}

	/**
	 * 功能描述：查询公司拱量关系列表
	 * @return
	 */
	public Result datagrid() {
		Page pageInfo = new Page(page, rows);

		String dealurId = (String) getParameter("dealurId");
		String dealurUnitName = (String) getParameter("dealurUnitName");
		String supplerUnitName = (String) getParameter("supplerUnitName");
		String conditionNumStart = (String) getParameter("conditionNumStart");
		String conditionNumEnd = (String) getParameter("conditionNumEnd");

		quantityUnitNum = new QuantityUnitNum();

		if (StringUtils.isNotBlank(dealurUnitName)) {
			quantityUnitNum.setDealerUnitName(dealurUnitName);
		}
		if (StringUtils.isNotBlank(supplerUnitName)) {
			quantityUnitNum.setSuplerUnitName(supplerUnitName);
		}
		if (StringUtils.isNotBlank(conditionNumStart)) {
			quantityUnitNum.setConditionNumStart(Integer.parseInt(conditionNumStart));
		}
		if (StringUtils.isNotBlank(conditionNumEnd)) {
			quantityUnitNum.setConditionNumEnd(Integer.parseInt(conditionNumEnd));
		}

		List<QuantityUnitNum> quantityUnitNumList =
				quantityUnitNumService.getQuantityUnitNumList(quantityUnitNum, pageInfo, getLoginUser(), getCompanyUnit(), isSiteAdmin(), isSupperAdmin());

		JsonConfig jsonConfig = JsonFilter.getIncludeConfig("");

		return datagrid(quantityUnitNumList, quantityUnitNumList.size(), jsonConfig);
	}

	/**
	 * 功能描述：添加公司拱量关系
	 */
	public Result addDealurUnit() {
		String dealurId = (String) getParameter("dealurId");
		String conditionNums = (String) getParameter("conditionNums");

		if (!StringUtils.isNotBlank(dealurId)) {
			simpleResult(map, false, "被拱量公司不能为空！");
			return jsonResult(map);
		}
		if (!StringUtils.isNotBlank(conditionNums)) {
			simpleResult(map, false, "拱量条件不能为空！");
			return jsonResult(map);
		}

		SysUnit supplierUnit = sysUnitService.findSysUnitById(getCompanyUnit().getId());
		SysUnit dealurUnit = sysUnitService.findSysUnitById(Long.parseLong(dealurId));


		quantityUnitNum = new QuantityUnitNum();

		quantityUnitNum.setDealerUnit(dealurUnit);

		quantityUnitNum.setSuplerUnit(supplierUnit);

		quantityUnitNum.setConditionNum(Integer.parseInt(conditionNums));

		quantityUnitNum.setUser(getLoginUser());


		quantityUnitNumService.save(quantityUnitNum);

		simpleResult(map, true, "添加成功！");

		return jsonResult(map);


	}

	/**
	 * 功能描述：查询公司串码
	 *          判断串码对应公司是否存在
	 *          判断串码公司是否已经添加
	 * @return
	 */
	public Result searchUnitByIdentityCode() {


		if (!StringUtils.isNotBlank(identityCode)) {
			simpleResult(map, false, "公司串码为空！");
			return jsonResult(map);
		}

		SysUnit sysUnit = sysUnitService.findSysUnitById(getCompanyUnit().getId());

		String loginIdentityCode = sysUnit.getIdentityCode();

		if (identityCode.equals(loginIdentityCode)) {
			simpleResult(map, false, "此串码为本公司串码，无法添加！");
			return jsonResult(map);
		}

		SysUnit dealerUnit = sysUnitService.findUnitByIdentityCode(identityCode);

		if (dealerUnit == null) {
			simpleResult(map, false, "此串码对应的公司不存在，请重新输入！");
			return jsonResult(map);
		}

		if (quantityUnitNumService.doCheckIdentityCodeExisted(sysUnit, dealerUnit)) {
			simpleResult(map, false, "此串码对应的公司已添加，无需重复添加！");
			return jsonResult(map);
		}

		map.put("dealerUnitId", dealerUnit.getId());
		map.put("dealerUnitName", dealerUnit.getName());
		map.put("dealerUnitIdectityCode", dealerUnit.getIdentityCode());

		simpleResult(map, true, "");
		return jsonResult(map);
	}

	/**
	 * 公司拱量维护页面
	 * @return
	 */
	public Result quantityUnitManage() {
		return dispatch();
	}

	/**
	 * 添加公司拱量
	 * @return
	 */
	public Result addQuantityUnit() {
		return dispatch();
	}


	//以下为setter和getter方法

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public QuantityUnitNum getQuantityUnitNum() {
		return quantityUnitNum;
	}

	public void setQuantityUnitNum(QuantityUnitNum quantityUnitNum) {
		this.quantityUnitNum = quantityUnitNum;
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
}



