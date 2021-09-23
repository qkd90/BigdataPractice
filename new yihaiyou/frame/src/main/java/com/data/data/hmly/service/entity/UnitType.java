package com.data.data.hmly.service.entity;

public enum UnitType {
	site("站点"), company("公司"), department("部门");

	private String description;

	UnitType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
