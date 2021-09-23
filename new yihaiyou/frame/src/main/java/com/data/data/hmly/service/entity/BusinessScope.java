package com.data.data.hmly.service.entity;

public enum BusinessScope {
	inlang("国内"), chujing("出境"), intenal("国际社"), other("其它");
	private String desc;

	private BusinessScope(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
