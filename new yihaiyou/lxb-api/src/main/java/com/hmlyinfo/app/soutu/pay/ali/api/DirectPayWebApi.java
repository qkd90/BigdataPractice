package com.hmlyinfo.app.soutu.pay.ali.api;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.pay.ali.service.AlipayTicketService;
import com.hmlyinfo.app.soutu.pay.ali.service.impl.DirectPayWebService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 *
 * <p>Title: DirectPayApi.java</p>
 *
 * <p>Description: 支付宝即时到账Api
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
@RequestMapping("/api/pub/alipay/directPay/")
public class DirectPayWebApi {
	
	
	@Autowired
	private DirectPayWebService directPayService;
	@Autowired
	private AlipayTicketService alipayTicketService;
	
	/**
	 * 测试
	 * @param request
	 * @return
	 */
	@RequestMapping("buildRequest")
	public @ResponseBody
	ActionResult buildRequest(final HttpServletRequest request) {
		
		Validate.notNull(request.getParameter("notifyUrl"), ErrorCode.ERROR_50001, "服务器异步通知页面路径{notifyUrl}不能为空");
		Validate.notNull(request.getParameter("outTradeNO"), ErrorCode.ERROR_50001, "商户网站订单系统中唯一订单号{outTradeNo}不能为空");
		Validate.notNull(request.getParameter("subject"), ErrorCode.ERROR_50001, "订单名称{subject}不能为空");
		Validate.notNull(request.getParameter("totalFee"), ErrorCode.ERROR_50001, "付款金额{totalFee}不能为空");
		
		Map params = HttpUtil.parsePageMap(request);
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("requestInfo", directPayService.buildRequest(params));
		
		return ActionResult.createSuccess(resultMap);
	}
	
	/**
	 * 处理支付宝通知信息
	 * <ul>
	 * 	<li>必选:请求信息中必须包含支付宝返的所有反馈信息，不能包含自定义参数</li>
	 *  <li>ulr:/api/alipay/directPay/dealNotify</li>
	 * </ul>
	 * @param request
	 * @return
	 */
	@RequestMapping("dealNotify")
	public @ResponseBody
	ActionResult dealNotify(final HttpServletRequest request) {
		Map<String,String> params = getNotifyParam(request);
		return ActionResult.createSuccess(directPayService.dealNotify(params));
	}
	
	//获取支付宝反馈信息
	private Map<String,String> getNotifyParam(HttpServletRequest request){
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		String name = "";
		String[] values = null;
		String valueStr = "";
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			name = (String) iter.next();
			values = (String[]) requestParams.get(name);
			valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}

			params.put(name, valueStr);
		}

		//去除额外的自定义参数，否则验证失败
		params.remove("appKey");
		
		return params;
	}
	
	
	
	/**
	 * （2.6.0）
	 * 根据总订单生成支付请求
	 * @param request
	 * @return
	 */
	@RequestMapping("buildNewRequest")
	public @ResponseBody
	ActionResult buildNewRequest(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("orderId"), ErrorCode.ERROR_50001, "没有订单id无法支付");
		Validate.notNull(request.getParameter("returnUrl"), ErrorCode.ERROR_50001, "参数错误");
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		Map<String, Object> resultMap = alipayTicketService.newPayOrder(params);
		return ActionResult.createSuccess(resultMap);
	}
	
	/**(2.6.0)
	 * 处理支付宝通知信息
	 * <ul>
	 * 	<li>必选:请求信息中必须包含支付宝返的所有反馈信息，不能包含自定义参数</li>
	 *  <li>ulr:/api/alipay/directPay/newDealNotify</li>
	 * </ul>
	 * @param request
	 * @return
	 */
	@RequestMapping("newDealNotify")
	public @ResponseBody
	ActionResult newDealNotify(final HttpServletRequest request) {
		Map<String,String> params = getNotifyParam(request);
		return ActionResult.createSuccess(directPayService.dealNotify(params));
	}
	
	/**
	 * 根据订单生成支付请求
	 * @param request
	 * @return
	 */
	@RequestMapping("buildOrderRequest")
	public @ResponseBody
	ActionResult buildOrderRequest(final HttpServletRequest request) {
		Validate.notNull(request.getParameter("scenicTicketOrderId"), ErrorCode.ERROR_50001, "没有订单id无法支付");
		Validate.notNull(request.getParameter("returnUrl"), ErrorCode.ERROR_50001, "参数错误");
		Map<String, Object> params = HttpUtil.parsePageMap(request);
		Map<String, Object> resultMap = alipayTicketService.payOrder(params);
		return ActionResult.createSuccess(resultMap);
	}
}
