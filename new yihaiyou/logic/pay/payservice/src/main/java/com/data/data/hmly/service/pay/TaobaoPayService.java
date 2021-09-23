package com.data.data.hmly.service.pay;

import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.pay.config.DirectPayWebCfg;
import com.data.data.hmly.service.pay.entity.PayLog;
import com.data.data.hmly.service.pay.entity.enums.PayAction;
import com.data.data.hmly.service.pay.entity.enums.PayTongdao;
import com.data.data.hmly.service.pay.exinfo.AlipayCore;
import com.data.data.hmly.service.pay.exinfo.AlipayNotify;
import com.data.data.hmly.service.pay.exinfo.AlipaySubmit;
import com.data.data.hmly.service.pay.util.Base64;
import com.data.data.hmly.service.pay.util.RSA;
import com.google.common.collect.Maps;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class TaobaoPayService extends PayService {

    private static final Logger logger = Logger.getLogger(TaobaoPayService.class);


    private PayLogService payLogService = SpringContextHolder.getBean("payLogService");

    private PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    private AlipayNotify alipayNotify = new AlipayNotify();
    private AlipaySubmit alipaySubmit = new AlipaySubmit();
    private static final Random random = new Random();


    // 回调地址
    public String NOTIFY_URL = propertiesManager.getString("NOTIFY_URL");
    public String RETURN_URL = propertiesManager.getString("RETURN_URL");
    public String PAY_SUCCESS_URL = propertiesManager.getString("PAY_SUCCESS_URL");
    public String PAY_FAIL_URL = propertiesManager.getString("PAY_FAIL_URL");
    public String PARTNER = propertiesManager.getString("PARTNER");
    public String LOGIN_NAME = propertiesManager.getString("LOGIN_NAME");
    //头部地址
    public String INDEX_PATH = propertiesManager.getString("INDEX_PATH");

    @Override
    public PayRequest doMakePayRequest(Order order) {
        String prePay = doGetRequsest(order);
        PayRequest request = new PayRequest();
        request.setPrePay(prePay);
        return request;

    }

    public Map<String, String> doMakePayRequestMap(Order order){
        List<OrderDetail> orderDetailList = order.getOrderDetails();
        Float cost = order.getPrice();
        String subject = "";
        if (StringUtils.isNotBlank(order.getName())) {
            subject = order.getName();
        } else {
            subject = orderDetailList.get(0).getProduct().getName();
        }
        Map<String, Object> orderMap = new HashMap<String, Object>();

//        orderMap.put("notifyUrl", NOTIFY_URL);
        orderMap.put("notifyUrl", INDEX_PATH + "/pay/pay/payBack.jhtml");

//        orderMap.put("returnUrl", RETURN_URL);
        orderMap.put("returnUrl", getOrderUrl(order));
//        orderMap.put("outTradeNO", order.getId());
        String outTradeNo = String.valueOf(new Date().getTime()) + String.valueOf(random.nextInt(99999));
//        orderMap.put("outTradeNO", order.getId());
        orderMap.put("outTradeNO", order.getOrderNo());

        orderMap.put("subject", subject);
        orderMap.put("totalFee", cost);
        //orderMap.put("payRespService", "dealOrderNotifyService");
        orderMap.put("paySuccessUrl", PAY_SUCCESS_URL);
        orderMap.put("payFailUrl", PAY_FAIL_URL);

        // notifyUrl,outTradeNO,subject,totalFee,payRespService,paySuccessUrl,payFailUrl
        buildValid(orderMap);

        //记录交易日志
        PayLog log = addPayLog(order);
        orderMap.put("outTradeNO", log.getId());
        String useAntiPhishingKey = (String) orderMap.get("useAntiPhishingKey");
        String antiPhishingKey = "";
        if (useAntiPhishingKey != null && useAntiPhishingKey.equals("T")) {
            try {
                antiPhishingKey = alipaySubmit.query_timestamp();
            } catch (Exception e) {
                logger.error("获取防钓鱼时间戳失败！", e);
            }
        }
//&sign="X6bcqBtRMasadajbQYyd80EhkwUfjUKTafIKhxLJiBzZd8NX%2BvIeyZxLVtr121B56eLoJU%2FyfrermTm1BE4nBZxf0B3hTGiMkHRQJWvbE8yMltad7F6VeEx9qrCGu%2Fl6fZLJA%2FZ8Gx%2B%2F9n46lDiOrC%2F6Rv5uvQHNryb6LkREpuw%3D"&sign_type="RSA"
        // 把请求参数打包成数组
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("service", "mobile.securitypay.pay");
        paramMap.put("partner", PARTNER);
        paramMap.put("_input_charset", "UTF-8");
        //商户网站订单系统中唯一订单号
        paramMap.put("out_trade_no", orderMap.get("outTradeNO").toString());
        //订单名称
        paramMap.put("subject", (String) orderMap.get("subject"));
        paramMap.put("payment_type", DirectPayWebCfg.PAYMENT_TYPE_PRODUCT);
        paramMap.put("seller_id", LOGIN_NAME);
        //付款金额
        paramMap.put("total_fee", orderMap.get("totalFee").toString());
        //订单描述
        paramMap.put("body", (String) orderMap.get("subject"));
        //超时时间，不填默认是15天。八个值可选：1h(1小时),2h(2小时),3h(3小时),1d(1天),3d(3天),7d(7天),15d(15天),1c(当天
        paramMap.put("it_b_pay", "1d");

        //服务器异步通知页面路径
        paramMap.put("notify_url", (String) orderMap.get("notifyUrl"));
        //商品展示地址
        paramMap.put("show_url", "http://www.1haiu.com");
//        paramMap.put("sign_type", "RSA");
//        paramMap.put("sign", "RSA");
        Map<String, String> sPara = AlipayCore.paraFilter(paramMap);
        // 生成签名结果
//        String mysign = alipaySubmit.buildRequestMysign(sPara);
        String prestr = AlipayCore.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        mysign = RSA.sign(prestr, propertiesManager.getString("KEY"), "UTF-8");
        // 签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", "RSA");
        //用户在创建交易exter_invoke_ip", (String) orderMap.get("exterInvokeIp"));

        // 建立请求
        logger.info("建立支付宝请求");

        return sPara;
    }

    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64
                    .decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance("SHA1WithRSA");

            signature.initSign(priKey);
            signature.update(content.getBytes("UTF-8"));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public PayBackObject doMakeBackObject(Map<String, String> params, Order order) {
        PayBackObject payBackObject = new PayBackObject();
        Map result = notifyResult(params, order);
        payBackObject.setResult(result);

        return payBackObject;
    }

    @Override
    public PayBackObject doMakeBackObject(Map<String, String> params, FerryOrder order) {
        PayBackObject payBackObject = new PayBackObject();
//        Map result = notifyResult(params, order);
//        payBackObject.setResult(result);

        return payBackObject;
    }

    // 已经成功回调后再次回调
    public PayBackObject doMakeBackObject() {
        PayBackObject payBackObject = new PayBackObject();
        Map<String, Object> result = Maps.newHashMap();
        result.put("dealResult", true);
        result.put("paySuccessUrl", PAY_SUCCESS_URL);
        result.put("payFailUrl", PAY_FAIL_URL);
        payBackObject.setResult(result);
        return payBackObject;
    }

    @Override
    public PaySearchResult doPaySearch() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void doPayRequest() {
        // TODO Auto-generated method stub
    }


    public String doGetRequsest(Order order) {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        // 查询订单信息
//        float cost = 0;
        List<OrderDetail> orderDetailList = order.getOrderDetails();
        String subject = "";
        if (StringUtils.isNotBlank(order.getName())) {
            subject = order.getName();
        } else {
            subject = orderDetailList.get(0).getProduct().getName();
        }
//        for (OrderDetail orderDetail : orderDetailList) {
//            if (orderDetail.getProductType() == ProductType.hotel && "ELONG".equals(String.valueOf(orderDetail.getProduct().getSource()))) {
//                cost += 0;
//            } else {
//                cost += orderDetail.getFinalPrice();
//            }
//        }
        Float cost = order.getPrice();
        // 若订单已经支付则抛出异常
        if (order.getStatus() != OrderStatus.WAIT) {
            logger.info("订单未处于待支付状态，不可支付");
        }


        // 创建支付请求
        String returnUrl = (String) paramMap.get("returnUrl");
        Map<String, Object> orderMap = new HashMap<String, Object>();

//        orderMap.put("notifyUrl", NOTIFY_URL);
        orderMap.put("notifyUrl", INDEX_PATH + "/pay/pay/payBack.jhtml");

//        orderMap.put("returnUrl", RETURN_URL);
        orderMap.put("returnUrl", getOrderUrl(order));
//        orderMap.put("outTradeNO", order.getId());
        String outTradeNo = String.valueOf(new Date().getTime()) + String.valueOf(random.nextInt(99999));
//        orderMap.put("outTradeNO", order.getId());
        orderMap.put("outTradeNO", order.getOrderNo());

        orderMap.put("subject", subject);
        orderMap.put("totalFee", cost);
        //orderMap.put("payRespService", "dealOrderNotifyService");
        orderMap.put("paySuccessUrl", PAY_SUCCESS_URL);
        orderMap.put("payFailUrl", PAY_FAIL_URL);

        // notifyUrl,outTradeNO,subject,totalFee,payRespService,paySuccessUrl,payFailUrl

        String prePay = doBuildRequest(order, orderMap);
        return prePay;
    }


    public String doBuildRequest(Order order, Map params) {
        //支付请求参数验证
        buildValid(params);

        //记录交易日志
        PayLog log = addPayLog(order);
        params.put("outTradeNO", log.getId());
        return makeRequestInfo(params);
    }

    // 支付请求参数验证
    private void buildValid(Map params) {
        String[] validArray = {"notifyUrl", "outTradeNO", "subject", "totalFee", "paySuccessUrl", "payFailUrl"};
        for (String valid : validArray) {
            if ((params.get(valid) == null || "".equals(params.get(valid).toString()))) {
                throw new RuntimeException("必选字段【" + valid + "】不能为空!");
            }
        }
    }

    // 记录请求日志
    public PayLog addPayLog(Order order) {
//        float cost = 0;
//        for (OrderDetail orderDetail : order.getOrderDetails()) {
//            cost += orderDetail.getFinalPrice();
//        }
        logger.info("记录交易日志");
        PayLog payLog = new PayLog();
        payLog.setOrderId(order.getId());
        payLog.setOrderType(order.getOrderType());
        payLog.setUser(order.getUser());
        payLog.setAction(PayAction.pay);
        payLog.setCost(order.getPrice());
        payLog.setRequestTime(new Date());
        payLog.setTongdao(PayTongdao.alipay);

        payLogService.save(payLog);
        return payLog;
    }

    // 记录回调日志
    public void addNotifyLog(Order order, Map<String, Object> paramMap) {
        PayLog payLog = new PayLog();

        payLog.setOrderId(order.getId());
        payLog.setOrderType(order.getOrderType());
        payLog.setUser(order.getUser());
        payLog.setAction(PayAction.requestback);
        float cost = Float.parseFloat(paramMap.get("total_fee").toString());
        payLog.setCost(cost);
        payLog.setRequestTime(new Date());
        payLog.setPayAccount(paramMap.get("buyer_email").toString());
        payLog.setSubject(paramMap.get("subject").toString());
        payLog.setTradeNo(paramMap.get("trade_no").toString());
        payLog.setNotifyId(paramMap.get("notify_id").toString());
        payLog.setNotifyType(paramMap.get("notify_type").toString());
        payLog.setTongdao(PayTongdao.alipay);
        order.setTradeNo(paramMap.get("trade_no").toString());  //支付成功后设置支付订单交易编号

        try {
            logger.info("格式化时间");
            String[] pattern = new String[]{"yyyy-MM", "yyyyMM", "yyyy/MM", "yyyyMMdd", "yyyy-MM-dd",
                    "yyyy/MM/dd", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"};
            payLog.setNotifyTime(DateUtils.parseDate((String) paramMap.get("notify_time"), pattern));
        } catch (Exception e) {
            logger.warn("从回调信息读取字段出现错误，原因是：" + e.getLocalizedMessage());
        }

        payLogService.save(payLog);
    }


    // 获取支付宝支付请求信息
    public String makeRequestInfo(Map params) {
        //是否启用防钓鱼时间戳
        String useAntiPhishingKey = (String) params.get("useAntiPhishingKey");
        String antiPhishingKey = "";
        if (useAntiPhishingKey != null && useAntiPhishingKey.equals("T")) {
            try {
                antiPhishingKey = alipaySubmit.query_timestamp();
            } catch (Exception e) {
                logger.error("获取防钓鱼时间戳失败！", e);
            }
        }

        // 把请求参数打包成数组
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("service", DirectPayWebCfg.DIRECT_PAY_SERVICE);
        paramMap.put("partner", PARTNER);
        paramMap.put("_input_charset", "utf-8");
        paramMap.put("payment_type", DirectPayWebCfg.PAYMENT_TYPE_PRODUCT);
        paramMap.put("seller_email", LOGIN_NAME);

        //服务器异步通知页面路径
        paramMap.put("notify_url", (String) params.get("notifyUrl"));
        //页面跳转同步通知页面路径
        paramMap.put("return_url", (String) params.get("returnUrl"));
        //商户网站订单系统中唯一订单号
        paramMap.put("out_trade_no", params.get("outTradeNO").toString());
        //订单名称
        paramMap.put("subject", (String) params.get("subject"));
        //付款金额
        paramMap.put("total_fee", params.get("totalFee").toString());
        //订单描述
        paramMap.put("body", (String) params.get("description"));
        //商品展示地址
        paramMap.put("show_url", (String) params.get("showUrl"));
        paramMap.put("anti_phishing_key", antiPhishingKey);
        //用户在创建交易时，该用户当前所使用机器的IP。
        paramMap.put("exter_invoke_ip", (String) params.get("exterInvokeIp"));

        // 建立请求
        logger.info("建立支付宝请求");
        return alipaySubmit.buildRequest(paramMap, "get", "确认");
    }


    // 处理支付宝回调信息
    public Map notifyResult(Map<String, String> nodifyMap, Order order) {

        boolean dealResult = dealNotify(nodifyMap, order);
        Map<String, Object> result = Maps.newHashMap();
        result.put("dealResult", dealResult);
        result.put("paySuccessUrl", PAY_SUCCESS_URL);
        result.put("payFailUrl", PAY_FAIL_URL);
        return result;
    }

    public boolean dealNotify(Map paramMap, Order order) {
        //验证是否通知信息由支付宝发起，不能包含自定义参数
        if (!verifyNotify(paramMap)) {
            return false;
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表
        //交易状态
        String tradeStatus = (String) paramMap.get("trade_status");

        //根据业务逻辑来编写
        //交易完成
        //注意：
        //"TRADE_FINISHED"该种交易状态只在两种情况下出现
        //1、开通了普通即时到账，买家付款成功后。
        //2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。

        //"TRADE_SUCCESS"该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
        if (tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {
            //判断该笔订单是否在商户网站中已经做过处理
            //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            //如果有做过处理，不执行商户的业务程序
            //lockTrade(paramMap);
            if (!hasBusinessDone(order)) {
                doBusiness(order);
                addNotifyLog(order, paramMap);
                return true;
            } else {//已处理，返回true
                return true;
            }
        }

        return false;
    }

    public String getOrderUrl(Order order) {
        String type = order.getOrderType().toString();
        String url = INDEX_PATH;
        if (type == "train" || type == "flight") {
            if (order.getOrderDetails().size() == 1) {
                url += "/lvxbang/order/singleOrderDetail.jhtml?orderId=" + order.getId();
            } else {
                url += "/lvxbang/order/returnOrderDetail.jhtml?orderId=" + order.getId();
            }
        }
        if (type == "ticket" ) {
            url += "/lvxbang/order/ticketOrderDetail.jhtml?orderId=" + order.getId();
        }
        if (type == "hotel" ) {
            url += "/lvxbang/order/hotelOrderDetail.jhtml?orderId=" + order.getId();
        }
        if (type == "plan" ) {
            url += "/lvxbang/order/planOrderDetail.jhtml?orderId=" + order.getId();
        }
        return url;
    }

    public boolean verifyNotify(Map paramMap) {
        return alipayNotify.verify(paramMap);
    }

    public void getDetail(long orderId) {
    }


}
