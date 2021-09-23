package com.hmlyinfo.app.soutu.activity.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class PlanPoll extends BaseEntity {

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
	private long joinedPlanId;
	/**
	 * 活动id
	 */
	private long activityId;

	private String ip;

	@JsonProperty
	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setJoinedPlanId(long joinedPlanId) {
		this.joinedPlanId = joinedPlanId;
	}

	@JsonProperty
	public long getJoinedPlanId() {
		return joinedPlanId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
