package com.hmlyinfo.app.soutu.pay.allin.controller;


import com.hmlyinfo.app.soutu.pay.allin.domain.PaymentResult;
import com.hmlyinfo.app.soutu.pay.allin.service.AllInPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.Map;

/**
 * 通联支付入口
 * Created by guoshijie on 2015/7/22.
 */
@Controller
@RequestMapping("/api/pub/allinpay")
public class AllInPayApi {

    private static final String SCENIC_TICKET_ORDER_HANDLER = "scenicTicketOrderHandler";

    @Autowired
    private AllInPayService allInPayService;

    @RequestMapping("/orderScenic")
    public String order(Long scenicTicketOrderId, String returnUrl,  Model model) {
        System.out.println(new File("").getAbsolutePath());
        Map<String, Object> result = allInPayService.buildRequest(scenicTicketOrderId, returnUrl, SCENIC_TICKET_ORDER_HANDLER);
        model.addAttribute("signMsg", result.get("signMsg"));
        model.addAttribute("order", result.get("order"));
        return "pay/all_in_pay/post";
    }

    @RequestMapping("/notify")
    @ResponseBody
    public void notifyCallBack(PaymentResult paymentResult) {
        allInPayService.processResult(paymentResult);
        System.out.println("成功");
    }
}
