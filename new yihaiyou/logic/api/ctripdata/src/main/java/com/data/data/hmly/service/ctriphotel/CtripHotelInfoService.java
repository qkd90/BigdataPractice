package com.data.data.hmly.service.ctriphotel;

import com.data.data.hmly.service.ctriphotel.base.HttpClientUtils;
import com.data.data.hmly.service.ctriphotel.base.MD5;
import com.data.data.hmly.service.ctriphotel.dao.CtripHotelIdDao;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelId;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vacuity on 15/12/2.
 */
@Service
public class CtripHotelInfoService {

    private final Logger logger = Logger.getLogger(CtripHotelInfoService.class);

    private final String HOTEL_LIST_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelSearch.asmx";

    private final String soap_prefix = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><Request xmlns=\"http://ctrip.com/\"><requestXML>";
    private final String soap_suffix = "</requestXML></Request></soap:Body></soap:Envelope>";


    private final String ALLIANCE_ID = "22422";
    private final String SID = "456399";
    private final String API_KEY = "47C87868-C59F-4072-A121-AAE03B428240";



    @Resource
    private CtripHotelIdDao ctripHotelIdDao;



    public void dotest(){
        CtripHotelId ctripHotelId = new CtripHotelId();
        ctripHotelId.setCityId(25L);
        ctripHotelId.setHotelId(11111L);
        ctripHotelIdDao.saveOrUpdate(ctripHotelId, ctripHotelId.getId());
    }

    public List<CtripHotelId> getList(Long cityId, Page page){
        return ctripHotelIdDao.getList(cityId, page);
    }

    public void insertId(Long cityId) {

        // 删除旧数据
        ctripHotelIdDao.delAll(cityId);

        String requestStr = getHotelRequestUrl(cityId);
        String result = postForXML(HOTEL_LIST_URL, requestStr);
        Pattern p = Pattern.compile("HotelId=\"(\\d+)\"");
        Matcher m = p.matcher(result);
        HashSet<Long> hotelIdSet = new HashSet<Long>();
        int i = 0;
        while (m.find()) {
            System.out.println(Long.parseLong(m.group(1)));
            CtripHotelId ctripHotelId = new CtripHotelId();
            ctripHotelId.setCityId(cityId);
            ctripHotelId.setHotelId(Long.parseLong(m.group(1)));
            try {
                System.out.println("i=" + i++);
                ctripHotelIdDao.saveOrUpdate(ctripHotelId, ctripHotelId.getId());
                ctripHotelIdDao.getHibernateTemplate().flush();
                ctripHotelIdDao.getHibernateTemplate().clear();
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }

        }
    }


    private String getHotelRequestUrl(Long cityId) {
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
            requestStr += " HotelCityCode=\"" + cityId + "\" ";
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
        return requestStr;
    }



    public String postForXML(String url, String xmlData) {
        try {
            HttpClient httpClient = HttpClientUtils.getHttpClient();

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(soap_prefix + StringEscapeUtils.escapeXml(xmlData) + soap_suffix, ContentType.TEXT_XML));
            Clock clock = new Clock();
            HttpResponse response = httpClient.execute(httpPost);
            String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(resultStr.substring(0, 1000));


            resultStr = resultStr.substring(resultStr.indexOf("<RequestResult>") + 15, resultStr.indexOf("</RequestResult>"));
            resultStr = StringEscapeUtils.unescapeXml(resultStr);

            return resultStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
