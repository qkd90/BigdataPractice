package com.hmlyinfo.app.soutu.signet.controller;

import com.hmlyinfo.app.soutu.signet.service.MerchantService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/api/pub/merchant")
public class MerchantApi
{
	@Autowired
	private MerchantService service;

	/**
	 * 商家列表
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ActionResult list(HttpServletRequest request){
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(service.list(params));
	}

	/**
	 * 商家的详情
	 * @return
	 */
	@RequestMapping("/info")
	@ResponseBody
	public ActionResult info(long id){
		return ActionResult.createSuccess(service.info(id));
	}


}
