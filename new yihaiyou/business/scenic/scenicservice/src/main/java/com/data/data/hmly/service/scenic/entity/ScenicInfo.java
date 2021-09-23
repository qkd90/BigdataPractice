package com.data.data.hmly.service.scenic.entity;

import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.data.data.hmly.service.scenic.vo.ScenicCommentVo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "scenic")
@DynamicUpdate
@DynamicInsert
public class ScenicInfo extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "star")
    private Integer star;
    @Column(name = "level")
    private String level;
    @Column(name = "score")
    private Integer score;
    @Column(name = "ranking")
    private Integer ranking;
    @Column(name = "price")
    private Float price;
    @Column(name = "ticket")
    private String ticket;
    @Column(name = "intro")
    private String intro;
    @Column(name = "cover")
    private String cover;
    @ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "father", nullable = false)
    @JoinColumn(name = "father")
    private ScenicInfo father;
    @Column(name = "is_three_level")
    private Boolean isThreeLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_code", nullable = false, insertable = false, updatable = false)
    private TbArea city;
    @Column(name = "city_code")
    private String cityCode; //设置这个值，city可以不用赋值，此属性智能规划使用
    @Column(name = "city_id")
    private Integer cityId;
    @Column(name = "is_show")
    private Boolean isShow;
    @Column(name = "status")
    private Integer status;
    @Column(name = "has_taxi")
    private Boolean hasTaxi;
    @Column(name = "has_bus")
    private Boolean hasBus;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
    @Column(name = "scenic_type")
    @Enumerated(EnumType.STRING)
    private ScenicInfoType scenicType;


    @ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "created_by", nullable = false)
    @JoinColumn(name = "created_by")
    private User createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "modified_by", nullable = false)
    @JoinColumn(name = "modified_by")
    private User modifiedBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_unit_id")
    private SysUnit companyUnit;

    @Column(name = "ctrip_scenic_id")
    private Integer ctripScenicId;
    @Transient
    private String longIntro;
    @Transient
    private String shortIntro;

    //    @OneToOne(mappedBy = "scenicInfo", fetch = FetchType.LAZY, optional = false)
    @OneToOne(mappedBy = "scenicInfo", fetch = FetchType.LAZY, optional = true)
    private ScenicOther scenicOther;
    @OneToOne(mappedBy = "scenicInfo", fetch = FetchType.LAZY, optional = true)
    private ScenicStatistics scenicStatistics;
    //    @OneToOne(mappedBy = "scenicInfo", fetch = FetchType.LAZY)
//    private ScenicArea scenicArea;
    @OneToOne(mappedBy = "scenicInfo", fetch = FetchType.LAZY, optional = true)
    private ScenicGeoinfo scenicGeoinfo;

    @OneToMany(mappedBy = "scenicInfo", fetch = FetchType.LAZY)
    private List<ScenicArea> scenicAreas;

    @OneToMany(mappedBy = "scenicInfo", fetch = FetchType.LAZY)
    private List<ScenicGallery> scenicGalleryList;

    @OneToMany(mappedBy = "scenicInfo", fetch = FetchType.LAZY)
    private List<ScenicThemeRelation> scenicThemeRelations;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetId", referencedColumnName = "id")
    @Where(clause = "targetType='SCENIC'")
    protected Set<LabelItem> labelItems;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetId", referencedColumnName = "id")
    @Where(clause = "type='scenic'")
    private List<Comment> scenicCommentList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenicInfoId", referencedColumnName = "id")
//    @Where(clause = "type='scenic'")
    private List<Ticket> ticketList;

    @Transient
    private Integer ticketNum;

    @Transient
    @JsonIgnoreProperties
    private Long startId;
    @Transient
    @JsonIgnoreProperties
    private Long endId;
    @Transient
    private String address;
    @Transient
    private Long cityIdLong;
    @Transient
    private String minPrice;

    public ScenicInfo() {

    }

    public ScenicInfo(Long id, String name, Integer score) {
        super();
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public ScenicInfo(Long id, Integer score) {
        super();
        this.id = id;
        this.score = score;
    }

    public ScenicInfo(Long id, String name, Long cityIdLong, Integer star, String address) {
        this.id = id;
        this.name = name;
        this.star = star;
        this.address = address;
        this.cityIdLong = cityIdLong;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public ScenicInfo getFather() {
        return father;
    }

    public void setFather(ScenicInfo father) {
        this.father = father;
    }

    public Boolean getIsThreeLevel() {
        return isThreeLevel;
    }

    public void setIsThreeLevel(Boolean isThreeLevel) {
        this.isThreeLevel = isThreeLevel;
    }

    public TbArea getCity() {
        return city;
    }

    public void setCity(TbArea city) {
        this.city = city;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getHasTaxi() {
        return hasTaxi;
    }

    public void setHasTaxi(Boolean hasTaxi) {
        this.hasTaxi = hasTaxi;
    }

    public Boolean getHasBus() {
        return hasBus;
    }

    public void setHasBus(Boolean hasBus) {
        this.hasBus = hasBus;
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

    public ScenicInfoType getScenicType() {
        return scenicType;
    }

    public void setScenicType(ScenicInfoType scenicType) {
        this.scenicType = scenicType;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public ScenicOther getScenicOther() {
        return scenicOther;
    }

    public void setScenicOther(ScenicOther scenicOther) {
        this.scenicOther = scenicOther;
    }

    public ScenicStatistics getScenicStatistics() {
        return scenicStatistics;
    }

    public void setScenicStatistics(ScenicStatistics scenicStatistics) {
        this.scenicStatistics = scenicStatistics;
    }

    public List<ScenicGallery> getScenicGalleryList() {
        return scenicGalleryList;
    }

    public void setScenicGalleryList(List<ScenicGallery> scenicGalleryList) {
        this.scenicGalleryList = scenicGalleryList;
    }

    public ScenicGeoinfo getScenicGeoinfo() {
        return scenicGeoinfo;
    }

    public void setScenicGeoinfo(ScenicGeoinfo scenicGeoinfo) {
        this.scenicGeoinfo = scenicGeoinfo;
    }

    public List<Comment> getScenicCommentList() {
        return scenicCommentList;
    }

    public void setScenicCommentList(List<Comment> scenicCommentList) {
        this.scenicCommentList = scenicCommentList;
    }

    public List<ScenicThemeRelation> getScenicThemeRelations() {
        return scenicThemeRelations;
    }

    public void setScenicThemeRelations(List<ScenicThemeRelation> scenicThemeRelations) {
        this.scenicThemeRelations = scenicThemeRelations;
    }

    public Set<LabelItem> getLabelItems() {
        return labelItems;
    }

    public void setLabelItems(Set<LabelItem> labelItems) {
        this.labelItems = labelItems;
    }

    public SysUnit getCompanyUnit() {
        return companyUnit;
    }

    public void setCompanyUnit(SysUnit companyUnit) {
        this.companyUnit = companyUnit;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
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

    public Integer getCtripScenicId() {
        return ctripScenicId;
    }

    public void setCtripScenicId(Integer ctripScenicId) {
        this.ctripScenicId = ctripScenicId;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public Integer getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCityIdLong() {
        return cityIdLong;
    }

    public void setCityIdLong(Long cityIdLong) {
        this.cityIdLong = cityIdLong;
    }


    public List<ScenicArea> getScenicAreas() {
        return scenicAreas;
    }

    public void setScenicAreas(List<ScenicArea> scenicAreas) {
        this.scenicAreas = scenicAreas;
    }


    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }
}