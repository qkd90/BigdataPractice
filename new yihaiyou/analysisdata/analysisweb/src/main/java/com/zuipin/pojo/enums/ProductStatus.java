package com.zuipin.pojo.enums;

public enum ProductStatus {
	下架(1), 上架(2), 禁用(3), 删除(4);
	private Integer	index;
	
	private ProductStatus(Integer index) {
		this.index = index;
	}
	
	public Integer getIndex() {
		return index;
	}
}
