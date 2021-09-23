package com.data.data.hmly.service.nctripticket;

import com.data.data.hmly.service.ctripcommon.dao.CtripAccessTokenDao;
import com.data.data.hmly.service.ctripcommon.dao.CtripApiLogDao;
import com.data.data.hmly.service.ctripcommon.entity.CtripAccessToken;
import com.data.data.hmly.service.ctripcommon.entity.CtripApiLog;
import com.data.data.hmly.service.ctripcommon.enums.CtripTicketIcode;
import com.data.data.hmly.service.ctripcommon.enums.OrderStatus;
import com.data.data.hmly.service.nctripticket.dao.CtripOrderFormInfoDao;
import com.data.data.hmly.service.nctripticket.dao.CtripScenicSpotResourceDao;
import com.data.data.hmly.service.nctripticket.entity.*;
import com.data.data.hmly.service.nctripticket.pojo.CtripCancelOrderItemVO;
import com.data.data.hmly.service.nctripticket.pojo.CtripOrderCancelCheckVO;
import com.data.data.hmly.service.nctripticket.pojo.CtripResultStatusVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuipin.util.DateUtils;
import com.zuipin.util.MD5;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.util.JavaIdentifierTransformer;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 携程数据接口
 * Created by caiys on 2016/1/25.
 */
@Service
public class CtripTicketApiService {
    private Log log = LogFactory.getLog(this.getClass());
    public static final String AUTH_TOKEN_URL = "https://OpenServiceAuth.Ctrip.com/OpenServiceAuth/authorize.ashx";
    public static final String REFRESH_TOKEN_URL = "https://OpenServiceAuth.Ctrip.com/OpenServiceAuth/refresh.ashx";
    //public static final String OPENSERVICE_URL = "https://sopenservice.ctrip.com/OpenService/ServiceProxy.ashx";
    public static final String OPENSERVICE_URL = "http://fenxiao.zmyou.com/api/market/scenic!api.action";
    @Resource
    private CtripApiLogDao ctripApiLogDao;
    @Resource
    private CtripAccessTokenDao ctripAccessTokenDao;
    @Resource
    private CtripOrderFormInfoDao ctripOrderFormInfoDao;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private CtripScenicSpotResourceDao ctripScenicSpotResourceDao;

    /**
     * 门票订单基本信息查询
     * @param ctripOrderId
     * @param uuid
     * @return
     */
    public OrderStatus doGetOrderStatus(Long ctripOrderId, String uuid, List<CtripCancelOrderItemVO> cancelItems) throws Exception {
        JSONObject body = new JSONObject();
        body.put("action", CtripTicketIcode.ORDER_INFO.getIcode());
        body.put("no", ctripOrderId);

        JSONObject resultJson = doPostOpenService(body);
        if (resultJson == null || !"ok".equals(resultJson.get("status"))) {
            return null;
        }
        String bodyJson = resultJson.getString("body");
        JSONObject orderInfoJson = JSONObject.fromObject(bodyJson);
        // 获取订单项
        if (cancelItems != null) {
            JSONArray orderItemList = orderInfoJson.getJSONArray("orderItemList");
            for (Object orderItemInfoObject : orderItemList) {
                JSONObject orderItemInfoJson = (JSONObject) orderItemInfoObject;
                JSONArray orderItemCodeList = orderItemInfoJson.getJSONArray("orderItemCodeList");
                for (Object orderItemCodeObject : orderItemCodeList) {
                    JSONObject orderItemCode = (JSONObject) orderItemCodeObject;
                    CtripCancelOrderItemVO cancelOrderItemVO = new CtripCancelOrderItemVO();
                    cancelOrderItemVO.setOrderItemId(orderItemCode.getLong("id"));
                    cancelOrderItemVO.setQuantity(orderItemCode.getInt("qty"));
                    cancelItems.add(cancelOrderItemVO);
                }
            }
        }

        // 获取订单状态
        // 携程订单状态 0:已提交；1:确认中；2:已确认；3:待付款；4:已付款；5:成交 (部分退)；6:退订；7:成交；8:取消；9:取消中；10:退订中
        String status = orderInfoJson.getString("state");   // 订单状态6: 退订 7: 成交 10:退订中
        if ("REFUND".equals(status)) {
            return OrderStatus.CANCELED;
        } else if ("USED".equals(status) || "CODE_SENDED".equals(status)) {
            return OrderStatus.SUCCESS;
        } else if ("REFUNDING".equals(status)) {
            return OrderStatus.CANCELING;
        } else {
            OrderStatus.OTHER.setDescription("未知状态" + status);
            return OrderStatus.OTHER;
        }
    }

