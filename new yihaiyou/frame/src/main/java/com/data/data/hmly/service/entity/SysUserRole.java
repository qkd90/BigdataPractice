package com.data.data.hmly.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author cjj
 * @date 2015年3月28日
 * @TODO 用户-角色映射表
 */
@Entity
@Table(name = "sys_user_role")
public class SysUserRole extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	private static final long	serialVersionUID	= -2291261685606913787L;
	
	private Long				id;											// 主键ID
																				
	private SysUser				user;
	private SysRole				role;
	
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public SysUser getUser() {
		return user;
	}
	
	public void setUser(SysUser user) {
		this.user = user;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	public SysRole getRole() {
		return role;
	}
	
	public void setRole(SysRole role) {
		this.role = role;
	}
	
}
