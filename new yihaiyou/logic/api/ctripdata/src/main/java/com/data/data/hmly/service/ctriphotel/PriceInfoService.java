package com.data.data.hmly.service.ctriphotel;

import com.data.data.hmly.service.ctriphotel.base.HttpClientUtils;
import com.data.data.hmly.service.ctriphotel.base.MD5;
import com.data.data.hmly.service.ctriphotel.dao.CtripHotelPreferentialDao;
import com.data.data.hmly.service.ctriphotel.dao.CtripHotelPriceDao;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelId;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelPreferential;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelPrice;
import com.data.data.hmly.service.ctriphotel.priceinfo.entity.DateRestrictionType;
import com.data.data.hmly.service.ctriphotel.priceinfo.entity.OfferType;
import com.data.data.hmly.service.ctriphotel.priceinfo.entity.RatePlanType;
import com.data.data.hmly.service.ctriphotel.priceinfo.entity.RatePlansType;
import com.data.data.hmly.service.ctriphotel.priceinfo.entity.RateType;
import com.data.data.hmly.service.ctriphotel.priceinfo.entity.ResponseType;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 15/12/3.
 */

@Service
public class PriceInfoService {

    private static Logger logger = Logger.getLogger(PriceInfoService.class);


    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    private static final String HOTEL_PRICE_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelRatePlan.asmx";

    private static final String SOAP_PREFIX = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><Request xmlns=\"http://ctrip.com/\"><requestXML>";
    private static final String SOAP_SUFFIX = "</requestXML></Request></soap:Body></soap:Envelope>";

    private static final String ALLIANCE_ID = "22422";
    private static final String SID = "456399";
    private static final String API_KEY = "47C87868-C59F-4072-A121-AAE03B428240";


    @Resource
    private CtripHotelInfoService ctripHotelInfoService;
    @Resource
    private CtripHotelPriceDao ctripHotelPriceDao;
    @Resource
    private CtripHotelPreferentialDao ctripHotelPreferentialDao;


    public List<CtripHotelPrice> getList(Long hotelId) {
        //
        return ctripHotelPriceDao.getList(hotelId);
    }

    public void insertHotelPrice(Long cityId) {


//        ctripHotelPreferentialDao.delAll();

        int pageSize = 10;
        int index = 1;
        int size = pageSize;
        while (size == pageSize) {
            System.out.println("index=" + index);
            Page page = new Page(index, pageSize);
            index++;
            List<CtripHotelId> ctripHotelIds = ctripHotelInfoService.getList(cityId, page);

            size = ctripHotelIds.size();
            if (ctripHotelIds.size() == 0) {
                break;
            }
            List<Long> hotelIds = new ArrayList<Long>();
            for (CtripHotelId ctripHotelId : ctripHotelIds) {
                hotelIds.add(ctripHotelId.getHotelId());
                ctripHotelPriceDao.delAll(ctripHotelId.getHotelId());
            }
            System.out.println(hotelIds);
            StringBuilder builder = getRequestXml(hotelIds);
            String request = builder.toString();
            String result = postForXML(HOTEL_PRICE_URL, request);

            result = result.replace("xmlns=\"http://www.opentravel.org/OTA/2003/05\"", "");
            JAXBContext context = null;
            try {
                context = JAXBContext.newInstance(ResponseType.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ResponseType responseType = (ResponseType) unmarshaller.unmarshal(new StringReader(result));

                List<RatePlansType> ratePlanTypeList = responseType.getHotelResponse().getOTAHotelRatePlanRS().getRatePlans();
                insertPrice(ratePlanTypeList);

            } catch (JAXBException e) {
                logger.error("没有正常获取到酒店价格数据: + hotelIds");
                e.printStackTrace();
            }

        }
    }

    public void insertPrice(List<RatePlansType> ratePlanTypeList) {
        for (RatePlansType ratePlansType : ratePlanTypeList) {
            insertRatePlans(ratePlansType);
        }
    }

    public void insertRatePlans(RatePlansType ratePlansType) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<CtripHotelPrice> ctripHotelPriceList = new ArrayList<CtripHotelPrice>();

        Long hotelId = Long.parseLong(ratePlansType.getHotelCode());
        List<RatePlanType> ratePlanTypeList = ratePlansType.getRatePlan();
        for (RatePlanType ratePlanType : ratePlanTypeList) {
            Long ratePlanCode = Long.parseLong(ratePlanType.getRatePlanCode());
            Integer ratePlanCategory = Integer.parseInt(ratePlanType.getRatePlanCategory());
            Boolean isCommissionable = Boolean.parseBoolean(ratePlanType.getIsCommissionable());
            Boolean rateReturn = Boolean.parseBoolean(ratePlanType.getRateReturn());
            Long marketCode = Long.parseLong(ratePlanType.getMarketCode());


            // 优惠政策
            try {
                OfferType offerType = ratePlanType.getOffers().getOffer();
                List<DateRestrictionType> dateRestriction = offerType.getOfferRules().getOfferRule().getDateRestriction();
                String offerDescription = offerType.getOfferDescription().getText();
                List<OfferType.Discount> discountList = offerType.getDiscount();
                List<CtripHotelPreferential> ctripHotelPreferentialList = new ArrayList<CtripHotelPreferential>();
                for (DateRestrictionType dateRestrictionType : dateRestriction) {
                    for (OfferType.Discount discount : discountList) {
                        //

                        CtripHotelPreferential ctripHotelPreferential = new CtripHotelPreferential();
                        ctripHotelPreferential.setHotelId(hotelId);
                        ctripHotelPreferential.setRatePlanCode(ratePlanCode);

                        String st = dateRestrictionType.getStart();
                        String et = dateRestrictionType.getEnd();
                        ctripHotelPreferential.setStart(sdf.parse(st));
                        ctripHotelPreferential.setEnd(sdf.parse(et));
                        ctripHotelPreferential.setDesc(offerDescription);
                        ctripHotelPreferential.setNightsRequired(Integer.parseInt(discount.getNightsRequired()));
                        ctripHotelPreferential.setNightsDiscounted(Integer.parseInt(discount.getNightsDiscounted()));
                        ctripHotelPreferential.setCreatedTime(new Date());
                        ctripHotelPreferentialList.add(ctripHotelPreferential);

                    }
                }
                ctripHotelPreferentialDao.save(ctripHotelPreferentialList);
            } catch (Exception e) {
                //
                System.out.println("\n优惠不存在");
            }


            processOtherInfo(sdf, ctripHotelPriceList, hotelId, ratePlanType, ratePlanCode, ratePlanCategory, isCommissionable, rateReturn, marketCode);
        }

        ctripHotelPriceDao.save(ctripHotelPriceList);

    }

