package com.data.data.hmly.service.elong;

import com.alibaba.fastjson.JSON;
import com.data.data.hmly.service.elong.pojo.CancelOrderCondition;
import com.data.data.hmly.service.elong.pojo.CreateOrderCondition;
import com.data.data.hmly.service.elong.pojo.CreateOrderResult;
import com.data.data.hmly.service.elong.pojo.CreditCardValidateResult;
import com.data.data.hmly.service.elong.pojo.EnumLocal;
import com.data.data.hmly.service.elong.pojo.HotelDetailCondition;
import com.data.data.hmly.service.elong.pojo.HotelIDListCondition;
import com.data.data.hmly.service.elong.pojo.HotelIdListResult;
import com.data.data.hmly.service.elong.pojo.HotelListCondition;
import com.data.data.hmly.service.elong.pojo.OrderIdCondition;
import com.data.data.hmly.service.elong.pojo.Reviews;
import com.data.data.hmly.service.elong.pojo.ValidateCreditCardCondition;
import com.data.data.hmly.service.elong.pojo.statics.hotelDetail.HotelType;
import com.data.data.hmly.service.elong.service.result.HotelDetail;
import com.data.data.hmly.service.elong.service.result.HotelListResult;
import com.data.data.hmly.service.elong.service.result.HotelOrderCancelResult;
import com.data.data.hmly.service.elong.service.result.HotelOrderDetailResult;
import com.data.data.hmly.service.elong.util.Http;
import com.data.data.hmly.service.elong.util.Tool;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.data.data.hmly.service.elong.service.result.HotelDetail;

/**
 * Created by Sane on 16/1/27.
 */
@Service
public class ElongHotelService {

    //    private String StaticsHotelListApi = "/Users/Sane/Downloads/hotellist.xml";
    private String staticsHotelListApi = "http://api.elong.com/xml/v2.0/hotel/hotellist.xml";
    private String staticsHotelDetailApi = "http://api.elong.com/xml/v2.0/hotel/cn/%s/%s.xml";
    private String commentListApi = "http://m.elong.com/hotel/api/morereview/?hotelid=%d&commenttype=1&pagesize=20&pageindex=%d";
    @Resource
    private PropertiesManager propertiesManager;
    private static String appUser = null;
    protected static String appKey = null;
    private static String appSecret = null;
    private static double version = 1.1;
    private static EnumLocal locale = EnumLocal.zh_CN;
    private static String serverHost = null;
    private final Log log = LogFactory.getLog(ElongHotelService.class);
    private final Pattern hotelIdPattern = Pattern.compile("HotelId=\"(\\d+)\"");

    public void init() {
        appUser = propertiesManager.getString("ELONG_APP_USER");
        appKey = propertiesManager.getString("ELONG_APP_KEY");
        appSecret = propertiesManager.getString("ELONG_APP_SECRET");
        serverHost = propertiesManager.getString("ELONG_SERVER");
        version = 1.10;
        if ("en_US".equals(propertiesManager.getString("ELONG_LOCALE"))) {
            locale = EnumLocal.en_US;
        }
    }

    /**
     * 获取艺龙开发接口的静态数据:酒店列表
     * 可以每天凌晨三点获取lastTime之后的数据,更新数据库(增量)
     * 并且每周执行一次全部更新(全量)
     *
     * @param lastTime    上次获取的时间
     * @param incremental 是否是增量(根据上次获取时间)
     * @return 酒店id列表(静态, 包括了更新的内容 更新时间等)
     */
    public Map<String, String> getStaticsHotelList(long lastTime, boolean incremental) {
        init();
        Map<String, String> hotelIds = requestForHotelIds(lastTime, incremental);
        return hotelIds;
    }

