package com.hmlyinfo.app.soutu.weixin.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

import org.codehaus.jackson.annotate.JsonProperty;

public class WxReplyLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String event;
	
	 /**
     * 记录用户操作参数
     */
    private String eventKey;
	
	/**
	 * 自定义回复的id，转到多客服存-1
	 */
	private long type;
	
	/**
	 * 
	 */
	private String openId;
	
	/**
	 * 用户发送的关键字
	 */
	private String content;
	
	/**
	 * T：成功返回信息（可能是自定义回复或是转发多客服）；F: 处理过程发生异常
	 */
	private String status;
	
	/**
	 * 记录异常原因
	 */
	private String exception;

	private Integer counts;

	public void setEvent(String event){
		this.event = event;
	}
	@JsonProperty
	public String getEvent(){
		return event;
	}
	public void setType(long type){
		this.type = type;
	}
	@JsonProperty
	public long getType(){
		return type;
	}
	public void setOpenId(String openId){
		this.openId = openId;
	}
	@JsonProperty
	public String getOpenId(){
		return openId;
	}
	public void setContent(String content){
		this.content = content;
	}
	@JsonProperty
	public String getContent(){
		return content;
	}
	public void setStatus(String status){
		this.status = status;
	}
	@JsonProperty
	public String getStatus(){
		return status;
	}
	public void setException(String exception){
		this.exception = exception;
	}
	@JsonProperty
	public String getException(){
		return exception;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	@JsonProperty
	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	@JsonProperty
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
}
