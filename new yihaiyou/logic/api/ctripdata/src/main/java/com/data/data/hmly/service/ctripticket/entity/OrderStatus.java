package com.data.data.hmly.service.ctripticket.entity;

public enum OrderStatus {
	PREPAY("预下单"), PAY("已支付"), CANCEL("已取消"), FAIL("创建失败");

	private String description;

	OrderStatus(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
