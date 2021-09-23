package com.hmlyinfo.app.soutu.scenicTicket.domain;

import java.io.Serializable;

public class WxToken implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5471318495368427281L;


	private String accessToken;

	private String jsapiTicket;

	private long timestamp;

	private String noncestr;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}


}
