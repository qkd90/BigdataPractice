package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.service.*;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.CmbService;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.LvjiOrderService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.LvjiOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.enums.CmbOrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.pay.CmbPayLogService;
import com.data.data.hmly.service.pay.CmbPayService;
import com.data.data.hmly.service.pay.PayBackObject;
import com.data.data.hmly.service.pay.PayRequest;
import com.data.data.hmly.service.pay.PayService;
import com.data.data.hmly.service.pay.entity.BankInfo;
import com.data.data.hmly.service.pay.entity.CmbPayLog;
import com.data.data.hmly.service.pay.util.httpclient.HttpUtil;
import com.data.data.hmly.util.GenZhBillNo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.MD5;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by huangpeijie on 2017-01-05,0005.
 */
public class PayWebAction extends YhyAction {
    @Resource
    private OrderService orderService;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private MemberService memberService;
    @Resource
    private FerryWebService ferryWebService;
    @Resource
    private OrderWebService orderWebService;
    @Resource
    private CmbPayService cmbPayService;
    @Resource
    private CmbPayLogService cmbPayLogService;
    @Resource
    private LvjiOrderService lvjiOrderService;
    @Resource
    private LvjiWebService lvjiWebService;

    private final PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    private final ObjectMapper mapper = new ObjectMapper();

    private Log log = LogFactory.getLog(PayWebAction.class);

    public Long orderId;
    public OrderType orderType;

    public String payForm;

    public Map<String, Object> orderMap = new HashMap<String, Object>();

    public OrderPayType payWay;
    public String errMsg;

    public String payPassword;
    public String json;
    public Long bankId;

    private static final String CMB_BILL_NO = "cmbBillNo";
    private final String branchId = propertiesManager.getString("BRANCH_ID");
    private final String coNo = propertiesManager.getString("CO_NO");
    private final String cmbPayUrl = propertiesManager.getString("CMB_PAY_URL");
    private final String cmbNotifyUrl = propertiesManager.getString("CMB_NOTIFY_URL");

    public String detailUrl;
    public Map<String, Object> doPayForm() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (OrderType.ferry.equals(orderType)) {
                FerryOrder order = ferryOrderService.getOrder(orderId);
                if (!OrderStatus.WAIT.equals(order.getStatus())) {
                    map.put("success", false);
                    map.put("errMsg", "订单不可支付");
                    return map;
                }
                if (OrderPayType.ULINEWECHAT.equals(payWay)) {
                    if (StringUtils.isNotBlank(order.getWechatCode())) {
                        payForm = order.getWechatCode();
                        map.put("success", true);
                        map.put("payForm", payForm);
                        return map;
                    }
                }
                if (OrderPayType.ULINEALIPAY.equals(payWay)) {
                    if (StringUtils.isNotBlank(order.getAlipayCode())) {
                        payForm = order.getAlipayCode();
                        map.put("success", true);
                        map.put("payForm", payForm);
                        return map;
                    }
                }
                PayManager payManager = PayManager.findService(payWay.name());
                PayService payService = payManager.getPayService();
                PayRequest request = payService.doMakePayRequest(order);

                payForm = request.getPrePay();

                if (OrderPayType.ULINEWECHAT.equals(payWay)) {
                    order.setWechatCode(payForm);
                    ferryOrderService.updateOrder(order);
                }
                if (OrderPayType.ULINEALIPAY.equals(payWay)) {
                    order.setAlipayCode(payForm);
                    ferryOrderService.updateOrder(order);
                }
            } else if (OrderType.lvji.equals(orderType)) {
                LvjiOrder order = lvjiOrderService.query(orderId);
                if (!OrderStatus.WAIT.equals(order.getStatus())) {
                    map.put("success", false);
                    map.put("errMsg", "订单不可支付");
                    return map;
                }
//                if (OrderPayType.ULINEWECHAT.equals(payWay)) {
//                    if (StringUtils.isNotBlank(order.getWechatCode())) {
//                        payForm = order.getWechatCode();
//                        map.put("success", true);
//                        map.put("payForm", payForm);
//                        return map;
//                    }
//                }
//                if (OrderPayType.ULINEALIPAY.equals(payWay)) {
//                    if (StringUtils.isNotBlank(order.getAlipayCode())) {
//                        payForm = order.getAlipayCode();
//                        map.put("success", true);
//                        map.put("payForm", payForm);
//                        return map;
//                    }
//                }
                PayManager payManager = PayManager.findService(payWay.name());
                PayService payService = payManager.getPayService();
                PayRequest request = payService.doMakePayRequest(order);

                payForm = request.getPrePay();

//                if (OrderPayType.ULINEWECHAT.equals(payWay)) {
//                    order.setWechatCode(payForm);
//                    ferryOrderService.updateOrder(order);
//                }
//                if (OrderPayType.ULINEALIPAY.equals(payWay)) {
//                    order.setAlipayCode(payForm);
//                    ferryOrderService.updateOrder(order);
//                }
            } else {
                Order order = orderService.get(orderId);
                if (!OrderStatus.WAIT.equals(order.getStatus())) {
                    map.put("success", false);
                    map.put("errMsg", "订单不可支付");
                    return map;
                }
                if (OrderPayType.ULINEWECHAT.equals(payWay)) {
                    if (StringUtils.isNotBlank(order.getWechatCode())) {
                        payForm = order.getWechatCode();
                        map.put("success", true);
                        map.put("payForm", payForm);
                        return map;
                    }
                }
                if (OrderPayType.ULINEALIPAY.equals(payWay)) {
                    if (StringUtils.isNotBlank(order.getAlipayCode())) {
                        payForm = order.getAlipayCode();
                        map.put("success", true);
                        map.put("payForm", payForm);
                        return map;
                    }
                }
                PayManager payManager = PayManager.findService(payWay.name());
                PayService payService = payManager.getPayService();
                PayRequest request = payService.doMakePayRequest(order);

                payForm = request.getPrePay();

                if (OrderPayType.ULINEWECHAT.equals(payWay)) {
                    order.setWechatCode(payForm);
                    orderService.update(order);
                }
                if (OrderPayType.ULINEALIPAY.equals(payWay)) {
                    order.setAlipayCode(payForm);
                    orderService.update(order);
                }
            }

