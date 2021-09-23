package com.data.data.hmly.service.entity;


import javax.persistence.*;

/**
 * Created by guoshijie on 2015/11/11.
 */
@javax.persistence.Entity
@Table(name = "sys_unit_qualification")
public class UnitQualification extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = -9010619245437721918L;
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "path")
	private String path;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unitId")
	private SysUnit sysUnit;
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private DoucementType type;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public SysUnit getSysUnit() {
		return sysUnit;
	}

	public void setSysUnit(SysUnit sysUnit) {
		this.sysUnit = sysUnit;
	}

	public DoucementType getType() {
		return type;
	}

	public void setType(DoucementType type) {
		this.type = type;
	}
}
