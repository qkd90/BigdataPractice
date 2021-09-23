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
 * @TODO 角色模块授权表
 */
@Entity
@Table(name = "sys_role_menu")
public class SysRoleMenu extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= 1L;
	private Long				id;
	private SysRole				role;
	private SysMenu				menu;
	
	public SysRoleMenu() {
		
	}
	
	public SysRoleMenu(Long roleId, Long menuId) {
		SysRole role = new SysRole();
		role.setId(roleId);
		this.role = role;
		SysMenu menu = new SysMenu();
		menu.setMenuid(menuId);
		this.menu = menu;
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
	@JoinColumn(name = "menu_id")
	public SysMenu getMenu() {
		return menu;
	}
	
	public void setMenu(SysMenu menu) {
		this.menu = menu;
	}
	
}