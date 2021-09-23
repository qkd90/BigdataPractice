package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.comment.CommentScoreTypeService;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Maps;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.SpringContextHolder;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Service
public class RecommendPlanBuilder {

    private static final String LVXBANG_RECOMMENDPLAN_BANNER = "lvxbang_recplan_banner";
	private static final String LVXBANG_RECOMMENDPLAN_INDEX_TEMPLATE = "/lvxbang/recplan/index.ftl";
	private static final String LVXBANG_RECOMMENDPLAN_INDEX_TARGET = "/lvxbang/recplan/index.htm";
	private static final String LVXBANG_RECOMMENDPLAN_DETAIL_TEMPLATE = "/lvxbang/recplan/detail.ftl";
	private static final String LVXBANG_RECOMMENDPLAN_DETAIL_TARGET = "/lvxbang/recplan/detail%d.htm";
    private static final String LVXBANG_RECOMMENDPLAN_HEAD_TEMPLATE = "/lvxbang/recplan/head.ftl";
    private static final String LVXBANG_RECOMMENDPLAN_HEAD_TARGET = "/lvxbang/recplan/head%d.htm";

	@Resource
	private RecommendPlanService recommendPlanService;
    @Resource
    private AdsBuilder adsBuilder;
	@Resource
	private CommentScoreTypeService commentScoreTypeService;
    private static final int PAGE_SIZE = 100;
	
	public void buildLXBDetail() {
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildLXBDetailTask(null);
            }
        });
	}

    public void buildLXBDetailByCondition(final RecommendPlan condition) {
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildLXBDetailTask(condition);
            }
        });

    }

    private Object buildLXBDetailTask(RecommendPlan originCondition) {
        int current;
        int page = 1;
        Clock clock = new Clock();
        RecommendPlan condition = new RecommendPlan();
        if (originCondition != null) {
            condition = originCondition;
        }
        condition.setStatus(2);
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            List<RecommendPlan> recommendPlans = recommendPlanService.list(condition, new Page(page, PAGE_SIZE));
            current =  recommendPlans.size();
            Iterator<RecommendPlan> iterator = recommendPlans.iterator();
            while (iterator.hasNext()) {
                RecommendPlan recommendPlan = iterator.next();
                recommendPlanService.makeDetailRecplanOnBuildOrEdit(recommendPlan);
                buildLXBDetail(recommendPlan);
            }
            System.out.println("recommendPlan : build完成第" + page + "页，花费" + clock.elapseTime() + "ms");
            page++;
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } while (current == PAGE_SIZE);
        return null;
    }

    public void buildLXBDetail(Long id) {
		RecommendPlan recommendPlan = recommendPlanService.get(id);
		buildLXBDetail(recommendPlan);
	}

	private void buildLXBDetail(RecommendPlan recommendPlan) {
        if (recommendPlan.getStatus() == null || recommendPlan.getStatus() != 2
                || recommendPlan.getDeleteFlag() == null || recommendPlan.getDeleteFlag() != 2) {
            return;
        }
        recommendPlanService.makeDetailRecplanOnBuildOrEdit(recommendPlan);
        Map<Object, Object> data = Maps.newHashMap();
        CommentScoreType commentScoreType = new CommentScoreType();
        commentScoreType.setTargetType(ProductType.recplan);
        List<CommentScoreType> commentScoreTypes = commentScoreTypeService.list(commentScoreType, null);
        data.put("recplan", recommendPlan);
        data.put("commentScoreTypes", commentScoreTypes);
        FreemarkerUtil.create(data, LVXBANG_RECOMMENDPLAN_DETAIL_TEMPLATE, String.format(LVXBANG_RECOMMENDPLAN_DETAIL_TARGET, recommendPlan.getId()));
        FreemarkerUtil.create(data, LVXBANG_RECOMMENDPLAN_HEAD_TEMPLATE, String.format(LVXBANG_RECOMMENDPLAN_HEAD_TARGET, recommendPlan.getId()));

    }

	public void buildLXBIndex() {
		Map<Object, Object> data = Maps.newHashMap();
		List<RecommendPlan> seasonSelectRecplans = recommendPlanService.getSeasonSelectRecplans();
		List<RecommendPlan> hotRecplans = recommendPlanService.getHotRecommendPlans();
        List<Ads> adses = adsBuilder.getAds(LVXBANG_RECOMMENDPLAN_BANNER);
        List<RecommendPlan> goodRecplans = recommendPlanService.getGoodRecommendPlans();
		List<RecommendPlan> themeRecplans = recommendPlanService.getThemeRecommendPlan();
		data.put("seasonSelectRecplans", seasonSelectRecplans);
		data.put("hotRecplans", hotRecplans);
        data.put("adses", adses);
        data.put("goodRecplans", goodRecplans);
		data.put("themeRecplans", themeRecplans);
		FreemarkerUtil.create(data, LVXBANG_RECOMMENDPLAN_INDEX_TEMPLATE, LVXBANG_RECOMMENDPLAN_INDEX_TARGET);
	}
}
