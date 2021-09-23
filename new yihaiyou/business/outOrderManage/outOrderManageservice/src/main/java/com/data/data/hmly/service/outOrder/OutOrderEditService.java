package com.data.data.hmly.service.outOrder;

import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.OrderTouristService;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.outOrder.entity.JszxOrder;
import com.data.data.hmly.service.outOrder.entity.JszxOrderDetail;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
import com.data.data.hmly.service.outOrder.vo.OrderTouristVo;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/9/14.
 */
@Service
public class OutOrderEditService {

    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private JszxOrderDetailService jszxOrderDetailService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private OrderTouristService orderTouristService;
    @Resource
    private ProductValidateCodeService productValidateCodeService;
    @Resource
    private JszxOrderService jszxOrderService;
    @Resource
    private OrderService orderService;


    public Map<String, Object> editOrderDetailDate(Map<String, Object> result, Order sourceOrder, OrderDetail sourceOrderDetail, String playDateStr) {
        User user = orderLogService.getSysOrderLogUser();
        String realOrderIdStr = sourceOrderDetail.getRealOrderId();
        List<OrderTourist> sourceOrderTouristList = orderTouristService.getByOrderDetailId(sourceOrderDetail.getId());
        if (!StringUtils.hasText(playDateStr)) {
            result.put("success", false);
            result.put("msg", "请选择要修改的日期!");
            return result;
        }
//        if (!StringUtils.hasText(realOrderIdStr)) {
//            result.put("success", false);
//            result.put("msg", "缺少供应商订单详情ID(realOrderId)!");
//            return result;
//        }
        if (sourceOrderTouristList.isEmpty()) {
            result.put("success", false);
            result.put("msg", "订单详情旅客列表为空! 不能修改!");
            return result;
        }

        JszxOrderDetail sourceJszxOrderDetail = null;
        if (StringUtils.hasText(realOrderIdStr)) {
            Long jszxOrderDetailId =  Long.parseLong(realOrderIdStr);
            sourceJszxOrderDetail = jszxOrderDetailService.get(jszxOrderDetailId);
            if (sourceJszxOrderDetail == null) {
                result.put("success", false);
                result.put("msg", "供应商订单详情不存在!");
                return result;
            }
            if (sourceJszxOrderDetail.getCount() != sourceJszxOrderDetail.getRestCount()) {
                result.put("success", false);
                result.put("msg", "门票有验票记录, 不可修改!");
                return result;
            }
        }

        // 新的使用时间
        Date targetPlayDate = DateUtils.getDate(playDateStr, "yyyy-MM-dd");


        // 操作库存
        // 加库存
        TicketDateprice sourceTicketDateprice = ticketDatepriceService.getTicketDatePrice(sourceOrderDetail.getCostId(), sourceOrderDetail.getPlayDate());
        sourceTicketDateprice.setInventory(sourceTicketDateprice.getInventory() + sourceOrderDetail.getNum());
        ticketDatepriceService.update(sourceTicketDateprice);
        // 减库存
        TicketDateprice targetTicketDateprice = ticketDatepriceService.getByDate(sourceOrderDetail.getCostId(), targetPlayDate);
        targetTicketDateprice.setInventory(targetTicketDateprice.getInventory() - sourceOrderDetail.getNum());


        // 新的订单对象
        OrderDetail targetOrderDetail = new OrderDetail();
        // 新旅客列表
        List<OrderTourist> targetOrderTouristList = new ArrayList<OrderTourist>();
        // 复制对象属性
        BeanUtils.copyProperties(sourceOrderDetail, targetOrderDetail, "id", "orderTouristList", "commissions", "orderDetailFlattened");
        targetOrderDetail.setOrder(sourceOrder);
        // 更新修改信息
        targetOrderDetail.setPlayDate(targetPlayDate);
        // 保存新的订单详情信息
        orderDetailService.save(targetOrderDetail);
        // 取消原有订单详情
        sourceOrderDetail.setStatus(OrderDetailStatus.CANCELED);
        sourceOrderDetail.setApiResult(sourceOrderDetail.getApiResult() + "(" + "订单日期修改为: " +
                playDateStr + ", 新的订单详情id为: " + targetOrderDetail.getId());
        orderDetailService.update(sourceOrderDetail);
        // 处理新的旅客列表信息
        for (OrderTourist sourceOrderTourist : sourceOrderTouristList) {
            OrderTourist targetOrderTourist = new OrderTourist();
            BeanUtils.copyProperties(sourceOrderTourist, targetOrderTourist, "id", "orderDetail");
            targetOrderTourist.setOrderDetail(targetOrderDetail);
            targetOrderTouristList.add(targetOrderTourist);
        }
        orderTouristService.saveAll(targetOrderTouristList);
        //
        // 已经向供应商系统下单的情况下, 需要处理供应商系统详情
        // 以及处理验证码部分
        if (StringUtils.hasText(realOrderIdStr) && sourceJszxOrderDetail != null) {
            // 新建供应商订单详情
            JszxOrderDetail targetJszxOrderDetail = new JszxOrderDetail();
            BeanUtils.copyProperties(sourceJszxOrderDetail, targetJszxOrderDetail, "id");
            targetJszxOrderDetail.setJszxOrder(sourceJszxOrderDetail.getJszxOrder());
            // 更新供应商订单详情
            targetJszxOrderDetail.setUseTime(targetPlayDate);
            // 保存新的供应商订单详情
            jszxOrderDetailService.save(targetJszxOrderDetail);
            targetOrderDetail.setRealOrderId(Long.toString(targetJszxOrderDetail.getId()));
            // 更新的订单详情
            orderDetailService.update(targetOrderDetail);
            // 取消原有供应商订单详情
            sourceJszxOrderDetail.setUseStatus(JszxOrderDetailStatus.CANCEL);
            jszxOrderDetailService.update(sourceJszxOrderDetail);
            // 处理验证码
            ProductValidateCode sourceProductValidateCode = productValidateCodeService.getPvCode(sourceJszxOrderDetail.getTicketNo());
            if (sourceProductValidateCode != null) {
                sourceProductValidateCode.setUsed(-1);
                productValidateCodeService.update(sourceProductValidateCode);
            }
            // 发送新验证码
            jszxOrderService.doSendMsg(targetJszxOrderDetail, null);
        }
        //
        // 写入订单日志
        OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单详情(G)#" + sourceOrderDetail.getId()
                + "修改使用日期, 从: " + DateUtils.format(sourceOrderDetail.getPlayDate(), "yyyy-MM-dd")
                + "修改为: " + DateUtils.format(targetOrderDetail.getPlayDate(), "yyyy-MM-dd")
                + "新的订单详情id为:" + targetOrderDetail.getId()
                + ", 现在状态:" + sourceOrderDetail.getStatus().getDescription(), sourceOrder.getId(), sourceOrderDetail.getId());
        orderLogService.loggingOrderLog(orderLog1);
        result.put("success", true);
        result.put("msg", "修改完成!");
        return result;
    }


    public Map<String, Object> editOrderDetailInfo(Map<String, Object> result, Order sourceOrder, OrderDetail sourceOrderDetail, List<OrderTouristVo> orderTouristVos) {
        User user = orderLogService.getSysOrderLogUser();
        String realOrderIdStr = sourceOrderDetail.getRealOrderId();
        List<OrderTourist> sourceOrderTourists = orderTouristService.getByOrderDetailId(sourceOrderDetail.getId());
        Map<Long, OrderTourist> orderTouristMap = orderTouristService.orderTouristToMap(sourceOrderTourists);
        if (orderTouristVos.isEmpty()) {
            result.put("success", false);
            result.put("msg", "没有要修改的旅客信息!");
            return result;
        }
        if (sourceOrderTourists.isEmpty()) {
            result.put("success", false);
            result.put("msg", "订单详情旅客列表为空! 不能修改!");
            return result;
        }
        JszxOrderDetail sourceJszxOrderDetail = null;
        JszxOrder sourceJszxOrder = null;
        if (StringUtils.hasText(realOrderIdStr)) {
            Long jszxOrderDetailId =  Long.parseLong(realOrderIdStr);
            sourceJszxOrderDetail = jszxOrderDetailService.get(jszxOrderDetailId);
            sourceJszxOrder = sourceJszxOrderDetail.getJszxOrder();
            if (sourceJszxOrderDetail == null) {
                result.put("success", false);
                result.put("msg", "供应商订单详情不存在!");
                return result;
            }
            if (sourceJszxOrderDetail.getCount() != sourceJszxOrderDetail.getRestCount()) {
                result.put("success", false);
                result.put("msg", "门票有验票记录, 不可修改!");
                return result;
            }
        }

        // 更新订单联系人, 电话信息
        String recName = result.get("recName").toString();
        String mobile = result.get("mobile").toString();
        if (StringUtils.hasText(recName) && !recName.equals(sourceOrder.getRecName())) {
            // 写入订单日志
            OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单(G)#" + sourceOrderDetail.getId()
                    + "修改订单联系人, 从: " + sourceOrder.getRecName()
                    + "修改为: " + recName, sourceOrder.getId(), sourceOrderDetail.getId());
            orderLogService.loggingOrderLog(orderLog1);
            sourceOrder.setRecName(recName);
        }
        if (StringUtils.hasText(mobile) && !mobile.equals(sourceOrder.getMobile())) {
            // 写入订单日志
            OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单(G)#" + sourceOrderDetail.getId()
                    + "修改订单联系人电话, 从: " + sourceOrder.getMobile()
                    + "修改为: " + mobile, sourceOrder.getId(), sourceOrderDetail.getId());
            orderLogService.loggingOrderLog(orderLog1);
            sourceOrder.setMobile(mobile);
        }
        orderService.update(sourceOrder);

        // 更新旅客列表
        for (OrderTouristVo orderTouristVo : orderTouristVos) {
            OrderTourist sourceOrderTourist = orderTouristMap.get(orderTouristVo.getId());
            if (StringUtils.hasText(orderTouristVo.getName()) && !orderTouristVo.getName().equals(sourceOrderTourist.getName())) {
                // 写入订单日志
                OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单详情(G)#" + sourceOrderDetail.getId()
                        + "修改订单旅客姓名, 从: " + sourceOrderTourist.getName()
                        + "修改为: " + orderTouristVo.getName(), sourceOrder.getId(), sourceOrderDetail.getId());
                orderLogService.loggingOrderLog(orderLog1);
                sourceOrderTourist.setName(orderTouristVo.getName());

            }
            if (StringUtils.hasText(orderTouristVo.getTel()) && !orderTouristVo.getTel().equals(sourceOrderTourist.getTel())) {
                // 写入订单日志
                OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单详情(G)#" + sourceOrderDetail.getId()
                        + "修改订单旅客电话, 从: " + sourceOrderTourist.getTel()
                        + "修改为: " + orderTouristVo.getTel(), sourceOrder.getId(), sourceOrderDetail.getId());
                orderLogService.loggingOrderLog(orderLog1);
                sourceOrderTourist.setTel(orderTouristVo.getTel());
            }
            if (StringUtils.hasText(orderTouristVo.getIdNumber()) && !orderTouristVo.getIdNumber().equals(sourceOrderTourist.getIdNumber())) {
                // 写入订单日志
                OrderLog orderLog1 = orderLogService.createOrderLog(user, "订单详情(G)#" + sourceOrderDetail.getId()
                        + "修改订单旅客身份证号码, 从: " + sourceOrderTourist.getIdNumber()
                        + "修改为: " + orderTouristVo.getIdNumber(), sourceOrder.getId(), sourceOrderDetail.getId());
                orderLogService.loggingOrderLog(orderLog1);
                sourceOrderTourist.setIdNumber(orderTouristVo.getIdNumber());
            }
        }
        orderTouristService.updateAll(sourceOrderTourists);

        // 已经向供应商系统下单的情况下, 需要处理供应商系统详情
        if (StringUtils.hasText(realOrderIdStr) && sourceJszxOrder != null) {
            // 处理联系人信息
            if (StringUtils.hasText(recName) && !recName.equals(sourceJszxOrder.getContact())) {
                sourceJszxOrder.setContact(recName);
            }
            if (StringUtils.hasText(mobile) && !mobile.equals(sourceJszxOrder.getPhone())) {
                sourceJszxOrder.setPhone(mobile);
                // 处理验证码部分
                // 重发验票短信(如果订单联系人电话发生变化)
                boolean reSendMsg = (Boolean) result.get("reSendMsg");
                if (reSendMsg) {
                    jszxOrderService.doSendMsg(sourceJszxOrder, null);
                }
            }
            jszxOrderService.update(sourceJszxOrder, sourceJszxOrder.getUser(), sourceJszxOrder.getCompanyUnit());
        }
        result.clear();
        result.put("success", true);
        result.put("msg", "修改完成!");
        return result;
    }
}
