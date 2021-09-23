package com.hmlyinfo.app.soutu.signet.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.signet.domain.SignetRecord;
import com.hmlyinfo.app.soutu.signet.service.ImageMarkLogoByIconService;
import com.hmlyinfo.app.soutu.signet.service.SignetRecordService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api/pub")
public class SignetRecordApi
{
	@Autowired
	private SignetRecordService service;
	@Autowired
	private ImageMarkLogoByIconService markService;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/signetrecord/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/signetrecord/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final SignetRecord domain)
	{
		service.addRecord(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/signetrecord/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/signetrecord/del")
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
	 *  <li>url:/api/signetrecord/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/signetrecord/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final SignetRecord domain)
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
	 * 	<li>url:/api/signetrecord/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/signetrecord/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/signetrecord/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/signetrecord/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
	/**
	 * <li>必选:水印地址{iconPath}</li>
	 * <li>必选:源图片地址{srcImgPath}</li>
	 * <li>必选:目标地址{targerPath}</li>
	 * <li>必选:位置x{x}</li>
	 * <li>必选:位置y{y}</li>
	 * <li>必选:宽度w{w}</li>
	 * <li>必选:高度h{h}</li>
	 * <li>可选:水印旋转角度{degree}</li>
	 * <li>可选:水印透明度{alpha}</li>
	 * <li>url:/api/pub/signetrecord/addmark</li>
	 * @return
	 */
	@RequestMapping("/signetrecord/addmark")
	public @ResponseBody ActionResult addMark(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("iconPath"), ErrorCode.ERROR_50001, "水印地址不能为空");
		Validate.notNull(request.getParameter("srcImgPath"), ErrorCode.ERROR_50001, "源图片地址");
		Validate.notNull(request.getParameter("targerPath"), ErrorCode.ERROR_50001, "目标地址");
		Validate.notNull(request.getParameter("x"), ErrorCode.ERROR_50001, "位置x");
		Validate.notNull(request.getParameter("y"), ErrorCode.ERROR_50001, "位置y");
		Validate.notNull(request.getParameter("w"), ErrorCode.ERROR_50001, "水印宽度w");
		Validate.notNull(request.getParameter("h"), ErrorCode.ERROR_50001, "水印宽度h");
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		markService.markImageByIcon(paramMap);
		return ActionResult.createSuccess();
	}
}
