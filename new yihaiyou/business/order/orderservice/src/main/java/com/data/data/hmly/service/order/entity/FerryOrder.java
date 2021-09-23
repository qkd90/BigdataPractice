package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderWay;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-11-21,0021.
 */
@Entity
@Table(name = "ferryOrder")
@JsonIgnoreProperties
public class FerryOrder extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Long orderId;
    private String ferryNumber;
    private String orderNumber;
    private String dailyFlightGid;
    private String flightNumber;
    private String flightLineName;
    private String departTime;
    private Integer seat;
    private Float amount;
    private Float returnAmount;
    private Float poundageAmount;
    private String poundageDescribe;
    private Long refundBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date waitTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    @JoinColumn(name = "user")
    private Member user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ferryOrder")
    private List<FerryOrderItem> ferryOrderItemList;

    @Column(name = "billType")
    @Enumerated(EnumType.STRING)
    private OrderBillType orderBillType;
    @Column(name = "billDays")
    private Integer orderBillDays;
    @Column(name = "billPrice")
    private Float orderBillPrice;
    @Column(name = "billDate")
    private Date orderBillDate;
    @Column(name = "billStatus")
    private Integer orderBillStatus;
    @Column(name = "billSummaryId")
    private Long billSummaryId;
    @Column(name = "refundDate")
    private Date refundDate;
    @Column(name = "refundBillSummaryId")
    private Long refundBillSummaryId;

    @Column(name = "deleteFlag")
    private Boolean deleteFlag = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "orderWay")
    private OrderWay orderWay;
    @Enumerated(EnumType.STRING)
    @Column(name = "payType")
    private OrderPayType payType;
    @Column(name = "wechatCode")
    private String wechatCode;
    @Column(name = "alipayCode")
    private String alipayCode;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cancelHandleTime", length = 19)
    private Date cancelHandleTime;  // 退单处理时间

    @Transient
    private String keyword;

    @Transient
    private String createTimeStart;
    @Transient
    private String createTimeEnd;

    @Transient
    private String departTimeStart;
    @Transient
    private String departTimeEnd;

    @Transient
    private Integer waitSeconds;

    public FerryOrder() {
    }

    public FerryOrder(String ferryNumber, String orderNumber, String flightNumber, String flightLineName, String departTime, Float amount, Float orderBillPrice, Float returnAmount, Date refundDate) {
        this.ferryNumber = ferryNumber;
        this.orderNumber = orderNumber;
        this.flightNumber = flightNumber;
        this.flightLineName = flightLineName;
        this.departTime = departTime;
        this.amount = amount;
        this.orderBillPrice = orderBillPrice;
        this.returnAmount = returnAmount;
        this.refundDate = refundDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getFerryNumber() {
        return ferryNumber;
    }

    public void setFerryNumber(String ferryNumber) {
        this.ferryNumber = ferryNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDailyFlightGid() {
        return dailyFlightGid;
    }

    public void setDailyFlightGid(String dailyFlightGid) {
        this.dailyFlightGid = dailyFlightGid;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    public List<FerryOrderItem> getFerryOrderItemList() {
        return ferryOrderItemList;
    }

    public void setFerryOrderItemList(List<FerryOrderItem> ferryOrderItemList) {
        this.ferryOrderItemList = ferryOrderItemList;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getFlightLineName() {
        return flightLineName;
    }

    public void setFlightLineName(String flightLineName) {
        this.flightLineName = flightLineName;
    }

    public Date getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Date waitTime) {
        this.waitTime = waitTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getDepartTimeStart() {
        return departTimeStart;
    }

    public void setDepartTimeStart(String departTimeStart) {
        this.departTimeStart = departTimeStart;
    }

    public String getDepartTimeEnd() {
        return departTimeEnd;
    }

    public void setDepartTimeEnd(String departTimeEnd) {
        this.departTimeEnd = departTimeEnd;
    }

    public Float getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Float returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Float getPoundageAmount() {
        return poundageAmount;
    }

    public void setPoundageAmount(Float poundageAmount) {
        this.poundageAmount = poundageAmount;
    }

    public String getPoundageDescribe() {
        return poundageDescribe;
    }

    public void setPoundageDescribe(String poundageDescribe) {
        this.poundageDescribe = poundageDescribe;
    }

    public OrderBillType getOrderBillType() {
        return orderBillType;
    }

    public void setOrderBillType(OrderBillType orderBillType) {
        this.orderBillType = orderBillType;
    }

    public Integer getOrderBillDays() {
        return orderBillDays;
    }

    public void setOrderBillDays(Integer orderBillDays) {
        this.orderBillDays = orderBillDays;
    }

    public Float getOrderBillPrice() {
        return orderBillPrice;
    }

    public void setOrderBillPrice(Float orderBillPrice) {
        this.orderBillPrice = orderBillPrice;
    }

    public Date getOrderBillDate() {
        return orderBillDate;
    }

    public void setOrderBillDate(Date orderBillDate) {
        this.orderBillDate = orderBillDate;
    }

    public Integer getOrderBillStatus() {
        return orderBillStatus;
    }

    public void setOrderBillStatus(Integer orderBillStatus) {
        this.orderBillStatus = orderBillStatus;
    }

    public Long getBillSummaryId() {
        return billSummaryId;
    }

    public void setBillSummaryId(Long billSummaryId) {
        this.billSummaryId = billSummaryId;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public Long getRefundBillSummaryId() {
        return refundBillSummaryId;
    }

    public void setRefundBillSummaryId(Long refundBillSummaryId) {
        this.refundBillSummaryId = refundBillSummaryId;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getRefundBy() {
        return refundBy;
    }

    public void setRefundBy(Long refundBy) {
        this.refundBy = refundBy;
    }

    public OrderWay getOrderWay() {
        return orderWay;
    }

    public void setOrderWay(OrderWay orderWay) {
        this.orderWay = orderWay;
    }

    public OrderPayType getPayType() {
        return payType;
    }

    public void setPayType(OrderPayType payType) {
        this.payType = payType;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    public String getAlipayCode() {
        return alipayCode;
    }

    public void setAlipayCode(String alipayCode) {
        this.alipayCode = alipayCode;
    }

    public Date getCancelHandleTime() {
        return cancelHandleTime;
    }

    public void setCancelHandleTime(Date cancelHandleTime) {
        this.cancelHandleTime = cancelHandleTime;
    }

    public Integer getWaitSeconds() {
        return waitSeconds;
    }

    public void setWaitSeconds(Integer waitSeconds) {
        this.waitSeconds = waitSeconds;
    }
}
