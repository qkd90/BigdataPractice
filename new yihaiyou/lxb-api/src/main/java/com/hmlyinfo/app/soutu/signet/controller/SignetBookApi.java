package com.hmlyinfo.app.soutu.signet.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.signet.domain.SignetBook;
import com.hmlyinfo.app.soutu.signet.service.SignetBookService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

@Controller
@RequestMapping("/api")
public class SignetBookApi
{
	@Autowired
	private SignetBookService service;

	/**
	 * 新增
	 * <ul>
	 * 	<li>url:/api/signetbook/add</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/pub/signetbook/add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final SignetBook domain)
	{
		service.edit(domain);
		
		return ActionResult.createSuccess(domain);
	}
	
	
	/**
	 * 删除
	 * <ul>
	 * 	<li>必选:标识{id}<li>
	 *  <li>url:/api/signetbook/del</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/pub/signetbook/del")
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
	 *  <li>url:/api/signetbook/edit</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/pub/signetbook/edit")
	public @ResponseBody ActionResult edit(final HttpServletRequest request, final SignetBook domain)
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
	 * 	<li>url:/api/signetbook/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/pub/signetbook/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request)
	{
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	/**
	 * 查询对象
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/signetbook/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/pub/signetbook/info")
	public @ResponseBody ActionResult info(final String id) 
	{
		Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
		return ActionResult.createSuccess(service.info(Long.valueOf(id)));
	}
	
	/**
	 * 获取签章本信息
	 * <ul>
	 * 	<li>必选:标识{id}</li>
	 *  <li>url:/api/signetbook/info</li>
	 * </ul>
	 * 
	 * @return
	 */
	@RequestMapping("/auth/signetbook/get")
	public @ResponseBody ActionResult get(final String bookType)
	{
		Validate.notNull(bookType, ErrorCode.ERROR_50001, "标识{bookType}不能为空");
		SignetBook book = service.get(MemberService.getCurrentUserId(), bookType);
		
		return ActionResult.createSuccess(book);
	}
}
