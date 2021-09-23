package com.data.data.hmly.action.lvxbang.response;

import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.response.TicketPriceAddInfo;

import java.util.List;

/**
 * Created by huangpeijie on 2016-04-12,0012.
 */
public class TicketPriceResponse {
    private Long id;
    private String name;
    private Long ticketId;
    private String formatBooking;
    private Float maketPrice;
    private Float discountPrice;
    private List<TicketPriceAddInfo> addInfoList;

    public TicketPriceResponse() {
    }

    public TicketPriceResponse(TicketPrice ticketPrice) {
        this.id = ticketPrice.getId();
        this.name = ticketPrice.getName();
        this.ticketId = ticketPrice.getTicket().getId();
        this.formatBooking = ticketPrice.getFormatBooking();
        this.maketPrice = ticketPrice.getMaketPrice();
        this.discountPrice = ticketPrice.getDiscountPrice();
        this.addInfoList = ticketPrice.getAddInfoList();
    }

    public TicketPriceResponse(TicketDateprice ticketDateprice) {
        this.id = ticketDateprice.getTicketPriceId().getId();
        this.name = ticketDateprice.getTicketPriceId().getName();
        this.ticketId = ticketDateprice.getTicketPriceId().getTicket().getId();
        this.formatBooking = ticketDateprice.getTicketPriceId().getFormatBooking();
        this.maketPrice = ticketDateprice.getMaketPrice();
        this.discountPrice = ticketDateprice.getPriPrice() + ticketDateprice.getRebate();
        this.addInfoList = ticketDateprice.getTicketPriceId().getAddInfoList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getFormatBooking() {
        return formatBooking;
    }

    public void setFormatBooking(String formatBooking) {
        this.formatBooking = formatBooking;
    }

    public Float getMaketPrice() {
        return maketPrice;
    }

    public void setMaketPrice(Float maketPrice) {
        this.maketPrice = maketPrice;
    }

    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public List<TicketPriceAddInfo> getAddInfoList() {
        return addInfoList;
    }

    public void setAddInfoList(List<TicketPriceAddInfo> addInfoList) {
        this.addInfoList = addInfoList;
    }
}
