package com.data.data.hmly.service.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TagLabelTreeVo implements Serializable {
	
	private static final long		serialVersionUID	= 1L;
	
	/** VO字段,不存数据库 **/
	private Long					id;
	private String					hasChild;						// 父级ID
	private String					name;
	private String					state;
	private String 					hasLabel;
	private Integer  				sort;
	private List<TagLabelTreeVo>	children;						// 子菜单 VO字段不注入数据库
																	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	
	
	public List<TagLabelTreeVo> getChildren() {
		return children;
	}
	
	public void setChildren(List<TagLabelTreeVo> children) {
		this.children = children;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHasLabel() {
		return hasLabel;
	}
	public void setHasLabel(String hasLabel) {
		this.hasLabel = hasLabel;
	}
	public String getHasChild() {
		return hasChild;
	}
	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}
