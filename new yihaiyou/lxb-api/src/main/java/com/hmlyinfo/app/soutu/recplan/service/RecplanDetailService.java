package com.hmlyinfo.app.soutu.recplan.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.common.service.DictService;
import com.hmlyinfo.app.soutu.delicacy.domain.Delicacy;
import com.hmlyinfo.app.soutu.delicacy.domain.Restaurant;
import com.hmlyinfo.app.soutu.delicacy.service.DelicacyService;
import com.hmlyinfo.app.soutu.delicacy.service.RestaurantService;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.service.CtripHotelService;
import com.hmlyinfo.app.soutu.plan.domain.Transportation;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.service.TransportationService;
import com.hmlyinfo.app.soutu.recplan.domain.Recplan;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanDay;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanPhoto;
import com.hmlyinfo.app.soutu.recplan.domain.RecplanTrip;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.base.exception.BizValidateException;

/**
 * 实现新推荐行程详情的功能
 */
@Service
public class RecplanDetailService {

	private final Logger			logger	= Logger.getLogger(RecplanDetailService.class);

	@Autowired
	private RecplanService			recplanService;
	@Autowired
	private RecplanDayService		recplanDayService;
	@Autowired
	private RecplanTripService		recplanTripService;
	@Autowired
	private RecplanPhotoService		recplanPhotoService;
	@Autowired
	private ScenicInfoService		scenicInfoService;
	@Autowired
	private CtripHotelService		ctripHotelService;
	@Autowired
	private RestaurantService		restaurantService;
	@Autowired
	private DelicacyService			delicacyService;
	@Autowired
	private TransportationService	transportationService;
	@Autowired
	private DictService				dictService;


	// 根据前端条件查询满足条件的三个推荐行程
	public List<Recplan> listDetails(Map<String, Object> paramMap) throws Exception {
		List<Recplan> recplans = recplanService.listRecplans(paramMap);

		return recplans;
	}

	// 推荐行程的详细信息
	public Recplan detail(long id) {
		// 查询行程天
		Recplan replan = recplanService.info(id);
		// 查询行程天，行程点，照片列表
		List<RecplanDay> recdayList = recplanDayService.list(ImmutableMap.of("recplanId", (Object) id));
		List<RecplanTrip> rectripList = recplanTripService.list(ImmutableMap.of("recplanId", (Object) id));
		List<RecplanPhoto> recphotoList = recplanPhotoService.list(ImmutableMap.of("recplanId", (Object) id));

		// 把每个节点（景点，酒店或餐厅）的详细信息插入到节点中
		detailMap(rectripList);

		// trip按照行程天分类
		Multimap<Long, RecplanTrip> tripMultimap = ArrayListMultimap.create();
		for (RecplanTrip recplanTrip : rectripList) {
			tripMultimap.put(recplanTrip.getRecdayId(), recplanTrip);
		}
		// photo按照trip分类
		Multimap<Long, RecplanPhoto> photoMultimap = ArrayListMultimap.create();
		for (RecplanPhoto recplanPhoto : recphotoList) {
			photoMultimap.put(recplanPhoto.getRectripId(), recplanPhoto);
		}

		// 组合数据
		int daySize = recdayList.size();
		if (daySize < 1) {
			logger.info("数据异常，没有行程天");
			throw new BizValidateException(ErrorCode.ERROR_53001, "数据异常");
		}
		for (int i = 0; i < daySize; i++) {

			RecplanDay day = recdayList.get(i);
			// 经过的所有城市的名称
			List<String> cityNames = new ArrayList<String>();
			String cityCodes = day.getCitys();
			String[] cities = cityCodes.split(",");
			for (String cityCode : cities) {
				String cityName = dictService.getCityById(cityCode);
				if (cityName != null && !"".equals(cityCode)) {
					cityNames.add(cityName);
				}
			}
			day.setCityNames(cityNames);

			List<RecplanTrip> tripList = (List<RecplanTrip>) tripMultimap.get(day.getId());

			for (RecplanTrip trip : tripList) {
				List<RecplanPhoto> photoCollection = (List<RecplanPhoto>) photoMultimap.get(trip.getId());
				trip.setRecplanPhotos(photoCollection);
			}
			day.setRecplanTrips(tripList);
		}
		replan.setRecplanDays(recdayList);

		return replan;
	}

