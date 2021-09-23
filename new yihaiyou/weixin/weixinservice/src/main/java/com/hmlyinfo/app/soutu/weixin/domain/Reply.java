package com.hmlyinfo.app.soutu.weixin.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import com.hmlyinfo.base.persistent.BaseEntity;

public class Reply extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 关键词
	 */
	private String keyword;
	
	/**
	 * 匹配类型（1->全匹配，2->模糊匹配）
	 */
	private int matchType;
	
	/**
	 * 接收消息类型：1表示接收普通消息（例如文本、图片、语言等）；2表示接收事件消息（例如关注、取消关注等）
	 */
	private int msgType;
	
	/**
	 * 显示在页面上的匹配类型
	 */
	private String showMatchType;
	
	/**
	 * 所在组的ID
	 */
	private Long wxReplyItemGroupId;
	
	@JsonProperty
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@JsonProperty
	public int getMatchType() {
		return matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}
	
	@JsonProperty
	public Long getWxReplyItemGroupId() {
		return wxReplyItemGroupId;
	}

	public void setWxReplyItemGroupId(Long wxReplyItemGroupId) {
		this.wxReplyItemGroupId = wxReplyItemGroupId;
	}

	@JsonProperty
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@JsonProperty
	public String getShowMatchType() {
		return showMatchType;
	}

	public void setShowMatchType(String showMatchType) {
		this.showMatchType = showMatchType;
	}
	@JsonProperty
	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

}
