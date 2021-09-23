package com.data.data.hmly.service.lxbcommon.dao;

import com.data.data.hmly.service.lxbcommon.entity.Coupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponStatus;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by zzl on 2016/5/9.
 */
@Component
public class CouponMgrDao extends DataAccess<Coupon> {

    /**
     * 功能描述：获取关注时红包
     * @return
     * @param date
     */
    public List<Coupon> findFirstSubCoupon(Date date) {
        Criteria<Coupon> criteria = new Criteria<Coupon>(Coupon.class);
        criteria.Add(Restrictions.or(Restrictions.eq("name", "线路红包券"), Restrictions.eq("name", "门票红包券")));
        criteria.eq("status", CouponStatus.open);
        if (date != null) {
            criteria.le("validStart", date);
            criteria.ge("validEnd", date);
        }
        return findByCriteria(criteria);
    }
}
