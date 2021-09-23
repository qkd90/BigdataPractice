package com.data.data.hmly.service.entity;


import com.data.data.hmly.enums.LabelStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "label")
public class Label extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "name")
	private String name;

    @Column(name = "alias")
    private String alias;
	
	@Column(name = "sort")
	private Integer sort;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private LabelStatus status;
	
	@Column(name = "createTime")
	private Date createTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
    private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private SysUnit sysUnit;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Label parent;

	@Column(name = "level")
	private Integer level;

	@Column(name = "leaf")
	private Boolean leaf;
	
	@Transient
    private List<Label> children;
	@Transient
    private Integer targSort;

    @Transient
    private String searchName;
	@Transient
	private Long parentId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    public LabelStatus getStatus() {
		return status;
	}

	public void setStatus(LabelStatus status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SysUnit getSysUnit() {
		return sysUnit;
	}

	public void setSysUnit(SysUnit sysUnit) {
		this.sysUnit = sysUnit;
	}

	public Label getParent() {
		return parent;
	}

	public void setParent(Label parent) {
		this.parent = parent;
	}

	public List<Label> getChildren() {
		return children;
	}

	public void setChildren(List<Label> children) {
		this.children = children;
	}

	public Integer getTargSort() {
		return targSort;
	}

	public void setTargSort(Integer targSort) {
		this.targSort = targSort;
	}

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
}
