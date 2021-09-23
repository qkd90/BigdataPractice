package com.hmlyinfo.app.soutu.signet.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class SignetRecord extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 *
	 */
	private long bookId;

	/**
	 *
	 */
	private long merchantId;

	private String signetFlag;


	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	@JsonProperty
	public long getBookId() {
		return bookId;
	}

	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}

	@JsonProperty
	public long getMerchantId() {
		return merchantId;
	}

	@JsonProperty
	public String getSignetFlag() {
		return signetFlag;
	}

	public void setSignetFlag(String signetFlag) {
		this.signetFlag = signetFlag;
	}


}
