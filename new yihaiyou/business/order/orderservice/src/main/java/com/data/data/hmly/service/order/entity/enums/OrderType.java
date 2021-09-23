package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by guoshijie on 2015/10/14.
 */
public enum OrderType {
    plan("行程"), ticket("门票"), train("火车票"), flight("机票"), hotel("酒店"), recharge("充值"), withdraw("提现"),
    line("线路"), scenic("门票"), recplan("游记"), restaurant("餐厅"), delicacy("美食"),
    ship("游轮"), cruiseship("邮轮"), ferry("轮渡"), sailboat("帆船"), yacht("游艇"), huanguyou("鹭岛游"), shenzhou("专车"), lvji("驴迹");
    private String description;

    OrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
