package com.data.data.hmly.service.ctriphotel;

import com.data.data.hmly.service.ctriphotel.base.HttpClientUtils;
import com.data.data.hmly.service.ctriphotel.base.MD5;
import com.data.data.hmly.service.ctriphotel.dao.CtripHotelDao;
import com.data.data.hmly.service.ctriphotel.dao.CtripHotelDescriptionDao;
import com.data.data.hmly.service.ctriphotel.dao.CtripHotelImgDao;
import com.data.data.hmly.service.ctriphotel.dao.CtripHotelMessageDao;
import com.data.data.hmly.service.ctriphotel.dao.CtripHotelPriceDao;
import com.data.data.hmly.service.ctriphotel.dao.CtripHotelRoomDao;
import com.data.data.hmly.service.ctriphotel.dao.CtripHotelServiceDao;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotel;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelDescription;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelId;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelImg;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelMessage;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelRoom;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelService;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.AwardType;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.CityImportantMessageTypeType;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.DescriptionType;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.GuestRoomType;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.HotelDescriptiveContentType;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.HotelInfoType;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.ImageItemType;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.MultimediaDescriptionType;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.ResponseType;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.ServiceType;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.TextItemType;
import com.data.data.hmly.service.ctriphotel.staticinfo.entity.TypeRoomType;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Criteria;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by vacuity on 15/12/3.
 */

@Service
public class CtripHotelStaticInfoService {


    private static final Logger LOGGER = Logger.getLogger(CtripHotelStaticInfoService.class);

    private static final String HOTEL_DETAIL_URL = "http://openapi.ctrip.com/Hotel/OTA_HotelDescriptiveInfo.asmx";

    private static final String SOAP_PREFIX = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><Request xmlns=\"http://ctrip.com/\"><requestXML>";
    private static final String SOAP_SUFFIX = "</requestXML></Request></soap:Body></soap:Envelope>";


    private static final String ALLIANCE_ID = "22422";
    private static final String SID = "456399";
    private static final String API_KEY = "47C87868-C59F-4072-A121-AAE03B428240";


    private static AtomicLong deleteTimer = new AtomicLong();
    private static AtomicLong updateTimer = new AtomicLong();
    private static AtomicLong insertTimer = new AtomicLong();
    private static AtomicLong waitTimer = new AtomicLong();


    private AtomicInteger insertCounter = new AtomicInteger();
    private AtomicInteger updateCounter = new AtomicInteger();
    private AtomicInteger failCounter = new AtomicInteger();
    private static AtomicInteger hotelCounter = new AtomicInteger();


    @Resource
    private CtripHotelInfoService ctripHotelInfoService;
    @Resource
    private CtripHotelDao ctripHotelDao;
    @Resource
    private CtripHotelDescriptionDao ctripHotelDescriptionDao;
    @Resource
    private CtripHotelImgDao ctripHotelImgDao;
    @Resource
    private CtripHotelMessageDao ctripHotelMessageDao;
    @Resource
    private CtripHotelPriceDao ctripHotelPriceDao;
    @Resource
    private CtripHotelRoomDao ctripHotelRoomDao;
    @Resource
    private CtripHotelServiceDao ctripHotelServiceDao;


    public List<CtripHotel> getList(Long cityId, Page page) {
        Criteria<CtripHotel> criteria = new Criteria<CtripHotel>(CtripHotel.class);
        criteria.eq("hotelCityCode", cityId);
        if (page != null) {
            return ctripHotelDao.findByCriteria(criteria, page);
        } else {
            return ctripHotelDao.findByCriteria(criteria);
        }
    }

    public List<CtripHotelImg> getImgList(Long hotelId) {
        return ctripHotelImgDao.getList(hotelId);
    }

