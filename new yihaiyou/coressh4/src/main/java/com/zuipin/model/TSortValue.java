package com.zuipin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_SORT_VALUE")
@SequenceGenerator(name = "SEQ_T_SORT_VALUE", sequenceName = "SEQ_T_SORT_VALUE", allocationSize = 1)
public class TSortValue implements java.io.Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_SORT_VALUE")
    @Column(name = "VALUE_ID", precision = 18, scale = 0)
    private Long   valueId;
    
    @Column(name = "VALUE_NAME", length = 20)
    private String valueName;
    
    @Column(name = "IS_PRICE", length = 2)
    private String isPrice;
    
    @Column(name = "START_PRICE", precision = 12, scale = 2, nullable = false)
    private Double startPrice;
    
    @Column(name = "END_PRICE", precision = 12, scale = 2, nullable = false)
    private Double endPrice;
    
    @Column(name = "SHOW_ORDER", precision = 8, scale = 0)
    private Long   showOrder;
    
    @Column(name = "MOD_MAN", length = 40)
    private String modMan;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MOD_DATE", length = 7)
    private Date   modDate;
    
    @Column(name = "SORT_ID", precision = 18, scale = 0)
    private Long   sortId;
    
    public Long getValueId() {
        return valueId;
    }
    
    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }
    
    public String getValueName() {
        return valueName;
    }
    
    public void setValueName(String valueName) {
        this.valueName = valueName;
    }
    
    public String getIsPrice() {
        return isPrice;
    }
    
    public void setIsPrice(String isPrice) {
        this.isPrice = isPrice;
    }
    
    public Double getStartPrice() {
        return startPrice;
    }
    
    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }
    
    public Double getEndPrice() {
        return endPrice;
    }
    
    public void setEndPrice(Double endPrice) {
        this.endPrice = endPrice;
    }
    
    public Long getShowOrder() {
        return showOrder;
    }
    
    public void setShowOrder(Long showOrder) {
        this.showOrder = showOrder;
    }
    
    public String getModMan() {
        return modMan;
    }
    
    public void setModMan(String modMan) {
        this.modMan = modMan;
    }
    
    public Date getModDate() {
        return modDate;
    }
    
    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }
    
    public Long getSortId() {
        return sortId;
    }
    
    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }
}