    private void processOtherInfo(SimpleDateFormat sdf, List<CtripHotelPrice> ctripHotelPriceList, Long hotelId, RatePlanType ratePlanType, Long ratePlanCode, Integer ratePlanCategory, Boolean isCommissionable, Boolean rateReturn, Long marketCode) {
        List<RateType> rateTypeList = ratePlanType.getRates().getRate();
        for (RateType rateType : rateTypeList) {
            CtripHotelPrice ctripHotelPrice = new CtripHotelPrice();

            ctripHotelPrice.setRatePlanCode(ratePlanCode);
            ctripHotelPrice.setHotelId(hotelId);
            ctripHotelPrice.setRatePlanCategory(ratePlanCategory);
            ctripHotelPrice.setIsCommissionabl(isCommissionable);
            ctripHotelPrice.setRateReturn(rateReturn);
            ctripHotelPrice.setMarketCode(marketCode);

            if (StringUtils.isNotBlank(rateType.getNumberOfUnits())) {
                ctripHotelPrice.setNumberOfUnits(Integer.parseInt(rateType.getNumberOfUnits()));
            }


            try {
                ctripHotelPrice.setStart(sdf.parse(rateType.getStart()));
                ctripHotelPrice.setEnd(sdf.parse(rateType.getEnd()));
            } catch (ParseException e) {
                e.printStackTrace();
                logger.error("时间格式化错误：" + rateType.getStart() + "_" + rateType.getEnd());
            }
            ctripHotelPrice.setIsInstantConfirm(Boolean.parseBoolean(rateType.getIsInstantConfirm()));
            ctripHotelPrice.setStatus(rateType.getStatus());
            ctripHotelPrice.setAmountBeforeTax(Float.parseFloat(rateType.getBaseByGuestAmts().getBaseByGuestAmt().getAmountBeforeTax()));
            ctripHotelPrice.setNumberOfGuests(Integer.parseInt(rateType.getBaseByGuestAmts().getBaseByGuestAmt().getNumberOfGuests()));
            if (rateType.getBaseByGuestAmts().getBaseByGuestAmt().getListPrice() != null
                    && !"".equals(rateType.getBaseByGuestAmts().getBaseByGuestAmt().getListPrice())) {
                ctripHotelPrice.setListPrice(Float.parseFloat(rateType.getBaseByGuestAmts().getBaseByGuestAmt().getListPrice()));
            }
            if (rateType.getGuaranteePolicies() != null) {
                if (rateType.getGuaranteePolicies().getGuaranteePolicy() != null) {
                    if (StringUtils.isNotBlank(rateType.getGuaranteePolicies().getGuaranteePolicy().getGuaranteeCode())) {
                        ctripHotelPrice.setGuaranteeCode(Integer.parseInt(rateType.getGuaranteePolicies().getGuaranteePolicy().getGuaranteeCode()));
                    }
                }
            }

            ctripHotelPrice.setCreatedTime(new Date());

            // 是否包含早餐
            Boolean breakfast = Boolean.parseBoolean(rateType.getMealsIncluded().getBreakfast());
            ctripHotelPrice.setBreakfast(breakfast);

            // cancel
            try {
                String cancelStart = rateType.getCancelPolicies().getCancelPenalty().getStart();
                String cancelEnd = rateType.getCancelPolicies().getCancelPenalty().getEnd();
                ctripHotelPrice.setCancelStart(sdf.parse(cancelStart));
                ctripHotelPrice.setCancelEnd(sdf.parse(cancelEnd));
            } catch (Exception e) {
                // 可能是由于可以随时取消（不包含取消时间）
            }

            // 房型描述
            ctripHotelPrice.setRoomDescription(ratePlanType.getDescription().getName());

            ctripHotelPriceList.add(ctripHotelPrice);

        }
    }

