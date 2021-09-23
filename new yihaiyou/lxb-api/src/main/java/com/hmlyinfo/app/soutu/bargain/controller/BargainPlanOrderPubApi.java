package com.hmlyinfo.app.soutu.bargain.controller;

import com.hmlyinfo.app.soutu.bargain.service.BargainPlanOrderService;
import com.hmlyinfo.app.soutu.scenicTicket.service.WeiXinService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by guoshijie on 2015/5/19.
 */
@Controller
@RequestMapping("/api/pub/bargain/plan/order")
public class BargainPlanOrderPubApi {

    private static Logger logger = Logger.getLogger(BargainPlanOrderPubApi.class);

    @Autowired
    BargainPlanOrderService bargainPlanOrderService;
    @Autowired
    WeiXinService wxService;

    @RequestMapping("/weixincallback")
    public ActionResult callback(HttpServletRequest request) {
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
        long bargainOrderId = bargainPlanOrderService.payOrderByPayOrder(payOrderId);
        wxService.pushBargainMessage(bargainOrderId);
        return null;
    }
}
