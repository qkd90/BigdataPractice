package com.hmlyinfo.app.soutu.recplan.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.recplan.domain.Recplan;
import com.hmlyinfo.app.soutu.recplan.service.RecplanOperationService;
import com.hmlyinfo.app.soutu.recplan.service.RecplanService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/auth/recplan")
public class RecplanApi
{
	@Autowired
	private RecplanService service;
	@Autowired
	private RecplanOperationService recplanOperationService;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/recplan/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final Recplan domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/recplan/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/del")
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
	 *  <li>url:/api/recplan/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final Recplan domain)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 引用推荐行程
	 * @param id
	 * @return
	 */
	@RequestMapping("/quote")
	public @ResponseBody ActionResult collect(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(recplanOperationService.quoteRecplan(Long.parseLong(id)));
	}
	
	
}
