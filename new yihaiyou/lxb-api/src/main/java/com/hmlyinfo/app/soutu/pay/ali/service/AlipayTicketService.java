package com.hmlyinfo.app.soutu.pay.ali.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.common.service.SequenceService;
import com.hmlyinfo.app.soutu.order.domain.Order;
import com.hmlyinfo.app.soutu.order.service.OrderService;
import com.hmlyinfo.app.soutu.pay.ali.service.impl.DirectPayWebService;
import com.hmlyinfo.app.soutu.scenicTicket.domain.PayOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketOrder;
import com.hmlyinfo.app.soutu.scenicTicket.service.PayOrderService;
import com.hmlyinfo.app.soutu.scenicTicket.service.ScenicTicketOrderService;
import com.hmlyinfo.base.util.Validate;

@Service
public class AlipayTicketService {
	
	private static int PAY_TYPE_ALI = 102;
	
	// 支付宝回调地址
	public static String DIRECT_NOTIFY_URL = Config.get("DIRECT_NOTIFY_URL");
	
	// 支付宝回调地址(2.6.0)
	public static String ORDER_ALI_NOTIFY_URL = Config.get("ORDER_ALI_NOTIFY_URL");
	
	
	@Autowired
	private ScenicTicketOrderService scenicTicketOrderService;
	@Autowired
	private DirectPayWebService directPayService;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private OrderService orderService;
	
	// 用支付宝支付订单
	@Transactional
	public Map<String, Object> payOrder(Map<String, Object> paramMap){
		
		// 查询订单信息
		long scenicTicketOrderId = Long.parseLong((String) paramMap.get("scenicTicketOrderId"));
		ScenicTicketOrder order = scenicTicketOrderService.info(scenicTicketOrderId);
		// 若订单已经支付则抛出异常
		if(order.getStatus() != ScenicTicketOrder.STATUS_NOT_PAID){
			Validate.isTrue(false, ErrorCode.ERROR_56008, "订单未处于待支付状态，不可支付");
		}
		// 创建支付单
		PayOrder payOrder = new PayOrder();
		//生成订单编号
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = new Date();
		String orderTime = df.format(new Date());
        String orderNum = orderTime + PAY_TYPE_ALI + sequenceService.getPayAliSeq();
        payOrder.setOrderNum(orderNum);
		payOrder.setUserId(order.getUserId());
		payOrder.setBody(order.getOrderName());
		payOrder.setTotalFee(order.getTotalFee());
		payOrderService.insert(payOrder);
		// 更新order
		order.setPayOrder(payOrder.getId());
		order.setPayType(PAY_TYPE_ALI);//需要在ScenicTicketOrder中添加PAY_TYPE＿ALI
		scenicTicketOrderService.update(order);
		
		// 创建支付请求
		String returnUrl = (String) paramMap.get("returnUrl");
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("notifyUrl", DIRECT_NOTIFY_URL);
		orderMap.put("returnUrl", returnUrl);
		orderMap.put("outTradeNO", payOrder.getOrderNum());
		
		
		orderMap.put("subject", order.getOrderName());
		orderMap.put("totalFee", order.getTotalFee());
		orderMap.put("payRespService", "dealNotifyService");
		orderMap.put("paySuccessUrl", "http://www.lvxbang.com");
		orderMap.put("payFailUrl", "http://www.lvxbang.com");
		
//		notifyUrl,outTradeNO,subject,totalFee,payRespService,paySuccessUrl,payFailUrl
		
		
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("requestInfo", directPayService.buildRequest(orderMap));
		return resultMap;
	}
	
	
	// 用支付宝支付订单(2.6.0)
	@Transactional
	public Map<String, Object> newPayOrder(Map<String, Object> paramMap) {

		// 查询订单信息
		long orderId = Long.parseLong((String) paramMap
				.get("orderId"));
		Order order = orderService.info(orderId);
		// 若订单已经支付则抛出异常
		if (order.getStatus() == Order.STATUS_PAIED) {
			Validate.isTrue(false, ErrorCode.ERROR_56008, "订单未处于待支付状态，不可支付");
		}
		// 创建支付单
		PayOrder payOrder = new PayOrder();
		// 生成订单编号
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = new Date();
		String orderTime = df.format(new Date());
		String orderNum = orderTime + PAY_TYPE_ALI
				+ sequenceService.getPayAliSeq();
		payOrder.setOrderNum(orderNum);
		payOrder.setUserId(order.getUserId());
		payOrder.setBody("行程" + order.getSourceId() + "对应的总订单");
		payOrder.setTotalFee(order.getPrice());
		payOrderService.insert(payOrder);
		// 更新order
		order.setPayOrderId(payOrder.getId());
		order.setPayType(PAY_TYPE_ALI);
		orderService.update(order);

		// 创建支付请求
		String returnUrl = (String) paramMap.get("returnUrl");
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("notifyUrl", ORDER_ALI_NOTIFY_URL);
		orderMap.put("returnUrl", returnUrl);
		orderMap.put("outTradeNO", payOrder.getOrderNum());

		orderMap.put("subject", payOrder.getBody());
		orderMap.put("totalFee", order.getPrice());
		orderMap.put("payRespService", "dealOrderNotifyService");
		orderMap.put("paySuccessUrl", "http://www.lvxbang.com");
		orderMap.put("payFailUrl", "http://www.lvxbang.com");

		// notifyUrl,outTradeNO,subject,totalFee,payRespService,paySuccessUrl,payFailUrl

		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("requestInfo", directPayService.buildRequest(orderMap));
		return resultMap;
	}

}
