package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class RecommendPlanTips extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 推荐行程id
	 */
	private long recommendPlanId;
	
	/*
	public enum Type {
        besttime("最佳旅游时间", "1"), clothdev("穿衣指南", "2"), necesing("出行必备","3"), atten("特别注意","4");
        private String name;
        private String value;

        private Type(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String toString() {
            return value;
        }

        public String getName() {
            return name;
        }
    }*/

	/**
	 * 贴士类型
	 */
	private int type;

	private String content;


	@JsonProperty
	public long getRecommendPlanId() {
		return recommendPlanId;
	}

	public void setRecommendPlanId(long recommendPlanId) {
		this.recommendPlanId = recommendPlanId;
	}

	@JsonProperty
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
