package com.data.data.hmly.service.area.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "destination")
public class Destination extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "scenic_id")
    private Long scenicId;
    @Column(name = "name")
    private String name;
    @Column(name = "code_name")
    private String codeName;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "area")
    private String area;
    @Column(name = "survey")
    private String survey;
    @Column(name = "days")
    private String days;
    @Column(name = "scenics")
    private String scenics;
    @Column(name = "months")
    private String months;
    @Column(name = "styles")
    private String styles;
    @Column(name = "language")
    private String language;
    @Column(name = "days_recommend")
    private String daysRecommend;
    @Column(name = "consumer")
    private String consumer;
    @Column(name = "feature")
    private String feature;
    @Column(name = "trip_tips")
    private String tripTips;
    @Column(name = "seasons")
    private String seasons;
    @Column(name = "custom")
    private String custom;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "map_level")
    private Integer mapLevel;
    @Column(name = "hd_min_level")
    private Integer hdMinLevel;
    @Column(name = "hd_max_level")
    private Integer hdMaxLevel;
    @Column(name = "modify_time")
    private Date modifyTime;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "adv_day")
    private Integer advDay;
    @Column(name = "adv_cost")
    private Integer advCost;
    @Column(name = "min_day")
    private Integer minDay;
    @Column(name = "max_day")
    private Integer maxDay;
	@Column(name = "show_index")
	private Boolean showIndex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getScenicId() {
		return scenicId;
	}

	public void setScenicId(Long scenicId) {
		this.scenicId = scenicId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSurvey() {
		return survey;
	}

	public void setSurvey(String survey) {
		this.survey = survey;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getScenics() {
		return scenics;
	}

	public void setScenics(String scenics) {
		this.scenics = scenics;
	}

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getStyles() {
		return styles;
	}

	public void setStyles(String styles) {
		this.styles = styles;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDaysRecommend() {
		return daysRecommend;
	}

	public void setDaysRecommend(String daysRecommend) {
		this.daysRecommend = daysRecommend;
	}

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getTripTips() {
		return tripTips;
	}

	public void setTripTips(String tripTips) {
		this.tripTips = tripTips;
	}

	public String getSeasons() {
		return seasons;
	}

	public void setSeasons(String seasons) {
		this.seasons = seasons;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getMapLevel() {
		return mapLevel;
	}

	public void setMapLevel(Integer mapLevel) {
		this.mapLevel = mapLevel;
	}

	public Integer getHdMinLevel() {
		return hdMinLevel;
	}

	public void setHdMinLevel(Integer hdMinLevel) {
		this.hdMinLevel = hdMinLevel;
	}

	public Integer getHdMaxLevel() {
		return hdMaxLevel;
	}

	public void setHdMaxLevel(Integer hdMaxLevel) {
		this.hdMaxLevel = hdMaxLevel;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getAdvDay() {
		return advDay;
	}

	public void setAdvDay(Integer advDay) {
		this.advDay = advDay;
	}

	public Integer getAdvCost() {
		return advCost;
	}

	public void setAdvCost(Integer advCost) {
		this.advCost = advCost;
	}

	public Integer getMinDay() {
		return minDay;
	}

	public void setMinDay(Integer minDay) {
		this.minDay = minDay;
	}

	public Integer getMaxDay() {
		return maxDay;
	}

	public void setMaxDay(Integer maxDay) {
		this.maxDay = maxDay;
	}

	public Boolean getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Boolean showIndex) {
		this.showIndex = showIndex;
	}

}
