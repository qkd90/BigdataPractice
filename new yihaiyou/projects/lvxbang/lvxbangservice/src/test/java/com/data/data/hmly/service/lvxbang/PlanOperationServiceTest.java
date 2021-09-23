package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.lvxbang.request.PlanDayUpdateRequest;
import com.data.data.hmly.service.lvxbang.request.PlanTripUpdateRequest;
import com.data.data.hmly.service.lvxbang.request.PlanUpdateRequest;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.dao.PlanDayDao;
import com.data.data.hmly.service.plan.dao.PlanTripDao;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.PlanTrip;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.entity.Member;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class PlanOperationServiceTest {

    @Resource
    private PlanOperationService planOperationService;
    @Resource
    private PlanService planService;
    @Resource
    private PlanDayDao planDayDao;
    @Resource
    private PlanTripDao planTripDao;
//    @Resource
//    private UserService userService;
    @Resource
    private MemberService memberService;
    @Resource
    private RecommendPlanService recommendPlanService;

    private Long id;

    @Before
    public void initial() {
        Plan condition = new Plan();
        Member user = new Member();
        user.setId(14l);
        condition.setUser(user);
        List<Plan> plans = planService.list(condition, new Page(0, 1));
        if (plans.isEmpty()) {
            Plan plan = planOperationService.createPlan(user, new Date());
            id = plan.getId();
        } else {
            id = plans.get(0).getId();
        }
    }

//    @Test
//    @Transactional
//    public void testCreatePlan() throws Exception {
//        User user = userService.get(14l);
//        Plan plan = planOperationService.createPlan(user, new Date());
//        Assert.notNull(plan.getId());
//        Plan inDbPlan = planService.get(plan.getId());
//        Assert.notNull(inDbPlan);
//    }

    @Test
    @Transactional
    public void testAddDays() throws Exception {
        Plan plan = planService.get(id);
        int addSize = 2;
        int days = plan.getPlanDayList().size();
        planOperationService.addDays(plan, addSize);
        Plan modifiedPlan = planService.get(plan.getId());
        Assert.isTrue(days + addSize == modifiedPlan.getPlanDays());
    }

    @Test
    public void testOptimize() throws Exception {

    }

    @Test
    @Transactional
    public void testExportRecommend() throws Exception {
        Plan plan = planService.get(id);
        RecommendPlan recommendPlan = planOperationService.exportRecommend(plan);
        Assert.notNull(recommendPlan);
        Assert.notNull(recommendPlan.getId());
        Assert.isTrue(recommendPlan.getRecommendPlanDays().size() == plan.getPlanDayList().size());
        for (int i = 0; i < recommendPlan.getRecommendPlanDays().size(); i++) {
            Assert.isTrue(recommendPlan.getRecommendPlanDays().get(i).getRecommendPlanTrips().size() == plan.getPlanDayList().get(i).getPlanTripList().size());
        }
        RecommendPlan inDbRecommendPlan = recommendPlanService.get(recommendPlan.getId());
        Assert.notNull(recommendPlan.getRecommendPlanDays().size() == inDbRecommendPlan.getRecommendPlanDays().size());
        for (int i = 0; i < recommendPlan.getRecommendPlanDays().size(); i++) {
            Assert.isTrue(recommendPlan.getRecommendPlanDays().get(i).getRecommendPlanTrips().size() == inDbRecommendPlan.getRecommendPlanDays().get(i).getRecommendPlanTrips().size());
        }
    }

    @Test
    public void testDoQuoteFromPlan() throws Exception {
        Plan plan = planService.get(id);
        Member user = memberService.get(14l);
        Plan target = planOperationService.doQuoteFromPlan(plan.getId(), user, "");
        Assert.notNull(target);
        Assert.notNull(target.getId());
    }

//    @Test
//    public void testDoQuoteFromRecommend() throws Exception {
//        List<RecommendPlan> recommendPlans = recommendPlanService.list(new RecommendPlan(), new Page(0, 1));
//        RecommendPlan recommendPlan = recommendPlans.get(0);
//        User user = userService.get(14l);
//        if (recommendPlan == null) {
//            Assert.notNull(null, "no recommend plan to test");
//        }
//        Plan target = planOperationService.doQuoteFromRecommend(recommendPlan.getId(), user, "");
//        Assert.notNull(target);
//        Assert.notNull(target.getId());
//    }

    @Test
    @Transactional
    public void testCreatePlan2() throws Exception {
        Member user = memberService.get(14l);
        PlanUpdateRequest request = new PlanUpdateRequest();
        request.name = "test plan";
        request.tips = "this is tips";
        request.startTime = new Date();
        request.days = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            PlanDayUpdateRequest dayRequest = new PlanDayUpdateRequest();
            dayRequest.cityId = 350200l;
            dayRequest.trips = Lists.newArrayList();
            for (int j = 0; j < 3; j++) {
                PlanTripUpdateRequest tripRequest = new PlanTripUpdateRequest();
                tripRequest.type = 1;
                tripRequest.scenicId = 13166l;
                tripRequest.traffic = 1;
                tripRequest.trafficCost = 100f;
                dayRequest.trips.add(tripRequest);
            }
            request.days.add(dayRequest);
        }
        Plan plan = planOperationService.createPlan(request, user, "");
        Assert.notNull(plan);
        Assert.notNull(plan.getId());
    }

    @Test
    @Transactional
    public void testUpdatePlan() throws Exception {
        Plan plan = planService.get(id);
        PlanUpdateRequest request = new PlanUpdateRequest();
        request.id = plan.getId();
        request.name = "test plan";
        request.tips = "this is tips";
        request.startTime = new Date();
        request.days = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            PlanDayUpdateRequest dayRequest = new PlanDayUpdateRequest();
            dayRequest.cityId = 350200l;
            dayRequest.trips = Lists.newArrayList();
            for (int j = 0; j < 3; j++) {
                PlanTripUpdateRequest tripRequest = new PlanTripUpdateRequest();
                tripRequest.type = 1;
                tripRequest.scenicId = 13166l;
                tripRequest.traffic = 1;
                tripRequest.trafficCost = 100f;
                dayRequest.trips.add(tripRequest);
            }
            request.days.add(dayRequest);
        }
        Member user = new Member();
        user.setId(-1l);
        Plan target = planOperationService.updatePlan(request, user, "");
        Assert.isNull(target);
        user.setId(14l);
        target = planOperationService.updatePlan(request, user, "");
        Assert.notNull(target);
        Assert.notNull(target.getId());
        List<PlanDay> planDays = planDayDao.findByHQL("from PlanDay where plan.id=" + id);
        Assert.isTrue(planDays.size() == 3);
        List<PlanTrip> planTrips = planTripDao.findByHQL("from PlanTrip where plan.id=" + id);
        Assert.isTrue(planTrips.size() == 9);
        for (PlanTrip planTrip : planTrips) {
            Assert.isTrue(planTrip.getScenicInfo().getId() == 13166l);
        }
        plan = planService.get(id);
        Assert.isTrue(plan.getName().equals(request.name));
    }
}