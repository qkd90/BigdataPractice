package com.data.data.hmly.service.ctripflight.entity.enums;

public enum InventoryType {
	
	FIX("定额"),FAV("见舱");
	private String description;

	InventoryType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
