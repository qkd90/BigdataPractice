package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.order.entity.ShenzhouOrder;

/**
 * Created by huangpeijie on 2016-09-12,0012.
 */
public class ShenzhouOrderResponse {
    private Long id;
    private String shenzhouOrderId;
    private String orderNo;
    private Integer serviceId;
    private Integer carGroupId;
    private String status;
    private String statusDesc;
    private String paymentStatus;
    private String startName;
    private String endName;
    private String passengerMobile;
    private String passengerName;
    private Float totalPrice;
    private Float arrearsPrice;
    private Float distance;
    private Float duration;
    private String driverName;
    private String vehicleNo;
    private String virtualPhone4Purchaser;
    private String virtualPhone4Passenger;
    private String vehicleModel;
    private String driverScore;
    private String createTime;
    private String departureTime;
    private String dispatchedTime;
    private String finishedTime;
    private Float startPrice;
    private Float kilometrePrice;
    private Float timePrice;
    private Float countChange;
    private Float longDistancePrice;
    private Integer score;

    public ShenzhouOrderResponse() {
    }

    public ShenzhouOrderResponse(ShenzhouOrder shenzhouOrder) {
        this.id = shenzhouOrder.getId();
        this.shenzhouOrderId = shenzhouOrder.getShenzhouOrderId();
        this.orderNo = shenzhouOrder.getOrderNo();
        this.serviceId = shenzhouOrder.getServiceId();
        this.carGroupId = shenzhouOrder.getCarGroupId();
        this.status = shenzhouOrder.getStatus().toString();
        this.statusDesc = shenzhouOrder.getStatus().getDescription();
        this.paymentStatus = shenzhouOrder.getPaymentStatus().getDescription();
        this.startName = shenzhouOrder.getStartName();
        this.endName = shenzhouOrder.getEndName();
        this.passengerMobile = shenzhouOrder.getPassengerMobile();
        this.passengerName = shenzhouOrder.getPassengerName();
        this.totalPrice = shenzhouOrder.getTotalPrice();
        this.arrearsPrice = shenzhouOrder.getArrearsPrice();
        this.distance = shenzhouOrder.getDistance();
        this.duration = shenzhouOrder.getDuration();
        this.driverName = shenzhouOrder.getDriverName();
        this.vehicleNo = shenzhouOrder.getVehicleNo();
        this.virtualPhone4Purchaser = shenzhouOrder.getVirtualPhone4Purchaser();
        this.virtualPhone4Passenger = shenzhouOrder.getVirtualPhone4Passenger();
        this.vehicleModel = shenzhouOrder.getVehicleModel();
        this.driverScore = shenzhouOrder.getDriverScore();
        this.createTime = DateUtils.formatDate(shenzhouOrder.getCreateTime());
        this.departureTime = DateUtils.formatDate(shenzhouOrder.getDepartureTime());
        if (shenzhouOrder.getDispatchedTime() != null) {
            this.dispatchedTime = DateUtils.formatShortDate(shenzhouOrder.getDispatchedTime());
        }
        if (shenzhouOrder.getFinishedTime() != null) {
            this.finishedTime = DateUtils.format(shenzhouOrder.getFinishedTime(), "HH:mm:ss");
        }
        this.startPrice = shenzhouOrder.getStartPrice();
        this.kilometrePrice = shenzhouOrder.getKilometrePrice();
        this.timePrice = shenzhouOrder.getTimePrice();
        this.countChange = shenzhouOrder.getCountChange();
        this.longDistancePrice = shenzhouOrder.getLongDistancePrice();
        this.score = shenzhouOrder.getScore();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
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

    public String getDriverScore() {
        return driverScore;
    }

    public void setDriverScore(String driverScore) {
        this.driverScore = driverScore;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDispatchedTime() {
        return dispatchedTime;
    }

    public void setDispatchedTime(String dispatchedTime) {
        this.dispatchedTime = dispatchedTime;
    }

    public String getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(String finishedTime) {
        this.finishedTime = finishedTime;
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

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
