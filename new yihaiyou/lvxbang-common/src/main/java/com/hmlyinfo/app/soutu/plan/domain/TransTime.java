package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

/**
 * Created by guoshijie on 2014/8/20.
 */
public class TransTime extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String positions;
	private int transit;
	private int driving;
	private int walking;

	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

	public int getTransit() {
		return transit;
	}

	public void setTransit(int transit) {
		this.transit = transit;
	}

	public int getDriving() {
		return driving;
	}

	public void setDriving(int driving) {
		this.driving = driving;
	}

	public int getWalking() {
		return walking;
	}

	public void setWalking(int walking) {
		this.walking = walking;
	}
}
