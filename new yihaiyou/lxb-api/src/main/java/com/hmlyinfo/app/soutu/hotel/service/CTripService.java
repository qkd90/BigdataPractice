package com.hmlyinfo.app.soutu.hotel.service;

import com.google.common.collect.Lists;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.domain.CtripPrice;
import com.hmlyinfo.app.soutu.hotel.mapper.CtripHotelMapper;
import com.hmlyinfo.base.util.HttpClientUtils;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.MD5;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by guoshijie on 2014/8/22.
 */
@Service
public class CTripService {
    public static final Logger logger = Logger.getLogger(CTripService.class);

    private static final String HOTEL_LIST_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelSearch.asmx";
    private static final String HOTEL_DETAIL_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelDescriptiveInfo.asmx";
    private static final String HOTEL_PRICE_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelRatePlan.asmx";
    private static final String HOTEL_PRICE_CACHE_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelCacheChange.asmx";
    private static final String HOTEL_ROOM_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelAvail.asmx";

    private static final String soap_prefix = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><Request xmlns=\"http://ctrip.com/\"><requestXML>";
    private static final String soap_suffix = "</requestXML></Request></soap:Body></soap:Envelope>";

    private static final String ALLIANCE_ID = "22422";
    private static final String SID = "456399";
    private static final String API_KEY = "47C87868-C59F-4072-A121-AAE03B428240";

    @Autowired
    CtripHotelMapper<CtripHotel> cHotelMapper;
    @Autowired
    CtripHotelService ctripHotelService;
    @Autowired
    CtripPriceService ctripPriceService;

    List<Long> tempIds = new ArrayList<Long>();

    private AtomicInteger up;
    private AtomicInteger in;
    private AtomicInteger failed;

    int max;//记录全局总数

    String planCodes = "1.110100;10.610100;2.310100;30.440300;32.440100;37.530700,4.500100;206.430100,17.330100;477.420100,28.510100;147.130300,43.460200;7.370200,25.350200,12.320100;144.370100,14.320500,559.410100;19.330900,6.210200,34.530100;21268.532900,3.120100,38.520100,27.430800;380.450100,13.320200,33.450300,189.450500,39.650100;299.441300,376.360100,375.330200,124.630100,99.640100,42.460100;105.140100,23.341000,350.410300,100.620100,58.810000,41.540100,35.532800;103.150100,422.440200,258.350100,31.440400,15.321000,614.370400,479.371000,617.710100,515.420500,513.140900;22.330600,533.370600,560.350600,354.450200,562.130800,353.320700,4255.150700,491.330300,447.440500,308.330700,550.130700,16.321100,720.710400,348.350800,3849.710300,305.360200,345.511100,179.520400,571.330400,478.340200,1030.610500,108.542600,59.820000,92.542300,1140.451000,7807.632200,175.654300,98.654000,3839.542400,97.542500,1806.533300,7537.513400,7810.710200,1838.513200,439.542200,21358.653100;104.140728,1083.150781,83.320583,21416.350627,26.350782,489.361130,657.429021,446.430382,677.430423,866.433123,871.450321,52.469002,44.469005,45.469006,94.510181,95.511181,20856.511528,91.513225,4130.513321,1222.513337,1819.530522,3996.530821,660.533421,11.620982";
    
