package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by zzl on 2016/2/17.
 */
public enum  OrderDetailStatus {
    UNCONFIRMED("待确认"), WAITING("待支付"), PAYED("已支付"),
    SUCCESS("预订成功"), FAILED("预订失败"),
    CANCELED("已取消"), REFUNDED("已退款"),
    CHECKIN("已入住"), CHECKOUT("已退房"), INVALID("无效订单"),
    // 已废弃状态不要使用
    CONFIRMED("已确认"), CANCELING("退订中"), REFUSE("拒绝"),
    PARTIAL_FAILED("部分失败"), RETRY("重试"), BOOKING("预订中"), CLOSED("已关闭");

    private String description;

    OrderDetailStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
