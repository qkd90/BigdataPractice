package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by guoshijie on 2015/10/14.
 */
public enum  OrderStatus {
	UNCONFIRMED("待确认"), WAIT("待支付"), PAYED("已支付"),
	SUCCESS("预订成功"), FAILED("预订失败"), PARTIAL_FAILED("部分失败"),
	PROCESSING("处理中"), PROCESSED("已处理"),
    REFUND("已退款"), CANCELED("已取消"), INVALID("无效订单"),
	CHECKIN("已入住"), CHECKOUT("已退房"),
	// 已废弃状态不要使用
	CANCELING("退订中"), CONFIRMED("已确认"), DELETED("已删除"), CLOSED("已关闭");

	private String description;

	OrderStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
