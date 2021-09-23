package com.data.data.hmly.service.ticket.entity.enmus;

/**
 * Created by zzl on 2016/11/30.
 */
public enum TicketPriceStatus {
    UP("上架"), DOWN("下架"), DEL("删除"),
    UP_CHECKING("上架中"), REFUSE("拒绝");

    private String description;

    TicketPriceStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
