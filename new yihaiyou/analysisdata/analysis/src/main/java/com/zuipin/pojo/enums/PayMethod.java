package com.zuipin.pojo.enums;

public enum PayMethod {
	现金(1), //
	月结(2), //
	// 月结15天(3), //
	// 月结30天(4), //
	// 月结60天(5), //
	// 月结90天(6), //
	赊账(7), //
	刷卡付款(8), //
	货到付款(9), //
	在线支付(10), //
	转账(11), //
	银行代扣(12), //
	未知方式(13);
	private Integer	index;
	
	private PayMethod(Integer index) {
		this.index = index;
	}
	
	public Integer getIndex() {
		return index;
	}
}
