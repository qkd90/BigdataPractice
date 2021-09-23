package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.ticket.entity.TicketPriceTypeExtend;

/**
 * Created by huangpeijie on 2016-11-14,0014.
 */
public class TicketPriceTypeExtendResponse {
    private String firstTitle;
    private String secondTitle;
    private String content;

    public TicketPriceTypeExtendResponse() {
    }

    public TicketPriceTypeExtendResponse(TicketPriceTypeExtend ticketPriceTypeExtend) {
        this.firstTitle = ticketPriceTypeExtend.getFirstTitle();
        this.secondTitle = ticketPriceTypeExtend.getSecondTitle();
        this.content = ticketPriceTypeExtend.getContent();
    }

    public String getFirstTitle() {
        return firstTitle;
    }

    public void setFirstTitle(String firstTitle) {
        this.firstTitle = firstTitle;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
