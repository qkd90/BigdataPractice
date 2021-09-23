package com.data.data.hmly.service.scenic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity(name = "scenicticketprice")
@Table(name = "ticketprice")
public class TicketPrice extends com.framework.hibernate.util.Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;                                            // 主键ID
    private String name;                                        // 门票类型名称
    private String type;                                        // 票型
    private String getTicket;                                    // 取票方式
    private String orderKnow;                                    // 预定须知
    private Float commission;                                    // 佣金
    private Float discountPrice;                                // 分销价
    private Float maketPrice;                                   //原价
    private Ticket ticket;                                        // 门票编号
    private Long userid;                                        // 创建人
    private Integer status;                                        // 状态
    private Date addTime;                                    // 创建时间
    private Double price; //分销价
    private Long ctripResourceId;
    private Long ctripTicketId;
    private Integer advanceBookingDays;                     //提前预订天数
    private String advanceBookingTime;                      //提前预定时间
    private Double minDiscountPrice;                        // 最小价格（分销价）

    private String formatType;
    private String formatBooking;
    private List<TicketPriceAddInfo> addInfoList;
    public TicketPrice() {

    }

    public TicketPrice(String type) {
        this.type = type;
    }

//    public TicketPrice() {
//            Long id, String name, String type, String getTicket,
//                       String orderKnow, Float commission, Ticket ticket, Long userid,
//                       Date addTime

//        super();
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.getTicket = getTicket;
//        this.orderKnow = orderKnow;
//        this.commission = commission;
//        this.ticket = ticket;
//        this.userid = userid;
//        this.addTime = addTime;
//    }

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", length = 20)
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "type", length = 20)
    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    @Column(name = "getTicket", length = 20)
    public String getGetTicket() {
        return getTicket;
    }


    public void setGetTicket(String getTicket) {
        this.getTicket = getTicket;
    }


    @Column(name = "orderKnow")
    public String getOrderKnow() {
        return orderKnow;
    }


    public void setOrderKnow(String orderKnow) {
        this.orderKnow = orderKnow;
    }

    @Column(name = "commission", length = 20)
    public Float getCommission() {
        return commission;
    }

    public void setCommission(Float commission) {
        this.commission = commission;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticketId", nullable = false)
    public Ticket getTicket() {
        return ticket;
    }


    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Column(name = "userid", length = 20)
    public Long getUserid() {
        return userid;
    }


    public void setUserid(Long userid) {
        this.userid = userid;
    }


    @Column(name = "addTime", length = 20)
    public Date getAddTime() {
        return addTime;
    }


    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }


    @Column(name = "discountPrice", length = 20)
    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Column(name = "status", length = 11)
    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getMaketPrice() {
        return maketPrice;
    }

    public void setMaketPrice(Float maketPrice) {
        this.maketPrice = maketPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "ctripResourceId")
    public Long getCtripResourceId() {
        return ctripResourceId;
    }

    public void setCtripResourceId(Long ctripResourceId) {
        this.ctripResourceId = ctripResourceId;
    }

    @Column(name = "ctripTicketId")
    public Long getCtripTicketId() {
        return ctripTicketId;
    }

    public void setCtripTicketId(Long ctripTicketId) {
        this.ctripTicketId = ctripTicketId;
    }

    @Column(name = "advanceBookingDays")
    public Integer getAdvanceBookingDays() {
        return advanceBookingDays;
    }

    public void setAdvanceBookingDays(Integer advanceBookingDays) {
        this.advanceBookingDays = advanceBookingDays;
    }

    @Column(name = "advanceBookingTime")
    public String getAdvanceBookingTime() {
        return advanceBookingTime;
    }

    public void setAdvanceBookingTime(String advanceBookingTime) {
        this.advanceBookingTime = advanceBookingTime;
    }

    @Column(name = "minDiscountPrice")
    public Double getMinDiscountPrice() {
        return minDiscountPrice;
    }

    public void setMinDiscountPrice(Double minDiscountPrice) {
        this.minDiscountPrice = minDiscountPrice;
    }

    @Transient
    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    @Transient
    public String getFormatBooking() {
        return formatBooking;
    }

    public void setFormatBooking(String formatBooking) {
        this.formatBooking = formatBooking;
    }

    @Transient
    public List<TicketPriceAddInfo> getAddInfoList() {
        return addInfoList;
    }

    public void setAddInfoList(List<TicketPriceAddInfo> addInfoList) {
        this.addInfoList = addInfoList;
    }

    public void formatType() {
        if ("adult".equals(this.type)) {
            this.formatType = "成人票";
        } else if ("student".equals(this.type)) {
            this.formatType = "学生票";
        } else if ("child".equals(this.type)) {
            this.formatType = "儿童票";
        } else if ("oldman".equals(this.type)) {
            this.formatType = "老人票";
        } else if ("taopiao".equals(this.type)) {
            this.formatType = "套票";
        } else if ("team".equals(this.type)) {
            this.formatType = "团队票";
        } else if ("other".equals(this.type)) {
            this.formatType = "其他";
        }

        if (this.advanceBookingDays == null) {
            return;
        }

        if (this.advanceBookingDays == 0) {
           this.formatBooking = "当天";
        } else {
            this.formatBooking = "提前" + this.advanceBookingDays + "天";
        }
        this.formatBooking += this.advanceBookingTime + "前";
    }
}
