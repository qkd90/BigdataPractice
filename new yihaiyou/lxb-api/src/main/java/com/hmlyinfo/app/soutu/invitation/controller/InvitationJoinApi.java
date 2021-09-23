package com.hmlyinfo.app.soutu.invitation.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationJoin;
import com.hmlyinfo.app.soutu.invitation.service.InvitationJoinService;
import  com.hmlyinfo.base.util.Validate;
import com.hmlyinfo.base.util.HttpUtil;

@Controller
@RequestMapping("/api/auth")
public class InvitationJoinApi
{
	@Autowired
	private InvitationJoinService service;

	/**
	 * 新增
	 * <ul>
	 *  <li>必选:标识{invitationId}</li>
	 * 	<li>url:/api/invitationJoin/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/invitationJoin/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final InvitationJoin domain)
	{
		Validate.notNull(request.getParameter("invitationId"), ErrorCode.ERROR_50001, "标识{invitationId}不能为空");
		return ActionResult.createSuccess(service.newInvitationJoin(domain));
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/invitationJoin/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/invitationJoin/del")
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
	 *  <li>url:/api/invitationJoin/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/invitationJoin/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final InvitationJoin domain)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	/**
	 * 
	 * 查询列表
	 * <ul>
	 * 	<li>必选：标识{invitationId}</li>
	 * 	<li>url:/api/auth/invitationJoin/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/invitationJoin/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("invitationId"), ErrorCode.ERROR_50001, "标识{invitationId}不能为空");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.listDetail(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/invitationJoin/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/invitationJoin/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
}
