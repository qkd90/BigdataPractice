package com.zuipin.model;

public class SearchCatVO {
    private String  weight;
    private Long    catId;
    private Long    parentCatId;
    private String  catName;
    private Integer pcount;
    
    public Long getCatId() {
        return catId;
    }
    
    public void setCatId(Long catId) {
        this.catId = catId;
    }
    
    public Long getParentCatId() {
        return parentCatId;
    }
    
    public void setParentCatId(Long parentCatId) {
        this.parentCatId = parentCatId;
    }
    
    public String getCatName() {
        return catName;
    }
    
    public void setCatName(String catName) {
        this.catName = catName;
    }
    
    public Integer getPcount() {
        return pcount;
    }
    
    public void setPcount(Integer pcount) {
        this.pcount = pcount;
    }
    
    public String getWeight() {
        return weight;
    }
    
    public void setWeight(String weight) {
        this.weight = weight;
    }
    
}
