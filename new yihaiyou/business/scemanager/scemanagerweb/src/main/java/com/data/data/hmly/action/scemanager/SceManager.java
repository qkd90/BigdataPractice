package com.data.data.hmly.action.scemanager;

import java.util.Date;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitDetail;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.entity.UserStatus;

public class SceManager {
	
	private Long id;
	private String sceName;
	private String account;
	private String city;
	private String password;
	private String status;
	protected Date createdTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSceName() {
		return sceName;
	}
	public void setSceName(String sceName) {
		this.sceName = sceName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
}
