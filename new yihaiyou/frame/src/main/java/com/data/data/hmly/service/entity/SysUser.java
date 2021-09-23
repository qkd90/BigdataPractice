package com.data.data.hmly.service.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

/**
 * @author cjj
 * @date 2015年3月28日
 * @TODO 系统用户表
 */
@Entity
@Table(name = "sys_user")
@PrimaryKeyJoinColumn(name = "userId")
public class SysUser extends User implements java.io.Serializable {

	private static final long serialVersionUID = -2291261685606913787L;

	@Column(name = "gender")
	private String gender = ""; // 性别
								// （男、女）
	@Column(name = "login_num")
	private Integer loginNum = 0; // 登陆次数
	@Column(name = "is_use")
	@Index(name = "search_is_use")
	private Boolean isUse = true; // 是否已激活
									// （已激活、已冻结）
	// private SysRole role; // 用户角色
	@Column(name = "del_flag")
	private Boolean delFlag;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_id")
	private SysUnit sysUnit; // 所属组织部门

	@Transient
	private String roles; // 当前用户角色
	@Transient
	private List<SysUserRole> sysRoles;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public Boolean getIsUse() {
		return isUse;
	}

	public void setIsUse(Boolean isUse) {
		this.isUse = isUse;
	}

	// @ManyToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "role_id")
	// public SysRole getRole() {
	// return role;
	// }
	//
	// public void setRole(SysRole role) {
	// this.role = role;
	// }

	/**
	 * @return the delFlag
	 */

	public Boolean getDelFlag() {
		return delFlag;
	}

	/**
	 * @param delFlag
	 *            the delFlag to set
	 */
	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public SysUnit getSysUnit() {
		return sysUnit;
	}

	public void setSysUnit(SysUnit sysUnit) {
		this.sysUnit = sysUnit;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<SysUserRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(List<SysUserRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

}
