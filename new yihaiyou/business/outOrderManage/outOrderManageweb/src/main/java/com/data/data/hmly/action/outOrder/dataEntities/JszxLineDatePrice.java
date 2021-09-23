package com.data.data.hmly.action.outOrder.dataEntities;


import java.util.Date;

/**
 * Created by dy on 2016/3/18.
 */
public class JszxLineDatePrice {

    private Long        id;                                            // 主键ID
    private Date        huiDate;                                    	// 日期
    private Float       priPrice;                                    // 分销价
    private Float       rebate;                                    // 佣金


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getHuiDate() {
        return huiDate;
    }

    public void setHuiDate(Date huiDate) {
        this.huiDate = huiDate;
    }

    public Float getPriPrice() {
        return priPrice;
    }

    public void setPriPrice(Float priPrice) {
        this.priPrice = priPrice;
    }

    public Float getRebate() {
        return rebate;
    }

    public void setRebate(Float rebate) {
        this.rebate = rebate;
    }
}
