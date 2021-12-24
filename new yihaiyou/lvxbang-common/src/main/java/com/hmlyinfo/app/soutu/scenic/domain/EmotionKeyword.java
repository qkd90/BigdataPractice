package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class EmotionKeyword extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 关键词
	 */
	private String keyword;

	/**
	 * 计数
	 */
	private int count;

	/**
	 * 景点id
	 */
	private int scenicId;


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@JsonProperty
	public String getKeyword() {
		return keyword;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@JsonProperty
	public int getCount() {
		return count;
	}

	public void setScenicId(int scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public int getScenicId() {
		return scenicId;
	}
}