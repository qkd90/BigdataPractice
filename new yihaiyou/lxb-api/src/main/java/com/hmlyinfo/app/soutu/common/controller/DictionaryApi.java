package com.hmlyinfo.app.soutu.common.controller;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.common.service.DictionaryService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;

@Controller
@RequestMapping("/api/auth/dictionary")
public class DictionaryApi
{
	@Autowired
	private DictionaryService service;
	
	/**
	 * 系统按城市返回字典列表
	 * <ul>
	 * 	<li>必选:城市id{cityId}</li>
	 *  <li>url:/api/auth/dictionary/list</li>
	 * </ul>
	 *  
	 * @return 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult getDictionary(final HttpServletRequest request) {
		
		
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		
		return ActionResult.createSuccess(service.getDictionary(paramMap));
	}
}
