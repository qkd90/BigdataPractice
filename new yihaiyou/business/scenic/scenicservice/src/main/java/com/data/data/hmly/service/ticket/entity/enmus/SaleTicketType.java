package com.data.data.hmly.service.ticket.entity.enmus;

/**
 * 
 * @author dy
 *
 */
public enum SaleTicketType {
	other("其他"), adult("成人"), child("儿童"), student("学生"), old("老人"), team("团体"), pageTicket("套票");

	private String description;

	SaleTicketType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
