package com.data.data.hmly.service.user.entity.enums;

public enum TicketReceiveType {
	
	INVITE("自取"), AGENT("代取");
	
	private String description;

	 TicketReceiveType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
	
}
