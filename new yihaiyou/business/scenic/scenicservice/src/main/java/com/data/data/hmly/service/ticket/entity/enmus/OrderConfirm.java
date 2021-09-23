package com.data.data.hmly.service.ticket.entity.enmus;

/**
 * 
 * @author dy
 *
 */
public enum OrderConfirm {
	noconfirm("无需确认"), confirm("确认");

	private String description;

	OrderConfirm(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
