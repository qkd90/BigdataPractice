package com.hmlyinfo.app.soutu.hotel.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class CtripPrice extends BaseEntity {

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
	private Date expireDate;

	/**
	 *
	 */
	private double price;

	/**
	 *
	 */
	private String description;

	/**
	 *
	 */
	private int ratePlanCode;
	private boolean hasBreakfast;
	private boolean hasBroadband;


	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	@JsonProperty
	public long getHotelId() {
		return hotelId;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@JsonProperty
	public Date getExpireDate() {
		return expireDate;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@JsonProperty
	public double getPrice() {
		return price;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setRatePlanCode(int ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}

	@JsonProperty
	public int getRatePlanCode() {
		return ratePlanCode;
	}

	@JsonProperty
	public boolean isHasBreakfast() {
		return hasBreakfast;
	}

	public void setHasBreakfast(boolean hasBreakfast) {
		this.hasBreakfast = hasBreakfast;
	}

	@JsonProperty
	public boolean isHasBroadband() {
		return hasBroadband;
	}

	public void setHasBroadband(boolean hasBroadband) {
		this.hasBroadband = hasBroadband;
	}
}
