package com.spark.service.hbase.pojo;

import com.zuipin.util.StringUtils;

public enum WebRequestType {
	/**
	 * 普通统计
	 */
	NORMAL,
	/**
	 * 产品页面
	 */
	PRODUCT,
	/**
	 * 下单成功页面
	 */
	ORDER_SUCCESS,
	/**
	 * 注册页面
	 */
	REG_PAGE,
	/**
	 * 注册成功页面
	 */
	REG_SUCCESS,
	/**
	 * 购物车
	 */
	CART_LIST,
	/**
	 * 搜索页面
	 */
	SEARCH,
	/**
	 * 购物车页
	 */
	CART,
	/**
	 * 曝光的商品请求
	 */
	PAOGUAN,
	/**
	 * 促销页
	 */
	CUXIAO;
	
	public static WebRequestType getType(String type) {
		if (StringUtils.isBlank(type)) {
			return WebRequestType.NORMAL;
		}
		for (WebRequestType item : WebRequestType.values()) {
			if (type.equals(item.name())) {
				return item;
			}
		}
		return WebRequestType.NORMAL;
	}
}
