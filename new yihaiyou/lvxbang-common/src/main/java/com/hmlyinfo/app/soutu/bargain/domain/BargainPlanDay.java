package com.hmlyinfo.app.soutu.bargain.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class BargainPlanDay extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long bargainPlanId;

	private int day;

	public void setBargainPlanId(long bargainPlanId) {
		this.bargainPlanId = bargainPlanId;
	}

	@JsonProperty
	public long getBargainPlanId() {
		return bargainPlanId;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@JsonProperty
	public int getDay() {
		return day;
	}
}
