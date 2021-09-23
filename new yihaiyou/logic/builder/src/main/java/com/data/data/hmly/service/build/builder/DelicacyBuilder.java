package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.comment.CommentScoreTypeService;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.data.data.hmly.service.comment.entity.enums.commentScoreTypeStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.restaurant.entity.DelicacyExtend;
import com.data.data.hmly.service.restaurant.entity.DelicacyRestaurant;
import com.data.data.hmly.service.restaurant.entity.Restaurant;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.data.data.hmly.util.Clock;
import com.data.data.solr.MulticoreSolrTemplate;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.SpringContextHolder;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by chenjiaqin on 2015/12/17.
 */
@Service
public class DelicacyBuilder {
    private static final String LVXBANG_DELICACY_DETAIL_TEMPLATE = "/lvxbang/delicacy/detail.ftl";
    private static final String LVXBANG_DELICACY_DETAIL_TARGET = "/lvxbang/delicacy/detail%d.htm";
    private static final String LVXBANG_DELICACY_INDEX_TEMPLATE = "/lvxbang/delicacy/index.ftl";
    private static final String LVXBANG_DELICACY_INDEX_TARGET = "/lvxbang/delicacy/index.htm";
    private static final String LVXBANG_DELICACY_HEAD_TEMPLATE = "/lvxbang/delicacy/head.ftl";
    private static final String LVXBANG_DELICACY_HEAD_TARGET = "/lvxbang/delicacy/head%d.htm";


    private static final int LVXBANG_DELICACY_INDEX_FEATURE_NUMBER = 8;

    private static final int PAGE_SIZE = 100;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private AreaService areaService;
    @Resource
    private AdsBuilder adsBuilder;
    @Resource
    private CommentScoreTypeService commentScoreTypeService;
    @Resource
    private MulticoreSolrTemplate solrTemplate;


