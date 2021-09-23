package com.data.data.hmly.service.nctripticket.entity;

import com.data.data.hmly.service.ctripcommon.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "nctrip_order_form_info")
public class CtripOrderFormInfo extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 20)
    private Long        id;		          // 标识
    @Column(name = "scenicSpotId", length = 20)
    private Long scenicSpotId;	          // （必填）景点ID
    @Column(name = "payMode", length = 16)
    private String payMode;                 // （必填）支付类型 O-现付,P-预付
    @Column(name = "thirdPayType", length = 11)
    private Integer thirdPayType;	          // （必填）支付方式 0-押金方式；1：支付宝代扣
    @Column(name = "peopleNumber", length = 11)
    private Integer peopleNumber;	          // （必填）人数
    @Column(name = "amount", length = 20, precision = 2)
    private Float amount;                  // （必填）订单支付金额
    @Column(name = "serverFrom", length = 256)
    private String serverFrom;              // （必填）订单来源渠道，建议填渠道名字
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;
    @Column(name = "orderStatus")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "uid", length = 64)
    private String uid;     // 用户ID
    @Column(name = "ctripOrderId", length = 20)
    private Long ctripOrderId;     // 携程回写订单ID
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cancelHandleTime", length = 19)
    private Date cancelHandleTime;  // 退单处理时间
    @Column(name = "distributorOrderId")
    private String distributorOrderId;     // （必填）分销商订单ID
    @Transient
    private List<CtripOrderFormResourceInfo> resourceInfoList;	  // （必填）资源列表

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("Distributor_OrderID")
    public String getDistributorOrderId() {
        return distributorOrderId;
    }

    public void setDistributorOrderId(String distributorOrderId) {
        this.distributorOrderId = distributorOrderId;
    }

    @JsonProperty("ScenicSpotID")
    public Long getScenicSpotId() {
        return scenicSpotId;
    }

    public void setScenicSpotId(Long scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    @JsonProperty("PayMode")
    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    @JsonProperty("ThirdPayType")
    public Integer getThirdPayType() {
        return thirdPayType;
    }

    public void setThirdPayType(Integer thirdPayType) {
        this.thirdPayType = thirdPayType;
    }

    @JsonProperty("PeopleNumber")
    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    @JsonProperty("Amount")
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @JsonProperty("ServerFrom")
    public String getServerFrom() {
        return serverFrom;
    }

    public void setServerFrom(String serverFrom) {
        this.serverFrom = serverFrom;
    }

//    @JsonProperty("ResourceInfoList")
    public List<CtripOrderFormResourceInfo> getResourceInfoList() {
        return resourceInfoList;
    }

    public void setResourceInfoList(List<CtripOrderFormResourceInfo> resourceInfoList) {
        this.resourceInfoList = resourceInfoList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getCtripOrderId() {
        return ctripOrderId;
    }

    public void setCtripOrderId(Long ctripOrderId) {
        this.ctripOrderId = ctripOrderId;
    }

    public Date getCancelHandleTime() {
        return cancelHandleTime;
    }

    public void setCancelHandleTime(Date cancelHandleTime) {
        this.cancelHandleTime = cancelHandleTime;
    }
}
