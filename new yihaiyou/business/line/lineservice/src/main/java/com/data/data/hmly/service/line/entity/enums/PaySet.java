package com.data.data.hmly.service.line.entity.enums;

/**
 * 支付设置
 * @author caiys
 * @date 2015年10月16日 上午9:23:40
 */
public enum PaySet {
	close("关闭支付"), earnest("需订金预订"), allpay("需全额支付");

	private String description;

	PaySet(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
