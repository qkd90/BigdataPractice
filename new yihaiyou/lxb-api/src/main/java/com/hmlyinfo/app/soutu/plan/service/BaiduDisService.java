package com.hmlyinfo.app.soutu.plan.service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.Point;
import com.hmlyinfo.app.soutu.plan.domain.RecommendPlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.scenic.domain.City;
import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.base.util.Clock;
import com.hmlyinfo.base.util.HttpClientUtils;
import com.hmlyinfo.base.util.ListUtil;

@Service
public class BaiduDisService {

	private static final Logger			logger				= Logger.getLogger(BaiduDisService.class);

	private static ObjectMapper			om					= new ObjectMapper();
	private static final String			API_KEY				= "R9424rkP6oyCzex5FuLa7XIw";

	private static final int			TYPE_WALK			= 3;
	private static final int			TYPE_TAXI			= 2;

	private static final int			THREAD_SIZE			= 100;
	private static final int			BAIDU_INTERVAL		= 60 * 1000;
	private static final int			BAIDU_WAIT_TIME		= 61 * 1000;

	private static final String			MODE_WALK			= "walking";
	private static final String			MODE_DRIVE			= "driving";
	private static final String			MODE_TRANSIT		= "transit";
	// 鼓浪屿的senicId
	private static final long			GULANGYU_SCENICID	= 13165;
	// 轮渡码头
	private static final String			LUNDU_NAME			= "鼓浪屿";
	private static final double			LUNDU_LNG			= 118.080102;
	private static final double			LUNDU_LAT			= 24.460961;

	AtomicInteger						threadCounter		= new AtomicInteger(0);
	AtomicLong							timer				= new AtomicLong(System.currentTimeMillis() - System.currentTimeMillis()
																	% BAIDU_INTERVAL);
	AtomicInteger						tripCounter			= new AtomicInteger();
	AtomicInteger						planCounter			= new AtomicInteger();
	AtomicLong							tripTimer			= new AtomicLong();

	@Autowired
	private PlanTripService				planTripService;
	@Autowired
	private CtripHotelService			hotelService;
	@Autowired
	private RestaurantService			restaurantService;
	@Autowired
	private ScenicInfoService			scenicInfoService;
	@Autowired
	private CityService					cityService;
	@Autowired
	private RecommendPlanTripService	recommendPlanTripService;

	// 已经完成的线程数量
	private final AtomicInteger			completeSize		= new AtomicInteger();

	private class CheckThread extends Thread {
		PlanTrip	sTrip;
		PlanTrip	eTrip;

		public CheckThread(PlanTrip sTrip, PlanTrip eTrip) {
			this.sTrip = sTrip;
			this.eTrip = eTrip;
		}

		@Override
		public void run() {
			try {
				Point sPoint = trip2Point(sTrip.getTripType(), sTrip.getScenicId());
				Point ePoint = trip2Point(eTrip.getTripType(), eTrip.getScenicId());
				if (gulangyu(sPoint) * gulangyu(ePoint) == -1) {
					if (gulangyu(sPoint) == 1) {
						sPoint.lng = LUNDU_LNG;
						sPoint.lat = LUNDU_LAT;
					} else {
						ePoint.lng = LUNDU_LNG;
						ePoint.lat = LUNDU_LAT;
					}
				}
				sTrip.setBaiduMap(dealDis(sPoint, ePoint));
			} catch (Exception e) {
				logger.error("plan#" + sTrip.getPlanId() + "执行失败", e);
			}
			completeSize.getAndIncrement(); // 每完成一个线程都要记录当次行程的完成数
		}
	}

	public int gulangyu(Point point) {
		if (point.scenicId == GULANGYU_SCENICID || point.father == GULANGYU_SCENICID) {
			return 1;
		} else {
			return -1;
		}
	}

	public List<PlanTrip> planDis(long planId) throws Exception {
		completeSize.getAndSet(0);

		List<PlanTrip> planTrips = planTripService.list(Collections.<String, Object> singletonMap("planId", planId));

		return setTripBaiduMap(planTrips);
	}

