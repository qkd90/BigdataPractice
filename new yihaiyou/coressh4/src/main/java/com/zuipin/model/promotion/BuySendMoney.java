package com.zuipin.model.promotion;


@SuppressWarnings("serial")
public class BuySendMoney extends Promotion {
    
    private Double buyMoney;
    
    private Double sendMoney;
    
    private String centSave; // 赠送返现或者储值：10.返现 11.储值
    
    private boolean isHalf; //区分“半价购”、“零元购”
                              
    public BuySendMoney() {
    }
    
    public BuySendMoney(Long modelId, String promNo, String promotionType) {
        super(modelId, promNo, promotionType);
    }
    
    public Double getBuyMoney() {
        return buyMoney;
    }
    
    public void setBuyMoney(Double buyMoney) {
        this.buyMoney = buyMoney;
    }
    
    public Double getSendMoney() {
        return sendMoney;
    }
    
    public void setSendMoney(Double sendMoney) {
        this.sendMoney = sendMoney;
    }
    
    public String getCentSave() {
        return centSave;
    }
    
    public void setCentSave(String centSave) {
        this.centSave = centSave;
    }

    public boolean getIsHalf() {
        return isHalf;
    }

    public void setIsHalf(boolean isHalf) {
        this.isHalf = isHalf;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)  
            return true;  
        if (obj == null)  
            return false;  
        if (getClass() != obj.getClass())  
            return false;  
        BuySendMoney other = (BuySendMoney) obj;  
        return buyMoney.equals(other.buyMoney) && sendMoney.equals(other.sendMoney) && centSave.equals(other.centSave) && isHalf == other.isHalf;  
    }
}