package com.data.data.hmly.action.mobile;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.other.OtherFavoriteService;
import com.data.data.hmly.service.other.entity.OtherFavorite;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2016/8/5.
 */
@NeedLogin
public class FavoriteWebAction extends MobileBaseAction {
    @Resource
    private OtherFavoriteService otherFavoriteService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private HotelService hotelService;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private PlanService planService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private LineService lineService;

    private ProductType favoriteType;
    private Long favoriteId;

    /**
     * 收藏、取消收藏，
     * 不存在则收藏，存在则取消收藏
     * @return
     */
    @AjaxCheck
    public Result favorite() throws LoginException {
        Member member = getLoginUser();

        boolean favorite = otherFavoriteService.checkExists(favoriteType, favoriteId, member.getId());
        if (favorite) { // 取消收藏
            if (ProductType.scenic.equals(favoriteType)) {
                scenicInfoService.deleteCollect(favoriteId);
            } else if (ProductType.hotel.equals(favoriteType)) {
                hotelService.deleteCollect(favoriteId);
            } else if (ProductType.delicacy.equals(favoriteType)) {
                delicacyService.deleteCollect(favoriteId);
            } else if (ProductType.plan.equals(favoriteType)) {
                planService.deleteCollect(favoriteId);
            } else if (ProductType.recplan.equals(favoriteType)) {
                recommendPlanService.deleteCollect(favoriteId);
            } else if (ProductType.line.equals(favoriteType)) {
                lineService.deleteCollect(favoriteId);
            } else {
                result.put("success", true);
                result.put("errorMsg", "未知收藏类型");
                return json(JSONObject.fromObject(result));
            }
            otherFavoriteService.doClearFavoriteBy(favoriteType, member.getId(), favoriteId);
            favorite = false;
        } else {    // 收藏
            // 收藏信息
            OtherFavorite otherFavorite = new OtherFavorite();
            otherFavorite.setUserId(member.getId());
            otherFavorite.setCreateTime(new Date());
            otherFavorite.setDeleteFlag(false);
            otherFavorite.setFavoriteId(favoriteId);
            otherFavorite.setFavoriteType(favoriteType);
            // 不同类型收藏
            if (ProductType.scenic.equals(favoriteType)) {
                ScenicInfo scenicInfo = scenicInfoService.addCollect(favoriteId);
                otherFavorite.completeFavorite(scenicInfo);
            } else if (ProductType.hotel.equals(favoriteType)) {
                Hotel hotel = hotelService.addCollect(favoriteId);
                otherFavorite.completeFavorite(hotel);
            } else if (ProductType.delicacy.equals(favoriteType)) {
                Delicacy delicacy = delicacyService.addCollect(favoriteId);
                otherFavorite.completeFavorite(delicacy);
            } else if (ProductType.plan.equals(favoriteType)) {
                Plan plan = planService.addCollect(favoriteId);
                otherFavorite.completeFavorite(plan);
            } else if (ProductType.recplan.equals(favoriteType)) {
                RecommendPlan recommendPlan = recommendPlanService.addCollect(favoriteId);
                otherFavorite.completeFavorite(recommendPlan);
            } else if (ProductType.line.equals(favoriteType)) {
                Line line = lineService.addCollect(favoriteId);
                List<Line> list = lineService.completeLinePrice(Lists.newArrayList(line));
                if (!list.isEmpty()) {
                    otherFavorite.completeFavorite(list.get(0));
                }
            } else {
                result.put("success", true);
                result.put("errorMsg", "未知收藏类型");
                return json(JSONObject.fromObject(result));
            }
            otherFavoriteService.doAddOtherFavorite(otherFavorite);
            favorite = true;
        }

        result.put("favorite", favorite);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public ProductType getFavoriteType() {
        return favoriteType;
    }

    public void setFavoriteType(ProductType favoriteType) {
        this.favoriteType = favoriteType;
    }
}
