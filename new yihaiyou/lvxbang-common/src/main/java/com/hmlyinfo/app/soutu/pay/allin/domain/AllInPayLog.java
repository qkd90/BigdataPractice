package com.hmlyinfo.app.soutu.pay.allin.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class AllInPayLog extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long orderId;

	/**
	 *
	 */
	private double totalFee;

	/**
	 *
	 */
	private String subject;

	/**
	 *
	 */
	private String orderDesc;

	/**
	 *
	 */
	private String notifyUrl;

	/**
	 *
	 */
	private String callbackUrl;

	/**
	 *
	 */
	private String notifyService;


	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	@JsonProperty
	public long getOrderId() {
		return orderId;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	@JsonProperty
	public double getTotalFee() {
		return totalFee;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@JsonProperty
	public String getSubject() {
		return subject;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	@JsonProperty
	public String getOrderDesc() {
		return orderDesc;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	@JsonProperty
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	@JsonProperty
	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setNotifyService(String notifyService) {
		this.notifyService = notifyService;
	}

	@JsonProperty
	public String getNotifyService() {
		return notifyService;
	}
}
