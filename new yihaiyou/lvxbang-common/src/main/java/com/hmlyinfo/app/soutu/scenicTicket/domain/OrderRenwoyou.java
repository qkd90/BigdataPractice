package com.hmlyinfo.app.soutu.scenicTicket.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class OrderRenwoyou extends BaseEntity {

	public static final int STATUS_ORDERED = 1;
	public static final int STATUS_PAID = 2;
	public static final int STATUS_FAILD = 3;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String orderNum;

	/**
	 *
	 */
	private long scenicTicketId;

	/**
	 *
	 */
	private long scenicTicketOrderId;

	/**
	 *
	 */
	private long subOrderId;

	/**
	 *
	 */
	private long scenicId;

	/**
	 *
	 */
	private long outerId;

	/**
	 *
	 */
	private int count;

	/**
	 *
	 */
	private Date ticketDate;

	/**
	 *
	 */
	private double totalAmount;

	/**
	 *
	 */
	private int status;

	/**
	 * 实际要求支付金额
	 */
	private double realAmount;

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

	public void setScenicTicketId(long scenicTicketId) {
		this.scenicTicketId = scenicTicketId;
	}

	@JsonProperty
	public long getScenicTicketId() {
		return scenicTicketId;
	}

	public void setScenicTicketOrderId(long scenicTicketOrderId) {
		this.scenicTicketOrderId = scenicTicketOrderId;
	}

	@JsonProperty
	public long getScenicTicketOrderId() {
		return scenicTicketOrderId;
	}

	@JsonProperty
	public long getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(long subOrderId) {
		this.subOrderId = subOrderId;
	}

	public void setScenicId(long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public long getScenicId() {
		return scenicId;
	}

	public void setOuterId(long outerId) {
		this.outerId = outerId;
	}

	@JsonProperty
	public long getOuterId() {
		return outerId;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@JsonProperty
	public int getCount() {
		return count;
	}

	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}

	@JsonProperty
	public Date getTicketDate() {
		return ticketDate;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@JsonProperty
	public double getTotalAmount() {
		return totalAmount;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}

	@JsonProperty
	public double getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(double realAmount) {
		this.realAmount = realAmount;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
}
