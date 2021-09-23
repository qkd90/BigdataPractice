package com.data.data.hmly.service.build.yhybuilder;

import com.data.data.hmly.service.build.enums.BuilderStatus;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipPlan;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.plan.RecommendPlanPhotoService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.entity.RecommendPlanPhoto;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dy on 2017/1/13.
 */
@Service
public class YhyRecommendPlanBuilder {

    private Logger logger = Logger.getLogger(YhyRecommendPlanBuilder.class);

    private static final String YHY_RECOMMEND_PLAN_INDEX_TEMPLATE = "/yhy/recommendPlan/index.ftl";
    private static final String YHY_RECOMMEND_PLAN_INDEX_TARGET = "/yhy/recommendPlan/index.htm";
    private static final String YHY_RECOMMEND_PLAN_DETAIL_TEMPLATE = "/yhy/recommendPlan/detail.ftl";
    private static final String YHY_RECOMMEND_PLAN_DETAIL_TARGET = "/yhy/recommendPlan/detail%d.htm";
    private static final String YHY_RECOMMEND_PLAN_HEAD_TEMPLATE = "/yhy/recommendPlan/head.ftl";
    private static final String YHY_RECOMMEND_PLAN_HEAD_TARGET = "/yhy/recommendPlan/head%d.htm";

    @Resource
    private YhyAdsBuilder yhyAdsBuilder;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private RecommendPlanPhotoService recommendPlanPhotoService;


    private final AtomicInteger buildingCount = new AtomicInteger();
    private final AtomicLong buildingCost = new AtomicLong();
    private static Long currentId;
    private BuilderStatus status = BuilderStatus.IDLE;
    private static final int PAGE_SIZE = 100;

    public void buildYhyRecommendPlanIndex() {
        Map<Object, Object> data = new HashMap<Object, Object>();
        //广告
        List<Ads> adses = yhyAdsBuilder.getAds(YhyAdsBuilder.YHY_RECPLAN_INDEX_TOP_BANNER);
        data.put("adses", adses);

        Page page = new Page(1, 5);

        RecommendPlan recommendPlan = new RecommendPlan();
        TbArea tbArea = new TbArea();
        tbArea.setId(3502L);
        recommendPlan.setCity(tbArea);
        recommendPlan.setStatus(2);
        final List<RecommendPlan> recommendPlanList = recommendPlanService.list(recommendPlan, page, "collectNum", "desc");

        List<RecommendPlan> responseRecommendPlanList = Lists.transform(recommendPlanList, new Function<RecommendPlan, RecommendPlan>() {
            @Override
            public RecommendPlan apply(RecommendPlan recommendPlan) {
                if (StringUtils.isBlank(recommendPlan.getCoverPath())) {
                    List<RecommendPlanPhoto> recommendPlanPhotos = recommendPlanPhotoService.getRecommendPlanPhotoListByReplanId(recommendPlan.getId());
                    if (!recommendPlanPhotos.isEmpty()) {
                        recommendPlan.setCoverPath(recommendPlanPhotos.get(0).getPhotoLarge());
                    }
                }
                return recommendPlan;
            }
        });

        data.put("recommendPlanList", responseRecommendPlanList);
        FreemarkerUtil.create(data, YHY_RECOMMEND_PLAN_INDEX_TEMPLATE, YHY_RECOMMEND_PLAN_INDEX_TARGET);
        logger.info("一海游游记首页构建完成...!");
    }

    public void buildRecommendPlanDetail(Long recommendPlanId) {
        RecommendPlan recommendPlan = recommendPlanService.get(recommendPlanId);
        buildYhyDetail(recommendPlan);
    }

    private void buildYhyDetail(RecommendPlan recommendPlan) {
        if (recommendPlan.getStatus() != 2) {
            return;
        }
        Clock clock = new Clock();
        Map<Object, Object> data = Maps.newHashMap();

        RecommendPlan recommendPlanRequest = new RecommendPlan();
        TbArea tbArea = new TbArea();
        tbArea.setId(3502L);
        recommendPlanRequest.setCity(tbArea);
        recommendPlanRequest.setStatus(2);
        List<RecommendPlan> recommendPlanList = recommendPlanService.list(recommendPlanRequest, new Page(1, 5), "viewNum", "desc");


        data.put("recommendPlan", recommendPlan);
        data.put("recommendPlanDays", recommendPlan.getRecommendPlanDays());
        data.put("recommendPlanList", recommendPlanList);
        FreemarkerUtil.create(data, YHY_RECOMMEND_PLAN_HEAD_TEMPLATE, String.format(YHY_RECOMMEND_PLAN_HEAD_TARGET, recommendPlan.getId()));
        FreemarkerUtil.create(data, YHY_RECOMMEND_PLAN_DETAIL_TEMPLATE, String.format(YHY_RECOMMEND_PLAN_DETAIL_TARGET, recommendPlan.getId()));
        currentId = recommendPlan.getId();
        buildingCount.getAndIncrement();
        buildingCost.getAndAdd(clock.totalTime());
        logger.info("build recommendPlan detail#" + recommendPlan.getId() + " success, cost " + clock.totalTime());
    }


    public void buildYhyDetail(final Long startId, final Long endId) {
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildYhyDetailTask(startId, endId);
            }
        });
    }

    public void buildYhyDetail() {
        if (status == BuilderStatus.RUNNING) {
            return;
        }
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildYhyDetailTask();
            }
        });
    }

    private Object buildYhyDetailTask() {
        status = BuilderStatus.RUNNING;
        int current;
        int page = 1;
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            RecommendPlan recommendPlan = new RecommendPlan();
            recommendPlan.setStatus(2);
            List<RecommendPlan> recommendPlanList = recommendPlanService.list(recommendPlan, new Page(page, PAGE_SIZE));
            for (RecommendPlan c : recommendPlanList) {
                buildYhyDetail(c);
            }
            current = recommendPlanList.size();
            page++;
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);

        } while (current == PAGE_SIZE);
        status = BuilderStatus.IDLE;
        return null;
    }

    private Object buildYhyDetailTask(Long startId, Long endId) {
        status = BuilderStatus.RUNNING;
        buildingCount.set(0);
        buildingCost.set(0);
        currentId = startId - 1;
        int current;
        Clock clock = new Clock();
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            List<RecommendPlan> recommendPlanList = recommendPlanService.getInIdRange(currentId, endId, PAGE_SIZE);
            for (RecommendPlan c : recommendPlanList) {
                buildYhyDetail(c);
                currentId = c.getId();
                logger.info("build #" + c.getId() + "finished, cost " + clock.elapseTime());
            }
            current = recommendPlanList.size();
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } while (current == PAGE_SIZE);
        logger.info("build finished, cost " + clock.totalTime() + "ms");
        status = BuilderStatus.IDLE;
        return null;
    }
}
