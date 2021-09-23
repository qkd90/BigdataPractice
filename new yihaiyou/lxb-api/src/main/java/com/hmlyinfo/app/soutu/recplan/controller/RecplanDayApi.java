package com.hmlyinfo.app.soutu.recplan.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanDay;
import com.hmlyinfo.app.soutu.recplan.service.RecplanDayService;
import  com.hmlyinfo.base.util.Validate;
import com.hmlyinfo.base.util.HttpUtil;

@Controller
@RequestMapping("/api/pub")
public class RecplanDayApi
{
	@Autowired
	private RecplanDayService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/recplanday/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/recplanday/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final RecplanDay domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/recplanday/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/recplanday/del")
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
	 *  <li>url:/api/recplanday/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/recplanday/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final RecplanDay domain)
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
	 * 	<li>url:/api/recplanday/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/recplanday/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/recplanday/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/recplanday/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
}
