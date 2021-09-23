package com.data.data.hmly.action.pay;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.PayManager;
import com.data.data.hmly.service.SendingMsgService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.MsgService;
import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.enums.CombineType;
import com.data.data.hmly.service.line.entity.enums.LineConfirmAndPayType;
import com.data.data.hmly.service.order.CmbService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderCostType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.pay.*;
import com.data.data.hmly.service.pay.util.httpclient.HttpUtil;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.opensymphony.xwork2.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class PayWebAction extends FrameBaseAction {

    private long orderId;

    private Order order = new Order();

    private String payForm;

    private Map<String, Object> orderMap = new HashMap<String, Object>();

    @Resource
    private OrderService orderService;
    @Resource
    private PayLogService payLogService;
    @Resource
    private WxService wxService;
    @Resource
    private SendingMsgService sendingMsgService;
    @Resource
    private ProductValidateCodeService productValidateCodeService;
    @Resource
    private TicketService ticketService;
    @Resource
    private MsgService msgService;
    @Resource
    private LineService lineService;
    @Resource
    private BalanceService balanceService;
    private Boolean expired;


    public Result payRequest() {
        PayManager payManager = PayManager.findService("taobao");
        PayService payService = payManager.getPayService();
        Order order = orderService.get(orderId);
        PayRequest request = payService.doMakePayRequest(order);

        payForm = request.getPrePay();
        return dispatch();
    }

    public Result weixin() {
        PayManager payManager = PayManager.findService("weixin");
        PayService payService = payManager.getPayService();
        Order order = orderService.get(orderId);
        PayRequest request = payService.doMakePayRequest(order);

        payForm = request.getPrePay();
        System.out.println("url=" + payForm);
        return dispatch();
    }


    public Result payBack() {
        System.out.println("Back............4");
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


    public Result weixinPayBack() {
        System.out.println("Back............5");
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
                    if (LineConfirmAndPayType.confirm.equals(line.getConfirmAndPay()) && !CombineType.combine.equals(line.getCombineType())) {
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
        orderService.update(order);
        return jsonResult(payBackObject.getResult());
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


    public Result paySearch() {
        return null;
    }

    public Result pay() {
        // fetch real pay orderinfo
        // make pay object
        // payRequest
        PayManager payManager = PayManager.findService("taobao");
        PayService payService = payManager.getPayService();
        //payService.doMakeBackObject();
        return null;
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

        //去除额外的自定义参数，否则验证失败
        params.remove("appKey");

        return params;
    }


    public Result confirmOrder() {
        order = orderService.get(orderId);
        expired = false;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            if (orderDetail.getPlayDate().before(new Date())) {
                expired = true;
                break;
            }
        }
        return dispatch();
    }

    public Result payOrder() {
        order = orderService.get(orderId);
        if (order.getStatus() != OrderStatus.WAIT) {
            return redirect("/");
        }
        float cost = 0;
        int num = 0;
        HashSet<String> productName = new HashSet<String>();
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            cost += orderDetail.getFinalPrice();
            num += orderDetail.getNum();
            productName.add(orderDetail.getProduct().getName());
        }

        orderMap.put("id", order.getId());
        orderMap.put("cost", cost);
        orderMap.put("num", num);
        orderMap.put("productName", productName);
        return dispatch();
    }

    public Result paySuccess() {
        return dispatch();
    }


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getPayForm() {
        return payForm;
    }

    public void setPayForm(String payForm) {
        this.payForm = payForm;
    }

    public Map<String, Object> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<String, Object> orderMap) {
        this.orderMap = orderMap;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }
}
