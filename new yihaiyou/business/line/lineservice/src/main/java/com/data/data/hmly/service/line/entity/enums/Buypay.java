package com.data.data.hmly.service.line.entity.enums;

/**
 * 购物与自费
 * @author caiys
 * @date 2015年10月16日 上午9:23:40
 */
public enum Buypay {
	noBuyNoPay("无购物无自费"), noBuyPay("无购物有自费"), buyNoPay("有购物无自费"), buyPay("有购物有自费");

	private String description;

	Buypay(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