    /**
     * 门票退单
     * @param ctripOrderId
     * @param reason
     * @param cancelItems
     * @return
     */
    public boolean doOrderCancel(Long ctripOrderId, String reason, List<CtripCancelOrderItemVO> cancelItems, String uuid) throws Exception {
        boolean success = false;
        CtripResultStatusVO resultStatus = doOrderCancelWithMsg(ctripOrderId, reason, cancelItems, uuid);
        if (resultStatus != null) {
            success = resultStatus.getIsSuccess();
        }
        return success;
    }

    /**
     * 门票退单
     * @param ctripOrderId
     * @param reason
     * @param cancelItems
     * @return
     */
    public CtripResultStatusVO doOrderCancelWithMsg(Long ctripOrderId, String reason, List<CtripCancelOrderItemVO> cancelItems, String uuid) throws Exception {
        JSONObject paramJson = new JSONObject();
        paramJson.put("action", CtripTicketIcode.ORDER_CANCEL.getIcode());
        paramJson.put("no", ctripOrderId);
        JSONObject resultJson = doPostOpenService(paramJson);
        if (resultJson == null) {
            return null;
        }
        CtripResultStatusVO resultStatus = new CtripResultStatusVO();
        if ("ok".equals(resultJson.get("status"))) {
            resultStatus.setIsSuccess(true);
        } else {
            resultStatus.setIsSuccess(false);
            resultStatus.setCustomerErrorMessage(resultJson.getString("body"));
        }
        return resultStatus;
    }

    /**
     * 门票可退检查
     * @param ctripOrderId
     * @return
     */
    public CtripOrderCancelCheckVO doOrderCancelCheck(Long ctripOrderId, String uuid) throws Exception {
        JSONObject paramJson = new JSONObject();
        paramJson.put("OrderID", ctripOrderId);
        JSONObject resultJson = doPostOpenService(paramJson);
        if (resultJson == null) {
            return null;
        }
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("cancelItems", CtripCancelOrderItemVO.class);
        CtripOrderCancelCheckVO ctripOrderCancelCheckVO = (CtripOrderCancelCheckVO) JSONObject.toBean(resultJson, getJsonConfig(CtripOrderCancelCheckVO.class, classMap));
        // 只留可取消的
        if (ctripOrderCancelCheckVO.getCancelItems() != null && !ctripOrderCancelCheckVO.getCancelItems().isEmpty()) {
            List<CtripCancelOrderItemVO> cancelItems = new ArrayList<CtripCancelOrderItemVO>();
            for (CtripCancelOrderItemVO cancelItem : ctripOrderCancelCheckVO.getCancelItems()) {
                if (cancelItem.getCancelItemStatus() == 1) {
                    cancelItems.add(cancelItem);
                }
            }
            ctripOrderCancelCheckVO.setCancelItems(cancelItems);
        }
        return ctripOrderCancelCheckVO;
    }

