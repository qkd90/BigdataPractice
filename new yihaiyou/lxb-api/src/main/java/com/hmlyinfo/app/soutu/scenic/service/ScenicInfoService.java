package com.hmlyinfo.app.soutu.scenic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.common.service.ISearchService;
import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.mapper.RestaurantMapper;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.mapper.CtripHotelMapper;
import com.hmlyinfo.app.soutu.plan.domain.PlanDay;
import com.hmlyinfo.app.soutu.plan.domain.PlanTrip;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.service.PlanDaysService;
import com.hmlyinfo.app.soutu.plan.service.PlanTripService;
import com.hmlyinfo.app.soutu.scenic.domain.City;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicOther;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicSearchParam;
import com.hmlyinfo.app.soutu.scenic.domain.Topic;
import com.hmlyinfo.app.soutu.scenic.domain.TopicScenic;
import com.hmlyinfo.app.soutu.scenic.mapper.ScenicInfoMapper;
import com.hmlyinfo.app.soutu.scenic.mapper.ScenicOtherMapper;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarTicket;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.service.QunarTicketService;
import com.hmlyinfo.app.soutu.scenicTicket.service.ScenicTicketService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.ResultList;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.StringUtil;

@Service
public class ScenicInfoService extends BaseService<ScenicInfo, Long> {

	@Autowired
	private ScenicInfoMapper<ScenicInfo> mapper;
	@Autowired
	private ScenicOtherMapper<ScenicOther> scenicOtherMapper;
	@Autowired
	private CtripHotelMapper<CtripHotel> cHotelMapper;
	@Autowired
	private RestaurantMapper<Restaurant> rMapper;
	@Autowired
	private PlanTripService planTripService;
	@Autowired
	private PlanDaysService planDaysService;
	@Autowired
	private CityService cityService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private TopicScenicService topicScenicService;
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private QunarTicketService qunarTicketService;
	@Autowired
	@Qualifier("openSearchService")
	private ISearchService searchService;
	@Autowired
	private ScenicTicketService scenicTicketService;

	@Override
	public BaseMapper<ScenicInfo> getMapper() {
		return mapper;
	}

	@Override
	public String getKey() {
		return "id";
	}
	
	// 智能优化增加景点
	public List<ScenicInfo> addScennic(Map<String,Object> paramMap)
	{
		return mapper.addScennic(paramMap);
	}
	
