package com.hmlyinfo.app.soutu.plan.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanTag;
import com.hmlyinfo.app.soutu.plan.service.RecommendPlanService;
import com.hmlyinfo.app.soutu.plan.service.RecommendPlanTagService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api/pub/recommend")
public class RecommendPlanApi
{
	@Autowired
	private RecommendPlanService service;
	@Autowired
	private RecommendPlanTagService recommendPlanTagService;

	/**
	 * 查询推荐行程
	 * <ul>
	 * 	<li>必选:id{id}</li>
	 * 	<li>url:/api/pub/recommend/info</li>
	 * </ul>
	 *
	 * @return
	 */
	public @ResponseBody ActionResult info(String id)
	{
		Validate.notNull(id, ErrorCode.ERROR_50001);
		
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
	
	/**
	 * 查询推荐行程详情
	 * <ul>
	 * 	<li>必选:id{id}</li>
	 * 	<li>url:/api/pub/recommend/detail</li>
	 * </ul>
	 *
	 * @return
	 */
	public @ResponseBody ActionResult detail(String id)
	{
		Validate.notNull(id, ErrorCode.ERROR_50001);
		
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
	
	/**
	 * 查询行程计划
	 * <ul>
	 * 	<li>可选:cityId{cityId}</li>
	 * 	<li>可选:最低花费{costL}</li>
	 * 	<li>可选:最高花费{costU}</li>
	 * 	<li>可选:最少游玩时间{dayL}</li>
	 * 	<li>可选:最多游玩时间{dayU}</li>
	 * 	<li>url:/api/pub/recommend/list</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult list(HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		if (paramMap.get("tag") != null) {
			String str = paramMap.remove("tag").toString();
			String arr[] = str.split(",");
			paramMap.put("tags", arr);
		}
		if (paramMap.get("cities") != null) {
			String str = paramMap.remove(" ").toString();
			String arr[] = str.split(",");
			paramMap.put("cities", arr);
		}
		return ActionResult.createSuccess(service.listRecommendPlan(paramMap));
	}
	
	/**
	 * 推荐行程详情
	 * @return
	 */
	@RequestMapping("/detail")
	public @ResponseBody ActionResult detail(long id)
	{

		return ActionResult.createSuccess(service.detail(id));
	}
	
	/**
	 * 推荐行程小贴士
	 * @return
	 */
	@RequestMapping("/tips")
	public @ResponseBody ActionResult tips(long id)
	{
		return ActionResult.createSuccess(service.tipDetail(id));
	}

	/**
	 * 传入一串标签的字符串，查询出标签对应的所有数据集
	 * <ul>
	 * 	<li>可选:cityCode（城市编号）</li>
	 * 	<li>url:/api/pub/recommend/tag</li>
	 * </ul>
	 */
	@RequestMapping("/tag")
	@ResponseBody
	public ActionResult tags(Long cityCode) {
		if (cityCode == null || cityCode <= 0) {
			cityCode = 100000L;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityCode", cityCode);
		return ActionResult.createSuccess(recommendPlanTagService.list(params));
	}

	/**
	 * 根据城市编号和天数筛选出推荐行程当中包含的标签
	 */
	@RequestMapping("/dayTag")
	@ResponseBody
	public ActionResult tags(int cityCode, int day) {
		List<RecommendPlanTag> list = recommendPlanTagService.list(cityCode, day);
		return ActionResult.createSuccess(list);
	}

	/**
	 * 根据城市编号和天数和标签查询推荐行程
	 */
	@RequestMapping("/getByDayTag")
	@ResponseBody
	public ActionResult getByDayTag(int cityCode, int day, String tag) {
		return ActionResult.createSuccess(service.getRecommendPlan(cityCode, day, tag));
	}

	
	/**
	 * 
	 *  <li>必选:cityId（城市编号）</li>
	 * 	<li>url:/api/pub/recommend/listdayscount</li>
	 * @param request
	 * @return
	 */
	@RequestMapping("/listdayscount")
	@ResponseBody
	public ActionResult listDaysResult(HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("cityId"), ErrorCode.ERROR_50001, "标识{cityId}不能为空");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.listDaysCount(paramMap));
	}
	
	/**
	 * 
	 *  <li>必选:cityIds（城市编号）</li>
	 * 	<li>url:/api/pub/recommend/listplancount</li>
	 * @param request
	 * @return
	 */
	@RequestMapping("/listplancount")
	@ResponseBody
	public ActionResult listCount(HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("cityIds"), ErrorCode.ERROR_50001, "标识{cityIds}不能为空");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.countList(paramMap));
	}
	
	
	/**
	 * 
	 * 	<li>url:/api/pub/recommend/count</li>
	 * @param request
	 * @return
	 */
	@RequestMapping("/count")
	@ResponseBody
	public ActionResult countPlan(HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return service.countAsResult(paramMap);
	}
	
	
	@RequestMapping("/updatetraffic")
	@ResponseBody
	public ActionResult updateTraffic(HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("recommendPlanId"), ErrorCode.ERROR_50001, "推荐行程标识{recommendPlanId}不能为空");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		service.updateRecommendPlan(paramMap);
		return ActionResult.createSuccess();
	}
	
}
