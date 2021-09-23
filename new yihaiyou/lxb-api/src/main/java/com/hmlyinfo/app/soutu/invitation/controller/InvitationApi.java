package com.hmlyinfo.app.soutu.invitation.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.invitation.domain.Invitation;
import com.hmlyinfo.app.soutu.invitation.service.InvitationService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api")
public class InvitationApi
{
	@Autowired
	private InvitationService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/auth/invitation/add</li>
	 * </ul>
	 *
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/auth/invitation/add")
	public @ResponseBody ActionResult add(String invitation) throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper om = new ObjectMapper();
		Invitation nInvitation = om.readValue(invitation, Invitation.class);
		return ActionResult.createSuccess(service.newInvitation(nInvitation));
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/auth/invitation/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/auth/invitation/del")
	public @ResponseBody ActionResult del(final HttpServletRequest request, final String id)
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.delInvitation(id);
		return ActionResult.createSuccess();
	}
	
	
	/**
	 * 编辑
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/auth/invitation/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/auth/invitation/edit")
	public @ResponseBody ActionResult edit(String invitation) throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper om = new ObjectMapper();
		Invitation nInvitation = om.readValue(invitation, Invitation.class);
		Validate.notNull(nInvitation.getId());
		return ActionResult.createSuccess(service.editInvitation(nInvitation));
	}
	
	
	/**
	 * 
	 * 查询列表
	 * <ul>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/pub/invitation/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/pub/invitation/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.listDetail(paramMap));
	}
	
	
	/**
	 * 查询某个结伴帖的详细信息 
	 * <ul>
	 *  <li>必选：标识{id}</li>
	 * 	<li>url:/api/pub/invitation/info</li>
	 * </ul>
	 * 
	 */
	@RequestMapping("/pub/invitation/info")
	public @ResponseBody ActionResult info(String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001);
		return ActionResult.createSuccess(service.invitationInfo(id));
	}
}
