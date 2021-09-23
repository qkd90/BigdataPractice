package com.data.data.hmly.service.ctriphotel;


import com.data.data.hmly.service.ctriphotel.base.MD5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vacuity on 15/12/3.
 */
public class CtripHotelOrderService {



    private static final String ALLIANCE_ID = "22422";
    private static final String SID = "456399";
    private static final String API_KEY = "47C87868-C59F-4072-A121-AAE03B428240";
    // 酒店可定性检查
    private static final String HOTEL_AVAIL_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelAvail.asmx";
    // 订单生成 OTA_HotelResn
    private static final String HOTEL_ORDER_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelRes.asmx";


    public static void main(String[] args) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uniqueid", "6b6b5ce1-573e-4f50-9a9d-36803a30c3f7");
        param.put("number", 1);
        param.put("hotelCode","2131045");
        param.put("start","2015-12-06T13:00:00.000+08:00");
        param.put("end","2015-12-07T13:00:00.000+08:00");
        param.put("ratePlanCode","12117043");
        param.put("arrivalTime", "19:00:00+08:00");
        param.put("lateArrivalTime", "2015-12-06T19:00:00.000+08:00");
        param.put("surname", "彭伟");
        param.put("phoneNumber", "18559216385");
        param.put("email", "vacuityv@gmail.com");
        param.put("guestCount", 1);
        param.put("amountBeforeTax", 118);
        param.put("paymentAmount", 118);

        List<String> surnameList = new ArrayList<String>();
        surnameList.add("彭伟");

        List<String> specialRequestList = new ArrayList<String>();
        specialRequestList.add("我只是想测试一下不要理我");

        CtripHotelOrderService service = new CtripHotelOrderService();
        Map<String, Object> map = service.hotelOrder(param, surnameList, specialRequestList);
        System.out.println(map.toString());


    }



    // 酒店可定性检查
    public Map<String, Object> hotelOrder(Map<String, Object> paramMap, List<String> customerList, List<String> specialRequestList) {

        String uniqueid = paramMap.get("uniqueid").toString();
        String surname = paramMap.get("surname").toString();
        String phoneNumber = paramMap.get("phoneNumber").toString();
        String email = paramMap.get("email").toString();
        String amountBeforeTax = paramMap.get("amountBeforeTax").toString();
        String paymentAmount = paramMap.get("paymentAmount").toString();
        // HotelCode属性：酒店代码；string类型；必填
        String hotelCode = paramMap.get("hotelCode").toString();
        // Start属性：入住日期；DateTime类型；必填
        String start = paramMap.get("start").toString();
        // End属性：离店日期；DateTime类型；必填
        String end = paramMap.get("end").toString();
        // RatePlanCode属性：价格计划代码；string类型；必填
        String ratePlanCode = paramMap.get("ratePlanCode").toString();
        // 将要预订的房间数量；int类型；必填
        String number = paramMap.get("number").toString();
        // Count属性：客人数量；int类型；必填 -->
        String guestCount = paramMap.get("guestCount").toString();
        // 入住人最晚到店时间，有可能最晚到店日期为第二天凌晨，格式为 yyyy-MM-dd hh:mm:ss，必填
        String lateArrivalTime = paramMap.get("lateArrivalTime").toString();
        String arrivalTime = paramMap.get("arrivalTime").toString();
        // TimeStamp:响应时间戳（从1970年到现在的秒数）
        String timeStamp = System.currentTimeMillis() + "";
        // Signature:MD5加密串
        String signature = MD5.GetMD5Code(timeStamp + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "OTA_HotelRes").toUpperCase();

        RequestXml requestXml = new RequestXml();
        String hotelOrderString = requestXml.HOTEL_ORDER;
        String customerName = requestXml.CUSTOMER_NAME;
        String specialRequest = requestXml.SPECIAL_REQUEST;

        String customerNameString = "";
        for (String cname : customerList) {
            String singleCustomer = customerName.replaceAll("#SURNAME", cname);
            customerNameString = customerNameString + singleCustomer;
        }

        String requestString = "";
        for (String rstring : specialRequestList) {
            String singleRequest = specialRequest.replaceAll("#SPECIAL_REQUEST", rstring);
            requestString = requestString + singleRequest;
        }

        hotelOrderString = hotelOrderString
                .replaceAll("#AllianceID", ALLIANCE_ID)
                .replaceAll("#SID", SID)
                .replaceAll("#TimeStamp", timeStamp)
                .replaceAll("#Signature", signature)
                .replaceAll("#HOTEL_CODE", hotelCode)
                .replaceAll("#START", start)
                .replaceAll("#END", end).replaceAll("#RATE_PLAN_CODE", ratePlanCode)
                .replaceAll("#LateArrivalTime", lateArrivalTime)
                .replaceAll("#UNIQUEID", uniqueid)
                .replaceAll("#NUMBER", number)
                .replaceAll("#ARRIVAL_TIME", arrivalTime)
                .replaceAll("#CUSTOMERS", customerNameString)
                .replaceAll("#SURNAME", surname)
                .replaceAll("#PHONE_NUMBER", phoneNumber)
                .replaceAll("#EMAIL", email)
                .replaceAll("#LATE_ARRIVAL_TIME", lateArrivalTime)
                .replaceAll("#GUEST_COUNT", guestCount)
                .replaceAll("#SPECIAL_REQUESTS", requestString)
                .replaceAll("#AMOUNT_BEFORE_TAX", amountBeforeTax)
                .replaceAll("#PAYMENT_AMOUNT", paymentAmount);

        System.out.println("request=\n" + hotelOrderString);
        CtripCommonService commonService = new CtripCommonService();
        Map<String, Object> map = commonService.postForXML(HOTEL_ORDER_URL, hotelOrderString);
        System.out.println("map=\n" + map);
        if (map == null || !"Success".equals(((Map) map.get("Header")).get("ResultCode").toString())) {
            return null;
        }

        return map;
    }
}