	public List<PlanTrip> setTripBaiduMap(List<PlanTrip> planTrips) {
		Clock clock = new Clock();
		int tripSize = planTrips.size();

		// 计算线程剩余空间
		int limit = THREAD_SIZE - threadCounter.get();

		for (int i = 0; i < tripSize - 1; i++) {
			final PlanTrip sTrip = planTrips.get(i);
			final PlanTrip eTrip = planTrips.get(i + 1);
			// 还差1个线程就占满线程空间时
			if (i == limit - 1) {
				logger.info("目前1分钟已执行" + threadCounter.get() + "个线程");
				if (System.currentTimeMillis() - timer.get() < BAIDU_INTERVAL) {
					logger.info("即将超限，等待" + (BAIDU_WAIT_TIME - (System.currentTimeMillis() - timer.get())) + "毫秒");
					try {
						Thread.sleep(BAIDU_WAIT_TIME - (System.currentTimeMillis() - timer.get()));
					} catch (InterruptedException e) {
						logger.error("线层等待失败");
					}
				}
			}
			new CheckThread(sTrip, eTrip).start();
		}
		logger.info("目前1分钟已执行" + threadCounter.get() + "个线程");
		while (completeSize.get() < tripSize - 1) { // 保证线程都执行完了
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("线层等待失败");
			}
		}
		tripCounter.getAndAdd(completeSize.get());
		tripTimer.getAndAdd(clock.elapseTime());
		logger.info("当前时间：" + new SimpleDateFormat("hh:mm:ss").format(new Date(timer.get())) + "，共完成" + planCounter.incrementAndGet() + ""
				+ tripCounter.get() + "个节点，花费" + tripTimer.get() + "毫秒");
		completeSize.getAndSet(0);
		return planTrips;
	}

	// 推荐行程计算交通信息
	public List<RecommendPlanTrip> recommendPlanDis(long rePlanId) {
		List<RecommendPlanTrip> tripList = recommendPlanTripService.list(Collections.<String, Object> singletonMap("recommendPlanId",
				rePlanId));
		for (int i = 0; i < tripList.size() - 1; i++) {
			if (threadCounter.get() > 98) {
				logger.info("目前1分钟已执行" + threadCounter.get() + "个线程");
				if (System.currentTimeMillis() - timer.get() < BAIDU_INTERVAL) {
					logger.info("即将超限，等待" + (BAIDU_WAIT_TIME - (System.currentTimeMillis() - timer.get())) + "毫秒");
					try {
						Thread.sleep(BAIDU_WAIT_TIME - (System.currentTimeMillis() - timer.get()));
					} catch (InterruptedException e) {
						logger.error("等待失败，" + e);
					}
				}
				threadCounter.getAndSet(0);
				timer.getAndSet(System.currentTimeMillis() - System.currentTimeMillis() % BAIDU_INTERVAL);
			}
			RecommendPlanTrip sTrip = tripList.get(i);
			RecommendPlanTrip eTrip = tripList.get(i + 1);
			Point sPoint = trip2Point(sTrip.getTripType(), sTrip.getScenicId());
			Point ePoint = trip2Point(eTrip.getTripType(), eTrip.getScenicId());
			Map<String, Object> baiduMap = dealDis(sPoint, ePoint);
			sTrip.setBaiduMap(baiduMap);
		}
		logger.info("推荐行程" + rePlanId + "执行完成");
		return tripList;
	}

	public Point trip2Point(int tripType, long scenicId) {
		Point point = new Point();
		String name = "";
		Double lng = (double) 0;
		Double lat = (double) 0;
		long father = 0;
		long cityCode = 0;
		if (tripType == TripType.HOTEL.value()) {
			CtripHotel hotel = hotelService.info(scenicId);
			name = hotel.getHotelName();
			lng = hotel.getLongitude();
			lat = hotel.getLatitude();
			cityCode = hotel.getCityCode();
		} else if (tripType == TripType.RESTAURANT.value()) {
			Restaurant restaurant = restaurantService.info(scenicId);
			name = restaurant.getResName();
			lng = Double.parseDouble(restaurant.getResLongitude());
			lat = Double.parseDouble(restaurant.getResLatitude());
			cityCode = restaurant.getCityCode();
		} else if (tripType == TripType.SCENIC.value()) {
			Map<String, Object> scenic = (Map<String, Object>) scenicInfoService.info(scenicId);

			name = (String) scenic.get("name");
			lng = (Double) scenic.get("longitude");
			lat = (Double) scenic.get("latitude");
			scenicId = (Long) scenic.get("id");
			if (scenic.get("father") != null) {
				father = (Long) scenic.get("father");
			}

			cityCode = Integer.parseInt((String) scenic.get("cityCode"));
		}

		List<City> cityList = cityService.list(Collections.<String, Object> singletonMap("cityCode", cityCode));
		City city = ListUtil.getSingle(cityList);
		String cityName = city.getName();
		point.name = name;
		point.lng = lng;
		point.lat = lat;
		point.cityName = cityName;
		point.scenicId = scenicId;
		point.father = father;
		return point;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> dealDis(Point startPoint, Point endPoint) {

		Map<String, Object> resMap = new HashMap<String, Object>();
		// 从百度返回数据
		Map<String, Object> walkMap = getBaiduMap(startPoint, endPoint, MODE_WALK);
		// 步行距离（单位：米）
		int walkDis = 0;
		int walkTime = 0;
		if (walkMap == null)
			return walkMap;
		if (walkMap.get("routes") != null) {
			walkDis = (Integer) ((List<Map<String, Object>>) walkMap.get("routes")).get(0).get("distance");
			walkTime = (Integer) ((List<Map<String, Object>>) walkMap.get("routes")).get(0).get("duration") / 60;
		}

		resMap.put("walkDis", walkDis);
		resMap.put("walkTime", walkTime);
		resMap.put("taxiDis", null);
		resMap.put("taxiTime", null);
		resMap.put("cost", null);
		// 判断直线距离，如果超过1000米就打车
		if (walkDis < 1000) {
			resMap.put("type", TYPE_WALK);
		} else {
			resMap.put("type", TYPE_TAXI);
			Map<String, Object> taxiMap = getBaiduMap(startPoint, endPoint, MODE_DRIVE);
			// 打车距离
			int taxiDis = (Integer) ((List<Map<String, Object>>) taxiMap.get("routes")).get(0).get("distance");
			// 打车时间
			int taxiTime = (Integer) ((List<Map<String, Object>>) taxiMap.get("routes")).get(0).get("duration") / 60;
			// 打车费用
			double cost = 0;
			if (taxiMap.get("taxi") != null) {
				Map<String, Object> taxiCostMap = (Map<String, Object>) taxiMap.get("taxi");
				cost = Double.parseDouble((String) ((List<Map<String, Object>>) taxiCostMap.get("detail")).get(0).get("total_price"));
			}
			resMap.put("taxiDis", taxiDis);
			resMap.put("taxiTime", taxiTime);
			resMap.put("cost", cost);
		}
		return resMap;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getBaiduMap(Point startPoint, Point endPoint, String mode) {
		// 从上个时间点到现在还没有超过一个百度频率计算周期，则线程数+1
		if (System.currentTimeMillis() - timer.get() < BAIDU_INTERVAL) {
			threadCounter.getAndIncrement();
		} else { // 否则重置周期开始时间点，线程数重新计算
			timer.getAndSet(System.currentTimeMillis() - System.currentTimeMillis() % BAIDU_INTERVAL);
			threadCounter.getAndSet(1);
		}

		String scityname = startPoint.cityName;
		String ecityname = endPoint.cityName;

		String origin = startPoint.lat + "," + startPoint.lng;
		String destination = endPoint.lat + "," + endPoint.lng;

		String baiduUrl = "http://api.map.baidu.com/direction/v1?mode=" + mode + "&origin=" + origin + "&destination=" + destination
				+ "&origin_region=" + scityname + "&destination_region=" + ecityname + "&output=json&ak=" + API_KEY;

		Map<String, Object> resultMap;
		try {
			long t1 = new Date().getTime();
			HttpClient httpClient = HttpClientUtils.getHttpClient();
			HttpPost httpPost = new HttpPost(baiduUrl);
			httpPost.setHeader("Accept-Encoding", "gzip");
			HttpResponse response = httpClient.execute(httpPost);
			String baiduStr = EntityUtils.toString(new GzipDecompressingEntity(response.getEntity()), "utf-8");
			// String baiduStr = HttpClientUtils.getHttp(baiduUrl);
			long t2 = new Date().getTime();

			Map<String, Object> drivingMap = om.readValue(baiduStr, Map.class);
			resultMap = (Map<String, Object>) drivingMap.get("result");
		} catch (Exception e) {
			resultMap = null;
		}
		return resultMap;
	}

}
