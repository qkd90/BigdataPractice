package com.hmlyinfo.app.soutu.plan.service;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.hmlyinfo.app.soutu.delicacy.domain.Delicacy;
import com.hmlyinfo.app.soutu.delicacy.domain.DelicacyRes;
import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.service.DelicacyResService;
import com.hmlyinfo.app.soutu.delicacy.service.DelicacyService;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.Transportation;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.mapper.PlanTripMapper;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanPhoto;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanTrip;
import com.hmlyinfo.app.soutu.recplan.service.RecplanPhotoService;
import com.hmlyinfo.app.soutu.recplan.service.RecplanTripService;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicOther;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.app.soutu.scenic.service.ScenicOtherService;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.Validate;

@Service
public class PlanTripService extends BaseService<PlanTrip, Long> {

	Logger								logger		= Logger.getLogger(PlanTripService.class);

	// 步行
	private static final int			TYPE_WALK	= 3;
	// 打车
	private static final int			TYPE_TAXI	= 2;

	@Autowired
	private PlanTripMapper<PlanTrip>	mapper;
	@Autowired
	private PlanDaysService				planDaysService;
	@Autowired
	private PlanService					planService;
	@Autowired
	private CtripHotelService			ctripHotelService;
	@Autowired
	private ScenicInfoService			scenicInfoService;
	@Autowired
	private RestaurantService			restService;
	@Autowired
	private BaiduDisService				baiduDisService;
	@Autowired
	private RecplanPhotoService			recplanPhotoService;
	@Autowired
	private DelicacyService				delicacyService;
	@Autowired
	private TransportationService		transportationService;
	@Autowired
	private RecplanTripService			recplanTripService;
	@Autowired
	private DelicacyResService			delicacyResService;
	@Autowired
	private ScenicOtherService			scenicOtherService;

	@Override
	public BaseMapper<PlanTrip> getMapper() {
		return mapper;
	}

	@Override
	public String getKey() {
		return "id";
	}

