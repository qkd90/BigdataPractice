package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.response.CollectResponse;
import com.data.data.hmly.service.CollectMobileService;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.entity.Member;
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
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-13,0013.
 */
public class CollectWebAction extends BaseAction {
    @Resource
    private OtherFavoriteService otherFavoriteService;
    @Resource
    private CollectMobileService collectMobileService;
    @Resource
    private CruiseShipDateService cruiseShipDateService;
    @Resource
    private RecommendPlanService recommendPlanService;

    public Member user;
    public Long targetId;
    public ProductType targetType;
    public Integer pageNo;
    public Integer pageSize;
    public String json;

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
        } else if (ProductType.cruiseship.equals(targetType)) {
            CruiseShipDate date = cruiseShipDateService.addCollectNum(targetId);
            otherFavorite.completeFavorite(date);
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
        List<CollectResponse> list = collectMobileService.collectList(user.getId(), targetType, page);
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("collectList", list);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    @AjaxCheck
    @NeedLogin
    public Result delete() throws IOException {
        user = getLoginUser();
        otherFavoriteService.doClearFavoriteBy(targetType, user.getId(), targetId);
        if (ProductType.cruiseship.equals(targetType)) {
            cruiseShipDateService.delCollectNum(targetId);
        }
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
    public Result batchDelete() throws IOException {
        user = getLoginUser();
        List<Long> ids = mapper.readValue(json, List.class);
        otherFavoriteService.doClearFavoritesBy(user.getId(), ids);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }
}
