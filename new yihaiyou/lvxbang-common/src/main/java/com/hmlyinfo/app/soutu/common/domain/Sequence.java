package com.hmlyinfo.app.soutu.common.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class Sequence extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String name;

	/**
	 *
	 */
	private int currentValue;

	/**
	 *
	 */
	private int increment;


	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}

	@JsonProperty
	public int getCurrentValue() {
		return currentValue;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	@JsonProperty
	public int getIncrement() {
		return increment;
	}
}
