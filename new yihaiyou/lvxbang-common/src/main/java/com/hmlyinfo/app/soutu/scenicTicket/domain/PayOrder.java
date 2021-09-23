package com.hmlyinfo.app.soutu.scenicTicket.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class PayOrder extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String VALIDATE_RESULT_SUCCESS = "ok";//验证状态：OK

	// 微信支付
	public static final int PAY_TYPE_WX = 1;
	// 支付宝支付
	public static final int PAY_TYPE_ALI = 102;
	//通联支付
	public static final int PAY_TYPE_ALL_IN = 103;

	/**
	 * 用户id
	 */
	private long userId;

	/**
	 * 订单在支付端（微信、支付宝）对应的支付订单id
	 */
	private String payOrderId;

	/**
	 * 商品详情
	 */
	private String body;

	/**
	 * 总费用
	 */
	private double totalFee;

	/**
	 * 支付类型
	 */
	private int payType;

	/**
	 * 对外支付单号
	 */
	private String orderNum;

	/**
	 * 终端ip
	 */
	private String ip;

	/**
	 * 预支付交易会话标识
	 */
	private String preOrderId;
	private String openId;

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonProperty
	public long getUserId() {
		return userId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	@JsonProperty
	public String getPayOrderId() {
		return payOrderId;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@JsonProperty
	public String getBody() {
		return body;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	@JsonProperty
	public double getTotalFee() {
		return totalFee;
	}

	@JsonProperty
	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	@JsonProperty
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@JsonProperty
	public String getIp() {
		return ip;
	}

	public void setPreOrderId(String preOrderId) {
		this.preOrderId = preOrderId;
	}

	@JsonProperty
	public String getPreOrderId() {
		return preOrderId;
	}

	@JsonProperty
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
