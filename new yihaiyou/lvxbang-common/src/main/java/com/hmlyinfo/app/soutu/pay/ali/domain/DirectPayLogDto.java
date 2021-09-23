package com.hmlyinfo.app.soutu.pay.ali.domain;

import com.hmlyinfo.base.persistent.BaseEntity;

import java.util.Date;

/**
 * <p>Title: DirectPayLog.java</p>
 * <p/>
 * <p>Description:支付宝交易日志信息 </p>
 * <p/>
 * <p>Date:2013-7-30</p>
 * <p/>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p/>
 * <p>Company: www.myleyi.com</p>
 *
 * @author zheng.yongfeng
 */
public class DirectPayLogDto extends BaseEntity {
	private int logId;                    //日志标识

	private long orderId;                //订单号

	private double totalFee;            //付款金额

	private String subject;                //订单名称
	private String orderDesc;            //订单描述
	private String useAntiPhishingKey;    //是否启用防钓鱼时间戳
	private String exterInvokeIp;        //用户创建订单所用ip
	private String sellerEmail;            //卖家支付宝账号
	private String notifyUrl;            //异步通知请求
	private String returnUrl;            //同步通知请求
	private String showUrl;                //商品展示请求

	private String payRespService;        //支付宝支付成功后业务服务名称
	private String paySuccessUrl;        //支付成功跳转页面
	private String payFailUrl;            //支付失败跳转页面

	private Date createTime;            //创建时间

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
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

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getUseAntiPhishingKey() {
		return useAntiPhishingKey;
	}

	public void setUseAntiPhishingKey(String useAntiPhishingKey) {
		this.useAntiPhishingKey = useAntiPhishingKey;
	}

	public String getExterInvokeIp() {
		return exterInvokeIp;
	}

	public void setExterInvokeIp(String exterInvokeIp) {
		this.exterInvokeIp = exterInvokeIp;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getPayRespService() {
		return payRespService;
	}

	public void setPayRespService(String payRespService) {
		this.payRespService = payRespService;
	}

	public String getPaySuccessUrl() {
		return paySuccessUrl;
	}

	public void setPaySuccessUrl(String paySuccessUrl) {
		this.paySuccessUrl = paySuccessUrl;
	}

	public String getPayFailUrl() {
		return payFailUrl;
	}

	public void setPayFailUrl(String payFailUrl) {
		this.payFailUrl = payFailUrl;
	}

}
