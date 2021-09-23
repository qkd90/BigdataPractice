package com.hmlyinfo.app.soutu.plan.controller;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.plan.service.HdScenicInfoService;
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
@RequestMapping("/api/pub/hd")
public class HdScenicInfoApi
{
	@Autowired
	private HdScenicInfoService service;

	/**
	 * 
	 * 系统查询手绘图信息。
	 * <ul>
	 *  <li>必选：城市区号{cityCode}</li>
	 *  <li>必选：缩放级别{zoomLevel}</li>
	 * 	<li>url:/api/pub/hd/list</li>
	 * </ul>
	 *
	 * @return
	 */
	@RequestMapping("/list")
	public @ResponseBody ActionResult listScenic(final HttpServletRequest request, boolean rankFlag){
		Validate.notNull(request.getParameter("cityCode"), ErrorCode.ERROR_51001);
		Validate.notNull(request.getParameter("zoomLevel"), ErrorCode.ERROR_51001);

		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		if(rankFlag == true){
			paramMap.put("orderColumn", "rank");
			//paramMap.put("orderType", "desc");
		}
		//List<HdScenicInfo> result = service.list(paramMap);
		return ActionResult.createSuccess(service.list(paramMap));
	}

	/**
	 * 传入一个str 如果是拼音按拼音查询，如果是中文按中文查询，返回列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/listName")
	@ResponseBody
	public ActionResult listName(final HttpServletRequest request){
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.listName(params));
	}
}