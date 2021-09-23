package com.hmlyinfo.app.soutu.scenicTicket.service;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.common.service.SequenceService;
import com.hmlyinfo.app.soutu.scenicTicket.domain.PayOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.RefundOrder;
import com.hmlyinfo.app.soutu.scenicTicket.domain.ScenicTicketOrder;
import com.hmlyinfo.app.soutu.scenicTicket.mapper.PayOrderMapper;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class PayOrderService extends BaseService<PayOrder, Long> {

    public static final int PAY_ORDER_TYPE_WEIXIN = 1; //支付类型：微信
    public static final int PAY_TYPE_ALI = 102; //支付类型：支付宝
    public static final int PAY_TYPE_ALL_IN = 103; //支付类型：支付宝



    @Autowired
    private PayOrderMapper<PayOrder> mapper;
    @Autowired
    private WeiXinService weiXinService;
    @Autowired
    RefundOrderService refundOrderService;
    @Autowired
    private SequenceService sequenceService;

    @Override
    public BaseMapper<PayOrder> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

    /**
     * 统一下订单
     */
    public PayOrder unifiedOrder(PayOrder payOrder, int type, String notifyUrl) {
        switch (type) {
            case PAY_ORDER_TYPE_WEIXIN:
                orderWeixin(payOrder, notifyUrl);
        }
        return payOrder;
    }

    private void orderWeixin(PayOrder payOrder, String notifyUrl) {
        insert(payOrder);
        payOrder.setUserId(MemberService.getCurrentUserId());
        Map<String, Object> map = weiXinService.weixinOrder(payOrder, notifyUrl);
        if (map.get("status") != null && WeiXinService.isValidate(map.get("status").toString())) {
            payOrder.setPreOrderId(map.get("prepay_id").toString());
            update(payOrder);
        } else {
            throw new BizValidateException(ErrorCode.ERROR_51001, map.get("return_msg").toString());
        }
    }

    public void refund(Long id, int type) {
        PayOrder payOrder = info(id);
        switch (type) {
            case PAY_ORDER_TYPE_WEIXIN:
                refundWeixin(payOrder);
        }
    }

    public void refundWeixin(PayOrder payOrder) {
        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setPayOrderId(payOrder.getId());
        refundOrder.setRefundFee(payOrder.getTotalFee());
        refundOrderService.insert(refundOrder);
        Map<String, Object> map = weiXinService.refund(refundOrder);
        if (map.get("status") != null && WeiXinService.isValidate(map.get("status").toString())) {
            refundOrder.setStatus(RefundOrder.STATUS_REFUND_SUCCESS);
            refundOrderService.update(refundOrder);
        } else {
            throw new BizValidateException(ErrorCode.ERROR_56004, map.get("return_msg").toString());
        }
    }

    public PayOrder order(ScenicTicketOrder scenicTicketOrder, int payType, String payTypeName) {
        if (scenicTicketOrder.getStatus() != ScenicTicketOrder.STATUS_NOT_PAID) {
            Validate.isTrue(false, ErrorCode.ERROR_56008, "订单未处于待支付状态，不可支付");
        }
        // 创建支付单
        PayOrder payOrder = new PayOrder();
        //生成订单编号
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String orderTime = df.format(new Date());
        String orderNum = orderTime + payType + sequenceService.getOrderSeq(payTypeName);
        payOrder.setOrderNum(orderNum);
        payOrder.setUserId(scenicTicketOrder.getUserId());
        payOrder.setBody(scenicTicketOrder.getOrderName());
        payOrder.setTotalFee(scenicTicketOrder.getTotalFee());
        insert(payOrder);
        return payOrder;
    }
    
}
