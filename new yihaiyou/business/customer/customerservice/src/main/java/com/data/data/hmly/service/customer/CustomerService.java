package com.data.data.hmly.service.customer;


import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.dao.UserDao;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.CommissionService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.dao.CommissionDao;
import com.data.data.hmly.service.order.entity.Commission;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Created by vacuity on 15/11/2.
 */

@Service
public class CustomerService {


    @Resource
    private CommissionDao commissionDao;
    @Resource
    private UserDao userDao;
    @Resource
    private CommissionService commissionService;
    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;

    public List<User> getCustomerList(User user, Page page, String account, String mobile) {
        Criteria<Commission> criteria = new Criteria<Commission>(Commission.class);
        DetachedCriteria orderDetailCriteria = criteria.createCriteria("orderDetail", "orderDetail", JoinType.INNER_JOIN);
        DetachedCriteria orderCriteria = orderDetailCriteria.createCriteria("order", "order", JoinType.INNER_JOIN);
        DetachedCriteria topProductCriteria = criteria.createCriteria("topProduct", "topProduct", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.or(Restrictions.eq("user.id", user.getId()), Restrictions.eq("topProduct.user.id", user.getId())));
        criteria.setProjection(Projections.groupProperty("order.user.id"));
        List<?> idList = commissionDao.findByCriteria(criteria, page);

        Criteria<User> userCriteria = new Criteria<User>(User.class);
        userCriteria.in("id", idList);
        if (account != null && !"".equals(account)) {
            userCriteria.eq("account", account);
        }
        if (mobile != null && !"".equals(mobile)) {
            userCriteria.eq("mobile", mobile);
        }
        List<User> userList = userDao.findByCriteria(userCriteria);
        return userList;
    }

    public User detail(long id) {
        return userService.get(id);
    }

    public List<Order> getOrderList(User user, Page page) {
        return orderService.getByUser(user, page);
    }

    public int getOrderNum(User user) {
        return getOrderList(user, null).size();
    }
}