    public void updateCtripHotel()
    {
    	logger.info("Update Start:");
    	Calendar c = Calendar.getInstance(); 
    	int day = c.get(Calendar.DATE);
    	
    	String[] planTask = planCodes.split(";");
    	if(day > planTask.length || day < 1){
    		return;
    	}
    	String task = planTask[day-1];
    	String[] dayPlan = task.split(",");
    	for(String dayTask : dayPlan){
    		String[] idcode = dayTask.split("\\.");
    		int cityId = Integer.parseInt(idcode[0]);
    		int cityCode = Integer.parseInt(idcode[1]);
    		search(cityId, cityCode);
    	}
    	logger.info("Update end");
    }
    
    
    //@Transactional
    @SuppressWarnings("unchecked")
    public List<CtripHotel> search(int cityId, int cityCode) {
        in = new AtomicInteger();
        up = new AtomicInteger();
        failed = new AtomicInteger();
        max = 1;
        final int city = cityCode;
        new Thread() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                while (max == 1) {
                	logger.info("cityCode =" + city + ":更新" + in.get() + "个酒店信息,失败" + failed.get() + "个,共计" + (System.currentTimeMillis() - time) / 1000 + "秒");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                logger.info("cityCode =" + city + ":执行结束，插入" + in.get() + "个酒店信息,失败" + failed.get() + "个,共计" + (System.currentTimeMillis() - time) / 1000 + "秒");
            }
        }.start();
        getCtripHotels(cityId, cityCode);
        max = 0;
        return null;
    }

    private List<CtripHotel> getCtripHotels(int cityId, int cityCode) {
        String requestStr = "<Request>"
                + "<Header AllianceID=\"" + ALLIANCE_ID + "\" SID=\"" + SID + "\" TimeStamp=\"" + System.currentTimeMillis()
                + "\" Signature=\"" + MD5.GetMD5Code(System.currentTimeMillis() + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "OTA_HotelSearch").toUpperCase()
                + "\" RequestType=\"OTA_HotelSearch\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\" />"
                + "<HotelRequest>"
                + "<RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
                + "<ns:OTA_HotelSearchRQ Version=\"1.0\" PrimaryLangID=\"zh\" xsi:schemaLocation=\"http://www.opentravel.org/OTA/2003/05 OTA_HotelSearchRQ.xsd\" xmlns=\"http://www.opentravel.org/OTA/2003/05\">"
//                <!--查询条件-->
//        <!-- AvailableOnlyIndicator 属性：bool类型。国内酒店：true只返回可预订酒店；false返回所有已激活酒店, 可能此酒店不能预订（会返回7天）；海外酒店：ture过滤掉booking和agoda酒店；如果为false,不过滤。-->
                + "<ns:Criteria AvailableOnlyIndicator=\"false\">"
//        <!-- 查询具体信息-->
//        <ns:Criterion>
                + "<ns:Criterion>"
//        <!--酒店信息查询条件：查询属性中至少有一条查询条件-->
//        <!-- HotelCityCode属性：城市ID ，int类型，可空-->
                + " <ns:HotelRef";
        if (cityId > 0) {
            requestStr += " HotelCityCode=\"" + cityId + "\"";
        }
////        <!-- AreaID属性：行政区ID ，int类型，可空-->
//        if (areaCode > 0) {
//            requestStr += " AreaID=\"" + areaCode + "\"";
//        }
////        <!-- HotelName属性：酒店名称，string类型，可空-->
//        if (hotelName != null) {
//            requestStr += " HotelName=\"" + hotelName + "\"";
//        }
//        <!--酒店的坐标：返回的酒店信息中的坐标类型-->
//        <!-- PositionTypeCode属性：坐标类型，string类型，可空，参见ListCode(PTC)，501Mapbar 坐标，502Google 坐标，默认为501-->
        requestStr += "/><ns:Position />"
//
//        <!--酒店等级-->
//        <!-- Provider属性：评分者，string类型，可空，HotelStarRate(酒店星级)，CtripStarRate(携程星级)，CtripRecommendRate(携程评分)-->
//        <!-- Rating属性：分数或级别，decimal类型，可空-->
                + "<ns:Award />"
                + "</ns:Criterion>"
                + "</ns:Criteria>"
                + "</ns:OTA_HotelSearchRQ>"
                + "</RequestBody>"
                + "</HotelRequest>"
                + "</Request>";
        Map<String, Object> map = postForXML(HOTEL_LIST_URL, requestStr);
        if (map == null || !"Success".equals(((Map) map.get("Header")).get("ResultCode").toString())) {
            return null;
        }
        List<Map<String, Object>> hotelList = (List<Map<String, Object>>) ((Map) ((Map) ((Map) map.get("HotelResponse")).get("OTA_HotelSearchRS")).get("Properties")).get("Property");
        logger.info("cityCode =" + cityCode + ":总共" + hotelList.size() + "个酒店");
        List<Long> hotelIds = new ArrayList<Long>();
        for (Map<String, Object> hotel : hotelList) {
            hotelIds.add(Long.valueOf(hotel.get("HotelId").toString()));
        }
        
        Map<String, Object> oldMap = new HashMap<String, Object>();
        oldMap.put("cityCode", cityCode);
        List<CtripHotel> oldHotelList = ctripHotelService.listColumns(oldMap, Lists.newArrayList("id"));
        List<String> oldIds = ListUtil.getIdList(oldHotelList, "id");
        for(String oid : oldIds){
        	if(!hotelIds.contains(oid)){
        		CtripHotel oldHotel = cHotelMapper.selById(oid);
        		oldHotel.setIsValid("F");
        		cHotelMapper.update(oldHotel);
        	}
        }
        
        long time = System.currentTimeMillis();
        long last;
        for (int i = 0; i < hotelIds.size(); i += 10) {
            if (i + 10 > hotelIds.size()) {
                getDetails(hotelIds.subList(i, hotelIds.size()), cityCode);
            } else {
                getDetails(hotelIds.subList(i, i + 10), cityCode);
            }
            last = System.currentTimeMillis() - time;
            if (last < 2100) {
                try {
                    Thread.sleep(2100 - last);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            time = System.currentTimeMillis();
        }
//        if (!newList.isEmpty()) {
//            ctripHotels.addAll(newList);
//        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<CtripHotel> getDetails(List<Long> ids, int cityCode) {
    	/*
        List<CtripHotel> templist = ctripHotelService.list(Collections.<String, Object>singletonMap("ids", ids));
        for (CtripHotel ctripHotel : templist) {
            up.getAndIncrement();
            ids.remove(ctripHotel.getId());
        }
        if (ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        */
    	
        tempIds.addAll(ids);
        
        /*
        if (tempIds.size() < 2) {
            return null;
        }
        */
        
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
                .append("<Request>\n")
                .append("<Header AllianceID=\"").append(ALLIANCE_ID).append("\" SID=\"").append(SID).append("\" TimeStamp=\"").append(System.currentTimeMillis())
                .append("\" Signature=\"").append(MD5.GetMD5Code(System.currentTimeMillis() + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "OTA_HotelDescriptiveInfo").toUpperCase())
                .append("\" RequestType=\"OTA_HotelDescriptiveInfo\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\" />")
                .append("<HotelRequest>\n")
                .append("<RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n")
                .append("<OTA_HotelDescriptiveInfoRQ Version=\"1.0\" xsi:schemaLocation=\"http://www.opentravel.org/OTA/2003/05 OTA_HotelDescriptiveInfoRQ.xsd\" xmlns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n")
                        //<!--酒店详细描述信息请求列表，可以请求多个酒店-->
                .append("<HotelDescriptiveInfos>\n");

        for (Long id : tempIds) {
            //<!--酒店信息，HotelCode属性：酒店代码，string类型，必填 -->
            //<!-- PositionTypeCode属性：返回的酒店的坐标类型，string类型，可空，参见ListCode(PTC)，501Mapbar 坐标，502Google 坐标，默认为502-->
            builder.append("<HotelDescriptiveInfo HotelCode=\"").append(id).append("\" PositionTypeCode=\"502\">\n")
                    //<!-- SendData属性：是否请求酒店类信息；bool类型，True表示需要发送酒店类信息；可空 -->
                    .append("<HotelInfo SendData=\"true\"/>\n")
                            //<!-- SendGuestRooms属性：是否发送客房信息；bool类型，True表示需要发送；可空 -->
                    .append("<FacilityInfo SendGuestRooms=\"true\"/>\n")
                            //<!-- SendAttractions属性：是否发送景点地标信息；bool类型，True表示需要发送；可空 -->
                            //<!-- SendRecreations属性：是否发送娱乐区域信息；bool类型，True表示需要发送；可空 -->
                    .append("<AreaInfo SendAttractions=\"true\" SendRecreations=\"true\"/>\n")
                            //<!--联系方式类信息-->
                            //<!-- SendData属性：是否发送联系方式类数据，bool类型，True表示需要发送；可空 -->
                    .append("<ContactInfo SendData=\"true\"/>\n")
                            //<!--多媒体信息-->
                            //<!-- SendData属性：是否发送多媒体数据，bool类型，True表示需要发送；可空 -->
                    .append("<MultimediaObjects SendData=\"true\"/>\n").append("</HotelDescriptiveInfo>\n");
        }

        builder.append("</HotelDescriptiveInfos>\n").append("</OTA_HotelDescriptiveInfoRQ>\n").append("</RequestBody>\n").append("</HotelRequest>\n").append("</Request>\n");
        Map<String, Object> map = postForXML(HOTEL_DETAIL_URL, builder.toString());
        if (map == null || !"Success".equals(((Map) map.get("Header")).get("ResultCode").toString())) {

            for (Long id : tempIds) {
                failed.getAndIncrement();
            }
            tempIds.clear();
            if (map != null) {
                logger.info(((Map) map.get("Header")).get("ResultMsg").toString());
            }
            return Collections.EMPTY_LIST;
        }
        tempIds.clear();
        List<Map<String, Object>> hotelList = (List<Map<String, Object>>) ((Map) ((Map) ((Map) map.get("HotelResponse")).get("OTA_HotelDescriptiveInfoRS")).get("HotelDescriptiveContents")).get("HotelDescriptiveContent");
        List<CtripHotel> ctripHotels = new ArrayList<CtripHotel>();
        for (Map<String, Object> hotel : hotelList) {
            CtripHotel ctripHotel = prepareCtripHotel(hotel,cityCode);
            try {
//                ctripHotelService.updateOrInsert(ctripHotel);
                in.getAndIncrement();
            } catch (Exception e) {
                failed.getAndIncrement();
                logger.info("存不进数据库");
            }
            hotel.put("id", ctripHotel.getId());
            ctripHotels.add(ctripHotel);
        }

        return ctripHotels;
    }

    @SuppressWarnings("unchecked")
    private CtripHotel prepareCtripHotel(Map<String, Object> hotel, int cityCode) {
        StringBuilder serviceStr = new StringBuilder();
        CtripHotel ctripHotel = new CtripHotel();
        ctripHotel.setCityCode(cityCode);
        ctripHotel.setUserId(1);
        ctripHotel.setId(Long.valueOf(hotel.get("HotelCode").toString()));
        ctripHotel.setHotelName(hotel.get("HotelName").toString());
        ctripHotel.setAddr(((Map) ((Map) hotel.get("HotelInfo")).get("Address")).get("AddressLine").toString());
        ctripHotel.setContact(((ArrayList<Map>) ((Map) ((Map) ((Map) hotel.get("ContactInfos")).get("ContactInfo")).get("Phones")).get("Phone")).get(0).get("PhoneNumber").toString());
        try {
            ctripHotel.setImgUrl(((Map) ((ArrayList<Map>) ((Map) ((ArrayList<Map<String, Object>>) ((Map) hotel.get("MultimediaDescriptions")).get("MultimediaDescription")).get(0).get("ImageItems")).get("ImageItem")).get(0).get("ImageFormat")).get("URL").toString());
        } catch (Exception e) {
            try {
                ctripHotel.setImgUrl(((Map) ((Map) ((Map) ((ArrayList<Map<String, Object>>) ((Map) hotel.get("MultimediaDescriptions")).get("MultimediaDescription")).get(0).get("ImageItems")).get("ImageItem")).get("ImageFormat")).get("URL").toString());
            } catch (Exception e1) {
                //logger.info("cityCode =" + cityCode + ":没有图片");
            }
        }
        if (((Map) hotel.get("HotelInfo")).get("Services") != null) {
            try {
                List<Map<String, Object>> services = (List<Map<String, Object>>) ((Map) ((Map) hotel.get("HotelInfo")).get("Services")).get("Service");
                if (services != null) {
                    for (Map<String, Object> service : services) {
                        serviceStr.append(service.get("DescriptiveText")).append(",");
                    }
                    serviceStr.setLength(serviceStr.length() - 1);
                }
            } catch (Exception e) {
                serviceStr.append(((Map) ((Map) ((Map) hotel.get("HotelInfo")).get("Services")).get("Service")).get("DescriptiveText"));
            }
        }
        ctripHotel.setService(serviceStr.toString());
        Map<String, Object> position = (Map) ((Map) hotel.get("HotelInfo")).get("Position");
        ctripHotel.setLatitude(Double.valueOf(position.get("Latitude").toString()));
        ctripHotel.setLongitude(Double.valueOf(position.get("Longitude").toString()));
        ctripHotel.setStar(Integer.valueOf((((ArrayList<Map>) ((Map) ((Map) hotel.get("AffiliationInfo")).get("Awards")).get("Award")).get(1)).get("Rating").toString()));
        try {
            ctripHotel.setIntro(((ArrayList<Map>) ((Map) ((ArrayList<Map<String, Object>>) ((Map) hotel.get("MultimediaDescriptions")).get("MultimediaDescription")).get(1).get("TextItems")).get("TextItem")).get(0).get("Description").toString());
        } catch (Exception e) {
            try {
                ctripHotel.setIntro(((Map) ((Map) ((ArrayList<Map<String, Object>>) ((Map) hotel.get("MultimediaDescriptions")).get("MultimediaDescription")).get(0).get("TextItems")).get("TextItem")).get("Description").toString());
            } catch (Exception e1) {
                //logger.info("cityCode =" + cityCode + ":没有文字");
            }
        } 
        if (((Map) hotel.get("HotelInfo")).get("WhenBuilt") != null) {
            ctripHotel.setOpenTime(((Map) hotel.get("HotelInfo")).get("WhenBuilt").toString());
        }
        return ctripHotel;
    }

    
    
    
    
    
    
    
    
    int i;//计数用
    int size;//每次批量

    public void getPrice(Long cityCode) {
        List<CtripHotel> hotelList = ctripHotelService.list(Collections.<String, Object>singletonMap("cityCode", cityCode));
        max = hotelList.size();
        long time;
        long last = 0;
        i=0;
        size = 10;
        up = new AtomicInteger();
        in = new AtomicInteger();
        new Thread() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                while (size < max) {
                    System.out.println("已完成" + i + "个酒店");
                    System.out.println("更新了" + up + "条");
                    System.out.println("插入了" + in + "条");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("执行结束，插入" + in.get() + "个信息,共计" + (System.currentTimeMillis() - time) / 1000 + "秒");
            }
        }.start();
        while (i < hotelList.size()) {
            if (size > hotelList.size()) {
                size = hotelList.size();
            }
            if (last < 2100) {
                try {
                    Thread.sleep(2100 - last);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            time = System.currentTimeMillis();
            List<CtripHotel> subList = hotelList.subList(i, size);
            getPrice(subList);
            last = System.currentTimeMillis() - time;
            i += 10;
            size += 10;
        }
    }

    
    
    
    private void getPrice(List<CtripHotel> subList) {
        StringBuilder builder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 28);
        Date endDate = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
                .append("<Request>\n")
                        //<!--AllianceID:分销商ID;SID:站点ID;TimeStamp:响应时间戳（从1970年到现在的秒数）;RequestType:请求接口的类型;Signature:MD5加密串-->
                .append("<Header AllianceID=\"").append(ALLIANCE_ID).append("\" SID=\"").append(SID).append("\" TimeStamp=\"").append(System.currentTimeMillis())
                .append("\" Signature=\"").append(MD5.GetMD5Code(System.currentTimeMillis() + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "OTA_HotelRatePlan").toUpperCase())
                .append("\" RequestType=\"OTA_HotelRatePlan\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\" />")
                .append("<HotelRequest>\n")
                .append("<RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n")
                .append("<ns:OTA_HotelRatePlanRQ TimeStamp=\"2014-09-01T00:00:00.000+08:00\" Version=\"1.0\">\n")
                        //价格计划查询列表（价格计划对应Ctrip子房型）,可罗列多个酒店进行查询//
                .append("<ns:RatePlans>\n");

        //酒店索引//
        // HotelCode属性：酒店代码；string类型；必填 //
        for (CtripHotel ctripHotel : subList) {
            //酒店价格计划查询//
            builder.append("<ns:RatePlan>\n").append("<ns:DateRange Start=\"").append(simpleDateFormat.format(startDate)).append("\" End=\"").append(simpleDateFormat.format(endDate)).append("\"/>\n")
                    //价格计划查询条件列表，可空->\n" +
                    .append("<ns:RatePlanCandidates>\n")
                            //价格计划查询条件//
                            // AvailRatesOnlyInd属性：只读取可用价格计划；bool类型，如果为true，表示只读取能预订的酒店价格计划，如果为false，表示显示所有的已激活的酒店的价格计划，可能此酒店的价格计划不能预订；可空 //
                    .append("<ns:RatePlanCandidate AvailRatesOnlyInd=\"true\" >\n")
                            //酒店索引列表//
                    .append("<ns:HotelRefs>\n")
                    .append("<ns:HotelRef HotelCode=\"").append(ctripHotel.getId()).append("\"/>\n")
                    .append("</ns:HotelRefs>\n")
                    .append("</ns:RatePlanCandidate>\n")
                    .append("</ns:RatePlanCandidates>\n")
                            //附加参数，一般不需要可以不用此节点，可空-//
                            // RestrictedDisplayIndicator属性：限制类型是否查询预付计划；bool类型，有些价格计划（子房型）仅用来对某些渠道开放预订。如果本限制节点为真，意味着这条价格计划（子房型）对普通终端用户不可用；可空//
                    .append("</ns:RatePlan>\n");
        }

        builder.append("</ns:RatePlans>\n")
                .append("</ns:OTA_HotelRatePlanRQ>\n")
                .append("</RequestBody>\n")
                .append("</HotelRequest>\n")
                .append("</Request>\n");

        Map<String, Object> map = postForXML(HOTEL_PRICE_URL, builder.toString());
        if (map == null || !"Success".equals(((Map) map.get("Header")).get("ResultCode").toString())) {
            return;
        }
        List<Map<String, Object>> ratePlans = (List<Map<String, Object>>) ((Map) ((Map) map.get("HotelResponse")).get("OTA_HotelRatePlanRS")).get("RatePlans");
        for (Map<String, Object> hotel : ratePlans) {
            List<CtripPrice> tempList = ctripPriceService.list(Collections.singletonMap("hotelId", hotel.get("HotelCode")));
            Map<String, Long> oldPriceMap = new HashMap<String, Long>();
            for (CtripPrice ctripPrice : tempList) {
                oldPriceMap.put(simpleDateFormat.format(ctripPrice.getExpireDate()) + "_" + ctripPrice.getRatePlanCode(), ctripPrice.getId());
            }
            if (hotel.get("RatePlan") instanceof ArrayList) {
                for (Map<String, Object> ratePlan : (ArrayList<Map<String, Object>>) hotel.get("RatePlan")) {
                    if (((Map) ratePlan.get("Rates")).get("Rate") instanceof ArrayList) {
                        for (Map<String, Object> rate : (ArrayList<Map<String, Object>>) ((Map) ratePlan.get("Rates")).get("Rate")) {
                            insertPrice(simpleDateFormat, hotel, oldPriceMap, ratePlan, rate);
                        }
                    } else {
                        Map<String, Object> rate = (Map<String, Object>) ((Map) ratePlan.get("Rates")).get("Rate");
                        insertPrice(simpleDateFormat, hotel, oldPriceMap, ratePlan, rate);
                    }


                }
            } else if (hotel.get("RatePlan") instanceof HashMap) {
                Map<String, Object> ratePlan = (Map<String, Object>) hotel.get("RatePlan");
                if (((Map) ratePlan.get("Rates")).get("Rate") instanceof ArrayList) {
                    for (Map<String, Object> rate : (ArrayList<Map<String, Object>>) ((Map) ratePlan.get("Rates")).get("Rate")) {
                        insertPrice(simpleDateFormat, hotel, oldPriceMap, ratePlan, rate);
                    }
                } else {
                    Map<String, Object> rate = (Map<String, Object>) ((Map) ratePlan.get("Rates")).get("Rate");
                    insertPrice(simpleDateFormat, hotel, oldPriceMap, ratePlan, rate);
                }

            } else {
                System.out.println("no plan");
            }

        }
    }

    private void insertPrice(SimpleDateFormat simpleDateFormat, Map<String, Object> hotel, Map<String, Long> oldPriceMap, Map<String, Object> ratePlan, Map<String, Object> rate) {
        CtripPrice ctripPrice = new CtripPrice();
        ctripPrice.setDescription(((Map) ratePlan.get("Description")).get("Name").toString());
        ctripPrice.setHotelId(Long.valueOf(hotel.get("HotelCode").toString()));
        ctripPrice.setRatePlanCode(Integer.parseInt(ratePlan.get("RatePlanCode").toString()));
        try {
            ctripPrice.setExpireDate(simpleDateFormat.parse(rate.get("End").toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ctripPrice.setPrice(Double.valueOf(((Map) ((Map) rate.get("BaseByGuestAmts")).get("BaseByGuestAmt")).get("AmountBeforeTax").toString()));
        // 判断是否包含免费宽带
        if (rate.get("Fees") == null || "".equals(rate.get("Fees").toString())) {
            ctripPrice.setHasBroadband(false);
        } else {
            boolean broadFlag = true;
            if (((Map) rate.get("Fees")).get("Fee") instanceof ArrayList) {
                List<Map<String, Object>> fees = (List<Map<String, Object>>) ((Map) rate.get("Fees")).get("Fee");
                for (Map<String, Object> fee : fees) {
                    if (Integer.parseInt(fee.get("Code").toString()) == 1002) {
                        broadFlag = false;
                        break;
                    }
                }
            } else {
                Map<String, Object> fee = (Map<String, Object>) ((Map) rate.get("Fees")).get("Fee");
                if (Integer.parseInt(fee.get("Code").toString()) == 1002) {
                    broadFlag = false;
                }
            }

            ctripPrice.setHasBroadband(broadFlag);
        }
        // 判断是否包含免费早餐
        if (Boolean.valueOf(((Map) rate.get("MealsIncluded")).get("Breakfast").toString())) {
            ctripPrice.setHasBreakfast(true);
        } else {
            ctripPrice.setHasBreakfast(false);
        }

        try {
            Long id = oldPriceMap.get(simpleDateFormat.format(ctripPrice.getExpireDate()) + "_" + ctripPrice.getRatePlanCode());
            if (id != null) {
                up.getAndIncrement();
//                ctripPrice.setId(id);
//                ctripPriceService.update(ctripPrice);
            } else {
                in.getAndIncrement();
                ctripPriceService.insert(ctripPrice);
            }
        } catch (Exception e) {
            System.out.println("存不进数据库");
            e.printStackTrace();
        }
    }


    public Map<String, Object> postForXML(String url, String xmlData) {
        try {
            HttpClient httpClient = HttpClientUtils.getHttpClient();

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(soap_prefix + StringEscapeUtils.escapeXml(xmlData) + soap_suffix, ContentType.TEXT_XML));
            HttpResponse response = httpClient.execute(httpPost);
            String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
            resultStr = resultStr.substring(resultStr.indexOf("<RequestResult>") + 15, resultStr.indexOf("</RequestResult>"));
            resultStr = StringEscapeUtils.unescapeXml(resultStr);
            return HttpUtil.readStringXmlOutNull(resultStr);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
