package com.hmlyinfo.app.soutu.signet.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.signet.domain.SignetImage;
import com.hmlyinfo.app.soutu.signet.service.SignetImageService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/pub")
public class SignetImageApi
{
	@Autowired
	private SignetImageService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/signetimage/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/signetimage/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final SignetImage domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/signetimage/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/signetimage/del")
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
	 *  <li>url:/api/signetimage/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/signetimage/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final SignetImage domain)
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
	 * 	<li>url:/api/signetimage/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/signetimage/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/signetimage/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/signetimage/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{bookId}</li>
	 *  <li>url:/api/signetimage/updateBybookid</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/signetimage/updateBybookid")
	public @ResponseBody ActionResult updateBybookid(final SignetImage si, String merchantId) 
	{
		Validate.notNull(si.getBookId(), ErrorCode.ERROR_50001, "标识{bookId}不能为空");
		Validate.notNull(merchantId, ErrorCode.ERROR_50001, "标识{merchantId}不能为空");
		service.updateBybookid(merchantId, si);
		
		return ActionResult.createSuccess();
	}
}
