package com.data.data.hmly.action.mobile.response;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.UserCouponStatus;

/**
 * Created by huangpeijie on 2016-05-17,0017.
 */
public class CouponResponse {
    private Long id;
    private String name;
    private Float faceValue;
    private Float useCondition;
    private String limitProductTypes;
    private String validStart;
    private String validEnd;
    private UserCouponStatus status;
    private String instructions;
    private String limitInfo;
    private String couponCode;

    public CouponResponse() {

    }

    public CouponResponse(UserCoupon userCoupon) {
        this.id = userCoupon.getId();
        this.name = userCoupon.getCoupon().getName();
        this.faceValue = userCoupon.getCoupon().getFaceValue();
        this.useCondition = userCoupon.getCoupon().getUseCondition();
        this.limitProductTypes = userCoupon.getLimitProductTypes();
        this.validStart = DateUtils.formatShortDate(userCoupon.getValidStart());
        this.validEnd = DateUtils.formatShortDate(userCoupon.getValidEnd());
        this.status = userCoupon.getStatus();
        this.instructions = userCoupon.getCoupon().getInstructions();
        this.limitInfo = userCoupon.getCoupon().getLimitInfo();
        this.couponCode = userCoupon.getCoupon().getCouponCode();
    }

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

    public Float getUseCondition() {
        return useCondition;
    }

    public void setUseCondition(Float useCondition) {
        this.useCondition = useCondition;
    }

    public String getLimitProductTypes() {
        return limitProductTypes;
    }

    public void setLimitProductTypes(String limitProductTypes) {
        this.limitProductTypes = limitProductTypes;
    }

    public String getValidStart() {
        return validStart;
    }

    public void setValidStart(String validStart) {
        this.validStart = validStart;
    }

    public String getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(String validEnd) {
        this.validEnd = validEnd;
    }

    public UserCouponStatus getStatus() {
        return status;
    }

    public void setStatus(UserCouponStatus status) {
        this.status = status;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getLimitInfo() {
        return limitInfo;
    }

    public void setLimitInfo(String limitInfo) {
        this.limitInfo = limitInfo;
    }
}
