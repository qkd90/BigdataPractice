package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.lvxbang.util.PlanTransformer;
import com.data.data.hmly.service.plan.PlanDayService;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.PlanTripService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.PlanTrip;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.entity.RecommendPlanDay;
import com.data.data.hmly.service.plan.entity.RecommendPlanTrip;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

//import com.data.hmly.service.translation.direction.baidu.pojo.BaiduTaxiDirection;

/**
 * @author Jonathan.Guo
 */
@Service
public class PlanOperateService {

//    private final Logger logger = Logger.getLogger(PlanOperateService.class);

    @Resource
    private PlanService planService;
    @Resource
    private PlanDayService planDayService;
    @Resource
    private PlanTripService planTripService;
    @Resource
    private RecommendPlanService recommendPlanService;


    public void doTransformPlan(RecommendPlan recommendPlan) {
        Plan plan = getPlanFromRecommendPlan(recommendPlan);
        planService.save(plan);
        for (RecommendPlanDay recommendPlanDay : recommendPlan.getRecommendPlanDays()) {
            PlanDay planDay = getPlanDayFromRecommendPlanDay(plan, recommendPlanDay);
            planDayService.save(planDay);
            for (RecommendPlanTrip trip : recommendPlanDay.getRecommendPlanTrips()) {
                PlanTrip planTrip = getPlanTripFromRecommendPlanTrip(plan, planDay, trip);
                planTripService.save(planTrip);

            }
        }
    }

    public Plan doTransformPlan(Long recommendPlanId) {
        RecommendPlan recommendPlan = recommendPlanService.get(recommendPlanId);
        Plan plan = getPlanFromRecommendPlan(recommendPlan);
        planService.save(plan);
        for (RecommendPlanDay recommendPlanDay : recommendPlan.getRecommendPlanDays()) {
            PlanDay planDay = getPlanDayFromRecommendPlanDay(plan, recommendPlanDay);
            planDayService.save(planDay);
            for (RecommendPlanTrip trip : recommendPlanDay.getRecommendPlanTrips()) {
                PlanTrip planTrip = getPlanTripFromRecommendPlanTrip(plan, planDay, trip);
                planTripService.save(planTrip);

            }
        }
        return null;
    }

    public Plan getPlanFromRecommendPlan(RecommendPlan recommendPlan) {
        Plan plan = new Plan();
        plan.setUser(recommendPlan.getUser());
        plan.setCity(recommendPlan.getCity());
        plan.setScenicId(recommendPlan.getScenicId());
        plan.setCityIds(recommendPlan.getCityIds());
        plan.setProvince(recommendPlan.getProvince());
        plan.setName(recommendPlan.getPlanName());
//        plan.setTips(recommendPlan.getTips());
        plan.setStartTime(recommendPlan.getStartTime());
        plan.setReturnTime(recommendPlan.getStartTime());
        plan.setCoverPath(recommendPlan.getCoverPath());
        plan.setCreateTime(new Date());
        plan.setPlanDays(recommendPlan.getDays());
        plan.setStatus(1);
//        plan.setPlanCitys(recommendPlan.getCityIds());
        plan.setValid(true);
//        plan.setTag(recommendPlan.getTagStr());
        plan.setDescription(recommendPlan.getDescription());
        plan.setStartCity(recommendPlan.getCity());
        plan.setPassScenics(recommendPlan.getPassScenics());
        plan.setPlatform(1);
        plan.setPushFlag(0);
        plan.setSource(11);
        plan.setSourceId(recommendPlan.getId());
        return plan;
    }

    protected PlanTrip getPlanTripFromRecommendPlanTrip(Plan plan, PlanDay day, RecommendPlanTrip trip) {
        PlanTrip planTrip = PlanTransformer.fromRecommend(trip);
        if (planTrip == null)
            return null;
        planTrip.setPlan(plan);
        planTrip.setPlanDay(day);

        planTripService.save(planTrip);

        return planTrip;
    }

    protected PlanDay getPlanDayFromRecommendPlanDay(Plan plan, RecommendPlanDay recommendPlanDay) {
        PlanDay planDay = PlanTransformer.fromRecommendDay(recommendPlanDay);
        planDay.setPlan(plan);
        return planDay;
    }

}
