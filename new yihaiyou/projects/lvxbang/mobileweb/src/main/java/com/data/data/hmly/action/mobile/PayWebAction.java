package com.data.data.hmly.action.mobile;

import com.data.data.hmly.service.PayManager;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.enums.LineConfirmAndPayType;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.pay.PayBackObject;
import com.data.data.hmly.service.pay.PayRequest;
import com.data.data.hmly.service.pay.PayService;
import com.data.data.hmly.service.pay.WxService;
import com.data.data.hmly.service.pay.util.httpclient.HttpUtil;
import com.data.data.hmly.service.user.ThirdPartyUserService;
import com.data.data.hmly.service.user.entity.ThirdPartyUser;
import com.data.data.hmly.service.user.entity.enums.ThirdPartyUserType;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.framework.struts.NeedLogin;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class PayWebAction extends MobileBaseAction {

    @Resource
    private OrderService orderService;
    @Resource
    private WechatAccountService wechatAccountService;
    @Resource
    private WxService wxService;
    @Resource
    private ThirdPartyUserService thirdPartyUserService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private BalanceService balanceService;
    @Resource
    private LineService lineService;
    private Long id;
    private Double cost;
    private Long orderId;
    public static WechatAccount wechatAccount;
    public static String filePath;
    private Log log = LogFactory.getLog(PayWebAction.class);

    @PostConstruct
    public void init() {
        propertiesManager = SpringContextHolder.getBean("propertiesManager");
        wechatAccountService = SpringContextHolder.getBean("wechatAccountService");
        wechatAccount = wechatAccountService.get(propertiesManager.getLong("WEBCHAT_ACCOUNT_ID"));
        filePath = propertiesManager.getString("CERT_DIR");
    }

    public Result checkStatus() {
        Order order = orderService.get(orderId);
        simpleResult(result, Boolean.valueOf(true), order.getStatus().name());
        result.put("type", order.getOrderType().name());
        result.put("cost", order.getPrice());
        return jsonResult(result);
    }

    @NeedLogin
    public Result getPrepayId() throws LoginException {
        Member member = getLoginUser();
        Order order = orderService.get(orderId);
        PayManager payManager = PayManager.findService("weixin");
        PayService payService = payManager.getPayService();
//        ThirdPartyUser thirdUser = thirdPartyUserService.getByUserIdAndType(member.getId(), ThirdPartyUserType.weixin, wechatAccount.getId());
        String ip = getRequest().getHeader("x-real-ip");
        if (!StringUtils.isNotBlank(ip))
            ip = getRequest().getRemoteAddr();
        Map map = payService.doMakePayRequest(order, wechatAccount, member.getCurrLoginOpenId(), ip);
        map.put("orderId", order.getId());
        return jsonResult(map);
    }

    @NeedLogin
    public Result getAppPrepayId() throws LoginException {
        Member member = getLoginUser();
        Order order = orderService.get(orderId);
        PayManager payManager = PayManager.findService("weixin");
        PayService payService = payManager.getPayService();
        ThirdPartyUser thirdUser = thirdPartyUserService.getByUserIdAndType(member.getId(), ThirdPartyUserType.weixin, wechatAccount.getId());
        String ip = getRequest().getHeader("x-real-ip");
        if (!StringUtils.isNotBlank(ip))
            ip = getRequest().getRemoteAddr();
        Map map = payService.doMakeAppPayRequest(order, wechatAccount, thirdUser.getOpenId(), ip);
        map.put("orderId", order.getId());
        return jsonResult(map);
    }

    @NeedLogin
    public Result doTransfers() throws LoginException {
        Member member = getLoginUser();
        Order order = orderService.get(orderId);
        PayManager payManager = PayManager.findService("weixin");
        PayService payService = payManager.getPayService();
        ThirdPartyUser thirdUser = thirdPartyUserService.getByUserIdAndType(member.getId(), ThirdPartyUserType.weixin, wechatAccount.getId());
        String ip = getRequest().getHeader("x-real-ip");
        if (!StringUtils.isNotBlank(ip))
            ip = getRequest().getRemoteAddr();
        Map map = payService.doTransfersRequest(order, wechatAccount, thirdUser.getOpenId(), ip, filePath);
        if (Boolean.valueOf(map.get("success").toString())) {
            order.setStatus(OrderStatus.SUCCESS);
        } else {
            order.setStatus(OrderStatus.FAILED);
            if ("NOTENOUGH".equals(map.get("errCode").toString())) {
                balanceService.doSendWXMsg(wechatAccount.getAccount() + "企业付款余额不足", wechatAccount);
            }
        }
        balanceService.savePayResult(order, AccountType.withdraw);
        map.put("orderId", order.getId());
        return jsonResult(map);
    }

    public Map<String, Object> doPayForm(String payWay) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Order order = orderService.get(orderId);
            PayManager payManager = PayManager.findService(payWay);
            PayService payService = payManager.getPayService();
            PayRequest request = payService.doMakePayRequest(order);
            String payForm = request.getPrePay();
            map.put("success", true);
            map.put("payForm", payForm);
            return map;
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", "创建支付链接失败");
            return map;
        }
    }


    public Result alinativePay() {
//        Map<String, Object> map = doPayForm("taobao");
//        return jsonResult(map);
        Map<String, Object> map = new HashMap<String, Object>();
        Object wayObj = getParameter("way");
        if (wayObj != null) {
            if ("alipay".equalsIgnoreCase(wayObj.toString())) {
                try {
                    Order order = orderService.get(orderId);
                    PayManager payManager = PayManager.findService("taobao");
                    PayService payService = payManager.getPayService();
                    Map<String, String> request = payService.doMakePayRequestMap(order);
                    StringBuilder sb = new StringBuilder();
                    for (Map.Entry<String, String> entry : request.entrySet()) {
                        if (sb.length() > 0) {
                            sb.append("&").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
                        } else {
                            sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
                        }
                    }
                    map.put("success", true);
                    map.put("payStr", sb.toString());
                    LOG.info(sb.toString());
                    return jsonResult(map);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    map.put("success", false);
                    map.put("errorMsg", "创建支付链接失败");
                    return jsonResult(map);
                }
            } else if ("wxpay".equalsIgnoreCase(wayObj.toString())) {
                try {
                    Order order = orderService.get(orderId);
                    PayManager payManager = PayManager.findService("weixin");
                    PayService payService = payManager.getPayService();
                    Map<String, String> request = payService.doMakePayRequestMap(order);
//                    StringBuilder sb = new StringBuilder();
//                    for (Map.Entry<String, String> entry : request.entrySet()) {
//                        if (sb.length() > 0) {
//                            sb.append("&").append(entry.getKey()).append(":\"").append(entry.getValue().toString()).append("\"");
//                        } else {
//                            sb.append(entry.getKey()).append(":\"").append(entry.getValue().toString()).append("\"");
//                        }
//                    }
                    map.put("success", true);
                    JSONObject params = JSONObject.fromObject(request);
                    map.put("payStr", params);
                    LOG.info(params.toString());
                    return jsonResult(map);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    map.put("success", false);
                    map.put("errorMsg", "创建支付链接失败");
                    return jsonResult(map);
                }
            }
        }
        return jsonResult(map);
    }

    public Result getPayConfig()
            throws Exception {
        String prepayId = (String) getParameter("prepayId");
        Map resultMap = wxService.doMakeConfig(wechatAccount.getId(), prepayId);
        resultMap.put("success", Boolean.valueOf(true));
        return json(JSONObject.fromObject(resultMap));
    }

    public Result payBack() {
        System.out.println("Back............1");
        HttpServletRequest request = getRequest();
        Map<String, String> params = getNotifyParam(request);
        Order order = null;
        if (params.get("out_trade_no") != null) {
            long orderId = Long.parseLong(params.get("out_trade_no").toString());
            order = orderService.get(orderId);
        }

        PayManager payManager = PayManager.findService("taobao");
        PayService payService = payManager.getPayService();
        PayBackObject payBackObject = payService.doMakeBackObject(params, order);
        orderService.processHasComment(order);
        orderService.update(order);
        return jsonResult(payBackObject.getResult());
    }

    //获取支付宝反馈信息
    private Map<String, String> getNotifyParam(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        String name = "";
        String[] values = null;
        String valueStr = "";
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            name = (String) iter.next();
            values = (String[]) requestParams.get(name);
            valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }

            params.put(name, valueStr);
        }

        // 去除额外的自定义参数，否则验证失败
        params.remove("appKey");

        return params;
    }

    public Result getAppPayConfig()
            throws Exception {
        String prepayId = (String) getParameter("prepayId");
        Map resultMap = wxService.doMakeAppConfig(wechatAccount.getId(), prepayId);
        resultMap.put("success", Boolean.valueOf(true));
        return json(JSONObject.fromObject(resultMap));
    }

    @NotNeedLogin
    public Result weixinPayBack() {
        log.info(String.format("weixinPayBack....................."));
        HttpServletRequest request = getRequest();
        Map<String, String> map = HttpUtil.parseXml(request);
        Map<String, String> orderMap = new TreeMap<String, String>();
        String sign = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if ("sign".equals(entry.getKey())) {
                sign = entry.getValue();
                continue;
            }
            orderMap.put(entry.getKey(), entry.getValue());
        }
        // 临时放入sign
        orderMap.put("sign", sign);

        Order order = null;
        if (orderMap.get("attach") != null) {
            Long orderId = Long.valueOf(orderMap.get("attach").toString());
            order = orderService.get(orderId);
            if (order.getStatus() == OrderStatus.PAYED) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("return_code", "SUCCESS");
                if (OrderType.line.equals(order.getOrderType())) {
                    Line line = lineService.loadLine(order.getOrderDetails().get(0).getProduct().getId());
                    if (LineConfirmAndPayType.confirm.equals(line.getConfirmAndPay())) {
                        order.setStatus(OrderStatus.SUCCESS);
                        orderService.update(order);
                    }
                    orderService.createContract(order.getId(), "微信支付", DateUtils.formatDate(new Date()));
                    line.setOrderSum(line.getOrderSum() + 1);
                    lineService.update(line);
                } else if (OrderType.recharge.equals(order.getOrderType())) {
                    order.setStatus(OrderStatus.SUCCESS);
                    balanceService.savePayResult(order, AccountType.recharge);
                }
                return jsonResult(resultMap);
            }
        }
        PayManager payManager = PayManager.findService("weixin");
        PayService payService = payManager.getPayService();
        PayBackObject payBackObject = payService.doMakeBackObject(orderMap, order);
        orderService.processHasComment(order);
        orderService.update(order);
        return jsonResult(payBackObject.getResult());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


}
