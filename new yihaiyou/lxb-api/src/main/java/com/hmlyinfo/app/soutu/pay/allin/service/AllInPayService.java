package com.hmlyinfo.app.soutu.pay.allin.service;

import com.allinpay.ets.client.RequestOrder;
import com.allinpay.ets.client.SecurityUtil;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.base.SpringContextUtils;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.common.service.SequenceService;
import com.hmlyinfo.app.soutu.pay.allin.controller.PostView;
import com.hmlyinfo.app.soutu.pay.allin.domain.AllInPayLog;
import com.hmlyinfo.app.soutu.pay.allin.domain.PaymentResult;
import com.hmlyinfo.app.soutu.scenicTicket.domain.PayOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketOrder;
import com.hmlyinfo.app.soutu.scenicTicket.service.PayOrderService;
import com.hmlyinfo.app.soutu.scenicTicket.service.ScenicTicketOrderService;
import com.hmlyinfo.base.util.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by guoshijie on 2015/7/22.
*/
@Service
public class AllInPayService {

    private static final Logger logger = Logger.getLogger(AllInPayService.class);

    private static final String SERVER_URL = Config.get("all.in.pay.server.url");
    private static final String KEY = Config.get("all.in.pay.key");
    private static final String NOTIFY_URL = Config.get("all.in.pay.notify.url");
    private static final String VERSION = Config.get("all.in.pay.version");
    private static final String MERCHANT_ID = Config.get("all.in.pay.merchant.id");
    private static final int SIGN_TYPE = 0;     //签名类型，1使用证书，0使用md5
    private static final int PAY_TYPE = 0;      //支付方式，1直连，0间连
    private static final String CERT_KEY_PATH = Config.get("all.in.pay.cert");

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private ScenicTicketOrderService scenicTicketOrderService;
    @Autowired
    private AllInPayLogService allInPayLogService;
    @Autowired
    private PaymentResultService paymentResultService;

    public Map<String, Object> buildRequest(Long scenicTicketOrderId, String callbackUrl, String handler) {

        ScenicTicketOrder order = scenicTicketOrderService.info(scenicTicketOrderId);
        Validate.isTrue(order.getStatus() == ScenicTicketOrder.STATUS_NOT_PAID, ErrorCode.ERROR_51001,
                "订单状态异常：订单号#" + scenicTicketOrderId + "，状态#" + order.getStatus());
        PayOrder payOrder;
        if (order.getPayOrder() != null) {
            payOrder = payOrderService.info(order.getPayOrder());
            Validate.isTrue(!isPaid(payOrder), ErrorCode.ERROR_51001,
                    "订单已支付：订单号#" + scenicTicketOrderId + "，支付单号#" + payOrder.getOrderNum());
        } else {
            payOrder = payOrderService.order(order, PayOrder.PAY_TYPE_ALL_IN, SequenceService.ALL_IN_KEY);
        }

//        //页面编码要与参数inputCharset一致，否则服务器收到参数值中的汉字为乱码而导致验证签名失败。
//        request.setCharacterEncoding("UTF-8");

        PostView postView = new PostView();
        postView.setServerUrl(SERVER_URL);
        postView.setKey(KEY);
        postView.setPickupUrl(callbackUrl);
        postView.setReceiveUrl(NOTIFY_URL);
        postView.setVersion(VERSION);
        postView.setSignType(SIGN_TYPE);
        postView.setMerchantId(MERCHANT_ID);
        postView.setPayerName(order.getBuyerName());
        if (order.getIdCardNo() != null) {
            postView.setPayerIDCard(SecurityUtil.encryptByPublicKey(CERT_KEY_PATH, order.getIdCardNo()));
        }
        postView.setPayerEmail(order.getBuyerEmail());
        postView.setPayerTelephone(order.getMobile());
        postView.setOrderNo(payOrder.getOrderNum());

        postView.setOrderAmount((int) (payOrder.getTotalFee() * 100));
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        String orderTime = df.format(new Date());
        postView.setOrderDatetime(orderTime);

        postView.setProductName(order.getOrderName());
        postView.setPayType(PAY_TYPE);


//        String inputCharset= request.getParameter("inputCharset");
//        String orderCurrency=request.getParameter("orderCurrency");
//        String orderExpireDatetime=request.getParameter("orderExpireDatetime");
//        String pid=request.getParameter("pid");
//        String productId=request.getParameter("productId");
//        String productNum=request.getParameter("productNum");
//        String productPrice=request.getParameter("productPrice");
//        String productDesc=request.getParameter("productDesc");
//        String ext1=request.getParameter("ext1");
//        String ext2=request.getParameter("ext2");
//        String extTL=request.getParameter("extTL");//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
//        String issuerId=request.getParameter("issuerId");
//        String pan=request.getParameter("pan");
//        String tradeNature = request.getParameter("tradeNature");

        //构造订单请求对象，生成signMsg。
        RequestOrder requestOrder = createRequestOrder(postView);

        String strSrcMsg = requestOrder.getSrc(); // 此方法用于debug，测试通过后可注释。
        System.out.println(strSrcMsg);
        String strSignMsg = requestOrder.doSign(); // 签名，设为signMsg字段值。
        System.out.println(strSignMsg);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("order", postView);
        result.put("signMsg", strSignMsg);
        allInPayLogService.log(payOrder, callbackUrl, NOTIFY_URL, handler);
        order.setPayOrder(payOrder.getId());
        order.setOrderNum(payOrder.getOrderNum());
        scenicTicketOrderService.update(order);
        return result;
    }

