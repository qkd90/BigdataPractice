package com.hmlyinfo.app.soutu.pay.ali.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.pay.ali.service.impl.DirectPayMobileService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;

/**
 * 
 *
 * <p>Title: DirectPayMobileApi.java</p>
 *
 * <p>Description: 支付宝即时到账手机支付Api
 * 
 *  *************************注意*****************
 *	如果您在接口集成过程中遇到问题，可以按照下面的途径来解决
 *	1、商户服务中心（https://b.alipay.com/support/helperApply.htm?action=consultationApply），提交申请集成协助，我们会有专业的技术工程师主动联系您协助解决
 *	2、商户帮助中心（http://help.alipay.com/support/232511-16307/0-16307.htm?sh=Y&info_type=9）
 *	3、支付宝论坛（http://club.alipay.com/read-htm-tid-8681712.html）
 *	如果不想使用扩展功能请把扩展功能参数赋空值。
 *	*********************************************
 * 
 * </p>
 * 
 * <p>Date:2013-7-26</p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: www.myleyi.com</p>
 *
 * @author zheng.yongfeng
 * @version
 */
@Controller
@RequestMapping("/api/alipay/directPayMobile/")
public class DirectPayMobileApi {
	@Autowired
	private DirectPayMobileService directPayMobileService;
	
	/**
	 * 测试
	 * @param request
	 * @return
	 */
	@RequestMapping("buildRequest")
	public @ResponseBody
	ActionResult buildRequest(final HttpServletRequest request) {
		
		Validate.notNull(request.getParameter("notifyUrl"), ErrorCode.ERROR_50001, "服务器异步通知页面路径{notifyUrl}不能为空");
		Validate.notNull(request.getParameter("merchantUrl"), ErrorCode.ERROR_50001, "服务器中断支付页面路径{merchantUrl}不能为空");
		Validate.notNull(request.getParameter("outTradeNo"), ErrorCode.ERROR_50001, "商户网站订单系统中唯一订单号{outTradeNo}不能为空");
		Validate.notNull(request.getParameter("subject"), ErrorCode.ERROR_50001, "订单名称{subject}不能为空");
		Validate.notNull(request.getParameter("totalFee"), ErrorCode.ERROR_50001, "付款金额{totalFee}不能为空");

		Map params = HttpUtil.parsePageMap(request);
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("requestInfo", directPayMobileService.buildRequest(params));
		
		return ActionResult.createSuccess(resultMap);
	}
	
	/**
	 * 处理支付宝异步通知信息
	 * <ul>
	 * 	<li>必选:请求信息中必须包含支付宝返的所有反馈信息，不能包含自定义参数</li>
	 *  <li>ulr:/api/alipay/directPayMobile/dealNotify</li>
	 * </ul>
	 * @param request
	 * @return
	 */
	@RequestMapping("dealNotify")
	public @ResponseBody
	ActionResult dealNotify(final HttpServletRequest request) {
		
		return ActionResult.createSuccess(directPayMobileService.dealNotify(HttpUtil.parsePageMap(request)));
	}
	
	
	/**
	 * 处理支付宝同步通知信息
	 * <ul>
	 * 	<li>必选:请求信息中必须包含支付宝返的所有反馈信息，不能包含自定义参数</li>
	 *  <li>ulr:/api/alipay/directPayMobile/dealCallBack</li>
	 * </ul>
	 * @param request
	 * @return
	 */
	@RequestMapping("dealCallBack")
	public @ResponseBody
	ActionResult dealCallBack(final HttpServletRequest request) 
	{
		
		Map<String, Object> param = HttpUtil.parsePageMap(request);
		return ActionResult.createSuccess(directPayMobileService.dealCallBack(param));
	}

}
