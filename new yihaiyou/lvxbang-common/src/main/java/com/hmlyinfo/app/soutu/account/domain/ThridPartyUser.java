package com.hmlyinfo.app.soutu.account.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class ThridPartyUser extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 第三方传来的openId
	 */
	private String openId;

	/**
	 * 用户编号
	 */
	private long userId;

	/**
	 * 第三方编号
	 */
	private int type;


	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@JsonProperty
	public String getOpenId() {
		return openId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
