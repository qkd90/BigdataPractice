package com.hmlyinfo.app.soutu.pay.ali.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

import java.util.Date;

/**
 * <p>Title: DirectPayReceiptDto.java</p>
 * <p/>
 * <p>Description: 支付宝回执信息</p>
 * <p/>
 * <p>Date:2013-7-30</p>
 * <p/>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p/>
 * <p>Company: www.myleyi.com</p>
 *
 * @author zheng.yongfeng
 */
public class DirectPayReceiptDto extends BaseEntity {
	private int receiptId;                //回执标识
	private int bussinessStatus = -1;    //业务逻辑处理结果

	private long orderId;                //订单号

	private double totalFee;            //付款金额

	private String subject;                //订单名称
	private String tradeNo;                //支付宝交易号
	private String tradeStatus;            //交易状态
	private String sellerEmail;            //卖家支付宝账号
	private String buyerEmail;            //买家支付宝账号
	private String notifyId;            //通知校验ID
	private String notifyType;            //通知类型
	private String receiptInfo;            //原始的支付宝回执信息

	private Date notifyTime;            //通知时间
	private Date createTime;            //创建时间

	/**
	 * 处理相关业务逻辑成功
	 */
	public static int BUSSINESS_STATUS_SUCCESS = 1;

	/**
	 * 处理相关业务逻辑失败
	 */
	public static int BUSSINESS_STATUS_ERROR = 0;

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getReceiptInfo() {
		return receiptInfo;
	}

	public void setReceiptInfo(String receiptInfo) {
		this.receiptInfo = receiptInfo;
	}

	public int getBussinessStatus() {
		return bussinessStatus;
	}

	public void setBussinessStatus(int bussinessStatus) {
		this.bussinessStatus = bussinessStatus;
	}


}
