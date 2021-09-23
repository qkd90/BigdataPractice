package com.hmlyinfo.app.soutu.scenicTicket.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by guoshijie on 2015/4/13.
 */
public class ScenicTicket extends BaseEntity {


	public static String PARENT_YES = "T";
	public static String PARENT_NO = "F";

	private Long scenicId;

	private String parentFlag;

	@JsonProperty
	public Long getScenicId() {
		return scenicId;
	}

	public void setScenicId(Long scenicId) {
		this.scenicId = scenicId;
	}

	@JsonProperty
	public String getParentFlag() {
		return parentFlag;
	}

	public void setParentFlag(String parentFlag) {
		this.parentFlag = parentFlag;
	}
}
