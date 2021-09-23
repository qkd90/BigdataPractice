package com.data.data.hmly.service.order;

import com.data.data.hmly.service.common.MsgService;
import com.data.data.hmly.service.common.ProValidCodeService;
import com.data.data.hmly.service.common.entity.ProValidCode;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.google.common.collect.Lists;
import com.zuipin.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/12/22.
 */
@Service
public class OrderValidateCodeService {
    @Resource
    private ProValidCodeService proValidCodeService;
    @Resource
    private MsgService msgService;

    /**
     * 订单验证码生成
     * @param order
     * @param orderDetail
     * @return
     */
    public List<Map<String, Object>> doCreateValidateCode(Order order, OrderDetail orderDetail) {
        List<Map<String, Object>> returnMapList = Lists.newArrayList();
        if (order == null) {
            return returnMapList;
        }
        if (orderDetail == null) {
            return returnMapList;
        }
        switch (order.getOrderType()) {
            case huanguyou:
                doCreateSailboatTicketValidCode(order, orderDetail, orderDetail.getNum(), returnMapList);
                break;
            case yacht:
                doCreateSailboatTicketValidCode(order, orderDetail, orderDetail.getNum(), returnMapList);
                break;
            case sailboat:
                doCreateSailboatTicketValidCode(order, orderDetail, orderDetail.getNum(), returnMapList);
                break;
            case hotel:
                doCreateHotelValidCode(order, orderDetail, returnMapList);
                break;
            case ticket:
                doCreateSailboatTicketValidCode(order, orderDetail, orderDetail.getNum(), returnMapList);
                break;
            case cruiseship:
                break;
            case plan:
                switch (orderDetail.getProductType()) {
                    case scenic:
                        doCreateSailboatTicketValidCode(order, orderDetail, orderDetail.getNum(), returnMapList);
                        break;
                    case hotel:
                        doCreateHotelValidCode(order, orderDetail, returnMapList);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return returnMapList;
    }

    public void doCreateSailboatTicketValidCode(Order order, OrderDetail orderDetail, Integer count, List<Map<String, Object>> returnMapList) {
        for (int i = 0; i < count; i++) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            ProValidCode pvCode = new ProValidCode();
            pvCode.setOrderId(order.getId());
            pvCode.setOrderDetailId(orderDetail.getId());
            pvCode.setOrderNo(order.getOrderNo());
            pvCode.setCount(1);
            pvCode.setProduct(orderDetail.getProduct());
            pvCode.setUsed(0);
            pvCode.setBuyer(order.getUser());
            pvCode.setBuyerName(order.getRecName());
            pvCode.setValidTime(DateUtils.getStartDay(orderDetail.getPlayDate(), 0));
            pvCode.setInvalidTime(DateUtils.getEndDay(orderDetail.getPlayDate(), 0));
            pvCode.setBuyerMobile(order.getMobile());
            pvCode.setSupplierName(orderDetail.getProduct().getCompanyUnit().getName());
            pvCode.setSupplierId(orderDetail.getProduct().getCompanyUnit().getId());
            pvCode.setProductName(orderDetail.getProduct().getName());
            pvCode.setProductTypeName(orderDetail.getSeatType());
            String code = msgService.checkCode(pvCode);
            pvCode.setCode(code);
            proValidCodeService.save(pvCode);
            resultMap.put("code", code);
            resultMap.put("orderDetailId", orderDetail.getId());
            resultMap.put("orderId", order.getId());
            returnMapList.add(resultMap);
        }
    }

    public void doCreateHotelValidCode(Order order, OrderDetail orderDetail, List<Map<String, Object>> returnMapList) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ProValidCode pvCode = new ProValidCode();
        pvCode.setOrderId(order.getId());
        pvCode.setOrderDetailId(orderDetail.getId());
        pvCode.setOrderNo(order.getOrderNo());
        pvCode.setCount(1);
        pvCode.setProduct(orderDetail.getProduct());
        pvCode.setUsed(0);
        pvCode.setBuyer(order.getUser());
        pvCode.setBuyerName(order.getRecName());
        pvCode.setValidTime(DateUtils.getStartDay(orderDetail.getPlayDate(), 0));
        pvCode.setInvalidTime(DateUtils.getEndDay(orderDetail.getPlayDate(), 0));
        pvCode.setBuyerMobile(order.getMobile());
        pvCode.setSupplierName(orderDetail.getProduct().getCompanyUnit().getName());
        pvCode.setSupplierId(orderDetail.getProduct().getCompanyUnit().getId());
        pvCode.setProductName(orderDetail.getProduct().getName());
        pvCode.setProductTypeName(orderDetail.getSeatType());
        String code = msgService.checkCode(pvCode);
        pvCode.setCode(code);
        proValidCodeService.save(pvCode);
        resultMap.put("code", code);
        resultMap.put("orderDetailId", orderDetail.getId());
        resultMap.put("orderId", order.getId());
        returnMapList.add(resultMap);
    }

}
