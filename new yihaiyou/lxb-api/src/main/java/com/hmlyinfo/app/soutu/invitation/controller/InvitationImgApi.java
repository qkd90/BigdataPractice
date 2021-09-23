package com.hmlyinfo.app.soutu.invitation.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationImg;
import com.hmlyinfo.app.soutu.invitation.service.InvitationImgService;
import  com.hmlyinfo.base.util.Validate;
import com.hmlyinfo.base.util.HttpUtil;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/api/auth")
public class InvitationImgApi
{
	@Autowired
	private InvitationImgService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/invitationImg/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/invitationImg/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final InvitationImg domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/invitationImg/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/invitationImg/del")
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
	 *  <li>url:/api/invitationImg/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/invitationImg/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final InvitationImg domain)
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
	 * 	<li>url:/api/invitationImg/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/invitationImg/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/invitationImg/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/invitationImg/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
}
