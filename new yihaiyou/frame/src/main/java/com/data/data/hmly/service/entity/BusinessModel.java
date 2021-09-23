package com.data.data.hmly.service.entity;

public enum BusinessModel {
	zhishu("直属"), guakao("挂靠");
	private String desc;

	private BusinessModel(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
