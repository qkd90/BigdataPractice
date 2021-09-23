package com.hmlyinfo.app.soutu.scenicTicket.qunar.domain;

import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicket;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Set;

public class QunarTicket extends ScenicTicket {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	// 在线支付
	public static final int PAY_WAY_ONLINE = 0;
	// 景区支付
	public static final int PAY_WAY_OUTLINE = 3;


	/**
	 * 产品id
	 */
	private String productId;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 票种名称
	 */
	private String ticketTypeName;

	/**
	 * 本地景点id
	 */
	private Long scenicId;

	/**
	 * 产品所属景点id列表
	 */
	private String sightIdList;

	/**
	 * 产品所属景点名称列表
	 */
	private String sightNameList;

	/**
	 * 支付方式
	 */
	private int payWay;

	/**
	 * 产品状态
	 */
	private String status;

	/**
	 * 支付等待时间（以分钟为单位）
	 */
	private int delayPayTime;

	/**
	 * 是否需要填写联系人电话
	 */
	private String needContactMobile;

	/**
	 * 是否需要填写联系人姓名
	 */
	private String needContactName;

	/**
	 * 是否需要填写联系人拼音
	 */
	private String needContactPinyin;

	/**
	 * 是否需要填写联系人邮箱
	 */
	private String needContactEmail;

	/**
	 * 是否需要填写联系人邮寄地址
	 */
	private String needContactPostalInfo;

	/**
	 * 每几张票需要一个游玩人信息
	 */
	private int passengerInfoPerNum;

	/**
	 * 是否需要填写游玩人信息
	 */
	private String needTravellerInfo;

	/**
	 * 是否填写游玩人姓名
	 */
	private String needPassengerName;

	/**
	 * 是否填写游玩人拼音
	 */
	private String needPassengerPinyin;

	/**
	 * 16.是否填写游玩人身份账号（如果16-19存在多个为true，游客只需填写一个）
	 */
	private String needPassengerIDCard;

	/**
	 * 17.是否填写游玩人护照
	 */
	private String needPassengerPassport;

	/**
	 * 18.是否填写游玩人台湾通行证
	 */
	private String needPassengerTaiwanPermit;

	/**
	 * 19.是否填写游玩人港澳通行证
	 */
	private String needPassengerHKAndMacauPermit;

	/**
	 * 填写游玩人信息时是否需要自定义项1
	 */
	private String needPassengerUserDefinedI;

	/**
	 * 填写游玩人信息时是否需要自定义项2
	 */
	private String needPassengerUserDefinedII;

	/**
	 * 填写游玩人信息时的自定义项1
	 */
	private String passengerUserDefinedI;

	/**
	 * 填写游玩人信息时的自定义项2
	 */
	private String passengerUserDefinedII;

	/**
	 * 是否限制入园 0=不限制，1=限制入园提前预订，2=限制预订后时间
	 */
	private int limitEnterType;

	/**
	 * 提前预订时间（天）
	 */
	private int advanceDay;

	/**
	 * 提前预订时间，格式：HH:mm，
	 */
	private String advanceTime;

	/**
	 * 预订后N小时后才能入园
	 */
	private int enterAfterBookHour;

	/**
	 * 价格模式 0日历 1分类
	 */
	private int teamType;

	/**
	 * 分类产品可以用日期时间段（排除不可使用日期,不可用星期，日历产品返回空）
	 */
	private String canUseDate;

	/**
	 * 每周可以使用的星期数（分类产品有效，日历产品为空）
	 */
	private String accurateDayofweek;

	/**
	 * 是否限制使用有效时间范围
	 */
	private String limitUseTimeRange;

	/**
	 * 使用有效开始时间，格式：HH:mm，limitUseTimeRange=true有效
	 */
	private String useTimeRangeStart;

	/**
	 * 使用有效结束时间，格式：HH:mm ，limitUseTimeRange=true有效
	 */
	private String useTimeRangeEnd;

