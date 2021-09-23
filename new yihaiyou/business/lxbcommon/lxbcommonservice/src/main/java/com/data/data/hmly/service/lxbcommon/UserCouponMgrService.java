package com.data.data.hmly.service.lxbcommon;

import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.lxbcommon.dao.CouponMgrDao;
import com.data.data.hmly.service.lxbcommon.dao.UserCouponMgrDao;
import com.data.data.hmly.service.lxbcommon.entity.Coupon;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponStatus;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponValidType;
import com.data.data.hmly.service.lxbcommon.entity.enums.UserCouponStatus;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zzl on 2016/5/11.
 */
@Service
public class UserCouponMgrService {

    @Resource
    private UserCouponMgrDao userCouponMgrDao;
    @Resource
    private CouponMgrDao couponMgrDao;
    @Resource
    private MemberService memberService;

    public void update(UserCoupon userCoupon) {
        userCouponMgrDao.update(userCoupon);
    }

    public List<UserCoupon> getUserCouponList(UserCoupon condition, Page page, String...orderProperties) {
        Criteria<UserCoupon> criteria = createCriteria(condition, orderProperties);
        if (page != null) {
            return userCouponMgrDao.findByCriteria(criteria, page);
        }
        return userCouponMgrDao.findByCriteria(criteria);
    }

    /**
     * 获取某张优惠券已经领取的数量
     * @param coupon
     * @return
     */
    public Integer getReceivedNum(Coupon coupon) {
        String countSql = "select count(id) as result from lxb_user_coupon where coupon_id=?";
        BigDecimal receivedNum = userCouponMgrDao.findIntegerBySQL(countSql, coupon.getId());
        return receivedNum == null ? BigDecimal.ZERO.intValue() : receivedNum.intValue();
    }

    /**
     * 获取某种优惠券已经领取的人次数量
     * @param coupon
     * @return
     */
    public Integer getReceivedPersonNum(Coupon coupon) {
        String countSql = "select count(1) as result from "
                + "(select count(id) from lxb_user_coupon where coupon_id=? group by user_id) as r";
        BigDecimal receivedPersonNum = userCouponMgrDao.findIntegerBySQL(countSql, coupon.getId());
        return receivedPersonNum == null ? BigDecimal.ZERO.intValue() : receivedPersonNum.intValue();
    }

