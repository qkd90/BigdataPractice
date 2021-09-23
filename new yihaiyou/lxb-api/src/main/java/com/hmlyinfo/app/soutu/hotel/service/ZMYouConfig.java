package com.hmlyinfo.app.soutu.hotel.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guoshijie on 2014/7/17.
 */
public class ZMYouConfig {

    /**
     * 酒店Api固定方法，不可改动
     */
    private static final String action = "GET_HOTEL_WITH_ROOM_LIST";
    /**
     * 页码，默认为1
     */
    private static final int currentPageNum = 1;
    /**
     * 每页显示数量
     */
    private static final int pageSize = 10;
    /**
     * 查询的最低价格
     */
    private static final int lowestPrice = 0;
    /**
     * 查询的最高价格
     */
    private static final int highestPrice = Integer.MAX_VALUE;
    /**
     * 查询的酒店星级，默认包含1~5星
     */
    private static final int[] starList = {1, 2, 3, 4, 5};
    /**
     * 查询的酒店类型，1: 星级酒店，2：经济型，3：快捷连锁
     */
    private static final int[] typeList = {1, 2, 3};
    /**
     * 省份Id，默认为福建省
     */
    private static final long provinceId = 350000;
    /**
     * 城市Id，默认为厦门市
     */
    private static final long cityId = 350200;

    private Map<String, Object> body = new HashMap<String, Object>();

    public ZMYouConfig() {
        body.put("action", action);
        body.put("currentPageNum", currentPageNum);
        body.put("pageSize", pageSize);
        body.put("lowestPrice", lowestPrice);
        body.put("highestPrice", highestPrice);
        body.put("starList", starList);
        body.put("typeList", typeList);
        body.put("provinceId", provinceId);
        body.put("cityId", cityId);
    }

    public Map<String, Object> getBody() {
        return body;
    }
}
