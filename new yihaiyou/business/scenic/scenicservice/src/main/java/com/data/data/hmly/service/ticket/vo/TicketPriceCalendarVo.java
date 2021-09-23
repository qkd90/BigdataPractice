package com.data.data.hmly.service.ticket.vo;

/**
 * Created by zzl on 2016/12/7.
 */
public class TicketPriceCalendarVo {
    private String date;
    private Float priPrice;
    private Float price;
    private Integer inventory;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getPriPrice() {
        return priPrice;
    }

    public void setPriPrice(Float priPrice) {
        this.priPrice = priPrice;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
}