	/**
	 * 查询行程景点列表
	 * 
	 * @param paramMap
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public List<Map<String, Object>> listScenic(Map<String, Object> paramMap) {
		List<PlanTrip> tripList = this.list(paramMap);
		Map<Integer, List<String>> colMap = Maps.newHashMap();
		colMap.put(TripType.SCENIC.value(), Lists.newArrayList("id", "name"));
		colMap.put(TripType.HOTEL.value(), Lists.newArrayList("id", "hotel_name"));
		colMap.put(TripType.RESTAURANT.value(), Lists.newArrayList("id", "res_name"));

		return listDetail(tripList, colMap);
	}

	/**
	 * 根据行程列表查询行程中的景点、酒店和餐厅
	 * 
	 * @param tripList
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public List<Map<String, Object>> listDetail(List<PlanTrip> tripList, Map<Integer, List<String>> colMap) {
		List<Map<String, Object>> dtList = new ArrayList<Map<String, Object>>();
		Multimap<Integer, Long> idsMap = ArrayListMultimap.create();

		int recType = 11;
		// 美食id
		int delicacyType = 12;

		// 查询酒店和美食关联
		Map<String, Object> delicacyMap = new HashMap<String, Object>();
		List<Long> resIds = new ArrayList<Long>();
		for (PlanTrip trip : tripList) {
			try {
				if (trip.getTripType() == TripType.RESTAURANT.value()) {
					resIds.add(trip.getScenicId());
				}
			} catch (Exception e) {
				logger.error("数据异常，该行程中存在不含有tripType的行程点");
			}
		}
		delicacyMap.put("resIds", resIds);
		List<DelicacyRes> delicacyResList = delicacyResService.list(delicacyMap);
		Map<Long, Long> delicacyResMap = new HashMap<Long, Long>();
		for (DelicacyRes delicacyRes : delicacyResList) {
			delicacyResMap.put(delicacyRes.getResId(), delicacyRes.getDelicacyId());
		}

		for (PlanTrip trip : tripList) {
			idsMap.put(trip.getTripType(), trip.getScenicId());
			if (trip.getSource() == PlanTrip.SOURCE_RECPLAN) {
				idsMap.put(recType, trip.getSourceId());
			}
			if (trip.getTripType() == TripType.RESTAURANT.value()) {
				if (trip.getDelicacyId() == 0) {
					if (delicacyResMap.get(trip.getScenicId()) != null) {
						trip.setDelicacyId(delicacyResMap.get(trip.getScenicId()));
					}
				}
				idsMap.put(delicacyType, trip.getDelicacyId());
			}

		}

		// 查询景点
		Map<String, Object> paramMap = Maps.newHashMap();
		List<ScenicInfo> scenicList = new ArrayList<ScenicInfo>();
		if (idsMap.get(TripType.SCENIC.value()).size() > 0) {
			paramMap.put("ids", idsMap.get(TripType.SCENIC.value()));
			scenicList = scenicInfoService.listColumns(paramMap, colMap.get(TripType.SCENIC.value()));
			dtList = ListUtil.listJoin(tripList, scenicList, "scenicId=id", "detail", null);

			Map<String, Object> scenicOtherMap = new HashMap<String, Object>();
			scenicOtherMap.put("scenicIds", idsMap.get(TripType.SCENIC.value()));
			List<ScenicOther> scenicOthers = scenicOtherService.list(scenicOtherMap);
			ListUtil.listJoin(dtList, scenicOthers, "scenicId=scenicInfoId", "scenicOther", null);
		}

		// 查询酒店
		if (idsMap.get(TripType.HOTEL.value()).size() > 0) {
			paramMap.put("ids", idsMap.get(TripType.HOTEL.value()));
			List<CtripHotel> hotelList = ctripHotelService.listColumns(paramMap, colMap.get(TripType.HOTEL.value()));
			if (dtList.size() == 0) {
				dtList = ListUtil.listJoin(tripList, hotelList, "scenicId=id", "detail", null);
			} else {
				ListUtil.listJoin(dtList, hotelList, "scenicId=id", "detail", null);
			}
		}

		// 查询餐厅
		if (idsMap.get(TripType.RESTAURANT.value()).size() > 0) {
			paramMap.put("ids", idsMap.get(TripType.RESTAURANT.value()));
			List<Restaurant> resList = restService.listColumns(paramMap, colMap.get(TripType.RESTAURANT.value()));

			if (dtList.size() == 0) {
				dtList = ListUtil.listJoin(tripList, resList, "scenicId=id", "detail", null);
			} else {
				ListUtil.listJoin(dtList, resList, "scenicId=id", "detail", null);
			}
		}

		// 查询美食
		if (idsMap.get(delicacyType).size() > 0) {
			paramMap.put("ids", idsMap.get(delicacyType));
			List<Delicacy> delicacies = delicacyService.list(paramMap);

			if (dtList.size() == 0) {
				dtList = ListUtil.listJoin(tripList, delicacies, "delicacyId=id", "foodDetail", null);
			} else {
				ListUtil.listJoin(dtList, delicacies, "delicacyId=id", "foodDetail", null);
			}
		}

		// 交通枢纽
		if (idsMap.get(TripType.STATION.value()).size() > 0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ids", idsMap.get(TripType.STATION.value()));
			List<Transportation> transportations = transportationService.list(params);

			if (dtList.size() == 0) {
				dtList = ListUtil.listJoin(tripList, transportations, "scenicId=id", "detail", null);
			} else {
				ListUtil.listJoin(dtList, transportations, "scenicId=id", "detail", null);
			}
		}

		// 查询图片
		if (idsMap.get(recType).size() > 0) {
			Map<String, Object> recphotoMap = new HashMap<String, Object>();
			recphotoMap.put("rectripIds", idsMap.get(recType));
			List<RecplanPhoto> photos = recplanPhotoService.list(recphotoMap);
			Multimap<Long, RecplanPhoto> photoMap = ArrayListMultimap.create();
			for (RecplanPhoto recplanPhoto : photos) {
				photoMap.put(recplanPhoto.getRectripId(), recplanPhoto);
			}

			// 在每个trip中插入图片信息
			for (Map<String, Object> dtMap : dtList) {
				long recplanTripId = Long.parseLong(dtMap.get("sourceId").toString());
				if (photoMap.get(recplanTripId) != null) {
					dtMap.put("recphoto", photoMap.get(recplanTripId));
				}
			}

			// 查询recplanTrip中的额外信息
			Map<String, Object> recplanTripMap = new HashMap<String, Object>();
			recplanTripMap.put("ids", idsMap.get(recType));
			List<RecplanTrip> recplanTrips = recplanTripService.list(recplanTripMap);
			ListUtil.listJoin(dtList, recplanTrips, "sourceId=id", "recplanTrip", null);
		}

		return dtList;
	}


	/**
	 * 修改备注
	 * <ul>
	 * <li>必选:标识{planId}</li>
	 * </ul>
	 *
	 * @return
	 */
	public PlanTrip updateRemark(PlanTrip plantrip) {

		PlanTrip planTrip = info(plantrip.getId());

		Validate.dataAuthorityCheck(planTrip);

		String tripDesc = plantrip.getTripDesc().toString();
		planTrip.setTripDesc(tripDesc);

		update(planTrip);
		return planTrip;
	}

