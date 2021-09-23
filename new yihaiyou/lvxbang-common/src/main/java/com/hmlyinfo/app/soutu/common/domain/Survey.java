package com.hmlyinfo.app.soutu.common.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

public class Survey extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long userId;

	/**
	 *
	 */
	private String content;

	/**
	 *
	 */
	private String name;

	/**
	 *
	 */
	private String email;


	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
