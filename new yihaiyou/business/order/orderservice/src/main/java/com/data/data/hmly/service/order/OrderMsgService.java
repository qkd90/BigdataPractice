package com.data.data.hmly.service.order;

import com.data.data.hmly.service.SendingMsgService;
import com.data.data.hmly.service.common.MsgService;
import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.entity.SendStatus;
import com.data.data.hmly.service.entity.SendingMsg;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.OrderTouristService;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.wechat.WechatDataTextService;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.WechatSupportAccountService;
import com.data.data.hmly.service.wechat.entity.WechatSupportAccount;
import com.data.data.hmly.service.wechat.entity.enums.NoticeType;
import com.data.data.hmly.util.MsgTemplateUtil;
import com.data.hmly.service.translation.flight.juhe.entity.TicketResult;
import com.data.hmly.service.translation.train.juhe.entity.CheckOrderResult;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/4/29.
 */
@Service
public class OrderMsgService {

    private static Log log = LogFactory.getLog(OrderMsgService.class);

    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private WechatService wechatService;
    @Resource
    private WechatDataTextService wechatDataTextService;
    @Resource
    private WechatSupportAccountService wechatSupportAccountService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderTouristService orderTouristService;
    @Resource
    private SendingMsgService sendingMsgService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private LinetypepriceService linetypepriceService;
    @Resource
    private ProductValidateCodeService productValidateCodeService;
    @Resource
    private MsgService msgService;

