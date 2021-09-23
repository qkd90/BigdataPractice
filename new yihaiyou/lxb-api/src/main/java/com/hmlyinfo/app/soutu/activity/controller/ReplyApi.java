package com.hmlyinfo.app.soutu.activity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.activity.domain.Advice;
import com.hmlyinfo.app.soutu.activity.domain.Reply;
import com.hmlyinfo.app.soutu.activity.service.AdviceService;
import com.hmlyinfo.app.soutu.activity.service.ReplyService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/auth/reply")
public class ReplyApi
{
	@Autowired
	private ReplyService replyService;
	@Autowired
	private AdviceService adviceService;

	/**
	 * 
	 * 查询列表
	 *
	 * @return 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request){
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(replyService.list(paramMap));
	}
	
	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public @ResponseBody ActionResult info(HttpServletRequest request){
		long id = Long.valueOf(request.getParameter("id"));
		return ActionResult.createSuccess(replyService.info(id));
	}
	
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ActionResult add(HttpServletRequest request){
		/*获取参数*/
		long adviceId = Long.valueOf(request.getParameter("adviceId"));
		long userId = MemberService.getCurrentUserId();
		String content = request.getParameter("content");
		/*数据验证*/
		Validate.notNull(adviceId, ErrorCode.ERROR_50001);
		Validate.notNull(userId, ErrorCode.ERROR_50001);
		Validate.notNull(content, ErrorCode.ERROR_50001);
		/*根据adviceId查询advice表，查询是否存在advcieId*/
		Map<String ,Object> adviceMap = new HashMap<String, Object>();
		adviceMap.put("id", adviceId);
		List<Advice> adviceList = adviceService.list(adviceMap);
		Validate.isTrue(!adviceList.isEmpty(), ErrorCode.ERROR_51009, "adviceId不存在");
		/*根据adviceId查询已有的数据*/
		Map<String ,Object> replyMap = new HashMap<String, Object>();
		replyMap.put("adviceId", adviceId);
		List<Reply> replyList = replyService.list(replyMap);
		Validate.isTrue(replyList.isEmpty(), ErrorCode.ERROR_51009,"一个adviceId只能回复一次");
		Reply reply = new Reply();
		reply.setUserId(userId);
		reply.setAdviceId(adviceId);
		reply.setContent(content);
		replyService.insert(reply);
		return ActionResult.createSuccess(reply);
	}
}
