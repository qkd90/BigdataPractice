package com.data.spider.service.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * 景点实体 ?
 *
 */
@Entity
@Table(name = "data_scenic")
@XmlRootElement
public class Scenic extends com.framework.hibernate.util.Entity {
	private Long id;
	private Long userId;
	private String name;
	private String cityCode;
	private Short star;
	private Integer price;
	private Integer score;
	private String address;
	private String telephone;
	private Integer orderNum;
	private Integer commentNum;
	private Integer goingCount;
	private Integer cameCount;
	private String openTime;
	private String adviceTime;
	private Integer adviceHours;
	private String bestTime;
	private String ticket;
	private String commenter;
	private String shortComment;
	private String coverLarge;
	private String coverMedium;
	private String coverSmall;
	private Long father;
	private String fatherName;
	private Boolean ifHasChild;
	private Double longitude;
	private Double latitude;
	private String url;
	private Timestamp modifyTime;
	private Timestamp createTime;
	private Integer isCity;
	private Integer isStation;
	private Integer stationType;
	private Double gcjLng;
	private Double gcjLat;
	private Float lowestPrice;
	private Float highestPrice;
	private Float marketPrice;
	private Integer status;
	private Integer flagHasTaxi;
	private Integer flagHasBus;
	private Integer flagThreeLevelRegion;
	private String dataSource;
	private Integer dataSourceId;
	private String dataSourceUrl;
	private String dataHtml;
	private String xml;
	private String level;
	private Integer ranking;
	private String bestHour;
	private String introduction;
	private String website;
	private String advice;
	private String theme;
	private String how;
	private String scenicType;
	private String notice;
	private String guide;
	private String parentsThreeLevelRegion;
	private String hpl;
	private String recommendReason;

