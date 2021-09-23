package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.enums.ThirdPartyUserType;
import com.data.data.hmly.service.FerryMobileService;
import com.data.data.hmly.service.LvjiMobileService;
import com.data.data.hmly.service.OrderMobileService;
import com.data.data.hmly.service.PayManager;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.MsgService;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.enums.CombineType;
import com.data.data.hmly.service.line.entity.enums.LineConfirmAndPayType;
import com.data.data.hmly.service.order.*;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.LvjiOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderCostType;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.pay.PayBackObject;
import com.data.data.hmly.service.pay.PayLogService;
import com.data.data.hmly.service.pay.PayRequest;
import com.data.data.hmly.service.pay.PayService;
import com.data.data.hmly.service.pay.WxService;
import com.data.data.hmly.service.pay.util.httpclient.HttpUtil;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.user.ThirdPartyUserService;
import com.data.data.hmly.service.user.entity.ThirdPartyUser;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.framework.struts.NeedLogin;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.MD5;
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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PayWebAction extends BaseAction {

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
    @Resource
    private PayLogService payLogService;
    @Resource
    private TicketService ticketService;
    @Resource
    private MsgService msgService;
    @Resource
    private YhyOrderService yhyOrderService;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private FerryMobileService ferryMobileService;
    @Resource
    private OrderMobileService orderMobileService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderValidateCodeService orderValidateCodeService;
    @Resource
    private LvjiOrderService lvjiOrderService;
    @Resource
    private LvjiMobileService lvjiMobileService;
    private Long id;
    private Double cost;
    private Long orderId;
    public String payPassword;
    public OrderType orderType;
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
        PayManager payManager = PayManager.findService(OrderPayType.WECHAT.name());
        PayService payService = payManager.getPayService();
//        ThirdPartyUser thirdUser = thirdPartyUserService.getByUserIdAndType(member.getId(), ThirdPartyUserType.weixin, wechatAccount.getId());
        String ip = getRequest().getHeader("x-real-ip");
        if (!StringUtils.isNotBlank(ip))
            ip = getRequest().getRemoteAddr();
        Map map;
        if (OrderType.ferry.equals(orderType)) {
            FerryOrder order = ferryOrderService.getOrder(orderId);
            map = payService.doMakePayRequest(order, wechatAccount, member.getCurrLoginOpenId(), ip);
            map.put("orderId", order.getId());
        } else if (OrderType.lvji.equals(orderType)) {
                    LvjiOrder lvjiOrder = lvjiOrderService.query(orderId);
            map = payService.doMakePayRequest(lvjiOrder, wechatAccount, member.getCurrLoginOpenId(), ip);
            map.put("orderId", lvjiOrder.getId());
        } else {
            Order order = orderService.get(orderId);
            map = payService.doMakePayRequest(order, wechatAccount, member.getCurrLoginOpenId(), ip, OrderType.recharge.equals(orderType));
            map.put("orderId", order.getId());
        }
        return jsonResult(map);
    }

    @NeedLogin
    public Result getAppPrepayId() throws LoginException {
        Member member = getLoginUser();
        PayManager payManager = PayManager.findService(OrderPayType.WECHAT.name());
        PayService payService = payManager.getPayService();
        ThirdPartyUser thirdUser = thirdPartyUserService.getByUserIdAndType(member.getId(), ThirdPartyUserType.weixin, wechatAccount.getId());
        String ip = getRequest().getHeader("x-real-ip");
        if (!StringUtils.isNotBlank(ip))
            ip = getRequest().getRemoteAddr();
        Map map;
        if (OrderType.ferry.equals(orderType)) {
            FerryOrder order = ferryOrderService.getOrder(orderId);
            map = payService.doMakeAppPayRequest(order, wechatAccount, thirdUser.getOpenId(), ip);
            map.put("orderId", order.getId());
        } else {
            Order order = orderService.get(orderId);
            map = payService.doMakeAppPayRequest(order, wechatAccount, thirdUser.getOpenId(), ip);
            map.put("orderId", order.getId());
        }
        return jsonResult(map);
    }

    @NeedLogin
    public Result doTransfers() throws LoginException {
        Member member = getLoginUser();
        Order order = orderService.get(orderId);
        if (order == null || !OrderType.withdraw.equals(order.getOrderType())) {
            result.put("success", false);
            return jsonResult(result);
        }
        PayManager payManager = PayManager.findService(OrderPayType.WECHAT.name());
        PayService payService = payManager.getPayService();
        ThirdPartyUser thirdUser = thirdPartyUserService.getByUserIdAndType(member.getId(), ThirdPartyUserType.weixin, wechatAccount.getId());
        String ip = getRequest().getHeader("x-real-ip");
        if (!StringUtils.isNotBlank(ip))
            ip = getRequest().getRemoteAddr();
        Map map = payService.doTransfersRequest(order, wechatAccount, thirdUser.getOpenId(), ip, filePath);
        if (Boolean.valueOf(map.get("success").toString())) {
            order.setStatus(OrderStatus.SUCCESS);
        } else {
            order.setStatus(OrderStatus.INVALID);
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
        log.warn("Back............4");
        HttpServletRequest request = getRequest();
        Map<String, String> params = getNotifyParam(request);
        Order order = null;
        if (params.get("out_trade_no") != null) {
            long orderId = Long.parseLong(params.get("out_trade_no").toString());
            order = orderService.get(payLogService.get(orderId).getOrderId());
        }

        PayManager payManager = PayManager.findService("taobao");
        PayService payService = payManager.getPayService();
        // 判断是否已经支付成功，如果是直接返回
        if (OrderStatus.PAYED == order.getStatus()) {
            PayBackObject payBackObject = payService.doMakeBackObject();
            if (OrderType.line.equals(order.getOrderType())) {
                Line line = lineService.loadLine(order.getOrderDetails().get(0).getProduct().getId());
                if (LineConfirmAndPayType.confirm.equals(line.getConfirmAndPay()) && !CombineType.combine.equals(line.getCombineType())) {
                    order.setStatus(OrderStatus.SUCCESS);
                    orderService.update(order);
                }
                orderService.createContract(order.getId(), "支付宝", DateUtils.formatDate(new Date()));
                line.setOrderSum(line.getOrderSum() + 1);
                lineService.update(line);
            } else if (OrderType.recharge.equals(order.getOrderType())) {
                order.setStatus(OrderStatus.SUCCESS);
                balanceService.savePayResult(order, AccountType.recharge);
            }
            return jsonResult(payBackObject.getResult());
        }

        PayBackObject payBackObject = payService.doMakeBackObject(params, order);
        orderService.update(order);
        // 成功后发送验票（门票）短信
        try {
            // 根据产品发送验证码
            List<OrderDetail> details = order.getOrderDetails();
            Set<Long> productIdSet = new HashSet<Long>();
            for (OrderDetail detail : details) {
                if (OrderCostType.scenic == detail.getCostType()) {
                    productIdSet.add(detail.getProduct().getId());
                }
            }
            Iterator<Long> its = productIdSet.iterator();
            while (its.hasNext()) {
                Long id = its.next();
                ProductValidateCode productValidateCode = new ProductValidateCode();
                Ticket ticket = ticketService.findTicketById(id);
                productValidateCode.setProduct(ticket);
                productValidateCode.setUsed(0);
                productValidateCode.setCreateTime(new Date());
                productValidateCode.setBuyerName(order.getRecName());
                productValidateCode.setBuyerMobile(order.getMobile());
                productValidateCode.setBuyer(order.getUser());
                productValidateCode.setOrderNo(String.valueOf(order.getId()));
                productValidateCode.setOrderCount(details.size());
                msgService.addAndSendMsgCode(productValidateCode);
//				productValidateCodeService.save(productValidateCode);
//				String content = "您预订的门票["+ticket.getName()+"]验证码为：" + num + "验票使用，请妥善保存。";
//				sendSms(order.getMobile(), content);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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
        log.warn("Back............5");
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

        String attach = orderMap.get("attach").toString();
        Long orderId = Long.valueOf(attach.split("-")[0]);
        PayManager payManager = PayManager.findService(OrderPayType.WECHAT.name());
        PayService payService = payManager.getPayService();
        PayBackObject payBackObject;
        if (OrderType.ferry.name().equals(attach.split("-")[1])) {
            FerryOrder ferryOrder = ferryOrderService.getOrder(orderId);
            if (ferryOrder.getStatus() == OrderStatus.SUCCESS) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("return_code", "SUCCESS");
                return jsonResult(resultMap);
            }
            payBackObject = payService.doMakeBackObject(orderMap, ferryOrder);
        } else {
            Order order = orderService.get(orderId);
            if (order.getStatus() == OrderStatus.SUCCESS) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("return_code", "SUCCESS");
                return jsonResult(resultMap);
            }
            payBackObject = payService.doMakeBackObject(orderMap, order);
        }
        return jsonResult(payBackObject.getResult());
    }

    @NeedLogin
    public Result payBalance() {
        Member member = getLoginUser();
        Member user = memberService.get(member.getId());
        if (StringUtils.isBlank(user.getPayPassword()) || StringUtils.isBlank(payPassword) || !user.getPayPassword().equals(MD5.caiBeiMD5(payPassword))) {
            result.put("success", false);
            result.put("errMsg", "支付密码错误");
            return jsonResult(result);
        }
        if (OrderType.ferry.equals(orderType)) {
            FerryOrder order = ferryOrderService.getOrder(orderId);
            if (!order.getStatus().equals(OrderStatus.WAIT)) {
                result.put("success", false);
                result.put("errMsg", "订单无法支付");
                return jsonResult(result);
            }
            if (user.getBalance() < order.getAmount()) {
                result.put("success", false);
                result.put("errMsg", "用户余额不足");
                return jsonResult(result);
            }
            ferryMobileService.saveBalancePay(order, user);
        } else if (OrderType.lvji.equals(orderType)) {

            LvjiOrder order = lvjiOrderService.query(orderId);
            if (!order.getStatus().equals(OrderStatus.WAIT)) {
                result.put("success", false);
                result.put("errMsg", "订单无法支付");
                return jsonResult(result);
            }
            if (user.getBalance() < order.getPrice()) {
                result.put("success", false);
                result.put("errMsg", "用户余额不足");
                return jsonResult(result);
            }
            lvjiMobileService.saveBalancePay(order, user);
        } else {
            Order order = orderService.get(orderId);
            if (!order.getStatus().equals(OrderStatus.WAIT)) {
                result.put("success", false);
                result.put("errMsg", "订单无法支付");
                return jsonResult(result);
            }
            if (user.getBalance() < order.getPrice()) {
                result.put("success", false);
                result.put("errMsg", "用户余额不足");
                return jsonResult(result);
            }
            orderMobileService.saveBalancePay(order, user);

        }

        member.setBalance(user.getBalance());
        getSession().setAttribute(UserConstants.CURRENT_LOGIN_USER, member);
        result.put("success", true);
        return jsonResult(result);
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