	/**
	 * 
	 * 根据景点城市、城市名或父景点id取得景点缩略信息。
	 * <ul>
	 * <li>必选：景点城市名{cityName}（三选一）</li>
	 * <li>必选：景点名{name}（三选一）</li>
	 * <li>必选：父景点id{father}（三选一）</li>
	 * <li>可选：分页大小{pageSize=10}</li>
	 * <li>可选：请求页码{page=1}</li>
	 * </ul>
	 * 
	 * @return ScenicList
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getScenicBrief(Map<String, Object> paramMap) {
		
		// 从2.7.0开始，cityCode不在通过in查找，而是通过cityCode进行前缀匹配
//		if (paramMap.get("cityCode") != null) {
//			Map<String, Object> cityParams = new HashMap<String, Object>();
//			cityParams.put("father", paramMap.get("cityCode"));
//			List<City> cityList = cityService.list(cityParams);
//			if (!cityList.isEmpty()) {
//				List<Long> cityIdList = ListUtil.getIdList(cityList, "cityCode");
//				cityIdList.add(Long.valueOf(paramMap.remove("cityCode").toString()));
//				paramMap.put("cityCodes", cityIdList);
//			}
//		}
		//List<Map<String, Object>> scenicInfoList = (List) mapper.list(paramMap);
		List<Map<String, Object>> scenicList = searchService.searchScenicByName(ScenicSearchParam.fromMap(paramMap));
		List<String> scenicIdList = ListUtil.getIdList(scenicList, "id");
		// 从OpenSearch查询出的ids本身是排好序的，因此从数据库取数据时按照ids里面的顺序取出来，并取出距离以及用户数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", scenicIdList);
		if (paramMap.get("longitude") != null) {
			params.put("longitude", paramMap.get("longitude"));
			params.put("latitude", paramMap.get("latitude"));
		}
		if (paramMap.get("gcjLng") != null) {
			params.put("gcjLng", paramMap.get("gcjLng"));
			params.put("gcjLat", paramMap.get("gcjLat"));
		}

		List<Map<String, Object>> scenicInfoList = mapper.listAndOrder(params);
				
		if (paramMap.get("planId") == null) {
			return scenicInfoList;
		}
		List<PlanTrip> planTrips = planTripService.list(Collections.singletonMap("planId", paramMap.get("planId")));
		List<PlanDay> planDays = planDaysService.list(Collections.singletonMap("planId", paramMap.get("planId")));
		Map<Long, Integer> planDayMap = new HashMap<Long, Integer>();
		for (PlanDay planDay : planDays) {
			planDayMap.put(planDay.getId(), planDay.getDays());
		}
		Map<Long, Integer> planTripMap = new HashMap<Long, Integer>();
		for (PlanTrip planTrip : planTrips) {
			planTripMap.put(planTrip.getScenicId(), planDayMap.get(planTrip.getPlanDaysId()));
		}

		for (Map<String, Object> scenicInfo : scenicInfoList) {
			Long scenicId = Long.valueOf(scenicInfo.get("id").toString());
			if (planTripMap.get(scenicId) == null) {
				scenicInfo.put("added", false);
			} else {
				scenicInfo.put("added", true);
			}
			scenicInfo.put("adddate", planTripMap.get(scenicId));
		}
		return scenicInfoList;
	}

	/**
	 * 针对百度地图绘制提供的获取景点数据API方法
	 * 
	 * @return ScenicList
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> listScenicForMap(Map<String, Object> paramMap) {
		if (paramMap.get("cityCode") != null) {
			Map<String, Object> cityParams = new HashMap<String, Object>();
			cityParams.put("father", paramMap.get("cityCode"));
			List<City> cityList = cityService.list(cityParams);
			if (!cityList.isEmpty()) {
				List<Long> cityIdList = ListUtil.getIdList(cityList, "cityCode");
				cityIdList.add(Long.valueOf(paramMap.remove("cityCode").toString()));
				paramMap.put("cityCodes", cityIdList);
			}
		}
		List<Map<String, Object>> scenicInfoList = new ArrayList<Map<String, Object>>();
		List<String> columnList = new ArrayList<String>();
		columnList.add("id");
		columnList.add("name");
		columnList.add("longitude");
		columnList.add("latitude");
		List<ScenicInfo> infoList = listColumns(paramMap, columnList);
		if (null != infoList && infoList.size() > 0) {
			for (ScenicInfo info : infoList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", info.getId());
				map.put("name", info.getName());
				map.put("longitude", info.getLongitude());
				map.put("latitude", info.getLatitude());
				scenicInfoList.add(map);
			}
		}

		if (paramMap.get("planId") == null) {
			return scenicInfoList;
		}
		List<PlanTrip> planTrips = planTripService.list(Collections.singletonMap("planId", paramMap.get("planId")));
		List<PlanDay> planDays = planDaysService.list(Collections.singletonMap("planId", paramMap.get("planId")));
		Map<Long, Integer> planDayMap = new HashMap<Long, Integer>();
		for (PlanDay planDay : planDays) {
			planDayMap.put(planDay.getId(), planDay.getDays());
		}
		Map<Long, Integer> planTripMap = new HashMap<Long, Integer>();
		for (PlanTrip planTrip : planTrips) {
			planTripMap.put(planTrip.getScenicId(), planDayMap.get(planTrip.getPlanDaysId()));
		}

		for (Map<String, Object> scenicInfo : scenicInfoList) {
			Long scenicId = Long.valueOf(scenicInfo.get("id").toString());
			if (planTripMap.get(scenicId) == null) {
				scenicInfo.put("added", false);
			} else {
				scenicInfo.put("added", true);
			}
			scenicInfo.put("adddate", planTripMap.get(scenicId));
		}
		return scenicInfoList;
	}

	public ActionResult countAsResult(Map<String, Object> paramMap) {
		if (paramMap.get("cityCode") != null) {
			Map<String, Object> cityParams = new HashMap<String, Object>();
			cityParams.put("father", paramMap.get("cityCode"));
			List<City> cityList = cityService.list(cityParams);
			if (!cityList.isEmpty()) {
				List<Long> cityIdList = ListUtil.getIdList(cityList, "cityCode");
				cityIdList.add(Long.valueOf(paramMap.remove("cityCode").toString()));
				paramMap.put("cityCodes", cityIdList);
			}
		}
		
		// 删除分页条件
		if(paramMap.get("pageDto") != null){
			paramMap.remove("pageDto");
		}
		int counts = searchService.countScenicByName(ScenicSearchParam.fromMap(paramMap));
		ActionResult result = new ActionResult();
		ResultList<Object> resultList = new ResultList<Object>();
		resultList.setCounts(counts);
		result.setResultList(resultList);
		return result;
	}

	/**
	 * 
	 * 根据搜索词反馈景点名。
	 * <ul>
	 * <li>必选：景点名{name}</li>
	 * <li>可选：分页大小{pageSize=10}</li>
	 * <li>可选：请求页码{page=1}</li>
	 * </ul>
	 * 
	 * @return ScenicList
	 */
	public List<Map<String, Object>> listName(Map<String, Object> paramMap) {
		
		return searchService.searchScenicByName(ScenicSearchParam.fromMap(paramMap));
	}

