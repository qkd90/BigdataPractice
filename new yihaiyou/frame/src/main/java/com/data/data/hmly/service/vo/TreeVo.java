package com.data.data.hmly.service.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class TreeVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1677401630538621267L;
	private Long				id;
	private String				text;
	private String				state				= "open";
	private Boolean				checked				= false;
	private String				iconCls;
	private List<TreeVo>		children;
	
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
	
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public List<TreeVo> getChildren() {
		return children;
	}
	
	public void setChildren(List<TreeVo> children) {
		this.children = children;
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
	
}
