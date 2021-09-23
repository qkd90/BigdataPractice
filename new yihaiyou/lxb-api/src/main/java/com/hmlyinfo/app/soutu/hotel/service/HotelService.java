package com.hmlyinfo.app.soutu.hotel.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.ZMYouProperties;
import com.hmlyinfo.app.soutu.common.service.ISearchService;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.domain.CtripPrice;
import com.hmlyinfo.app.soutu.hotel.domain.Hotel;
import com.hmlyinfo.app.soutu.hotel.domain.HotelSearchParam;
import com.hmlyinfo.app.soutu.hotel.mapper.HotelMapper;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.ResultList;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.persistent.PageDto;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.ListUtil;

/**
 * Created by guoshijie on 2014/7/17.
 */
@Service
public class HotelService extends BaseService<Hotel, Long> {

	@Autowired
	private HotelCommentService	hotelCommentservice;
	@Autowired
	private HotelMapper<Hotel>	hotelMapper;
	@Autowired
	private CtripHotelService	ctripHotelService;
	@Autowired
	private CtripPriceService	ctripPriceService;
	@Autowired
	private ScenicInfoService	scenicInfoService;
	@Autowired
	private UserService			userService;
	@Autowired
	@Qualifier("openSearchService")
	private ISearchService		searchService;

	private String				hotelUrl	= ZMYouProperties.get("hotelApi");

	@SuppressWarnings("unchecked")
	public ResultList<Map<String, Object>> listByZMYou(Map<String, Object> params) {
		// 酒店列表api的固定action
		params.put("action", "GET_HOTEL_WITH_ROOM_LIST");
		// 分解分页信息，转换为远程Api的分页信息
		PageDto pageDto = (PageDto) params.get("pageDto");
		if (pageDto.getPage() > 0) {
			params.put("currentPageNum", pageDto.getPage());
		}
		params.put("pageSize", pageDto.getPageSize());
		String bodyStr;
		try {
			bodyStr = new ObjectMapper().writeValueAsString(params);
		} catch (IOException e) {
			throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
		}
		// 组装参数，请求远程api，获取hotel信息
		Map<String, String> paramMap = prepareParam(bodyStr);
		String JsonStr = HttpUtil.postStrFromUrl(hotelUrl, paramMap);
		// 格式化返回的json数据
		Map<String, Object> bodyMap = toZMYouResult(JsonStr);
		ResultList<Map<String, Object>> resultList = new ResultList<Map<String, Object>>();
		resultList.setCounts((Integer) ((Map) bodyMap.get("pageInfo")).get("totalRowNum"));
		resultList.setData((List) bodyMap.get("pageData"));
		// addHotel(resultList.getData());

		return resultList;
	}

