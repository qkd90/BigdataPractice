package com.data.data.hmly.service.wechat.entity.enums;

/**
 * Created by vacuity on 15/11/19.
 */
public enum MatchType {
	explicit("全匹配"),implicit("模糊匹配");
	private String description;

	MatchType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
	
}