    /**
     * 门票创建订单
     * @param ctripOrderFormInfo
     * @param ctripOrderContactInfo
     * @param ctripOrderPassengerInfos
     * @param uuid
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void doCreateOrder(CtripOrderFormInfo ctripOrderFormInfo, CtripOrderContactInfo ctripOrderContactInfo, List<CtripOrderPassengerInfo> ctripOrderPassengerInfos, String uuid) throws Exception {
//        // 订单信息（门票资源）
//        JSONObject orderFormInfoJson = JSONObject.fromObject(ctripOrderFormInfo, getJsonConfig(ctripOrderFormInfo));
        // 旅客信息列表
        JSONArray guestList = new JSONArray();
        for (CtripOrderPassengerInfo passengerInfo : ctripOrderPassengerInfos) {
            JSONObject guest = new JSONObject();
            guest.put("guestName", passengerInfo.getcName());
            guest.put("certType", "01");
            guest.put("certNo", passengerInfo.getIdCardNo());
            guestList.add(guest);
        }
        // 资源列表
        JSONArray orderItemList = new JSONArray();
        for (CtripOrderFormResourceInfo resourceInfo : ctripOrderFormInfo.getResourceInfoList()) {
            JSONObject orderItem = new JSONObject();
            orderItem.put("id", resourceInfo.getResourceId());
            orderItem.put("qty", resourceInfo.getQuantity());
            orderItem.put("salePrice", resourceInfo.getPrice());
            orderItem.put("ticketType", "PERSONAL");
            orderItem.put("startDate", DateUtils.formatShortDate(resourceInfo.getUseDate()));
            orderItem.put("guestList", guestList);
            orderItemList.add(orderItem);
        }
//        // 分销商信息
//        String allianceId = propertiesManager.getString("CTRIP_ALLIANCE_ID");   // 联盟ID
//        String sid = propertiesManager.getString("CTRIP_SID");  // SID
//        JSONObject allianceInfoJson = new JSONObject();
//        allianceInfoJson.put("AllianceID", allianceId);
//        allianceInfoJson.put("SID", sid);
//        // 订单联系人信息
//        JSONObject orderContactInfoJson = JSONObject.fromObject(ctripOrderContactInfo, getJsonConfig(ctripOrderContactInfo));
//        // 用户信息
//        JSONObject userInfoJson = new JSONObject();
//        userInfoJson.put("Uid", ctripOrderFormInfo.getUid());

        // 创建订单参数
        JSONObject paramJson = new JSONObject();
        paramJson.put("action", CtripTicketIcode.CREATE_ORDER.getIcode());
        paramJson.put("outOrderNo", ctripOrderFormInfo.getDistributorOrderId());
        paramJson.put("buyer", ctripOrderContactInfo.getName());
        paramJson.put("mobile", ctripOrderContactInfo.getMobile());
        paramJson.put("idCardNo", ctripOrderPassengerInfos.get(0).getIdCardNo());
        paramJson.put("salePrice", ctripOrderFormInfo.getAmount());
        paramJson.put("orderItemList", orderItemList);
        JSONObject resultJson = doPostOpenService(paramJson);
        if (resultJson != null && "ok".equals(resultJson.get("status"))) {   // 成功返回
            String body = resultJson.get("body").toString();
            JSONObject bodyJson = JSONObject.fromObject(body);
            Long orderId = Long.valueOf(bodyJson.get("no").toString());
            // 回写成功状态和携程订单ID
            ctripOrderFormInfo.setOrderStatus(OrderStatus.SUCCESS);
            ctripOrderFormInfo.setUpdateTime(new Date());
            ctripOrderFormInfo.setCtripOrderId(orderId);
            ctripOrderFormInfoDao.update(ctripOrderFormInfo);
        } else {
            // 回写失败状态
            updateOrderFail(ctripOrderFormInfo);
        }
    }

    /**
     * 门票可订性检查
     * @param resourceInfoList
     * @param ctripOrderContactInfo
     * @param ctripOrderPassengerInfos
     * @param uuid
     */
    public boolean doOrderBookingCheck(List<CtripOrderFormResourceInfo> resourceInfoList, CtripOrderContactInfo ctripOrderContactInfo, List<CtripOrderPassengerInfo> ctripOrderPassengerInfos, String uuid) throws Exception {
        boolean success = false;
        // 资源列表
        float amount = 0.0f;
        JSONArray resourceInfoJsons = new JSONArray();
        for (CtripOrderFormResourceInfo resourceInfo : resourceInfoList) {
            JSONObject resourceInfoJson = JSONObject.fromObject(resourceInfo, getJsonConfig(resourceInfo));
            resourceInfoJsons.add(resourceInfoJson);
            amount = amount + resourceInfo.getPrice() * resourceInfo.getQuantity();
        }
        // 订单联系人信息
        JSONObject orderContactInfoJson = JSONObject.fromObject(ctripOrderContactInfo, getJsonConfig(ctripOrderContactInfo));
        // 旅客信息列表
        JSONArray passengerInfoJsons = new JSONArray();
        for (CtripOrderPassengerInfo passengerInfo : ctripOrderPassengerInfos) {
            JSONObject passengerInfoJson = JSONObject.fromObject(passengerInfo, getJsonConfig(passengerInfo));
            passengerInfoJsons.add(passengerInfoJson);
        }

        // 创建订单参数
        JSONObject paramJson = new JSONObject();
        paramJson.put("ResourceInfoList", resourceInfoJsons);
        paramJson.put("UID", propertiesManager.getString("CTRIP_UID"));
        paramJson.put("ContactInfo", orderContactInfoJson);
        paramJson.put("PassenerInfoList", passengerInfoJsons);
        paramJson.put("Amount", amount);
        paramJson.put("PayMode", "P");
        paramJson.put("DistributionChannelID", 9);
        JSONObject resultJson = doPostOpenService(paramJson);
        if (resultJson == null) {
            return success;
        }
        // 检查是否执行成功，如果是，同时更新订单状态
//        CtripApiLog ctripApiLog = ctripApiLogDao.findUniqueBy(uuid, CtripTicketIcode.ORDER_CANCEL.toString());
        JSONObject resultStatusType = resultJson.getJSONObject("ResultStatus");
        if (resultStatusType != null && !resultStatusType.isEmpty()) { // 可能情况返回结果：{"ErrCode":232,"ErrMsg":"ACCESS_DENIED"}
            CtripResultStatusVO resultStatus = (CtripResultStatusVO) JSONObject.toBean(resultStatusType, getJsonConfig(CtripResultStatusVO.class, null));
            success = resultStatus.getIsSuccess();
        }
        return success;
    }

    /**
     * 回写失败状态
     * @param ctripOrderFormInfo
     */
    public void updateOrderFail(CtripOrderFormInfo ctripOrderFormInfo) {
        ctripOrderFormInfo.setOrderStatus(OrderStatus.FAIL);
        ctripOrderFormInfo.setUpdateTime(new Date());
        ctripOrderFormInfoDao.update(ctripOrderFormInfo);
    }

