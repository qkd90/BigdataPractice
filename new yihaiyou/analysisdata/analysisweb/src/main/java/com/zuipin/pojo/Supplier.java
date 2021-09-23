package com.zuipin.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.zuipin.service.entity.SysUser;

/**
 * Supplier entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "supplier")
public class Supplier extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= -2414681009787527103L;
	private Long				id;
	private String				createdTime;									// 创建时间
	private SysUser				wfStaff;										// 用户信息表
	private String				supplierName;									// 供应商名称
	private String				supplierCode;									// 供应商编码
	private String				supplierAbbreviation;							// 供应商简称
	private String				contacts;										// 联系人
	private String				mobilephone;									// 手机号码
	private String				telephone;										// 座机号码
	private String				email;											// 邮箱
	private String				companyUrl;									// 公司网址
	private String				inputPerson;									// 修改人 //
	private String				inputDate;										// 修改时间
	private String				logo;											// 公司logo
	private Boolean				isbrand;										// 是否为品牌商，1是，0否
	private String				companyAddress;								// 公司地址
	private String				companyDesc;									// 公司描述
	private String				fax;											// 传真
	private String				prov;											// 省
	private String				city;											// 市
	private String				area;											// 县
	private Long				sempId;
	private String				checkStatus;									// 审核状态，0为已审核通过，1为未审核 2为审核不通过
	private String				status;										// 合作状态 0为备选 1为合作中 2为淘汰
	private String				isPass;										// 是否为合格供应商（1为合格 2为不合格）
																				
	private String				regisTime;										// 成立时间
	private String				qq;											// QQ
	private String				hasEnterPlat;									// 已入住电商平台
	private String				hasEnterTime;									// 入驻申请时间
	private Integer				hasLicense;									// 是否有营业执照 ， 0为有，1为无
	private Integer				hasTax;										// 是否有税务登记表， 0为有，1为无
	private Integer				hasQuality;									// 产品质量检验合格证明或质量检测报告，0为有，1为无
	private Integer				hasQs;											// QS认证 ， 0为有，1为无
	private Integer				hasAttest;										// 是否有茶园认证，0为有，1为无
	private Double				coreArea;										// 核心面积
	private Double				proArea;										// 产区面积
	private Double				outerArea;										// 外山面积
	private String				rewards;										// 获奖情况
	private String				applyMan;										// 申请人签字
	private String				applyTime;										// 申请日期
	private Integer				companyNum;									// 企业员工数量
	private Double				yearAccount;									// 年销售额
	private Integer				newProNum;										// 新品开发数量
	private Double				springNum;										// 春季产能
	private Double				summerNum;										// 夏季产能
	private Double				winterNum;										// 秋冬季产能
																				
	private String				passTime;										// 鉴定时间
	private String				supplyType;									// 供应品类
	private String				yearValue;										// 年产值
	private String				regisNum;										// 注册资金
	private String				superPro;										// 优势产品
	private String				superDesc;										// 综合优势描述
	private Long				ratingId;
	private Integer				pgCount;										// 评估次数 ，最多进行2次
																				
	public Supplier() {
	}
	
	public Supplier(Long id) {
		super();
		this.id = id;
	}
	
	public Supplier(String supplierName) {
		this.supplierName = supplierName;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "CREATED_TIME")
	public String getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STAFF_ID")
	public SysUser getWfStaff() {
		return wfStaff;
	}
	
	public void setWfStaff(SysUser wfStaff) {
		this.wfStaff = wfStaff;
	}
	
	@Column(name = "SUPPLIER_NAME", length = 500)
	public String getSupplierName() {
		return this.supplierName;
	}
	
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	@Column(name = "SUPPLIER_ABBREVIATION", length = 500)
	public String getSupplierAbbreviation() {
		return this.supplierAbbreviation;
	}
	
	public void setSupplierAbbreviation(String supplierAbbreviation) {
		this.supplierAbbreviation = supplierAbbreviation;
	}
	
	@Column(name = "CONTACTS", length = 500)
	public String getContacts() {
		return this.contacts;
	}
	
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@Column(name = "MOBILEPHONE", length = 500)
	public String getMobilephone() {
		return this.mobilephone;
	}
	
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	
	@Column(name = "TELEPHONE", length = 500)
	public String getTelephone() {
		return this.telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Column(name = "EMAIL", length = 500)
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "COMPANY_URL", length = 500)
	public String getCompanyUrl() {
		return this.companyUrl;
	}
	
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	
	@Column(name = "INPUT_PERSON", length = 500)
	public String getInputPerson() {
		return this.inputPerson;
	}
	
	public void setInputPerson(String inputPerson) {
		this.inputPerson = inputPerson;
	}
	
	@Column(name = "INPUT_DATE", length = 19)
	public String getInputDate() {
		return this.inputDate;
	}
	
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	
	@Column(name = "LOGO", length = 500)
	public String getLogo() {
		return this.logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Column(name = "ISBRAND")
	public Boolean getIsbrand() {
		return this.isbrand;
	}
	
	public void setIsbrand(Boolean isbrand) {
		this.isbrand = isbrand;
	}
	
	@Column(name = "COMPANY_ADDRESS", length = 500)
	public String getCompanyAddress() {
		return this.companyAddress;
	}
	
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	@Column(name = "COMPANY_DESC", length = 500)
	public String getCompanyDesc() {
		return this.companyDesc;
	}
	
	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}
	
	@Column(name = "FAX", length = 50)
	public String getFax() {
		return this.fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Column(name = "PROV")
	public String getProv() {
		return prov;
	}
	
	/**
	 * @param prov
	 *            the prov to set
	 */
	public void setProv(String prov) {
		this.prov = prov;
	}
	
	@Column(name = "CITY")
	public String getCity() {
		return city;
	}
	
	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "AREA")
	public String getArea() {
		return area;
	}
	
	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	
	@Column(name = "SEMP_ID")
	public Long getSempId() {
		return sempId;
	}
	
	public void setSempId(Long sempId) {
		this.sempId = sempId;
	}
	
	@Column(name = "SUPPLIER_CODE")
	public String getSupplierCode() {
		return supplierCode;
	}
	
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	@Column(name = "CHECK_STATUS")
	public String getCheckStatus() {
		return checkStatus;
	}
	
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "IS_PASS")
	public String getIsPass() {
		return isPass;
	}
	
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	
	@Column(name = "REGIS_TIME")
	public String getRegisTime() {
		return regisTime;
	}
	
	public void setRegisTime(String regisTime) {
		this.regisTime = regisTime;
	}
	
	@Column(name = "QQ")
	public String getQq() {
		return qq;
	}
	
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@Column(name = "HAS_ENTER_PLAT")
	public String getHasEnterPlat() {
		return hasEnterPlat;
	}
	
	public void setHasEnterPlat(String hasEnterPlat) {
		this.hasEnterPlat = hasEnterPlat;
	}
	
	@Column(name = "HAS_ENTER_TIME")
	public String getHasEnterTime() {
		return hasEnterTime;
	}
	
	public void setHasEnterTime(String hasEnterTime) {
		this.hasEnterTime = hasEnterTime;
	}
	
	@Column(name = "HAS_LICENSE")
	public Integer getHasLicense() {
		return hasLicense;
	}
	
	public void setHasLicense(Integer hasLicense) {
		this.hasLicense = hasLicense;
	}
	
	@Column(name = "HAS_TAX")
	public Integer getHasTax() {
		return hasTax;
	}
	
	public void setHasTax(Integer hasTax) {
		this.hasTax = hasTax;
	}
	
	@Column(name = "HAS_QUALITY")
	public Integer getHasQuality() {
		return hasQuality;
	}
	
	public void setHasQuality(Integer hasQuality) {
		this.hasQuality = hasQuality;
	}
	
	@Column(name = "HAS_ATTEST")
	public Integer getHasAttest() {
		return hasAttest;
	}
	
	public void setHasAttest(Integer hasAttest) {
		this.hasAttest = hasAttest;
	}
	
	@Column(name = "CORE_AREA")
	public Double getCoreArea() {
		return coreArea;
	}
	
	public void setCoreArea(Double coreArea) {
		this.coreArea = coreArea;
	}
	
	@Column(name = "PRO_AREA")
	public Double getProArea() {
		return proArea;
	}
	
	public void setProArea(Double proArea) {
		this.proArea = proArea;
	}
	
	@Column(name = "OUTER_AREA")
	public Double getOuterArea() {
		return outerArea;
	}
	
	public void setOuterArea(Double outerArea) {
		this.outerArea = outerArea;
	}
	
	@Column(name = "HAS_QS")
	public Integer getHasQs() {
		return hasQs;
	}
	
	public void setHasQs(Integer hasQs) {
		this.hasQs = hasQs;
	}
	
	@Column(name = "REWARDS")
	public String getRewards() {
		return rewards;
	}
	
	public void setRewards(String rewards) {
		this.rewards = rewards;
	}
	
	@Column(name = "APPLY_MAN")
	public String getApplyMan() {
		return applyMan;
	}
	
	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}
	
	@Column(name = "APPLY_TIME")
	public String getApplyTime() {
		return applyTime;
	}
	
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	
	@Column(name = "COMPANY_NUM")
	public Integer getCompanyNum() {
		return companyNum;
	}
	
	public void setCompanyNum(Integer companyNum) {
		this.companyNum = companyNum;
	}
	
	@Column(name = "YEAR_ACCOUNT")
	public Double getYearAccount() {
		return yearAccount;
	}
	
	public void setYearAccount(Double yearAccount) {
		this.yearAccount = yearAccount;
	}
	
	@Column(name = "NEW_PRO_NUM")
	public Integer getNewProNum() {
		return newProNum;
	}
	
	public void setNewProNum(Integer newProNum) {
		this.newProNum = newProNum;
	}
	
	@Column(name = "SPRING_NUM")
	public Double getSpringNum() {
		return springNum;
	}
	
	public void setSpringNum(Double springNum) {
		this.springNum = springNum;
	}
	
	@Column(name = "SUMMER_NUM")
	public Double getSummerNum() {
		return summerNum;
	}
	
	public void setSummerNum(Double summerNum) {
		this.summerNum = summerNum;
	}
	
	@Column(name = "WINTER_NUM")
	public Double getWinterNum() {
		return winterNum;
	}
	
	public void setWinterNum(Double winterNum) {
		this.winterNum = winterNum;
	}
	
	@Column(name = "PASS_TIME")
	public String getPassTime() {
		return passTime;
	}
	
	public void setPassTime(String passTime) {
		this.passTime = passTime;
	}
	
	@Column(name = "SUPPLY_TYPE")
	public String getSupplyType() {
		return supplyType;
	}
	
	public void setSupplyType(String supplyType) {
		this.supplyType = supplyType;
	}
	
	@Column(name = "YEAR_VALUE")
	public String getYearValue() {
		return yearValue;
	}
	
	public void setYearValue(String yearValue) {
		this.yearValue = yearValue;
	}
	
	@Column(name = "REGIS_NUM")
	public String getRegisNum() {
		return regisNum;
	}
	
	public void setRegisNum(String regisNum) {
		this.regisNum = regisNum;
	}
	
	@Column(name = "SUPER_PRO")
	public String getSuperPro() {
		return superPro;
	}
	
	public void setSuperPro(String superPro) {
		this.superPro = superPro;
	}
	
	@Column(name = "SUPER_DESC")
	public String getSuperDesc() {
		return superDesc;
	}
	
	public void setSuperDesc(String superDesc) {
		this.superDesc = superDesc;
	}
	
	@Column(name = "RATING_ID")
	public Long getRatingId() {
		return ratingId;
	}
	
	public void setRatingId(Long ratingId) {
		this.ratingId = ratingId;
	}
	
	@Column(name = "PG_COUNT")
	public Integer getPgCount() {
		return pgCount;
	}
	
	public void setPgCount(Integer pgCount) {
		this.pgCount = pgCount;
	}
	
}