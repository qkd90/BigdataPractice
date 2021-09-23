package com.data.data.hmly.service.discount;

import com.data.data.hmly.service.discount.dao.CouponDao;
import com.data.data.hmly.service.lxbcommon.entity.Coupon;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by huangpeijie on 2016-05-09,0009.
 */
@Service
public class CouponService {
    @Resource
    private CouponDao couponDao;

    public Coupon get(Long id) {
        return couponDao.load(id);
    }
}
