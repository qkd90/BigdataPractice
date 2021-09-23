package com.zuipin.pojo.enums;

public enum ProductUnit {
	
	斤(1), 条(2), 泡(3), 袋(4), 千克(5), 支(6), 只(7), 组(8), 个(9), 和(10), 公斤(11), 片(12), 对(13), 台(14), 块(15), 尊(16), 张(17), 砖(18), 罐(19), 根(20), 饼(21), 盒(22), 提(23), 把(24), 份(25), 次(26), 瓶(
			27), 套(28), 快(29), 串(30), 包(31), 管(32), 各(33), 件(34), 听(35), 站(36), 卷(37), 及in(38), pcs(39);
	
	private Integer	index;
	
	private ProductUnit(Integer index) {
		this.index = index;
	}
	
	public Integer getIndex() {
		return index;
	}
}
