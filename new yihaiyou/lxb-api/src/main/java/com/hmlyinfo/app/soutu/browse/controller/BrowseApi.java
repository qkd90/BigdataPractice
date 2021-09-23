package com.hmlyinfo.app.soutu.browse.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.browse.domain.Browse;
import com.hmlyinfo.app.soutu.browse.service.BrowseService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;

@Controller
@RequestMapping("/api/pub/browse")
public class BrowseApi
{
	@Autowired
	private BrowseService service;
	
	/**
	 * 
	 * 查询列表
	 *
	 * @return 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request){
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.list(paramMap));
	}
	
	@RequestMapping("/add")
	public @ResponseBody ActionResult addDays(final HttpServletRequest request, Browse browse)
	{
		browse = service.insert(browse);
		return ActionResult.createSuccess(browse);
	}
}
