package com.hmlyinfo.app.soutu.pay.allin.service;

import com.hmlyinfo.app.soutu.pay.allin.domain.PaymentResult;
import com.hmlyinfo.app.soutu.pay.base.service.IDirectPayService;
import org.apache.log4j.Logger;

/**
 * Created by guoshijie on 2015/7/31.
 */
public interface AllInPayHandler extends IDirectPayService {

    static final Logger logger = Logger.getLogger(AllInPayHandler.class);

    /**
     * 处理支付成功后的相关业务逻辑
     * @param paymentResult
     */
    boolean doBusiness(PaymentResult paymentResult);

    /**
     * 判断该笔订单是否在商户网站中已经做过处理
     * 如果有做过处理，不执行商户的业务程序
     * @param paymentResult
     */
    boolean hasBusinessDone(PaymentResult paymentResult);

    /**
     * 处理订单前先锁住订单记录
     * @param paymentResult
     */
    void lockTrade(PaymentResult paymentResult);
}
