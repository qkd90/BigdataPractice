package com.hmlyinfo.app.soutu.weixin.service;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanOrder;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketOrder;
import com.hmlyinfo.base.util.Validate;

/**
 * 微信推送消息
 */
@Service
public class pushMsgService {

	@Autowired
	private WeiXinService weiXinService;
	
	public static int PUSH_NO = 1; // 还未推送
	public static int PUSH_YES = 2; // 成功推送
	public static String TICKET_TEMP_ID = Config.get("TICKET_TEMP_ID");
	public static String BARGAIN_TEMP_ID = Config.get("BARGAIN_TEMP_ID");
	
	/**
	 * 购买门票成功后推送消息
	 * @param order
	 * @param url
	 */
	public boolean pushTicketMsg(String openId, String url, ScenicTicketOrder order) 
	{
    	// 判断是否已经支付
    	if(order.getPushFlag() == ScenicTicketOrder.PUSH_YES || order.getStatus() == ScenicTicketOrder.STATUS_NOT_PAID){
    		return false;
    	}
    	
    	//String url = Config.get("H5_SRV_ADDR_WWW") + "/order/detail/" + order.getId();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String orderDate = sdf.format(order.getOrderDate());
		String[][] infoStrings = { { "first", "恭喜您，您的门票购买成功！" },
				{ "OrderID", order.getOrderNum() }, 
				{ "PkgName", order.getOrderName() },
				{ "TakeOffDate", orderDate },
				{ "remark", "如有疑问，请致电0592-5530252。" } };

		Map<String, Map<String, Object>> paramMap = Maps.newHashMap();
		for (String[] info : infoStrings) {
			Map<String, Object> infoMap = Maps.newHashMap();
			infoMap.put("value", info[1]);
			paramMap.put(info[0], infoMap);
		}
		
		/*Long userId = order.getUserId();
 		Map<String, Object> userMap = Maps.newHashMap();
 		userMap.put("userId", userId);
 		userMap.put("type", "3");
 		List<ThridPartyUser> userList = ThridPartyUserService.list(userMap);
 		String openId = userList.get(0).getOpenId();*/
		
		try {
			weiXinService.wxPushTemplate(TICKET_TEMP_ID, openId, url, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			Validate.isTrue(false, ErrorCode.ERROR_50001, "推送信息发送失败");
		} 
		
		return true;
		
		// TODO:调用者需更新推送状态
		// order.setPushFlag(ScenicTicketOrder.PUSH_YES);
		//orderService.updateScenicTicketOrder(order);
	}
	
	/**
	 * 购买特价行程后推送消息
	 * @param order
	 * @param url
	 */
	public boolean pushBargainPlanMsg(String openId, String url, BargainPlanOrder order) 
	{
    	// 判断是否已经支付
    	if(order.getPushFlag() == BargainPlanOrder.PUSH_YES || order.getStatus() != BargainPlanOrder.STATUS_PAY_SUCCESS){
    		return false;
    	}
    	
    	//String url = Config.get("H5_SRV_ADDR_WWW") + "/order/onsale/order/detail/" + order.getId();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String orderDate = sdf.format(order.getPlayDate());
		String[][] infoStrings = { { "first", "恭喜您，您的特价行程购买成功！" },
				{ "OrderID", " " + order.getId().toString() }, 
				{ "PkgName", " " + order.getOrderName() },
				{ "TakeOffDate", " " + orderDate },
				{ "Remark", "如有疑问，请致电0592-5530252。" } };

		Map<String, Map<String, Object>> paramMap = Maps.newHashMap();
		for (String[] info : infoStrings) {
			Map<String, Object> infoMap = Maps.newHashMap();
			infoMap.put("value", info[1]);
			paramMap.put(info[0], infoMap);
		}
		
		/*Long userId = order.getUserId();
 		Map<String, Object> userMap = Maps.newHashMap();
 		userMap.put("userId", userId);
 		userMap.put("type", "3");
 		List<ThridPartyUser> userList = ThridPartyUserService.list(userMap);
 		String openId = userList.get(0).getOpenId();*/
		
		try {
			weiXinService.wxPushTemplate(BARGAIN_TEMP_ID, openId, url, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			Validate.isTrue(false, ErrorCode.ERROR_50001, "推送信息发送失败");
		}
		
		return true;
		
		// TODO:调用者需更新推送状态
		//order.setPushFlag(BargainPlanOrder.PUSH_YES);
		//orderService.updateBargainPlanOrder(order);
	}
	
	/**
	 * 创建行程后推送消息
	 */
	public void pushCreatePlanMsg() 
	{
		
	}
	
}
