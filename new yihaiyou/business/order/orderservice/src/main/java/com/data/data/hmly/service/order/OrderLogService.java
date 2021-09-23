package com.data.data.hmly.service.order;

import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.dao.OrderLogDao;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.data.data.hmly.service.order.entity.enums.OrderLogLevel;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zzl on 2016/3/28.
 */
@Service
public class OrderLogService {

    @Resource
    private PropertiesManager propertiesManager;

    @Resource
    private OrderLogDao orderLogDao;
    @Resource
    private UserService userService;

    public User getSysOrderLogUser() {
        String orderLogUserCfg = propertiesManager.getString("ORDER_LOG_USER");
        User user;
        Long userId;
        if (StringUtils.hasText(orderLogUserCfg) && orderLogUserCfg.matches("^[1-9]\\d*$")) {
            userId = Long.parseLong(orderLogUserCfg);
            user = userService.get(userId);
        } else {
            user = userService.get(9L);
        }
        return user;
    }

    public void loggingOrderLog(OrderLog orderLog) {
        orderLogDao.save(orderLog);
    }

    public OrderLog createOrderLog(User operator, String content, Long orderId, Long orderDetailId, Object...level) {
        OrderLog orderLog = new OrderLog();
        if (orderId == null) {
            return null;
        }
        if (operator == null || operator.getId() == null) {
            return null;
        }
        if (StringUtils.isBlank(content)) {
            return null;
        }
        if (level != null && level.length == 1 && level[0] != null) {
            orderLog.setLevel(OrderLogLevel.valueOf(level[0].toString()));
        }
        orderLog.setOrderId(orderId);
        orderLog.setOrderDetailId(orderDetailId);
        orderLog.setLogContent(content);
        orderLog.setOperator(operator);
        orderLog.setRecordTime(new Date());
        return orderLog;
    }

    public List<OrderLog> getLogs(String orderIdStr, String orderDetailIdStr, Page page) {
        Criteria<OrderLog> criteria = new Criteria<OrderLog>(OrderLog.class);
        criteria.orderBy("id", "desc");
        Long orderId = Long.parseLong(orderIdStr);
        criteria.eq("orderId", orderId);
        if (StringUtils.hasText(orderDetailIdStr)) {
            Long orderDetailId = Long.parseLong(orderDetailIdStr);
            criteria.eq("orderDetailId", orderDetailId);
        }
        return orderLogDao.findByCriteria(criteria, page);
    }

    public List<OrderLog> getLogsByOrderId(Long orderId) {
        Criteria<OrderLog> criteria = new Criteria<OrderLog>(OrderLog.class);
        criteria.eq("orderId", orderId);
        return orderLogDao.findByCriteria(criteria);
    }

    public List<OrderLog> getLogsByOrderDetailId(Long orderDetailId) {
        Criteria<OrderLog> criteria = new Criteria<OrderLog>(OrderLog.class);
        criteria.eq("orderDetailId", orderDetailId);
        return orderLogDao.findByCriteria(criteria);
    }
}
