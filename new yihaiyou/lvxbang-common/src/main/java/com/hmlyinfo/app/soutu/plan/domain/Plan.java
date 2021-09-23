package com.hmlyinfo.app.soutu.plan.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.base.persistent.BaseEntity;

public class Plan extends BaseEntity {

	public final static int					PLAN_STATUS_TRUE	= 1;	// 行程计划数据状态：1表示有点击保存的数据
	public final static int					PLAN_STATUS_FALSE	= 2;	// 行程计划数据状态：2表示数据暂时无效

	// 行程来自于推荐行程
	public final static int					SOURCE_REC			= 1;
	// 收藏
	public final static int					TAG_COLLECT			= 1;
	// 已购买
	public final static int					TAG_PAID			= 2;

	// 未确定显示
	public final static int					PLATFORM_UNCERTAIN	= 0;
	// PC端创建的行程
	public final static int					PLATFORM_PC			= 1;
	// H5创建的行程
	public final static int					PLATFORM_H5			= 2;

	/**
	 *
	 */
	private static final long				serialVersionUID	= 1L;

	/**
	 * 用户编号
	 */
	private long							userId;

	/**
	 * 景点编号
	 */
	private long							scenicId;

	/**
	 * 城市
	 */
	private long							cityId;

	/**
	 * 所有城市列表
	 */
	private String							cityIds;

	/**
	 * 行程计划名
	 */
	private String							planName;

	/**
	 * 出发时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date							startTime;

	/**
	 * 返回时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date							returnTime;

	/**
	 * 公开标志
	 */
	private boolean							publicFlag;

	/**
	 * 推荐标志
	 */
	private boolean							recommendFlag;

	/**
	 * 行程封面图片
	 */
	private String							coverPath;
	/**
	 * 缩略图
	 */
	private String							coverSmall;

	/**
	 * 省份
	 */
	private String							province;

	/**
	 * 行程天数
	 */
	private int								dayCount;

	/**
	 * 收藏数
	 */
	private int								collectNum;

	/**
	 * 评论数
	 */
	private int								commentNum;
	/**
	 * 引用数
	 */
	private int								quoteNum;

	/**
	 * 分享数
	 */
	private int								shareNum;

	/**
	 * 小贴士内容
	 */
	private String							tipsContent;

	/**
	 * 浏览数
	 */
	private int								viewNum;

	/**
	 * 来源（0表示原创，1表示引用）
	 */
	private int								source;

	/**
	 * 来源id
	 */
	private long							sourceId;

	/**
	 * 行程计划数据状态
	 * 0或NULL为临时数据，未保存
	 * 1为已保存
	 */
	private int								status;

	/**
	 * 行程花费
	 */
	private int								planCost;

	/**
	 * 包含套票的行程花费
	 */
	private int								includeSeasonticketCost;

	/**
	 * 行程天数，自动统计程序生成
	 */
	private int								planDays;

	/**
	 * 数据是否有效
	 */
	private boolean							valid;

	/**
	 * 行程酒店花费
	 */
	private int								planHotelCost;

	/**
	 * 行程交通花费
	 */
	private int								planTravelCost;

	/**
	 * 行程门票花费
	 */
	private int								planTicketCost;

	/**
	 * 行程门票花费（包含套票）
	 */
	private int								planSeasonticketCost;

	private transient long					joinedPlanId;
	private transient int					totalPrice;
	private transient int					scenicCount;
	private transient int					votes;
	private transient int					ranking;
	private transient Map<String, Object>	destination;
	private transient List<PlanDay>			planDayList;
	private transient PlanDay				planDay;

	private transient User					user;

	private transient List<String>			cityNames;

	/**
	 * 标签，标记已购买，已收藏等
	 */
	private int								tag;

	/**
	 * 行程描述
	 */
	private String							description;

	/**
	 * 行程平台（1、PC 2、移动端）
	 */
	private int								platform;

	/**
	 * 出发城市
	 */
	private long							startCity;

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	@JsonProperty
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public boolean isPublicFlag() {
		return publicFlag;
	}

