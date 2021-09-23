package com.data.data.hmly.service.lxbcommon;

import com.data.data.hmly.service.lxbcommon.dao.CouponMgrDao;
import com.data.data.hmly.service.lxbcommon.entity.Coupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponDiscountType;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponReceiveLimitType;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponStatus;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponUseConditionType;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponValidType;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.CouponCodeUtils;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/5/9.
 */
@Service
public class CouponMgrService {

    @Resource
    private CouponMgrDao couponMgrDao;

    public Coupon get(Long id) {
        return couponMgrDao.load(id);
    }

    public Map<String, Object> doCloseCoupon(Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Coupon coupon = couponMgrDao.load(id);
            coupon.setStatus(CouponStatus.closed);
            couponMgrDao.update(coupon);
            result.put("success", true);
            result.put("msg", "优惠券下架成功!");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "优惠券下架失败!");
        }
        return result;
    }

    public Map<String, Object> doOpenCoupon(Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        Coupon coupon = couponMgrDao.load(id);
        Date nowTime = new Date();
        Date validEnd = coupon.getValidEnd();
        if (validEnd.getTime() > nowTime.getTime()) {
            coupon.setStatus(CouponStatus.open);
            couponMgrDao.update(coupon);
            result.put("success", true);
            result.put("msg", "优惠券上架成功!");
        } else {
            result.put("success", false);
            result.put("msg", "该优惠券已经过期,不可以上架!");
        }
        return result;
    }

    public void updateSingleCoupon(Coupon coupon) {
        couponMgrDao.update(coupon);
    }

    public boolean update(Coupon targetCoupon, Coupon sourceCoupon) {
        try {
            if (StringUtils.hasText(targetCoupon.getName())) {
                sourceCoupon.setName(targetCoupon.getName());
            }
            if (targetCoupon.getFaceValue() != null && targetCoupon.getFaceValue() > 0) {
                sourceCoupon.setFaceValue(targetCoupon.getFaceValue());
            }
            if (targetCoupon.getCouponDiscountType() != null) {
                sourceCoupon.setCouponDiscountType(targetCoupon.getCouponDiscountType());
                if (CouponDiscountType.money.equals(targetCoupon.getCouponDiscountType())) {
                    sourceCoupon.setMaxDiscount(null);
                }
            }
            if (targetCoupon.getCirculation() != null && targetCoupon.getCirculation() > 0) {
                sourceCoupon.setCirculation(targetCoupon.getCirculation());
            }
            if (targetCoupon.getCouponUseConditionType() != null) {
                sourceCoupon.setCouponUseConditionType(targetCoupon.getCouponUseConditionType());
                if (CouponUseConditionType.none.equals(targetCoupon.getCouponUseConditionType())) {
                    sourceCoupon.setUseCondition(null);
                }
            }
            if (targetCoupon.getUseCondition() != null && targetCoupon.getUseCondition() > 0) {
                sourceCoupon.setUseCondition(targetCoupon.getUseCondition());
            }
            if (targetCoupon.getValidDays() != null && targetCoupon.getValidDays() > 0) {
                sourceCoupon.setValidDays(targetCoupon.getValidDays());
            }
            if (targetCoupon.getValidStart() != null && targetCoupon.getValidEnd() != null) {
                sourceCoupon.setValidStart(targetCoupon.getValidStart());
                sourceCoupon.setValidEnd(targetCoupon.getValidEnd());
            }
            if (targetCoupon.getCouponValidType() != null) {
                sourceCoupon.setCouponValidType(targetCoupon.getCouponValidType());
                if (CouponValidType.range.equals(targetCoupon.getCouponValidType())
                        || CouponValidType.forever.equals(targetCoupon.getCouponValidType())) {
                    sourceCoupon.setValidDays(null);
                }
            }
            if (StringUtils.hasText(targetCoupon.getLimitProductTypes())) {
                sourceCoupon.setLimitProductTypes(targetCoupon.getLimitProductTypes());
            }
            if (StringUtils.hasText(targetCoupon.getLimitTargetIds())) {
                sourceCoupon.setLimitTargetIds(targetCoupon.getLimitTargetIds());
            }
            if (StringUtils.hasText(targetCoupon.getInstructions())) {
                sourceCoupon.setInstructions(targetCoupon.getInstructions());
            }
            if (StringUtils.hasText(targetCoupon.getLimitInfo())) {
                sourceCoupon.setLimitInfo(targetCoupon.getLimitInfo());
            }
            if (targetCoupon.getCouponReceiveLimitType() != null) {
                sourceCoupon.setCouponReceiveLimitType(targetCoupon.getCouponReceiveLimitType());
                if (CouponReceiveLimitType.none.equals(targetCoupon.getCouponReceiveLimitType())
                        || CouponReceiveLimitType.code.equals(targetCoupon.getCouponReceiveLimitType())) {
                    sourceCoupon.setReceiveLimit(null);
                }
            }
            if (targetCoupon.getReceiveLimit() != null && targetCoupon.getReceiveLimit() > 0) {
                sourceCoupon.setReceiveLimit(targetCoupon.getReceiveLimit());
            }
            if (targetCoupon.getAvailableNum() != null && targetCoupon.getAvailableNum() > 0) {
                sourceCoupon.setAvailableNum(targetCoupon.getAvailableNum());
            }
            if (targetCoupon.getMaxDiscount() != null && targetCoupon.getMaxDiscount() > 0) {
                sourceCoupon.setMaxDiscount(targetCoupon.getMaxDiscount());
            }
            if (targetCoupon.getStatus() != null) {
                Date nowTime = new Date();
                Date validEnd = targetCoupon.getValidEnd();
                if (validEnd.getTime() <= nowTime.getTime()) {
                    sourceCoupon.setStatus(CouponStatus.expired);
                } else {
                    sourceCoupon.setStatus(targetCoupon.getStatus());
                }
            }
            couponMgrDao.update(sourceCoupon);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean save(Coupon coupon) {
        try {
            coupon.setAvailableNum(coupon.getCirculation());
            coupon.setCreateTime(new Date());
            coupon.setCouponCode(CouponCodeUtils.getCouponCode());
            couponMgrDao.save(coupon);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Coupon> getCouponList(Coupon condition, Page page, String...orderProperties) {
        Criteria<Coupon> criteria = createCriteria(condition, orderProperties);
        if (page != null) {
            return couponMgrDao.findByCriteria(criteria, page);
        }
        return couponMgrDao.findByCriteria(criteria);
    }

    private Criteria<Coupon> createCriteria(Coupon condition, String...orderProperties) {
        Criteria<Coupon> criteria = new Criteria<Coupon>(Coupon.class);
        if (orderProperties != null) {
            if (orderProperties.length > 1 && orderProperties[0] != null && orderProperties[1] != null) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            } else if (orderProperties.length == 1 && orderProperties[0] != null) {
                criteria.orderBy(orderProperties[0], "desc");
            }
        }
        if (condition == null) {
            return criteria;
        }
        if (StringUtils.hasText(condition.getName())) {
            criteria.like("name", condition.getName(), MatchMode.ANYWHERE);
        }
        if (condition.getStatus() != null) {
            criteria.eq("status", condition.getStatus());
        }
        if (condition.getNeededStatuses() != null && !condition.getNeededStatuses().isEmpty()) {
            criteria.in("status", condition.getNeededStatuses());
        }
        return criteria;
    }
}
