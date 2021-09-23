package com.hmlyinfo.app.soutu.activity.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.activity.service.JoinedPlanService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/auth/joinedplan")
public class JoinedPlanApi
{
	@Autowired
	private JoinedPlanService joinedPlanService;

	/**
	 * 
	 * 查询列表
	 *
	 * @return 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(joinedPlanService.list(paramMap));
	}
	
	/**
	 * 根据id查询对象
	 * @param
	 * @return
	 */
	@RequestMapping("/info")
	public @ResponseBody ActionResult info(HttpServletRequest request){
		long id = Long.valueOf(request.getParameter("id"));
		return ActionResult.createSuccess(joinedPlanService.info(id));
	}
	
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ActionResult add(HttpServletRequest request){
		/*获取参数*/
		long planId = Long.valueOf(request.getParameter("planId"));
		/*验证*/
		Validate.notNull(planId, ErrorCode.ERROR_50001);
		return ActionResult.createSuccess(joinedPlanService.joinActivity(planId));
	}

	/**
	 * 用户是否有行程参赛
	 *
	 * @return
	 */
	@RequestMapping("/isUserJoinedPlan")
	@ResponseBody
	public ActionResult isUserJoinedPlan() {
		long userId = MemberService.getCurrentUserId();
		boolean flag = joinedPlanService.isUserJoinedPlan(userId);
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		result.put("isUserJoinedPlan", flag);
		return ActionResult.createSuccess(result);
	}
}
