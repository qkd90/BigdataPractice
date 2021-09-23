package com.data.data.hmly.service.ctriphotel;

import com.data.data.hmly.service.ctriphotel.base.MD5;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vacuity on 15/11/30.
 */

@Service
public class CtripHotelAvailService {

    private static final String ALLIANCE_ID = "22422";
    private static final String SID = "456399";
    private static final String API_KEY = "47C87868-C59F-4072-A121-AAE03B428240";
    // 酒店可定性检查
    private static final String HOTEL_AVAIL_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelAvail.asmx";
    // 订单生成 OTA_HotelResn
    private static final String HOTEL_ORDER_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelRes.asmx";

//
//    @Resource
//    private CtripCommonService ctripCommonService;



    public static void main(String[] args) {
        int cityId = 25;
        int cityCode = 350200;
        List<Long> hotelIds = new ArrayList<Long>();
        //1768447, 1612083, 1771482,
        hotelIds.add(2131045L);
//        hotelIds.add(2131056L);
//        hotelIds.add(2131782L);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("hotelCode","2131045");
        param.put("start","2015-12-06T13:00:00.000+08:00");
        param.put("end","2015-12-10T13:00:00.000+08:00");
        param.put("ratePlanCode","12117042");
        param.put("Quantity","1");
        param.put("count","1");
        param.put("lateArrivalTime", "2015-12-06T15:00:00.000+08:00");

        CtripHotelAvailService service = new CtripHotelAvailService();

        Boolean check = service.checkResult(param);
        if (check){
            System.out.println("available");
        }


    }


    public Boolean checkResult(Map<String, Object> paramMap){

        String quantityString = paramMap.get("Quantity").toString();
        Integer quantity = Integer.parseInt(quantityString);

        Map<String, Object> map = new CtripHotelAvailService().hotelAvail(paramMap);

        try {
            Map<String, Object> roomStay = (Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) map.get("HotelResponse")).get("OTA_HotelAvailRS")).get("RoomStays")).get("RoomStay");
            String availabilityStatus = roomStay.get("AvailabilityStatus").toString();

            if (!"AvailableForSale".equals(availabilityStatus)){
                return false;
            }

            String availableQuantity = ((Map<String, Object>) ((Map<String, Object>) roomStay.get("RatePlans")).get("RatePlan")).get("AvailableQuantity").toString();
            Integer availNum = Integer.parseInt(availableQuantity);
            if (availNum >= 8 || availNum >= quantity){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return null;
        }
    }



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
        // 入住人最晚到店时间，有可能最晚到店日期为第二天凌晨，格式为 yyyy-MM-dd hh:mm:ss，必填
        String lateArrivalTime = paramMap.get("lateArrivalTime").toString();
        // TimeStamp:响应时间戳（从1970年到现在的秒数）
        String timeStamp = System.currentTimeMillis() + "";
        // Signature:MD5加密串
        String signature = MD5.GetMD5Code(System.currentTimeMillis() + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "OTA_HotelAvail").toUpperCase();

        String hotelAvailString = new RequestXml().AVAIL_REQUEST;

        hotelAvailString = hotelAvailString
                .replaceAll("#AllianceID", ALLIANCE_ID).replaceAll("#SID", SID)
                .replaceAll("#TimeStamp", timeStamp).replaceAll("#Signature", signature)
                .replaceAll("#HotelCode", hotelCode).replaceAll("#Start", start)
                .replaceAll("#End", end).replaceAll("#RatePlanCode", ratePlanCode)
                .replaceAll("#Quantity", quantity).replaceAll("#Count", count).replaceAll("#LateArrivalTime", lateArrivalTime);

        CtripCommonService commonService = new CtripCommonService();
        Map<String, Object> map = commonService.postForXML(HOTEL_AVAIL_URL, hotelAvailString);
        if (map == null || !"Success".equals(((Map) map.get("Header")).get("ResultCode").toString())) {
            return null;
        }

        return map;
    }
}
