package com.data.data.hmly.service.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user_relation")
public class UserRelation extends com.framework.hibernate.util.Entity {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long				id;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date				createdTime;
	private Integer				level;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private User 				parentUser; 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "childId")
	private User 				childUser;
	
	
	public UserRelation() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public User getParentUser() {
		return parentUser;
	}
	public void setParentUser(User parentUser) {
		this.parentUser = parentUser;
	}
	public User getChildUser() {
		return childUser;
	}
	public void setChildUser(User childUser) {
		this.childUser = childUser;
	} 
	

}
