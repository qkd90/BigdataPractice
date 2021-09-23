package com.data.data.hmly.service.ctripflight.entity.enums;

public enum PriceType {

	NormalPrice("普通"),SingleTripPrice("提前预售特价"),CZSpecialPrice("南航特价");
	
	private String description;

	PriceType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
	
}
