package com.data.hmly.service.translation.flight.juhe;

import com.data.hmly.service.translation.flight.juhe.entity.CancelCheckResult;
import com.data.hmly.service.translation.flight.juhe.entity.CancelOrderResult;
import com.data.hmly.service.translation.flight.juhe.entity.CheckPriceResult;
import com.data.hmly.service.translation.flight.juhe.entity.CreateOrderResult;
import com.data.hmly.service.translation.flight.juhe.entity.FlightPolicy;
import com.data.hmly.service.translation.flight.juhe.entity.OrderFlights;
import com.data.hmly.service.translation.flight.juhe.entity.OrderPassenger;
import com.data.hmly.service.translation.flight.juhe.entity.PaymentResult;
import com.data.hmly.service.translation.flight.juhe.entity.RefundCheckResult;
import com.data.hmly.service.translation.flight.juhe.entity.RefundResult;
import com.data.hmly.service.translation.flight.juhe.entity.TicketResult;
import com.data.hmly.service.translation.util.CommonUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Sane on 15/12/23.
 */
public class JuheFlightService {
    //    private final static Logger logger = Logger.getLogger(JuheFlightService.class);
    //    private final static String key = "";
    private final static String flightPolicyApi = "http://api2.juheapi.com/zteict/order/flightPolicy?fromCity=%s&toCity=%s&flightDate=%s&airline=%s&key=%s";
    private final static String orderPriceApi = "http://api2.juheapi.com/zteict/order/price?takeoffPort=%s&landingPort=%s&flightDate=%s&flightNum=%s&cabin=%s&finallyPrice=%d&key=%s";
    private final static String orderCreateApi = "http://api2.juheapi.com/zteict/order/create";
    private final static String orderCreateParam = "totalAmount=%d&paymentFlag=%s&orderFlights=%s&passengers=%s&orderId=%s&key=%s";
    private final static String orderCheckApi = "http://api2.juheapi.com/zteict/order/get?key=%s&orderId=%s&ticketNum=%s";
    private final static String orderCancelApi = "http://api2.juheapi.com/zteict/order/cancel?explain=%s&orderId=%s&key=%s";
    private final static String orderPaymentApi = "http://api2.juheapi.com/zteict/order/payment?key=%s&orderId=%s";
    private final static String orderRefundCheckApi = "http://api2.juheapi.com/zteict/order/refundcheck?key=%s&orderId=%s&ticketNum=%s";
    private final static String orderRefundApi = "http://api2.juheapi.com/zteict/order/refundapply?freewillFlag=%s&key=%s&orderId=%s&ticketNum=%s";
    private final static String ticketGetApi = "http://api2.juheapi.com/zteict/ticket/get?key=%s&orderId=%s";


    /**
     * 搜索航班和机票
     *
     * @param fromCity   出发地三字码
     * @param toCity     目的地三字码
     * @param flightDate 起飞日期
     * @param airline    航空公司代码
     * @param key        应用APPKEY
     * @return 符合条件的航班＋i 机票
     */
    public static FlightPolicy search(String fromCity, String toCity, String flightDate, String airline, String key) {
        String json = CommonUtils.getJson(String.format(flightPolicyApi, fromCity, toCity, flightDate, airline, key));
        //出错
        if (json == null || !json.contains("{\"error_code\":\"200\"")) {
            return null;
        }
        //json不规范，当只有一个航班时，不是数组形式，所以这里判断不是数组时加入数组的“[]”
        if (json.endsWith("}}")) {
            json = json.substring(0, json.length() - 1).replace("\"result\":{", "\"result\":[{") + "]}";
        }
        if (json.contains("\"cabinInfos\":{\"")) {
            json = json.replace("\"cabinInfos\":{\"", "\"cabinInfos\":[{\"").replace("\"},\"childAirportPrice\"", "\"}],\"childAirportPrice\"");
        }

        if (json != null) {
            FlightPolicy result = new Gson().fromJson(json, FlightPolicy.class);
            if (result != null)
                return result;
        }
        return null;
    }

    /**
     * (patFlag='Y' 才需要验价)
     * 根据出发、到达、日期、航班号、舱位等验证价格
     *
     * @param fromCity   出发城市
     * @param toCity     到达城市
     * @param flightDate 出发日期
     * @param flightNum  航班号
     * @param cabin      舱位
     * @param price      价格
     * @param key        应用APPKEY
     * @return 验证价格是否正确
     */
    public static CheckPriceResult checkPrice(String fromCity, String toCity, String flightDate, String flightNum, String cabin, int price, String key) {
        String json = CommonUtils.getJson(String.format(orderPriceApi, fromCity, toCity, flightDate, flightNum, cabin, price, key));
        if (json != null) {
            CheckPriceResult result = new Gson().fromJson(json, CheckPriceResult.class);
            if (result != null)
                return result;
        }
        return null;
    }

