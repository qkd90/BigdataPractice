package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class Transportation extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 车站名称
	 */
	private String name;

	/**
	 * 百度经度
	 */
	private double lng;

	/**
	 * 百度纬度
	 */
	private double lat;

	/**
	 * 谷歌经度
	 */
	private double gcjLng;

	/**
	 * 谷歌纬度
	 */
	private double gcjLat;

	/**
	 * 城市代码
	 */
	private String cityCode;

	/**
	 * 详细地址
	 */
	private String address;

	/**
	 * 封面图
	 */
	private String coverImg;

	/**
	 * 交通类型:1、火车站，2、机场
	 */
	private int type;

	/**
	 * 到达预留时间
	 */
	private Date arriveTime;

	/**
	 * 离开准备时间
	 */
	private Date leaveTime;


	/**
	 * 交通枢纽所在城市名称
	 * （临时数据，数据库不保存）
	 */
	private String cityName;


	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
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

	public void setGcjLng(double gcjLng) {
		this.gcjLng = gcjLng;
	}

	@JsonProperty
	public double getGcjLng() {
		return gcjLng;
	}

	public void setGcjLat(double gcjLat) {
		this.gcjLat = gcjLat;
	}

	@JsonProperty
	public double getGcjLat() {
		return gcjLat;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@JsonProperty
	public String getCityCode() {
		return cityCode;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JsonProperty
	public String getAddress() {
		return address;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	@JsonProperty
	public String getCoverImg() {
		return coverImg;
	}

	public void setType(int type) {
		this.type = type;
	}

	@JsonProperty
	public int getType() {
		return type;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	@JsonProperty
	public Date getArriveTime() {
		return arriveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	@JsonProperty
	public Date getLeaveTime() {
		return leaveTime;
	}

	@JsonProperty
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
