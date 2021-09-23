package com.hmlyinfo.app.soutu.recplan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class RecplanPhoto extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 推荐行程id
	 */
	private long recplanId;

	/**
	 * 行程天id
	 */
	private long recdayId;

	/**
	 * 所属推荐行程trip的编号
	 */
	private long rectripId;

	/**
	 * 大图
	 */
	private String photoLarge;

	/**
	 * 中图
	 */
	private String photoMedium;

	/**
	 * 小图
	 */
	private String photoSmall;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 宽
	 */
	private int width;

	/**
	 * 高
	 */
	private int height;


	public void setRecplanId(long recplanId) {
		this.recplanId = recplanId;
	}

	@JsonProperty
	public long getRecplanId() {
		return recplanId;
	}

	public void setRecdayId(long recdayId) {
		this.recdayId = recdayId;
	}

	@JsonProperty
	public long getRecdayId() {
		return recdayId;
	}

	public void setRectripId(long rectripId) {
		this.rectripId = rectripId;
	}

	@JsonProperty
	public long getRectripId() {
		return rectripId;
	}

	public void setPhotoLarge(String photoLarge) {
		this.photoLarge = photoLarge;
	}

	@JsonProperty
	public String getPhotoLarge() {
		return photoLarge;
	}

	public void setPhotoMedium(String photoMedium) {
		this.photoMedium = photoMedium;
	}

	@JsonProperty
	public String getPhotoMedium() {
		return photoMedium;
	}

	public void setPhotoSmall(String photoSmall) {
		this.photoSmall = photoSmall;
	}

	@JsonProperty
	public String getPhotoSmall() {
		return photoSmall;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getDescription() {
		return description;
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
