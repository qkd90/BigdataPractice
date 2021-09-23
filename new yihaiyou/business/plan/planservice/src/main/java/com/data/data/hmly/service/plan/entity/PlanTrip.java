package com.data.data.hmly.service.plan.entity;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "plan_trip")
public class PlanTrip extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final Long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id")
	private Plan plan;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_days_id")
	private PlanDay planDay;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scenic_Id")
	private ScenicInfo scenicInfo;
	@Column(name = "travel_type")
	private Integer travelType;
	@Column(name = "travel_detail")
	private String travelDetail;
	@Column(name = "travel_time")
	private String travelTime;
	@Column(name = "travel_hours")
	private Integer travelHours;
	@Column(name = "travel_mileage")
	private String travelMileage;
	@Column(name = "trip_type")
	private Integer tripType;
	@Column(name = "start_time")
	private Date startTime;
	@Column(name = "order_num")
	private Integer orderNum;
	@Column(name = "custom_trip_name")
	private String customTripName;
	@Column(name = "trip_desc")
	private String tripDesc;
	@Column(name = "modify_time")
	private Date modifyTime;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "visitor_time")
	private String visitorTime;
	@Column(name = "travel_price")
	private Float travelPrice;
	@Column(name = "id_backup")
	private Long idBackup;
	@Column(name = "source")
	private Integer source;
	@Column(name = "source_id")
	private Long sourceId;
	@Column(name = "delicacy_id")
	private Long delicacyId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PlanDay getPlanDay() {
		return planDay;
	}

	public void setPlanDay(PlanDay planDay) {
		this.planDay = planDay;
	}

	public ScenicInfo getScenicInfo() {
		return scenicInfo;
	}

	public void setScenicInfo(ScenicInfo scenicInfo) {
		this.scenicInfo = scenicInfo;
	}

	public Integer getTravelType() {
		return travelType;
	}

	public void setTravelType(Integer travelType) {
		this.travelType = travelType;
	}

	public String getTravelDetail() {
		return travelDetail;
	}

	public void setTravelDetail(String travelDetail) {
		this.travelDetail = travelDetail;
	}

	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public Integer getTravelHours() {
		return travelHours;
	}

	public void setTravelHours(Integer travelHours) {
		this.travelHours = travelHours;
	}

	public String getTravelMileage() {
		return travelMileage;
	}

	public void setTravelMileage(String travelMileage) {
		this.travelMileage = travelMileage;
	}

	public Integer getTripType() {
		return tripType;
	}

	public void setTripType(Integer tripType) {
		this.tripType = tripType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getCustomTripName() {
		return customTripName;
	}

	public void setCustomTripName(String customTripName) {
		this.customTripName = customTripName;
	}

	public String getTripDesc() {
		return tripDesc;
	}

	public void setTripDesc(String tripDesc) {
		this.tripDesc = tripDesc;
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

	public String getVisitorTime() {
		return visitorTime;
	}

	public void setVisitorTime(String visitorTime) {
		this.visitorTime = visitorTime;
	}

	public Float getTravelPrice() {
		return travelPrice;
	}

	public void setTravelPrice(Float travelPrice) {
		this.travelPrice = travelPrice;
	}

	public Long getIdBackup() {
		return idBackup;
	}

	public void setIdBackup(Long idBackup) {
		this.idBackup = idBackup;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Long getDelicacyId() {
		return delicacyId;
	}

	public void setDelicacyId(Long delicacyId) {
		this.delicacyId = delicacyId;
	}
}
