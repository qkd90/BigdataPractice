package com.zuipin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_MEMBER_MOBILE")
public class TMemberMobile implements java.io.Serializable {
	
	@Id
	@Column(name = "ID", precision = 18, scale = 0)
	private Long	id;
	
	@Column(name = "MEMBER_ID", precision = 18, scale = 0, nullable = false)
	private Long	memberId;
	
	@Column(name = "MOBILE", length = 21, nullable = false, unique = true)
	private String	mobile;
	
	public TMemberMobile() {
		// TODO Auto-generated constructor stub
	}
	
	public TMemberMobile(Long id, Long memberId, String mobile) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.mobile = mobile;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMemberId() {
		return memberId;
	}
	
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
