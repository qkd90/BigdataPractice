package com.hmlyinfo.app.soutu.pay.ali.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.SpringContextUtils;
import com.hmlyinfo.app.soutu.pay.ali.config.CommonCfg;
import com.hmlyinfo.app.soutu.pay.ali.config.DirectPayWebCfg;
import com.hmlyinfo.app.soutu.pay.ali.direct.web.AlipayNotify;
import com.hmlyinfo.app.soutu.pay.ali.direct.web.AlipaySubmit;
import com.hmlyinfo.app.soutu.pay.ali.domain.DirectPayLogDto;
import com.hmlyinfo.app.soutu.pay.ali.domain.DirectPayReceiptDto;
import com.hmlyinfo.app.soutu.pay.base.service.IDirectPayService;
import com.hmlyinfo.base.exception.BizLogicException;
import com.hmlyinfo.base.util.StringUtil;

/**
 * 
 *
 * <p>Title: DirectPayService.java</p>
 *
 * <p>Description: 支付宝即时到账服务</p>
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
public class DirectPayWebService{
	@Autowired
	private DirectPayLogService directPayLogService;
	
	@Autowired
	private DirectPayReceiptService directPayReceiptService;
	private ObjectMapper om = new ObjectMapper();
	private static final Log logs = LogFactory.getLog(DirectPayWebService.class);
	
	/**
	 * 处理支付宝通知请求
	 * @param paramMap
	 * @return
	 */
	public Map dealNotify(Map paramMap){
		//获取支付请求日志信息
		logs.info("获取支付请求日志信息");
		DirectPayLogDto payLogDto = getPayLogInfo(paramMap);
		//获取支付成功后需要回调的业务服务
		logs.info("获取支付成功后需要回调的业务服务");
		IDirectPayService payService = getPayService(payLogDto);
		boolean dealResult = dealNotify(paramMap,payService);
		
		Map<String, Object> result = Maps.newHashMap();
		result.put("dealResult", dealResult);
		result.put("paySuccessUrl", payLogDto.getPaySuccessUrl());
		result.put("payFailUrl", payLogDto.getPayFailUrl());
		
		return result;
	}
	
	/**
	 * 处理业务逻辑
	 * 请求信息中必须包含支付宝返的所有反馈信息，不能包含自定义参数
	 * @param request
	 * @throws Exception 
	 */
	public boolean dealNotify(Map paramMap,IDirectPayService directPayService){
		//验证是否通知信息由支付宝发起，不能包含自定义参数
		if (!verifyNotify(paramMap)){
			return false;
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表
		//交易状态
		String tradeStatus = (String)paramMap.get("trade_status");
		
		//根据业务逻辑来编写
		//交易完成
		//注意：
		//"TRADE_FINISHED"该种交易状态只在两种情况下出现
		//1、开通了普通即时到账，买家付款成功后。
		//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
		
		//"TRADE_SUCCESS"该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
		if(tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")){
			//判断该笔订单是否在商户网站中已经做过处理
			//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
			//如果有做过处理，不执行商户的业务程序
			directPayService.lockTrade(paramMap);
			if (!directPayService.hasBusinessDone(paramMap)) {
				//添加支付宝回执信息
				DirectPayReceiptDto receiptDto = null;
				try {
					logs.info("添加支付宝回执信息");
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
		
		return false;
	}
	
	/**
	 * 验证响应信息是否由支付宝发出
	 * 请求信息中必须包含支付宝返的所有反馈信息，不能包含自定义参数
	 * @param paramMap
	 * @return
	 */
	public boolean verifyNotify(Map paramMap){
		return AlipayNotify.verify(paramMap);
	}
	
	/**
	 * 创建即时到账交易请求
	 * <ul>
	 * 	<li>必选:服务器异步通知页面路径{notifyUrl}</li>
	 * 	<li>必选:商户网站订单系统中唯一订单号{outTradeNO}</li>
	 * 	<li>必选:订单名称{subject}</li>
	 * 	<li>必选:付款金额{totalFee}</li>
	 *  <li>必选:支付成功后回调的业务逻辑服务{payRespService}</li>
	 *  
	 *  <li>同步通知可选，异步通知必选:回调服务执行成功后的跳转页面{paySuccessUrl}</li>
	 *  <li>同步通知可选，异步通知必选:回调服务执行失败后的跳转页面{payFailUrl}</li>
	 *  
	 *  <li>可选:页面跳转同步通知页面路径{returnUrl}</li>
	 * 	<li>可选:订单描述{description}</li>
	 * 	<li>
	 * 		可选:商品展示地址{showUrl}
	 * 		          需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
	 *  </li>
	 * 	<li>
	 * 		可选:用户在创建交易时所使用机器的IP{exterInvokeIp}
	 * 		          如果商户申请后台开通防钓鱼IP地址检查选项，此字段必填，校验用
	 * 	</li>
	 * 	<li>
	 * 		可选:是否启用防钓鱼时间戳{useAntiPhishingKey,"T"启用，"F"不启用，默认不启用}
	 * 			如果已申请开通防钓鱼时间戳验证，则必需启用
	 *  </li>
	 * </ul>
	 * @param request
	 * @return
	 */
	public String buildRequest(Map params){
		//支付请求参数验证
		buildValid(params);
		
		//记录交易日志
		addPayLog(params);
		
		return getRequestInfo(params);
	}
	
	//支付请求参数验证
	private void buildValid(Map params){
		String[] validArray = {"notifyUrl","outTradeNO","subject","totalFee","payRespService","paySuccessUrl","payFailUrl"};
		for (String valid : validArray) {
			if(StringUtil.isEmpty(params.get(valid).toString())){
				throw new RuntimeException("必选字段【" + valid + "】不能为空!");
			}
		}
	}
	
	//根据订单号获取支付请求日志信息
	private DirectPayLogDto getPayLogInfo(Map paramMap){
		logs.info("根据订单号获取支付请求日志信息");
		//获取订单号
		long orderId = Long.parseLong((String)paramMap.get("out_trade_no"));
		logs.info("订单号" + orderId);
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
		logs.info("记录交易日志");
		DirectPayLogDto directPayLogDto = new DirectPayLogDto();
		directPayLogDto.setOrderId(Long.parseLong((String)params.get("outTradeNO")));
		directPayLogDto.setSubject((String)params.get("subject"));
		directPayLogDto.setTotalFee((Double)params.get("totalFee"));
		directPayLogDto.setOrderDesc((String)params.get("description"));
		directPayLogDto.setUseAntiPhishingKey((String)params.get("useAntiPhishingKey"));
		directPayLogDto.setExterInvokeIp((String)params.get("exterInvokeIp"));
		directPayLogDto.setNotifyUrl((String)params.get("notifyUrl"));
		directPayLogDto.setReturnUrl((String)params.get("returnUrl"));
		directPayLogDto.setShowUrl((String)params.get("showUrl"));
		directPayLogDto.setPayRespService((String)params.get("payRespService"));
		directPayLogDto.setPaySuccessUrl((String)params.get("paySuccessUrl"));
		directPayLogDto.setPayFailUrl((String)params.get("payFailUrl"));
		
		directPayLogService.insert(directPayLogDto);
	}
	
	//获取支付宝支付请求信息
	private String getRequestInfo(Map params){
		//是否启用防钓鱼时间戳
		String useAntiPhishingKey = (String)params.get("useAntiPhishingKey");
		String antiPhishingKey = "";
		if (useAntiPhishingKey != null && useAntiPhishingKey.equals("T")) {
			try {
				antiPhishingKey = AlipaySubmit.query_timestamp();
			} catch (Exception e) {
				throw new BizLogicException("获取防钓鱼时间戳失败！");
			} 
		}
		
		// 把请求参数打包成数组
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("service", DirectPayWebCfg.DIRECT_PAY_SERVICE);
		paramMap.put("partner", CommonCfg.PARTNER);
		paramMap.put("_input_charset", CommonCfg.INPUT_CHARSET);
		paramMap.put("payment_type", DirectPayWebCfg.PAYMENT_TYPE_PRODUCT);
		paramMap.put("seller_email", CommonCfg.LOGIN_NAME);
		
		//服务器异步通知页面路径
		paramMap.put("notify_url", (String)params.get("notifyUrl"));
		//页面跳转同步通知页面路径
		paramMap.put("return_url", (String)params.get("returnUrl"));
		//商户网站订单系统中唯一订单号
		paramMap.put("out_trade_no", (String)params.get("outTradeNO"));
		//订单名称
		paramMap.put("subject", (String)params.get("subject"));
		//付款金额
		paramMap.put("total_fee", params.get("totalFee").toString());
		//订单描述
		paramMap.put("body", (String)params.get("description"));
		//商品展示地址
		paramMap.put("show_url", (String)params.get("showUrl"));
		paramMap.put("anti_phishing_key", antiPhishingKey);
		//用户在创建交易时，该用户当前所使用机器的IP。
		paramMap.put("exter_invoke_ip", (String)params.get("exterInvokeIp"));

		// 建立请求
		logs.info("建立支付宝请求");
		return AlipaySubmit.buildRequest(paramMap, "get", "确认");
	}
	
	//添加支付宝回执信息
	private DirectPayReceiptDto addPayReceipt(Map paramMap){
		logs.info("添加支付宝回执信息");
		DirectPayReceiptDto dto = new DirectPayReceiptDto();
		dto.setOrderId(Long.parseLong((String)paramMap.get("out_trade_no")));
		dto.setTotalFee(Double.parseDouble((String)paramMap.get("total_fee")));
		dto.setSubject((String)paramMap.get("subject"));
		dto.setTradeNo((String)paramMap.get("trade_no"));
		dto.setTradeStatus((String)paramMap.get("trade_status"));
		dto.setSellerEmail((String)paramMap.get("seller_email"));
		dto.setBuyerEmail((String)paramMap.get("buyer_email"));
		dto.setNotifyId((String)paramMap.get("notify_id"));
		dto.setNotifyType((String)paramMap.get("notify_type"));
		try
		{
			logs.info("格式化时间");
			String[] pattern = new String[]{"yyyy-MM", "yyyyMM", "yyyy/MM", "yyyyMMdd", "yyyy-MM-dd",
					"yyyy/MM/dd", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"};
			dto.setNotifyTime(DateUtils.parseDate((String)paramMap.get("notify_time"), pattern));
			dto.setReceiptInfo(om.writeValueAsString(paramMap));
		}
		catch (Exception e)
		{
			logs.warn("从回调信息读取字段出现错误，原因是：" + e.getLocalizedMessage());
		}
		dto.setBussinessStatus(DirectPayReceiptDto.BUSSINESS_STATUS_SUCCESS);
		
		//添加回执
		logs.info("添加回执");
		directPayReceiptService.insert(dto);
		return dto;
	}
}