    /**
     * 创建订单
     *
     * @param key        聚合key
     * @param orderId    订单id
     * @param flight     航班
     * @param passengers 乘客
     * @return 创建订单结果
     */
    public static CreateOrderResult createOrder(String key, String orderId, int totalAmount, OrderFlights flight, List<OrderPassenger> passengers) {
        String flightJson = null;
        String passengersJson = null;
        try {
            flightJson = URLEncoder.encode(new Gson().toJson(flight), "UTF-8");
            passengersJson = URLEncoder.encode(new Gson().toJson(passengers), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        String json = post(orderCreateApi, String.format(orderCreateParam, totalAmount,'N', flightJson, passengersJson, orderId, key));
        String json = CommonUtils.getJson(orderCreateApi + "?" + String.format(orderCreateParam, totalAmount, 'N', flightJson, passengersJson, orderId, key));
        if (json != null) {
            CreateOrderResult result = new Gson().fromJson(json, CreateOrderResult.class);
            if (result != null)
                return result;
        }
        return null;
    }

    /**
     * 获取订单信息
     *
     * @param key      用户订单id
     * @param orderId  聚合key
     * @param ticketId 票单号(可调用"根据订单id获取票号"接口获取)
     * @return CancelOrderResult
     */
    public static CancelCheckResult checkOrderResult(String key, String orderId, String ticketId) {
        String json = CommonUtils.getJson(String.format(orderCheckApi, key, orderId, ticketId));
        if (json != null) {
            CancelCheckResult result = new Gson().fromJson(json, CancelCheckResult.class);
            if (result != null)
                return result;
        }
        return null;


    }

    /**
     * 取消订单
     *
     * @param explain 取消原因 非必填
     * @param key     用户订单id
     * @param orderId 聚合key
     * @return CancelOrderResult
     */
    public static CancelOrderResult cancelOrderResult(String explain, String key, String orderId) {
        String json = CommonUtils.getJson(String.format(orderCancelApi, explain, key, orderId));
        if (json != null) {
            CancelOrderResult result = new Gson().fromJson(json, CancelOrderResult.class);
            if (result != null)
                return result;
        }
        return null;


    }


    /**
     * 付款
     *
     * @param key     聚合key
     * @param orderId 订单id
     * @return 返回付款结果
     */
    public static PaymentResult pay(String key, String orderId) {
        String json = CommonUtils.getJson(String.format(orderPaymentApi, key, orderId));
        if (json != null) {
            PaymentResult result = new Gson().fromJson(json, PaymentResult.class);
            if (result != null)
                return result;
        }
        return null;
    }

    /**
     * 退单验证
     *
     * @param key     聚合key
     * @param orderId 订单id
     * @return 返回退单验证结果
     */
    public static RefundCheckResult refundCheck(String key, String orderId, String ticketNum) {
        String json = CommonUtils.getJson(String.format(orderRefundCheckApi, key, orderId, ticketNum));
        if (json != null) {
            RefundCheckResult result = new Gson().fromJson(json, RefundCheckResult.class);
            if (result != null)
                return result;
        }
        return null;
    }

    /**
     * 退单申请
     *
     * @param freewill 自愿标记(Y表示自愿N表示非自愿)
     * @param key      聚合key
     * @param orderId  订单id
     * @param ticketId 机票id
     * @return
     */

    public static RefundResult refund(boolean freewill, String key, String orderId, String ticketId) {
        String json = CommonUtils.getJson(String.format(orderRefundApi, freewill ? "Y" : "N", key, orderId, ticketId));
        if (json != null) {
            RefundResult result = new Gson().fromJson(json, RefundResult.class);
            if (result != null)
                return result;
        }
        return null;
    }


    /**
     * 获得订单的机票信息
     *
     * @param key     聚合key
     * @param orderId 订单id
     * @return 返回该订单的机票结果
     */
    public static TicketResult getTicket(String key, String orderId) {
        String json = CommonUtils.getJson(String.format(ticketGetApi, key, orderId));
        if (!json.contains("\"error_code\": \"200\"")) {
            json = json.replace("\"result\":\"\"", "\"result\":null");
        }
        if (json != null) {
            TicketResult result = new Gson().fromJson(json, TicketResult.class);
            if (result != null)
                return result;
        }
        return null;
    }
}
