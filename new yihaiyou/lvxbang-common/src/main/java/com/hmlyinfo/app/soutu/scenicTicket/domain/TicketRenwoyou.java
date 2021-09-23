package com.hmlyinfo.app.soutu.scenicTicket.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class TicketRenwoyou extends ScenicTicket {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 本地景点id
	 */
	private Long scenicId;

	/**
	 * 外部门票id
	 */
	private long outerTicketId;

	/**
	 * 门票状态，1：为正常；0：为下架，表示不能购买
	 */
	private boolean status;

	/**
	 * 景区门票名称
	 */
	private String name;

	/**
	 * 门票描述
	 */
	private String description;

	/**
	 * 分销价格
	 */
	private double salePrice;

	/**
	 * 建议销售价格
	 */
	private double suggestPrice;

	/**
	 * 市场价格
	 */
	private double marketPrice;

	/**
	 * 旅行帮销售价格
	 */
	private double lvxbangPrice;

	/**
	 * 返现
	 */
	private double discount;

	/**
	 * 评论后返现
	 */
	private double commentDiscount;
	/**
	 * 门票供应商合作的截止时间，门票的使用有效期，最大不超过该截止时间
	 */
	private Date endOfTime;

	/**
	 * 预订提示
	 */
	private String notice;

	/**
	 * 预订提前的天数，不小于0的整数，如果为零，表示可以预订当天
	 */
	private int orderAdvanceDays;

	/**
	 * 0到23之间的整数，表示预订，需要在这个小时数之前，24小时制
	 */
	private int orderBeforeHour;

	/**
	 * 0到55之间的整数，表示预订，需要在这个分钟数之前
	 */
	private int orderBeforeMin;

	/**
	 * 最多可以提前预订的天数
	 */
	private int maxOrderAdvanceDays;

	/**
	 * 退款需提前有效期的小时数，当为“0”时，表示在有效期内，都可以退款，否则，必须比有效期开始时，提前多少个小时退款
	 */
	private int refundAdvanceHours;

	/**
	 * 需提醒客户注意的信息，比如，景区开放时间，取票地点等等
	 */
	private String additionalInfo;

	/**
	 * 景点门票使用有效期，单位天
	 */
	private int effectiveDate;

	/**
	 * 景区开放的星期X，eg：validWeeks=1,3,5,7，表示景区只在星期一、三、五、日开放
	 */
	private String validWeeks;

	/**
	 * 门票的特殊有效日期，比如黄金周，只在黄金周那几天有效
	 */
	private String validDates;

	/**
	 * 是否是库存限制的门票，0：非库存限制，1：库存限制，有库存限制的门票，预订前，要先判断是否有库存，或者下单分预下单和支付两步走
	 */
	private boolean isInvtLimit;

	/**
	 * 是否为是实名制景区，0：非实名制，1：实名制，实名制景区，必须正确录入每位游客的姓名和身份证
	 */
	private boolean isRealName;

	/**
	 * 是否必须填入一个游客的身份证号码，以便验证，0：不需要，1：必须填入
	 */
	private boolean idCardNeeded;

	/**
	 * 是否要指定出发日期，1：必须指定出发日期，格式yyyy-MM-dd
	 */
	private boolean startDateFlag;

	/**
	 * 景点名称
	 */
	private String scenicName;

	/**
	 * 是否为主要门票，T表示首要门票
	 */
	private String primaryFlag;

	/**
	 * 表示联票，T表示为联票
	 */
	private String seasonTicketFlag;

	public void setScenicId(Long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public Long getScenicId() {
		return scenicId;
	}

	public void setOuterTicketId(long outerTicketId) {
		this.outerTicketId = outerTicketId;
	}

	@JsonProperty
	public long getOuterTicketId() {
		return outerTicketId;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@JsonProperty
	public boolean getStatus() {
		return status;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	@JsonProperty
	public double getSalePrice() {
		return salePrice;
	}

	public void setSuggestPrice(double suggestPrice) {
		this.suggestPrice = suggestPrice;
	}

	@JsonProperty
	public double getSuggestPrice() {
		return suggestPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	@JsonProperty
	public double getMarketPrice() {
		return marketPrice;
	}

	public void setEndOfTime(Date endOfTime) {
		this.endOfTime = endOfTime;
	}

	@JsonProperty
	public Date getEndOfTime() {
		return endOfTime;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	@JsonProperty
	public String getNotice() {
		return notice;
	}

	public void setOrderAdvanceDays(int orderAdvanceDays) {
		this.orderAdvanceDays = orderAdvanceDays;
	}

	@JsonProperty
	public int getOrderAdvanceDays() {
		return orderAdvanceDays;
	}

	public void setOrderBeforeHour(int orderBeforeHour) {
		this.orderBeforeHour = orderBeforeHour;
	}

	@JsonProperty
	public int getOrderBeforeHour() {
		return orderBeforeHour;
	}

	public void setOrderBeforeMin(int orderBeforeMin) {
		this.orderBeforeMin = orderBeforeMin;
	}

	@JsonProperty
	public int getOrderBeforeMin() {
		return orderBeforeMin;
	}

	public void setMaxOrderAdvanceDays(int maxOrderAdvanceDays) {
		this.maxOrderAdvanceDays = maxOrderAdvanceDays;
	}

	@JsonProperty
	public int getMaxOrderAdvanceDays() {
		return maxOrderAdvanceDays;
	}

	public void setRefundAdvanceHours(int refundAdvanceHours) {
		this.refundAdvanceHours = refundAdvanceHours;
	}

	@JsonProperty
	public int getRefundAdvanceHours() {
		return refundAdvanceHours;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@JsonProperty
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setEffectiveDate(int effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@JsonProperty
	public int getEffectiveDate() {
		return effectiveDate;
	}

	public void setValidWeeks(String validWeeks) {
		this.validWeeks = validWeeks;
	}

	@JsonProperty
	public String getValidWeeks() {
		return validWeeks;
	}

	public void setValidDates(String validDates) {
		this.validDates = validDates;
	}

	@JsonProperty
	public String getValidDates() {
		return validDates;
	}

	public void setIsInvtLimit(boolean isInvtLimit) {
		this.isInvtLimit = isInvtLimit;
	}

	@JsonProperty
	public boolean getIsInvtLimit() {
		return isInvtLimit;
	}

	public void setIsRealName(boolean isRealName) {
		this.isRealName = isRealName;
	}

	@JsonProperty
	public boolean getIsRealName() {
		return isRealName;
	}

	public void setIdCardNeeded(boolean idCardNeeded) {
		this.idCardNeeded = idCardNeeded;
	}

	@JsonProperty
	public boolean getIdCardNeeded() {
		return idCardNeeded;
	}

	public void setStartDateFlag(boolean startDateFlag) {
		this.startDateFlag = startDateFlag;
	}

	@JsonProperty
	public boolean getStartDateFlag() {
		return startDateFlag;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	@JsonProperty
	public String getScenicName() {
		return scenicName;
	}

	@JsonProperty
	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

	@JsonProperty
	public double getLvxbangPrice() {
		return lvxbangPrice;
	}

	public void setLvxbangPrice(double lvxbangPrice) {
		this.lvxbangPrice = lvxbangPrice;
	}

	@JsonProperty
	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@JsonProperty
	public double getCommentDiscount() {
		return commentDiscount;
	}

	public void setCommentDiscount(double commentDiscount) {
		this.commentDiscount = commentDiscount;
	}

	@JsonProperty
	public String getSeasonTicketFlag() {
		return seasonTicketFlag;
	}

	public void setSeasonTicketFlag(String seasonTicketFlag) {
		this.seasonTicketFlag = seasonTicketFlag;
	}
}
