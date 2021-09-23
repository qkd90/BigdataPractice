package com.data.data.hmly.service.order;

import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.RefundLog;
import com.data.data.hmly.service.order.entity.enums.RefundChannel;
import com.data.data.hmly.service.order.entity.enums.RefundStatus;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.gson.WeChatPay;
import com.gson.bean.QueryRefundResult;
import com.gson.bean.RefundResult;
import com.gson.bean.RefundResultDetail;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2016/5/25.
 */
@Service
public class OrderRefundService {

    @Resource
    private OrderLogService orderLogService;
    @Resource
    private WechatAccountService wechatAccountService;
    @Resource
    private PropertiesManager propertiesManager;
//    @Resource
//    private PayLogService payLogService;
    @Resource
    private RefundLogService refundLogService;

    /**
     * 退款预处理方法
     * 根据订单原本支付方式, 进行相应退款
     * @param order
     * @param orderDetail
     * @param rFee
     */
    public Map<String, Object> doStartRefund(Order order, OrderDetail orderDetail, final Float rFee) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = orderLogService.getSysOrderLogUser();
        if (orderDetail != null) {
            OrderLog orderRefundLog = orderLogService.createOrderLog(user, "订单详情##"
                    + orderDetail.getId() + "发起退款, 申请的退款金额为:" + rFee, order.getId(), orderDetail.getId());
            orderLogService.loggingOrderLog(orderRefundLog);
        } else {
            OrderLog orderRefundLog = orderLogService.createOrderLog(user, "订单##"
                    + order.getId() + "发起退款, 申请的退款金额为:" + rFee, order.getId(), null);
            orderLogService.loggingOrderLog(orderRefundLog);
        }

