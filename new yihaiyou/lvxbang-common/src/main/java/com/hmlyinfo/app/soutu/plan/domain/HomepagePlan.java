package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

public class HomepagePlan extends BaseEntity {

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
	 * 主要城市编号
	 */
	private long cityId;

	/**
	 * 推荐权重
	 */
	private long recommendWeight;

	/**
	 * 推荐人
	 */
	private long recommendName;

	/**
	 * 推荐理由
	 */
	private String recommendReason;

	/**
	 * 去过的人数
	 */
	private int goneCount;

	/**
	 * 将前往的人数
	 */
	private int goingCount;


	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public long getPlanId() {
		return planId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getCityId() {
		return cityId;
	}

	public void setRecommendWeight(long recommendWeight) {
		this.recommendWeight = recommendWeight;
	}

	public long getRecommendWeight() {
		return recommendWeight;
	}

	public void setRecommendName(long recommendName) {
		this.recommendName = recommendName;
	}

	public long getRecommendName() {
		return recommendName;
	}

	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}

	public String getRecommendReason() {
		return recommendReason;
	}

	public int getGoneCount() {
		return goneCount;
	}

	public void setGoneCount(int goneCount) {
		this.goneCount = goneCount;
	}

	public int getGoingCount() {
		return goingCount;
	}

	public void setGoingCount(int goingCount) {
		this.goingCount = goingCount;
	}
}
