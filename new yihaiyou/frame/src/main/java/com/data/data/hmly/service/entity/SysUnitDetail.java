package com.data.data.hmly.service.entity;

import com.framework.hibernate.annotation.RegFilter;

import javax.persistence.*;

@Entity
@Table(name = "sys_unit_detail")
@RegFilter
public class SysUnitDetail extends com.framework.hibernate.util.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9010619245437721918L;
	@Id
	@GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	private SupplierType supplierType;
	@Enumerated(EnumType.STRING)
	private BusinessScope businessScope;
	@Enumerated(EnumType.STRING)
	private BusinessType businessType;
	@Enumerated(EnumType.STRING)
	private BusinessModel businessModel;
	private String telphone, fax, mainBody, partnerChannel, partnerUrl, partnerAdvantage, introduction, contactName, mobile, mainBusiness, buildTime;
	
	private Long scenicid;
	
	private String brandName;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="unitId")
	private SysUnit sysUnit;
	private String logoImgPath;
	private Long inivitorId;
	private String inivitorName;
	private String legalPerson;
	private String legalIdCardNo;
	@Enumerated(EnumType.STRING)
	private CertificateType certificateType;


	private String crtacc;	//银行帐号
	private String crtnam;	//银行开户名
	private String crtbnk;	//银行名称
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "crtCity")
	private TbArea crtCity;	//开户城市

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SupplierType getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(SupplierType supplierType) {
		this.supplierType = supplierType;
	}

	public BusinessScope getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(BusinessScope businessScope) {
		this.businessScope = businessScope;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public BusinessModel getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(BusinessModel businessModel) {
		this.businessModel = businessModel;
	}

	@RegFilter
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	@RegFilter
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	@RegFilter
	public String getMainBody() {
		return mainBody;
	}

	public void setMainBody(String mainBody) {
		this.mainBody = mainBody;
	}
	@RegFilter
	public String getPartnerChannel() {
		return partnerChannel;
	}

	public void setPartnerChannel(String partnerChannel) {
		this.partnerChannel = partnerChannel;
	}
	@RegFilter
	public String getPartnerUrl() {
		return partnerUrl;
	}

	public void setPartnerUrl(String partnerUrl) {
		this.partnerUrl = partnerUrl;
	}
	@RegFilter
	public String getPartnerAdvantage() {
		return partnerAdvantage;
	}

	public void setPartnerAdvantage(String partnerAdvantage) {
		this.partnerAdvantage = partnerAdvantage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	@RegFilter
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	@RegFilter
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}
	@RegFilter
	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}
	@RegFilter
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public SysUnit getSysUnit() {
		return sysUnit;
	}

	public void setSysUnit(SysUnit sysUnit) {
		this.sysUnit = sysUnit;
	}

	@RegFilter
	public String getLogoImgPath() {
		return logoImgPath;
	}

	public void setLogoImgPath(String logoImgPath) {
		this.logoImgPath = logoImgPath;
	}

	public Long getInivitorId() {
		return inivitorId;
	}

	public void setInivitorId(Long inivitorId) {
		this.inivitorId = inivitorId;
	}
	@RegFilter
	public String getInivitorName() {
		return inivitorName;
	}

	public void setInivitorName(String inivitorName) {
		this.inivitorName = inivitorName;
	}
	@Column(name = "scenicid")
	public Long getScenicid() {
		return scenicid;
	}

	public void setScenicid(Long scenicid) {
		this.scenicid = scenicid;
	}
	@RegFilter
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	@RegFilter
	public String getLegalIdCardNo() {
		return legalIdCardNo;
	}

	public void setLegalIdCardNo(String legalIdCardNo) {
		this.legalIdCardNo = legalIdCardNo;
	}

	public CertificateType getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(CertificateType certificateType) {
		this.certificateType = certificateType;
	}
	@RegFilter
	public String getCrtacc() {
		return crtacc;
	}

	public void setCrtacc(String crtacc) {
		this.crtacc = crtacc;
	}
	@RegFilter
	public String getCrtnam() {
		return crtnam;
	}

	public void setCrtnam(String crtnam) {
		this.crtnam = crtnam;
	}
	@RegFilter
	public String getCrtbnk() {
		return crtbnk;
	}

	public void setCrtbnk(String crtbnk) {
		this.crtbnk = crtbnk;
	}

	public TbArea getCrtCity() {
		return crtCity;
	}

	public void setCrtCity(TbArea crtCity) {
		this.crtCity = crtCity;
	}
}
