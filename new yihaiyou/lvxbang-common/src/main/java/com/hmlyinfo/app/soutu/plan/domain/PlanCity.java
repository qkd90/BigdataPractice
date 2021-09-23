package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

public class PlanCity extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 计划编号
	 */
	private long planId;

	/**
	 * 行程天编号
	 */
	private long planDaysId;

	/**
	 * 城市编号
	 */
	private long cityId;


	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public long getPlanId() {
		return planId;
	}

	public void setPlanDaysId(long planDaysId) {
		this.planDaysId = planDaysId;
	}

	public long getPlanDaysId() {
		return planDaysId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getCityId() {
		return cityId;
	}
}