    public boolean doSendCoupon(String couponIds, String memberIds) {
        try {
            String[] couponIdArr = couponIds.replaceAll("\\s", "").split(",");
            String[] memberIdArr = memberIds.replaceAll("\\s", "").split(",");
            for (String couponIdStr : couponIdArr) {
                Long couponId = Long.parseLong(couponIdStr);
                Coupon coupon = couponMgrDao.load(couponId);
                for (String memberIdStr : memberIdArr) {
                    if (coupon.getAvailableNum() <= 0) {
                        break;
                    }
                    Long memberId = Long.parseLong(memberIdStr);
                    Member member = memberService.get(memberId);
                    boolean isCanReceive = isCanReceive(coupon, member);
                    if (member == null) {
                        continue;
                    }
                    if (!isCanReceive) {
                        continue;
                    }
                    Date nowTime = new Date();
                    UserCoupon userCoupon = new UserCoupon();
                    userCoupon.setCoupon(coupon);
                    userCoupon.setMember(member);
                    if (CouponValidType.range.equals(coupon.getCouponValidType())) {
                        userCoupon.setValidStart(coupon.getValidStart());
                        userCoupon.setValidEnd(coupon.getValidEnd());
                    } else if (CouponValidType.days.equals(coupon.getCouponValidType())) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(nowTime);
                        calendar.add(Calendar.DATE, coupon.getValidDays());
                        userCoupon.setValidStart(nowTime);
                        userCoupon.setValidEnd(calendar.getTime());
                    }
                    if (userCoupon.getValidEnd().getTime() > nowTime.getTime()) {
                        userCoupon.setStatus(UserCouponStatus.unused);
                    } else {
                        userCoupon.setStatus(UserCouponStatus.expired);
                    }
                    userCoupon.setCreateTime(nowTime);
                    userCouponMgrDao.save(userCoupon);
                    coupon.setAvailableNum(coupon.getAvailableNum() - 1);
                    couponMgrDao.update(coupon);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用户是否可以领取优惠券查询
     * @param coupon
     * @param member
     * @return
     */
    private boolean isCanReceive(Coupon coupon, Member member) {
        Criteria<UserCoupon> userCouponCriteria = new Criteria<>(UserCoupon.class);
        userCouponCriteria.createCriteria("coupon", "coupon");
        userCouponCriteria.createCriteria("member", "member");
        userCouponCriteria.eq("coupon.id", coupon.getId());
        userCouponCriteria.eq("member.id", member.getId());
        userCouponCriteria.ne("status", UserCouponStatus.del);
        userCouponCriteria.setProjection(Projections.groupProperty("id"));
        List<?> alreadyReceiveIds = userCouponMgrDao.findByCriteria(userCouponCriteria);
        // 用户已经领取该优惠券的数量
        Integer nowReceiveNum = alreadyReceiveIds.size();
        // 该优惠券每个人限制领取的数量
        Integer receiveLimit = coupon.getReceiveLimit();
        // 优惠券无领取限制
        if (receiveLimit == null) {
            return true;
        }
        // 用户未领取该优惠券
        if (alreadyReceiveIds.isEmpty()) {
            return true;
        }
        // 用户领取的数量未达到领取限制
        if (nowReceiveNum < receiveLimit) {
            return true;
        }
        // 该优惠券不可用
        if (CouponStatus.closed.equals(coupon.getStatus())) {
            return false;
        }
        return false;
    }

    public Map<String, Object> doSetavailable(String idStr) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (!StringUtils.hasText(idStr)) {
            result.put("success", false);
            result.put("msg", "用户优惠券ID不可为空!");
        }
        Long id = Long.parseLong(idStr);
        UserCoupon userCoupon = userCouponMgrDao.load(id);
        if (UserCouponStatus.expired.equals(userCoupon.getStatus())) {
            result.put("success", false);
            result.put("msg", "用户优惠券已经过期,不可启用!");
        }
        if (UserCouponStatus.del.equals(userCoupon.getStatus())) {
            result.put("success", false);
            result.put("msg", "用户优惠券已经删除,不可启用!");
        }
        if (UserCouponStatus.used.equals(userCoupon.getStatus())) {
            result.put("success", false);
            result.put("msg", "用户优惠券已经使用,不可启用!");
        }
        userCoupon.setStatus(UserCouponStatus.unused);
        userCouponMgrDao.update(userCoupon);
        result.put("success", true);
        result.put("msg", "已启用该用户优惠券!");
        return result;
    }

    public Map<String, Object> doSetunavailable(String idStr) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (!StringUtils.hasText(idStr)) {
            result.put("success", false);
            result.put("msg", "用户优惠券ID不可为空!");
        }
        Long id = Long.parseLong(idStr);
        UserCoupon userCoupon = userCouponMgrDao.load(id);
        if (UserCouponStatus.expired.equals(userCoupon.getStatus())) {
            result.put("success", false);
            result.put("msg", "用户优惠券已经过期,无需禁用!");
        }
        if (UserCouponStatus.del.equals(userCoupon.getStatus())) {
            result.put("success", false);
            result.put("msg", "用户优惠券已经删除,无需禁用!");
        }
        if (UserCouponStatus.used.equals(userCoupon.getStatus())) {
            result.put("success", false);
            result.put("msg", "用户优惠券已经使用,无需禁用!");
        }
        userCoupon.setStatus(UserCouponStatus.unavailable);
        userCouponMgrDao.update(userCoupon);
        result.put("success", true);
        result.put("msg", "已禁用该用户优惠券!");
        return result;
    }

    public Criteria<UserCoupon> createCriteria(UserCoupon condition, String...orderProperties) {
        Criteria<UserCoupon> criteria = new Criteria<UserCoupon>(UserCoupon.class);
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
        if (condition.getCoupon() != null) {
            criteria.createCriteria("coupon", "coupon");
            if (StringUtils.hasText(condition.getCoupon().getName())) {
                criteria.like("coupon.name", condition.getCoupon().getName());
            }
        }
        if (condition.getMember() != null) {
            criteria.createCriteria("member", "member");
            if (StringUtils.hasText(condition.getMember().getUserName())) {
                criteria.like("member.userName", condition.getMember().getUserName());
            }
            if (StringUtils.hasText(condition.getMember().getNickName())) {
                criteria.like("member.nickName", condition.getMember().getNickName());
            }
        }
        if (condition.getStatus() != null) {
            criteria.eq("status", condition.getStatus());
        }
        if (condition.getNeededStatuses() != null && !condition.getNeededStatuses().isEmpty()) {
            criteria.in("status", condition.getNeededStatuses());
        }
        return criteria;
    }

    /**
     * 功能描述：保存关注微信用户领取的红包关系
     * @param member
     */
    public void saveWechatUserCoupon(Member member) {
        List<Coupon> couponList = couponMgrDao.findFirstSubCoupon(new Date());
        if (!couponList.isEmpty()) {
            List<UserCoupon> userCoupons = new ArrayList<UserCoupon>();
            for (Coupon coupon : couponList) {
                Integer maxCount = coupon.getReceiveLimit();
                Integer restCount = coupon.getAvailableNum();
                if (restCount > maxCount) {
                    for (int i = 0; i < maxCount; i++) {
                        UserCoupon userCoupon = new UserCoupon();
                        checkValidTime(coupon, userCoupon);
                        userCoupon.setCoupon(coupon);
                        userCoupon.setMember(member);
                        userCoupon.setStatus(UserCouponStatus.unused);
                        userCoupon.setCreateTime(new Date());
                        userCoupons.add(userCoupon);
                    }
                    restCount = restCount - maxCount;
                    coupon.setAvailableNum(restCount);
                    couponMgrDao.update(coupon);
                }
            }
            if (!userCoupons.isEmpty()) {
                userCouponMgrDao.save(userCoupons);
            }
        }
    }

    public void checkValidTime(Coupon coupon, UserCoupon userCoupon) {
        if (coupon.getCouponValidType() == CouponValidType.days) {
            Date endDate = DateUtils.getEndDay(new Date(), coupon.getValidDays());
            userCoupon.setValidStart(new Date());
            userCoupon.setValidEnd(endDate);
        } else if (coupon.getCouponValidType() == CouponValidType.range) {
            userCoupon.setValidEnd(coupon.getValidEnd());
            userCoupon.setValidStart(coupon.getValidStart());
        } else {
            userCoupon.setValidEnd(null);
            userCoupon.setValidStart(null);
        }
    }
}
