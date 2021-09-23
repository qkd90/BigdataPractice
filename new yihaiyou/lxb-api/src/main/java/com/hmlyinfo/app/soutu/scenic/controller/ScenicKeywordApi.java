package com.hmlyinfo.app.soutu.scenic.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicKeyword;
import com.hmlyinfo.app.soutu.scenic.service.ScenicKeywordService;
import  com.hmlyinfo.base.util.Validate;
import com.hmlyinfo.base.util.HttpUtil;

@Controller
@RequestMapping("/api/pub/scenickeyword")
public class ScenicKeywordApi
{
	@Autowired
	private ScenicKeywordService service;

	/**
	 * 
	 * 系统列出情绪关键词计数信息。
	 * <ul>
	 *  <li>必选：景点Id{scenicId}</li>
	 * 	<li>可选：分页大小{pageSize=10}</li>
	 * 	<li>可选：请求页码{page=1}</li>
	 * 	<li>url:/api/pub/scenickeyword/list</li>
	 * </ul>
	 * 
	 * @return 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult getScenicKeyword(final HttpServletRequest request)
	{
		Validate.notNull(request.getParameter("scenicId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		List<ScenicKeyword> scenicKeyword = service.list(paramMap);

		return ActionResult.createSuccess(scenicKeyword);
	}
	
}
