package com.hmlyinfo.app.soutu.delicacy.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

public class DelicacyGood extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long delicacyId;

	/**
	 *
	 */
	private long userId;


	public void setDelicacyId(long delicacyId) {
		this.delicacyId = delicacyId;
	}

	public long getDelicacyId() {
		return delicacyId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}
}
