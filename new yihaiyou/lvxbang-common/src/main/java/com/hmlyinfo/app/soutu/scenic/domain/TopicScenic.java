package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class TopicScenic extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private long topicId;
	private long scenicId;
	private String recommendReason;
	private transient ScenicInfo scenic;


	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	@JsonProperty
	public long getTopicId() {
		return topicId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}

	@JsonProperty
	public String getRecommendReason() {
		return recommendReason;
	}

	@JsonProperty
	public ScenicInfo getScenicInfo() {
		return scenic;
	}

	public void setScenicInfo(ScenicInfo scenic) {
		this.scenic = scenic;
	}
}
