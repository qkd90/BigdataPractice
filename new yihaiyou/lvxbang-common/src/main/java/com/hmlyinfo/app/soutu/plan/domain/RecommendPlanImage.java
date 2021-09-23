package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class RecommendPlanImage extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 推荐行程的id
	 */
	private long recommendPlanId;

	/**
	 * 推荐行程的天id
	 */
	private long recommendPlanDayId;

	/**
	 * 推荐行程游玩的id
	 */
	private long recommendPlanTripId;

	/**
	 * 大图片
	 */
	private String coverLarge;

	/**
	 * 中图片
	 */
	private String coverMedium;

	/**
	 * 小图片
	 */
	private String coverSmall;


	public void setRecommendPlanId(long recommendPlanId) {
		this.recommendPlanId = recommendPlanId;
	}

	@JsonProperty
	public long getRecommendPlanId() {
		return recommendPlanId;
	}

	public void setRecommendPlanDayId(long recommendPlanDayId) {
		this.recommendPlanDayId = recommendPlanDayId;
	}

	@JsonProperty
	public long getRecommendPlanDayId() {
		return recommendPlanDayId;
	}

	public void setRecommendPlanTripId(long recommendPlanTripId) {
		this.recommendPlanTripId = recommendPlanTripId;
	}

	@JsonProperty
	public long getRecommendPlanTripId() {
		return recommendPlanTripId;
	}

	public void setCoverLarge(String coverLarge) {
		this.coverLarge = coverLarge;
	}

	@JsonProperty
	public String getCoverLarge() {
		return coverLarge;
	}

	public void setCoverMedium(String coverMedium) {
		this.coverMedium = coverMedium;
	}

	@JsonProperty
	public String getCoverMedium() {
		return coverMedium;
	}

	public void setCoverSmall(String coverSmall) {
		this.coverSmall = coverSmall;
	}

	@JsonProperty
	public String getCoverSmall() {
		return coverSmall;
	}
}
