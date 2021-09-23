package com.hmlyinfo.app.soutu.recplan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class Recplan extends BaseEntity {

	// 已删除
	public final static int DELETED_YES = 1;
	// 未删除
	public final static int DELETED_NO = 2;

	// 草稿
	public final static int STATUS_DRAFT = 1;
	// 上架
	public final static int STATUS_ADDED = 2;
	// 下架
	public final static int STATUS_OFF = 3;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户编号
	 */
	private long userId;

	/**
	 * 目的地对应的景点id
	 */
	private long scenicId;

	/**
	 * 城市id
	 */
	private long cityId;

	/**
	 * 所经过的城市
	 */
	private String cityIds;

	/**
	 * 省份
	 */
	private String province;

	/**
	 * 分享数
	 */
	private int shareNum;

	/**
	 * 引用数
	 */
	private int quoteNum;

	/**
	 * 收藏数
	 */
	private int collectNum;

	/**
	 * 行程计划名
	 */
	private String planName;

	/**
	 * 行程概述
	 */
	private String description;

	/**
	 * 行程封面图片
	 */
	private String coverPath;

	/**
	 * 行程封面图片
	 */
	private String coverSmall;

	/**
	 * 手机端封面图片
	 */
	private String phoneCoverPath;

	/**
	 * 手机端封面图片
	 */
	private String phoneCoverSmall;

	/**
	 * 游玩天数
	 */
	private int days;

	/**
	 * 景点数
	 */
	private int scenics;

	/**
	 * 住宿花费
	 */
	private double hotelCost;

	/**
	 * 交通花费
	 */
	private double travelCost;

	/**
	 * 是否有效
	 */
	private int valid;

	/**
	 * 标签
	 */
	private String tags;

	/**
	 * 排序权重
	 */
	private int weight;

	/**
	 * 选中的景点/美食累加数
	 */
	private int selected;

	/**
	 * 不包含套票总花费
	 */
	private double cost;

	/**
	 * 包含套票的总花费
	 */
	private double includeSeasonticketCost;

	/**
	 * 门票花费
	 */
	private double ticketCost;

	/**
	 * 套票花费
	 */
	private double seasonticketCost;

	/**
	 * 推荐位置（1、2、3分别代表第一、二、三位，其余数字代表无推荐位置）
	 */
	private int recLoc;

	/**
	 * 建议游玩时间
	 */
	private String bestTime;

	/**
	 * 穿衣指南
	 */
	private String advClothes;

	/**
	 * 出行必备
	 */
	private String necessity;

	/**
	 * 特别注意
	 */
	private String attention;

	/**
	 * 是否删除标志（1、是 2、否）
	 */
	private int deleteFlag;

	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 标志位：1-仅pc端显示；2-仅手机端显示；3-pc端和手机端都显示；
	 */
	private int mark;

	/**
	 * 行程下包含的行程天列表
	 */
	private List<RecplanDay> recplanDays;

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	@JsonProperty
	public long getCityId() {
		return cityId;
	}

	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}

	@JsonProperty
	public String getCityIds() {
		return cityIds;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@JsonProperty
	public String getProvince() {
		return province;
	}

	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
	}

	@JsonProperty
	public int getShareNum() {
		return shareNum;
	}

	public void setQuoteNum(int quoteNum) {
		this.quoteNum = quoteNum;
	}

	@JsonProperty
	public int getQuoteNum() {
		return quoteNum;
	}

	public void setCollectNum(int collectNum) {
		this.collectNum = collectNum;
	}

	@JsonProperty
	public int getCollectNum() {
		return collectNum;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	@JsonProperty
	public String getPlanName() {
		return planName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	@JsonProperty
	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverSmall(String coverSmall) {
		this.coverSmall = coverSmall;
	}

	@JsonProperty
	public String getCoverSmall() {
		return coverSmall;
	}

	public void setPhoneCoverPath(String phoneCoverPath) {
		this.phoneCoverPath = phoneCoverPath;
	}

	@JsonProperty
	public String getPhoneCoverPath() {
		return phoneCoverPath;
	}

	public void setPhoneCoverSmall(String phoneCoverSmall) {
		this.phoneCoverSmall = phoneCoverSmall;
	}

	@JsonProperty
	public String getPhoneCoverSmall() {
		return phoneCoverSmall;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@JsonProperty
	public int getDays() {
		return days;
	}

	public void setScenics(int scenics) {
		this.scenics = scenics;
	}

	@JsonProperty
	public int getScenics() {
		return scenics;
	}

	public void setHotelCost(double hotelCost) {
		this.hotelCost = hotelCost;
	}

	@JsonProperty
	public double getHotelCost() {
		return hotelCost;
	}

	public void setTravelCost(double travelCost) {
		this.travelCost = travelCost;
	}

	@JsonProperty
	public double getTravelCost() {
		return travelCost;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	@JsonProperty
	public int getValid() {
		return valid;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@JsonProperty
	public String getTags() {
		return tags;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@JsonProperty
	public int getWeight() {
		return weight;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	@JsonProperty
	public int getSelected() {
		return selected;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@JsonProperty
	public double getCost() {
		return cost;
	}

	public void setIncludeSeasonticketCost(double includeSeasonticketCost) {
		this.includeSeasonticketCost = includeSeasonticketCost;
	}

	@JsonProperty
	public double getIncludeSeasonticketCost() {
		return includeSeasonticketCost;
	}

	public void setTicketCost(double ticketCost) {
		this.ticketCost = ticketCost;
	}

	@JsonProperty
	public double getTicketCost() {
		return ticketCost;
	}

	public void setSeasonticketCost(double seasonticketCost) {
		this.seasonticketCost = seasonticketCost;
	}

	@JsonProperty
	public double getSeasonticketCost() {
		return seasonticketCost;
	}

	public void setRecLoc(int recLoc) {
		this.recLoc = recLoc;
	}

	@JsonProperty
	public int getRecLoc() {
		return recLoc;
	}

	@JsonProperty
	public List<RecplanDay> getRecplanDays() {
		return recplanDays;
	}

	public void setRecplanDays(List<RecplanDay> recplanDays) {
		this.recplanDays = recplanDays;
	}

	@JsonProperty
	public String getBestTime() {
		return bestTime;
	}

	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}

	@JsonProperty
	public String getAdvClothes() {
		return advClothes;
	}

	public void setAdvClothes(String advClothes) {
		this.advClothes = advClothes;
	}

	@JsonProperty
	public String getNecessity() {
		return necessity;
	}

	public void setNecessity(String necessity) {
		this.necessity = necessity;
	}

	@JsonProperty
	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	@JsonProperty
	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@JsonProperty
	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
}
