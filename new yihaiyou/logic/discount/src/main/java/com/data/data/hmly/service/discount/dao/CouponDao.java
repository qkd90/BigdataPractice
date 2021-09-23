package com.data.data.hmly.service.discount.dao;

import com.data.data.hmly.service.lxbcommon.entity.Coupon;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

/**
 * Created by huangpeijie on 2016-05-09,0009.
 */
@Repository
public class CouponDao extends DataAccess<Coupon> {

    private Criteria<Coupon> createCriteria(Coupon coupon) {
        Criteria<Coupon> criteria = new Criteria<>(Coupon.class);

        return criteria;
    }
}