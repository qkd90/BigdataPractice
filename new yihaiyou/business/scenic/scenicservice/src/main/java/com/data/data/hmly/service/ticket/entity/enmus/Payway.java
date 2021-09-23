package com.data.data.hmly.service.ticket.entity.enmus;

/**
 * 
 * @author dy
 *
 */
public enum Payway {
	scenicpay("景区支付"), offlinepay("线下支付"), allpay("全额支付"), nopay("不支付");
	

	private String description;

	Payway(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
