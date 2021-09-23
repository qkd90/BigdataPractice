package com.data.data.hmly.action.lxbcommon;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.lxbcommon.vo.CouponComboVo;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.lxbcommon.CouponMgrService;
import com.data.data.hmly.service.lxbcommon.UserCouponMgrService;
import com.data.data.hmly.service.lxbcommon.entity.Coupon;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/5/9.
 */
public class CouponAction extends FrameBaseAction {

    @Resource
    private CouponMgrService couponMgrService;
    @Resource
    private UserCouponMgrService userCouponMgrService;

    private int page;
    private int rows;
    private String orderProperty;
    private String orderType;

    private Long id;
    private String validStart;
    private String validEnd;

    private Coupon coupon = new Coupon();



    public Result list() {
        return dispatch();
    }

    public Result getCouponList() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Page page = new Page(this.page, this.rows);
        List<Coupon> couponList = couponMgrService.getCouponList(coupon, page, orderProperty, orderType);
        for (Coupon coupon : couponList) {
            Integer receivedNum = userCouponMgrService.getReceivedNum(coupon);
            Integer receivedPersonedNum = userCouponMgrService.getReceivedPersonNum(coupon);
            coupon.setReceivedNum(receivedNum);
            coupon.setReceivedPersonNum(receivedPersonedNum);
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(couponList, page.getTotalCount(), jsonConfig);
    }

    public Result getCouponComboData() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Page page = new Page(this.page, this.rows);
        List<Coupon> couponList = couponMgrService.getCouponList(coupon, page, orderProperty, orderProperty);
        List<CouponComboVo> couponComboVoList = new ArrayList<CouponComboVo>();
        for (Coupon coupon : couponList) {
            CouponComboVo couponComboVo = new CouponComboVo();
            couponComboVo.setId(coupon.getId());
            couponComboVo.setName(coupon.getName());
            couponComboVoList.add(couponComboVo);
        }
        return datagrid(couponComboVoList, page.getTotalCount());
    }

    public Result commitCoupon() {
        Map<String, Object> result = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        if (coupon.getId() != null && coupon.getId() > 0) {
            Coupon sourceCoupon = couponMgrService.get(coupon.getId());
            if (StringUtils.hasText(validStart)) {
                coupon.setValidStart(DateUtils.getDate(validStart, "yyyy-MM-dd HH:mm:ss"));
            }
            if (StringUtils.hasText(validEnd)) {
                coupon.setValidEnd(DateUtils.getDate(validEnd, "yyyy-MM-dd HH:mm:ss"));
            }
            boolean updateSuccess = couponMgrService.update(coupon, sourceCoupon);
            result.put("success", updateSuccess);
            if (updateSuccess) {
                result.put("msg", "优惠券保存成功!");
            } else {
                result.put("msg", "优惠券保存失败!");
            }
        } else if (coupon.getId() == null) {
            if (StringUtils.hasText(validStart)) {
                coupon.setValidStart(DateUtils.getDate(validStart, "yyyy-MM-dd HH:mm:ss"));
            }
            if (StringUtils.hasText(validEnd)) {
                coupon.setValidEnd(DateUtils.getDate(validEnd, "yyyy-MM-dd HH:mm:ss"));
            }
            boolean saveSuccess = couponMgrService.save(coupon);
            result.put("success", saveSuccess);
            if (saveSuccess) {
                result.put("msg", "优惠券保存成功!");
            } else {
                result.put("msg", "优惠券保存失败!");
            }
        }
        return json(JSONObject.fromObject(result));
    }

    public Result closeCoupon() {
        Map<String, Object> result = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        if (id == null || id <= 0) {
            result.put("success", false);
            result.put("msg", "优惠券ID不可为空或不存在的优惠券");
        }
        result = couponMgrService.doCloseCoupon(id);
        return json(JSONObject.fromObject(result));
    }

    public Result openCoupon() {
        Map<String, Object> result = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        if (id == null || id <= 0) {
            result.put("success", false);
            result.put("msg", "优惠券ID不可为空或不存在的优惠券");
        }
        result = couponMgrService.doOpenCoupon(id);
        return json(JSONObject.fromObject(result));
    }

    public Result detailCoupon() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Coupon coupon = couponMgrService.get(id);
            if (coupon != null) {
                result.put("coupon.id", coupon.getId());
                result.put("coupon.name", coupon.getName());
                result.put("coupon.couponDiscountType", coupon.getCouponDiscountType());
                result.put("coupon.faceValue", coupon.getFaceValue());
                result.put("coupon.maxDiscount", coupon.getMaxDiscount());
                result.put("coupon.circulation", coupon.getCirculation());
                result.put("coupon.availableNum", coupon.getAvailableNum());
                result.put("coupon.couponValidType", coupon.getCouponValidType());
                result.put("coupon.validDays", coupon.getValidDays());
                result.put("validStart", DateUtils.format(coupon.getValidStart(), "yyyy-MM-dd HH:mm:dd"));
                result.put("validEnd", DateUtils.format(coupon.getValidEnd(), "yyyy-MM-dd HH:mm:dd"));
                result.put("coupon.couponUseConditionType", coupon.getCouponUseConditionType());
                result.put("coupon.useCondition", coupon.getUseCondition());
                result.put("coupon.couponReceiveLimitType", coupon.getCouponReceiveLimitType());
                result.put("coupon.receiveLimit", coupon.getReceiveLimit());
                result.put("coupon.limitProductTypes", coupon.getLimitProductTypes().replaceAll("\\s", "").split(","));
                result.put("coupon.status", coupon.getStatus());
                result.put("coupon.instructions", coupon.getInstructions());
                result.put("coupon.limitInfo", coupon.getLimitInfo());
            }
        }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValidStart() {
        return validStart;
    }

    public void setValidStart(String validStart) {
        this.validStart = validStart;
    }

    public String getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(String validEnd) {
        this.validEnd = validEnd;
    }

    public CouponMgrService getCouponMgrService() {
        return couponMgrService;
    }

    public void setCouponMgrService(CouponMgrService couponMgrService) {
        this.couponMgrService = couponMgrService;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

}
