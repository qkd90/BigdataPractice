package com.data.data.hmly.service.order;

import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.common.YhyMsgService;
import com.data.data.hmly.service.entity.FerryMember;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.dao.FerryOrderDao;
import com.data.data.hmly.service.order.dao.FerryOrderItemDao;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.FerryOrderItem;
import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.util.FerryUtil;
import com.data.data.hmly.service.order.util.LvjiUtil;
import com.data.data.hmly.service.order.util.MsgTemplateKey;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-11-21,0021.
 */
@Service
public class FerryOrderService {
    @Resource
    private FerryOrderDao ferryOrderDao;
    @Resource
    private FerryOrderItemDao ferryOrderItemDao;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderService orderService;
    @Resource
    private YhyMsgService yhyMsgService;

    /**
     * 账单详情查询
     *
     * @param orderBillSummary
     * @param page
     * @return
     */
    public List<FerryOrder> getOrderBillDetail(OrderBillSummary orderBillSummary, Page page) {
        return ferryOrderDao.getOrderBillDetail(orderBillSummary, page);
    }

    public void saveOrder(FerryOrder order) {
        ferryOrderDao.save(order);
    }

    public void updateOrder(FerryOrder order) {
        ferryOrderDao.update(order);
    }

    public FerryOrder getOrder(Long id) {
        return ferryOrderDao.load(id);
    }

    public FerryOrder getOrderByNo(String orderNo) {
        Criteria<FerryOrder> criteria = new Criteria<>(FerryOrder.class);
        criteria.eq("orderNumber", orderNo);
        return ferryOrderDao.findUniqueByCriteria(criteria);
    }

    public void saveItem(FerryOrderItem item) {
        ferryOrderItemDao.save(item);
    }

    public void updateItem(FerryOrderItem item) {
        ferryOrderItemDao.update(item);
    }

    public FerryOrderItem getItem(Long id) {
        return ferryOrderItemDao.load(id);
    }

    public List<FerryOrder> getByOrderId(Long orderId) {
        Criteria<FerryOrder> criteria = new Criteria<FerryOrder>(FerryOrder.class);
        criteria.eq("orderId", orderId);
        return ferryOrderDao.findByCriteria(criteria);
    }

    public List<FerryOrder> getByUserId(Long userId, Page page, String... orderParams) {
        Criteria<FerryOrder> criteria = new Criteria<FerryOrder>(FerryOrder.class);
        criteria.eq("user.id", userId);
        criteria.isNull("orderId");
        criteria.ne("deleteFlag", true);
        if (orderParams.length > 0) {
            if (orderParams.length > 1) {
                criteria.orderBy(orderParams[0], orderParams[1]);
            } else {
                criteria.orderBy(Order.asc(orderParams[0]));
            }
        }
        if (page == null) {
            return ferryOrderDao.findByCriteria(criteria);
        }
        return ferryOrderDao.findByCriteria(criteria, page);
    }

    public List<FerryOrder> list(FerryOrder ferryOrder, Page page, String... orderParams) {
        Criteria<FerryOrder> criteria = new Criteria<FerryOrder>(FerryOrder.class);
        fmtCriteria(ferryOrder, criteria);
        if (ferryOrder.getCancelHandleTime() != null) {
            criteria.or(Restrictions.isNull("cancelHandleTime"), Restrictions.lt("cancelHandleTime", ferryOrder.getCancelHandleTime()));
        }
        if (ferryOrder.getUser() != null) {
            criteria.eq("user", ferryOrder.getUser());
        }
        if (orderParams.length > 0) {
            if (orderParams.length > 1) {
                criteria.orderBy(orderParams[0], orderParams[1]);
            } else {
                criteria.orderBy(Order.asc(orderParams[0]));
            }
        }
        if (page == null) {
            return ferryOrderDao.findByCriteria(criteria);
        }
        return ferryOrderDao.findByCriteria(criteria, page);
    }

    private void fmtCriteria(FerryOrder ferryOrder, Criteria<FerryOrder> criteria) {
        if (ferryOrder == null) {
            return;
        }

        if (ferryOrder.getStatus() != null) {
            criteria.eq("status", ferryOrder.getStatus());
        }

        if (StringUtils.isNotBlank(ferryOrder.getCreateTimeStart())) {
            criteria.ge("createTime", DateUtils.toDate(ferryOrder.getCreateTimeStart()));
        }

        if (StringUtils.isNotBlank(ferryOrder.getCreateTimeEnd())) {
            criteria.le("createTime", DateUtils.toDate(ferryOrder.getCreateTimeEnd()));
        }

        if (StringUtils.isNotBlank(ferryOrder.getDepartTime())) {
            criteria.like("departTime", ferryOrder.getDepartTime(), MatchMode.ANYWHERE);
        }

        if (StringUtils.isNotBlank(ferryOrder.getKeyword())) {
            Disjunction dis = Restrictions.disjunction();
            dis.add(Restrictions.eq("orderNumber", ferryOrder.getKeyword()));
            dis.add(Restrictions.eq("flightNumber", ferryOrder.getKeyword()));
            dis.add(Restrictions.eq("flightLineName", ferryOrder.getKeyword()));
            criteria.add(dis);
        }
    }

