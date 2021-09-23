package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.entity.enums.ShenzhouOrderPaymentStatus;
import com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by huangpeijie on 2016-09-08,0008.
 */
@Entity
@Table(name = "shenzhouorder")
@JsonIgnoreProperties
public class ShenzhouOrder extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "shenzhouOrderId")
    private String shenzhouOrderId;
    @Column(name = "orderNo")
    private String orderNo;
    @Column(name = "serviceId")
    private Integer serviceId;
    @Column(name = "carGroupId")
    private Integer carGroupId;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ShenzhouOrderStatus status;
    @Column(name = "paymentStatus")
    @Enumerated(EnumType.STRING)
    private ShenzhouOrderPaymentStatus paymentStatus;
    @Column(name = "startLat")
    private Double startLat;
    @Column(name = "startLng")
    private Double startLng;
    @Column(name = "startName")
    private String startName;
    @Column(name = "endLat")
    private Double endLat;
    @Column(name = "endLng")
    private Double endLng;
    @Column(name = "endName")
    private String endName;
    @Column(name = "passengerMobile")
    private String passengerMobile;
    @Column(name = "passengerName")
    private String passengerName;
    @Column(name = "totalPrice")
    private Float totalPrice;
    @Column(name = "arrearsPrice")
    private Float arrearsPrice;
    @Column(name = "distance")
    private Float distance;
    @Column(name = "duration")
    private Float duration;
    @Column(name = "driverName")
    private String driverName;
    @Column(name = "vehicleNo")
    private String vehicleNo;
    @Column(name = "virtualPhone4Purchaser")
    private String virtualPhone4Purchaser;
    @Column(name = "virtualPhone4Passenger")
    private String virtualPhone4Passenger;
    @Column(name = "vehicleModel")
    private String vehicleModel;
    @Column(name = "driverScore")
    private String driverScore;
    @Column(name = "createTime")
    private Date createTime;
    @Column(name = "departureTime")
    private Date departureTime;
    @Column(name = "dispatchedTime")
    private Date dispatchedTime;
    @Column(name = "finishedTime")
    private Date finishedTime;
    @Column(name = "startPrice")
    private Float startPrice = 0f;
    @Column(name = "kilometrePrice")
    private Float kilometrePrice = 0f;
    @Column(name = "timePrice")
    private Float timePrice = 0f;
    @Column(name = "countChange")
    private Float countChange = 0f;
    @Column(name = "longDistancePrice")
    private Float longDistancePrice = 0f;
    @Column(name = "cancelReson")
    private String cancelReson;
    @Column(name = "cancelCost")
    private Float cancelCost = 0f;
    @Column(name = "score")
    private Integer score;
    @Column(name = "frozenPrice")
    private Float frozenPrice;
    @ManyToOne
    @JoinColumn(name = "userid", unique = true, nullable = false, updatable = false)
    private Member user;
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

    @Column(name = "deleteFlag")
    private Boolean deleteFlag = false;

    @Transient
    private Date startTime;

    @Transient
    private Date endTime;

    public ShenzhouOrder() {
    }

    public ShenzhouOrder(String shenzhouOrderId, String orderNo, String passengerMobile, String passengerName, Float totalPrice, Float orderBillPrice) {
        this.shenzhouOrderId = shenzhouOrderId;
        this.orderNo = orderNo;
        this.passengerMobile = passengerMobile;
        this.passengerName = passengerName;
        this.totalPrice = totalPrice;
        this.orderBillPrice = orderBillPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShenzhouOrderId() {
        return shenzhouOrderId;
    }

    public void setShenzhouOrderId(String shenzhouOrderId) {
        this.shenzhouOrderId = shenzhouOrderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getCarGroupId() {
        return carGroupId;
    }

    public void setCarGroupId(Integer carGroupId) {
        this.carGroupId = carGroupId;
    }

    public ShenzhouOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ShenzhouOrderStatus status) {
        this.status = status;
    }

    public ShenzhouOrderPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(ShenzhouOrderPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public String getPassengerMobile() {
        return passengerMobile;
    }

    public void setPassengerMobile(String passengerMobile) {
        this.passengerMobile = passengerMobile;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Float getArrearsPrice() {
        return arrearsPrice;
    }

    public void setArrearsPrice(Float arrearsPrice) {
        this.arrearsPrice = arrearsPrice;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVirtualPhone4Purchaser() {
        return virtualPhone4Purchaser;
    }

    public void setVirtualPhone4Purchaser(String virtualPhone4Purchaser) {
        this.virtualPhone4Purchaser = virtualPhone4Purchaser;
    }

    public String getVirtualPhone4Passenger() {
        return virtualPhone4Passenger;
    }

    public void setVirtualPhone4Passenger(String virtualPhone4Passenger) {
        this.virtualPhone4Passenger = virtualPhone4Passenger;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getDispatchedTime() {
        return dispatchedTime;
    }

    public void setDispatchedTime(Date dispatchedTime) {
        this.dispatchedTime = dispatchedTime;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    public String getDriverScore() {
        return driverScore;
    }

    public void setDriverScore(String driverScore) {
        this.driverScore = driverScore;
    }

    public Float getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Float startPrice) {
        this.startPrice = startPrice;
    }

    public Float getKilometrePrice() {
        return kilometrePrice;
    }

    public void setKilometrePrice(Float kilometrePrice) {
        this.kilometrePrice = kilometrePrice;
    }

    public Float getTimePrice() {
        return timePrice;
    }

    public void setTimePrice(Float timePrice) {
        this.timePrice = timePrice;
    }

    public Float getCountChange() {
        return countChange;
    }

    public void setCountChange(Float countChange) {
        this.countChange = countChange;
    }

    public Float getLongDistancePrice() {
        return longDistancePrice;
    }

    public void setLongDistancePrice(Float longDistancePrice) {
        this.longDistancePrice = longDistancePrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCancelReson() {
        return cancelReson;
    }

    public void setCancelReson(String cancelReson) {
        this.cancelReson = cancelReson;
    }

    public Double getStartLat() {
        return startLat;
    }

    public void setStartLat(Double startLat) {
        this.startLat = startLat;
    }

    public Double getStartLng() {
        return startLng;
    }

    public void setStartLng(Double startLng) {
        this.startLng = startLng;
    }

    public Double getEndLat() {
        return endLat;
    }

    public void setEndLat(Double endLat) {
        this.endLat = endLat;
    }

    public Double getEndLng() {
        return endLng;
    }

    public void setEndLng(Double endLng) {
        this.endLng = endLng;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Float getFrozenPrice() {
        return frozenPrice;
    }

    public void setFrozenPrice(Float frozenPrice) {
        this.frozenPrice = frozenPrice;
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

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Float getCancelCost() {
        return cancelCost;
    }

    public void setCancelCost(Float cancelCost) {
        this.cancelCost = cancelCost;
    }
}
