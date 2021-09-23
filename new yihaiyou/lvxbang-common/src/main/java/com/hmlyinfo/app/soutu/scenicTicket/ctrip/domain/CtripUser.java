package com.hmlyinfo.app.soutu.scenicTicket.ctrip.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class CtripUser extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long uid;

	private String ctripUid;

	@JsonProperty
	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	@JsonProperty
	public String getCtripUid() {
		return ctripUid;
	}

	public void setCtripUid(String ctripUid) {
		this.ctripUid = ctripUid;
	}

}
