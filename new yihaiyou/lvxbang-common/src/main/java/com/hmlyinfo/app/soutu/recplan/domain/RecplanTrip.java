package com.hmlyinfo.app.soutu.recplan.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class RecplanTrip implements Serializable, Cloneable {

	// 办理入住
	public static int TAG_CHECK_IN = 1;
	// 出发游玩
	public static int TAG_PLAY = 2;
	// 返回休息
	public static int TAG_BACK = 3;
	// 退房离开
	public static int TAG_CHECK_OUT = 4;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Date modifyTime = new Date();

	private Date createTime;

	/**
	 * 行程id
	 */
	private long recplanId;

	/**
	 * 用户id
	 */
	private long userId;

	/**
	 * 行程天编号
	 */
	private long recdayId;

	/**
	 * 景点编号
	 */
	private long scenicId;

	/**
	 * 交通类型
	 */
	private int travelType;

	/**
	 * 类型
	 */
	private int tripType;

	/**
	 * 开始时间
	 */
	private Time startTime;

	/**
	 * 顺序号
	 */
	private int orderNum;

	/**
	 * 行程站备注
	 */
	private String tripDesc;

	/**
	 * 打车花费
	 */
	private double taxiCost;

	/**
	 * 打车详情
	 */
	private String taxiDetail;

	/**
	 * 打车时间
	 */
	private String taxiTime;

	/**
	 * 打车时间
	 */
	private int taxiHour;

	/**
	 * 打车距离
	 */
	private String taxiMilleage;

	/**
	 * 步行详情
	 */
	private String walkDetail;

	/**
	 * 步行时间
	 */
	private String walkTime;

	/**
	 * 步行时间
	 */
	private int walkHour;

	/**
	 * 步行距离
	 */
	private String walkMilleage;

	/**
	 * 公交花费
	 */
	private double busCost;

	/**
	 * 公交详情
	 */
	private String busDetail;

	/**
	 * 公交时间
	 */
	private String busTime;

	/**
	 * 公交时间
	 */
	private int busHour;

	/**
	 * 公交距离
	 */
	private String busMilleage;

	/**
	 * 属于该行程点的照片
	 */
	private List<RecplanPhoto> recplanPhotos;

	/**
	 * 怎么玩
	 */
	private String exa;

	/**
	 * 行程点的详细信息
	 */
	private Object detail;

	/**
	 * 直线距离
	 */
	private int lineDis;

	/**
	 * 美食id，当trip为餐厅时不能为空
	 */
	private long delicacyId;

	/**
	 * 美食详情
	 */
	private Object foodDetail;

	/**
	 * 酒店标签
	 */
	private int tag;

	/**
	 * 封面图
	 */
	private String coverImg;

	/**
	 * 封面图宽
	 */
	private int imgWidth;

	/**
	 * 封面图高
	 */
	private int imgHeight;

	public void setRecplanId(long recplanId) {
		this.recplanId = recplanId;
	}

	@JsonProperty
	public long getRecplanId() {
		return recplanId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setRecdayId(long recdayId) {
		this.recdayId = recdayId;
	}

	@JsonProperty
	public long getRecdayId() {
		return recdayId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setTravelType(int travelType) {
		this.travelType = travelType;
	}

	@JsonProperty
	public int getTravelType() {
		return travelType;
	}

	public void setTripType(int tripType) {
		this.tripType = tripType;
	}

	@JsonProperty
	public int getTripType() {
		return tripType;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	@JsonProperty
	public Time getStartTime() {
		return startTime;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	@JsonProperty
	public int getOrderNum() {
		return orderNum;
	}

	public void setTripDesc(String tripDesc) {
		this.tripDesc = tripDesc;
	}

	@JsonProperty
	public String getTripDesc() {
		return tripDesc;
	}

	public void setTaxiCost(double taxiCost) {
		this.taxiCost = taxiCost;
	}

	@JsonProperty
	public double getTaxiCost() {
		return taxiCost;
	}

	public void setTaxiDetail(String taxiDetail) {
		this.taxiDetail = taxiDetail;
	}

	@JsonProperty
	public String getTaxiDetail() {
		return taxiDetail;
	}

	public void setTaxiTime(String taxiTime) {
		this.taxiTime = taxiTime;
	}

	@JsonProperty
	public String getTaxiTime() {
		return taxiTime;
	}

	public void setTaxiHour(int taxiHour) {
		this.taxiHour = taxiHour;
	}

	@JsonProperty
	public int getTaxiHour() {
		return taxiHour;
	}

	public void setTaxiMilleage(String taxiMilleage) {
		this.taxiMilleage = taxiMilleage;
	}

	@JsonProperty
	public String getTaxiMilleage() {
		return taxiMilleage;
	}

	public void setWalkDetail(String walkDetail) {
		this.walkDetail = walkDetail;
	}

	@JsonProperty
	public String getWalkDetail() {
		return walkDetail;
	}

	public void setWalkTime(String walkTime) {
		this.walkTime = walkTime;
	}

	@JsonProperty
	public String getWalkTime() {
		return walkTime;
	}

	public void setWalkHour(int walkHour) {
		this.walkHour = walkHour;
	}

	@JsonProperty
	public int getWalkHour() {
		return walkHour;
	}

	public void setWalkMilleage(String walkMilleage) {
		this.walkMilleage = walkMilleage;
	}

	@JsonProperty
	public String getWalkMilleage() {
		return walkMilleage;
	}

	public void setBusCost(double busCost) {
		this.busCost = busCost;
	}

	@JsonProperty
	public double getBusCost() {
		return busCost;
	}

	public void setBusDetail(String busDetail) {
		this.busDetail = busDetail;
	}

	@JsonProperty
	public String getBusDetail() {
		return busDetail;
	}

	public void setBusTime(String busTime) {
		this.busTime = busTime;
	}

	@JsonProperty
	public String getBusTime() {
		return busTime;
	}

	public void setBusHour(int busHour) {
		this.busHour = busHour;
	}

	@JsonProperty
	public int getBusHour() {
		return busHour;
	}

	public void setBusMilleage(String busMilleage) {
		this.busMilleage = busMilleage;
	}

	@JsonProperty
	public String getBusMilleage() {
		return busMilleage;
	}

	@JsonProperty
	public List<RecplanPhoto> getRecplanPhotos() {
		return recplanPhotos;
	}

	public void setRecplanPhotos(List<RecplanPhoto> recplanPhotos) {
		this.recplanPhotos = recplanPhotos;
	}

	@JsonProperty
	public String getExa() {
		return exa;
	}

	public void setExa(String exa) {
		this.exa = exa;
	}

	@JsonProperty
	public Object getDetail() {
		return detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

	@JsonProperty
	public int getLineDis() {
		return lineDis;
	}

	public void setLineDis(int lineDis) {
		this.lineDis = lineDis;
	}

	@JsonProperty
	public long getDelicacyId() {
		return delicacyId;
	}

	public void setDelicacyId(long delicacyId) {
		this.delicacyId = delicacyId;
	}

	@JsonProperty
	public Object getFoodDetail() {
		return foodDetail;
	}

	public void setFoodDetail(Object foodDetail) {
		this.foodDetail = foodDetail;
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

	@JsonProperty
	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	@JsonProperty
	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	@JsonProperty
	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
