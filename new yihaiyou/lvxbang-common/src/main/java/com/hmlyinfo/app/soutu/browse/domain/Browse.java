package com.hmlyinfo.app.soutu.browse.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class Browse extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户IP
	 */
	private String ip;

	/**
	 * 访问地址
	 */
	private String url;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 浏览器类型
	 */
	private String browserType;


	@JsonProperty
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@JsonProperty
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty
	public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}


}
