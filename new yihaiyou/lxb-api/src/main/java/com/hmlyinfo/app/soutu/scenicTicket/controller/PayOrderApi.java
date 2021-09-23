package com.hmlyinfo.app.soutu.scenicTicket.controller;

import com.hmlyinfo.app.soutu.scenicTicket.domain.PayOrder;
import com.hmlyinfo.app.soutu.scenicTicket.service.PayOrderService;
import com.hmlyinfo.app.soutu.scenicTicket.service.ScenicTicketOrderService;
import com.hmlyinfo.app.soutu.scenicTicket.service.WeiXinService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/api/public/payOrder")
public class PayOrderApi {

    Logger logger = Logger.getLogger(PayOrderApi.class);

    @Autowired
    private PayOrderService service;
    @Autowired
    private ScenicTicketOrderService scenicTicketOrderService;
    @Autowired
    private WeiXinService wxService;

    /**
     * 查询列表
     * <ul>
     * <li>可选：分页大小{pageSize=10}</li>
     * <li>可选：请求页码{page=1}</li>
     * <li>url:/api/PayOrder/list</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public ActionResult list(Long id) {
        return ActionResult.createSuccess(service.info(id));
    }

    /**
     * 统一下订单
     *
     * @return
     */
    @RequestMapping("/unifiedOrder")
    @ResponseBody
    public ActionResult unifiedOrder(PayOrder payOrder, int type, String notifyUrl) {
        service.unifiedOrder(payOrder, type, notifyUrl);
        return ActionResult.createSuccess();
    }

    /**
     * 统一下订单
     *
     * @return
     */
    @RequestMapping("/refund")
    @ResponseBody
    public ActionResult refund(Long id, int type) {
        service.refund(id, type);
        return ActionResult.createSuccess();
    }

//    /**
//     * 使用门票订单下支付订单
//     */
//    @RequestMapping("/planOrder")
//    @ResponseBody
//    public ActionResult planOrder(Long scenicTicketOrderId, int type) {
//        service.
//    }

    /**
     * 微信下单回调地址
     * @throws Exception 
     */
    @RequestMapping("/weixinCallBack")
    @ResponseBody
    public ActionResult weixinBack(HttpServletRequest request) throws Exception {
        Map<String, String> map = HttpUtil.parseXml(request);
        Map<String, Object> orderMap = new TreeMap<String, Object>();
        String sign = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if ("sign".equals(entry.getKey())) {
                sign = entry.getValue();
                continue;
            }
            orderMap.put(entry.getKey(), entry.getValue());
        }
        String validateStr = WeiXinService.getSign(orderMap);
        if (!validateStr.equals(sign)) {
            logger.error("签名验证失败");
            return null;
        }
        Long payOrderId = Long.valueOf(orderMap.get("out_trade_no").toString());
        long orderId = scenicTicketOrderService.payOrderByPayOrder(payOrderId);
        wxService.pushTicketMessage(orderId);
        return null;
    }
    
}
