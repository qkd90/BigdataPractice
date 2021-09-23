package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class PlanComment extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 行程编号
	 */
	private long planId;

	/**
	 * 用户编号
	 */
	private long userId;

	/**
	 * 评论内容
	 */
	private String content;

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	@JsonProperty
	public long getPlanId() {
		return planId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}
}
