package com.hmlyinfo.app.soutu.point.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

/**
 * Created by guoshijie on 2014/7/10.
 */
public class Point extends BaseEntity {

	private long userId;
	private int point;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
}
