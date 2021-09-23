package com.data.data.hmly.service.entity;

import com.framework.hibernate.annotation.RegFilter;
import com.google.common.collect.Lists;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cjj
 * @date 2015年3月28日
 * @TODO 组织
 */
@Entity
@Table(name = "sys_unit")
@RegFilter
public class SysUnit extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name; // 组织名称
	private String unitNo; // 组织编号
	private SysUnit parentUnit; // 上级组织

	private SysUnit companyUnit; // 所属公司
	private Boolean delFlag; // 删除状态 0:未删除 1已删除
	private Integer status; // 是否冻结 0:未冰结 1已冻结
	private Integer seq; // 排序号
	private SysSite sysSite = new SysSite(); // 所属公司(站点)信息
	private String remark; // 备注描述
	private String address;
	private TbArea area = new TbArea();
	private SysUnitDetail sysUnitDetail = new SysUnitDetail();
	private List<UnitQualification> qualificationList = Lists.newArrayList();
	private UnitType unitType;
	private Date createTime;
	private String reason;
	private Boolean testFlag = false; // 测试标志 0:非测试 1:测试
	private List<SysUnitImage> sysUnitImages;
	/* VO属性* */
	private List<SysUnit> children = new ArrayList<SysUnit>();
	private String state = "closed";
	private String iconCls;
	private Long companyUnitId;


	private String identityCode; // 公司串码
	
	private String parentName;
	
	private String siteName;

	public SysUnit() {
	}

	public SysUnit(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public SysUnit(Long id) {
		super();
		this.id = id;
	}

	public SysUnit(String name) {
		super();
		this.name = name;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name")
	@RegFilter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "del_flag")
	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "unit_no")
	@RegFilter
	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	@Column(name = "seq")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id")
	public SysSite getSysSite() {
		return sysSite;
	}

	public void setSysSite(SysSite sysSite) {
		this.sysSite = sysSite;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Column(name = "identityCode")
	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	public SysUnit getParentUnit() {
		return parentUnit;
	}

	public void setParentUnit(SysUnit parentUnit) {
		this.parentUnit = parentUnit;
	}

	@Transient
	public List<SysUnit> getChildren() {
		return children;
	}

	public void setChildren(List<SysUnit> children) {
		this.children = children;
	}

	@Transient
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Transient
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "areaId")
	public TbArea getArea() {
		return area;
	}

	public void setArea(TbArea area) {
		this.area = area;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unitDetailId")
	public SysUnitDetail getSysUnitDetail() {
		return sysUnitDetail;
	}

	public void setSysUnitDetail(SysUnitDetail sysUnitDetail) {
		this.sysUnitDetail = sysUnitDetail;
	}

	@Column(name = "unitType")
	@Enumerated(EnumType.STRING)
	public UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_unit_id")
	public SysUnit getCompanyUnit() {
		return companyUnit;
	}

	public void setCompanyUnit(SysUnit companyUnit) {
		this.companyUnit = companyUnit;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysUnit")
	public List<SysUnitImage> getSysUnitImages() {
		return sysUnitImages;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sysUnit")
	public List<UnitQualification> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List<UnitQualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public void setSysUnitImages(List<SysUnitImage> sysUnitImages) {
		this.sysUnitImages = sysUnitImages;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(Boolean testFlag) {
		this.testFlag = testFlag;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Transient
	public Long getCompanyUnitId() {
		if (companyUnit != null) {
			companyUnitId = companyUnit.getId();
		}
		return companyUnitId;
	}
	@Transient
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	@Transient
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}




}