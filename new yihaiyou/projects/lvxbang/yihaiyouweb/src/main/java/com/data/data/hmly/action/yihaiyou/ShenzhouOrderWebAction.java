package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.response.ShenzhouOrderResponse;
import com.data.data.hmly.service.OrderShenzhouService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.ShenzhouOrderService;
import com.data.data.hmly.service.order.entity.ShenzhouOrder;
import com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus;
import com.data.data.hmly.service.order.util.ShenzhouUtil;
import com.data.data.hmly.service.order.util.ShenzhouUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.framework.struts.NotNeedLogin;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.HttpUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

/**
 * Created by huangpeijie on 2016-09-08,0008.
 */
public class ShenzhouOrderWebAction extends BaseAction {
    @Resource
    private OrderShenzhouService orderShenzhouService;
    @Resource
    private ShenzhouOrderService shenzhouOrderService;
    public Member user;
    public Long orderId;
    public Boolean force;
    public String reason;
    public Integer reasonId;
    public Integer score;
    public ShenzhouOrderStatus status;
    public Boolean arrival;
    public Integer pageNo;
    public Integer pageSize;

    @NeedLogin
    @AjaxCheck
    public Result save() {
        user = getLoginUser();
        if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
            return redirectUrlJson(user);
        } else if (user.getShenzhouTokenDate().before(new Date())) {
            refreshToken(user);
            if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
                return redirectUrlJson(user);
            }
        }
        HttpServletRequest request = getRequest();
        Map<String, String[]> map = request.getParameterMap();
        Map<String, Object> orderMap = new TreeMap<String, Object>();
        for (String s : map.keySet()) {
            orderMap.put(s, map.get(s)[0]);
        }
        orderMap.put("access_token", user.getShenzhouAccessToken());
        orderMap.put("companyId", ShenzhouUtil.companyId);
        try {
            String createStr = HttpUtils.post(ShenzhouUtil.shenzhouApi + ShenzhouUrl.CREATE_ORDER, orderMap);
            Map<String, Object> createResult = mapper.readValue(createStr, Map.class);
            if ("SUCCESS".equals(createResult.get("status"))) {
                Map<String, Object> params = Maps.newHashMap();
                params.put("access_token", user.getShenzhouAccessToken());
                String orderId = (String) ((Map) createResult.get("content")).get("orderId");
                ShenzhouOrder shenzhouOrder = new ShenzhouOrder();
                shenzhouOrder.setShenzhouOrderId(orderId);
                shenzhouOrder.setUser(user);
                shenzhouOrder.setTotalPrice(0f);
                shenzhouOrderService.save(shenzhouOrder);
                params.put("orderId", orderId);
                String detailStr = HttpUtils.post(ShenzhouUtil.shenzhouApi + ShenzhouUrl.ORDER_DETAIL, params);
                Map<String, Object> detailResult = new ObjectMapper().readValue(detailStr, Map.class);
                ShenzhouOrder order = orderShenzhouService.saveOrder((Map<String, Object>) detailResult.get("content"), user, shenzhouOrder.getId(), null, 0d);
                if (order == null) {
                    result.put("success", false);
                } else {
                    result.put("success", true);
                    result.put("orderId", order.getId());
                }
            } else {
                result.put("success", false);
                result.put("errMsg", createResult.get("msg"));
            }
        } catch (IOException e) {
            result.put("success", false);
            e.printStackTrace();
        }
        return jsonResult(result);
    }

    @NeedLogin
    @AjaxCheck
    public Result update() throws IOException {
        user = getLoginUser();
        ShenzhouOrder order = shenzhouOrderService.get(orderId);
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
        if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
            return redirectUrlJson(user);
        } else if (user.getShenzhouTokenDate().before(new Date())) {
            refreshToken(user);
            if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
                return redirectUrlJson(user);
            }
        }
        order = orderShenzhouService.updateOrder(order, ShenzhouUtil.shenzhouApi + ShenzhouUrl.ORDER_DETAIL, null, 0d);
        result.put("success", true);
        result.put("order", new ShenzhouOrderResponse(order));
        return jsonResult(result);
    }

    @AjaxCheck
    @NeedLogin
    public Result cancelReason() throws IOException {
        user = getLoginUser();
        if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
            return redirectUrlJson(user);
        } else if (user.getShenzhouTokenDate().before(new Date())) {
            refreshToken(user);
            if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
                return redirectUrlJson(user);
            }
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("access_token", user.getShenzhouAccessToken());
        String detailStr = HttpUtils.post(ShenzhouUtil.shenzhouApi + ShenzhouUrl.CANCEL_REASON, params);
        Map<String, Object> reasonResult = mapper.readValue(detailStr, Map.class);
        List<Map<String, Object>> cancelReasonList = Lists.newArrayList();
        if (reasonResult.get("content") != null) {
            List<Map<String, Object>> content = (List<Map<String, Object>>) reasonResult.get("content");
            for (Map<String, Object> map : content) {
                if (Boolean.valueOf(map.get("arrival").toString()) == arrival) {
                    cancelReasonList.add(map);
                }
            }
        }
        result.put("success", true);
        result.put("cancelReasonList", cancelReasonList);
        return jsonResult(result);
    }

    @AjaxCheck
    @NeedLogin
    public Result cancel() throws IOException {
        user = getLoginUser();
        ShenzhouOrder order = shenzhouOrderService.get(orderId);
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
        if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
            return redirectUrlJson(user);
        } else if (user.getShenzhouTokenDate().before(new Date())) {
            refreshToken(user);
            if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
                return redirectUrlJson(user);
            }
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("access_token", user.getShenzhouAccessToken());
        params.put("orderId", order.getShenzhouOrderId());
        params.put("force", force);
        params.put("reasonId", reasonId);
        String detailStr = HttpUtils.post(ShenzhouUtil.shenzhouApi + ShenzhouUrl.CANCEL_ORDER, params);
        Map<String, Object> detailResult = new ObjectMapper().readValue(detailStr, Map.class);
        Map<String, Object> content = (Map<String, Object>) detailResult.get("content");
        if ((detailResult.get("busiCode") != null && detailResult.get("busiCode").equals("canceled")) || (detailResult.get("isCanceled") != null && Boolean.valueOf(detailResult.get("isCanceled").toString())) || detailResult.get("status").toString().equals("SUCCESS")) {
            Double cost = 0d;
            if (content.get("needPay") != null && Boolean.valueOf(content.get("needPay").toString())) {
                cost = Double.valueOf(content.get("cost").toString());
            }
            order = orderShenzhouService.updateOrder(order, ShenzhouUtil.shenzhouApi + ShenzhouUrl.ORDER_DETAIL, reason, cost);
            result.put("success", true);
            result.put("order", new ShenzhouOrderResponse(order));
            return jsonResult(result);
        } else {
            result.put("success", false);
            if (content.get("needPay") != null && Boolean.valueOf(content.get("needPay").toString())) {
                result.put("needPay", true);
                result.put("cost", content.get("cost"));
            } else {
                result.put("needpay", false);
            }
            return jsonResult(result);
        }
    }

    @AjaxCheck
    @NeedLogin
    public Result detail() throws IOException {
        user = getLoginUser();
        ShenzhouOrder order = shenzhouOrderService.get(orderId);
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
        if (order.getPaymentStatus() == null) {
            if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
                return redirectUrlJson(user);
            } else if (user.getShenzhouTokenDate().before(new Date())) {
                refreshToken(user);
                if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
                    return redirectUrlJson(user);
                }
            }
            order = orderShenzhouService.updateOrder(order, ShenzhouUtil.shenzhouApi + ShenzhouUrl.ORDER_DETAIL, null, 0d);
        }
        result.put("success", true);
        result.put("order", new ShenzhouOrderResponse(order));
        return jsonResult(result);
    }

    @AjaxCheck
    @NeedLogin
    public Result delete() {
        user = getLoginUser();
        ShenzhouOrder order = shenzhouOrderService.get(orderId);
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
        order.setDeleteFlag(true);
        shenzhouOrderService.update(order);
        result.put("success", true);
        return jsonResult(result);
    }

    @AjaxCheck
    @NeedLogin
    public Result list() throws IOException {
        user = getLoginUser();
        ShenzhouOrder shenzhouOrder = new ShenzhouOrder();
        shenzhouOrder.setUser(user);
        Page page = new Page(pageNo, pageSize);
        List<ShenzhouOrder> orderList = shenzhouOrderService.getOrderList(shenzhouOrder, page, "createTime", "desc");
        List<ShenzhouOrderResponse> responses = Lists.newArrayList();
        for (ShenzhouOrder order : orderList) {
            if (order.getPaymentStatus() != null) {
                responses.add(new ShenzhouOrderResponse(order));
                continue;
            }
            if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
                return redirectUrlJson(user);
            } else if (user.getShenzhouTokenDate().before(new Date())) {
                refreshToken(user);
                if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
                    return redirectUrlJson(user);
                }
            }
            order = orderShenzhouService.updateOrder(order, ShenzhouUtil.shenzhouApi + ShenzhouUrl.ORDER_DETAIL, null, 0d);
            responses.add(new ShenzhouOrderResponse(order));
        }
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("success", true);
        result.put("orderList", responses);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    @AjaxCheck
    @NeedLogin
    public Result comment() throws IOException {
        user = getLoginUser();
        if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
            return redirectUrlJson(user);
        } else if (user.getShenzhouTokenDate().before(new Date())) {
            refreshToken(user);
            if (StringUtils.isBlank(user.getShenzhouAccessToken()) || StringUtils.isBlank(user.getShenzhouRefreshToken())) {
                return redirectUrlJson(user);
            }
        }
        ShenzhouOrder order = shenzhouOrderService.get(orderId);
        Map<String, Object> params = Maps.newHashMap();
        params.put("access_token", user.getShenzhouAccessToken());
        params.put("orderId", order.getShenzhouOrderId());
        params.put("score", score);
        String detailStr = HttpUtils.post(ShenzhouUtil.shenzhouApi + ShenzhouUrl.COMMENT_ORDER, params);
        Map<String, Object> commentResult = mapper.readValue(detailStr, Map.class);
        if (commentResult.get("content") != null && commentResult.get("content").toString().equals("success")) {
            order.setScore(score);
            shenzhouOrderService.update(order);
            result.put("success", true);
        } else {
            order = orderShenzhouService.updateOrder(order, ShenzhouUtil.shenzhouApi + ShenzhouUrl.ORDER_DETAIL, null, 0d);
            result.put("success", false);
            result.put("score", order.getScore());
        }
        return jsonResult(result);
    }

    @NotNeedLogin
    public Result orderNotify() {
        HttpServletRequest request = getRequest();
        String q = request.getParameter("q");
        try {
            Map<String, Object> map = mapper.readValue(q, Map.class);
            Map<String, Object> content = (Map<String, Object>) map.get("content");
            String shenzhouOrderId = content.get("orderId").toString();
            ShenzhouOrder shenzhouOrder = new ShenzhouOrder();
            shenzhouOrder.setShenzhouOrderId(shenzhouOrderId);
            final List<ShenzhouOrder> list = shenzhouOrderService.getOrderList(shenzhouOrder, null);
            if (!list.isEmpty()) {
                Member member = list.get(0).getUser();
                if (member.getShenzhouTokenDate().before(new Date())) {
                    refreshToken(member);
                }
                GlobalTheadPool.instance.submit(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        orderShenzhouService.updateOrder(list.get(0), ShenzhouUtil.shenzhouApi + ShenzhouUrl.ORDER_DETAIL, null, 0d);
                        return null;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult(result);
    }
}
