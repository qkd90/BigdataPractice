package com.data.data.hmly.service.ticket.entity.enmus;

/**
 * 
 * @author dy
 *
 */
public enum TicketType {
	scenic("景点门票"), shows("演出门票"), sailboat("帆船"), yacht("游艇"), huanguyou("鹭岛游"),lvji("驴迹");

	private String description;

	TicketType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
