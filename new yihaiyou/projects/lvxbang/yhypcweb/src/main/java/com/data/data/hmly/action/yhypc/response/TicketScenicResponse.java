package com.data.data.hmly.action.yhypc.response;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by huangpeijie on 2016-10-08,0008.
 */
public class TicketScenicResponse {
    private Long id;
    private String scenicName;
    private String playDate;
    private List<TicketResponse> tickets = Lists.newArrayList();
    private TicketResponse selectedTicket;
    private List<SimpleTourist> tourists;
    private Float price = 0f;
    private List<ProValidCodeResponse> codes = Lists.newArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public List<TicketResponse> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketResponse> tickets) {
        this.tickets = tickets;
    }

    public TicketResponse getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(TicketResponse selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

    public List<SimpleTourist> getTourists() {
        return tourists;
    }

    public void setTourists(List<SimpleTourist> tourists) {
        this.tourists = tourists;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<ProValidCodeResponse> getCodes() {
        return codes;
    }

    public void setCodes(List<ProValidCodeResponse> codes) {
        this.codes = codes;
    }
}
