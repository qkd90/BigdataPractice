package com.hmlyinfo.app.soutu.plan.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.domain.OptimizeOption;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.service.OptimizeService;
import com.hmlyinfo.app.soutu.plan.service.PlanDaysOperationService;
import com.hmlyinfo.app.soutu.plan.service.PlanDaysService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/auth/plan/days")
public class PlanDaysApi {
	@Autowired
	private PlanDaysService service;
	@Autowired
	private PlanDaysOperationService planDaysOperationService;
	@Autowired
	OptimizeService optimizeService;
	
	
	/**
	 * 查询行程天信息
	 * <ul>
	 * 	<li>必选:行程id{planId}</li>
	 * 	<li>必选:行程天id{dayId}</li>
	 * 	<li>url:/api/auth/plan/days/info</li>
	 * </ul>
	 *
	 * @return
	 */
	public @ResponseBody ActionResult info(String planId, String dayId)
	{
		Validate.notNull(planId, ErrorCode.ERROR_51001);
		Validate.notNull(dayId, ErrorCode.ERROR_51001);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("planId", planId);
		paramMap.put("id", dayId);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询行程天
	 * <ul>
	 * 	<li>必选:行程id{planId}</li>
	 * 	<li>可选:第几天{days}</li>
	 * 	<li>url:/api/auth/plan/days/search</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/search")
	public @ResponseBody ActionResult searchDay(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
 		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	
	/**
	 * 添加行程计划天
	 * <ul>
	 * 	<li>必选:行程id{planId}</li>
	 *  <li>必选:第若干天{days}</li>
	 * 	<li>url:/api/plan/days/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ActionResult addDays(final HttpServletRequest request, PlanDay planDays)
	{
		Validate.notNull(planDays.getPlanId(), ErrorCode.ERROR_51001);
		Validate.notNull(planDays.getDays(), ErrorCode.ERROR_51001);
		planDays = service.addDays(planDays);
		
		return ActionResult.createSuccess(planDays);
	}
	
	/**
	 * 删除某一天
	 * <ul>
	 * 	<li>必选:行程id{planId}</li>
	 *  <li>必选:第若干天{days}</li>
	 *  <li>url:/api/plan/del</li>
	 * </ul>
	 * @return 
	 */
	@RequestMapping("/del")
	@ResponseBody
	public ActionResult delDays(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);
		Validate.notNull(request.getParameter("days"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		planDaysOperationService.delDays(paramMap);
		return ActionResult.createSuccess();
	}
	
	/**
	 * 修改行程计划天
	 * <ul>
	 *  <li>必选:行程天id数组{days}</li>
	 * 	<li>url:/api/plan/days/update</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/update")
	public @ResponseBody ActionResult updateDays(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("days"), ErrorCode.ERROR_51001); 
		String[] orderNum = request.getParameter("days").toString().split(",");
		List<Long> daysIdList = new ArrayList<Long>(); 
		for(int i = 0; i < orderNum.length; ++i){
			daysIdList.add(Long.valueOf(orderNum[i]));
		}
		
		List<PlanDay> planDaysList = service.updateDays(daysIdList);
		return ActionResult.createSuccess(planDaysList);
	}
	
//	/**
//	 * 优化当天行程
//	 * <ul>
//	 *  <li>必选:行程id{planId}</li>
//	 *  <li>必选:第若干天{days}</li>
//	 *  <li>必选:交通方式{trans="driving", "transit", "walking"}
//	 * 	<li>url:/api/plan/days/update</li>
//	 * </ul>
//	 *
//	 * @return
//	 */
//	@RequestMapping("/optimize")
//	public @ResponseBody ActionResult optimize(final HttpServletRequest request)
//	{
//		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);
////		Validate.notNull(request.getParameter("days"), ErrorCode.ERROR_51001);
////		Validate.notNull(request.getParameter("trans"), ErrorCode.ERROR_51001);
//		//将request中的一串东西转成map
//		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
//
//		Map<String, Object> planTrip = service.optimize(paramMap);
//
//		return ActionResult.createSuccess(planTrip);
//	}

	/**
	 * 优化整个行程行程
	 * <ul>
	 *  <li>必选:行程id{planId}</li>
	 *  <li>必选:要分成多少天{days}</li>
	 *  <li>必选:交通方式{trans="driving", "transit", "walking"}
	 * 	<li>url:/api/plan/days/update</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/optimize")
	public @ResponseBody ActionResult optimizeAll(final HttpServletRequest request, OptimizeOption options)
	{
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);
		Validate.notNull(request.getParameter("days"), ErrorCode.ERROR_51001);
//		Validate.notNull(request.getParameter("trans"), ErrorCode.ERROR_51001);
		Long planId = Long.valueOf(request.getParameter("planId"));
		
		// TODO 为什么这么设置？
		options.setHour(-1);
		options.setType(-1);
		
		/**
		 * 加载行程所需的数据结构
		 */
		//加载行程计划下的所有节点
		List<PlanTrip> planTripList = service.listTrip(Collections.<String, Object>singletonMap("planId", planId)); 
		List<Long> planDayIdList = ListUtil.getIdList(planTripList, "planDaysId");
		List<PlanDay> planDayList = service.list(Collections.<String, Object>singletonMap("ids", planDayIdList));
		Map<Long, Integer> planDayMap = new HashMap<Long, Integer>();
		for (PlanDay planDay : planDayList) {
			planDayMap.put(planDay.getId(), planDay.getDays());
		}
		for (PlanTrip planTrip : planTripList) {
			planTrip.setDay(planDayMap.get(planTrip.getPlanDaysId()));
		}
		
		// 智能优化
		List<Map<String, Object>> planTrip = service.optimize(planTripList, options);

		return ActionResult.createSuccess(planTrip);
	}


	/**
	 * 更新planDay耗时和公里数
	 * <ul>
	 *  <li>必选:行程天id{id}</li>
	 *  <li>必选:耗时{spendTime}</li>
	 *  <li>必选:公里{kilometre}</li>
	 * 	<li>url:/api/auth/plan/days/addspendtime</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/addspendtime")
	public @ResponseBody ActionResult addSpendTime(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
		Validate.notNull(request.getParameter("spendTime"), ErrorCode.ERROR_51001);
		Validate.notNull(request.getParameter("kilometre"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		service.addSpendTime(paramMap);
		return ActionResult.createSuccess();
	}
	
}