        String orderNo = order.getTradeNo();
        String refundNo = "R" + orderNo + "D" + orderDetail.getId();
        Float totalPrice = order.getPrice() * 100;
        if (orderDetail != null) {
            // 组合线路订单的总价按照order来计算
            if (order.getIsCombineLine() != null && order.getIsCombineLine()) {
                totalPrice = order.getPrice() * 100;
            } else {
                totalPrice = orderDetail.getFinalPrice() * 100;
            }
        }
        Float refundFee = rFee * 100;
//        Integer weixinTotalPrice = totalPrice.intValue();
//        Integer weixinRefundFee = refundFee.intValue();
//        Long companyId = Long.parseLong(propertiesManager.getString("WX_COMPANY_ID"));
//        String wxAccount = propertiesManager.getString("WX_ACCOUNT_NAME");
        RefundLog refundLog = new RefundLog();
        if (StringUtils.hasText(order.getWechatCode())) {
            if (orderDetail != null) {
                OrderLog orderRefundLog2 = orderLogService.createOrderLog(user, "订单详情##"
                        + orderDetail.getId() + "发起微信退款, 申请的退款金额为:" + rFee, order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderRefundLog2);
            } else {
                OrderLog orderRefundLog2 = orderLogService.createOrderLog(user, "订单##"
                        + order.getId() + "发起微信退款, 申请的退款金额为:" + rFee, order.getId(), null);
                orderLogService.loggingOrderLog(orderRefundLog2);
            }
            refundLog.setOrderId(order.getId());
            refundLog.setRefundDesc("无理由退款");
            refundLog.setChannel(RefundChannel.weixin);
            refundLog.setOrderName(order.getName());
            refundLog.setResult(RefundStatus.WAITING);  //待审核
            refundLog.setTotalRefund(rFee);
            refundLog.setRefundNo(refundNo);
            refundLog.setTradeNo(orderNo);
            refundLog.setOrderNo(orderNo);
            refundLog.setCreateTime(new Date());
            refundLog.setUpdateTime(new Date());
            refundLogService.saveOrUpdate(refundLog);
            // 微信支付方式退款 (微信金额单位为 分)

            // 返回退款结果
            result.put("isAbleToCancel", true);
            result.put("msg", "提交退订成功!");

            // 返回退款结果
//            result.put("isAbleToCancel", refundResult.isSuccess());
//            if (refundResult.isSuccess() && !StringUtils.hasText(refundResult.getErrMsg())) {
//                result.put("msg", "提交退订成功!");
//            } else if (!refundResult.isSuccess() && StringUtils.hasText(refundResult.getMsg())) {
//                result.put("msg", refundResult.getMsg());
//            } else if (!refundResult.isSuccess() && StringUtils.hasText(refundResult.getErrMsg())) {
//                result.put("msg", refundResult.getErrMsg());
//            } else if (!refundResult.isSuccess() && StringUtils.hasText(refundResult.getErrCode())) {
//                result.put("msg", refundResult.getErrCode());
//            } else if (!refundResult.isSuccess() && !StringUtils.hasText(refundResult.getErrMsg())
//                    && !StringUtils.hasText(refundResult.getMsg()) && !StringUtils.hasText(refundResult.getErrCode())) {
//                result.put("msg", "微信返回退款失败! 微信没有返回任何消息! 请先检查证书以及商户配置是否正确!");
//            } else {
//                result.put("msg", "没有可用的退款消息! 检查微信退款接口!");
//            }
        } else if (!StringUtils.hasText(order.getWechatCode())) {
            if (orderDetail != null) {
                OrderLog orderRefundLog2 = orderLogService.createOrderLog(user, "订单详情##"
                        + orderDetail.getId() + "发起支付宝退款, 申请的退款金额为:" + rFee, order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderRefundLog2);
            } else {
                OrderLog orderRefundLog2 = orderLogService.createOrderLog(user, "订单##"
                        + order.getId() + "发起支付宝退款, 申请的退款金额为:" + rFee, order.getId(), null);
                orderLogService.loggingOrderLog(orderRefundLog2);
            }
            //PayLog payLog = payLogService.findPayLogByOrder(order.getId(), order.getUser().getId(), PayAction.requestback);
            refundFee = refundFee / 100;
            refundLog.setOrderId(order.getId());
            refundLog.setRefundDesc("无理由退款");
            refundLog.setChannel(RefundChannel.taobao);
            refundLog.setOrderName(order.getName());
            refundLog.setResult(RefundStatus.WAITING);  //待审核
            refundLog.setTotalRefund(refundFee.floatValue());
            refundLog.setTradeNo(order.getTradeNo());
            refundLog.setRefundNo(refundNo);
            refundLog.setOrderNo(order.getOrderNo());
            refundLog.setCreateTime(new Date());
            refundLog.setUpdateTime(new Date());
            refundLogService.saveOrUpdate(refundLog);
            // 返回退款结果
            result.put("isAbleToCancel", true);
            result.put("msg", "提交退订成功!");
        } else {
            // TODO 其他方式退款
            if (orderDetail != null) {
                OrderLog orderRefundLog2 = orderLogService.createOrderLog(user, "订单详情##"
                        + orderDetail.getId() + "未找到相应退款方式, 申请的退款金额为:" + rFee, order.getId(), orderDetail.getId());
                orderLogService.loggingOrderLog(orderRefundLog2);
            } else {
                OrderLog orderRefundLog2 = orderLogService.createOrderLog(user, "订单##"
                        + order.getId() + "未找到相应退款方式, 申请的退款金额为:" + rFee, order.getId(), null);
                orderLogService.loggingOrderLog(orderRefundLog2);
            }
            // 返回退款结果
            result.put("isAbleToCancel", false);
            result.put("msg", "退订失败, 没有找到退款方式");
        }
        return result;
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
    private RefundResult doPreRefund(String orderNo, String refundNo, Integer totalFee, Integer refundFee, Long siteId, String wxAccount) {
        WechatAccount wechatAccount = wechatAccountService.findUniqueSiteAccount(wxAccount, siteId);
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
        paramMap.clear();
        paramMap.put("appid", wechatAccount.getAppId());      // 公众账号ID
        paramMap.put("mch_id", wechatAccount.getMchId());     // 商户号
        paramMap.put("out_trade_no", orderNo);   // 商户订单号：32个字符内，唯一，重新支付用原订单号
        RefundLog refundLog = refundLogService.findRefundLogByTradeNo(orderNo, RefundStatus.SUCCESS);
        if (!refundResult.isSuccess()) {
            refundLog.setResult(RefundStatus.FAIL);
            refundLog.setRefundDesc(refundResult.getErrMsg());
            refundLog.setUpdateTime(new Date());
            refundLogService.saveOrUpdate(refundLog);
            return refundResult;
        }
        //查询退款结果
        QueryRefundResult queryRefundResult = WeChatPay.queryRefundOrder(paramMap, paternerKey);
        if (!queryRefundResult.getReturnCode() && !queryRefundResult.getResultCode()) {
            refundLog.setRequeryRefundDesc(queryRefundResult.getErrCode() + ":" +queryRefundResult.getErrCodeDesc());
            refundLog.setUpdateTime(new Date());
            refundLogService.saveOrUpdate(refundLog);
            return refundResult;
        }

        if (queryRefundResult.getRefundCount() > 0) {
            for (RefundResultDetail detail : queryRefundResult.getRefundResultDetailList()) {
                if ("PROCESSING".equals(detail.getStatus())) {
                    refundLog.setResult(RefundStatus.PROCESSING);
                    refundLog.setRefundDesc("请前往微信商户平台进行退款！");
                } else if ("SUCCESS".equals(detail.getStatus())) {
                    refundLog.setResult(RefundStatus.SUCCESS);
                    refundLog.setRefundDesc("退款成功！");
                } else if ("NOTSURE".equals(detail.getStatus())) {
                    refundLog.setResult(RefundStatus.NOTSURE);
                    refundLog.setRefundDesc("未确定，需要商户原退款单号重新发起！");
                } else if ("CHANGE".equals(detail.getStatus())) {
                    refundLog.setResult(RefundStatus.CHANGE);
                    refundLog.setRefundDesc("转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款！");
                }
            }
        }

        refundLog.setUpdateTime(new Date());
        refundLogService.saveOrUpdate(refundLog);

        return refundResult;
    }

}
