package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class City extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 城市名
	 */
	private String name;

	/**
	 * 城市区号
	 */
	private int cityCode;

	/**
	 * 上一级城市
	 */
	private long father;

	/**
	 * 城市级别
	 */
	private int level;

	/**
	 * 城市中每两个景点之间的时间
	 */
	private int hour;

	@JsonProperty
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setFather(long father) {
		this.father = father;
	}

	@JsonProperty
	public long getFather() {
		return father;
	}


	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	@JsonProperty
	public int getCityCode() {
		return cityCode;
	}

	@JsonProperty
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}
}
