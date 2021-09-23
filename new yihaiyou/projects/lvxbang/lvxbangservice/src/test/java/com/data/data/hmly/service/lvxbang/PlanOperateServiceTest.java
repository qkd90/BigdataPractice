package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.plan.PlanDayService;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.PlanTripService;
import com.data.data.hmly.service.plan.RecommendPlanDayService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.RecommendPlanTripService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.entity.RecommendPlanDay;
import com.data.data.hmly.service.plan.entity.RecommendPlanTrip;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Sane on 16/5/3.
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class PlanOperateServiceTest {
    @Resource
    PlanOperateService planOperateService;
    @Resource
    RecommendPlanService recommendPlanService;
    @Resource
    RecommendPlanDayService recommendPlanDayService;
    @Resource
    RecommendPlanTripService recommendPlanTripService;
    @Resource
    private PlanService planService;
    @Resource
    private PlanDayService planDayService;
    @Resource
    private PlanTripService planTripService;

    @Test
    public void transformPlan() throws Exception {
        RecommendPlan condition = new RecommendPlan();
        condition.setStatus(2);
        List<RecommendPlan> recommendPlanList = recommendPlanService.list(condition, new Page(1, 1));
        for (RecommendPlan recommendPlan : recommendPlanList) {
            System.out.println("transform:" + recommendPlan.getId() + "\t" + recommendPlan.getPlanName());
            Criteria<RecommendPlanDay> planDayCriteria = new Criteria<RecommendPlanDay>(RecommendPlanDay.class);
            planDayCriteria.eq("recommendPlan", recommendPlan);
            List<RecommendPlanDay> recommendPlanDays = recommendPlanDayService.list(planDayCriteria);
            Plan plan = planOperateService.getPlanFromRecommendPlan(recommendPlan);
            planService.save(plan);
            RecommendPlanDay dayCondtion = new RecommendPlanDay();
            dayCondtion.setRecommendPlan(recommendPlan);

//            for (RecommendPlanDay recommendPlanDay : recommendPlan.getRecommendPlanDays()) {
            for (RecommendPlanDay recommendPlanDay : recommendPlanDays) {
                PlanDay planDay = planOperateService.getPlanDayFromRecommendPlanDay(plan, recommendPlanDay);
                planDayService.save(planDay);

                Criteria<RecommendPlanTrip> planTripCriteria = new Criteria<RecommendPlanTrip>(RecommendPlanTrip.class);
                planTripCriteria.eq("recommendPlanDay", recommendPlanDay);
                List<RecommendPlanTrip> recommendPlanTrips = recommendPlanTripService.list(planTripCriteria);
//                for (RecommendPlanTrip trip : recommendPlanTrips) {
//                    PlanTrip planTrip = planOperateService.getPlanTripFromRecommendPlanTrip(plan, planDay, trip);
//                    if (planTrip != null)
//                        planTripService.save(planTrip);
//                }
            }
        }
    }

}