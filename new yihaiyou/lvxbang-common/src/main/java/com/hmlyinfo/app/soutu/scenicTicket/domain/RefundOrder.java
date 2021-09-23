package com.hmlyinfo.app.soutu.scenicTicket.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class RefundOrder extends BaseEntity {

	public static final int STATUS_REFUND_SUCCESS = 1;
	public static final int STATUS_REFUND_WAIT = 0;
	private static final long serialVersionUID = 1L;

	private long payOrderId;
	private double refundFee;
	private int status;


	public void setPayOrderId(long payOrderId) {
		this.payOrderId = payOrderId;
	}

	@JsonProperty
	public long getPayOrderId() {
		return payOrderId;
	}

	public void setRefundFee(double refundFee) {
		this.refundFee = refundFee;
	}

	@JsonProperty
	public double getRefundFee() {
		return refundFee;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JsonProperty
	public int getStatus() {
		return status;
	}
}
