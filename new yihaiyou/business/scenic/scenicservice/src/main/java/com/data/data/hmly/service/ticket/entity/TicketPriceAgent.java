package com.data.data.hmly.service.ticket.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "ticketprice_agent")
public class TicketPriceAgent extends com.framework.hibernate.util.Entity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;                                            // 主键ID

    @Column(name = "name", length = 20)
    private String name;                                        // 门票类型名称

    @Column(name = "type", length = 20)
    private String type;                                        // 票型

    @Column(name = "getTicket", length = 20)
    private String getTicket;                                    // 取票方式

    @Column(name = "orderKnow")
    private String orderKnow;                                    // 预定须知

    @Column(name = "rebate", length = 20)
    private Float rebate;                                        // 佣金

    @Column(name = "discountPrice", length = 20)
    private Float discountPrice;                                // 分销价

    @Column(name = "maketPrice")
    private Float maketPrice;                                   //原价

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;                                        // 门票编号

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_ticket_id", nullable = false)
    private Ticket topTicket;                                        // 门票编号

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_tickettype_id", nullable = false)
    private TicketPrice topTicketPrice;                              // 父级票种编号

    @Column(name = "userid", length = 20)
    private Long userid;                                        // 创建人

    @Column(name = "status", length = 11)
    private Integer status;                                        // 状态

    @Column(name = "addTime", length = 20)
    private Date addTime;                                    // 创建时间

    @Column(name = "price")
    private Double price;                                   //分销价

    @Column(name = "ctripResourceId")
    private Long ctripResourceId;

    @Column(name = "ctripTicketId")
    private Long ctripTicketId;

    @Column(name = "advanceBookingDays")
    private Integer advanceBookingDays;                     //提前预订天数

    @Column(name = "advanceBookingTime")
    private String advanceBookingTime;                      //提前预定时间



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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGetTicket() {
        return getTicket;
    }

    public void setGetTicket(String getTicket) {
        this.getTicket = getTicket;
    }

    public String getOrderKnow() {
        return orderKnow;
    }

    public void setOrderKnow(String orderKnow) {
        this.orderKnow = orderKnow;
    }

    public Float getRebate() {
        return rebate;
    }

    public void setRebate(Float rebate) {
        this.rebate = rebate;
    }

    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Float getMaketPrice() {
        return maketPrice;
    }

    public void setMaketPrice(Float maketPrice) {
        this.maketPrice = maketPrice;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTopTicket() {
        return topTicket;
    }

    public void setTopTicket(Ticket topTicket) {
        this.topTicket = topTicket;
    }

    public TicketPrice getTopTicketPrice() {
        return topTicketPrice;
    }

    public void setTopTicketPrice(TicketPrice topTicketPrice) {
        this.topTicketPrice = topTicketPrice;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCtripResourceId() {
        return ctripResourceId;
    }

    public void setCtripResourceId(Long ctripResourceId) {
        this.ctripResourceId = ctripResourceId;
    }

    public Long getCtripTicketId() {
        return ctripTicketId;
    }

    public void setCtripTicketId(Long ctripTicketId) {
        this.ctripTicketId = ctripTicketId;
    }

    public Integer getAdvanceBookingDays() {
        return advanceBookingDays;
    }

    public void setAdvanceBookingDays(Integer advanceBookingDays) {
        this.advanceBookingDays = advanceBookingDays;
    }

    public String getAdvanceBookingTime() {
        return advanceBookingTime;
    }

    public void setAdvanceBookingTime(String advanceBookingTime) {
        this.advanceBookingTime = advanceBookingTime;
    }
}
