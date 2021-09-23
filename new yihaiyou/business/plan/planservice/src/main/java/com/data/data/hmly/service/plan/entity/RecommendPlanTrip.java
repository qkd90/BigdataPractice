package com.data.data.hmly.service.plan.entity;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.plan.vo.SimpleRecommendPlanPhoto;
import com.data.data.hmly.service.restaurant.entity.Delicacy;

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
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recommend_plan_trip")
public class RecommendPlanTrip extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recplan_id")
	private RecommendPlan recommendPlan;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recday_id")
	private RecommendPlanDay recommendPlanDay;
    @Column(name = "scenic_id")
    private Long scenicId;
    @Column(name = "traffic_type")
	private Integer trafficType;
	@Column(name = "trip_type")
	private Integer tripType;
	@Column(name = "start_time")
	private Date startTime;
	@Column(name = "order_num")
	private Integer orderNum;
	@Column(name = "trip_desc")
	private String tripDesc;
	@Column(name = "modify_time")
	private Date modifyTime;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "taxi_cost")
	private Float taxiCost;
	@Column(name = "taxi_detail")
	private String taxiDetail;
	@Column(name = "taxi_time")
	private String taxiTime;
	@Column(name = "taxi_hour")
	private Integer taxiHour;
	@Column(name = "taxi_milleage")
	private String taxiMilleage;
	@Column(name = "walk_detail")
	private String walkDetail;
	@Column(name = "walk_time")
	private String walkTime;
	@Column(name = "walk_hour")
	private Integer walkHour;
	@Column(name = "walk_milleage")
	private String walkMilleage;
	@Column(name = "bus_cost")
	private Float busCost;
	@Column(name = "bus_detail")
	private String busDetail;
	@Column(name = "bus_time")
	private String busTime;
	@Column(name = "bus_hour")
	private Integer busHour;
	@Column(name = "bus_milleage")
	private String busMilleage;
	@Column(name = "exa")
	private String exa;
	@Column(name = "line_dis")
	private Integer lineDis;
	@Column(name = "old_price")
	private Float oldPrice;
	@Column(name = "user_price")
	private Float userPrice;
	@Column(name = "advice_hours")
	private Integer adviceHours;
	@Column(name = "city_code")
	private String cityCode;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delicacy_id")
	private Delicacy delicacy;
	@Column(name = "scenic_name")
	private String scenicName;
	@Column(name = "cover_img")
	private String coverImg;
	@Column(name = "img_width")
	private Integer imgWidth;
	@Column(name = "img_height")
	private Integer imgHeight;
	@Column(name = "sort")
	private Integer sort;
	@Column(name = "data_source")
	private String dataSource;
	@Column(name = "data_source_id")
	private Integer dataSourceId;
	@Column(name = "data_source_type")
	private String dataSourceType;

    @OneToMany(mappedBy = "recommendPlanTrip", fetch = FetchType.LAZY)
    private List<RecommendPlanPhoto> recommendPlanPhotos;
    @Transient
    private Integer score;
    @Transient
    private List<SimpleRecommendPlanPhoto> photos;
    @Transient
    private Long recplanId;
    @Transient
    private Long recdayId;
    @Transient
    private Long rectripId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RecommendPlan getRecommendPlan() {
		return recommendPlan;
	}

	public void setRecommendPlan(RecommendPlan recommendPlan) {
		this.recommendPlan = recommendPlan;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RecommendPlanDay getRecommendPlanDay() {
		return recommendPlanDay;
	}

	public void setRecommendPlanDay(RecommendPlanDay recommendPlanDay) {
		this.recommendPlanDay = recommendPlanDay;
	}

    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    public Integer getTrafficType() {
		return trafficType;
	}

	public void setTrafficType(Integer trafficType) {
		this.trafficType = trafficType;
	}

	public Integer getTripType() {
		return tripType;
	}

	public void setTripType(Integer tripType) {
		this.tripType = tripType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getTripDesc() {
		return tripDesc;
	}

	public void setTripDesc(String tripDesc) {
		this.tripDesc = tripDesc;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getTaxiCost() {
		return taxiCost;
	}

	public void setTaxiCost(Float taxiCost) {
		this.taxiCost = taxiCost;
	}

	public String getTaxiDetail() {
		return taxiDetail;
	}

	public void setTaxiDetail(String taxiDetail) {
		this.taxiDetail = taxiDetail;
	}

	public String getTaxiTime() {
		return taxiTime;
	}

	public void setTaxiTime(String taxiTime) {
		this.taxiTime = taxiTime;
	}

	public Integer getTaxiHour() {
		return taxiHour;
	}

	public void setTaxiHour(Integer taxiHour) {
		this.taxiHour = taxiHour;
	}

	public String getTaxiMilleage() {
		return taxiMilleage;
	}

	public void setTaxiMilleage(String taxiMilleage) {
		this.taxiMilleage = taxiMilleage;
	}

	public String getWalkDetail() {
		return walkDetail;
	}

	public void setWalkDetail(String walkDetail) {
		this.walkDetail = walkDetail;
	}

	public String getWalkTime() {
		return walkTime;
	}

	public void setWalkTime(String walkTime) {
		this.walkTime = walkTime;
	}

	public Integer getWalkHour() {
		return walkHour;
	}

	public void setWalkHour(Integer walkHour) {
		this.walkHour = walkHour;
	}

	public String getWalkMilleage() {
		return walkMilleage;
	}

	public void setWalkMilleage(String walkMilleage) {
		this.walkMilleage = walkMilleage;
	}

	public Float getBusCost() {
		return busCost;
	}

	public void setBusCost(Float busCost) {
		this.busCost = busCost;
	}

	public String getBusDetail() {
		return busDetail;
	}

	public void setBusDetail(String busDetail) {
		this.busDetail = busDetail;
	}

	public String getBusTime() {
		return busTime;
	}

	public void setBusTime(String busTime) {
		this.busTime = busTime;
	}

	public Integer getBusHour() {
		return busHour;
	}

	public void setBusHour(Integer busHour) {
		this.busHour = busHour;
	}

	public String getBusMilleage() {
		return busMilleage;
	}

	public void setBusMilleage(String busMilleage) {
		this.busMilleage = busMilleage;
	}

	public String getExa() {
		return exa;
	}

	public void setExa(String exa) {
		this.exa = exa;
	}

	public Integer getLineDis() {
		return lineDis;
	}

	public void setLineDis(Integer lineDis) {
		this.lineDis = lineDis;
	}

	public Float getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Float oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Float getUserPrice() {
		return userPrice;
	}

	public void setUserPrice(Float userPrice) {
		this.userPrice = userPrice;
	}

	public Integer getAdviceHours() {
		return adviceHours;
	}

	public void setAdviceHours(Integer adviceHours) {
		this.adviceHours = adviceHours;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Delicacy getDelicacy() {
		return delicacy;
	}

	public void setDelicacy(Delicacy delicacy) {
		this.delicacy = delicacy;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public Integer getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(Integer imgWidth) {
		this.imgWidth = imgWidth;
	}

	public Integer getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(Integer imgHeight) {
		this.imgHeight = imgHeight;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

    public List<RecommendPlanPhoto> getRecommendPlanPhotos() {
        return recommendPlanPhotos;
    }

    public void setRecommendPlanPhotos(List<RecommendPlanPhoto> recommendPlanPhotos) {
        this.recommendPlanPhotos = recommendPlanPhotos;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<SimpleRecommendPlanPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<SimpleRecommendPlanPhoto> photos) {
        this.photos = photos;
    }

    public Long getRecplanId() {
        return recplanId;
    }

    public void setRecplanId(Long recplanId) {
        this.recplanId = recplanId;
    }

    public Long getRecdayId() {
        return recdayId;
    }

    public void setRecdayId(Long recdayId) {
        this.recdayId = recdayId;
    }

    public Long getRectripId() {
        return rectripId;
    }

    public void setRectripId(Long rectripId) {
        this.rectripId = rectripId;
    }
}
