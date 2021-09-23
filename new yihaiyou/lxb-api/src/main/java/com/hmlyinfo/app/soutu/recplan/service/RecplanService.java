package com.hmlyinfo.app.soutu.recplan.service;

import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlan;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanDay;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanImage;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.service.PlanService;
import com.hmlyinfo.app.soutu.plan.service.RecommendPlanService;
import com.hmlyinfo.app.soutu.recplan.domain.Recplan;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanDay;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanPhoto;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanTrip;
import com.hmlyinfo.app.soutu.recplan.mapper.RecplanMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;

@Service
public class RecplanService extends BaseService<Recplan, Long> {

	private static final Logger		logger	= Logger.getLogger(RecplanService.class);

	@Autowired
	private RecplanDayService		recplanDayService;
	@Autowired
	private RecplanTripService		recplanTripService;
	@Autowired
	private RecplanPhotoService		recplanPhotoService;

	@Autowired
	private RecommendPlanService	recommendPlanService;
	
	@Autowired
	private PlanService planService;

	@Autowired
	private RecplanMapper<Recplan>	mapper;

	@Override
	public BaseMapper<Recplan> getMapper() {
		return mapper;
	}

	@Override
	public String getKey() {
		return "id";
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public long insert(long id) {

		RecommendPlan recommendPlan = recommendPlanService.detail(id);

		Long planId = recommendPlan.getPlanId();
		Plan plan = planService.infoTrip(planId);
		
		Recplan recplan = new Recplan();
		recplan.setUserId(recommendPlan.getUserId());
		// recplan.setScenicId();
		Long cityId = recommendPlan.getCityId();
		recplan.setCityId(cityId);
		recplan.setCityIds(cityId + "");
		// recplan.setProvince();
		recplan.setShareNum(0);
		recplan.setQuoteNum(0);
		recplan.setCollectNum(0);
		recplan.setPlanName(plan.getPlanName());
		recplan.setDescription(recommendPlan.getRecommendReason());
		recplan.setCoverPath(recommendPlan.getCover());
		// recplan.setCoverSmall();
		recplan.setDays(recommendPlan.getPlanDays());
		recplan.setScenics(recommendPlan.getScenicCount());
		recplan.setCost((int) recommendPlan.getPlanCost());
		recplan.setHotelCost(0);
		recplan.setTravelCost(0);
		recplan.setTicketCost(0);
		recplan.setValid(1);
		// 更新旧行程为仅手机端显示
		recplan.setMark(recommendPlan.getMark());
		insert(recplan);
		long recplanId = recplan.getId();
		List<RecommendPlanDay> recommendPlanDays = recommendPlan.getRecommendPlanDay();
		for (int i = 0; i < recommendPlanDays.size(); i++) {
			RecommendPlanDay recommendPlanDay = recommendPlanDays.get(i);
			createRecPlanFromRecommendPlan(cityId, recplanId, i, recommendPlanDay);
		}

		return recplanId;

	}

	private void createRecPlanFromRecommendPlan(long cityId, long recPlanId, int i, RecommendPlanDay recommendPlanDay) {
		RecplanDay recplanDay = new RecplanDay();
		recplanDay.setRecplanId(recPlanId);
		recplanDay.setDay(i);
		recplanDay.setCost(Double.parseDouble(recommendPlanDay.getDayCost()));
		recplanDay.setHours(Integer.parseInt(recommendPlanDay.getTotalTime()));
		recplanDay.setCitys(cityId + "");
		recplanDay.setHotel(recommendPlanDay.getRecommendHotel());
		recplanDay.setFood(recommendPlanDay.getRecommendDelicacy());
		recplanDay.setScenics(recommendPlanDay.getScenicCount());
		recplanDayService.insert(recplanDay);
		long recplanDayId = recplanDay.getId();

		List<Map<String, Object>> recommendPlanTrips = recommendPlanDay.getTripList();

		for (int j = 0; j < recommendPlanTrips.size(); j++) {

			Map<String, Object> trip = recommendPlanTrips.get(j);

			createRecPlanTripFromRecommendPlan(recPlanId, recplanDayId, j, trip);
		}
	}

	private void createRecPlanTripFromRecommendPlan(long recplanId, long recplanDayId, int j, Map<String, Object> trip) {
		RecplanTrip recplanTrip = new RecplanTrip();

		recplanTrip.setRecplanId(recplanId);
		recplanTrip.setRecdayId(recplanDayId);
		// recplanTrip.setUserId();
		recplanTrip.setScenicId((Long) trip.get("scenicId"));
		recplanTrip.setTravelType((Integer) trip.get("trafficType"));
		recplanTrip.setTripType((Integer) trip.get("tripType"));
		recplanTrip.setStartTime((Time) trip.get("startTime"));
		recplanTrip.setOrderNum(j);
		// recplanTrip.setTripDesc();
		// recplanTrip.setTaxiCost(0);
		// recplanTrip.setTaxiDetail();
		// recplanTrip.setTaxiHour();
		// recplanTrip.setTaxiMilleage();
		// recplanTrip.setTaxiTime();
		// recplanTrip.setWalkDetail();
		// recplanTrip.setWalkHour();
		// recplanTrip.setWalkMilleage();
		// recplanTrip.setWalkTime();
		// recplanTrip.setBusCost();
		// recplanTrip.setBusDetail();
		// recplanTrip.setBusHour();
		// recplanTrip.setBusMilleage();
		// recplanTrip.setBusTime();
		recplanTripService.insert(recplanTrip);
		long recplanTripId = recplanTrip.getId();

		List<RecommendPlanImage> imgList = (List<RecommendPlanImage>) trip.get("recommendPlanImageList");
		for (RecommendPlanImage img : imgList) {
			RecplanPhoto recplanPhoto = new RecplanPhoto();
			recplanPhoto.setRecplanId(recplanId);
			recplanPhoto.setRecdayId(recplanDayId);
			recplanPhoto.setRectripId(recplanTripId);
			recplanPhoto.setPhotoLarge(img.getCoverLarge());
			recplanPhoto.setPhotoMedium(img.getCoverMedium());
			recplanPhoto.setPhotoSmall(img.getCoverSmall());
			// recplanPhoto.setDescription();
			recplanPhotoService.insert(recplanPhoto);
		}
	}



	// 查询满足条件的推荐行程
	public List<Recplan> listRecplans(Map<String, Object> paramMap) {
		int days = Integer.parseInt(paramMap.get("days").toString());
		List<Recplan> recplans = list(paramMap);
		if (recplans.size() > 0) {
			return recplans;
		}

		paramMap.remove("days");
		paramMap.put("daysU", days);
		recplans = list(paramMap);
		if (recplans.size() > 0) {
			return recplans;
		}

		paramMap.remove("daysU");
		paramMap.put("daysL", days);
		recplans = list(paramMap);

		return recplans;
	}

	// 查询满足条件的推荐行程(旧版)
	public List<Recplan> listRecplansOld(Map<String, Object> paramMap) {

		int days = Integer.parseInt(paramMap.get("days").toString());
		int costL = Integer.parseInt(paramMap.get("costL").toString());
		int costU = Integer.MAX_VALUE;
		if (paramMap.get("costU") != null) {
			costU = Integer.parseInt(paramMap.get("costU").toString());
		}

		// 一般：1000< x <= 3000
		// 特殊：0<= x <= 1000(两端等于)
		if (costL == 0) {
			costL = -1;
			paramMap.put("costL", costL);
		}

		List<Recplan> recplans = list(paramMap);
		// 根据条件查询到结果
		if (recplans.size() > 0) {
			return recplans;
		}

		// 存在满足资金要求但是不满足天数的推荐行程
		paramMap.remove("days");
		if (this.count(paramMap) > 0) {
			// 天数比预定天数小
			paramMap.put("daysU", days);
			paramMap.put("orderColumn", "days");
			paramMap.put("orderType", "desc");
			recplans = list(paramMap);
			if (recplans.size() > 0) {
				return recplans;
			}

			// 天数比预定天数大
			paramMap.remove("daysU");
			paramMap.put("daysL", days);
			recplans = list(paramMap);
			if (recplans.size() > 0) {
				return recplans;
			}
		}

		// 存在满足天数但是不满足资金条件的推荐行程
		paramMap.put("days", days);
		paramMap.remove("costL");
		paramMap.remove("costU");
		int newCostL = costL;
		int newCostU = costU;
		// 价格方向（1代表向减少的方向移动，2代表向增加的方向移动）
		int direction = 1;
		if (this.count(paramMap) > 0) {
			// 存在资金更少的行程
			while (true) {

				// 根据上一次查询的条件和查询方向计算本次查询条件和方向
				switch (newCostL) {
				case -1:
					newCostL = costU;
					direction = 2;
					break;
				case 1000:
					if (direction == 1) {
						newCostL = -1;
					} else {
						newCostL = 3000;
					}
					break;
				case 3000:
					if (direction == 1) {
						newCostL = 1000;
					} else {
						newCostL = 5000;
					}
					break;
				case 5000:
					if (direction == 1) {
						newCostL = 3000;
					}
					break;
				}

				if (newCostL == -1) {
					newCostU = 1000;
				} else if (newCostL == 5000) {
					newCostU = Integer.MAX_VALUE;
				} else {
					newCostU = newCostL + 2000;
				}

				paramMap.put("costL", newCostL);
				paramMap.put("costU", newCostU);
				recplans = list(paramMap);
				if (recplans.size() > 0) {
					return recplans;
				}

				// 查询完所有条件未找到
				// 这种情况不应该出现，因为前面只根据天数条件查询到了数据
				if (newCostL == 5000 && direction == 2) {
					break;
				}

			}
		}

		TripType.HOTEL.value();
		// 未查到行程，返回空数据
		return recplans;
	}

}
