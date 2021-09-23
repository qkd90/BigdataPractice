package com.data.data.hmly.service.lxbcommon.entity;

import com.data.data.hmly.service.lxbcommon.entity.enums.CouponDiscountType;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponReceiveLimitType;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponStatus;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponUseConditionType;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponValidType;

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

/**
 * Created by zzl on 2016/5/5.
 */
@Entity
@Table(name = "lxb_coupon")
public class Coupon extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "face_value")
    private Float faceValue;

    @Column(name = "coupon_discount_type")
    @Enumerated(EnumType.STRING)
    private CouponDiscountType couponDiscountType;

    @Column(name = "circulation")
    private Integer circulation;

    @Column(name = "coupon_use_condition_type")
    @Enumerated(EnumType.STRING)
    private CouponUseConditionType couponUseConditionType;

    @Column(name = "use_condition")
    private Float useCondition;

    @Column(name = "valid_days")
    private Integer validDays;

    @Column(name = "valid_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validStart;

    @Column(name = "valid_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date validEnd;

    @Column(name = "coupon_valid_type")
    @Enumerated(EnumType.STRING)
    private CouponValidType couponValidType;

    @Column(name = "limit_product_types")
    private String limitProductTypes;

    @Column(name = "limit_target_ids")
    private String limitTargetIds;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "limit_info")
    private String limitInfo;

    @Column(name = "coupon_receive_limit_type")
    @Enumerated(EnumType.STRING)
    private CouponReceiveLimitType couponReceiveLimitType;

    @Column(name = "receive_limit")
    private Integer receiveLimit;

    @Column(name = "available_num")
    private Integer availableNum;

    @Column(name = "max_discount")
    private Float maxDiscount;

    @Column(name = "coupon_code", updatable = false, nullable = false)
    private String couponCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


    @Transient
    private List<CouponStatus> neededStatuses;
    @Transient
    private Integer receivedNum;
    @Transient
    private Integer receivedPersonNum;


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

    public Float getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Float faceValue) {
        this.faceValue = faceValue;
    }

    public CouponDiscountType getCouponDiscountType() {
        return couponDiscountType;
    }

    public void setCouponDiscountType(CouponDiscountType couponDiscountType) {
        this.couponDiscountType = couponDiscountType;
    }

    public Integer getCirculation() {
        return circulation;
    }

    public void setCirculation(Integer circulation) {
        this.circulation = circulation;
    }

    public Float getUseCondition() {
        return useCondition;
    }

    public CouponUseConditionType getCouponUseConditionType() {
        return couponUseConditionType;
    }

    public void setCouponUseConditionType(CouponUseConditionType couponUseConditionType) {
        this.couponUseConditionType = couponUseConditionType;
    }

    public void setUseCondition(Float useCondition) {
        this.useCondition = useCondition;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public Date getValidStart() {
        return validStart;
    }

    public void setValidStart(Date validStart) {
        this.validStart = validStart;
    }

    public Date getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(Date validEnd) {
        this.validEnd = validEnd;
    }

    public CouponValidType getCouponValidType() {
        return couponValidType;
    }

    public void setCouponValidType(CouponValidType couponValidType) {
        this.couponValidType = couponValidType;
    }

    public String getLimitProductTypes() {
        return limitProductTypes;
    }

    public void setLimitProductTypes(String limitProductTypes) {
        this.limitProductTypes = limitProductTypes;
    }

    public String getLimitTargetIds() {
        return limitTargetIds;
    }

    public void setLimitTargetIds(String limitTargetIds) {
        this.limitTargetIds = limitTargetIds;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getLimitInfo() {
        return limitInfo;
    }

    public void setLimitInfo(String limitInfo) {
        this.limitInfo = limitInfo;
    }

    public CouponReceiveLimitType getCouponReceiveLimitType() {
        return couponReceiveLimitType;
    }

    public void setCouponReceiveLimitType(CouponReceiveLimitType couponReceiveLimitType) {
        this.couponReceiveLimitType = couponReceiveLimitType;
    }

    public Integer getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(Integer availableNum) {
        this.availableNum = availableNum;
    }

    public Integer getReceiveLimit() {
        return receiveLimit;
    }

    public void setReceiveLimit(Integer receiveLimit) {
        this.receiveLimit = receiveLimit;
    }

    public Float getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Float maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public CouponStatus getStatus() {
        return status;
    }

    public void setStatus(CouponStatus status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<CouponStatus> getNeededStatuses() {
        return neededStatuses;
    }

    public void setNeededStatuses(List<CouponStatus> neededStatuses) {
        this.neededStatuses = neededStatuses;
    }

    public Integer getReceivedNum() {
        return receivedNum;
    }

    public void setReceivedNum(Integer receivedNum) {
        this.receivedNum = receivedNum;
    }

    public Integer getReceivedPersonNum() {
        return receivedPersonNum;
    }

    public void setReceivedPersonNum(Integer receivedPersonNum) {
        this.receivedPersonNum = receivedPersonNum;
    }
}
