package com.hmlyinfo.app.soutu.plan.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class RecommendPlanTag extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 城市代码
	 */
	private long cityCode;

	/**
	 * 标签名称
	 */
	private String name;

	/**
	 * 临时的确认标签是否选中
	 */
	private transient boolean checked;


	public void setCityCode(long cityCode) {
		this.cityCode = cityCode;
	}

	@JsonProperty
	public long getCityCode() {
		return cityCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
