package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class HdMapLevel extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long destinationId;

	/**
	 *
	 */
	private int level;

	/**
	 *
	 */
	private double north;

	/**
	 *
	 */
	private double south;

	/**
	 *
	 */
	private double west;

	/**
	 *
	 */
	private double east;

	/**
	 *
	 */
	private double hdNorth;

	/**
	 *
	 */
	private double hdSouth;

	/**
	 *
	 */
	private double hdWest;

	/**
	 *
	 */
	private double hdEast;

	public void setDestinationId(long destinationId) {
		this.destinationId = destinationId;
	}

	@JsonProperty
	public long getDestinationId() {
		return destinationId;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@JsonProperty
	public int getLevel() {
		return level;
	}

	public void setNorth(double north) {
		this.north = north;
	}

	@JsonProperty
	public double getNorth() {
		return north;
	}

	public void setSouth(double south) {
		this.south = south;
	}

	@JsonProperty
	public double getSouth() {
		return south;
	}

	public void setWest(double west) {
		this.west = west;
	}

	@JsonProperty
	public double getWest() {
		return west;
	}

	public void setEast(double east) {
		this.east = east;
	}

	@JsonProperty
	public double getEast() {
		return east;
	}

	@JsonProperty
	public double getHdNorth() {
		return hdNorth;
	}

	public void setHdNorth(double hdNorth) {
		this.hdNorth = hdNorth;
	}

	@JsonProperty
	public double getHdSouth() {
		return hdSouth;
	}

	public void setHdSouth(double hdSouth) {
		this.hdSouth = hdSouth;
	}

	@JsonProperty
	public double getHdWest() {
		return hdWest;
	}

	public void setHdWest(double hdWest) {
		this.hdWest = hdWest;
	}

	@JsonProperty
	public double getHdEast() {
		return hdEast;
	}

	public void setHdEast(double hdEast) {
		this.hdEast = hdEast;
	}

}
