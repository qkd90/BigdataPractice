package com.data.data.service.optimize;

public enum PlanHourType {

	HOUR0_0(0, 0), HOUR6_8(6, 8), HOUR9_11(9, 11), HOUR12(12, 16);
	private int	min, max;

	private PlanHourType(int min, int max) {
		// TODO Auto-generated constructor stub
		this.min = min;
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public static PlanHourType findHour(int index) {
		for (PlanHourType item : values()) {
			if (item.ordinal() == index) {
				return item;
			}
		}
		return null;
	}

	public int getAvg() {
		return (min + max) * 60 / 2;
	}

}
