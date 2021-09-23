package com.zuipin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "V_PRODUCT_INDEX")
public class VProductIndex implements java.io.Serializable {
	@Id
	@Column(name = "ID", precision = 18, scale = 0)
	private Long	id;
	@Column(name = "PRO_NAME")
	private String	proName;
	@Column(name = "BRAND_NAME")
	private String	brandName;
	@Column(name = "CAT_NAME_X")
	private String	catNameX;
	@Column(name = "CAT_ID_X")
	private Long	catIdX;
	@Column(name = "CAT_NAME_Y")
	private String	catNameY;
	@Column(name = "CAT_ID_Y")
	private Long	catIdY;
	@Column(name = "CAT_NAME_Z")
	private String	catNameZ;
	@Column(name = "CAT_ID_Z")
	private Long	catIdZ;
	@Column(name = "SALE_PRICE")
	private Double	salePrice;
	@Column(name = "ON_SALE_DATE")
	private Date	onSaleDate;
	@Column(name = "NET_SALE_COUNT")
	private Double	netSaleCount;
	@Column(name = "COMM_COUNT")
	private Integer	commCount;
	@Column(name = "VALUE_ID")
	private String	valueId;
	@Column(name = "MALL_KEY_WORDS")
	private String	mallKeyWords;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProName() {
		return proName;
	}
	
	public void setProName(String proName) {
		this.proName = proName;
	}
	
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public String getCatNameX() {
		return catNameX;
	}
	
	public void setCatNameX(String catNameX) {
		this.catNameX = catNameX;
	}
	
	public Long getCatIdX() {
		return catIdX;
	}
	
	public void setCatIdX(Long catIdX) {
		this.catIdX = catIdX;
	}
	
	public String getCatNameY() {
		return catNameY;
	}
	
	public void setCatNameY(String catNameY) {
		this.catNameY = catNameY;
	}
	
	public Long getCatIdY() {
		return catIdY;
	}
	
	public void setCatIdY(Long catIdY) {
		this.catIdY = catIdY;
	}
	
	public String getCatNameZ() {
		return catNameZ;
	}
	
	public void setCatNameZ(String catNameZ) {
		this.catNameZ = catNameZ;
	}
	
	public Long getCatIdZ() {
		return catIdZ;
	}
	
	public void setCatIdZ(Long catIdZ) {
		this.catIdZ = catIdZ;
	}
	
	public Double getSalePrice() {
		return salePrice;
	}
	
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	
	public Date getOnSaleDate() {
		return onSaleDate;
	}
	
	public void setOnSaleDate(Date onSaleDate) {
		this.onSaleDate = onSaleDate;
	}
	
	public Double getNetSaleCount() {
		return netSaleCount;
	}
	
	public void setNetSaleCount(Double netSaleCount) {
		this.netSaleCount = netSaleCount;
	}
	
	public Integer getCommCount() {
		return commCount;
	}
	
	public void setCommCount(Integer commCount) {
		this.commCount = commCount;
	}
	
	public String getValueId() {
		return valueId;
	}
	
	public void setValueId(String valueId) {
		this.valueId = valueId;
	}
	
	public String getMallKeyWords() {
		return mallKeyWords;
	}
	
	public void setMallKeyWords(String mallKeyWords) {
		this.mallKeyWords = mallKeyWords;
	}
}
