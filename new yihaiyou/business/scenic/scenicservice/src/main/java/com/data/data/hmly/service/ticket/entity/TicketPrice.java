package com.data.data.hmly.service.ticket.entity;

import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.ticket.entity.enmus.TicketPriceStatus;
import com.data.data.hmly.service.ticket.response.TicketPriceAddInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "ticketprice")
public class TicketPrice extends com.framework.hibernate.util.Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;                                            // 主键ID
    private String name;                                        // 门票类型名称
    private String type;                                        // 票型
    private String getTicket;                                    // 取票方式
    private String orderKnow;                                    // 预定须知
    private Float commission;                                    // 佣金
    private Float discountPrice;                                // 销售价
    private Float maketPrice;                                   //市场价
    private Ticket ticket;                                        // 门票编号
    private Long originId;
    private Long userid;                                        // 创建人
    private Integer orderNum;                                   //订单数
    private Integer score;                                      //评分
    private TicketPriceStatus status;                                      // 状态
    private ShowStatus showStatus;
    private Date addTime;                                    // 创建时间
    private Date modifyTime;                                    // 更新时间
    private Float price;                                    //结算价
    private Long ctripTicketResourceId;
    private Long ctripResourceId;
    private Long ctripTicketId;
    private Integer advanceBookingDays;                     //提前预订天数
    private String advanceBookingTime;                      //提前预定时间
    private Float minDiscountPrice;                        // 最小价格（分销价）

    private Boolean isTodayValid;                       //今日是否可订
    private Boolean isConditionRefund;                   //条件退

    @Column(name = "auditBy")
    private Long auditBy; // 审核人,
    @Column(name = "auditTime")
    private Date auditTime; // 审核时间,
    @Column(name = "auditReason")
    private String auditReason; // 审核原因,
//    @Column(name = "tourPlaceType")
    @Column(name = "showOrder")
    private Integer showOrder;

    private String formatBooking;
    private List<TicketPriceAddInfo> addInfoList;
    private Float rebate;


    private List<TicketPriceTypeExtend> ticketPriceTypeExtends;


    // 页面字段
    private String formatType;
    private Long scenicId;
    private Long linedaysId;
    private String ticketName;
    private Date priceDate;
    private String telephone;
    private String contactName;
    private String ticketType;

    public TicketPrice() {

    }

    public TicketPrice(String type) {
        this.type = type;
    }

    public TicketPrice(Long id, String name, String type, String getTicket,
                       String orderKnow, Float commission, Ticket ticket, Long userid,
                       Date addTime) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.getTicket = getTicket;
        this.orderKnow = orderKnow;
        this.commission = commission;
        this.ticket = ticket;
        this.userid = userid;
        this.addTime = addTime;
    }

    public TicketPrice(Long id, Long scenicId, String ticketName, String name, Float minDiscountPrice) {
        this.id = id;
        this.ticketName = ticketName;
        this.scenicId = scenicId;
        this.minDiscountPrice = minDiscountPrice;
        this.name = name;
    }

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

    @Column(name = "origin_id")
    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
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

    @Column(name = "modifyTime")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Column(name = "discountPrice", length = 20)
    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Column(name = "isTodayValid")
    public Boolean getIsTodayValid() {
        return isTodayValid;
    }

    public void setIsTodayValid(Boolean isTodayValid) {
        this.isTodayValid = isTodayValid;
    }

    @Column(name = "isConditionRefund")
    public Boolean getIsConditionRefund() {
        return isConditionRefund;
    }

    public void setIsConditionRefund(Boolean isConditionRefund) {
        this.isConditionRefund = isConditionRefund;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public TicketPriceStatus getStatus() {
        return status;
    }

    public void setStatus(TicketPriceStatus status) {
        this.status = status;
    }

    @Column(name = "show_status")
    @Enumerated(EnumType.STRING)
    public ShowStatus getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(ShowStatus showStatus) {
        this.showStatus = showStatus;
    }

    @Column(name = "orderNum", length = 11)
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Column(name = "score", length = 11)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Float getMaketPrice() {
        return maketPrice;
    }

    public void setMaketPrice(Float maketPrice) {
        this.maketPrice = maketPrice;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Column(name = "ctripTicketResourceId")
    public Long getCtripTicketResourceId() {
        return ctripTicketResourceId;
    }

    public void setCtripTicketResourceId(Long ctripTicketResourceId) {
        this.ctripTicketResourceId = ctripTicketResourceId;
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

    @OneToMany(mappedBy = "ticketPrice")
    public List<TicketPriceTypeExtend> getTicketPriceTypeExtends() {
        return ticketPriceTypeExtends;
    }

    public void setTicketPriceTypeExtends(List<TicketPriceTypeExtend> ticketPriceTypeExtends) {
        this.ticketPriceTypeExtends = ticketPriceTypeExtends;
    }

    @Column(name = "minDiscountPrice")
    public Float getMinDiscountPrice() {
        return minDiscountPrice;
    }

    public void setMinDiscountPrice(Float minDiscountPrice) {
        this.minDiscountPrice = minDiscountPrice;
    }

    @Transient
    public String getFormatType() {
        if ("adult".equals(this.type)) {
            return "成人票";
        } else if ("student".equals(this.type)) {
            return "学生票";
        } else if ("child".equals(this.type)) {
            return "儿童票";
        } else if ("oldman".equals(this.type)) {
            return "老人票";
        } else if ("taopiao".equals(this.type)) {
            return "套票";
        } else if ("team".equals(this.type)) {
            return "团队票";
        } else {
            return "其他";
        }
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

    @Transient
    public Float getRebate() {
        return rebate;
    }

    public void setRebate(Float rebate) {
        this.rebate = rebate;
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

    @Transient
    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    @Transient
    public Long getLinedaysId() {
        return linedaysId;
    }

    public void setLinedaysId(Long linedaysId) {
        this.linedaysId = linedaysId;
    }

    @Transient
    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    @Transient
    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    public Long getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(Long auditBy) {
        this.auditBy = auditBy;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    @Transient
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Transient
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Transient
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }
}