	public void deleteByDay(Long planDaysId) {
		mapper.delByDay(planDaysId);
	}

	public void deleteByPlan(Long planId) {
		mapper.delByPlan(planId);
	}

	@Transactional
	public void insertMore(List<PlanTrip> list) {
		
		long maxId = mapper.getMaxId();
		for (PlanTrip trip : list)
		{
			trip.setId(++maxId);
		}
		mapper.insertmore(list);
	}

	// 插入行程交通花费
	public void insertTrafficCost(long planId) {
		List<PlanTrip> planTrips = new ArrayList<PlanTrip>();
		try {
			planTrips = baiduDisService.planDis(planId);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Map<Long, Integer> planPrice = new HashMap<Long, Integer>();
		List<Long> planDayIdList = new ArrayList<Long>();
		int dayPrice = 0;
		for (int i = 0; i < planTrips.size() - 1; i++) {
			PlanTrip planTrip = planTrips.get(i);
			boolean lastPoint = false;
			if (planTrip.getPlanDaysId() != planTrips.get(i + 1).getPlanDaysId()) {
				lastPoint = true;
				dayPrice = 0;
			}
			int tripPrice = 0;
			if (planTrip.getBaiduMap() == null) {
				continue;
			}
			Map<String, Object> baiduMap = planTrip.getBaiduMap();
			int walkDis = (Integer) baiduMap.get("walkDis");
			if (walkDis == 0) {
				continue;
			}
			if (walkDis < 1000) {
				if (lastPoint == false) {
					planTrip.setTravelType(TYPE_WALK);
					int time = (Integer) baiduMap.get("walkTime");
					String travelTime = "";

					if (time > 60) {
						travelTime = travelTime + (time / 60) + "小时";
					}
					travelTime = travelTime + (time % 60) + "分钟";
					planTrip.setTravelTime(travelTime);
					planTrip.setTravelHours((Integer) baiduMap.get("walkTime"));
					planTrip.setTravelPrice(0);
					planTrip.setTravelMileage(baiduMap.get("walkDis") + "米");
					update(planTrip);
				}
			} else {
				if (lastPoint == false) {
					double cost = (Double) baiduMap.get("cost");
					int time = (Integer) baiduMap.get("taxiTime");
					String travelTime = "";

					if (time > 60) {
						travelTime = travelTime + (time / 60) + "小时";
					}
					travelTime = travelTime + (time % 60) + "分钟";
					planTrip.setTravelTime(travelTime);
					planTrip.setTravelType(TYPE_TAXI);
					planTrip.setTravelHours(time);
					planTrip.setTravelPrice(new Double(cost).intValue());
					int taxiDis = (Integer) baiduMap.get("taxiDis");
					if (taxiDis < 1000) {
						planTrip.setTravelMileage(taxiDis + "米");
					} else {
						// 1024米显示1公里
						if (taxiDis % 1000 < 100) {
							planTrip.setTravelMileage((taxiDis / 1000) + "公里");
						} else {
							double dis = Double.parseDouble(taxiDis + "") / 1000.0;
							DecimalFormat df = new DecimalFormat("0.0");
							planTrip.setTravelMileage(df.format(dis) + "公里");
						}
					}

					update(planTrip);

					dayPrice += planTrip.getTravelPrice();
					planDayIdList.add(planTrip.getPlanDaysId());

				}

			}

			if (lastPoint == false) {
				planPrice.put(planTrip.getPlanDaysId(), dayPrice);
			}
		}

		// 总行程交通花费
		int totalPrice = 0;
		// 更新每天的交通花费
		if (planDayIdList.size() > 0) {
			Map<String, Object> planDayMap = new HashMap<String, Object>();
			planDayMap.put("ids", planDayIdList);
			List<PlanDay> planDayList = planDaysService.listColumns(planDayMap, Lists.newArrayList("id", "traffic_cost"));
			for (PlanDay planDay : planDayList) {
				if (planPrice.get(planDay.getId()) != null) {
					planDay.setTrafficCost(planPrice.get(planDay.getId()));
					totalPrice += planDay.getTrafficCost();
					planDaysService.update(planDay);
				}
			}
		}

		// 更新行程总花费
		Plan plan = planService.info(planId);
		plan.setPlanTravelCost(totalPrice);
		plan.setPlanCost(plan.getPlanTicketCost() + plan.getPlanHotelCost() + totalPrice);
		plan.setPlanSeasonticketCost(plan.getIncludeSeasonticketCost() + plan.getPlanHotelCost() + totalPrice);
		planService.update(plan);

	}
	
}
