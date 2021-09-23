package com.data.data.hmly.service.plan.entity;


import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.vo.LabelItemsVo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zuipin.util.DateUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

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
import java.util.Set;

@Entity
@Table(name = "recommend_plan")
public class RecommendPlan extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Member user;
	@Column(name = "scenic_id")
	private Long scenicId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private TbArea city;
	@Column(name = "city_ids")
	private String cityIds;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "province")
	private TbArea province;
	@Column(name = "share_num")
	private Integer shareNum;
	@Column(name = "quote_num")
	private Integer quoteNum;
	@Column(name = "collect_num")
	private Integer collectNum;
	@Column(name = "view_num")
	private Integer viewNum;
	@Column(name = "plan_name")
	private String planName;
	@Column(name = "description")
	private String description;
	@Column(name = "cover_path")
	private String coverPath;
	@Column(name = "cover_small")
	private String coverSmall;
	@Column(name = "phone_cover_path")
	private String phoneCoverPath;
	@Column(name = "phone_cover_small")
	private String phoneCoverSmall;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "modify_time")
	private Date modifyTime;
    @Column(name = "start_time")
    private Date startTime;
	@Column(name = "days")
	private Integer days;
	@Column(name = "scenics")
	private Integer scenics;
    @Column(name = "pass_scenics")
    private String passScenics;
	@Column(name = "hotel_cost")
	private Float hotelCost;
	@Column(name = "travel_cost")
	private Float travelCost;
	@Column(name = "valid")
	private Integer valid;
	@Column(name = "tags")
	private String tags;
	@Column(name = "weight")
	private Integer weight;
	@Column(name = "selected")
	private Integer selected;
	@Column(name = "cost")
	private Float cost;
	@Column(name = "include_seasonticket_cost")
	private Float includeSeasonticketCost;
	@Column(name = "ticket_cost")
	private Float ticketCost;
	@Column(name = "seasonticket_cost")
	private Float seasonticketCost;
	@Column(name = "rec_loc")
	private Integer recLoc;
	@Column(name = "status")
	private Integer status;
	@Column(name = "best_time")
	private String bestTime;
	@Column(name = "adv_clothes")
	private String advClothes;
	@Column(name = "necessity")
	private String necessity;
	@Column(name = "attention")
	private String attention;
	@Column(name = "delete_flag")
	private Integer deleteFlag;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private User createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by")
	private User updatedBy;
	@Column(name = "food_cost")
	private Float foodCost;
	@Column(name = "mark")
	private Integer mark;
	@Column(name = "data_source")
	private String dataSource;
	@Column(name = "data_source_id")
	private String dataSourceId;
	@Column(name = "data_source_code")
	private String dataSourceCode;
	@Column(name = "tagStr")
	private String tagStr;
	@OneToMany(mappedBy = "recommendPlan", fetch = FetchType.LAZY)
	private List<RecommendPlanDay> recommendPlanDays;
	@Column(name = "show_index")
	private Boolean showIndex;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "targetId", referencedColumnName = "id")
	@Where(clause = "targetType='RECOMMEND_PLAN'")
	protected Set<LabelItem> labelItems;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "targetId", referencedColumnName = "id")
	@Where(clause = "type='recplan' and status='NORMAL'")
	private List<Comment> recommendPlanCommentList;
	@Column(name = "line_id")
	private Long lineId;