	/**
	 * 酒店数据与数据库酒店点评接口
	 * <ul>
	 * <li>必填:酒店数据{hotelList}</li>
	 * </ul>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> packWithLocalHotel(ResultList<Map<String, Object>> resultList) {
		List<Long> ids = new ArrayList<Long>();
		List<Map<String, Object>> lastList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> hotel : resultList.getData()) {
			ids.add(Long.valueOf(hotel.get("id").toString()));
			List<Map<String, Object>> roomList = (List<Map<String, Object>>) hotel.get("hotelRoomList");
			if (roomList.isEmpty()) {
				continue;
			}
			float lowestPrice = 100000;
			for (Map<String, Object> roomMap : roomList) {
				float price = Float.valueOf(roomMap.get("avgSuggestPrice").toString());
				if (price >= 1 && price < lowestPrice) {
					lowestPrice = price;
				}
			}
			hotel.put("lowestHotelPrice", (int) lowestPrice);
		}
		if (ids.size() != 0) {
			List<Map<String, Object>> hotelList = (List) hotelMapper.list(Collections.<String, Object> singletonMap("ids", ids));
			Map<String, String> joinMap = new HashMap<String, String>();
			joinMap.put("id", "id");
			lastList = ListUtil.listJoin(resultList.getData(), hotelList, joinMap, "localHotel", null);
		}
		return lastList;
	}

	@SuppressWarnings("unchecked")
	Map<String, Object> toZMYouResult(String jsonStr) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Object> hotelMap = mapper.readValue(jsonStr, Map.class);
			if (!"OK".equalsIgnoreCase(hotelMap.get("status").toString())) {
				throw new BizValidateException(ErrorCode.ERROR_51001, hotelMap.get("body").toString());
			}
			String body = hotelMap.get("body").toString();
			return mapper.readValue(body, Map.class);
		} catch (IOException e) {
			throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> detail(long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 酒店详细api的固定action
		params.put("action", "GET_HOTEL_WITH_ROOM");
		params.put("id", id);
		String bodyStr;
		try {
			bodyStr = new ObjectMapper().writeValueAsString(params);
		} catch (IOException e) {
			throw new BizValidateException(ErrorCode.ERROR_51001, e.getMessage());
		}
		// 组装参数，请求远程api，获取hotel信息
		Map<String, String> paramMap = prepareParam(bodyStr);
		String jsonStr = HttpUtil.postStrFromUrl(hotelUrl, paramMap);
		Map<String, Object> hotelMap = toZMYouResult(jsonStr);
		hotelMap.put("localHotel", hotelMapper.selById(String.valueOf(id)));
		List<Map<String, Object>> roomList = (List<Map<String, Object>>) hotelMap.get("hotelRoomList");
		if (roomList.isEmpty()) {
			return hotelMap;
		}
		float lowestPrice = 100000;
		for (Map<String, Object> roomMap : roomList) {
			float price = Float.valueOf(roomMap.get("avgSuggestPrice").toString());
			if (price >= 1 && price < lowestPrice) {
				lowestPrice = price;
			}
		}
		hotelMap.put("lowestHotelPrice", (int) lowestPrice);
		return hotelMap;
	}

	Map<String, String> prepareParam(String bodyStr) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String user = ZMYouProperties.get("userName");
		String pwd = ZMYouProperties.get("password");
		String key = ZMYouProperties.get("md5key");
		paramMap.put("url", hotelUrl);
		paramMap.put("u", user);
		paramMap.put("p", pwd);
		paramMap.put("body", bodyStr);
		String md5 = DigestUtils.md5Hex(user + pwd + bodyStr + key);
		paramMap.put("sign", md5);
		return paramMap;
	}

	/**
	 * 酒店数据与数据库酒店点评接口
	 * <ul>
	 * <li>必填:酒店数据{hotelList}</li>
	 * </ul>
	 */
	public void addHotel(List<Map<String, Object>> hotelList) {
		for (Map<String, Object> hotel : hotelList) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("hotelId", hotel.get("id"));
			Hotel eachhotel = hotelMapper.selById(hotel.get("id").toString());
			int hotelComNum = hotelCommentservice.count(paramMap);
			if (eachhotel != null) {
				if (hotelComNum != eachhotel.getCommentNum()) {
					eachhotel.setCommentNum(hotelComNum);
					hotelMapper.update(eachhotel);
				}
			} else {
				eachhotel = new Hotel();
				eachhotel.setId(Long.valueOf(hotel.get("id").toString()));
				eachhotel.setScore(0);
				if (hotel.get("longitude") != null) {
					eachhotel.setLongitude(Double.valueOf(hotel.get("longitude").toString()));
					eachhotel.setLatitude(Double.valueOf(hotel.get("latitude").toString()));
				}
				eachhotel.setCommentNum(hotelComNum);
				hotelMapper.insert(eachhotel);
			}
		}
	}

	/**
	 * 携程酒店信息接口
	 * 
	 * @return CtripHotel list
	 */
	public List<CtripHotel> listCtrip(Map<String, Object> paramMap) {

		/**
		 * openSearch以及条件筛选，排序，分页，本地数据库只需要查询出详细信息即可
		 */
		List<String> hotelIdList = getHotelIdByOpenQuery(paramMap);

		// paramMap.clear();
		// paramMap.put("ids", hotelIdList);

		// 从OpenSearch查询出的ids本身是排好序的，因此从数据库取数据时按照ids里面的顺序取出来，并取出距离以及用户数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", hotelIdList);
		if (paramMap.get("longitude") != null) {
			params.put("longitude", paramMap.get("longitude"));
			params.put("latitude", paramMap.get("latitude"));
		}
		if (paramMap.get("gcjLng") != null) {
			params.put("gcjLng", paramMap.get("gcjLng"));
			params.put("gcjLat", paramMap.get("gcjLat"));
		}

		List<CtripHotel> list = ctripHotelService.listAndOrder(params);
		return list;

		// 以前做法，测试新方法无误后删除
		// List<CtripHotel> list = ctripHotelService.list(paramMap);
		// List<Long> idList = ListUtil.getIdList(list, "userId");
		//
		// List<User> userList = userService.list(Collections.<String, Object>
		// singletonMap("ids", idList));
		//
		// Map<Long, User> tempUserMap = new HashMap<Long, User>();
		// for (User user : userList) {
		// tempUserMap.put(user.getId(), user);
		// }
		// for (CtripHotel hotel : list) {
		// User user = tempUserMap.get(hotel.getUserId());
		// hotel.setNickName(user.getNickname());
		// hotel.setUserface(user.getUserface());
		// }
		// return list;
	}

	public ActionResult countCtrip(Map<String, Object> paramMap) {

		int counts = searchService.countHotelByName(HotelSearchParam.fromMap(paramMap));
		return ActionResult.createCount(counts);
	}

	public CtripHotel infoCtrip(Map<String, Object> params) {
		CtripHotel ctripHotel = ctripHotelService.info(Long.valueOf(params.get("id").toString()));
		Map<String, Object> priceMap = new HashMap<String, Object>();
		priceMap.put("hotelId", params.get("id"));
		priceMap.put("pageSize", 500);
		List<CtripPrice> priceList = ctripPriceService.list(priceMap);
		Map<Integer, Map<String, Object>> roomMap = new HashMap<Integer, Map<String, Object>>();
		for (CtripPrice ctripPrice : priceList) {
			Map<String, Object> room = roomMap.get(ctripPrice.getRatePlanCode());
			if (room == null) {
				room = new HashMap<String, Object>();
				room.put("roomName", ctripPrice.getDescription());
				room.put("avgSuggestPrice", ctripPrice.getPrice());
				room.put("hasBreakfast", ctripPrice.isHasBreakfast());
				room.put("hasBroadband", ctripPrice.isHasBroadband());
				roomMap.put(ctripPrice.getRatePlanCode(), room);
			} else {
				if ((Double) room.get("avgSuggestPrice") > ctripPrice.getPrice()) {
					room.put("avgSuggestPrice", ctripPrice.getPrice());
				}
			}
		}
		List<Map<String, Object>> roomList = new ArrayList<Map<String, Object>>();
		roomList.addAll(roomMap.values());
		ctripHotel.setHotelRoomList(roomList);
		return ctripHotel;
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
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> listName(Map<String, Object> paramMap) {
		paramMap.put("isValid", "T");

		return searchService.searchHotelByName(HotelSearchParam.fromMap(paramMap));

	}

	/**
	 * 从阿里云的开放搜索中查询酒店信息
	 * 因为阿里云已经内置拼音和中文同时搜索，并且分词机制也处理得很好，所以无需做后续处理
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<String> getHotelIdByOpenQuery(Map<String, Object> paramMap) {
		List<Map<String, Object>> solrList = listName(paramMap);

		return ListUtil.getIdList(solrList, "id");
	}

	@Override
	public BaseMapper<Hotel> getMapper() {
		return hotelMapper;
	}

	@Override
	public String getKey() {
		return "id";
	}

}
