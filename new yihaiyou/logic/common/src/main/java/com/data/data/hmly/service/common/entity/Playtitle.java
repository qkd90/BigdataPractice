package com.data.data.hmly.service.common.entity;

// Generated 2015-10-21 17:56:07 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Playtitle generated by hbm2java
 */
@Entity
@Table(name = "playtitle")
public class Playtitle extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private Long id;
	private String name;
	private Long userid;
	private Date createTime;

	public Playtitle() {
	}

	public Playtitle(String name, Long userid, Date createTime) {
		this.name = name;
		this.userid = userid;
		this.createTime = createTime;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "userid")
	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