	@Id
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Basic
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "city_code")
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Basic
	@Column(name = "star")
	public Short getStar() {
		return star;
	}

	public void setStar(Short star) {
		this.star = star;
	}

	@Basic
	@Column(name = "price")
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Basic
	@Column(name = "score")
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Basic
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Basic
	@Column(name = "telephone")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Basic
	@Column(name = "order_num")
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Basic
	@Column(name = "comment_num")
	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	@Basic
	@Column(name = "going_count")
	public Integer getGoingCount() {
		return goingCount;
	}

	public void setGoingCount(Integer goingCount) {
		this.goingCount = goingCount;
	}

	@Basic
	@Column(name = "came_count")
	public Integer getCameCount() {
		return cameCount;
	}

	public void setCameCount(Integer cameCount) {
		this.cameCount = cameCount;
	}

	@Basic
	@Column(name = "open_time")
	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	@Basic
	@Column(name = "advice_time")
	public String getAdviceTime() {
		return adviceTime;
	}

	public void setAdviceTime(String adviceTime) {
		this.adviceTime = adviceTime;
	}

	@Basic
	@Column(name = "advice_hours")
	public Integer getAdviceHours() {
		return adviceHours;
	}

	public void setAdviceHours(Integer adviceHours) {
		this.adviceHours = adviceHours;
	}

	@Basic
	@Column(name = "best_time")
	public String getBestTime() {
		return bestTime;
	}

	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}

	@Basic
	@Column(name = "ticket")
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Basic
	@Column(name = "commenter")
	public String getCommenter() {
		return commenter;
	}

	public void setCommenter(String commenter) {
		this.commenter = commenter;
	}

	@Basic
	@Column(name = "short_comment")
	public String getShortComment() {
		return shortComment;
	}

	public void setShortComment(String shortComment) {
		this.shortComment = shortComment;
	}

	@Basic
	@Column(name = "cover_large")
	public String getCoverLarge() {
		return coverLarge;
	}

	public void setCoverLarge(String coverLarge) {
		this.coverLarge = coverLarge;
	}

	@Basic
	@Column(name = "cover_medium")
	public String getCoverMedium() {
		return coverMedium;
	}

	public void setCoverMedium(String coverMedium) {
		this.coverMedium = coverMedium;
	}

	@Basic
	@Column(name = "cover_small")
	public String getCoverSmall() {
		return coverSmall;
	}

	public void setCoverSmall(String coverSmall) {
		this.coverSmall = coverSmall;
	}

	@Basic
	@Column(name = "father")
	public Long getFather() {
		return father;
	}

	public void setFather(Long father) {
		this.father = father;
	}

	@Basic
	@Column(name = "father_name")
	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	@Basic
	@Column(name = "if_has_child")
	public Boolean getIfHasChild() {
		return ifHasChild;
	}

	public void setIfHasChild(Boolean ifHasChild) {
		this.ifHasChild = ifHasChild;
	}

	@Basic
	@Column(name = "longitude")
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Basic
	@Column(name = "latitude")
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Basic
	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Basic
	@Column(name = "modify_time")
	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Basic
	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Basic
	@Column(name = "is_city")
	public Integer getIsCity() {
		return isCity;
	}

	public void setIsCity(Integer isCity) {
		this.isCity = isCity;
	}

	@Basic
	@Column(name = "is_station")
	public Integer getIsStation() {
		return isStation;
	}

	public void setIsStation(Integer isStation) {
		this.isStation = isStation;
	}

	@Basic
	@Column(name = "station_type")
	public Integer getStationType() {
		return stationType;
	}

	public void setStationType(Integer stationType) {
		this.stationType = stationType;
	}

	@Basic
	@Column(name = "gcj_lng")
	public Double getGcjLng() {
		return gcjLng;
	}

	public void setGcjLng(Double gcjLng) {
		this.gcjLng = gcjLng;
	}

	@Basic
	@Column(name = "gcj_lat")
	public Double getGcjLat() {
		return gcjLat;
	}

	public void setGcjLat(Double gcjLat) {
		this.gcjLat = gcjLat;
	}

	@Basic
	@Column(name = "lowest_price")
	public Float getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(Float lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	@Basic
	@Column(name = "highest_price")
	public Float getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(Float highestPrice) {
		this.highestPrice = highestPrice;
	}

	@Basic
	@Column(name = "market_price")
	public Float getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Float marketPrice) {
		this.marketPrice = marketPrice;
	}

	@Basic
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Basic
	@Column(name = "flag_has_taxi")
	public Integer getFlagHasTaxi() {
		return flagHasTaxi;
	}

	public void setFlagHasTaxi(Integer flagHasTaxi) {
		this.flagHasTaxi = flagHasTaxi;
	}

	@Basic
	@Column(name = "flag_has_bus")
	public Integer getFlagHasBus() {
		return flagHasBus;
	}

	public void setFlagHasBus(Integer flagHasBus) {
		this.flagHasBus = flagHasBus;
	}

	@Basic
	@Column(name = "flag_three_level_region")
	public Integer getFlagThreeLevelRegion() {
		return flagThreeLevelRegion;
	}

	public void setFlagThreeLevelRegion(Integer flagThreeLevelRegion) {
		this.flagThreeLevelRegion = flagThreeLevelRegion;
	}

	@Basic
	@Column(name = "data_source")
	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Basic
	@Column(name = "data_source_id")
	public Integer getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	@Basic
	@Column(name = "data_source_url")
	public String getDataSourceUrl() {
		return dataSourceUrl;
	}

	public void setDataSourceUrl(String dataSourceUrl) {
		this.dataSourceUrl = dataSourceUrl;
	}

	@Basic
	@Column(name = "data_html")
	public String getDataHtml() {
		return dataHtml;
	}

	public void setDataHtml(String dataHtml) {
		this.dataHtml = dataHtml;
	}

	@Basic
	@Column(name = "xml")
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	@Basic
	@Column(name = "level")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Basic
	@Column(name = "ranking")
	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	@Basic
	@Column(name = "best_hour")
	public String getBestHour() {
		return bestHour;
	}

	public void setBestHour(String bestHour) {
		this.bestHour = bestHour;
	}

	@Basic
	@Column(name = "introduction")
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Basic
	@Column(name = "website")
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Basic
	@Column(name = "advice")
	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	@Basic
	@Column(name = "theme")
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Basic
	@Column(name = "how")
	public String getHow() {
		return how;
	}

	public void setHow(String how) {
		this.how = how;
	}

	@Basic
	@Column(name = "scenic_type")
	public String getScenicType() {
		return scenicType;
	}

	public void setScenicType(String scenicType) {
		this.scenicType = scenicType;
	}

	@Basic
	@Column(name = "notice")
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	@Basic
	@Column(name = "guide")
	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	@Basic
	@Column(name = "parents_three_level_region")
	public String getParentsThreeLevelRegion() {
		return parentsThreeLevelRegion;
	}

	public void setParentsThreeLevelRegion(String parentsThreeLevelRegion) {
		this.parentsThreeLevelRegion = parentsThreeLevelRegion;
	}

	@Basic
	@Column(name = "hpl")
	public String getHpl() {
		return hpl;
	}

	public void setHpl(String hpl) {
		this.hpl = hpl;
	}

	@Basic
	@Column(name = "recommend_reason")
	public String getRecommendReason() {
		return recommendReason;
	}

	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}

}
