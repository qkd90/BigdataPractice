/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2013 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson.inf;

/**
 * 消息类型
 * @author L.cm
 * email: 596392912@qq.com
 * site:  http://www.dreamlu.net
 *
 */
public enum MsgTypes {
	text("文本消息"), 
	location("地理位置消息"), 
	image("图片消息"),
	link("链接消息"),
	voice("语音消息"),
	event("事件消息"),
	video("视频消息"),
	news("图文"),
	music("音乐"),
	verify("verify");
	private String type;
	
	MsgTypes(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
