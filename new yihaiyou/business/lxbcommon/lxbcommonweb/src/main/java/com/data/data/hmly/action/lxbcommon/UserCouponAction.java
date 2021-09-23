package com.data.data.hmly.action.lxbcommon;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.lxbcommon.vo.UserCouponVo;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.lxbcommon.UserCouponMgrService;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/5/11.
 */
public class UserCouponAction extends FrameBaseAction {


    @Resource
    private UserCouponMgrService userCouponMgrService;

    private int page;
    private int rows;
    private String orderProperty;
    private String orderType;

    private String couponIds;
    private String memberIds;


    private UserCoupon userCoupon = new UserCoupon();

    public Result list() {
        return dispatch("/WEB-INF/jsp/lxbcommon/coupon/user_coupon.jsp");
    }

    public Result getUserCouponList() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Page page = new Page(this.page, this.rows);
        List<UserCoupon> userCouponList = userCouponMgrService.getUserCouponList(userCoupon, page, orderProperty, orderType);
        List<UserCouponVo> userCouponVoList = new ArrayList<UserCouponVo>();
        for (UserCoupon userCoupon : userCouponList) {
            UserCouponVo userCouponVo = new UserCouponVo();
            userCouponVo.setId(userCoupon.getId());
            userCouponVo.setUserName(userCoupon.getMember().getUserName());
            if (StringUtils.hasText(userCoupon.getMember().getMobile())) {
                userCouponVo.setContact(userCoupon.getMember().getMobile());
            } else if (StringUtils.hasText(userCoupon.getMember().getTelephone())) {
                userCouponVo.setContact(userCoupon.getMember().getTelephone());
            } else if (StringUtils.hasText(userCoupon.getMember().getEmail())) {
                userCouponVo.setContact(userCoupon.getMember().getEmail());
            }
            userCouponVo.setCouponName(userCoupon.getCoupon().getName());
            userCouponVo.setValidStart(DateUtils.format(userCoupon.getValidStart(), "yyyy-MM-dd HH:mm:ss"));
            userCouponVo.setValidEnd(DateUtils.format(userCoupon.getValidEnd(), "yyyy-MM-dd HH:mm:ss"));
            userCouponVo.setStatus(userCoupon.getStatus().toString());
            userCouponVo.setUseTime(DateUtils.format(userCoupon.getUseTime(), "yyyy-MM-dd HH:mm:ss"));
            userCouponVoList.add(userCouponVo);
        }
        return datagrid(userCouponVoList, page.getTotalCount());
    }

    public Result sendCoupon() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (!StringUtils.hasText(couponIds)) {
            result.put("success", false);
            result.put("msg", "没有要发放的优惠券!");
            return json(JSONObject.fromObject(result));
        }
        if (!StringUtils.hasText(memberIds)) {
            result.put("success", false);
            result.put("msg", "没有要发放用户!");
            return json(JSONObject.fromObject(result));
        }
        boolean sendSuccess = userCouponMgrService.doSendCoupon(couponIds, memberIds);
        result.put("success", sendSuccess);
        if (sendSuccess) {
            result.put("msg", "优惠券发放成功!");
        } else {
            result.put("msg", "优惠券发放失败!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result setUnavailable() {
        String idStr = getRequest().getParameter("id");
        Map<String, Object> result = userCouponMgrService.doSetunavailable(idStr);
        return json(JSONObject.fromObject(result));
    }

    public Result setAvailable() {
        String idStr = getRequest().getParameter("id");
        Map<String, Object> result = userCouponMgrService.doSetavailable(idStr);
        return json(JSONObject.fromObject(result));
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public UserCoupon getUserCoupon() {
        return userCoupon;
    }

    public void setUserCoupon(UserCoupon userCoupon) {
        this.userCoupon = userCoupon;
    }

    public String getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(String couponIds) {
        this.couponIds = couponIds;
    }

    public String getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String memberIds) {
        this.memberIds = memberIds;
    }
}
