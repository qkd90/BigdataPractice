package com.data.data.hmly.action.yhypc.response;

import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;

/**
 * Created by huangpeijie on 2016-04-22,0022.
 */
public class TicketResponse {
    private Long ticketId;
    private String ticketName;
    private Long priceId;
    private Float price;
    private Integer num;

    public TicketResponse() {
    }

    public TicketResponse(TicketPrice ticketPrice) {
        this.ticketId = ticketPrice.getTicket().getId();
        this.ticketName = ticketPrice.getName();
        this.priceId = ticketPrice.getId();
        this.price = ticketPrice.getDiscountPrice();
    }

    public TicketResponse(TicketDateprice ticketDateprice) {
        TicketPrice ticketPrice = ticketDateprice.getTicketPriceId();
        this.ticketId = ticketPrice.getTicket().getId();
        this.ticketName = ticketPrice.getName();
        this.priceId = ticketPrice.getId();
        this.price = ticketDateprice.getPriPrice();
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
