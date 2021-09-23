package com.hmlyinfo.app.soutu.plan.domain;

/**
 * Created by guoshijie on 2014/12/11.
 */
public class Point {
	public long id;
	public double x;
	public double y;
	public int cluster;

	/**
	 * 行程点名称
	 */
	public String name;

	/**
	 * 行程点经度
	 */
	public double lng;

	/**
	 * 行程点纬度
	 */
	public double lat;

	/**
	 *
	 */
	public long scenicId;

	/**
	 *
	 */
	public long father;

	/**
	 * 行程点所在城市
	 */
	public String cityName;
}
