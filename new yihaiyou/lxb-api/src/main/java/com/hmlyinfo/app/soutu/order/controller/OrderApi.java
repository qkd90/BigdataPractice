package com.hmlyinfo.app.soutu.order.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.order.domain.Order;
import com.hmlyinfo.app.soutu.order.service.OrderService;
import  com.hmlyinfo.base.util.Validate;
import com.hmlyinfo.base.util.HttpUtil;

@Controller
@RequestMapping("/api/auth")
public class OrderApi
{
	@Autowired
	private OrderService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/order/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/order/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final Order domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/order/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/order/del")
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
	 *  <li>url:/api/order/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/order/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final Order domain)
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
	 * 	<li>url:/api/order/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/order/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/order/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/order/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
	
	/**
	 * 根据行程id批量下单
	 * 
	 * @param planId
	 * @return
	 */
	@RequestMapping("/order/preorder")
	public @ResponseBody ActionResult preOrder(final HttpServletRequest request) 
	{
		Validate.notNull(request.getParameter("planId"), ErrorCode.ERROR_50001, "标识{planId}不能为空");
		Validate.notNull(request.getParameter("fromTime"), ErrorCode.ERROR_50001, "起始时间不能为空");
		return ActionResult.createSuccess(service.preOrder(request));
	}
	
}
