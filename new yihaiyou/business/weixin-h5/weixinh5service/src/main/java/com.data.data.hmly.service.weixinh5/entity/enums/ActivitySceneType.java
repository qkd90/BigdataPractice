package com.data.data.hmly.service.weixinh5.entity.enums;

/**
 * Created by dy on 2016/2/17.
 */
public enum ActivitySceneType {

    attention_shop("关注过本店的客户"),old_buyer("购买过本店商品的客户"),ctrip_vip("携程会员"),customer_no_limit("客户类型无限制"),buy_product("购买商品"),user_register("新用户注册"),comment_product("点评商品"),index_self_get("网店首页自主领取");

    private String description;

    ActivitySceneType(String description) {
        this.description = description;
    }

    public String getDescription() {

        return description;
    }
}
