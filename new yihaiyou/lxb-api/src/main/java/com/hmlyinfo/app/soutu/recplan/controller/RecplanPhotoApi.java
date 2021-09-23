package com.hmlyinfo.app.soutu.recplan.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanPhoto;
import com.hmlyinfo.app.soutu.recplan.service.RecplanPhotoService;
import  com.hmlyinfo.base.util.Validate;
import com.hmlyinfo.base.util.HttpUtil;

@Controller
@RequestMapping("/api/pub")
public class RecplanPhotoApi
{
	@Autowired
	private RecplanPhotoService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/recplanphoto/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/recplanphoto/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final RecplanPhoto domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/recplanphoto/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/recplanphoto/del")
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
	 *  <li>url:/api/recplanphoto/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/recplanphoto/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final RecplanPhoto domain)
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
	 * 	<li>url:/api/recplanphoto/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/recplanphoto/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/recplanphoto/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/recplanphoto/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
}
