package com.hmlyinfo.app.soutu.activity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.activity.domain.Activity;
import com.hmlyinfo.app.soutu.activity.domain.JoinedPlan;
import com.hmlyinfo.app.soutu.activity.domain.PlanPoll;
import com.hmlyinfo.app.soutu.activity.service.ActivityService;
import com.hmlyinfo.app.soutu.activity.service.JoinedPlanService;
import com.hmlyinfo.app.soutu.activity.service.PlanPollService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/auth/planpoll")
public class PlanPollApi {
	@Autowired
	private PlanPollService planPollService;
	@Autowired
	private JoinedPlanService joinedPlanService;
	@Autowired
	private ActivityService activityService;
	/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping("/list")
	public @ResponseBody
	ActionResult list(final HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(planPollService.list(paramMap));
	}

	/**
	 * 根据id查询对象
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public @ResponseBody
	ActionResult info(HttpServletRequest request) {
		long id = Long.valueOf(request.getParameter("id"));
		return ActionResult.createSuccess(planPollService.info(id));
	}

	/**
	 * 添加
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody
	ActionResult add(HttpServletRequest request) {
		/* 获取参数 */
		long joinedPlanId = Long.valueOf(request.getParameter("joinedPlanId"));
		/* 数据验证 */
		Validate.notNull(joinedPlanId, ErrorCode.ERROR_50001);
		/*根据joinedPlanId查询出对应的对象*/
		JoinedPlan joinedPlan = joinedPlanService.info(joinedPlanId);
		
		return doVote(joinedPlan, request);
	}
	
	/**
	 * 根据planId投票
	 * @param request
	 * @return
	 */
	@RequestMapping("/vote")
	public @ResponseBody ActionResult vote(HttpServletRequest request)
	{
		String planId = request.getParameter("planId");
		Validate.notNull(planId, ErrorCode.ERROR_50001);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("planId", planId);
		JoinedPlan joinedPlan = (JoinedPlan)joinedPlanService.one(paramMap);
		
		return doVote(joinedPlan, request);
	}
	
	private ActionResult doVote(JoinedPlan joinedPlan, HttpServletRequest request)
	{
		long userId = MemberService.getCurrentUserId();
		Validate.notNull(userId, ErrorCode.ERROR_50001);

		Validate.notNull(joinedPlan, ErrorCode.ERROR_53001, "该行程未参赛！");
		/* 根据状态是1(进行中)的查询activity表对应的活动 */
		Map<String, Object> activeMap = new HashMap<String, Object>();
		activeMap.put("status", Activity.STATUS_RUNNING);
		List<Activity> activityList = activityService.list(activeMap);
		Validate.isTrue(!activityList.isEmpty(), ErrorCode.ERROR_53001, "本活动已结束，敬请关注旅行帮其他活动讯息");
		long activityId = activityList.get(0).getId();
		Validate.isTrue(activityId == joinedPlan.getActivityId(),ErrorCode.ERROR_59002,"本期活动已结束，敬请期待下期活动");
		/* 根据userid和activityId查询对应的集合数据，如果集合不为空，说明已经投票，就抛出异常 */
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("activityId", activityId);
		List<PlanPoll> list = planPollService.list(paramMap);
		Validate.isTrue(list.isEmpty(), ErrorCode.ERROR_59001, "每个用户只能投一次票");
		/*给planPoll对象设置对应的值，执行添加投票*/
		String ip = request.getParameter("ip");
		PlanPoll planPoll = new PlanPoll();
		planPoll.setUserId(userId);
		planPoll.setJoinedPlanId(joinedPlan.getId());
		planPoll.setActivityId(activityId);
		planPoll.setIp(ip);
		planPollService.insert(planPoll);
		/*获取该行程的票数，在原有的票数上加1,修改该参赛行程的票数*/
		int pollCount = joinedPlan.getPollCount() + 1;
		joinedPlan.setPollCount(pollCount);
		joinedPlanService.update(joinedPlan);
		return ActionResult.createSuccess();
	}

	/**
	 * 判断是否已经投过票
	 * @return
	 */
	@RequestMapping("/isUserPoll")
	public @ResponseBody ActionResult isUserPoll(){
		long userId = MemberService.getCurrentUserId();
		boolean flag = planPollService.isUserPoll(userId);
		Map<String,Boolean> result= new HashMap<String,Boolean>();
		result.put("isUserPoll", flag);
		return ActionResult.createSuccess(result);
	}
}
