package com.hmlyinfo.app.soutu.weixin.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class WxFlowName extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 访问的url
	 */
	private String url;
	
	/**
	 * 访问页面名称
	 */
	private String name;
	
	public void setUrl(String url){
		this.url = url;
	}
	@JsonProperty
	public String getUrl(){
		return url;
	}
	public void setName(String name){
		this.name = name;
	}
	@JsonProperty
	public String getName(){
		return name;
	}
}
