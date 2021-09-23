package com.hmlyinfo.app.soutu.plan.domain.type;

public enum TripType {
	SCENIC(1), RESTAURANT(2), HOTEL(3), STATION(4);

	private int value = 0;

	private TripType(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}

	public static TripType valueOf(int value) {
		switch (value) {
			case 1:
				return SCENIC;
			case 2:
				return RESTAURANT;
			case 3:
				return HOTEL;
			case 4:
				return STATION;
			default:
				return null;
		}
	}

	public String toString() {
		return value + "";
	}
}