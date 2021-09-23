package com.hmlyinfo.app.soutu.account.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

import java.util.Date;

public class AccessToken extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 客户端ID
	 */
	private String clientId;

	/**
	 * 用户编号
	 */
	private long userId;

	/**
	 * 令牌
	 */
	private String token;

	/**
	 * 临时授权码
	 */
	private String code;

	/**
	 * 过期时间
	 */
	private Date expiryDate;


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
