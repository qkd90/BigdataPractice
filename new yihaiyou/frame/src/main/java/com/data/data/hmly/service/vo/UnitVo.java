package com.data.data.hmly.service.vo;

import java.util.ArrayList;
import java.util.List;

import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;

public class UnitVo extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= 1L;
	
	private Long				id;
	private String				name;											// 组织名称
	private String				unitNo;										// 组织编号
	private Boolean				delFlag;										// 删除状态 0:未删除 1已删除
	private Integer				status;										// 是否冻结 0:未冰结 1已冻结
	private Integer				seq;											// 排序号
	private SysSite				sysSite;										// 所属公司(站点)信息
	private String				remark;										// 备注描述
	private SysUnit 			companyUnit;								// 所在公司
	/* VO属性* */
	private List<UnitVo>		children			= new ArrayList<UnitVo>();
	private String				state				= "closed";
	private String				iconCls;
	private String				text				= name;
	
	public UnitVo() {
	}
	
	public UnitVo(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public UnitVo(String name) {
		super();
		this.name = name;
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
	
	public Boolean getDelFlag() {
		return delFlag;
	}
	
	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getUnitNo() {
		return unitNo;
	}
	
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	
	public Integer getSeq() {
		return seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public SysSite getSysSite() {
		return sysSite;
	}
	
	public void setSysSite(SysSite sysSite) {
		this.sysSite = sysSite;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public List<UnitVo> getChildren() {
		return children;
	}
	
	public void setChildren(List<UnitVo> children) {
		this.children = children;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getIconCls() {
		return iconCls;
	}
	
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public SysUnit getCompanyUnit() {
		return companyUnit;
	}

	public void setCompanyUnit(SysUnit companyUnit) {
		this.companyUnit = companyUnit;
	}

	
}