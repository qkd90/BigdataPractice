package com.data.data.hmly.service.line.entity.enums;

public enum ProductAttr {
	gentuan("跟团游"), zizhuzijia("自助自驾"), custommade("精品定制"), ziyou("自助游"), zijia("自驾游"), dulibaotuan("独立包团"), localplay("当地参团");
	String desc;

	private ProductAttr(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