    public static String postForXML(String url, String xmlData) {
        try {
            HttpClient httpClient = HttpClientUtils.getHttpClient();

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(SOAP_PREFIX + StringEscapeUtils.escapeXml(xmlData) + SOAP_SUFFIX, ContentType.TEXT_XML));
            Clock clock = new Clock();
            HttpResponse response = httpClient.execute(httpPost);
            String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");


            resultStr = resultStr.substring(resultStr.indexOf("<RequestResult>") + 15, resultStr.indexOf("</RequestResult>"));
            resultStr = StringEscapeUtils.unescapeXml(resultStr);

            String hh = resultStr.substring(0, 100);
            System.out.println(hh);
            return resultStr;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }


    private static StringBuilder getRequestXml(List<Long> subList) {
        StringBuilder builder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 28);
        Date endDate = calendar.getTime();
        long timeStamp = System.currentTimeMillis();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
                .append("<Request>\n")
                //<!--AllianceID:分销商ID;SID:站点ID;TimeStamp:响应时间戳（从1970年到现在的秒数）;RequestType:请求接口的类型;Signature:MD5加密串-->
                .append("<Header AllianceID=\"").append(ALLIANCE_ID).append("\" SID=\"").append(SID).append("\" TimeStamp=\"").append(timeStamp)
                .append("\" Signature=\"").append(MD5.GetMD5Code(timeStamp + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "OTA_HotelRatePlan").toUpperCase())
                .append("\" RequestType=\"OTA_HotelRatePlan\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\" />")
                .append("<HotelRequest>\n")
                .append("<RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n")
                .append("<ns:OTA_HotelRatePlanRQ TimeStamp=\"2014-09-01T00:00:00.000+08:00\" Version=\"1.0\">\n")
                //价格计划查询列表（价格计划对应Ctrip子房型）,可罗列多个酒店进行查询//
                .append("<ns:RatePlans>\n");

        //酒店索引//
        // HotelCode属性：酒店代码；string类型；必填 //
        for (Long id : subList) {
            //酒店价格计划查询//
            builder.append("<ns:RatePlan>\n").append("<ns:DateRange Start=\"").append(simpleDateFormat.format(startDate)).append("\" End=\"").append(simpleDateFormat.format(endDate)).append("\"/>\n")
                    //价格计划查询条件列表，可空->\n" +
                    .append("<ns:RatePlanCandidates>\n")
                    //价格计划查询条件//
                    // AvailRatesOnlyInd属性：只读取可用价格计划；bool类型，如果为true，表示只读取能预订的酒店价格计划，如果为false，表示显示所有的已激活的酒店的价格计划，可能此酒店的价格计划不能预订；可空 //
                    .append("<ns:RatePlanCandidate AvailRatesOnlyInd=\"true\" >\n")
                    //酒店索引列表//
                    .append("<ns:HotelRefs>\n")
                    .append("<ns:HotelRef HotelCode=\"").append(id).append("\"/>\n")
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
        return builder;
    }

}
