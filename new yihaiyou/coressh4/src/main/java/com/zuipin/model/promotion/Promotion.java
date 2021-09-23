package com.zuipin.model.promotion;

@SuppressWarnings("serial")
public class Promotion implements java.io.Serializable {
    
    private Long   modelId;
    
    private String promNo;
    
    private String promotionType;
    
    public Promotion() {
    }
    
    public Promotion(Long modelId, String promNo, String promotionType) {
        this.modelId = modelId;
        this.promNo = promNo;
        this.promotionType = promotionType;
    }
    
    public Long getModelId() {
        return modelId;
    }
    
    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
    
    public String getPromNo() {
        return promNo;
    }
    
    public void setPromNo(String promNo) {
        this.promNo = promNo;
    }
    
    public String getPromotionType() {
        return promotionType;
    }
    
    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }
    
}
