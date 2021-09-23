package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

public class NoteDay extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 游记编号
	 */
	private long noteId;

	/**
	 * 旅行日期
	 */
	private Date travelDate;

	/**
	 * 当天气候
	 */
	private String weather;

	/**
	 * 当前气候类型
	 */
	private String weatherType;

	/**
	 * 空气质量
	 */
	private String airQuality;

	/**
	 * 描述
	 */
	private String dayDesc;

	private List<NoteScenic> scenicList;


	@JsonProperty
	public List<NoteScenic> getScenicList() {
		return scenicList;
	}

	public void setScenicList(List<NoteScenic> scenicList) {
		this.scenicList = scenicList;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	@JsonProperty
	public long getNoteId() {
		return noteId;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	@JsonProperty
	public Date getTravelDate() {
		return travelDate;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	@JsonProperty
	public String getWeather() {
		return weather;
	}

	public void setDayDesc(String dayDesc) {
		this.dayDesc = dayDesc;
	}

	@JsonProperty
	public String getDayDesc() {
		return dayDesc;
	}

	@JsonProperty
	public String getWeatherType() {
		return weatherType;
	}

	public void setWeatherType(String weatherType) {
		this.weatherType = weatherType;
	}

	@JsonProperty
	public String getAirQuality() {
		return airQuality;
	}

	public void setAirQuality(String airQuality) {
		this.airQuality = airQuality;
	}
}