	/**
	 * 
	 * 根据城市返回城市景点总数
	 * <ul>
	 * <li>必选：城市名{cityName}</li>
	 * </ul>
	 * 
	 * @return City Number
	 */
	public int cityCount(Map<String, Object> paramMap) {

		return count(paramMap);
	}

	/**
	 * 
	 * 系统查询景点其他信息
	 * <ul>
	 * <li>必选：景点id{scenicId}</li>
	 * </ul>
	 * 
	 * @return ScenicOther
	 */
	public ScenicOther infoOther(Map<String, Object> paramMap) {
		paramMap.put("scenicInfoId", paramMap.get("scenicId"));
		paramMap.remove("scenicId");
		return scenicOtherMapper.list(paramMap).get(0);
	}

	/**
	 * 查询精简的scenicInfo信息
	 */
	public List<ScenicInfo> listBrief(Map<String, Object> params) {
		return mapper.listBrief(params);
	}

	public void updateShareNum(List<Long> ids) {
		mapper.updateShareNum(ids);
	}

	public List<ScenicInfo> listByPosition(Map<String, Object> params) {
		return mapper.selectByPosition(params);
	}

	public int countByPosition(Map<String, Object> params) {
		return mapper.countByPosition(params);
	}

	public List<Topic> listTopic(Map<String, Object> params) {
		return topicService.list(params);
	}

