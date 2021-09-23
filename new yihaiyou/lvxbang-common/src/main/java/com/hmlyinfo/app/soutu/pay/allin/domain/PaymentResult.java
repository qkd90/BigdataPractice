package com.hmlyinfo.app.soutu.pay.allin.domain;

import java.util.Date;

public class PaymentResult extends com.allinpay.ets.client.PaymentResult {


	private Long id;

	private Date createTime;

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		if (createTime == null) {
			createTime = new Date();
		}
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
