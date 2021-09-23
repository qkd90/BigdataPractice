package com.hmlyinfo.app.soutu.weixin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.weixin.domain.WxReply;
import com.hmlyinfo.app.soutu.weixin.service.WxReplyService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/pub/wx/reply")
public class WxReplyApi
{
	@Autowired
	private WxReplyService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/apipubwx/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final WxReply domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/apipubwx/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/del")
	public @ResponseBody ActionResult del(final HttpServletRequest request, final String id)
	{
		Validate.notNull(id, ErrorCode.ERROR_50001);
		service.del(id);
		
		return ActionResult.createSuccess();
	}
	
	
	/**
	 * 编辑
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/apipubwx/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final WxReply domain)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001);
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	/**
	 * 
	 * 查询列表
	 * <ul>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/apipubwx/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/apipubwx/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/info")
	public @ResponseBody ActionResult info(final long id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001);
		return ActionResult.createSuccess(service.info(id));
	}
}
