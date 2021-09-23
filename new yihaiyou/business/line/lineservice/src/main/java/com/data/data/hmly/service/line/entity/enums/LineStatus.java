package com.data.data.hmly.service.line.entity.enums;

/**
 * 线路类型
 * @author caiys
 * @date 2015年10月16日 上午9:23:40
 */
public enum LineStatus {
	show("上架"), hide("下架"), del("删除"), outday("过期"), checking("审核中");

	private String description;

	LineStatus(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
}
