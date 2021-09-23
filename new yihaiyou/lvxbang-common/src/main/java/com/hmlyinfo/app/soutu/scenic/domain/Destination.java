package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by guoshijie on 2014/7/15.
 */
public class Destination extends BaseEntity {

	private Long scenicId;
	private String name;
	private String codeName;
	private String cityCode;
	private String area;
	private String survey;
	private String days;
	private String scenics;
	private transient String weather;
	private String months;
	private String styles;
	private String language;
	private String daysRecommend;
	private String consumer;
	private String seasons;
	private double longitude;
	private double latitude;
	private int mapLevel;
	private int hdMinLevel;
	private int hdMaxLevel;

	/**
	 * 建议游玩天数
	 */
	private int advDay;

	/**
	 * 建议游玩时间
	 */
	private int advCost;

	/**
	 * 游玩天数下限
	 */
	private int minDay;

	/**
	 * 游玩天数上限
	 */
	private int maxDay;

	@JsonProperty
	public Long getScenicId() {
		return scenicId;
	}

	public void setScenicId(Long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	@JsonProperty
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@JsonProperty
	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	@JsonProperty
	public String getStyles() {
		return styles;
	}

	public void setStyles(String styles) {
		this.styles = styles;
	}

	@JsonProperty
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@JsonProperty
	public String getDaysRecommend() {
		return daysRecommend;
	}

	public void setDaysRecommend(String daysRecommend) {
		this.daysRecommend = daysRecommend;
	}

	@JsonProperty
	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	@JsonProperty
	public String getSeasons() {
		return seasons;
	}

	public void setSeasons(String seasons) {
		this.seasons = seasons;
	}

	@JsonProperty
	public String getSurvey() {
		return survey;
	}

	public void setSurvey(String survey) {
		this.survey = survey;
	}

	@JsonProperty
	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	@JsonProperty
	public String getScenics() {
		return scenics;
	}

	public void setScenics(String scenics) {
		this.scenics = scenics;
	}

	@JsonProperty
	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	@JsonProperty
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@JsonProperty
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@JsonProperty
	public int getMapLevel() {
		return mapLevel;
	}

	public void setMapLevel(int mapLevel) {
		this.mapLevel = mapLevel;
	}

	@JsonProperty
	public int getHdMaxLevel() {
		return hdMaxLevel;
	}

	public void setHdMaxLevel(int hdMaxLevel) {
		this.hdMaxLevel = hdMaxLevel;
	}

	@JsonProperty
	public int getHdMinLevel() {
		return hdMinLevel;
	}

	public void setHdMinLevel(int hdMinLevel) {
		this.hdMinLevel = hdMinLevel;
	}

	@JsonProperty
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@JsonProperty
	public int getAdvDay() {
		return advDay;
	}

	public void setAdvDay(int advDay) {
		this.advDay = advDay;
	}

	@JsonProperty
	public int getAdvCost() {
		return advCost;
	}

	public void setAdvCost(int advCost) {
		this.advCost = advCost;
	}

	@JsonProperty
	public int getMinDay() {
		return minDay;
	}

	public void setMinDay(int minDay) {
		this.minDay = minDay;
	}

	@JsonProperty
	public int getMaxDay() {
		return maxDay;
	}

	public void setMaxDay(int maxDay) {
		this.maxDay = maxDay;
	}
}
