package com.data.data.hmly.service.lxbcommon.entity;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.lxbcommon.entity.enums.CustomArrange;
import com.data.data.hmly.service.lxbcommon.entity.enums.CustomStatus;
import com.data.data.hmly.service.lxbcommon.entity.enums.CustomType;
import com.data.data.hmly.service.plan.entity.Plan;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by caiys on 2016/6/15.
 * 定制需求
 */
@Entity
@Table(name = "custom_require")
public class CustomRequire extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;            // 标识
    @Enumerated(EnumType.STRING)
    @Column(name = "customType")
    private CustomType customType;  // 定制类型：公司、家庭、个人
//    @Column(name = "startCityId")
//    private Long startCityId; // 出发地
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "startCityId")
    private TbArea startCity;
    @Temporal(TemporalType.DATE)
    @Column(name = "startDate")
    private Date startDate;     // 出发日期
    @Column(name = "day")
    private Integer day;        // 游玩天数
    @Column(name = "adjustFlag")
    private Boolean adjustFlag;  // 是否根据实际情况调整
    @Enumerated(EnumType.STRING)
    @Column(name = "arrange")
    private CustomArrange arrange; // 行程安排：紧凑、适中、宽松、不确定
    @Column(name = "adult")
    private Integer adult;   // 成人数
    @Column(name = "child")
    private Integer child; // 儿童数
    @Column(name = "minPrice")
    private Float minPrice; // 预算最低价
    @Column(name = "maxPrice")
    private Float maxPrice; // 预算最高价
    @Column(name = "contactor")
    private String contactor; // 联系人
    @Column(name = "contactPhone")
    private String contactPhone; // 联系手机
    @Column(name = "contactEmail")
    private String contactEmail; // 联系邮箱
//    private Long createBy; // 创建人
    @ManyToOne
    @JoinColumn(name = "createBy")
    private Member member;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime; // 创建时间
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CustomStatus status; // 状态：处理中、已处理、已取消
    @Column(name = "remark")
    private String remark; // 备注
//    private Long refPlanId; // 关联行程标识
    @ManyToOne
    @JoinColumn(name = "refPlanId")
    private Plan plan; // 关联行程标识
    @ManyToOne
    @JoinColumn(name = "handleBy")
    private SysUser handler; // 处理人
//    private Long handleBy; // 处理人
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "handleTime", length = 19)
    private Date handleTime; // 处理时间
    @OneToMany(mappedBy = "customRequire")
    private Set<CustomRequireDestination> customRequireDestinations;

    // 页面字段
    @Transient
    private List<CustomRequireDestination> destinations;

    @Transient
    private Boolean isChina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomType getCustomType() {
        return customType;
    }

    public void setCustomType(CustomType customType) {
        this.customType = customType;
    }

    public TbArea getStartCity() {
        return startCity;
    }

    public void setStartCity(TbArea startCity) {
        this.startCity = startCity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Boolean getAdjustFlag() {
        return adjustFlag;
    }

    public void setAdjustFlag(Boolean adjustFlag) {
        this.adjustFlag = adjustFlag;
    }

    public CustomArrange getArrange() {
        return arrange;
    }

    public void setArrange(CustomArrange arrange) {
        this.arrange = arrange;
    }

    public Integer getAdult() {
        return adult;
    }

    public void setAdult(Integer adult) {
        this.adult = adult;
    }

    public Integer getChild() {
        return child;
    }

    public void setChild(Integer child) {
        this.child = child;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CustomStatus getStatus() {
        return status;
    }

    public void setStatus(CustomStatus status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public SysUser getHandler() {
        return handler;
    }

    public void setHandler(SysUser handler) {
        this.handler = handler;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Set<CustomRequireDestination> getCustomRequireDestinations() {
        return customRequireDestinations;
    }

    public void setCustomRequireDestinations(Set<CustomRequireDestination> customRequireDestinations) {
        this.customRequireDestinations = customRequireDestinations;
    }

    public List<CustomRequireDestination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<CustomRequireDestination> destinations) {
        this.destinations = destinations;
    }

    public Boolean getIsChina() {
        return isChina;
    }

    public void setIsChina(Boolean isChina) {
        this.isChina = isChina;
    }
}
