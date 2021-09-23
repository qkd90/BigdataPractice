package com.hmlyinfo.app.soutu.common.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

public class Dictionary extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long type;

	/**
	 *
	 */
	private String name;


	public void setType(long type) {
		this.type = type;
	}

	public long getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
