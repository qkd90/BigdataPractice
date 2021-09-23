package com.hmlyinfo.app.soutu.plan.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.plan.domain.Plan;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlan;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanDay;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanImage;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanTips;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.mapper.RecommendPlanMapper;
import com.hmlyinfo.app.soutu.plan.mapper.RecommendPlanTipsMapper;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendPlanService extends BaseService<RecommendPlan, Long> {
	
	// 步行
	private static final int TYPE_WALK = 3;
	// 打车
	private static final int TYPE_TAXI = 2;

    @Autowired
    private RecommendPlanMapper<RecommendPlan> mapper;
    @Autowired
    private RecommendPlanTipsMapper<RecommendPlanTips> rptMapper;
    @Autowired
    private RecommendPlanDayService rdService;
    @Autowired
    private RecommendPlanTripService tripService;
    @Autowired
    private CtripHotelService ctripHotelService;
    @Autowired
    private ScenicInfoService scenicService;
    @Autowired
	private RestaurantService restService;
    @Autowired
    private RecommendPlanImageService recommendPlanImageService;
    @Autowired
    private PlanTripService planTripService;
    @Autowired
    private BaiduDisService baiduDisService;

    @Override
    public BaseMapper<RecommendPlan> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }
    
    public List<RecommendPlan> listColumns(Map<String, Object> paramMap, List<String> columns) {
        paramMap.put("needColumns", columns);
        return mapper.listColumns(paramMap);
    }

    public RecommendPlan detail(long id) {

        // 查询行程天
        RecommendPlan rp = this.info(id);
        // 查询行程天景点，行程天中已经包含了花费和建议游玩时间
        List<RecommendPlanDay> dayList = rdService.list(ImmutableMap.of("recommendPlanId", (Object) id));
        int scenicCount = 0;
        // 查询景点、酒店、和餐厅
        for (RecommendPlanDay planDay : dayList) {
            Map<String, Object> tripParam = Maps.newHashMap();
            tripParam.put("recommendPlanId", id);
            tripParam.put("recommendPlanDayId", planDay.getId());
            List<RecommendPlanTrip> tripList = tripService.list(tripParam);
            List<RecommendPlanImage> imgList = recommendPlanImageService.list(tripParam);
            Multimap<Long, RecommendPlanImage> imgMap = ArrayListMultimap.create();
            for (RecommendPlanImage img : imgList) {
                imgMap.put(img.getRecommendPlanTripId(), img);
            }

            List<PlanTrip> planTripList = planTripService.list(Collections.<String, Object>singletonMap("planDaysId", planDay.getPlanDayId()));
            Map<Integer, PlanTrip> planTripMap = new HashMap<Integer, PlanTrip>();
            for (PlanTrip planTrip : planTripList) {
                planTripMap.put(planTrip.getOrderNum() - 1, planTrip);
            }
            Multimap<Integer, Long> idsMap = ArrayListMultimap.create();
            for (int i = 0; i < tripList.size(); i++) {
                RecommendPlanTrip trip = tripList.get(i);
                trip.setRecommendPlanImageList((List<RecommendPlanImage>) imgMap.get(trip.getId()));
                // 查询酒店
                if (trip.getTripType() == TripType.HOTEL.value()) {
                    planDay.setHotelTrip(trip);
                    CtripHotel hotel = ctripHotelService.info(trip.getScenicId());
                    planDay.setHotel(hotel);
                }
                idsMap.put(trip.getTripType(), trip.getScenicId());
                if (planTripMap.get(i) == null) {
                    continue;
                }
                trip.setStartTime(planTripMap.get(i).getStartTime());
            }
            // 查询景点列表
            Map<String, Object> paramMap = Maps.newHashMap();
            Collection<Long> sidsL = idsMap.get(TripType.SCENIC.value());
            paramMap.put("ids", sidsL);
            // 统计景点数量
            scenicCount += sidsL.size();
            List<ScenicInfo> scenicList = scenicService.listColumns(paramMap, Lists.newArrayList("*"));
            List<Map<String, Object>> ntList = ListUtil.listJoin(tripList, scenicList, "scenicId=id", "detail", null);
            planDay.setScenicCount(sidsL.size());

            paramMap.put("ids", idsMap.get(TripType.RESTAURANT.value()));
            List<Restaurant> resList = restService.listColumns(paramMap, Lists.newArrayList("*"));
            ListUtil.listJoin(ntList, resList, "scenicId=id", "detail", null);
            
            paramMap.put("ids", idsMap.get(TripType.HOTEL.value()));
            List<CtripHotel> hotelList = ctripHotelService.listColumns(paramMap, Lists.newArrayList("*"));
            ListUtil.listJoin(ntList, hotelList, "scenicId=id", "detail", null);

            planDay.setTripList(ntList);
        }

        rp.setRecommendPlanDay(dayList);
        rp.setScenicCount(scenicCount);

        return rp;
    }


    public List<RecommendPlanTips> tipDetail(long id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("recommendPlanId", id);
        List<RecommendPlanTips> tipList = rptMapper.list(paramMap);
        return tipList;
    }


    public List<RecommendPlan> listDaysCount(Map<String, Object> paramMap) {
        List<RecommendPlan> rpList = this.listColumns(paramMap, Lists.newArrayList("plan_days"));
        List<Integer> planDaysList = new ArrayList<Integer>();
        List<RecommendPlan> resList = new ArrayList<RecommendPlan>();
        for (RecommendPlan rPlan : rpList) {
            if (rPlan == null)
                continue;
            int days = rPlan.getPlanDays();
            if (!planDaysList.contains(days)) {
                planDaysList.add(rPlan.getPlanDays());
                resList.add(rPlan);
            }
        }
        return resList;
    }

    public RecommendPlan getRecommendPlan(int cityCode, int day, String tag) {
//		RecommendPlan recommendPlan = planCacheService.getRecommendPlan(cityCode, day, tag);
//		if (recommendPlan != null) {
//			return recommendPlan;
//		}
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cityId", cityCode);
        params.put("planDays", day);
        if (!StringUtil.isEmpty(tag)) {
            params.put("tags", Collections.singletonList(tag));
        }
        List<RecommendPlan> list = list(params);
//		planCacheService.addRecommendPlan(cityCode, day, tag, list.get(0));
        return list.get(0);
    }
    
    //根据城市id列表查询每个城市对应的推荐行程数量
    public List<Map<String, Object>> countList(Map<String, Object> paramMap){
    	String cityIdString = (String) paramMap.get("cityIds");
    	String[] cityIdList = cityIdString.split(",");
    	List<Map<String, Object>> resList = new ArrayList<Map<String,Object>>();
    	for(String cityCode : cityIdList){
    		List<RecommendPlan> recommendPlans = list(Collections.<String, Object>singletonMap("cityId", cityCode));
    		
    		Map<String, Object> resMap = new HashMap<String, Object>();
    		resMap.put("cityCode", cityCode);
    		resMap.put("count", recommendPlans.size());
    		resList.add(resMap);
    	}
    	return resList;
    }
    
    // 获取推荐行程列表，需要每个行程包含的景点数量
    public List<RecommendPlan> listRecommendPlan(Map<String, Object> paramMap)
    {
    	List<RecommendPlan> recommendPlans = list(paramMap);
    	for(RecommendPlan recommendPlan : recommendPlans) {
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("recommendPlanId", recommendPlan.getId());
    		params.put("tripType", TripType.SCENIC.value());
    		recommendPlan.setScenicCount(tripService.count(params));
    	}
    	return recommendPlans;
    }
    
    
    // 更新推荐行程数据
    public void updateRecommendPlan(Map<String, Object> paramMap){
    	long recommendPlanId = Long.parseLong(paramMap.get("recommendPlanId").toString());
    	List<RecommendPlanTrip> recommendPlanTrips = baiduDisService.recommendPlanDis(recommendPlanId);
    	
    	int totalPrice = 0;
    	Map<Long, Integer> planPrice = new HashMap<Long, Integer>();
    	List<Long> planDayIdList = new ArrayList<Long>();
    	int dayPrice = 0;
    	for(int i = 0; i < recommendPlanTrips.size() - 1; i++){
    		RecommendPlanTrip planTrip = recommendPlanTrips.get(i);
    		boolean lastPoint = false;
    		if(planTrip.getRecommendPlanDayId() != recommendPlanTrips.get(i + 1).getRecommendPlanDayId()){
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
					planTrip.setTrafficType(TYPE_WALK);
					int time = (Integer) baiduMap.get("walkTime");
					String travelTime = "";
					
					if(time > 60){
						travelTime = travelTime + (time / 60) + "小时";
					}
					travelTime = travelTime + (time % 60) + "分钟";
					planTrip.setTrafficTime(travelTime);
					planTrip.setTrafficHours((Integer) baiduMap.get("walkTime"));
					planTrip.setTrafficPrice(0);
					planTrip.setTrafficMileage(baiduMap.get("walkDis") + "米");
					tripService.update(planTrip);
				}
    		}else {
				if (lastPoint == false) {
					double cost = (Double) baiduMap.get("cost");
					int time = (Integer) baiduMap.get("taxiTime");
					String travelTime = "";
					
					if(time > 60){
						travelTime = travelTime + (time / 60) + "小时";
					}
					travelTime = travelTime + (time % 60) + "分钟";
					planTrip.setTrafficTime(travelTime);
					planTrip.setTrafficType(TYPE_TAXI);
					planTrip.setTrafficHours(time);
					planTrip.setTrafficPrice(new Double(cost).intValue());
					int taxiDis = (Integer) baiduMap.get("taxiDis");
					if(taxiDis < 1000){
						planTrip.setTrafficMileage(taxiDis + "米");
					}else {
						// 1024米显示1公里
						if(taxiDis % 1000 < 100){
							planTrip.setTrafficMileage((taxiDis / 1000) + "公里");
						}else {
							double dis = Double.parseDouble(taxiDis + "") / 1000.0;
							DecimalFormat df = new DecimalFormat("0.0");
							planTrip.setTrafficMileage(df.format(dis) + "公里");
						}
					}
					
					tripService.update(planTrip);

					dayPrice += planTrip.getTrafficPrice();
					totalPrice += planTrip.getTrafficPrice();
					planDayIdList.add(planTrip.getRecommendPlanDayId());
					
				}
				
			}
    		
			if (lastPoint == false) {
				planPrice.put(planTrip.getRecommendPlanDayId(), dayPrice);
			}
    	}
    	// 更新行程总花费
    	// 暂时不需要
    	
    	
    	// 更新每天的交通花费
    	// 暂时不需要
//    	if(planDayIdList.size() > 0){
//    		Map<String, Object> planDayMap = new HashMap<String, Object>();
//        	planDayMap.put("ids", planDayIdList);
//        	List<PlanDay> planDayList = planDaysService.listColumns(planDayMap, Lists.newArrayList("id", "traffic_cost"));
//        	for(PlanDay planDay : planDayList){
//        		if(planPrice.get(planDay.getId()) != null){
//        			planDay.setTrafficCost(planPrice.get(planDay.getId()));
//        			planDaysService.update(planDay);
//        		}
//        	}
//    	}
    }
}
