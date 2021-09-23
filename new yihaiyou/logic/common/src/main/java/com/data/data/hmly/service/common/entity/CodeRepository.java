package com.data.data.hmly.service.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by guoshijie on 2015/11/9.
 */
@Entity
@Table(name = "coderepository")
public class CodeRepository extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = -9215023448601457011L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private long   id;
	@Column(name = "name")
	private String name;
	@Column(name = "count")
	private int    count;
	@Column(name = "prefix")
	private String prefix;
	@Column(name = "updateTime")
	private Date   updateTime;
	@Column(name = "createTime")
	private Date   createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
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
