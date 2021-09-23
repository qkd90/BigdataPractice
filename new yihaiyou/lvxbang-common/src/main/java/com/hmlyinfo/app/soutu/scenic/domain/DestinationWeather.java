package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

/**
 * Created by guoshijie on 2014/8/14.
 */
public class DestinationWeather extends BaseEntity {

	private long destinationId;
	private int month;
	private String weather;

	public long getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(long destinationId) {
		this.destinationId = destinationId;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}
}
