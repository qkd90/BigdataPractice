package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by guoshijie on 2015/10/14.
 */
public enum OrderReceiveType {
	MSG("短信"), EXPRESSWAY("快递");

	private String description;

	OrderReceiveType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