	/**
	 * 使用说明
	 */
	private String useDescription;

	/**
	 * 费用说明
	 */
	private String feeDescription;

	/**
	 * 支持退款
	 */
	private String canRefund;

	/**
	 * 退款必须发生在有效期前的某天数
	 */
	private int refundAdvanceDay;

	/**
	 * 退款必须发生在有效期前的某时刻，格式：HH:mm
	 */
	private String refundAdvanceTime;

	/**
	 * 退款手续费收取类型，0=按订单，1=按张数
	 */
	private int refundPoundageType;

	/**
	 * 退款手续费（单位是元）
	 */
	private double refundPoundage;

	/**
	 * 退款说明
	 */
	private String refundDescription;

	/**
	 * 供应商名称
	 */
	private String supplierName;

	/**
	 * 供应商电话
	 */
	private String supplierTelPhone;

	/**
	 * 上架时间，格式：yyyy-MM-dd HH:mm:ss.SSS
	 */
	private String publishTime;

	/**
	 * 下架时间，格式：yyyy-MM-dd HH:mm:ss.SSS
	 */
	private String expireTime;

	/**
	 * 门票价格
	 */
	private QunarPrice price;

	/**
	 * 是否为主要门票，T表示首要门票
	 */
	private String primaryFlag;

	/**
	 * 表示联票，T表示为联票
	 */
	private String seasonTicketFlag;

	/**
	 * 门票对应的景点列表
	 */
	private Set<Long> scenicIdList;

	/**
	 * 临时变量
	 */
	private transient int comSize;

	@JsonProperty
	public QunarPrice getPrice() {
		return price;
	}

