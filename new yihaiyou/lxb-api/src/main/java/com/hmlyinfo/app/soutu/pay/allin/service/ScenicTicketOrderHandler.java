package com.hmlyinfo.app.soutu.pay.allin.service;

import com.hmlyinfo.app.soutu.pay.allin.domain.PaymentResult;
import com.hmlyinfo.app.soutu.scenicTicket.domain.PayOrder;
import com.hmlyinfo.app.soutu.scenicTicket.service.PayOrderService;
import com.hmlyinfo.app.soutu.scenicTicket.service.ScenicTicketOrderService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/7/31.
 */
@Service
public class ScenicTicketOrderHandler implements AllInPayHandler {
    
    Logger logger = Logger.getLogger(ScenicTicketOrderHandler.class);


    public static final String TRADE_SUCCESS = "1";

    @Autowired
    private ScenicTicketOrderService scenicTicketOrderService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private PaymentResultService paymentResultService;


    @Override
    @Transactional
    public boolean doBusiness(PaymentResult paymentResult) {
        // 用户付款成功，门票付款
        if (TRADE_SUCCESS.equals(paymentResult.getPayResult())) {
            // 去付款
            Long orderNum = Long.valueOf(paymentResult.getOrderNo());
            Map<String, Object> payOrderMap = new HashMap<String, Object>();
            payOrderMap.put("orderNum", orderNum);
            List<PayOrder> payOrderList = payOrderService.list(payOrderMap);
            long payOrderId = -1;
            if (payOrderList.size() > 0) {
                payOrderId = payOrderList.get(0).getId();
            }
            try {
                scenicTicketOrderService.payOrderByPayOrder(payOrderId);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            // 更新payOrder中的支付宝交易单号
            PayOrder payOrder = payOrderService.info(payOrderId);
            if (paymentResult.getPaymentOrderId() != null) {
                payOrder.setPreOrderId(paymentResult.getPaymentOrderId());
            }
            payOrderService.update(payOrder);
            paymentResultService.insert(paymentResult);
            return true;
        } else {
            logger.info("支付失败，交易单号#" + paymentResult.getOrderNo());
        }
        return false;
    }

    @Override
    public boolean hasBusinessDone(PaymentResult paymentResult) {
        return false;
    }

    @Override
    public void lockTrade(PaymentResult paymentResult) {

    }

    @Override
    public boolean doBusiness(Map paramMap) {
        return false;
    }

    @Override
    public boolean hasBusinessDone(Map paramMap) {
        return false;
    }

    @Override
    public void lockTrade(Map paramMap) {

    }
}
