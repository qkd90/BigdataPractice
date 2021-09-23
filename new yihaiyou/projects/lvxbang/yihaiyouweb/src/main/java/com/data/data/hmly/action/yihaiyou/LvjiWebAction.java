package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.request.OrderUpdateRequest;
import com.data.data.hmly.action.yihaiyou.request.SimpleTourist;
import com.data.data.hmly.action.yihaiyou.response.FerryOrderResponse;
import com.data.data.hmly.action.yihaiyou.response.OrderSimpleResponse;
import com.data.data.hmly.service.LvjiMobileService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.order.LvjiOrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.FerryOrderItem;
import com.data.data.hmly.service.order.entity.LvjiOrder;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.dao.TicketDao;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.util.GenOrderNo;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Think on 2017/8/18.
 */
public class LvjiWebAction extends BaseAction {

    @Resource
    private LvjiOrderService lvjiOrderService;
    @Resource
    private TicketService ticketService;
    @Resource
    private LvjiMobileService lvjiMobileService;

    public Member user;
    public String contactJson;
    public Long orderId;

    /**
     * 保存订单
     *
     * @return
     */
    @AjaxCheck
    @NeedLogin
    public Result saveOrder() throws Exception {

        user = getLoginUser();
        if (UserStatus.blacklist.equals(user.getStatus())) {
            result.put("success", false);
            result.put("errMsg", "该用户在黑名单中");
            return jsonResult(result);
        }
        Map<String, Object> data = mapper.readValue(contactJson, Map.class);
        LvjiOrder order = lvjiMobileService.setValueForOrder(Long.valueOf(data.get("productId").toString()), user);
        order.setUserName(data.get("name").toString());
        order.setMobile(data.get("mobile").toString());
        lvjiOrderService.saveOrder(order);
        result.put("orderId", order.getId());
        result.put("suceess", true);
        return jsonResult(result);
    }

    /**
     * 查询订单信息
     */
    @NeedLogin
    @AjaxCheck
    public Result orderInfo() {

        user = getLoginUser();
        LvjiOrder order = lvjiOrderService.query(orderId);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        OrderSimpleResponse response = lvjiMobileService.orderDetail(orderId);
        result.put("response", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    /**
     * 取消订单
     */
    @NeedLogin
    @AjaxCheck
    public Result cancelOrder() {
        user = getLoginUser();
        LvjiOrder order = lvjiOrderService.query(orderId);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        if (OrderStatus.CANCELED.equals(order.getStatus())) {
            result.put("success", true);
            return jsonResult(result);
        }
        result = lvjiMobileService.cancelOrder(order);
        return jsonResult(result);
    }

    //申请退款
    @NeedLogin
    @AjaxCheck
    public Result refundOrder() {
        user = getLoginUser();
        LvjiOrder order = lvjiOrderService.query(orderId);
        if (order == null) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        if (!order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            result.put("errMsg", "订单不存在");
            return jsonResult(result);
        }
        if (!OrderStatus.PAYED.equals(order.getStatus())) {
            result.put("success", false);
            result.put("errMsg", "订单状态为" + order.getStatus().getDescription() + "，不能申请退款。");
            return jsonResult(result);
        }
        result = lvjiOrderService.doRefundLjOrder(order, user);
        if ("0".equals(result.get("status"))) {
            OrderSimpleResponse response = lvjiMobileService.orderDetail(order.getId());
            response.setOrderStatus(order.getStatus());
            result.put("order", response);
            result.put("success", true);
        }
        return jsonResult(result);
    }
}