	public void setPrice(QunarPrice price) {
		this.price = price;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@JsonProperty
	public String getProductId() {
		return productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@JsonProperty
	public String getProductName() {
		return productName;
	}

	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}

	@JsonProperty
	public String getTicketTypeName() {
		return ticketTypeName;
	}

	public void setScenicId(Long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public Long getScenicId() {
		return scenicId;
	}

	public void setSightIdList(String sightIdList) {
		this.sightIdList = sightIdList;
	}

	@JsonProperty
	public String getSightIdList() {
		return sightIdList;
	}

	public void setSightNameList(String sightNameList) {
		this.sightNameList = sightNameList;
	}

	@JsonProperty
	public String getSightNameList() {
		return sightNameList;
	}

	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}

	@JsonProperty
	public int getPayWay() {
		return payWay;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty
	public String getStatus() {
		return status;
	}

	public void setDelayPayTime(int delayPayTime) {
		this.delayPayTime = delayPayTime;
	}

	@JsonProperty
	public int getDelayPayTime() {
		return delayPayTime;
	}

	public void setNeedContactMobile(String needContactMobile) {
		this.needContactMobile = needContactMobile;
	}

	@JsonProperty
	public String getNeedContactMobile() {
		return needContactMobile;
	}

	public void setNeedContactName(String needContactName) {
		this.needContactName = needContactName;
	}

	@JsonProperty
	public String getNeedContactName() {
		return needContactName;
	}

	public void setNeedContactPinyin(String needContactPinyin) {
		this.needContactPinyin = needContactPinyin;
	}

	@JsonProperty
	public String getNeedContactPinyin() {
		return needContactPinyin;
	}

	public void setNeedContactEmail(String needContactEmail) {
		this.needContactEmail = needContactEmail;
	}

	@JsonProperty
	public String getNeedContactEmail() {
		return needContactEmail;
	}

	public void setNeedContactPostalInfo(String needContactPostalInfo) {
		this.needContactPostalInfo = needContactPostalInfo;
	}

	@JsonProperty
	public String getNeedContactPostalInfo() {
		return needContactPostalInfo;
	}

	public void setPassengerInfoPerNum(int passengerInfoPerNum) {
		this.passengerInfoPerNum = passengerInfoPerNum;
	}

	@JsonProperty
	public int getPassengerInfoPerNum() {
		return passengerInfoPerNum;
	}

	public void setNeedTravellerInfo(String needTravellerInfo) {
		this.needTravellerInfo = needTravellerInfo;
	}

	@JsonProperty
	public String getNeedTravellerInfo() {
		return needTravellerInfo;
	}

	public void setNeedPassengerName(String needPassengerName) {
		this.needPassengerName = needPassengerName;
	}

	@JsonProperty
	public String getNeedPassengerName() {
		return needPassengerName;
	}

	public void setNeedPassengerPinyin(String needPassengerPinyin) {
		this.needPassengerPinyin = needPassengerPinyin;
	}

	@JsonProperty
	public String getNeedPassengerPinyin() {
		return needPassengerPinyin;
	}

	public void setNeedPassengerIDCard(String needPassengerIDCard) {
		this.needPassengerIDCard = needPassengerIDCard;
	}

	@JsonProperty
	public String getNeedPassengerIDCard() {
		return needPassengerIDCard;
	}

	public void setNeedPassengerPassport(String needPassengerPassport) {
		this.needPassengerPassport = needPassengerPassport;
	}

	@JsonProperty
	public String getNeedPassengerPassport() {
		return needPassengerPassport;
	}

	public void setNeedPassengerTaiwanPermit(String needPassengerTaiwanPermit) {
		this.needPassengerTaiwanPermit = needPassengerTaiwanPermit;
	}

	@JsonProperty
	public String getNeedPassengerTaiwanPermit() {
		return needPassengerTaiwanPermit;
	}

	public void setNeedPassengerHKAndMacauPermit(String needPassengerHKAndMacauPermit) {
		this.needPassengerHKAndMacauPermit = needPassengerHKAndMacauPermit;
	}

	@JsonProperty
	public String getNeedPassengerHKAndMacauPermit() {
		return needPassengerHKAndMacauPermit;
	}

	public void setNeedPassengerUserDefinedI(String needPassengerUserDefinedI) {
		this.needPassengerUserDefinedI = needPassengerUserDefinedI;
	}

	@JsonProperty
	public String getNeedPassengerUserDefinedI() {
		return needPassengerUserDefinedI;
	}

	public void setNeedPassengerUserDefinedII(String needPassengerUserDefinedII) {
		this.needPassengerUserDefinedII = needPassengerUserDefinedII;
	}

	@JsonProperty
	public String getNeedPassengerUserDefinedII() {
		return needPassengerUserDefinedII;
	}

	public void setPassengerUserDefinedI(String passengerUserDefinedI) {
		this.passengerUserDefinedI = passengerUserDefinedI;
	}

	@JsonProperty
	public String getPassengerUserDefinedI() {
		return passengerUserDefinedI;
	}

	public void setPassengerUserDefinedII(String passengerUserDefinedII) {
		this.passengerUserDefinedII = passengerUserDefinedII;
	}

	@JsonProperty
	public String getPassengerUserDefinedII() {
		return passengerUserDefinedII;
	}

	public void setLimitEnterType(int limitEnterType) {
		this.limitEnterType = limitEnterType;
	}

	@JsonProperty
	public int getLimitEnterType() {
		return limitEnterType;
	}

	public void setAdvanceDay(int advanceDay) {
		this.advanceDay = advanceDay;
	}

	@JsonProperty
	public int getAdvanceDay() {
		return advanceDay;
	}

	public void setAdvanceTime(String advanceTime) {
		this.advanceTime = advanceTime;
	}

	@JsonProperty
	public String getAdvanceTime() {
		return advanceTime;
	}

	public void setEnterAfterBookHour(int enterAfterBookHour) {
		this.enterAfterBookHour = enterAfterBookHour;
	}

	@JsonProperty
	public int getEnterAfterBookHour() {
		return enterAfterBookHour;
	}

	public void setTeamType(int teamType) {
		this.teamType = teamType;
	}

	@JsonProperty
	public int getTeamType() {
		return teamType;
	}

	public void setCanUseDate(String canUseDate) {
		this.canUseDate = canUseDate;
	}

	@JsonProperty
	public String getCanUseDate() {
		return canUseDate;
	}

	public void setAccurateDayofweek(String accurateDayofweek) {
		this.accurateDayofweek = accurateDayofweek;
	}

	@JsonProperty
	public String getAccurateDayofweek() {
		return accurateDayofweek;
	}

	public void setLimitUseTimeRange(String limitUseTimeRange) {
		this.limitUseTimeRange = limitUseTimeRange;
	}

	@JsonProperty
	public String getLimitUseTimeRange() {
		return limitUseTimeRange;
	}

	public void setUseTimeRangeStart(String useTimeRangeStart) {
		this.useTimeRangeStart = useTimeRangeStart;
	}

	@JsonProperty
	public String getUseTimeRangeStart() {
		return useTimeRangeStart;
	}

	public void setUseTimeRangeEnd(String useTimeRangeEnd) {
		this.useTimeRangeEnd = useTimeRangeEnd;
	}

	@JsonProperty
	public String getUseTimeRangeEnd() {
		return useTimeRangeEnd;
	}

	public void setUseDescription(String useDescription) {
		this.useDescription = useDescription;
	}

	@JsonProperty
	public String getUseDescription() {
		return useDescription;
	}

	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}

	@JsonProperty
	public String getFeeDescription() {
		return feeDescription;
	}

	public void setCanRefund(String canRefund) {
		this.canRefund = canRefund;
	}

	@JsonProperty
	public String getCanRefund() {
		return canRefund;
	}

	public void setRefundAdvanceDay(int refundAdvanceDay) {
		this.refundAdvanceDay = refundAdvanceDay;
	}

	@JsonProperty
	public int getRefundAdvanceDay() {
		return refundAdvanceDay;
	}

	public void setRefundAdvanceTime(String refundAdvanceTime) {
		this.refundAdvanceTime = refundAdvanceTime;
	}

	@JsonProperty
	public String getRefundAdvanceTime() {
		return refundAdvanceTime;
	}

	public void setRefundPoundageType(int refundPoundageType) {
		this.refundPoundageType = refundPoundageType;
	}

	@JsonProperty
	public int getRefundPoundageType() {
		return refundPoundageType;
	}

	public void setRefundPoundage(double refundPoundage) {
		this.refundPoundage = refundPoundage;
	}

	@JsonProperty
	public double getRefundPoundage() {
		return refundPoundage;
	}

	public void setRefundDescription(String refundDescription) {
		this.refundDescription = refundDescription;
	}

	@JsonProperty
	public String getRefundDescription() {
		return refundDescription;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@JsonProperty
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierTelPhone(String supplierTelPhone) {
		this.supplierTelPhone = supplierTelPhone;
	}

	@JsonProperty
	public String getSupplierTelPhone() {
		return supplierTelPhone;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	@JsonProperty
	public String getPublishTime() {
		return publishTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	@JsonProperty
	public String getExpireTime() {
		return expireTime;
	}

	@JsonProperty
	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

	@JsonProperty
	public String getSeasonTicketFlag() {
		return seasonTicketFlag;
	}

	public void setSeasonTicketFlag(String seasonTicketFlag) {
		this.seasonTicketFlag = seasonTicketFlag;
	}

	@JsonProperty
	public Set<Long> getScenicIdList() {
		return scenicIdList;
	}

	public void setScenicIdList(Set<Long> scenicIdList) {
		this.scenicIdList = scenicIdList;
	}

	@JsonProperty
	public int getComSize() {
		return comSize;
	}

	public void setComSize(int comSize) {
		this.comSize = comSize;
	}


}
