package com.data.data.hmly.service.ctripcommon.enums;

/**
 * 携程订单状态 0:已提交；1:确认中；2:已确认；3:待付款；4:已付款；5:成交 (部分退)；6:退订；7:成交；8:取消；9:取消中；10:退订中
 */
public enum OrderStatus {
    CREATE("创建订单"), SUCCESS("下单成功"), CANCELING("退订中"), CANCELED("退订"), FAIL("创建失败"),
    OTHER("未知状态");

    private String description;

    OrderStatus(String s) {
        this.description = s;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
