package com.data.data.hmly.service.scenic.entity;

/**
 * 
 * @author dy
 *
 */
public enum TicketType {
	scenic("景点门票"), shows("演出门票"), sailboat("帆船"), yacht("游艇"), huanguyou("鹭岛游");

	private String description;

	TicketType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
