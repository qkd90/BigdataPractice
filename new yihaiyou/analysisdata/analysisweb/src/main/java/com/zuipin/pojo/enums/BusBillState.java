package com.zuipin.pojo.enums;

/**
 * @author cjj purBillState 2014年5月28日上午8:44:09 TODO :功能 采购状态
 */
public enum BusBillState {
	待发货(1), 已发货(2), 待签收(3), 已签收(4), 已完成(5), 已取消(6), 未知状态(7);
	private Integer	index;
	
	private BusBillState(Integer index) {
		this.index = index;
	}
	
	public Integer getIndex() {
		return index;
	}
}
