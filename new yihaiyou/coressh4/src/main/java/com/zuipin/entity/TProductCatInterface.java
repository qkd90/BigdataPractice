package com.zuipin.entity;

public interface TProductCatInterface {
    public Long getId();
    
    public void setId(Long id);
    
    public String getCatName();
    
    public void setCatName(String catName);
    
    public Long getCatLevel();
    
    public void setCatLevel(Long catLevel);
    
    public Long getParentId();
    
    public void setParentId(Long parentId);
    
    public String getCatCode();
    
    public void setCatCode(String catCode);
    
    public Long getCatSeq();
    
    public void setCatSeq(Long catSeq);
    
    public String getBrandShow();
    
    public void setBrandShow(String brandShow);
    
    public String getIsShow();
    
    public void setIsShow(String isShow);
    
    public String getMallKey();
    
    public void setMallKey(String mallKey);
    
    public String getMallKeyWords();
    
    public void setMallKeyWords(String mallKeyWords);
    
    public String getMallKeyDescription();
    
    public void setMallKeyDescription(String mallKeyDescription);
    
    public String getStatus();
    
    public void setStatus(String status);
    
    public String getIfCustomUrl();
    
    public void setIfCustomUrl(String ifCustomUrl);
    
    public String getCustomUrl();
    
    public void setCustomUrl(String customUrl);
    
    public String getOpenNewWindow();
    
    public void setOpenNewWindow(String openNewWindow);
    
}
