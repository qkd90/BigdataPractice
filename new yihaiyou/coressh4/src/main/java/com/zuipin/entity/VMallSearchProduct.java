package com.zuipin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "V_MALL_SEARCH_PRODUCT")
public class VMallSearchProduct implements java.io.Serializable {
    @Id
    @Column(name = "ID", precision = 18, scale = 0)
    private Long              id;
    @Column(name = "PRO_NAME", length = 100)
    private String            proName;
    @Column(name = "PRO_NO", length = 20)
    private String            proNo;
    @Column(name = "BAR_CODE", length = 100)
    private String            barCode;
    @Column(name = "REMARKS")
    private String            remarks;
    @Column(name = "MARKET_PRICE")
    private String            marketPrice;
    @Column(name = "STATE", length = 2)
    private String            state;
    @Column(name = "PRODUCT_PARAM")
    private String            productParam;
    @Column(name = "PRODUCT_PACK")
    private String            productPack;
    @Column(name = "SALE_SERVICE")
    private String            saleService;
    @Column(name = "WEIGHT", precision = 6)
    private Double            weight;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UP_MARKET_DATETIME", length = 7)
    private Date              upMarketDatetime;
    @Column(name = "NET_SALE_FLAG")
    private String            netSaleFlag;
    @Column(name = "PACK_FLAG", length = 1)
    private String            packFlag;
    @Column(name = "TRY_FLAG", length = 1)
    private String            tryFlag;
    @Column(name = "INPUT_PERSON", length = 50)
    private String            inputPerson;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "INPUT_DATE", length = 7)
    private Date              inputDate;
    @Column(name = "UPDATE_PERSON", length = 50)
    private String            updatePerson;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE", length = 7)
    private Date              updateDate;
    @Column(name = "SILVER_COUNT_SCALE", precision = 2)
    private Double            silverCountScale;
    @Column(name = "GOLD_COUNT_SCALE", precision = 2)
    private Double            goldCountScale;
    @Column(name = "DIA_COUNT_SCALE", precision = 2)
    private Double            diaCountScale;
    @Column(name = "UNIT", length = 20)
    private String            unit;
    @Column(name = "UNIT_NAME", length = 20)
    private String            unitName;
    @Column(name = "ACCURACY", length = 1)
    private Integer           accuracy;
    @Column(name = "PRODUCE_ADDR", length = 100)
    private String            produceAddr;
    @Column(name = "NET_SALE_COUNT", precision = 12, scale = 2)
    private Double            netSaleCount;
    @Column(name = "PATH", length = 100)
    private String            path;
    @Column(name = "REGION_ID")
    private Long              regionId;
    @Column(name = "SALE_PRICE", precision = 12)
    private Double            salePrice;
    @Column(name = "DISCOUNT", precision = 12)
    private Double            discount;
    @Column(name = "BRAND_ID")
    private Long              brandId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ON_SALE_DATE", length = 7)
    private Date              onSaleDate;
    @Column(name = "IS_GIFT")
    private String            isGift;
    @Column(name = "COMM_COUNT")
    private Long              commCount;
    @Column(name = "SIGN_COUNT")
    private Long              signCount;
    @Column(name = "DIS_COUNT")
    private Long              disCount;
    @Column(name = "ATTEN_COUNT")
    private Long              attenCount;
    @Column(name = "PROMPT_DISCOUNT", precision = 12)
    private Double            promptDiscount;
    @Column(name = "THUMBNAIL", length = 100)
    private String            thumbnail;
    @Column(name = "START_QUANTITY", precision = 6)
    private Double            startQuantity;
    @Column(name = "STEP_QUANTITY", precision = 6)
    private Double            stepQuantity;
    @Column(name = "GROUP_PRICE", precision = 12, scale = 2, nullable = false)
    private Double            groupPrice;
    @Column(name = "AMOUNT", precision = 12, scale = 2)
    private Double            amount;
    @Column(name = "PURCHASE_PRICE", precision = 12, scale = 2)
    private Double            purchasePrice;
    @Column(name = "GROSS_MARGIN", precision = 5,  scale = 4)
    private Double grossMargin;
    @Column(name = "STORE_ID", precision = 18, scale = 0)
    private Long            storeId;
    @Column(name = "MERCHANT_ID", precision = 18, scale = 0)
    private Long            merchantId;        
    @Column(name = "STORE_NAME", length = 200)
    private String            storeName;
    @Column(name = "SUB_STORE_NAME", length = 200)
    private String            subStoreName; 
    @Column(name = "STORE_TYPE", length = 2)
    private String            storeType;    
    
    private transient Integer stockCount;
    // 促销价 此属性不做数据库映射
    private transient Double  promPrice;
    private transient String  promotionType;   // 促销类别
    private transient String  sign;            // 1是促销产品
    private transient String  highlighterName; // 搜索高亮显示字段
                                                
    public Double getGroupPrice() {
        return groupPrice;
    }
    
    public void setGroupPrice(Double groupPrice) {
        this.groupPrice = groupPrice;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getProName() {
        return proName;
    }
    
    public String getMarketPrice() {
        return marketPrice;
    }
    
    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }
    
    public void setProName(String proName) {
        this.proName = proName;
    }
    
    public String getProNo() {
        return proNo;
    }
    
    public void setProNo(String proNo) {
        this.proNo = proNo;
    }
    
    public String getBarCode() {
        return barCode;
    }
    
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public Integer getStockCount() {
        return stockCount;
    }
    
    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }
    
    public String getProductParam() {
        return productParam;
    }
    
    public void setProductParam(String productParam) {
        this.productParam = productParam;
    }
    
    public String getProductPack() {
        return productPack;
    }
    
    public void setProductPack(String productPack) {
        this.productPack = productPack;
    }
    
    public String getSaleService() {
        return saleService;
    }
    
    public void setSaleService(String saleService) {
        this.saleService = saleService;
    }
    
    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    public Date getUpMarketDatetime() {
        return upMarketDatetime;
    }
    
    public void setUpMarketDatetime(Date upMarketDatetime) {
        this.upMarketDatetime = upMarketDatetime;
    }
    
    public String getNetSaleFlag() {
        return netSaleFlag;
    }
    
    public void setNetSaleFlag(String netSaleFlag) {
        this.netSaleFlag = netSaleFlag;
    }
    
    public String getPackFlag() {
        return packFlag;
    }
    
    public void setPackFlag(String packFlag) {
        this.packFlag = packFlag;
    }
    
    public String getTryFlag() {
        return tryFlag;
    }
    
    public void setTryFlag(String tryFlag) {
        this.tryFlag = tryFlag;
    }
    
    public String getInputPerson() {
        return inputPerson;
    }
    
    public void setInputPerson(String inputPerson) {
        this.inputPerson = inputPerson;
    }
    
    public Date getInputDate() {
        return inputDate;
    }
    
    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }
    
    public String getUpdatePerson() {
        return updatePerson;
    }
    
    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public Double getSilverCountScale() {
        return silverCountScale;
    }
    
    public void setSilverCountScale(Double silverCountScale) {
        this.silverCountScale = silverCountScale;
    }
    
    public Double getGoldCountScale() {
        return goldCountScale;
    }
    
    public void setGoldCountScale(Double goldCountScale) {
        this.goldCountScale = goldCountScale;
    }
    
    public Double getDiaCountScale() {
        return diaCountScale;
    }
    
    public void setDiaCountScale(Double diaCountScale) {
        this.diaCountScale = diaCountScale;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public String getUnitName() {
        return unitName;
    }
    
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    
    public Integer getAccuracy() {
        return accuracy;
    }
    
    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }
    
    public String getProduceAddr() {
        return produceAddr;
    }
    
    public void setProduceAddr(String produceAddr) {
        this.produceAddr = produceAddr;
    }
    
    public Double getNetSaleCount() {
        return netSaleCount;
    }
    
    public void setNetSaleCount(Double netSaleCount) {
        this.netSaleCount = netSaleCount;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public Long getRegionId() {
        return regionId;
    }
    
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }
    
    public Double getSalePrice() {
        return salePrice;
    }
    
    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }
    
    public Double getDiscount() {
        return discount;
    }
    
    public void setDiscount(Double discount) {
        this.discount = discount;
    }
    
    public Long getBrandId() {
        return brandId;
    }
    
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
    
    public Date getOnSaleDate() {
        return onSaleDate;
    }
    
    public void setOnSaleDate(Date onSaleDate) {
        this.onSaleDate = onSaleDate;
    }
    
    public String getIsGift() {
        return isGift;
    }
    
    public void setIsGift(String isGift) {
        this.isGift = isGift;
    }
    
    public Long getCommCount() {
        return commCount;
    }
    
    public void setCommCount(Long commCount) {
        this.commCount = commCount;
    }
    
    public Long getSignCount() {
        return signCount;
    }
    
    public void setSignCount(Long signCount) {
        this.signCount = signCount;
    }
    
    public Long getDisCount() {
        return disCount;
    }
    
    public void setDisCount(Long disCount) {
        this.disCount = disCount;
    }
    
    public Long getAttenCount() {
        return attenCount;
    }
    
    public void setAttenCount(Long attenCount) {
        this.attenCount = attenCount;
    }
    
    public Double getPromptDiscount() {
        return promptDiscount;
    }
    
    public void setPromptDiscount(Double promptDiscount) {
        this.promptDiscount = promptDiscount;
    }
    
    public String getThumbnail() {
        return thumbnail;
    }
    
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    
    public Double getStartQuantity() {
        return startQuantity;
    }
    
    public void setStartQuantity(Double startQuantity) {
        this.startQuantity = startQuantity;
    }
    
    public Double getStepQuantity() {
        return stepQuantity;
    }
    
    public void setStepQuantity(Double stepQuantity) {
        this.stepQuantity = stepQuantity;
    }
    
    public Double getPromPrice() {
        return promPrice;
    }
    
    public void setPromPrice(Double promPrice) {
        this.promPrice = promPrice;
    }
    
    public String getPromotionType() {
        return promotionType;
    }
    
    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public Double getPurchasePrice() {
        return purchasePrice;
    }
    
    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    
    public String getHighlighterName() {
        return highlighterName;
    }
    
    public void setHighlighterName(String highlighterName) {
        this.highlighterName = highlighterName;
    }

    public Double getGrossMargin() {
        return grossMargin;
    }

    public void setGrossMargin(Double grossMargin) {
        this.grossMargin = grossMargin;
    }
    
	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getSubStoreName() {
		return subStoreName;
	}

	public void setSubStoreName(String subStoreName) {
		this.subStoreName = subStoreName;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
    
    
}
