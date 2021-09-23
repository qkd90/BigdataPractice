package com.hmlyinfo.app.soutu.scenicTicket.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScenicTicketOrder extends BaseEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final int OUTER_TYPE_RENWOYOU = 1;        //任我游服务器
	public static final int STATUS_FAILED = -1;             //支付失败
	public static final int STATUS_WAITING = 0;             //等待处理中
	public static final int STATUS_NOT_PAID = 1;            //未支付
	public static final int STATUS_PAID = 2;                //已支付
	public static final int STATUS_CHECKED = 3;             //已出票
	public static final int STATUS_ORDER_TICKET = 11;       //订票
	public static final int STATUS_REFUNDING_ORDER = 12;    //退票
	public static final int STATUS_REFUND_ORDER = 13;       //退款
	public static final int STATUS_USED = 14;
	// 还未推送
	public static int PUSH_NO = 1;
	// 已经通过微信推送购买成功
	public static int PUSH_YES = 2;

	// 微信支付
	public static int PAY_TYPE_WEIXIN = 1;
	// 支付宝支付
	public static int PAY_TYPE_ALI = 102;

	// 是套票
	public static int SEASON_TICKET_YES = 1;
	// 非套票
	public static int SEASON_TICKET_NO = 2;
	// 在线支付
	public static int PAY_ONLINE = 1;
	// 离线支付
	public static int PAY_OFFLINE = 2;


	private String orderNum;

	private Long planId;

	/**
	 * 用户id
	 */
	private long userId;

	/**
	 * 手机
	 */
	private String mobile;

	/**
	 * 购买人姓名
	 */
	private String buyerName;

	/**
	 * 购买人身份证号码
	 */
	private String idCardNo;

	/**
	 * 购买人邮箱
	 */
	private String buyerEmail;

	/**
	 * 购买人邮编
	 */
	private String buyerPostCode;

	/**
	 * 购买人地址
	 */
	private String buyerAddress;

	/**
	 * 临时的门票集合
	 */
	private transient List<ScenicTicketSubOrder> subOrders = new ArrayList<ScenicTicketSubOrder>();

	/**
	 * 该订单包含的所有景点列表，以逗号隔开
	 */
	private transient String scenicIds;

	/**
	 * 支付的订单
	 */
	private Long payOrder;

	/**
	 * 是否是整个行程行程
	 */
	private boolean planFlag;

	/**
	 * 支付的类型
	 */
	private int payType;

	/**
	 * 支付状态
	 */
	private int status;

	/**
	 * 订单名称
	 */
	private String orderName;

	/**
	 * 订单日期
	 */
	private Date orderDate;

	/**
	 * 订单总价
	 */
	private double totalFee;

	private int count;

	private int seasonTicket;

	private int onlinePay;

	private int pushFlag;

	/**
	 * tb_order的id
	 */
	private long orderId;


	@JsonProperty
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@JsonProperty
	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	@JsonProperty
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	@JsonProperty
	public String getBuyerName() {
		return buyerName;
	}

	@JsonProperty
	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	@JsonProperty
	public List<ScenicTicketSubOrder> getSubOrders() {
		return subOrders;
	}

	public void setSubOrders(List<ScenicTicketSubOrder> subOrders) {
		this.subOrders = subOrders;
	}


	@JsonProperty
	public String getScenicIds() {
		return scenicIds;
	}

	public void setScenicIds(String scenicIds) {
		this.scenicIds = scenicIds;
	}

	@JsonProperty
	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	@JsonProperty
	public Long getPayOrder() {
		return payOrder;
	}

	public void setPayOrder(Long payOrder) {
		this.payOrder = payOrder;
	}

	@JsonProperty
	public boolean isPlanFlag() {
		return planFlag;
	}

	public void setPlanFlag(boolean planFlag) {
		this.planFlag = planFlag;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JsonProperty
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@JsonProperty
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	@JsonProperty
	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	@JsonProperty
	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	@JsonProperty
	public String getBuyerPostCode() {
		return buyerPostCode;
	}

	public void setBuyerPostCode(String buyerPostCode) {
		this.buyerPostCode = buyerPostCode;
	}

	@JsonProperty
	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	@JsonProperty
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@JsonProperty
	public int getSeasonTicket() {
		return seasonTicket;
	}

	public void setSeasonTicket(int seasonTicket) {
		this.seasonTicket = seasonTicket;
	}

	@JsonProperty
	public int getOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(int onlinePay) {
		this.onlinePay = onlinePay;
	}


	@JsonProperty
	public int getPushFlag() {
		return pushFlag;
	}

	public void setPushFlag(int pushFlag) {
		this.pushFlag = pushFlag;
	}

	@JsonProperty
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
}
