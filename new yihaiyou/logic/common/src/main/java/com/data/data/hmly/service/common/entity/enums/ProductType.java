package com.data.data.hmly.service.common.entity.enums;

public enum ProductType {
	scenic("门票"), restaurant("餐厅"), hotel("酒店"), line("线路"), train("火车票"),
    flight("机票"), delicacy("美食"), recplan("游记"), recharge("充值"), plan("行程"),
	impression("印象"), ship("游轮"), withdraw("提现"), insurance("保险"), bus("汽车"),
    cruiseship("邮轮"), sailboat("游艇帆船"), sailboat_typeprice("游艇帆船价格类型"), yacht("游艇船票"), huanguyou("鹭岛游"), ferry("轮渡");

	private String desc;

	ProductType(String desc) {
		this.desc = desc;
	}

	ProductType() {

	}

	public String getDescription() {
		return this.desc;
	}

}
