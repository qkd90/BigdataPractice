package com.hmlyinfo.app.soutu.common.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.common.domain.Survey;
import com.hmlyinfo.app.soutu.common.service.SurveyService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/api/pub")
public class SurveyApi
{
	@Autowired
	private SurveyService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/survey/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/survey/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final Survey domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/survey/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/survey/del")
	public @ResponseBody ActionResult del(final HttpServletRequest request, final String id)
	{
		Validate.notNull(id, ErrorCode.ERROR_51001);
		service.del(id);
		
		return ActionResult.createSuccess();
	}
	
	
	/**
	 * 编辑
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/survey/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/survey/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final Survey domain)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_51001);
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	/**
	 * 
	 * 查询列表
	 * <ul>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/survey/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/survey/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/survey/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/survey/info")
	public @ResponseBody ActionResult info(final long id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_51001);
		return ActionResult.createSuccess(service.info(id));
	}
}
