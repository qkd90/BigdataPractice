package com.zuipin.model;

public class SearchStoreVO {
    
    private Long    merchantId;
    private Long    storeId;
    private String  storeName;
    private String subStoreName;
    
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getSubStoreName() {
		return subStoreName;
	}
	public void setSubStoreName(String subStoreName) {
		this.subStoreName = subStoreName;
	}
    
 
    
}