	// 查询每一个行程点的（景点，酒店或餐厅）详细信息
	public void detailMap(List<RecplanTrip> rectrips) {

		Multimap<Integer, Long> idsMap = ArrayListMultimap.create();

		// 查询景点，酒店,餐厅和美食的id列表
		// 暂时定义美食type为11
		int delicacyType = 11;
		Map<String, Object> paramMap = Maps.newHashMap();
		for (int i = 0; i < rectrips.size(); i++) {
			RecplanTrip trip = rectrips.get(i);
			idsMap.put(trip.getTripType(), trip.getScenicId());
			// trip为餐厅时需要美食的id
			if (trip.getTripType() == TripType.RESTAURANT.value()) {
				idsMap.put(delicacyType, trip.getDelicacyId());
			}
		}

		// 暂时存储查询到的景点，餐厅和酒店信息
		Map<String, Object> infoMap = new HashMap<String, Object>();

		// 景点
		Collection<Long> sidsL = idsMap.get(TripType.SCENIC.value());
		if (sidsL.size() > 0) {
			paramMap.put("ids", sidsL);
			List<ScenicInfo> scenicList = scenicInfoService.listColumns(paramMap, Lists.newArrayList("*"));
			for (ScenicInfo scenicInfo : scenicList) {
				String key = TripType.SCENIC.value() + "_" + scenicInfo.getId();
				infoMap.put(key, scenicInfo);
			}
		}
		// 餐厅
		if (idsMap.get(TripType.RESTAURANT.value()).size() > 0) {
			paramMap.put("ids", idsMap.get(TripType.RESTAURANT.value()));
			List<Restaurant> resList = restaurantService.listColumns(paramMap, Lists.newArrayList("*"));
			for (Restaurant restaurant : resList) {
				String key = TripType.RESTAURANT.value() + "_" + restaurant.getId();
				infoMap.put(key, restaurant);
			}
		}
		// 酒店
		if (idsMap.get(TripType.HOTEL.value()).size() > 0) {
			paramMap.put("ids", idsMap.get(TripType.HOTEL.value()));
			List<CtripHotel> hotelList = ctripHotelService.listColumns(paramMap, Lists.newArrayList("*"));
			for (CtripHotel ctripHotel : hotelList) {
				String key = TripType.HOTEL.value() + "_" + ctripHotel.getId();
				infoMap.put(key, ctripHotel);
			}
		}
		// 美食
		if (idsMap.get(delicacyType).size() > 0) {
			paramMap.put("ids", idsMap.get(delicacyType));
			List<Delicacy> delicacyList = delicacyService.list(paramMap);
			for (int i = 0; i < delicacyList.size(); i++) {
				Map<String, Object> delicacy = (Map<String, Object>) delicacyList.get(i);
				String key = delicacyType + "_" + delicacy.get("id").toString();
				infoMap.put(key, delicacy);
			}
		}
		// 交通枢纽
		if (idsMap.get(TripType.STATION.value()).size() > 0) {
			paramMap.put("ids", idsMap.get(TripType.STATION.value()));
			List<Transportation> transportations = transportationService.list(paramMap);
			for (Transportation transportation : transportations) {
				String cityName = dictService.getCityById(transportation.getCityCode());
				transportation.setCityName(cityName);
				String key = TripType.STATION.value() + "_" + transportation.getId();
				infoMap.put(key, transportation);
			}
		}

		// 把每个节点的详细信息插入到trip列表中
		for (RecplanTrip trip : rectrips) {
			String key = trip.getTripType() + "_" + trip.getScenicId();
			if (infoMap.get(key) != null) {
				trip.setDetail(infoMap.get(key));
			} else {
				continue;
			}

			if (trip.getTripType() == TripType.RESTAURANT.value()) {
				String delicacyKey = delicacyType + "_" + trip.getDelicacyId();
				if (infoMap.get(delicacyKey) != null) {
					trip.setFoodDetail(infoMap.get(delicacyKey));
				}
			}
		}
	}

	

	// 返回recplanTrips中的交通枢纽
	public RecplanTrip trsInRectrip(Collection<RecplanTrip> recplanTrips) {
		RecplanTrip res = null;
		for (RecplanTrip recplanTrip : recplanTrips) {
			if (recplanTrip.getTripType() == TripType.STATION.value()) {
				res = recplanTrip;
				break;
			}
		}

		return res;
	}

	// 返回一天之中的第二个交通枢纽
	public RecplanTrip lastTrsInRectrip(Collection<RecplanTrip> recplanTrips) {
		List<RecplanTrip> trips = (List<RecplanTrip>) recplanTrips;
		RecplanTrip res = null;
		for (int i = trips.size() - 1; i >= 0; i--) {
			RecplanTrip trip = trips.get(i);
			if (trip.getTripType() == TripType.STATION.value()) {
				res = trip;
				break;
			}
		}
		return res;
	}

	// 返回recplanTrips中的酒店
	public RecplanTrip hotelInRectrip(Collection<RecplanTrip> recplanTrips) {
		RecplanTrip res = null;
		for (RecplanTrip recplanTrip : recplanTrips) {
			if (recplanTrip.getTripType() == TripType.HOTEL.value()) {
				res = recplanTrip;
				break;
			}
		}

		return res;
	}

