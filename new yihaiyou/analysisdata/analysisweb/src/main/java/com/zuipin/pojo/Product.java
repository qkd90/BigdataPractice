package com.zuipin.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.zuipin.pojo.enums.ProductStatus;
import com.zuipin.pojo.enums.ProductUnit;

/**
 * Product entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "product")
@JsonFilter("product")
public class Product extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= -2063273875260661158L;
	private Long				id;											//
	private ProductCat			productCat;									// 商品分类
	private Supplier			supplier;										// 默认供应商 TODO 该字段可以废弃
	private String				proName;										// 商品名称
	private String				proNo;											// 商品编码
	private String				typeCode;										// 类目编码
	private String				typeProName;									// 类目产品名称
	private Boolean				hasMakeUp;										// TODO 该字段可以废弃
	private ProductStatus		status;										// 是否上下架
	private Double				weight;										// 重量
	private ProductUnit			proUnit;										// 单位
	private String				specifications;								// 规格
	private String				inputPerson;
	private Date				inputDate;
	private String				updatePerson;
	private Date				updateDate;
	private Double				salePrice;										// 销售价（会员价）
	private Double				costPrice;										// 成本价（批发价）
	private String				pageTitle;										// 页面标题
	private String				pageKeyWord;									// 页面关键词
	private String				describe;										// 描述
	private String				proNoSku;										// 编码SKU
	private String				defaultImg;									// 商品图片
	private String				imageDir			= "";						// 图片文件夹
	private Integer				sempMaterialId;								// 对应的赛普meterialId
	private Double				wuliuFenVal;									// 物流分值
	private Double				stockWarnValue;								// 下限
	private Double				stockTopsValue;								// 上限
	private String				subHeading;									// 副标题
	private String				headDesc;										// 头部描述
	private Integer				validMonth;									// 保质期时间，默认单位：月
	private Integer				isVirtual;										// 是否是虚拟，默认0：不是虚拟； 1：是虚拟
	private Integer				productType;									// 商品类型 0为采销 1为分销
	private Integer				isSample;										// 是否有样品（1：没有；2：有）
																				
	/************** 物流资料 ******************/
	private Double				purchasePrice;									// 采购单价
																				
	/**
	 * 是否拆分， 是： 1
	 */
	public static final int		IS_DIS_TRUE			= 1;
	
	/**
	 * 是否拆分，否：0
	 */
	public static final int		IS_DIS_FALSE		= 0;
	
	// Constructors
	
	/** default constructor */
	public Product() {
	}
	
	public Product(Long id) {
		this.id = id;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROCAT_ID")
	public ProductCat getProductCat() {
		return this.productCat;
	}
	
	public void setProductCat(ProductCat productCat) {
		this.productCat = productCat;
		/*
		 * this.productCat = new ProductCat(); this.productCat.setId(productCat.getId()); this.productCat.setCatName(productCat.getCatName());
		 */
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUPPLIER_ID")
	public Supplier getSupplier() {
		return this.supplier;
	}
	
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
		/*
		 * this.supplier = new Supplier(); this.supplier.setId(supplier.getId()); this.supplier.setSupplierName(supplier.getSupplierName());
		 */
	}
	
	@Column(name = "PRO_NAME", length = 500)
	public String getProName() {
		return this.proName;
	}
	
	public void setProName(String proName) {
		this.proName = proName;
	}
	
	@Column(name = "HAS_MAKE_UP")
	public Boolean getHasMakeUp() {
		return this.hasMakeUp;
	}
	
	public void setHasMakeUp(Boolean hasMakeUp) {
		this.hasMakeUp = hasMakeUp;
	}
	
	@Column(name = "PRO_NO", length = 500)
	public String getProNo() {
		return this.proNo;
	}
	
	public void setProNo(String proNo) {
		this.proNo = proNo;
	}
	
	@Column(name = "TYPE_CODE")
	public String getTypeCode() {
		return typeCode;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	@Column(name = "TYPE_PRO_NAME")
	public String getTypeProName() {
		return typeProName;
	}
	
	public void setTypeProName(String typeProName) {
		this.typeProName = typeProName;
	}
	
	@Column(name = "STATE")
	@Enumerated(EnumType.STRING)
	public ProductStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(ProductStatus status) {
		this.status = status;
	}
	
	@Column(name = "WEIGHT", precision = 22, scale = 0)
	public Double getWeight() {
		return this.weight;
	}
	
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	@Column(name = "INPUT_PERSON", length = 500)
	public String getInputPerson() {
		return this.inputPerson;
	}
	
	public void setInputPerson(String inputPerson) {
		this.inputPerson = inputPerson;
	}
	
	@Column(name = "INPUT_DATE", length = 19)
	public Date getInputDate() {
		return this.inputDate;
	}
	
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
	@Column(name = "UPDATE_PERSON", length = 500)
	public String getUpdatePerson() {
		return this.updatePerson;
	}
	
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	
	@Column(name = "UPDATE_DATE", length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Column(name = "PRO_UNIT")
	@Enumerated(EnumType.STRING)
	public ProductUnit getProUnit() {
		return this.proUnit;
	}
	
	public void setProUnit(ProductUnit proUnit) {
		this.proUnit = proUnit;
	}
	
	@Column(name = "PURCHASE_PRICE", precision = 22, scale = 0)
	public Double getPurchasePrice() {
		return this.purchasePrice;
	}
	
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	@Column(name = "SPECIFICATIONS", length = 500)
	public String getSpecifications() {
		return this.specifications;
	}
	
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	
	@Column(name = "SALE_PRICE", precision = 20, scale = 5)
	public Double getSalePrice() {
		return this.salePrice;
	}
	
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	
	@Column(name = "COST_PRICE", precision = 20, scale = 5)
	public Double getCostPrice() {
		return costPrice;
	}
	
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	
	@Column(name = "PAGE_TITLE", length = 500)
	public String getPageTitle() {
		return this.pageTitle;
	}
	
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
	@Column(name = "PAGE_KEY_WORD", length = 500)
	public String getPageKeyWord() {
		return this.pageKeyWord;
	}
	
	public void setPageKeyWord(String pageKeyWord) {
		this.pageKeyWord = pageKeyWord;
	}
	
	@Column(name = "DESCRIPTION")
	public String getDescribe() {
		return this.describe;
	}
	
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	@Column(name = "PRO_NO_SKU", length = 500)
	public String getProNoSku() {
		return this.proNoSku;
	}
	
	public void setProNoSku(String proNoSku) {
		this.proNoSku = proNoSku;
	}
	
	@Column(name = "DEFAULT_IMG", length = 500)
	public String getDefaultImg() {
		return defaultImg;
	}
	
	public void setDefaultImg(String defaultImg) {
		this.defaultImg = defaultImg;
	}
	
	@Column(name = "IMAGE_DIR", length = 500)
	public String getImageDir() {
		return imageDir;
	}
	
	public void setImageDir(String imageDir) {
		this.imageDir = imageDir;
	}
	
	@Column(name = "SEMP_MATERIAL_ID")
	public Integer getSempMaterialId() {
		return sempMaterialId;
	}
	
	public void setSempMaterialId(Integer sempMaterialId) {
		this.sempMaterialId = sempMaterialId;
	}
	
	@Column(name = "STOCK_WARN_VALUE")
	public Double getStockWarnValue() {
		return this.stockWarnValue;
	}
	
	public void setStockWarnValue(Double stockWarnValue) {
		this.stockWarnValue = stockWarnValue;
	}
	
	@Column(name = "STOCK_TOPS_VALUE")
	public Double getStockTopsValue() {
		return this.stockTopsValue;
	}
	
	public void setStockTopsValue(Double stockTopsValue) {
		this.stockTopsValue = stockTopsValue;
	}
	
	@Column(name = "SUBHEADING")
	public String getSubHeading() {
		return subHeading;
	}
	
	public void setSubHeading(String subHeading) {
		this.subHeading = subHeading;
	}
	
	@Column(name = "HEADDESC")
	public String getHeadDesc() {
		return headDesc;
	}
	
	public void setHeadDesc(String headDesc) {
		this.headDesc = headDesc;
	}
	
	@Column(name = "VALID_MONTH")
	public Integer getValidMonth() {
		return validMonth;
	}
	
	public void setValidMonth(Integer validMonth) {
		this.validMonth = validMonth;
	}
	
	@Column(name = "WULIU_FEN_VAL", precision = 20, scale = 5)
	public Double getWuliuFenVal() {
		return wuliuFenVal;
	}
	
	public void setWuliuFenVal(Double wuliuFenVal) {
		this.wuliuFenVal = wuliuFenVal;
	}
	
	@Column(name = "IS_VIRTUAL", length = 1)
	public Integer getIsVirtual() {
		return isVirtual;
	}
	
	public void setIsVirtual(Integer isVirtual) {
		this.isVirtual = isVirtual;
	}
	
	@Column(name = "PRO_TYPE")
	public Integer getProductType() {
		return productType;
	}
	
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	
	@Column(name = "IS_SAMPLE")
	public Integer getIsSample() {
		return isSample;
	}
	
	public void setIsSample(Integer isSample) {
		this.isSample = isSample;
	}
	
}