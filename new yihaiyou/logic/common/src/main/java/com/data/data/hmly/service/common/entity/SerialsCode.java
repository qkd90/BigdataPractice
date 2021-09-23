package com.data.data.hmly.service.common.entity;

/**
 * Created by guoshijie on 2015/11/9.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "serialscode")
public class SerialsCode extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = -9215023448601457011L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long           id;
	@ManyToOne
	@JoinColumn(name = "repositoryId")
	private CodeRepository repository;
	@Column(name = "code")
	private String         code;
	@Column(name = "occupied")
	private boolean        occupied;
	@Column(name = "updateTime")
	private Date           updateTime;
	@Column(name = "createTime")
	private Date           createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CodeRepository getRepository() {
		return repository;
	}

	public void setRepository(CodeRepository repository) {
		this.repository = repository;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
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
