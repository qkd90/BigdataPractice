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
 * @TODO 角色资源授权表
 */
@Entity
@Table(name = "sys_role_resource")
public class SysRoleResource extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= 1L;
	private Long				id;
	private SysRole				role;
	private SysResource			resource;
	
	public SysRoleResource() {
	}
	
	public SysRoleResource(Long roleId, Long rid) {
		SysRole role = new SysRole();
		role.setId(roleId);
		this.role = role;
		SysResource resource = new SysResource();
		resource.setId(rid);
		this.resource = resource;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	public SysRole getRole() {
		return role;
	}
	
	public void setRole(SysRole role) {
		this.role = role;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "resource_id")
	public SysResource getResource() {
		return resource;
	}
	
	public void setResource(SysResource resource) {
		this.resource = resource;
	}
	
}