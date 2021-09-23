package com.data.data.hmly.service.line.entity.enums;

/**
 * 发团地点类型
 * @author caiys
 * @date 2015年10月16日 上午9:23:40
 */
public enum TourPlaceType {
	start("出发地成团"), dest("目的地成团");

	private String description;

	TourPlaceType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
