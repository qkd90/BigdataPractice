package com.zuipin.pojo.enums;

public enum ImportStatus {
	
	已处理(0), 未处理(1);
	private Integer	index;
	
	private ImportStatus(Integer index) {
		this.index = index;
	}
	
	public Integer getIndex() {
		return index;
	}
	
	public void setIndex(Integer index) {
		this.index = index;
	}
	
}
