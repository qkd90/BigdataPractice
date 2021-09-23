package com.zuipin.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
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

/**
 * ProductCat entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "product_cat")
public class ProductCat extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= 1365375235859600751L;
	private Long				id;
	private ProductCat			parentProductCat;
	private String				parentProductCatName;
	private Long				parentProductCatId;
	private String				catName;
	private Long				catLevel;
	private String				catCode;
	private String				catSeq;
	private Boolean				brandShow;
	private Boolean				isShow;
	private String				mallKey;
	private String				mallKeyWords;
	private String				mallKeyDescription;
	private Boolean				isDelete;
	private Long				userId;
	private String				userName;
	private Double				profitMargin;
	private String				anotherName;
	private String				iconUrl;
	private String				iconUrlBak;
	private Date				createDate;
	private Set<ProductCat>		productCats			= new LinkedHashSet<ProductCat>();
	private Set<Product>		products			= new HashSet<Product>(0);
	private Integer				sempMaterialTypeId;
	private Integer				validMonth;											// 保质期，单位为月
																						
	// Constructors
	
	/** default constructor */
	public ProductCat() {
	}
	
	public ProductCat(Integer id) {
		if (id != null) {
			this.id = Long.valueOf(id);
		}
	}
	
	public ProductCat(Long id) {
		this.id = id;
	}
	
	/** full constructor */
	public ProductCat(ProductCat parentProductCat, String catName, Long catLevel, String catCode, String catSeq, Boolean brandShow, Boolean isShow, String mallKey,
			String mallKeyWords, String mallKeyDescription, Boolean isDelete, Long userId, String userName, Double profitMargin, String anotherName, String iconUrl,
			String iconUrlBak, Date createDate, Set<ProductCat> productCats, Set<Product> products) {
		this.parentProductCat = parentProductCat;
		this.catName = catName;
		this.catLevel = catLevel;
		this.catCode = catCode;
		this.catSeq = catSeq;
		this.brandShow = brandShow;
		this.isShow = isShow;
		this.mallKey = mallKey;
		this.mallKeyWords = mallKeyWords;
		this.mallKeyDescription = mallKeyDescription;
		this.isDelete = isDelete;
		this.userId = userId;
		this.userName = userName;
		this.profitMargin = profitMargin;
		this.anotherName = anotherName;
		this.iconUrl = iconUrl;
		this.iconUrlBak = iconUrlBak;
		this.createDate = createDate;
		this.productCats = productCats;
		this.products = products;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	@JsonIgnore
	public ProductCat getParentProductCat() {
		return this.parentProductCat;
	}
	
	public void setParentProductCat(ProductCat parentProductCat) {
		this.parentProductCat = parentProductCat;
	}
	
	@Column(name = "CAT_NAME", length = 500)
	public String getCatName() {
		return this.catName;
	}
	
	public void setCatName(String catName) {
		this.catName = catName;
	}
	
	@Column(name = "CAT_LEVEL")
	public Long getCatLevel() {
		return this.catLevel;
	}
	
	public void setCatLevel(Long catLevel) {
		this.catLevel = catLevel;
	}
	
	@Column(name = "CAT_CODE", length = 500)
	public String getCatCode() {
		return this.catCode;
	}
	
	public void setCatCode(String catCode) {
		this.catCode = catCode;
	}
	
	@Column(name = "CAT_SEQ")
	public String getCatSeq() {
		return this.catSeq;
	}
	
	public void setCatSeq(String catSeq) {
		this.catSeq = catSeq;
	}
	
	@Column(name = "BRAND_SHOW")
	public Boolean getBrandShow() {
		return this.brandShow;
	}
	
	public void setBrandShow(Boolean brandShow) {
		this.brandShow = brandShow;
	}
	
	@Column(name = "IS_SHOW")
	public Boolean getIsShow() {
		return this.isShow;
	}
	
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	
	@Column(name = "MALL_KEY", length = 500)
	public String getMallKey() {
		return this.mallKey;
	}
	
	public void setMallKey(String mallKey) {
		this.mallKey = mallKey;
	}
	
	@Column(name = "MALL_KEY_WORDS", length = 500)
	public String getMallKeyWords() {
		return this.mallKeyWords;
	}
	
	public void setMallKeyWords(String mallKeyWords) {
		this.mallKeyWords = mallKeyWords;
	}
	
	@Column(name = "MALL_KEY_DESCRIPTION", length = 500)
	public String getMallKeyDescription() {
		return this.mallKeyDescription;
	}
	
	public void setMallKeyDescription(String mallKeyDescription) {
		this.mallKeyDescription = mallKeyDescription;
	}
	
	@Column(name = "IS_DELETE")
	public Boolean getIsDelete() {
		return this.isDelete;
	}
	
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	@Column(name = "USER_ID")
	public Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name = "USER_NAME", length = 500)
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "PROFIT_MARGIN", precision = 22, scale = 0)
	public Double getProfitMargin() {
		return this.profitMargin;
	}
	
	public void setProfitMargin(Double profitMargin) {
		this.profitMargin = profitMargin;
	}
	
	@Column(name = "ANOTHER_NAME", length = 500)
	public String getAnotherName() {
		return this.anotherName;
	}
	
	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}
	
	@Column(name = "ICON_URL", length = 500)
	public String getIconUrl() {
		return this.iconUrl;
	}
	
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	@Column(name = "ICON_URL_BAK", length = 500)
	public String getIconUrlBak() {
		return this.iconUrlBak;
	}
	
	public void setIconUrlBak(String iconUrlBak) {
		this.iconUrlBak = iconUrlBak;
	}
	
	@Column(name = "CREATE_DATE", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentProductCat")
	@JsonIgnore
	public Set<ProductCat> getProductCats() {
		return this.productCats;
	}
	
	public void setProductCats(Set<ProductCat> productCats) {
		this.productCats = productCats;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productCat")
	@JsonIgnore
	public Set<Product> getProducts() {
		return this.products;
	}
	
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	@Transient
	public String getParentProductCatName() {
		return parentProductCatName;
	}
	
	public void setParentProductCatName(String parentProductCatName) {
		this.parentProductCatName = parentProductCatName;
	}
	
	@Transient
	public Long getParentProductCatId() {
		return parentProductCatId;
	}
	
	public void setParentProductCatId(Long parentProductCatId) {
		this.parentProductCatId = parentProductCatId;
	}
	
	@Column(name = "SEMP_MATERIALTYPEID", length = 11)
	public Integer getSempMaterialTypeId() {
		return sempMaterialTypeId;
	}
	
	public void setSempMaterialTypeId(Integer sempMaterialTypeId) {
		this.sempMaterialTypeId = sempMaterialTypeId;
	}
	
	@Column(name = "VALID_MONTH")
	public Integer getValidMonth() {
		return validMonth;
	}
	
	public void setValidMonth(Integer validMonth) {
		this.validMonth = validMonth;
	}
	
}