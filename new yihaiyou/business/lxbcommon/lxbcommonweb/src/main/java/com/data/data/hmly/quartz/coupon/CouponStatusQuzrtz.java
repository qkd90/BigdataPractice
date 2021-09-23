package com.data.data.hmly.quartz.coupon;

import com.data.data.hmly.service.lxbcommon.CouponMgrService;
import com.data.data.hmly.service.lxbcommon.entity.Coupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.CouponStatus;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zzl on 2016/5/18.
 */
@Component
public class CouponStatusQuzrtz {

    @Resource
    private CouponMgrService couponMgrService;

    public void doRefreshCouponStatus() {
        List<Coupon> couponList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        Coupon condition = new Coupon();
        List<CouponStatus> statusList = new ArrayList<CouponStatus>();
        statusList.add(CouponStatus.open);
        condition.setNeededStatuses(statusList);
        while (true) {
            page = new Page(pageIndex, pageSize);
            // 取出需要更新状态的优惠券列表
            couponList = couponMgrService.getCouponList(condition, page);
            Iterator<Coupon> iterator = couponList.iterator();
            while (iterator.hasNext()) {
                Coupon coupon = iterator.next();
                Date nowTime = new Date();
                Date validEnd = coupon.getValidEnd();
                // 处理过期的优惠券
                if (nowTime.getTime() >= validEnd.getTime()) {
                    coupon.setStatus(CouponStatus.expired);
                    couponMgrService.updateSingleCoupon(coupon);
                }
            }
            // 本次已处理总订单详情数目
            processed += couponList.size();
            if (processed >= total) {
                break;
            }
            pageIndex += 1;
            couponList.clear();
        }
    }
}
