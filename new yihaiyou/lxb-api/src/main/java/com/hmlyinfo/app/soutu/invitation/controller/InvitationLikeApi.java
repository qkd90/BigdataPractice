package com.hmlyinfo.app.soutu.invitation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ImmutableMap;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationLike;
import com.hmlyinfo.app.soutu.invitation.service.InvitationLikeService;
import  com.hmlyinfo.base.util.Validate;
import com.hmlyinfo.base.util.HttpUtil;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/api")
public class InvitationLikeApi
{
	@Autowired
	private InvitationLikeService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>必选:标识{invitationId}<li>
	 * 	<li>url:/api/auth/invitationLike/changeLike</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/auth/invitationLike/changeLike")
	public @ResponseBody ActionResult changeLike(final HttpServletRequest request, final String invitationId)
	{
		Validate.notNull(invitationId, ErrorCode.ERROR_50001);
		long userId = MemberService.getCurrentUserId();
		service.changeLike(Long.valueOf(invitationId), userId);
		
		return ActionResult.createSuccess();
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/invitationLike/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/auth/invitationLike/del")
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
	 *  <li>url:/api/invitationLike/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/auth/invitationLike/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final InvitationLike domain)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	/**
	 * 
	 * 查询列表
	 * <ul>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/invitationLike/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/pub/invitationLike/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.count(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/invitationLike/isLiked</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/auth/invitationLike/isLiked")
	public @ResponseBody ActionResult isLiked(final long invitationId)
	{
		long userId = MemberService.getCurrentUserId();
		boolean isLiked = service.isLiked(invitationId, userId);
		return ActionResult.createSuccess(ImmutableMap.of("isLiked", isLiked));

	}
	
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/invitationLike/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/auth/invitationLike/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
}
