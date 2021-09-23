package com.data.data.hmly.service.balance;

import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.balance.dao.AccountLogDao;
import com.data.data.hmly.service.balance.entity.AccountLog;
import com.data.data.hmly.service.balance.entity.enums.AccountStatus;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.dao.SysSiteDao;
import com.data.data.hmly.service.dao.SysUnitDao;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by vacuity on 15/10/20.
 */

@Service
public class AccountLogService {
    @Resource
    private AccountLogDao accountLogDao;

    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private OrderService orderService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysSiteDao sysSiteDao;
    @Resource
    private SysUnitDao sysUnitDao;


    public List<AccountLog> findBalanceMgrList(AccountLog accountLog, Page pageInfo, Long companyId, SysUser sysUser,
                                               boolean isSiteAdmin, boolean isSupperAdmin) {
        Criteria<AccountLog> criteria = new Criteria<AccountLog>(AccountLog.class);
        criteria.createCriteria("companyUnit", "cp", JoinType.INNER_JOIN);
        criteria.createCriteria("user", "usr", JoinType.INNER_JOIN);

        if (!isSupperAdmin) {
            criteria.eq("cp.sysSite.id", sysUser.getSysSite().getId());
            if (!isSiteAdmin) {
                criteria.eq("companyUnit.id", sysUser.getSysUnit().getCompanyUnit().getId());   // 公司查询自己的数据
            }
        }
        foramtCond(accountLog, criteria);

        if (accountLog.getInType() != null) {
            criteria.in("type", accountLog.getInType());
        }

        if (companyId != null) {
            criteria.eq("companyUnit.id", companyId);
        }

        if (accountLog.getCreateTimeStart() != null) {
            criteria.ge("createTime", accountLog.getCreateTimeStart());
        }
        if (accountLog.getCreateTimeEnd() != null) {
            criteria.le("createTime", accountLog.getCreateTimeEnd());
        }
        criteria.orderBy("createTime", "DESC");
        return accountLogDao.findByCriteria(criteria, pageInfo);
    }

    public List<AccountLog> getAccountList(AccountLog accountLog,
                       Page pageInfo, SysUnit sysUnit, SysUser sysUser,
                       boolean isSiteAdmin, boolean isSupperAdmin) {
        Criteria<AccountLog> criteria = new Criteria<AccountLog>(AccountLog.class);
        // 数据过滤
        if (isSiteAdmin) {
            SysSite sysSite = sysSiteDao.load(sysUser.getSysSite().getId());
            List<SysUnit> units = sysUnitDao.findUnitBySite(sysSite);
//            units.add(sysUnit);
            criteria.in("companyUnit", units);
        } else if (!isSupperAdmin && !isSiteAdmin){
            criteria.eq("companyUnit", sysUser.getSysUnit().getCompanyUnit());
        }
        foramtCond(accountLog, criteria);
        criteria.orderBy("createTime", "DESC");
        return accountLogDao.findByCriteria(criteria, pageInfo);
    }

    public void foramtCond(AccountLog accountLog, Criteria<AccountLog> criteria) {


        if (accountLog.getOrderType() != null) {
            criteria.eq("orderType", accountLog.getOrderType());
        }
        if (accountLog.getUser() != null) {
            criteria.eq("user", accountLog.getUser());
        }
        if (accountLog.getAccountUser() != null && accountLog.getAccountUser().getId() != null) {
            criteria.eq("accountUser.id", accountLog.getAccountUser().getId());
        }
        if (accountLog.getInStatus() != null && !accountLog.getInStatus().isEmpty()) {
            List<SimpleExpression> simpleExpressions = Lists.newArrayList();
            for (AccountStatus status : accountLog.getInStatus()) {
                simpleExpressions.add(Restrictions.eq("status", status));
            }
            criteria.or(simpleExpressions.toArray(new SimpleExpression[simpleExpressions.size()]));
        }
        if (accountLog.getBalance() != null) {
            criteria.eq("balance", accountLog.getBalance());
        }
        if (accountLog.getMoney() != null) {
            criteria.eq("money", accountLog.getMoney());
        }
        if (accountLog.getStatus() != null) {
            criteria.eq("status", accountLog.getStatus());
        }
        if (accountLog.getOrderNo() != null) {
            criteria.eq("orderNo", accountLog.getOrderNo());
        }
        if (accountLog.getType() != null) {
            criteria.eq("type", accountLog.getType());
        }

    }


    public AccountLog get(Long id) {
        return accountLogDao.load(id);
    }



    public void updateOrderStatus(Order order) {

        List<OrderDetail> detailList = orderDetailService.getByOrderId(order.getId());
        OrderDetail detail = null;
        if (!detailList.isEmpty() && order.getStatus() == OrderStatus.PAYED) {
            detail = detailList.get(0);
            order.setStatus(OrderStatus.SUCCESS);
            detail.setStatus(OrderDetailStatus.SUCCESS);
            orderDetailService.update(detail);
        } else if (order.getStatus() == OrderStatus.FAILED) {
            detail = detailList.get(0);
            detail.setStatus(OrderDetailStatus.FAILED);
            orderDetailService.update(detail);
        } else if (order.getStatus() == OrderStatus.WAIT) {
            detail = detailList.get(0);
            detail.setStatus(OrderDetailStatus.WAITING);
            orderDetailService.update(detail);
        }
        orderService.update(order);

    }


    public void update(AccountLog accountLog) {
        accountLog.setUpdateTime(new Date());
        accountLogDao.save(accountLog);
    }

    public void save(AccountLog accountLog) {
        accountLog.setCreateTime(new Date());
        accountLog.setUpdateTime(new Date());
        accountLogDao.save(accountLog);
    }

