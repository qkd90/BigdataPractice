package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.app.soutu.plan.domain.type.TravelType;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanPhoto;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PlanTrip implements Serializable, Cloneable {

	// 从推荐行程引用
	public final static int SOURCE_RECPLAN = 1;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Date modifyTime = new Date();

	private Date createTime;


	/**
	 * 行程编号
	 */
	private long planId;

	/**
	 * 行程天编号
	 */
	private long planDaysId;

	/**
	 * 景点编号
	 */
	private long scenicId;

	/**
	 * 用户编号
	 */
	private long userId;

	/**
	 * 行程类型
	 */
	private TripType tripType;

	/**
	 * 顺序号
	 */
	private int orderNum;

	/**
	 * 自定义行程站名称
	 */
	private String customTripName;

	/**
	 * 行程站备注
	 */
	private String tripDesc;

	/**
	 * 行程站交通方式
	 */
	private TravelType travelType;

	/**
	 * 交通方式详细信息
	 */
	private String travelDetail;

	/**
	 * 里程数
	 */
	private String travelMileage;

	/**
	 * 交通方式花费时间
	 */
	private String travelTime;

	/**
	 * 交通时间
	 */
	private int travelHours;

	/**
	 * 出发时间
	 */
	private Time startTime;

	/**
	 *
	 */
	private Map<String, Object> baiduMap;

	private transient int day;

	/**
	 * 行程交通价格
	 * add by 林则金
	 */
	private int travelPrice;

	/**
	 * 来源
	 */
	private int source;

	/**
	 * 来源id
	 */
	private long sourceId;

	/**
	 * 属于该行程点的照片
	 */
	private List<RecplanPhoto> recplanPhotos;

	/**
	 * 美食id，当trip为餐厅时不能为空
	 */
	private long delicacyId;


	/**
	 * 酒店标签（和推荐行程酒店标签保持一致）
	 */
	private int tag;


	@JsonProperty
	public String getTravelDetail() {
		return travelDetail;
	}

	public void setTravelDetail(String travelDetail) {
		this.travelDetail = travelDetail;
	}

	@JsonProperty
	public String getTravelMileage() {
		return travelMileage;
	}

	public void setTravelMileage(String travelMileage) {
		this.travelMileage = travelMileage;
	}

	@JsonProperty
	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	@JsonProperty
	public long getPlanId() {
		return planId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	@JsonProperty
	public long getPlanDaysId() {
		return planDaysId;
	}

	public void setPlanDaysId(long planDaysId) {
		this.planDaysId = planDaysId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public int getTripType() {
		return tripType.value();
	}

	public void setTripType(int tripType) {
		this.tripType = TripType.valueOf(tripType);
	}

	@JsonProperty
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	@JsonProperty
	public String getCustomTripName() {
		return customTripName;
	}

	public void setCustomTripName(String customTripName) {
		this.customTripName = customTripName;
	}

	@JsonProperty
	public String getTripDesc() {
		return tripDesc;
	}

	public void setTripDesc(String tripDesc) {
		this.tripDesc = tripDesc;
	}

	@JsonProperty
	public int getTravelType() {
		return travelType.value();
	}

	public void setTravelType(int travelType) {

		this.travelType = TravelType.valueOf(travelType);
	}

	@JsonProperty
	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	@JsonProperty
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@JsonProperty
	public int getTravelPrice() {
		return travelPrice;
	}

	public void setTravelPrice(int travelPrice) {
		this.travelPrice = travelPrice;
	}

	@JsonProperty
	public int getTravelHours() {
		return travelHours;
	}

	public void setTravelHours(int travelHours) {
		this.travelHours = travelHours;
	}

	@JsonProperty
	public Map<String, Object> getBaiduMap() {
		return baiduMap;
	}

	public void setBaiduMap(Map<String, Object> baiduMap) {
		this.baiduMap = baiduMap;
	}

	@JsonProperty
	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	@JsonProperty
	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	@JsonProperty
	public List<RecplanPhoto> getRecplanPhotos() {
		return recplanPhotos;
	}

	public void setRecplanPhotos(List<RecplanPhoto> recplanPhotos) {
		this.recplanPhotos = recplanPhotos;
	}

	@JsonProperty
	public long getDelicacyId() {
		return delicacyId;
	}

	public void setDelicacyId(long delicacyId) {
		this.delicacyId = delicacyId;
	}

	@JsonProperty
	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getModifyTime() {
		if (modifyTime == null) {
			modifyTime = new Date();
		}
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@JsonProperty
	public Date getCreateTime() {
		if (createTime == null) {
			createTime = new Date();
		}
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
