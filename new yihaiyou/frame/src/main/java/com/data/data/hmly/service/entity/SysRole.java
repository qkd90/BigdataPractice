package com.data.data.hmly.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * WebStore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_role")
public class SysRole extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Boolean delFlag; // 是否删除 0未删 1已删除
	private Integer status; // 是否冻结 0:未冰结 1已冻结
	private Integer seq; // 排序号
	private String remark; // 描述
	private String displayName; // 页面显示名称（拥有单个角色时显示，多个显示默认文字）
	private SysSite sysSite;

	public SysRole() {
	}

	public SysRole(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SysRole(String name) {
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

	@Column(name = "seq")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "displayName")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "siteid")
	public SysSite getSysSite() {
		return sysSite;
	}

	public void setSysSite(SysSite sysSite) {
		this.sysSite = sysSite;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
	public Boolean isSupperAdmin() {
		return id.intValue() == -2;
	}

	@Transient
	public Boolean isSiteAdmin() {
		return id.intValue() == -1;
	}

	@Transient
	public Boolean isCommpanyAdmin() {
		return id.intValue() == 0;
	}

}