    //    public void buildLXBDetail() {
//        List<Delicacy> delicacies = delicacyService.listAll();
//        for (Delicacy delicacy : delicacies) {
//            buildLXBDetail(delicacy);
//        }
//    }
    public void buildLXBDetail() {
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildLXBDetailTask();
            }
        });
    }

    private Object buildLXBDetailTask() {
        int current;
        int page = 1;
        Clock clock = new Clock();
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            List<Delicacy> delicacies = delicacyService.list(new Delicacy(), new Page(page, PAGE_SIZE));
            for (Delicacy delicacy : delicacies) {
                buildLXBDetail(delicacy);
            }
            current = delicacies.size();
            System.out.println("build完成第" + page + "页，花费" + clock.elapseTime() + "ms");
            page++;
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } while (current == PAGE_SIZE);
        return null;
    }

    public void buildLXBDetail(Long id) {
        Delicacy delicacy = delicacyService.get(id);
        buildLXBDetail(delicacy);
    }

    private void buildLXBDetail(Delicacy delicacy) {
        if (delicacy.getStatus() != 1) {
            return;
        }
        Map<Object, Object> data = Maps.newHashMap();
        CommentScoreType commentScoreType = new CommentScoreType();
        commentScoreType.setTargetType(ProductType.delicacy);
        commentScoreType.setStatus(commentScoreTypeStatus.USE);
        List<CommentScoreType> commentScoreTypes = commentScoreTypeService.list(commentScoreType, null);
        data.put("delicacy", delicacy);
        List<DelicacyRestaurant> delicacyRestaurants = delicacy.getRestaurants();
        DelicacyExtend delicacyExtend = delicacy.getExtend();
        List<Map<String, Object>> restaurants = Lists.newArrayList();
        for (int i = 0; i < delicacyRestaurants.size(); i++) {
            Map<String, Object> restaurantList = Maps.newHashMap();
            Restaurant r = delicacyRestaurants.get(i).getRestaurant();
            if (r == null) {
                continue;
            }
            if (r.getGeoInfo() == null) {
                continue;
            }
            restaurantList.put("restaurant", r);
            List<ScenicInfo> scenicList = Lists.newArrayList();
            //restaurants.add(delicacyRestaurants.get(i).getRestaurant());
            Double baidulng = r.getGeoInfo().getBaiduLng();
            Double baidulat = r.getGeoInfo().getBaiduLat();
            r.getExtend().getAddress();
            r.getExtend().getTelephone();
            r.getExtend().getShortComment();
            if (baidulng == null || baidulat == null) {
                continue;
            }
            SolrQuery query = new SolrQuery("type:scenic_info");
            QueryResponse response = solrTemplate.nearBy(query, baidulat.toString(), baidulng.toString(), ScenicSolrEntity.class, SolrQuery.ORDER.asc, 10f);
            SolrDocumentList result = response.getResults();
            if (result.size() > 0) {
                for (int j = 0; j < Math.min(3, result.size()); j++) {

                    SolrDocument entity = result.get(j);
                    Long id = (Long) entity.getFieldValue("id");
                    String name = (String) entity.getFieldValue("name");
                    ScenicInfo scenic = new ScenicInfo();
                    scenic.setId(id);
                    scenic.setName(name);
                    scenicList.add(scenic);
                }
            }
            restaurantList.put("scenicList", scenicList);
//            for (SolrDocument entity : result) {
//                System.out.println(entity.getFieldValue("name"));
//            }
            //每家饭店信息（）
            restaurants.add(restaurantList);
        }

        TbArea city = delicacy.getCity();
        data.put("city", city);
        data.put("restaurants", restaurants);
        data.put("delicacyExtend", delicacyExtend);
        data.put("commentScoreTypes", commentScoreTypes);
        FreemarkerUtil.create(data, LVXBANG_DELICACY_DETAIL_TEMPLATE, String.format(LVXBANG_DELICACY_DETAIL_TARGET, delicacy.getId()));
        FreemarkerUtil.create(data, LVXBANG_DELICACY_HEAD_TEMPLATE, String.format(LVXBANG_DELICACY_HEAD_TARGET, delicacy.getId()));
    }

    public void buildLXBIndex() {
        List<Ads> adses = adsBuilder.getAds(AdsBuilder.LVXBANG_DELICACY_BANNER);
        Map<Object, Object> data = Maps.newHashMap();

        List<RecommendPlan> delicacyRecommendPlan = recommendPlanService.getDelicacyRecommendPlan();
        List<Delicacy> recommendDelicacy = delicacyService.getRecommendDelicacy();
        Label label1 = new Label();
        label1.setName("特色美食城市");
        List<TbArea> featuredDelicacyCity = areaService.getAreaByLabel(label1);
        data.put("featuredDelicacyCity", featuredDelicacyCity);
        List<List<Delicacy>> featureDelicacyList = Lists.newArrayList();
        for (TbArea tbArea : featuredDelicacyCity) {
            List<Delicacy> featuredDelicacies = getFeatureDelicaciesByCity(tbArea);
            featureDelicacyList.add(featuredDelicacies);
        }
        data.put("featureDelicacyList", featureDelicacyList);
        Label label2 = new Label();
        label2.setName("美食城市");
        List<TbArea> delicacyCity = areaService.getAreaByLabel(label2);
        data.put("delicacyRecommendPlan", delicacyRecommendPlan);
        data.put("recommendDelicacy", recommendDelicacy);
        data.put("delicacyCity", delicacyCity);
        data.put("adses", adses);

        FreemarkerUtil.create(data, LVXBANG_DELICACY_INDEX_TEMPLATE, LVXBANG_DELICACY_INDEX_TARGET);
    }

    private List<Delicacy> getFeatureDelicaciesByCity(TbArea tbArea) {
        List<Delicacy> featuredDelicacies = delicacyService.getFeaturedDelicacy(tbArea.getCityCode());
        if (featuredDelicacies.size() >= LVXBANG_DELICACY_INDEX_FEATURE_NUMBER) {
            return featuredDelicacies.subList(0, 8);
        }
        List<Long> featureDelicacyIdList = Lists.transform(featuredDelicacies, new Function<Delicacy, Long>() {
            @Override
            public Long apply(Delicacy delicacy) {
                return delicacy.getId();
            }
        });
        Delicacy filter = new Delicacy();
        TbArea cityFilter = new TbArea();
        cityFilter.setId(tbArea.getId());
        filter.setCity(cityFilter);
        List<Delicacy> additionalDelicacy = delicacyService.list(filter, new Page(0, LVXBANG_DELICACY_INDEX_FEATURE_NUMBER), "extend.agreeNum", "desc");
        for (Delicacy delicacy : additionalDelicacy) {
            if (featuredDelicacies.size() >= LVXBANG_DELICACY_INDEX_FEATURE_NUMBER) {
                break;
            }
            if (!featureDelicacyIdList.contains(delicacy.getId())) {
                featuredDelicacies.add(delicacy);
            }
        }
        return featuredDelicacies;
    }
}