    public List<FerryOrder> getBySno(String ferryOrderNo) {
        Criteria<FerryOrder> criteria = new Criteria<FerryOrder>(FerryOrder.class);
        criteria.eq("ferryNumber", ferryOrderNo);
        return ferryOrderDao.findByCriteria(criteria);
    }

    public List<FerryOrderItem> getItemByOrderNoAndIdnumber(String ferryOrderNo, String idnumber) {
        Criteria<FerryOrderItem> criteria = new Criteria<FerryOrderItem>(FerryOrderItem.class);
        criteria.createCriteria("ferryOrder", "ferryOrder");
        criteria.eq("ferryOrder.ferryNumber", ferryOrderNo);
        criteria.eq("idnumber", idnumber);
        return ferryOrderItemDao.findByCriteria(criteria);
    }

    public void updateOrderBill(FerryOrder order) {
        OrderBillType billType = OrderBillType.valueOf(propertiesManager.getString("FERRY_BILL_TYPE"));
        Integer billDay = Integer.valueOf(propertiesManager.getString("FERRY_BILL_DAY"));
        order.setOrderBillType(billType);
        order.setOrderBillDays(billDay);
        order.setOrderBillDate(orderDetailService.getBillDate(order.getOrderBillType(), order.getOrderBillDays()));
        order.setOrderBillStatus(0);
        if (order.getStatus().equals(OrderStatus.CANCELED) || order.getStatus().equals(OrderStatus.REFUND)) {
            order.setOrderBillPrice(order.getPoundageAmount());
        } else {
            order.setOrderBillPrice(order.getAmount());
        }
        updateOrder(order);
    }

    /**
     * 轮渡船票退款申请
     *
     * @return
     */
    public Map<String, Object> doDefundOrder(Long ferryOrderId, SysUser loginUser) {
        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("success", false);
//        map.put("errorMsg", "该账单不是未结算状态，请获取最新数据进行操作。");
        FerryOrder ferryOrder = ferryOrderDao.load(ferryOrderId);
        map = FerryUtil.returnSaleOrder(ferryOrder.getFerryNumber());
        if (map.get("success") != null && (Boolean) map.get("success")) {   // 提交申请成功
            ferryOrder.setReturnAmount((Float) map.get("returnAmount"));    // 预退款金额
            ferryOrder.setPoundageAmount((Float) map.get("poundageAmount"));    // 预收手续费
            ferryOrder.setPoundageDescribe((String) map.get("poundageDescribe"));   // 手续费说明
            ferryOrder.setStatus(OrderStatus.CANCELING);
            ferryOrder.setRefundBy(loginUser.getId());
            ferryOrderDao.save(ferryOrder);

            // 如果是行程规划，查询是否有船票订单
            if (ferryOrder.getOrderId() != null) {
                com.data.data.hmly.service.order.entity.Order order = orderService.get(ferryOrder.getOrderId());
                orderService.updateOrderStatus(order, OrderStatus.CANCELING, OrderDetailStatus.CANCELING);
            }
            // 发送短信
            Map<String, Object> msgData = new HashMap<String, Object>();
            msgData.put("amount", ferryOrder.getReturnAmount());
            FerryMember ferryMember = ferryOrder.getUser().getFerryMember();
            // @WEB_SMS
            yhyMsgService.doSendSMS(msgData, ferryMember.getMobile(), MsgTemplateKey.USER_FERRY_REFUND_WAIT_TLE);
        }
        return map;
    }

    public Map<String, Object> doDefundOrder(FerryOrder ferryOrder, Member member) {
        Map<String, Object> map = FerryUtil.returnSaleOrder(ferryOrder.getFerryNumber());
        if (map.get("success") != null && (Boolean) map.get("success")) {   // 提交申请成功
            ferryOrder.setReturnAmount((Float) map.get("returnAmount"));    // 预退款金额
            ferryOrder.setPoundageAmount((Float) map.get("poundageAmount"));    // 预收手续费
            ferryOrder.setPoundageDescribe((String) map.get("poundageDescribe"));   // 手续费说明
            ferryOrder.setStatus(OrderStatus.CANCELING);
            ferryOrder.setRefundBy(member.getId());
            ferryOrderDao.save(ferryOrder);

            // 如果是行程规划，查询是否有船票订单
            if (ferryOrder.getOrderId() != null) {
                com.data.data.hmly.service.order.entity.Order order = orderService.get(ferryOrder.getOrderId());
                orderService.updateOrderStatus(order, OrderStatus.CANCELING, OrderDetailStatus.CANCELING);
            }
        }
        return map;
    }

    public FerryOrder getById(Long ferryOrderId) {
        FerryOrder ferryOrder = ferryOrderDao.load(ferryOrderId);
        return ferryOrder;
    }

    public void doRecordLog(String content, Long orderId, Long orderDetailId, OrderLogLevel orderLogLevel) {
        SysUser admin = sysUserService.findUserByAccount("admin");  // 默认为admin操作
        OrderLog orderLog = orderLogService.createOrderLog(admin, content, orderId, orderDetailId, orderLogLevel);
        orderLogService.loggingOrderLog(orderLog);
    }

}
