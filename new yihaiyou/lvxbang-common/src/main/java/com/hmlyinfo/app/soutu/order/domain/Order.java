package com.hmlyinfo.app.soutu.order.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class Order extends BaseEntity {

	// 订单未支付
	public final static int STATUS_NOT_PAIED = 1;
	// 订单已支付
	public final static int STATUS_PAIED = 2;

	// 订单来源（行程）
	public final static int SOURCE_PLAN = 1;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 用户id
	 */
	private long userId;

	/**
	 * 总金额
	 */
	private double price;

	/**
	 * 状态
	 */
	private int status;

	/**
	 * 支付类型
	 */
	private int payType;

	/**
	 * 支付单号
	 */
	private long payOrderId;


	/**
	 * 发送给支付宝的订单号
	 */
	private String orderNum;

	/**
	 * 订单来源
	 */
	private int sourceType;

	/**
	 * 订单来源id（planId）
	 */
	private long sourceId;

	/**
	 * 形成名称
	 */
	private String planName;

	/**
	 * 订单开始日期
	 */
	private Date startDate;

	/**
	 * 订单结束日期
	 */
	private Date endDate;


	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@JsonProperty
	public double getPrice() {
		return price;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	@JsonProperty
	public int getPayType() {
		return payType;
	}

	public void setPayOrderId(long payOrderId) {
		this.payOrderId = payOrderId;
	}

	@JsonProperty
	public long getPayOrderId() {
		return payOrderId;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@JsonProperty
	public String getOrderNum() {
		return orderNum;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	@JsonProperty
	public int getSourceType() {
		return sourceType;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	@JsonProperty
	public long getSourceId() {
		return sourceId;
	}

	@JsonProperty
	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	@JsonProperty
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonProperty
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