    /**
     * 门票价格日历
     * @param resourceIDList    资源ID列表
     * @param startDate 起始时间
     * @param endDate   结束时间
     */
    public List<CtripResourcePriceCalendar> doGetResourcePriceCalendar(List<Long> resourceIDList, Date startDate, Date endDate) throws Exception {
        JSONObject paramJson = new JSONObject();
//        List<CtripResourcePriceCalendar> calendarlists = new ArrayList<CtripResourcePriceCalendar>();
//        Integer total = resourceIDList.size();
//        Integer pageSize = 200;  // 最大支持20
//        Integer index = 0;
//        while (index < total) {
//            Integer toIndex = index + pageSize;
//            if (toIndex > total) {  // 判断是否超过总数
//                toIndex = total;
//            }
//            List<Long> subScenicSpotIdList = resourceIDList.subList(index, toIndex);
        paramJson.put("idList", resourceIDList);
        paramJson.put("startDate", DateUtils.format(startDate, "yyyy-MM-dd"));
        paramJson.put("endDate", DateUtils.format(endDate, "yyyy-MM-dd"));
        paramJson.put("action", CtripTicketIcode.TICKET_PRICE_CALENDAR.getIcode());
        JSONObject resultJson = doPostOpenService(paramJson);
        List<CtripResourcePriceCalendar> calendarlist = new ArrayList<CtripResourcePriceCalendar>();
        if (resultJson == null) {
            return calendarlist;
        }
        String body = resultJson.get("body").toString();
        JSONObject bodyJson = JSONObject.fromObject(body);
        for (Object id : bodyJson.keySet()) {
            JSONObject ticket = bodyJson.getJSONObject(id.toString());

            String priceStr = ticket.get("datePriceLs").toString();
            JSONArray priceList = JSONArray.fromObject(priceStr);

            for (int j = 0; j < priceList.size(); j++) {
                JSONObject price = priceList.getJSONObject(j);
                CtripResourcePriceCalendar calendar = new CtripResourcePriceCalendar();
                calendar.setResourceId(Long.valueOf(ticket.get("id").toString()));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                calendar.setPriceDate(sdf.parse(price.get("priceDate").toString()));
                calendar.setMarketPrice(Double.valueOf(price.get("marketPrice").toString()));
                calendar.setPrice(Double.valueOf(price.get("salePrice").toString()));
                calendar.setCtripPrice(Double.valueOf(price.get("suggestPrice").toString()));
                calendarlist.add(calendar);

            }
            //  }
        }
        return calendarlist;
    }
        /*JSONArray resourceArray = resultJson.getJSONArray("ResourceList");
        if (resourceArray != null && !resourceArray.isEmpty())
        for (Object resourceObj : resourceArray) {
            JSONObject resourceJson = JSONObject.fromObject(resourceObj);
            Long rescourceId = resourceJson.getLong("ResourceID");
            JSONArray dateList = resourceJson.getJSONArray("DateList");
            if (dateList != null && !dateList.isEmpty()) {
                List<CtripResourcePriceCalendar> resourcePriceCalendars = new ArrayList<CtripResourcePriceCalendar>();
                for (Object rpcObject : dateList) {
                    JSONObject rpcJson = (JSONObject) rpcObject;
                    CtripResourcePriceCalendar resourcePriceCalendar = (CtripResourcePriceCalendar) JSONObject.toBean(rpcJson, getJsonConfig(CtripResourcePriceCalendar.class, null));
                    resourcePriceCalendar.setCtripResourceId(rescourceId);
                    resourcePriceCalendars.add(resourcePriceCalendar);
                }
                resourcesPriceCalendars.addAll(resourcePriceCalendars);
            }
        }
        return resourcesPriceCalendars;
    }*/

