package com.data.data.hmly.service.lvxbang.util;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.PlanStatistic;
import com.data.data.hmly.service.plan.entity.PlanTrip;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.plan.entity.RecommendPlanDay;
import com.data.data.hmly.service.plan.entity.RecommendPlanTrip;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Jonathan.Guo
 */
public class PlanTransformer {

    public static RecommendPlan fromPlan(Plan plan) {
        RecommendPlan recommendPlan = new RecommendPlan();
        recommendPlan.setUser(plan.getUser());
        recommendPlan.setScenicId(plan.getScenicId());
        recommendPlan.setCity(plan.getCity());
        recommendPlan.setCityIds(plan.getCityIds());
        recommendPlan.setProvince(plan.getProvince());
        recommendPlan.setShareNum(plan.getPlanStatistic().getShareNum());
        recommendPlan.setQuoteNum(plan.getPlanStatistic().getQuoteNum());
        recommendPlan.setCollectNum(plan.getPlanStatistic().getCollectNum());
        recommendPlan.setPlanName(plan.getName());
        recommendPlan.setDescription(plan.getDescription());
        recommendPlan.setCoverPath(plan.getCoverPath());
        recommendPlan.setCoverSmall(plan.getCoverSmall());
        recommendPlan.setCreateTime(new Date());
        recommendPlan.setModifyTime(new Date());
        recommendPlan.setDays(plan.getPlanDays());
        int scenics = 0;
        for (PlanDay planDay : plan.getPlanDayList()) {
            scenics += planDay.getPlanTripList().size();
        }
        recommendPlan.setScenics(scenics);
        recommendPlan.setValid(1);
        recommendPlan.setCost(plan.getPlanStatistic().getPlanCost());
        recommendPlan.setHotelCost(plan.getPlanStatistic().getPlanHotelCost());
        recommendPlan.setFoodCost(plan.getPlanStatistic().getPlanFoodCost());
        recommendPlan.setTravelCost(plan.getPlanStatistic().getPlanTravelCost());
        recommendPlan.setTicketCost(plan.getPlanStatistic().getPlanTicketCost());
        recommendPlan.setSeasonticketCost(plan.getPlanStatistic().getPlanSeasonticketCost());
        recommendPlan.setIncludeSeasonticketCost(plan.getPlanStatistic().getIncludeSeasonticketCost());
        recommendPlan.setStatus(0);
        recommendPlan.setDeleteFlag(0);
        recommendPlan.setCreatedBy(plan.getUser());
        recommendPlan.setUpdatedBy(plan.getUser());
        return recommendPlan;
    }

    public static RecommendPlanDay fromPlanDay(PlanDay planDay) {
        RecommendPlanDay recommendPlanDay = new RecommendPlanDay();
        recommendPlanDay.setDay(planDay.getDays());
        recommendPlanDay.setModifyTime(new Date());
        recommendPlanDay.setCreateTime(new Date());
        recommendPlanDay.setScenics(planDay.getPlanTripList().size());
        recommendPlanDay.setHours(planDay.getPlayTime());
        if (planDay.getCity() != null) {
            recommendPlanDay.setCitys(planDay.getCity().getId().toString());
        }
        if (planDay.getHotel() != null) {
            recommendPlanDay.setHotel(planDay.getHotel().getName());
        }
        recommendPlanDay.setTicketCost(planDay.getTicketCost());
        recommendPlanDay.setIncludeSeasonticketCost(planDay.getIncludeSeasonticketCost());
        recommendPlanDay.setSeasonticketCost(planDay.getIncludeSeasonticketCost() - planDay.getTicketCost());
        recommendPlanDay.setTrafficCost(planDay.getTrafficCost());
        recommendPlanDay.setHotelCost(planDay.getHotelCost());
        recommendPlanDay.setTrafficTime(planDay.getTrafficTime());
        recommendPlanDay.setCost(planDay.getFoodCost() + planDay.getHotelCost() + planDay.getTicketCost() + planDay.getTrafficCost());
        recommendPlanDay.setFoodCost(planDay.getFoodCost());
        return recommendPlanDay;
    }

