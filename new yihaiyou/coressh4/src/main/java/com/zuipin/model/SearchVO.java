package com.zuipin.model;

public class SearchVO {
    
    private String  keyword;
    
    private String  psort;
    
    private String  cid2;
    
    private String  cid3;
    
    private String  brand;
    
    private String  price;
    
    private String  filter;
    
    private Integer pageNO;
    
    private Integer pageSize;
    
    private float   takeIndexSeconds;
    
    private String areaId;
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public String getPsort() {
        return psort;
    }
    
    public void setPsort(String psort) {
        this.psort = psort;
    }
    
    public String getCid2() {
        return cid2;
    }
    
    public void setCid2(String cid2) {
        this.cid2 = cid2;
    }
    
    public String getCid3() {
        return cid3;
    }
    
    public void setCid3(String cid3) {
        this.cid3 = cid3;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getFilter() {
        return filter;
    }
    
    public void setFilter(String filter) {
        this.filter = filter;
    }
    
    public Integer getPageNO() {
        return pageNO;
    }
    
    public void setPageNO(Integer pageNO) {
        this.pageNO = pageNO;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public float getTakeIndexSeconds() {
        return takeIndexSeconds;
    }
    
    public void setTakeIndexSeconds(float takeIndexSeconds) {
        this.takeIndexSeconds = takeIndexSeconds;
    }

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
}
