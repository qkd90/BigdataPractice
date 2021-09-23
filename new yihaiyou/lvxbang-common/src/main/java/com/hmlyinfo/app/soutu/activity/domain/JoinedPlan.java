package com.hmlyinfo.app.soutu.activity.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class JoinedPlan extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 行程id
	 */
	private long planId;
	/**
	 * 活动id
	 */
	private long activityId;
	/**
	 * 票数
	 */
	private int pollCount;
	/**
	 * 用户id
	 */
	private long userId;

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	@JsonProperty
	public long getPlanId() {
		return planId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	@JsonProperty
	public long getActivityId() {
		return activityId;
	}

	public void setPollCount(int pollCount) {
		this.pollCount = pollCount;
	}

	@JsonProperty
	public int getPollCount() {
		return pollCount;
	}
}
