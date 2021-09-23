package com.hmlyinfo.app.soutu.weixin.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import com.hmlyinfo.base.persistent.BaseEntity;

public class WxReplyVoice extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 
	 */
	private String title;
	
	/**
	 * 微信语音素材id
	 */
	private String mediaId;
	
	public void setTitle(String title){
		this.title = title;
	}
	@JsonProperty
	public String getTitle(){
		return title;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	@JsonProperty
	public String getMediaId() {
		return mediaId;
	}
}
