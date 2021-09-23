package com.hmlyinfo.app.soutu.plan.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.service.AddScenicService;
import com.hmlyinfo.app.soutu.plan.service.BaiduDisService;
import com.hmlyinfo.app.soutu.plan.service.CreatePdfService;
import com.hmlyinfo.app.soutu.plan.service.PlanDetailService;
import com.hmlyinfo.app.soutu.plan.service.PlanEditService;
import com.hmlyinfo.app.soutu.plan.service.PlanInvitationService;
import com.hmlyinfo.app.soutu.plan.service.PlanOperationService;
import com.hmlyinfo.app.soutu.plan.service.PlanService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/pub/plan")
public class PlanPubApi {

	@Autowired
	private PlanService			planService;
	@Autowired
	PlanDetailService			planDetailService;
	@Autowired
	private BaiduDisService		baiduService;
	@Autowired
	private AddScenicService	addScenicService;
	@Autowired
	private PlanEditService		planEditService;
	@Autowired
	private CreatePdfService	createPdfService;
	@Autowired
	private PlanOperationService planOperationService;
	@Autowired
	private PlanInvitationService planInvitationService;
	private ObjectMapper om = new ObjectMapper();
	
	/**
	 * 获取用于编辑的行程信息
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>url:/api/auth/plan/info</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/info")
	public @ResponseBody ActionResult info(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);

		// 返回该行程ID的对应行程和天数
		return ActionResult.createSuccess(planService.infoTrip(Long.valueOf(request.getParameter("planId"))));
	}

	/**
	 * 获取用于编辑的行程信息
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>url:/api/auth/plan/info</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/summaryinfo")
	public @ResponseBody ActionResult summaryInfo(String planId) {
		Validate.notNull(planId, ErrorCode.ERROR_51001);

		// 返回该行程ID的对应行程和天数
		return ActionResult.createSuccess(planService.info(Long.valueOf(planId)));
	}

	/**
	 * 根据条件列出行程列表
	 * <ul>
	 * <li>url:/api/pub/plan/list</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ActionResult list(final HttpServletRequest request) {
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(planService.list(params));
	}

	/**
	 * 根据条件计算行程数量
	 * <ul>
	 * <li>url:/api/pub/plan/count</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/count")
	@ResponseBody
	public ActionResult count(final HttpServletRequest request) {
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		params.put("status", Plan.PLAN_STATUS_TRUE);
		return planService.countAsResult(params);
	}

	/**
	 * 系统列出热门行程
	 * <ul>
	 * <li>可选:条数{pageSize}</li>
	 * <li>url:/api/pub/plan/hot</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/hot")
	@ResponseBody
	public ActionResult hotPlan(final HttpServletRequest request) {

		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		paramMap.put("noteFlag", true);
		return ActionResult.createSuccess(planService.getHotPlan(paramMap));
	}

	/**
	 * 系统查询计划的行程信息
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>url:/api/pub/plan/listTrips</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/listTrips")
	public @ResponseBody ActionResult listPlanTrips(long planId) {
		Validate.notNull(planId, ErrorCode.ERROR_51001);

		return ActionResult.createSuccess(planService.listPlanTrips(planId));
	}

	/**
	 * 浏览数+1接口
	 * <ul>
	 * <li>必选:行程Id{id}</li>
	 * <li>url:/api/pub/plan/viewnum/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/viewnum/add")
	public @ResponseBody ActionResult addViewNum(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		paramMap.put("viewNum", true);
		planService.addAllNum(paramMap);
		return ActionResult.createSuccess();
	}

	/**
	 * 浏览数+1接口
	 * <ul>
	 * <li>必选:行程Id{id}</li>
	 * <li>url:/api/pub/plan/quotenum/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/quotenum/add")
	public @ResponseBody ActionResult addQuoateNum(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		paramMap.put("quoteNum", true);
		planService.addAllNum(paramMap);
		return ActionResult.createSuccess();
	}

	/**
	 * 分享数+1接口
	 * <ul>
	 * <li>必选:行程Id{id}</li>
	 * <li>url:/api/pub/plan/sharenum/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/sharenum/add")
	public @ResponseBody ActionResult addShareNum(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		paramMap.put("shareNum", true);
		planService.addAllNum(paramMap);
		return ActionResult.createSuccess();
	}

	/**
	 * 
	 * 景点详情获取相关行程
	 * <ul>
	 * <li>必选：景点id{scenicId}</li>
	 * <li>url:/api/pub/plan/relate</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/relate")
	public @ResponseBody ActionResult relatedPlan(final HttpServletRequest request) {

		Validate.notNull(request.getParameter("scenicId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(planService.getRelatePlan(paramMap));
	}

	/**
	 *
	 * 景点详情获取相关行程总数
	 * <ul>
	 * <li>必选：景点id{scenicId}</li>
	 * <li>url:/api/pub/plan/relatecount</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/relatecount")
	public @ResponseBody ActionResult countRelatedPlan(final HttpServletRequest request) {

		Validate.notNull(request.getParameter("scenicId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		return planService.countRelatePlan(paramMap);
	}

	/**
	 * 增加行程浏览数 <li>url:/api/pub/plan/addview</li>
	 * 
	 * @return
	 */
	@RequestMapping("/addview")
	@ResponseBody
	public ActionResult addPlanView(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_50001);
		boolean result = planService.addPlanView(request.getParameter("planId"));
		return ActionResult.createSuccess(result);
	}


	@RequestMapping("/test")
	public @ResponseBody ActionResult testTrip(PlanTrip trip) {
		int size = 41000;
		long i = 40000;
		long time = System.currentTimeMillis();
		while (i < size) {
			planService.info(i);
			i++;
		}
		System.out.println("while cost " + (System.currentTimeMillis() - time) + "ms");
		time = System.currentTimeMillis();
		List<Long> ids = new ArrayList<Long>();
		i = 40000;
		while (i < size) {
			ids.add(i);
			i++;
		}
		planService.list(Collections.<String, Object> singletonMap("ids", ids));
		System.out.println("in cost " + (System.currentTimeMillis() - time) + "ms");
		return null;
	}

	/**
	 * 获取行程结果页
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public ActionResult planDetail(Long planId) {
		// 不需要复制酒店和酒店标签信息
		int flag = 0;
		int traffic = 0;
		return ActionResult.createSuccess(planDetailService.getPlanDetail(planId, flag, traffic));
	}

	/**
	 * 获取行程结果页(包含多个酒店以及标签信息)
	 */
	@RequestMapping("/showdetail")
	@ResponseBody
	public ActionResult showPlanDetail(Long planId) {
		// 要复制酒店并包含酒店标签信息
		int flag = 1;
		int traffic = 0;
		return ActionResult.createSuccess(planDetailService.getPlanDetail(planId, flag, traffic));
	}

	/**
	 * 修改行程状态
	 * add by 施清局
	 * <ul>
	 * <li>必选:行程planId{planId}</li>
	 * <li>url:/api/pub/plan/updatePlanSTATS</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/updatePlanSTATS")
	@ResponseBody
	public ActionResult updatePlanSTATS(String planId) {
		planOperationService.updatePlanSTATS(planId);
		return ActionResult.createSuccess();
	}

	/**
	 * <ul>
	 * <li>获取相关结伴帖数量</li>
	 * <li>必选:行程planId{planId}或者{scenicId}</li>
	 * <li>url:/api/pub/plan/involvedinvitation</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/invitationcount")
	public @ResponseBody ActionResult invitationCount(final HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		Validate.isTrue(paramMap.get("planId") != null || paramMap.get("scenicId") != null, ErrorCode.ERROR_51001);
		int count = planInvitationService.invitationCount(paramMap);
		return ActionResult.createSuccess(ImmutableMap.of("count", count));
	}

	/**
	 * <ul>
	 * <li>获取相关结伴帖列表</li>
	 * <li>必选:行程planId{planId}或者{scenicId}</li>
	 * <li>可选：分页大小{pageSize}</li>
	 * <li>可选：请求页码{page}</li>
	 * <li>url:/api/pub/plan/involvedinvitation</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/involvedinvitation")
	@ResponseBody
	public ActionResult involvedInvitation(final HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		Validate.isTrue(paramMap.get("planId") != null || paramMap.get("scenicId") != null, ErrorCode.ERROR_51001);
		return ActionResult.createSuccess(planInvitationService.involvedInvitationDetail(paramMap));
	}

	/**
	 * <ul>
	 * <li>获取有交集的人数量</li>
	 * <li>必选:行程planId{planId}或者{scenicId}</li>
	 * <li>url:/api/pub/plan/involvedinvitation</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/usercount")
	public @ResponseBody ActionResult userCount(final HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		Validate.isTrue(paramMap.get("planId") != null || paramMap.get("scenicId") != null, ErrorCode.ERROR_51001);
		int count = planInvitationService.userCount(paramMap);
		return ActionResult.createSuccess(ImmutableMap.of("count", count));
	}

	/**
	 * <ul>
	 * <li>获取有交集的人详情</li>
	 * <li>必选:行程planId{planId}或者{scenicId}</li>
	 * <li>可选：分页大小{pageSize}</li>
	 * <li>可选：请求页码{page}</li>
	 * <li>url:/api/pub/plan/involveduser</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/involveduser")
	public @ResponseBody ActionResult involvedUser(final HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		Validate.isTrue(paramMap.get("planId") != null || paramMap.get("scenicId") != null, ErrorCode.ERROR_51001);
		return ActionResult.createSuccess(planInvitationService.involvedUserDetail(paramMap));
	}

	@RequestMapping("/editplan")
	public @ResponseBody ActionResult editPlan(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("planData"), ErrorCode.ERROR_51001, "参数有误");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(planEditService.editPlan(paramMap));
	}

	@RequestMapping("/plandis")
	public @ResponseBody ActionResult planDis(final HttpServletRequest request) throws Exception {
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001, "参数有误");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		long planId = Long.parseLong((String) paramMap.get("planId"));
		return ActionResult.createSuccess(baiduService.planDis(planId));
	}

	/**
	 * 根据当前行程推荐酒店
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rechotel")
	public @ResponseBody ActionResult rechotel(final HttpServletRequest request) throws Exception {
		Validate.notNull(request.getParameter("locations"), ErrorCode.ERROR_51001, "参数有误");
		Validate.notNull(request.getParameter("price"), ErrorCode.ERROR_51001, "参数有误");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(planService.reCtripHotels(paramMap));
	}

	/**
	 * 根据当前行程和选择的景点判断景点应该插入的位置
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addscenic")
	public @ResponseBody ActionResult addScenic(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("dayList"), ErrorCode.ERROR_51001, "参数有误");
		Validate.notNull(request.getParameter("scenicId"), ErrorCode.ERROR_51001, "参数有误");
		Validate.notNull(request.getParameter("tripType"), ErrorCode.ERROR_51001, "参数有误");
		return ActionResult.createSuccess(addScenicService.addScenic(request));
	}

	/**
	 * 从推荐行程
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertbyrecplan")
	public @ResponseBody ActionResult insertByRecplan(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("tripList"), ErrorCode.ERROR_51001, "tripList参数有误");
		Validate.notNull(request.getParameter("days"), ErrorCode.ERROR_51001, "days参数有误");
		Validate.notNull(request.getParameter("cityCode"), ErrorCode.ERROR_51001, "cityCode参数有误");
		return ActionResult.createSuccess(planEditService.editFromRecplan(request));
	}

	@RequestMapping("/planpdf")
	public @ResponseBody ActionResult getPlanPdf(final HttpServletRequest request) throws Exception {
		Validate.notNull(request.getParameter("url"), ErrorCode.ERROR_51001, "url不能为空");
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001, "planId不能为空");
		String url = request.getParameter("url").toString();
		long planId = Long.parseLong(request.getParameter("planId").toString());
		return ActionResult.createSuccess(createPdfService.createPdf(url, planId));
	}

	/**
	 * 获取行程结果页
	 * 为了pdf打印
	 * (包含多个酒店、标签信息以及交通信息)
	 */
	@RequestMapping("/pdfdetail")
	@ResponseBody
	public ActionResult planDetailPdf(Long planId) {
		// 要复制酒店并包含酒店标签信息
		int flag = 1;
		int traffic = 1;
		return ActionResult.createSuccess(planDetailService.getPlanDetail(planId, flag, traffic));
	}

	@RequestMapping("/childdetail")
	@ResponseBody
	public ActionResult childPlanDetail(Long planId) {
		// 要复制酒店并包含酒店标签信息
		int flag = 1;
		int traffic = 0;
		return ActionResult.createSuccess(planDetailService.childDetail(planId, flag, traffic));
	}
	
	/**
	 * <ul>
	 * <li>根据cookie中的数据返回一个带有层级结构的行程详情</li>
	 * <li>V2.7版本</li>
	 * <li>必选:行程字符串jsonData : {planId : planId, planDayList: [ { planTripList : [ { scenicId : scenicId, tripType : tripType }, { ... } ] }, { ... } ]} </li>
	 * <li>url:/api/pub/plan/processPlanWithCookiedata</li>
	 * </ul>
	 *
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/processPlanWithCookiedata")
	public @ResponseBody ActionResult processPlanWithCookiedata(String jsonData) throws JsonParseException, JsonMappingException, IOException
	{
		Validate.notNull(jsonData, ErrorCode.ERROR_51001, "jsonData不能为空");
		Plan plan = om.readValue(jsonData, Plan.class);
		plan = planDetailService.processPlanWithDetailAndLevel(plan);
		
		return ActionResult.createSuccess(plan);
	}
}
