package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class ScenicInfo extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 景点名
	 */
	private String name;

	/**
	 * 城市区号
	 */
	private String cityCode;

	/**
	 * 评级
	 */
	private int score;

	/**
	 * 星级
	 */
	private int star;

	/**
	 * 价格
	 */
	private int price;

	/**
	 * 去过的人
	 */
	private int cameCount;
	/**
	 * 去过的人
	 */
	private int goingCount;


	/**
	 * 开放时间
	 */
	private String openTime;

	/**
	 * 建议游玩时间
	 */
	private String adviceTime;

	/**
	 * 建议游玩时间数
	 */
	private int adviceHours;

	/**
	 * 最佳游玩时间
	 */
	private String bestTime;

	/**
	 * 票价信息
	 */
	private String ticket;

	/**
	 * 一句话点评
	 */
	private String shortComment;

	/**
	 * 点评人
	 */
	private Long userId;

	/**
	 * 封面图大
	 */
	private String coverLarge;

	/**
	 * 封面图中
	 */
	private String coverMedium;

	/**
	 * 封面图小
	 */
	private String coverSmall;

	/**
	 * 父景点
	 */
	private int father;

	/**
	 * 是否有子景点
	 */
	private boolean ifHasChild;

	/**
	 * 经度
	 */
	private double longitude;

	/**
	 * 纬度
	 */
	private double latitude;

	/**
	 * GCJ经度
	 */
	private double gcjLng;

	/**
	 * GCJ纬度
	 */
	private double gcjLat;

	/**
	 * 链接地址
	 */
	private String url;

	/**
	 * 旅游建议
	 */
	private String advice;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 电话
	 */
	private String telephone;

	/**
	 * 网站地址
	 */
	private String website;


	/**
	 * 注意事项
	 */
	private String warning;

	/**
	 * 最佳旅游时间
	 */
	private String bestTimeStr;

	/**
	 * 紧急医疗
	 */
	private String hospital;

	/**
	 * 当地风俗
	 */
	private String custom;

	/**
	 * 重点推荐
	 */
	private String recommend;

	/**
	 * 评论数
	 */
	private int commentNum;

	private transient int distance;

	/**
	 * 是否是城市 add by 林则金
	 */
	private int isCity;

	/**
	 * 是否是车站
	 */
	private int isStation;

	/**
	 * 最低价格
	 */
	private float lowestPrice;

	/**
	 * 市场价
	 */
	private float marketPrice;

	/**
	 * 最高价格
	 */
	private float highestPrice;

	/**
	 * 父景点是否可以打车
	 */
	private int flagHasTaxi;

	/**
	 * 父景点是都有公交
	 */
	private int flagHasBus;

	/**
	 * 是否是三级景点
	 */
	private int flagThreeLevelRegion;

	/**
	 * 父景点是否属于三级景区
	 */
	private String parentsThreeLevelRegion;

	/**
	 * 推荐指数
	 */
	private int orderNum;

	/*
	 * 1.自定义 2.去哪儿 3.任我游
	 */
	private int firstTicketSource;

	private int firstTicketId;

	/**
	 * 怎么玩
	 */
	private String play;

	/**
	 * 父景点名称
	 */
	private String fatherName;


	@JsonProperty
	public int getGoingCount() {
		return goingCount;
	}

	public void setGoingCount(int goingCount) {
		this.goingCount = goingCount;
	}

	@JsonProperty
	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	@JsonProperty
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@JsonProperty
	public int getScore() {
		return score;
	}

	public void setCameCount(int cameCount) {
		this.cameCount = cameCount;
	}

	@JsonProperty
	public int getCameCount() {
		return cameCount;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	@JsonProperty
	public String getOpenTime() {
		return openTime;
	}

	public void setAdviceTime(String adviceTime) {
		this.adviceTime = adviceTime;
	}

	@JsonProperty
	public String getAdviceTime() {
		return adviceTime;
	}

	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}

	@JsonProperty
	public String getBestTime() {
		return bestTime;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@JsonProperty
	public String getTicket() {
		return ticket;
	}

	public void setShortComment(String shortComment) {
		this.shortComment = shortComment;
	}

	@JsonProperty
	public String getShortComment() {
		return shortComment;
	}

	public void setCoverLarge(String coverLarge) {
		this.coverLarge = coverLarge;
	}

	@JsonProperty
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public String getCoverLarge() {
		return coverLarge;
	}

	public void setCoverMedium(String coverMedium) {
		this.coverMedium = coverMedium;
	}

	@JsonProperty
	public String getCoverMedium() {
		return coverMedium;
	}

	public void setCoverSmall(String coverSmall) {
		this.coverSmall = coverSmall;
	}

	@JsonProperty
	public String getCoverSmall() {
		return coverSmall;
	}

	public void setFather(int father) {
		this.father = father;
	}

	@JsonProperty
	public int getFather() {
		return father;
	}

	public void setIfHasChild(boolean ifHasChild) {
		this.ifHasChild = ifHasChild;
	}

	@JsonProperty
	public boolean getIfHasChild() {
		return ifHasChild;
	}


	@JsonProperty
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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
	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	@JsonProperty
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@JsonProperty
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@JsonProperty
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@JsonProperty
	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	@JsonProperty
	public String getBestTimeStr() {
		return bestTimeStr;
	}

	public void setBestTimeStr(String bestTimeStr) {
		this.bestTimeStr = bestTimeStr;
	}

	@JsonProperty
	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	@JsonProperty
	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	@JsonProperty
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	@JsonProperty
	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	@JsonProperty
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@JsonProperty
	public int getAdviceHours() {
		return adviceHours;
	}

	public void setAdviceHours(int adviceHours) {
		this.adviceHours = adviceHours;
	}

	@JsonProperty
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@JsonProperty
	public int getIsCity() {
		return isCity;
	}

	public void setIsCity(int isCity) {
		this.isCity = isCity;
	}

	@JsonProperty
	public int getIsStation() {
		return isStation;
	}

	public void setIsStation(int isStation) {
		this.isStation = isStation;
	}

	@JsonProperty
	public double getGcjLng() {
		return gcjLng;
	}

	public void setGcjLng(double gcjLng) {
		this.gcjLng = gcjLng;
	}

	@JsonProperty
	public double getGcjLat() {
		return gcjLat;
	}

	public void setGcjLat(double gcjLat) {
		this.gcjLat = gcjLat;
	}

	@JsonProperty
	public float getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(float lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	@JsonProperty
	public float getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(float marketPrice) {
		this.marketPrice = marketPrice;
	}

	@JsonProperty
	public float getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(float highestPrice) {
		this.highestPrice = highestPrice;
	}

	@JsonProperty
	public int getFlagHasTaxi() {
		return flagHasTaxi;
	}

	public void setFlagHasTaxi(int flagHasTaxi) {
		this.flagHasTaxi = flagHasTaxi;
	}

	@JsonProperty
	public int getFlagHasBus() {
		return flagHasBus;
	}

	public void setFlagHasBus(int flagHasBus) {
		this.flagHasBus = flagHasBus;
	}

	@JsonProperty
	public int getFlagThreeLevelRegion() {
		return flagThreeLevelRegion;
	}

	public void setFlagThreeLevelRegion(int flagThreeLevelRegion) {
		this.flagThreeLevelRegion = flagThreeLevelRegion;
	}

	@JsonProperty
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	@JsonProperty
	public int getFirstTicketSource() {
		return firstTicketSource;
	}

	public void setFirstTicketSource(int firstTicketSource) {
		this.firstTicketSource = firstTicketSource;
	}

	@JsonProperty
	public int getFirstTicketId() {
		return firstTicketId;
	}

	public void setFirstTicketId(int firstTicketId) {
		this.firstTicketId = firstTicketId;
	}

	@JsonProperty
	public String getParentsThreeLevelRegion() {
		return parentsThreeLevelRegion;
	}

	public void setParentsThreeLevelRegion(String parentsThreeLevelRegion) {
		this.parentsThreeLevelRegion = parentsThreeLevelRegion;
	}

	@JsonProperty
	public String getPlay() {
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
	}

	@JsonProperty
	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

}
