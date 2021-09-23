package com.data.data.hmly.model;

import java.io.Serializable;
import java.util.List;

public class CusTree extends Tree implements Serializable {
	private static final long	serialVersionUID	= 1l;
	
	private Boolean				isShow				= true; // 是否可见
	private Integer				seq;						// 排序号
	private Integer				type;						// 0分类包(文件夹),1具体分类
	private String				iconCls;					// 图标
	private List<CusTree>		children;					// 下级菜单
															
	public Boolean getIsShow() {
		return isShow;
	}
	
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	
	public Integer getSeq() {
		return seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public List<CusTree> getChildren() {
		return children;
	}
	
	public void setChildren(List<CusTree> children) {
		this.children = children;
	}
	
	public String getIconCls() {
		return iconCls;
	}
	
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
}
