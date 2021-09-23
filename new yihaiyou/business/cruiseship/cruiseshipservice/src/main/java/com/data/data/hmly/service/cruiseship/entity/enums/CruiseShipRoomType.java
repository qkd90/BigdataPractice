package com.data.data.hmly.service.cruiseship.entity.enums;

public enum CruiseShipRoomType {
inside("内舱房"), seascape("海景房"), balcony("阳台房"), suite("套房");

	private String description;

	CruiseShipRoomType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
