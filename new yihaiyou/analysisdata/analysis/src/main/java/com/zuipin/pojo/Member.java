package com.zuipin.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zuipin.pojo.enums.MemberType;

/**
 * Member entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "member")
public class Member extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long		serialVersionUID	= -6421897910894894286L;
	private Long					id;
	private String					account;												// 客户编码
	private String					nickName;												// 客户编码名称
	private String					realName;												// 联系人
	private String					phone;													// 手机号
	private String					province;												// 省
	private String					city;													// 市
	private String					area;													// 区
	private String					sendTo;												// 发货地址
	private String					address;												// 地址【可不用】
	/* 20140821 新增 start */
	private String					memberName;
	private Long					lev;
	private MemberType				memberType;
	private Long					spId;
	// 导入或创建 1为外部导入,0为使用系统创建
	private Boolean					isImport;
	/* 20140821新增 end */
	private String					email;
	private MemberRank				memberRank;
	private Date					registerDate;
	private String					password;
	private Long					integral;
	private Date					loginDate;
	private String					createSite;
	private Boolean					isVerifyEmail;
	private Double					balance;
	private Long					usableIntegral;
	private Double					usableBalance;
	private Set<ShippingAddress>	shippingAddresses	= new HashSet<ShippingAddress>(0);
	private Date					beginDate;
	private Date					endDate;
	
	// Constructors
	
	/** default constructor */
	public Member() {
	}
	
	/** full constructor */
	public Member(String account, String nickName, String realName, String phone, String email, MemberRank memberRank, Date registerDate) {
		this.account = account;
		this.nickName = nickName;
		this.realName = realName;
		this.phone = phone;
		this.email = email;
		this.memberRank = memberRank;
		this.registerDate = registerDate;
	}
	
	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "ACCOUNT", length = 500)
	public String getAccount() {
		return this.account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	@Column(name = "NICK_NAME", length = 500)
	public String getNickName() {
		return this.nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Column(name = "REAL_NAME", length = 500)
	public String getRealName() {
		return this.realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Column(name = "PHONE", length = 500)
	public String getPhone() {
		return this.phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "EMAIL", length = 500)
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RANK_ID")
	public MemberRank getMemberRank() {
		return this.memberRank;
	}
	
	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}
	
	@Column(name = "REGISTER_DATE", length = 19)
	public Date getRegisterDate() {
		return this.registerDate;
	}
	
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	@Column(name = "PASSWORD", length = 500)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "INTEGRAL")
	public Long getIntegral() {
		return this.integral;
	}
	
	public void setIntegral(Long integral) {
		this.integral = integral;
	}
	
	@Column(name = "LOGIN_DATE", length = 19)
	public Date getLoginDate() {
		return this.loginDate;
	}
	
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	@Column(name = "CREATE_SITE", length = 500)
	public String getCreateSite() {
		return this.createSite;
	}
	
	public void setCreateSite(String createSite) {
		this.createSite = createSite;
	}
	
	@Column(name = "IS_VERIFY_EMAIL")
	public Boolean getIsVerifyEmail() {
		return this.isVerifyEmail;
	}
	
	public void setIsVerifyEmail(Boolean isVerifyEmail) {
		this.isVerifyEmail = isVerifyEmail;
	}
	
	@Column(name = "BALANCE", precision = 22, scale = 0)
	public Double getBalance() {
		return this.balance;
	}
	
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	@Column(name = "USABLE_INTEGRAL")
	public Long getUsableIntegral() {
		return this.usableIntegral;
	}
	
	public void setUsableIntegral(Long usableIntegral) {
		this.usableIntegral = usableIntegral;
	}
	
	@Column(name = "USABLE_BALANCE", precision = 22, scale = 0)
	public Double getUsableBalance() {
		return this.usableBalance;
	}
	
	public void setUsableBalance(Double usableBalance) {
		this.usableBalance = usableBalance;
	}
	
	@Column(name = "MEMBER_NAME", length = 32)
	public String getMemberName() {
		return memberName;
	}
	
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
	@JsonIgnore
	public Set<ShippingAddress> getShippingAddresses() {
		return this.shippingAddresses;
	}
	
	public void setShippingAddresses(Set<ShippingAddress> shippingAddresses) {
		this.shippingAddresses = shippingAddresses;
	}
	
	@Transient
	public Date getBeginDate() {
		return beginDate;
	}
	
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@Transient
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name = "LEV")
	public Long getLev() {
		return lev;
	}
	
	public void setLev(Long lev) {
		this.lev = lev;
	}
	
	@Column(name = "PROVINCE")
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(name = "CITY")
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "AREA")
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "SEND_TO")
	public String getSendTo() {
		return sendTo;
	}
	
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	
	@Column(name = "IS_IMPORT")
	public Boolean getIsImport() {
		return isImport;
	}
	
	public void setIsImport(Boolean isImport) {
		this.isImport = isImport;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TYPE_ID")
	public MemberType getMemberType() {
		return memberType;
	}
	
	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}
	
	@Column(name = "TRADER_ID")
	public Long getSpId() {
		return spId;
	}
	
	public void setSpId(Long spId) {
		this.spId = spId;
	}
	
}