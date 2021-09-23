package com.hmlyinfo.app.soutu.weixin.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import com.hmlyinfo.base.persistent.BaseEntity;

public class ReplyItem extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 消息类型：1表示文本，2表示图文，3表示语音，4表示地图位置，5表示微信卡券
	 */
	private String msgType;
	
	/**
	 * 消息ID
	 */
	private Long msgId;
	
	/**
	 * 自动回复组的编号
	 */
	private int orderNum;
	
	/**
	 * 所在组的ID
	 */
	private Long wxReplyItemGroupId;
	
	@JsonProperty
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	@JsonProperty
	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	@JsonProperty
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	
	@JsonProperty
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@JsonProperty
	public Long getWxReplyItemGroupId() {
		return wxReplyItemGroupId;
	}

	public void setWxReplyItemGroupId(Long wxReplyItemGroupId) {
		this.wxReplyItemGroupId = wxReplyItemGroupId;
	}
}
