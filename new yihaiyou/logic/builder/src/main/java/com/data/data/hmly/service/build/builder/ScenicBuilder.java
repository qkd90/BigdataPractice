package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.build.enums.BuilderStatus;
import com.data.data.hmly.service.build.response.MonitorResponse;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.comment.CommentScoreTypeService;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.request.ScenicSearchRequest;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Maps;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.SpringContextHolder;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by chenjiaqin on 2015/12/17.
 */
@Service
public class ScenicBuilder {

    private static final Logger LOGGER = Logger.getLogger(ScenicBuilder.class);

    private static final String LVXBANG_SCENIC_DETAIL_TEMPLATE = "/lvxbang/scenic/detail.ftl";
    private static final String LVXBANG_SCENIC_DETAIL_TARGET = "/lvxbang/scenic/detail%d.htm";
    private static final String LVXBANG_SCENIC_INDEX_TEMPLATE = "/lvxbang/scenic/index.ftl";
    private static final String LVXBANG_SCENIC_INDEX_TARGET = "/lvxbang/scenic/index.htm";
    private static final String LVXBANG_SCENIC_HEAD_TEMPLATE = "/lvxbang/scenic/head.ftl";
    private static final String LVXBANG_SCENIC_HEAD_TARGET = "/lvxbang/scenic/head%d.htm";

    private static final String LVXBANG_SCENIC_BANNER = "lvxbang_scenic_banner";

    private static final int PAGE_SIZE = 100;
    private final AtomicInteger buildingCount = new AtomicInteger();
    private final AtomicLong buildingCost = new AtomicLong();
    private static Long currentId;

    private BuilderStatus status = BuilderStatus.IDLE;

    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private LabelService labelService;
    @Resource
    private AdsBuilder adsBuilder;
//    @Resource
//    private TicketPriceService ticketPriceService;
//    @Resource
//    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private CommentScoreTypeService commentScoreTypeService;

    public void resetStatus() {
        status = BuilderStatus.IDLE;
    }

    public MonitorResponse monitor() {
        return new MonitorResponse().withId(currentId)
                .withName("scenic")
                .withCount(buildingCount.get())
                .withStatus(status)
                .withTime(buildingCost.get());
    }

    public void buildLXBDetail() {
        if (status == BuilderStatus.RUNNING) {
            return;
        }
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildLXBDetailTask();
            }
        });
    }

    public void buildLXBDetail(final Long startId, final Long endId) {
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildLXBDetailTask(startId, endId);
            }
        });
    }

    private Object buildLXBDetailTask(Long startId, Long endId) {
        status = BuilderStatus.RUNNING;
        buildingCount.set(0);
        buildingCost.set(0);
        currentId = startId - 1;
        int current;
        Clock clock = new Clock();
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            List<ScenicInfo> scenicInfos = scenicInfoService.getInIdRange(currentId, endId, PAGE_SIZE);
            for (ScenicInfo scenicInfo : scenicInfos) {
                buildLXBDetail(scenicInfo);
                currentId = scenicInfo.getId();
                LOGGER.info("build #" + scenicInfo.getId() + "finished, cost " + clock.elapseTime());
            }
            current = scenicInfos.size();
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } while (current == PAGE_SIZE);
        LOGGER.info("build finished, cost " + clock.totalTime() + "ms");
        status = BuilderStatus.IDLE;
        return null;
    }

    private Object buildLXBDetailTask() {
        status = BuilderStatus.RUNNING;
        int current;
        int page = 1;
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            List<ScenicInfo> scenicInfos = scenicInfoService.list(new ScenicInfo(), new Page(page, PAGE_SIZE));
            for (ScenicInfo scenicInfo : scenicInfos) {
                buildLXBDetail(scenicInfo);
            }
            current = scenicInfos.size();
            page++;
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);

        } while (current == PAGE_SIZE);
        status = BuilderStatus.IDLE;
        return null;
    }

    public void buildLXBDetail(Long id) {
        ScenicInfo scenicInfo = scenicInfoService.get(id);
        buildLXBDetail(scenicInfo);
    }

    private void buildLXBDetail(ScenicInfo scenicInfo) {
        if (scenicInfo.getStatus() != 1) {
            return;
        }
        Clock clock = new Clock();
        Map<Object, Object> data = Maps.newHashMap();
        ScenicInfo childScenic = new ScenicInfo();
        childScenic.setFather(scenicInfo);
        List<ScenicInfo> childrenScenicInfo = scenicInfoService.list(childScenic, new Page(0, 5), "ranking", "asc");
        Integer childrenScenicCount = childrenScenicInfo.size();
        if (childrenScenicCount > 4) {
            childrenScenicInfo = childrenScenicInfo.subList(0, 4);
        }
        CommentScoreType commentScoreType = new CommentScoreType();
        commentScoreType.setTargetType(ProductType.scenic);
        List<CommentScoreType> commentScoreTypes = commentScoreTypeService.list(commentScoreType, null);
        data.put("scenicInfo", scenicInfo);
        data.put("childrenScenicCount", childrenScenicCount);
        data.put("childrenScenicInfo", childrenScenicInfo);
        data.put("commentScoreTypes", commentScoreTypes);
        FreemarkerUtil.create(data, LVXBANG_SCENIC_HEAD_TEMPLATE, String.format(LVXBANG_SCENIC_HEAD_TARGET, scenicInfo.getId()));
        FreemarkerUtil.create(data, LVXBANG_SCENIC_DETAIL_TEMPLATE, String.format(LVXBANG_SCENIC_DETAIL_TARGET, scenicInfo.getId()));
        currentId = scenicInfo.getId();
        buildingCount.getAndIncrement();
        buildingCost.getAndAdd(clock.totalTime());
        LOGGER.info("build scenic detail#" + scenicInfo.getId() + " success, cost " + clock.totalTime());
    }

    public void buildLXBIndex() {
        Map<Object, Object> data = Maps.newHashMap();
        List<ScenicInfo> seasonRecommendScenic = scenicInfoService.getSeasonRecommendScenic();
        List<ScenicInfo> recommendScenic = scenicInfoService.getRecommendScenic();
        List<ScenicInfo> recommendScenicWithImg = scenicInfoService.getRecommendScenicWithImg();
        Label parent = new Label();
        parent.setName("公共标签_景点主题");
        parent = labelService.list(parent, null).get(0);
        Label label = new Label();
        label.setParent(parent);
        label.setStatus(LabelStatus.USE);
        List<Label> scenicTheme = labelService.list(label, null);
        List<Ads> adses = adsBuilder.getAds(LVXBANG_SCENIC_BANNER);
        ScenicSearchRequest request = new ScenicSearchRequest();
        List<Long> count = new ArrayList<Long>();
        for (Label theme : scenicTheme) {
            request.setTheme(theme.getName());
            count.add(scenicInfoService.countFromSolr(request));
        }
        for (int i = 0; i < count.size(); i++) {
            for (int j = i; j < count.size(); j++) {
                if (count.get(i) < count.get(j)) {
                    Collections.swap(count, i, j);
                    Collections.swap(scenicTheme, i, j);
                }
            }
        }
        scenicTheme = scenicTheme.subList(0, 6);
        data.put("seasonRecommendScenic", seasonRecommendScenic);
        data.put("recommendScenic", recommendScenic);
        data.put("recommendScenicWithImg", recommendScenicWithImg);
        data.put("scenicTheme", scenicTheme);
        data.put("adses", adses);
        FreemarkerUtil.create(data, LVXBANG_SCENIC_INDEX_TEMPLATE, LVXBANG_SCENIC_INDEX_TARGET);
    }
}
