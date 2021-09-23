package com.hmlyinfo.app.soutu.pay.ali.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.order.service.OrderService;
import com.hmlyinfo.app.soutu.pay.ali.domain.DirectPayReceiptDto;
import com.hmlyinfo.app.soutu.pay.base.service.IDirectPayService;
import com.hmlyinfo.app.soutu.scenicTicket.domain.PayOrder;
import com.hmlyinfo.app.soutu.scenicTicket.service.PayOrderService;

@Service
public class DealOrderNotifyService implements IDirectPayService{

	
	public static final Logger logger = Logger.getLogger(DealNotifyService.class);
	
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private DirectPayReceiptService dtoService;
	
	@Override
	public boolean doBusiness(Map paramMap) {
		
		String tradeStatus = (String)paramMap.get("trade_status");
		// 用户付款成功，门票付款
		if (tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {
			logger.info("用户付款成功,门票付款");
			// 去付款
			Long orderNum = Long.valueOf(paramMap.get("out_trade_no").toString());
			
			logger.info("orderNum=" + orderNum);
			
			Map<String, Object> payOrderMap = new HashMap<String, Object>();
			payOrderMap.put("orderNum", orderNum.toString());
			List<PayOrder> payOrderList = payOrderService.list(payOrderMap);
			long payOrderId = -1;
			if(payOrderList.size() > 0){
				payOrderId = payOrderList.get(0).getId();
				
				logger.info("payOrderId=" + payOrderId);
			}
			logger.info("去经销商付款……");
			try {
				orderService.pay(payOrderId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("部分订单付款失败", e);
			}
			// 更新payOrder中的支付宝交易单号
			logger.info("更新payOrder中的支付宝交易单号");
			PayOrder payOrder = payOrderService.info(payOrderId);
			if (paramMap.get("trade_no") != null) {
				payOrder.setPreOrderId((String) paramMap.get("trade_no"));
			}
			payOrderService.update(payOrder);
			return true;
		}
		return false;
	}

	@Override
	public boolean hasBusinessDone(Map paramMap) {
		// 根据有没有日志判断任务有没有被执行
		Map<String, Object> dtoMap = new HashMap<String, Object>();
		dtoMap.put("orderId", paramMap.get("out_trade_no"));
		dtoMap.put("tradeNo", paramMap.get("trade_no"));
		List<DirectPayReceiptDto> dtoList = dtoService.list(dtoMap);
		logger.info("根据有没有日志判断任务有没有被执行" + dtoMap);
		if(dtoList.size() > 0){
			logger.info("已经被执行");
			return true;
		}
		logger.info("未被执行");
		return false;
	}

	@Override
	public void lockTrade(Map paramMap) {
		// TODO Auto-generated method stub
		
	}
}
