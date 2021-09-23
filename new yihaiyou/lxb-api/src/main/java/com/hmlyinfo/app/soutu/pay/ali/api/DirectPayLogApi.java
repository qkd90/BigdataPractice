package com.hmlyinfo.app.soutu.pay.ali.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hmlyinfo.app.soutu.pay.ali.domain.DirectPayLogDto;
import com.hmlyinfo.app.soutu.pay.ali.service.impl.DirectPayLogService;
import com.hmlyinfo.base.ActionResult;

/**
 * 
 *
 * <p>Title: DirectPayLogApi.java</p>
 *
 * <p>Description:支付宝交易日志相关Api </p>
 * 
 * <p>Date:2013-7-30</p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: www.myleyi.com</p>
 *
 * @author zheng.yongfeng
 * @version
 */
@Controller
@RequestMapping("/api/alipay/directPayLog/")
@SuppressWarnings("unchecked")
public class DirectPayLogApi {
	@Autowired
	private DirectPayLogService directPayLogService;

	/**
	 * 新增支付宝交易日志信息
	 * @param request
	 * <ul>
	 * 	<li>url:/api/alipay/directPayLog/add</li>
	 * </ul>
	 * @return
	 */
	@RequestMapping("add")
	public @ResponseBody ActionResult add(final HttpServletRequest request, final DirectPayLogDto dto){
		
		return ActionResult.createSuccess(directPayLogService.insert(dto));
	}
	
	
	
}
