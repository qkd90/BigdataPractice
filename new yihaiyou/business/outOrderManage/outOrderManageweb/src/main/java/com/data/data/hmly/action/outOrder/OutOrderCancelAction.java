package com.data.data.hmly.action.outOrder;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.outOrder.OutOrderCancelService;
import com.opensymphony.xwork2.Result;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2016/6/7.
 */
public class OutOrderCancelAction extends FrameBaseAction {

    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderService orderService;
    @Resource
    private OutOrderCancelService outOrderCancelService;


    public Result cancelByOutOrderDetail() {
        final HttpServletRequest request = getRequest();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String detailId = request.getParameter("orderDetailId");
            if (detailId != null && Long.parseLong(detailId) > 0) {
                Long orderDetailId = Long.parseLong(detailId);
                OrderDetail orderDetail = orderDetailService.get(orderDetailId);
                Order order = orderDetail.getOrder();
                SysUser loginUser = getLoginUser();
                if (loginUser == null) {
                    result = orderService.handleTicketCancelResult(result, "请先登录!", orderDetail, false);
                    return json(JSONObject.fromObject(result));
                }
                result = orderService.isAbleToCancel(order, orderDetail);
                // 再次初步验证是否可以退订 (根据状态初审)
                if (!(Boolean) result.get("isAbleToCancel")) {
                    return json(JSONObject.fromObject(result));
                }
                //...
                result = outOrderCancelService.doStartCancel(order, orderDetail, loginUser);
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
