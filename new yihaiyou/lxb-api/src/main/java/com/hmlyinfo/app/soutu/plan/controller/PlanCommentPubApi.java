package com.hmlyinfo.app.soutu.plan.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.service.PlanCommentService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/api/pub/plan/comment")
public class PlanCommentPubApi {

	@Autowired
	private PlanCommentService planCommentService;


	/**
	 * 查询行程评论
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>url:/api/pub/plan/comment/list</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ActionResult searchPlanCom(final HttpServletRequest request) {

		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);

		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		paramMap.put("userNeed", true);
		return ActionResult.createSuccess(planCommentService.searchPlanCom(paramMap));
	}

	@RequestMapping("/count")
	@ResponseBody
	public ActionResult countPlanCom(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return planCommentService.countPlanCom(paramMap);
	}

	/**
	 * 查询行程评论回复
	 * <ul>
	 * <li>必选:行程评论id{commentId}</li>
	 * <li>url:/api/pub/plan/comment/listreply</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/listreply")
	@ResponseBody
	public ActionResult searchReply(final HttpServletRequest request) {

		Validate.notNull(request.getParameter("commentId"), ErrorCode.ERROR_51001);

		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(planCommentService.searchReply(paramMap));
	}
}
