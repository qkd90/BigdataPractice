package com.hmlyinfo.app.soutu.recplan.service;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TravelType;
import com.hmlyinfo.app.soutu.plan.service.PlanDaysService;
import com.hmlyinfo.app.soutu.plan.service.PlanService;
import com.hmlyinfo.app.soutu.plan.service.PlanTripService;
import com.hmlyinfo.app.soutu.recplan.domain.Recplan;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanDay;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanTrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 实现对新推荐行程的操作
 */
@Service
public class RecplanOperationService {

	@Autowired
	private RecplanService recplanService;
	@Autowired
	private PlanService planService;
	@Autowired
	private PlanDaysService planDaysService;
	@Autowired
	private PlanTripService planTripService;
	@Autowired
	private RecplanDetailService recplanDetailService;

	/**
	 * 引用/收藏行程
	 *
	 * 复制一份推荐行程到行程
	 *
	 * @param id 推荐行程id
	 * @return Plan
	 */
	@Transactional
	public Plan quoteRecplan(long id) {

		// 获取推荐行程详细信息
		Recplan recplan = recplanDetailService.detail(id);
		int quoteNum = recplan.getQuoteNum();
		quoteNum++;
		recplan.setQuoteNum(quoteNum);
		recplanService.update(recplan);
		Plan plan = createPlanFromRecPlan(recplan);

		// 获取推荐行程天
		List<RecplanDay> recplanDays = recplan.getRecplanDays();
		for (RecplanDay recplanDay : recplanDays) {
			createPlanDayFromRecPlan(recplan, plan, recplanDay);
		}

		return plan;
	}


	private void createPlanDayFromRecPlan(Recplan recplan, Plan plan, RecplanDay recplanDay) {
		// 复制推荐行程天信息到行程天并插入数据库
		PlanDay day = new PlanDay();
		day.setPlanId(plan.getId());

		// 游玩时间
		int time = recplanDay.getHours();
		int hour = time / 60;
		int minute = time % 60;
		String spendTime = "";
		if (hour > 0) {
			spendTime = spendTime + hour + "小时";
		}
		if (minute > 0) {
			spendTime = spendTime + minute + "分钟";
		}
		day.setSpendTime(spendTime);
		day.setTicketCost(new Double(recplanDay.getTicketCost()).intValue());
		day.setIncludeSeasonticketCost(new Double(recplanDay.getIncludeSeasonticketCost()).intValue());
		day.setTrafficCost(new Double(recplanDay.getTrafficCost()).intValue());
		day.setTrafficTime(recplanDay.getTrafficTime());
		day.setHotelCost(new Double(recplanDay.getHotelCost()).intValue());
		day.setDays(recplanDay.getDay());
		day.setPlayTime(recplanDay.getHours());
		planDaysService.insert(day);
		long planDayId = day.getId();

		// 获取推荐行程点
		Collection<RecplanTrip> recplanTrips = recplanDay.getRecplanTrips();
		for (RecplanTrip recplanTrip : recplanTrips) {
			createPlanTripFromRecPlan(recplan, plan, planDayId, recplanTrip);
		}
	}

	private void createPlanTripFromRecPlan(Recplan recplan, Plan plan, long planDayId, RecplanTrip recplanTrip) {
		// 复制推荐行程点信息到行程点
		PlanTrip trip = new PlanTrip();
		trip.setPlanId(plan.getId());
		trip.setPlanDaysId(planDayId);
		trip.setUserId(plan.getUserId());
		trip.setScenicId(recplanTrip.getScenicId());
		trip.setTravelType(recplanTrip.getTravelType());
		trip.setSource(PlanTrip.SOURCE_RECPLAN);
		trip.setSourceId(recplanTrip.getId());
		// 交通信息
		if (recplanTrip.getTravelType() == TravelType.DRIVING.value()) {
			trip.setTravelHours(recplanTrip.getTaxiHour());
			trip.setTravelMileage(recplanTrip.getTaxiMilleage());
			trip.setTravelPrice(new Double(recplanTrip.getTaxiCost()).intValue());
			trip.setTravelTime(recplanTrip.getTaxiTime());
		} else if (recplanTrip.getTravelType() == TravelType.TRANSIT.value()) {
			trip.setTravelHours(recplanTrip.getBusHour());
			trip.setTravelMileage(recplanTrip.getBusMilleage());
			trip.setTravelPrice(new Double(recplanTrip.getBusCost()).intValue());
			trip.setTravelTime(recplanTrip.getBusTime());
		} else {
			trip.setTravelHours(recplanTrip.getWalkHour());
			trip.setTravelMileage(recplanTrip.getWalkMilleage());
			trip.setTravelPrice(new Double(recplan.getCost()).intValue());
			trip.setTravelTime(recplanTrip.getWalkTime());
		}
		trip.setTripType(recplanTrip.getTripType());
		trip.setStartTime(recplanTrip.getStartTime());
		trip.setOrderNum(recplanTrip.getOrderNum());
		trip.setTripDesc(recplanTrip.getTripDesc());
		planTripService.insert(trip);
	}

	public Plan createPlanFromRecPlan(Recplan recplan) {
		// 获取当前用户id
		long userId = MemberService.getCurrentUserId();

		// 复制推荐行程信息到行程并插入数据库
		Plan plan = new Plan();
		plan.setPlanName(recplan.getPlanName());
		plan.setUserId(userId);
		plan.setScenicId(recplan.getScenicId());
		plan.setCityIds(recplan.getCityIds());
		plan.setProvince(recplan.getProvince());
		plan.setShareNum(0);
		plan.setCommentNum(0);
		plan.setQuoteNum(0);
		plan.setCollectNum(0);
		plan.setPublicFlag(true);
		plan.setRecommendFlag(false);
		plan.setCoverPath(recplan.getCoverPath());
		plan.setCoverSmall(recplan.getCoverSmall());
		plan.setViewNum(0);
		plan.setPlanDays(recplan.getDays());
		plan.setStatus(Plan.PLAN_STATUS_TRUE);
		plan.setPlanCost(new Double(recplan.getCost()).intValue());
		plan.setIncludeSeasonticketCost(new Double(recplan.getIncludeSeasonticketCost()).intValue());
		plan.setPlanHotelCost(new Double(recplan.getHotelCost()).intValue());
		plan.setPlanTravelCost(new Double(recplan.getTravelCost()).intValue());
		plan.setPlanTicketCost(new Double(recplan.getTicketCost()).intValue());
		plan.setValid(true);
		plan.setSource(Plan.SOURCE_REC);
		plan.setSourceId(recplan.getId());
		plan.setTag(Plan.TAG_COLLECT);
		plan.setPlatform(Plan.PLATFORM_PC);
		planService.insert(plan);
		return plan;
	}
}
