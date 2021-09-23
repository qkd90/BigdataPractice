package com.hmlyinfo.app.soutu.scenicTicket.qunar.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class QunarOrder extends BaseEntity {

	public static int ORDER_STATUS_SUCCESS = 1;
	public static int ORDER_STATUS_FAILED = 2;

	public static int PAY_STATUS_SUCCESS = 1;
	public static int PAY_STATUS_FAILED = 2;

	public static int TICKETING_STATUS_SUCCESS = 1;
	public static int TICKETING_STATUS_FAILED = 2;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	private String orderNum;

	/**
	 *
	 */
	private long subOrderId;

	/**
	 * 所属景点id
	 */
	private long scenicId;

	/**
	 * Qunar订单id
	 */
	private String qunarOrderId;

	/**
	 * 订单联系电话
	 */
	private String contactMobile;

	/**
	 * 订单联系人，部分订单要求输入
	 */
	private String contactUser;

	/**
	 * 订单联系邮箱，部分订单要求输入
	 */
	private String contactEmail;

	/**
	 * 订单联系人拼音，部分订单要求输入
	 */
	private String contactUserPinyin;

	/**
	 * 订单联系人地址邮编，部分订单要求输入
	 */
	private String contactPostCode;

	/**
	 * 订单联系人地址，部分订单要求输入
	 */
	private String contactPostAddress;

	/**
	 * 购买门票数量
	 */
	private int quantity;

	/**
	 * 使用日期，格式为YYYY-MM-DD（下单产品的价格模式是日历模式时必填）
	 */
	private String useDate;

	/**
	 * 产品ID
	 */
	private String productId;

	/**
	 * 单个产品分销价
	 */
	private String qunarPrice;

	/**
	 * 订单状态（1.创建成功 2.创建失败）
	 */
	private int orderStatus;

	/**
	 * 支付状态（1.已支付 2.未支付 ）
	 */
	private int payStatus;

	/**
	 * 出票状态（1.已出票 2.未出票）
	 */
	private int ticketingStatus;

	/**
	 * Qunar返回的付款金额
	 */
	private double payAmount;

	/**
	 * 失败原因
	 */
	private String failReason;


	@JsonProperty
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@JsonProperty
	public long getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(long subOrderId) {
		this.subOrderId = subOrderId;
	}

	public void setQunarOrderId(String qunarOrderId) {
		this.qunarOrderId = qunarOrderId;
	}

	@JsonProperty
	public String getQunarOrderId() {
		return qunarOrderId;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	@JsonProperty
	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}

	@JsonProperty
	public String getContactUser() {
		return contactUser;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	@JsonProperty
	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactUserPinyin(String contactUserPinyin) {
		this.contactUserPinyin = contactUserPinyin;
	}

	@JsonProperty
	public String getContactUserPinyin() {
		return contactUserPinyin;
	}

	public void setContactPostCode(String contactPostCode) {
		this.contactPostCode = contactPostCode;
	}

	@JsonProperty
	public String getContactPostCode() {
		return contactPostCode;
	}

	public void setContactPostAddress(String contactPostAddress) {
		this.contactPostAddress = contactPostAddress;
	}

	@JsonProperty
	public String getContactPostAddress() {
		return contactPostAddress;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@JsonProperty
	public int getQuantity() {
		return quantity;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	@JsonProperty
	public String getUseDate() {
		return useDate;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@JsonProperty
	public String getProductId() {
		return productId;
	}

	public void setQunarPrice(String qunarPrice) {
		this.qunarPrice = qunarPrice;
	}

	@JsonProperty
	public String getQunarPrice() {
		return qunarPrice;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	@JsonProperty
	public int getOrderStatus() {
		return orderStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	@JsonProperty
	public int getPayStatus() {
		return payStatus;
	}

	public void setTicketingStatus(int ticketingStatus) {
		this.ticketingStatus = ticketingStatus;
	}

	@JsonProperty
	public int getTicketingStatus() {
		return ticketingStatus;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	@JsonProperty
	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
}
