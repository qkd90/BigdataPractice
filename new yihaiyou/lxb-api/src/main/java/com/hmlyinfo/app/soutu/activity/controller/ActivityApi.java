package com.hmlyinfo.app.soutu.activity.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.activity.service.ActivityService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;

@Controller
@RequestMapping("/api/pub/activity")
public class ActivityApi
{
	@Autowired
	private ActivityService activityService;
	
	/**
	 * 
	 * 查询列表
	 *
	 * @return 
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult list(final HttpServletRequest request){
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(activityService.list(paramMap));
	}
	
	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	@RequestMapping("/info")
	public @ResponseBody ActionResult info(HttpServletRequest request){
		Long id = Long.valueOf(request.getParameter("id"));
		return ActionResult.createSuccess(activityService.info(id));
	}
}
