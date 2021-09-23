package com.data.data.hmly.service.ctripflight.entity.enums;

public enum OrderType {
	
	ADU("成人"),CHI("儿童"),BAB("婴儿");
	
	private String description;

	OrderType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
	

}
