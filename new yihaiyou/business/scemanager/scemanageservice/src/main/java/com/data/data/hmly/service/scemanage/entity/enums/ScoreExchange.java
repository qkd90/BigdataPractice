package com.data.data.hmly.service.scemanage.entity.enums;

/**
 * 线路类型
 * @author caiys
 * @date 2015年10月16日 上午9:23:40
 */
public enum ScoreExchange {
	participation("参加积分兑换"), no("不参加");

	private String description;

	ScoreExchange(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
