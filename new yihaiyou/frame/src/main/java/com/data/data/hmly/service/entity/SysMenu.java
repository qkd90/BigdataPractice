package com.data.data.hmly.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author cjj
 * @date 2015年3月28日
 * @TODO 系统模块表
 */
@Entity
@Table(name = "sys_menu")
public class SysMenu extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;

	private Long menuid;
	private String menuname;
	private Integer menulevel;
	private String url;
	private Long parentId;
	private String menuImg;
	private String icon;
	private Integer seq;
	private Integer status; // 菜单状态 0或null 启用, 1:禁用
	private Boolean delFlag; // 删除状态 0或null:未删除 ,1:已删除
	private String remark; // 模块备注/描述
	private Integer isPublic; // 是否为公共模块
	private Boolean subSystemFlag;	// 是否子系统标志
	// /** VO字段,不存数据库 **/
	// private Long id;
	// private String text;
	// private String state;
	// private Boolean checked = false;
	// private String iconCls;
	private List<SysMenu> children; // 子菜单 VO字段不注入数据库
									// private List<SysResource> resources; //
									// 子资源 VO字段不注入数据库
									// private Integer hasRight = 0; // 是否拥有该权限
									// VO字段不注入数据库 0:无权限 1:有权限 2:公开
									//

	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Long getMenuid() {
		return menuid;
	}

	public void setMenuid(Long id) {
		this.menuid = id;
	}

	@Column(name = "MENU_NAME")
	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuName) {
		this.menuname = menuName;
	}

	@Column(name = "MENU_LEVEL")
	public Integer getMenulevel() {
		return menulevel;
	}

	public void setMenulevel(Integer menuLevel) {
		this.menulevel = menuLevel;
	}

	@Column(name = "MENU_URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String menuUrl) {
		this.url = menuUrl;
	}

	@Column(name = "PARENT_ID")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "MENU_IMG")
	public String getMenuImg() {
		return menuImg;
	}

	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}

	@Column(name = "MENU_ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String menuIcon) {
		this.icon = menuIcon;
	}

	@Column(name = "SEQ")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "del_Flag")
	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "is_public")
	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	// @Transient
	// public List<SysResource> getResources() {
	// return resources;
	// }
	//
	// public void setResources(List<SysResource> resources) {
	// this.resources = resources;
	// }
	//
	// @Transient
	// public Integer getHasRight() {
	// return hasRight;
	// }
	//
	// public void setHasRight(Integer hasRight) {
	// this.hasRight = hasRight;
	// }
	//
	// @Transient
	// public Long getId() {
	// return id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }
	//
	// @Transient
	// public String getText() {
	// return text;
	// }
	//
	// public void setText(String text) {
	// this.text = text;
	// }
	//
	// @Transient
	// public String getState() {
	// return state;
	// }
	//
	// public void setState(String state) {
	// this.state = state;
	// }
	//
	// @Transient
	// public Boolean getChecked() {
	// return checked;
	// }
	//
	// public void setChecked(Boolean checked) {
	// this.checked = checked;
	// }
	//
	// @Transient
	// public String getIconCls() {
	// return iconCls;
	// }
	//
	// public void setIconCls(String iconCls) {
	// this.iconCls = iconCls;
	// }
	//
	@Transient
	public List<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

	@Column(name = "subSystemFlag")
	public Boolean getSubSystemFlag() {
		return subSystemFlag;
	}

	public void setSubSystemFlag(Boolean subSystemFlag) {
		this.subSystemFlag = subSystemFlag;
	}
}