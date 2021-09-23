package com.hmlyinfo.app.soutu.activity.controller;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.activity.domain.Advice;
import com.hmlyinfo.app.soutu.activity.service.AdviceService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
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
@RequestMapping("/api/pub/advice")
public class AdvicePubApi
{
	@Autowired
	private AdviceService adviceService;

	@RequestMapping("/listwithreply")
	public @ResponseBody ActionResult listWithReply(final HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(adviceService.listWithReply(paramMap));
	}
	
	/**
	 * 查询评论数量
	 * <ul>
	 * <li>必选:用户id{userId}</li>
	 * <li>url:/api/pub/advice/count</li>
	 * </ul>
	 * @return
	 */
	@RequestMapping("/count")
	@ResponseBody
	public ActionResult count(final HttpServletRequest request) {
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		return adviceService.countAsResult(params);
	}
	
	
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ActionResult add(HttpServletRequest request){
		/*获取参数*/
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		/*数据验证*/
//		Validate.notNull(userId, ErrorCode.ERROR_50001);
//		Validate.notNull(title, ErrorCode.ERROR_50001);
		Validate.notNull(content, ErrorCode.ERROR_50001);
		
		Advice advice = new Advice();
//		advice.setUserId(userId);
		advice.setTitle(title);
		advice.setContent(content);
		adviceService.insert(advice);
		return ActionResult.createSuccess(advice);	
	}
}
