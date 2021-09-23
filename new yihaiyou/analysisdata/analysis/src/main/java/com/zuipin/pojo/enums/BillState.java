package com.zuipin.pojo.enums;

/**
 * @author cjj purBillState 2014年5月28日上午8:44:09 TODO :功能
 */
public enum BillState {
	// 订单状态 能用的几种订单状态：
	新建(0), //
	待确认(1), // 客服确认
	待通知发货(2), // 客服通知仓库发货
	待审核(3), // 仓库审核
	审核待打印(4), //
	缺货订单(5), //
	打印待发货(6), //
	缺货已打印(7), //
	已生成总拣单(8), //
	总拣完成(9), //
	分拣完成(10), //
	待收款(11), // [已出库改成待收款用于门店销售单出库用]
	完成(12), //
	中止(13), //
	退换货待处理(14), //
	退换货已处理(15), //
	备单(16), //
	挂单(17), //
	// /////////////////以下可废弃//////////////
	正在分拣(18);
	private Integer	index;
	
	private BillState(Integer index) {
		this.index = index;
	}
	
	public Integer getIndex() {
		return index;
	}
	
	public static BillState findBillState(String str) {
		for (BillState state : BillState.values()) {
			if (state.name().equals(str))
				return state;
		}
		return null;
	}
	
	public static void main(String[] args) {
		BillState findBillState = findBillState("已签收");
		System.out.println(findBillState);
	}
	
}
