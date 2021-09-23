package com.hmlyinfo.app.soutu.plan.domain.type;

public enum TravelType {
	INVALID(0), TRANSIT(1), DRIVING(2), WALKING(3);

	private int value = 0;

	private TravelType(int value) {
		this.value = value;
	}

	public String toString() {
		return this.value + "";
	}

	public int value() {
		return value;
	}

	public static TravelType valueOf(int value) {
		switch (value) {
			case 0:
				return INVALID;
			case 1:
				return TRANSIT;
			case 2:
				return DRIVING;
			case 3:
				return WALKING;
			default:
				return null;
		}
	}


}