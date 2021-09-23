package com.hmlyinfo.app.soutu.scenicTicket.qunar.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarPrice;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarPriceService;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/pub")
public class QunarPriceApi
{
	@Autowired
	private QunarPriceService service;
	@Autowired
	private QunarService qunarService;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/qunarprice/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/qunarprice/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final QunarPrice domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/qunarprice/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/qunarprice/del")
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
	 *  <li>url:/api/qunarprice/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/qunarprice/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final QunarPrice domain)
	{
		Validate.notNull(request.getParameter("id"), ErrorCode.ERROR_50001, "标识{id}不能为空");
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	/**
	 * 
	 * 查询列表
	 * <ul>
	 *  <li>必选：门票本地id</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/qunarprice/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/qunarprice/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		
		Validate.notNull(request.getParameter("ticketId"), ErrorCode.ERROR_50001, "标识{ticketId}不能为空");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.listByTicketId(paramMap));
	}
	
	/**
	 * 
	 * 根据productId和日期查询门票价格
	 * <ul>
	 * 	<li>必选:产品ID{productId}</li>
	 * 	<li>必选：日期{date}</li>
	 * 	<li>url:/api/qunarprice/bizinfo</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/qunarprice/bizinfo")
	public @ResponseBody ActionResult bizinfo(String productId, String date)
	{
		Validate.notNull(productId, ErrorCode.ERROR_50001, "标识{productId}不能为空");
		Validate.notNull(date, ErrorCode.ERROR_50001, "标识{date}不能为空");
		
		return ActionResult.createSuccess(service.bizinfo(productId, date));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/qunarprice/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/qunarprice/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
	/**
	 * 接收价格信息变动通知
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/qunarprice/updateprices")
	public @ResponseBody Map<String, String> updatePrices(final HttpServletRequest request) 
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return service.updatePrices(paramMap);
	}
	
	@RequestMapping("/qunarprice/addprices")
	public void getPrice(final HttpServletRequest request) 
	{
		service.getPriceInfo();
	}
	
	
}
