package com.data.data.hmly.action.order;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.OrderCancelService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.opensymphony.xwork2.Result;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2016/3/31.
 */
public class OrderCancelAction extends FrameBaseAction {

    @Resource
    private OrderCancelService orderCancelService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;


    public Result cancelByOrderDetail() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String detailId = request.getParameter("orderDetailId");
            if (detailId != null && Long.parseLong(detailId) > 0) {
                Long orderDetailId = Long.parseLong(detailId);
                OrderDetail orderDetail = orderDetailService.get(orderDetailId);
                SysUser loginUser = getLoginUser();
                if (loginUser == null) {
                    result = orderService.handleTicketCancelResult(result, "请先登录!", orderDetail, false);
                    return json(JSONObject.fromObject(result));
                }
                Order order = orderDetail.getOrder();
                //...
                result = orderCancelService.doStartCancel(order, orderDetail, loginUser);
                //...
            } else {
                result.put("isAbleToCancel", false);
                result.put("msg", "无效订单详情!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("isAbleToCancel", false);
            result.put("msg", "退订异常!");
        }
        return json(JSONObject.fromObject(result));
    }

}
