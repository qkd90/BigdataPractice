package com.hmlyinfo.app.soutu.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.user.domain.LeavedMessage;
import com.hmlyinfo.app.soutu.user.mapper.LeavedMessageMapper;
import com.hmlyinfo.app.soutu.user.service.LeavedMessageService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/auth/leavedmessage")
public class LeaveMessageApi {
	
	
	@Autowired
	private LeavedMessageMapper<LeavedMessage> mapper;
	
	@Autowired
	private LeavedMessageService service;
	
	/**
	 * 新增，如果参数中含有exid代表回复
	 * <ul>
	 * 	<li>url:/api/auth/leavedmessage/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		service.newMessage(paramMap);
		return ActionResult.createSuccess();
	}
	
	
	/**
	 * 标记为已读
	 * <ul>
	 *  <li>必选：标识firstUser{firstUser}</li>
	 * 	<li>url:/api/auth/leavedmessage/update</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/update")
	public @ResponseBody ActionResult update(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("firstUser"), ErrorCode.ERROR_50001, "标识{firstUser}不能为空");
		String firstUser = request.getParameter("firstUser");
		service.updateStatus(firstUser);
		return ActionResult.createSuccess();
	}
	
	/**
	 * 留言列表
	 * <ul>
	 * 	<li>url:/api/auth/leavedmessage/listmessage</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/listmessage")
	public @ResponseBody ActionResult listMessage(final HttpServletRequest request)
	{
		return ActionResult.createSuccess(service.listMessage());
	}
	
	
	/**
	 * 和某个人的留言记录
	 * <ul>
	 *  <li>必选：标识secondUser{secondUser}</li>
	 * 	<li>url:/api/auth/leavedmessage/listdetail</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/listdetail")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("secondUser"), ErrorCode.ERROR_50001, "标识{secondUser}不能为空");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.listDetail(paramMap));
	}
	
	
	/**
	 * 未读留言数量
	 * <ul>
	 * 	<li>url:/api/auth/leavedmessage/count</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/count")
	public @ResponseBody ActionResult count()
	{
		int count = service.messageCount();
		return ActionResult.createSuccess(ImmutableMap.of("count", count));
	}
	

	
}


