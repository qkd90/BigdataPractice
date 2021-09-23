package com.hmlyinfo.app.soutu.hotel.ctriporder.service;

import com.hmlyinfo.base.util.MD5;
import com.hmlyinfo.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class HotelAvailService {


	private static final String ALLIANCE_ID = "22422";
	private static final String SID = "456399";
	private static final String API_KEY = "47C87868-C59F-4072-A121-AAE03B428240";
	// 酒店可定性检查
	private static final String HOTEL_AVAIL_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelAvail.asmx";


	@Autowired
	private CtripCommonService ctripCommonService;


	// 酒店可定性检查
	public Map<String, Object> hotelAvail(Map<String, Object> paramMap) {

		// HotelCode属性：酒店代码；string类型；必填
		String hotelCode = paramMap.get("hotelCode").toString();
		// Start属性：入住日期；DateTime类型；必填
		String start = paramMap.get("start").toString();
		// End属性：离店日期；DateTime类型；必填
		String end = paramMap.get("end").toString();
		// RatePlanCode属性：价格计划代码；string类型；必填
		String ratePlanCode = paramMap.get("ratePlanCode").toString();
		// Quantity属性：将要预订的房间数量；int类型；必填
		String quantity = paramMap.get("Quantity").toString();
		// Count属性：客人数量；int类型；必填 -->
		String count = paramMap.get("count").toString();
		// TimeStamp:响应时间戳（从1970年到现在的秒数）
		String timeStamp = System.currentTimeMillis() + "";
		// Signature:MD5加密串
		String signature = MD5.GetMD5Code(System.currentTimeMillis() + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "OTA_HotelAvail").toUpperCase();

		String hotelAvailString = StringUtil.getStrFromFile(getClass().getResourceAsStream("../../ctripxml/HotelAvail.xml"));

		hotelAvailString = hotelAvailString
			.replaceAll("#AllianceID", ALLIANCE_ID).replaceAll("#SID", SID)
			.replaceAll("#TimeStamp", timeStamp).replaceAll("#Signature", signature)
			.replaceAll("#HotelCode", hotelCode).replaceAll("#Start", start)
			.replaceAll("#End", end).replaceAll("#RatePlanCode", ratePlanCode)
			.replaceAll("#Quantity", quantity).replaceAll("#Count", count);

		Map<String, Object> map = ctripCommonService.postForXML(HOTEL_AVAIL_URL, hotelAvailString);
		if (map == null || !"Success".equals(((Map) map.get("Header")).get("ResultCode").toString())) {
			return null;
		}

		return null;
	}

}
