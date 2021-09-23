package com.hmlyinfo.app.soutu.bargain.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class BargainPlanGallery extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private long bargainPlanId;
	private String coverSmall;
	private String coverLarge;

	public void setBargainPlanId(long bargainPlanId) {
		this.bargainPlanId = bargainPlanId;
	}

	@JsonProperty
	public long getBargainPlanId() {
		return bargainPlanId;
	}

	public void setCoverSmall(String coverSmall) {
		this.coverSmall = coverSmall;
	}

	@JsonProperty
	public String getCoverSmall() {
		return coverSmall;
	}

	public void setCoverLarge(String coverLarge) {
		this.coverLarge = coverLarge;
	}

	@JsonProperty
	public String getCoverLarge() {
		return coverLarge;
	}
}
