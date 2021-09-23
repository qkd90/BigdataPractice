package com.data.data.hmly.service.common.entity;

import com.zuipin.util.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by caiys on 2016/3/24.
 */
@Entity
@Table(name = "productvalidaterecord")
@Deprecated
public class ProductValidateRecord extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long    id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validateCodeId")
    private ProductValidateCode productValidateCode;
    @Column(name = "productId")
    private Long productId;
    @Column(name = "productName")
    private String productName;
    @Column(name = "scenicId")
    private Long scenicId;
    @Column(name = "validateCount")
    private Integer validateCount;
    @Column(name = "validateBy")
    private Long validateBy;
    @Column(name = "validateTime")
    private Date validateTime;
    @Column(name = "buyerName")
    private String buyerName;
    @Column(name = "buyerMobile")
    private String buyerMobile;

    // 页面字段
    @Transient
    private Date    validateTimeStart;
    @Transient
    private Date    validateTimeEnd;
    @Transient
    private String    validateByName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductValidateCode getProductValidateCode() {
        return productValidateCode;
    }

    public void setProductValidateCode(ProductValidateCode productValidateCode) {
        this.productValidateCode = productValidateCode;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    public Long getValidateBy() {
        return validateBy;
    }

    public void setValidateBy(Long validateBy) {
        this.validateBy = validateBy;
    }

    public Date getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(Date validateTime) {
        this.validateTime = validateTime;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public Integer getValidateCount() {
        return validateCount;
    }

    public void setValidateCount(Integer validateCount) {
        this.validateCount = validateCount;
    }

    public Date getValidateTimeStart() {
        return validateTimeStart;
    }

    public void setValidateTimeStart(Date validateTimeStart) {
        this.validateTimeStart = validateTimeStart;
    }

    public Date getValidateTimeEnd() {
        return validateTimeEnd;
    }

    public void setValidateTimeEnd(Date validateTimeEnd) {
        this.validateTimeEnd = validateTimeEnd;
    }

    public String getValidateTimeStr() {
        if (validateTime != null) {
            return DateUtils.format(validateTime, "HH:mm");
        }
        return "";
    }

    public String getNameAndCount() {
        return getProductName() + "x" + getValidateCount();
    }


    public String getValidateByName() {
        return validateByName;
    }

    public void setValidateByName(String validateByName) {
        this.validateByName = validateByName;
    }
}
