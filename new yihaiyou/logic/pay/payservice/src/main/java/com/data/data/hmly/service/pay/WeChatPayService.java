package com.data.data.hmly.service.pay;

import com.data.data.hmly.service.order.RefundLogService;
import com.data.data.hmly.service.pay.dao.PayLogDao;
import com.data.data.hmly.service.wechat.dao.WechatAccountDao;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.gson.WeChatPay;
import com.gson.bean.PayResult;
import com.gson.bean.RefundResult;
import com.zuipin.util.PropertiesManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caiys on 2016/4/21.
 */
@Service
public class WeChatPayService {
    @Resource
    private PayLogDao payLogDao;
    @Resource
    private WechatAccountDao wechatAccountDao;
    @Resource
    private PropertiesManager propertiesManager;

    @Resource
    private RefundLogService refundLogService;
    /**
     * 微信预支付
     * @param bizId
     * @param runningNo
     * @param amount
     * @param desc
     * @return
     */
    public PayResult doPrePay(String bizId, String runningNo, Integer amount, String desc, Long siteId) {
        WechatAccount wechatAccount = findSiteWechatAccount(siteId);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appid", wechatAccount.getAppId());      // 公众账号ID
        paramMap.put("mch_id", wechatAccount.getMchId());     // 商户号
        paramMap.put("body", desc);       // 商品描述
        paramMap.put("out_trade_no", runningNo);   // 商户订单号：32个字符内，唯一，重新支付用原订单号
        paramMap.put("total_fee", String.valueOf(amount)); // 订单总金额，单位为分
        paramMap.put("notify_url", propertiesManager.getString("WEBCHAT_NOTIFY_URL"));  // 通知地址
        paramMap.put("attach", bizId + "," + siteId); // 附加数据
        String paternerKey = wechatAccount.getMchKey();
        PayResult payResult = WeChatPay.unifiedOrder(paramMap, paternerKey);
        return payResult;
    }

    /**
     * 微信预退款
     * @param orderNo       订单编号（商户侧传给微信的订单号）
     * @param refundNo      退款编号（商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔）
     * @param totalFee      总金额 （订单总金额，单位为分，只能为整数，详见支付金额）
     * @param refundFee     退款金额 （退款总金额，订单总金额，单位为分，只能为整数，详见支付金额）
     * @param siteId        站点编号
     * @return
     */
    public RefundResult doPreRefund(String orderNo, String refundNo, Integer totalFee, Integer refundFee, Long siteId) {
        WechatAccount wechatAccount = findSiteWechatAccount(siteId);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appid", wechatAccount.getAppId());      // 公众账号ID
        paramMap.put("mch_id", wechatAccount.getMchId());     // 商户号
        paramMap.put("out_trade_no", orderNo);   // 商户订单号：32个字符内，唯一，重新支付用原订单号
        paramMap.put("out_refund_no", refundNo);   // 商户退款编号：32个字符内，唯一，重新退款用退款编号
        paramMap.put("total_fee", String.valueOf(totalFee)); // 订单总金额，单位为分
        paramMap.put("refund_fee", String.valueOf(refundFee)); // 订单退款金额，单位为分
        paramMap.put("op_user_id", wechatAccount.getOriginalId());
        String paternerKey = wechatAccount.getMchKey();
//        String refundUrl = propertiesManager.getString("WEBCHAT_REFUND_URL");
        String filePath = propertiesManager.getString("CERT_DIR");
        String wId = wechatAccount.getMchId();

        RefundResult refundResult = WeChatPay.refundOrder(paramMap, paternerKey, filePath, wId);

        return refundResult;
    }

    public WechatAccount findSiteWechatAccount(Long siteId) {
        return wechatAccountDao.findSiteWechatAccount(siteId);
    }

}
