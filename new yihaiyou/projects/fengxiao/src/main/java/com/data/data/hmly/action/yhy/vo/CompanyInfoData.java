package com.data.data.hmly.action.yhy.vo;

import com.data.data.hmly.service.contract.entity.ContractAppendices;
import com.data.data.hmly.service.entity.UnitQualification;
import com.ibm.icu.impl.coll.ContractionsAndExpansions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/12/23.
 */
public class CompanyInfoData {

    private Long userId;
    private Long contractId;
    private String account;
    private String password;
    private String mobile;
    private String userName;
    private Date effectiveTime;
    private Date expirationTime;
    private String valuationModels;
    private String settlementType;
    private String legalPerson;
    private String legalIdCardNo;
    private String mainBusiness;
    private String passportNo;
    private String positiveIdcard;
    private String oppositiveIdcard;
    private String businessLicenseImg;
    private String passportImg;
    private String idCardImg;
    private Integer status;
    private String certificateType;
    private List<UnitQualification> qualificationList = new ArrayList<UnitQualification>();
    private List<ContractAppendices> appendiceses = new ArrayList<ContractAppendices>();
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getValuationModels() {
        return valuationModels;
    }

    public void setValuationModels(String valuationModels) {
        this.valuationModels = valuationModels;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalIdCardNo() {
        return legalIdCardNo;
    }

    public void setLegalIdCardNo(String legalIdCardNo) {
        this.legalIdCardNo = legalIdCardNo;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPositiveIdcard() {
        return positiveIdcard;
    }

    public void setPositiveIdcard(String positiveIdcard) {
        this.positiveIdcard = positiveIdcard;
    }

    public String getOppositiveIdcard() {
        return oppositiveIdcard;
    }

    public void setOppositiveIdcard(String oppositiveIdcard) {
        this.oppositiveIdcard = oppositiveIdcard;
    }

    public String getBusinessLicenseImg() {
        return businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }

    public String getIdCardImg() {
        return idCardImg;
    }

    public void setIdCardImg(String idCardImg) {
        this.idCardImg = idCardImg;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public List<UnitQualification> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<UnitQualification> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public String getPassportImg() {
        return passportImg;
    }

    public void setPassportImg(String passportImg) {
        this.passportImg = passportImg;
    }

    public List<ContractAppendices> getAppendiceses() {
        return appendiceses;
    }

    public void setAppendiceses(List<ContractAppendices> appendiceses) {
        this.appendiceses = appendiceses;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
