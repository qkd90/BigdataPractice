package com.hmlyinfo.app.soutu.scenicTicket.qunar.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarContant;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarContantService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/auth")
public class QunarContantApi
{
	@Autowired
	private QunarContantService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/qunarContant/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/qunarContant/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final QunarContant domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/qunarContant/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/qunarContant/del")
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
	 *  <li>url:/api/qunarContant/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/qunarContant/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final QunarContant domain)
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
	 * 	<li>url:/api/qunarContant/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/qunarContant/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/qunarContant/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/qunarContant/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
}
