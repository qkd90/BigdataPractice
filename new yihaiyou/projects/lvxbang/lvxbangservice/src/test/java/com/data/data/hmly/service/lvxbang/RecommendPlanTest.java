package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.request.RecommendPlanSearchRequest;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.framework.hibernate.util.Page;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2015/12/28.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class RecommendPlanTest {

    @Resource
    private RecommendPlanService recommendPlanService;


    @Test
    public void testGetReplanByDelicacy() {
        RecommendPlanSearchRequest recommendPlanSearchRequest = new RecommendPlanSearchRequest();
//        recommendPlanSearchRequest.setDelicacyIds(4L); //美食相关游记
        recommendPlanSearchRequest.setScenicId(13166L); //景点相关游记
        Page page = new Page(1, 10);
        List<RecommendPlanSolrEntity> recommendPlans = recommendPlanService.listFromSolr(recommendPlanSearchRequest, page);
        for (RecommendPlanSolrEntity solrEntity : recommendPlans) {
            System.err.println("id: " + solrEntity.getId());
            System.err.println("名称: " + solrEntity.getName());
            System.err.println("分享数: " + solrEntity.getShareNum());
            System.err.println("引用数: " + solrEntity.getQuoteNum());
        }
    }

    @Test
    public void testHotRecommendPlans() {
        List<RecommendPlan> hotPlans1 = recommendPlanService.getHotRecommendPlans();
        for (RecommendPlan plan : hotPlans1) {
            System.err.println("id: " + plan.getId());
            System.err.println("名称: " + plan.getPlanName());
            System.err.println("天数: " + plan.getDays());
            System.err.println("分享数: " + plan.getShareNum());
            System.err.println("引用数: " + plan.getQuoteNum());
        }
        List<RecommendPlan> hotPlans2 = recommendPlanService.getHotRecommendPlans(210200L);
        for (RecommendPlan plan : hotPlans2) {
            System.err.println("id: " + plan.getId());
            System.err.println("名称: " + plan.getPlanName());
            System.err.println("天数: " + plan.getDays());
            System.err.println("分享数: " + plan.getShareNum());
            System.err.println("引用数: " + plan.getQuoteNum());
        }
    }

    @Test
    public void testSeasonSelectRecplans() {
        List<RecommendPlan> hotPlans = recommendPlanService.getSeasonSelectRecplans();
        for (RecommendPlan plan : hotPlans) {
            System.err.println("id: " + plan.getId());
            System.err.println("名称: " + plan.getPlanName());
            System.err.println("天数: " + plan.getDays());
            System.err.println("分享数: " + plan.getShareNum());
            System.err.println("引用数: " + plan.getQuoteNum());
        }
    }

    @Test
    public void testUserPublishedRecplan() {
        Page page = new Page(1, 10);
        List<RecommendPlan> plans = recommendPlanService.getUserPublishedRecplan(page, 14L);
        for (RecommendPlan plan : plans) {
            System.err.println("id: " + plan.getId());
            System.err.println("名称: " + plan.getPlanName());
            System.err.println("天数: " + plan.getDays());
            Assert.isTrue(plan.getStatus() == 2);
        }
    }

    @Test
    public void testUserDraftRecplan() {
        Page page = new Page(1, 10);
        List<RecommendPlan> plans = recommendPlanService.getUserDraftRecplan(page, 14L);
        for (RecommendPlan plan : plans) {
            System.err.println("id: " + plan.getId());
            System.err.println("名称: " + plan.getPlanName());
            System.err.println("天数: " + plan.getDays());
            Assert.isTrue(plan.getStatus() == 1);
        }
    }

    @Test
    public void testIndexRecplan() {
        RecommendPlan recommendPlan = new RecommendPlan();
        recommendPlan.setId(177L);
        //todo: please fix it
//        recommendPlanService.indexRecommendPlanWithCondition(recommendPlan, new Page(1, 10)); //索引测试
    }

    @Test
    public void updateRecommendPlanInfo() {

    }
}
