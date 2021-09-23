package com.hmlyinfo.app.soutu.delicacy.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.delicacy.service.DelicacyService;
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
@RequestMapping("/api/auth/delicacy")
public class DelicacyAuthApi
{
	@Autowired
	private DelicacyService service;
	
	/**
	 * 美食点赞
	 * <ul>
	 * 	<li>必选:美食id{delicacyId}</li>
	 * </ul>
	 *  <li>url:/api/auth/delicacy/addgood</li>
	 * @return 
	 */
	@RequestMapping("/addgood")
	public @ResponseBody ActionResult addGood(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("delicacyId"), ErrorCode.ERROR_51001);
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.addGood(paramMap));
	}
}