    /**
     * 门票景点详情（门票资源）
     *
     */
    public List<CtripScenicSpotInfo> doGetScenicSpotInfo() throws Exception {
        /*JSONObject paramJson = new JSONObject();
        paramJson.put("ScenicSpotIDList", scenicSpotIDList);
        paramJson.put("DistributionChannelID", 9);
        paramJson.put("ResponseDataType", 0);   // 0：返回所有信息 1：返回景点+产品信息（排除非主产品Addinfo信息）+可选项信息（排除掉可选项时效信息） 2：只返回景点信息
        paramJson.put("ImageSizeKey", "C_500_280_Q90");*/
        List<CtripScenicSpotInfo> infoList = new ArrayList<CtripScenicSpotInfo>();
        JSONObject paramJson = new JSONObject();

        Boolean flag = true;
        int num = 1;
        while (flag) {
            paramJson.put("currentPageNum", num);
            paramJson.put("pageSize", 50);
            paramJson.put("cityId", 350200);
            num++;
            paramJson.put("action", CtripTicketIcode.SCENICSPOT_INFO.getIcode());
            JSONObject resultJson = doPostOpenService(paramJson);
            String body = resultJson.get("body").toString();
            JSONObject bodyJson = JSONObject.fromObject(body);
            JSONArray scenicSpotInfoArray = bodyJson.getJSONArray("pageData");
            if (scenicSpotInfoArray.size() == 0) {
                flag = false;
            }
            if (resultJson == null) {
                return infoList;
            }
            //String pageInfo = bodyJson.get("pageInfo").toString();
            for (int i = 0; i < scenicSpotInfoArray.size(); i++) {
                JSONObject object = scenicSpotInfoArray.getJSONObject(i);
                CtripScenicSpotInfo info = new CtripScenicSpotInfo();
                List<CtripScenicSpotResource> resources = new ArrayList<CtripScenicSpotResource>();
                JSONArray ticketInfoArray = object.get("ticketList") == null ? new JSONArray() : object.getJSONArray("ticketList");
                info.setIsCanBooking(false);
                for (int j = 0; j < ticketInfoArray.size(); j++) {
                    JSONObject ticketObject = ticketInfoArray.getJSONObject(j);
                    Boolean status = "1".equals(ticketObject.get("status").toString());
                    info.setIsCanBooking(info.getIsCanBooking() || status);
                    CtripScenicSpotResource resource = new CtripScenicSpotResource();
                    resource.setProductId(Long.valueOf(object.get("id").toString()));
                    resource.setTicketType(2);
                    resource.setCtripResourceId(Long.valueOf(ticketObject.get("id").toString()));
                    resource.setAdvanceBookingDays(Integer.valueOf(ticketObject.get("orderAdvanceDays").toString()));
                    resource.setAdvanceBookingTime(ticketObject.get("orderBeforeHour").toString() + ":" + ticketObject.get("orderBeforeMin").toString());
                    resource.setCtripPrice(Double.valueOf(ticketObject.get("suggestPrice").toString()));
                    resource.setIsBookingLimit(Boolean.valueOf(ticketObject.get("isInvtLimit").toString()));
                    resource.setMarketPrice(Double.valueOf(ticketObject.get("marketPrice").toString()));
                    resource.setMaxQuantity(Integer.valueOf(ticketObject.get("maxOrderNum").toString()));
                    resource.setMinQuantity(Integer.valueOf(ticketObject.get("minOrderNum").toString()));
                    resource.setName(ticketObject.get("name").toString());
                    resource.setPrice(Double.valueOf(ticketObject.get("salePrice").toString()));
                    resources.add(resource);

                }
                info.setId(Long.valueOf(object.get("id").toString()));
                info.setName(object.get("name").toString());
                info.setStar(Integer.valueOf(object.get("star").toString()));
                info.setOpenTimeDesc(object.get("openTime").toString());
                info.setAddress(object.get("addr").toString());
                info.setImageUrl(object.get("imgUrl").toString());


                info.setResourceList(resources);
                infoList.add(info);
            }
        }


        return infoList;
    }
       /* String body = resultJson.get("body").toString();
        JSONObject bodyJson = JSONObject.fromObject(body);
        JSONArray scenicSpotInfoArray = bodyJson.getJSONArray("pageData");
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("displayTagGroupList", CtripDisplayTagGroup.class);
        classMap.put("displayTagList", CtripDisplayTag.class);
        classMap.put("poiInfo", CtripScenicSpotPoiInfo.class);
        classMap.put("cityInfo", CtripScenicSpotCityInfo.class);
        classMap.put("productInfo", CtripScenicSpotProduct.class);
        classMap.put("productAddInfoList", CtripProductAddInfo.class);
        classMap.put("productAddInfoDetailList", CtripAddInfoDetail.class);
          classMap.put("ticketList", CtripScenicSpotResource.class);
        classMap.put("resourceAddInfoList", CtripResourceAddInfo.class);
        classMap.put("resourceAddInfoDetailList", CtripAddInfoDetail.class);
        if (scenicSpotInfoArray != null && !scenicSpotInfoArray.isEmpty()) {
            for (Object scenicSpotInfoObject : scenicSpotInfoArray) {
                JSONObject scenicSpotInfoJson = (JSONObject) scenicSpotInfoObject;
                CtripScenicSpotInfo ctripScenicSpotInfo = (CtripScenicSpotInfo) JSONObject.toBean(scenicSpotInfoJson, getJsonConfig(CtripScenicSpotInfo.class, classMap));
                scenicSpotInfos.add(ctripScenicSpotInfo);
            }
        }
        return scenicSpotInfos;
    }*/

