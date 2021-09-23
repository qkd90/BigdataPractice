package com.data.data.hmly.service.order;

import com.data.data.hmly.service.order.dao.ShenzhouOrderDao;
import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.ShenzhouOrder;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.entity.enums.ShenzhouOrderStatus;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangpeijie on 2016-09-08,0008.
 */
@Service
public class ShenzhouOrderService {
    @Resource
    private ShenzhouOrderDao shenzhouOrderDao;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private OrderDetailService orderDetailService;

    /**
     * 账单详情查询
     * @param orderBillSummary
     * @param page
     * @return
     */
    public List<ShenzhouOrder> getOrderBillDetail(OrderBillSummary orderBillSummary, Page page) {
        return shenzhouOrderDao.getOrderBillDetail(orderBillSummary, page);
    }

    public void save(ShenzhouOrder shenzhouOrder) {
        shenzhouOrderDao.save(shenzhouOrder);
    }

    public void update(ShenzhouOrder shenzhouOrder) {
        shenzhouOrderDao.update(shenzhouOrder);
    }

    public void saveOrUpdate(ShenzhouOrder shenzhouOrder) {
        shenzhouOrderDao.saveOrUpdate(shenzhouOrder, shenzhouOrder.getId());
    }

    public ShenzhouOrder get(Long id) {
        return shenzhouOrderDao.load(id);
    }

    public List<ShenzhouOrder> getOrderList(ShenzhouOrder shenzhouOrder, Page pageInfo, String... orderTypes) {
        Criteria<ShenzhouOrder> criteria = new Criteria<ShenzhouOrder>(ShenzhouOrder.class);

        createCriteria(shenzhouOrder, criteria);
//        criteria.ne("status", ShenzhouOrderStatus.deleted);
        criteria.ne("deleteFlag", true);

        if (orderTypes.length > 1) {
            criteria.orderBy(orderTypes[0], orderTypes[1]);
        } else if (orderTypes.length == 1) {
            criteria.orderBy(Order.desc(orderTypes[0]));
        }

        if (pageInfo == null) {
            return shenzhouOrderDao.findByCriteria(criteria);
        }
        return shenzhouOrderDao.findByCriteria(criteria, pageInfo);
    }

    private void createCriteria(ShenzhouOrder shenzhouOrder, Criteria<ShenzhouOrder> criteria) {
        if (shenzhouOrder == null) {
            return;
        }

        if (StringUtils.isNotBlank(shenzhouOrder.getShenzhouOrderId())) {
            criteria.eq("shenzhouOrderId", shenzhouOrder.getShenzhouOrderId());
        }

        if (StringUtils.isNotBlank(shenzhouOrder.getOrderNo())) {
            criteria.eq("orderNo", shenzhouOrder.getOrderNo());
        }

        if (shenzhouOrder.getServiceId() != null) {
            criteria.eq("serviceId", shenzhouOrder.getServiceId());
        }

        if (shenzhouOrder.getCarGroupId() != null) {
            criteria.eq("carGroupId", shenzhouOrder.getCarGroupId());
        }

        if (shenzhouOrder.getStatus() != null) {
            criteria.eq("status", shenzhouOrder.getStatus());
        }

        if (shenzhouOrder.getPaymentStatus() != null) {
            criteria.eq("paymentStatus", shenzhouOrder.getPaymentStatus());
        }

        if (StringUtils.isNotBlank(shenzhouOrder.getPassengerName())) {
            criteria.eq("passengerName", shenzhouOrder.getPassengerName());
        }

        if (StringUtils.isNotBlank(shenzhouOrder.getPassengerMobile())) {
            criteria.eq("passengerMobile", shenzhouOrder.getPassengerMobile());
        }

        if (StringUtils.isNotBlank(shenzhouOrder.getVehicleNo())) {
            criteria.eq("vehicleNo", shenzhouOrder.getVehicleNo());
        }

        if (shenzhouOrder.getStartTime() != null) {
            criteria.ge("createTime", shenzhouOrder.getStartTime());
        }

        if (shenzhouOrder.getEndTime() != null) {
            criteria.le("createTime", shenzhouOrder.getEndTime());
        }

        if (shenzhouOrder.getUser() != null) {
            if (StringUtils.isNotBlank(shenzhouOrder.getUser().getAccount())) {
                criteria.createCriteria("user", "u");
                criteria.eq("u.account", shenzhouOrder.getUser().getAccount());
            }
            if (shenzhouOrder.getUser().getId() != null) {
                criteria.eq("user.id", shenzhouOrder.getUser().getId());
            }
        }

    }

    public List<ShenzhouOrder> getOrderList() {
        Criteria<ShenzhouOrder> criteria = new Criteria<ShenzhouOrder>(ShenzhouOrder.class);
        criteria.ne("status", ShenzhouOrderStatus.invalid);
        criteria.ne("status", ShenzhouOrderStatus.canceled);
        criteria.ne("status", ShenzhouOrderStatus.completed);
        return shenzhouOrderDao.findByCriteria(criteria);
    }

    public void updateOrderBill(ShenzhouOrder order) {
        OrderBillType billType = OrderBillType.valueOf(propertiesManager.getString("SHENZHOU_BILL_TYPE"));
        Integer billDay = Integer.valueOf(propertiesManager.getString("SHENZHOU_BILL_DAY"));
        order.setOrderBillType(billType);
        order.setOrderBillDays(billDay);
        order.setOrderBillDate(orderDetailService.getBillDate(order.getOrderBillType(), order.getOrderBillDays()));
        order.setOrderBillStatus(0);
        if (order.getStatus().equals(ShenzhouOrderStatus.canceled)) {
            order.setOrderBillPrice(order.getCancelCost());
        } else {
            order.setOrderBillPrice(order.getTotalPrice());
        }
        update(order);
    }
}
