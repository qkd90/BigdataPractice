package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.Map;

public class RecommendPlan extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 行程编号
	 */
	private long planId;

	/**
	 * 用户编号
	 */
	private long userId;

	/**
	 * 主要城市编号
	 */
	private long cityId;

	/**
	 * 推荐权重
	 */
	private long recommendWeight;

	/**
	 * 推荐人
	 */
	private long recommendName;

	/**
	 * 推荐理由
	 */
	private String recommendReason;

	/**
	 * 去过的人数
	 */
	private int goneCount;

	/**
	 * 将前往的人数
	 */
	private int goingCount;

	/**
	 * 亮点
	 */
	private String label;

	/**
	 * 行程花费
	 */
	private int planCost;

	/**
	 * 行程天数
	 */
	private int planDays;

	/**
	 * 行程名称
	 */
	private transient String planName;

	/**
	 * 临时计算行程天数
	 */
	private transient int planDayCount;

	/**
	 * 临时计算的景点数
	 */
	private transient int scenicCount;

	/**
	 * 临时推荐行程天的集合
	 */
	private transient List<RecommendPlanDay> recommendPlanDay;

	/**
	 * 目的地信息
	 */
	private transient Map<String, Object> destination;

	/**
	 * 临时的旅游小贴士
	 *
	 * @return
	 */
	private transient String tipsContent;

	/**
	 * 整个行程的花费
	 *
	 * @return
	 */
	private transient float costTotal;

	/**
	 * 亮点集合
	 */
	private transient String[] labels;

	private transient int quoteNum;

	/**
	 * 封面图片
	 */
	private String cover;

	/**
	 * 推荐行程标签
	 */
	private String tag;

	private int recommendStatus;

	private String cities;

	private int salePrice;
	
	/**
	 * 标志位：1-仅pc端显示；2-仅手机端显示；3-pc端和手机端都显示；
	 */
	private int mark;
	
	/**
	 * 推荐位置（1、2、3分别代表第一、二、三位，99代表无推荐位置）
	 */
	private int recLoc;

	@JsonProperty
	public int getQuoteNum() {
		return quoteNum;
	}

	public void setQuoteNum(int quoteNum) {
		this.quoteNum = quoteNum;
	}

	@JsonProperty
	public float getCostTotal() {
		return costTotal;
	}

	public void setCostTotal(float costTotal) {
		this.costTotal = costTotal;
	}

	@JsonProperty
	public List<RecommendPlanDay> getRecommendPlanDay() {
		return recommendPlanDay;
	}

	public void setRecommendPlanDay(List<RecommendPlanDay> recommendPlanDay) {
		this.recommendPlanDay = recommendPlanDay;
	}

	@JsonProperty
	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	@JsonProperty
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@JsonProperty
	public String getTipsContent() {
		return tipsContent;
	}

	public void setTipsContent(String tipsContent) {
		this.tipsContent = tipsContent;
	}

	@JsonProperty
	public int getScenicCount() {
		return scenicCount;
	}

	public void setScenicCount(int scenicCount) {
		this.scenicCount = scenicCount;
	}

	@JsonProperty
	public int getPlanDayCount() {
		return planDayCount;
	}

	public void setPlanDayCount(int planDayCount) {
		this.planDayCount = planDayCount;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	@JsonProperty
	public long getPlanId() {
		return planId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	@JsonProperty
	public long getCityId() {
		return cityId;
	}

	public void setRecommendWeight(long recommendWeight) {
		this.recommendWeight = recommendWeight;
	}

	@JsonProperty
	public long getRecommendWeight() {
		return recommendWeight;
	}

	public void setRecommendName(long recommendName) {
		this.recommendName = recommendName;
	}

	@JsonProperty
	public long getRecommendName() {
		return recommendName;
	}

	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}

	@JsonProperty
	public String getRecommendReason() {
		return recommendReason;
	}

	@JsonProperty
	public int getGoneCount() {
		return goneCount;
	}

	public void setGoneCount(int goneCount) {
		this.goneCount = goneCount;
	}

	@JsonProperty
	public int getGoingCount() {
		return goingCount;
	}

	public void setGoingCount(int goingCount) {
		this.goingCount = goingCount;
	}

	@JsonProperty
	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	@JsonProperty
	public Map<String, Object> getDestination() {
		return destination;
	}

	public void setDestination(Map<String, Object> destination) {
		this.destination = destination;
	}

	@JsonProperty
	public int getPlanCost() {
		return planCost;
	}

	public void setPlanCost(int planCost) {
		this.planCost = planCost;
	}

	@JsonProperty
	public int getPlanDays() {
		return planDays;
	}

	public void setPlanDays(int planDays) {
		this.planDays = planDays;
	}

	@JsonProperty
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@JsonProperty
	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	@JsonProperty
	public int getRecommendStatus() {
		return recommendStatus;
	}

	public void setRecommendStatus(int recommendStatus) {
		this.recommendStatus = recommendStatus;
	}

	@JsonProperty
	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}

	@JsonProperty
	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	
	@JsonProperty
	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
	
	@JsonProperty
	public int getRecLoc() {
		return recLoc;
	}

	public void setRecLoc(int recLoc) {
		this.recLoc = recLoc;
	}
}
