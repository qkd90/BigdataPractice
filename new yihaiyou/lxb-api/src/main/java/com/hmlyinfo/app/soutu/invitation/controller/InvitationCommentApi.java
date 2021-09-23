package com.hmlyinfo.app.soutu.invitation.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.app.soutu.activity.domain.Notification;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationComment;
import com.hmlyinfo.app.soutu.invitation.service.InvitationCommentService;
import  com.hmlyinfo.base.util.Validate;
import com.hmlyinfo.base.util.HttpUtil;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/api")
public class InvitationCommentApi
{
	@Autowired
	private InvitationCommentService service;

	/**
	 * 新增
	 * <ul>
	 *  <li>必选:标识{invitationId}<li>
	 * 	<li>url:/api/invitationComment/add</li>
	 * </ul>
	 * @return
	 */
	@RequestMapping("/auth/invitationComment/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final InvitationComment domain)
	{
		Validate.notNull(request.getParameter("invitationId"), ErrorCode.ERROR_50001, "标识{invitationId}不能为空");
		return ActionResult.createSuccess(service.newInvitationComment(domain));
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/invitationComment/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/auth/invitationComment/del")
	public @ResponseBody ActionResult del(final HttpServletRequest request, final String id)
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.del(id);
		
		return ActionResult.createSuccess();
	}
	
	
	/**
	 * 编辑
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/invitationComment/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/auth/invitationComment/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final InvitationComment domain)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	/**
	 * 
	 * 查询列表
	 * <ul>
	 * <li>必选:标识{id}</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/invitationComment/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/pub/invitationComment/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.listDetail(paramMap));
	}
	
	
	/**
	 * 聊天通知列表
	 * <ul>
	 * 	<li>url:/api/auth/invitationComment/noticelist</li>
	 * </ul>
	 * @return
	 */
	@RequestMapping("/auth/invitationComment/noticelist")
	public @ResponseBody ActionResult NotificationList(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.notificationList(paramMap));
	}
	
	
	/**
	 * 聊天通知数量
	 * <ul>
	 * 	<li>url:/api/auth/invitationComment/noticecount</li>
	 * </ul>
	 * @return
	 */
	@RequestMapping("/auth/invitationComment/noticecount")
	public @ResponseBody ActionResult NotificationCount()
	{
		int noticecoumt = service.notificationCount();
		return ActionResult.createSuccess(ImmutableMap.of("noticecount", noticecoumt));
	}
	
	
	/**
	 * 标记为已读
	 * <ul>
	 *   <li>必选：评论id{id}</li>
	 * 	 <li>url:/api/auth/invitationComment/setreaded</li>
	 * </ul>
	 * @return
	 */
	@RequestMapping("/auth/invitationComment/setreaded")
	public @ResponseBody ActionResult SetReaded(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001, "标识{id}不能为空");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		service.setReaded(paramMap);
		return ActionResult.createSuccess();
	}
	
	
	/**
	 * 标记为已回复
	 * <ul>
	 *   <li>必选：评论id{id}</li>
	 * 	 <li>url:/api/auth/invitationComment/setreplied</li>
	 * </ul>
	 * @return
	 */
	@RequestMapping("/auth/invitationComment/setreplied")
	public @ResponseBody ActionResult SetReplied(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001, "标识{id}不能为空");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		service.setReplied(paramMap);
		return ActionResult.createSuccess();
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/auth/invitationComment/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/pub/invitationComment/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
}
