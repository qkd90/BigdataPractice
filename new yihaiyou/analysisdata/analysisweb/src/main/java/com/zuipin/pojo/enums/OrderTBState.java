package com.zuipin.pojo.enums;

/**
 * @author cjj
 * @date 2014年10月15日
 * @TODO 订单颜色状态
 */
public enum OrderTBState {
	blue(1), gray(2), red(3), green(4);
	private Integer	index;
	
	private OrderTBState(Integer index) {
		this.index = index;
	}
	
	public Integer getIndex() {
		return index;
	}
	
}
