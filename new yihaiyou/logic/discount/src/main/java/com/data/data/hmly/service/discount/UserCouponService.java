package com.data.data.hmly.service.discount;

import com.data.data.hmly.service.discount.dao.UserCouponDao;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangpeijie on 2016-05-09,0009.
 */
@Service
public class UserCouponService {
    @Resource
    private UserCouponDao userCouponDao;

    public UserCoupon get(Long id) {
        return userCouponDao.load(id);
    }

    public void update(UserCoupon userCoupon) {
        userCouponDao.update(userCoupon);
    }

    public List<UserCoupon> list(UserCoupon userCoupon, Page page, String... orderProperties) {
        Criteria<UserCoupon> criteria = createCriteria(userCoupon, orderProperties);
        if (page != null) {
            return userCouponDao.findByCriteria(criteria, page);
        } else {
            return userCouponDao.findByCriteria(criteria);
        }
    }

    public Long count(UserCoupon userCoupon) {
        Criteria<UserCoupon> criteria = createCriteria(userCoupon);
        criteria.setProjection(Projections.rowCount());
        return userCouponDao.findLongCriteria(criteria);
    }

    public void save(UserCoupon userCoupon) {
        userCouponDao.save(userCoupon);
    }

    private Criteria<UserCoupon> createCriteria(UserCoupon userCoupon, String... orderProperties) {
        Criteria<UserCoupon> criteria = new Criteria<UserCoupon>(UserCoupon.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.desc(orderProperties[0]));
        }
        if (userCoupon.getMember() != null) {
            criteria.eq("member.id", userCoupon.getMember().getId());
        }
        if (userCoupon.getCoupon() != null) {
            if (userCoupon.getCoupon().getId() != null) {
                criteria.eq("coupon.id", userCoupon.getCoupon().getId());
            }
            if (StringUtils.isNotBlank(userCoupon.getCoupon().getLimitProductTypes())) {
                criteria.createCriteria("coupon", "coupon", JoinType.INNER_JOIN);
                criteria.or(Restrictions.like("coupon.limitProductTypes", userCoupon.getCoupon().getLimitProductTypes()), Restrictions.eq("coupon.limitProductTypes", ""));
            }
        }
        if (userCoupon.getStatus() != null) {
            criteria.eq("status", userCoupon.getStatus());
        }
        if (userCoupon.getValidStart() != null) {
            criteria.add(Restrictions.le("validStart", userCoupon.getValidStart()));
        }
        if (userCoupon.getValidEnd() != null) {
            criteria.add(Restrictions.ge("validEnd", userCoupon.getValidEnd()));
        }
        return criteria;
    }
}