    /**
     * 景点ID列表查询
     *
     * @return 返回门票景点ID列表
     * @throws Exception 请求错误时抛异常
     */
    public List<Long> doGetScenicSpotIdList() throws Exception {
        JSONObject paramJson = new JSONObject();
        List<Long> idList = new ArrayList<Long>();
        Boolean flag = true;
        int num = 1;
        while (flag) {
            paramJson.put("action", CtripTicketIcode.SCENICSPOT_ID.getIcode());
            paramJson.put("currentPageNum", num);
            paramJson.put("pageSize", 100);
            num++;
            JSONObject resultJson = doPostOpenService(paramJson);

            JSONArray pageData = JSONArray.fromObject(JSONObject.fromObject(resultJson.get("body")).get("pageData"));
         //   JSONArray pageData = JSONArray.fromObject(JSONObject.fromObject(resultJson.get("body")));
            if (pageData.size() == 0) {
                flag = false;
            }
            for (int i = 1; i < pageData.size(); i++) {
                JSONObject data = pageData.getJSONObject(i);
                idList.add(Long.valueOf(data.get("id").toString()));
            }
        }
        return idList;
    }

    /**
     * 查询最大的日期值
     * @param icode
     * @return
     */
    public Date findMaxNextTime(String icode) {
        return ctripApiLogDao.findMaxNextTime(icode);
    }

    /**
     * 业务接口通用方法，保存执行记录
     * @param body 业务接口icode
     * @param body 业务接口其他参数
     * @return  成功返回成功json，失败返回null
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public JSONObject doPostOpenService(JSONObject body) throws Exception {
        //通过key取值
//        String allianceId = propertiesManager.getString("CTRIP_ALLIANCE_ID");   // 联盟ID
//        String sid = propertiesManager.getString("CTRIP_SID");  // SID
//        String token = getToken();
//        String urlParams = "AID=" + allianceId + "&SID=" + sid + "&ICODE=" + icode.getIcode()
//                + "&UUID=" + uuid + "&Token=" + token + "&E=R3&Mode=1&Format=JSON";
//        paramJson.put("AllianceID", allianceId);    // 创建订单可以不用该项
//        paramJson.put("SID", sid);                   // 创建订单可以不用该项
       /* String userName = propertiesManager.getString("ZMY_API_USER");
        String port = propertiesManager.getString("ZMY_API_PORT");
        String key = propertiesManager.getString("ZMY_API_KEY");*/
//        String u = "api_test";
//        String p = "c4ca4238a0b923820dcc509a6f75849b";
//        String md5key = "ot7M30XwoGL35IOl";
        String u = propertiesManager.getString("JISAN_U");
        String p = propertiesManager.getString("JISAN_P");
        String md5key = propertiesManager.getString("JISAN_MD5KEY");
        String paramStr = body.toString();
        String sign = MD5Util.MD5Encode(u + p + paramStr + md5key, "utf-8");
        String urlParams = "u=" + u + "&p=" + p + "&body=" + body + "&sign=" + sign ;

