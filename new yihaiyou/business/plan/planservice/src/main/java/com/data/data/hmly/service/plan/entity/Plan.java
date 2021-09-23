package com.data.data.hmly.service.plan.entity;

import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "plan")
public class Plan extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final Long serialVersionUID = 1L;

    public static final int STATUS_VALID = 1;
    public static final int STATUS_INVALID = 0;

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
	private TbArea city;
	@Column(name = "city_ids")
	private String cityIds;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "province")
	private TbArea province;
	@Column(name = "name")
	private String name;
    @Column(name = "sub_title")
    private String subTitle;
	@Column(name = "tips")
	private String tips;
	@Column(name = "start_time")
	private Date startTime;
	@Column(name = "return_time")
	private Date returnTime;
	@Column(name = "public_flag")
	private Boolean publicFlag;
	@Column(name = "recommend_flag")
	private Boolean recommendFlag;
    @Column(name = "rec_reason")
    private String recReason;
	@Column(name = "cover_path")
	private String coverPath;
	@Column(name = "cover_small")
	private String coverSmall;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "modify_time")
	private Date modifyTime;
	@Column(name = "scenic_order")
	private String scenicOrder;
	@Column(name = "plan_days")
	private Integer planDays;
	@Column(name = "status")
	private Integer status;
	@Column(name = "plan_citys")
	private String planCitys;
	@Column(name = "id_backup")
	private Long idBackup;
	@Column(name = "valid")
	private Boolean valid;
	@Column(name = "source")
	private Integer source;
	@Column(name = "source_id")
	private Long sourceId;
	@Column(name = "tag")
	private Integer tag;
	@Column(name = "description")
	private String description;
	@Column(name = "platform")
	private Integer platform;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "start_city")
	private TbArea startCity;
	@Column(name = "push_flag")
	private Integer pushFlag;
	@Column(name = "pass_scenics")
	private String passScenics;
	@Column(name = "complete_flag")
	private Boolean completeFlag;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "targetId", referencedColumnName = "id")
	@Where(clause = "targetType='PLAN'")
	protected Set<LabelItem> labelItems;
	@OneToOne(mappedBy = "plan", fetch = FetchType.LAZY)
	private PlanStatistic planStatistic;
	@OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
	private List<PlanDay> planDayList;

	@Transient
	private Float price;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

    public void setPublicFlag(Boolean publicFlag) {
        this.publicFlag = publicFlag;
    }

    public Boolean getPublicFlag() {
        return publicFlag;
    }

    public Boolean getRecommendFlag() {
        return recommendFlag;
    }

    public void setRecommendFlag(Boolean recommendFlag) {
        this.recommendFlag = recommendFlag;
    }

    public String getRecReason() {
        return recReason;
    }

    public void setRecReason(String recReason) {
        this.recReason = recReason;
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

	public String getScenicOrder() {
		return scenicOrder;
	}

	public void setScenicOrder(String scenicOrder) {
		this.scenicOrder = scenicOrder;
	}

	public Integer getPlanDays() {
		return planDays;
	}

	public void setPlanDays(Integer planDays) {
		this.planDays = planDays;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPlanCitys() {
		return planCitys;
	}

	public void setPlanCitys(String planCitys) {
		this.planCitys = planCitys;
	}

	public Long getIdBackup() {
		return idBackup;
	}

	public void setIdBackup(Long idBackup) {
		this.idBackup = idBackup;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

    public TbArea getStartCity() {
        return startCity;
    }

    public void setStartCity(TbArea startCity) {
        this.startCity = startCity;
    }

    public Integer getPushFlag() {
		return pushFlag;
	}

	public void setPushFlag(Integer pushFlag) {
		this.pushFlag = pushFlag;
	}

	public PlanStatistic getPlanStatistic() {
		return planStatistic;
	}

	public void setPlanStatistic(PlanStatistic planStatistic) {
		this.planStatistic = planStatistic;
	}

	public List<PlanDay> getPlanDayList() {
		return planDayList;
	}

	public void setPlanDayList(List<PlanDay> planDayList) {
		this.planDayList = planDayList;
	}

	public String getPassScenics() {
		return passScenics;
	}

	public void setPassScenics(String passScenics) {
		this.passScenics = passScenics;
	}

	public Boolean getCompleteFlag() {
		return completeFlag;
	}

	public void setCompleteFlag(Boolean completeFlag) {
		this.completeFlag = completeFlag;
	}

	public Set<LabelItem> getLabelItems() {
		return labelItems;
	}

	public void setLabelItems(Set<LabelItem> labelItems) {
		this.labelItems = labelItems;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
