package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class Gallery extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 景点id
	 */
	private int scenicId;

	/**
	 * 画册类型
	 */
	public enum Category {
		cover("亮点", "cover"), scene("风景", "scene"),
		food("美食", "food"), shopping("购物", "shopping"), activity("活动", "activity");
		private String name;
		private String value;

		private Category(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String toString() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	private Category category;

	/**
	 * 画册描述
	 */
	private String content;


	public void setScenicId(int scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public int getScenicId() {
		return scenicId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}
}
