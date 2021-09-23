package com.data.data.hmly.action.lvxbang.response;

/**
 * Created by HMLY on 2016/6/1.
 */
public class LineTicketDateprice {

    private static final long serialVersionUID = 1L;

    private Long        id;                                            // 主键ID
    private String        huiDate;                                    	// 日期
    private Long        ticketPriceId;                                // 线路报价Id
    private Float       priPrice;                                    // 分销价
    private Float       rebate;                                    // 佣金
    private Float		maketPrice;								//市场价


    public LineTicketDateprice() {

    }

    public LineTicketDateprice(Long id, String huiDate, Long ticketPriceId,
                           Float priPrice , Float rebate) {
        super();
        this.id = id;
        this.huiDate = huiDate;
        this.ticketPriceId = ticketPriceId;
        this.priPrice = priPrice;
        this.rebate = rebate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHuiDate() {
        return huiDate;
    }

    public void setHuiDate(String huiDate) {
        this.huiDate = huiDate;
    }

    public Long getTicketPriceId() {
        return ticketPriceId;
    }

    public void setTicketPriceId(Long ticketPriceId) {
        this.ticketPriceId = ticketPriceId;
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

    public Float getMaketPrice() {
        return maketPrice;
    }

    public void setMaketPrice(Float maketPrice) {
        this.maketPrice = maketPrice;
    }
}
