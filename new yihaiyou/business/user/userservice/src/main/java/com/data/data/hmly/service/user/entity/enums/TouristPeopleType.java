package com.data.data.hmly.service.user.entity.enums;

public enum TouristPeopleType {
	
	ADULT("成人"), KID("小孩");
	
	private String description;

	TouristPeopleType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
	
}
