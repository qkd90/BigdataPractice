package com.data.data.hmly.service.user.entity.enums;

/**
 * Created by guoshijie on 2015/10/30.
 */
public enum TouristIdType {
	
   	

	IDCARD("身份证"), STUDENTCARD("学生证"), PASSPORT("护照"), REMNANTSOLDIER("残军证");
	
	private String description;

	 TouristIdType(String s) {
		this.description = s;
	}

	public String getDescription() {
		return this.description;
	}
	
}
