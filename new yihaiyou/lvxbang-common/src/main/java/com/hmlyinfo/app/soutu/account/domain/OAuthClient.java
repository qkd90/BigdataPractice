package com.hmlyinfo.app.soutu.account.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class OAuthClient extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 客户端ID
	 */
	private String clientId;

	/**
	 * 客户端密钥
	 */
	private String clientSecret;

	/**
	 * 重定向地址
	 */
	private String redirectUri;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	private Scope scope;

	public enum Scope {
		auth, trust
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	@JsonProperty
	public String getRedirectUri() {
		return redirectUri;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}


}
