package com.data.data.hmly.service.nctripticket.entity;


import com.data.data.hmly.service.ctripcommon.enums.RowStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2016/1/25.
 */
@Entity
@Table(name = "nctrip_scenic_spot_resource")
public class CtripScenicSpotResource extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 20)
    private Long        id;		          // 由于携程存在不同景点门票资源ID相同的数据
    @Column(name = "ctripResourceId", length = 20)
    private Long        ctripResourceId;		          // 门票资源ID
    @Column(name = "productId", length = 20)
    private Long        productId;		  // 门票产品ID
    @Column(name = "scenicSpotId", length = 20)
    private Long        scenicSpotId;	        //  同景点ID
    @Column(name = "name", length = 256)
    private String      name;		          // 门票资源名称
    @Column(name = "marketPrice", length = 20, precision = 2)
    private Double      marketPrice;		  // 门票资源市场价
    @Column(name = "price", length = 20, precision = 2)
    private Double      price;		          // 分销价
    @Column(name = "ctripPrice", length = 20, precision = 2)
    private Double      ctripPrice;		  // 门票资源携程卖价
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "firstBooking", length = 19)
    private Date        firstBooking;	  // 最早可预订日期
    @Column(name = "minQuantity", length = 11)
    private Integer     minQuantity;	      // 可选最小份数
    @Column(name = "maxQuantity", length = 11)
    private Integer     maxQuantity;	      // 可选最大份数
    @Column(name = "ticketType", length = 11)
    private Integer     ticketType;		  // 门票资源类型，值可能有：0，其他。2.单票。4.套票
    @Column(name = "isHighRisk")
    private Boolean     isHighRisk;		  // 是否高风险
    @Column(name = "exchangeMode", length = 8)
    private String      exchangeMode;		  // 兑换方式，值可能有：1，有效证件。2，确认单。3，短信。4，二维码。5，实物票，6，陪同单
    @Column(name = "payMode", length = 8)
    private String      payMode;		      // 支付方式(O-现付;P-预付)
    @Column(name = "advanceBookingDays", length = 11)
    private Integer     advanceBookingDays; // 提前预订天数
    @Column(name = "advanceBookingTime", length = 256)
    private String      advanceBookingTime; // 提前预定时间
    @Column(name = "categoryId", length = 11)
    private Integer     categoryId;		  // 类型ID：4景点门票;36高尔夫球票;38一日游;40园内餐饮票;41园内交通票;42园内演出票;43园内其他票;44联票
    @Column(name = "peopleGroup", length = 8)
    private String      peopleGroup;		  // 适用人群(成人:1 儿童:2 学生:4 老人:8 其他:16 家庭:32)
    @Column(name = "unitQuantity", length = 11)
    private Integer     unitQuantity;		  // 单位人数（人/份）
    @Column(name = "saleTag", length = 256)
    private String      saleTag;		      // 营销标签
    @Column(name = "isBookingLimit")
    private Boolean     isBookingLimit;	  //	是否限购
    @Column(name = "refundNewType", length = 11)
    private Integer     refundNewType;	  //	退订类型；1.随时退，2.非随时退，3.不可退
    @Column(name = "isPreferential")
    private Boolean     isPreferential;	  //	是否特惠票
    @Column(name = "isSaleAlone")
    private Boolean     isSaleAlone;	      //	是否单独售卖
    @Column(name = "customerInfoTemplateId", length = 20)
    private Long        customerInfoTemplateId;	//	出行人信息模板ID
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;
    @Column(name = "rowStatus")
    @Enumerated(EnumType.STRING)
    private RowStatus rowStatus;

    @Transient
    private String        firstBookingDate;	  // 最早可预订日期
    @Transient
    private List<CtripDisplayTagGroup> displayTagGroupList;		//	展示tag分组
    @Transient
    private List<CtripResourceAddInfo> resourceAddInfoList;    //	门票附加信息列表

    public CtripScenicSpotResource() {
    }

    public CtripScenicSpotResource(Long id, Long ctripResourceId, Long productId) {
        this.id = id;
        this.ctripResourceId = ctripResourceId;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCtripPrice() {
        return ctripPrice;
    }

    public void setCtripPrice(Double ctripPrice) {
        this.ctripPrice = ctripPrice;
    }

    public Date getFirstBooking() {
        return firstBooking;
    }

    public void setFirstBooking(Date firstBooking) {
        this.firstBooking = firstBooking;
    }

    public String getFirstBookingDate() {
        return firstBookingDate;
    }

    public void setFirstBookingDate(String firstBookingDate) {
        this.firstBookingDate = firstBookingDate;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Integer getTicketType() {
        return ticketType;
    }

    public void setTicketType(Integer ticketType) {
        this.ticketType = ticketType;
    }

    public Boolean getIsHighRisk() {
        return isHighRisk;
    }

    public void setIsHighRisk(Boolean isHighRisk) {
        this.isHighRisk = isHighRisk;
    }

    public String getExchangeMode() {
        return exchangeMode;
    }

    public void setExchangeMode(String exchangeMode) {
        this.exchangeMode = exchangeMode;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public Integer getAdvanceBookingDays() {
        return advanceBookingDays;
    }

    public void setAdvanceBookingDays(Integer advanceBookingDays) {
        this.advanceBookingDays = advanceBookingDays;
    }

    public String getAdvanceBookingTime() {
        return advanceBookingTime;
    }

    public void setAdvanceBookingTime(String advanceBookingTime) {
        this.advanceBookingTime = advanceBookingTime;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getPeopleGroup() {
        return peopleGroup;
    }

    public void setPeopleGroup(String peopleGroup) {
        this.peopleGroup = peopleGroup;
    }

    public Integer getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(Integer unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public String getSaleTag() {
        return saleTag;
    }

    public void setSaleTag(String saleTag) {
        this.saleTag = saleTag;
    }

    public Boolean getIsBookingLimit() {
        return isBookingLimit;
    }

    public void setIsBookingLimit(Boolean isBookingLimit) {
        this.isBookingLimit = isBookingLimit;
    }

    public Integer getRefundNewType() {
        return refundNewType;
    }

    public void setRefundNewType(Integer refundNewType) {
        this.refundNewType = refundNewType;
    }

    public Boolean getIsPreferential() {
        return isPreferential;
    }

    public void setIsPreferential(Boolean isPreferential) {
        this.isPreferential = isPreferential;
    }

    public Boolean getIsSaleAlone() {
        return isSaleAlone;
    }

    public void setIsSaleAlone(Boolean isSaleAlone) {
        this.isSaleAlone = isSaleAlone;
    }

    public Long getCustomerInfoTemplateId() {
        return customerInfoTemplateId;
    }

    public void setCustomerInfoTemplateId(Long customerInfoTemplateId) {
        this.customerInfoTemplateId = customerInfoTemplateId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public RowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }

    public List<CtripDisplayTagGroup> getDisplayTagGroupList() {
        return displayTagGroupList;
    }

    public void setDisplayTagGroupList(List<CtripDisplayTagGroup> displayTagGroupList) {
        this.displayTagGroupList = displayTagGroupList;
    }

    public List<CtripResourceAddInfo> getResourceAddInfoList() {
        return resourceAddInfoList;
    }

    public void setResourceAddInfoList(List<CtripResourceAddInfo> resourceAddInfoList) {
        this.resourceAddInfoList = resourceAddInfoList;
    }

    public Long getScenicSpotId() {
        return scenicSpotId;
    }

    public void setScenicSpotId(Long scenicSpotId) {
        this.scenicSpotId = scenicSpotId;
    }

    public Long getCtripResourceId() {
        return ctripResourceId;
    }

    public void setCtripResourceId(Long ctripResourceId) {
        this.ctripResourceId = ctripResourceId;
    }
}
