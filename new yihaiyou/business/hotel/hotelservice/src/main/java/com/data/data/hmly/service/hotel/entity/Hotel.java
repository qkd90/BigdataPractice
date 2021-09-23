package com.data.data.hmly.service.hotel.entity;


import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.hotel.vo.HotelCommentVo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hotel")
@PrimaryKeyJoinColumn(name = "productId")
public class Hotel extends Product implements java.io.Serializable {

    private static final long serialVersionUID = -3429057633149659471L;
    @Column(name = "score")
    private Float score;
    @Column(name = "star")
    private Integer star;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "targetId")
    private Long targetId;
    @Column(name = "shortDesc")
    private String shortDesc;
    @Column(name = "policy")
    private String policy;
    @Column(name = "cover")
    private String cover;
//    @Column(name = "productId", insertable = false, updatable = false)
//	private Long productId;
    @Column(name = "ranking")
    private Integer ranking;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "brand_id")
    private Integer brandId;
    @Column(name = "facilities")
    private String facilities;
    @Column(name = "general_amenities")
    private String generalAmenities;
    @Column(name = "recreation_amenities")
    private String recreationAmenities;
    @Column(name = "service_amenities")
    private String serviceAmenities;
    @Column(name = "theme")
    private String theme;

    @Column(name = "orderConfirm")
    private String orderConfirm;

    @OneToOne(mappedBy = "hotel", fetch = FetchType.LAZY)
    private HotelExtend extend;
    @OneToMany
//    @JoinColumn(name = "targetId", referencedColumnName = "id") // referencedColumnName会引起跟父类关联时id取值不正确，如需调整请保证“贴标签管理”按标签查询酒店正常
    @JoinColumn(name = "targetId")
    private List<Comment> commentList;
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private List<HotelPrice> hotelPriceList;

    @Column(name = "repeat_flag")
    private Boolean repeatFlag;


    @Transient
    private Long recommendPlanId;
    @Transient
    private String recommendPlanName;
    @Transient
    private String longIntro;
    @Transient
    private String shortIntro;
    @Transient
    private Date qryStartTime;
    @Transient
    private Date qryEndTime;
    @Transient
    private String address;
    @Transient
    private String telephone;
    @Transient
    private String contactName;
    @Transient
    private List<HotelArea> hotelAreas;
    @Transient
    private List<Productimage> productimages;
    @Transient
    private String minPrice;
    @Transient
    private String serviceNames;



    public Hotel() {
    }

    public Hotel(Long id, Long sourceId) {
        this.id = id;
        this.sourceId = sourceId;
    }

    public Hotel(Long id, String name, Integer star, Integer ranking, String address) {

        this.id = id;
        this.name = name;
        this.star = star;
        this.ranking = ranking;
        this.address = address;

    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public HotelExtend getExtend() {
        return extend;
    }

    public void setExtend(HotelExtend extend) {
        this.extend = extend;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

//	public Long getProductId() {
//		return productId;
//	}

    public Long getRecommendPlanId() {
        return recommendPlanId;
    }

    public void setRecommendPlanId(Long recommendPlanId) {
        this.recommendPlanId = recommendPlanId;
    }

    public String getRecommendPlanName() {
        return recommendPlanName;
    }

    public void setRecommendPlanName(String recommendPlanName) {
        this.recommendPlanName = recommendPlanName;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getLongIntro() {
        return longIntro;
    }

    public void setLongIntro(String longIntro) {
        this.longIntro = longIntro;
    }

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getGeneralAmenities() {
        return generalAmenities;
    }

    public void setGeneralAmenities(String generalAmenities) {
        this.generalAmenities = generalAmenities;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getRecreationAmenities() {
        return recreationAmenities;
    }

    public void setRecreationAmenities(String recreationAmenities) {
        this.recreationAmenities = recreationAmenities;
    }

    public String getServiceAmenities() {
        return serviceAmenities;
    }

    public void setServiceAmenities(String serviceAmenities) {
        this.serviceAmenities = serviceAmenities;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<HotelPrice> getHotelPriceList() {
        return hotelPriceList;
    }

    public void setHotelPriceList(List<HotelPrice> hotelPriceList) {
        this.hotelPriceList = hotelPriceList;
    }

    public Date getQryStartTime() {
        return qryStartTime;
    }

    public void setQryStartTime(Date qryStartTime) {
        this.qryStartTime = qryStartTime;
    }

    public Date getQryEndTime() {
        return qryEndTime;
    }

    public void setQryEndTime(Date qryEndTime) {
        this.qryEndTime = qryEndTime;
    }

    public String getOrderConfirm() {
        return orderConfirm;
    }

    public void setOrderConfirm(String orderConfirm) {
        this.orderConfirm = orderConfirm;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public List<HotelArea> getHotelAreas() {
        return hotelAreas;
    }

    public void setHotelAreas(List<HotelArea> hotelAreas) {
        this.hotelAreas = hotelAreas;
    }

    public List<Productimage> getProductimages() {
        return productimages;
    }

    public void setProductimages(List<Productimage> productimages) {
        this.productimages = productimages;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(String serviceNames) {
        this.serviceNames = serviceNames;
    }

    public Boolean getRepeatFlag() {
        return repeatFlag;
    }

    public void setRepeatFlag(Boolean repeatFlag) {
        this.repeatFlag = repeatFlag;
    }
}
