package com.zuipin.pojo;

import javax.persistence.Id;

import com.framework.hibernate.util.Entity;

@javax.persistence.Entity
public class Myorder extends Entity {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8129318299932673000L;
	@Id
	private Long				id;
	private Long				userid;
	private Float				totlamt;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserid() {
		return userid;
	}
	
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	public Float getTotlamt() {
		return totlamt;
	}
	
	public void setTotlamt(Float totlamt) {
		this.totlamt = totlamt;
	}
	
}
