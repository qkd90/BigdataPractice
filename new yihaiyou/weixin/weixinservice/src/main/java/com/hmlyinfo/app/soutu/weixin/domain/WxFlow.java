package com.hmlyinfo.app.soutu.weixin.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class WxFlow extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 访问的url
	 */
	private String url;
	
	/**
	 * 访问的ip
	 */
	private String ip;
	
	/**
	 * 响应时间
	 */
	private String responseTime;
	
	/**
	 * 日志日期
	 */
	private Date date;

	private String urlName;

	private String pv;

	private String uv;
	

	public void setUrl(String url){
		this.url = url;
	}
	@JsonProperty
	public String getUrl(){
		return url;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	@JsonProperty
	public String getIp(){
		return ip;
	}
	public void setResponseTime(String responseTime){
		this.responseTime = responseTime;
	}
	@JsonProperty
	public String getResponseTime(){
		return responseTime;
	}
	public void setDate(Date date){
		this.date = date;
	}
	@JsonProperty
	public Date getDate(){
		return date;
	}
	@JsonProperty
	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	@JsonProperty
	public String getPv() {
		return pv;
	}
	@JsonProperty
	public void setPv(String pv) {
		this.pv = pv;
	}

	public String getUv() {
		return uv;
	}

	public void setUv(String uv) {
		this.uv = uv;
	}
}
