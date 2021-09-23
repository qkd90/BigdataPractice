package com.data.data.hmly.service.nctripticket.entity;

import com.data.data.hmly.service.ctripcommon.enums.RowStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "nctrip_scenic_spot_info")
public class CtripScenicSpotInfo extends com.framework.hibernate.util.Entity {
    @Id
    @Column(name = "id", length = 20)
    private Long    id;	        //  景点ID
    @Column(name = "name", length = 256)
    private String  name;		    //	景点名称
    @Column(name = "address", length = 1024)
    private String  address;		//	景点地址
    @Column(name = "commentGrade", length = 20, precision = 2)
    private Double  commentGrade;	//  点评分
    @Column(name = "pmRecommand", length = 512)
    private String  pmRecommand;  //	产品经理推荐
    @Column(name = "star", length = 11)
    private Integer star;		    //	景区星级
    @Column(name = "marketPrice", length = 20, precision = 2)
    private Double  marketPrice;	//	市场价
    @Column(name = "price", length = 20, precision = 2)
    private Double  price;		    //	分销价格
    @Column(name = "ctripPrice", length = 20, precision = 2)
    private Double  ctripPrice;	//	携程卖价
    @Column(name = "imageUrl", length = 256)
    private String  imageUrl;		//	图片，目前取主产品第一张封面图片，若主产品无图数据，此字段也无数据
    @Column(name = "url", length = 256)
    private String  url;		    //	景点对应URL
    @Column(name = "openTimeDesc", length = 1024)
    private String  openTimeDesc;	//	营业时间
    @Column(name = "isCanBooking")
    private Boolean isCanBooking;	//	景点是否可订
    @Column(name = "saleTag", length = 256)
    private String  saleTag;		//	营销标签
    @Column(name = "longitude", length = 20, precision = 2)
    private Double  longitude;	//	经度
    @Column(name = "latitude", length = 20, precision = 2)
    private Double  latitude;     //	纬度
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
    private List<CtripDisplayTagGroup> displayTagGroupList;		// 展示tag分组
    @Transient
    private CtripScenicSpotPoiInfo		poiInfo;	// PIO信息
    @Transient
    private CtripScenicSpotCityInfo		cityInfo;	// 城市信息
    @Transient
    private CtripScenicSpotProduct		productInfo;	// 门票产品信息
    @Transient
    private List<CtripScenicSpotResource> resourceList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CtripScenicSpotResource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<CtripScenicSpotResource> resourceList) {
        this.resourceList = resourceList;
    }

    public Boolean getCanBooking() {
        return isCanBooking;
    }

    public void setCanBooking(Boolean canBooking) {
        isCanBooking = canBooking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getCommentGrade() {
        return commentGrade;
    }

    public void setCommentGrade(Double commentGrade) {
        this.commentGrade = commentGrade;
    }

    public String getPmRecommand() {
        return pmRecommand;
    }

    public void setPmRecommand(String pmRecommand) {
        this.pmRecommand = pmRecommand;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOpenTimeDesc() {
        return openTimeDesc;
    }

    public void setOpenTimeDesc(String openTimeDesc) {
        this.openTimeDesc = openTimeDesc;
    }

    public Boolean getIsCanBooking() {
        return isCanBooking;
    }

    public void setIsCanBooking(Boolean isCanBooking) {
        this.isCanBooking = isCanBooking;
    }

    public String getSaleTag() {
        return saleTag;
    }

    public void setSaleTag(String saleTag) {
        this.saleTag = saleTag;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public List<CtripDisplayTagGroup> getDisplayTagGroupList() {
        return displayTagGroupList;
    }

    public void setDisplayTagGroupList(List<CtripDisplayTagGroup> displayTagGroupList) {
        this.displayTagGroupList = displayTagGroupList;
    }

    public CtripScenicSpotPoiInfo getPoiInfo() {
        return poiInfo;
    }

    public void setPoiInfo(CtripScenicSpotPoiInfo poiInfo) {
        this.poiInfo = poiInfo;
    }

    public CtripScenicSpotCityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CtripScenicSpotCityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public CtripScenicSpotProduct getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(CtripScenicSpotProduct productInfo) {
        this.productInfo = productInfo;
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
}
