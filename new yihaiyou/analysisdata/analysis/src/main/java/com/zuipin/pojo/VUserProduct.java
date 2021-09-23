package com.zuipin.pojo;

public class VUserProduct implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5046708419104071699L;
	
	private Long				userId, itemId;
	private Float				preference;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getItemId() {
		return itemId;
	}
	
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public Float getPreference() {
		return preference;
	}
	
	public void setPreference(Float preference) {
		this.preference = preference;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
