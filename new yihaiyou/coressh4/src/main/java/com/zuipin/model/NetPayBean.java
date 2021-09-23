package com.zuipin.model;

/**
 * @版权：象屿商城 版权所有 (c) 2012
 * @author wangj
 * @version Revision 2.0.0
 * @email:wangj@xiangyu.cn
 * @see:
 * @创建日期：2012-8-23
 * @功能说明：网银支付实体bean
 */
public class NetPayBean {
    
    private String orderNo;    // 订单号
    private String whatBank;   // 什么银行支付
    private double netPayPrice; // 网银支付的金额
    private String payState;   // 支付状态
    private String qid;        // 交易流水号
                                
    // 以下为get和set方法
    public String getOrderNo() {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    public String getWhatBank() {
        return whatBank;
    }
    
    public void setWhatBank(String whatBank) {
        this.whatBank = whatBank;
    }
    
    public double getNetPayPrice() {
        return netPayPrice;
    }
    
    public void setNetPayPrice(double netPayPrice) {
        this.netPayPrice = netPayPrice;
    }
    
    public String getPayState() {
        return payState;
    }
    
    public void setPayState(String payState) {
        this.payState = payState;
    }
    
    public String getQid() {
        return qid;
    }
    
    public void setQid(String qid) {
        this.qid = qid;
    }
    
}
