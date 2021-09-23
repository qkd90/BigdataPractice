package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

public class Collection extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 用户编号
	 */
	private long userId;

	/**
	 * 行程编号
	 */
	private long planId;


	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public long getPlanId() {
		return planId;
	}
}
