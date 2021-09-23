package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class RouteResultBaidu extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private String startCitycode;

	/**
	 *
	 */
	private double startLat;

	/**
	 *
	 */
	private double startLng;

	/**
	 *
	 */
	private String endCitycode;

	/**
	 *
	 */
	private double endLat;

	/**
	 *
	 */
	private double endLng;

	/**
	 *
	 */
	private int travelType;

	/**
	 *
	 */
	private int travelTime;

	/**
	 *
	 */
	private int travelDist;

	/**
	 *
	 */
	private int travelCost;


	public void setStartCitycode(String startCitycode) {
		this.startCitycode = startCitycode;
	}

	@JsonProperty
	public String getStartCitycode() {
		return startCitycode;
	}

	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}

	@JsonProperty
	public double getStartLat() {
		return startLat;
	}

	public void setStartLng(double startLng) {
		this.startLng = startLng;
	}

	@JsonProperty
	public double getStartLng() {
		return startLng;
	}

	public void setEndCitycode(String endCitycode) {
		this.endCitycode = endCitycode;
	}

	@JsonProperty
	public String getEndCitycode() {
		return endCitycode;
	}

	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}

	@JsonProperty
	public double getEndLat() {
		return endLat;
	}

	public void setEndLng(double endLng) {
		this.endLng = endLng;
	}

	@JsonProperty
	public double getEndLng() {
		return endLng;
	}

	public void setTravelType(int travelType) {
		this.travelType = travelType;
	}

	@JsonProperty
	public int getTravelType() {
		return travelType;
	}

	public void setTravelTime(int travelTime) {
		this.travelTime = travelTime;
	}

	@JsonProperty
	public int getTravelTime() {
		return travelTime;
	}

	public void setTravelDist(int travelDist) {
		this.travelDist = travelDist;
	}

	@JsonProperty
	public int getTravelDist() {
		return travelDist;
	}

	public void setTravelCost(int travelCost) {
		this.travelCost = travelCost;
	}

	@JsonProperty
	public int getTravelCost() {
		return travelCost;
	}
}