        String result = null;
        JSONObject resultJson = null;
        String errMsg = null;
        try {
            result = post(OPENSERVICE_URL + "?" + urlParams, null);
        } catch (Exception e) {
            e.printStackTrace();
            errMsg = StringUtils.substring(e.getMessage(), 0, 200);
        }
        // 保存执行结果
        CtripApiLog ctripApiLog = new CtripApiLog();
        if (result != null) {
            resultJson = JSONObject.fromObject(result);
            /*JSONObject resultStatusType = resultJson.getJSONObject("ResultStatus");
            CtripResultStatusVO resultStatus = null;*/
            /*if (resultStatusType == null || resultStatusType.isEmpty()) { // 可能情况返回结果：{"ErrCode":232,"ErrMsg":"ACCESS_DENIED"}
                resultStatus = (CtripResultStatusVO) JSONObject.toBean(resultJson, getJsonConfig(CtripResultStatusVO.class, null));
                resultStatus.setIsSuccess(StringUtils.isNotBlank(resultStatus.getErrorCode()));
                ctripApiLog.setErrorCode(resultStatus.getErrCode());
                ctripApiLog.setErrorMessage(resultStatus.getErrMsg());
            } else {
                resultStatus = (CtripResultStatusVO) JSONObject.toBean(resultStatusType, getJsonConfig(CtripResultStatusVO.class, null));
                ctripApiLog.setErrorCode(resultStatus.getErrorCode());
                ctripApiLog.setErrorMessage(resultStatus.getErrorMessage());
            }*/
           /* ctripApiLog.setSuccess(resultStatus.getIsSuccess());
            if (!resultStatus.getIsSuccess()) {
                log.error(resultStatus.getErrorCode() + "-" + resultStatus.getErrorMessage());
                resultJson = null;
            } else if (true) {  // 格式：2016-2-3 18:19:36
     //       } else if (icode == CtripTicketIcode.SCENICSPOT_ID) {  // 格式：2016-2-3 18:19:36
                String nextTimeStr = (String) resultJson.get("NextTime");
                ctripApiLog.setNextTime(string2Date(nextTimeStr));
            }*/
        } else {
            resultJson = null;
            ctripApiLog.setSuccess(false);
            ctripApiLog.setErrorMessage(errMsg);
        }
        /*ctripApiLog.setIcode(icode.getIcode());
        ctripApiLog.setDescription(icode.getDescription());
        ctripApiLog.setExecTime(new Date());
        ctripApiLog.setParamJson(StringUtils.substring(paramJson.toString(), 0, 512));
        ctripApiLog.setUuid(uuid);
        setHandleIds(ctripApiLog, icode, paramJson);*/
//        ctripApiLogDao.save(ctripApiLog);
        return resultJson;
    }

    /**
     * 设置处理参数标识
     * @param ctripApiLog
     * @param icode
     * @param paramJson
     */
    public void setHandleIds(CtripApiLog ctripApiLog, CtripTicketIcode icode, JSONObject paramJson) {
        if (icode == CtripTicketIcode.SCENICSPOT_INFO) {
            String scenicSpotIdsString = paramJson.getJSONArray("ScenicSpotIDList").toString();
            ctripApiLog.setHandleIds(scenicSpotIdsString.substring(1, scenicSpotIdsString.length() - 1));
        } else if (icode == CtripTicketIcode.TICKET_PRICE_CALENDAR) {
            String resourceIdsString = paramJson.getJSONArray("ResourceIDList").toString();
            ctripApiLog.setHandleIds(resourceIdsString.substring(1, resourceIdsString.length() - 1));
        } else if (icode == CtripTicketIcode.CREATE_ORDER) {
            JSONObject orderFormInfoJson = paramJson.getJSONObject("OrderFormInfo");
            Long distributorOrderId = orderFormInfoJson.getLong("Distributor_OrderID");
            ctripApiLog.setHandleIds(distributorOrderId.toString());
        } else if (icode == CtripTicketIcode.ORDER_CANCEL_CHECK || icode == CtripTicketIcode.ORDER_CANCEL || icode == CtripTicketIcode.ORDER_INFO) {
            Long ctripOrderId = paramJson.getLong("OrderID");
            ctripApiLog.setHandleIds(ctripOrderId.toString());
        }
    }

    /**
     * 获取token，
     * 接口返回数据格式：{"AID":303441,"SID":776193,"Access_Token":"f069ef4d2641408cad45abf06ef6e5c3","Expires_In":600,"Refresh_Token":"259a41e3d5114ec0b85683755cc39eba"}
     * @return  返回可用的token
     * @throws Exception    请求错误时抛异常
     */
    public String getToken() throws Exception {
        String allianceId = propertiesManager.getString("CTRIP_ALLIANCE_ID");   // 联盟ID
        String sid = propertiesManager.getString("CTRIP_SID");  // SID
        String apiKey = propertiesManager.getString("CTRIP_KEY");   // APIKEY

        CtripAccessToken ctripAccessToken = ctripAccessTokenDao.findUniqueBy(Integer.valueOf(allianceId), Integer.valueOf(sid));
        // 检查token是否已经过期
        Date nowDate = new Date();
        if (ctripAccessToken != null && ctripAccessToken.getExpireDate().after(nowDate)) {
            return ctripAccessToken.getAccessToken();
        }

        // 未缓存token或者token已失效，重新获取
        String paramStr = "AID=" + allianceId + "&SID=" + sid + "&KEY=" + apiKey;
        String result = post(AUTH_TOKEN_URL + "?" + paramStr, null);
        JSONObject jsonObject = JSONObject.fromObject(result);
        String errcode = (String) jsonObject.get("errcode");
        if (StringUtils.isNotBlank(errcode)) {
            String errmsg = (String) jsonObject.get("errmsg");
            throw new Exception(errcode + "-" + errmsg);
        }
        if (ctripAccessToken != null) { // 更新
            ctripAccessToken.setAccessToken((String) jsonObject.get("Access_Token"));
            long expireDate = nowDate.getTime() + (Integer) jsonObject.get("Expires_In") * 1000;
            ctripAccessToken.setExpireDate(new Date(expireDate));
            ctripAccessToken.setRefreshToken((String) jsonObject.get("Refresh_Token"));
            ctripAccessToken.setExecTime(nowDate);
            ctripAccessTokenDao.update(ctripAccessToken);
        } else {
            ctripAccessToken = new CtripAccessToken();
            ctripAccessToken.setAid(Integer.valueOf(allianceId));
            ctripAccessToken.setSid(Integer.valueOf(sid));
            ctripAccessToken.setAccessToken((String) jsonObject.get("Access_Token"));
            long expireDate = nowDate.getTime() + (Integer) jsonObject.get("Expires_In") * 1000;
            ctripAccessToken.setExpireDate(new Date(expireDate));
            ctripAccessToken.setRefreshToken((String) jsonObject.get("Refresh_Token"));
            ctripAccessToken.setExecTime(nowDate);
            ctripAccessTokenDao.save(ctripAccessToken);
        }
        return (String) jsonObject.get("Access_Token");
    }

    /**
     * 发送请求方法
     * @param url       可带参数url
     * @param paramStr  其他参数
     * @return          请求结果
     */
    public String post(String url, String paramStr) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setRequestMethod("POST");
            if (StringUtils.isNotBlank(paramStr)) {
                conn.setRequestProperty("Content-Type", "json");
            }
