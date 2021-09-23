package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

public class ScenicTime extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long scenicIdFrom;

	/**
	 *
	 */
	private long scenicIdTo;

	/**
	 *
	 */
	private int driving;

	/**
	 *
	 */
	private int transit;

	/**
	 *
	 */
	private int walking;


	public void setScenicIdFrom(long scenicIdFrom) {
		this.scenicIdFrom = scenicIdFrom;
	}

	public long getScenicIdFrom() {
		return scenicIdFrom;
	}

	public void setScenicIdTo(long scenicIdTo) {
		this.scenicIdTo = scenicIdTo;
	}

	public long getScenicIdTo() {
		return scenicIdTo;
	}

	public void setDriving(int driving) {
		this.driving = driving;
	}

	public int getDriving() {
		return driving;
	}

	public void setTransit(int transit) {
		this.transit = transit;
	}

	public int getTransit() {
		return transit;
	}

	public void setWalking(int walking) {
		this.walking = walking;
	}

	public int getWalking() {
		return walking;
	}
}
