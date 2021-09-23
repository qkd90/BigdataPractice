package com.zuipin.model.promotion;

import java.util.ArrayList;
import java.util.List;

import com.zuipin.model.CartItem;

@SuppressWarnings("serial")
public class BuySendGood extends Promotion {
    
    private Double         buyCount;
    
    private Double         fullCount;
    
    private String         unitName;
    
    private List<CartItem> buyGiftItems = new ArrayList<CartItem>();
    
    public BuySendGood() {
    }
    
    public BuySendGood(Long modelId, String promNo, String promotionType) {
        super(modelId, promNo, promotionType);
    }
    
    public Double getBuyCount() {
        return buyCount;
    }
    
    public void setBuyCount(Double buyCount) {
        this.buyCount = buyCount;
    }
    
    public Double getFullCount() {
        return fullCount;
    }
    
    public void setFullCount(Double fullCount) {
        this.fullCount = fullCount;
    }
    
    public String getUnitName() {
        return unitName;
    }
    
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    
    public List<CartItem> getBuyGiftItems() {
        return buyGiftItems;
    }
    
    public void setBuyGiftItems(List<CartItem> buyGiftItems) {
        this.buyGiftItems = buyGiftItems;
    }
}
