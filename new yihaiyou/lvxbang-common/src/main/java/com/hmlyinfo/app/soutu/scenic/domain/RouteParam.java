package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.app.soutu.plan.domain.type.TravelType;

public class RouteParam {
	private City startCity;
	private double startLat;
	private double startLng;
	private City endCity;
	private double endLat;
	private double endLng;
	private TravelType travelType;


	public double getStartLat() {
		return startLat;
	}

	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}

	public double getStartLng() {
		return startLng;
	}

	public void setStartLng(double startLng) {
		this.startLng = startLng;
	}

	public double getEndLat() {
		return endLat;
	}

	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}

	public double getEndLng() {
		return endLng;
	}

	public void setEndLng(double endLng) {
		this.endLng = endLng;
	}

	public TravelType getTravelType() {
		return travelType;
	}

	public void setTravelType(TravelType travelType) {
		this.travelType = travelType;
	}

	public City getStartCity() {
		return startCity;
	}

	public void setStartCity(City startCity) {
		this.startCity = startCity;
	}

	public City getEndCity() {
		return endCity;
	}

	public void setEndCity(City endCity) {
		this.endCity = endCity;
	}

}
