package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class RecommendPlanDay extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 推荐行id
	 */
	private long recommendPlanId;

	/**
	 * 行程天id
	 */
	private long planDayId;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 游玩的主题
	 */
	private String recommendTopic;

	/**
	 * 住宿推荐
	 */
	private String recommendHotel;

	/**
	 * 美食推荐
	 */
	private String recommendDelicacy;

	/**
	 * 每天的花费
	 */
	private String dayCost;

	/**
	 * 每天的游玩时间
	 */
	private String totalTime;
	/**
	 * 临时的推荐行程旅游集合
	 */
	private transient List<RecommendPlanTrip> recommendPlanTripList;
	/**
	 * 临时的景点数
	 */
	private transient int scenicCount;
	/**
	 * 临时的门票总价
	 */
	private transient int ticket;

	/**
	 * 临时游玩时间
	 *
	 * @param
	 */
	private transient float travelTime;

	/**
	 * 推荐行程酒店
	 *
	 * @return
	 */
	private transient CtripHotel hotel;

	/**
	 * 类型为酒店的行程
	 */
	private transient RecommendPlanTrip hotelTrip;

	List tripList;

	@JsonProperty
	public float getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(float travelTime) {
		this.travelTime = travelTime;
	}

	@JsonProperty
	public List<RecommendPlanTrip> getRecommendPlanTripList() {
		return recommendPlanTripList;
	}

	public void setRecommendPlanTripList(List<RecommendPlanTrip> recommendPlanTripList) {
		this.recommendPlanTripList = recommendPlanTripList;
	}

	@JsonProperty
	public int getScenicCount() {
		return scenicCount;
	}

	public void setScenicCount(int scenicCount) {
		this.scenicCount = scenicCount;
	}

	@JsonProperty
	public int getTicket() {
		return ticket;
	}

	public void setTicket(int ticket) {
		this.ticket = ticket;
	}

	public void setRecommendPlanId(long recommendPlanId) {
		this.recommendPlanId = recommendPlanId;
	}

	@JsonProperty
	public long getRecommendPlanId() {
		return recommendPlanId;
	}

	public void setPlanDayId(long planDayId) {
		this.planDayId = planDayId;
	}

	@JsonProperty
	public long getPlanDayId() {
		return planDayId;
	}

	@JsonProperty
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setRecommendTopic(String recommendTopic) {
		this.recommendTopic = recommendTopic;
	}

	@JsonProperty
	public String getRecommendTopic() {
		return recommendTopic;
	}

	public void setRecommendHotel(String recommendHotel) {
		this.recommendHotel = recommendHotel;
	}

	@JsonProperty
	public String getRecommendHotel() {
		return recommendHotel;
	}

	public void setRecommendDelicacy(String recommendDelicacy) {
		this.recommendDelicacy = recommendDelicacy;
	}

	@JsonProperty
	public String getRecommendDelicacy() {
		return recommendDelicacy;
	}

	@JsonProperty
	public CtripHotel getHotel() {
		return hotel;
	}

	public void setHotel(CtripHotel hotel) {
		this.hotel = hotel;
	}

	@JsonProperty
	public List getTripList() {
		return tripList;
	}

	public void setTripList(List tripList) {
		this.tripList = tripList;
	}

	@JsonProperty
	public String getDayCost() {
		return dayCost;
	}

	public void setDayCost(String dayCost) {
		this.dayCost = dayCost;
	}

	@JsonProperty
	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	@JsonProperty
	public RecommendPlanTrip getHotelTrip() {
		return hotelTrip;
	}

	public void setHotelTrip(RecommendPlanTrip hotelTrip) {
		this.hotelTrip = hotelTrip;
	}


}
