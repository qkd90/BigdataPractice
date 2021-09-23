package com.hmlyinfo.app.soutu.weixin.domain;

public class ReplyModel {
	
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息类型
	 */
	private String type;


	private long msgId;


	private String strId;
	/**
	 * 消息id
	 */
	private Long id;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public String getStrId() {
		return strId;
	}

	public void setStrId(String strId) {
		this.strId = strId;
	}
}