            map.put("success", true);
            map.put("payForm", payForm);
            return map;

        } catch (Exception e) {
            //
            map.put("success", false);
            map.put("errMsg", "创建支付链接失败");
            return map;
        }
    }

    public Result aliPay() {
        payWay = OrderPayType.ULINEALIPAY;
        Map<String, Object> map = doPayForm();
        return jsonResult(map);
    }

    public Result wechatPay() {
        getLoginUser();
        payWay = OrderPayType.ULINEWECHAT;
        Map<String, Object> map = doPayForm();
        return jsonResult(map);
    }

    public Result aliPayBack() {
        log.warn("aliPayBack............................");
        HttpServletRequest request = getRequest();
        Map<String, String> map = HttpUtil.parseXml(request);
        Map<String, String> orderMap = new TreeMap<String, String>();
        String sign = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if ("sign".equals(entry.getKey())) {
                sign = entry.getValue();
                continue;
            }
            if (StringUtils.isNotBlank(entry.getValue())) {
                orderMap.put(entry.getKey(), entry.getValue());
            }
        }
        // 临时放入sign
        orderMap.put("sign", sign);

        String orderNo = orderMap.get("out_trade_no").toString();
        PayManager payManager = PayManager.findService(OrderPayType.ULINEALIPAY.name());
        PayService payService = payManager.getPayService();
        PayBackObject payBackObject;

        Order order = orderService.getOrderByNo(orderNo);
        if (order == null) {
            FerryOrder ferryOrder = ferryOrderService.getOrderByNo(orderNo);
            if (ferryOrder.getStatus() == OrderStatus.SUCCESS) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("return_code", "SUCCESS");
                return jsonResult(resultMap);
            }
            payBackObject = payService.doMakeBackObject(orderMap, ferryOrder);
        } else {
            if (order.getStatus() == OrderStatus.SUCCESS) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("return_code", "SUCCESS");
                return jsonResult(resultMap);
            }
            payBackObject = payService.doMakeBackObject(orderMap, order);
        }
        return jsonResult(payBackObject.getResult());
    }

    public Result weixinPayBack() {
        log.warn("weixinPayBack............................");
        HttpServletRequest request = getRequest();
        Map<String, String> map = HttpUtil.parseXml(request);
        Map<String, String> orderMap = new TreeMap<String, String>();
        String sign = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if ("sign".equals(entry.getKey())) {
                sign = entry.getValue();
                continue;
            }
            if (StringUtils.isNotBlank(entry.getValue())) {
                orderMap.put(entry.getKey(), entry.getValue());
            }
        }
        // 临时放入sign
        orderMap.put("sign", sign);

        String attach = orderMap.get("attach").toString();
        Long orderId = Long.valueOf(attach.split("-")[0]);
        PayManager payManager = PayManager.findService(OrderPayType.ULINEWECHAT.name());
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
            ferryWebService.saveBalancePay(order, user);
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
            lvjiWebService.saveBalancePay(order, user);
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
            orderWebService.saveBalancePay(order, user);
        }
        member.setBalance(user.getBalance());
        getSession().setAttribute("LOGIN_USER", member);
        result.put("success", true);
        return jsonResult(result);
    }

    public Result cmbPay() {
        Order order = orderService.get(orderId);
        order.setBillNo(GenZhBillNo.generate(propertiesManager.getString("MACHINE_NO", "")));
        order.setCmbStatus(CmbOrderStatus.wait);
        order.setCmbTime(DateUtils.format(new Date(), "yyyyMMdd"));
        orderService.update(order);
        return redirect(cmbPayUrl + "?BranchID=" + branchId + "&CoNo=" + coNo + "&BillNo=" + order.getBillNo()
                + "&Amount=" + order.getPrice() + "&Date=" + order.getCmbTime() + "&MerchantUrl=" + cmbNotifyUrl + "&MerchantPara=" + order.getId());
    }

    public Result cmbPayBack() {
        HttpServletRequest request = getRequest();
        Map<String, String[]> map = request.getParameterMap();
        Map<String, String> orderMap = new TreeMap<String, String>();
        for (String s : map.keySet()) {
            orderMap.put(s, map.get(s)[0]);
        }
        CmbService cmbService = new CmbService();
        if (!cmbService.checkInfoFromBank(request.getQueryString())) {
            return redirect("/lvxbang/lxbPay/request.jhtml?orderId=" + orderMap.get("MerchantPara"));
        }
        Map<String, Object> result = cmbService.doPayBack(orderMap);
        if (!(Boolean) result.get("success")) {
            return redirect("/lvxbang/lxbPay/request.jhtml?orderId=" + orderMap.get("MerchantPara"));
        } else {
            return redirect((String) result.get("url"));
        }
    }

    public Result cmbWithdraw() throws IOException {
        Member user = getLoginUser();
        BankInfo bankInfo;
        if (bankId != null && bankId > 0) {
            CmbPayLog cmbPayLog = cmbPayLogService.get(bankId);
            if (cmbPayLog == null || !cmbPayLog.getUser().getId().equals(user.getId())) {
                result.put("success", false);
                return jsonResult(result);
            }
            bankInfo = new BankInfo();
            bankInfo.setBankNo(cmbPayLog.getBankNo());
            bankInfo.setBankName(cmbPayLog.getBankName());
            bankInfo.setHolderName(cmbPayLog.getHolderName());
            bankInfo.setProvince(cmbPayLog.getProvince());
            bankInfo.setCity(cmbPayLog.getCity());
        } else {
            bankInfo = mapper.readValue(json, BankInfo.class);
        }
        Order order = orderService.get(orderId);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            result.put("success", false);
            return jsonResult(result);
        }
        Map<String, Object> resultMap = cmbPayService.doWithdrawRequest(bankInfo, order);
        result.put("success", resultMap.get("success"));
        if (!(Boolean) result.get("success")) {
            result.put("errMsg", "提现失败");
        }
        return jsonResult(result);
    }

    public Result lastCmbLog() {
        Member user = getLoginUser();
        if (user == null) {
            result.put("success", false);
            return jsonResult(result);
        }
        CmbPayLog search = new CmbPayLog();
        search.setUser(user);
        search.setSuccess(true);
        List<CmbPayLog> cmbPayLogs = cmbPayLogService.list(search, new Page(1, 1), "createTime", "desc");
        if (cmbPayLogs.isEmpty()) {
            result.put("success", false);
            return jsonResult(result);
        }
        CmbPayLog cmbPayLog = cmbPayLogs.get(0);
        JSONObject bank = new JSONObject();
        bank.put("id", cmbPayLog.getId());
        bank.put("bankName", cmbPayLog.getBankName());
        bank.put("bankNo", "************" + cmbPayLog.getBankNo().substring(cmbPayLog.getBankNo().length() - 4));
        result.put("success", true);
        result.put("bank", bank);
        return jsonResult(result);
    }
}
