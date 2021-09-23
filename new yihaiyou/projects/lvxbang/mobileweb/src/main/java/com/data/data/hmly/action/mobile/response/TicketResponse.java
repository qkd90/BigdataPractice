package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.action.mobile.enums.TicketStatus;
import com.data.data.hmly.service.ticket.entity.TicketPrice;

/**
 * Created by huangpeijie on 2016-04-22,0022.
 */
public class TicketResponse {
    private String scenicName;
    private Long ticketId;
    private String ticketName;
    private Long priceId;
    private Float price;
    private String playDate;
    private TicketStatus status;
    private String seatType;

    public TicketResponse() {
    }

    public TicketResponse(TicketPrice ticketPrice) {
        this.scenicName = ticketPrice.getTicket().getScenicInfo().getName();
        this.ticketId = ticketPrice.getTicket().getId();
        this.ticketName = ticketPrice.getName();
        this.priceId = ticketPrice.getId();
        this.price = ticketPrice.getDiscountPrice();
        ticketPrice.formatType();
        this.seatType = ticketPrice.getFormatType();
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
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

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
}
