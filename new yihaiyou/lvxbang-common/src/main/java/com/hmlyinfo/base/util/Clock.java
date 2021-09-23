package com.hmlyinfo.base.util;

/**
 * Created by guoshijie on 2014/7/21.
 */
public class Clock {

	long startTime;
	long previousTime;

	public Clock() {
		start();
	}

	public void start() {
		startTime = System.currentTimeMillis();
		previousTime = startTime;

	}

	public long totalTime() {
		return System.currentTimeMillis() - startTime;
	}

	public long elapseTime() {
		long time = System.currentTimeMillis() - previousTime;
		previousTime = System.currentTimeMillis();
		return time;
	}

}