//            conn.setHostnameVerifier(new Verifier());
            conn.setDoOutput(true); // 是否输入参数
            conn.setDoInput(true);
//            log.info("请求url：" + url);
            log.info("请求体：" + paramStr);
            if (StringUtils.isNotBlank(paramStr)) {
                byte[] bytes = paramStr.getBytes("UTF-8");
                conn.getOutputStream().write(bytes);    // 输入参数
            }
            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            log.info("返回结果：" + sb.toString());
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    public String postHttp(String url, String paramStr) throws UnsupportedEncodingException {
//        String postUrl = url + "?RequestJson=" + paramStr;
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpRequst = new HttpPost(url);
        List nameValuePairs = new ArrayList();
        JSONObject jsonObject = JSONObject.fromObject(paramStr);
        Set set = jsonObject.keySet();
        Iterator its = set.iterator();
        while (its.hasNext()) {
            String key = (String) its.next();
            nameValuePairs.add(new BasicNameValuePair(key, (String) jsonObject.get(key)));
        }
        httpRequst.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        try {
            //执行请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpRequst);
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //判断响应实体是否为空
            if (entity != null) {
                String resultStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                log.info("返回结果：" + resultStr);
                return resultStr;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * json字符串转javabean
     * 转json时默认首字符转小写，因为json不支持参数名首字母大写
     * @return 首字母小写的JsonConfig
     */
    public JsonConfig getJsonConfig(Class clazz, Map clazzMap) {
        JsonConfig config = new JsonConfig();
        config.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {
            @Override
            public String transformToJavaIdentifier(String str) {
                return convertStr(str);
            }
        });
        // 忽略javabean缺少的属性的json值，否则会抛Unknown property异常
        config.setJavaPropertyFilter(new PropertyFilter() {
            public boolean apply(Object paramObject1, String paramString, Object paramObject2) {
                String property = convertStr(paramString);
                Field[] fields = paramObject1.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().equals(property)) {
                        return false;
                    }
                }
                return true;
            }
        });
        config.setRootClass(clazz);
        if (clazzMap != null) {
            config.setClassMap(clazzMap);
        }
        return config;
    }

    /**
     * 将字符串转为驼峰格式字符串
     * @param str
     * @return
     */
    public String convertStr(String str) {
        Pattern p = Pattern.compile("[A-Z]{2,}");
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String g = m.group();
            g = g.toLowerCase();
            String firstLetter = g.substring(0, 1).toUpperCase();   // 第一个字母大写
            g = firstLetter + g.substring(1);
            if (m.end() != str.length()) { // 如果不是结尾子串，最后一个字母也转为大写，表示第二个单词首字母
                String lastLetter = g.substring(g.length() - 1).toUpperCase();
                g = g.substring(0, g.length() - 1) + lastLetter;
            }
            m.appendReplacement(sb, g);
        }
        m.appendTail(sb);
        String firstLetter = sb.substring(0, 1).toLowerCase();
        sb.replace(0, 1, firstLetter);
        return sb.toString();
    }

    /**
     * javabean转json
     * 在javabean get方法中用注解@JsonProperty("Distributor_OrderID")设置别名
     * @param obj
     * @return
     */
    public JsonConfig getJsonConfig(final Object obj) {
        JsonConfig config = new JsonConfig();
        config.registerJsonBeanProcessor(obj.getClass(), new JsonBeanProcessor() {
            @Override
            public JSONObject processBean(Object o, JsonConfig jsonConfig) {
                try {
                    return getJsonObject(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        return config;
    }

    public JSONObject getJsonObject(final Object obj) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(JsonProperty.class);
            if (annotation != null) {
                Method m = annotation.getClass().getDeclaredMethod("value");
                String anonValue = (String) m.invoke(annotation);
                Object propValue = method.invoke(obj);
                jsonObject.element(anonValue, propValue);
            }
        }
        return jsonObject;
    }

    /**
     * 字符串转为日期类型,格式：2016-2-3 18:19:36
     * @param dateStr
     * @return
     */
    public Date string2Date(String dateStr) {
        String[] dateTimeArray = dateStr.split(" ");
        String[] dateArray = dateTimeArray[0].split("-");
        String[] timeArray = dateTimeArray[1].split(":");
        return DateUtils.date(Integer.valueOf(dateArray[0]), Integer.valueOf(dateArray[1]) - 1, Integer.valueOf(dateArray[2]),
                Integer.valueOf(timeArray[0]), Integer.valueOf(timeArray[1]), Integer.valueOf(timeArray[2]));
    }

}
