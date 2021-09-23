package com.hmlyinfo.app.soutu.plan.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.service.CreatePdfService;
import com.hmlyinfo.app.soutu.plan.service.PlanEditService;
import com.hmlyinfo.app.soutu.plan.service.PlanOperationService;
import com.hmlyinfo.app.soutu.plan.service.PlanService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/auth/plan")
public class PlanApi {
	@Autowired
	private PlanService service;
	@Autowired
	private CreatePdfService createPdfService;
	@Autowired
	private PlanEditService planEditService;
	@Autowired
	private PlanOperationService planOperationService;
	private ObjectMapper om = new ObjectMapper();

	/**
	 * 创建行程计划
	 * <ul>
	 * <li>必选:景点id{scenicId}</li>
	 * <li>url:/api/auth/plan/create</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/create")
	public @ResponseBody
	ActionResult creatPlan(final HttpServletRequest request, Plan plan) {
		Validate.notNull(plan.getScenicId(), ErrorCode.ERROR_51001); // 请求景点编号取值非空验证

		// 返回值
		return ActionResult.createSuccess(service.creatPlan(plan));
	}

	/**
	 * 更新行程计划
	 * <ul>
	 * <li>必选:景点id{scenicId}</li>
	 * <li>url:/api/auth/plan/update</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ActionResult updatePlan(final HttpServletRequest request, Plan plan) {
		Validate.notNull(plan.getId(), ErrorCode.ERROR_51001); // 请求景点编号取值非空验证

		// 返回值
		service.update(plan);
		return ActionResult.createSuccess();
	}

	/**
	 * 更新行程出发时间
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>url:/api/auth/plan/updatetime/li>
	 * </ul>
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/updatetime")
	public @ResponseBody
	ActionResult updateTime(final HttpServletRequest request) throws ParseException {

		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);

		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(service.updateTime(paramMap));
	}

	/**
	 * 查询小贴士内容
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>url:/api/auth/plan/tips/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	/*
	 * @RequestMapping("/tips/info") public @ResponseBody ActionResult
	 * searchTips(final HttpServletRequest request){
	 * 
	 * Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);
	 * 
	 * Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
	 * 
	 * return ActionResult.createSuccess(service.getTips(paramMap)); }
	 */
	/**
	 * 修改小贴士内容
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>必选:内容{tipsContent}</li>
	 * <li>url:/api/auth/plan/updatetips</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/updatetips")
	public @ResponseBody
	ActionResult updateTips(final HttpServletRequest request) {

		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);

		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(service.updateTips(paramMap));
	}

	/**
	 * 修改行程名
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>必选:行程名{planName}</li>
	 * <li>url:/api/auth/plan/updatename</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/updatename")
	public @ResponseBody
	ActionResult updatePlanName(final HttpServletRequest request) {

		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);

		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(service.updatePlanName(paramMap));
	}

	/**
	 * 查询我的收藏行程
	 * <ul>
	 * <li>必选:用户id{userId}</li>
	 * <li>url:/api/auth/plan/collection/list</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/collection/list")
	@ResponseBody
	public ActionResult collectionPlan(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("userId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.getCollection(paramMap));
	}

	/**
	 * 查询我的收藏数量
	 * <ul>
	 * <li>必选:用户id{userId}</li>
	 * <li>url:/api/auth/plan/collection/count</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/collection/count")
	@ResponseBody
	public ActionResult countCollection(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("userId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return service.countCollection(paramMap);
	}

	/**
	 * 删除我的收藏行程
	 * <ul>
	 * <li>必选:收藏id{id}</li>
	 * <li>url:/api/auth/plan/collection/del</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/collection/del")
	public @ResponseBody
	ActionResult delCollectionPlan(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		service.delCollection(paramMap);
		return ActionResult.createSuccess();
	}

	/**
	 * 复制一份引用的行程到编辑行程栏
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>url:/api/auth/plan/quote</li>
	 * </ul>
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/quote")
	@ResponseBody
	public ActionResult quotePlan(final HttpServletRequest request) throws ParseException {
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(Collections.singletonMap("id", planOperationService.quotePlan(paramMap)));
	}

	/**
	 * 添加所有景点的camcount数
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>url:/api/auth/plan/addsceniccamcount</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/addsceniccamcount")
	public @ResponseBody
	ActionResult addCamCount(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		service.addCamCount(paramMap);
		return ActionResult.createSuccess();
	}

	/**
	 * 收藏数+1
	 * <ul>
	 * <li>必选:行程Id{id}</li>
	 * <li>url:/api/auth/plan/collection/add</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/collection/add")
	public @ResponseBody
	ActionResult addCollectNum(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		service.addCollect(paramMap);
		return ActionResult.createSuccess();
	}

	/**
	 * 删除行程
	 * <ul>
	 * <li>必选：行程Id{id}</li>
	 * </ul>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ActionResult deletePlan(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
		service.deletePlan(Long.valueOf(request.getParameter("id")));
		return ActionResult.createSuccess();
	}

	/**
	 * 通过用户id获取planList
	 */
	@RequestMapping("/myplan")
	@ResponseBody
	public ActionResult myPlan(final HttpServletRequest request) {
		Map<String, Object> params = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(service.listMyPlan(params));
	}

