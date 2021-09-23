package com.zuipin.pojo.enums;

/**
 * @author cjj purBillState 2014年5月28日上午8:44:09 TODO :功能 采购状态
 */
public enum OrderType {
	供应商代销(1), 供应商经销(2);
	private Integer	index;
	
	private OrderType(Integer index) {
		this.index = index;
	}
	
	public Integer getIndex() {
		return index;
	}
	
}
