package com.data.data.hmly.service.line.entity.enums;

/**
 * 线路类型
 * @author caiys
 * @date 2015年10月16日 上午9:23:40
 */
public enum LineImageType {
	home("首选图片"), detail("详情图片");

	private String description;

	LineImageType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
