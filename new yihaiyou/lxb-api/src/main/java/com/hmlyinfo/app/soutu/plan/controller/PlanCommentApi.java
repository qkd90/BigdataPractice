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
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
@RequestMapping("/api/auth/plan/comment")
public class PlanCommentApi {

	@Autowired
	private PlanCommentService planCommentService;

	/**
	 * 增加一条行程评论
	 * <ul>
	 * <li>必选:行程id{planId}</li>
	 * <li>必选:评论内容{content}</li>
	 * <li>url:/api/auth/plan/comment/add</li>
	 * </ul>
	 *
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ActionResult addPlanCom(final HttpServletRequest request)
		throws UnsupportedEncodingException {

		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_51001);

		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(planCommentService.addPlanCom(paramMap));
	}

	/**
	 * 删除一条行程评论
	 * <ul>
	 * <li>必选:行程评论id{id}</li>
	 * <li>url:/api/auth/plan/comment/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public ActionResult delPlanCom(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		planCommentService.delPlanCom(paramMap);
		return ActionResult.createSuccess();
	}

	/**
	 * 增加一条行程评论回复
	 * <ul>
	 * <li>必选:行程评论id{commentId}</li>
	 * <li>必选:行程id{planId}</li>
	 * <li>必选:评论内容{userReturn}</li>
	 * <li>url:/api/auth/plan/comment/addreply</li>
	 * </ul>
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/addreply")
	@ResponseBody
	public ActionResult addReply(final HttpServletRequest request)
		throws UnsupportedEncodingException {

		Validate.notNull(request.getParameter("commentId"),
			ErrorCode.ERROR_51001);

		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);

		return ActionResult.createSuccess(planCommentService.addPlanComReply(paramMap));
	}

	/**
	 * 删除一条行程评论回复
	 * <ul>
	 * <li>必选:行程评论id{id}</li>
	 * <li>url:/api/auth/plan/comment/delreply</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/delreply")
	@ResponseBody
	public ActionResult delReply(final HttpServletRequest request) {

		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);

		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		planCommentService.delPlanComReply(paramMap);
		return ActionResult.createSuccess();
	}
}
