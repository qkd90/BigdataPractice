/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2013 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson.inf;

import org.apache.commons.lang.StringUtils;

/**
 * 消息类型
 * @author L.cm
 * email: 596392912@qq.com
 * site:  http://www.dreamlu.net
 *
 */
public enum EventTypes {
	subscribe("subscribe"), 
	unsubscribe("unsubscribe"), 
	SCAN("SCAN"),
	LOCATION("LOCATION"),
	CLICK("CLICK"),
	VIEW("VIEW"),
	TEMPLATESENDJOBFINISH("模板消息发送成功"),
	MASSSENDJOBFINISH("群发消息发送成功"),
	// 新增客服事件
	kf_create_session("客服会话接入"),
	kf_close_session("客服会话退出");
	private String type;
	
	EventTypes(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 是否需要回复的事件
	 * @return
	 */
	public static boolean isNeedReplyEvent(String event) {
		if (StringUtils.isBlank(event)) {
			return true;
		}
		if (EventTypes.subscribe == EventTypes.valueOf(event)) {
			return true;
		}
		if (EventTypes.CLICK == EventTypes.valueOf(event)) {
			return true;
		}
		return false;
	}
}