	public void setPublicFlag(boolean publicFlag) {
		this.publicFlag = publicFlag;
	}

	public boolean isRecommendFlag() {
		return recommendFlag;
	}

	public void setRecommendFlag(boolean recommendFlag) {
		this.recommendFlag = recommendFlag;
	}

	@JsonProperty
	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	@JsonProperty
	public String getPlanName() {
		return planName;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonProperty
	public Date getStartTime() {
		return startTime;
	}

	@JsonProperty
	public int getDayCount() {
		return dayCount;
	}

	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}

	@JsonProperty
	public int getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(int collectNum) {
		this.collectNum = collectNum;
	}

	@JsonProperty
	public int getQuoteNum() {
		return quoteNum;
	}

	public void setQuoteNum(int quoteNum) {
		this.quoteNum = quoteNum;
	}

	@JsonProperty
	public int getShareNum() {
		return shareNum;
	}

	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
	}

	@JsonProperty
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	@JsonProperty
	public String getTipsContent() {
		return tipsContent;
	}

	public void setTipsContent(String tipsContent) {
		this.tipsContent = tipsContent;
	}

	@JsonProperty
	public int getViewNum() {
		return viewNum;
	}

	public void setViewNum(int viewNum) {
		this.viewNum = viewNum;
	}

	@JsonProperty
	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	@JsonProperty
	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	@JsonProperty
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonProperty
	public String getCoverSmall() {
		return coverSmall;
	}

	public void setCoverSmall(String coverSmall) {
		this.coverSmall = coverSmall;
	}

	@JsonProperty
	public int getScenicCount() {
		return scenicCount;
	}

	public void setScenicCount(int scenicCount) {
		this.scenicCount = scenicCount;
	}

	@JsonProperty
	public List<PlanDay> getPlanDayList() {
		return planDayList;
	}

	public void setPlanDayList(List<PlanDay> planDayList) {
		this.planDayList = planDayList;
	}

	@JsonProperty
	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	@JsonProperty
	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	@JsonProperty
	public Map<String, Object> getDestination() {
		return destination;
	}

	public void setDestination(Map<String, Object> destination) {
		this.destination = destination;
	}

	@JsonProperty
	public long getJoinedPlanId() {
		return joinedPlanId;
	}

	public void setJoinedPlanId(long joinedPlanId) {
		this.joinedPlanId = joinedPlanId;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	public String getCityIds() {
		return cityIds;
	}

	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}

	@JsonProperty
	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@JsonProperty
	public int getIncludeSeasonticketCost() {
		return includeSeasonticketCost;
	}

	public void setIncludeSeasonticketCost(int includeSeasonticketCost) {
		this.includeSeasonticketCost = includeSeasonticketCost;
	}

	@JsonProperty
	public PlanDay getPlanDay() {
		return planDay;
	}

	public void setPlanDay(PlanDay planDay) {
		this.planDay = planDay;
	}

	@JsonProperty
	public int getPlanHotelCost() {
		return planHotelCost;
	}

	public void setPlanHotelCost(int planHotelCost) {
		this.planHotelCost = planHotelCost;
	}

	@JsonProperty
	public int getPlanTravelCost() {
		return planTravelCost;
	}

	public void setPlanTravelCost(int planTravelCost) {
		this.planTravelCost = planTravelCost;
	}

	@JsonProperty
	public int getPlanTicketCost() {
		return planTicketCost;
	}

	public void setPlanTicketCost(int planTicketCost) {
		this.planTicketCost = planTicketCost;
	}

	@JsonProperty
	public int getPlanSeasonticketCost() {
		return planSeasonticketCost;
	}

	public void setPlanSeasonticketCost(int planSeasonticketCost) {
		this.planSeasonticketCost = planSeasonticketCost;
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
	public List<String> getCityNames() {
		return cityNames;
	}

	public void setCityNames(List<String> cityNames) {
		this.cityNames = cityNames;
	}

	@JsonProperty
	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	@JsonProperty
	public long getStartCity() {
		return startCity;
	}

	public void setStartCity(long startCity) {
		this.startCity = startCity;
	}

}
