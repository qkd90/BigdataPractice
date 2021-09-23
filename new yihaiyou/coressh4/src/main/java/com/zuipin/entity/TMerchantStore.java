package com.zuipin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_MERCHANT_STORE")
public class TMerchantStore implements java.io.Serializable {
	
	private Long	id;
	private Long	merchantId;
	private String	storeName;
	private String	subStoreName;
	private String	logo1Url;
	private String	logo2Url;
	private String	address;
	private String	mobile;
	private String	businessHours;
	private String	merchantIntro;
	private Integer	praiseCount;
	private String	storeType;
	private String	company;
	private Long	busClassId;
	private String	mapCoordinate;
	private String	isSelfSupport;
	private String	isFlagship;
	private String	serviceQq;
	private String	servicePhone;
	private Double	freeCarryCost;
	private Double	carryCost;
	private String	storeCode;
	private String	isChain;
	private Double	zyPresentCashback;
	private Double	zyDeductionCashback;
	private Double	tyPresentCashback;
	private Double	tyDeductionCashback;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "MERCHANT_ID", nullable = false, precision = 18, scale = 0)
	public Long getMerchantId() {
		return this.merchantId;
	}
	
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Column(name = "STORE_NAME", length = 200)
	public String getStoreName() {
		return this.storeName;
	}
	
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@Column(name = "SUB_STORE_NAME", length = 200)
	public String getSubStoreName() {
		return this.subStoreName;
	}
	
	public void setSubStoreName(String subStoreName) {
		this.subStoreName = subStoreName;
	}
	
	@Column(name = "LOGO1_URL", length = 200)
	public String getLogo1Url() {
		return this.logo1Url;
	}
	
	public void setLogo1Url(String logo1Url) {
		this.logo1Url = logo1Url;
	}
	
	@Column(name = "LOGO2_URL", length = 200)
	public String getLogo2Url() {
		return this.logo2Url;
	}
	
	public void setLogo2Url(String logo2Url) {
		this.logo2Url = logo2Url;
	}
	
	@Column(name = "ADDRESS", length = 400)
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "MOBILE", length = 40)
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "BUSINESS_HOURS", length = 100)
	public String getBusinessHours() {
		return this.businessHours;
	}
	
	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}
	
	@Column(name = "MERCHANT_INTRO")
	public String getMerchantIntro() {
		return this.merchantIntro;
	}
	
	public void setMerchantIntro(String merchantIntro) {
		this.merchantIntro = merchantIntro;
	}
	
	@Column(name = "PRAISE_COUNT", precision = 8, scale = 0)
	public Integer getPraiseCount() {
		return this.praiseCount;
	}
	
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
	
	@Column(name = "STORE_TYPE", length = 2)
	public String getStoreType() {
		return this.storeType;
	}
	
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	
	@Column(name = "COMPANY", length = 100)
	public String getCompany() {
		return this.company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Column(name = "BUS_CLASS_ID", precision = 18, scale = 0)
	public Long getBusClassId() {
		return this.busClassId;
	}
	
	public void setBusClassId(Long busClassId) {
		this.busClassId = busClassId;
	}
	
	@Column(name = "MAP_COORDINATE", length = 50)
	public String getMapCoordinate() {
		return this.mapCoordinate;
	}
	
	public void setMapCoordinate(String mapCoordinate) {
		this.mapCoordinate = mapCoordinate;
	}
	
	@Column(name = "IS_SELF_SUPPORT", length = 1)
	public String getIsSelfSupport() {
		return this.isSelfSupport;
	}
	
	public void setIsSelfSupport(String isSelfSupport) {
		this.isSelfSupport = isSelfSupport;
	}
	
	@Column(name = "IS_FLAGSHIP", length = 1)
	public String getIsFlagship() {
		return this.isFlagship;
	}
	
	public void setIsFlagship(String isFlagship) {
		this.isFlagship = isFlagship;
	}
	
	@Column(name = "SERVICE_QQ", length = 200)
	public String getServiceQq() {
		return this.serviceQq;
	}
	
	public void setServiceQq(String serviceQq) {
		this.serviceQq = serviceQq;
	}
	
	@Column(name = "SERVICE_PHONE", length = 200)
	public String getServicePhone() {
		return this.servicePhone;
	}
	
	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}
	
	@Column(name = "FREE_CARRY_COST", precision = 12, scale = 2)
	public Double getFreeCarryCost() {
		return freeCarryCost;
	}
	
	public void setFreeCarryCost(Double freeCarryCost) {
		this.freeCarryCost = freeCarryCost;
	}
	
	@Column(name = "CARRY_COST", precision = 12, scale = 2)
	public Double getCarryCost() {
		return carryCost;
	}
	
	public void setCarryCost(Double carryCost) {
		this.carryCost = carryCost;
	}
	
	@Column(name = "STORE_CODE", length = 20)
	public String getStoreCode() {
		return storeCode;
	}
	
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	@Column(name = "IS_CHAIN", length = 1)
	public String getIsChain() {
		return isChain;
	}
	
	public void setIsChain(String isChain) {
		this.isChain = isChain;
	}
	
	@Column(name = "ZY_PRESENT_CASHBACK", precision = 12, scale = 3)
	public Double getZyPresentCashback() {
		return zyPresentCashback;
	}
	
	public void setZyPresentCashback(Double zyPresentCashback) {
		this.zyPresentCashback = zyPresentCashback;
	}
	
	@Column(name = "ZY_DEDUCTION_CASHBACK", precision = 12, scale = 3)
	public Double getZyDeductionCashback() {
		return zyDeductionCashback;
	}
	
	public void setZyDeductionCashback(Double zyDeductionCashback) {
		this.zyDeductionCashback = zyDeductionCashback;
	}
	
	@Column(name = "TY_PRESENT_CASHBACK", precision = 12, scale = 3)
	public Double getTyPresentCashback() {
		return tyPresentCashback;
	}
	
	public void setTyPresentCashback(Double tyPresentCashback) {
		this.tyPresentCashback = tyPresentCashback;
	}
	
	@Column(name = "TY_DEDUCTION_CASHBACK", precision = 12, scale = 3)
	public Double getTyDeductionCashback() {
		return tyDeductionCashback;
	}
	
	public void setTyDeductionCashback(Double tyDeductionCashback) {
		this.tyDeductionCashback = tyDeductionCashback;
	}
	
}