    private boolean isPaid(PayOrder payOrder) {
        Map<String, Object> paymentResultParams = new HashMap<String, Object>();

        paymentResultParams.put("orderNo", payOrder.getOrderNum());
        List<PaymentResult> paymentResult = paymentResultService.list(paymentResultParams);
        return !paymentResult.isEmpty();
    }

    private RequestOrder createRequestOrder(PostView postView) {
        RequestOrder requestOrder = new com.allinpay.ets.client.RequestOrder();
        requestOrder.setPickupUrl(postView.getPickupUrl());
        requestOrder.setReceiveUrl(postView.getReceiveUrl());
        requestOrder.setVersion(postView.getVersion());
        requestOrder.setSignType(postView.getSignType());
        requestOrder.setPayType(postView.getPayType());
//        requestOrder.setIssuerId(issuerId);
        requestOrder.setMerchantId(postView.getMerchantId());
        requestOrder.setPayerName(postView.getPayerName());
        requestOrder.setPayerEmail(postView.getPayerEmail());
        requestOrder.setPayerTelephone(postView.getPayerTelephone());
        requestOrder.setPayerIDCard(postView.getPayerIDCard());
        requestOrder.setPid(postView.getPid());
        requestOrder.setOrderNo(postView.getOrderNo());
        requestOrder.setOrderAmount(postView.getOrderAmount());
        requestOrder.setOrderCurrency(postView.getOrderCurrency());
        requestOrder.setOrderDatetime(postView.getOrderDatetime());
        requestOrder.setOrderExpireDatetime(postView.getOrderExpireDatetime());
        requestOrder.setProductName(postView.getProductName());
//        if(null!=productPrice&&!"".equals(postView)){
//            requestOrder.setProductPrice(Long.parseLong(productPrice));
//        }
//        if(null!=productNum&&!"".equals(productNum)){
//            requestOrder.setProductNum(Integer.parseInt(productNum));
//        }
//        requestOrder.setProductId(postView.getProductId());
//        requestOrder.setProductDesc(postView.getProductDesc());
//        requestOrder.setExt1(ext1);
//        requestOrder.setExt2(ext2);
//        requestOrder.setExtTL(extTL);//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
//        requestOrder.setPan(pan);
//        requestOrder.setTradeNature(tradeNature);
        requestOrder.setKey(postView.getKey()); //key为MD5密钥，密钥是在通联支付网关会员服务网站上设置。
        return requestOrder;
    }

    public void processResult(PaymentResult paymentResult) {
        AllInPayLog payLog = allInPayLogService.getByOrder(paymentResult.getOrderNo());
        if (payLog == null) {
            logger.error("查询不到支付日志日志,支付单号#" + paymentResult.getOrderNo());
            return;
        }
        paymentResult.setKey(KEY);
        paymentResult.setCertPath(CERT_KEY_PATH);
        boolean verifyResult = paymentResult.verify();
        if (!verifyResult) {
            logger.error("支付回调信息不匹配，支付单号#" + paymentResult.getOrderNo());
            return;
        }
        AllInPayHandler handler = getPayService(payLog);
        if (handler.hasBusinessDone(paymentResult)) {
            logger.error("支付信息已处理过，支付单号#" + paymentResult.getOrderNo());
            return;
        }
        handler.doBusiness(paymentResult);
    }

    private AllInPayHandler getPayService(AllInPayLog payLog) {
        String handler = payLog.getNotifyService();
        try {
            Object payService = SpringContextUtils.getBean(handler);
            if (!(payService instanceof AllInPayHandler)) {
                logger.error("处理异常，非法回调处理类名#" + handler + ",交易号#" + payLog.getOrderId());
            }
            return (AllInPayHandler) payService;
        } catch (Exception e) {
            logger.error("交易号【" + payLog.getOrderId() + "】对应的业务服务处理失败", e);
        }
        return null;
    }
}
