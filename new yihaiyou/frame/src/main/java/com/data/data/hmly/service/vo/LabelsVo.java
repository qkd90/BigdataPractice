package com.data.data.hmly.service.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LabelsVo implements Serializable {
	
	private static final long		serialVersionUID	= 1L;
	
	/** VO字段,不存数据库 **/
	private Long					id;
	private String					isParent;
	private Integer					level;
	private String					text;
	private String					name;
    private String                 alias;
	private Date 					createTime;
	private String					state;
	private String					status;
	private String					iconCls;
	private String					sort;
	private Boolean 				leaf;
	private Boolean                selected;
	private String					parentId;	// 父级ID，用Long转json有问题
	private String					dir;	// 所在路径
	private List<LabelsVo>	children;
																	
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
	
	
	public String getIconCls() {
		return iconCls;
	}
	
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public List<LabelsVo> getChildren() {
		return children;
	}
	
	public void setChildren(List<LabelsVo> children) {
		this.children = children;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
