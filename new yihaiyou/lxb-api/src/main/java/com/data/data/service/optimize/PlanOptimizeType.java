package com.data.data.service.optimize;

public enum PlanOptimizeType {

	/**
	 * 指定某天优化
	 */
	OPTIMIZE_POINT_DAY,
	/**
	 * 景点数确定
	 */
	OPTIMIZE_SCENIC_CONFIRM,
	/**
	 * 天数确定
	 */
	OPTIMIZE_DAY_CONFIRM;

	public static PlanOptimizeType find(int ordinal) {
		for (PlanOptimizeType item : values()) {
			if (item.ordinal() == ordinal) {
				return item;
			}
		}
		return null;
	}

}
