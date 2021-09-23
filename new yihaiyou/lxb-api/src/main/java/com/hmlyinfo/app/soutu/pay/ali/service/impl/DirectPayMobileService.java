package com.hmlyinfo.app.soutu.pay.ali.service.impl;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.SpringContextUtils;
import com.hmlyinfo.app.soutu.pay.ali.config.CommonCfg;
import com.hmlyinfo.app.soutu.pay.ali.config.DirectPayMobileCfg;
import com.hmlyinfo.app.soutu.pay.ali.direct.mobile.AlipayNotify;
import com.hmlyinfo.app.soutu.pay.ali.direct.mobile.AlipaySubmit;
import com.hmlyinfo.app.soutu.pay.ali.domain.DirectPayLogDto;
import com.hmlyinfo.app.soutu.pay.ali.domain.DirectPayReceiptDto;
import com.hmlyinfo.app.soutu.pay.ali.util.UtilDate;
import com.hmlyinfo.app.soutu.pay.base.service.IDirectPayService;
import com.hmlyinfo.base.util.StringUtil;

/**
 * 
 *
 * <p>Title: DirectPayMobileService.java</p>
 *
 * <p>Description: 支付宝即时到账服务-手机网页</p>
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
@Service
public class DirectPayMobileService{
	@Autowired
	private DirectPayLogService directPayLogService;
	
	@Autowired
	private DirectPayReceiptService directPayReceiptService;
	
	private ObjectMapper om = new ObjectMapper();
	
	private static final Log logs = LogFactory.getLog(DirectPayMobileService.class);
	
	/**
	 * 处理支付宝异步通知
	 * @param paramMap
	 * @return
	 * @throws Exception 
	 */
	public Map dealNotify(Map paramMap){
		//解析通知信息中的notify_data xml数据
		Map notifyData = getNotifyData((String)paramMap.get("notify_data"));
		notifyData.putAll(paramMap);
		
		//获取支付请求日志信息
		DirectPayLogDto payLogDto = getPayLogInfo(notifyData);

		//返回值
		Map<String, Object> result = Maps.newHashMap();
		result.put("dealResult", false);//初始值，交易不成功
		result.put("paySuccessUrl", payLogDto.getPaySuccessUrl());
		result.put("payFailUrl", payLogDto.getPayFailUrl());
		
		//去除额外的自定义参数，否则验证失败
		paramMap.remove("appKey");
		if (verifyNotify(paramMap)) {//验证成功，不能含有自定义参数
			//交易状态
			//注意：
			//"TRADE_FINISHED"该种交易状态只在两种情况下出现
			//1、开通了普通即时到账，买家付款成功后。
			//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
			
			//"TRADE_SUCCESS"该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
			String tradeStatus = (String)notifyData.get("trade_status");
			if ("TRADE_FINISHED".equals(tradeStatus) 
					|| "TRADE_SUCCESS".equals(tradeStatus)) {
				//获取支付成功后需要回调的业务服务
				IDirectPayService payService = getPayService(payLogDto);
				result.put("dealResult", deal(notifyData, payService));
			}
		}
		
		return result;
	}
	
	/**
	 * 处理支付宝同步通知
	 * @param paramMap
	 * @return
	 */
	public Map dealCallBack(Map paramMap){
		//获取支付请求日志信息
		DirectPayLogDto payLogDto = getPayLogInfo(paramMap);
		
		//返回值
		Map<String, Object> result = Maps.newHashMap();
		result.put("dealResult", false);//初始值，交易不成功
		result.put("paySuccessUrl", payLogDto.getPaySuccessUrl());
		result.put("payFailUrl", payLogDto.getPayFailUrl());
		
		//去除额外的自定义参数，否则验证失败
		paramMap.remove("appKey");
		if(AlipayNotify.verifyReturn(paramMap)){//验证通过
			//交易状态
			String tradeStatus = (String)paramMap.get("result");
			if ("success".equals(tradeStatus)) {
				//获取支付成功后需要回调的业务服务
				IDirectPayService payService = getPayService(payLogDto);
				result.put("dealResult", deal(paramMap, payService));
			}
		}
		
		return result;
	}
	
	//验证异步通知请求是否是支付宝发出来的
	private boolean verifyNotify(Map paramMap){
		try {
			return AlipayNotify.verifyNotify(paramMap);
		} 
		catch (Exception e) {
			return false;
		}
	}
	
	//解析异步通知信息中的notify_data内容，将其保存到map中
	private Map getNotifyData(String notifyData){
		Map<String, String> map = Maps.newHashMap();
		if (notifyData == null || notifyData.equals("")) {
			return map;
		}
		
		String[] infoArray = {"out_trade_no","total_fee","subject","trade_no",
								"trade_status","seller_email","buyer_email","notify_id",
								"notify_type","notify_time"};
		//XML解析notify_data数据
		Document notifyDataDoc = null;
		try {
			notifyDataDoc = DocumentHelper.parseText(notifyData);
		}
		catch (DocumentException e) {
			throw new RuntimeException("解析异步通知数据notify_data xml数据失败!",e.getCause());
		}
		
		for (String info : infoArray) {
			map.put(info, notifyDataDoc.selectSingleNode("//notify/" + info).getText());
		}
		
		return map;
	}
	
	/**
	 * 处理支付成功后的业务逻辑
	 * 
	 * @param request
	 * @throws Exception 
	 */
	public boolean deal(Map paramMap,IDirectPayService directPayService){
		//判断该笔订单是否在商户网站中已经做过处理
		//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
		//如果有做过处理，不执行商户的业务程序
		directPayService.lockTrade(paramMap);
		if (!directPayService.hasBusinessDone(paramMap)) {
			//添加支付宝回执信息
			DirectPayReceiptDto receiptDto = null;
			try {
				receiptDto = addPayReceipt(paramMap);
				return directPayService.doBusiness(paramMap);	
			} 
			catch (Exception e) {
				//执行业务失败
				if (receiptDto != null) {
					//更新 “业务逻辑结果”为失败
					receiptDto.setBussinessStatus(DirectPayReceiptDto.BUSSINESS_STATUS_ERROR);
					directPayReceiptService.edit(receiptDto);
				}
				e.printStackTrace();
				throw new RuntimeException("处理支付宝通知相关业务失败!",e.getCause());
			}
		}
		else{//已处理，返回true
			return true;
		}
	}
	
	/**
	 * 创建即时到账交易请求
	 * <ul>
	 * 	<li>必选:服务器异步通知页面路径{notifyUrl}</li>
	 * 	<li>必选:操作中断返回地址{merchantUrl}</li>
	 * 	<li>必选:商户网站订单系统中唯一订单号{outTradeNO}</li>
	 * 	<li>必选:订单名称{subject}</li>
	 * 	<li>必选:付款金额{totalFee}</li>
	 *  <li>必选:支付成功后回调的业务逻辑服务{payRespService}</li>
	 *  
	 *  <li>同步通知可选，异步通知必选:回调服务执行成功后的跳转页面{paySuccessUrl}</li>
	 *  <li>同步通知可选，异步通知必选:回调服务执行失败后的跳转页面{payFailUrl}</li>
	 *  
	 *  <li>可选:页面跳转同步通知页面路径{callBackUrl}</li>
	 *  <li>可选:订单描述{description}</li>
	 *  
	 * </ul>
	 * @param request
	 * @return
	 */
	public String buildRequest(Map params){
		//支付请求参数验证
		buildValid(params);
		
		//记录交易日志
		addPayLog(params);
		
		String requestInfo = null;
		try {
			requestInfo = getRequestInfo(params);
		} 
		catch (Exception e) {
			throw new RuntimeException("创建支付请求失败", e.getCause());
		}
		
		return requestInfo;
	}
	
	//支付请求参数验证
	private void buildValid(Map params){
		String[] validArray = {"notifyUrl","merchantUrl","outTradeNO","subject","totalFee","payRespService","paySuccessUrl","payFailUrl"};
		for (String valid : validArray) {
			if(StringUtil.isEmpty((String)params.get(valid))){
				throw new RuntimeException("必选字段【" + valid + "】不能为空!");
			}
		}
	}
	
	//根据订单号获取支付请求日子信息
	private DirectPayLogDto getPayLogInfo(Map paramMap){
		//获取订单号
		long orderId = Long.parseLong((String)paramMap.get("out_trade_no"));
		DirectPayLogDto payLogDto = directPayLogService.getLatestByOrderId(orderId);
		if (payLogDto == null) {
			//记录回执信息
			addPayReceipt(paramMap);
			throw new RuntimeException("交易号【" + orderId + "】对应的支付请求日志未找到!");
		}
		
		return payLogDto;
	}
	
	//获取支付成功后的业务服务处理类
	private IDirectPayService getPayService(DirectPayLogDto logDto){
		String serviceBean = logDto.getPayRespService();
		Object payService = null;
		try {
			payService = SpringContextUtils.getBean(serviceBean);
			if (!(payService instanceof IDirectPayService)) {
				throw new Exception();
			}
		} 
		catch (Exception e) {
			throw new RuntimeException("交易号【" + logDto.getOrderId() + "】对应的业务服务处理类未找到或未继承【DirectPayServiceSupport】!");
		}
		
		return (IDirectPayService)payService;
	}
		
	//记录交易日志
	private void addPayLog(Map params){
		DirectPayLogDto directPayLogDto = new DirectPayLogDto();
		directPayLogDto.setOrderId(Long.parseLong((String)params.get("outTradeNO")));
		directPayLogDto.setSubject((String)params.get("subject"));
		directPayLogDto.setTotalFee(Double.parseDouble((String)params.get("totalFee")));
		directPayLogDto.setOrderDesc((String)params.get("description"));
		directPayLogDto.setNotifyUrl((String)params.get("notifyUrl"));
		directPayLogDto.setReturnUrl((String)params.get("callBackUrl"));
		directPayLogDto.setPayRespService((String)params.get("payRespService"));
		directPayLogDto.setPaySuccessUrl((String)params.get("paySuccessUrl"));
		directPayLogDto.setPayFailUrl((String)params.get("payFailUrl"));
		
		directPayLogService.insert(directPayLogDto);
	}
	
	//请求业务参数详细
	//调用授权接口alipay.wap.trade.create.direct获取授权码token
	private String getReqDataToken(Map params) throws Exception{
		//请求业务参数详细
		StringBuilder reqTokenBuilder = new StringBuilder();
		reqTokenBuilder.append("<direct_trade_create_req><notify_url>")
					   .append((String)params.get("notifyUrl"))
					   .append("</notify_url><call_back_url>")
					   .append((String)params.get("callBackUrl"))
					   .append("</call_back_url><seller_account_name>")
					   .append(CommonCfg.LOGIN_NAME)
					   .append("</seller_account_name><out_trade_no>")
					   .append((String)params.get("outTradeNO"))
					   .append("</out_trade_no><subject>")
					   .append((String)params.get("subject"))
					   .append("</subject><total_fee>")
					   .append((String)params.get("totalFee"))
					   .append("</total_fee><merchant_url>")
					   .append((String)params.get("merchantUrl"))
					   .append("</merchant_url></direct_trade_create_req>");
		
		//把请求参数打包成数组
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("service", DirectPayMobileCfg.DIRECT_PAY_CREATE_SERVICE);
		paramMap.put("partner", CommonCfg.PARTNER);
		paramMap.put("_input_charset", CommonCfg.INPUT_CHARSET);
		paramMap.put("sec_id", DirectPayMobileCfg.SIGN_TYPE);
		paramMap.put("format", DirectPayMobileCfg.FORMAT);
		paramMap.put("v", DirectPayMobileCfg.VERSION);
		//请求号，必填，须保证每次请求都是唯一
		paramMap.put("req_id", UtilDate.getOrderNum());	
		paramMap.put("req_data",reqTokenBuilder.toString());
		
		//建立请求
		String htmlTextToken = AlipaySubmit.buildRequest(DirectPayMobileCfg.ALIPAY_GATEWAY_NEW,"", "",paramMap);
		//URLDECODE返回的信息
		htmlTextToken = URLDecoder.decode(htmlTextToken,CommonCfg.INPUT_CHARSET);
		
		//获取token
		return AlipaySubmit.getRequestToken(htmlTextToken);
	}
	
	//获取支付宝支付请求信息
	//根据授权码token调用交易接口alipay.wap.auth.authAndExecute
	private String getRequestInfo(Map params) throws Exception{
		String reqToken = getReqDataToken(params);
		//业务详细
		StringBuilder requestBuilder = new StringBuilder();
		requestBuilder.append("<auth_and_execute_req><request_token>")
					  .append(reqToken)
					  .append("</request_token></auth_and_execute_req>");
		
		//把请求参数打包成数组
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("service", DirectPayMobileCfg.DIRECT_PAY_EXECUTE_SERVICE);
		paramMap.put("partner", CommonCfg.PARTNER);
		paramMap.put("_input_charset", CommonCfg.INPUT_CHARSET);
		paramMap.put("sec_id", DirectPayMobileCfg.SIGN_TYPE);
		paramMap.put("format", DirectPayMobileCfg.FORMAT);
		paramMap.put("v", DirectPayMobileCfg.VERSION);
		paramMap.put("req_data",requestBuilder.toString());
		
		//建立请求
		return AlipaySubmit.buildRequest(DirectPayMobileCfg.ALIPAY_GATEWAY_NEW, paramMap, "get", "确认");
	}
	
	//添加支付宝回执信息
	private DirectPayReceiptDto addPayReceipt(Map paramMap){
		DirectPayReceiptDto dto = new DirectPayReceiptDto();
		dto.setOrderId(Long.parseLong((String)paramMap.get("out_trade_no")));
        if (null != paramMap.get("total_fee"))
        {
            dto.setTotalFee(Double.parseDouble((String)paramMap.get("total_fee")));
        }
		dto.setSubject((String)paramMap.get("subject"));
		dto.setTradeNo((String)paramMap.get("trade_no"));
		dto.setTradeStatus((String)paramMap.get("trade_status"));
		dto.setSellerEmail((String)paramMap.get("seller_email"));
		dto.setBuyerEmail((String)paramMap.get("buyer_email"));
		dto.setNotifyId((String)paramMap.get("notify_id"));
		dto.setNotifyType((String)paramMap.get("notify_type"));
		try
		{
			dto.setNotifyTime(DateUtils.parseDate((String)paramMap.get("notify_time"), new String[]{"yyyy-MM-dd"}));
			dto.setReceiptInfo(om.writeValueAsString(paramMap));
		}
		catch (Exception e)
		{
			logs.warn("从回调信息读取字段出现错误，原因是：" + e.getLocalizedMessage());
		}
		dto.setBussinessStatus(DirectPayReceiptDto.BUSSINESS_STATUS_SUCCESS);
		
		//添加回执
		return directPayReceiptService.insert(dto);
	}
}