//    @Transient
//    private Integer picNum;

    @Transient
    @JsonIgnoreProperties
    private Long startId;
    @Transient
    @JsonIgnoreProperties
    private Long endId;

    @Transient
    private Set<LabelItemsVo> labelItemsVos;

	public Long getScenicId() {
		return scenicId;
	}

	public void setScenicId(Long scenicId) {
		this.scenicId = scenicId;
	}

	public TbArea getCity() {
		return city;
	}

	public void setCity(TbArea city) {
		this.city = city;
	}

	public String getCityIds() {
		return cityIds;
	}

	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}

	public TbArea getProvince() {
		return province;
	}

	public void setProvince(TbArea province) {
		this.province = province;
	}

	public Integer getShareNum() {
		return shareNum;
	}

	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}

	public Integer getQuoteNum() {
		return quoteNum;
	}

	public void setQuoteNum(Integer quoteNum) {
		this.quoteNum = quoteNum;
	}

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public String getCoverSmall() {
		return coverSmall;
	}

	public void setCoverSmall(String coverSmall) {
		this.coverSmall = coverSmall;
	}

	public String getPhoneCoverPath() {
		return phoneCoverPath;
	}

	public void setPhoneCoverPath(String phoneCoverPath) {
		this.phoneCoverPath = phoneCoverPath;
	}

	public String getPhoneCoverSmall() {
		return phoneCoverSmall;
	}

	public void setPhoneCoverSmall(String phoneCoverSmall) {
		this.phoneCoverSmall = phoneCoverSmall;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getScenics() {
		return scenics;
	}

    public String getPassScenics() {
        return passScenics;
    }

    public void setPassScenics(String passScenics) {
        this.passScenics = passScenics;
    }

    public void setScenics(Integer scenics) {
		this.scenics = scenics;
	}

	public Float getHotelCost() {
		return hotelCost;
	}

	public void setHotelCost(Float hotelCost) {
		this.hotelCost = hotelCost;
	}

	public Float getTravelCost() {
		return travelCost;
	}

	public void setTravelCost(Float travelCost) {
		this.travelCost = travelCost;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getSelected() {
		return selected;
	}

	public void setSelected(Integer selected) {
		this.selected = selected;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public Float getIncludeSeasonticketCost() {
		return includeSeasonticketCost;
	}

	public void setIncludeSeasonticketCost(Float includeSeasonticketCost) {
		this.includeSeasonticketCost = includeSeasonticketCost;
	}

	public Float getTicketCost() {
		return ticketCost;
	}

	public void setTicketCost(Float ticketCost) {
		this.ticketCost = ticketCost;
	}

	public Float getSeasonticketCost() {
		return seasonticketCost;
	}

	public void setSeasonticketCost(Float seasonticketCost) {
		this.seasonticketCost = seasonticketCost;
	}

	public Integer getRecLoc() {
		return recLoc;
	}

	public void setRecLoc(Integer recLoc) {
		this.recLoc = recLoc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBestTime() {
		return bestTime;
	}

	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}

	public String getAdvClothes() {
		return advClothes;
	}

	public void setAdvClothes(String advClothes) {
		this.advClothes = advClothes;
	}

	public String getNecessity() {
		return necessity;
	}

	public void setNecessity(String necessity) {
		this.necessity = necessity;
	}

	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Float getFoodCost() {
		return foodCost;
	}

	public void setFoodCost(Float foodCost) {
		this.foodCost = foodCost;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getDataSourceCode() {
		return dataSourceCode;
	}

	public void setDataSourceCode(String dataSourceCode) {
		this.dataSourceCode = dataSourceCode;
	}

	public String getTagStr() {
		return tagStr;
	}

	public void setTagStr(String tagStr) {
		this.tagStr = tagStr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    public List<RecommendPlanDay> getRecommendPlanDays() {
		return recommendPlanDays;
	}

	public void setRecommendPlanDays(List<RecommendPlanDay> recommendPlanDays) {
		this.recommendPlanDays = recommendPlanDays;
	}

	public Boolean getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Boolean showIndex) {
		this.showIndex = showIndex;
	}

	public Set<LabelItem> getLabelItems() {
		return labelItems;
	}

	public void setLabelItems(Set<LabelItem> labelItems) {
		this.labelItems = labelItems;
	}

	public List<Comment> getRecommendPlanCommentList() {
		return recommendPlanCommentList;
	}

	public void setRecommendPlanCommentList(List<Comment> recommendPlanCommentList) {
		this.recommendPlanCommentList = recommendPlanCommentList;
	}

	public Integer getViewNum() {
		return viewNum;
	}

	public void setViewNum(Integer viewNum) {
		this.viewNum = viewNum;
	}

    public Long getStartId() {
        return startId;
    }

    public void setStartId(Long startId) {
        this.startId = startId;
    }

    public Long getEndId() {
        return endId;
    }

    public void setEndId(Long endId) {
        this.endId = endId;
    }

    public Set<LabelItemsVo> getLabelItemsVos() {
        return labelItemsVos;
    }

    public void setLabelItemsVos(Set<LabelItemsVo> labelItemsVos) {
        this.labelItemsVos = labelItemsVos;
    }

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public String getStartTimeStr() {
		if (startTime != null) {
			return DateUtils.format(startTime, "yyyy/MM/dd");
		}
        return "";
    }
}