	/**
	 * 将线性的行程结构转换成树形的行程结构
	 * @Title: childListDetail
	 * @email pengwei@hmlyinfo.com
	 * @date 2015年11月10日 下午6:11:02
	 * @version 
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 *
	 * @return Recplan
	 * @throws
	 */
	public Recplan childListDetail(long id) throws Exception {
		Recplan recplan = detail(id);

		HashSet<Long> parentSet = new HashSet<Long>();

		for (RecplanDay recplanDay : recplan.getRecplanDays()){
			// 旧的行程点列表
			List<RecplanTrip> recplanTripList = recplanDay.getRecplanTrips();
			// 新的行程列表，只存储父景点以及酒店、交通和餐厅
			List<RecplanTrip> newRecplanTripList = new ArrayList<RecplanTrip>();
			// 新列表中最后一个节点是景点
			long lastScenic = -1;
			for (RecplanTrip recplanTrip : recplanTripList) 
			{
				/**
				 * 酒店和交通枢纽，不会是景点的子节点，直接加入到新的行程列表中
				 */
				if (recplanTrip.getTripType() == TripType.HOTEL.value() || recplanTrip.getTripType() == TripType.STATION.value()){
					newRecplanTripList.add(recplanTrip);
					lastScenic = -1;
					continue;
				}
				/**
				 *  餐厅，如果最后一个节点是景点，则需要将该节点增加到该景点的子节点中，否则添加到新列表中
				 */
				if (recplanTrip.getTripType() == TripType.RESTAURANT.value()) 
				{
					if (lastScenic == -1) {
						newRecplanTripList.add(recplanTrip);
						continue;
					} else 
					// 添加到最后一个景点的子节点中
					{
						newRecplanTripList.get(newRecplanTripList.size() - 1).addChildrenTrip(recplanTrip);
						
						continue;
					}
				}
				
				/**
				 * 这里是景点的情况
				 */
				// 当前景点
				long scenicId = recplanTrip.getScenicId();
				ScenicInfo scenicInfo = (ScenicInfo) recplanTrip.getDetail();
				String parentsThreeLevelRegion = scenicInfo.getParentsThreeLevelRegion();
				// 当前景点是父景点，添加到新的列表中，数据中此节点为空，也认为该景点是父景点
				if (parentsThreeLevelRegion == null || "F".equals(parentsThreeLevelRegion)){
					lastScenic = scenicId;
					newRecplanTripList.add(recplanTrip);
					continue;
				} 
				// 该景点是子景点，需要确认是否是上一个节点的子景点，如果是，则直接添加到上一个节点的子节点列表中，否则，需要创建一个新的父节点并将子节点添加到到这个父节点中
				else 
				{
					long parentId = scenicInfo.getFather();
					// 正好是前一个父景点是当前景点的父亲，则添加到最后一个节点中
					if (parentId == lastScenic) 
					{
						newRecplanTripList.get(newRecplanTripList.size() - 1).addChildrenTrip(recplanTrip);
					} 
					else 
					{
                        lastScenic = parentId;
						// 添加到父景点Set中
						parentSet.add(parentId);
						// New parent-trip
						RecplanTrip parentTrip = new RecplanTrip();
						parentTrip.setScenicId(parentId);
						parentTrip.setTripType(TripType.SCENIC.value());
						parentTrip.setRecplanId(recplanTrip.getRecplanId());
						parentTrip.setRecdayId(recplanTrip.getRecdayId());

						List<RecplanTrip> children = new ArrayList<RecplanTrip>();
						children.add(recplanTrip);
						parentTrip.setChildrenList(children);

						newRecplanTripList.add(parentTrip);
					}
				}



			}

			// 把子景点中的最后一个餐厅移出
			for (int i = 0; i < newRecplanTripList.size(); i++){
				RecplanTrip recplanTrip = newRecplanTripList.get(i);
				List<RecplanTrip> children = recplanTrip.getChildrenList();
				if (children == null){
					continue;
				}
                int loc = i + 1;
                int childrenSize = children.size();
				if (childrenSize > 0){
                    for (int j = childrenSize - 1; j >= 0; j--){
                        RecplanTrip lastRecTrip = children.get(j);
                        if (lastRecTrip.getTripType() == TripType.RESTAURANT.value()){
                            newRecplanTripList.add(loc++, lastRecTrip);
                            children.remove(j);
                        }else {
                            break;
                        }
                    }

				}
			}
			// Day
			recplanDay.setRecplanTrips(newRecplanTripList);
		}

		// 暂时存储查询到的景点信息
		Map<Long, Object> infoMap = new HashMap<Long, Object>();

		if (parentSet.size() > 0) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ids", parentSet);
			List<ScenicInfo> scenicList = scenicInfoService.listColumns(paramMap, Lists.newArrayList("*"));
			for (ScenicInfo scenicInfo : scenicList) {
				infoMap.put(scenicInfo.getId(), scenicInfo);
			}
		}

		for (RecplanDay recplanDay : recplan.getRecplanDays()) {
			for (RecplanTrip recplanTrip : recplanDay.getRecplanTrips()) {
				if (recplanTrip.getTripType() == TripType.SCENIC.value() && recplanTrip.getDetail() == null) {
					if (infoMap.get(recplanTrip.getScenicId()) != null){
						recplanTrip.setDetail(infoMap.get(recplanTrip.getScenicId()));
					}
				}
			}
		}

		return recplan;
	}

}
