package com.data.data.hmly.service.entity;

public enum BusinessType {

	dijie("地接"), zhutuan("组团"), zhonghe("综合");
	private String desc;

	private BusinessType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