    public void insertHotel(Long cityId) {


        List<CtripHotel> oldList = getList(cityId, null);
        for (CtripHotel ctripHotel : oldList) {
            ctripHotel.setValid(false);


        }
        ctripHotelDao.updateAll(oldList);

        int pageSize = 10;
        int index = 1;
        int size = pageSize;
        while (size == pageSize && index < 11) {
            System.out.println("index=" + index);
            Page page = new Page(index, pageSize);
            index++;
            List<CtripHotelId> ctripHotelIds = ctripHotelInfoService.getList(cityId, page);

            if (ctripHotelIds.size() == 0) {
                break;
            }
            List<Long> hotelIds = new ArrayList<Long>();
            for (CtripHotelId ctripHotelId : ctripHotelIds) {
                hotelIds.add(ctripHotelId.getHotelId());
                ctripHotelDescriptionDao.delAll(ctripHotelId.getHotelId());
                ctripHotelImgDao.delAll(ctripHotelId.getHotelId());
                ctripHotelMessageDao.delAll(ctripHotelId.getHotelId());
                ctripHotelRoomDao.delAll(ctripHotelId.getHotelId());
                ctripHotelServiceDao.delAll(ctripHotelId.getHotelId());
            }

            System.out.println(hotelIds);
            String requestUrl = getDetailRequestUrl(hotelIds);
            String result = postForXML(HOTEL_DETAIL_URL, requestUrl, true);

            result = result.replace("xmlns=\"http://www.opentravel.org/OTA/2003/05\"", "");
            JAXBContext context = null;
            try {
                context = JAXBContext.newInstance(ResponseType.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ResponseType responseType = (ResponseType) unmarshaller.unmarshal(new StringReader(result));

                List<HotelDescriptiveContentType> hotelDescriptiveContentTypeList = responseType.getHotelResponse().getOTAHotelDescriptiveInfoRS().getHotelDescriptiveContents().getHotelDescriptiveContent();
                if (hotelDescriptiveContentTypeList == null || hotelDescriptiveContentTypeList.size() == 0) {
                    LOGGER.error("没有正常获取到酒店数据:" + hotelIds);
                    continue;
                }
                insertHotelList(hotelDescriptiveContentTypeList);
                size = hotelDescriptiveContentTypeList.size();

            } catch (JAXBException e) {
                LOGGER.error("没有正常获取到酒店数据: + hotelIds");
                e.printStackTrace();
            }

        }
    }


    public void insertHotelList(List<HotelDescriptiveContentType> hotelDescriptiveContentTypeList) {
        for (HotelDescriptiveContentType hotelDescriptiveContentType : hotelDescriptiveContentTypeList) {
            insertHotel(hotelDescriptiveContentType);
        }
    }

    public void insertHotel(HotelDescriptiveContentType hotelDescriptiveContentType) {
        Boolean newFlag = false;
        Long hotelId = Long.parseLong(hotelDescriptiveContentType.getHotelCode());
        CtripHotel ctripHotel = ctripHotelDao.load(hotelId);
        if (ctripHotel == null) {
            ctripHotel = new CtripHotel();
            ctripHotel.setId(Long.parseLong(hotelDescriptiveContentType.getHotelCode()));
            newFlag = true;
        }
        ctripHotel.setHotelCityCode(Long.parseLong(hotelDescriptiveContentType.getHotelCityCode()));
        ctripHotel.setHotelName(hotelDescriptiveContentType.getHotelName());
        ctripHotel.setAreaId(Integer.parseInt(hotelDescriptiveContentType.getAreaID()));

        HotelInfoType hotelInfoType = hotelDescriptiveContentType.getHotelInfo();
        ctripHotel.setAddress(hotelInfoType.getAddress().getAddressLine());
        ctripHotel.setLongitude(Double.parseDouble(hotelInfoType.getPosition().getLongitude()));
        ctripHotel.setLatitude(Double.parseDouble(hotelInfoType.getPosition().getLatitude()));
        ctripHotel.setCreatedTime(new Date());

        List<DescriptionType> descriptionTypeList = hotelDescriptiveContentType.getPolicies().getPolicy().getPolicyInfoCodes().getPolicyInfoCode().getDescription();
        arrivalAndDeparture(descriptionTypeList, ctripHotel);

        List<AwardType> awardTypeList = hotelDescriptiveContentType.getAffiliationInfo().getAwards().getAward();
        awardType(awardTypeList, ctripHotel);


        processMedia(hotelDescriptiveContentType, hotelId);


        // message
        processHotelMessage(hotelDescriptiveContentType, hotelId);


        // service
        processhotelService(hotelDescriptiveContentType, hotelId);


        // room
        processHotelRoom(hotelDescriptiveContentType, hotelId);

        ctripHotel.setValid(true);
        if (newFlag) {
            ctripHotelDao.save(ctripHotel);
        } else {
            ctripHotelDao.update(ctripHotel);
        }
        System.out.println("id=" + ctripHotel.getId());

    }

    private void processMedia(HotelDescriptiveContentType hotelDescriptiveContentType, Long hotelId) {
        List<MultimediaDescriptionType> multimediaDescriptionTypeList = hotelDescriptiveContentType.getMultimediaDescriptions().getMultimediaDescription();
        List<CtripHotelImg> ctripHotelImgList = new ArrayList<CtripHotelImg>();
        List<CtripHotelDescription> ctripHotelDescriptionList = new ArrayList<CtripHotelDescription>();
        for (MultimediaDescriptionType multimediaDescriptionType : multimediaDescriptionTypeList) {
            //TODO img des
            if (multimediaDescriptionType.getImageItems() != null) {
                List<ImageItemType> imageItemTypeList = multimediaDescriptionType.getImageItems().getImageItem();

                for (ImageItemType imageItemType : imageItemTypeList) {
                    CtripHotelImg ctripHotelImg = new CtripHotelImg();
                    ctripHotelImg.setHotelId(hotelId);
                    ctripHotelImg.setCategory(Integer.parseInt(imageItemType.getCategory()));
                    ctripHotelImg.setUrl(imageItemType.getImageFormat().getURL());
                    ctripHotelImg.setDescription(imageItemType.getDescription().getCaption());
                    ctripHotelImg.setCreatedTime(new Date());
                    ctripHotelImgList.add(ctripHotelImg);
                }
            }

            //
            if (multimediaDescriptionType.getTextItems() != null) {
                List<TextItemType> textItemTypeList = multimediaDescriptionType.getTextItems().getTextItem();

                for (TextItemType textItemType : textItemTypeList) {
                    CtripHotelDescription ctripHotelDescription = new CtripHotelDescription();
                    ctripHotelDescription.setHotelId(hotelId);
                    ctripHotelDescription.setCategory(Integer.parseInt(textItemType.getCategory()));
                    ctripHotelDescription.setDescription(textItemType.getDescription());
                    ctripHotelDescription.setCreatedTime(new Date());
                    ctripHotelDescriptionList.add(ctripHotelDescription);
                }
            }
        }
        try {
            ctripHotelImgDao.save(ctripHotelImgList);
            ctripHotelDescriptionDao.save(ctripHotelDescriptionList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
        }
    }

    private void processHotelRoom(HotelDescriptiveContentType hotelDescriptiveContentType, Long hotelId) {
        List<GuestRoomType> guestRoomTypeList = hotelDescriptiveContentType.getFacilityInfo().getGuestRooms().getGuestRoom();
        if (guestRoomTypeList != null) {
            List<CtripHotelRoom> ctripHotelRoomList = new ArrayList<CtripHotelRoom>();
            for (GuestRoomType guestRoomType : guestRoomTypeList) {
                TypeRoomType typeRoomType = guestRoomType.getTypeRoom();
                CtripHotelRoom ctripHotelRoom = new CtripHotelRoom();
                ctripHotelRoom.setHotelId(hotelId);
                ctripHotelRoom.setRoomTypeCode(Long.parseLong(typeRoomType.getRoomTypeCode()));
                ctripHotelRoom.setStandardOccupancy(Integer.parseInt(typeRoomType.getStandardOccupancy()));
                ctripHotelRoom.setSize(typeRoomType.getSize());
                ctripHotelRoom.setName(typeRoomType.getName());
                ctripHotelRoom.setFloor(typeRoomType.getFloor());
                ctripHotelRoom.setInvBlockCode(Integer.parseInt(typeRoomType.getInvBlockCode()));
                if (typeRoomType.getBedTypeCode() != null) {
                    ctripHotelRoom.setBedTypeCode(Long.parseLong(typeRoomType.getBedTypeCode()));
                }
                ctripHotelRoom.setQuantity(Integer.parseInt(typeRoomType.getQuantity()));
                ctripHotelRoom.setNonSmoking(Boolean.parseBoolean(typeRoomType.getNonSmoking()));
                ctripHotelRoom.setRoomSize(typeRoomType.getRoomSize());
                ctripHotelRoom.setHasWindow(Integer.parseInt(typeRoomType.getHasWindow()));
                ctripHotelRoom.setCreatedTime(new Date());
                ctripHotelRoomList.add(ctripHotelRoom);
            }
            ctripHotelRoomDao.save(ctripHotelRoomList);
        }
    }

    private void processhotelService(HotelDescriptiveContentType hotelDescriptiveContentType, Long hotelId) {
        List<ServiceType> serviceTypeList = hotelDescriptiveContentType.getHotelInfo().getServices().getService();
        List<CtripHotelService> ctripHotelServiceList = new ArrayList<CtripHotelService>();
        for (ServiceType serviceType : serviceTypeList) {
            CtripHotelService ctripHotelService = new CtripHotelService();
            ctripHotelService.setHotelId(hotelId);
            ctripHotelService.setServiceCode(Integer.parseInt(serviceType.getCode()));
            if (serviceType.getID() != null && !"".equals(serviceType.getID())) {
                ctripHotelService.setServiceId(Integer.parseInt(serviceType.getID()));
            }
            ctripHotelService.setDescriptiveText(serviceType.getDescriptiveText());
            ctripHotelService.setCreatedTime(new Date());
            ctripHotelServiceList.add(ctripHotelService);
        }
        ctripHotelServiceDao.save(ctripHotelServiceList);
    }

    private void processHotelMessage(HotelDescriptiveContentType hotelDescriptiveContentType, Long hotelId) {
        List<CityImportantMessageTypeType> cityImportantMessageTypeTypeList = hotelDescriptiveContentType.getTPAExtensions().getCityImportantMessage().getCityImportantMessageType();
        List<CtripHotelMessage> ctripHotelMessageList = new ArrayList<CtripHotelMessage>();
        for (CityImportantMessageTypeType cityImportantMessageTypeType : cityImportantMessageTypeTypeList) {
            CtripHotelMessage ctripHotelMessage = new CtripHotelMessage();
            ctripHotelMessage.setHotelId(hotelId);

            try {
                // 2014-05-22T00:00:00
                String unixTimeString = "1970-01-01T00:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date unixTime = sdf.parse(unixTimeString);
                if (cityImportantMessageTypeType.getStartDate() != null && !"".equals(cityImportantMessageTypeType.getStartDate())) {
                    ctripHotelMessage.setStartDate(sdf.parse(cityImportantMessageTypeType.getStartDate()));
                    if (ctripHotelMessage.getStartDate().before(unixTime)) {
                        continue;
                    }
                }
                if (cityImportantMessageTypeType.getEndDate() != null && !"".equals(cityImportantMessageTypeType.getEndDate())) {
                    ctripHotelMessage.setEndDate(sdf.parse(cityImportantMessageTypeType.getEndDate()));
                    if (ctripHotelMessage.getEndDate().before(unixTime)) {
                        continue;
                    }
                }
            } catch (Exception e) {
                LOGGER.error("时间格式化失败：" + e.getLocalizedMessage());
            }
            ctripHotelMessage.setContent(cityImportantMessageTypeType.getMessageContent());
            ctripHotelMessage.setCreatedTime(new Date());
            ctripHotelMessageList.add(ctripHotelMessage);
        }
        ctripHotelMessageDao.save(ctripHotelMessageList);
    }

    private void arrivalAndDeparture(List<DescriptionType> descriptionTypeList, CtripHotel ctripHotel) {
        //
        for (DescriptionType descriptionType : descriptionTypeList) {
            if ("ArrivalAndDeparture".equals(descriptionType.getName())) {
                ctripHotel.setArrivalAndDeparture(descriptionType.getText());
                continue;
            }
            if ("Cancel".equals(descriptionType.getName())) {
                ctripHotel.setCancel(descriptionType.getText());
                continue;
            }
            if ("DepositAndPrepaid".equals(descriptionType.getName())) {
                ctripHotel.setDepositAndPrepaid(descriptionType.getText());
                continue;
            }
            if ("Pet".equals(descriptionType.getName())) {
                ctripHotel.setPet(descriptionType.getText());
                continue;
            }
            if ("Requirements".equals(descriptionType.getName())) {
                ctripHotel.setRequirements(descriptionType.getText());
                continue;
            }
        }
    }

    private void awardType(List<AwardType> awardTypeList, CtripHotel ctripHotel) {
        for (AwardType awardType : awardTypeList) {
            if ("HotelStarLicence".equals(awardType.getProvider())) {
                ctripHotel.setHotelStarLicence(Float.parseFloat(awardType.getRating()));
            }
            if ("HotelStarRate".equals(awardType.getProvider())) {
                ctripHotel.setHotelStarRate(Float.parseFloat(awardType.getRating()));
            }
            if ("CtripStarRate".equals(awardType.getProvider())) {
                ctripHotel.setCtripStarRate(Float.parseFloat(awardType.getRating()));
            }
            if ("CtripUserRate".equals(awardType.getProvider())) {
                ctripHotel.setCtripUserRate(Float.parseFloat(awardType.getRating()));
            }
            if ("CtripCommRate".equals(awardType.getProvider())) {
                ctripHotel.setCtripCommRate(Float.parseFloat(awardType.getRating()));
            }
        }
    }


    private static String getDetailRequestUrl(List<Long> ids) {
        Long currMills = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
                .append("<Request>\n")
                .append("<Header AllianceID=\"").append(ALLIANCE_ID).append("\" SID=\"").append(SID).append("\" TimeStamp=\"").append(currMills)
                .append("\" Signature=\"").append(MD5.GetMD5Code(currMills + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "OTA_HotelDescriptiveInfo").toUpperCase())
                .append("\" RequestType=\"OTA_HotelDescriptiveInfo\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\" />")
                .append("<HotelRequest>\n")
                .append("<RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n")
                .append("<OTA_HotelDescriptiveInfoRQ Version=\"1.0\" xsi:schemaLocation=\"http://www.opentravel.org/OTA/2003/05 OTA_HotelDescriptiveInfoRQ.xsd\" xmlns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n")
                //<!--酒店详细描述信息请求列表，可以请求多个酒店-->
                .append("<HotelDescriptiveInfos>\n");

        for (Long id : ids) {
            //<!--酒店信息，HotelCode属性：酒店代码，string类型，必填 -->
            //<!-- PositionTypeCode属性：返回的酒店的坐标类型，string类型，可空，参见ListCode(PTC)，501Mapbar 坐标，502Google 坐标，默认为502-->
            builder.append("<HotelDescriptiveInfo HotelCode=\"").append(id).append("\" PositionTypeCode=\"501\">\n")
                    //<!-- SendData属性：是否请求酒店类信息；bool类型，True表示需要发送酒店类信息；可空 -->
                    .append("<HotelInfo SendData=\"true\"/>\n")
                    //<!-- SendGuestRooms属性：是否发送客房信息；bool类型，True表示需要发送；可空 -->
                    .append("<FacilityInfo SendGuestRooms=\"true\"/>\n")
                    //<!-- SendAttractions属性：是否发送景点地标信息；bool类型，True表示需要发送；可空 -->
                    //<!-- SendRecreations属性：是否发送娱乐区域信息；bool类型，True表示需要发送；可空 -->
                    .append("<AreaInfo SendAttractions=\"false\" SendRecreations=\"false\"/>\n")
                    //<!--联系方式类信息-->
                    //<!-- SendData属性：是否发送联系方式类数据，bool类型，True表示需要发送；可空 -->
                    .append("<ContactInfo SendData=\"true\"/>\n")
                    //<!--多媒体信息-->
                    //<!-- SendData属性：是否发送多媒体数据，bool类型，True表示需要发送；可空 -->
                    .append("<MultimediaObjects SendData=\"true\"/>\n").append("</HotelDescriptiveInfo>\n");
        }

        builder.append("</HotelDescriptiveInfos>\n").append("</OTA_HotelDescriptiveInfoRQ>\n").append("</RequestBody>\n").append("</HotelRequest>\n").append("</Request>\n");
        return builder.toString();
    }


    public static String postForXML(String url, String xmlData, boolean record) {
        try {
            HttpClient httpClient = HttpClientUtils.getHttpClient();

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(SOAP_PREFIX + StringEscapeUtils.escapeXml(xmlData) + SOAP_SUFFIX, ContentType.TEXT_XML));
            Clock clock = new Clock();
            HttpResponse response = httpClient.execute(httpPost);
//            if (record) {
//                postTimer.getAndAdd(clock.elapseTime());
//            }
            String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");


            resultStr = resultStr.substring(resultStr.indexOf("<RequestResult>") + 15, resultStr.indexOf("</RequestResult>"));
            resultStr = StringEscapeUtils.unescapeXml(resultStr);

//            resultStr = resultStr.replaceAll("&lt;", "<");
//            resultStr = resultStr.replaceAll("&gt;", ">");
//            resultStr = StringEscapeUtils.unescapeXml(resultStr);
//            Map<String, Object> result = XMLUtil.readStringXmlOut(resultStr);
////            if (record) {
////                parseTimer.getAndAdd(clock.elapseTime());
////            }
            return resultStr;
        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

}
