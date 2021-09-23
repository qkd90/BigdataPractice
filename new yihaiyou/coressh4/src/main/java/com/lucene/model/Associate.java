package com.lucene.model;

import java.util.ArrayList;
import java.util.List;

public class Associate {
    private String          keyword;
    private String          catNameZ;
    private String          catIdZ;
    private boolean         isCat;
    private int             amount;
    private int             index;
    private List<Associate> associates = new ArrayList<Associate>();
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public boolean isCat() {
        return isCat;
    }
    
    public void setCat(boolean isCat) {
        this.isCat = isCat;
    }
    
    public String getCatNameZ() {
        return catNameZ;
    }
    
    public void setCatNameZ(String catNameZ) {
        this.catNameZ = catNameZ;
    }
    
    public String getCatIdZ() {
        return catIdZ;
    }
    
    public void setCatIdZ(String catIdZ) {
        this.catIdZ = catIdZ;
    }
    
    public List<Associate> getAssociates() {
        return associates;
    }
    
    public void setAssociates(List<Associate> associates) {
        this.associates = associates;
    }
    
}
