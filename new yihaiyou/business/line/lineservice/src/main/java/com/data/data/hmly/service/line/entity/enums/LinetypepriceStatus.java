package com.data.data.hmly.service.line.entity.enums;

/**
 * 线路类型
 * @author caiys
 * @date 2015年10月16日 上午9:23:40
 */
public enum LinetypepriceStatus {
	enable("正常"), disable("异常");

	private String description;

	LinetypepriceStatus(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
