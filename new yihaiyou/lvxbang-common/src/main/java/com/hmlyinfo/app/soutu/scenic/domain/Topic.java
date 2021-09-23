package com.hmlyinfo.app.soutu.scenic.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class Topic extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private int cityCode;
	private String name;
	private transient List topicScenicList;

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	@JsonProperty
	public int getCityCode() {
		return cityCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	@JsonProperty
	public List getTopicScenicList() {
		return topicScenicList;
	}

	public void setTopicScenicList(List topicScenicList) {
		this.topicScenicList = topicScenicList;
	}
}