	public List listTopicScenic(Map<String, Object> paramMap) {
        List<TopicScenic> topicScenicList = topicScenicService.list(paramMap);
        List idList = ListUtil.getIdList(topicScenicList, "scenicId");

        Map<String, Object> scenicMap = new HashMap<String, Object>();
        scenicMap.put("ids", idList);
        if (paramMap.get("longitude") != null) {
            scenicMap.put("longitude", paramMap.get("longitude"));
        }
        if (paramMap.get("latitude") != null) {
            scenicMap.put("latitude", paramMap.get("latitude"));
        }
        if (paramMap.get("distance") != null) {
            scenicMap.put("distance", paramMap.get("distance"));
        }
        List<ScenicInfo> scenicInfoList = listBrief(scenicMap);
        List<Map<String, Object>> result = ListUtil.listJoin(topicScenicList, scenicInfoList, "scenicId=id", "scenic", null);
        Collections.sort(result, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                int dis1 = (Integer) (((Map<String, Object>) (o1.get("scenic"))).get("distance"));
                int dis2 = (Integer) (((Map<String, Object>) (o2.get("scenic"))).get("distance"));
                return dis1 - dis2;
            }
        });
        return result;
    }

	public List<ScenicInfo> listColumns(Map<String, Object> params, List<String> columns) {
		params.put("needColumns", columns);
		return mapper.listColumns(params);
	}

	/**
	 * 根据行程ID和行程类型统计门票价格和游玩时间
	 * 
	 * @param Map
	 *            <String, Object> paramMap将前台接收的参数封装称List<PlanTrip>集合调用countAmountAndPlayTime(List<PlanTrip>)方法
	 * @return HashMap 返回值HashMap说明：Map<"amount",统计后的价格> Map<"playTime"，统计后的游玩时间>
	 */
	public Map<String, Object> countAmountAndPlayTime(Map<String, Object> paramMap) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<PlanTrip> planTripList = new ArrayList<PlanTrip>();
		String tripJSON = paramMap.get("tripJSON").toString();
		List<Map<String, Object>> dataMap = new ObjectMapper().readValue(tripJSON, ArrayList.class);
		if (null != dataMap && dataMap.size() > 0) {
			for (Map<String, Object> map : dataMap) {
				long scenicId = Long.parseLong(map.get("id").toString());
				int tripType = Integer.parseInt(map.get("tripType").toString());
				PlanTrip planTrip = new PlanTrip();
				planTrip.setScenicId(scenicId);
				planTrip.setTripType(tripType);
				planTripList.add(planTrip);
			}
			resultMap = countAmountAndPlayTime(planTripList);
		}
		return resultMap;
	}
	
	/**
	 * 计算每天景点花费和游玩时间之和
	 * 版本2.0
	 * 规则：简单累加当天景点游玩时间
	 * 计算花费查询景点是否使用套票，计算使用套票和不使用套票的花费
	 * @return
	 */
	public Map<String, Object> countAmountAndPlayTimeV2(List<PlanTrip> list)
	{
		Map<Integer, List<String>> colMap = Maps.newHashMap();
		// 最低门票价格、采集门票价格、采集的游玩时间（分钟）
		colMap.put(TripType.SCENIC.value(), Lists.newArrayList("id", "lowest_price", "price", "advice_hours"));
		// 酒店的最低价格
		colMap.put(TripType.HOTEL.value(), Lists.newArrayList("id", "lowest_hotel_price"));
		colMap.put(TripType.RESTAURANT.value(), Lists.newArrayList("id", "price"));
		
		int totalPrice = 0;
		int totalMin = 0;
		int hotelPrice = 0;
		int totalSeasonTicketPrice = 0;
		int travelCost = 0;
		int travelMin = 0;
		int resCost = 0;
		List<Map<String, Object>> tripDetailList = planTripService.listDetail(list, colMap);
		
		List<Long> scenicCollection  = Lists.newArrayList();
		// 定义一个map存放景点价格
		Map<Long, Object> scenicPriceMap = Maps.newHashMap();
		for (Map<String, Object> tripDetail : tripDetailList)
		{
			Map<String, Object> dMap = (Map<String, Object>)tripDetail.get("detail");
			if (dMap == null)
			{
				continue;
			}
			
			int tripType = (Integer)tripDetail.get("tripType");
			
			if (tripType == TripType.SCENIC.value())
			{
				// 封装用于查询套票的景点id
				scenicCollection.add((Long)tripDetail.get("scenicId"));
				
				int lowestPrice = StringUtil.getIntFromDouble(dMap.get("lowestPrice"));
				int price = StringUtil.getIntFromObj(dMap.get("price"));
				if (lowestPrice != 0)
				{
					scenicPriceMap.put((Long)tripDetail.get("scenicId"), lowestPrice);
					totalPrice += lowestPrice;
				}
				else
				{
					scenicPriceMap.put((Long)tripDetail.get("scenicId"), price);
					totalPrice += price;
				}
				
				// 统计游玩时间
				int adviceHours = StringUtil.getIntFromObj(dMap.get("adviceHours"));
				totalMin += adviceHours;
			}
			else if (tripType == TripType.HOTEL.value())
			{
				// 先把酒店花费计算出来
				hotelPrice = StringUtil.getIntFromDouble(dMap.get("lowestHotelPrice"));
			}else if (tripType == TripType.RESTAURANT.value()) {
				// 计算餐厅花费时间和花费
				// 餐厅花费时间为每一家餐厅60分钟
				totalMin += 60;
				resCost += (Integer)dMap.get("price");
				
			}
			
			// 计算交通花费和交通时间
			travelCost += StringUtil.getIntFromDouble(tripDetail.get("travelPrice"));
			travelMin += StringUtil.getIntFromDouble(tripDetail.get("travelHours"));
		}
		
		List<QunarTicket> qunarTickets = qunarTicketService.listSeasonTicket(scenicCollection);
		// 若查询到套票，则处理套票数据
		if (qunarTickets != null)
		{
			// 匹配套票，计算花费
			for (QunarTicket t : qunarTickets)
			{
				Set<Long> seasonContain = scenicTicketService.useSeason(scenicCollection, t);
				if (seasonContain.size() > 0)
				{
					totalSeasonTicketPrice = StringUtil.getIntFromDouble(t.getPrice().getSalePrice());
					// 匹配到的景点需要移除，避免重复匹配
					scenicCollection.removeAll(seasonContain);
				}

			}
		}
		
		// 计算其它景点花费
		for (Long sid : scenicCollection)
		{
			int sprice = (Integer) scenicPriceMap.get(sid);
			totalSeasonTicketPrice += sprice;
		}
		
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("amount", totalPrice);
		resultMap.put("totalSeasonTicketPrice", totalSeasonTicketPrice);
		resultMap.put("hotelPrice", hotelPrice);
		resultMap.put("playTime", totalMin);
		resultMap.put("travelCost", travelCost);
		resultMap.put("travelMin", travelMin);
		resultMap.put("resCost", resCost);
		
		return resultMap;
	}

	/**
	 * 根据行程ID和行程类型统计门票价格和游玩时间
	 * 
	 * @param list
	 *            PlanTrip集合，ID和tripType不能为空
	 * @return HashMap HashMap说明：Map<"amount",统计后的价格> Map<"playTime"，统计后的游玩时间>
	 */
	public Map<String, Object> countAmountAndPlayTime(List<PlanTrip> list) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("amount", 0);
		resultMap.put("playTime", 0);
		resultMap.put("isCountedIds", "");
		List<String> scenicIds = new ArrayList<String>();
		List<String> restaurantIds = new ArrayList<String>();
		if (null != list && list.size() > 0) {
			for (PlanTrip planTrip : list) {
				long id = planTrip.getScenicId();
				int tripType = planTrip.getTripType();
				if (TripType.SCENIC.value() == tripType) {
					scenicIds.add(id + "");
				} else if (TripType.RESTAURANT.value() == tripType) {
					restaurantIds.add(id + "");
				}
			}
		} else {
			resultMap.remove("isCountedIds");
			return resultMap;
		}
		if (null != scenicIds && scenicIds.size() > 0) {
			Map<String, Object> scenicParamMap = new HashMap<String, Object>();
			scenicParamMap.put("ids", scenicIds);
			List<String> scenicColumns = new ArrayList<String>();
			scenicColumns.add("id");
			scenicColumns.add("name");
			scenicColumns.add("price");
			scenicColumns.add("advice_hours");
			scenicColumns.add("father");
			scenicColumns.add("is_city");
			List<ScenicInfo> scenicList = listColumns(scenicParamMap, scenicColumns);
			if (null != scenicList && scenicList.size() > 0) {
				for (ScenicInfo scenicInfo : scenicList) {
					cascadeCountScenicAmountAndPlayTime(scenicInfo, resultMap);
				}
			}
		}
		if (null != restaurantIds && restaurantIds.size() > 0) {
			Map<String, Object> restaurantParamMap = new HashMap<String, Object>();
			restaurantParamMap.put("ids", restaurantIds);
			List<String> restaurantColumns = new ArrayList<String>();
			restaurantColumns.add("id");
			restaurantColumns.add("res_price");
			List<Restaurant> restaurantList = restaurantService.listColumns(restaurantParamMap, restaurantColumns);
			if (null != restaurantList && restaurantList.size() > 0) {
				int amount = Integer.parseInt(resultMap.get("amount").toString());
				for (Restaurant restaurant : restaurantList) {
					amount += restaurant.getPrice();
				}
				resultMap.put("amount", amount);
			}
		}
		resultMap.remove("isCountedIds");
		return resultMap;
	}

	/**
	 * 递归统计景点门票及游玩时间
	 * 
	 * @param info
	 */
	private void cascadeCountScenicAmountAndPlayTime(ScenicInfo scenicInfo, Map<String, Object> resultMap) {
		// 如果是城市，则不进入统计
		if (null != scenicInfo && scenicInfo.getIsCity() != 1) {
			// 定义一个已经统计过的ID集合
			// 如同时去日光岩和菽庄花园，当程序获取日光岩的门票及游玩时间时，
			// 已经把鼓浪屿的门票和游玩时间统计过了，在获取菽庄花园的时候，就要跳过鼓浪屿的统计
			String isCountedIds = resultMap.get("isCountedIds").toString();
			int amount = Integer.parseInt(resultMap.get("amount").toString());
			int playTime = Integer.parseInt(resultMap.get("playTime").toString());
			// 获取scenicInfo的上层，判断如果是城市，说明scenicInfo就是顶级景点
			ScenicInfo fatherInfo = getSingleScenicInfo(scenicInfo.getFather());
			// 如果已经统计过，则不再累加
			if (isCountedIds.indexOf(scenicInfo.getId() + ",") < 0) {
				// amount（门票价格）与游玩时间不同，游玩时间只取顶级景点的游玩时间，而门票是一直往上层累加，
				// 如鼓浪屿的门票（其实是渡轮价格）是10元，日光岩的价格是60元，则到日光岩的总价格是70元
				amount += scenicInfo.getPrice();
				// 第三步：如果上层景点是NULL或者是城市，则说明是顶级景点，累加游玩时间
				if (null == fatherInfo || fatherInfo.getIsCity() == 1)
					playTime += scenicInfo.getAdviceHours();
				isCountedIds += scenicInfo.getId() + ",";
				resultMap.put("amount", amount);
				resultMap.put("playTime", playTime);
				resultMap.put("isCountedIds", isCountedIds);
			}
			// 如果上层景点不为空，则递归继续统计上层
			if (null != fatherInfo) {
				cascadeCountScenicAmountAndPlayTime(fatherInfo, resultMap);
			}
		}

	}

	private ScenicInfo getSingleScenicInfo(int infoId) {
		ScenicInfo tempInfo = null;
		if (0 != infoId) {
			Map<String, Object> tempObj = (Map<String, Object>) mapper.selById(infoId + "");
			if (null != tempObj && null != tempObj.get("id")) {
				Long id = Long.parseLong(tempObj.get("id").toString());
				int price = null == tempObj.get("price") ? 0 : Integer.parseInt(tempObj.get("price").toString());
				int hours = null == tempObj.get("adviceHours") ? 0 : Integer.parseInt(tempObj.get("adviceHours").toString());
				int father = null == tempObj.get("father") ? 0 : Integer.parseInt(tempObj.get("father").toString());
				String name = null == tempObj.get("name") ? "" : tempObj.get("name").toString();
				int isCity = null == tempObj.get("isCity") ? 0 : Integer.parseInt(tempObj.get("isCity").toString());
				tempInfo = new ScenicInfo();
				tempInfo.setId(id);
				tempInfo.setPrice(price);
				tempInfo.setAdviceHours(hours);
				tempInfo.setFather(father);
				tempInfo.setIsCity(isCity);
				tempInfo.setName(name);
			}
		}
		return tempInfo;
	}

	public static void main(String[] args) {
		String cityCode = "350200";
		String start = cityCode.substring(0, 4);
		String last = cityCode.substring(4, 6);
		System.out.println(start + "=====" + last);
	}
	
	
	public double updateLng(double bd_lng, double bd_lat)
	{
		double xpi = 3.14159265358979324 * 3000.0 / 180.0;
		double x = bd_lng - 0.0065;
		double y = bd_lat - 0.006;
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * xpi);
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * xpi);
	    double gg_lng = z * Math.cos(theta);
	    double gg_lat = z * Math.sin(theta);
	    return gg_lng;
	}
	public double updateLat(double bd_lng, double bd_lat)
	{
		double xpi = 3.14159265358979324 * 3000.0 / 180.0;
		double x = bd_lng - 0.0065;
		double y = bd_lat - 0.006;
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * xpi);
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * xpi);
	    double gg_lng = z * Math.cos(theta);
	    double gg_lat = z * Math.sin(theta);
	    return gg_lat;
	}


	//更新谷歌经纬度
	public void updateGcj()
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> planColumns = new ArrayList<String>();
		planColumns.add("id");
		planColumns.add("longitude");
		planColumns.add("latitude");
		planColumns.add("gcj_lng");
		planColumns.add("gcj_lat");
		paramMap.put("needColumns", planColumns);
		List<ScenicInfo> sList = mapper.listColumns(paramMap);
		List<CtripHotel> hList = cHotelMapper.listColumns(paramMap);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<String> resColumns = new ArrayList<String>();
		resColumns.add("id");
		resColumns.add("res_longitude");
		resColumns.add("res_latitude");
		resMap.put("needColumns", resColumns);
		List<Restaurant> rList = rMapper.listColumns(resMap);
		double bd_lng;
		double bd_lat;
		
		for(ScenicInfo ss : sList){
			bd_lng = ss.getLongitude();
			bd_lat = ss.getLatitude();
			ss.setGcjLng(updateLng(bd_lng, bd_lat));
			ss.setGcjLat(updateLat(bd_lng, bd_lat));
			mapper.update(ss);
		}
		
		
		for(CtripHotel cc : hList){
			bd_lng = cc.getLongitude();
			bd_lat = cc.getLatitude();
			System.out.println("test:   " + bd_lng);
			cc.setGcjLng(updateLng(bd_lng, bd_lat));
			cc.setGcjLat(updateLat(bd_lng, bd_lat));
			cHotelMapper.update(cc);
		}
		
		for(Restaurant rr : rList){
			if(rr.getResLatitude() == null || rr.getResLongitude() == null)
				continue;
			bd_lng = Double.parseDouble(rr.getResLongitude());
			bd_lat = Double.parseDouble(rr.getResLatitude());
			rr.setGcjLng(updateLng(bd_lng, bd_lat));
			rr.setGcjLat(updateLat(bd_lng, bd_lat));
			rMapper.update(rr);
			
		}
	}
	
	/**
	 * 根据父景点获取子景点列表
	 * @param scenicIdList
	 * @param id
	 */
	public void prepareScenic(List<Long> scenicIdList, Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("father", id);
		List<String> columnList = new ArrayList<String>();
		columnList.add("id");
		List<ScenicInfo> list = this.listColumns(paramMap, columnList);
		scenicIdList.add(id);
		if (!list.isEmpty()) {
			
			List idsList = ListUtil.getIdListEx(list, "id");
			scenicIdList.addAll(idsList);
			Map<String, Object> param1 = Maps.newHashMap();
			param1.put("fathers", idsList);
			List<ScenicInfo> sublist = this.listColumns(param1, columnList);
			for (ScenicInfo si : sublist)
			{
				scenicIdList.add(si.getId());
			}
		}
	}
	
}
