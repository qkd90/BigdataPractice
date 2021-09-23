package com.data.data.hmly.service.vo;

import java.io.Serializable;
import java.util.List;

import com.data.data.hmly.service.entity.SysResource;

public class TreeGridRightVo implements Serializable {
	
	private static final long		serialVersionUID	= 1L;
	
	/** VO字段,不存数据库 **/
	private Long					id;
	private String					parentId;						// 父级ID
	private String					text;
	private String					state;
	private Boolean					checked				= false;
	private String					iconCls;
	private List<TreeGridRightVo>	children;						// 子菜单 VO字段不注入数据库
	private List<SysResource>		resources;						// 子资源 VO字段不注入数据库
	private Integer					hasRight			= 0;		// 是否拥有该权限 VO字段不注入数据库 0:无权限 1:有权限 2:公开
																	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public Boolean getChecked() {
		return checked;
	}
	
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public String getIconCls() {
		return iconCls;
	}
	
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public List<TreeGridRightVo> getChildren() {
		return children;
	}
	
	public void setChildren(List<TreeGridRightVo> children) {
		this.children = children;
	}
	
	public List<SysResource> getResources() {
		return resources;
	}
	
	public void setResources(List<SysResource> resources) {
		this.resources = resources;
	}
	
	public Integer getHasRight() {
		return hasRight;
	}
	
	public void setHasRight(Integer hasRight) {
		this.hasRight = hasRight;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
