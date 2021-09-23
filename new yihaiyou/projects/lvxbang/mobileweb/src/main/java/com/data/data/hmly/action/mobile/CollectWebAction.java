package com.data.data.hmly.action.mobile;

import com.data.data.hmly.action.mobile.response.CollectResponse;
import com.data.data.hmly.service.CollectMobileService;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.impression.ImpressionService;
import com.data.data.hmly.service.impression.entity.Impression;
import com.data.data.hmly.service.other.OtherFavoriteService;
import com.data.data.hmly.service.other.entity.OtherFavorite;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.opensymphony.xwork2.Result;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-13,0013.
 */
public class CollectWebAction extends MobileBaseAction {
    @Resource
    private OtherFavoriteService otherFavoriteService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private ImpressionService impressionService;
    @Resource
    private CollectMobileService collectMobileService;

    public Member user;
    public Long targetId;
    public ProductType targetType;
    public Integer pageNo;
    public Integer pageSize;
    /**
     * 添加收藏
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result save() throws LoginException {
        user = getLoginUser();
        if (otherFavoriteService.checkExists(targetType, targetId, user.getId())) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        OtherFavorite otherFavorite = new OtherFavorite();
        otherFavorite.setFavoriteType(targetType);
        otherFavorite.setFavoriteId(targetId);
        otherFavorite.setUserId(user.getId());
        otherFavorite.setCreateTime(new Date());
        otherFavorite.setDeleteFlag(false);
        if (ProductType.recplan.equals(targetType)) {
            RecommendPlan recommendPlan = recommendPlanService.addCollect(targetId);
            otherFavorite.completeFavorite(recommendPlan);
        } else if (ProductType.impression.equals(targetType)) {
            Impression impression = impressionService.addCollect(targetId);
            otherFavorite.completeFavorite(impression);
        } else {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        otherFavoriteService.doAddOtherFavorite(otherFavorite);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 验证是否收藏
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result check() throws LoginException {
        user = getLoginUser();
        Boolean exists = otherFavoriteService.checkExists(targetType, targetId, user.getId());
        result.put("exists", exists);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 收藏列表
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result list() throws LoginException {
        user = getLoginUser();
        Page page = new Page(pageNo, pageSize);
        List<CollectResponse> list = collectMobileService.collectList(user.getId(), page);
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("collectList", list);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 删除收藏
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result delete() throws LoginException {
        user = getLoginUser();
        otherFavoriteService.doClearFavoriteBy(targetType, user.getId(), targetId);
        if (ProductType.recplan.equals(targetType)) {
            recommendPlanService.deleteCollect(targetId);
        } else if (ProductType.impression.equals(targetType)) {
            impressionService.deleteCollect(targetId);
        } else {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }
}
