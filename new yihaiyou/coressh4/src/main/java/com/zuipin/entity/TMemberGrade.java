package com.zuipin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TMemberGrade entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_MEMBER_GRADE")
public class TMemberGrade implements java.io.Serializable {
	
	// Fields
	
	private Long	id;
	private Double	saleDiscount;
	private String	gradeName;
	private String	inputPerson;
	private Date	inputDate;
	private String	updatePerson;
	private Date	updateDate;
	private Double	totalSaleAmt;
	private Double	secTotalSaleAmt;
	private Integer	showOrder;
	private String	saleIntro;
	private Long	nextGrade;
	private String	discountAuth;
	private String	officialCard;
	private String	status;
	private Double	smallCardDeductDiscount;
	private Double	cardDeductDiscount;
	private Double	memberDiscount;
	
	// Constructors
	
	/** default constructor */
	public TMemberGrade() {
	}
	
	/** minimal constructor */
	public TMemberGrade(Long id, Double saleDiscount, String gradeName, String inputPerson, Date inputDate, String updatePerson, Date updateDate, Double totalSaleAmt,
			Double secTotalSaleAmt, Integer showOrder, String discountAuth, String officialCard, String status) {
		this.id = id;
		this.saleDiscount = saleDiscount;
		this.gradeName = gradeName;
		this.inputPerson = inputPerson;
		this.inputDate = inputDate;
		this.updatePerson = updatePerson;
		this.updateDate = updateDate;
		this.totalSaleAmt = totalSaleAmt;
		this.secTotalSaleAmt = secTotalSaleAmt;
		this.showOrder = showOrder;
		this.discountAuth = discountAuth;
		this.officialCard = officialCard;
		this.status = status;
	}
	
	/** full constructor */
	public TMemberGrade(Long id, Double saleDiscount, String gradeName, String inputPerson, Date inputDate, String updatePerson, Date updateDate, Double totalSaleAmt,
			Double secTotalSaleAmt, Integer showOrder, String saleIntro, Long nextGrade, String discountAuth, String officialCard, String status) {
		this.id = id;
		this.saleDiscount = saleDiscount;
		this.gradeName = gradeName;
		this.inputPerson = inputPerson;
		this.inputDate = inputDate;
		this.updatePerson = updatePerson;
		this.updateDate = updateDate;
		this.totalSaleAmt = totalSaleAmt;
		this.secTotalSaleAmt = secTotalSaleAmt;
		this.showOrder = showOrder;
		this.saleIntro = saleIntro;
		this.nextGrade = nextGrade;
		this.discountAuth = discountAuth;
		this.officialCard = officialCard;
		this.status = status;
	}
	
	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 18, scale = 0)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "SALE_DISCOUNT", nullable = false, precision = 3, scale = 3)
	public Double getSaleDiscount() {
		return this.saleDiscount;
	}
	
	public void setSaleDiscount(Double saleDiscount) {
		this.saleDiscount = saleDiscount;
	}
	
	@Column(name = "GRADE_NAME", nullable = false, length = 50)
	public String getGradeName() {
		return this.gradeName;
	}
	
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	@Column(name = "INPUT_PERSON", nullable = false, length = 50)
	public String getInputPerson() {
		return this.inputPerson;
	}
	
	public void setInputPerson(String inputPerson) {
		this.inputPerson = inputPerson;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INPUT_DATE", nullable = false, length = 7)
	public Date getInputDate() {
		return this.inputDate;
	}
	
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
	@Column(name = "UPDATE_PERSON", nullable = false, length = 50)
	public String getUpdatePerson() {
		return this.updatePerson;
	}
	
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE", nullable = false, length = 7)
	public Date getUpdateDate() {
		return this.updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Column(name = "TOTAL_SALE_AMT", nullable = false, precision = 12)
	public Double getTotalSaleAmt() {
		return this.totalSaleAmt;
	}
	
	public void setTotalSaleAmt(Double totalSaleAmt) {
		this.totalSaleAmt = totalSaleAmt;
	}
	
	@Column(name = "SEC_TOTAL_SALE_AMT", nullable = false, precision = 12)
	public Double getSecTotalSaleAmt() {
		return this.secTotalSaleAmt;
	}
	
	public void setSecTotalSaleAmt(Double secTotalSaleAmt) {
		this.secTotalSaleAmt = secTotalSaleAmt;
	}
	
	@Column(name = "SHOW_ORDER", nullable = false, precision = 8, scale = 0)
	public Integer getShowOrder() {
		return this.showOrder;
	}
	
	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}
	
	@Column(name = "SALE_INTRO", length = 200)
	public String getSaleIntro() {
		return this.saleIntro;
	}
	
	public void setSaleIntro(String saleIntro) {
		this.saleIntro = saleIntro;
	}
	
	@Column(name = "NEXT_GRADE", precision = 18, scale = 0)
	public Long getNextGrade() {
		return this.nextGrade;
	}
	
	public void setNextGrade(Long nextGrade) {
		this.nextGrade = nextGrade;
	}
	
	@Column(name = "DISCOUNT_AUTH", nullable = false, length = 2)
	public String getDiscountAuth() {
		return this.discountAuth;
	}
	
	public void setDiscountAuth(String discountAuth) {
		this.discountAuth = discountAuth;
	}
	
	@Column(name = "OFFICIAL_CARD", nullable = false, length = 2)
	public String getOfficialCard() {
		return this.officialCard;
	}
	
	public void setOfficialCard(String officialCard) {
		this.officialCard = officialCard;
	}
	
	@Column(name = "STATUS", nullable = false, length = 2)
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "SMALL_CARD_DEDUCT_DISCOUNT", precision = 12, scale = 2)
	public Double getSmallCardDeductDiscount() {
		return smallCardDeductDiscount;
	}
	
	public void setSmallCardDeductDiscount(Double smallCardDeductDiscount) {
		this.smallCardDeductDiscount = smallCardDeductDiscount;
	}
	
	@Column(name = "CARD_DEDUCT_DISCOUNT", precision = 12, scale = 2)
	public Double getCardDeductDiscount() {
		return cardDeductDiscount;
	}
	
	public void setCardDeductDiscount(Double cardDeductDiscount) {
		this.cardDeductDiscount = cardDeductDiscount;
	}
	
	@Column(name = "MEMBER_DISCOUNT", precision = 12, scale = 2)
	public Double getMemberDiscount() {
		return memberDiscount;
	}
	
	public void setMemberDiscount(Double memberDiscount) {
		this.memberDiscount = memberDiscount;
	}
	
}
