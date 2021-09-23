package com.data.data.hmly.service.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.user.entity.enums.TicketReceiveType;

@Entity
@Table(name = "ticket_receiver")
public class TicketReceiver extends com.framework.hibernate.util.Entity implements java.io.Serializable  {
	
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, insertable = true, updatable = true)
	private Long				id;	
	@Column(name = "tel")
	private String              tel;	
	@Column(name = "name")
	private String              name;
	@Column(name = "email")
	private String              email;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", unique = true, nullable = false, updatable = false)
	private User        user;	
	@Column(name = "idNumber")
	private String              idNumber;
	@Enumerated(EnumType.STRING)	
	private TicketReceiveType   ticketReceiveType;	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date				createdTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date				updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
		
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public TicketReceiveType getTicketReceiveType() {
		return ticketReceiveType;
	}
	public void setTicketReceiveType(TicketReceiveType ticketReceiveType) {
		this.ticketReceiveType = ticketReceiveType;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