    /**
     * 参见com.data.data.hmly.service.balance.BalanceService#updateBalance(java.lang.Double, com.data.data.hmly.service.balance.entity.enums.AccountType, java.lang.Long, java.lang.Long, java.lang.Long)
     * @param order
     * @param sysUser
     * @param companyUnit
     * @return
     */
    @Deprecated
    public boolean insertAccountLog(Order order, SysUser sysUser, SysUnit companyUnit) {
        AccountLog accountLog = new AccountLog();
        SysUser sysUserTemp = sysUserService.load(sysUser.getId());
        Double balance = 0d;
        if (sysUserTemp.getBalance() == null) {
            balance = order.getPrice().doubleValue();
        } else {
            balance = sysUserTemp.getBalance() + order.getPrice();
        }
        sysUserTemp.setBalance(balance);
        accountLog.setBalance(balance);
        accountLog.setMoney(order.getPrice().doubleValue());
        accountLog.setOrderNo(order.getOrderNo());
        accountLog.setDescription(order.getRemark());
        accountLog.setType(AccountType.recharge);
        accountLog.setStatus(AccountStatus.normal);
        accountLog.setDescription("充值");
        accountLog.setUser(sysUserTemp);
        accountLog.setCompanyUnit(companyUnit);

        List<AccountLog> accountLogs = findListByAccountLog(accountLog);

        if (accountLogs.isEmpty()) {
            sysUserService.updateUser(sysUserTemp);
            save(accountLog);
            return true;
        } else {
            return false;
        }
    }

    public Long countAccountLog(AccountLog accountLog) {
        Criteria<AccountLog> criteria = new Criteria<AccountLog>(AccountLog.class);
        foramtCond(accountLog, criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) accountLogDao.findUniqueValue(criteria);
    }

    public List<AccountLog> listAccountLog(AccountLog accountLog, Page page, String... orderProperties) {
        Criteria<AccountLog> criteria = new Criteria<AccountLog>(AccountLog.class);
        foramtCond(accountLog, criteria);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(org.hibernate.criterion.Order.desc(orderProperties[0]));
        }
        if (page != null) {
            return accountLogDao.findByCriteria(criteria, page);
        }
        return accountLogDao.findByCriteria(criteria);
    }

    public List<AccountLog> findListByAccountLogAll(AccountLog accountLog, String... orderProperties) {

        Criteria<AccountLog> criteria = new Criteria<AccountLog>(AccountLog.class);

        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(org.hibernate.criterion.Order.desc(orderProperties[0]));
        }
        if (accountLog.getOrderNo() != null) {
            criteria.eq("orderNo", accountLog.getOrderNo());
        }
        if (accountLog.getStatus() != null) {
            criteria.eq("status", accountLog.getStatus());
        }
        if (accountLog.getType() != null) {
            criteria.eq("type", accountLog.getType());
        }
        if (accountLog.getOrderType() != null) {
            criteria.eq("orderType", accountLog.getOrderType());
        }
        if (accountLog.getUser() != null) {
            criteria.eq("user", accountLog.getUser());
        }
        if (accountLog.getAccountUser() != null) {
            criteria.eq("accountUser", accountLog.getAccountUser());
        }
        if (accountLog.getInStatus() != null && !accountLog.getInStatus().isEmpty()) {
            List<SimpleExpression> simpleExpressions = Lists.newArrayList();
            for (AccountStatus status : accountLog.getInStatus()) {
                simpleExpressions.add(Restrictions.eq("status", status));
            }
            criteria.or(simpleExpressions.toArray(new SimpleExpression[simpleExpressions.size()]));
        }
        return accountLogDao.findByCriteria(criteria);
    }

    public List<AccountLog> findListByAccountLog(AccountLog accountLog, String... orderProperties) {

        Criteria<AccountLog> criteria = new Criteria<AccountLog>(AccountLog.class);

        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(org.hibernate.criterion.Order.desc(orderProperties[0]));
        }
        if (accountLog.getOrderNo() != null) {
            criteria.eq("orderNo", accountLog.getOrderNo());
        }
        if (accountLog.getStatus() != null) {
            criteria.eq("status", accountLog.getStatus());
        }
        if (accountLog.getType() != null) {
            criteria.eq("type", accountLog.getType());
        }
        if (accountLog.getUser() != null) {
            criteria.eq("user", accountLog.getUser());
        }
        criteria.ne("orderType", OrderType.shenzhou);
        return accountLogDao.findByCriteria(criteria);
    }


    /**
     * 随机生成订单编号
     * @return
     */
    public String createOrderNo() {

        int code = 0;

        while (true) {

            int num = new Random().nextInt(1000000000);

            while (num < 100000000) {
                num = num * 10;
            }
                code = num;
                break;
        }

        return "A"+code;

    }


    public List<AccountLog> findYhyBalanceMgrList(AccountLog accountLog, Page pageInfo, SysUser loginUser) {

        Criteria<AccountLog> criteria = new Criteria<AccountLog>(AccountLog.class);

        foramtCond(accountLog, criteria);

        criteria.eq("user.id", loginUser.getId());

        if (accountLog.getInType() != null) {
            criteria.eq("type", accountLog.getInType());
        }

        if (accountLog.getCreateTimeStart() != null) {
            criteria.ge("createTime", accountLog.getCreateTimeStart());
        }
        if (accountLog.getCreateTimeEnd() != null) {
            criteria.le("createTime", accountLog.getCreateTimeEnd());
        }
        criteria.orderBy("createTime", "DESC");
        return accountLogDao.findByCriteria(criteria, pageInfo);

    }
}
