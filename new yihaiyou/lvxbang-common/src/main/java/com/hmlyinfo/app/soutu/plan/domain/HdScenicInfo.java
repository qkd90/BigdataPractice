package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class HdScenicInfo extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long scenicId;

	/**
	 *
	 */
	private String cityCode;

	/**
	 *
	 */
	private int zoomLevel;

	/**
	 *
	 */
	private String style;

	/**
	 *
	 */
	private int width;

	/**
	 *
	 */
	private int height;

	/**
	 *
	 */
	private double lng;

	/**
	 *
	 */
	private double lat;

	private boolean operable;

	private String name;

	/**
	 * 拼音
	 */
	private String spellName;

	/**
	 * 等级排序
	 */
	private int rank;

	/**
	 * 景点名称
	 */
	private String scenicName;


	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@JsonProperty
	public String getCityCode() {
		return cityCode;
	}

	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	@JsonProperty
	public int getZoomLevel() {
		return zoomLevel;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@JsonProperty
	public String getStyle() {
		return style;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@JsonProperty
	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@JsonProperty
	public int getHeight() {
		return height;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@JsonProperty
	public double getLng() {
		return lng;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@JsonProperty
	public double getLat() {
		return lat;
	}

	@JsonProperty
	public boolean isOperable() {
		return operable;
	}

	public void setOperable(boolean operable) {
		this.operable = operable;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getSpellName() {
		return spellName;
	}

	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}

	@JsonProperty
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@JsonProperty
	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}
}