    public static RecommendPlanTrip fromPlanTrip(PlanTrip planTrip) {
        RecommendPlanTrip recommendPlanTrip = new RecommendPlanTrip();
        recommendPlanTrip.setUser(planTrip.getUser());
        recommendPlanTrip.setScenicId(planTrip.getScenicInfo().getId());
        recommendPlanTrip.setTrafficType(planTrip.getTravelType());
        recommendPlanTrip.setTripType(planTrip.getTripType());
        recommendPlanTrip.setStartTime(planTrip.getStartTime());
        recommendPlanTrip.setOrderNum(planTrip.getOrderNum());
        recommendPlanTrip.setTripDesc(planTrip.getTripDesc());
        recommendPlanTrip.setModifyTime(new Date());
        recommendPlanTrip.setCreateTime(new Date());
        recommendPlanTrip.setOldPrice(planTrip.getScenicInfo().getPrice());
        recommendPlanTrip.setScenicName(planTrip.getScenicInfo().getName());
        recommendPlanTrip.setSort(planTrip.getOrderNum());
        recommendPlanTrip.setCityCode(planTrip.getScenicInfo().getCityCode());
        return recommendPlanTrip;
    }

    public static Plan copyPlan(Plan plan) {
        Plan target = new Plan();
        BeanUtils.copyProperties(target, plan);
        Date now = new Date();
        target.setId(null);
        target.setStartTime(DateUtils.truncateTime(new Date()));
        target.setReturnTime(DateUtils.add(target.getStartTime(), Calendar.DAY_OF_MONTH, target.getPlanDays()));
        target.setCreateTime(now);
        target.setModifyTime(now);
        target.setPushFlag(1);
        target.setSource(1);
        target.setStatus(Plan.STATUS_VALID);
        //remove mapped data to avoid error
        target.setPlanDayList(null);
        PlanStatistic planStatistic = new PlanStatistic();
        BeanUtils.copyProperties(planStatistic, plan.getPlanStatistic());
        planStatistic.setId(null);
        planStatistic.setPlan(target);
        planStatistic.setCollectNum(0);
        planStatistic.setCommentNum(0);
        planStatistic.setQuoteNum(0);
        planStatistic.setShareNum(0);
        planStatistic.setViewNum(0);
        target.setPlanStatistic(planStatistic);
        return target;
    }

    public static PlanDay copyPlanDay(PlanDay planDay) {
        PlanDay dayTarget = new PlanDay();
        BeanUtils.copyProperties(dayTarget, planDay);
        dayTarget.setId(null);
        dayTarget.setCreateTime(new Date());
        dayTarget.setModifyTime(new Date());
        dayTarget.setPlanTripList(null);
        return dayTarget;
    }

    public static PlanTrip copyPlanTrip(PlanTrip planTrip) {
        PlanTrip tripTarget = new PlanTrip();
        BeanUtils.copyProperties(tripTarget, planTrip);
        tripTarget.setId(null);
        tripTarget.setCreateTime(new Date());
        tripTarget.setModifyTime(new Date());
        return tripTarget;
    }


    public static PlanTrip fromRecommend(RecommendPlanTrip recommendPlanTrip) {
        if (recommendPlanTrip.getTripType() != 1) {
            return null;
        }
        PlanTrip planTrip = new PlanTrip();
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setId(recommendPlanTrip.getScenicId());
        planTrip.setScenicInfo(scenicInfo);
        if (recommendPlanTrip.getTrafficType() != null) {
            planTrip.setTravelType(recommendPlanTrip.getTrafficType());
            if (recommendPlanTrip.getTrafficType() == 1) {
                planTrip.setTravelDetail(recommendPlanTrip.getBusDetail());
                planTrip.setTravelTime(recommendPlanTrip.getBusTime());
                planTrip.setTravelHours(recommendPlanTrip.getBusHour());
                planTrip.setTravelMileage(recommendPlanTrip.getBusMilleage());
            } else if (recommendPlanTrip.getTrafficType() == 2) {
                planTrip.setTravelDetail(recommendPlanTrip.getTaxiDetail());
                planTrip.setTravelTime(recommendPlanTrip.getTaxiTime());
                planTrip.setTravelHours(recommendPlanTrip.getTaxiHour());
                planTrip.setTravelMileage(recommendPlanTrip.getTaxiMilleage());
            } else {
                planTrip.setTravelDetail(recommendPlanTrip.getWalkDetail());
                planTrip.setTravelDetail(recommendPlanTrip.getWalkTime());
                planTrip.setTravelHours(recommendPlanTrip.getWalkHour());
                planTrip.setTravelMileage(recommendPlanTrip.getWalkMilleage());
            }
        }
        planTrip.setTripType(recommendPlanTrip.getTripType());
        planTrip.setStartTime(recommendPlanTrip.getStartTime());
        planTrip.setOrderNum(recommendPlanTrip.getOrderNum());
        planTrip.setTripDesc(recommendPlanTrip.getTripDesc());
        planTrip.setModifyTime(new Date());
        planTrip.setCreateTime(new Date());
        planTrip.setTravelPrice(recommendPlanTrip.getOldPrice());
        return planTrip;
    }

