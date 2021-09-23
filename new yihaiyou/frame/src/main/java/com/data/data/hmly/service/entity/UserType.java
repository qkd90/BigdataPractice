package com.data.data.hmly.service.entity;

public enum UserType {
	USER(3L), ADMIN(2L), SiteManage(-1L), AllSiteManage(-2L), CompanyManage(0L), ScenicManage(1L), DISTRIBUTOR(4L);
	private Long id;

	UserType(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
