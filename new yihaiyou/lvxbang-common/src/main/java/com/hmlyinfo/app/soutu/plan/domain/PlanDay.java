package com.hmlyinfo.app.soutu.plan.domain;


import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PlanDay extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 行程计划编号
	 */
	private long planId;

	/**
	 * 第几天
	 */
	private int days;

	/**
	 * 耗时
	 */
	private String spendTime;

	/**
	 * 公里
	 */
	private String kilometre;

	/**
	 * 日期
	 */
	private Date date;

	/**
	 * 门票花费
	 */
	private int ticketCost;

	/**
	 * 包含套票的门票花费
	 */
	private int includeSeasonticketCost;

	/**
	 * 交通花费
	 */
	private int trafficCost;

	/**
	 * 酒店花费
	 */
	private int hotelCost;

	/**
	 * 景点游玩时间
	 */
	private int playTime;

	/**
	 * 交通时间
	 */
	private int trafficTime;

	/**
	 * 行程天的酒店
	 */
	private transient CtripHotel hotel;
	/**
	 * 景点个数
	 */
	private transient int scenicCount;
	/**
	 * 城市编号
	 */
	private transient int cityCode;
	/**
	 * 出发城市名称
	 */
	private transient String cityName;

	/**
	 * 到达城市编号
	 */
	private transient int endCityCode;

	private long daysId;


	/**
	 * 当天总游玩时间
	 */
	private transient int totalTime;
	/**
	 * 当天总花费金额
	 */
	private transient int totalPrice;
	/**
	 * 当天总花费金额（包含套票）
	 */
	private transient int totalSeasonTicketPrice;

	/**
	 * 当天包含的景点列表
	 */
	private transient List<Map<String, Object>> scenicList;

	/**
	 * 当天包含的Trip列表
	 */
	private transient List<PlanTrip> planTripList;

	/**
	 * 天数
	 */
	private transient String endCityName;

	@JsonProperty
	public String getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(String spendTime) {
		this.spendTime = spendTime;
	}

	@JsonProperty
	public String getKilometre() {
		return kilometre;
	}

	public void setKilometre(String kilometre) {
		this.kilometre = kilometre;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	@JsonProperty
	public long getPlanId() {
		return planId;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@JsonProperty
	public int getDays() {
		return days;
	}

	@JsonProperty
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonProperty
	public CtripHotel getHotel() {
		return hotel;
	}

	public void setHotel(CtripHotel hotel) {
		this.hotel = hotel;
	}

	@JsonProperty
	public List<Map<String, Object>> getScenicList() {
		return scenicList;
	}

	public void setScenicList(List<Map<String, Object>> scenicList) {
		this.scenicList = scenicList;
	}

	@JsonProperty
	public int getScenicCount() {
		return scenicCount;
	}

	public void setScenicCount(int scenicCount) {
		this.scenicCount = scenicCount;
	}

	@JsonProperty
	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	@JsonProperty
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@JsonProperty
	public int getEndCityCode() {
		return endCityCode;
	}

	public void setEndCityCode(int endCityCode) {
		this.endCityCode = endCityCode;
	}

	@JsonProperty
	public String getEndCityName() {
		return endCityName;
	}

	public void setEndCityName(String endCityName) {
		this.endCityName = endCityName;
	}

	@JsonProperty
	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	@JsonProperty
	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setDaysId(long daysId) {
		this.daysId = daysId;
	}

	@JsonProperty
	public long getdaysId() {
		return daysId;
	}

	@JsonProperty
	public int getTicketCost() {
		return ticketCost;
	}

	public void setTicketCost(int ticketCost) {
		this.ticketCost = ticketCost;
	}

	@JsonProperty
	public int getTrafficCost() {
		return trafficCost;
	}

	public void setTrafficCost(int trafficCost) {
		this.trafficCost = trafficCost;
	}

	@JsonProperty
	public int getHotelCost() {
		return hotelCost;
	}

	public void setHotelCost(int hotelCost) {
		this.hotelCost = hotelCost;
	}

	@JsonProperty
	public int getPlayTime() {
		return playTime;
	}

	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}

	@JsonProperty
	public int getTrafficTime() {
		return trafficTime;
	}

	public void setTrafficTime(int trafficTime) {
		this.trafficTime = trafficTime;
	}

	@JsonProperty
	public int getIncludeSeasonticketCost() {
		return includeSeasonticketCost;
	}

	public void setIncludeSeasonticketCost(int includeSeasonticketCost) {
		this.includeSeasonticketCost = includeSeasonticketCost;
	}

	@JsonProperty
	public int getTotalSeasonTicketPrice() {
		return totalSeasonTicketPrice;
	}

	public void setTotalSeasonTicketPrice(int totalSeasonTicketPrice) {
		this.totalSeasonTicketPrice = totalSeasonTicketPrice;
	}

	@JsonProperty
	public List<PlanTrip> getPlanTripList() {
		return planTripList;
	}

	public void setPlanTripList(List<PlanTrip> planTripList) {
		this.planTripList = planTripList;
	}
}
