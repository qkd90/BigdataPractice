package com.hmlyinfo.app.soutu.recplan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class RecplanDay extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 行程计划编号
	 */
	private long recplanId;

	/**
	 * 第几天
	 */
	private int day;

	/**
	 * 景点数
	 */
	private int scenics;

	/**
	 * 游玩时间
	 */
	private int hours;

	/**
	 * 经过的所有城市
	 */
	private String citys;

	/**
	 * 当天概述
	 */
	private String description;

	/**
	 * 住宿推荐
	 */
	private String hotel;

	/**
	 * 美食推荐
	 */
	private String food;

	/**
	 * 门票花费
	 */
	private double ticketCost;

	/**
	 * 包含套票的门票花费
	 */
	private double seasonticketCost;

	/**
	 * 此天下的行程点列表
	 */
	private List<RecplanTrip> recplanTrips;

	/**
	 * 交通花费
	 */
	private double trafficCost;

	/**
	 * 酒店花费
	 */
	private double hotelCost;

	/**
	 * 交通时间
	 */
	private int trafficTime;

	/**
	 * 总花费
	 */
	private double cost;

	/**
	 * 包含套票的总花费
	 */
	private double includeSeasonticketCost;

	/**
	 * 经过的所有城市名，临时变量不存数据库
	 */
	private List<String> cityNames;

	public void setRecplanId(long recplanId) {
		this.recplanId = recplanId;
	}

	@JsonProperty
	public long getRecplanId() {
		return recplanId;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@JsonProperty
	public int getDay() {
		return day;
	}

	public void setScenics(int scenics) {
		this.scenics = scenics;
	}

	@JsonProperty
	public int getScenics() {
		return scenics;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	@JsonProperty
	public int getHours() {
		return hours;
	}

	public void setCitys(String citys) {
		this.citys = citys;
	}

	@JsonProperty
	public String getCitys() {
		return citys;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	@JsonProperty
	public String getHotel() {
		return hotel;
	}

	public void setFood(String food) {
		this.food = food;
	}

	@JsonProperty
	public String getFood() {
		return food;
	}

	public void setTicketCost(double ticketCost) {
		this.ticketCost = ticketCost;
	}

	@JsonProperty
	public double getTicketCost() {
		return ticketCost;
	}

	public void setSeasonticketCost(double seasonticketCost) {
		this.seasonticketCost = seasonticketCost;
	}

	@JsonProperty
	public double getSeasonticketCost() {
		return seasonticketCost;
	}

	@JsonProperty
	public List<RecplanTrip> getRecplanTrips() {
		return recplanTrips;
	}

	public void setRecplanTrips(List<RecplanTrip> recplanTrips) {
		this.recplanTrips = recplanTrips;
	}

	@JsonProperty
	public double getTrafficCost() {
		return trafficCost;
	}

	public void setTrafficCost(double trafficCost) {
		this.trafficCost = trafficCost;
	}

	@JsonProperty
	public double getHotelCost() {
		return hotelCost;
	}

	public void setHotelCost(double hotelCost) {
		this.hotelCost = hotelCost;
	}

	@JsonProperty
	public int getTrafficTime() {
		return trafficTime;
	}

	public void setTrafficTime(int trafficTime) {
		this.trafficTime = trafficTime;
	}

	@JsonProperty
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@JsonProperty
	public double getIncludeSeasonticketCost() {
		return includeSeasonticketCost;
	}

	public void setIncludeSeasonticketCost(double includeSeasonticketCost) {
		this.includeSeasonticketCost = includeSeasonticketCost;
	}

	@JsonProperty
	public List<String> getCityNames() {
		return cityNames;
	}

	public void setCityNames(List<String> cityNames) {
		this.cityNames = cityNames;
	}
}
