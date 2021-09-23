package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.discount.UserCouponService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.lxbcommon.entity.Coupon;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponStatus;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponValidType;
import com.data.data.hmly.service.lxbcommon.entity.enums.UserCouponStatus;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-05-09,0009.
 */
@Service
public class DiscountService {
//    @Resource
//    private CouponService couponService;
    @Resource
    private UserCouponService userCouponService;

    public void addUserCoupon(Coupon coupon, Member member) {
        if (coupon.getStatus().equals(CouponStatus.closed)) {
            return;
        }
        UserCoupon search = new UserCoupon();
        search.setCoupon(coupon);
        search.setMember(member);
        Long count = userCouponService.count(search);
        if (count >= coupon.getReceiveLimit()) {
            return;
        }
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setCoupon(coupon);
        userCoupon.setMember(member);
        userCoupon.setStatus(UserCouponStatus.unused);
        userCoupon.setValidStart(coupon.getValidStart());
        Date end = coupon.getValidEnd();
        if (CouponValidType.days.equals(coupon.getCouponValidType())) {
            Date date = DateUtils.add(new Date(), Calendar.DATE, coupon.getValidDays());
            if (date.before(end)) {
                end = date;
            }
        } else if (CouponValidType.forever.equals(coupon.getCouponValidType())) {
            end = null;
        }
        userCoupon.setValidEnd(end);
        userCouponService.save(userCoupon);
    }

    public List<UserCoupon> listUserCoupon(ProductType type, Member member) {
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setMember(member);
        userCoupon.setStatus(UserCouponStatus.unused);
        userCoupon.setValidStart(new Date());
        userCoupon.setValidEnd(new Date());
        Coupon coupon = new Coupon();
        coupon.setLimitProductTypes(type.toString());
        userCoupon.setCoupon(coupon);

        List<UserCoupon> userCouponList = userCouponService.list(userCoupon, null);

        for (UserCoupon uc : userCouponList) {
            completeTypes(uc);
        }
        return userCouponList;
    }

    public UserCoupon completeTypes(UserCoupon userCoupon) {
        String productTypes = userCoupon.getCoupon().getLimitProductTypes();
        if (StringUtils.isBlank(productTypes)) {
            userCoupon.setLimitProductTypes("线路/门票/酒店/飞机/火车");
        } else {
            userCoupon.setLimitProductTypes(productTypes.replace("plan", "线路").replace("flight", "飞机").replace("train", "火车").replace("scenic", "门票").replace("hotel", "酒店").replace(",", "/"));
        }
        return userCoupon;
    }
}
