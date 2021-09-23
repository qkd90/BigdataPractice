package com.hmlyinfo.app.soutu.plan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.plan.domain.RecommendPlan;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanTag;
import com.hmlyinfo.base.cache.CacheProvider;
import com.hmlyinfo.base.cache.XMemcachedImpl;

/**
 * Created by guoshijie on 2015/4/14.
 */
@Service
public class PlanCacheService {

    private static final String TAG_PREFIX = "R_TAG_";
    private static final String TAG_PLAN_PREFIX = "R_TAG_PLAN_";

    private static CacheProvider saeMemcache = XMemcachedImpl.getInstance();

    public List<RecommendPlanTag> getRecommendTags(int cityCode, int day) {
        String key = getTagKey(cityCode, day);
        return saeMemcache.get(key);
    }

    private String getTagKey(int cityCode, int day) {
        return TAG_PREFIX + cityCode + "_" + day;
    }

    public void addRecommendTags(int cityCode, int day, List<RecommendPlanTag> tags) {
        String key = getTagKey(cityCode, day);
        saeMemcache.set(key, tags, 60 * 60);
    }

    public RecommendPlan getRecommendPlan(int cityCode, int day, String tag) {
        String key = getTagPlanKey(cityCode, day, tag);
        return saeMemcache.get(key);
    }

    public void addRecommendPlan(int cityCode, int day, String tag, RecommendPlan recommendPlan) {
        String key = getTagPlanKey(cityCode, day, tag);
        saeMemcache.set(key, recommendPlan, 60 * 60);
    }

    private String getTagPlanKey(int cityCode, int day, String tag) {
        return TAG_PLAN_PREFIX + cityCode + day + tag;
    }

}
