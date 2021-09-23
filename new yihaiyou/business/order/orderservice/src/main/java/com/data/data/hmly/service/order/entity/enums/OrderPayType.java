package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by guoshijie on 2015/10/14.
 */
public enum OrderPayType {
	ONLINE("在线支付"), OFFLINE("离线支付"), ZHAOH("招行支付"), WECHAT("微信支付"), ULINEWECHAT("优畅微信支付"), ULINEALIPAY("优畅支付宝支付");

	private String description;

	OrderPayType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
