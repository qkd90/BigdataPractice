package com.hmlyinfo.app.soutu.bargain.domain;

import com.hmlyinfo.base.persistent.BaseEntity;
import org.codehaus.jackson.annotate.JsonProperty;

public class BargainPlanTrip extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final int TYPE_SCENIC = 1;
	public static final int TYPE_RESTAURANT = 2;
	public static final int TYPE_HOTEL = 3;
	public static final int TYPE_TRANSIT = 4;

	private long bargainPlanId;
	private long bargainPlanDayId;
	private int type;
	private int day;
	private int index;
	private String name;
	private String time;
	private String description;

	public void setBargainPlanId(long bargainPlanId) {
		this.bargainPlanId = bargainPlanId;
	}

	@JsonProperty
	public long getBargainPlanId() {
		return bargainPlanId;
	}

	public void setBargainPlanDayId(long bargainPlanDayId) {
		this.bargainPlanDayId = bargainPlanDayId;
	}

	@JsonProperty
	public long getBargainPlanDayId() {
		return bargainPlanDayId;
	}

	public void setType(int type) {
		this.type = type;
	}

	@JsonProperty
	public int getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	@JsonProperty
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@JsonProperty
	public int getIndex() {
		return index;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
