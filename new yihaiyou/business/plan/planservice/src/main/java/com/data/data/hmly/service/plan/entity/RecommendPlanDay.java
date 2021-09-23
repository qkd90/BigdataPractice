package com.data.data.hmly.service.plan.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recommend_plan_day")
public class RecommendPlanDay extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recplan_id")
	private RecommendPlan recommendPlan;
	@Column(name = "day")
	private Integer day;
	@Column(name = "modify_time")
	private Date modifyTime;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "scenics")
	private Integer scenics;
	@Column(name = "hours")
	private Integer hours;
	@Column(name = "citys")
	private String citys;
	@Column(name = "description")
	private String description;
	@Column(name = "hotel")
	private String hotel;
	@Column(name = "food")
	private String food;
	@Column(name = "ticket_cost")
	private Float ticketCost;
	@Column(name = "seasonticket_cost")
	private Float seasonticketCost;
	@Column(name = "traffic_cost")
	private Float trafficCost;
	@Column(name = "hotel_cost")
	private Float hotelCost;
	@Column(name = "traffic_time")
	private Integer trafficTime;
	@Column(name = "cost")
	private Float cost;
	@Column(name = "include_seasonticket_cost")
	private Float includeSeasonticketCost;
	@Column(name = "food_cost")
	private Float foodCost;
	@Column(name = "isLast")
	private String isLast;
	@OneToMany(mappedBy = "recommendPlanDay", fetch = FetchType.LAZY)
	private List<RecommendPlanTrip> recommendPlanTrips;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RecommendPlan getRecommendPlan() {
		return recommendPlan;
	}

	public void setRecommendPlan(RecommendPlan recommendPlan) {
		this.recommendPlan = recommendPlan;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
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

	public Integer getScenics() {
		return scenics;
	}

	public void setScenics(Integer scenics) {
		this.scenics = scenics;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public String getCitys() {
		return citys;
	}

	public void setCitys(String citys) {
		this.citys = citys;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public Float getTicketCost() {
		return ticketCost;
	}

	public void setTicketCost(Float ticketCost) {
		this.ticketCost = ticketCost;
	}

	public Float getSeasonticketCost() {
		return seasonticketCost;
	}

	public void setSeasonticketCost(Float seasonticketCost) {
		this.seasonticketCost = seasonticketCost;
	}

	public Float getTrafficCost() {
		return trafficCost;
	}

	public void setTrafficCost(Float trafficCost) {
		this.trafficCost = trafficCost;
	}

	public Float getHotelCost() {
		return hotelCost;
	}

	public void setHotelCost(Float hotelCost) {
		this.hotelCost = hotelCost;
	}

	public Integer getTrafficTime() {
		return trafficTime;
	}

	public void setTrafficTime(Integer trafficTime) {
		this.trafficTime = trafficTime;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public Float getIncludeSeasonticketCost() {
		return includeSeasonticketCost;
	}

	public void setIncludeSeasonticketCost(Float includeSeasonticketCost) {
		this.includeSeasonticketCost = includeSeasonticketCost;
	}

	public Float getFoodCost() {
		return foodCost;
	}

	public void setFoodCost(Float foodCost) {
		this.foodCost = foodCost;
	}

	public String getIsLast() {
		return isLast;
	}

	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}

	public List<RecommendPlanTrip> getRecommendPlanTrips() {
		return recommendPlanTrips;
	}

	public void setRecommendPlanTrips(List<RecommendPlanTrip> recommendPlanTrips) {
		this.recommendPlanTrips = recommendPlanTrips;
	}
}
