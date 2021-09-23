package com.hmlyinfo.app.soutu.scenicTicket.qunar.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.hmlyinfo.base.util.HttpUtil;

//
// partCode：613084394
// key：piao_factory_key
// url：b2bdev.piao.qunar.com
//
//
// 2830068584
// slx_key
//
//
@Service
public class QunarService {

	private static final String	PARTNER_CODE			= "3999347859";
	private static final String	PARTNER_KEY				= "a61ac440fe54d5e99025d5541aa12038";

	private static final String	VERSION					= "1.0.0";
	private static final String	PRODUCT_INFO_LIST_URL	= "http://b2b.piao.qunar.com/openapi/productInfoList";
	private static final String	SINGLE_PRODUCT_URL		= "http://b2b.piao.qunar.com/openapi/singleProduct";
	private static final String	PRICE_INFO_LIST_URL		= "http://b2b.piao.qunar.com/openapi/priceInfoList";
	private static final String	SINGLE_PRICE_URL		= "http://b2b.piao.qunar.com/openapi/singlePrice";
	private static final String	SIGHT_INFOS_URL			= "http://b2b.piao.qunar.com/openapi/sightInfos";
	private static final String	INFOS_BY_SIGHTIDS_URL	= "http://b2b.piao.qunar.com/openapi/getProductInfoBySightIds";

	Logger						logger					= Logger.getLogger(QunarService.class);

	private ObjectMapper		objectMapper			= new ObjectMapper();

	// 产品基本信息列表
	public Map<String, Object> productInfoList(String pageNo) {
		Map<String, String> params = Maps.newHashMap();
		params.put("version", "1.0.0");
		params.put("partnerCode", PARTNER_CODE);
		params.put("pageNo", pageNo);
		String sign = SignUtil.sign(params, PARTNER_KEY);
		params.put("sign", sign);
		Map<String, Object> resMap = postStrFromRest(PRODUCT_INFO_LIST_URL, params);

		return resMap;

	}

	// 单个产品信息
	public Map<String, Object> singleProduct(String productId) {
		Map<String, String> params = Maps.newHashMap();
		params.put("version", "1.0.0");
		params.put("partnerCode", PARTNER_CODE);
		params.put("productId", productId);
		String sign = SignUtil.sign(params, PARTNER_KEY);
		params.put("sign", sign);
		Map<String, Object> resMap = postStrFromRest(SINGLE_PRODUCT_URL, params);

		return resMap;
	}

	// 产品报价列表
	public Map<String, Object> priceInfoList(String pageNo) {
		Map<String, String> params = Maps.newHashMap();
		params.put("version", "1.0.0");
		params.put("partnerCode", PARTNER_CODE);
		params.put("pageNo", pageNo);
		String sign = SignUtil.sign(params, PARTNER_KEY);
		params.put("sign", sign);
		Map<String, Object> resMap = postStrFromRest(PRICE_INFO_LIST_URL, params);

		return resMap;
	}

	// 单个产品报价
	public Map<String, Object> singlePrice(String productId) {
		Map<String, String> params = Maps.newHashMap();
		params.put("version", "1.0.0");
		params.put("partnerCode", PARTNER_CODE);
		params.put("productId", productId);
		String sign = SignUtil.sign(params, PARTNER_KEY);
		params.put("sign", sign);
		Map<String, Object> resMap = postStrFromRest(SINGLE_PRICE_URL, params);

		return resMap;
	}

	// 批量获取景点信息
	public Map<String, Object> sightInfos(String pageNo) {
		Map<String, String> params = Maps.newHashMap();
		params.put("version", "1.0.0");
		params.put("partnerCode", PARTNER_CODE);
		params.put("pageNo", pageNo);
		String sign = SignUtil.sign(params, PARTNER_KEY);
		params.put("sign", sign);
		Map<String, Object> resMap = postStrFromRest(SIGHT_INFOS_URL, params);

		return resMap;
	}

	// 根据景点Id获取产品信息
	public Map<String, Object> getProductInfoBySightIds(String sightIds) {
		Map<String, String> params = Maps.newHashMap();
		params.put("version", "1.0.0");
		params.put("partnerCode", PARTNER_CODE);
		params.put("sightIds", sightIds);
		String sign = SignUtil.sign(params, PARTNER_KEY);
		params.put("sign", sign);
		Map<String, Object> resMap = postStrFromRest(INFOS_BY_SIGHTIDS_URL, params);

		return resMap;
	}

	// 调用Qunar提供的API
	public Map<String, Object> postStrFromRest(String url, Map<String, String> paramMap) {

		String strRes = HttpUtil.postStrFromUrl(url, paramMap);

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> paramSign = new HashMap<String, String>();
		try {
			params = objectMapper.readValue(strRes, Map.class);
			paramSign = objectMapper.readValue(strRes, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		if ((Boolean) (params.get("status")) == false) {
			return params;
		}

		String data = "";
		if (SignUtil.checkSign(paramSign, PARTNER_KEY, paramSign.get("sign"))) {
			// 解密后得到业务数据
			// BASE64Utils.decode详见附录5.1.2
			data = BASE64Utils.decode(paramSign.get("data"));
		}

		// 处理直接返回以[]包起来的MapList
		if (data.indexOf('[') == 0) {
			data = "{\"datalist\":" + data + "}";
		}

		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			resMap = objectMapper.readValue(data, Map.class);
		} catch (Exception e) {
			logger.info("获取库存失败，没有查询到该门票数据");
		}
		return resMap;
	}

}
