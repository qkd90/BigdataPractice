package com.hmlyinfo.app.soutu.bargain.controller;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanOrder;
import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanOrderItem;
import com.hmlyinfo.app.soutu.bargain.service.BargainPlanOrderService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenicTicket.service.PayOrderService;
import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.util.HttpUtil;
import com.hmlyinfo.base.util.Validate;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/auth/bargain/plan/order")
public class BargainPlanOrderApi {

    public static Logger logger = Logger.getLogger(BargainPlanOrderApi.class);

    @Autowired
    BargainPlanOrderService service;
    @Autowired
    PayOrderService payOrderService;

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/save")
    @ResponseBody
    public ActionResult save(final HttpServletRequest request) {
        String bargainStr = request.getParameter("bargainJson");
        String itemStr = request.getParameter("itemJson");
        try {
            BargainPlanOrder bargainPlanOrder = objectMapper.readValue(bargainStr, BargainPlanOrder.class);
            BargainPlanOrderItem[] bargainPlanOrderItems = objectMapper.readValue(itemStr, BargainPlanOrderItem[].class);
            service.order(bargainPlanOrder, bargainPlanOrderItems);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("id", bargainPlanOrder.getId());
            return ActionResult.createSuccess(result);
        } catch (IOException e) {
            logger.error("解析订单信息错误", e);
        }
        return ActionResult.createFail(ErrorCode.ERROR_56005, "下单失败");
    }

    @RequestMapping("/update")
    @ResponseBody
    public ActionResult update(final HttpServletRequest request) {
        String bargainStr = request.getParameter("bargainJson");
        String itemStr = request.getParameter("itemJson");
        try {
            BargainPlanOrder bargainPlanOrder = objectMapper.readValue(bargainStr, BargainPlanOrder.class);
            BargainPlanOrderItem[] bargainPlanOrderItems = objectMapper.readValue(itemStr, BargainPlanOrderItem[].class);
            service.update(bargainPlanOrder, bargainPlanOrderItems);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("id", bargainPlanOrder.getId());
            return ActionResult.createSuccess(result);
        } catch (IOException e) {
            logger.error("解析订单信息错误", e);
        }
        return ActionResult.createFail(ErrorCode.ERROR_56005, "订单信息更新失败");
    }

    @RequestMapping("/prepay")
    @ResponseBody
    public ActionResult pay(Long orderId, String ip) {
        Map<String, Object> result = service.prePayOrder(orderId, ip);
        return ActionResult.createSuccess(result);
    }

    /**
     * 查询列表
     * <ul>
     * <li>可选：分页大小{pageSize=10}</li>
     * <li>可选：请求页码{page=1}</li>
     * <li>url:/api/pay/list</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ActionResult list(final HttpServletRequest request) {
        Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
        paramMap.put("userId", MemberService.getCurrentUserId());

        return ActionResult.createSuccess(service.list(paramMap));
    }

    /**
     * 查询对象
     * <ul>
     * <li>必选:标识{id}</li>
     * <li>url:/api/pay/info</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public ActionResult info(Long id) {
        Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
        return ActionResult.createSuccess(service.info(Long.valueOf(id)));
    }

    @RequestMapping("/detail")
    @ResponseBody
    public ActionResult detail(Long id) {
        Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
        return ActionResult.createSuccess(service.detail(id));
    }

    @RequestMapping("delete")
    @ResponseBody
    public ActionResult delete(Long id) {
        Validate.notNull(id, ErrorCode.ERROR_50001, "标识{id}不能为空");
        service.delete(id);
        return ActionResult.createSuccess();
    }

    @RequestMapping("pay")
    @ResponseBody
    public ActionResult pay(Long id) {
        service.payOrder(id);
        return ActionResult.createSuccess();
    }
}
