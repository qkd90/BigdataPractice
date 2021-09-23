package com.data.data.hmly.action.outOrder;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.outOrder.vo.OrderTouristVo;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.OrderTouristService;
import com.data.data.hmly.service.outOrder.OutOrderEditService;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.framework.struts.JsonDateValueProcessor;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/9/6.
 */
public class OutOrderEditAction extends FrameBaseAction {

    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private OutOrderEditService outOrderEditService;
    @Resource
    private OrderTouristService orderTouristService;

    private Long orderId;
    private Long orderDetailId;
    private String targetDate;
    private Long costId;
    private String recName;
    private String mobile;
    private Boolean reSendMsg;

    private List<OrderTouristVo> orderTouristVos = new ArrayList<OrderTouristVo>();

    public Result editOrderDetailDate() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (orderId != null && orderDetailId != null && StringUtils.hasText(targetDate) && costId != null) {
            Order sourceOrder = orderService.get(orderId);
            OrderDetail sourceOrderDetail = orderDetailService.get(orderDetailId);
            if (ProductType.scenic.equals(sourceOrderDetail.getProductType()) && sourceOrder != null && sourceOrderDetail != null) {
                result = outOrderEditService.editOrderDetailDate(result, sourceOrder, sourceOrderDetail, targetDate);
            } else {
                result.put("success", false);
                result.put("msg", "只可以修改门票订单详情或者订单不存在!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "订单信息不完整, 无法修改!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result getEditTicketPrice() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (orderDetailId != null) {
            OrderDetail orderDetail = orderDetailService.get(orderDetailId);
            Long costId = orderDetail == null ? null : orderDetail.getCostId();
            if (costId != null) {
                List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.getSamePriceByPriceId(costId, orderDetail.getPlayDate(), orderDetail.getNum());
                boolean success = ticketDatepriceList == null || ticketDatepriceList.isEmpty() ? false : true;
                String msg = success ? "" : "无可用门票价格";
                result.put("success", success);
                result.put("msg", msg);
                result.put("data", ticketDatepriceList);
            } else {
                result.put("success", false);
                result.put("msg", "订单详情不存在或订单详情缺少门票价格ID-(CostId)!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "缺少订单详情ID!");
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("ticketPriceId");
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result getEditTicketInfo() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (orderDetailId != null) {
            OrderDetail sourceOrderDetail = orderDetailService.findFullById(orderDetailId);
            Order sourceOrder = sourceOrderDetail == null ? null : sourceOrderDetail.getOrder();
            if (sourceOrderDetail != null && sourceOrder != null) {
                List<OrderTourist> orderTouristList = sourceOrderDetail.getOrderTouristList();
                boolean success = orderTouristList == null || orderTouristList.isEmpty() ? false : true;
                String msg = orderTouristList.isEmpty() ? "旅客列表为空! 无法修改" : "";
                result.put("success", success);
                result.put("msg", msg);
                result.put("order", sourceOrder);
                result.put("orderDetail", sourceOrderDetail);
            } else {
                result.put("success", false);
                result.put("msg", "订单详情或订单不存在!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "缺少订单详情ID!");
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("orderTouristList");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result editOrderDetailInfo() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (orderId != null && orderDetailId != null) {
            Order sourceOrder = orderService.get(orderId);
            OrderDetail sourceOrderDetail = orderDetailService.get(orderDetailId);
            if (ProductType.scenic.equals(sourceOrderDetail.getProductType()) && sourceOrder != null && sourceOrderDetail != null) {
                result.put("recName", recName);
                result.put("mobile", mobile);
                result.put("reSendMsg", reSendMsg);
                result = outOrderEditService.editOrderDetailInfo(result, sourceOrder, sourceOrderDetail, orderTouristVos);
            } else {
                result.put("success", false);
                result.put("msg", "只可以修改门票订单详情或者订单/订单详情不存在!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "订单信息不完整, 无法修改!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getReSendMsg() {
        return reSendMsg;
    }

    public void setReSendMsg(Boolean reSendMsg) {
        this.reSendMsg = reSendMsg;
    }

    public List<OrderTouristVo> getOrderTouristVos() {
        return orderTouristVos;
    }

    public void setOrderTouristVos(List<OrderTouristVo> orderTouristVos) {
        this.orderTouristVos = orderTouristVos;
    }
}
