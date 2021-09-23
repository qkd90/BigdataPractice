package com.hmlyinfo.app.soutu.plan.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.service.PlanDaysService;
import com.hmlyinfo.app.soutu.plan.service.PlanService;
import com.hmlyinfo.app.soutu.plan.service.PlanTripService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/pub/plan/trip")
public class PlanTripPubApi
{
	@Autowired
	private PlanTripService service;
	@Autowired
	private PlanDaysService planDayservice;
	@Autowired
	private PlanService planService;
	
	
	/**
	 * 查询当天所有行程节点信息
	 * <ul>
	 * 	<li>必选:行程id{planId}</li>
	 *  <li>必选:第若干天{days}</li>
	 * 	<li>url:/api/plan/days/list</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		String days = request.getParameter("days");
		String planDaysId = request.getParameter("planDaysId");
		
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);
		Validate.isTrue(StringUtils.isNotBlank(days) || StringUtils.isNotBlank(planDaysId), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);  
		
		List<PlanTrip> planTripList = planDayservice.listTrip(paramMap);
		
		return ActionResult.createSuccess(planTripList);		
	}	
	
	/**
	 * 查询行程景点列表
	 * <ul>
	 * 	<li>必选:行程id{planId}</li>
	 *  <li>可选:行程天id{planDaysId}</li>
	 * 	<li>url:/api/pub/plan/trip/listScenic</li>
	 * </ul>
	 *
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/listScenic")
	public @ResponseBody ActionResult listScenic(String planId, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		Validate.notNull(planId, ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.listScenic(paramMap));
	}
	
	
	/**
	 * 调用百度API计算行程花费并插入
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/inserttrafficcost")
	public @ResponseBody ActionResult insertCost(HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);
		long planId = Long.parseLong(request.getParameter("planId").toString());
		service.insertTrafficCost(planId);
		return ActionResult.createSuccess();
	}
	
}
