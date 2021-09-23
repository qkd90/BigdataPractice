package com.data.data.hmly.service.line.entity.enums;

/**
 * 线路组合类型
 * @author caiys
 * @date 2015年10月16日 上午9:23:40
 */
public enum CombineType {
	single("单一型"), combine("组合型");

	private String description;

	CombineType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
