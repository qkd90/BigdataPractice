package com.hmlyinfo.app.soutu.scenicTicket.qunar.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarTicket;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarTicketService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/pub")
public class QunarTicketApi
{
	@Autowired
	private QunarTicketService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/qunarticket/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/qunarticket/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final QunarTicket domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/qunarticket/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/qunarticket/del")
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
	 *  <li>url:/api/qunarticket/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/qunarticket/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final QunarTicket domain)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	/**
	 * 
	 * 查询列表
	 * <ul>
	 * 	<li>必选：景点id{scenicId}</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/qunarticket/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/qunarticket/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		String scenicId = request.getParameter("scenicId");
		Validate.notNull(scenicId, ErrorCode.ERROR_51001, "景点id{scenicId}为必传参数");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.listDetail(paramMap, Long.valueOf(scenicId)));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/qunarticket/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/qunarticket/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
	
	
	/**
	 * 初始化qunar相关的缓存数据
	 */
	@RequestMapping("/qunarticket/initcache")
	public void initTicketCache() 
	{
		new Thread(){
			public void run(){
				service.initCache();
			}
		}.start();
		
	}
	
	
	/**
	 * 初始化qunar相关的缓存数据
	 */
	@RequestMapping("/qunarticket/test")
	public void test() 
	{
		service.availableCount("3032292592", "");
	}
	
}