    public static PlanDay fromRecommendDay(RecommendPlanDay recommendPlanDay) {
        PlanDay planDay = new PlanDay();
        if (StringUtils.isNotBlank(recommendPlanDay.getCitys())) {
            String[] cityIds = recommendPlanDay.getCitys().split(",");
            TbArea city = new TbArea();
            city.setId(Long.valueOf(cityIds[0]));
            planDay.setCity(city);
        }
//        this.traffic = traffic;
//        this.hotel = hotel;
        planDay.setDays(recommendPlanDay.getDay());
        planDay.setModifyTime(new Date());
        planDay.setCreateTime(new Date());
        planDay.setTicketCost(recommendPlanDay.getTicketCost());
        planDay.setIncludeSeasonticketCost(recommendPlanDay.getIncludeSeasonticketCost());
        planDay.setTrafficCost(recommendPlanDay.getTrafficCost());
        planDay.setHotelCost(recommendPlanDay.getHotelCost());
        planDay.setFoodCost(recommendPlanDay.getFoodCost());
        planDay.setPlayTime(recommendPlanDay.getHours());
        planDay.setTrafficTime(recommendPlanDay.getTrafficTime());
        return planDay;
    }

    public static Plan fromRecommendTrip(RecommendPlan recommendPlan) {
        Plan plan = new Plan();
        plan.setName(recommendPlan.getPlanName());
        plan.setScenicId(recommendPlan.getScenicId());
        plan.setCity(recommendPlan.getCity());
        plan.setCityIds(recommendPlan.getCityIds());
        plan.setProvince(recommendPlan.getProvince());
        plan.setStartTime(DateUtils.truncateTime(new Date()));
        plan.setReturnTime(DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, recommendPlan.getDays()));
        plan.setPublicFlag(false);
        plan.setRecommendFlag(false);
        plan.setCoverPath(recommendPlan.getCoverPath());
        plan.setCoverSmall(recommendPlan.getCoverSmall());
        plan.setCreateTime(new Date());
        plan.setModifyTime(new Date());
        plan.setPlanDays(recommendPlan.getDays());
        plan.setStatus(Plan.STATUS_VALID);
        plan.setValid(true);
        plan.setDescription(recommendPlan.getDescription());
        plan.setPlatform(1);
        plan.setPushFlag(1);
        plan.setSource(1);
        plan.setSourceId(recommendPlan.getId());
        PlanStatistic planStatistic = new PlanStatistic();

        planStatistic.setId(null);
        planStatistic.setPlan(plan);
        planStatistic.setCollectNum(0);
        planStatistic.setCommentNum(0);
        planStatistic.setQuoteNum(0);
        planStatistic.setShareNum(0);
        planStatistic.setViewNum(0);
        planStatistic.setPlanCost(recommendPlan.getCost());
        planStatistic.setIncludeSeasonticketCost(recommendPlan.getIncludeSeasonticketCost());
        planStatistic.setPlanFoodCost(recommendPlan.getFoodCost());
        planStatistic.setPlanHotelCost(recommendPlan.getHotelCost());
        planStatistic.setPlanSeasonticketCost(recommendPlan.getSeasonticketCost());
        planStatistic.setPlanTicketCost(recommendPlan.getTicketCost());
        planStatistic.setPlanTravelCost(recommendPlan.getTravelCost());
        plan.setPlanStatistic(planStatistic);
        return plan;
    }
}
