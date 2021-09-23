package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class RecommendPlanTrip extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 推荐行程id
	 */
	private long recommendPlanId;

	/**
	 * 推荐行程天id
	 */
	private long recommendPlanDayId;

	/**
	 * 行程游玩id
	 */
	private long planTripId;

	/**
	 * 怎么go
	 */
	private String howToGo;

	/**
	 * 怎么玩
	 */
	private String howToPlay;

	/**
	 * 小贴士
	 */
	private String tipsContent;
	/**
	 * 景点id
	 */
	private long scenicId;
	/**
	 * 景点类型
	 */
	private int tripType;

	/**
	 * 景点名称
	 */
	private transient String scenicName;
	/**
	 * 票价
	 */
	private transient int ticket;
	/**
	 * 经度
	 */
	private transient double longitude;
	/**
	 * 纬度
	 */
	private transient double latitude;

	/**
	 * 推荐行程图片的集合
	 */
	private transient List<RecommendPlanImage> recommendPlanImageList;
	/**
	 * 游玩的时间
	 */
	private transient int travelTime;
	/**
	 * 景点门票信息
	 */
	private transient String ticketInfo;
	/**
	 * 景点的游玩信息
	 */
	private transient String travelTimeInfo;

	/**
	 * 出发时间
	 */
	private transient Date startTime;

	/**
	 * 交通时间
	 */
	private String trafficTime;

	/**
	 * 交通时间
	 */
	private int trafficHours;

	/**
	 * 交通距离
	 */
	private String trafficMileage;

	/**
	 * 交通花费
	 */
	private int trafficPrice;

	/**
	 * 交通类型
	 */
	private int trafficType;

	/**
	 *
	 */
	private Map<String, Object> baiduMap;


	@JsonProperty
	public String getTravelTimeInfo() {
		return travelTimeInfo;
	}

	public void setTravelTimeInfo(String travelTimeInfo) {
		this.travelTimeInfo = travelTimeInfo;
	}

	@JsonProperty
	public String getTicketInfo() {
		return ticketInfo;
	}

	public void setTicketInfo(String ticketInfo) {
		this.ticketInfo = ticketInfo;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	@JsonProperty
	public int getTicket() {
		return ticket;
	}

	public void setTicket(int ticket) {
		this.ticket = ticket;
	}

	@JsonProperty
	public int getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(int travelTime) {
		this.travelTime = travelTime;
	}

	public void setRecommendPlanId(long recommendPlanId) {
		this.recommendPlanId = recommendPlanId;
	}

	@JsonProperty
	public long getRecommendPlanId() {
		return recommendPlanId;
	}

	public void setRecommendPlanDayId(long recommendPlanDayId) {
		this.recommendPlanDayId = recommendPlanDayId;
	}

	@JsonProperty
	public long getRecommendPlanDayId() {
		return recommendPlanDayId;
	}

	public void setPlanTripId(long planTripId) {
		this.planTripId = planTripId;
	}

	@JsonProperty
	public long getPlanTripId() {
		return planTripId;
	}

	public void setHowToGo(String howToGo) {
		this.howToGo = howToGo;
	}

	@JsonProperty
	public String getHowToGo() {
		return howToGo;
	}

	public void setHowToPlay(String howToPlay) {
		this.howToPlay = howToPlay;
	}

	@JsonProperty
	public String getHowToPlay() {
		return howToPlay;
	}

	public void setTipsContent(String tipsContent) {
		this.tipsContent = tipsContent;
	}

	@JsonProperty
	public String getTipsContent() {
		return tipsContent;
	}

	@JsonProperty
	public List<RecommendPlanImage> getRecommendPlanImageList() {
		return recommendPlanImageList;
	}

	public void setRecommendPlanImageList(List<RecommendPlanImage> recommendPlanImageList) {
		this.recommendPlanImageList = recommendPlanImageList;
	}

	@JsonProperty
	public int getTripType() {
		return tripType;
	}

	public void setTripType(int tripType) {
		this.tripType = tripType;
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
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonProperty
	public String getTrafficTime() {
		return trafficTime;
	}

	public void setTrafficTime(String trafficTime) {
		this.trafficTime = trafficTime;
	}

	@JsonProperty
	public int getTrafficHours() {
		return trafficHours;
	}

	public void setTrafficHours(int trafficHours) {
		this.trafficHours = trafficHours;
	}

	@JsonProperty
	public String getTrafficMileage() {
		return trafficMileage;
	}

	public void setTrafficMileage(String trafficMileage) {
		this.trafficMileage = trafficMileage;
	}

	@JsonProperty
	public int getTrafficPrice() {
		return trafficPrice;
	}

	public void setTrafficPrice(int trafficPrice) {
		this.trafficPrice = trafficPrice;
	}

	@JsonProperty
	public int getTrafficType() {
		return trafficType;
	}

	public void setTrafficType(int trafficType) {
		this.trafficType = trafficType;
	}

	@JsonProperty
	public Map<String, Object> getBaiduMap() {
		return baiduMap;
	}

	public void setBaiduMap(Map<String, Object> baiduMap) {
		this.baiduMap = baiduMap;
	}


}
