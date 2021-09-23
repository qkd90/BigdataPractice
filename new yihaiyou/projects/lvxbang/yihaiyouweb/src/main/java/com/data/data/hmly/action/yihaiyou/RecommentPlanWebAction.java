package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.lvxbang.AdvertisingService;
import com.data.data.hmly.service.other.OtherFavoriteService;
import com.data.data.hmly.service.plan.RecommendPlanDayService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.RecommendPlanTripService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.entity.RecommendPlanDay;
import com.data.data.hmly.service.plan.request.RecommendPlanSearchRequest;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/10/9.
 */
public class RecommentPlanWebAction extends BaseAction {

    @Resource
    private RecommendPlanService recommentPlanService;

    @Resource
    private RecommendPlanDayService recommentPlanDayService;

    @Resource
    private RecommendPlanTripService recommentPlanTripService;

    @Resource
    private AdvertisingService advertisingService;

    @Resource
    private OtherFavoriteService otherFavoriteService;


    private RecommendPlanSearchRequest recommendPlanSearchRequest = new RecommendPlanSearchRequest();
    private Map<String, Object> result = new HashMap<String, Object>();
    private Integer page = 1;
    private Integer pageSize = 10;


    public Result getRecommendPlanCommentList() {
        String recommendPlanIdStr = (String) getParameter("recommendPlanId");
        if (!StringUtils.isNotBlank(recommendPlanIdStr)) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        RecommendPlan recommendPlan = recommentPlanService.get(Long.parseLong(recommendPlanIdStr));
        if (recommendPlan == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        List<Comment> commentList = recommendPlan.getRecommendPlanCommentList();

        if (commentList == null || commentList.isEmpty()) {
            commentList = new ArrayList<Comment>();
        }
        Collections.reverse(commentList);
        result.put("userName", recommendPlan.getUser().getNickName());
        result.put("commentList", commentList);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig("user")));
    }

    public Result addViewNum() {
        String recommendPlanIdStr = (String) getParameter("recommendPlanId");
        if (!StringUtils.isNotBlank(recommendPlanIdStr)) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        // 取出现有游记的浏览数目
        Integer nowViewNum = recommentPlanService.getViewNum(Long.parseLong(recommendPlanIdStr));
        if (nowViewNum == null) {
            nowViewNum = 0;
        }
        recommentPlanService.updateViewNum(Long.parseLong(recommendPlanIdStr), nowViewNum + 1);
        RecommendPlan recommendPlan = recommentPlanService.get(Long.parseLong(recommendPlanIdStr));
        recommentPlanService.indexRecommendPlan(recommendPlan);     //单条跟新游记索引
        // 返回分享数目
        result.put("viewNum", nowViewNum + 1);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result getRecommendPlanDetail() {
        String recommendPlanIdStr = (String) getParameter("recommendPlanId");
        String favoriteStr = (String) getParameter("favorite");
        if (!StringUtils.isNotBlank(recommendPlanIdStr)) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        RecommendPlan recommendPlan = recommentPlanService.get(Long.parseLong(recommendPlanIdStr));
        if (recommendPlan == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }

        RecommendPlanDay recommendPlanDay = new RecommendPlanDay();
        recommendPlanDay.setRecommendPlan(recommendPlan);
        List<RecommendPlanDay> recommendPlanDays = recommentPlanDayService.getPlanDaysList(recommendPlanDay);
        // 查询是否收藏
        boolean favorite = false;
        if (StringUtils.isNotBlank(favoriteStr)) {
            Member member = getLoginUser();
            if (member != null) {
                favorite = otherFavoriteService.checkExists(ProductType.recplan, Long.valueOf(recommendPlanIdStr), member.getId());
            }
        }
        result.put("favorite", favorite);
        result.put("recommendPlan", recommendPlan);
        result.put("recommendPlanDays", recommendPlanDays);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig("recommendPlanTrips", "recommendPlanPhotos")));
    }


    public Result getRecommendPlanList() {
        Page pageInfo = new Page(page, pageSize);



        List<RecommendPlanSolrEntity> planSolrEntityList = recommentPlanService.listFromSolr(recommendPlanSearchRequest, pageInfo);
        if (planSolrEntityList.isEmpty()) {
            result.put("success", false);
            result.put("nomore", true);
            result.put("msg", "没有数据");
            return json(JSONObject.fromObject(result));
        }

        if (pageInfo.getPageIndex() >= pageInfo.getPageCount()) {

            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("rePlanList", planSolrEntityList);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result getRecplanTopBannerAds() {
        List<Ads> topBannerAds = advertisingService.getMobileRecplanBanner();
        result.put("ads", topBannerAds);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    public RecommendPlanSearchRequest getRecommendPlanSearchRequest() {
        return recommendPlanSearchRequest;
    }

    public void setRecommendPlanSearchRequest(RecommendPlanSearchRequest recommendPlanSearchRequest) {
        this.recommendPlanSearchRequest = recommendPlanSearchRequest;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
