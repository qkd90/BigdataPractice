package com.data.data.hmly.service.order.entity.enums;

/**
 * Created by huangpeijie on 2016-09-08,0008.
 */
public enum ShenzhouOrderStatus {
    created("新建", "新建", 1), invalid("无效", "无效", 9), dispatched("已接单", "已派单", 2), arriving("已接单", "已出发", 3), arrived("已接单", "已到达", 4),
    canceled("已取消", "已取消", 9), serviceStarted("进行中", "已开始服务", 5), serviceFinished("进行中", "已结束服务", 6),
    feeSubmitted("进行中", "已提交费用", 7), paid("待评价", "已支付待评价", 8), completed("已完成", "已完成", 9), deleted("已删除", "", 10);

    private String description;

    private String shenzhouDesc;

    private Integer level;

    ShenzhouOrderStatus(String description, String shenzhouDesc, Integer level) {
        this.description = description;
        this.shenzhouDesc = shenzhouDesc;
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public String getShenzhouDesc() {
        return shenzhouDesc;
    }

    public Integer getLevel() {
        return level;
    }
}