    /**
     * 订单支付成功短信通知
     *
     * @param order
     */
    public void doSendOrderPaySuccess(Order order) {
        User user = orderLogService.getSysOrderLogUser();
        List<OrderDetail> detailList = orderDetailService.getByOrderId(order.getId());
        Map<String, Object> data = getOrderPaySuccessMsgData(order, detailList);
        String templateName;
        String wxTemplateName;
        if (OrderType.ticket.equals(order.getOrderType())) {
            // 门票
            templateName = propertiesManager.getString("ORDER_TICKET_PAY_SCS_TLE");
            wxTemplateName = propertiesManager.getString("WX_TICKET_PAY_SCS_TLE");
        } else if (OrderType.hotel.equals(order.getOrderType())) {
            // 酒店
            templateName = propertiesManager.getString("HOTEL_PAY_SCS_TLE");
            wxTemplateName = propertiesManager.getString("WX_HOTEL_PAY_SCS_TLE");
        } else if (OrderType.train.equals(order.getOrderType())) {
            // 火车
            if (detailList.size() == 1) {
                templateName = propertiesManager.getString("ORDER_TRAIN_PAY_SCS_TLE");
                wxTemplateName = propertiesManager.getString("WX_TRAIN_PAY_SCS_TLE");
            } else if (detailList.size() == 2) {
                templateName = propertiesManager.getString("ORDER_ROUND_TRAIN_PAY_SCS_TLE");
                wxTemplateName = propertiesManager.getString("WX_ROUND_TRAIN_PAY_SCS_TLE");
            } else {
                templateName = propertiesManager.getString("ORDER_TRAIN_PAY_SCS_TLE");
                wxTemplateName = propertiesManager.getString("WX_TRAIN_PAY_SCS_TLE");
            }
        } else if (OrderType.flight.equals(order.getOrderType())) {
            // 机票
            if (detailList.size() == 1) {
                templateName = propertiesManager.getString("ORDER_FLIGHT_PAY_SCS_TLE");
                wxTemplateName = propertiesManager.getString("WX_FLIGHT_PAY_SCS_TLE");
            } else if (detailList.size() == 2) {
                templateName = propertiesManager.getString("ORDER_ROUND_FLIGHT_PAY_SCS_TLE");
                wxTemplateName = propertiesManager.getString("WX_ROUND_FLIGHT_PAY_SCS_TLE");
            } else {
                templateName = propertiesManager.getString("ORDER_FLIGHT_PAY_SCS_TLE");
                wxTemplateName = propertiesManager.getString("WX_FLIGHT_PAY_SCS_TLE");
            }
        } else if (OrderType.plan.equals(order.getOrderType())) {
            // 行程规划
            templateName = propertiesManager.getString("ORDER_PLAN_PAY_SCS_TLE");
            wxTemplateName = propertiesManager.getString("WX_PLAN_PAY_SCS_TLE");
        } else if (OrderType.line.equals(order.getOrderType())) {
            // 线路
            templateName = propertiesManager.getString("ORDER_LINE_PAY_SCS_TLE");
            wxTemplateName = propertiesManager.getString("WX_LINE_PAY_SCS_TLE");
        } else if (OrderType.ship.equals(order.getOrderType())) {
            templateName = propertiesManager.getString("ORDER_SHIP_PAY_SCS_TLE");
            wxTemplateName = propertiesManager.getString("WX_SHIP_PAY_SCS_TLE");
        } else {
            // 默认支付成功模板
            templateName = propertiesManager.getString("ORDER_PAY_SCS_TLE");
            wxTemplateName = propertiesManager.getString("WX_ORDER_PAY_SCS_TLE");
        }
        // 短信部分
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, order.getMobile(), user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + order.getOrderType().getDescription()
                                + "订单支付成功短信发送取消,短信内容为空",
                        order.getId(),
                        null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + order.getOrderType().getDescription()
                        + "订单支付成功短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + order.getOrderType().getDescription()
                            + "订单支付成功短信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + order.getOrderType().getDescription()
                    + "订单支付成功短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + order.getOrderType().getDescription()
                                + "订单支付成功微信发送取消,短信内容为空",
                        order.getId(),
                        null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + order.getOrderType().getDescription()
                        + "订单支付成功微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + order.getOrderType().getDescription()
                            + "订单支付成功微信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + order.getOrderType().getDescription()
                    + "订单支付成功微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 门票预订成功短信通知
     *
     * @param orderDetail
     * @param ticketPrice
     */
    public void doSendTicketBookingSuccessMsg(OrderDetail orderDetail, TicketPrice ticketPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getTicketMsgData(orderDetail, ticketPrice);
        String templateName = propertiesManager.getString("ORDER_TICKET_BOOKING_SCS_TLE");
        String wxTemplateName = propertiesManager.getString("WX_TICKET_BOOKING_SCS_TLE");
        // 短信部分
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);

            if (StringUtils.hasText(msgContent)) {
                // 发送短信
//                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "门票预订成功短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "门票预订成功短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "门票预订成功短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "门票预订成功短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "门票预订成功微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "门票预订成功微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "门票预订成功微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "门票预订成功微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 门票预订失败短信通知
     *
     * @param orderDetail
     * @param ticketPrice
     */
    public void doSendTicketBookingFailMsg(OrderDetail orderDetail, TicketPrice ticketPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getTicketMsgData(orderDetail, ticketPrice);
        String templateName = propertiesManager.getString("ORDER_TICKET_BOOKING_FAIL_TLE");
        String wxTemplateName = propertiesManager.getString("WX_TICKET_BOOKING_FAIL_TLE");
        // 短信部分
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "门票预订失败短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "门票预订失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "门票预订失败短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "门票预订失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "门票预订失败微信发送取消, 短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "门票预订失败微信发送取消, 短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "门票预订失败微信发送失败! 没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "门票预订失败微信发送失败! 没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 门票退订成功短信通知
     *
     * @param orderDetail
     * @param ticketPrice
     */
    public void doSendTicketCancelSuccessMsg(OrderDetail orderDetail, TicketPrice ticketPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getTicketMsgData(orderDetail, ticketPrice);
        String templateName = propertiesManager.getString("ORDER_TICKET_CANCEL_SCS_TLE");
        String wxTemplateName = propertiesManager.getString("WX_TICKET_CANCEL_SCS_TLE");
        // 短信部分
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "门票退订成功短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "门票退订成功短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "门票退订成功短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "门票退订成功短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信消息
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "门票退订成功微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "门票退订成功微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "门票退订成功微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "门票退订成功微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 门票退订失败短信通知
     *
     * @param orderDetail
     * @param ticketPrice
     */
    public void doSendTicketCancelFailMsg(OrderDetail orderDetail, TicketPrice ticketPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getTicketMsgData(orderDetail, ticketPrice);
        String templateName = propertiesManager.getString("ORDER_TICKET_CANCEL_FAIL_TLE");
        String wxTemplateName = propertiesManager.getString("WX_TICKET_CANCEL_FAIL_TLE");
        // 短信部分
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "门票退订失败短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "门票退订失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "门票退订失败短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "门票退订失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信消息
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "门票退订失败微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "门票退订失败微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "门票退订失败微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "门票退订失败微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 酒店预订成功短信通知
     *
     * @param orderDetail
     * @param hotelPrice
     */
    public void doSendHotelBookingSuccessMsg(OrderDetail orderDetail, HotelPrice hotelPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getHotelMsgData(orderDetail, hotelPrice);
        String templateName = propertiesManager.getString("ORDER_HOTEL_BOOKING_SCS_TLE");
        String wxTemplateName = propertiesManager.getString("WX_HOTEL_BOOKING_SCS_TLE");
        // 短信部分
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
//                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "酒店预订成功短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "酒店预订成功短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "酒店预订成功短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "酒店预订成功短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信消息
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "酒店预订成功微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "酒店预订成功微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "酒店预订成功微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "酒店预订成功微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 酒店预订失败短信通知
     *
     * @param orderDetail
     * @param hotelPrice
     */
    public void doSendHotelBookingFailMsg(OrderDetail orderDetail, HotelPrice hotelPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getHotelMsgData(orderDetail, hotelPrice);
        String templateName;
        String wxTemplateName;
        if (PriceStatus.GUARANTEE.equals(hotelPrice.getStatus())) {
            templateName = propertiesManager.getString("ORDER_G_HOTEL_BOOKING_FAIL_TLE");
            wxTemplateName = propertiesManager.getString("WX_G_HOTEL_BOOKING_FAIL_TLE");
        } else if (PriceStatus.UP.equals(hotelPrice.getStatus())) {
            templateName = propertiesManager.getString("ORDER_D_HOTEL_BOOKING_FAIL_TLE");
            wxTemplateName = propertiesManager.getString("WX_D_HOTEL_BOOKING_FAIL_TLE");
        } else {
            templateName = propertiesManager.getString("ORDER_HOTEL_BOOKING_FAIL_TLE");
            wxTemplateName = propertiesManager.getString("WX_HOTEL_BOOKING_FAIL_TLE");
        }
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "酒店预订失败短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "酒店预订失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "酒店预订失败短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "酒店预订失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信消息
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "酒店预订失败微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "酒店预订失败微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "酒店预订失败微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "酒店预订失败微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 酒店取消成功短信通知
     *
     * @param orderDetail
     * @param hotelPrice
     */
    public void doSendHotelCancelSuuccessMsg(OrderDetail orderDetail, HotelPrice hotelPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getHotelMsgData(orderDetail, hotelPrice);
        String templateName = propertiesManager.getString("ORDER_HOTEL_CANCEL_SCS_TLE");
        String wxTemplateName = propertiesManager.getString("WX_HOTEL_CANCEL_SCS_TLE");
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
//                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "酒店取消成功短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "酒店取消成功短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "酒店取消成功短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "酒店取消成功短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信消息
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "酒店取消成功微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "酒店取消成功微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "酒店取消成功微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "酒店取消成功微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 酒店取消失败短信通知
     *
     * @param orderDetail
     * @param hotelPrice
     */
    public void doSendHotelCancelFailMsg(OrderDetail orderDetail, HotelPrice hotelPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getHotelMsgData(orderDetail, hotelPrice);
        String templateName = propertiesManager.getString("ORDER_HOTEL_CANCEL_FAIL_TLE");
        String wxTemplateName = propertiesManager.getString("WX_HOTEL_CANCEL_FAIL_TLE");
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "酒店取消失败短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "酒店取消失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "酒店取消失败短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "酒店取消失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信消息
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "酒店取消失败微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "酒店取消失败微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "酒店取消失败微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "酒店取消失败微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 火车票预订成功短信通知
     *
     * @param orderDetail
     * @param trafficPrice
     * @param checkOrderResult
     */
    public void doSendTrainBookingSuccessMsg(OrderDetail orderDetail, TrafficPrice trafficPrice, CheckOrderResult checkOrderResult) {
        User user = orderLogService.getSysOrderLogUser();
        List<CheckOrderResult.ResultEntity.PassengersEntity> passengers = checkOrderResult.getResult().getPassengers();
        List<OrderTourist> orderTouristList = orderTouristService.getByOrderDetailId(orderDetail.getId());
        Map<String, Object> touristMap = orderDetailService.orderTouristToMap(orderTouristList);
        for (CheckOrderResult.ResultEntity.PassengersEntity passengersEntity : passengers) {
            Map<String, Object> data = getTrainMsgData(orderDetail, trafficPrice, checkOrderResult, passengersEntity);
            String templateName = propertiesManager.getString("ORDER_TRAIN_BOOKING_SCS_TLE");
            String wxTemplateName = propertiesManager.getString("WX_TRAIN_BOOKING_SCS_TLE");
            // 短信部分
            if (StringUtils.hasText(templateName)) {
                String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
                // 获取短信内容
                String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
                if (StringUtils.hasText(msgContent)) {
                    // 发送短信
                    String receiver = touristMap.get(passengersEntity.getPassportseno()).toString();
                    this.doSendMsg(msgContent, receiver, user, null, orderDetail);
                } else {
                    OrderLog orderLog = orderLogService.createOrderLog(
                            user,
                            "订单详情#" + orderDetail.getId() + "##" + passengersEntity.getPassengersename() + "火车票预订成功短信发送取消,短信内容为空",
                            orderDetail.getOrder().getId(),
                            orderDetail.getId());
                    orderLogService.loggingOrderLog(orderLog);
                    log.error("订单详情#" + orderDetail.getId() + "##" + passengersEntity.getPassengersename() + "火车票预订成功短信发送取消,短信内容为空");
                    return;
                }
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "##" + passengersEntity.getPassengersename() + "火车票预订成功短信发送失败!没有在配置文件中找到模板名称",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "##" + passengersEntity.getPassengersename() + "火车票预订成功短信发送失败!没有在配置文件中找到模板名称");
                return;
            }
            // 微信部分
            if (StringUtils.hasText(wxTemplateName)) {
                String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
                // 获取微信消息内容
                String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
                if (StringUtils.hasText(wxMsgContent)) {
                    // 发送微信消息
                    this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
                } else {
                    OrderLog orderLog = orderLogService.createOrderLog(
                            user,
                            "订单详情#" + orderDetail.getId() + "##" + passengersEntity.getPassengersename() + "火车票预订成功微信发送取消,短信内容为空",
                            orderDetail.getOrder().getId(),
                            orderDetail.getId());
                    orderLogService.loggingOrderLog(orderLog);
                    log.error("订单详情#" + orderDetail.getId() + "##" + passengersEntity.getPassengersename() + "火车票预订成功微信发送取消,短信内容为空");
                    return;
                }
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "##" + passengersEntity.getPassengersename() + "火车票预订成功微信发送失败!没有在配置文件中找到模板名称",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "##" + passengersEntity.getPassengersename() + "火车票预订成功微信发送失败!没有在配置文件中找到模板名称");
                return;
            }
        }
    }

    /**
     * 火车票预订失败短信通知
     *
     * @param orderDetail
     * @param trafficPrice
     */
    public void doSendTrainBookingFailMsg(OrderDetail orderDetail, TrafficPrice trafficPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getTrainMsgData(orderDetail, trafficPrice, null, null);
        String templateName = propertiesManager.getString("ORDER_TRAIN_BOOKING_FAIL_TLE");
        String wxTemplateName = propertiesManager.getString("WX_TRAIN_BOOKING_FAIL_TLE");
        // 短信部分
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "火车票预订失败短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "火车票预订失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "火车票预订失败短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "火车票预订失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信消息
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "火车票预订失败微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "火车票预订失败微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "火车票预订失败微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "火车票预订失败微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 火车票退订成功短信通知
     *
     * @param orderDetail
     * @param trafficPrice
     */
    public void doSendTrainCancelSuccessMsg(OrderDetail orderDetail, TrafficPrice trafficPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getTrainMsgData(orderDetail, trafficPrice, null, null);
        String templateName = propertiesManager.getString("ORDER_TRAIN_CANCEL_SCS_TLE");
        String wxTemplateName = propertiesManager.getString("WX_TRAIN_CANCEL_SCS_TLE");
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "火车票退订成功短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "火车票退订成功短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "火车票退订成功短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "火车票退订成功短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信消息
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "火车票退订成功微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "火车票退订成功微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "火车票退订成功微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "火车票退订成功微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 火车票退订失败短信通知
     *
     * @param orderDetail
     * @param trafficPrice
     */
    public void doSendTrainCancelFailMsg(OrderDetail orderDetail, TrafficPrice trafficPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getTrainMsgData(orderDetail, trafficPrice, null, null);
        String templateName = propertiesManager.getString("ORDER_TRAIN_CANCEL_FAIL_TLE");
        String wxTemplateName = propertiesManager.getString("WX_TRAIN_CANCEL_FAIL_TLE");
        // 短信部分
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "火车票退订失败短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "火车票退订失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "火车票退订失败短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "火车票退订失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信消息
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "火车票退订失败微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "火车票退订失败微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "火车票退订失败微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "火车票退订失败微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 机票预订成功短信通知
     *
     * @param orderDetail
     * @param trafficPrice
     * @param ticketResult
     */
    public void doSendFlightBookingSuccessMsg(OrderDetail orderDetail, TrafficPrice trafficPrice, TicketResult ticketResult) {
        User user = orderLogService.getSysOrderLogUser();
        TicketResult.ResultEntity resultEntity = ticketResult.getResult();
        List<TicketResult.ResultEntity.TicketListEntity> ticketList = resultEntity.getTicketList();
        for (TicketResult.ResultEntity.TicketListEntity ticketListEntity : ticketList) {
            String ticketStatus = ticketListEntity.getTicketStatus();
            if ("SUCC_ISSUE".equals(ticketStatus)) {
                Map<String, Object> data = getFlightMsgData(orderDetail, trafficPrice, ticketListEntity);
                String templateName = propertiesManager.getString("ORDER_FLIGHT_BOOKING_SCS_TLE");
                String wxTemplateName = propertiesManager.getString("WX_FLIGHT_BOOKING_SCS_TLE");
                // 短信部分
                if (StringUtils.hasText(templateName)) {
                    String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
                    // 获取短信内容
                    String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
                    if (StringUtils.hasText(msgContent)) {
                        // 发送短信
                        this.doSendMsg(msgContent, ticketListEntity.getPassengerMobile(), user, null, orderDetail);
                    } else {
                        OrderLog orderLog = orderLogService.createOrderLog(
                                user,
                                "订单详情#" + orderDetail.getId() + "##" + ticketListEntity.getPassengerName()
                                        + "机票预订成功短信发送取消,短信内容为空",
                                orderDetail.getOrder().getId(),
                                orderDetail.getId());
                        orderLogService.loggingOrderLog(orderLog);
                        log.error("订单详情#" + orderDetail.getId() + "机票预订成功短信发送取消,短信内容为空");
                        return;
                    }
                } else {
                    OrderLog orderLog = orderLogService.createOrderLog(
                            user,
                            "订单详情#" + orderDetail.getId() + "机票预订成功短信发送失败!没有在配置文件中找到模板名称",
                            orderDetail.getOrder().getId(),
                            orderDetail.getId());
                    orderLogService.loggingOrderLog(orderLog);
                    log.error("订单详情#" + orderDetail.getId() + "机票预订成功短信发送失败!没有在配置文件中找到模板名称");
                    return;
                }
                // 微信部分
                if (StringUtils.hasText(wxTemplateName)) {
                    String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
                    // 获取微信消息内容
                    String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
                    if (StringUtils.hasText(wxMsgContent)) {
                        // 发送微信
                        this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
                    } else {
                        OrderLog orderLog = orderLogService.createOrderLog(
                                user,
                                "订单详情#" + orderDetail.getId() + "##" + ticketListEntity.getPassengerName()
                                        + "机票预订成功短信发送取消,短信内容为空",
                                orderDetail.getOrder().getId(),
                                orderDetail.getId());
                        orderLogService.loggingOrderLog(orderLog);
                        log.error("订单详情#" + orderDetail.getId() + "机票预订成功短信发送取消,短信内容为空");
                        return;
                    }
                } else {
                    OrderLog orderLog = orderLogService.createOrderLog(
                            user,
                            "订单详情#" + orderDetail.getId() + "##" + ticketListEntity.getPassengerName()
                                    + "机票预订成功微信发送失败!没有在配置文件中找到模板名称",
                            orderDetail.getOrder().getId(),
                            orderDetail.getId());
                    orderLogService.loggingOrderLog(orderLog);
                    log.error("订单详情#" + orderDetail.getId() + "##" + ticketListEntity.getPassengerName()
                            + "机票预订成功微信发送失败!没有在配置文件中找到模板名称");
                    return;
                }
            } else if ("FAIL_ISSUE".equals(ticketStatus)) {
                // this status may never happen
                // just do nothing now
                // 机票不会只有某几张出票失败, 要么整个订单出票失败
            } else {
                // Nothing to do
                // ...
            }
        }
    }

    /**
     * 机票预订失败短信通知
     *
     * @param orderDetail
     * @param trafficPrice
     */
    public void doSendFlightBookingFailMsg(OrderDetail orderDetail, TrafficPrice trafficPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getFlightMsgData(orderDetail, trafficPrice, null);
        String templateName = propertiesManager.getString("ORDER_FLIGHT_BOOKING_FAIL_TLE");
        String wxTemplateName = propertiesManager.getString("WX_FLIGHT_BOOKING_FAIL_TLE");
        // 短信部分
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "机票预订失败短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "机票预订失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "机票预订失败短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "机票预订失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "机票预订失败微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "机票预订失败微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "机票预订失败微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "机票预订失败微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 机票取消成功短信通知
     *
     * @param orderDetail
     * @param trafficPrice
     */
    public void doSendFlightCancelSuccessMsg(OrderDetail orderDetail, TrafficPrice trafficPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getFlightMsgData(orderDetail, trafficPrice, null);
        String templateName = propertiesManager.getString("ORDER_FLIGHT_CANCEL_SCS_TLE");
        String wxTemplateName = propertiesManager.getString("WX_FLIGHT_CANCEL_SCS_TLE");
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "机票取消成功短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "机票取消成功短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "机票取消成功短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "机票取消成功短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "机票取消成功微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "机票取消成功微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "机票取消成功微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "机票取消成功微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 机票取消失败短信通知
     *
     * @param orderDetail
     * @param trafficPrice
     */
    public void doSendFlightCancelFailMsg(OrderDetail orderDetail, TrafficPrice trafficPrice) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getFlightMsgData(orderDetail, trafficPrice, null);
        String templateName = propertiesManager.getString("ORDER_FLIGHT_CANCEL_FAIL_TLE");
        String wxTemplateName = propertiesManager.getString("WX_FLIGHT_CANCEL_FAIL_TLE");
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, orderDetail.getOrder().getMobile(), user, null, orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "机票取消失败短信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "机票取消失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "机票取消失败短信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "机票取消失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, orderDetail.getOrder(), orderDetail);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "机票取消失败微信发送取消,短信内容为空",
                        orderDetail.getOrder().getId(),
                        orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "机票取消失败微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单详情#" + orderDetail.getId() + "机票取消失败微信发送失败!没有在配置文件中找到模板名称",
                    orderDetail.getOrder().getId(),
                    orderDetail.getId());
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单详情#" + orderDetail.getId() + "机票取消失败微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 线路预订成功短信通知
     * @param order
     * @param line
     */
    public void doSendLineBookingSuccessMsg(Order order, Line line) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getLineMsgData(order, line);
        String templateName = "";
        String wxTemplateName = "";
        if (OrderStatus.WAIT.equals(order.getStatus())) {
            templateName = propertiesManager.getString("ORDER_LINE_BOOKING_SCS_PAY_TLE");
            wxTemplateName = propertiesManager.getString("WX_LINE_BOOKING_SCS_PAY_TLE");
        } else {
            templateName = propertiesManager.getString("ORDER_LINE_BOOKING_SCS_TLE");
            wxTemplateName = propertiesManager.getString("WX_LINE_BOOKING_SCS_TLE");
        }
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, order.getMobile(), user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "线路预订成功短信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "线路预订成功短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "线路预订成功短信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "线路预订成功短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "线路预订成功微信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "线路预订成功微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user, "订单#" + order.getId() + "线路预订成功微信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "线路预订成功微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 线路预订失败短信通知
     * @param order
     * @param line
     */
    public void doSendLineBookingFailMsg(Order order, Line line) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getLineMsgData(order, line);
        String templateName = propertiesManager.getString("ORDER_LINE_BOOKING_FAIL_TLE");
        String wxTemplateName = propertiesManager.getString("WX_LINE_BOOKING_FAIL_TLE");
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, order.getMobile(), user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "线路预订失败短信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "线路预订失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "线路预订失败短信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "线路预订失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "线路预订失败微信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "线路预订失败微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "线路预订失败微信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "线路预订失败微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 线路退订成功短信通知
     * @param order
     * @param line
     */
    public void doSendLineCancelSuccessMsg(Order order, Line line) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getLineMsgData(order, line);
        String templateName = propertiesManager.getString("ORDER_LINE_CANCEL_SCS_TLE");
        String wxTemplateName = propertiesManager.getString("WX_LINE_CANCEL_SCS_TLE");
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, order.getMobile(), user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "线路退订成功短信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + order.getId() + "线路退订成功短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "线路退订成功短信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "线路退订成功短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "线路退订成功微信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "线路退订成功微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "线路退订成功微信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "线路退订成功微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 线路退订失败短信通知
     * @param order
     * @param line
     */
    public void doSendLineCancelFailMsg(Order order, Line line) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getLineMsgData(order, line);
        String templateName = propertiesManager.getString("ORDER_LINE_CANCEL_FAIL_TLE");
        String wxTemplateName = propertiesManager.getString("WX_LINE_CANCEL_FAIL_TLE");
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, order.getMobile(), user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "线路退订失败短信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "线路退订失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "线路退订失败短信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "线路退订失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "线路退订失败微信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "线路退订失败微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "线路退订失败微信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "线路退订失败微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 交通-船票预订成功短信通知
     * @param order
     * @param traffic
     */
    public void doSendShipBookingSuccessMsg(Order order, Traffic traffic) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getShipMsgData(order, traffic);
        String templateName = propertiesManager.getString("ORDER_SHIP_BOOKING_SCS_TLE");
        String wxTemplateName = propertiesManager.getString("WX_SHIP_BOOKING_SCS_TLE");
        // 短信部分
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, order.getMobile(), user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "船票预订成功短信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "船票预订成功短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "船票预订成功短信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "船票预订成功短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "船票预订成功微信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "船票预订成功微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user, "订单#" + order.getId() + "船票预订成功微信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "船票预订成功微信发送失败!没有在配置文件中找到模板名称");
            return;
        }

    }

    /**
     * 交通-船票预订失败短信
     * @param order
     * @param traffic
     */
    public void doSendShipBookingFailMsg(Order order, Traffic traffic) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getShipMsgData(order, traffic);
        String templateName = propertiesManager.getString("ORDER_SHIP_BOOKING_FAIL_TLE");
        String wxTemplateName = propertiesManager.getString("WX_SHIP_BOOKING_FAIL_TLE");
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, order.getMobile(), user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "船票预订失败短信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "船票预订失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "船票预订失败短信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "船票预订失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "船票预订失败微信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "船票预订失败微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "船票预订失败微信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "船票预订失败微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    public void doSendShipCancelSuccessMsg(Order order, Traffic traffic) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getShipMsgData(order, traffic);
        String templateName = propertiesManager.getString("ORDER_SHIP_CANCEL_SCS_TLE");
        String wxTemplateName = propertiesManager.getString("WX_SHIP_CANCEL_SCS_TLE");
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, order.getMobile(), user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "船票退订成功短信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + order.getId() + "船票退订成功短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "船票退订成功短信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "船票退订成功短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "船票退订成功微信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "船票退订成功微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "船票退订成功微信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "船票退订成功微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    public void doSendShipCancelFailMsg(Order order, Traffic traffic) {
        User user = orderLogService.getSysOrderLogUser();
        Map<String, Object> data = getShipMsgData(order, traffic);
        String templateName = propertiesManager.getString("ORDER_SHIP_CANCEL_FAIL_TLE");
        String wxTemplateName = propertiesManager.getString("WX_SHIP_CANCEL_FAIL_TLE");
        if (StringUtils.hasText(templateName)) {
            String msgTemplate = wechatDataTextService.findContentByTitle(templateName);
            // 获取短信内容
            String msgContent = MsgTemplateUtil.createContent(data, msgTemplate);
            if (StringUtils.hasText(msgContent)) {
                // 发送短信
                this.doSendMsg(msgContent, order.getMobile(), user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "船票退订失败短信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "船票退订失败短信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "船票退订失败短信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "船票退订失败短信发送失败!没有在配置文件中找到模板名称");
            return;
        }
        // 微信部分
        if (StringUtils.hasText(wxTemplateName)) {
            String wxMsgTemplate = wechatDataTextService.findContentByTitle(wxTemplateName);
            // 获取微信消息内容
            String wxMsgContent = MsgTemplateUtil.createContent(data, wxMsgTemplate);
            if (StringUtils.hasText(wxMsgContent)) {
                // 发送微信
                this.doSendWXMsg(wxMsgContent, user, order, null);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "船票退订失败微信发送取消,短信内容为空",
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "船票退订失败微信发送取消,短信内容为空");
                return;
            }
        } else {
            OrderLog orderLog = orderLogService.createOrderLog(
                    user,
                    "订单#" + order.getId() + "船票退订失败微信发送失败!没有在配置文件中找到模板名称",
                    order.getId(), null);
            orderLogService.loggingOrderLog(orderLog);
            log.error("订单#" + order.getId() + "船票退订失败微信发送失败!没有在配置文件中找到模板名称");
            return;
        }
    }

    /**
     * 根据游艇帆船、门票订单编辑短信生成验证码，并发送短信
     * @param orderDetail
     */
    public void doSendOrderValidteCodeMsg(OrderDetail orderDetail) {
        Order order = orderDetail.getOrder();

        if (order == null || orderDetail == null) {
            return;
        }
        String needCodeContent = "您已成功购买：";
        needCodeContent += orderDetail.getProduct().getName() + "+";
        if (orderDetail.getStatus() == OrderDetailStatus.SUCCESS) {
            needCodeContent += orderDetail.getSeatType() + "x" + orderDetail.getNum() + "张，";
            String scenicCode = "";
            for (int i = 0; i < orderDetail.getNum(); i++) {
                ProductValidateCode pvCode = new ProductValidateCode();
                pvCode.setOrderId(order.getId());
                pvCode.setOrderNo(order.getOrderNo());
                pvCode.setOrderCount(1);
                pvCode.setProduct(orderDetail.getProduct());
                pvCode.setUsed(0);
                pvCode.setBuyer(order.getUser());
                pvCode.setBuyerName(order.getRecName());
                pvCode.setValidStartTime(DateUtils.getStartDay(orderDetail.getPlayDate(), 0));
                pvCode.setValidEndTime(DateUtils.getEndDay(orderDetail.getPlayDate(), 0));
                pvCode.setBuyerMobile(order.getMobile());
                pvCode.setSupplierName(orderDetail.getProduct().getCompanyUnit().getName());
                pvCode.setSupplierId(orderDetail.getProduct().getCompanyUnit().getId());
                pvCode.setTicketName(orderDetail.getSeatType());
                String code = msgService.checkCode(pvCode);
                scenicCode += code + "、";
                pvCode.setCode(code);
                productValidateCodeService.save(pvCode);
            }
            if (scenicCode.length() > 1) {
                scenicCode = scenicCode.substring(0, scenicCode.length() - 1);
            }
            needCodeContent += "验证码：" + scenicCode + "；";
            msgService.sendOrderSms(order.getOrderNo(), order.getMobile(), needCodeContent);
        }

    }

    /**
     * 组装订单支付成功短信模板数据
     * @param order
     * @return
     */
    private Map<String, Object> getOrderPaySuccessMsgData(Order order, List<OrderDetail> detailList) {
        Map<String, Object> data = new HashMap<String, Object>();
        OrderType orderType = order.getOrderType();
        data.put("order", order);
        if (OrderType.ticket.equals(orderType)) {
            // 门票订单只会有一个订单详情
            OrderDetail orderDetail = detailList.get(0);
            Long costId = orderDetail.getCostId();
            TicketPrice ticketPrice = ticketPriceService.findFullById(costId);
            data.put("orderDetail", orderDetail);
            data.put("ticketPrice", ticketPrice);
            data.put("ticket", ticketPrice.getTicket());
        } else if (OrderType.hotel.equals(orderType)) {
            // 酒店订单只会有一个订单详情
            OrderDetail orderDetail = detailList.get(0);
            Long costId = orderDetail.getCostId();
            HotelPrice hotelPrice = hotelPriceService.findFullById(costId);
            data.put("orderDetail", orderDetail);
            data.put("hotelPrice", hotelPrice);
            data.put("hotel", hotelPrice.getHotel());
        } else if (OrderType.train.equals(orderType)) {
            for (int i = 0; i < detailList.size(); i++) {
                OrderDetail orderDetail = detailList.get(i);
                Long costId = orderDetail.getCostId();
                TrafficPrice trafficPrice = trafficPriceService.findFullById(costId);
                data.put("orderDetail" + i, orderDetail);
                data.put("trafficPrice" + i, trafficPrice);
                data.put("traffic" + i, trafficPrice.getTraffic());
            }
        } else if (OrderType.flight.equals(orderType)) {
            for (int i = 0; i < detailList.size(); i++) {
                OrderDetail orderDetail = detailList.get(i);
                Long costId = orderDetail.getCostId();
                TrafficPrice trafficPrice = trafficPriceService.findFullById(costId);
                data.put("orderDetail" + i, orderDetail);
                data.put("trafficPrice" + i, trafficPrice);
                data.put("traffic" + i, trafficPrice.getTraffic());
            }
        } else if (OrderType.line.equals(orderType)) {
            // 线路订单只有一个详情
            OrderDetail orderDetail = detailList.get(0);
            Long costId = orderDetail.getCostId();
            Linetypeprice linetypeprice = linetypepriceService.findFullById(costId);
            Line line = linetypeprice.getLine();
            data.put("orderDetail", orderDetail);
            data.put("line", line);
        } else if (OrderType.ship.equals(orderType)) {
            for (int i = 0; i < detailList.size(); i++) {
                OrderDetail orderDetail = detailList.get(i);
                Long costId = orderDetail.getCostId();
                TrafficPrice trafficPrice = trafficPriceService.findFullById(costId);
                data.put("orderDetail" + i, orderDetail);
                data.put("trafficPrice" + i, trafficPrice);
                data.put("traffic" + i, trafficPrice.getTraffic());
            }
        }
        return data;
    }

    /**
     * 组装门票短信模板数据
     *
     * @param orderDetail
     * @param ticketPrice
     * @return
     */
    private Map<String, Object> getTicketMsgData(OrderDetail orderDetail, TicketPrice ticketPrice) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderDetail", orderDetail);
        data.put("order", orderDetail.getOrder());
        data.put("user", orderDetail.getOrder().getUser());
        data.put("ticketPrice", ticketPrice);
        data.put("ticket", ticketPrice.getTicket());
        return data;
    }

    /**
     * 组装酒店短信模板数据
     *
     * @param orderDetail
     * @param hotelPrice
     * @return
     */
    private Map<String, Object> getHotelMsgData(OrderDetail orderDetail, HotelPrice hotelPrice) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderDetail", orderDetail);
        data.put("order", orderDetail.getOrder());
        data.put("user", orderDetail.getOrder().getUser());
        data.put("hotelPrice", hotelPrice);
        data.put("hotel", hotelPrice.getHotel());
        return data;
    }

    /**
     * 组装火车票短信模板数据
     *
     * @param orderDetail
     * @param trafficPrice
     * @param passenger
     * @return
     */
    private Map<String, Object> getTrainMsgData(OrderDetail orderDetail, TrafficPrice trafficPrice, CheckOrderResult checkOrderResult, CheckOrderResult.ResultEntity.PassengersEntity passenger) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderDetail", orderDetail);
        data.put("order", orderDetail.getOrder());
        data.put("user", orderDetail.getOrder().getUser());
        data.put("trafficPrice", trafficPrice);
        data.put("traffic", trafficPrice.getTraffic());
        if (checkOrderResult != null) {
            data.put("checkOrderResult", checkOrderResult);
        }
        if (passenger != null) {
            data.put("passenger", passenger);
        }
        return data;
    }

    /**
     * 组装机票短信模板数据
     *
     * @param orderDetail
     * @param trafficPrice
     * @param ticketListEntity
     * @return
     */
    private Map<String, Object> getFlightMsgData(OrderDetail orderDetail, TrafficPrice trafficPrice, TicketResult.ResultEntity.TicketListEntity ticketListEntity) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("orderDetail", orderDetail);
        data.put("order", orderDetail.getOrder());
        data.put("user", orderDetail.getOrder().getUser());
        data.put("trafficPrice", trafficPrice);
        data.put("traffic", trafficPrice.getTraffic());
        if (ticketListEntity != null) {
            data.put("ticket", ticketListEntity);
        }
        return data;
    }

    /**
     * 组装线路短信模板数据
     * @param order
     * @param line
     * @return
     */
    private Map<String, Object> getLineMsgData(Order order, Line line) {
        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("orderDetail", orderDetail);
        data.put("order", order);
        data.put("user", order.getUser());
        data.put("line", line);
        return data;
    }

    private Map<String, Object> getShipMsgData(Order order, Traffic traffic) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("order", order);
        data.put("user", order.getUser());
        data.put("traffic", traffic);
        return data;
    }

    /**
     * 短信发送方法
     * 微信发送方法
     *
     * @param content
     * @param receiver
     * @param user
     * @param orderDetail
     */
    private void doSendMsg(String content, String receiver, User user, Order order, OrderDetail orderDetail) {
        SendingMsg sendingMsg = new SendingMsg();
        if (StringUtils.isMobile(receiver)) {
            sendingMsg.setReceivernum(receiver);
            sendingMsg.setContext(content);
            sendingMsg.setSendtime(new Date());
            sendingMsg.setStatus(SendStatus.newed);
            sendingMsgService.save(sendingMsg);
            if (orderDetail != null) {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "##" + receiver + "短信发送成功, 短信内容: " + content,
                        orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "##" + receiver + "短信发送成功, 短信内容: " + content,
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
            }

        } else {
            if (orderDetail != null) {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "短信发送失败,接收人手机号码不合法, 短信内容: " + content,
                        orderDetail.getOrder().getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "短信发送失败,接收人手机号码不合法");
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "短信发送失败,接收人手机号码不合法, 短信内容: " + content,
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "短信发送失败,接收人手机号码不合法");
            }
        }
    }

    /**
     * 微信发送方法
     * @param content
     * @param user
     * @param order
     * @param orderDetail
     */
    public void doSendWXMsg(String content, User user, Order order, OrderDetail orderDetail) {
        try {
            // 获取微信公众号客服配置信息
            String wxCompanyIds = propertiesManager.getString("WX_COMPANY_ID");
            String wxAccountNames = propertiesManager.getString("WX_ACCOUNT_NAME");
            if (!StringUtils.hasText(wxCompanyIds) || !StringUtils.hasText(wxAccountNames)) {
                if (orderDetail != null) {
                    OrderLog orderLog = orderLogService.createOrderLog(
                            user,
                            "订单详情#" + orderDetail.getId() + "微信发送失败,没有找到companyId或accountName配置, 短信内容: " + content,
                            order.getId(), orderDetail.getId());
                    orderLogService.loggingOrderLog(orderLog);
                    log.error("订单详情#" + orderDetail.getId() + "微信发送失败, 没有找到companyId或accountName配置");
                } else {
                    OrderLog orderLog = orderLogService.createOrderLog(
                            user,
                            "订单#" + order.getId() + "微信发送失败,没有找到companyId或accountName配置, 短信内容: " + content,
                            order.getId(), null);
                    orderLogService.loggingOrderLog(orderLog);
                    log.error("订单#" + order.getId() + "微信发送失败, 没有找到companyId或accountName配置");
                }
                return;
            }
            List<WechatSupportAccount> wechatSupportAccountList = wechatSupportAccountService.getByAccountList(wxCompanyIds, wxAccountNames);
            for (WechatSupportAccount wechatSupportAccount : wechatSupportAccountList) {
                wechatService.doSendTplMessage(wechatSupportAccount.getWechatAccount().getId(), wechatSupportAccount.getOpenId(), NoticeType.order, content, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (orderDetail != null) {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单详情#" + orderDetail.getId() + "微信发送失败,出现异常, 微信消息内容: " + content,
                        order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单详情#" + orderDetail.getId() + "微信发送失败, 出现异常");
            } else {
                OrderLog orderLog = orderLogService.createOrderLog(
                        user,
                        "订单#" + order.getId() + "微信发送失败,出现异常, 微信消息内容: " + content,
                        order.getId(), null);
                orderLogService.loggingOrderLog(orderLog);
                log.error("订单#" + order.getId() + "微信发送失败, 出现异常");
            }

        }
    }
}