	/**
	 * 修改行程状态 add by 林则金
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>必选:行程状态{status}</li>
	 * <li>url:/api/auth/plan/updatestatus</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/updatestatus")
	public @ResponseBody
	ActionResult updatePlanStatus(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);
		Validate.notNull(request.getParameter("status"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.updatePlanStatus(paramMap));
	}
	
	
	
	/**
	 * 用户已经去过的所有城市
	 * 
	 * @return
	 */
	@RequestMapping("/arrivedcity")
	public @ResponseBody ActionResult arrivedCity()
	{
		return ActionResult.createSuccess(service.arrivedCityList());
	}
	
	
	/**
	 * <li>必选:行程数据planData{planData}</li>
	 * <li>url:/api/auth/plan/editplan</li>
	 * @param request
	 * @return
	 */
	@RequestMapping("/editplan")
	public @ResponseBody ActionResult editPlan(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("planData"), ErrorCode.ERROR_51001, "参数有误");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(planEditService.editPlan(paramMap));
	}
	
	
	/**
	 * 为行程插入用户
	 * <li>必选:行程planId{planId}</li>
	 * <li>url:/api/auth/plan/insertuser</li>
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertUser")
	public @ResponseBody ActionResult insertUser(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001, "参数有误");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		service.insertUser(paramMap);
		return ActionResult.createSuccess();
	}
	
	/**
	 * 把行程信息打印在pdf上
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/planpdf")
    public @ResponseBody ActionResult getPlanPdf(final HttpServletRequest request) throws Exception
    {
	    Validate.notNull(request.getParameter("url"), ErrorCode.ERROR_51001, "url不能为空");
        Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001, "planId不能为空");
        String url = request.getParameter("url").toString();
        long planId = Long.parseLong(request.getParameter("planId").toString());
        return ActionResult.createSuccess(createPdfService.createPdf(url, planId));
    }
	
	/**
     * 从推荐行程
     * 
     * @param request
     * @return
     */
    @RequestMapping("/insertbyrecplan")
    public @ResponseBody ActionResult insertByRecplan(final HttpServletRequest request)
    {
        Validate.notNull(request.getParameter("tripList"), ErrorCode.ERROR_51001, "tripList参数有误");
        Validate.notNull(request.getParameter("days"), ErrorCode.ERROR_51001, "days参数有误");
        Validate.notNull(request.getParameter("cityCode"), ErrorCode.ERROR_51001, "cityCode参数有误");
        return ActionResult.createSuccess(planEditService.editFromRecplan(request));
    }
    
    /**
     * 创建行程完成后的推送
     * @param request
     * @return
     */
    @RequestMapping("/pushMsg")
    public @ResponseBody ActionResult createPlanPushMsg(final HttpServletRequest request)
    {
        Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001, "tripList参数有误");
        service.pushCreatePlanMsg(Long.valueOf(request.getParameter("planId")));
        
        return null;
    }
    
    /**
	 * 更新行程酒店
	 * V2.7版本 PC端酒店编辑页面只保存酒店
	 * jsonData 的结构为 {planId : "{id}" planDayList : [{days : {days}, hotelId : "{hotelId, hotelCost}"}, {...}]}
	 * @author shiqingju@hmlyinfo.com
	 * @date 2015-11-09
	 * 
	 * <li>必选:行程jsonData{jsonData}</li>
	 * <li>url:/api/auth/plan/updatePlanhotel</li>
	 * @param request
	 * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
	 */
    @RequestMapping("/updatePlanhotel")
    public @ResponseBody ActionResult updatePlanhotel(String jsonData) throws JsonParseException, JsonMappingException, IOException
    {
    	Validate.notNull(jsonData, ErrorCode.ERROR_51001, "jsonData不能为空");
    	Plan plan = om.readValue(jsonData, Plan.class);
    	
    	service.updatePlanhotel(plan);
    	
    	return ActionResult.createSuccess();
    }

}
