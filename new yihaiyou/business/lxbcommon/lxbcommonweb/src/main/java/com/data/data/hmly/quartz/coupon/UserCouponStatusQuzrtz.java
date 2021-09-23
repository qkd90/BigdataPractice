package com.data.data.hmly.quartz.coupon;

import com.data.data.hmly.service.lxbcommon.UserCouponMgrService;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.UserCouponStatus;
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
public class UserCouponStatusQuzrtz {

    @Resource
    private UserCouponMgrService userCouponMgrService;


    public void doRefreshUserCouponStatus() {
        List<UserCoupon> userCouponList;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        Integer processed = 0;
        Integer total = 0;
        Page page;
        UserCoupon condition = new UserCoupon();
        List<UserCouponStatus> statusList = new ArrayList<UserCouponStatus>();
        statusList.add(UserCouponStatus.unavailable);
        statusList.add(UserCouponStatus.del);
        statusList.add(UserCouponStatus.unused);
        condition.setNeededStatuses(statusList);
        while (true) {
            page = new Page(pageIndex, pageSize);
            // 取出需要更新状态的用户优惠券列表
            userCouponList = userCouponMgrService.getUserCouponList(condition, page);
            Iterator<UserCoupon> iterator = userCouponList.iterator();
            while (iterator.hasNext()) {
                UserCoupon userCoupon = iterator.next();
                Date nowTime = new Date();
                Date validEnd = userCoupon.getValidEnd();
                // 处理过期的用户优惠券
                if (nowTime.getTime() >= validEnd.getTime()) {
                    userCoupon.setStatus(UserCouponStatus.expired);
                    userCouponMgrService.update(userCoupon);
                }
            }
            // 本次已处理总订单详情数目
            processed += userCouponList.size();
            if (processed >= total) {
                break;
            }
            pageIndex += 1;
            userCouponList.clear();
        }
    }
}
