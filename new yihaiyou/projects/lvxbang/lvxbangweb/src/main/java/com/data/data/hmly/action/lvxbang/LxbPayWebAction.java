package com.data.data.hmly.action.lvxbang;

import com.danga.MemCached.MemCachedClient;
import com.data.data.hmly.service.PayManager;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.CmbOrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.pay.PayBackObject;
import com.data.data.hmly.service.pay.PayRequest;
import com.data.data.hmly.service.pay.PayService;
import com.data.data.hmly.service.pay.util.httpclient.HttpUtil;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class LxbPayWebAction extends LxbAction {


    @Resource
    private OrderService orderService;
    //    @Resource
//    private TrafficPriceService trafficPriceService;
    @Resource
    private final PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    private Long orderId;

    private Order order = new Order();

    private String payForm;

    private Map<String, Object> orderMap = new HashMap<String, Object>();

    private String payWay;
    private String errMsg;

    private String detailUrl;

    private static final String CMB_BILL_NO = "cmbBillNo";
    private final String branchId = propertiesManager.getString("BRANCH_ID");
    private final String coNo = propertiesManager.getString("CO_NO");
    private final String cmbPayUrl = propertiesManager.getString("CMB_PAY_URL");
    private final String cmbNotifyUrl = propertiesManager.getString("CMB_NOTIFY_URL");


    public Result request() {
        getLoginUser();
        order = orderService.get(orderId);
        if ("train".equals(order.getOrderType().toString()) || "flight".equals(order.getOrderType().toString())) {
            String name = "";
            int i = 1;
            for (OrderDetail od : order.getOrderDetails()) {
//                Long priceId = od.getCostId();
//                TrafficPrice trafficPrice = trafficPriceService.get(priceId);
//                name += trafficPrice.get
                Product product = od.getProduct();
                Traffic traffic = (Traffic) product;
                name += traffic.getLeaveTransportation().getName() + "-" + traffic.getArriveTransportation().getName();
                if (i == 1) {
                    name += ";";
                }
                i++;
            }

            order.setName(name);
        }

        return dispatch();
    }

    public Map<String, Object> doPayForm() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            order = orderService.get(orderId);
            if ("weixin".equals(payWay)) {
                //
                Date nowDate = new Date();
                if (StringUtils.isNotBlank(order.getWechatCode()) && nowDate.before(order.getWechatTime())) {
                    payForm = order.getWechatCode();
                    map.put("success", true);
                    map.put("payForm", payForm);
                    return map;
                }
            }
            PayManager payManager = PayManager.findService(payWay);
            PayService payService = payManager.getPayService();
            PayRequest request = payService.doMakePayRequest(order);

            payForm = request.getPrePay();

            if ("weixin".equals(payWay)) {
                order.setWechatCode(payForm);
                Long time = new Date().getTime();
                time = time + 2 * 60 * 60 * 1000;
                Date expireTime = new Date(time);
                order.setWechatTime(expireTime);
                orderService.update(order);
            }

            map.put("success", true);
            map.put("payForm", payForm);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return map;

        } catch (Exception e) {
            //
            map.put("success", false);
            map.put("errMsg", "创建支付链接失败");
            return map;
        }
    }

    public Result aliPay() {
        payWay = "taobao";
        Map<String, Object> map = doPayForm();
        if (map.get("payForm") == null) {
            //
            errMsg = map.get("errMsg").toString();
        } else {
            errMsg = "";
            payForm = map.get("payForm").toString();
        }
        return dispatch();
    }

    public Result wechatPay() {
        getLoginUser();
        payWay = "weixin";
        Map<String, Object> map = doPayForm();
        order = orderService.get(orderId);

        switch (order.getOrderType()) {
            case plan:
                detailUrl = "/lvxbang/order/planOrderDetail.jhtml?orderId=" + order.getId();
                break;
            case ticket:
                detailUrl = "/lvxbang/order/ticketOrderDetail.jhtml?orderId=" + order.getId();
                break;
            case train:
                if (order.getOrderDetails().size() > 1) {
                    detailUrl = "/lvxbang/order/returnOrderDetail.jhtml?orderId=" + order.getId();
                } else {
                    detailUrl = "/lvxbang/order/singleOrderDetail.jhtml?orderId=" + order.getId();
                }
                break;
            case flight:
                if (order.getOrderDetails().size() > 1) {
                    detailUrl = "/lvxbang/order/returnOrderDetail.jhtml?orderId=" + order.getId();
                } else {
                    detailUrl = "/lvxbang/order/singleOrderDetail.jhtml?orderId=" + order.getId();
                }
                break;
            case hotel:
                detailUrl = "/lvxbang/order/hotelOrderDetail.jhtml?orderId=" + order.getId();
                break;
            default:
                break;
        }
        if (map.get("payForm") == null) {
            //
            errMsg = map.get("errMsg").toString();
        } else {
            errMsg = "";
            payForm = map.get("payForm").toString();
        }
        return dispatch();
    }

    public Result cmbPay() {
        MemCachedClient memCachedClient = SpringContextHolder.getBean("memCachedClient");
        Integer billNo = (Integer) memCachedClient.get(CMB_BILL_NO);
        order = orderService.get(orderId);
        if (billNo == null) {
            billNo = 0;
        }
        billNo++;
        memCachedClient.set(CMB_BILL_NO, billNo);
        StringBuilder sb = new StringBuilder();
        sb.append(10000000000l + billNo).deleteCharAt(0);
        order.setBillNo(sb.toString());
        order.setCmbStatus(CmbOrderStatus.wait);
        order.setCmbTime(DateUtils.format(new Date(), "yyyyMMdd"));
        orderService.update(order);
        return redirect(cmbPayUrl + "?BranchID=" + branchId + "&CoNo=" + coNo + "&BillNo=" + order.getBillNo()
                + "&Amount=" + order.getPrice() + "&Date=" + order.getCmbTime() + "&MerchantUrl=" + cmbNotifyUrl + "&MerchantPara=" + order.getId());
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

    public Result weixinPayBack() {
        System.out.println("Back............2");
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


    public Result paySearch() {
        return null;
    }

    public Result pay() {
        // fetch real pay orderinfo
        // make pay object
        // payRequest
//        PayManager payManager = PayManager.findService("taobao");
//        PayService payService = payManager.getPayService();
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

        // 去除额外的自定义参数，否则验证失败
        params.remove("appKey");

        return params;
    }


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
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

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
