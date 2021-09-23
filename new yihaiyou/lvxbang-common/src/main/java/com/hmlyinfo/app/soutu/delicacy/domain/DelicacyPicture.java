package com.hmlyinfo.app.soutu.delicacy.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class DelicacyPicture extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 美食编号
	 */
	private long delicacyId;

	/**
	 * 美食图片
	 */
	private String foodPicture;

	/**
	 *
	 */
	private int width;

	/**
	 *
	 */
	private int height;


	public void setDelicacyId(long delicacyId) {
		this.delicacyId = delicacyId;
	}

	@JsonProperty
	public long getDelicacyId() {
		return delicacyId;
	}

	public void setFoodPicture(String foodPicture) {
		this.foodPicture = foodPicture;
	}

	@JsonProperty
	public String getFoodPicture() {
		return foodPicture;
	}

	@JsonProperty
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@JsonProperty
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
