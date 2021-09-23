package com.data.data.hmly.service.line.entity.enums;

/**
 * 线路类型
 * @author caiys
 * @date 2015年10月16日 上午9:23:40
 */
public enum LineType {
	city("厦门游"), around("周边游"), china("国内游"), foreign("出境游");

	private String description;

	LineType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
