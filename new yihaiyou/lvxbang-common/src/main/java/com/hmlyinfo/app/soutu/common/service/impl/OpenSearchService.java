package com.hmlyinfo.app.soutu.common.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.common.service.ISearchService;
import com.hmlyinfo.app.soutu.hotel.domain.HotelSearchParam;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicSearchParam;
import com.hmlyinfo.base.util.Validate;
import com.opensearch.javasdk.CloudsearchClient;
import com.opensearch.javasdk.CloudsearchSearch;
import com.opensearch.javasdk.object.KeyTypeEnum;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class OpenSearchService implements ISearchService {

	public final Logger logger = Logger.getLogger(OpenSearchService.class);

	private String accesskey = Config.get("ALIYUN_OPENSEARCH_KEY");
	private String secret = Config.get("ALIYUN_OPENSEARCH_SECRET");
	private String host = Config.get("ALIYUN_OPENSEARCH_HOST");
	private ObjectMapper om = new ObjectMapper();

	private CloudsearchClient cloudSearchClient;

	private CloudsearchClient getClient() {
		if (cloudSearchClient == null) {
			Map<String, Object> opts = Maps.newHashMap();
			opts.put("host", host);
			cloudSearchClient = new CloudsearchClient(accesskey, secret, opts, KeyTypeEnum.ALIYUN);
		}

		return cloudSearchClient;
	}

	@Override
	public List<Map<String, Object>> searchScenicByName(ScenicSearchParam scenicSearchParam) {
		List<Map<String, Object>> searchResult = null;
		CloudsearchClient client = getClient();
		CloudsearchSearch search = getScenicSearch(scenicSearchParam);
		try {
			String strResult = search.search();
			searchResult = getListFromResult(strResult);
		} catch (Exception e) {
			logger.info("查询失败，原因为：" + e.getLocalizedMessage());
			e.printStackTrace();
			Validate.notNull(null, ErrorCode.ERROR_50003);
		}

		return searchResult;
	}

	@Override
	public int countScenicByName(ScenicSearchParam scenicSearchParam) {
		int count = 0;
		CloudsearchSearch search = getScenicSearch(scenicSearchParam);
		search.setHits(0);

		try {
			String strResult = search.search();
			Map<String, Object> resultMap = om.readValue(strResult, Map.class);
			count = (Integer) ((Map<String, Object>) resultMap.get("result")).get("total");
		} catch (Exception e) {
			e.printStackTrace();
			Validate.notNull(null, ErrorCode.ERROR_50003);
		}

		return count;
	}

	@Override
	public List<Map<String, Object>> searchHotelByName(HotelSearchParam searchParam) {

		List<Map<String, Object>> searchResult = null;

		CloudsearchSearch search = getHotelSearch(searchParam);

		try {
			String strResult = search.search();
			searchResult = getListFromResult(strResult);
		} catch (Exception e) {
			e.printStackTrace();
			Validate.notNull(null, ErrorCode.ERROR_50003);
		}

		return searchResult;
	}

	@Override
	public int countHotelByName(HotelSearchParam searchParam) {

		int count = 0;
		CloudsearchSearch search = getHotelSearch(searchParam);
		search.setHits(0);

		try {
			String strResult = search.search();
			Map<String, Object> resultMap = om.readValue(strResult, Map.class);
			count = (Integer) ((Map<String, Object>) resultMap.get("result")).get("total");
		} catch (Exception e) {
			e.printStackTrace();
			Validate.notNull(null, ErrorCode.ERROR_50003);
		}

		return count;
	}

	private List<Map<String, Object>> getListFromResult(String strResult) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> resultMap = om.readValue(strResult, Map.class);
		if (!"OK".equals(resultMap.get("status"))) {
			logger.info("返回status！=OK，原因是：" + strResult);
			Validate.notNull(null, ErrorCode.ERROR_50003);
		}
		return (List<Map<String, Object>>) ((Map<String, Object>) resultMap.get("result")).get("items");
	}

	// 包装酒店查询参数
	private CloudsearchSearch getHotelSearch(HotelSearchParam searchParam) {
		CloudsearchClient client = getClient();
		CloudsearchSearch search = new CloudsearchSearch(client);
		search.addIndex("lvxbang_hotel");

		if (searchParam.getHotelName() != null) {
			search.setQueryString("'" + Strings.nullToEmpty(searchParam.getHotelName()) + "'");
		} else {
			search.setQueryString("''");
		}
		if (searchParam.getCityCode() != 0) {
			search.addFilter("city_code=" + searchParam.getCityCode());
		}
		if (searchParam.getLowestPrice() != 0) {
			search.addFilter("lowest_hotel_price>" + searchParam.getLowestPrice());
		}
		if (searchParam.getHighestPrice() != 0) {
			search.addFilter("lowest_hotel_price<" + searchParam.getHighestPrice());
		}
		if (searchParam.getDistance() != 0) {
			if (searchParam.getLatitude() != 0) {
				String distanceString = "distance(longitude,latitude,"
					+ "\"" + searchParam.getLongitude() + "\","
					+ "\"" + searchParam.getLatitude() + "\")"
					+ "<" + searchParam.getDistance();
				search.addFilter(distanceString);
			} else if (searchParam.getGoogle_latitude() != 0) {
				String distanceString = "distance(google_longitude,google_latitude," + "\""
					+ searchParam.getGoogle_longitude() + "\"," + "\""
					+ searchParam.getGoogle_latitude() + "\")"
					+ "<" + searchParam.getDistance();
				search.addFilter(distanceString);
			}
		}
		if (searchParam.getStartIndex() != 0) {
			search.setStartHit(searchParam.getStartIndex());
		}
		if (searchParam.getLength() != 0) {
			search.setHits(searchParam.getLength());
		}

		if (searchParam.getOrderColumn() != null) {
			String orderColumn = "";
			if ("distance".equals(searchParam.getOrderColumn())) {
				if (searchParam.getLatitude() != 0) {
					orderColumn = "distance(longitude,latitude,"
						+ "\"" + searchParam.getLongitude() + "\","
						+ "\"" + searchParam.getLatitude() + "\")";
				} else if (searchParam.getGoogle_latitude() != 0) {
					orderColumn = "distance(google_longitude,google_latitude,"
						+ "\"" + searchParam.getGoogle_longitude() + "\","
						+ "\"" + searchParam.getGoogle_latitude() + "\")";
				}
			} else {
				orderColumn = searchParam.getOrderColumn();
			}

			String orderType = "";
			if (searchParam.getOrderType() != null) {
				if (searchParam.getOrderType().equals("asc")) {
					orderType = "+";
				}
			} else {
				orderType = "-";
			}

			if (!orderColumn.equals("")) {
				search.addSort(orderColumn, orderType);
			}
		} else {
			search.addSort("hotel_rank", "-");
		}

		search.setFormat("json");
		return search;
	}


	// 包装景点查询参数
	private CloudsearchSearch getScenicSearch(ScenicSearchParam scenicSearchParam) {
		CloudsearchClient client = getClient();
		CloudsearchSearch search = new CloudsearchSearch(client);
		search.addIndex("lvxbang_scenic");

		if (scenicSearchParam.getName() != null) {
			search.setQueryString("'" + Strings.nullToEmpty(scenicSearchParam.getName()) + "'");
		} else {
			search.setQueryString("''");
		}
		if (scenicSearchParam.getCityCode() != 0) {
			search.addFilter("city_code=" + scenicSearchParam.getCityCode());
		}
		if (scenicSearchParam.getIsCity() == 0) {
			search.addFilter("is_city=0");
		}
		if (scenicSearchParam.getIsStation() == 0) {
			search.addFilter("is_station=0");
		}
		if (scenicSearchParam.getParentsThreeLevelRegion() != null) {
			search.addFilter("parents_three_level_region=" + "\"" + scenicSearchParam.getParentsThreeLevelRegion() + "\"");
		}

		if (scenicSearchParam.getDistance() != 0) {
			if (scenicSearchParam.getLatitude() != 0) {
				String distanceString = "distance(longitude,latitude,"
					+ "\"" + scenicSearchParam.getLongitude() + "\","
					+ "\"" + scenicSearchParam.getLatitude() + "\")"
					+ "<" + scenicSearchParam.getDistance();
				search.addFilter(distanceString);
			} else if (scenicSearchParam.getGcjLat() != 0) {
				String distanceString = "distance(gcj_lng,gcj_lat," + "\""
					+ scenicSearchParam.getGcjLng() + "\"," + "\""
					+ scenicSearchParam.getGcjLat() + "\")"
					+ "<" + scenicSearchParam.getDistance();
				search.addFilter(distanceString);
			}
		}
		if (scenicSearchParam.getStartIndex() != 0) {
			search.setStartHit(scenicSearchParam.getStartIndex());
		}
		if (scenicSearchParam.getLength() != 0) {
			search.setHits(scenicSearchParam.getLength());
		}

		if (scenicSearchParam.getIds() != null) {
			List<Long> idsList = scenicSearchParam.getIds();
			String idsString = "";
			if (idsList.size() > 0) {
				idsString = "(id=" + idsList.get(0);
			}
			for (int i = 1; i < idsList.size(); i++) {
				idsString = idsString + " OR id=" + idsList.get(i);
			}
			idsString = idsString + ")";
			search.addFilter(idsString);
		}

		if (scenicSearchParam.getCityCodes() != null) {
			List<Object> cityCodes = scenicSearchParam.getCityCodes();
			String cityCodeString = "";
			if (cityCodes.size() > 0) {
				cityCodeString = "(city_code=" + cityCodes.get(0);

				for (int i = 1; i < cityCodes.size(); i++) {
					cityCodeString = cityCodeString + " OR city_code=" + cityCodes.get(i);
				}
				cityCodeString = cityCodeString + ")";
				search.addFilter(cityCodeString);
			}

		}


		if (scenicSearchParam.getOrderColumn() != null) {
			String orderColumn = "";
			if (scenicSearchParam.getOrderColumn().equals("distance")) {
				if (scenicSearchParam.getLatitude() != 0) {
					orderColumn = "distance(longitude,latitude,"
						+ "\"" + scenicSearchParam.getLongitude() + "\","
						+ "\"" + scenicSearchParam.getLatitude() + "\")";
				} else if (scenicSearchParam.getGcjLat() != 0) {
					orderColumn = "distance(gcj_lng,gcj_lat,"
						+ "\"" + scenicSearchParam.getGcjLng() + "\","
						+ "\"" + scenicSearchParam.getGcjLat() + "\")";
				}
			} else {
				orderColumn = scenicSearchParam.getOrderColumn();
			}

			String orderType = "";
			if (scenicSearchParam.getOrderType() != null) {
				if (scenicSearchParam.getOrderType().equals("asc")) {
					orderType = "+";
				} else {
					orderType = "-";
				}
			} else {
				orderType = "-";
			}
			search.addSort(orderColumn, orderType);

		}
		search.setFormat("json");
		return search;
	}


}
