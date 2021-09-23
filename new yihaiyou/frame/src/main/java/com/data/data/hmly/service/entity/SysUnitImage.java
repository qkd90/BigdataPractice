package com.data.data.hmly.service.entity;

import com.data.data.hmly.enums.SysUnitImageType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by guoshijie on 2015/11/11.
 */
@Entity
@Table(name = "sys_unit_image")
public class SysUnitImage extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = -9010619245437721918L;
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long             id;
	@ManyToOne
	@JoinColumn(name = "unitId")
	private SysUnit          sysUnit;
	@Column(name = "path")
	private String           path;
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private SysUnitImageType type;
	@Column(name = "sort")
	private int              sort;
	@Column(name = "updateTime")
	private Date updateTime;
	@Column(name = "createTime")
	private Date             createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SysUnit getSysUnit() {
		return sysUnit;
	}

	public void setSysUnit(SysUnit sysUnit) {
		this.sysUnit = sysUnit;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public SysUnitImageType getType() {
		return type;
	}

	public void setType(SysUnitImageType type) {
		this.type = type;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
