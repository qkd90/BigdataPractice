package com.hmlyinfo.app.soutu.account.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

import java.util.Date;

public class OAuthServer extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 客户端id
	 */
	private int clientId;

	/**
	 * 用户id
	 */
	private long userId;

	/**
	 * 令牌
	 */
	private String token;

	/**
	 * 过期时间
	 */
	private Date expireTime;


	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getClientId() {
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

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}
}
