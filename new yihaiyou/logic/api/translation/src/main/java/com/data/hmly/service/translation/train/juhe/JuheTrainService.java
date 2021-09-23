package com.data.hmly.service.translation.train.juhe;

import com.data.hmly.service.translation.train.juhe.entity.CancelOrderResult;
import com.data.hmly.service.translation.train.juhe.entity.CheckOrderResult;
import com.data.hmly.service.translation.train.juhe.entity.Passenger;
import com.data.hmly.service.translation.train.juhe.entity.PaymentResult;
import com.data.hmly.service.translation.train.juhe.entity.SubmitOrderRequest;
import com.data.hmly.service.translation.train.juhe.entity.SubmitOrderResult;
import com.data.hmly.service.translation.train.juhe.entity.Ticket;
import com.data.hmly.service.translation.train.juhe.entity.TicketResult;
import com.data.hmly.service.translation.train.juhe.entity.TicketsAvailable;
import com.data.hmly.service.translation.util.CommonUtils;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Sane on 16/1/6.
 */
public class JuheTrainService {
    private static String ticketsSearchApi = "http://op.juhe.cn/trainTickets/ticketsAvailable?dtype=json&train_date=%s&from_station=%s&to_station=%s&key=%s";
    private static String submitOrderUrl = "http://op.juhe.cn/trainTickets/submit";
    private static String orderStatusUrl = "http://op.juhe.cn/trainTickets/orderStatus?key=%s&orderid=%s";
    private static String cancelOrderUrl = "http://op.juhe.cn/trainTickets/cancel?key=%s&orderid=%s";
    private static String submitOrderParamApi = "dtype=json&key=%s&user_orderid=%s&train_date=%s&from_station_code=%s&from_station_name=%s" +
            "&to_station_code=%s&to_station_name=%s&checi=%s&passengers=%s";
    //    private final static String orderPaymentApi = "http://api2.juheapi.com/zteict/order/payment?key=%s&orderId=%s";
    private static String orderPaymentApi = "http://op.juhe.cn/trainTickets/pay?key=%s&orderid=%s";
    private static String ticketGetApi = "http://op.juhe.cn/trainTickets/orderStatus?key=%s&orderid=%s";
    private static String refundApi = "http://op.juhe.cn/trainTickets/refund";
    private static String refundParam = "key=%s&orderid=%s&tickets=%s";


    /**
     * 查询余票
     *
     * @param date        出发日期
     * @param fromStation 出发站点(code)
     * @param toStation   到达站点(code)
     * @param key         聚合key
     * @return
     */
    public static List<TicketsAvailable.ResultEntity.Ticket> searchTickets(String date, String fromStation, String toStation, String key) {
        String json = CommonUtils.getJson(String.format(ticketsSearchApi, date, fromStation, toStation, key));
        if (json != null && !json.contains("\"error_code\":0")) {
            return null;
        }
        if (json != null) {
            TicketsAvailable result = new Gson().fromJson(json, TicketsAvailable.class);
            if (result != null)
                return result.getResult().getList();
        }
        return null;
    }

    /**
     * 提交订单
     *
     * @param orderRequest 请求信息,包含日期 始发站 终点站 等
     * @param key          聚合key
     * @param orderId      订单id
     * @param passengers   乘客信息
     * @return
     */
    public static SubmitOrderResult submitOrder(SubmitOrderRequest orderRequest, String key, String orderId, List<Passenger> passengers) {
        String ps = new Gson().toJson(passengers);
        String request = String.format(submitOrderParamApi, key, orderId, orderRequest.getTrainDate(), orderRequest.getFromCode(), orderRequest.getFromName(), orderRequest.getToCode(), orderRequest.getToName(), orderRequest.getCheci(), ps);
        String json = CommonUtils.postJson(submitOrderUrl, request);
        return new Gson().fromJson(json, SubmitOrderResult.class);
    }

    /**
     * 查看订单状态
     *
     * @param key     聚合key
     * @param orderId 订单id
     * @return
     */
    public static CancelOrderResult cancelOrder(String key, String orderId) {
        String json = CommonUtils.getJson(String.format(cancelOrderUrl, key, orderId));
        if (json != null) {
            CancelOrderResult result = new Gson().fromJson(json, CancelOrderResult.class);
            if (result != null)
                return result;
        }
        return null;
    }

    /**
     * 取消待支付订单
     * 0：刚提交，待处理；处理完将变成1或2；
     * 1：失败／失效／取消的订单；
     * 2：占座成功待支付（此时可取消订单，超时不支付将失效）；
     * 3：支付成功待出票；
     * 4：出票成功；
     * 5：出票失败；关于出票失败的问题，请阅读 http://code.juhe.cn/docs/201 中第33条
     * 6：正在处理线上退票请求；请阅读 http://code.juhe.cn/docs/201 中第16、17、18条
     * 7：有乘客退票（改签）成功（status保存的是最后一次操作该订单后的状态，先有乘客退票失败，
     * 然后有乘客退票成功，那么status为7）；
     * 8：有乘客退票失败（status保存的是最后一次操作该订单后的状态，先有乘客退票成功，
     * 然后有乘客退票失败，那么status为8）；
     * 将来可能会增加状态，但不会减少，请您在程序中做好兼容性处理。
     *
     * @param key     聚合key
     * @param orderId 订单id
     * @return
     */
    public static CheckOrderResult checkOrder(String key, String orderId) {
        String json = CommonUtils.getJson(String.format(orderStatusUrl, key, orderId));
        if (json != null) {
            CheckOrderResult result = new Gson().fromJson(json, CheckOrderResult.class);
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
     * 获得订单的火车票信息
     *
     * @param key     聚合key
     * @param orderId 订单id
     * @return 返回该订单的火车票结果
     */
    public static TicketResult getTicket(String key, String orderId) {
        String json = CommonUtils.getJson(String.format(ticketGetApi, key, orderId));
        if (json != null && !json.contains("\"error_code\": 0")) {
            json = json.replace("\"result\":\"\"", "\"result\":null");
        }
        if (json != null) {
            TicketResult result = new Gson().fromJson(json, TicketResult.class);
            if (result != null)
                return result;
        }
        return null;
    }

    /**
     * 退票
     *
     * @param key     聚合key
     * @param orderId 订单id
     * @return 返回该订单的火车票结果
     */
    public static TicketResult refundTicket(String key, String orderId, List<Ticket> tickets) {
        String json = CommonUtils.postJson(refundApi, String.format(refundParam, key, orderId, new Gson().toJson(tickets)));
        if (!json.contains("\"error_code\": 0")) {
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
