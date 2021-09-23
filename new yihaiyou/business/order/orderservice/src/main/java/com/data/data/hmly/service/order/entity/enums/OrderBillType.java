package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by zzl on 2016/10/25.
 */
public enum OrderBillType {

    D0("实时结算(含节假日)"), D1("隔天结算(含节假日)"), DN("隔N天结算(含节假日)"),
    T0("实时结算(不含假节日)"), T1("隔天结算(不含节假日)"), TN("隔N天结算(不含节假日)"),
    week("周结"), month("月结");

    private String description;

    OrderBillType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
