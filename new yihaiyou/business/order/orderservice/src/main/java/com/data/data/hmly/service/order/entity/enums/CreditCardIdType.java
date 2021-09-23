package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by zzl on 2016/4/27.
 */
public enum CreditCardIdType {

    IdentityCard("身份证"), Passport("护照"), Other("其他");

    private String des;

    CreditCardIdType(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }
}
