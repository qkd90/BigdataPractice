package com.data.data.service.pojo;

import org.codehaus.jackson.annotate.JsonProperty;

import com.hmlyinfo.base.persistent.BaseEntity;

public class Dis extends BaseEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * 城市编号
	 */
	private int					cityCode;

	/**
	 * 
	 */
	private long				sId;

	/**
	 * 
	 */
	private double				sLng;

	/**
	 * 
	 */
	private double				sLat;

	/**
	 * 终点scenicId
	 */
	private long				eId;

	/**
	 * 
	 */
	private double				eLng;

	/**
	 * 
	 */
	private double				eLat;

	/**
	 * 
	 */
	private int					taxiDis;

	/**
	 * 
	 */
	private int					taxiCost;

	/**
	 * 
	 */
	private int					taxiTime;

	/**
	 * 
	 */
	private int					walkDis;

	/**
	 * 
	 */
	private int					walkTime;

	/**
	 * 公交距离
	 */
	private int					busDis;

	/**
	 * 
	 */
	private int					busCost;

	/**
	 * 
	 */
	private int					busTime;

	private int					taxiEx;
	private int					walkEx;
	private int					busEx;
	private int					endScenicPlayHour;

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	@JsonProperty
	public int getCityCode() {
		return cityCode;
	}

	public void setSId(long sId) {
		this.sId = sId;
	}

	@JsonProperty
	public long getSId() {
		return sId;
	}

	public void setSLng(double sLng) {
		this.sLng = sLng;
	}

	@JsonProperty
	public double getSLng() {
		return sLng;
	}

	public void setSLat(double sLat) {
		this.sLat = sLat;
	}

	@JsonProperty
	public double getSLat() {
		return sLat;
	}

	public void setEId(long eId) {
		this.eId = eId;
	}

	@JsonProperty
	public long getEId() {
		return eId;
	}

	public void setELng(double eLng) {
		this.eLng = eLng;
	}

	@JsonProperty
	public double getELng() {
		return eLng;
	}

	public void setELat(double eLat) {
		this.eLat = eLat;
	}

	@JsonProperty
	public double getELat() {
		return eLat;
	}

	public void setTaxiDis(int taxiDis) {
		this.taxiDis = taxiDis;
	}

	@JsonProperty
	public int getTaxiDis() {
		return taxiDis;
	}

	public void setTaxiCost(int taxiCost) {
		this.taxiCost = taxiCost;
	}

	@JsonProperty
	public int getTaxiCost() {
		return taxiCost;
	}

	public void setTaxiTime(int taxiTime) {
		this.taxiTime = taxiTime;
	}

	@JsonProperty
	public int getTaxiTime() {
		return taxiTime;
	}

	public void setWalkDis(int walkDis) {
		this.walkDis = walkDis;
	}

	@JsonProperty
	public int getWalkDis() {
		return walkDis;
	}

	public void setWalkTime(int walkTime) {
		this.walkTime = walkTime;
	}

	@JsonProperty
	public int getWalkTime() {
		return walkTime;
	}

	public void setBusDis(int busDis) {
		this.busDis = busDis;
	}

	@JsonProperty
	public int getBusDis() {
		return busDis;
	}

	public void setBusCost(int busCost) {
		this.busCost = busCost;
	}

	@JsonProperty
	public int getBusCost() {
		return busCost;
	}

	public void setBusTime(int busTime) {
		this.busTime = busTime;
	}

	@JsonProperty
	public int getBusTime() {
		return busTime;
	}

	@JsonProperty
	public int getTaxiEx() {
		return taxiEx;
	}

	public void setTaxiEx(int taxiEx) {
		this.taxiEx = taxiEx;
	}

	@JsonProperty
	public int getWalkEx() {
		return walkEx;
	}

	public void setWalkEx(int walkEx) {
		this.walkEx = walkEx;
	}

	@JsonProperty
	public int getBusEx() {
		return busEx;
	}

	public void setBusEx(int busEx) {
		this.busEx = busEx;
	}

	public static Dis builder(long sId, long eId) {
		Dis dis = new Dis();
		dis.setSId(sId);
		dis.setEId(eId);
		dis.setTaxiTime(30);
		return dis;
	}

	public int getEndScenicPlayHour() {
		// TODO Auto-generated method stub
		return endScenicPlayHour;
	}

	public void setEndScenicPlayHour(int endScenicPlayHour) {
		this.endScenicPlayHour = endScenicPlayHour;
	}

}
