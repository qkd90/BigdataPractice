package com.hmlyinfo.app.soutu.common.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.common.service.ISearchService;
import com.hmlyinfo.app.soutu.hotel.domain.HotelSearchParam;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicSearchParam;
import com.hmlyinfo.base.util.HttpClientUtils;
import com.hmlyinfo.base.util.Validate;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SolrSearchService implements ISearchService {
	private String solrAddr = Config.get("solrAddress");


	public List<Map<String, Object>> searchScenicByName(String scenicName,
	                                                    String cityCode) {
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("cityCode", cityCode);
		paramMap.put("name", scenicName);

		return searchScenic(paramMap);
	}

	private List<Map<String, Object>> searchScenic(Map<String, Object> paramMap) {

		List<Map<String, Object>> resultList = Lists.newArrayList();

		String cityCode = paramMap.get("cityCode").toString();
		String startCode = cityCode.substring(0, 4);
		String endCode = cityCode.substring(4, 6);
		String queryCityCode = "";

		// 处理cityCode规则
		if ("00".equals(endCode)) {
			queryCityCode = "citycode:" + startCode + "*";
		} else {
			queryCityCode = "citycode:" + cityCode;
		}

		// 处理输入数据
		String name = paramMap.get("name") + "";
		String queryName = "";
		if (name.matches("^[0-9a-zA-Z]*")) { // 如果是拼音，用拼音模糊查询
			queryName = "name:" + name + "*";
			paramMap.put("sort", "name asc");
		} else { // 如果是非拼音，认为是中文查询，使用solr的分词查询
			queryName = "subject:" + name;
		}
		paramMap.put("q", queryName + " AND " + queryCityCode);
		paramMap.put("wt", "json");
		paramMap.put("indent", "true");
		paramMap.remove("name");
		paramMap.remove("cityCode");

		HttpClient httpClient = HttpClientUtils.getHttpClient();
		ObjectMapper objectMapper = new ObjectMapper();
		HttpPost httpPost = new HttpPost(solrAddr);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			if (null == entry.getValue() || StringUtils.isBlank(entry.getValue().toString())) {
				continue;
			}
			NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
			nameValuePairs.add(nvp);
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			// 取得页面json数据，转为字符串
			String strRes = EntityUtils.toString(response.getEntity());

			// json数据转成Map
			Map<String, Object> resultMap = objectMapper.readValue(strRes, Map.class);

			Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("response");

			if (responseMap != null && responseMap.get("docs") != null) {
				List docs = (List) responseMap.get("docs");
				for (Object doc : docs) {
					Map<String, Object> docMap = (Map<String, Object>) doc;
					Map<String, Object> itemMap = new HashMap<String, Object>();
					itemMap.put("scenicId", docMap.get("scenic"));
					itemMap.put("scenicName", docMap.get("subject"));
					resultList.add(itemMap);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Validate.notNull(null, ErrorCode.ERROR_51001);
		}

		return resultList;
	}

	public List<Map<String, Object>> searchHotelByName(String hotelName,
	                                                   String cityCode, int start, int end) {

		Map<String, Object> paramMap = Maps.newHashMap();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		Object nameObj = hotelName;
		Object codeObj = cityCode;
		String searchName = "";
		String qStr = "";
		if (null != nameObj) {
			searchName = nameObj.toString();
			String queryName = "";
			if (searchName.matches("^[0-9a-zA-Z]*")) { //如果是拼音，用拼音模糊查询
				queryName = "pinyin:*" + searchName + "*^1";
			} else { //如果是非拼音，认为是中文查询，使用solr的分词查询
				queryName = "title:" + searchName + "^1";
			}
			qStr = queryName;
		}

		if (null != codeObj) {
			cityCode = codeObj.toString();
			String startCode = cityCode.substring(0, 4);
			String endCode = cityCode.substring(4, 6);
			String queryCityCode = "";
			if ("00".equals(endCode)) {
				queryCityCode = "cityCode:" + startCode + "*";
			} else {
				queryCityCode = "cityCode:" + cityCode;
			}

			if ("".equals(qStr)) qStr = queryCityCode;
			else qStr += " AND " + queryCityCode;
		}

		paramMap.put("q", "".equals(qStr) ? "*:*" : qStr);
//		paramMap.put("q", "name:" + paramMap.get("name") + " AND city:" + paramMap.get("cityCode"));
		paramMap.put("wt", "json");
		paramMap.put("indent", "false");
		paramMap.remove("name");
		paramMap.remove("cityCode");

		HttpClient httpClient = HttpClientUtils.getHttpClient();
		ObjectMapper objectMapper = new ObjectMapper();
		HttpPost httpPost = new HttpPost(Config.get("solr.hotel.address"));

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			if (null == entry.getValue() || StringUtils.isBlank(entry.getValue().toString())) {
				continue;
			}
			NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
			nameValuePairs.add(nvp);
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			// 取得页面json数据，转为字符串
			String strRes = EntityUtils.toString(response.getEntity());

			// json数据转成Map
			Map<String, Object> resultMap = objectMapper.readValue(strRes, Map.class);

			Map<String, Object> responseMap = (Map<String, Object>) resultMap.get("response");

			if (responseMap != null && responseMap.get("docs") != null) {
				List docs = (List) responseMap.get("docs");
				for (Object doc : docs) {
					Map<String, Object> docMap = (Map<String, Object>) doc;
					Map<String, Object> itemMap = new HashMap<String, Object>();
					itemMap.put("hotelId", docMap.get("hotelId"));
					itemMap.put("hotelName", docMap.get("title"));
					resultList.add(itemMap);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Validate.notNull(null, ErrorCode.ERROR_51001);
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> searchHotelByName(
		HotelSearchParam searchParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countHotelByName(HotelSearchParam searchParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> searchScenicByName(
		ScenicSearchParam scenicSearchParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countScenicByName(ScenicSearchParam scenicSearchParam) {
		// TODO Auto-generated method stub
		return 0;
	}


}
