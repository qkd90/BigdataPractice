package com.data.data.hmly.service.entity;

public enum SupplierType {

	zhuangxiang("专线"), other("其他"), zhonghe("综合"), dijie("地接"), chujing("出境"), ticket("票务"), hotel("酒店民宿"), sailboat("游艇帆船"), cruiseship("游轮旅游"), scenic("景点门票");

//	'zhuangxiang','zhonghe','dijie','chujing','cruiseship','sailboat','ticket','scenic','hotel'

	private String desc;

	private SupplierType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static SupplierType findByName(String supplierType) {
		// TODO Auto-generated method stub
		for (SupplierType type : values()) {
			if (type.name().equals(supplierType)) {
				return type;
			}
		}
		return null;
	}

}
