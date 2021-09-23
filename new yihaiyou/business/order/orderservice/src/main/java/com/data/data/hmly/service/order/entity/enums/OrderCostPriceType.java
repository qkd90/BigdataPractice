package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by guoshijie on 2015/10/30.
 */
public enum OrderCostPriceType {
	ADULT("成人价"), CHILD("儿童价");

	private String name;

	OrderCostPriceType() {
	}

	OrderCostPriceType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
