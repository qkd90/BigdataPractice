package com.hmlyinfo.app.soutu.hotel.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class HotelIndex extends BaseEntity {

	public static final int HOTEL_SOURCE_CTRIP = 1;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private long hotelId;

	/**
	 *
	 */
	private String hotelName;

	/**
	 *
	 */
	private double gcjLng;

	/**
	 *
	 */
	private double gcjLat;

	/**
	 *
	 */
	private int cityCode;

	/**
	 * 酒店数据来源（1.携程）
	 */
	private int hotelSource;


	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	@JsonProperty
	public long getHotelId() {
		return hotelId;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	@JsonProperty
	public String getHotelName() {
		return hotelName;
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

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	@JsonProperty
	public int getCityCode() {
		return cityCode;
	}

	public void setHotelSource(int hotelSource) {
		this.hotelSource = hotelSource;
	}

	@JsonProperty
	public int getHotelSource() {
		return hotelSource;
	}
}
