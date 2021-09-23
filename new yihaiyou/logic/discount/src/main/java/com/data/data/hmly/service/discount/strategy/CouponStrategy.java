package com.data.data.hmly.service.discount.strategy;

import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponDiscountType;
import com.data.data.hmly.service.lxbcommon.entity.enums.UserCouponStatus;
import com.data.data.hmly.service.order.entity.Order;
import com.zuipin.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-05-09,0009.
 */
public class CouponStrategy implements DiscountStrategy {
    private Order order;
    private UserCoupon userCoupon;

    public CouponStrategy(Order order, UserCoupon userCoupon) {
        this.order = order;
        this.userCoupon = userCoupon;
    }

    @Override
    public Boolean checkUse() {
        if (!order.getUser().getId().equals(userCoupon.getMember().getId())) {
            return false;
        }
        if (!userCoupon.getStatus().equals(UserCouponStatus.unused)) {
            return false;
        }
        if (StringUtils.isNotBlank(userCoupon.getCoupon().getLimitProductTypes())) {
            List<String> typeList = Arrays.asList(userCoupon.getCoupon().getLimitProductTypes().split(","));
            String type = order.getOrderType().toString();
            if ("ticket".equals(type)) {
                type = "scenic";
            }
            if (!typeList.contains(type)) {
                return false;
            }
        }
        if (new Date().before(userCoupon.getValidStart()) || new Date().after(userCoupon.getValidEnd())) {
            return false;
        }
        if (order.getPrice() < userCoupon.getCoupon().getUseCondition()) {
            return false;
        }
        return true;
    }

    @Override
    public Float discount() {
        if (!checkUse()) {
            return order.getPrice();
        }
        if (userCoupon.getCoupon().getCouponDiscountType().equals(CouponDiscountType.money)) {
            return order.getPrice() - userCoupon.getCoupon().getFaceValue();
        }
        return order.getPrice();
    }
}
