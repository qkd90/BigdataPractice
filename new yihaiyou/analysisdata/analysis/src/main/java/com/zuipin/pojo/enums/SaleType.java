package com.zuipin.pojo.enums;

public enum SaleType {
	销售(1), 配套(2);
	private Integer	index;
	
	private SaleType(Integer index) {
		this.index = index;
	}
	
	public Integer getIndex() {
		return index;
	}
}