    private Map<String, String> requestForHotelIds(long lastTime, boolean incremental) {
        Map<String, String> hotelIds = new HashMap<String, String>();
        if (staticsHotelListApi.startsWith("http")) {
            try {
                URL url = new URL(staticsHotelListApi);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String line;
                for (line = br.readLine(); line != null; line = br.readLine()) {
                    if (line.contains("HotelId")) {
                        if (incremental) {
                            if (judgeTime(lastTime, line)) break;
                        }
                        hotelIds.put(getHotelId(line), line);
                    }
                }
                con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            File file = new File(staticsHotelListApi);
            if (file.exists()) {
                try {
                    FileReader fr = new FileReader(file.getAbsoluteFile());
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                        if (line.contains("HotelId")) {
                            if (incremental && judgeTime(lastTime, line)) {
                                break;
                            }
                            hotelIds.put(getHotelId(line), line);
                        }
                    }
                    br.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return hotelIds;
    }

    private boolean judgeTime(long lastTime, String line) throws ParseException {
        Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(line.substring(43, 63));
        if (time.getTime() - lastTime <= 0) {
            return true;
        }
        System.err.println(time + "\t" + line);
        return false;
    }

    private String getHotelId(String line) {
        final Matcher m = hotelIdPattern.matcher(line);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }


    /**
     * 获取艺龙开发接口的静态数据:酒店详情
     * xml格式
     *
     * @param hotelId 酒店id
     * @return 酒店详情(静态)
     */
    public HotelType getStaticsHotelDetail(String hotelId) {
        init();
        String url = String.format(staticsHotelDetailApi, hotelId.substring(hotelId.length() - 2, hotelId.length()), hotelId);
        String xml = Http.HttpSend("GET", url, "");
        if (StringUtils.isBlank(xml))
            return null;
        xml = xml.trim().replaceAll("0001-01-01T00:00:00", "2001-01-01T00:00:00");
        HotelType hotel = null;
        try {
            JAXBContext orderResponseContext = JAXBContext.newInstance(HotelType.class);
            Unmarshaller unmarshaller = orderResponseContext.createUnmarshaller();
            hotel = (HotelType) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            log.error("Parse response's xml error. hotelId=" + hotelId);
            e.printStackTrace();
        }
        return hotel;
    }


    /**
     * @param hotelIdListCondition
     * @return 酒店ID列表(动态)
     */
    public HotelIdListResult getHotelIdList(HotelIDListCondition hotelIdListCondition) {
        init();
        BaseRequst<HotelIDListCondition> req = new BaseRequst<HotelIDListCondition>(version, locale, hotelIdListCondition);
        String responseData = getResponse(toJson(req), "hotel.id.list", false);

        System.out.println(responseData);
        return new Gson().fromJson(responseData, HotelIdListResult.class);
    }

    /**
     * @param hotelListCondition
     * @return 酒店列表(动态)
     */
    public HotelListResult getHotelList(HotelListCondition hotelListCondition) {
        init();
        BaseRequst<HotelListCondition> req = new BaseRequst<HotelListCondition>(version, locale, hotelListCondition);
        String responseData = getResponse(toJson(req), "hotel.list", false);
        return new Gson().fromJson(responseData, HotelListResult.class);
    }


    /**
     * 获取酒店
     *
     * @param hotelId 酒店ID
     * @return
     */
    public HotelDetail getHotelDetail(long hotelId) {
        init();
        Date date = new Date();
        date = DateUtils.getDate(DateUtils.format(date, "yyyyMMdd"), "yyyyMMdd");
        Date arriveDate = Tool.addDate(date, 1);
        Date departureDate = Tool.addDate(arriveDate, 30);  // 加载30天数据
        HotelDetailCondition hotelDetailCondition = new HotelDetailCondition();
        hotelDetailCondition.setArrivalDate(arriveDate);
        hotelDetailCondition.setDepartureDate(departureDate);
        hotelDetailCondition.setHotelIds(String.valueOf(hotelId));
        hotelDetailCondition.setOptions("2");
        return getHotelDetail(hotelDetailCondition);
    }

    public HotelDetail getHotelDetail(long hotelId, Date arriveDate, Date departureDate) {
        init();
        HotelDetailCondition hotelDetailCondition = new HotelDetailCondition();
        hotelDetailCondition.setArrivalDate(arriveDate);
        hotelDetailCondition.setDepartureDate(departureDate);
        hotelDetailCondition.setHotelIds(String.valueOf(hotelId));
        hotelDetailCondition.setOptions("2");
        return getHotelDetail(hotelDetailCondition);
    }


    /**
     * @param arriveDate       入住日期
     * @param departureDate    离店日期
     * @param hotelId          酒店ID
     * @param checkInPersonNum 房间入住人数
     * @param detailOptions    可调用getDetailOptions获取
     * @return
     */
    public HotelDetail getHotelDetail(Date arriveDate, Date departureDate, long hotelId, Integer checkInPersonNum, String detailOptions, String roomId, Integer ratePlanId) {
        init();
        HotelDetailCondition hotelDetailCondition = new HotelDetailCondition();
        hotelDetailCondition.setArrivalDate(arriveDate);
        hotelDetailCondition.setDepartureDate(departureDate);
        hotelDetailCondition.setHotelIds(String.valueOf(hotelId));
        hotelDetailCondition.setCheckInPersonAmount(checkInPersonNum);
        if (ratePlanId != null)
            hotelDetailCondition.setRatePlanId(ratePlanId);
        if (roomId != null)
            hotelDetailCondition.setRoomTypeId(roomId);
        hotelDetailCondition.setOptions(detailOptions);
        return getHotelDetail(hotelDetailCondition);
    }

    /**
     * 获取详情时,options的值,如果不需要则不必调用
     *
     * @param needDetail 需要返回详情 名称 地址 电话等
     * @param needRooms  需要返回房型
     * @param needImages 需要返回图片
     * @return
     */
    public String getDetailOptions(boolean needDetail, boolean needRooms, boolean needImages) {
        String options = "";
        if (needDetail) {
            options = addDetailOption(options, "1");
        }
        if (needRooms) {
            options = addDetailOption(options, "2");
        }
        if (needImages) {
            options = addDetailOption(options, "3");
        }
        return options;
    }

    private String addDetailOption(String options, String option) {
        if (options == null)
            options = "";
        options += option;
        return options;
    }

    /**
     * 获取酒店详情
     *
     * @param hotelDetailCondition
     * @return
     */
    public HotelDetail getHotelDetail(HotelDetailCondition hotelDetailCondition) {
        init();
        BaseRequst<HotelDetailCondition> req = new BaseRequst<HotelDetailCondition>(version, locale, hotelDetailCondition);
        String responseData = getResponse(toJson(req), "hotel.detail", false);

        Gson gson = new GsonBuilder()
                .serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")//时间转化为特定格式
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.
                .setPrettyPrinting() //对json结果格式化.
                .create();
        return gson.fromJson(responseData, HotelDetail.class);
    }


    /**
     * 创建订单
     *
     * @param createOrderCondition
     * @return
     */
    public CreateOrderResult createOrder(CreateOrderCondition createOrderCondition) {
        init();
        BaseRequst<CreateOrderCondition> req = new BaseRequst<CreateOrderCondition>(version, locale, createOrderCondition);
        String responseData = getResponse(toJson(req), "hotel.order.create", true);
        return new Gson().fromJson(responseData, CreateOrderResult.class);
    }


    /**
     * 验证信用卡是否可用
     *
     * @param creditCardNo 信用卡号
     * @return "IsValid":信用卡是否可用,"IsNeedVerifyCode":是否需要信用卡的cvv码
     */
    public CreditCardValidateResult validateCreditCart(String creditCardNo) {
        init();
        ValidateCreditCardCondition validateCreditCardCondition = new ValidateCreditCardCondition();
        long ts = System.currentTimeMillis() / 1000;
        String key = appKey.substring(appKey.length() - 8, appKey.length());
        String cardNo = null;
        try {
            cardNo = Tool.encryptDES(ts + "#" + creditCardNo, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        validateCreditCardCondition.setCreditCardNo(cardNo);
        return validateCreditCart(validateCreditCardCondition);
    }

    /**
     * 验证信用卡是否可用
     *
     * @param validateCreditCardCondition
     * @return "IsValid":信用卡是否可用,"IsNeedVerifyCode":是否需要信用卡的cvv码
     */
    public CreditCardValidateResult validateCreditCart(ValidateCreditCardCondition validateCreditCardCondition) {
        init();
        BaseRequst<ValidateCreditCardCondition> req = new BaseRequst<ValidateCreditCardCondition>(version, locale, validateCreditCardCondition);
        String responseData = getResponse(toJson(req), "common.creditcard.validate", true);
        return new Gson().fromJson(responseData, CreditCardValidateResult.class);
    }


    /**
     * 取消订单
     * cencelCode 示例:
     * 对酒店相关条件不满意
     * 航班推迟
     * 价格过高，客人不接受
     * 通过其它途径预订
     * 行程变更
     * 已换酒店
     * 重单
     * 其它
     *
     * @param orderId    订单id
     * @param cancelCode String(10) 取消类型
     * @param reason     String(50) 原因
     * @return
     */
    public HotelOrderCancelResult cancelOrder(long orderId, String cancelCode, String reason) {
        CancelOrderCondition condition = new CancelOrderCondition();
        if (reason != null && reason.length() > 50) {
            reason = reason.substring(0, 50);
        }
        condition.setReason(reason);
        if (cancelCode != null && cancelCode.length() > 10) {
            cancelCode = reason.substring(0, 10);
        }
        condition.setCancelCode(cancelCode);
        condition.setOrderId(orderId);
        return cancelOrder(condition);
    }

    /**
     * 取消订单
     *
     * @param cancelOrderCondition
     * @return
     */
    public HotelOrderCancelResult cancelOrder(CancelOrderCondition cancelOrderCondition) {
        init();
        BaseRequst<CancelOrderCondition> req = new BaseRequst<CancelOrderCondition>(version, locale, cancelOrderCondition);
        String responseData = getResponse(toJson(req), "hotel.order.cancel", true);
        return new Gson().fromJson(responseData, HotelOrderCancelResult.class);
    }


    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    public HotelOrderDetailResult getOrderDetail(long orderId) {
        OrderIdCondition condition = new OrderIdCondition();
        condition.setOrderId(orderId);
        return getOrderDetail(condition);
    }

    /**
     * 获取订单详情
     *
     * @param orderIdCondition
     * @return
     */
    public HotelOrderDetailResult getOrderDetail(OrderIdCondition orderIdCondition) {
        init();
        BaseRequst<OrderIdCondition> req = new BaseRequst<OrderIdCondition>(version, locale, orderIdCondition);
        String responseData = getResponse(toJson(req), "hotel.order.detail", true);

        Gson gson = new GsonBuilder()
                .serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")//时间转化为特定格式
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.
                .setPrettyPrinting() //对json结果格式化.
                .create();
        return gson.fromJson(responseData, HotelOrderDetailResult.class);
    }

    private String toJson(Object obj) {
        String str = JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss");
        return str;
    }


    private String getResponse(String req, String method, boolean https) {
        long epoch = System.currentTimeMillis() / 1000;
        String sig = Tool.md5(epoch + Tool.md5(req + appKey) + appSecret);
        String url = "http" + (https ? "s" : "") + "://" + serverHost + "/rest?format=json&method=" + method + "&user=" + appUser
                + "&timestamp=" + epoch + "&signature=" + sig + "&data=" + Tool.encodeUri(req);
        String result;
        if (https) {
            result = Http.HttpsSend("POST", url, "");
        } else {
            result = Http.HttpSend("POST", url, "");
        }
        if (StringUtils.isBlank(result))
            return null;
        return result.trim().replaceAll("0001-01-01T00:00:00", "2001-01-01T00:00:00");
    }


    public Reviews getReviews(long elongId, int pageIndex) {
        String json = null;
        try {
            json = Http.HttpSend("GET", String.format(commentListApi, elongId, pageIndex), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json == null)
            return null;
        return new Gson().fromJson(json, Reviews.class);
    }

    public List<Reviews.CommentsEntity> getCommentLimitTime(long elongId, Date after) {
        int pageIndex = 1;
        long timeStamp = after.getTime();
        List<Reviews.CommentsEntity> results = new ArrayList<Reviews.CommentsEntity>();
        do {
            Reviews reviews = getReviews(elongId, pageIndex);
            if (reviews == null || reviews.isError() || reviews.getComments() == null) {
                break;
            }
            for (Reviews.CommentsEntity commentInfoEntity : reviews.getComments()) {
                long publishTime = 0;
                try {
                    publishTime = new SimpleDateFormat("yyyy-MM-dd").parse(commentInfoEntity.getCommentDateTime()).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (timeStamp > publishTime) {
                    continue;
                }
                results.add(commentInfoEntity);
            }
            if (reviews.getCommentCount() < pageIndex * 20
                    || reviews.getCommentCount() <= results.size())
                break;
        } while (pageIndex++ < 1000);
        return results;
    }
}
