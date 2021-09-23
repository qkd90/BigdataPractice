package com.data.data.hmly.service.ctripcommon.enums;

/**
 * 线路类型
 * @author caiys
 * @date 2015年10月16日 上午9:23:40
 */
public enum RowStatus {
	INSERT("插入"), UPDATE("更新"), DELETE("删除");

	private String description;

	RowStatus(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
